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
public class ThreadRecentTask extends Thread {
	
	private static final String TAG = "ThreadRecentTask";
	private DataProxy dataproxy;
	private Context context;
	private static boolean running = false;
	
	public ThreadRecentTask(Context context,DataProxy dataproxy) {
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
		TaskTracker taskTracker = new TaskTracker(context);
		ArrayList<String> list = new ArrayList<String>();
		taskTracker.getRecentTaskPackageName(list);
		
		if(list.size()>0){
			String latestTask = SharedPrefProxy.getLatestTaskPackage(context);
			Iterator<String> iterator = list.iterator();
			while(iterator.hasNext()){
				String packageName = iterator.next();
				if(latestTask.equals(packageName)){
					break;
				}
				else{
					
				}
			}
		}
		dataproxy.updateAppInstalled();
		running = false;
	}
	
}
