/**
 * 
 */
package com.android.aid;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "DatabaseHelper";

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	
//	private int version;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
//		this.version = version;
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		createNewTables(db);
		
//		InitDeviceRegister(db);
		
//		InitDummyShuttleDispatch(db);
		
	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
//		DropTables(db, oldVersion);
		
//		CreateTables(db, newVersion);
		
//		InitDeviceRegister(db);
		
//		InitDummyShuttleDispatch(db);
		
		switch(oldVersion){
		
		case Data.DB_VERSION_1:
			updateVersion_2(db);			
		
		case Data.DB_VERSION_2:
			updateVersion_3(db);		
		
		case Data.DB_VERSION_3:
			updateVersion_4(db);	
			
		case Data.DB_VERSION_4:
			updateVersion_5(db);
			
		}

	}
	
	
	private void createNewTables(SQLiteDatabase db){
		
		String sql=null;
		
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_APP_REPOSITORY 
				+ "("
				+ Data.DB_COLUMN_APP_CATEGORY +","
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_FILE +","
				+ Data.DB_COLUMN_APP_URL +","
				+ Data.DB_COLUMN_APP_VERSION_NAME +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_APP_ICON_FILE +","
				+ Data.DB_COLUMN_APP_ICON_URL +","
				+ Data.DB_COLUMN_APP_OWNER +","
				+ Data.DB_COLUMN_APP_VENDOR +","
				+ Data.DB_COLUMN_APP_AGENT +","
				+ Data.DB_COLUMN_APP_STATE +","
				+ Data.DB_COLUMN_APP_PRICE +","
				+ Data.DB_COLUMN_APP_PRICING_POLICY +","
				+ Data.DB_COLUMN_APP_DEPLOY_DATE +","
				+ Data.DB_COLUMN_APP_UPDATE_STAMP +","
				+ Data.DB_COLUMN_UPDATE_FLAG
				+ ");";
		creatTable(db,sql);

		Log.i(TAG, sql);

		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_APP_INSTALLED 
				+ "("
				+ Data.DB_COLUMN_APP_CATEGORY +","
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_NAME +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME +","
				+ Data.DB_COLUMN_APP_LATEST_UPDATE_TIME +","
				+ Data.DB_COLUMN_UPDATE_FLAG
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
		

		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_APP_DOWNLOADED 
				+ "("
				+ Data.DB_COLUMN_APP_CATEGORY +","
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_NAME +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_APP_FILE_PATH +","
				+ Data.DB_COLUMN_UPDATE_FLAG
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
		

		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_APP_STATE_MONITOR 
				+ "("
				+ Data.DB_COLUMN_APP_CATEGORY +","
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_LOCATION +","
				+ Data.DB_COLUMN_APP_VERSION_NAME +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_APP_FILE +","
				+ Data.DB_COLUMN_APP_URL +","
				+ Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME +","
				+ Data.DB_COLUMN_APP_LATEST_UPDATE_TIME +","
				+ Data.DB_COLUMN_UPDATE_FLAG
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
	
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_APP_DOWNLOADING 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_APP_FILE +","
				+ Data.DB_COLUMN_APP_URL +","
				+ Data.DB_COLUMN_APP_FILE_PATH +","
				+ Data.DB_COLUMN_APP_FILE_LENGTH +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_LENGTH +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_START_TIME +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_STATE +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_SILENCE
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
	

		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_APP_MY 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_CATEGORY +"," //add from DB_VERSION_4
				+ Data.DB_COLUMN_UPDATE_FLAG
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);

		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_CATEGORY_MANAGER 
				+ "("
				+ Data.DB_COLUMN_CATEGORY_SERIAL +","
				+ Data.DB_COLUMN_APP_CATEGORY +","
				+ Data.DB_COLUMN_CATEGORY_DIR +","
				+ Data.DB_COLUMN_CATEGORY_STATUS +","
				+ Data.DB_COLUMN_CATEGORY_MAPPING +","
				+ Data.DB_COLUMN_UPDATE_FLAG +","
				+ Data.DB_COLUMN_CATEGORY_STAMP 
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
	
		//DB_VERSION_2
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_DOWNLOAD_REPORTER 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_APP_URL +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_START_TIME +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME 
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
	
		//DB_VERSION_3
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_INSTALL_REPORTER 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_INSTALL_ACTION +","
				+ Data.DB_COLUMN_INSTALL_ACTION_TIME 
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
		
		//DB_VERSION_5
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_LAUNCH_REPORTER 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_LAUNCH_BY_ME +","
				+ Data.DB_COLUMN_LAUNCH_TIME 
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
		
	
	}
	
	
	private void updateVersion_2(SQLiteDatabase db){
		String sql = null; 
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_DOWNLOAD_REPORTER 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_APP_URL +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_START_TIME +","
				+ Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME 
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);


	}
		
	private void updateVersion_3(SQLiteDatabase db){

		String sql = null; 
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_INSTALL_REPORTER 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_INSTALL_ACTION +","
				+ Data.DB_COLUMN_INSTALL_ACTION_TIME 
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
		
		
	}
	
	private void updateVersion_4(SQLiteDatabase db){

		String sql = null; 
		sql = "ALTER TABLE " 
				+ Data.DB_TABLE_APP_MY  
				+ " ADD "
				+ Data.DB_COLUMN_APP_CATEGORY + " TEXT "
				+ ";";
		creatTable(db,sql);
		Log.i(TAG, sql);
		
		
	}
	
	private void updateVersion_5(SQLiteDatabase db){

		String sql = null; 
		sql = "CREATE TABLE " 
				+ Data.DB_TABLE_LAUNCH_REPORTER 
				+ "("
				+ Data.DB_COLUMN_APP_PACKAGE +","
				+ Data.DB_COLUMN_APP_LABEL +","
				+ Data.DB_COLUMN_APP_VERSION_CODE +","
				+ Data.DB_COLUMN_LAUNCH_BY_ME +","
				+ Data.DB_COLUMN_LAUNCH_TIME 
				+ ");";
		creatTable(db,sql);
		Log.i(TAG, sql);
		
		
	}

	private void creatTable(SQLiteDatabase db,String sql){
		
		try{
			db.execSQL(sql);
		}
		catch(SQLiteException e){
			Log.e(TAG, "exception:"+sql);
			Log.e(TAG, "cause:"+e.getCause()+",msg:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
/*	
	private void dropTable(SQLiteDatabase db,String sql){
		try{
			db.execSQL(sql);
		}
		catch(SQLiteException e){
			Log.e(TAG, "exception:"+sql);
			e.printStackTrace();
		}
		
	}
	
	
	private void CreateTables(SQLiteDatabase db,int version){
		
		String sql = null ;
		String tablecolumns = null;
		String[] table = null;
	
		int length = Data.Db_Table_Object.length;
		int i = 0, j = 0, l =0 ;
		while(i<length){
			
			table = (String[])Data.Db_Table_Object[i];
			
			tablecolumns = "(";
			
			j = 0;
			l = table.length;
			while (j<l){
				tablecolumns += table[j];
				tablecolumns += " TEXT";
				j++;
				if(j<l)
					tablecolumns += ",";
			}
	
			tablecolumns += ");";
		
			sql = "CREATE TABLE " + Data.Db_Table_Name[version][i] + tablecolumns;
			
			try{
				db.execSQL(sql);
			}
			catch(SQLiteException e){
				Log.e("DB_ERROR", e.toString());
			}
			i++;
		
		}
	
		
	}

	private void DropTables(SQLiteDatabase db,int version){
		
		
		String sql = null ;
	
		int length = Data.Db_Table_Name[version].length;
		int i = 0, j = 0, l =0 ;
		while(i<length){
			
		
			sql = "DROP TABLE " + Data.Db_Table_Name[version][i];
			
			try{
				db.execSQL(sql);
			}
			catch(SQLiteException e){
				Log.e("DB_ERROR", e.toString());
			}
			i++;
		
		}

	}

	private void InitDeviceRegister(SQLiteDatabase db){
		
	}

	private void InitDummyShuttleDispatch(SQLiteDatabase db){
		
	}

*/	
}
