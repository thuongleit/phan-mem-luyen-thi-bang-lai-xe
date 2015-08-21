package com.uit.Providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class XepLoai {
	public static final String KEY_ROWID = "id";
	public static final String KEY_USERID = "userid";
	public static final String KEY_IDDETHI = "iddethi";
	public static final String KEY_USERNAME = "name";
	public static final String KEY_NGAYGIOTHI = "ngaygiothi";
	public static final String KEY_THOIGIANHOANTHANH = "thoigianhoanthanh";
	public static final String KEY_KETQUA = "ketqua";
	private static final String TAG = "ThongKe";
	
	private static final String DATABASE_NAME = "lythuyetlaixe";
	private static final String DATABASE_TABLE = "thongke";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = 
			"create table thongke (id integer primary key autoincrement, userid integer not null, iddethi integer not null, ngaygiothi long not null, "
			+ "thoigianhoanthanh integer, ketqua integer);";
	
	
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	//constructor
	public XepLoai(Context _context){
		super();
		this.context = _context;
		DBHelper = new DatabaseHelper(context);
		
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		//constructor
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		//

		@Override
		public void onCreate(SQLiteDatabase db) {
			try{
				db.execSQL(DATABASE_CREATE);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " +
						newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
			onCreate(db);
			
		}
		
	}

	//open the database
	public XepLoai open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//close the database
	public void close(){
		DBHelper.close();
	}

	//insert a person into the database
		public long insertRowByUserId(Context _context, int idUser, int idDeThi, long _ngaygiothi, int _thoigianhoanthanh, int _ketqua){
			//get userid from name
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_USERID, idUser);
			initialValues.put(KEY_IDDETHI, idDeThi);
			initialValues.put(KEY_NGAYGIOTHI, _ngaygiothi);
			initialValues.put(KEY_THOIGIANHOANTHANH, _thoigianhoanthanh);
			initialValues.put(KEY_KETQUA, _ketqua);			
			return db.insert(DATABASE_TABLE, null, initialValues);
			
		}


	//Xoa tat ca cac hang du lieu trong bang thong ke
	public boolean deleteAllRows(){
		return db.delete(DATABASE_TABLE, null , null) > 0;
	}

	//retrieves all rows
	public Cursor getAllRows() throws SQLException{
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_USERID, KEY_NGAYGIOTHI, KEY_THOIGIANHOANTHANH, KEY_KETQUA, KEY_IDDETHI}, null, null, null, null, null);
	}
	
	//Truy van 10 nguoi thi tot nhat
	public Cursor getTenRows() throws SQLException{
		return db.query(DATABASE_TABLE, new String[] {KEY_USERID, KEY_NGAYGIOTHI, KEY_THOIGIANHOANTHANH, KEY_KETQUA, KEY_IDDETHI}, null, null, null, null, KEY_KETQUA + " DESC, " + KEY_THOIGIANHOANTHANH + " ASC");
	}

	//truy van thong tin theo ngaygio thi
	public Cursor getInfoBelongTime(long ngaygiothi) throws SQLException{
		return db.query(DATABASE_TABLE, new String[] {KEY_IDDETHI, KEY_USERID, KEY_THOIGIANHOANTHANH, KEY_KETQUA}, KEY_NGAYGIOTHI + "=" + ngaygiothi , null, null, null, null);
	}

	public void UpdateRowByUserId(int idUser,	long _ngaygiothi, int _thoigianhoanthanh, int _ketqua) {
		
		//get userid from name
		ContentValues initialValues = new ContentValues();				
		initialValues.put(KEY_THOIGIANHOANTHANH, _thoigianhoanthanh);
		initialValues.put(KEY_KETQUA, _ketqua);			
		db.update(DATABASE_TABLE, initialValues, KEY_USERID + "=" + idUser + " AND "
				+ KEY_NGAYGIOTHI + "=" + _ngaygiothi, null) ;
	}

	public Cursor getTenRowsByUserId(int userID) throws SQLException{
		return db.query(DATABASE_TABLE, new String[] {KEY_NGAYGIOTHI, KEY_KETQUA, KEY_THOIGIANHOANTHANH }, KEY_USERID + "=" + userID,
				null, null, null, 
				KEY_NGAYGIOTHI + " DESC " );
		
	}

	public boolean DeleteByUserID(int userID) {
		return db.delete(DATABASE_TABLE, KEY_USERID + "=" + userID , null) > 0;
		
	}

}
