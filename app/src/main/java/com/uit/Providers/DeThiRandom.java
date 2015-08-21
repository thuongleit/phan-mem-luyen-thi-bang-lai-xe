package com.uit.Providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DeThiRandom {
	public static final String KEY_IDBODE = "idbode";
	public static final String KEY_IDUSER = "iduser";
	public static final String KEY_IDCAUHOI = "idcauhoi";
	public static final String KEY_DAPAN = "dapan";
	public static final String KEY_DATRALOI = "datraloi";
	public static final String KEY_TRALOIDUNG = "traloidung";	
	public static final String KEY_HINHANH= "hinhanh";
	private static final String TAG = "DeThiRandom";
	
	private static final String DATABASE_NAME = "lythuyetlaixe";
	private static final String DATABASE_TABLE = "dethirandom";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = 
			"create table dethirandom (idbode integer not null, iduser integer not null, idcauhoi integer not null, "
			+ "dapan text, datraloi text default null, traloidung integer default 0, hinhanh text default null, "
			+ "primary key(idbode, iduser, idcauhoi));";
	
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	/**
	 * constructor
	 * @param _context
	 */
	public DeThiRandom(Context _context){
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

	/**
	 * open the database
	 * @return
	 * @throws SQLException
	 */
	public DeThiRandom open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * close the database
	 */
	public void close(){
		DBHelper.close();
	}

	/**
	 * insert a question into the database belong userid
	 * @param idBode
	 * @param userId
	 * @param idCauhoi
	 * @param dapAnCauHoi
	 * @return
	 */
	public long insertQuestionBelongUser(int idBode, int userId, int idCauhoi, String dapAnCauHoi, String hinhanh){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_IDBODE, idBode);
		initialValues.put(KEY_IDUSER, userId);
		initialValues.put(KEY_IDCAUHOI, idCauhoi);
		initialValues.put(KEY_DAPAN, dapAnCauHoi);
		if(hinhanh != null && !(hinhanh.equalsIgnoreCase(""))){
			initialValues.put(KEY_HINHANH, hinhanh);
		}
		return db.insert(DATABASE_TABLE, null, initialValues);		
	}

	/**
	 * delete a particular question belong userid and idBode
	 * @param idCauhoi
	 * @param idBode
	 * @param idUser
	 * @return
	 */
	public boolean deleteUser(int idCauhoi, int idBode, int idUser ){
		return db.delete(DATABASE_TABLE, KEY_IDBODE + "=" + idBode + " AND "
				+ KEY_IDUSER + "=" + idUser + " AND "
				+ KEY_IDCAUHOI + "=" + idCauhoi
				, null) > 0;
	}

	
	/**
	 * update the answer for a question
	 * @param idBode
	 * @param idUser
	 * @param idCauhoi
	 * @param dapan
	 * @return
	 */
	public boolean updateAnswer(int idBode, int idUser, int idCauhoi, String dapan){
		Log.d("dang update tai CauHoi voi dapan ", 
				 ((Integer)idCauhoi).toString() + " " + dapan);
		ContentValues args = new ContentValues();
		args.put(KEY_DATRALOI, dapan);
		
		return db.update(DATABASE_TABLE, args, 
						KEY_IDBODE + "=" + idBode + " AND "
						+ KEY_IDUSER + "=" + idUser + " AND "
						+ KEY_IDCAUHOI + "=" + idCauhoi
						, null) > 0;
		
	}
	
	/**
	 * Update result is right or wrong for a question
	 * @param idBode
	 * @param idUser
	 * @param idCauhoi
	 * @param result
	 * @return
	 */
	public boolean updateResult(int idBode, int idUser, int idCauhoi, int result){
		
		
		ContentValues args = new ContentValues();
		args.put(KEY_TRALOIDUNG, result);
		
		return db.update(DATABASE_TABLE, args, 
						KEY_IDBODE + "=" + idBode + " AND "
						+ KEY_IDUSER + "=" + idUser + " AND "
						+ KEY_IDCAUHOI + "=" + idCauhoi
						, null) > 0;
	}
	
	/**
	 * hàm trả v�? số lần được ch�?n của các câu h�?i tùy theo iduser
	 * @param userId
	 * @return
	 */
	public Cursor getQuestionsByUserId(int userId) {
		Cursor mCursor = 
				db.query(DATABASE_TABLE, new String[] {KEY_IDCAUHOI}, KEY_IDUSER + "=" + userId, null, null, null, null, null);
		if(mCursor != null){
			mCursor.moveToFirst();			
		}
		return mCursor;
	}
	
	/**
	 * Hàm trả v�? ID bộ đ�? mới nhất theo userid
	 * @param userId
	 * @return
	 */
	public int getIdLastBodeByUserId(int userId){
		int idBode = 0;
		boolean ck = CheckTestByUserId(userId);
		if(ck == false)
			return 0;
						
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE, new String[] {KEY_IDBODE}, KEY_IDUSER + "=" + userId, null, null, null, KEY_IDBODE + " DESC", null);
		if(mCursor != null){
			mCursor.moveToFirst();
			idBode = mCursor.getInt(0);
		}
		
		
//		Log.d("id bo de", ((Integer)idBode).toString());
		return idBode;
	}
	
	/**
	 * ham check user nay da thi lan nao chua? neu chua thi thi tra ve true
	 * @param userID
	 * @return
	 */
	public boolean CheckTestByUserId(int userID) {
		Cursor c = db.query(true, DATABASE_TABLE, new String[] {KEY_IDUSER}, KEY_IDUSER + "=" + userID, null, null, null, null, null);
		
		if(c!= null && c.moveToFirst()){
			return true;
		}
		return false;
	}
	
	/**
	 * hàm trả v�? 30 câu trắc nghiệm mới nhất của iduser
	 * sắp xếp theo chiều tăng dần của id câu hỏi
	 * @param userID
	 * @return
	 */
	public Cursor getIdQuestionsForDethi(int userID){
		//lay ve id cua bo de moi nhat
		int idde = getIdLastBodeByUserId(userID);
		
		//truy van trong bang dethi random de lay ra bo de id cua userid
		Cursor c = db.query(DATABASE_TABLE, new String[] {KEY_IDCAUHOI, KEY_DAPAN, KEY_IDBODE, KEY_DATRALOI, KEY_HINHANH}, 
				KEY_IDBODE + " = " + idde + " AND " + KEY_IDUSER + " = " + userID, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
		}
		return c;
	}
	
	public Cursor getDaTraLoiByIdUserLastDeThi(int userID){
		//lay ve id cua bo de moi nhat
		int idde = getIdLastBodeByUserId(userID);
		
		//truy van trong bang dethi random de lay ra bo de id cua userid
		Cursor c = db.query(DATABASE_TABLE, new String[] { KEY_DATRALOI}, 
				KEY_IDBODE + " = " + idde + " AND " + KEY_IDUSER + " = " + userID, 
				null, null, null, null);
		if(c!=null){
			c.moveToFirst();
		}
		return c;
	}
	
	/**
	 * Hàm trả v�? phương án trả l�?i ngư�?i dùng ch�?n của câu h�?i có idQuestion,	 thuộc bộ đ�? có idBode của user idUser
	 * @param idQuestion
	 * @param idBode
	 * @param idUser
	 * @return
	 */
	 
	public String getDatraloiByIdQuestion(int idQuestion, int idBode, int idUser){
		String datraloi = null;
		Cursor c = db.query(DATABASE_TABLE, new String[]{KEY_DATRALOI}, KEY_IDBODE + " = " + idBode + " AND " +
				KEY_IDUSER + " = " + idUser + " AND "+
				KEY_IDCAUHOI + " = " + idQuestion, null, null, null, null);
		if(c !=null){
			c.moveToFirst();
			datraloi = c.getString(0);
		}
		return datraloi;
	}
	
	/**Hàm trả về kết quả là mảng đã trả lời đúng (hoặc sai, hoặc chưa trả lời) ứng với các câu tương ứng
	 * 
	 * @param idBode
	 * @param idUser
	 * @return
	 */
	public Cursor getKetQuaByIdBoDeIdUser(int idBode, int idUser){
		return db.query(DATABASE_TABLE, new String[]{KEY_DATRALOI, KEY_TRALOIDUNG}, KEY_IDBODE + " = " + idBode + " AND " +
				KEY_IDUSER + "=" + idUser , null, null, null, null);
		
	}
	/**
	 * Hàm trả về các câu hỏi theo idDeThi và idUser được truyền vào
	 */
	public Cursor getIdQuestionsByIdUserIdDeThi(int idUser, int idBoDe){
		
		return db.query(DATABASE_TABLE, new String[]{KEY_IDCAUHOI, KEY_DAPAN, KEY_DATRALOI, KEY_TRALOIDUNG, KEY_HINHANH}, KEY_IDBODE + " = " + idBoDe + " AND " +
				KEY_IDUSER + " = " + idUser , null, null, null, null);
		
	}
	public boolean DeleteByUserID(int userID) {
		// TODO Auto-generated method stub
		return db.delete(DATABASE_TABLE, KEY_IDUSER + "=" + userID , null) > 0;
	}
}
