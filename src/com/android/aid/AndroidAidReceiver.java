/**
 * 
 */
package com.android.aid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class AndroidAidReceiver extends BroadcastReceiver {
	
	private static final String TAG = "AndroidAidReceiver";

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.i(TAG,action);
		
		Intent startIntent = new Intent();
		

		startIntent.setClass(context, AndroidAidService.class);
		
		
		if(Intent.ACTION_BOOT_COMPLETED.equals(action)){
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_CLOUD, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_INSTALL_REPORTER, true);
    		startIntent.putExtra(Data.SERVICE_REQUEST_LAUNCH_REPORTER, true);
    		startIntent.putExtra(Data.SERVICE_REQUEST_RECENT_TASK, true);
			
			context.startService(startIntent);
			
		}
		else if(Intent.ACTION_DATE_CHANGED.equals(action)){
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_CLOUD, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_INSTALL_REPORTER, true);
    		startIntent.putExtra(Data.SERVICE_REQUEST_LAUNCH_REPORTER, true);
    		startIntent.putExtra(Data.SERVICE_REQUEST_RECENT_TASK, true);
						
			context.startService(startIntent);	
			
		}
		else if(Intent.ACTION_SCREEN_ON.equals(action)){
			
		}
		else if(Intent.ACTION_SCREEN_OFF.equals(action)){
			
		}
		else if(Intent.ACTION_USER_PRESENT.equals(action)){
			
//			if(!SharedPrefProxy.isCloudUpdatedToday(context)){
				startIntent.putExtra(Data.SERVICE_REQUEST_APP_CLOUD, true);
//			}
			
//			startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
			startIntent.putExtra(Data.SERVICE_REQUEST_INSTALL_REPORTER, true);
    		startIntent.putExtra(Data.SERVICE_REQUEST_LAUNCH_REPORTER, true);
    		startIntent.putExtra(Data.SERVICE_REQUEST_RECENT_TASK, true);
						
			context.startService(startIntent);	
		}
		else if(Intent.ACTION_MEDIA_MOUNTED.equals(action)
				|| Intent.ACTION_MEDIA_UNMOUNTED.equals(action)
				|| Intent.ACTION_MEDIA_REMOVED.equals(action)
				|| Intent.ACTION_MEDIA_EJECT.equals(action)
				|| Intent.ACTION_UMS_CONNECTED.equals(action)
				|| Intent.ACTION_UMS_DISCONNECTED.equals(action)
				){
			startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
			context.startService(startIntent);	
			
		}
		/*
		else if(Intent.ACTION_PACKAGE_ADDED.equals(action) 
				|| Intent.ACTION_PACKAGE_REMOVED.equals(action)
				|| Intent.ACTION_PACKAGE_REPLACED.equals(action)
				){
			
			String packageName = intent.getDataString().substring("package:".length());      
            startIntent.putExtra(Data.INTENT_ACTION, action);
            startIntent.putExtra(Data.INTENT_DATA_PACKAGE, packageName);
            startIntent.putExtra(Data.SERVICE_REQUEST_PACKAGE_CHANGED, true);
			
//			startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
			context.startService(startIntent);	
			
		}
		*/
		else if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            Bundle bundle = intent.getExtras();
            if(bundle.getBoolean(ConnectivityManager.EXTRA_IS_FAILOVER)){
            	
            }
            else if(bundle.getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY)){
            	
            }
            
            else
            {
            	NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            	NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            	if (mobile.isConnected()||wifi.isConnected())
            	{
//            		startIntent.putExtra(Data.SERVICE_REQUEST_APP_CLOUD, true);
            		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
            		startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
            		startIntent.putExtra(Data.SERVICE_REQUEST_INSTALL_REPORTER, true);
            		startIntent.putExtra(Data.SERVICE_REQUEST_LAUNCH_REPORTER, true);
        			            			
//    				startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
//    				startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
            		context.startService(startIntent);	
            	}
            }
		}
		else if(Data.INTENT_ACTION_DOWNLOAD_REPORTER.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
    		context.startService(startIntent);	
		}
		else if(Data.INTENT_ACTION_INSTALL_REPORTER.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_INSTALL_REPORTER, true);
    		context.startService(startIntent);	
		}
		else if(Data.INTENT_ACTION_LAUNCH_REPORTER.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_LAUNCH_REPORTER, true);
    		context.startService(startIntent);	
		}
		else if(Data.INTENT_ACTION_PACKAGE_CHANGE.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
    		context.startService(startIntent);	
		}
		else if(Data.INTENT_ACTION_START_DOWNLOADING.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
    		context.startService(startIntent);	
		}
		else if(Data.INTENT_ACTION_RECENT_TASK.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_RECENT_TASK, true);
    		context.startService(startIntent);	
		}

	}

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#peekService(android.content.Context, android.content.Intent)
	 */
	@Override
	public IBinder peekService(Context myContext, Intent service) {
		// TODO Auto-generated method stub
		return super.peekService(myContext, service);
	}

}
