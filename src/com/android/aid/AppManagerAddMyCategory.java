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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author wangpeifeng
 *
 */
public class AppManagerAddMyCategory extends Activity {
	
	private static final String TAG = "AppManagerAddMyCategory";
	
	private Context context;
	private DataProxy dataproxy;
	
	private ListView listView;
	private ListAdapterAppAdd listAdapter; 
	private CheckBox checkBox;
	private Button buttonSet;
	private Button buttonCancel;
	private ImageButton imageButtonBack;
	private RadioGroup radioGroup;
	private String category;
	private int index;
	private String label;
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

		Intent intent = getIntent();
		category = intent.getStringExtra(Data.DB_COLUMN_APP_CATEGORY);
		index = intent.getIntExtra(Data.INTENT_DATA_LIST_INDEX, 0);// default is first item;
		
		label = intent.getStringExtra(Data.INTENT_DATA_APP_LABEL);
		
		context = this;
		dataproxy = new DataProxy(context);

		setTheme(R.style.Transparent);
		setContentView(R.layout.appaddcategory);
/*
		listView = (ListView)findViewById(R.id.listView);
		
		listAppMy = new ArrayList<AppInfoData>();
		
		listAppMy = dataproxy.queryAppMy(listAppMy);
		
		listAppInstalled = new ArrayList<AppInfoData>();
		
		//listAppInstalled = dataproxy.queryAppInstalled(listAppInstalled);
		listAppInstalled = dataproxy.queryAppInstalledNotInRepo(listAppInstalled);
		
//		Log.i(TAG, "appInstalled:"+listAppInstalled.size());

		setMyFlag(listAppInstalled,listAppMy);
		
		
		
		listAdapter = new ListAdapterAppAdd(this,listAppInstalled,Data.DB_VALUE_LOCATION_INSTALLED);

		listView.setAdapter(listAdapter);
    
		//listView.setOnItemClickListener(listenerListInstalled);
*/	
		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(Data.APP_ADD_MY_CATEGORY_TITLE+" \""+label+"\"");
	
		buttonSet = (Button)findViewById(R.id.buttonSet);
		buttonSet.setOnClickListener(listenerButton);
		
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(listenerButton);

		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);

		radioGroup = (RadioGroup)findViewById(R.id.RadioGroup);
		
		
		
		radioGroup.check(Common.categoryToAddMyRadioButtonId(category));
		
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra(Data.INTENT_DATA_LIST_INDEX, index);
		
		setResult(Common.categoryToAddMyRadioButtonId(category),intent);
		
		
		super.finish();
	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//setResult(0);
		super.onDestroy();
	}


	private OnClickListener listenerButton = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			switch(v.getId()){
			case R.id.imageButtonBack:
			case R.id.buttonCancel:
				//setResult(Common.categoryToAddMyRadioButtonId(category));
				
				finish();
				break;
				
			case R.id.buttonSet:
				
				category = Common.addMyRadioButtonIdToCategory(radioGroup.getCheckedRadioButtonId());
				
				
				finish();
				break;
				
			}
		}
	};
	/*
	public OnItemClickListener listenerListInstalled = new OnItemClickListener(){
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i(TAG, listAppInstalled.get(position).getAppMy()?"true":"false");
			listAppInstalled.get(position).setAppMy(!listAppInstalled.get(position).getAppMy());
			Log.i(TAG, listAppInstalled.get(position).getAppMy()?"true":"false");
			
		}
		
	};
	*/
	/*
	private void setMyFlag(ArrayList<AppInfoData> listinstalled, ArrayList<AppInfoData> listmy){
		
		int length = listinstalled.size();
		int l = listmy.size();
		int i = 0;
		while(i<length){
			int j = 0;
			while(j<l){
				if(listinstalled.get(i).getPackage().equals(listmy.get(j).getPackage())){
					
					listinstalled.get(i).setAppMy(true);
					Log.i(TAG, "set flag:"+i);
					break;
					
				}
				j++;
				
			}
			
			i++;
		}
	}
	*/

}
