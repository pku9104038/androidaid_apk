/**
 * 
 */
package com.android.aid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class AndroidAidPackageReceiver extends BroadcastReceiver {
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
		
		if(Intent.ACTION_PACKAGE_ADDED.equals(action) 
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

	}

}
