/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.ActivityManager.RecentTaskInfo;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


/**
 * @author wangpeifeng
 *
 */
public class DataProxy {
	
	private static final String TAG = "DataProxy";
	
	private Context context;
	private ContentResolver resolver;

	public static HashMap<String,ThreadDownload> mapThreadDownload;

	public DataProxy(Context context){
		this.context = context;
		resolver = context.getContentResolver();
		
		if(mapThreadDownload==null){
			mapThreadDownload = new HashMap<String,ThreadDownload>();
		}
		
	}
	
	public void insertLauchReporter(String packageName, String launch_by_me){
		
		
		if(isPackageInRepo(packageName)){
			Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
					+Data.URI_APP_INSTALLED);
		
			if(this.isAppInDatabase(uri, packageName)){

				uri = new Uri.Builder().build().parse(Data.URI_HEADER
						+Data.URI_LAUNCH_REPORTER);

				AppInfoData info = queryAppInstalled(packageName);
			
				ContentValues values = new ContentValues();
		
				values.put(Data.DB_COLUMN_APP_PACKAGE, info.getPackage());
		
				values.put(Data.DB_COLUMN_APP_LABEL, info.getLabel());
		
				values.put(Data.DB_COLUMN_APP_VERSION_CODE, info.getVersionCode());
		
				values.put(Data.DB_COLUMN_LAUNCH_BY_ME, launch_by_me);
		
				values.put(Data.DB_COLUMN_LAUNCH_TIME, Common.StampToyyyyMMddHHmmss(System.currentTimeMillis()));
			
				resolver.insert(uri, values);

			}
			
		}		
	}

	public AppInfoData queryLaunchReporter(){
		
		AppInfoData appInfoData = null;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_LAUNCH_REPORTER
				+Data.URI_LIMIT_1
				);
		
		String[] table = Data.Db_Table_Launch_Reporter;
		String[] projection = new String[table.length];
		int i=0;
		int l=table.length;
		while (i<l){
			projection[i] = table[i];
			i++;
		}
		
		String sortOrder = null;
		String selection = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
				
			appInfoData = new AppInfoData(context);

			appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
			appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
			appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));

			appInfoData.setLaunchByMe(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_LAUNCH_BY_ME)));
			appInfoData.setLaunchTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_LAUNCH_TIME)));
			
		}	
		if(cursor!=null)
			cursor.close();
		
		
		return appInfoData;
		
	}
	
	public void deleteLaunchReporter(AppInfoData appInfoData){
		
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_LAUNCH_REPORTER
				);
		
		String where = Data.DB_COLUMN_LAUNCH_TIME+"=?"+" AND "+Data.DB_COLUMN_APP_PACKAGE+"=?";
		String[] selectionArgs = new String[2];
		selectionArgs[0] = appInfoData.getLaunchTime();
		selectionArgs[1] = appInfoData.getPackage();
		
		resolver.delete(uri,where, selectionArgs);
		
	}
	
	
	
	public void insertInstallReporter(String packageName, String action){
		
		Log.i(TAG, "insertInstallReporter:"+packageName+" "+action);
		
		String install_action=null;
		
		if(Intent.ACTION_PACKAGE_ADDED.equals(action)){
			install_action = Data.DB_VALUE_INSTALL_ADD;
		}
		else if(Intent.ACTION_PACKAGE_REMOVED.equals(action)){
			install_action = Data.DB_VALUE_INSTALL_REMOVE;
		}
		else if(Intent.ACTION_PACKAGE_REPLACED.equals(action)){
			install_action = Data.DB_VALUE_INSTALL_REPLACE;
		}
		
		
		AppInfoData info = new AppInfoData(context);
		
		info.setPackage(packageName);
		info.setInstallAction(action);
		info.setInstallActionTime(Common.StampToyyyyMMddHHmmss(System.currentTimeMillis()));
		
		try {
						
			PackageManager packageManager =context.getPackageManager();  
			
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			
			info.setLabel(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());

			info.setVersionCode(packageInfo.versionCode);
			
				
					
		} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
		//				e.printStackTrace();
			AppInfoData infoData = queryAppInstalled(packageName);	
			info.setLabel(infoData.getLabel());
			info.setVersionCode(infoData.getVersionCode());
					
		}
		

		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
			
					+Data.URI_INSTALL_REPORTER);
		
			
		
		ContentValues values = new ContentValues();
		
		values.put(Data.DB_COLUMN_APP_PACKAGE, info.getPackage());
		
		values.put(Data.DB_COLUMN_APP_LABEL, info.getLabel());
		
		values.put(Data.DB_COLUMN_APP_VERSION_CODE, info.getVersionCode());
		
		values.put(Data.DB_COLUMN_INSTALL_ACTION, install_action);
		
		values.put(Data.DB_COLUMN_INSTALL_ACTION_TIME, info.getInstallActionTime());
			

		resolver.insert(uri, values);
		
	}

	public AppInfoData queryInstallReporter(){
		
		AppInfoData appInfoData = null;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_INSTALL_REPORTER
				+Data.URI_LIMIT_1
				);
		
		String[] projection = new String[Data.Db_Table_Install_Reporter.length];
		int i=0;
		int l=Data.Db_Table_Install_Reporter.length;
		while (i<l){
			projection[i] = Data.Db_Table_Install_Reporter[i];
			i++;
		}
		
		String sortOrder = null;
		String selection = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
				
			appInfoData = new AppInfoData(context);

			appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
			appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
			appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));

			appInfoData.setInstallAction(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_INSTALL_ACTION)));
			appInfoData.setInstallActionTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_INSTALL_ACTION_TIME)));
			
		}	
		if(cursor!=null)
			cursor.close();
		
		
		return appInfoData;
		
	}
	
	public void deleteInstallReporter(AppInfoData appInfoData){
		
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_INSTALL_REPORTER
				);
		
		String where = Data.DB_COLUMN_INSTALL_ACTION_TIME+"=?"+" AND "+Data.DB_COLUMN_APP_PACKAGE+"=?";
		String[] selectionArgs = new String[2];
		selectionArgs[0] = appInfoData.getInstallActionTime();
		selectionArgs[1] = appInfoData.getPackage();
		
		resolver.delete(uri,where, selectionArgs);
		
	}
	

	private void insertDownloadReporter(AppInfoData appInfoData){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_DOWNLOAD_REPORTER);
	
		
			 ContentValues values = new ContentValues();
			 values.put(Data.DB_COLUMN_APP_PACKAGE, appInfoData.getPackage());
			 values.put(Data.DB_COLUMN_APP_LABEL, appInfoData.getLabel());
			 values.put(Data.DB_COLUMN_APP_VERSION_CODE, appInfoData.getVersionCode());
			 values.put(Data.DB_COLUMN_APP_URL, appInfoData.getUrl());
			 values.put(Data.DB_COLUMN_APP_DOWNLOAD_START_TIME, appInfoData.getStartTime());
			 values.put(Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME, Common.StampToyyyyMMddHHmmss(System.currentTimeMillis()));
			 	
			 resolver.insert(uri, values);
	
		
	}

	public AppInfoData queryDownloadReporter(){
		
		AppInfoData appInfoData = null;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_DOWNLOAD_REPORTER
				+Data.URI_LIMIT_1
				);
		
		String[] projection = new String[Data.Db_Table_Download_Reporter.length];
		int i=0;
		int l=Data.Db_Table_Download_Reporter.length;
		while (i<l){
			projection[i] = Data.Db_Table_Download_Reporter[i];
			i++;
		}
		
		String sortOrder = null;
		String selection = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
				
			appInfoData = new AppInfoData(context);

			appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
			appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
			appInfoData.setUrl(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_URL)));
			appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));

			appInfoData.setStartTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_DOWNLOAD_START_TIME)));
			appInfoData.setStopTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME)));
					
		}	
		if(cursor!=null)
			cursor.close();
		
		
		return appInfoData;
		
	}
	
	public void deleteDownloadReporter(AppInfoData appInfoData){
		
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_DOWNLOAD_REPORTER
				);
		
		String where = Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME+"=?"+" AND "+Data.DB_COLUMN_APP_PACKAGE+"=?";
		String[] selectionArgs = new String[2];
		selectionArgs[0] = appInfoData.getStopTime();
		selectionArgs[1] = appInfoData.getPackage();
		
		resolver.delete(uri,where, selectionArgs);
		
	}
	

	private void updateCategoryManager(String resp){

//		Log.i(TAG, "updateCategoryManager");
		
		if(resp!=null){
	//		Log.i(TAG, "resp != null");
				
			try {
				JSONObject obj = new JSONObject(resp);
				
				
				boolean api_resp = obj.getBoolean(Data.API_JSON_RESPONSE);
				if(api_resp){
//					Log.i(TAG, "api_resp == true");
							
					JSONArray jsonArray = obj.getJSONArray(Data.API_JSON_CATEGORY_ARRAY);
				
					int size = jsonArray.length();
//					Log.i(TAG, "size = "+size);
				
					
					ArrayList<CategoryInfoData> listInfo = new ArrayList<CategoryInfoData>();
					
					for(int i=0;i<size;i++){
						
						JSONObject jsonInfo = jsonArray.getJSONObject(i);
						
						CategoryInfoData infoData = new CategoryInfoData();
					
						infoData.setSerial(jsonInfo.getString(Data.DB_COLUMN_CATEGORY_SERIAL));
						infoData.setCategory(jsonInfo.getString(Data.DB_COLUMN_APP_CATEGORY));
						infoData.setDir(jsonInfo.getString(Data.DB_COLUMN_CATEGORY_DIR));
						infoData.setStatus(jsonInfo.getString(Data.DB_COLUMN_CATEGORY_STATUS));
						infoData.setMapping(jsonInfo.getString(Data.DB_COLUMN_CATEGORY_MAPPING));
						infoData.setStamp(jsonInfo.getString(Data.DB_COLUMN_CATEGORY_STAMP));
					
						listInfo.add(infoData);
						Log.i(TAG, "add "+jsonInfo.getString(Data.DB_COLUMN_APP_CATEGORY)+",serial:"+jsonInfo.getString(Data.DB_COLUMN_CATEGORY_SERIAL));
					
					}	
				
					if(listInfo.size()>0){
						updateCategoryDatabase(listInfo);
					}
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		}
	}
	
	
	
	public void updateCategoryManager(){
		
		String stamp = queryCategoryStamp();
		
		HttpProxy httpproxy = new HttpProxy(context);
		String resp = httpproxy.postCategoryQuery(stamp);
		updateCategoryManager(resp);
	
	}
	
	
	
	private void updateCategoryDatabase(ArrayList<CategoryInfoData> listInfo){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
			+Data.URI_CATEGORY_MANAGER);

		this.setDataTableUpdateFlag(uri);
				
		
		Iterator<CategoryInfoData> l = listInfo.iterator();
		 while (l.hasNext()) {
			 CategoryInfoData info = (CategoryInfoData) l.next();
		
			 ContentValues values = new ContentValues();
			 
			 values.put(Data.DB_COLUMN_CATEGORY_SERIAL, Integer.parseInt(info.getSerial()));
			 values.put(Data.DB_COLUMN_APP_CATEGORY, info.getCategory());
			 values.put(Data.DB_COLUMN_CATEGORY_DIR, info.getDir());
			 values.put(Data.DB_COLUMN_CATEGORY_STATUS, info.getStatus());
			 values.put(Data.DB_COLUMN_CATEGORY_MAPPING, info.getMapping());
			 values.put(Data.DB_COLUMN_CATEGORY_STAMP, info.getStamp());
				
			 //resolver.insert(uri, values);
//			Log.i(TAG, "insert "+info.getCategory());
			 if(!isCategoryInDatabase(uri,info.getCategory())){
				 resolver.insert(uri, values);
			 }
			 else{
				 
				 values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_NULL);

				 String where = Data.DB_COLUMN_APP_CATEGORY + "=?";
				 String[] selectionArgs = new String[1];
				 selectionArgs[0] = info.getCategory();
				 
				 resolver.update(uri, values, where, selectionArgs);
								 
			 }
			
				
		 }
		 
		this.cleanDataTable(uri);
			
		this.clearDataTableUpdateFlag(uri);
			
	
		
	}
	
	public ArrayList<CategoryInfoData> queryCategory(ArrayList<CategoryInfoData> list){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_CATEGORY_MANAGER
				);
		
		String[] table = Data.Db_Table_Category_Manager;
		String[] projection = new String[table.length];
		int i=0;
		int l=table.length;
		while (i<l){
			projection[i] = table[i];
			i++;
		}

		String selection = null ;
		String[] selectionArgs = null;
		
		String sortOrder =  Data.DB_COLUMN_CATEGORY_SERIAL;
		//selection = Data.DB_COLUMN_APP_CATEGORY+"=?" ;
		//selectionArgs = new String[1];
		//selectionArgs[0] = category;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				CategoryInfoData info = new CategoryInfoData();

				info.setCategory(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_CATEGORY)));
				info.setIcon(context.getResources().getDrawable(Common.categoryToImageID(info.getCategory())));
				Log.i(TAG,"serial:"+cursor.getInt(getProjectionIndex(projection,Data.DB_COLUMN_CATEGORY_SERIAL))//);
						+",category:"+cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_CATEGORY)));					
				list.add(info);
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		
		
		return list;
		
	}

	
	public String queryCategoryStamp(){
		
		String stamp = null;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_CATEGORY_MANAGER
				+Data.URI_LIMIT_1
				);
		
		
		String[] projection = new String[1];
		projection[0] = Data.DB_COLUMN_CATEGORY_STAMP;
		
		String selection = null;
		String sortOrder = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				stamp = cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_CATEGORY_STAMP));
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		
		
		return stamp;
		
	}
	
	public String queryCategoryDir(String category){
		
		String result = null;
		
		if(Data.DB_VALUE_CATEGORY_ICON.equals(category)){
			return Data.DB_VALUE_CATEGORY_DIR_ICON;
		}
		else if(Data.DB_VALUE_CATEGORY_ANDROIDAID.equals(category)){
			return Data.DB_VALUE_CATEGORY_DIR_ANDROIDAID;
		}
		// change to original file dir
		else{
			return Data.DB_VALUE_CATEGORY_DIR_ONLINE;
		}
		
	/*	
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_CATEGORY_MANAGER
				+Data.URI_LIMIT_1
				);
		
		
		String[] projection = new String[1];
		projection[0] = Data.DB_COLUMN_CATEGORY_DIR;
		
		String selection = Data.DB_COLUMN_APP_CATEGORY + "=?";
		String sortOrder = null;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = category;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				result = cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_CATEGORY_DIR));
			
			}while(cursor.moveToNext());
		}	
		else{
			initCategoryDir();
		}
		if(cursor!=null)
			cursor.close();
		
		
		
		return result;
	*/	
	}
	
	private void initCategoryDir(){
		
		this.updateCategoryManager(FileProxy.getCategoryAssets(context));
		
	}

	
	
/*	
	public void syncApiVersion(){
		HttpProxy httpproxy = new HttpProxy(context);
		httpproxy.updateConfigFile();
		
	}
*/
	
	public boolean newVersionReady(AppInfoData infoData){
		
		boolean flag = false;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADED
				+Data.URI_LIMIT_1
				);
		
		String[] table = Data.Db_Table_App_Downloaded;
		String[] projection = new String[table.length];
		int i=0;
		int l=table.length;
		while (i<l){
			projection[i] = table[i];
			i++;
		}

		String selection = Data.DB_COLUMN_APP_PACKAGE + "=?";
		String sortOrder = Data.DB_COLUMN_APP_VERSION_CODE + " DESC";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = Data.DB_VALUE_PACKAGE_ANDROIDAID;
		
		Cursor cursor = null;
		
		try{
			cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
			int rows = 0, columns = 0;
			if(cursor!=null){
				rows = cursor.getCount();
				columns = cursor.getColumnCount();
			}
			
			if(rows>0 && columns>0){
		
				cursor.moveToFirst();
				
				int curVer = context.getPackageManager().getPackageInfo(Data.DB_VALUE_PACKAGE_ANDROIDAID, 0).versionCode;
				int newVer = Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE)));
//				Log.i(TAG, "newVer = "+newVer+",curVer = "+curVer);
				
				if(newVer>curVer){
				
					infoData.setPackage(Data.DB_VALUE_PACKAGE_ANDROIDAID);
				
					infoData.setLabel(context.getResources().getString(R.string.app_name));
				
					infoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				
					infoData.setFileName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE)));

					infoData.setPath(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_PATH)));
					
//					Log.i(TAG, "newVersion "+infoData.getPath());
					File file = new File(infoData.getPath());
					if(file.exists()){
						flag = true;
					}
				}
			}
			
			if(cursor!=null)
				cursor.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public void updateVersionRepository(){

		HttpProxy httpproxy = new HttpProxy(context);
		
		String resp = httpproxy.postVersionRepoQuery();
//		Log.i(TAG, "updateVersionRepository");
		
		if(resp!=null){
				
//			Log.i(TAG, "updateVersionRepository resp !=null ");
			
			try {

				JSONObject jsonInfo = new JSONObject(resp);
				
				if(jsonInfo!=null){

//					Log.i(TAG, "updateVersionRepository jsonInfo !=null ");

					boolean api_resp = jsonInfo.getBoolean(Data.API_JSON_RESPONSE);
				
					if(api_resp){
							
						
						AppInfoData infoData = new AppInfoData(context);
						
						infoData.setCategory(Data.DB_VALUE_CATEGORY_ANDROIDAID);
						
						infoData.setPackage(Data.DB_VALUE_PACKAGE_ANDROIDAID);
						
						infoData.setLabel(context.getResources().getString(R.string.app_name));
						
						infoData.setVersionCode(Integer.parseInt(jsonInfo.getString(Data.DB_COLUMN_VERSION_CODE)));
						
						infoData.setFileName(jsonInfo.getString(Data.DB_COLUMN_VERSION_FILE));
	
						try {
							if(infoData.getVersionCode() > context.getPackageManager().getPackageInfo(Data.DB_VALUE_PACKAGE_ANDROIDAID, 0).versionCode ){
								startDownloading(infoData,Data.DB_VALUE_YES);
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
							
										
					}	
				
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		}
		
		
	}
	
	public  void updateAppMyDatabase(ArrayList<AppInfoData> applist){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_MY);

		this.setDataTableUpdateFlag(uri);
		
	
		//Uri.Builder builder = new Uri.Builder();
		
		Iterator<AppInfoData> l = applist.iterator();
		 while (l.hasNext()) {
			 AppInfoData info = (AppInfoData) l.next();
			 if(info.getAppMy()){
				 ContentValues values = new ContentValues();
				 values.put(Data.DB_COLUMN_APP_PACKAGE, info.getPackage());
				 values.put(Data.DB_COLUMN_APP_LABEL, info.getLabel());
				 values.put(Data.DB_COLUMN_APP_CATEGORY, info.getAppMyCategory());
				 
				 //resolver.insert(uri, values);
				 if(!isAppInDatabase(uri,info.getPackage())){
					 resolver.insert(uri, values);
				 }
				 else{
				 
					 values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_NULL);

					 String where = Data.DB_COLUMN_APP_PACKAGE + "=?";
					 String[] selectionArgs = new String[1];
					 selectionArgs[0] = info.getPackage();
				 
					 resolver.update(uri, values, where, selectionArgs);
								 
				 }
			 }

		 }
		 
		this.cleanDataTable(uri);
		
		//this.clearDataTableUpdateFlag(uri);
		
		
	}
	public ArrayList<AppInfoData> queryAppMy(ArrayList<AppInfoData> listApps,String category){
		
//		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
	
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_MY
				);
		
		String[] table = Data.Db_Table_App_My;
		String[] projection = new String[table.length];
		int i=0;
		int l=table.length;
		while (i<l){
			projection[i] = table[i];
			i++;
		}

		String selection = null ;
		String[] selectionArgs = null;
		
		String sortOrder =  Data.DB_COLUMN_APP_LABEL;
		if(!Data.DB_VALUE_CATEGORY_ALL.equals(category)){
			selection = Data.DB_COLUMN_APP_CATEGORY+"=?" ;
			selectionArgs = new String[1];
			selectionArgs[0] = category;
			
		}
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfoData = new AppInfoData(context);

				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setAppMyCategory(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_CATEGORY)));
				appInfoData.setLocation(Data.DB_VALUE_LOCATION_INSTALLED);
				try {
					appInfoData.setIcon(context.getPackageManager().getApplicationIcon(appInfoData.getPackage()));
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
									
				listApps.add(appInfoData);
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		
		
		return listApps;
		
	}
	/*
	public void startIconDownload(AppInfoData appInfoData,String silence){

		//appInfoData.addPathSuffix();
		
		if(!isAppDownloading(appInfoData)){
			
		
			Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING);

			
			ContentValues values = new ContentValues();
		 
			values.put(Data.DB_COLUMN_APP_PACKAGE, appInfoData.getPackage());
			
			values.put(Data.DB_COLUMN_APP_LABEL, appInfoData.getLabel());
			 		 
			values.put(Data.DB_COLUMN_APP_VERSION_CODE, appInfoData.getVersionCode());
		 
			values.put(Data.DB_COLUMN_APP_FILE, appInfoData.getFileName());
		 
//			values.put(Data.DB_COLUMN_APP_URL, appInfoData.getUrl());
			String root = FileProxy.getDownloadRootUrl(context);
			String dir = queryCategoryDir(appInfoData.getCategory());
			String url = root+dir+appInfoData.getFileName();
			values.put(Data.DB_COLUMN_APP_URL,url);
			
			//appInfoData.addPathSuffix();
			appInfoData.setSavPath();
			values.put(Data.DB_COLUMN_APP_FILE_PATH, appInfoData.getPath());
		
		
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_STATE, Data.DB_VALUE_DOWNLOAD_DOWNLOADING);
		 
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_SILENCE, silence);
			 
//			values.put(Data.DB_COLUMN_APP_FILE£ßLENGTH, ""+0);
		
//			values.put(Data.DB_COLUMN_APP_DOWNLOAD_LENGTH, ""+0);
		
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_START_TIME, Common.StampToyyyyMMddHHmmss(System.currentTimeMillis()));
		
			resolver.insert(uri, values);

			Log.i(TAG, "start download "+appInfoData.getFileName());
		}
			 
				
	}
	
	*/
	
	public void startDownloading(AppInfoData appInfoData,String silence){

		//appInfoData.addPathSuffix();
		
		if(!isAppDownloading(appInfoData)){
			
		
			Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING);

			
			ContentValues values = new ContentValues();
		 
			values.put(Data.DB_COLUMN_APP_PACKAGE, appInfoData.getPackage());
			
			values.put(Data.DB_COLUMN_APP_LABEL, appInfoData.getLabel());
			 		 
			values.put(Data.DB_COLUMN_APP_VERSION_CODE, appInfoData.getVersionCode());
		 
			values.put(Data.DB_COLUMN_APP_FILE, appInfoData.getFileName());
		 
//			values.put(Data.DB_COLUMN_APP_URL, appInfoData.getUrl());
			String root = FileProxy.getDownloadRootUrl(context);
			String dir = queryCategoryDir(appInfoData.getCategory());
			String url = root+dir+appInfoData.getFileName();
			values.put(Data.DB_COLUMN_APP_URL,url);
			
			//appInfoData.addPathSuffix();
			appInfoData.setSavPath();
			values.put(Data.DB_COLUMN_APP_FILE_PATH, appInfoData.getPath());
		
		
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_STATE, Data.DB_VALUE_DOWNLOAD_DOWNLOADING);
		 
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_SILENCE, silence);
			 
			values.put(Data.DB_COLUMN_APP_FILE_LENGTH,""+0);
		
//			values.put(Data.DB_COLUMN_APP_DOWNLOAD_LENGTH, ""+0);
		
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_START_TIME, Common.StampToyyyyMMddHHmmss(System.currentTimeMillis()));
		
			resolver.insert(uri, values);

			Log.i(TAG, "start download "+appInfoData.getFileName());
		}
		
		Common.broadcastStartDownloading(context);
			 
				
	}
	
	public void updateAppDownloadFileLength(AppInfoData appInfoData){
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING);
		ContentValues values = new ContentValues();
		values.put(Data.DB_COLUMN_APP_FILE_LENGTH, ""+appInfoData.getFileLength());

		String where = Data.DB_COLUMN_APP_FILE_PATH + "=?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = appInfoData.getPath();
		

		resolver.update(uri, values, where, selectionArgs);

	}
	/*
	public void updateAppDownloadSavedLength(AppInfoData appInfoData){
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING);
		ContentValues values = new ContentValues();
		values.put(Data.DB_COLUMN_APP_DOWNLOAD_LENGTH, ""+appInfoData.getDownloadedLength());

		String where = Data.DB_COLUMN_APP_FILE_PATH + "=?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = appInfoData.getPath();
		

		resolver.update(uri, values, where, selectionArgs);

	}
	*/
	public void finishDownloading(AppInfoData appInfoData){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING);
		
		appInfoData.setStopTime(Common.StampToyyyyMMddHHmmss(System.currentTimeMillis()));
		
		ContentValues values = new ContentValues();
		values.put(Data.DB_COLUMN_APP_DOWNLOAD_STATE, Data.DB_VALUE_DOWNLOAD_DOWNLOADED);
		values.put(Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME, appInfoData.getStopTime());
		
		String where = Data.DB_COLUMN_APP_PACKAGE + "=?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = appInfoData.getPackage();
		

		resolver.update(uri, values, where, selectionArgs);
		String path = appInfoData.getPath();
		File file = new File(path);
		if(file.exists()){
//			appInfoData.removePathSuffix();
			path = path.substring(0, path.length()-Data.DB_VALUE_DOWNLOADING_SUFFIX.length());
			file.renameTo(new File(path));
			appInfoData.setPath(path);
		}
		
//		Log.i(TAG, appInfoData.getFileName().substring(appInfoData.getFileName().length()-4).toLowerCase());
		if(".apk".equals(appInfoData.getFileName().substring(appInfoData.getFileName().length()-".apk".length()).toLowerCase())){

			insertAppDownloadDatabase(appInfoData);
			
			insertDownloadReporter(appInfoData);
			
			Common.broadcastDownloadReporter(context);
			
		}
		
		
		
		Log.i(TAG, "finished download " + appInfoData.getFileName());

	}
	
	public void cancelDownloading(AppInfoData appInfoData){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING);
		ContentValues values = new ContentValues();
		if(Data.DB_VALUE_DOWNLOAD_PAUSE.equals(appInfoData.getStatus())){
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_STATE, Data.DB_VALUE_DOWNLOAD_PAUSE);
		}
		else if(Data.DB_VALUE_DOWNLOAD_CANCEL.equals(appInfoData.getStatus())){
			values.put(Data.DB_COLUMN_APP_DOWNLOAD_STATE, Data.DB_VALUE_DOWNLOAD_CANCEL);
		}
		
		String where = Data.DB_COLUMN_APP_PACKAGE + "=?";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = appInfoData.getPackage();
		

		resolver.update(uri, values, where, selectionArgs);
		
		Log.i(TAG, "cancel download " + appInfoData.getFileName());

	}
	/*
	public AppInfoData queryAppDownloadlength(AppInfoData appInfoData){
		
		
		//appInfoData.setDownloadedLength(-1);
		
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING
				);
		String[] projection = new String[Data.Db_Table_App_Downloading.length];
		int i=0;
		int l=Data.Db_Table_App_Downloading.length;
		while (i<l){
			projection[i] = Data.Db_Table_App_Downloading[i];
			i++;
		}
	
		String selection = Data.DB_COLUMN_APP_DOWNLOAD_STATE+"=?" + " AND " + Data.DB_COLUMN_APP_FILE_PATH+"=?";
		String sortOrder = null;
		String[] selectionArgs = new String[2];
		selectionArgs[0] = Data.DB_VALUE_DOWNLOAD_DOWNLOADING;
		selectionArgs[1] = appInfoData.getPath();
		
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				appInfoData.setDownloadedLength(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_DOWNLOAD_LENGTH))));
				appInfoData.setFileLength(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE£ßLENGTH))));
//				appInfoData.setPath(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_PATH)));
			}while(cursor.moveToNext());
		}
		else{
			appInfoData.setDownloadedLength(-1);
		}

		if(cursor!=null)
			cursor.close();
		
		
//		Log.i(TAG, "queryAppDownloadlength = "+ appInfoData.getDownloadedLength() );
		
		return appInfoData;
		
	}
	
	*/
	
	public ArrayList<AppInfoData> queryAppDownloading(ArrayList<AppInfoData> listApps,int  type){
		
		listApps = new ArrayList<AppInfoData>();
	
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING
				);
		
		String[] projection = new String[Data.Db_Table_App_Downloading.length];
		int i=0;
		int l=Data.Db_Table_App_Downloading.length;
		while (i<l){
			projection[i] = Data.Db_Table_App_Downloading[i];
			i++;
		}
		String sortOrder = null;
		String selection = null;
		String[] selectionArgs = null;
		switch(type){
		case Data.DB_VALUE_QUERY_DOWNLOADING:// downloading , not silence
			selection = Data.DB_COLUMN_APP_DOWNLOAD_STATE+"=?" 
					+ " AND " + Data.DB_COLUMN_APP_DOWNLOAD_SILENCE + " = ?";
			selectionArgs = new String[2];
			selectionArgs[0] = Data.DB_VALUE_DOWNLOAD_DOWNLOADING;
			selectionArgs[1] = Data.DB_VALUE_NO;
			break;
			
		case Data.DB_VALUE_QUERY_DOWNLOADING_PAUSE:// downloading , pause, not silence
		
			selection = "("+ Data.DB_COLUMN_APP_DOWNLOAD_STATE+" = ? " + " OR " + Data.DB_COLUMN_APP_DOWNLOAD_STATE+" = ? )"
					+ " AND " + Data.DB_COLUMN_APP_DOWNLOAD_SILENCE + " = ?";
			selectionArgs = new String[3];
			selectionArgs[0] = Data.DB_VALUE_DOWNLOAD_DOWNLOADING;
			selectionArgs[1] = Data.DB_VALUE_DOWNLOAD_PAUSE;
			selectionArgs[2] = Data.DB_VALUE_NO;
		
			break;
		case Data.DB_VALUE_QUERY_DOWNLOADING_PAUSE_SILENCE:// downloading , pause, silence
			
			selection = "("+ Data.DB_COLUMN_APP_DOWNLOAD_STATE+" = ? " + " OR " + Data.DB_COLUMN_APP_DOWNLOAD_STATE+" = ? )";
			selectionArgs = new String[2];
			selectionArgs[0] = Data.DB_VALUE_DOWNLOAD_DOWNLOADING;
			selectionArgs[1] = Data.DB_VALUE_DOWNLOAD_PAUSE;
		
			break;
			
		case Data.DB_VALUE_QUERY_DOWNLOADING_SILENCE:// downloading , silence
			
			selection = Data.DB_COLUMN_APP_DOWNLOAD_STATE+" = ? ";
			selectionArgs = new String[1];
			selectionArgs[0] = Data.DB_VALUE_DOWNLOAD_DOWNLOADING;
			
			break;
			
		}
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfoData = new AppInfoData(context);

				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setFileName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE)));
				appInfoData.setUrl(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_URL)));
				appInfoData.setPath(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_PATH)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfoData.setFileLength(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_LENGTH))));
//				appInfoData.setDownloadedLength(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_DOWNLOAD_LENGTH))));
				
				appInfoData.setStatus(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_DOWNLOAD_STATE)));
				appInfoData.setStartTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_DOWNLOAD_START_TIME)));
					
				listApps.add(appInfoData);
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
//		Log.i(TAG, "queryDownloading rows="+rows+", columns = "+columns);
		
		return listApps;
		
	}
	
	public void cancelDownloading(ArrayList<AppInfoData> listApps){
		

		Iterator iterator = listApps.iterator();
		
		while(iterator.hasNext()){
			AppInfoData infoData = (AppInfoData) iterator.next();
			if(infoData.getDownloadStop()){
				cancelDownloading(infoData);

				File file = new File(infoData.getPath());
				if(file.exists()){
					file.delete();
				}

			}
		}
	}
	

	public ArrayList<AppInfoData> queryAppInstalledNotInRepo(ArrayList<AppInfoData> listApps){
		
//		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
	
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_INSTALLED
				);
		String[] table = Data.Db_Table_App_Installed;
		String[] projection = new String[table.length];
		int i=0;
		int l=table.length;
		while (i<l){
			projection[i] = table[i];
			i++;
		}

		String selection = null;
		String sortOrder = Data.DB_COLUMN_APP_LABEL;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfoData = new AppInfoData(context);

				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfoData.setVersionName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_NAME)));
				appInfoData.setInstallTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME)));
				appInfoData.setUpdateTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LATEST_UPDATE_TIME)));
				appInfoData.setLocation(Data.DB_VALUE_LOCATION_INSTALLED);
				
				try {
					appInfoData.setIcon(context.getPackageManager().getApplicationIcon(appInfoData.getPackage()));
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!isPackageInRepo(appInfoData.getPackage())){					
					listApps.add(appInfoData);
				}
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		
		
		return listApps;
		
	}
	

	
	public ArrayList<AppInfoData> queryAppInstalled(ArrayList<AppInfoData> listApps){
		
//		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
	
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_INSTALLED
				);
		String[] table = Data.Db_Table_App_Installed;
		String[] projection = new String[table.length];
		int i=0;
		int l=table.length;
		while (i<l){
			projection[i] = table[i];
			i++;
		}

		String selection = null;
		String sortOrder = Data.DB_COLUMN_APP_LABEL;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfoData = new AppInfoData(context);

				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfoData.setVersionName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_NAME)));
				appInfoData.setInstallTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME)));
				appInfoData.setUpdateTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LATEST_UPDATE_TIME)));
				appInfoData.setLocation(Data.DB_VALUE_LOCATION_INSTALLED);
				
				try {
					appInfoData.setIcon(context.getPackageManager().getApplicationIcon(appInfoData.getPackage()));
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
									
				listApps.add(appInfoData);
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		
		
		return listApps;
		
	}
	

	public AppInfoData queryAppInstalled(String packageName){
		
		AppInfoData appInfoData = null;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_INSTALLED
				);
		String[] table = Data.Db_Table_App_Installed;
		String[] projection = new String[table.length];
		int i=0;
		int l=table.length;
		while (i<l){
			projection[i] = table[i];
			i++;
		}

		String selection = Data.DB_COLUMN_APP_PACKAGE+"=?"
				;
		String sortOrder = null;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = packageName;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				appInfoData = new AppInfoData(context);

				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfoData.setVersionName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_NAME)));
				appInfoData.setInstallTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME)));
				appInfoData.setUpdateTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LATEST_UPDATE_TIME)));
				appInfoData.setLocation(Data.DB_VALUE_LOCATION_INSTALLED);
				
				try {
					appInfoData.setIcon(context.getPackageManager().getApplicationIcon(appInfoData.getPackage()));
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
									
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		
		
		return appInfoData;
		
	}
	

	
	public void updateAppInstalled(){
		
		Log.i(TAG, "updateAppInstalled");
		
		PackageManager packageManager =context.getPackageManager();  
		
		ArrayList<ApplicationInfo> listApp = (ArrayList<ApplicationInfo>) packageManager.getInstalledApplications(PackageManager.GET_ACTIVITIES);
		
//		Log.i(TAG, "listApp.size()="+listApp.size());
		Iterator<ApplicationInfo> iterator = listApp.iterator();
		
		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
		
		
		while(iterator.hasNext()){
			
					
			ApplicationInfo appInfo = (ApplicationInfo) iterator.next();
			
			
			Intent intent = packageManager.getLaunchIntentForPackage(    
					appInfo.packageName);  
//			Log.i(TAG, appInfo.packageName);

			if(intent!=null){
				
/*				
				String action = intent.getAction();
				Log.i(TAG, action);
				Set<String> category = intent.getCategories();
				if(category!=null){
					Iterator<String> i = category.iterator();
					while(i.hasNext()){
						String strCategory = i.next();
						Log.i(TAG, strCategory);
					}
				}
*/
				
//				if (category!=null && category.contains(Intent.CATEGORY_LAUNCHER)
//					&& Intent.ACTION_MAIN.equals(action) ){
					
					AppInfoData appInfoData = new AppInfoData(context);
					appInfoData.setPackage(appInfo.packageName);
								
					appInfoData.setLabel(packageManager.getApplicationLabel(appInfo).toString());
					PackageInfo packageInfo;
					try {
						packageInfo = packageManager.getPackageInfo(appInfo.packageName, PackageManager.GET_ACTIVITIES);
						appInfoData.setInstallTime(Common.StampToString(packageInfo.firstInstallTime));
//						Log.i(TAG, appInfo.packageName+" first install time:"+appInfoData.getInstallTime());
						appInfoData.setUpdateTime(Common.StampToString(packageInfo.lastUpdateTime));
//						Log.i(TAG, appInfo.packageName+" last update time:"+appInfoData.getUpdateTime());
						appInfoData.setVersionCode(packageInfo.versionCode);
						appInfoData.setVersionName(packageInfo.versionName);
				
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					listApps.add(appInfoData);
			
//				}	
			
			}
			
		}
		
		if(listApps.size()>0){
			updateAppInstallDatabase(listApps);
		}

	
			
	}
	
	private void updateAppInstallDatabase(ArrayList<AppInfoData> applist){
		
		Log.i(TAG, "updateAppInstallDatabase");
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_INSTALLED);

		this.setDataTableUpdateFlag(uri);
		
	
		//Uri.Builder builder = new Uri.Builder();
		
		Iterator<AppInfoData> l = applist.iterator();
		 while (l.hasNext()) {
			 
			 AppInfoData info = (AppInfoData) l.next();
		
			 ContentValues values = new ContentValues();
			 values.put(Data.DB_COLUMN_APP_PACKAGE, info.getPackage());
			 values.put(Data.DB_COLUMN_APP_LABEL, info.getLabel());
			 values.put(Data.DB_COLUMN_APP_VERSION_CODE, info.getVersionCode());
			 values.put(Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME, info.getInstallTime());
			 values.put(Data.DB_COLUMN_APP_LATEST_UPDATE_TIME, info.getUpdateTime());
			 	
			 //resolver.insert(uri, values);
			 if(!isAppInDatabase(uri,info.getPackage())){
				 resolver.insert(uri, values);
			 }
			 else{
				 
				 values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_NULL);

				 String where = Data.DB_COLUMN_APP_PACKAGE + "=?";
				 String[] selectionArgs = new String[1];
				 selectionArgs[0] = info.getPackage();
				 
				 resolver.update(uri, values, where, selectionArgs);
								 
			 }

		 }
		 
		this.cleanDataTable(uri);
		
		//this.clearDataTableUpdateFlag(uri);
		
		
	}
	
	public ArrayList<AppInfoData> queryAppDownloadedDB(){
		
		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
	
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADED
				);
		
		String[] projection = new String[Data.Db_Table_App_Downloaded.length];
		int i=0;
		int l=Data.Db_Table_App_Downloaded.length;
		while (i<l){
			projection[i] = Data.Db_Table_App_Downloaded[i];
			i++;
		}
		String sortOrder = null;
		String selection = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfoData = new AppInfoData(context);

				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setFileName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE)));
//				appInfoData.setUrl(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_URL)));
				appInfoData.setPath(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_PATH)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
//				appInfoData.setFileLength(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE£ßLENGTH))));
//				appInfoData.setDownloadedLength(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_DOWNLOAD_LENGTH))));
				
				FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
				
				listApps.add(appInfoData);
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
//		Log.i(TAG, "queryDownloading rows="+rows+", columns = "+columns);
		
		return listApps;
		
	}
	
	public ArrayList<AppInfoData> queryAppDownloaded(){
		
		
		ArrayList<String> listFile = new ArrayList<String>();
		
		//ArrayList<String> listFile = FileProxy.GetFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),
		//		"apk",true);
		
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		File file = new File(path);
		if(file.exists() && file.isDirectory()){
			FileProxy.GetFiles(listFile,Environment.getExternalStorageDirectory().getAbsolutePath(),
				"apk",true);
		}
		path = Environment.getExternalStorageDirectory().getAbsolutePath()+"2";
		file = new File(path);
		if(file.exists() && file.isDirectory()){
			FileProxy.GetFiles(listFile,Environment.getExternalStorageDirectory().getAbsolutePath(),
				"apk",true);
		}
		
		Iterator<String> iterator = listFile.iterator();
		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
		
		while(iterator.hasNext()){
			String  filename = (String) iterator.next();
			AppInfoData apkInfoData = FileProxy.getApkFileInfo(context, filename);
			if(apkInfoData != null){   
				AppInfoData appInfoData = new AppInfoData(context);
				
				appInfoData.setPackage(apkInfoData.getPackage());
	            appInfoData.setLabel(apkInfoData.getLabel());
	            appInfoData.setVersionCode(apkInfoData.getVersionCode());
	            appInfoData.setPath(filename);
	            appInfoData.setVersionName(apkInfoData.getVersionName());
	            
	            FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
	            
	            listApps.add(appInfoData);
	            
	        }      
		}	
		

		return listApps;
		
	}

	
	
	public void updateAppDownloaded(){
		
		
		ArrayList<String> listFile = new ArrayList<String>();
		//ArrayList<String> listFile = FileProxy.GetFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),
		//		"apk",true);
		
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		File file = new File(path);
		if(file.exists() && file.isDirectory()){
			FileProxy.GetFiles(listFile,Environment.getExternalStorageDirectory().getAbsolutePath(),
				"apk",true);
		}
		path = Environment.getExternalStorageDirectory().getAbsolutePath()+"2";
		file = new File(path);
		if(file.exists() && file.isDirectory()){
			FileProxy.GetFiles(listFile,Environment.getExternalStorageDirectory().getAbsolutePath(),
				"apk",true);
		}
		
		Iterator iterator = listFile.iterator();
		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
		AppInfoData appInfoData;
		while(iterator.hasNext()){
			String  filename = (String) iterator.next();
			AppInfoData apkInfoData = FileProxy.getApkFileInfo(context, filename);
			if(apkInfoData != null){   
				appInfoData = new AppInfoData(context);
				
				appInfoData.setPackage(apkInfoData.getPackage());
	            appInfoData.setLabel(apkInfoData.getLabel());
	            appInfoData.setVersionCode(apkInfoData.getVersionCode());
	            appInfoData.setPath(filename);
	            appInfoData.setVersionName(apkInfoData.getVersionName());
	            
	            listApps.add(appInfoData);
	        }      
		}	
		
		
		if(listApps.size()>0){
			updateAppDownloadDatabase(listApps);
		}
		
		appInfoData = new AppInfoData(context);
		if(newVersionReady(appInfoData)){
			// removed according to the customer request
//			Common.notifyNewVersion(context, appInfoData.getPath());
		}

		
	}
	
	
	private void insertAppDownloadDatabase(AppInfoData appInfoData){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADED);

		
			 ContentValues values = new ContentValues();
			 values.put(Data.DB_COLUMN_APP_PACKAGE, appInfoData.getPackage());
			 values.put(Data.DB_COLUMN_APP_LABEL, appInfoData.getLabel());
			 values.put(Data.DB_COLUMN_APP_VERSION_CODE, appInfoData.getVersionCode());
			 values.put(Data.DB_COLUMN_APP_FILE_PATH, appInfoData.getPath());
			 	
			 resolver.insert(uri, values);

		
	}
	
	
	private void updateAppDownloadDatabase(ArrayList<AppInfoData> applist){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADED);

		this.setDataTableUpdateFlag(uri);
		
	
		//Uri.Builder builder = new Uri.Builder();
		
		Iterator<AppInfoData> l = applist.iterator();
		 while (l.hasNext()) {
			 AppInfoData info = (AppInfoData) l.next();
		
			 ContentValues values = new ContentValues();
			 values.put(Data.DB_COLUMN_APP_PACKAGE, info.getPackage());
			 values.put(Data.DB_COLUMN_APP_LABEL, info.getLabel());
			 values.put(Data.DB_COLUMN_APP_VERSION_CODE, info.getVersionCode());
			 values.put(Data.DB_COLUMN_APP_FILE_PATH, info.getPath());
			 	
			 //resolver.insert(uri, values);
			 
			 if(!isAppInDatabase(uri,info.getPackage())){
				 resolver.insert(uri, values);
			 }
			 else{
				 
				 values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_NULL);

				 String where = Data.DB_COLUMN_APP_PACKAGE + "=?";
				 String[] selectionArgs = new String[1];
				 selectionArgs[0] = info.getPackage();
				 
				 resolver.update(uri, values, where, selectionArgs);
								 
			 }

		 }
		 
		this.cleanDataTable(uri);
		
		//this.clearDataTableUpdateFlag(uri);
		
		
	}
	
	
	public void setDataTableUpdateFlag(Uri uri){

//		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
//				+Data.URI_APP_REPOSITORY);
		
		ContentValues values = new ContentValues();
		values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_DELETE);

		String where = null;
		String[] selectionArgs = null;
		

		resolver.update(uri, values, where, selectionArgs);
	
	}

	
	private void setAppRepoUpdateFlag(){

		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_REPOSITORY);
		
		ContentValues values = new ContentValues();
		values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_UPDATING);

		String where = Data.DB_COLUMN_APP_STATE+"=?";//+"'"+Data.DB_VALUE_FLAG_DELETE+"'";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = Data.DB_VALUE_STATE_ONLINE;
		
		resolver.update(uri, values, where, selectionArgs);
	
	}
	
	private void clearAppRepoUpdateFlag(){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_REPOSITORY);
		
		ContentValues values = new ContentValues();
		values.put(Data.DB_COLUMN_APP_STATE, Data.DB_VALUE_STATE_OFFLINE);

		String where = Data.DB_COLUMN_APP_STATE+"=?" +" AND " +Data.DB_COLUMN_UPDATE_FLAG +"=?";
		String[] selectionArgs = new String[2];
		selectionArgs[0] = Data.DB_VALUE_STATE_ONLINE;
		selectionArgs[1] = Data.DB_VALUE_FLAG_UPDATING;
		
		resolver.update(uri, values, where, selectionArgs);

		values = new ContentValues();
		values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_NULL);

		where = Data.DB_COLUMN_UPDATE_FLAG+"=?";
		selectionArgs = new String[1];
		selectionArgs[0] = Data.DB_VALUE_FLAG_UPDATING;
		
		resolver.update(uri, values, where, selectionArgs);

	}
	

	public void clearDataTableUpdateFlag(Uri uri){
//		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
//				+Data.URI_APP_REPOSITORY);
		
		ContentValues values = new ContentValues();
		values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_NULL);

		String where = null;
		String[] selectionArgs = null;
		

		resolver.update(uri, values, where, selectionArgs);
		
	}
	
	public void cleanDataTable(Uri uri){
		
//		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
//				+Data.URI_APP_REPOSITORY);
		
	
		String where = Data.DB_COLUMN_UPDATE_FLAG+"=?";//+"'"+Data.DB_VALUE_FLAG_DELETE+"'";
		String[] selectionArgs = new String[1];
		selectionArgs[0] = Data.DB_VALUE_FLAG_DELETE;
		
		resolver.delete(uri,where, selectionArgs);

		
	}
	
	public void updateAppRepository(ArrayList<AppInfoData> listApps, String resp){
	//	HttpProxy httpproxy = new HttpProxy(context);
	//	String resp = httpproxy.postCategoryQuery(Data.DB_VALUE_CATEGORY_ALL);
		Log.i(TAG, "updateAppRepository");
		if(resp!=null){
			
	//		setAppRepoUpdateFlag();
			
			try {
				JSONObject obj = new JSONObject(resp);
				
				
//				ArrayList listApps = new ArrayList<AppInfoData>();
				
				boolean api_resp = obj.getBoolean(Data.API_JSON_RESPONSE);
				if(api_resp){
//					Log.i(TAG, "API_JSON_RESPONSE:true");
					
					SharedPrefProxy.setCloudUpdateNow(context);
					
					JSONArray jsonarrayApps = obj.getJSONArray(Data.API_JSON_APP_ARRAY);
				
					int size = jsonarrayApps.length();
				
					JSONObject jsonAppInfo;
					ArrayList<AppInfoData> listAppRepo = new ArrayList<AppInfoData>();
					for(int i=0;i<size;i++){
						jsonAppInfo = jsonarrayApps.getJSONObject(i);
						AppInfoData appInfoData = new AppInfoData(context);
					
						appInfoData.setCategory(jsonAppInfo.getString(Data.DB_COLUMN_APP_CATEGORY));
						appInfoData.setLabel(jsonAppInfo.getString(Data.DB_COLUMN_APP_LABEL));
						appInfoData.setPackage(jsonAppInfo.getString(Data.DB_COLUMN_APP_PACKAGE));
						appInfoData.setVersionCode(Integer.parseInt(jsonAppInfo.getString(Data.DB_COLUMN_APP_VERSION_CODE)));
						
						appInfoData.setFileName(jsonAppInfo.getString(Data.DB_COLUMN_APP_FILE)); 
						// change to original file name
						//appInfoData.setFileName(jsonAppInfo.getString(Data.DB_COLUMN_APP_FILE_ORIGINAL));
//						
//						appInfoData.setUrl(jsonAppInfo.getString(Data.DB_COLUMN_APP_URL));
						appInfoData.setStamp(jsonAppInfo.getString(Data.DB_COLUMN_APP_UPDATE_STAMP));
						appInfoData.setState(jsonAppInfo.getString(Data.DB_COLUMN_APP_STATE));
						appInfoData.setPrice(Integer.parseInt(jsonAppInfo.getString(Data.DB_COLUMN_APP_PRICE)));
//						Log.i(TAG, "app_"+i+":"+appInfoData.getPackage()+": app_state = "+appInfoData.getState());
						appInfoData.setIcon(null);
					
						listAppRepo.add(appInfoData);
					
					}	
				
					if(listAppRepo.size()>0){
						updateAppRepoDatabase(listAppRepo);
					}
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "cause:"+e.getCause()+",msg:"+e.getMessage());
			}
			catch (Exception e1){
				e1.printStackTrace();
			}
						
		}
	}
	
	
	
	public void updateAppRepository(ArrayList<AppInfoData> listApps){
		
		String stamp = queryAppRepoStamp();
		
		HttpProxy httpproxy = new HttpProxy(context);
		String resp = httpproxy.postAppRepoQuery(stamp);
		updateAppRepository(listApps,resp);
	
	}
	
	public void updateAppRepository(){
		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
		
		updateAppRepository(listApps);
	
	}
	
	public void initAppRepository(ArrayList<AppInfoData> listApps){
		Log.i(TAG, "initAppRepository");
		//updateAppRepository(listApps,FileProxy.getAppRepoAssets(context));
	}
	
	
	private void updateAppRepoDatabase(ArrayList<AppInfoData> applist){
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
			+Data.URI_APP_REPOSITORY);

		//this.setDataTableUpdateFlag(uri);
		setAppRepoUpdateFlag();
				
		int i =0;
		Iterator<AppInfoData> l = applist.iterator();
		 while (l.hasNext()) {
			 AppInfoData info = (AppInfoData) l.next();
		
			 ContentValues values = new ContentValues();
			 values.put(Data.DB_COLUMN_APP_CATEGORY, info.getCategory());
			 values.put(Data.DB_COLUMN_APP_PACKAGE, info.getPackage());
			 values.put(Data.DB_COLUMN_APP_LABEL, info.getLabel());
			 values.put(Data.DB_COLUMN_APP_VERSION_CODE, info.getVersionCode());
			 values.put(Data.DB_COLUMN_APP_FILE, info.getFileName());
//			 values.put(Data.DB_COLUMN_APP_URL, info.getUrl());
			 values.put(Data.DB_COLUMN_APP_UPDATE_STAMP,info.getStamp() );
			 values.put(Data.DB_COLUMN_APP_STATE,info.getState() );
			 values.put(Data.DB_COLUMN_APP_PRICE,info.getPrice() );
				
			 if(!isAppInDatabase(uri,info.getPackage())){
//				 Log.i(TAG, "insert_"+i+" "+info.getLabel()+": "+info.getState());
				 resolver.insert(uri, values);
			 }
			 else{
				 
				 values.put(Data.DB_COLUMN_UPDATE_FLAG, Data.DB_VALUE_FLAG_NULL);

				 String where = Data.DB_COLUMN_APP_PACKAGE + "=?";
				 String[] selectionArgs = new String[1];
				 selectionArgs[0] = info.getPackage();
				 
//				 Log.i(TAG, "update_"+i+" "+info.getLabel()+": "+info.getState());
				 
				 resolver.update(uri, values, where, selectionArgs);
								 
			 }
			 
			 i++;
		 }
		 
		 clearAppRepoUpdateFlag();
		 
		//this.cleanDataTable(uri);
			
		//this.clearDataTableUpdateFlag(uri);
			
	
		
	}
	
	
	
	public String queryAppRepoStamp(){
		
		String stamp = null;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_REPOSITORY
				+Data.URI_LIMIT_1
				);
		
		
		String[] projection = new String[1];
		projection[0] = Data.DB_COLUMN_APP_UPDATE_STAMP;
		
		String selection = null;
		String sortOrder = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				stamp = cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_UPDATE_STAMP));
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		
		
		return stamp;
		
	}
	
	public ArrayList<AppInfoData> queryAppRepository(ArrayList<AppInfoData> listApps){
		
//		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
	
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_REPOSITORY
				);
		
		
		String[] projection = new String[8];
		projection[0] = Data.DB_COLUMN_APP_CATEGORY;
		projection[1] = Data.DB_COLUMN_APP_PACKAGE;
		projection[2] = Data.DB_COLUMN_APP_LABEL;
		projection[3] = Data.DB_COLUMN_APP_VERSION_CODE;
		projection[4] = Data.DB_COLUMN_APP_FILE;
		projection[5] = Data.DB_COLUMN_APP_STATE;
		projection[6] = Data.DB_COLUMN_APP_UPDATE_STAMP;
		projection[7] = Data.DB_COLUMN_APP_PRICE;
		
		String selection = null;
		String sortOrder = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfo = new AppInfoData(context);

				appInfo.setCategory(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_CATEGORY)));
				appInfo.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfo.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfo.setFileName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE)));
//				appInfo.setUrl(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_URL)));
				appInfo.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfo.setPrice(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PRICE))));
				appInfo.setState(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_STATE)));
				appInfo.setStamp(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_UPDATE_STAMP)));
			
				appInfo.setLocation(Data.DB_VALUE_LOCATION_CLOUD);
				
				appInfo.setIcon(context.getResources().getDrawable(R.drawable.appicon_default));
				
				if(!isAppInstalled(appInfo))
					if(!isAppDownloaded(appInfo))
						isAppDownloading(appInfo);
				
				if(Data.DB_VALUE_LOCATION_CLOUD.equals(appInfo.getLocation()) 
						|| Data.DB_VALUE_LOCATION_DOWNLOADING.equals(appInfo.getLocation()) ){
					isIconReady(appInfo);
				}

				
				listApps.add(appInfo);
			
			}while(cursor.moveToNext());
		}
		else{
			Log.i(TAG, "initAppRepository2204");
			initAppRepository(listApps);
		}
		if(cursor!=null)
			cursor.close();
		
		
		
		return listApps;
		
	}
	
	public ArrayList<AppInfoData> queryAppRepoNotInMy(ArrayList<AppInfoData> listApps,String category){
		
//		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
	
//		Log.i(TAG, "queryAppRepository:"+category);
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_REPOSITORY
				);
		
		Uri uriMy = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_MY
				);
		
		String[] projection = new String[8];
		
		projection[0] = Data.DB_COLUMN_APP_CATEGORY;
		projection[1] = Data.DB_COLUMN_APP_PACKAGE;
		projection[2] = Data.DB_COLUMN_APP_LABEL;
		projection[3] = Data.DB_COLUMN_APP_VERSION_CODE;
		projection[4] = Data.DB_COLUMN_APP_FILE;
		projection[5] = Data.DB_COLUMN_APP_STATE;
		projection[6] = Data.DB_COLUMN_APP_UPDATE_STAMP;
		projection[7] = Data.DB_COLUMN_APP_PRICE;
		
		
		String selection = Data.DB_COLUMN_APP_CATEGORY+"=?" 
				+ " AND " + Data.DB_COLUMN_APP_STATE + " =? ";
		String sortOrder = Data.DB_COLUMN_APP_PRICE + " DESC";
		String[] selectionArgs = new String[2];
		selectionArgs[0] = category;
		selectionArgs[1] = Data.DB_VALUE_STATE_ONLINE;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfoData = new AppInfoData(context);

				appInfoData.setCategory(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_CATEGORY)));
				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setFileName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE)));
//				appInfoData.setUrl(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_URL)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfoData.setState(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_STATE)));
				appInfoData.setPrice(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PRICE))));
				appInfoData.setStamp(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_UPDATE_STAMP)));
				appInfoData.setLocation(Data.DB_VALUE_LOCATION_CLOUD);
				
				appInfoData.setIcon(context.getResources().getDrawable(R.drawable.appicon_default));
				
				if(!isAppInDatabase(uriMy, appInfoData.getPackage())){
					if(!isAppInstalled(appInfoData))
						if(!isAppDownloaded(appInfoData))
							isAppDownloading(appInfoData);
				
					if(Data.DB_VALUE_LOCATION_CLOUD.equals(appInfoData.getLocation()) 
							|| Data.DB_VALUE_LOCATION_DOWNLOADING.equals(appInfoData.getLocation()) ){
						isIconReady(appInfoData);
					}
					listApps.add(appInfoData);
				}
				
			}while(cursor.moveToNext());
		}	
		else{
			Log.i(TAG, "initAppRepository2292");
			initAppRepository(listApps);
		}
		if(cursor!=null)
			cursor.close();
		
		
		
		return listApps;
		
	}
	
	public ArrayList<AppInfoData> queryAppRepository(ArrayList<AppInfoData> listApps,String category){
		
//		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
	
//		Log.i(TAG, "queryAppRepository:"+category);
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_REPOSITORY
				);
		
		
		String[] projection = new String[8];
		
		projection[0] = Data.DB_COLUMN_APP_CATEGORY;
		projection[1] = Data.DB_COLUMN_APP_PACKAGE;
		projection[2] = Data.DB_COLUMN_APP_LABEL;
		projection[3] = Data.DB_COLUMN_APP_VERSION_CODE;
		projection[4] = Data.DB_COLUMN_APP_FILE;
		projection[5] = Data.DB_COLUMN_APP_STATE;
		projection[6] = Data.DB_COLUMN_APP_UPDATE_STAMP;
		projection[7] = Data.DB_COLUMN_APP_PRICE;
		
		
		String selection = Data.DB_COLUMN_APP_CATEGORY+"=?" 
				+ " AND " + Data.DB_COLUMN_APP_STATE + " =? ";
		String sortOrder = Data.DB_COLUMN_APP_PRICE + " DESC";
		String[] selectionArgs = new String[2];
		selectionArgs[0] = category;
		selectionArgs[1] = Data.DB_VALUE_STATE_ONLINE;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				AppInfoData appInfoData = new AppInfoData(context);

				appInfoData.setCategory(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_CATEGORY)));
				appInfoData.setPackage(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PACKAGE)));
				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setFileName(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE)));
//				appInfoData.setUrl(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_URL)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfoData.setState(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_STATE)));
				appInfoData.setPrice(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_PRICE))));
				appInfoData.setStamp(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_UPDATE_STAMP)));
				appInfoData.setLocation(Data.DB_VALUE_LOCATION_CLOUD);
				
				appInfoData.setIcon(context.getResources().getDrawable(R.drawable.appicon_default));
				
				if(!isAppInstalled(appInfoData))
					if(!isAppDownloaded(appInfoData))
						isAppDownloading(appInfoData);
				
				if(Data.DB_VALUE_LOCATION_CLOUD.equals(appInfoData.getLocation()) 
						|| Data.DB_VALUE_LOCATION_DOWNLOADING.equals(appInfoData.getLocation()) ){
					isIconReady(appInfoData);
				}
				listApps.add(appInfoData);
			
			}while(cursor.moveToNext());
		}	
		else{
			Log.i(TAG, "initAppRepository2374");
			initAppRepository(listApps);
		}
		if(cursor!=null)
			cursor.close();
		
		
		
		return listApps;
		
	}
	
	public boolean isAppInstalled(AppInfoData appInfoData){
		boolean flag = false;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_INSTALLED
				);
		
		
		String[] projection = new String[4];
		projection[0] = Data.DB_COLUMN_APP_LABEL;
		projection[1] = Data.DB_COLUMN_APP_VERSION_CODE;
		projection[2] = Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME;
		projection[3] = Data.DB_COLUMN_APP_LATEST_UPDATE_TIME;
		
		String selection = Data.DB_COLUMN_APP_PACKAGE+"=?";
		String sortOrder = null;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = appInfoData.getPackage();
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{

				appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
				appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
				appInfoData.setInstallTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FIRST_INSTALLED_TIME)));
				appInfoData.setUpdateTime(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LATEST_UPDATE_TIME)));
				appInfoData.setLocation(Data.DB_VALUE_LOCATION_INSTALLED);
				try {
					appInfoData.setIcon(context.getPackageManager().getApplicationIcon(appInfoData.getPackage()));
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				flag= true;
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
				
		return flag;
	}
	
	public boolean isAppDownloaded(AppInfoData appInfoData){
		boolean flag = false;

		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADED
				);
		
		
		String[] projection = new String[3];
		projection[0] = Data.DB_COLUMN_APP_LABEL;
		projection[1] = Data.DB_COLUMN_APP_VERSION_CODE;
		projection[2] = Data.DB_COLUMN_APP_FILE_PATH;
		
		String selection = Data.DB_COLUMN_APP_PACKAGE+"=?";
		String sortOrder = null;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = appInfoData.getPackage();
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{

				String path = cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_PATH));
				if(path!=null){
					File file = new File(path);
					if(file.exists()){
						appInfoData.setLabel(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_LABEL)));
						appInfoData.setVersionCode(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_VERSION_CODE))));
						appInfoData.setPath(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_PATH)));
						appInfoData.setLocation(Data.DB_VALUE_LOCATION_DOWNLOADED);
				
						FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
				
						flag= true;
					}
				}
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
	
		return flag;
	}

	
	public boolean isAppDownloading(AppInfoData appInfoData){
		
		boolean flag = false;
		
		String path = appInfoData.getSavedPath();
		File file = new File(path);

		//File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+appInfoData.getFileName());

//		Log.i(TAG, "isDownloading:"+file.getAbsolutePath()+(flag?" true":" false"));
		
		if(file.exists()){
			Log.i(TAG, "isDownloading: file exist");
				
			if(isAppDownloadingDatabase(appInfoData)){
				
				Log.i(TAG, "isDownloading: update");
					
			
				Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
					+Data.URI_APP_DOWNLOADING);
			
				ContentValues values = new ContentValues();
				values.put(Data.DB_COLUMN_APP_DOWNLOAD_STATE, Data.DB_VALUE_DOWNLOAD_DOWNLOADED);

				String where = Data.DB_COLUMN_APP_PACKAGE + "=?";
				String[] selectionArgs = new String[1];
				selectionArgs[0] = appInfoData.getPackage();
			

				resolver.update(uri, values, where, selectionArgs);
			
			}
			/*
			else{
				Log.i(TAG, "isDownloading: insert");
				
				Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
					+Data.URI_APP_DOWNLOADING);

					
				ContentValues values = new ContentValues();
				 
				values.put(Data.DB_COLUMN_APP_PACKAGE, appInfoData.getPackage());
					
				values.put(Data.DB_COLUMN_APP_LABEL, appInfoData.getLabel());
					 		 
				values.put(Data.DB_COLUMN_APP_VERSION_CODE, appInfoData.getVersionCode());
				 
				values.put(Data.DB_COLUMN_APP_FILE, appInfoData.getFileName());
				 
				String root = FileProxy.getDownloadRootUrl(context);
				String dir = queryCategoryDir(appInfoData.getAppCategory());
				String url = root+dir+appInfoData.getFileName();
				values.put(Data.DB_COLUMN_APP_URL,url);
					
//				appInfoData.addPathSuffix();
				values.put(Data.DB_COLUMN_APP_FILE_PATH, appInfoData.getTempPath());
				
				
				values.put(Data.DB_COLUMN_APP_DOWNLOAD_STATE, Data.DB_VALUE_DOWNLOAD_DOWNLOADED);
				
				values.put(Data.DB_COLUMN_APP_DOWNLOAD_START_TIME, Common.StampToString(System.currentTimeMillis()));
				
				
				resolver.insert(uri, values);
				
			}
			*/
			
//			appInfoData.removePathSuffix();
			appInfoData.setPath(path);
			insertAppDownloadDatabase(appInfoData);
						
//			finishAppDownload(appInfoData);
			
			flag =  true;
			
		}
		else{
			
			flag =  isAppDownloadingDatabase(appInfoData);
		}
		
		
		return flag;

		
	}
	
	private boolean isAppDownloadingDatabase(AppInfoData appInfoData){

		boolean flag = false;
		
		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_DOWNLOADING
				);
		
		String[] projection = new String[Data.Db_Table_App_Downloading.length];
		int i=0;
		int l=Data.Db_Table_App_Downloading.length;
		while (i<l){
			projection[i] = Data.Db_Table_App_Downloading[i];
			i++;
		}		
		
		String selection = Data.DB_COLUMN_APP_PACKAGE+"=?"+" AND "+ Data.DB_COLUMN_APP_DOWNLOAD_STATE + "=?";
		String sortOrder = null;
		String[] selectionArgs = new String[2];
		selectionArgs[0] = appInfoData.getPackage();
		selectionArgs[1] = Data.DB_VALUE_DOWNLOAD_DOWNLOADING;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{

				appInfoData.setLocation(Data.DB_VALUE_LOCATION_DOWNLOADING);
				
				// add for progress track
				appInfoData.setFileLength(Integer.parseInt(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_LENGTH))));
				appInfoData.setPath(cursor.getString(getProjectionIndex(projection,Data.DB_COLUMN_APP_FILE_PATH)));
				//FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
				
				flag= true;
			
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
	
		return flag;

	}
	
	public boolean isPackageInRepo(String packageName){
		boolean flag = false;

		Uri uri = new Uri.Builder().build().parse(Data.URI_HEADER
				+Data.URI_APP_REPOSITORY
				+Data.URI_LIMIT_1
				);
		
		String[] projection = null;
		
		String selection = Data.DB_COLUMN_APP_PACKAGE+"=?"
//				+ " AND "
//				+ Data.DB_COLUMN_APP_STATE+"=?"
				;
		String sortOrder = null;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = packageName;
//		selectionArgs[1] = Data.DB_VALUE_STATE_ONLINE;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			flag= true;
		}	
		if(cursor!=null)
			cursor.close();
		
		Log.i(TAG, "isPackageOnline:"+(flag?"true":"false"));
		return flag;
	}
	
	private boolean isAppInDatabase(Uri uri, String packageName){

		boolean flag = false;
		
		String[] projection = null;
		
		String selection = Data.DB_COLUMN_APP_PACKAGE+"=?";
		String sortOrder = null;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = packageName;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				flag= true;
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
		
		Log.i(TAG, packageName+" in "+uri.toString()+(flag?" true!":" false!"));
	
		return flag;

	}
	
	
	private boolean isCategoryInDatabase(Uri uri, String category){

		boolean flag = false;
		
		AppInfoData appInfoData = new AppInfoData(context);
		
		String[] projection = null;
		
		String selection = Data.DB_COLUMN_APP_CATEGORY+"=?";
		String sortOrder = null;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = category;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
			do{
				flag= true;
			}while(cursor.moveToNext());
		}	
		if(cursor!=null)
			cursor.close();
	
		return flag;

	}
	private boolean isIconReady(AppInfoData appInfoData){
		boolean flag = false;
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        		+ "/icon/"+appInfoData.getPackage()+".png";
		File file = new File(path);
		if(!file.exists()){
//			Log.i(TAG, "icon not exist");
			FileProxy.checkIconAsset(context, appInfoData.getPackage());
			
		}
		
        if(file.exists()){
//        	Log.i(TAG, "icon exist");
			FileProxy.setAppFileIcon(context, path, appInfoData);
        	flag = true;
        }
        else{
        	
        	AppInfoData infoData = new AppInfoData(context);
        	infoData.setCategory(Data.DB_VALUE_CATEGORY_ICON);
        	infoData.setPackage(appInfoData.getPackage()+Data.DB_VALUE_ICON_SUFFIX);
        	infoData.setLabel(appInfoData.getLabel()+Data.DB_VALUE_CATEGORY_ICON);
        	infoData.setFileName(appInfoData.getPackage()+Data.DB_VALUE_ICON_SUFFIX);
        	infoData.setVersionCode(appInfoData.getVersionCode());
        	
        	startDownloading(infoData,Data.DB_VALUE_YES);
//          	Log.i(TAG, "icon download");
        	
        }
		
		return flag;
	}

/*	
	public Uri InsertCarTracker(String number, double latitude, double longitude,  String provider, long stamp,float speed, float bearing, float accuracy) throws Exception{
		
		//Uri.Builder builder = new Uri.Builder();
		Uri uri = new Uri.Builder().build().parse(Constants.URI_HEADER
			+Constants.URI_CAR_TRACKER);

		ContentValues values = new ContentValues();
		values.put(Constants.DB_COLUMN_CAR_NUMBER, number);
		values.put(Constants.DB_COLUMN_CAR_POS_LATE6,""+(int)(latitude*1E6));
		values.put(Constants.DB_COLUMN_CAR_POS_LONGE6, ""+(int)(longitude*1E6));
		values.put(Constants.DB_COLUMN_CAR_POS_STAMP, Convertor.StampToString(stamp));
		values.put(Constants.DB_COLUMN_CAR_POS_PROVIDER, provider);
		values.put(Constants.DB_COLUMN_CAR_POS_SPEEDE6, ""+(int)(speed*1E6));
		values.put(Constants.DB_COLUMN_CAR_POS_BEARINGE6, ""+(int)(bearing*1E6));
		values.put(Constants.DB_COLUMN_CAR_POS_ACCURACY, ""+(int)accuracy);
		
		return resolver.insert(uri, values);
	}
	
	public int ReportCarTracker() throws Exception{
		
		int reported = 0; 
		
		Uri uri = new Uri.Builder().build().parse(Constants.URI_HEADER
				+Constants.URI_CAR_TRACKER
				+Constants.URI_LIMIT_1);
		
		
		String[] table = Constants.Db_Table_Car_Tracker;
		int l = table.length;
		
		String[] projection = new String[l+1];
		int i =0;
		while (i<l){
			projection[i] = table[i];
			i++;
		}
		projection[i] = Constants.DB_COLUMN_ROWID;
		
		String selection = null;
		String sortOrder = null;
		String[] selectionArgs = null;
		
		Cursor cursor = null;
		
		cursor = resolver.query(uri,projection, selection, selectionArgs, sortOrder);
	
		int rows = 0, columns = 0;
		if(cursor!=null){
			rows = cursor.getCount();
			columns = cursor.getColumnCount();
		}
		if(rows>0 && columns>0){
		
			cursor.moveToFirst();
		
			String number = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_NUMBER));
			String late6 = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_POS_LATE6));
			String longe6 = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_POS_LONGE6));
			String stamp = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_POS_STAMP));
			String type = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_POS_PROVIDER));
			String speed = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_POS_SPEEDE6));
			String bearing = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_POS_BEARINGE6));
			String accuracy = cursor.getString(getProjectionIndex(projection,Constants.DB_COLUMN_CAR_POS_ACCURACY));
			int rowid = cursor.getInt(getProjectionIndex(projection,Constants.DB_COLUMN_ROWID));
			
			HttpProxy http = new HttpProxy(context);
			String response = null;
		
			response = http.postCarPositionReport(late6, longe6, number, type, stamp,speed,bearing,accuracy);
					
			if(Constants.API_RESP_SUCCESS.equals(response)){
				
				String where = Constants.DB_COLUMN_ROWID +"=?";
				selectionArgs = new String[1];
				selectionArgs[0] =""+rowid;
				reported = resolver.delete(uri,where, selectionArgs);
			}
		}	
		if(cursor!=null)
			cursor.close();
	
		return reported;
	}
	*/
/*
	public static String StampToString(long stamp){
		
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(stamp);
		date =  calendar.getTime();
		
//    	String format = "yyyy-MM-dd HH:mm:ss.SSSZ"; 
    	String format = "yyyy-MM-dd HH:mm:ss"; 
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));	
		sdf.setTimeZone(TimeZone.getTimeZone("PRC"));	
		
		return sdf.format(date);
		
    }
	*/
	private int getProjectionIndex(String[] projection,String column){
		int index = 0;

		int i = 0;
		int l = projection.length;
		while(i<l){
			if(projection[i].equals(column)){
				index = i;
				return index;
			}
			i++;
		}

		
		return index;
	}
}
