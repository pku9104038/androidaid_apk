/**
 * 
 */
package com.android.aid;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class AndroidAidService extends Service {

	
	private static final String TAG = "AndroidAidService";
	private static Context context;
	private boolean running = false;
	private Thread thread = null;
	
	private int download_state = 1;
	
	//public static ArrayList<ThreadDownload> listThreadDownload;
	
	private DataProxy dataproxy;
	private AndroidAidReceiverScreen reciverScreen;

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		context = getApplication();
		
		dataproxy = new DataProxy(context);

		reciverScreen=new AndroidAidReceiverScreen();  
	    IntentFilter recevierFilter=new IntentFilter();  
	    recevierFilter.addAction(Intent.ACTION_SCREEN_ON);  
	    recevierFilter.addAction(Intent.ACTION_SCREEN_OFF);  
	    registerReceiver(reciverScreen, recevierFilter);  
		
		super.onCreate();
	}

	
	
	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "service start");
		
		if(intent==null){
			super.onStartCommand(intent, flags, startId);
		}
		
		
		
		try{
		
		
		
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_APP_CLOUD, false)){

			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_APP_CLOUD);

			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	        if (mobile.isConnected()||wifi.isConnected())
	        {
	        	checkCloudData();
	        }
			
		}
		
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_APP_DOWNLOADED);

			checkDownloadedApp();
		}
		
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_APP_INSTALLED, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_APP_INSTALLED);
			checkInstalledApp();
		}
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, false)){
			download_state = 1;
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_APP_DOWNLOADING);
			
			startDownloadThread();
		}
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_DOWNLOAD_REPORTER);
			
			startDownloadReporterThread();
		}
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_INSTALL_REPORTER, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_INSTALL_REPORTER);
			
			startInstallReporterThread();
		}
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_PACKAGE_CHANGED, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_PACKAGE_CHANGED);
			checkPackageChange(intent.getStringExtra(Data.INTENT_ACTION),intent.getStringExtra(Data.INTENT_DATA_PACKAGE));
		}
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_LAUNCH_REPORTER, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_LAUNCH_REPORTER);
			
			startLaunchReporterThread();
		}
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_RECENT_TASK, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_RECENT_TASK);
			
			//startRecentTaskThread();
		}
		if(intent.getBooleanExtra(Data.SERVICE_REQUEST_CURRENT_TASK_START, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_CURRENT_TASK_START);
			
			startCurrentTaskThread();
		}if(intent.getBooleanExtra(Data.SERVICE_REQUEST_CURRENT_TASK_STOP, false)){
			Log.i(TAG, "service request:"+Data.SERVICE_REQUEST_CURRENT_TASK_STOP);
			
			stopCurrentTaskThread();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return super.onStartCommand(intent, flags, startId);
	}



	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
//		running = false;
		
		unregisterReceiver(reciverScreen);
		super.onDestroy();
	}

	
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	

	private void checkPackageChange(String action, String packageName){
		Log.i(TAG, action);
		Log.i(TAG, packageName);
//		if(!ThreadPackageChange.isRunning()){
			ThreadPackageChange thread = new ThreadPackageChange(context,dataproxy,action,packageName);
		
			thread.start();
//		}
		
	}
	
	private void checkCloudData(){
		
		
		if(!ThreadCloudSync.isRunning()){
			if(SharedPrefProxy.isCloudUpdatedRequest(context)){

				ThreadCloudSync thread = new ThreadCloudSync(context,dataproxy);
				thread.start();
				Log.i(TAG, "update request!");
				SharedPrefProxy.setCloudUpdateAuto(context);
			}
			else{
				if(!SharedPrefProxy.isCloudUpdatedToday(context)){

					ThreadCloudSync thread = new ThreadCloudSync(context,dataproxy);
					thread.start();
					
					Log.i(TAG, "Today update auto!");
					
				}
				else{
					ThreadCloudSync thread = new ThreadCloudSync(context,dataproxy);
					thread.start();
					Log.i(TAG, "today updated!");
				}
			}
		}
	
	}
	
	private void checkDownloadedApp(){
		if(!ThreadApkSync.isRunning()){

			ThreadApkSync thread = new ThreadApkSync(context,dataproxy);
		
			thread.start();
		}
	
	}
	
	private void checkInstalledApp(){
		
		if(!ThreadPackageSync.isRunning()){

			ThreadPackageSync thread = new ThreadPackageSync(context,dataproxy);

			thread.start();
		}

	
	}
	
	
	
	private void startDownloadThread(){
		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
//		Log.i(TAG, "startDownloadThread");
		
		try{
			listApps = dataproxy.queryAppDownloading(listApps,Data.DB_VALUE_QUERY_DOWNLOADING_SILENCE);
			Log.i(TAG, "downloading files = " + listApps.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Iterator iterator = listApps.iterator();
	
		
		while(iterator.hasNext()){
			
			AppInfoData appInfoData = (AppInfoData) iterator.next();
			
			boolean thread_running = false;
			/*
			for(int i=0;i<ThreadDownload.listThreadDownload.size();i++){
				if(ThreadDownload.listThreadDownload.get(i).getPath().equals(appInfoData.getPath())){
					thread_running = true;
					Log.i(TAG, "ThreadDownload "+ i + ": "+appInfoData.getFileName()+" "+ThreadDownload.listThreadDownload.get(i).getState().toString());
					break;
				}
			}
			if(!thread_running){
				ThreadDownload thread = new ThreadDownload(context,appInfoData,dataproxy);
				ThreadDownload.listThreadDownload.add(thread);
				thread.start();
				Log.i(TAG, "ThreadDownload start " + ThreadDownload.listThreadDownload.size() +": "+  appInfoData.getFileName());
			}
			*/
			
			for(int i=0;i<ThreadDownload.getListSize();i++){
				if(ThreadDownload.isDownloading(appInfoData.getPackage())){
					thread_running = true;
					Log.i(TAG, "ThreadDownload "+ i + ": "+appInfoData.getFileName()+" "+ThreadDownload.getThread(i).getState().toString());
					break;
				}
			}
			if(!thread_running){
				ThreadDownload thread = new ThreadDownload(context,appInfoData,dataproxy);
				ThreadDownload.addThread(thread);
				thread.start();
				Log.i(TAG, "ThreadDownload start " + ThreadDownload.getListSize() +": "+  appInfoData.getFileName());
			}
			
			
	
		}
	}
	
	private void startDownloadReporterThread(){
		
		if(!ThreadDownloadReporter.isRunning()){
			ThreadDownloadReporter thread = new ThreadDownloadReporter(context, dataproxy);
			thread.start();
		}
	}

	private void startInstallReporterThread(){
		
		if(!ThreadInstallReporter.isRunning()){
			ThreadInstallReporter thread = new ThreadInstallReporter(context, dataproxy);
			thread.start();
		}
	}
	
	private void startLaunchReporterThread(){
		
		if(!ThreadLaunchReporter.isRunning()){
			ThreadLaunchReporter thread = new ThreadLaunchReporter(context, dataproxy);
			thread.start();
		}
	}

	private void startRecentTaskThread(){
		
		if(!ThreadRecentTask.isRunning()){
			ThreadRecentTask thread = new ThreadRecentTask(context, dataproxy);
			thread.start();
		}
	}
	
	private void startCurrentTaskThread(){
		
		if(!ThreadCurrentTask.isRunning()){
			ThreadCurrentTask thread = new ThreadCurrentTask(context, dataproxy);
			thread.start();
		}
	}
	private void stopCurrentTaskThread(){
		
		ThreadCurrentTask.stopRunning();
	}


}
