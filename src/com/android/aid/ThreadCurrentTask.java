/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

/**
 * @author wangpeifeng
 *
 */
public class ThreadCurrentTask extends Thread {
	
	private static final String TAG = "ThreadCurrentTask";
	private DataProxy dataproxy;
	private Context context;
	private static boolean running = false;
	
	public ThreadCurrentTask(Context context,DataProxy dataproxy) {
		super();
		//this.dataproxy = dataproxy;
		this.context = context;
		this.dataproxy = dataproxy;
		
		running = true;
	}
	
	public static boolean isRunning(){
		return running;
	}
	
	public static void stopRunning(){
		running = false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		TaskTracker taskTracker = new TaskTracker(context);
		
		while(running){
			String packageName = taskTracker.getRunningPackage();
			if(!packageName.equals(SharedPrefProxy.getLatestTaskPackage(context))){
				dataproxy.insertLauchReporter(packageName, Data.DB_VALUE_NO);
				SharedPrefProxy.setLatestTaskPackage(context, packageName);
				Common.broadcastLaunchReporter(context);
			}
			try{
				sleep(1000*60);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
	}
	
}
