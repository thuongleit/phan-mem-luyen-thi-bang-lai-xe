package com.uit.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.uit.R;
import com.uit.Providers.DeThiRandom;
import com.uit.Providers.XepLoai;
import com.uit.objects.UserAction;

public class AccountActivity extends Activity {

	ImageButton btnAdd, btnDel, btnEdit, btnThongTin;
	
	public AccountActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_account);
		
		btnAdd = (ImageButton) findViewById(R.id.a_btnAdd);
		btnDel = (ImageButton) findViewById(R.id.a_btnDel);
		btnThongTin = (ImageButton) findViewById(R.id.a_btnInfo);
		btnEdit = (ImageButton) findViewById(R.id.a_btnEdit);
		
		btnAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				createAddUserDialog();
			}
		});

		btnThongTin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(AccountActivity.this, About.class);
				startActivity(i);
			}
		});

		btnDel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				CreateChooseDelDialog();
			}
		});

		btnEdit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				createEditUserDialog();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i = new Intent(AccountActivity.this, BaseActivity.class);
			startActivity(i);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void createAddUserDialog() {
		final EditText input = new EditText(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(input);
		
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
				} else if(result == 1){
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
	

	public void CreateDelUserDialog() {
		SharedPreferences account = getSharedPreferences(UserAction.ACCOUNT, 0);
		final String username = account.getString(UserAction.NAME, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Bạn có chắc chắn muốn xóa tài khoản " + username);
		builder.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						
						UserAction user = new UserAction(getBaseContext());
						
						boolean delete = true;
						
						int userID = user.getIdByName(username);
						//delete in any database table had this userID
						XepLoai xl = new XepLoai(getBaseContext());
						xl.open();
						delete = xl.DeleteByUserID(userID);
						xl.close();

						DeThiRandom dt = new DeThiRandom(getBaseContext());
						dt.open();
						delete = dt.DeleteByUserID(userID);
						dt.close();
													
						int result = user.DelUser(username);
						if (result == 2) {	
							if (ListUser().length == 0) {															
								user.removeSaveInfo();								
							} 
							Log.w("xoa tai khoan ok?", "OK");
							dialog.dismiss();
							Toast.makeText(getBaseContext(), "Xóa tài khoản thành công!", Toast.LENGTH_LONG).show();
						}
						else if(result == 1){
							CreateDelUserDialog();
							Toast.makeText(getBaseContext(), "Không tồn tại tài khoản này trong cơ sở dữ liệu", Toast.LENGTH_LONG).show();							
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
	

	public void createEditUserDialog() {
		final UserAction user = new UserAction(this);
		// String[] usernames is a list of username query from database
		final String[] listUser;
		listUser = user.getListofUserName(this);
		if(listUser != null && listUser.length != 0){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setSingleChoiceItems(listUser, 0, null);
			builder.setTitle("Chọn tài khoản của bạn...");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					int itemChecked = ((AlertDialog) dialog).getListView()
							.getCheckedItemPosition();
					for (int i = 0; i < listUser.length; i++) {
						if (itemChecked == i) {
							user.storeInformation(listUser[i]);
							dialog.dismiss();						
							
							Intent intent = new Intent(AccountActivity.this, LuocSu.class);
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
		else{
			Toast.makeText(getApplicationContext(), "Không có tài khoản nào!", Toast.LENGTH_SHORT).show();
		}
	}

	private String[] ListUser() {
		String[] listUser;
		UserAction u = new UserAction(this);
		// String[] usernames is a list of username query from database
		listUser = u.getListofUserName(this);
		return listUser;
	}
	
	public void CreateChooseDelDialog() {
		final UserAction user = new UserAction(this);
		// String[] usernames is a list of username query from database
		final String[] listUser;
		listUser = user.getListofUserName(this);
		if(listUser != null && listUser.length != 0){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setSingleChoiceItems(listUser, 0, null);
			builder.setTitle("Chọn tên tài khoản...");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					
					int itemChecked = ((AlertDialog) dialog).getListView()
							.getCheckedItemPosition();
					for (int i = 0; i < listUser.length; i++) {
						if (itemChecked == i) {
							user.storeInformation(listUser[i]);
							
							dialog.dismiss();
							
							CreateDelUserDialog();
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
		else{
			Toast.makeText(getApplicationContext(), "Không có tài khoản nào!", Toast.LENGTH_SHORT).show();
		}
		
	}
}
