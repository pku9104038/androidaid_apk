/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @author wangpeifeng
 *
 */
public class AppManagerRecent extends Activity {
	private static final String TAG = "AppManagerRecent";
	private Context context;
	
	private ArrayList<String> gridlist;
	private GridView gridView;
	private GridAdapterPackage gridAdapter;
	private ImageButton imageButtonBack;
	private PackageManager packageManager;
	private TaskTracker taskTracker;
	
	private DataProxy dataproxy;
	private boolean newVersion = false;
	private AppInfoData appInfoData;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		context = this;

		setTheme(R.style.Transparent);
		setContentView(R.layout.appmanager);
		dataproxy = new DataProxy(context);

		gridlist = new ArrayList<String>();
		/*
		appInfoData = new AppInfoData(context);
		if(dataproxy.newVersionReady(appInfoData)){
			newVersion = true;
			appInfoData.setLabel(getResources().getString(R.string.app_name));
			appInfoData.setLocation(Data.DB_VALUE_LOCATION_DOWNLOADED);
			FileProxy.setApkFileIcon(context, appInfoData.getPath(),appInfoData);
			gridlist.add(appInfoData.getLabel());
			Toast.makeText(context, 
					//getResources().getString(R.string.app_name)+
					Data.TOAST_NEW_VERSOIN_READY, Toast.LENGTH_LONG).show();

		}
		*/
		taskTracker = new TaskTracker(this);
		packageManager =getPackageManager();  
		gridlist = taskTracker.getRecentTaskPackageName(gridlist);

		gridView = (GridView)findViewById(R.id.gridView);
        
		gridAdapter = new GridAdapterPackage(this,gridlist,packageManager);
    
		gridView.setAdapter(gridAdapter);
    
		gridView.setOnItemClickListener(listenerGrid);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra(Data.APP_REQUEST);
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(name);
		
		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);

		
/*		
		LayoutInflater inflater = LayoutInflater.from(context);
		try{
				
		
			popupView = inflater.inflate(R.layout.appmanager, null);
			
			gridView = (GridView)popupView.findViewById(R.id.gridView);
        
			gridAdapter = new GridAdapterPackage(this,gridlist,packageManager);
        
			gridView.setAdapter(gridAdapter);
        
			gridView.setOnItemClickListener(listenerGrid);
			
			Intent intent = getIntent();
			String name = intent.getStringExtra(Data.APP_REQUEST);
			TextView tvName = (TextView)popupView.findViewById(R.id.textViewName);
			tvName.setText(name);
			
			imageButtonBack = (ImageButton)popupView.findViewById(R.id.imageButtonBack);
			imageButtonBack.setOnClickListener(listenerButton);
			
			windowManager=(WindowManager)getApplicationContext().getSystemService("window");
	        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	 
	        wmParams.type=2002;   //这里是关键，你也可以试试2003
	        wmParams.format=1;
	        
	        wmParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//	        		|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;     
	        wmParams.width=WindowManager.LayoutParams.MATCH_PARENT;
	        wmParams.height=WindowManager.LayoutParams.MATCH_PARENT;
	        
	        windowManager.addView(popupView, wmParams);  //创建View
	        
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
/*
		try{
			windowManager.removeView(popupView);
		}
		catch(Exception e){
			e.printStackTrace();
		}
*/
		super.onStop();
	
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
			
			finish();
			
			
		}
	};
	
	private OnItemClickListener listenerGrid = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
//			windowManager.removeView(popupView);
			Log.i(TAG, "position="+position);
			Intent intent = taskTracker.getRecentTaskIntent(gridlist.get(position));
			startActivityForResult(intent,0);
			finish();
			
		}
    	
    };

}
