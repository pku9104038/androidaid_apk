/**
 * 
 */
package com.android.aid;

import android.content.Context;

/**
 * @author wangpeifeng
 *
 */
public class ThreadApkSync extends Thread {
	private static final String TAG = "ThreadApkSync";
	private DataProxy dataproxy;
	private Context context;
	
	private static boolean running = false; 
	
	public ThreadApkSync(Context context,DataProxy dataproxy) {
		super();
		//this.dataproxy = dataproxy;
		this.context = context;
		this.dataproxy = new DataProxy(context);
		
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
		dataproxy.updateAppDownloaded();
		
		running = false;
	}
	
}
