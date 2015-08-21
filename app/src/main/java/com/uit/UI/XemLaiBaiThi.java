package com.uit.UI;

import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uit.R;
import com.uit.Providers.CauHoiDB;
import com.uit.Providers.DeThiRandom;
import com.uit.Providers.HashmapDB;
import com.uit.objects.CustomAdapterForListView;
import com.uit.objects.UserAction;

public class XemLaiBaiThi extends Activity implements OnClickListener{

	
	private String username; //biến lưu tên user  
	private int idUser; //biến lưu id của user  
	private int idBode = 0; //biến lưu bộ đề thi   của user  
	private TextView txtUserName, txtDiemThi; 
	private ImageButton btnNext, btnPrev; //các imageButton tiếp theo, và quay lại
	HashMap<String, Integer> hashMapHinhAnh = new HashMap<String, Integer>();
	

	String noidungcauhoi ="";
	private int cauhientai=0; //câu hỏi hiện tại  , từ 0->29
	
		
	private String[] datraloi = new String[30]; //Mảng lưu các đáp án được chọn, mặc định là không chọn gì.
	private int[] bode = new int[30]; //mảng lưu 30 id của bộ đề thi hiện tại.	
	private String[] dapan = new String[30]; //mảng lưu 30 đáp án tương ứng của 30 câu.
	private int[] traloidung = new int[30];
	private String[] hinhanh = new String[30]; //mảng lưu link hình ảnh của các câu hỏi (nếu có)
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.uit.R.layout.xem_lai_bai_thi);
	
		//khoi tao cac mang
		for(int i=0; i<30; i++){
			datraloi[i] = null;
			traloidung[i] = 0;
			hinhanh[i] = null;
		}
		
		SharedPreferences cauHT = getSharedPreferences(KetQuaMotAccount.IDCAUHOI, 0);	 
		cauhientai = cauHT.getInt(KetQuaMotAccount.IDCAUHOI, 0);
		
		//Lấy về Hashmap cho các hình của các câu hỏi có hình
		hashMapHinhAnh = new HashmapDB().getMapImageCauHoi();
		
		/**
		 *Lấy tên user và idUser  , idDeThi
		 */	
		SharedPreferences account = getSharedPreferences(UserAction.ACCOUNT, 0);
		username = account.getString(UserAction.NAME, null);
		
		SharedPreferences idU = getSharedPreferences(UserAction.USERID, 0);
		idUser = idU.getInt(UserAction.USERID, 0);
		
		SharedPreferences idDT = getSharedPreferences(KetQuaMotAccount.IDDETHI, 0);		
		idBode = idDT.getInt(KetQuaMotAccount.IDDETHI, 0);
		
		Log.w("id bo de", ((Integer)idBode).toString());
		
		txtUserName = (TextView)findViewById(com.uit.R.id.xlbt_txtTenNguoiThi);
		txtUserName.setText("Phần thi của: " + username);
		
		getIdQuestions(this, idUser, idBode);
       
		
		createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
        
		//
		//
		txtDiemThi = (TextView)findViewById(com.uit.R.id.xlbt_txtDiem);
		int diemthi=0;
		for(int i=0; i<30; i++){
			diemthi += traloidung[i];
		}
		txtDiemThi.setText("Điểm thi: " + ((Integer)diemthi).toString());
		
		//
		//Các button tiếp theo và lùi lại
		btnNext = (ImageButton)findViewById(com.uit.R.id.xlbt_btnThiNext);
		btnPrev = (ImageButton)findViewById(com.uit.R.id.xlbt_btnThiPrev);
		//
		//Xử lý các sự kiện cho các button này		
		btnNext.setOnClickListener(this);
		
		btnPrev.setOnClickListener(this);
		
		//
		//Xử lý sự kiện cho các Image trong HrizontalScrollbar khi click chọn
		//sẽ hiển thị câu hỏi đúng với số thự tự trên image đó
		//
		//Khai báo 30 image
		ImageView imageCau1 = (ImageView)findViewById(R.id.xlbt_imageCau1);
		ImageView imageCau2 = (ImageView)findViewById(R.id.xlbt_imageCau2);
		ImageView imageCau3 = (ImageView)findViewById(R.id.xlbt_imageCau3);
		ImageView imageCau4 = (ImageView)findViewById(R.id.xlbt_imageCau4);
		ImageView imageCau5 = (ImageView)findViewById(R.id.xlbt_imageCau5);
		ImageView imageCau6 = (ImageView)findViewById(R.id.xlbt_imageCau6);
		ImageView imageCau7 = (ImageView)findViewById(R.id.xlbt_imageCau7);
		ImageView imageCau8 = (ImageView)findViewById(R.id.xlbt_imageCau8);
		ImageView imageCau9 = (ImageView)findViewById(R.id.xlbt_imageCau9);
		ImageView imageCau10 = (ImageView)findViewById(R.id.xlbt_imageCau10);
		
		ImageView imageCau11 = (ImageView)findViewById(R.id.xlbt_imageCau11);
		ImageView imageCau12 = (ImageView)findViewById(R.id.xlbt_imageCau12);
		ImageView imageCau13 = (ImageView)findViewById(R.id.xlbt_imageCau13);
		ImageView imageCau14 = (ImageView)findViewById(R.id.xlbt_imageCau14);
		ImageView imageCau15 = (ImageView)findViewById(R.id.xlbt_imageCau15);
		ImageView imageCau16 = (ImageView)findViewById(R.id.xlbt_imageCau16);
		ImageView imageCau17 = (ImageView)findViewById(R.id.xlbt_imageCau17);
		ImageView imageCau18 = (ImageView)findViewById(R.id.xlbt_imageCau18);
		ImageView imageCau19 = (ImageView)findViewById(R.id.xlbt_imageCau19);
		ImageView imageCau20 = (ImageView)findViewById(R.id.xlbt_imageCau20);
		
		ImageView imageCau21 = (ImageView)findViewById(R.id.xlbt_imageCau21);
		ImageView imageCau22 = (ImageView)findViewById(R.id.xlbt_imageCau22);
		ImageView imageCau23 = (ImageView)findViewById(R.id.xlbt_imageCau23);
		ImageView imageCau24 = (ImageView)findViewById(R.id.xlbt_imageCau24);
		ImageView imageCau25 = (ImageView)findViewById(R.id.xlbt_imageCau25);
		ImageView imageCau26 = (ImageView)findViewById(R.id.xlbt_imageCau26);
		ImageView imageCau27 = (ImageView)findViewById(R.id.xlbt_imageCau27);
		ImageView imageCau28 = (ImageView)findViewById(R.id.xlbt_imageCau28);
		ImageView imageCau29 = (ImageView)findViewById(R.id.xlbt_imageCau29);
		ImageView imageCau30 = (ImageView)findViewById(R.id.xlbt_imageCau30);
		
		//
		//các sự kiện cho các image này
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
		
		for(int i=0; i<30; i++){
			if(traloidung[i] == 1)
				UpdateIconTrueAtQuetion(i);//update lai icon cho cac cau (tra loi dung)
			else if(datraloi[i] != null)
				UpdateIconFalseAtQuetion(i);//update lai icon cho cac cau (tra loi sai)
		}
		
		
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		//Click button Next
		
		case com.uit.R.id.xlbt_btnThiNext:
			if(cauhientai == 29){
					
			}
			else
			{
				cauhientai++;
				Log.d("cau hien tai", ((Integer)cauhientai).toString());					
				createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
				
				if(cauhientai == 29){
					//dang o cau cuoi cung, chuyen icon						
					btnNext.setBackgroundResource(com.uit.R.drawable.thi_finish);
				}
				else
				{
					//khong phai cau cuoi cung
					btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
				}
			}
			break;
			//
			//Click button Previous
		case com.uit.R.id.xlbt_btnThiPrev:
			if(cauhientai == 0){
				Toast.makeText(getBaseContext(), "Đang ở câu đầu tiên", Toast.LENGTH_SHORT).show();
			}
			else
			{
				cauhientai--;
				Log.d("cau hien tai", ((Integer)cauhientai).toString());
				createLayoutViewQuestion(getApplication(), cauhientai);
//				AddIconSelectedIfHas(lvCacDapAn, getBaseContext(), cauhientai);
				if(cauhientai<=28){
					//khong phai cau cuoi cung
					btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
				}
			}
			break;
		case com.uit.R.id.xlbt_imageCau1:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 0;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau2:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 1;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau3:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 2;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau4:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 3;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau5:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 4;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau6:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 5;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau7:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			
			cauhientai = 6;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau8:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 7;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau9:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 8;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau10:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 9;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau11:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 10;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau12:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 11;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau13:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 12;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau14:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 13;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau15:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 14;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau16:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 15;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau17:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 16;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau18:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 17;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau19:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 18;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau20:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 19;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau21:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 20;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau22:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 21;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau23:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 22;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau24:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 23;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau25:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 24;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau26:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 25;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau27:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 26;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau28:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 27;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau29:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 28;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_next);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		case com.uit.R.id.xlbt_imageCau30:
			//Gọi hàm tạo nội dung câu hỏi cho câu 1
			cauhientai = 29;
			btnNext.setBackgroundResource(com.uit.R.drawable.thi_finish);
			createLayoutViewQuestion(XemLaiBaiThi.this, cauhientai);
			break;
		}
	}
	
	
	
	/**
	 * hàm truy vấn lấy về 30 câu trắc nghiệm theo idDeThi của user có idlUser là đối số truyền vào
	 * @param _context
	 * @param idUser
	 */
	public void getIdQuestions(Context _context, int idUser, int idDe){		
		int i=0;
		DeThiRandom dethirandom = new DeThiRandom(_context);
		dethirandom.open();
		Cursor c = dethirandom.getIdQuestionsByIdUserIdDeThi(idUser, idDe);
		if(c.moveToFirst()){
			do{
				bode[i] = c.getInt(0);
				dapan[i] = c.getString(1);
				if(c.getString(2) != null)
				{
					datraloi[i] = c.getString(2);
				}
								
				traloidung[i] = c.getInt(3);
				
				if(c.getString(4) != null){
					hinhanh[i] = c.getString(4);
				}
				i++;
			}while(c.moveToNext());
		}
		
		dethirandom.close();
		
	}
	
	/**
	 * Hàm trả về các thông số câu hỏi dựa vào idQuestion 
	 * @param _context
	 * @param idQuestion
	 * @return
	 */
	public String[] getCacPATLTheoIdCauHoi(Context _context, int _thutucauhoi){
		Log.d("ID cau hoi", ((Integer)bode[_thutucauhoi]).toString());
		
		String phuongana = "", phuonganb = "", phuonganc = "", phuongand ="";
		CauHoiDB ch = new CauHoiDB(_context);
		ch.open();
		Cursor cursorCauhoi = ch.getQuestionById(bode[_thutucauhoi]);
		if(cursorCauhoi.moveToFirst()){
			//lay ve noi dung cau hoi, va noi dung cac phuog an tra loi
			this.noidungcauhoi = cursorCauhoi.getString(0);
			phuongana = cursorCauhoi.getString(1);
			phuonganb = cursorCauhoi.getString(2);
			phuonganc = cursorCauhoi.getString(3);
			phuongand = cursorCauhoi.getString(4);
			
		}
		ch.close();		
				
		String[] list;
		int index = 4;
		if(phuonganc.equals("") || phuonganc == null){
			index = 2; 
		}
		else if(phuongand.equals("") || phuongand == null){
			index = 3;
		}
		
		list = new String[index];
		//copy du lieu
		list[0] = phuongana;
		list[1] = phuonganb;
		if(index == 3){
			list[2] = phuonganc;			
		}
		if(index == 4){
			list[2] = phuonganc;
			list[3] = phuongand;
		}
		
		return list;
		
	}
	
	/**
	 * Tạo layout chứa các phương án trả lời của câu hỏi có thứ tự từ 0->29 trong mảng bộ đề bode[30]
	 * Hàm này quan trọng nhất class Thi thử
	 * Mảng bộ đề chứa 30 id câu hỏi được random cho đề thi
	 * Nếu câu nào người dùng đã trả lời thì in phương án trả lời ra
	 * @param _context
	 * @param thutucauhoi
	 */
	public void createLayoutViewQuestion(final Context _context, final int thutucauhoi){
		Log.d("Cau thu: ", ((Integer)(thutucauhoi + 1)).toString());
		
		TextView noidungcauhoi; //Textview chứa nội dung câu hỏi
		
		
		String[] list = this.getCacPATLTheoIdCauHoi(_context, thutucauhoi);
		
		noidungcauhoi = (TextView)findViewById(com.uit.R.id.xlbt_txtNoidungCauhoi);
		noidungcauhoi.setText("Câu " + (thutucauhoi + 1) + ": " + this.noidungcauhoi);
		
		//
		//Chèn hình nếu câu hỏi này có hình
		
		if(hinhanh[thutucauhoi] != null && !(hinhanh[thutucauhoi]).equalsIgnoreCase("")){
			Log.w("Hinh anh tai cau hoi hien tai", hinhanh[thutucauhoi]);
			Log.e("id cua hinh", ((Integer)hashMapHinhAnh.get(hinhanh[thutucauhoi])).toString());
			ImageView hinhchocauhoi = (ImageView)findViewById(com.uit.R.id.xlbt_imageHinhChoCauHoi);
			
			hinhchocauhoi.setImageResource(((Integer)hashMapHinhAnh.get(hinhanh[thutucauhoi])).intValue());
		
		}
		else{
			ImageView hinhchocauhoi = (ImageView)findViewById(com.uit.R.id.xlbt_imageHinhChoCauHoi);
			hinhchocauhoi.setImageDrawable(null);
			
		
		}
		//
		//trả về  adapter các đáp án của câu hỏi này
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, com.uit.R.layout.list_dap_an_cau_hoi, com.uit.R.id.xlbt_txtlistDapAn, list);
		int posChecked = -1;

		if(datraloi[thutucauhoi] != null && datraloi[thutucauhoi].equalsIgnoreCase("A"))
		{
			posChecked = 0;
			
		}
		else if(datraloi[thutucauhoi] != null && datraloi[thutucauhoi].equalsIgnoreCase("B"))
		{
			posChecked = 1;
		}
		else if(datraloi[thutucauhoi] != null && datraloi[thutucauhoi].equalsIgnoreCase("C"))
		{
			posChecked = 2;
		}
		else if(datraloi[thutucauhoi] != null && datraloi[thutucauhoi].equalsIgnoreCase("D"))
		{
			posChecked = 3;
		}
		
		int posRight = -1;
		if(dapan[thutucauhoi] != null && dapan[thutucauhoi].equalsIgnoreCase("A"))
		{
			posRight = 0;
			
		}
		else if(dapan[thutucauhoi] != null && dapan[thutucauhoi].equalsIgnoreCase("B"))
		{
			posRight = 1;
		}
		else if(dapan[thutucauhoi] != null && dapan[thutucauhoi].equalsIgnoreCase("C"))
		{
			posRight = 2;
		}
		else if(dapan[thutucauhoi] != null && dapan[thutucauhoi].equalsIgnoreCase("D"))
		{
			posRight = 3;
		}
		
		//tại vị trí đáp án đã chọn trước đó (posChecked). ta thiết lập icon là dấu tick
		CustomAdapterForListView adapter = new CustomAdapterForListView(this, com.uit.R.id.xlbt_listPhuongAn, list, posChecked, posRight);
		final ListView lvCacDapAn = (ListView) (findViewById(com.uit.R.id.xlbt_listPhuongAn)); //Listview chứa các đáp án a, b, c, d
		lvCacDapAn.setAdapter(adapter);
		
	

		
		
		
	}
	
	/**
	 * Hàm update icon của câu hỏi đã được trả lời dung, thêm vào dấu tick để đánh dấu
	 * @param _thutucauhoi
	 */
	
	public void UpdateIconTrueAtQuetion(int _thutucauhoi){
		LinearLayout layout = (LinearLayout)findViewById(com.uit.R.id.xlbt_layoutCacCauHoi);
		ImageView imv = (ImageView)(layout.getChildAt(_thutucauhoi));
		switch(_thutucauhoi)
		{
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
	
	public void UpdateIconFalseAtQuetion(int _thutucauhoi){
		LinearLayout layout = (LinearLayout)findViewById(com.uit.R.id.xlbt_layoutCacCauHoi);
		ImageView imv = (ImageView)(layout.getChildAt(_thutucauhoi));
		switch(_thutucauhoi)
		{
		case 0:
			imv.setBackgroundResource(com.uit.R.drawable.nf_one);
			break;
		case 1:
			imv.setBackgroundResource(com.uit.R.drawable.nf_two);
			break;
		case 2:
			imv.setBackgroundResource(com.uit.R.drawable.nf_three);
			break;
		case 3:
			imv.setBackgroundResource(com.uit.R.drawable.nf_four);
			break;
		case 4:
			imv.setBackgroundResource(com.uit.R.drawable.nf_five);
			break;
		case 5:
			imv.setBackgroundResource(com.uit.R.drawable.nf_six);
			break;
		case 6:
			imv.setBackgroundResource(com.uit.R.drawable.nf_seven);
			break;
		case 7:
			imv.setBackgroundResource(com.uit.R.drawable.nf_eight);
			break;
		case 8:
			imv.setBackgroundResource(com.uit.R.drawable.nf_nine);
			break;
		case 9:
			imv.setBackgroundResource(com.uit.R.drawable.nf_ten);
			break;
		case 10:
			imv.setBackgroundResource(com.uit.R.drawable.nf_elevent);
			break;
		case 11:
			imv.setBackgroundResource(com.uit.R.drawable.nf_twelve);
			break;
		case 12:
			imv.setBackgroundResource(com.uit.R.drawable.nf_thirteen);
			break;
		case 13:
			imv.setBackgroundResource(com.uit.R.drawable.nf_fourteen);
			break;
		case 14:
			imv.setBackgroundResource(com.uit.R.drawable.nf_fifteen);
			break;
		case 15:
			imv.setBackgroundResource(com.uit.R.drawable.nf_sixteen);
			break;
		case 16:
			imv.setBackgroundResource(com.uit.R.drawable.nf_seventeen);
			break;
		case 17:
			imv.setBackgroundResource(com.uit.R.drawable.nf_eighteen);
			break;
		case 18:
			imv.setBackgroundResource(com.uit.R.drawable.nf_nineteen);
			break;
		case 19:
			imv.setBackgroundResource(com.uit.R.drawable.nf_twenty);
			break;
		case 20:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_one);
			break;
		case 21:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_two);
			break;
		case 22:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_three);
			break;
		case 23:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_four);
			break;
		case 24:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_five);
			break;
		case 25:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_six);
			break;
		case 26:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_seven);
			break;
		case 27:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_eight);
			break;
		case 28:
			imv.setBackgroundResource(com.uit.R.drawable.nf_t_nine);
			break;
		case 29:
			imv.setBackgroundResource(com.uit.R.drawable.nf_thirty);
			break;
		
			
		}
		
	}
	
	
}
