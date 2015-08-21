package com.uit.Providers;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uit.objects.HuongDan;

public class HuongDanDB {
	public static final String KEY_ID = "id";
	public static final String KEY_TIEUDE = "tieude";
	public static final String KEY_TIEUDECON = "tieudecon";
	public static final String KEY_NOIDUNG = "noidung";

	private static final String TAG = "CauHoi";

	private static final String DATABASE_NAME = "lythuyetlaixe";
	private static final String DATABASE_TABLE = "huongdan";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table huongdan (id integer primary key not null, tieude text, "
			+ " tieudecon text, noidung text";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public HuongDanDB(Context context) {
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
	public HuongDanDB open() throws SQLException {
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
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_TIEUDE,
				KEY_TIEUDECON, KEY_NOIDUNG }, null, null, null, null, null);
	}

	public Cursor getSpecialRow(int id) {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_TIEUDE,
				KEY_TIEUDECON, KEY_NOIDUNG }, KEY_ID + "=" + id, null, null,
				null, null);
	}

	public ArrayList<HuongDan> get_all_huongdan() {
		ArrayList<HuongDan> list = new ArrayList<HuongDan>();
		int id;
		String tieude = "";
		String tieudecon = "";
		String noidung = "";
		HuongDanDB db = new HuongDanDB(context);
		db.open();
		Cursor c;
		c = db.getAllRows();
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
				tieude = c.getString(1);
				tieudecon = c.getString(2);
				noidung = c.getString(3);
				HuongDan temp = new HuongDan(id, tieude, tieudecon, noidung);
				list.add(temp);
			} while (c.moveToNext());
		}
		db.close();
		return list;
	}
	
	public ArrayList<HuongDan> get_huongdan_id(int _id) {
		ArrayList<HuongDan> list = new ArrayList<HuongDan>();
		int id;
		String tieude = "";
		String tieudecon = "";
		String noidung = "";
		HuongDanDB db = new HuongDanDB(context);
		db.open();
		Cursor c;
		c = db.getSpecialRow(_id);
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
				tieude = c.getString(1);
				tieudecon = c.getString(2);
				noidung = c.getString(3);
				HuongDan temp = new HuongDan(id, tieude, tieudecon, noidung);
				list.add(temp);
			} while (c.moveToNext());
		}
		db.close();
		return list;
	}
}
