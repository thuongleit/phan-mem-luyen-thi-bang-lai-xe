package com.uit.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uit.R;
import com.uit.Providers.UserDB;
import com.uit.objects.UserAction;

public class BaseActivity extends Activity {

	ImageButton btnHocTap, btnTaiKhoan, btnThongKe, btnThiThu;
	TextView lblAbout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		lblAbout = (TextView) findViewById(R.id.m_lblAbout);
		btnHocTap = (ImageButton) findViewById(com.uit.R.id.m_btnTrain);
		btnTaiKhoan = (ImageButton) findViewById(com.uit.R.id.m_btnAccount);
		btnThongKe = (ImageButton) findViewById(com.uit.R.id.m_btnStatistic);
		btnThiThu = (ImageButton) findViewById(com.uit.R.id.m_btnTest);

		btnTaiKhoan.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(BaseActivity.this, AccountActivity.class);
				// i.putExtra(UserActivity.ACCOUNT,
				// txtUsername.getText().toString());
				startActivity(i);
				finish();
			}
		});

		btnHocTap.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(BaseActivity.this, HocTapActivity.class);
				startActivity(i);
			}
		});

		btnThongKe.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//
				Intent i = new Intent(BaseActivity.this, ThongKeActivity.class);
				startActivity(i);
			}
		});

		btnThiThu.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int numUser = new UserDB(getApplicationContext()).getListofUserName().length;
				if(numUser == 0){
					createLoginDialog();
				}
				else{
					CreateEnterThiThuDialog();
				}
			}
		});
		
		lblAbout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BaseActivity.this, About.class);
				startActivity(i);
			}
		});
	}

	public void createLoginDialog() {
		final EditText input = new EditText(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(input);
		builder.setCancelable(false);
		builder.setTitle("Input your Name...");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String username = input.getText().toString().trim();
				UserAction user = new UserAction(getBaseContext());
				int result = user.AddUser(username);
				if (result == 3) {
					user.storeInformation(username);
					dialog.dismiss();
					Toast.makeText(getBaseContext(), "Thêm tài khoản thành công!", Toast.LENGTH_SHORT).show();
					
					final ProgressDialog progDialog = ProgressDialog.show(
							BaseActivity.this, "Đang tạo đề thi.",
							"Vui lòng chờ!", true);
					new Thread() {
						public void run() {
							Looper.prepare();
							try {
								// sleep the thread, whatever time you want.
								sleep(3000);
							} catch (Exception e) {
							}
							progDialog.dismiss();
							Looper.loop();
						}
					}.start();
					
					Intent intent = new Intent(BaseActivity.this,ThiThuActivity.class);
					startActivity(intent);
					
				}else if(result == 1){
					Toast.makeText(getBaseContext(), "Tên tài khoản không thể để trống!", Toast.LENGTH_SHORT).show();
				} else if(result == 2){
					Toast.makeText(getBaseContext(), "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
				} else if(result == 4){
					Toast.makeText(getBaseContext(), "Thêm tài khoản thất bại!", Toast.LENGTH_SHORT).show();
				}	
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * Hàm đăng nhập trước khi vào thi thử
	 */
	public void CreateEnterThiThuDialog() {
		final UserAction user = new UserAction(this);
		// String[] usernames is a list of username query from database
		final String[] listUser;
		listUser = user.getListofUserName(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setSingleChoiceItems(listUser, 0, null);
		builder.setTitle("Choose your Name...");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				int itemChecked = ((AlertDialog) dialog).getListView()
						.getCheckedItemPosition();
				for (int i = 0; i < listUser.length; i++) {
					if (itemChecked == i) {
						// txtUsername.setText(listUser[i]);
						user.storeInformation(listUser[i]);
						dialog.dismiss();

						final ProgressDialog progDialog = ProgressDialog.show(
								BaseActivity.this, "Đang tạo đề thi.",
								"Vui lòng chờ!", true);
						new Thread() {
							public void run() {
								Looper.prepare();
								try {
									// sleep the thread, whatever time you want.
									sleep(3000);
								} catch (Exception e) {
								}
								progDialog.dismiss();
								Looper.loop();
							}
						}.start();

						Intent intent = new Intent(BaseActivity.this,
								ThiThuActivity.class);
						startActivity(intent);
					}
				}
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
