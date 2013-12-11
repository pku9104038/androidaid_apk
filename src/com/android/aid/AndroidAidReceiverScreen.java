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
public class AndroidAidReceiverScreen extends BroadcastReceiver {
	private static final String TAG = "AndroidAidReceiverScreen";

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
		
		if(Intent.ACTION_SCREEN_ON.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_CURRENT_TASK_START, true);
    		context.startService(startIntent);	
			
		}
		else if(Intent.ACTION_SCREEN_OFF.equals(action)){
    		startIntent.putExtra(Data.SERVICE_REQUEST_CURRENT_TASK_STOP, true);
						
			context.startService(startIntent);	
			
		}
		

	}

}
