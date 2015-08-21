package com.uit.UI;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.uit.R;
import com.uit.Functions.NgayThang;
import com.uit.Providers.DeThiRandom;
import com.uit.Providers.XepLoai;
import com.uit.objects.UserAction;

public class KetQuaMotAccount extends Activity implements OnClickListener {

	public final static String IDDETHI = "id de thi";
	public final static String IDCAUHOI = "id cau hoi";

	int thoigianhoanthanh, ketqua;
	int idDe = 0;
	TextView tenAccount; // Textview chứa tên account
	TextView ngayGioThi; // Textview chứa ngày giờ thi
	TextView diemThi;
	TextView thoigianthi;
	TextView doTruot;

	ImageView imageKetQuaCau1;
	ImageView imageKetQuaCau2;
	ImageView imageKetQuaCau3;
	ImageView imageKetQuaCau4;
	ImageView imageKetQuaCau5;
	ImageView imageKetQuaCau6;
	ImageView imageKetQuaCau7;
	ImageView imageKetQuaCau8;
	ImageView imageKetQuaCau9;
	ImageView imageKetQuaCau10;
	ImageView imageKetQuaCau11;
	ImageView imageKetQuaCau12;
	ImageView imageKetQuaCau13;
	ImageView imageKetQuaCau14;
	ImageView imageKetQuaCau15;
	ImageView imageKetQuaCau16;
	ImageView imageKetQuaCau17;
	ImageView imageKetQuaCau18;
	ImageView imageKetQuaCau19;
	ImageView imageKetQuaCau20;
	ImageView imageKetQuaCau21;
	ImageView imageKetQuaCau22;
	ImageView imageKetQuaCau23;
	ImageView imageKetQuaCau24;
	ImageView imageKetQuaCau25;
	ImageView imageKetQuaCau26;
	ImageView imageKetQuaCau27;
	ImageView imageKetQuaCau28;
	ImageView imageKetQuaCau29;
	ImageView imageKetQuaCau30;
	ImageView imageKQEmotion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.uit.R.layout.ketquathi_mot_account);

		tenAccount = (TextView) findViewById(com.uit.R.id.kq_mot_user_name);
		ngayGioThi = (TextView) findViewById(com.uit.R.id.kq_mot_user_ngaythi);
		diemThi = (TextView) findViewById(com.uit.R.id.kq_mot_user_diem);
		thoigianthi = (TextView) findViewById(com.uit.R.id.kq_mot_user_thoigianthi);
		doTruot = (TextView) findViewById(com.uit.R.id.kq_mot_user_dotruot);

		imageKQEmotion = (ImageView) findViewById(R.id.kq_image_emotion);
		imageKetQuaCau1 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau1);
		imageKetQuaCau2 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau2);
		imageKetQuaCau3 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau3);
		imageKetQuaCau4 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau4);
		imageKetQuaCau5 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau5);
		imageKetQuaCau6 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau6);
		imageKetQuaCau7 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau7);
		imageKetQuaCau8 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau8);
		imageKetQuaCau9 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau9);
		imageKetQuaCau10 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau10);
		imageKetQuaCau11 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau11);
		imageKetQuaCau12 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau12);
		imageKetQuaCau13 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau13);
		imageKetQuaCau14 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau14);
		imageKetQuaCau15 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau15);
		imageKetQuaCau16 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau16);
		imageKetQuaCau17 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau17);
		imageKetQuaCau18 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau18);
		imageKetQuaCau19 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau19);
		imageKetQuaCau20 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau20);
		imageKetQuaCau21 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau21);
		imageKetQuaCau22 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau22);
		imageKetQuaCau23 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau23);
		imageKetQuaCau24 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau24);
		imageKetQuaCau25 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau25);
		imageKetQuaCau26 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau26);
		imageKetQuaCau27 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau27);
		imageKetQuaCau28 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau28);
		imageKetQuaCau29 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau29);
		imageKetQuaCau30 = (ImageView) findViewById(com.uit.R.id.imageKetQuaCau30);

		imageKetQuaCau1.setOnClickListener(this);
		imageKetQuaCau2.setOnClickListener(this);
		imageKetQuaCau3.setOnClickListener(this);
		imageKetQuaCau4.setOnClickListener(this);
		imageKetQuaCau5.setOnClickListener(this);
		imageKetQuaCau6.setOnClickListener(this);
		imageKetQuaCau7.setOnClickListener(this);
		imageKetQuaCau8.setOnClickListener(this);
		imageKetQuaCau9.setOnClickListener(this);
		imageKetQuaCau10.setOnClickListener(this);
		imageKetQuaCau11.setOnClickListener(this);
		imageKetQuaCau12.setOnClickListener(this);
		imageKetQuaCau13.setOnClickListener(this);
		imageKetQuaCau14.setOnClickListener(this);
		imageKetQuaCau15.setOnClickListener(this);
		imageKetQuaCau16.setOnClickListener(this);
		imageKetQuaCau17.setOnClickListener(this);
		imageKetQuaCau18.setOnClickListener(this);
		imageKetQuaCau19.setOnClickListener(this);
		imageKetQuaCau20.setOnClickListener(this);
		imageKetQuaCau21.setOnClickListener(this);
		imageKetQuaCau22.setOnClickListener(this);
		imageKetQuaCau23.setOnClickListener(this);
		imageKetQuaCau24.setOnClickListener(this);
		imageKetQuaCau25.setOnClickListener(this);
		imageKetQuaCau26.setOnClickListener(this);
		imageKetQuaCau27.setOnClickListener(this);
		imageKetQuaCau28.setOnClickListener(this);
		imageKetQuaCau29.setOnClickListener(this);
		imageKetQuaCau30.setOnClickListener(this);

		SharedPreferences NGTShared = getSharedPreferences(
				UserAction.START_TIME, 0);
		long ngaygiothi = NGTShared.getLong(UserAction.START_TIME, 0);

		SharedPreferences Account = getSharedPreferences(UserAction.ACCOUNT, 0);
		String tenAccount = Account.getString(UserAction.NAME, null);

		createLayoutViewResult(this, ngaygiothi, tenAccount);

	}

	public void createLayoutViewResult(final Context _context,
			final long ngaygiothi, String tenaccount) {

		tenAccount.setText("Tên Account: " + tenaccount);

		NgayThang ngaythi = new NgayThang();

		Date d = ngaythi.FromLongToDate(ngaygiothi);
		// ngay - thang - nam
		String ngay, thang, nam, gio, phut, giay;
		ngay = ((Integer) d.getDate()).toString();
		thang = ((Integer) (d.getMonth() + 1)).toString();
		nam = ((Integer) (d.getYear() + 1900)).toString();
		gio = ((Integer) d.getHours()).toString();
		phut = ((Integer) d.getMinutes()).toString();
		giay = ((Integer) d.getSeconds()).toString();

		ngayGioThi.setText("Ngày thi: " + ngay + "/" + thang + "/" + nam
				+ "      " + gio + ":" + phut + "':" + giay + "\"");

		int[] listKetQua = new int[30];
		listKetQua = this.getKetQuaTheoNgayThi(_context, ngaygiothi);

		for (int i : listKetQua) {
			Log.i("ket qua thi", ((Integer) i).toString());
		}

		for (int i = 0; i < 30; i++) {
			if (listKetQua[i] == 1)
				suaHinhDung(i);// chinh sua hinh anh cua cac cau tra loi dung
			if (listKetQua[i] == -1)
				suaHinhSai(i);// chinh sua hinh anh cua cac cau tra loi sai
		}

		// chuyen ve phut, giay
		String[] minsec = new String[2];
		minsec = ngaythi.FromLongToMinSec(thoigianhoanthanh);
		thoigianthi.setText("Thời gian làm bài: " + minsec[0] + "':"
				+ minsec[1] + "\"");

		diemThi.setText("Điểm thi: " + ketqua);
		if (ketqua >= 26) {
			doTruot.setText("Kết quả: Chúc mừng! Bạn đã vượt qua kỳ thi.");
			imageKQEmotion.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.kq_emtion_dau));
		} else {
			doTruot.setText("Kết quả: Bạn chưa vượt qua. Hãy cố gắng hơn!");
			imageKQEmotion.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.kq_emtion_rot));
		}

	}

	/**
	 * Hàm trả về một mảng các câu trả lời đúng, sai, hoặc chưa trả lời của đề
	 * thi thuộc user tại thời điểm cho trước.
	 * 
	 * @param _context
	 * @param ngaygiothi
	 * @return
	 */

	int[] getKetQuaTheoNgayThi(Context _context, long ngaygiothi) {
		// lay ve idBoDe, idUser, thoigianhoanthanh, ketqua thi tai thoi gian
		// cho truoc
		int idUser = 0;
		XepLoai xl = new XepLoai(this);
		xl.open();
		Cursor c = xl.getInfoBelongTime(ngaygiothi);
		if (c != null) {
			if (c.moveToFirst()) {
				idDe = c.getInt(0);
				idUser = c.getInt(1);
				thoigianhoanthanh = c.getInt(2);
				ketqua = c.getInt(3);
			}

		}
		xl.close();

		Log.w("id de thi", ((Integer) idDe).toString());
		Log.w("id User", ((Integer) idUser).toString());

		// lay ve ket qua nhung cau tra loi thuoc idBoDe cua idUser
		DeThiRandom dtrd = new DeThiRandom(_context);
		int[] ketquathi = new int[30];
		int index = 0;
		int traloidung = 0;
		dtrd.open();
		Cursor cKQ = dtrd.getKetQuaByIdBoDeIdUser(idDe, idUser);
		if (cKQ != null && cKQ.moveToFirst()) {
			do {

				if (cKQ.getString(0) != null && !cKQ.getString(0).equals("")) {
					// neu co chon phuong an tra loi
					traloidung = cKQ.getInt(1);

					if (traloidung == 1) {
						// tra loi dung
						ketquathi[index] = 1;
					} else {
						ketquathi[index] = -1;// tra loi sai
					}
				}

				index++;
			} while (cKQ.moveToNext());
		}
		dtrd.close();

		return ketquathi;

	}

	public void UpdateSharedPreferences(int idDeThi, int idCauhoi) {
		SharedPreferences dethi = this.getSharedPreferences(IDDETHI, 0);
		SharedPreferences.Editor editor = dethi.edit();
		editor.putInt(IDDETHI, idDeThi);
		editor.commit();

		SharedPreferences cauhoi = this.getSharedPreferences(IDCAUHOI, 0);
		SharedPreferences.Editor editor2 = cauhoi.edit();
		editor2.putInt(IDCAUHOI, idCauhoi);
		editor2.commit();
	}

	public void suaHinhDung(int idCau) {
		switch (idCau) {
		case 0:
			imageKetQuaCau1.setBackgroundResource(com.uit.R.drawable.nt_one);
			break;
		case 1:
			imageKetQuaCau2.setBackgroundResource(com.uit.R.drawable.nt_two);
			break;
		case 2:
			imageKetQuaCau3.setBackgroundResource(com.uit.R.drawable.nt_three);
			break;
		case 3:
			imageKetQuaCau4.setBackgroundResource(com.uit.R.drawable.nt_four);
			break;
		case 4:
			imageKetQuaCau5.setBackgroundResource(com.uit.R.drawable.nt_five);
			break;
		case 5:
			imageKetQuaCau6.setBackgroundResource(com.uit.R.drawable.nt_six);
			break;
		case 6:
			imageKetQuaCau7.setBackgroundResource(com.uit.R.drawable.nt_seven);
			break;
		case 7:
			imageKetQuaCau8.setBackgroundResource(com.uit.R.drawable.nt_eight);
			break;
		case 8:
			imageKetQuaCau9.setBackgroundResource(com.uit.R.drawable.nt_nine);
			break;
		case 9:
			imageKetQuaCau10.setBackgroundResource(com.uit.R.drawable.nt_ten);
			break;
		case 10:
			imageKetQuaCau11
					.setBackgroundResource(com.uit.R.drawable.nt_elevent);
			break;
		case 11:
			imageKetQuaCau12
					.setBackgroundResource(com.uit.R.drawable.nt_twelve);
			break;
		case 12:
			imageKetQuaCau13
					.setBackgroundResource(com.uit.R.drawable.nt_thirteen);
			break;
		case 13:
			imageKetQuaCau14
					.setBackgroundResource(com.uit.R.drawable.nt_fourteen);
			break;
		case 14:
			imageKetQuaCau15
					.setBackgroundResource(com.uit.R.drawable.nt_fifteen);
			break;
		case 15:
			imageKetQuaCau16
					.setBackgroundResource(com.uit.R.drawable.nt_sixteen);
			break;
		case 16:
			imageKetQuaCau17
					.setBackgroundResource(com.uit.R.drawable.nt_seventeen);
			break;
		case 17:
			imageKetQuaCau18
					.setBackgroundResource(com.uit.R.drawable.nt_eighteen);
			break;
		case 18:
			imageKetQuaCau19
					.setBackgroundResource(com.uit.R.drawable.nt_nineteen);
			break;
		case 19:
			imageKetQuaCau20
					.setBackgroundResource(com.uit.R.drawable.nt_twenty);
			break;
		case 20:
			imageKetQuaCau21.setBackgroundResource(com.uit.R.drawable.nt_t_one);
			break;
		case 21:
			imageKetQuaCau22.setBackgroundResource(com.uit.R.drawable.nt_t_two);
			break;
		case 22:
			imageKetQuaCau23
					.setBackgroundResource(com.uit.R.drawable.nt_t_three);
			break;
		case 23:
			imageKetQuaCau24
					.setBackgroundResource(com.uit.R.drawable.nt_t_four);
			break;
		case 24:
			imageKetQuaCau25
					.setBackgroundResource(com.uit.R.drawable.nt_t_five);
			break;
		case 25:
			imageKetQuaCau26.setBackgroundResource(com.uit.R.drawable.nt_t_six);
			break;
		case 26:
			imageKetQuaCau27
					.setBackgroundResource(com.uit.R.drawable.nt_t_seven);
			break;
		case 27:
			imageKetQuaCau28
					.setBackgroundResource(com.uit.R.drawable.nt_t_eight);
			break;
		case 28:
			imageKetQuaCau29
					.setBackgroundResource(com.uit.R.drawable.nt_t_nine);
			break;
		case 29:
			imageKetQuaCau30
					.setBackgroundResource(com.uit.R.drawable.nt_thirty);
			break;
		}
	}

	public void suaHinhSai(int idCau) {
		switch (idCau) {
		case 0:
			imageKetQuaCau1.setBackgroundResource(com.uit.R.drawable.nf_one);
			break;
		case 1:
			imageKetQuaCau2.setBackgroundResource(com.uit.R.drawable.nf_two);
			break;
		case 2:
			imageKetQuaCau3.setBackgroundResource(com.uit.R.drawable.nf_three);
			break;
		case 3:
			imageKetQuaCau4.setBackgroundResource(com.uit.R.drawable.nf_four);
			break;
		case 4:
			imageKetQuaCau5.setBackgroundResource(com.uit.R.drawable.nf_five);
			break;
		case 5:
			imageKetQuaCau6.setBackgroundResource(com.uit.R.drawable.nf_six);
			break;
		case 6:
			imageKetQuaCau7.setBackgroundResource(com.uit.R.drawable.nf_seven);
			break;
		case 7:
			imageKetQuaCau8.setBackgroundResource(com.uit.R.drawable.nf_eight);
			break;
		case 8:
			imageKetQuaCau9.setBackgroundResource(com.uit.R.drawable.nf_nine);
			break;
		case 9:
			imageKetQuaCau10.setBackgroundResource(com.uit.R.drawable.nf_ten);
			break;
		case 10:
			imageKetQuaCau11
					.setBackgroundResource(com.uit.R.drawable.nf_elevent);
			break;
		case 11:
			imageKetQuaCau12
					.setBackgroundResource(com.uit.R.drawable.nf_twelve);
			break;
		case 12:
			imageKetQuaCau13
					.setBackgroundResource(com.uit.R.drawable.nf_thirteen);
			break;
		case 13:
			imageKetQuaCau14
					.setBackgroundResource(com.uit.R.drawable.nf_fourteen);
			break;
		case 14:
			imageKetQuaCau15
					.setBackgroundResource(com.uit.R.drawable.nf_fifteen);
			break;
		case 15:
			imageKetQuaCau16
					.setBackgroundResource(com.uit.R.drawable.nf_sixteen);
			break;
		case 16:
			imageKetQuaCau17
					.setBackgroundResource(com.uit.R.drawable.nf_seventeen);
			break;
		case 17:
			imageKetQuaCau18
					.setBackgroundResource(com.uit.R.drawable.nf_eighteen);
			break;
		case 18:
			imageKetQuaCau19
					.setBackgroundResource(com.uit.R.drawable.nf_nineteen);
			break;
		case 19:
			imageKetQuaCau20
					.setBackgroundResource(com.uit.R.drawable.nf_twenty);
			break;
		case 20:
			imageKetQuaCau21.setBackgroundResource(com.uit.R.drawable.nf_t_one);
			break;
		case 21:
			imageKetQuaCau22.setBackgroundResource(com.uit.R.drawable.nf_t_two);
			break;
		case 22:
			imageKetQuaCau23
					.setBackgroundResource(com.uit.R.drawable.nf_t_three);
			break;
		case 23:
			imageKetQuaCau24
					.setBackgroundResource(com.uit.R.drawable.nf_t_four);
			break;
		case 24:
			imageKetQuaCau25
					.setBackgroundResource(com.uit.R.drawable.nf_t_five);
			break;
		case 25:
			imageKetQuaCau26.setBackgroundResource(com.uit.R.drawable.nf_t_six);
			break;
		case 26:
			imageKetQuaCau27
					.setBackgroundResource(com.uit.R.drawable.nf_t_seven);
			break;
		case 27:
			imageKetQuaCau28
					.setBackgroundResource(com.uit.R.drawable.nf_t_eight);
			break;
		case 28:
			imageKetQuaCau29
					.setBackgroundResource(com.uit.R.drawable.nf_t_nine);
			break;
		case 29:
			imageKetQuaCau30
					.setBackgroundResource(com.uit.R.drawable.nf_thirty);
			break;
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case com.uit.R.id.imageKetQuaCau1:

			UpdateSharedPreferences(idDe, 0);
			break;
		case com.uit.R.id.imageKetQuaCau2:

			UpdateSharedPreferences(idDe, 1);
			break;
		case com.uit.R.id.imageKetQuaCau3:

			UpdateSharedPreferences(idDe, 2);
			break;
		case com.uit.R.id.imageKetQuaCau4:

			UpdateSharedPreferences(idDe, 3);
			break;
		case com.uit.R.id.imageKetQuaCau5:

			UpdateSharedPreferences(idDe, 4);
			break;
		case com.uit.R.id.imageKetQuaCau6:

			UpdateSharedPreferences(idDe, 5);
			break;
		case com.uit.R.id.imageKetQuaCau7:

			UpdateSharedPreferences(idDe, 6);
			break;
		case com.uit.R.id.imageKetQuaCau8:

			UpdateSharedPreferences(idDe, 7);
			break;
		case com.uit.R.id.imageKetQuaCau9:

			UpdateSharedPreferences(idDe, 8);
			break;
		case com.uit.R.id.imageKetQuaCau10:

			UpdateSharedPreferences(idDe, 9);
			break;
		case com.uit.R.id.imageKetQuaCau11:

			UpdateSharedPreferences(idDe, 10);
			break;
		case com.uit.R.id.imageKetQuaCau12:

			UpdateSharedPreferences(idDe, 11);
			break;
		case com.uit.R.id.imageKetQuaCau13:

			UpdateSharedPreferences(idDe, 12);
			break;
		case com.uit.R.id.imageKetQuaCau14:

			UpdateSharedPreferences(idDe, 13);
			break;
		case com.uit.R.id.imageKetQuaCau15:

			UpdateSharedPreferences(idDe, 14);
			break;
		case com.uit.R.id.imageKetQuaCau16:

			UpdateSharedPreferences(idDe, 15);
			break;
		case com.uit.R.id.imageKetQuaCau17:

			UpdateSharedPreferences(idDe, 16);
			break;
		case com.uit.R.id.imageKetQuaCau18:

			UpdateSharedPreferences(idDe, 17);
			break;
		case com.uit.R.id.imageKetQuaCau19:

			UpdateSharedPreferences(idDe, 18);
			break;
		case com.uit.R.id.imageKetQuaCau20:

			UpdateSharedPreferences(idDe, 19);
			break;
		case com.uit.R.id.imageKetQuaCau21:

			UpdateSharedPreferences(idDe, 20);
			break;
		case com.uit.R.id.imageKetQuaCau22:

			UpdateSharedPreferences(idDe, 21);
			break;
		case com.uit.R.id.imageKetQuaCau23:

			UpdateSharedPreferences(idDe, 22);
			break;
		case com.uit.R.id.imageKetQuaCau24:

			UpdateSharedPreferences(idDe, 23);
			break;
		case com.uit.R.id.imageKetQuaCau25:

			UpdateSharedPreferences(idDe, 24);
			break;
		case com.uit.R.id.imageKetQuaCau26:

			UpdateSharedPreferences(idDe, 25);
			break;
		case com.uit.R.id.imageKetQuaCau27:

			UpdateSharedPreferences(idDe, 26);
			break;
		case com.uit.R.id.imageKetQuaCau28:

			UpdateSharedPreferences(idDe, 27);
			break;
		case com.uit.R.id.imageKetQuaCau29:

			UpdateSharedPreferences(idDe, 28);
			break;
		case com.uit.R.id.imageKetQuaCau30:

			UpdateSharedPreferences(idDe, 29);
			break;
		}

		Intent i = new Intent(getApplicationContext(), XemLaiBaiThi.class);
		startActivity(i);
	}
}
