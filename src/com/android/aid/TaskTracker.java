/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class TaskTracker {
	private static final String TAG = "TaskTracker";
	private Context context;

	/**
	 * @param context
	 */
	public TaskTracker(Context context) {
		super();
		this.context = context;
	}
	
	

	//RunningServicesInfo
    public  String getRunningServicesInfo() {

            StringBuffer serviceInfo = new StringBuffer();
            final ActivityManager activityManager = (ActivityManager) context
                            .getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningServiceInfo> services = activityManager.getRunningServices(100);

            int i =0;
            Iterator<RunningServiceInfo> l = services.iterator();
            while (l.hasNext()) {
                    
            	RunningServiceInfo si = (RunningServiceInfo) l.next();
                i++;
            	serviceInfo.append("\nservice No."+i+":").append(si.service);
                
            	serviceInfo.append("\nactiveSince: ").append(stamp2string(si.activeSince));
                
            	serviceInfo.append("\nlastActivityTime: ").append(stamp2string(si.lastActivityTime));
                
            	serviceInfo.append("\n\n");
            }
            return serviceInfo.toString();
    } 
    
    public boolean checkRunningService(String component){
    	
    	 
    	final ActivityManager activityManager = (ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> services = activityManager.getRunningServices(100);

        int i =0;
        Iterator<RunningServiceInfo> l = services.iterator();
        while (l.hasNext()) {
                
        	RunningServiceInfo si = (RunningServiceInfo) l.next();
            i++;
            StringBuffer serviceInfo = new StringBuffer();
            String comInfo = serviceInfo.append(si.service).toString();
        	if(comInfo.equals(component)){
        		return true;
        	}
            
        }
    	return false;
    }
    
    private String stamp2string(long stampIn){
    	
    	long stamp = stampIn/1000;
    	String strTime = null;
    	long day = 60*60*24;
    	long hour = 60*60;
    	long minute = 60;
    	long second = 1;
    	strTime = ""+(int)(stamp/day)+"Days" 
    				+(int)(stamp%day/hour) + ":" 
    			+ (int)(stamp%hour/minute) + ":"
    			+ (int)(stamp%second);
    	return strTime;
    	
    	
    }
    
    public String getRunningTaskInfo(){
    	
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RunningTaskInfo> forGroundActivity = activityManager.getRunningTasks(100); 
		
		StringBuffer taskInfo = new StringBuffer();
		taskInfo.append("RunningTask");
		 
		int i = 0;
		Iterator<RunningTaskInfo> l = forGroundActivity.iterator();
		 while (l.hasNext()) {
			 RunningTaskInfo ri = (RunningTaskInfo) l.next();
			 i++;
			 taskInfo.append("\nbaseActivity No." + i +":").append(ri.baseActivity);
			 taskInfo.append("\ntopActivity: ").append(ri.topActivity);
			 taskInfo.append("\nnumActivities: ").append(ri.numActivities);
			 taskInfo.append("\nnumRunning: ").append(ri.numRunning);
			 taskInfo.append("\n\n");
			 ri.baseActivity.getPackageName();
		 }
		 return taskInfo.toString();
		
	}
	
    public String getRunningPackage(){
    	
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RunningTaskInfo> forGroundActivity = activityManager.getRunningTasks(1); 
		
		 
		return forGroundActivity.get(0).baseActivity.getPackageName();
		
	}
    
    public String getRecentTaskInfo(){
    	
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RecentTaskInfo> forRecentTask = activityManager.getRecentTasks(20, ActivityManager.RECENT_WITH_EXCLUDED); 
		
		StringBuffer taskInfo = new StringBuffer();
		int i=0;
		Iterator<RecentTaskInfo> l = forRecentTask.iterator();
		 while (l.hasNext()) {
			 RecentTaskInfo ri = (RecentTaskInfo) l.next();
			 i++;
			 taskInfo.append("RecentTask No."+i+":").append(ri.baseIntent.getComponent());
			 //taskInfo.append("\norigActivity: ").append(ri.origActivity);
			 //taskInfo.append("\nIntentAction: ").append(ri.baseIntent.getAction());
			 
			 taskInfo.append("\n\n");
		 }
		 return taskInfo.toString();
		
    }
    
    public ArrayList<String> getRecentTaskPackageName(ArrayList<String> list){
 //   	ArrayList<String> list = new ArrayList<String>();
		
    	ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RecentTaskInfo> forRecentTask = activityManager.getRecentTasks(50, ActivityManager.RECENT_WITH_EXCLUDED); 
		
		int i=0;
		
		int length = forRecentTask.size();		
		
		Log.i(TAG, "length="+length);
		
		while (i<length) {
		
			Log.i(TAG, "i="+i);
			RecentTaskInfo ri = forRecentTask.get(i);
			Intent intent = ri.baseIntent;
			Set<String> category = intent.getCategories();
			if(category!=null){
				if(category.contains("android.intent.category.LAUNCHER")
						&& "android.intent.action.MAIN".equals(intent.getAction())){
					String packageName = new String(ri.baseIntent.getComponent().getPackageName());
					
					list.add(packageName);
				}
			}
			i++;
		 
		}	
    	return list;
    	
    }
    
    public Intent getRecentTaskIntent(String packageName){
    	Intent intent = null;
    	
    	ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
		List<RecentTaskInfo> forRecentTask = activityManager.getRecentTasks(50, ActivityManager.RECENT_WITH_EXCLUDED); 
		
		int i=0;
		
		int length = forRecentTask.size();		
		
		Log.i(TAG, "length="+length);
		
		while (i<length) {
		
			Log.i(TAG, "i="+i);
			RecentTaskInfo ri = forRecentTask.get(i);
			intent = new Intent(ri.baseIntent);
			String name = intent.getComponent().getPackageName();
			if(packageName.equals(name))
				return intent;
			else
				intent = null;
			
			i++;
		 
		}	
    	
    	
    	return intent;
    	
    }

}
