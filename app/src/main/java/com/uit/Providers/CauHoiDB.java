package com.uit.Providers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uit.objects.CauHoi;

public class CauHoiDB {
	public static final String KEY_ROWID = "id";
	public static final String KEY_NOIDUNG = "noidung";
	public static final String KEY_LUACHON1 = "luachon1";
	public static final String KEY_LUACHON2 = "luachon2";
	public static final String KEY_LUACHON3 = "luachon3";
	public static final String KEY_LUACHON4 = "luachon4";
	public static final String KEY_DAPAN = "dapan";
	public static final String KEY_HINHANH = "hinhanh";
	public static final String KEY_THELOAI = "theloai";
	public static final String KEY_SOLANTRALOISAI = "solantraloisai";

	private static final String TAG = "CauHoi";

	private static final String DATABASE_NAME = "lythuyetlaixe";
	private static final String DATABASE_TABLE = "cauhoi";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table cauhoi (id integer primary key not null, noidung text, "
			+ " luachon1 text, luachon2 text, luachon3 text default(null), luachon4 text default(null), "
			+ " dapan text, hinhanh text default(null), "
			+ " theloai integer, solantraloisai integer default(0));";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	// constructor
	public CauHoiDB(Context _context) {
		super();
		this.context = _context;
		DBHelper = new DatabaseHelper(context);

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
	public CauHoiDB open() throws SQLException {
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
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NOIDUNG,
				KEY_LUACHON1, KEY_LUACHON2, KEY_LUACHON3, KEY_LUACHON4,
				KEY_DAPAN, KEY_HINHANH, KEY_THELOAI, KEY_SOLANTRALOISAI },
				null, null, null, null, null);
	}

	// get questions has most number of wrong answer
	public Cursor getMostWrongRows() throws SQLException {
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NOIDUNG,
				KEY_LUACHON1, KEY_LUACHON2, KEY_LUACHON3, KEY_LUACHON4,
				KEY_DAPAN, KEY_HINHANH, KEY_THELOAI, KEY_SOLANTRALOISAI },
				null, null, null, null, KEY_SOLANTRALOISAI + " DESC");
	}

	public Cursor getRowsWithTheLoai(int loai) throws SQLException {
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NOIDUNG,
				KEY_LUACHON1, KEY_LUACHON2, KEY_LUACHON3, KEY_LUACHON4,
				KEY_DAPAN, KEY_HINHANH, KEY_THELOAI, KEY_SOLANTRALOISAI },
				KEY_THELOAI + "=" + loai, null, null, null, null);
	}

	public ArrayList<CauHoi> getTheLoai(int[] loai) {
		ArrayList<CauHoi> cauhoi = new ArrayList<CauHoi>();
		CauHoi temp;
		String noidung;
		int id;
		String luachon1;
		String luachon2;
		String luachon3;
		String luachon4;
		String dapan;
		String hinhanh;
		int theloai;
		int solantraloisai;

		CauHoiDB db = new CauHoiDB(context);
		db.open();
		for (int i = 0; i < loai.length; i++) {
			Cursor c = db.getRowsWithTheLoai(loai[i]);
			if (c.moveToFirst()) {
				do {
					id = c.getInt(0);
					noidung = c.getString(1);
					luachon1 = c.getString(2);
					luachon2 = c.getString(3);
					luachon3 = c.getString(4);
					luachon4 = c.getString(5);
					dapan = c.getString(6);
					hinhanh = c.getString(7);
					theloai = c.getInt(8);
					solantraloisai = c.getInt(9);
					temp = new CauHoi(id, noidung, luachon1, luachon2,
							luachon3, luachon4, dapan, hinhanh, theloai,
							solantraloisai);
					cauhoi.add(temp);
				} while (c.moveToNext());
			}
		}
		db.close();
		return cauhoi;
	}

	public ArrayList<CauHoi> getMostWrongs(int count) {
		ArrayList<CauHoi> cauhoi = new ArrayList<CauHoi>();
		CauHoi temp;
		String noidung;
		int id;
		String luachon1;
		String luachon2;
		String luachon3;
		String luachon4;
		String dapan;
		String hinhanh;
		int theloai;
		int solantraloisai;

		CauHoiDB db = new CauHoiDB(context);
		db.open();
		Cursor c = db.getMostWrongRows();
		int index = 0;
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
				noidung = c.getString(1);
				luachon1 = c.getString(2);
				luachon2 = c.getString(3);
				luachon3 = c.getString(4);
				luachon4 = c.getString(5);
				dapan = c.getString(6);
				hinhanh = c.getString(7);
				theloai = c.getInt(8);
				solantraloisai = c.getInt(9);
				temp = new CauHoi(id, noidung, luachon1, luachon2, luachon3,
						luachon4, dapan, hinhanh, theloai, solantraloisai);
				cauhoi.add(temp);
				index++;
			} while (c.moveToNext() && index < count); // count = quy định số
														// câu sai nhìu nhất
														// được hiển thị
		}
		db.close();
		return cauhoi;
	}

	public boolean updateWrongAnswerById(int idQuestion) {

		ContentValues args = new ContentValues();
		args.put(KEY_SOLANTRALOISAI, getWrongNumberById(idQuestion) + 1);// tang
																			// len
																			// them
																			// 1
																			// lan
																			// sai
																			// nua
																			// cho
																			// cau
																			// hoi
																			// nay

		return db.update(DATABASE_TABLE, args, KEY_ROWID + " = " + idQuestion,
				null) > 0;
	}

	// get answer for questions has id input
	public Cursor getAnswerAndImageById(int idQuestion) {
		return db.query(DATABASE_TABLE,
				new String[] { KEY_DAPAN, KEY_HINHANH }, KEY_ROWID + " = "
						+ idQuestion, null, null, null, null);
	}

	// get question by id
	public Cursor getQuestionById(int idQuestion) {
		return db.query(DATABASE_TABLE, new String[] { KEY_NOIDUNG,
				KEY_LUACHON1, KEY_LUACHON2, KEY_LUACHON3, KEY_LUACHON4,
				KEY_DAPAN }, KEY_ROWID + " = " + idQuestion, null, null, null,
				null);

	}

	// get wrong number of question has idQuestion
	public int getWrongNumberById(int idQuestion) {
		int result = 0;
		Cursor c = db.query(DATABASE_TABLE,
				new String[] { KEY_SOLANTRALOISAI }, KEY_ROWID + " = "
						+ idQuestion, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			result = c.getInt(0);
		}
		return result;
	}
}
