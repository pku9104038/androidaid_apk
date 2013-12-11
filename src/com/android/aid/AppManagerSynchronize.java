/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author wangpeifeng
 *
 */
public class AppManagerSynchronize extends Activity {
	
	private static final String TAG = "AppManagerSynchronize";
	
	
	private Context context;
//	private DataProxy dataproxy;
	
	private ListView listView;
	private ListAdapterSynchronize listAdapter; 
//	private CheckBox checkBox;
	private Button buttonSet;
	private Button buttonCancel;
	private ImageButton imageButtonBack;

	private ArrayList<AppInfoData> listApps;
	
	private boolean running;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		context = this;
//		dataproxy = new DataProxy(context);
		
		SharedPrefProxy.setCloudUpdateRequest(context);
	
		setTheme(R.style.Transparent);
		setContentView(R.layout.appsynchronize);
		
		listView = (ListView)findViewById(R.id.listView);
	    
		listApps = new ArrayList<AppInfoData>();
		
		AppInfoData appInfoData;
		
		appInfoData = new AppInfoData(context);
		appInfoData.setLabel(Data.APP_SYNCHRONIZATION_CONFIG);
		appInfoData.setLocation(Data.DB_VALUE_LOCATION_INTERNAL);
		appInfoData.setIcon(context.getResources().getDrawable(R.drawable.menu_icon_refresh_lib));
		listApps.add(appInfoData);

		appInfoData = new AppInfoData(context);
		appInfoData.setLabel(Data.APP_SYNCHRONIZATION_REPO);
		appInfoData.setLocation(Data.DB_VALUE_LOCATION_INTERNAL);
		appInfoData.setIcon(context.getResources().getDrawable(R.drawable.menu_icon_refresh_lib));
		listApps.add(appInfoData);

		appInfoData = new AppInfoData(context);
		appInfoData.setLabel(Data.APP_SYNCHRONIZATION_VERSION);
		appInfoData.setLocation(Data.DB_VALUE_LOCATION_INTERNAL);
		appInfoData.setIcon(context.getResources().getDrawable(R.drawable.menu_icon_refresh_lib));
		listApps.add(appInfoData);
		
		listAdapter = new ListAdapterSynchronize(this,listApps);

		listView.setAdapter(listAdapter);
	
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(Data.APP_SYNCHRONIZATION_TITLE);
	
		buttonSet = (Button)findViewById(R.id.buttonSet);
		buttonSet.setOnClickListener(listenerButton);
		buttonSet.setText(R.string.stop);
		
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(listenerButton);
		buttonCancel.setText(R.string.back);

		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);
		
		Thread thread = new Thread(updating);
    	thread.start();
    	
		Intent startIntent = new Intent();
		Log.i(TAG, "start AndroidAidService");
		startIntent.setClass(context, AndroidAidService.class);

		startIntent.putExtra(Data.SERVICE_REQUEST_APP_CLOUD, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
		context.startService(startIntent);	

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		setResult(0);
		running = false;
		super.onDestroy();
	}

	private OnClickListener listenerButton = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.imageButtonBack:
				
			case R.id.buttonCancel:
			
				finish();
				break;
				
			case R.id.buttonSet:
				finish();
				break;
				
			}
		}

	};
	
	/*
	public OnItemClickListener listenerListDownloading = new OnItemClickListener(){
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub

			
			listApps.get(position).setDownloadStop(!listApps.get(position).getDownloadStop());
			if(listApps.get(position).getDownloadStop()){
				
			}
			else{
				
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
            			Message msg = new Message();
            			msg.what = Data.HANDLER_MSG_WHAT_RENEW;
            			handler.sendMessage(msg);
            			
        			}catch(Exception e){
        				e.printStackTrace();
        			}
        			
       		}while(running);
 
    	}
    		
    };

	public Handler handler = new Handler()

    {

             
    	@Override

             
    	public void handleMessage(Message msg)

    	{
       		switch(msg.what){
       		
		
    		case Data.HANDLER_MSG_WHAT_RENEW:
    			

 
    			Log.i(TAG, "notifyDataSetChanged");
    			
    			listAdapter.notifyDataSetChanged();
    	           		
    			listView.invalidate();
    			
    			break;
    			
       		}
    	}
    	
    };
    
    
}
