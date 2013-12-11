/**
 * 
 */
package com.android.aid;

import android.content.Context;

/**
 * @author wangpeifeng
 *
 */
public class ThreadPackageSync extends Thread {
	
	private static final String TAG = "ThreadPackageSync";
	private DataProxy dataproxy;
	private Context context;
	private static boolean running = false;
	
	public ThreadPackageSync(Context context,DataProxy dataproxy) {
		super();
		//this.dataproxy = dataproxy;
		this.context = context;
		this.dataproxy = dataproxy;
		
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
		dataproxy.updateAppInstalled();
		running = false;
	}
	
}
