package com.uit.Providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDB {
	public static final String KEY_ROWID = "id";
	public static final String KEY_NAME = "name";	
	private static final String TAG = "Person";
	
	private static final String DATABASE_NAME = "lythuyetlaixe";
	private static final String DATABASE_TABLE = "users";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = 
			"create table users (id integer primary key autoincrement, "
			+ "name text not null);";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	//constructor
	public UserDB(Context _context){
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
	public UserDB open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//close the database
	public void close(){
		DBHelper.close();
	}

	//insert a person into the database
	public long insertUser(String name){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);		
		return db.insert(DATABASE_TABLE, null, initialValues);
		
	}

	//delete a particular user
	public boolean deleteUser(int rowId){
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	//retrieves all users
	public Cursor getAllUsers(){
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME}, null, null, null, null, null);
	}
	
	//retrieves a particular user by rowId
	public Cursor getUser(int rowId) throws SQLException{
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if(mCursor != null){
			mCursor.moveToFirst();			
		}
		return mCursor;
	}
	
	
	
	//retrieves a particular user by name
	public Cursor getUser(String name) throws SQLException{
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME}, KEY_NAME + " like '" + name + "'", null, null, null, null, null);
		if(mCursor != null){
			mCursor.moveToFirst();			
		}
		return mCursor;
	}

	//update a user
	public boolean updateUser(int rowId, String name, String email){
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);		
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
		
	}
	//function to check name of user
    public boolean CheckName(Cursor c, String _name){
    	String str = c.getString(1);
    	if(str.equals(_name))
    		return true;    	return false;
    }
    
    public String[] getListofUserName() {

		// create a Person instance
		UserDB user = new UserDB(context);
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
    public boolean DeleteByUserID(int userID) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + userID , null) > 0;
	}
  
}
