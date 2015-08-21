package com.uit.Functions;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class CountDown extends Activity{
	TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	tv = new TextView(this);
	this.setContentView(tv);
	
	MyCount counter = new MyCount(5000,1000);
	counter.start();
	}
	
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			tv.setText("done!");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			tv.setText("Left: " + millisUntilFinished/1000);
		}
		
	}


	
}

