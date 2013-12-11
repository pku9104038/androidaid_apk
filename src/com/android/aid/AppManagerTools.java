/**
 * 
 */
package com.android.aid;


import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;

/**
 * @author wangpeifeng
 *
 */
public class AppManagerTools extends Activity {
	
	private static final String TAG = "AppManagerTools";
	
	private Context context;
	private DataProxy dataproxy;
	
	private ListView listView;
	private ListAdapterText listAdapter; 
	private Button buttonSet;
	private Button buttonCancel;
	private ImageButton imageButtonBack;

	private ArrayList<AppInfoData> listApps;

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
		setContentView(R.layout.appadd);
		
		listView = (ListView)findViewById(R.id.listView);
	    
		listApps = new ArrayList<AppInfoData>();

		//listApps = checkAppRepoDatabase(listApps); 
		listApps = checkAppInstalledDatabase(listApps); 
		
		
		listAdapter = new ListAdapterText(this,listApps);
		

		listView.setAdapter(listAdapter);
    
		listView.setOnItemClickListener(listenerListApps);
	
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(Data.APP_DATABASE_TITLE);
	
		buttonSet = (Button)findViewById(R.id.buttonSet);
		buttonSet.setOnClickListener(listenerButton);
		
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(listenerButton);

		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);

		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		setResult(0);
		super.onDestroy();
	}

	private OnClickListener listenerButton = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.imageButtonBack:
				
			case R.id.buttonCancel:
				
			case R.id.buttonSet:
				finish();
				break;
				
			}
		}

	};
	
	public OnItemClickListener listenerListApps = new OnItemClickListener(){
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i(TAG, listApps.get(position).getDownloadStop()?"true":"false");
			listApps.get(position).setDownloadStop(!listApps.get(position).getDownloadStop());
			Log.i(TAG, listApps.get(position).getDownloadStop()?"true":"false");
			
		}
		
	};
	
	public ArrayList<AppInfoData> checkAppRepoDatabase(ArrayList<AppInfoData> listApps){
		
		listApps = dataproxy.queryAppRepository(listApps); 

		Iterator<com.android.aid.AppInfoData> iterator = listApps.iterator();
		
		int i = 1;
		while(iterator.hasNext()){
			
			AppInfoData apkInfoData = iterator.next();
			if(apkInfoData != null){   
 
				apkInfoData.setNotes("APP :"+i+"\n");
				apkInfoData.addNotes("label="+apkInfoData.getLabel()+"\n");
				apkInfoData.addNotes("category="+apkInfoData.getCategory()+"\n");
				apkInfoData.addNotes("location="+apkInfoData.getLocation()+"\n");
				apkInfoData.addNotes("packageName="+apkInfoData.getPackage()+"\n");
				apkInfoData.addNotes("state="+apkInfoData.getState()+"\n");
				apkInfoData.addNotes("price="+apkInfoData.getPrice()+"\n");
				apkInfoData.addNotes("versionCode="+apkInfoData.getVersionCode()+"\n");
				apkInfoData.addNotes("stamp="+apkInfoData.getStamp()+"\n");
				
				
			}
			i++;
		}
		
		
		return listApps;
	}
	
public ArrayList<AppInfoData> checkAppInstalledDatabase(ArrayList<AppInfoData> listApps){
		
		listApps = dataproxy.queryAppInstalled(listApps); 

		Iterator<com.android.aid.AppInfoData> iterator = listApps.iterator();
		
		int i = 1;
		while(iterator.hasNext()){
			
			AppInfoData apkInfoData = iterator.next();
			if(apkInfoData != null){   
 
				apkInfoData.setNotes("APP :"+i+"\n");
				apkInfoData.addNotes("label="+apkInfoData.getLabel()+"\n");
				apkInfoData.addNotes("location="+apkInfoData.getLocation()+"\n");
				apkInfoData.addNotes("packageName="+apkInfoData.getPackage()+"\n");
				apkInfoData.addNotes("versionCode="+apkInfoData.getVersionCode()+"\n");
				apkInfoData.addNotes("install time="+apkInfoData.getInstallTime()+"\n");
				apkInfoData.addNotes("update time="+apkInfoData.getUpdateTime()+"\n");
				
				
			}
			i++;
		}
		
		
		return listApps;
	}
	
}
