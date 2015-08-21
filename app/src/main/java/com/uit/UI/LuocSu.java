package com.uit.UI;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.uit.R;
import com.uit.Functions.NgayThang;
import com.uit.Providers.UserDB;
import com.uit.Providers.XepLoai;
import com.uit.objects.UserAction;

public class LuocSu extends Activity {
	UserDB p;
	int userID;
	String username;
	int[] diem = new int[10];
	int[] thoigianthi = new int[10];
	long[] ngaythi = new long[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Date testDate = new Date();
		// Log.d("Time now!", ((Long)testDate.getTime()).toString());
		SharedPreferences account = getSharedPreferences(UserAction.ACCOUNT, 0);
		username = account.getString(UserAction.NAME, null);
		
		SharedPreferences uID = getSharedPreferences(UserAction.USERID, 0);
		userID  = uID.getInt(UserAction.USERID, 0);
		
		p = new UserDB(this);

		TableLayout table = new TableLayout(this);
		table.setBackgroundResource(R.drawable.ketqua_bgr);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		TableRow rowTitle = new TableRow(this);
		rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

		TableRow rowLabels = new TableRow(this);
		TableRow[] rowDetail = new TableRow[10];
		
		// title column/row
		TextView title = new TextView(this);
		title.setText("Mười bài thi gần nhất của " + username);
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
				tv[i].setText("Ngày thi");
			}
			if (i == 1) {
				tv[i].setText("Điểm");
			}
			if (i == 2) {
				tv[i].setText("Thời gian");
			}
			if (i == 3) {
				tv[i].setText("Chi tiết");
			}
			tv[i].setTextColor(Color.parseColor("#330000"));
			tv[i].setTypeface(Typeface.SERIF, Typeface.BOLD);
			tv[i].setGravity(Gravity.CENTER);

			rowLabels.addView(tv[i]);
		}
		table.addView(rowTitle);
		table.addView(rowLabels);

		
		
		int index = 0;

		XepLoai xl = new XepLoai(this);
		xl.open();
		Cursor c = xl.getTenRowsByUserId(userID);
		if (c != null && c.moveToFirst()) {
			do {
				ngaythi[index] = c.getLong(0);
				diem[index] = c.getInt(1);
				thoigianthi[index] = c.getInt(2);				
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
					//set datetime
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
							+ ":" + phut + "':" + giay + "\"");
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
					final long nthi = ngaythi[i];
					//detail
					tv[j].setText("Chi tiết");
					tv[j].setOnClickListener(new TextView.OnClickListener(){

						public void onClick(View v) {
							// TODO Auto-generated method stub
							//khi click vao se hien thi intent moi
							//liet ke bai thi tai thoi diem do
							UserAction u = new UserAction(LuocSu.this);
							u.UpdateTimeDo(nthi);
							
							Intent i = new Intent(LuocSu.this, KetQuaMotAccount.class);
							startActivity(i);
						}
						
					});
				}
				//set mau chan le
				if(i % 2 == 0){
					tv[j].setTextColor(Color.parseColor("#0004FF"));
				}else{
					tv[j].setTextColor(Color.parseColor("#A80002"));
				}
				tv[j].setTypeface(Typeface.SERIF);
				tv[j].setGravity(Gravity.CENTER);

				rowDetail[i].addView(tv[j]);
			}
			table.addView(rowDetail[i]);
		}

		setContentView(table);
	}

}
