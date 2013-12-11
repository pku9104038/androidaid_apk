/**
 * 
 */
package com.android.aid;

import org.json.JSONObject;

import android.content.Context;

/**
 * @author wangpeifeng
 *
 */
public class ThreadPackageChange extends Thread {
	
	private static final String TAG = "ThreadPackageChange";
	
	private Context context;
	private DataProxy dataproxy;
	private String action;
	private String packageName;
	private static boolean running = false;
	
	public ThreadPackageChange(Context context, DataProxy dataproxy,String action,String packageName) {
		super();
		this.context = context;
		this.dataproxy = dataproxy;
		this.action = action;
		this.packageName = packageName;
		running = true;
	}
	
	public static boolean isRunning(){
		return running;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		if(dataproxy.isPackageInRepo(packageName)){
			dataproxy.insertInstallReporter(packageName,action);
			Common.broadcastInstallReporter(context);
		}
		Common.broadcastPackageChange(context);
		
		running = false;
		
	}
	
}
