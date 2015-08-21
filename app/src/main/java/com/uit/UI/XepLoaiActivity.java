package com.uit.UI;

import java.util.Date;

import com.uit.Functions.NgayThang;
import com.uit.Providers.UserDB;
import com.uit.Providers.XepLoai;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class XepLoaiActivity extends Activity {
	UserDB p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Date testDate = new Date();
		// Log.d("Time now!", ((Long)testDate.getTime()).toString());
		p = new UserDB(this);

		TableLayout table = new TableLayout(this);

		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		TableRow rowTitle = new TableRow(this);
		rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

		TableRow rowLabels = new TableRow(this);
		TableRow[] rowDetail = new TableRow[10];

		// title column/row
		TextView title = new TextView(this);
		title.setText("Danh sách các kết quả tốt nhất");
		title.setTextColor(Color.parseColor("#990000"));

		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		title.setGravity(Gravity.CENTER);
		title.setTypeface(Typeface.SERIF, Typeface.BOLD);

		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = 4;

		rowTitle.addView(title, params);// add tieu de vao hang dau tien

		TextView[] tv = new TextView[4];
		for (int i = 0; i < 4; i++) {
			tv[i] = new TextView(this);
			if (i == 0) {
				tv[i].setText("Tên");
			}
			if (i == 1) {
				tv[i].setText("Điểm");
			}
			if (i == 2) {
				tv[i].setText("Thời gian");
			}
			if (i == 3) {
				tv[i].setText("Ngày thi");
			}
			tv[i].setTextColor(Color.parseColor("#330000"));
			tv[i].setTypeface(Typeface.SERIF, Typeface.BOLD);
			tv[i].setGravity(Gravity.CENTER);

			rowLabels.addView(tv[i]);
		}
		table.addView(rowTitle);
		table.addView(rowLabels);

		int[] name = new int[10];
		int[] diem = new int[10];
		int[] thoigianthi = new int[10];
		long[] ngaythi = new long[10];
		int index = 0;

		XepLoai xl = new XepLoai(this);
		xl.open();
		Cursor c = xl.getTenRows();
		if (c.moveToFirst()) {
			do {
				name[index] = c.getInt(0);
				ngaythi[index] = c.getLong(1);
				thoigianthi[index] = c.getInt(2);
				diem[index] = c.getInt(3);
				index++;// index toi da la 10

			} while (c.moveToNext() && index < 10);// lay 10 nguoi cao nhat
		}
		xl.close();

		NgayThang nt = new NgayThang();

		for (int i = 0; i < index; i++) {
			rowDetail[i] = new TableRow(this);
			for (int j = 0; j < 4; j++) {
				tv[j] = new TextView(this);
				if (j == 0) {
					p.open();
					Cursor cUserName = p.getUser(name[i]);
					String username = "Unknown";
					if (cUserName.moveToFirst()) {
						username = cUserName.getString(1).toString();
					}
					tv[j].setText(username);
					p.close();
				}
				if (j == 1) {
					tv[j].setText(((Integer) diem[i]).toString());
				}
				if (j == 2) {
					// chuyen ve phut, giay
					String[] minsec = new String[2];
					minsec = nt.FromLongToMinSec(thoigianthi[i]);
					tv[j].setText(minsec[0] + "':" + minsec[1] + "\"");
				}
				if (j == 3) {
					// chuyen ve ngay thang nam

					Date d = nt.FromLongToDate(ngaythi[i]);
					// ngay - thang - nam
					String ngay, thang, nam, gio, phut, giay;
					ngay = ((Integer) d.getDate()).toString();
					thang = ((Integer) (d.getMonth() + 1)).toString();
					nam = ((Integer) (d.getYear() + 1900)).toString();
					gio = ((Integer) d.getHours()).toString();
					phut = ((Integer) d.getMinutes()).toString();
					giay = ((Integer) d.getSeconds()).toString();

					tv[j].setText(ngay + "/" + thang + "/" + nam + "\n" + gio
							+ ":" + phut + ":" + giay);
				}
				//set mau chan le
				if(i % 2 == 0){
					tv[j].setTextColor(Color.parseColor("#FF3600"));
				}else{
					tv[j].setTextColor(Color.parseColor("#0000CC"));
				}
				tv[j].setTypeface(Typeface.SERIF);
				tv[j].setGravity(Gravity.CENTER);

				rowDetail[i].addView(tv[j]);
			}
			table.addView(rowDetail[i]);
		}

		setContentView(table);

		// setContentView(R.layout.account);
	}

}
