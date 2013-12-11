/**
 * 
 */
package com.android.aid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class SharedPrefProxy {
	private static final String TAG = "SharedPrefProxy";
	
	public static void setLatestTaskPackage(Context context,String packageName){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(Data.FILE_KEY_LATEST_TASK_PACKAGE, packageName);
		editor.commit();
		
	}
	
	public static String getLatestTaskPackage(Context context){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		String packageName = myPreferences.getString(Data.FILE_KEY_LATEST_TASK_PACKAGE, Data.FILE_DATA_TASK_PACKAGE_NULL);
		
		return packageName;
	}
	
	public static void setCloudUpdateRequest(Context context){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(Data.FILE_KEY_CLOUD_SYNC_MODE, Data.FILE_VALUE_CLOUD_SYNC_REQUEST);
		editor.commit();
		
		//Log.i(TAG, "set update request");
		
	}
	
	public static void setCloudUpdateAuto(Context context){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(Data.FILE_KEY_CLOUD_SYNC_MODE, Data.FILE_VALUE_CLOUD_SYNC_AUTO);
		editor.commit();
		
		//Log.i(TAG, "set update auto");
		
	}
	public static boolean isCloudUpdatedRequest(Context context){
		boolean flag = false;
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		String date = myPreferences.getString(Data.FILE_KEY_CLOUD_SYNC_MODE, Data.FILE_VALUE_CLOUD_SYNC_AUTO);
		if(date.equals(Data.FILE_VALUE_CLOUD_SYNC_REQUEST)){
			flag = true;
		//	Log.i(TAG, "update request!");
		}
		else{
		//	Log.i(TAG, "update auto");
		}
		return flag;
	}

	
	public static void setCloudUpdateNow(Context context){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		String date = Common.StampToyyyyMMdd(System.currentTimeMillis());
		editor.putString(Data.FILE_KEY_CLOUD_SYNC_DATE, date);
		editor.commit();
		//Log.i(TAG, "now update:"+date);
		
	}
	public static boolean isCloudUpdatedToday(Context context){
		boolean flag = false;
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		String date = myPreferences.getString(Data.FILE_KEY_CLOUD_SYNC_DATE, null);
		//Log.i(TAG, "last update:"+date);
		if(Common.StampToyyyyMMdd(System.currentTimeMillis()).equals(date)){
			flag = true;
		}
		
		return flag;
	}
	
}
