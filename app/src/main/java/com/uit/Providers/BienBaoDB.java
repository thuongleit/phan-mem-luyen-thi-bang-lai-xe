package com.uit.Providers;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uit.objects.BienBao;

public class BienBaoDB {
	public static final String KEY_ID = "id";
	public static final String KEY_LINK = "link";
	public static final String KEY_TEN_BB = "tenbb";
	public static final String KEY_NOIDUNG = "noidung";
	public static final String KEY_PHANLOAI = "phanloai";
	public static final int ID_ALL = 0;
	public static final int ID_CAM = 1;
	public static final int ID_NGUYHIEM = 2;
	public static final int ID_HIEULENH = 3;
	public static final int ID_CHIDAN = 4;
	public static final int ID_PHU = 5;

	public static final String STR_ALL = "Tất cả";
	public static final String STR_CAM = "Biển cấm";
	public static final String STR_HIEULENH = "Biển hiệu lệnh";
	public static final String STR_CHIDAN = "Biển chỉ dẫn";
	public static final String STR_NGUYHIEM = "Biển nguy hiểm";
	public static final String STR_PHU = "Biển báo phụ";

	private static final String TAG = "CauHoi";

	private static final String DATABASE_NAME = "lythuyetlaixe";
	private static final String DATABASE_TABLE = "bienbao";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table bienbao (id integer primary key not null, link text, "
			+ " tenbb text, noidung text, phanloai text";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public BienBaoDB(Context context) {
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
	public BienBaoDB open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// close the database
	public void close() {
		DBHelper.close();
	}

	// delete
	public boolean deleteAllRows() {
		return db.delete(DATABASE_TABLE, null, null) > 0;
	}

	// retrieves all rows
	public Cursor getAllRows() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_LINK,
				KEY_TEN_BB, KEY_NOIDUNG, KEY_PHANLOAI }, null, null, null,
				null, null);
	}

	public Cursor getSpecialRow(int loai) {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_LINK,
				KEY_TEN_BB, KEY_NOIDUNG, KEY_PHANLOAI }, KEY_PHANLOAI + "="
				+ loai, null, null, null, null);
	}

	public ArrayList<BienBao> get_list_bienbao(int loai) {
		ArrayList<BienBao> list_bienbao = new ArrayList<BienBao>();
		int id;
		String linkanh = "";
		String tenbb = "";
		String noidung = "";
		String phanloai = "";
		BienBaoDB bb = new BienBaoDB(context);
		bb.open();
		Cursor c;
		if (loai == ID_ALL) {
			c = bb.getAllRows();
		} else {
			c = bb.getSpecialRow(loai);
		}
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
				linkanh = c.getString(1);
				tenbb = c.getString(2);
				noidung = c.getString(3);
				phanloai = c.getString(4);
				BienBao bb_temp = new BienBao(id, linkanh, tenbb, noidung,
						phanloai);
				list_bienbao.add(bb_temp);
			} while (c.moveToNext());
		}
		bb.close();
		return list_bienbao;
	}

	public BienBao getBienBaoWithTen(String tenbb) {
		BienBao bienbao = null;
		BienBaoDB db = new BienBaoDB(context);
		db.open();
		Cursor c = db.getBienBaoWithTenBB(tenbb);
		if (c.moveToFirst()) {
			do {
				int _id = c.getInt(0);
				String linkanh = c.getString(1);
				String noidung = c.getString(3);
				String phanloai = c.getString(4);
				bienbao = new BienBao(_id, linkanh, tenbb, noidung, phanloai);
			} while (c.moveToNext());
		}
		db.close();
		return bienbao;
	}

	public Cursor getBienBaoWithID(int id) {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_LINK,
				KEY_TEN_BB, KEY_NOIDUNG, KEY_PHANLOAI }, KEY_ID + "=" + id,
				null, null, null, null);
	}

	public Cursor getBienBaoWithTenBB(String tenbb) {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_LINK,
				KEY_TEN_BB, KEY_NOIDUNG, KEY_PHANLOAI }, KEY_TEN_BB + "=\""
				+ tenbb + "\"", null, null, null, null);
	}

}
