/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author wangpeifeng
 *
 */
public class AppManagerMy extends Activity {
	
	private static final String TAG = "AppManagerMy";
	
	private static final int HANDLER_MSG_WHAT_PROGRESS = 1;
	
	AppInfoData appInfoData;
	
	private String name;
	private ImageButton imageButtonBack;
	
	
	private ArrayList<AppInfoData> listApps;

	private Context context;
	private DataProxy dataproxy;
	
	private GridView gridView;
	private GridAdapterIcon gridAdapter;

	
	private boolean newVersion = false;
	
	
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
	
		setTheme(R.style.Transparent);
		setContentView(R.layout.appmanager);

		
		refreshMyView();
		
		super.onCreate(savedInstanceState);

	}
	
	
	private void refreshMyView(){
		
		appInfoData = new AppInfoData(context);
		listApps = new ArrayList<AppInfoData>();
		if(dataproxy.newVersionReady(appInfoData)){
			newVersion = true;
			appInfoData.setLabel(getResources().getString(R.string.app_name));
			appInfoData.setLabel(Data.DB_VALUE_VERSION_UPDATE);
			appInfoData.setLocation(Data.DB_VALUE_LOCATION_DOWNLOADED);
			appInfoData.setPackage(Data.DB_VALUE_PACKAGE_ANDROIDAID+".apk");
			
			FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
			listApps.add(appInfoData);
//			Toast.makeText(context, 
					//getResources().getString(R.string.app_name)+
//					Data.TOAST_NEW_VERSOIN_READY, Toast.LENGTH_LONG).show();

		}
		
		
		appInfoData = new AppInfoData(context);
		appInfoData.setLabel(Data.APP_ADD_MY);
		appInfoData.setLocation(Data.DB_VALUE_LOCATION_INTERNAL);
		appInfoData.setIcon(context.getResources().getDrawable(R.drawable.menu_icon_about));
		listApps.add(appInfoData);

		appInfoData = new AppInfoData(context);
		appInfoData.setLabel(Data.APP_DOWNLOADING);
		appInfoData.setLocation(Data.DB_VALUE_LOCATION_INTERNAL);
		appInfoData.setIcon(context.getResources().getDrawable(R.drawable.menu_icon_download));
		listApps.add(appInfoData);

		appInfoData = new AppInfoData(context);
		appInfoData.setLabel(Data.APP_SYNCHRONIZATION);
		appInfoData.setLocation(Data.DB_VALUE_LOCATION_INTERNAL);
		appInfoData.setIcon(context.getResources().getDrawable(R.drawable.menu_icon_refresh_lib));
		listApps.add(appInfoData);

		
/*
		//test tools
		appInfoData = new AppInfoData(context);
		appInfoData.setLabel(Data.APP_DATABASE);
		appInfoData.setLocation(Data.DB_VALUE_LOCATION_INTERNAL);
		appInfoData.setIcon(context.getResources().getDrawable(R.drawable.download_mgr));
		listApps.add(appInfoData);
*/

		listApps = dataproxy.queryAppMy(listApps,Data.DB_VALUE_CATEGORY_MY);

		gridView = (GridView)findViewById(R.id.gridView);
        
		gridAdapter = new GridAdapterIcon(this,listApps);
        
		gridView.setAdapter(gridAdapter);
        
		gridView.setOnItemClickListener(listenerGrid);
		
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(name);
		
		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);

		
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		refreshMyView();
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
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
			switch(v.getId()){
			case R.id.imageButtonBack:
				finish();
				break;
				
			}
		}
		
		
	};
	
	
	private OnItemClickListener listenerGrid = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String label = listApps.get(position).getLabel();
			
			if(Data.APP_ADD_MY.equals(label)){
				Intent intent =new Intent(context,AppManagerAddMy.class);
				startActivityForResult(intent,0);
				
			}
			else if (Data.APP_DOWNLOADING.equals(label)){
				Intent intent =new Intent(context,AppManagerDownloading.class);
				startActivityForResult(intent,0);
				
			}
			else if (Data.APP_SYNCHRONIZATION.equals(label)){
				
				Intent intent =new Intent(context,AppManagerSynchronize.class);
				startActivityForResult(intent,0);

/*
				Intent startIntent = new Intent();
				Log.i(TAG, "start AndroidAidService");
				startIntent.setClass(context, AndroidAidService.class);

				startIntent.putExtra(Data.SERVICE_REQUEST_APP_CLOUD, true);
				startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
				startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
				startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
				startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
				context.startService(startIntent);	
				Toast.makeText(context, Data.TOAST_SYNC_SERVICRE_START, Toast.LENGTH_LONG).show();
*/				
			}
			else if(Data.APP_DATABASE.equals(label)){
				Intent intent =new Intent(context,AppManagerTools.class);
				startActivityForResult(intent,0);
				
			}
			else if(Data.DB_VALUE_VERSION_UPDATE.equals(label)){
				
				AppInfoData appInfoData = listApps.get(position);
				Intent startIntent = new Intent();

				startIntent.setAction(android.content.Intent.ACTION_VIEW);
	               
				startIntent.setDataAndType(Uri.fromFile(new File(appInfoData.getPath())),    
		                "application/vnd.android.package-archive");    
		        startActivityForResult(startIntent,0); 
		        //finish();

				
			}
			else{

				AppInfoData appInfoData = listApps.get(position);
				Intent startIntent = new Intent();

				if(Data.DB_VALUE_LOCATION_INSTALLED.equals(appInfoData.getLocation())){
					startIntent = getPackageManager().getLaunchIntentForPackage(    
			                appInfoData.getPackage());  
					startActivityForResult(startIntent,0);	
					finish();
				
				}
				
			}
					
			
		}
    	
    };
    
	

}
