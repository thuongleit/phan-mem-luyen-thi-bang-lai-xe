package com.uit.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.uit.Providers.DeThiRandom;
import com.uit.Providers.UserDB;
import com.uit.Providers.XepLoai;

public class UserAction {
	public final static String ACCOUNT = "account";
	public final static String NAME = "name";
	public final static String USERID = "id";// dung không

	
	public final static String START_TIME = "thoi gian bat dau lam bai";
	private Context context;
	final UserDB user;

	public UserAction(Context context) {
		super();
		this.context = context;
		user = new UserDB(context);

	}

	public int AddUser(String username) {
		int result = 0;
		
		// check name is not null
		if (username == null || username.trim().equals("")) {
			return result = 1;
//			result = "Tên tài khoản không thể để trống";
			
		}
		
		// get all users
		user.open();
		Cursor c = user.getAllUsers();
		if (c != null && c.moveToFirst()) {
			do {
				// check name is not exists in database
				if (user.CheckName(c, username)) {
					return result = 2;
//					result = "Tên tài khoản đã tồn tại";
					
				}
				// check email rule is true

			} while (c.moveToNext());
		}

		// insert data
		// insert to database
		long i = user.insertUser(username);

		if (i != -1)
			result = 3;
//			result = "Thêm tài khoản thành công!";
		else
			result = 4;
//			result = "Thêm tài khoản thất bại";

		// close adapter
		user.close();
		return result;
	}

	public int DelUser(String username) {
		int result;

		final UserDB p = new UserDB(context);
		
		boolean delete = false;

		if (username == null) {
			result =  0;
//			result = "Vui lòng chọn tài khoản";
		} else {
			int id = -1;
			p.open();
			Cursor c = p.getAllUsers();
			if (c.moveToFirst()) {
				do {
					if (p.CheckName(c, username)){
						id = c.getInt(0);
						break;
					}
				} while (c.moveToNext());
			}
			// if don't exists username entered in database, warning!
			if (id == -1) {
				result = 1;
//				result = "Không tồn tại tài khoản này trong cơ sở dữ liệu";
			} else {
				// delete user name entered
				//delete in any database table had this userID
				XepLoai xl = new XepLoai(context);
				xl.open();
				delete = xl.DeleteByUserID(id);
				xl.close();

				DeThiRandom dt = new DeThiRandom(context);
				dt.open();
				delete = dt.DeleteByUserID(id);
				dt.close();
				
				
				p.deleteUser(id);
				if(delete == true){
					result = 2;
//					result = "Xóa tài khoản thành công!";
				}else{
					result = 3;
//					result = "Xóa tài khoản không thành công!";
				}
				
			}
			p.close();
		}
		return result;
	}

	public void storeInformation(String username) {
		SharedPreferences account = context.getSharedPreferences(ACCOUNT, 0);
		SharedPreferences.Editor editor = account.edit();
		editor.putString(NAME, username);
		editor.commit();

		// edit id user
		int id = getIdByName(username);
		SharedPreferences userID = context.getSharedPreferences(USERID, 0);
		SharedPreferences.Editor editUserId = userID.edit();
		editUserId.putInt(USERID, id);
		editUserId.commit();
	}

	public void removeSaveInfo() {
		SharedPreferences account = context.getSharedPreferences(ACCOUNT, 0);
		SharedPreferences.Editor editor = account.edit();
		editor.remove(ACCOUNT);
		editor.commit();

		SharedPreferences userID = context.getSharedPreferences(USERID, 0);
		SharedPreferences.Editor editUserId = userID.edit();
		editUserId.remove(USERID);
		editUserId.commit();
	}

	// get id user by Name
	public int getIdByName(String _name) {
		UserDB p = new UserDB(context);
		p.open();
		Cursor c = p.getUser(_name);
		int id = 0;
		if (c.moveToFirst()) {
			id = c.getInt(0);
		}

		p.close();
		return id;

	}

	/**
	 * function query list of username from database after click button delete
	 * 
	 * @param _context
	 * @return
	 */
	public String[] getListofUserName(Context _context) {

		// create a Person instance
		UserDB user = new UserDB(_context);
		user.open();

		Cursor c = user.getAllUsers();
		int count = c.getCount();
		// Toast.makeText(getBaseContext(), ((Integer)count).toString(),
		// Toast.LENGTH_SHORT).show();
		int index = 0;
		String[] result = new String[count];
		if (c.moveToFirst()) {
			do {
				// add name to list String
				result[index++] = c.getString(1).toString();
			} while (c.moveToNext());
		}
		user.close();

		return result;
	}

	/**
	 * function to check name of user
	 * 
	 * @param c
	 * @param _name
	 * @return
	 */
	public boolean CheckName(Cursor c, String _name) {
		String str = c.getString(1);
		if (str.equals(_name))
			return true;
		return false;
	}

	public void UpdateTimeDo(long ngaygioThi){
		SharedPreferences start_time = context.getSharedPreferences(START_TIME, 0);
		SharedPreferences.Editor editor = start_time.edit();
		editor.putLong(START_TIME, ngaygioThi);		
		editor.commit();
	}
}