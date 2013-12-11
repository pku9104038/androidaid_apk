/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author wangpeifeng
 *
 */
public class AppManagerActivity extends Activity {
	private static final String TAG = "AppManagerActivity";
	
	private static final int HANDLER_MSG_WHAT_DOWNLOADING = 1;
	private static final int HANDLER_MSG_WHAT_RENEW = HANDLER_MSG_WHAT_DOWNLOADING + 1;
	private static final int HANDLER_MSG_WHAT_WAITING = HANDLER_MSG_WHAT_RENEW + 1;
	private static final int HANDLER_MSG_WHAT_NULL = HANDLER_MSG_WHAT_WAITING + 1;
	
	
	private View popupView = null;
	private WindowManager.LayoutParams wmParams;
	private ImageButton imageButtonBack;
	
	private ProgressBar progressBar;
	private TextView textViewProgress;
	private Thread thread = null;
	private boolean running = false;
	private AppInfoData appDownloading = null;
	private AppInfoData appInfoData = null;

	//private ArrayList<AppInfoData> listApps;

	private Context context;
	private DataProxy dataproxy;
	private String name;
	private GridView gridView;
	private GridAdapterIcon gridAdapter;
	ArrayList<AppInfoData> listApps;
	
	private int versionIcon = 0;

	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		name = intent.getStringExtra(Data.APP_REQUEST);
		
		context = this;
		dataproxy = new DataProxy(context);
		
//		listApps = new ArrayList<AppInfoData>();
		
		appInfoData = new AppInfoData(context);
		
/*	move to checkAppCategory
		listApps = new ArrayList<AppInfoData>();
		if(dataproxy.newVersionReady(appInfoData)){
			versionIcon = 1;
			appInfoData.setLabel(Data.DB_VALUE_VERSION_UPDATE);
			appInfoData.setLocation(Data.DB_VALUE_LOCATION_DOWNLOADED);
			FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
			listApps.add(appInfoData);
			Toast.makeText(context, 
					//getResources().getString(R.string.app_name)+
					Data.TOAST_NEW_VERSOIN_READY, Toast.LENGTH_LONG).show();

		}
*/
/*		
		if(dataproxy.newVersionReady(appInfoData)){
//			updateConfirm();
			Toast.makeText(context, 
					//getResources().getString(R.string.app_name)+
					"易捷桌面 版本升级啦！ 请进入\"自定义\"升级体验最新应用！", Toast.LENGTH_LONG).show();
			
//			Common.showNotification(context, "版本升级", "版本升级!", "新版本就绪.", 0, R.drawable.app_icon);
		}

*/
		
		setTheme(R.style.Transparent);
		setContentView(R.layout.appmanager);

		gridView = (GridView)findViewById(R.id.gridView);
		
		listApps = checkAppCategory(name);
		
    
		gridAdapter = new GridAdapterIcon(this,listApps);
    
		gridView.setAdapter(gridAdapter);
    
		gridView.setOnItemClickListener(listenerGrid);
	
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(name);
	
		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);

		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		textViewProgress = (TextView)findViewById(R.id.textViewProgress);

/*		
        if(listApps.size()<=versionIcon){
    		progressBar.setVisibility(View.VISIBLE);
			textViewProgress.setVisibility(View.VISIBLE);
			textViewProgress.setText("?");
        	Thread thread = new Thread(waiting);
        	thread.start();
        }
        */
		
		
    	Thread thread = new Thread(updating);
    	thread.start();
    
		
/*		
		inflater = LayoutInflater.from(context);

		popupView = null;
		
		windowsManager = null;
		
		

		try{
			
			
			popupView = inflater.inflate(R.layout.appmanager, null);
		
		
			gridView = (GridView)popupView.findViewById(R.id.gridView);

			listApps = checkAppCategory2(name);
			
        
			gridAdapter = new GridAdapterIcon(this,listApps);
        
			gridView.setAdapter(gridAdapter);
        
			gridView.setOnItemClickListener(listenerGrid);
		
			TextView tvName = (TextView)popupView.findViewById(R.id.textViewName);
			tvName.setText(name);
		
			imageButtonBack = (ImageButton)popupView.findViewById(R.id.imageButtonBack);
			imageButtonBack.setOnClickListener(listenerButton);

			progressBar = (ProgressBar)popupView.findViewById(R.id.progressBar);
			textViewProgress = (TextView)popupView.findViewById(R.id.textViewProgress);
			
			windowsManager=(WindowManager)getApplicationContext().getSystemService("window");
	        wmParams = new WindowManager.LayoutParams();
	 
//
//	         *以下都是WindowManager.LayoutParams的相关属性
//	         * 具体用途请参考SDK文档
//
	        wmParams.type=2002;   //这里是关键，你也可以试试2003
	        wmParams.format=1;
	 
//	         *这里的flags也很关键
//	         *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
//	         *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）

	        wmParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;//;
//   		|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;     
	        
//	        		|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;     
	        wmParams.width=WindowManager.LayoutParams.MATCH_PARENT;
	        wmParams.height=WindowManager.LayoutParams.MATCH_PARENT;
	        
	        if(listApps.size()==0){
	    		progressBar.setVisibility(View.VISIBLE);
				textViewProgress.setVisibility(View.VISIBLE);
				textViewProgress.setText("?");
	        	Thread thread = new Thread(waiting);
	        	thread.start();
	        }

			windowsManager.addView(popupView, wmParams);  //创建View
	        
		}
		catch(Exception e){
				e.printStackTrace();
		}

*/		
		super.onCreate(savedInstanceState);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		finish();
		super.onStop();
	}




	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
/*
		if(popupView!=null && windowsManager!=null){
			try{
				windowsManager.removeView(popupView);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
*/	
		running = false;
		this.setResult(0);
		super.onDestroy();
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			finish();
			return true;
			
		}
		return super.onKeyDown(keyCode, event);
	}


	private OnClickListener listenerButton = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			finish();
			
			
		}
	};
	
	
	private OnItemClickListener listenerGrid = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub

			//appInfoData = listApps.get(position);
			appInfoData = (AppInfoData) gridAdapter.getItem(position);
			if(Data.DB_VALUE_LOCATION_INSTALLED.equals(appInfoData.getLocation())){
				dataproxy.insertLauchReporter(appInfoData.getPackage(), Data.DB_VALUE_YES);
				Common.broadcastLaunchReporter(context);
				Intent startIntent = new Intent();
				startIntent = getPackageManager().getLaunchIntentForPackage(    
			                appInfoData.getPackage());  
				startActivityForResult(startIntent,0);	
				//finish();
				
			}
			
			else if (Data.DB_VALUE_LOCATION_DOWNLOADED.equals(appInfoData.getLocation())){
				File file = new File(appInfoData.getPath());
				if(file.exists()){
		        // 设置目标应用安装包路径    
					Intent startIntent = new Intent();
					startIntent.setAction(android.content.Intent.ACTION_VIEW);
	               
					startIntent.setDataAndType(Uri.fromFile(file),    
		                "application/vnd.android.package-archive");    
					startActivityForResult(startIntent,0); 
			        finish();
				}
		        else{
		        	Toast.makeText(context, "应用文件已失效，需下载更新...", Toast.LENGTH_LONG).show();

		        	dataproxy.startDownloading(appInfoData,Data.DB_VALUE_NO);		
					startDownloading();
					
					Intent startIntent = new Intent(context, AndroidAidService.class);
					startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
					
					startService(startIntent);	
		        	
		        }
			}
			
			else if (Data.DB_VALUE_LOCATION_DOWNLOADING.equals(appInfoData.getLocation())){
				startDownloading();
				Log.i(TAG, "downloading");
				
			}
			
			else if(Data.DB_VALUE_LOCATION_CLOUD.equals(appInfoData.getLocation())){
				Log.i(TAG, "cloud");
				dataproxy.startDownloading(appInfoData,Data.DB_VALUE_NO);		
				startDownloading();
				
				//update info for progress tracker
/*
				ArrayList<AppInfoData> listApps = checkAppCategory(name);
        		gridAdapter = new GridAdapterIcon(context,listApps);
    			gridView.setAdapter(gridAdapter);
*/
				
			}
			
			
		}
    	
    };
    
    
    private void startDownloading(){

    	
		
		Intent startIntent = new Intent(context, AndroidAidService.class);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
		
		startService(startIntent);	
/*		
		Log.i(TAG, "start AndroidAidService");
		
		appDownloading = appInfoData;
		
		progressBar.setVisibility(View.VISIBLE);

		textViewProgress.setVisibility(View.VISIBLE);
		textViewProgress.setText("?");
		
		thread = new Thread(downloading);
		running = true;
		thread.start();
	
*/    	
    }

	private ArrayList<AppInfoData> checkAppCategory(String category){

		ArrayList<AppInfoData> listApps = new ArrayList<AppInfoData>();
		if(dataproxy.newVersionReady(appInfoData)){
			versionIcon = 1;
			appInfoData.setLabel(Data.DB_VALUE_VERSION_UPDATE);
			appInfoData.setPackage(Data.DB_VALUE_PACKAGE_ANDROIDAID+".apk");
			
			appInfoData.setLocation(Data.DB_VALUE_LOCATION_DOWNLOADED);
			FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
			listApps.add(appInfoData);
//			Toast.makeText(context, 
					//getResources().getString(R.string.app_name)+
//					Data.TOAST_NEW_VERSOIN_READY, Toast.LENGTH_LONG).show();

		}
		
		listApps = dataproxy.queryAppMy(listApps,category);
		listApps = dataproxy.queryAppRepoNotInMy(listApps,category);
		
		return listApps;
		//return dataproxy.queryAppRepository(listApps,category);
		
	}
	
	private ArrayList<AppInfoData> updateAppDownloading(ArrayList<AppInfoData> listApps){

		Iterator<AppInfoData> iterator = listApps.iterator();
		while(iterator.hasNext()){
			AppInfoData info = iterator.next();
			if(!dataproxy.isAppInstalled(info))
				if(!dataproxy.isAppDownloaded(info))
					dataproxy.isAppDownloading(info);

		}
		return listApps;
		
	}
/*	
    Runnable waiting = new Runnable()

    {

    	public void run() {
		// TODO Auto-generated method stub
    		int waiting = 10;
    		do{
    			try{
    				Message msg = new Message();
    				Bundle bundle = new Bundle();
        			
        			bundle.putInt("waiting", waiting);
        			msg.setData(bundle);
        			msg.what = HANDLER_MSG_WHAT_WAITING;
        			handler.sendMessage(msg);
        			
        			Thread.sleep(1500);
        			listApps = checkAppCategory(name);
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    			waiting--;
    			
    		}while(listApps.size()<=versionIcon && waiting > 0);
    		
    		if(listApps.size()>versionIcon){
    			Message msg = new Message();
    			msg.what = HANDLER_MSG_WHAT_RENEW;
    			handler.sendMessage(msg);
    		}
    		else{
    			Message msg = new Message();
    			msg.what = HANDLER_MSG_WHAT_NULL;
    			handler.sendMessage(msg);
    		}

    	}
    		
    };

*/
    Runnable updating = new Runnable()

    {

    	public void run() {
		// TODO Auto-generated method stub
    		running = true;
  	   		do{
        			try{
            			Thread.sleep(1500);
//            			listApps = checkAppCategory(name);

            			Message msg = new Message();
            			msg.what = HANDLER_MSG_WHAT_RENEW;
            			handler.sendMessage(msg);
            			
        			}catch(Exception e){
        				e.printStackTrace();
        			}
        			
       		}while(running);
 
    	}
    		
    };


    Runnable downloading = new Runnable()

    {

    	public void run() {
		// TODO Auto-generated method stub
    		
    		boolean progressFlag = false;
    		
    		while(running){
			

   				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+appDownloading.getFileName());
   				
   				int indexOfThread = ThreadDownload.getIndexOf(appDownloading.getPackage());
   				
   				if(file.exists()){
   					
       				Intent startIntent = new Intent();
       				startIntent.setAction(android.content.Intent.ACTION_VIEW);
       				startIntent.setDataAndType(Uri.fromFile(file),    
       		                "application/vnd.android.package-archive");    
       		        startActivity(startIntent); 

       		        Message msg = new Message();
        			msg.what = HANDLER_MSG_WHAT_RENEW;
        			handler.sendMessage(msg);
       		        
       				running=false;
    			}
   				
    			else if(DataProxy.mapThreadDownload.containsKey(appDownloading.getPackage())){
	    				
    				Log.i(TAG, "Thread counting");
	    				    				
    				String packageName = appDownloading.getPackage();
//    				long filelength = ThreadDownload.getFileLength(packageName);
//    				long savedlength = ThreadDownload.getDownloadLength(packageName);
    				long filelength = DataProxy.mapThreadDownload.get(packageName).getFileLength();      				
    				long savedlength = DataProxy.mapThreadDownload.get(packageName).getDownloadLength();  
    				
    				progressFlag = !progressFlag;
    				
    				Log.i(TAG, packageName+" : "
    						+ filelength + "/"
    						+ savedlength );

    				Message msg = new Message();
    				Bundle bundle = new Bundle();
	        		bundle.putBoolean("FLAG", progressFlag);
	        		
        			bundle.putLong("filelength", filelength);
        			bundle.putLong("savedlength", savedlength);
        			msg.setData(bundle);

    				msg.what = HANDLER_MSG_WHAT_DOWNLOADING;
    				handler.sendMessage(msg);
      			}
    			
    			else{
    	    		int waiting = 10;
    	    		do{
    	    			try{
    	    				Message msg = new Message();
    	    				Bundle bundle = new Bundle();
    	        			
    	        			bundle.putInt("waiting", waiting);
    	        			msg.setData(bundle);
    	        			msg.what = HANDLER_MSG_WHAT_WAITING;
    	        			handler.sendMessage(msg);
    	        			
    	        			Thread.sleep(1500);
    	        			
    	    			}catch(Exception e){
    	    				e.printStackTrace();
    	    			}
    	    			waiting--;
    	    			
    	    			if(DataProxy.mapThreadDownload.containsKey(appDownloading.getPackage())){
    	    				waiting = 0;
    	    				Log.i(TAG, "Thread running");
    	    			}
    	    			else{
    	    				Log.i(TAG, "Thread waiting");
    	    			}
    	    			
    	    		}while(waiting > 0);
    	    		
    	    		if(DataProxy.mapThreadDownload.containsKey(appDownloading.getPackage())){
//    	   				Log.i(TAG, "Thread counting");
    	   				
        				String packageName = appDownloading.getPackage();
//        				long filelength = ThreadDownload.getFileLength(packageName);
//        				long savedlength = ThreadDownload.getDownloadLength(packageName);
        				long filelength = DataProxy.mapThreadDownload.get(packageName).getFileLength();      				
        				long savedlength = DataProxy.mapThreadDownload.get(packageName).getDownloadLength();  
        				
        				Log.i(TAG, packageName+":"+filelength + "/"	+ savedlength );

        				Message msg = new Message();
        				Bundle bundle = new Bundle();
    	        			
            			bundle.putLong("filelength", filelength);
            			bundle.putLong("savedlength", savedlength);
            			msg.setData(bundle);

        				msg.what = HANDLER_MSG_WHAT_DOWNLOADING;
        				handler.sendMessage(msg);
    	    		}
    	    		else{
    	    			Message msg = new Message();
    	    			msg.what = HANDLER_MSG_WHAT_NULL;
    	    			handler.sendMessage(msg);
    	    			running = false;
    	    		}
    	     				
    			}
   				

    			if(appDownloading!=null){
    			}
    			
    			try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
	
    		}
    		finish();
    	}
    };
    
    
	public Handler handler = new Handler()

    {

             
    	@Override

             
    	public void handleMessage(Message msg)

    	{
       		switch(msg.what){
       		
    		case HANDLER_MSG_WHAT_DOWNLOADING:
    			/*
   				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+appDownloading.getFileName());
   				if(file.exists()){
   					
       				Intent startIntent = new Intent();
       				startIntent.setAction(android.content.Intent.ACTION_VIEW);
       				startIntent.setDataAndType(Uri.fromFile(file),    
       		                "application/vnd.android.package-archive");    
       		        startActivity(startIntent); 
       		        
       				running=false;
       				finish();
    			}
    			else{
    				String packageName = appDownloading.getPackage();
    				long filelength = ThreadDownload.getFileLength(packageName);
    				long savedlength = ThreadDownload.getDownloadLength(packageName);
    				
    				Log.i(TAG, ThreadDownload.getIndexOf(appDownloading.getPackage())+ " : "
    						+ filelength + "/"
    						+ savedlength );
				
    				if(filelength > 0
    						&&	savedlength > -1){
    					textViewProgress.setText(""+(int)(savedlength*100/filelength)+"%");
    				}
       				else{
       					textViewProgress.setText("?%");
    				}
    			}
    			*/
    			
				long filelength = msg.getData().getLong("filelength");
				long savedlength = msg.getData().getLong("savedlength");
				boolean flag = msg.getData().getBoolean("FLAG");
				
				if(flag){
					if(filelength > 0 &&	savedlength > -1){
						textViewProgress.setText(""+(int)(savedlength*100/filelength)+"%");
					}
					else{
						textViewProgress.setText("?%");
					}
				}
				else{
					textViewProgress.setText(appDownloading.getLabel());

				}
    			
    			break;
    			
    		case HANDLER_MSG_WHAT_WAITING:
    			Log.i(TAG, "waiting "+msg.getData().getInt("waiting"));
    			textViewProgress.setText(""+msg.getData().getInt("waiting"));
    			
    			break;

    		case HANDLER_MSG_WHAT_NULL:
    			
    			progressBar.setVisibility(View.INVISIBLE);
        		
    			textViewProgress.setText("后台进程处理中...");
    			
    			
    			break;
    		
    		case HANDLER_MSG_WHAT_RENEW:
    			
        		progressBar.setVisibility(View.INVISIBLE);
        		textViewProgress.setVisibility(View.INVISIBLE);
/*
    			ArrayList<AppInfoData> listApps = checkAppCategory(name);

        		gridAdapter = new GridAdapterIcon(context,listApps);
    			gridView.setAdapter(gridAdapter);
*/    			
    			updateAppDownloading(listApps);

        		gridAdapter.notifyDataSetChanged();
        		gridView.invalidate();
        		
    			break;
    			
       		}
    	}
    	
    };
    

}
