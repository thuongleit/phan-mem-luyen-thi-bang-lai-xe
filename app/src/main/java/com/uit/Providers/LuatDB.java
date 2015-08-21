package com.uit.Providers;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uit.objects.Luat;

public class LuatDB {
	public static final String KEY_ID = "id";
	public static final String KEY_CHUONG = "chuong";
	public static final String KEY_CAPDO = "capdo";
	public static final String KEY_NOIDUNG = "noidung";
	public static final int ID_CHUONG = 1;
	public static final int ID_DIEU = 2;
	public static final int ID_MUC = 3;

	private static final String TAG = "CauHoi";

	private static final String DATABASE_NAME = "lythuyetlaixe";
	private static final String DATABASE_TABLE = "luatgiaothong";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table luatgiaothong (id integer primary key not null, chuong integer, "
			+ " capdo integer, noidung text";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public LuatDB(Context context) {
		super();
		this.context = context;
		DBHelper = new DatabaseHelper(this.context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		// constructor
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		//

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	// open the database
	public LuatDB open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// close the database
	public void close() {
		DBHelper.close();
	}

	// insert a question into the database
	// this function don't need
	/*
	 * public long insertRow(Context _context, String name, long _ngaygiothi,
	 * long _thoigianhoanthanh, int _ketqua){ //get userid from name Person p =
	 * new Person(_context); Cursor user = p.getUser(name);
	 * 
	 * int userId = -1; if(user.moveToFirst()){ //get id userId =
	 * user.getInt(0); }
	 * 
	 * if(userId == -1){ Toast.makeText(_context,
	 * "Không tồn tại tài khoản này!", Toast.LENGTH_SHORT).show(); return -1; }
	 * else { ContentValues initialValues = new ContentValues();
	 * initialValues.put(KEY_USERID, userId); initialValues.put(KEY_NGAYGIOTHI,
	 * _ngaygiothi); initialValues.put(KEY_THOIGIANHOANTHANH,
	 * _thoigianhoanthanh); initialValues.put(KEY_KETQUA, _ketqua); return
	 * db.insert(DATABASE_TABLE, null, initialValues); } }
	 */

	// delete
	public boolean deleteAllRows() {
		return db.delete(DATABASE_TABLE, null, null) > 0;
	}

	// retrieves all rows
	public Cursor getAllRows() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CHUONG,
				KEY_CAPDO, KEY_NOIDUNG }, null, null, null, null, null);
	}

	// public Cursor getSpecialRow(int loai) {
	// return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_CHUONG,
	// KEY_CAPDO, KEY_NOIDUNG }, KEY_PHANLOAI + "="
	// + loai, null, null, null, null);
	// }

	public ArrayList<Luat> get_luat_all() {
		ArrayList<Luat> list_luat = new ArrayList<Luat>();
		int id;
		int chuong;
		int capdo;
		String noidung = "";

		LuatDB db = new LuatDB(context);
		db.open();
		Cursor c;
		c = db.getAllRows();
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
				chuong = c.getInt(1);
				capdo = c.getInt(2);
				noidung = c.getString(3);
				Luat luat_temp = new Luat(id, chuong, capdo, noidung);
				list_luat.add(luat_temp);
			} while (c.moveToNext());
		}
		db.close();
		return list_luat;
	}

	public ArrayList<Luat> getChuong() {
		ArrayList<Luat> list_luat = new ArrayList<Luat>();
		Luat luat = null;
		LuatDB db = new LuatDB(context);
		db.open();
		Cursor c = db.getLuatWithCapDo(LuatDB.ID_CHUONG);
		if (c.moveToFirst()) {
			do {
				int id = c.getInt(0);
				int _chuong = c.getInt(1);
				int capdo = c.getInt(2);
				String noidung = c.getString(3);
				luat = new Luat(id, _chuong, capdo, noidung);
				list_luat.add(luat);
			} while (c.moveToNext());
		}
		db.close();
		return list_luat;
	}

	public ArrayList<Luat> getDieu(int chuong, int capdo) {
		ArrayList<Luat> list_luat = new ArrayList<Luat>();
		Luat luat = null;
		LuatDB db = new LuatDB(context);
		db.open();
		Cursor c = db.getLuatWithDieu(chuong, capdo);
		if (c.moveToFirst()) {
			do {
				int id = c.getInt(0);
				int _chuong = c.getInt(1);
				int _capdo = c.getInt(2);
				String noidung = c.getString(3);
				luat = new Luat(id, _chuong, _capdo, noidung);
				list_luat.add(luat);
			} while (c.moveToNext());
		}
		db.close();
		return list_luat;
	}
	public Luat getLuatwithId(int id) {
		Luat luat = null;
		LuatDB db = new LuatDB(context);
		db.open();
		Cursor c = db.getLuatWithID(id);
		if (c.moveToFirst()) {
			do {
				int _id = c.getInt(0);
				int chuong = c.getInt(1);
				int capdo = c.getInt(2);
				String noidung = c.getString(3);
				luat = new Luat(_id, chuong, capdo, noidung);
			} while (c.moveToNext());
		}
		db.close();
		return luat;
	}

	private Cursor getLuatWithCapDo(int capdo) {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CHUONG,
				KEY_CAPDO, KEY_NOIDUNG }, KEY_CAPDO + "=" + capdo, null, null,
				null, null);
	}

	private Cursor getLuatWithDieu(int chuong, int capdo) {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CHUONG,
				KEY_CAPDO, KEY_NOIDUNG }, KEY_CHUONG + "=" + chuong + " and "
				+ KEY_CAPDO + "=" + capdo, null, null, null, null);
	}

	private Cursor getLuatWithID(int id) {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CHUONG,
				KEY_CAPDO, KEY_NOIDUNG }, KEY_ID + "=" + id, null, null, null,
				null);
	}

}
