package com.uit.UI;

import com.uit.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class HocTapActivity extends Activity {
	
	ImageButton btnBB, btnHocLuat, btnDeThi, btnHuongDan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hoctap);
		
		btnBB = (ImageButton) findViewById(R.id.t_btnBienBao);
		btnHocLuat = (ImageButton) findViewById(R.id.t_btnLuat);
		btnDeThi = (ImageButton) findViewById(R.id.t_btnCauHoi);
		btnHuongDan = (ImageButton) findViewById(R.id.t_btnHuongDan);
		
		btnBB.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(HocTapActivity.this, BienBaoGridview.class);
				startActivity(i);
			}
		});
		
		btnHocLuat.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(HocTapActivity.this, HocLuatActivity_Chuong.class);
				startActivity(i);
			}
		});
		
		btnDeThi.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(HocTapActivity.this, HocDeThiActivity.class);
				startActivity(i);
			}
		});
		
		btnHuongDan.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(HocTapActivity.this, HuongDanActivity.class);
				startActivity(i);
			}
		});
	}
}
