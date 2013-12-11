/**
 * 
 */
package com.android.aid;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class DataProvider extends ContentProvider {

	private final static String TAG = "DataProvider";
	
	SQLiteDatabase db = null;
	private DatabaseHelper dbHelper = null;
	private final  static byte[] _dblock = new byte[0]; 
	
	/**
	 * 
	 */
	public DataProvider() {
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int rows = -1;
		
		String args = "";

		synchronized (_dblock){

		
			try{
				db = dbHelper.getWritableDatabase();
				
	
				if(selectionArgs!=null){
					int l = selectionArgs.length;
					for (int i = 0; i<l; i++){
						args += " "+selectionArgs[i];
					}
					
				}
				
					
//				Log.i(TAG, "delete: "+uri.getPath() + " where "+selection+" Args "+args);
			
				rows = db.delete(uri.getLastPathSegment(), selection, selectionArgs);
				
				
			}
			
			catch(SQLiteException e){
				
				Log.i(TAG, "cause:"+e.getCause()+",msg"+e.getMessage());
			
				e.printStackTrace();
			}
			finally{
//				db.close();
			}
				
			
		}
		
		Log.i(TAG, "delete: "+uri.getPath() + " where "+selection+" Args "+args + " rows " + rows);
			
		return rows;
		
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		long index = -1;
		
		synchronized (_dblock){
				
				try{
		
					db = dbHelper.getWritableDatabase();
//					Log.i(TAG, "insert: "+uri.getPath());
					index = db.insert(uri.getLastPathSegment(), null, values);
//					db.close();
				}
				catch(SQLiteException e){
					Log.i(TAG, "cause:"+e.getCause()+",msg"+e.getMessage());
					
					e.printStackTrace();
//					db.close();
				}
				finally{
//					if(db!=null)
//						db.close();
				}
				
			
		}
		
		if(index==-1)
			
			return null;
		
		else{
			Log.i(TAG, "insert: "+uri.getLastPathSegment()+" "+index);
			return uri.buildUpon().appendEncodedPath("/"+index).build();
			
		}
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		
		String dbName = Data.DB_DATABASE;
		int dbVersion = Data.DB_VERSION;
		CursorFactory factory = null;
		dbHelper = new DatabaseHelper(getContext(),dbName,factory,dbVersion);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Cursor cursor = null;

		try{
			String limit = uri.getQuery();
			
			
			db = dbHelper.getReadableDatabase();
			String groupBy = null, having = null;

//			Log.i(TAG, "query: "+uri.getPath());
			cursor = db.query(uri.getLastPathSegment(), projection, selection, selectionArgs, groupBy, having, sortOrder,limit);
			
			int rows = 0, columns = 0;
			if(cursor!=null){
				rows = cursor.getCount();
				columns = cursor.getColumnCount();
			}
			if(rows>0 && columns>0){
//				Log.i(TAG, "query: "+rows+" rows,"+columns+" columns");
				
			}

		}
		catch(SQLiteException e){
			Log.i(TAG, "cause:"+e.getCause()+",msg"+e.getMessage());
			
			e.printStackTrace();
		}
		finally{
//			db.close();
		}
		
		return cursor;
		
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int rows = -1;
		synchronized (_dblock){
			
			try{
				db = dbHelper.getWritableDatabase();
//						Log.i(TAG, "update: "+uri.getPath());
				rows = db.update(uri.getLastPathSegment(), values, selection, selectionArgs);
			}
			catch(SQLiteException e){
				Log.i(TAG, "cause:"+e.getCause()+",msg"+e.getMessage());
				
				e.printStackTrace();
			}
			finally{
//				db.close();
			}
			
		}
		Log.i(TAG, "update: "+ uri.getLastPathSegment() +" rows "+ rows);
		
		return rows;
	}

}
