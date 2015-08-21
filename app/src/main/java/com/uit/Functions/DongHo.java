package com.uit.Functions;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

/**
 * Class tạo đồng hồ đếm ngược, và trả về các phương thức pause, resume,stop
 * và các phương thức lấy thời gian khi kết thúc, pause
 * @author ThanhVinh
 *
 */
public class DongHo {
	TextView txtDongHoDemNguoc;
	long timecount = 1200000;
	MyCount counter;
	
	
	public DongHo(TextView tv){
		this.txtDongHoDemNguoc = tv;
	
		counter = new MyCount(timecount,1000);
		counter.start();
	
	}
	
	//ham tra ve thoi gian hien tai
	public int getThoiGianDaQua(){
		return (int)(1200000 - timecount);
	}
	
	//ham pause va lay ve gia tri cua 
	public void pause(){
		Log.d("timecount", ((Long)timecount).toString());
		counter.cancel();
		
	}
	
	//ham resume
	public void resume(){
		Log.d("timecount", ((Long)timecount).toString());
		counter = new MyCount(timecount, 1000);
		counter.start();
	}
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			txtDongHoDemNguoc.setText("00':00\"");
			
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			timecount = (millisUntilFinished - 1000);
			long second = (millisUntilFinished/1000) % 60;
			long min = (millisUntilFinished/1000)/60;
			
			String s = ((Long)second).toString();
			String m = ((Long)min).toString();
			
			if(min < 10){
				m = "0" + m;
			}
			if(second < 10){
				s = "0" + s;
			}
			
			txtDongHoDemNguoc.setText(m + "':" + s + "\"");
			
		}
		
	}
	
}
