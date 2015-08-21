package com.uit.UI;

import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uit.R;
import com.uit.Functions.DongHo;
import com.uit.Providers.CauHoiDB;
import com.uit.Providers.DeThiRandom;
import com.uit.Providers.HashmapDB;
import com.uit.Providers.XepLoai;
import com.uit.objects.CustomAdapterForListView;
import com.uit.objects.DeThi;
import com.uit.objects.UserAction;

public class ThiThuActivity extends Activity implements OnClickListener {

	/**
	 * Khai báo biến
	 */

	private String username; // biến lưu tên user đang thi
	private int idUser; // biến lưu id của user đang thi
	private int idBode = 0; // biến lưu bộ đề thi đang thi của user
	private TextView txtUserName, txtDongHo; // Textview tên user và đồng hồ đếm
												// ngược
	private ImageButton btnNext, btnPrev; // các imageButton tiếp theo, và quay
											// lại
	HashMap<String, Integer> hashMapHinhAnh = new HashMap<String, Integer>();

	String noidungcauhoi = "";
	private int cauhientai = 0; // câu hỏi hiện tại đang thi, từ 0->29
	private DongHo dongho;
	private long ngaygiothi; // biến lưu ngày giờ bắt đầu thi
	private int countClickFinish = 0; // biến đếm số lần click vào imageButton
										// finish để nộp bài. - có tác dụng hạn
										// chế số lần click vào nút này, tránh
										// sai sót dữ liệu khi click liên tục
	private String[] datraloi = new String[30]; // Mảng lưu các đáp án được
												// chọn, mặc định là không chọn
												// gì.
	private int[] bode = new int[30]; // mảng lưu 30 id của bộ đề thi hiện tại.
	private String[] dapan = new String[30]; // mảng lưu 30 đáp án tương ứng của
												// 30 câu.
	private String[] hinhanh = new String[30]; // mảng lưu link hình ảnh của các
												// câu hỏi (nếu có)

	private Thread theodoithoigianhoanthanh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.uit.R.layout.exam);

		//
		// khởi tạo các mảng
		datraloi = new String[30];
		for (int i = 0; i < 30; i++) {
			datraloi[i] = null;
			hinhanh[i] = null;
		}

		cauhientai = 0;

		// Lấy về Hashmap cho các hình của các câu hỏi có hình
		hashMapHinhAnh = new HashmapDB().getMapImageCauHoi();

		// lay ngay gio hien tai
		Date now = new Date();
		ngaygiothi = now.getTime();

		UserAction u = new UserAction(this);
		u.UpdateTimeDo(ngaygiothi);

		/**
		 * Lấy tên user và idUser
		 */
		SharedPreferences account = getSharedPreferences(UserAction.ACCOUNT,
				0);
		username = account.getString(UserAction.NAME, null);

		SharedPreferences idU = getSharedPreferences(UserAction.USERID, 0);
		idUser = idU.getInt(UserAction.USERID, 0);

		txtUserName = (TextView) findViewById(com.uit.R.id.txtTenNguoiThi);
		txtUserName.setText("Phần thi của: " + username);

		Log.d("User Name", username);
		Log.d("User ID", ((Integer) idUser).toString());

		//
		Log.d("Radom bo de", "start");
		DeThi dethi = new DeThi();
		dethi.TaoBoDe(getApplicationContext(), idUser);

		Log.d("Random bo de", "finish");

		//
		// tra ve mang cau hoi va dap an
		getIdQuestions(ThiThuActivity.this, idUser);

		for (int i : bode) {
			Log.d("id cau hoi", ((Integer) i).toString());
		}

		for (String i : dapan) {
			Log.d("dap an", i);
		}

		for (String i : hinhanh) {
			if (i != null)
				Log.d("Hinh anh tai cau", i);
		}

		createLayoutViewQuestion(ThiThuActivity.this, cauhientai);

		//
		//
		txtDongHo = (TextView) findViewById(com.uit.R.id.txtDongHo);
		dongho = new DongHo(txtDongHo);

		//
		//
		// tra ve chieu dai va rong man hinh dien thoai
		// Display display = ((WindowManager)
		// this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		//
		//
		// int width = display.getWidth();
		// int height = display.getHeight();

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//
		// Tạo một thread theo dõi thời gian hoàn thành bài thi.
		//

		theodoithoigianhoanthanh = new Thread() {
			public void run() {
				Looper.prepare();
				while (dongho.getThoiGianDaQua() < 1199001) {
					// Log.i("Thoi gian da qua",
					// String.valueOf(dongho.getThoiGianDaQua()));
				}
				ketthucbaithi(ThiThuActivity.this, dongho.getThoiGianDaQua());
				showDialog(2);
				Looper.loop();
			}

		};
		theodoithoigianhoanthanh.start();

		//
		//

		//
		// Các button tiếp theo và lùi lại
		btnNext = (ImageButton) findViewById(com.uit.R.id.btnThiNext);
		btnPrev = (ImageButton) findViewById(com.uit.R.id.btnThiPrev);
		//
		// Xử lý các sự kiện cho các button này
		btnNext.setOnClickListener(this);

		btnPrev.setOnClickListener(this);

		//
		// Xử lý sự kiện cho các Image trong HrizontalScrollbar khi click chọn
		// sẽ hiển thị câu hỏi đúng với số thự tự trên image đó
		//
		// Khai báo 30 image
		ImageView imageCau1 = (ImageView) findViewById(R.id.imageCau1);
		ImageView imageCau2 = (ImageView) findViewById(R.id.imageCau2);
		ImageView imageCau3 = (ImageView) findViewById(R.id.imageCau3);
		ImageView imageCau4 = (ImageView) findViewById(R.id.imageCau4);
		ImageView imageCau5 = (ImageView) findViewById(R.id.imageCau5);
		ImageView imageCau6 = (ImageView) findViewById(R.id.imageCau6);
		ImageView imageCau7 = (ImageView) findViewById(R.id.imageCau7);
		ImageView imageCau8 = (ImageView) findViewById(R.id.imageCau8);
		ImageView imageCau9 = (ImageView) findViewById(R.id.imageCau9);
		ImageView imageCau10 = (ImageView) findViewById(R.id.imageCau10);

		ImageView imageCau11 = (ImageView) findViewById(R.id.imageCau11);
		ImageView imageCau12 = (ImageView) findViewById(R.id.imageCau12);
		ImageView imageCau13 = (ImageView) findViewById(R.id.imageCau13);
		ImageView imageCau14 = (ImageView) findViewById(R.id.imageCau14);
		ImageView imageCau15 = (ImageView) findViewById(R.id.imageCau15);
		ImageView imageCau16 = (ImageView) findViewById(R.id.imageCau16);
		ImageView imageCau17 = (ImageView) findViewById(R.id.imageCau17);
		ImageView imageCau18 = (ImageView) findViewById(R.id.imageCau18);
		ImageView imageCau19 = (ImageView) findViewById(R.id.imageCau19);
		ImageView imageCau20 = (ImageView) findViewById(R.id.imageCau20);

		ImageView imageCau21 = (ImageView) findViewById(R.id.imageCau21);
		ImageView imageCau22 = (ImageView) findViewById(R.id.imageCau22);
		ImageView imageCau23 = (ImageView) findViewById(R.id.imageCau23);
		ImageView imageCau24 = (ImageView) findViewById(R.id.imageCau24);
		ImageView imageCau25 = (ImageView) findViewById(R.id.imageCau25);
		ImageView imageCau26 = (ImageView) findViewById(R.id.imageCau26);
		ImageView imageCau27 = (ImageView) findViewById(R.id.imageCau27);
		ImageView imageCau28 = (ImageView) findViewById(R.id.imageCau28);
		ImageView imageCau29 = (ImageView) findViewById(R.id.imageCau29);
		ImageView imageCau30 = (ImageView) findViewById(R.id.imageCau30);

		//
		// các sự kiện cho các image này
		imageCau1.setOnClickListener(this);
		imageCau2.setOnClickListener(this);
		imageCau3.setOnClickListener(this);
		imageCau4.setOnClickListener(this);
		imageCau5.setOnClickListener(this);
		imageCau6.setOnClickListener(this);
		imageCau7.setOnClickListener(this);
		imageCau8.setOnClickListener(this);
		imageCau9.setOnClickListener(this);
		imageCau10.setOnClickListener(this);

		imageCau11.setOnClickListener(this);
		imageCau12.setOnClickListener(this);
		imageCau13.setOnClickListener(this);
		imageCau14.setOnClickListener(this);
		imageCau15.setOnClickListener(this);
		imageCau16.setOnClickListener(this);
		imageCau17.setOnClickListener(this);
		imageCau18.setOnClickListener(this);
		imageCau19.setOnClickListener(this);
		imageCau20.setOnClickListener(this);

		imageCau21.setOnClickListener(this);
		imageCau22.setOnClickListener(this);
		imageCau23.setOnClickListener(this);
		imageCau24.setOnClickListener(this);
		imageCau25.setOnClickListener(this);
		imageCau26.setOnClickListener(this);
		imageCau27.setOnClickListener(this);
		imageCau28.setOnClickListener(this);
		imageCau29.setOnClickListener(this);
		imageCau30.setOnClickListener(this);

		/*
		 * insert vào bảng thống kê các dữ liệu rỗng -- nhằm tránh trường hợp
		 * force giữa chừng
		 */
		//
		// ---update bang thong ke doi vs user nay
		// idUser, ngaygiothi, thoigianhoanthanh, va ketqua la so cau dung
		XepLoai xeploai = new XepLoai(this);
		xeploai.open();
		xeploai.insertRowByUserId(this, idUser, idBode, ngaygiothi, 0, 0);
		xeploai.close();

	}

	//
	// Bắt các sự kiện key event trong khi thi
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_HOME:
			// lấy về thời gian thi

			// Hiện thông báo muốn thoát và xem kết quả
			showDialog(0);
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// Click button Next

		case com.uit.R.id.btnThiNext:
			if (cauhientai == 29) {
				// ve dich, nop bai thi
				Toast.makeText(getBaseContext(), "Kết thúc bài thi.",
						Toast.LENGTH_SHORT).show();
				countClickFinish++;
				// goi ham ketthucbaithi
				// phong truong hop click nhieu lan khi chua xu ly xong
				if (countClickFinish == 1) {
					createProgressBarWait(0);
					ketthucbaithi(ThiThuActivity.this,
							dongho.getThoiGianDaQua());

				}

			} else {
				cauhientai++;
				Log.d("cau hien tai", ((Integer) cauhientai).toString());
				createLayoutViewQuestion(ThiThuActivity.this, cauhientai);

				if (cauhientai == 29) {
					// dang o cau cuoi cung, chuyen icon
					btnNext.setBackgroundResource(com.uit.R.drawable.thi_finish);
				} else {
					// khong phai cau cuoi cung
					btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
				}
			}
			break;
		//
		// Click button Previous
		case com.uit.R.id.btnThiPrev:
			if (cauhientai == 0) {
				Toast.makeText(getBaseContext(), "Đang ở câu đầu tiên",
						Toast.LENGTH_SHORT).show();
			} else {
				cauhientai--;
				Log.d("cau hien tai", ((Integer) cauhientai).toString());
				createLayoutViewQuestion(getApplication(), cauhientai);
				// AddIconSelectedIfHas(lvCacDapAn, getBaseContext(),
				// cauhientai);
				if (cauhientai <= 28) {
					// khong phai cau cuoi cung
					btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
				}
			}
			break;
		case com.uit.R.id.imageCau1:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 0;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau2:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 1;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau3:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 2;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau4:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 3;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau5:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 4;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau6:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 5;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau7:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1

			cauhientai = 6;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau8:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 7;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau9:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 8;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau10:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 9;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau11:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 10;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau12:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 11;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau13:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 12;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau14:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 13;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau15:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 14;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau16:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 15;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau17:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 16;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau18:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 17;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau19:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 18;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau20:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 19;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau21:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 20;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau22:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 21;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau23:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 22;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau24:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 23;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau25:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 24;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau26:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 25;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau27:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 26;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau28:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 27;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau29:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 28;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		case com.uit.R.id.imageCau30:
			// Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 29;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_finish);
			createLayoutViewQuestion(ThiThuActivity.this, cauhientai);
			break;
		}
	}

	// ham huy k chon cua cac item thuoc listview
	public void DestroyFocusItemOnListView(ListView l) {
		int countChild = l.getChildCount();
		int color = Color.parseColor("#424142");
		for (int i = 0; i < countChild; i++) {
			l.getChildAt(i).setBackgroundColor(color);
		}
	}

	/**
	 * hàm truy vấn lấy về 30 câu trắc nghiệm mới nhất của user có idlUser là
	 * đối số truyền vào
	 * 
	 * @param _context
	 * @param idUser
	 */
	public void getIdQuestions(Context _context, int idUser) {
		int i = 0;
		DeThiRandom dethirandom = new DeThiRandom(_context);
		dethirandom.open();
		Cursor c = dethirandom.getIdQuestionsForDethi(idUser);
		if (c.moveToFirst()) {
			do {
				bode[i] = c.getInt(0);
				dapan[i] = c.getString(1);
				if (idBode == 0) {
					// lay ve idbode thi hien tai
					idBode = c.getInt(2);

				}
				if (c.getString(4) != null) {
					hinhanh[i] = c.getString(4);
				}
				i++;
			} while (c.moveToNext());
		}

		dethirandom.close();

	}

	/**
	 * Hàm trả về các thông số câu hỏi dựa vào idQuestion
	 * 
	 * @param _context
	 * @param idQuestion
	 * @return
	 */
	public String[] getCacPATLTheoIdCauHoi(Context _context, int _thutucauhoi) {
		Log.d("ID cau hoi", ((Integer) bode[_thutucauhoi]).toString());

		String phuongana = "", phuonganb = "", phuonganc = "", phuongand = "";
		CauHoiDB ch = new CauHoiDB(_context);
		ch.open();
		Cursor cursorCauhoi = ch.getQuestionById(bode[_thutucauhoi]);
		if (cursorCauhoi.moveToFirst()) {
			// lay ve noi dung cau hoi, va noi dung cac phuog an tra loi
			this.noidungcauhoi = cursorCauhoi.getString(0);
			phuongana = cursorCauhoi.getString(1);
			phuonganb = cursorCauhoi.getString(2);
			phuonganc = cursorCauhoi.getString(3);
			phuongand = cursorCauhoi.getString(4);

		}
		ch.close();

		String[] list;
		int index = 4;
		if (phuonganc.equals("") || phuonganc == null) {
			index = 2;
		} else if (phuongand.equals("") || phuongand == null) {
			index = 3;
		}

		list = new String[index];
		// copy du lieu
		list[0] = phuongana;
		list[1] = phuonganb;
		if (index == 3) {
			list[2] = phuonganc;
		}
		if (index == 4) {
			list[2] = phuonganc;
			list[3] = phuongand;
		}

		return list;

	}

	/**
	 * Tạo layout chứa các phương án trả lời của câu hỏi có thứ tự từ 0->29
	 * trong mảng bộ đề bode[30] Hàm này quan trọng nhất class Thi thử Mảng bộ
	 * đề chứa 30 id câu hỏi được random cho đề thi Nếu câu nào người dùng đã
	 * trả lời thì in phương án trả lời ra
	 * 
	 * @param _context
	 * @param thutucauhoi
	 */
	public void createLayoutViewQuestion(final Context _context,
			final int thutucauhoi) {
		Log.d("Cau thu: ", ((Integer) (thutucauhoi + 1)).toString());

		TextView noidungcauhoi; // Textview chứa nội dung câu hỏi

		String[] list = this.getCacPATLTheoIdCauHoi(_context, thutucauhoi);

		noidungcauhoi = (TextView) findViewById(com.uit.R.id.txtNoidungCauhoi);
		noidungcauhoi.setText("Câu " + (thutucauhoi + 1) + ": "
				+ this.noidungcauhoi);

		//
		// Chèn hình nếu câu hỏi này có hình

		if (hinhanh[thutucauhoi] != null
				&& !(hinhanh[thutucauhoi]).equalsIgnoreCase("")) {
			Log.w("Hinh anh tai cau hoi hien tai", hinhanh[thutucauhoi]);
			Log.e("id cua hinh", ((Integer) hashMapHinhAnh
					.get(hinhanh[thutucauhoi])).toString());
			ImageView hinhchocauhoi = (ImageView) findViewById(com.uit.R.id.imageHinhChoCauHoi);

			hinhchocauhoi.setImageResource(((Integer) hashMapHinhAnh
					.get(hinhanh[thutucauhoi])).intValue());

		} else {
			ImageView hinhchocauhoi = (ImageView) findViewById(com.uit.R.id.imageHinhChoCauHoi);
			hinhchocauhoi.setImageDrawable(null);

		}
		//
		// trả về adapter các đáp án của câu hỏi này
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// com.uit.R.layout.list_dap_an_cau_hoi, com.uit.R.id.txtlistDapAn,
		// list);
		int posChecked = -1;

		if (datraloi[thutucauhoi] != null
				&& datraloi[thutucauhoi].equalsIgnoreCase("A")) {
			posChecked = 0;

		} else if (datraloi[thutucauhoi] != null
				&& datraloi[thutucauhoi].equalsIgnoreCase("B")) {
			posChecked = 1;
		} else if (datraloi[thutucauhoi] != null
				&& datraloi[thutucauhoi].equalsIgnoreCase("C")) {
			posChecked = 2;
		} else if (datraloi[thutucauhoi] != null
				&& datraloi[thutucauhoi].equalsIgnoreCase("D")) {
			posChecked = 3;
		}

		// tại vị trí đáp án đã chọn trước đó (posChecked). ta thiết lập icon là
		// dấu tick
		CustomAdapterForListView adapter = new CustomAdapterForListView(this,
				com.uit.R.id.listPhuongAn, list, posChecked);
		final ListView lvCacDapAn = (ListView) (findViewById(com.uit.R.id.listPhuongAn)); // Listview
																							// chứa
																							// các
																							// đáp
																							// án
																							// a,
																							// b,
																							// c,
																							// d
		lvCacDapAn.setAdapter(adapter);
		//
		// Xét sự kiện click vào 1 trong các đáp án - chọn câu trả lời
		lvCacDapAn.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				//
				// Gọi hàm hủy đáp án đã được chọn trước đó.
				DestroyFocusItemOnListView(lvCacDapAn);
				//
				// Set lại màu nền cho các đáp án
				int color = Color.parseColor("#ff9900");
				//
				// Set màu nền cho đáp án được chọn
				lvCacDapAn.getChildAt(arg2).setBackgroundColor(color);
				//
				// update lại màn hình
				lvCacDapAn.refreshDrawableState();

				//
				// Lấy về đáp án đã chọn để update vào cơ sở dữ liệu
				// thay đổi giá trị cho biến đã trả lời dachon = -1 default
				int dachon = arg2;
				//
				int oldPosition = -1;
				if (datraloi[thutucauhoi] != null
						&& datraloi[thutucauhoi].equalsIgnoreCase("A")) {
					oldPosition = 0;

				} else if (datraloi[thutucauhoi] != null
						&& datraloi[thutucauhoi].equalsIgnoreCase("B")) {
					oldPosition = 1;
				} else if (datraloi[thutucauhoi] != null
						&& datraloi[thutucauhoi].equalsIgnoreCase("C")) {
					oldPosition = 2;
				} else if (datraloi[thutucauhoi] != null
						&& datraloi[thutucauhoi].equalsIgnoreCase("D")) {
					oldPosition = 3;
				}
				//
				// Thay đổi icon cho đáp án được chọn thành dấu tick
				DestroyAllIconAndAddIconSelected(lvCacDapAn, dachon,
						oldPosition);
				//
				// chỉnh lại icon của câu hỏi trong scrollbar
				// add thêm dấu tick, đánh dấu đã trả lời rồi.
				UpdateIconTickAtQuetion(thutucauhoi);
				//
				// lấy về đáp án được chọn, lưu lai trong biến đã trả loiwd
				// datraloi
				if (dachon == 0) {
					datraloi[thutucauhoi] = "A";

				} else if (dachon == 1) {
					datraloi[thutucauhoi] = "B";

				} else if (dachon == 2) {
					datraloi[thutucauhoi] = "C";

				} else if (dachon == 3) {
					datraloi[thutucauhoi] = "D";

				}
				Log.d("Da chon dap an: ", datraloi[thutucauhoi]);

				// update
				updateCauTraLoi(_context, bode[thutucauhoi], idUser, idBode,
						datraloi[thutucauhoi]);
			}

		});

	}

	/**
	 * Hàm update icon của câu hỏi đã được trả lời, thêm vào dấu tick để đánh
	 * dấu
	 * 
	 * @param _thutucauhoi
	 */

	public void UpdateIconTickAtQuetion(int _thutucauhoi) {
		LinearLayout layout = (LinearLayout) findViewById(com.uit.R.id.layoutCacCauHoi);
		ImageView imv = (ImageView) (layout.getChildAt(_thutucauhoi));
		switch (_thutucauhoi) {
		case 0:
			imv.setBackgroundResource(com.uit.R.drawable.nt_one);
			break;
		case 1:
			imv.setBackgroundResource(com.uit.R.drawable.nt_two);
			break;
		case 2:
			imv.setBackgroundResource(com.uit.R.drawable.nt_three);
			break;
		case 3:
			imv.setBackgroundResource(com.uit.R.drawable.nt_four);
			break;
		case 4:
			imv.setBackgroundResource(com.uit.R.drawable.nt_five);
			break;
		case 5:
			imv.setBackgroundResource(com.uit.R.drawable.nt_six);
			break;
		case 6:
			imv.setBackgroundResource(com.uit.R.drawable.nt_seven);
			break;
		case 7:
			imv.setBackgroundResource(com.uit.R.drawable.nt_eight);
			break;
		case 8:
			imv.setBackgroundResource(com.uit.R.drawable.nt_nine);
			break;
		case 9:
			imv.setBackgroundResource(com.uit.R.drawable.nt_ten);
			break;
		case 10:
			imv.setBackgroundResource(com.uit.R.drawable.nt_elevent);
			break;
		case 11:
			imv.setBackgroundResource(com.uit.R.drawable.nt_twelve);
			break;
		case 12:
			imv.setBackgroundResource(com.uit.R.drawable.nt_thirteen);
			break;
		case 13:
			imv.setBackgroundResource(com.uit.R.drawable.nt_fourteen);
			break;
		case 14:
			imv.setBackgroundResource(com.uit.R.drawable.nt_fifteen);
			break;
		case 15:
			imv.setBackgroundResource(com.uit.R.drawable.nt_sixteen);
			break;
		case 16:
			imv.setBackgroundResource(com.uit.R.drawable.nt_seventeen);
			break;
		case 17:
			imv.setBackgroundResource(com.uit.R.drawable.nt_eighteen);
			break;
		case 18:
			imv.setBackgroundResource(com.uit.R.drawable.nt_nineteen);
			break;
		case 19:
			imv.setBackgroundResource(com.uit.R.drawable.nt_twenty);
			break;
		case 20:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_one);
			break;
		case 21:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_two);
			break;
		case 22:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_three);
			break;
		case 23:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_four);
			break;
		case 24:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_five);
			break;
		case 25:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_six);
			break;
		case 26:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_seven);
			break;
		case 27:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_eight);
			break;
		case 28:
			imv.setBackgroundResource(com.uit.R.drawable.nt_t_nine);
			break;
		case 29:
			imv.setBackgroundResource(com.uit.R.drawable.nt_thirty);
			break;

		}

	}

	/**
	 * Update phương án người dùng trả lời vào bảng dethirandom
	 * 
	 * @param _context
	 * @param idCauhoi
	 * @param idUser
	 * @param idBode
	 * @param dapan
	 */
	public void updateCauTraLoi(Context _context, int idCauhoi, int idUser,
			int idBode, String dapan) {
		DeThiRandom dethiRandom = new DeThiRandom(_context);
		dethiRandom.open();
		boolean ketqua = dethiRandom.updateAnswer(idBode, idUser, idCauhoi,
				dapan);
		if (ketqua) {
			Log.d("Update cau tra loi", "Thanh cong");
		} else {
			Log.d("Update cau tra loi", "That bai");
		}
		dethiRandom.close();

	}

	/**
	 * Thiết lập lại icon cho câu trả lời được chọn.
	 * 
	 * @param l
	 * @param position
	 */
	public void DestroyAllIconAndAddIconSelected(ListView l, int newPosition,
			int oldPosition) {
		int countChild = l.getChildCount();
		if (countChild != 0) {

			//
			// Thiết lập lại giá trị cho đáp án oldPostion
			if (oldPosition != -1) {
				// Nếu trước đó đã chọn đáp án cho câu hỏi này
				LinearLayout childList = (LinearLayout) l
						.getChildAt(oldPosition);
				ImageView imageIcon = (ImageView) childList.getChildAt(0);

				if (oldPosition == 0) {
					// set phuong an A
					imageIcon.setBackgroundResource(com.uit.R.drawable.a_icon);
				} else if (oldPosition == 1) {
					// set B
					imageIcon.setBackgroundResource(com.uit.R.drawable.b_icon);
				} else if (oldPosition == 2) {
					// set C
					imageIcon.setBackgroundResource(com.uit.R.drawable.c_icon);
				} else if (oldPosition == 3) {
					// set D
					imageIcon.setBackgroundResource(com.uit.R.drawable.d_icon);
				}

			}
			//
			// Thiết lập lại icon cho đáp án mới
			// Hoặc
			// Nếu chưa chọn đáp án cho câu hỏi này
			// thì ta chỉ cần chỉnh lại icon cho đáp án tại vị trí newPosition

			LinearLayout childChecked = (LinearLayout) l
					.getChildAt(newPosition);
			ImageView imageChecked = (ImageView) childChecked.getChildAt(0);
			imageChecked.setBackgroundResource(com.uit.R.drawable.thi_dachon);
		}
	}

	/**
	 * hàm ketthucbaithi hàm này được gọi khi click vào imangebutton finish báo
	 * kết thúc bài thi
	 * 
	 * @param _context
	 * @param thoigianhoanthanh
	 */

	public void ketthucbaithi(Context _context, int thoigianhoanthanh) {

		// update cac cau tra loi dung
		// select trong csdl ra mang cac cau tra loi cua user co idUser va bo de
		// nay

		int index = 0;
		DeThiRandom d = new DeThiRandom(_context);
		d.open();
		Cursor c = d.getDaTraLoiByIdUserLastDeThi(idUser);
		if (c != null) {
			if (c.moveToFirst()) {
				do {
					datraloi[index++] = c.getString(0);
				} while (c.moveToNext());
			}

		}
		d.close();

		// for (String s : datraloi) {
		// if (s!= null)
		// Log.d("Mang tra loi cua user", s);
		// }

		//

		int countTraloiDung = 0;
		// thuc hien so sanh
		DeThiRandom d1 = new DeThiRandom(_context);
		d1.open();
		CauHoiDB ch = new CauHoiDB(_context);
		ch.open();
		for (int i = 0; i < 30; i++) {
			int traloidung = 0;
			if (datraloi[i] != null && dapan[i].equalsIgnoreCase(datraloi[i])) {
				traloidung = 1;
				countTraloiDung++;
			}
			//
			// update phuong an chon cua user vao bang ket qua
			Log.e("Cau ", ((Integer) i).toString());
			Log.e("tra loi dung?", ((Integer) traloidung).toString());

			if (traloidung == 1) {
				d1.updateResult(idBode, idUser, bode[i], 1);
			}

			// va cau hoi hay sai vao trong bang cau hoi hay sai
			else {
				ch.updateWrongAnswerById(bode[i]);
			}

		}
		d1.close();
		ch.close();
		//
		// ---update bang thong ke doi vs user nay
		// idUser, ngaygiothi, thoigianhoanthanh, va ketqua la so cau dung
		Log.w("So diem:", ((Integer) countTraloiDung).toString());
		XepLoai xeploai = new XepLoai(_context);
		xeploai.open();
		xeploai.UpdateRowByUserId(idUser, ngaygiothi, thoigianhoanthanh,
				countTraloiDung);
		xeploai.close();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case 0:
			// tạo dialog khi người dùng muốn thoát khi đang thi - nút nhấn back
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setMessage("Bạn muốn thoát và kết thúc bài thi?");

			builder1.setPositiveButton("Kết thúc",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							ThiThuActivity.this.dongho.pause();
							createProgressBarWait(0);
							ketthucbaithi(ThiThuActivity.this,
									dongho.getThoiGianDaQua());

						}

					});
			builder1.setNegativeButton("Làm tiếp",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}
					});
			AlertDialog dialog1 = builder1.create();
			dialog1.show();
			break;
		case 1:
			// tạo dialog khi người dùng click nút finish để kết thúc bài thi
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setCancelable(false);
			builder2.setMessage("Bạn có muốn xem kết quả ?");
			builder2.setTitle("Bạn đã hoàn thành bài thi.");
			builder2.setPositiveButton("Đồng ý",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
							Intent i = new Intent(getApplicationContext(),
									KetQuaMotAccount.class);
							startActivity(i);
						}

					});
			builder2.setNegativeButton("Bỏ qua",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();

						}
					});
			AlertDialog dialog2 = builder2.create();
			dialog2.show();
			break;
		case 2:
			// tạo dialog khi người dùng đã hết thời gian làm bài thi
			AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
			builder3.setCancelable(false);
			builder3.setMessage("Bạn có muốn xem kết quả ?");
			builder3.setTitle("Bạn đã hết thời gian làm bài thi.");
			builder3.setPositiveButton("Đồng ý",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
							Intent i = new Intent(getApplicationContext(),
									KetQuaMotAccount.class);
							startActivity(i);
						}

					});
			builder3.setNegativeButton("Bỏ qua",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();

						}
					});
			AlertDialog dialog3 = builder3.create();
			dialog3.show();
			break;
		}
		return null;
	}

	public void createProgressBarWait(int i) {
		switch (i) {
		case 0:
			// cho luu bai thi
			final ProgressDialog progDialog2 = ProgressDialog.show(
					ThiThuActivity.this, "Đang lưu bài thi...",
					"Vui lòng chờ!", true);
			dongho.pause();
			new Thread() {
				public void run() {
					Looper.prepare();
					try {
						// sleep the thread, whatever time you want.
						sleep(5000);
					} catch (Exception e) {
					}
					progDialog2.dismiss();
					showDialog(1);
					Looper.loop();
				}
			}.start();

			break;

		}

	}

}
