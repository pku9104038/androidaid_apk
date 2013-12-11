/**
 * 
 */
package com.android.aid;

import android.content.Context;
import android.content.Intent;

/**
 * @author wangpeifeng
 *
 */
public class ThreadCloudSync extends Thread {
	private static final String TAG = "ThreadCloudSync";
	private DataProxy dataproxy;
	private Context context;
	private static boolean running;
	private static boolean config_checked, repo_checked, version_checked;
	
	public ThreadCloudSync(Context context,DataProxy dataproxy) {
		super();
		//this.dataproxy = dataproxy;
		this.context = context;
		this.dataproxy = dataproxy;
		
	}
	public static boolean isRunning(){
		return running;
	}
	
	public static boolean isConfigChecked(){
		return config_checked;
	}
	public static boolean isRepoChecked(){
		return repo_checked;
	}
	public static boolean isVersionChecked(){
		return version_checked;
	}
	public static void clearChecked(){
		config_checked = false;
		repo_checked = false;
		version_checked = false;
			
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		running = true;

		clearChecked();
		
		HttpProxy httpproxy = new HttpProxy(context);
		
		httpproxy.updateConfigFile();
		
		config_checked = true;
		
		// keep it for category mapping
		dataproxy.updateCategoryManager();
		
		dataproxy.updateAppRepository();

		repo_checked = true;
		
		dataproxy.updateVersionRepository();
		
		version_checked = true;
		
		SharedPrefProxy.setCloudUpdateNow(context);
		
//		httpproxy.checkServerCode();
		
		Intent startIntent = new Intent();
		startIntent.setClass(context, AndroidAidService.class);
		
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);

		context.startService(startIntent);
			
		running = false;

	}
	
	

}
