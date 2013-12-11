/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
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
public class AppManagerDownloading extends Activity {
	
	private static final String TAG = "AppManagerDownloading";
	
	private static final int HANDLER_MSG_WHAT_DOWNLOADING = 1;
	private static final int HANDLER_MSG_WHAT_RENEW = HANDLER_MSG_WHAT_DOWNLOADING + 1;
	private static final int HANDLER_MSG_WHAT_WAITING = HANDLER_MSG_WHAT_RENEW + 1;
	private static final int HANDLER_MSG_WHAT_NULL = HANDLER_MSG_WHAT_WAITING + 1;
	
	
	private Context context;
	private DataProxy dataproxy;
	
	private ListView listView;
	private ListAdapterDownloading listAdapter; 
	private CheckBox checkBox;
	private Button buttonSet;
	private Button buttonCancel;
	private ImageButton imageButtonBack;

	private ArrayList<AppInfoData> listAppDownloading;
	
	private boolean running;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		context = this;
		dataproxy = new DataProxy(context);
	
		setTheme(R.style.Transparent);
		setContentView(R.layout.appdownloading);
		
		listView = (ListView)findViewById(R.id.listView);
	    
		listAppDownloading = new ArrayList<AppInfoData>();
		
		//listAppDownloading = dataproxy.queryAppDownloading(listAppDownloading,Data.DB_VALUE_QUERY_DOWNLOADING_SILENCE);
		listAppDownloading = dataproxy.queryAppDownloading(listAppDownloading,Data.DB_VALUE_QUERY_DOWNLOADING);
		
		
		listAdapter = new ListAdapterDownloading(this,listAppDownloading,Data.DB_VALUE_DOWNLOAD_DOWNLOADING);
		

		listView.setAdapter(listAdapter);
    
		//listView.setOnItemClickListener(listenerListDownloading);
	
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(Data.APP_DOWNLOADING_TITLE);
	
		buttonSet = (Button)findViewById(R.id.buttonSet);
		buttonSet.setOnClickListener(listenerButton);
		
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(listenerButton);

		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);
		
		Thread thread = new Thread(updating);
    	thread.start();
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
				Iterator<AppInfoData> iterator = listAppDownloading.iterator();
				
				while(iterator.hasNext()){
					
					AppInfoData infoData = (AppInfoData) iterator.next();
					if(infoData.getDownloadStop()){
						if(DataProxy.mapThreadDownload.containsKey(infoData.getPackage())){
							ThreadDownload thread = dataproxy.mapThreadDownload.get(infoData.getPackage());
							thread.resumeDownload();
						}
					}
					
					
				}
				
				finish();
				break;
				
			case R.id.buttonSet:
				iterator = listAppDownloading.iterator();
				
				while(iterator.hasNext()){
					
					AppInfoData infoData = (AppInfoData) iterator.next();
					if(infoData.getDownloadStop()){
						if(DataProxy.mapThreadDownload.containsKey(infoData.getPackage())){
							ThreadDownload thread = DataProxy.mapThreadDownload.get(infoData.getPackage());
							thread.stopRunning();
						}
					}
					
					
				}
				
				dataproxy.cancelDownloading(listAppDownloading);
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

			
			listAppDownloading.get(position).setDownloadStop(!listAppDownloading.get(position).getDownloadStop());
			if(listAppDownloading.get(position).getDownloadStop()){
				
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
//            			listApps = checkAppCategory(name);
//            			Log.i(TAG, "notifyDataSetChanged");
//            			listAdapter.notifyDataSetChanged();
            			Message msg = new Message();
            			msg.what = HANDLER_MSG_WHAT_RENEW;
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
       		
		
    		case HANDLER_MSG_WHAT_RENEW:
    			

    			//listAppDownloading = updateDownloadProgress(listAppDownloading); 
    			
    			
    			//listAdapter = new ListAdapterDownloading(context,listAppDownloading,Data.DB_VALUE_DOWNLOAD_DOWNLOADING);
    			

    			//listView.setAdapter(listAdapter);

    			Log.i(TAG, "notifyDataSetChanged");
    			
    			listAdapter.notifyDataSetChanged();
    			
    	           		
    			listView.invalidate();
    			
    			break;
    			
       		}
    	}
    	
    };
    
    private ArrayList<AppInfoData> updateDownloadProgress(ArrayList<AppInfoData> listApps){
    	
    	
    	
    	return listApps;
    }
}
