/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * @author wangpeifeng
 *
 */
public class Common {
	
	private static final String TAG = "AndroidAidCommon";
/*
	public static void setCloudUpdateRequest(Context context){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(Data.FILE_KEY_CLOUD_SYNC_MODE, Data.FILE_VALUE_CLOUD_SYNC_REQUEST);
		editor.commit();
		
		Log.i(TAG, "set update request");
		
	}
	
	public static void setCloudUpdateAuto(Context context){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(Data.FILE_KEY_CLOUD_SYNC_MODE, Data.FILE_VALUE_CLOUD_SYNC_AUTO);
		editor.commit();
		
		Log.i(TAG, "set update auto");
		
	}
	public static boolean isCloudUpdatedRequest(Context context){
		boolean flag = false;
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		String date = myPreferences.getString(Data.FILE_KEY_CLOUD_SYNC_MODE, Data.FILE_VALUE_CLOUD_SYNC_AUTO);
		if(date.equals(Data.FILE_VALUE_CLOUD_SYNC_REQUEST)){
			flag = true;
			Log.i(TAG, "update request!");
		}
		else{
			Log.i(TAG, "update auto");
		}
		return flag;
	}

	
	public static void setCloudUpdateNow(Context context){
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		String date = StampToyyyyMMdd(System.currentTimeMillis());
		editor.putString(Data.FILE_KEY_CLOUD_SYNC_DATE, date);
		editor.commit();
		Log.i(TAG, "now update:"+date);
		
	}
	public static boolean isCloudUpdatedToday(Context context){
		boolean flag = false;
		
		SharedPreferences myPreferences = context.getSharedPreferences(Data.FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		String date = myPreferences.getString(Data.FILE_KEY_CLOUD_SYNC_DATE, null);
		Log.i(TAG, "last update:"+date);
		if(StampToyyyyMMdd(System.currentTimeMillis()).equals(date)){
			flag = true;
		}
		
		return flag;
	}
*/	
	public static int categoryToImageID(String category){
		int index = -1;
		if(Data.DB_VALUE_CATEGORY_IM.equals(category)){
			index = R.drawable.img_0_1;
		}
		else if(Data.DB_VALUE_CATEGORY_GAME.equals(category)){
			index = R.drawable.img_1_1;
		}
		else if(Data.DB_VALUE_CATEGORY_MUSIC.equals(category)){
			index = R.drawable.img_2_1;
		}
		else if(Data.DB_VALUE_CATEGORY_VIDEO.equals(category)){
			index = R.drawable.img_3_1;
		}
		else if(Data.DB_VALUE_CATEGORY_SHOPPING.equals(category)){
			index = R.drawable.img_4_1;
		}
		else if(Data.DB_VALUE_CATEGORY_TRANSPORT.equals(category)){
			index = R.drawable.img_5_1;
		}
		else if(Data.DB_VALUE_CATEGORY_WEATHER.equals(category)){
			index = R.drawable.img_6_1;
		}
		else if(Data.DB_VALUE_CATEGORY_WEB.equals(category)){
			index = R.drawable.img_7_1;
		}
		else if(Data.DB_VALUE_CATEGORY_READING.equals(category)){
			index = R.drawable.img_8_1;
		}
		else if(Data.DB_VALUE_CATEGORY_TOOLS.equals(category)){
			index = R.drawable.img_9_1;
		}
		else if(Data.DB_VALUE_CATEGORY_PICTURE.equals(category)){
			index = R.drawable.img_10_1;
		}
		else if(Data.DB_VALUE_CATEGORY_LIFE.equals(category)){
			index = R.drawable.img_11_1;
		}
		else if(Data.DB_VALUE_CATEGORY_CAMERA.equals(category)){
			index = R.drawable.img_12_1;
		}
		else if(Data.DB_VALUE_CATEGORY_NEWS.equals(category)){
			index = R.drawable.img_13_1;
		}
		else if(Data.DB_VALUE_CATEGORY_MY.equals(category)){
			index = R.drawable.img_15_1;
		}
		else if(Data.DB_VALUE_CATEGORY_NULL.equals(category)){
			index = R.drawable.icon_download_manage_fail;
		}
		
		Log.i(TAG, category+":id = "+index);
		
		return index;
	}

	public static int categoryToGridPosition(String category){
		int index = -1;
		
		if(Data.DB_VALUE_CATEGORY_IM.equals(category)){
			index = 0;
		}
		else if(Data.DB_VALUE_CATEGORY_GAME.equals(category)){
			index = 1;
		}
		else if(Data.DB_VALUE_CATEGORY_MUSIC.equals(category)){
			index = 2;
		}
		else if(Data.DB_VALUE_CATEGORY_VIDEO.equals(category)){
			index = 3;
		}
		else if(Data.DB_VALUE_CATEGORY_SHOPPING.equals(category)){
			index = 4;
		}
		else if(Data.DB_VALUE_CATEGORY_TRANSPORT.equals(category)){
			index = 5;
		}
		else if(Data.DB_VALUE_CATEGORY_WEATHER.equals(category)){
			index = 6;
		}
		else if(Data.DB_VALUE_CATEGORY_WEB.equals(category)){
			index = 7;
		}
		else if(Data.DB_VALUE_CATEGORY_READING.equals(category)){
			index = 8;
		}
		else if(Data.DB_VALUE_CATEGORY_TOOLS.equals(category)){
			index = 9;
		}
		else if(Data.DB_VALUE_CATEGORY_PICTURE.equals(category)){
			index = 10;
		}
		else if(Data.DB_VALUE_CATEGORY_LIFE.equals(category)){
			index = 11;
		}
		else if(Data.DB_VALUE_CATEGORY_CAMERA.equals(category)){
			index = 12;
		}
		else if(Data.DB_VALUE_CATEGORY_NEWS.equals(category)){
			index = 13;
		}
		else if(Data.DB_VALUE_CATEGORY_MY.equals(category)){
			index = 15;
		}
		else if(Data.DB_VALUE_CATEGORY_NULL.equals(category)){
			index = 14;
		}
	
		return index;
	}
	
	public static String positionToCategory(int index){
		String category = null;

		switch(index){
		case 15:
			category = Data.DB_VALUE_CATEGORY_MY;
			break;
		case 14:
			category = Data.DB_VALUE_CATEGORY_NULL;
			break;
		case 0:
			category = Data.DB_VALUE_CATEGORY_IM;
			break;
		case 1:
			category = Data.DB_VALUE_CATEGORY_GAME;
			break;
		case 2:
			category = Data.DB_VALUE_CATEGORY_MUSIC;
			break;
		case 3:
			category = Data.DB_VALUE_CATEGORY_VIDEO;
			break;
		case 4:
			category = Data.DB_VALUE_CATEGORY_SHOPPING;
			break;
		case 5:
			category = Data.DB_VALUE_CATEGORY_TRANSPORT;
			break;
		case 6:
			category = Data.DB_VALUE_CATEGORY_WEATHER;
			break;
		case 7:
			category = Data.DB_VALUE_CATEGORY_WEB;
			break;
		case 8:
			category = Data.DB_VALUE_CATEGORY_READING;
			break;
		case 9:
			category = Data.DB_VALUE_CATEGORY_TOOLS;
			break;
		case 10:
			category = Data.DB_VALUE_CATEGORY_PICTURE;
			break;
		case 11:
			category = Data.DB_VALUE_CATEGORY_LIFE;
			break;
		case 12:
			category = Data.DB_VALUE_CATEGORY_CAMERA;
			break;
		case 13:
			category = Data.DB_VALUE_CATEGORY_NEWS;
			break;
		}
		return category;
	}
	
	public static int categoryToAddMyRadioButtonId(String category){
		int index = -1;
		
		if(Data.DB_VALUE_CATEGORY_IM.equals(category)){
			index = R.id.radioButtonIM;
		}
		else if(Data.DB_VALUE_CATEGORY_GAME.equals(category)){
			index = R.id.radioButtonGame;
		}
		else if(Data.DB_VALUE_CATEGORY_MUSIC.equals(category)){
			index = R.id.radioButtonMusic;
		}
		else if(Data.DB_VALUE_CATEGORY_VIDEO.equals(category)){
			index = R.id.radioButtonVideo;
		}
		else if(Data.DB_VALUE_CATEGORY_SHOPPING.equals(category)){
			index = R.id.radioButtonShopping;
		}
		else if(Data.DB_VALUE_CATEGORY_TRANSPORT.equals(category)){
			index = R.id.radioButtonTransport;
		}
		else if(Data.DB_VALUE_CATEGORY_WEATHER.equals(category)){
			index = R.id.radioButtonWeather;
		}
		else if(Data.DB_VALUE_CATEGORY_WEB.equals(category)){
			index = R.id.radioButtonWeb;
		}
		else if(Data.DB_VALUE_CATEGORY_READING.equals(category)){
			index = R.id.radioButtonReading;
		}
		else if(Data.DB_VALUE_CATEGORY_TOOLS.equals(category)){
			index = R.id.radioButtonTools;
		}
		else if(Data.DB_VALUE_CATEGORY_PICTURE.equals(category)){
			index = R.id.radioButtonPicture;
		}
		else if(Data.DB_VALUE_CATEGORY_LIFE.equals(category)){
			index = R.id.radioButtonLife;
		}
		else if(Data.DB_VALUE_CATEGORY_CAMERA.equals(category)){
			index = R.id.radioButtonCamera;
		}
		else if(Data.DB_VALUE_CATEGORY_NEWS.equals(category)){
			index = R.id.radioButtonNews;
		}
		else if(Data.DB_VALUE_CATEGORY_MY.equals(category)){
			index = R.id.radioButtonMy;
		}
		else if(Data.DB_VALUE_CATEGORY_NULL.equals(category)){
			index = R.id.radioButtonCancel;
		}
	
		return index;
	}
	
	public static String addMyRadioButtonIdToCategory(int index){
		String category = null;

		switch(index){
		case R.id.radioButtonMy:
			category = Data.DB_VALUE_CATEGORY_MY;
			break;
		case R.id.radioButtonCancel:
			category = Data.DB_VALUE_CATEGORY_NULL;
			break;
		case R.id.radioButtonIM:
			category = Data.DB_VALUE_CATEGORY_IM;
			break;
		case R.id.radioButtonGame:
			category = Data.DB_VALUE_CATEGORY_GAME;
			break;
		case R.id.radioButtonMusic:
			category = Data.DB_VALUE_CATEGORY_MUSIC;
			break;
		case R.id.radioButtonVideo:
			category = Data.DB_VALUE_CATEGORY_VIDEO;
			break;
		case R.id.radioButtonShopping:
			category = Data.DB_VALUE_CATEGORY_SHOPPING;
			break;
		case R.id.radioButtonTransport:
			category = Data.DB_VALUE_CATEGORY_TRANSPORT;
			break;
		case R.id.radioButtonWeather:
			category = Data.DB_VALUE_CATEGORY_WEATHER;
			break;
		case R.id.radioButtonWeb:
			category = Data.DB_VALUE_CATEGORY_WEB;
			break;
		case R.id.radioButtonReading:
			category = Data.DB_VALUE_CATEGORY_READING;
			break;
		case R.id.radioButtonTools:
			category = Data.DB_VALUE_CATEGORY_TOOLS;
			break;
		case R.id.radioButtonPicture:
			category = Data.DB_VALUE_CATEGORY_PICTURE;
			break;
		case R.id.radioButtonLife:
			category = Data.DB_VALUE_CATEGORY_LIFE;
			break;
		case R.id.radioButtonCamera:
			category = Data.DB_VALUE_CATEGORY_CAMERA;
			break;
		case R.id.radioButtonNews:
			category = Data.DB_VALUE_CATEGORY_NEWS;
			break;
		}
		return category;
	}
	
    /**
     * Return the hexadecimal format of a plain text string.
     *
     * @param strValue
     * @return
     */ 
    public static String stringToHex(String strValue) {
        byte byteData[] = null;
        int intHex = 0;
        String strHex = "";
        String strReturn = "";
        try {
            byteData = strValue.getBytes("ISO8859-1");
            for (int i=0;i<byteData.length;i++)
            {
                intHex = (int)byteData[i];
                if (intHex<0)
                    intHex += 256;
                if (intHex<16)
                    strHex += "0" + Integer.toHexString(intHex).toUpperCase();
                else
                    strHex += Integer.toHexString(intHex).toUpperCase();
            }
            strReturn = strHex;
 
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return strReturn;
    }
	
	public static String StampToString(long stamp){
/*
		String[] formats = new String[] {  
				"yyyy-MM-dd",   
				"yyyy-MM-dd HH:mm",  
				"yyyy-MM-dd HH:mmZ",   
				"yyyy-MM-dd HH:mm:ss.SSSZ",
				  "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
				}; 
*/		
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(stamp);
		date =  calendar.getTime();
		
//    	String format = "yyyy-MM-dd HH:mm:ss.SSSZ"; 
    	String format = "yyyy-MM-dd HH:mm:ss.SSSZ"; 
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));	
		sdf.setTimeZone(TimeZone.getTimeZone("PRC"));	
		
		return sdf.format(date);
		
    }
	
	public static String StampToyyyyMMdd(long stamp){

		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(stamp);
		date =  calendar.getTime();
		
    	String format = "yyyy-MM-dd"; 
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));	
		sdf.setTimeZone(TimeZone.getTimeZone("PRC"));	
		
		return sdf.format(date);
		
	}
	
	
	public static String StampToStringMMddHHmm(long stamp){
		
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(stamp);
		date =  calendar.getTime();
		
//    	String format = "yyyy-MM-dd HH:mm:ss.SSSZ"; 
    	String format = "MM-dd HH:mm"; 
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));	
		sdf.setTimeZone(TimeZone.getTimeZone("PRC"));	
		
		return sdf.format(date);
		
    }
	

	public static String StampToyyyyMMddHHmmss(long stamp){
		
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(stamp);
		date =  calendar.getTime();
		
    	String format = "yyyy-MM-dd HH:mm:ss.SSSZ"; 
//    	String format = "yyyy-MM-dd HH:mm:ss"; 
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));	
		sdf.setTimeZone(TimeZone.getTimeZone("PRC"));	
		
		String time =  sdf.format(date);
		return time.substring(0, "yyyy-MM-dd HH:mm:ss".length());
		
		
		
    }
	
	public static String getStamyyyyMMddHHmmss(String stamp){
		return stamp.substring(0,"yyyy-MM-dd HH:mm:ss".length());
    }
	
	public static void broadcastStartDownloading(Context context){
		
		Intent intent = new Intent();
		intent.setAction(Data.INTENT_ACTION_START_DOWNLOADING);
		
		context.sendBroadcast(intent);
		
	}
	
	public static void broadcastDownloadReporter(Context context){
		
		Intent intent = new Intent();
		intent.setAction(Data.INTENT_ACTION_DOWNLOAD_REPORTER);
		
		context.sendBroadcast(intent);
		
	}
	
	public static void broadcastInstallReporter(Context context){
		
		Intent intent = new Intent();
		intent.setAction(Data.INTENT_ACTION_INSTALL_REPORTER);
		
		context.sendBroadcast(intent);
		
	}
	
	public static void broadcastLaunchReporter(Context context){
		
		Intent intent = new Intent();
		intent.setAction(Data.INTENT_ACTION_LAUNCH_REPORTER);
		
		context.sendBroadcast(intent);
		
	}
	public static void broadcastPackageChange(Context context){
		
		Intent intent = new Intent();
		intent.setAction(Data.INTENT_ACTION_PACKAGE_CHANGE);
		
		context.sendBroadcast(intent);
		
	}
	
	/*
	tickerText提示通知时的文本
	contentTitle你拖动状态栏下来的时候，显示的标题
	contentText你拖动状态栏下来的时候，显示的内容
	id 是一个标识，也许可以多个通知
	resId 图片资源，通知前面的图片
	*/
	
	public static void showNotification(Context context,String tickerText, String contentTitle,
			String contentText, int id, int resId){
                                 //创建通知对象，前面是图片资源，后面参数是当前时间，表示马上接到通知
		NotificationManager notificationManager= (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
		
 		Notification notification = new Notification(resId,
				tickerText, System.currentTimeMillis());
                                  //PendingIntent 表示当你点击通知后，intent执行的内容
 		Intent intent = new Intent(context,AppManagerMy.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_MY);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				((Activity)context).getIntent(), 0);
                                  //通知显示内容
		notification.setLatestEventInfo(context, contentTitle, contentText,
				pendingIntent);

                                  //notification.defaults = defaults;设置为震动，除震动外还有其他的，但是必须在唤醒前调用。

                                  //唤醒
		notificationManager.notify(id, notification);

	}
	
	public static void notifyNewVersion(Context context,String path){
		
		NotificationManager notificationManager= (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
		
		Notification note=new Notification();

		note.flags=Notification.FLAG_AUTO_CANCEL;

		RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);  

		contentView.setTextViewText(R.id.textViewNotificationTime, StampToStringMMddHHmm(System.currentTimeMillis()));  

		note.contentView = contentView;

		note.tickerText="易捷桌面 版本升级啦！";

		// note.sound=Uri.parse("file:///sdcard/good.mp3");

		note.icon= R.drawable.icon_3gstone;
		
		Intent startIntent = new Intent();
		startIntent.setAction(android.content.Intent.ACTION_VIEW);
       
		startIntent.setDataAndType(Uri.fromFile(new File(path)),    
            "application/vnd.android.package-archive");    

		PendingIntent p=PendingIntent.getActivity(context, 0, startIntent , 0);//这个非要不可。

		note.contentIntent=p;

		// note.setLatestEventInfo(TestMapActivity.this, "黄金版电子档", "龙湖英雄在线",p);//如果要添加上面自定义的显示效果就不能添加此句。

		notificationManager.notify(1, note);		
	}

}
