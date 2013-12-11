/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class AppManagerAddMy extends Activity {
	
	private static final String TAG = "AppManagerAddMy";
	
	private Context context;
	private DataProxy dataproxy;
	
	private ListView listView;
	private ListAdapterAppAdd listAdapter; 
	private CheckBox checkBox;
	private Button buttonSet;
	private Button buttonCancel;
	private ImageButton imageButtonBack;

	//private ArrayList<AppInfoData> listApps;

	private ArrayList<AppInfoData> listAppMy;
	private ArrayList<AppInfoData> listAppInstalled;


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
		
		listAppMy = new ArrayList<AppInfoData>();
		
		listAppMy = dataproxy.queryAppMy(listAppMy,Data.DB_VALUE_CATEGORY_ALL);
		
		listAppInstalled = new ArrayList<AppInfoData>();
		
		//listAppInstalled = dataproxy.queryAppInstalled(listAppInstalled);
		listAppInstalled = dataproxy.queryAppInstalledNotInRepo(listAppInstalled);
		
//		Log.i(TAG, "appInstalled:"+listAppInstalled.size());

		setMyFlag(listAppInstalled,listAppMy);
		
		
		
		listAdapter = new ListAdapterAppAdd(this,listAppInstalled,Data.DB_VALUE_LOCATION_INSTALLED);

		listView.setAdapter(listAdapter);
    
		listView.setOnItemClickListener(listenerListInstalled);
	
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(Data.APP_ADD_MY_TITLE);
	
		buttonSet = (Button)findViewById(R.id.buttonSet);
		buttonSet.setOnClickListener(listenerButton);
		
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(listenerButton);

		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);

	}
	
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		
		Log.i(TAG, "request:"+requestCode+",result:"+resultCode);
		
		switch(requestCode){
		
		case Data.ACTIVITY_REQUEST_MY_CATEGORY:
			int index = data.getIntExtra(Data.INTENT_DATA_LIST_INDEX, 0);
			if(R.id.radioButtonCancel == resultCode){
				listAppInstalled.get(index).setAppMy(false);
			}
			else{
				listAppInstalled.get(index).setAppMy(true);
				
			}
			
			listAppInstalled.get(index).setAppMyCategory(Common.addMyRadioButtonIdToCategory(resultCode));
			listAdapter.notifyDataSetChanged();
			break;
				
		}
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
				finish();
				break;
				
			case R.id.buttonSet:
				dataproxy.updateAppMyDatabase(listAppInstalled);
				finish();
				break;
				
			}
		}
	};
	
	public OnItemClickListener listenerListInstalled = new OnItemClickListener(){
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i(TAG, position+":"+listAppInstalled.get(position).getLabel()+":"+listAppInstalled.get(position).getAppMy());
			
			//Intent intent = new Intent(context,AppManagerAddMyCategory.class);
			Intent intent = new Intent(context,AppManagerAddMyCategoryGrid.class);
			//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			String category;
			if(listAppInstalled.get(position).getAppMy()){
				category = listAppInstalled.get(position).getAppMyCategory();
				
			}
			else{
				category =  Data.DB_VALUE_CATEGORY_MY;
			}
			intent.putExtra(Data.DB_COLUMN_APP_CATEGORY, category);
			intent.putExtra(Data.INTENT_DATA_LIST_INDEX, position);
			intent.putExtra(Data.INTENT_DATA_APP_LABEL, listAppInstalled.get(position).getLabel());
			
			
			((Activity) context).startActivityForResult(intent,Data.ACTIVITY_REQUEST_MY_CATEGORY);
			
		}
		
	};
	
	
	private void setMyFlag(ArrayList<AppInfoData> listinstalled, ArrayList<AppInfoData> listmy){
		
		int length = listinstalled.size();
		int l = listmy.size();
		int i = 0;
		while(i<length){
			int j = 0;
			while(j<l){
				if(listinstalled.get(i).getPackage().equals(listmy.get(j).getPackage())){
					
					listinstalled.get(i).setAppMy(true);
					listinstalled.get(i).setAppMyCategory(listmy.get(j).getAppMyCategory());
					Log.i(TAG, "set flag:"+i);
					break;
					
				}
				j++;
				
			}
			
			i++;
		}
	}


}
