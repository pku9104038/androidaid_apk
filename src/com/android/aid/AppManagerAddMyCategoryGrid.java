/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;
import java.util.Iterator;

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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author wangpeifeng
 *
 */
public class AppManagerAddMyCategoryGrid extends Activity {
	
	private static final String TAG = "AppManagerAddMyCategoryGrid";
	
	private Context context;
	private DataProxy dataproxy;
	
	private Button buttonSet;
	private Button buttonCancel;
	private ImageButton imageButtonBack;
	private GridView gridView;
	private GridAdapterCategory gridAdapter;
	private String category;
	private int index;
	private String label;
	int position;
	
	
	private ArrayList<CategoryInfoData> listCategory;

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
		setContentView(R.layout.appaddcategorygrid);

		TextView tvName = (TextView)findViewById(R.id.textViewName);
		tvName.setText(Data.APP_ADD_MY_CATEGORY_TITLE+" \""+label+"\"");
	
		buttonSet = (Button)findViewById(R.id.buttonSet);
		buttonSet.setOnClickListener(listenerButton);
		
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(listenerButton);

		imageButtonBack = (ImageButton)findViewById(R.id.imageButtonBack);
		imageButtonBack.setOnClickListener(listenerButton);
		
		gridView = (GridView) findViewById(R.id.gridView);
		
		listCategory = new ArrayList<CategoryInfoData>();
		
		listCategory = dataproxy.queryCategory(listCategory);
		
		CategoryInfoData info = new CategoryInfoData();
		info.setCategory(Data.DB_VALUE_CATEGORY_NULL);
		info.setIcon(getResources().getDrawable(Common.categoryToImageID(info.getCategory())));
		listCategory.add(info);
		
		info = new CategoryInfoData();
		info.setCategory(Data.DB_VALUE_CATEGORY_MY);
		info.setIcon(getResources().getDrawable(Common.categoryToImageID(info.getCategory())));
		listCategory.add(info);
		
		position = Common.categoryToGridPosition(category);
		listCategory.get(position).setSelect(true);
		
		gridAdapter = new GridAdapterCategory(context, listCategory);
		
		gridView.setAdapter(gridAdapter);
		
		gridView.setOnItemClickListener(listenerGridItem);
		
		
		

				
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


	private OnItemClickListener listenerGridItem = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			int i=0;
			while(i<listCategory.size()){
				listCategory.get(i).setSelect(false);
				i++;
			}
			listCategory.get(position).setSelect(true);
			gridAdapter.notifyDataSetChanged();
			gridView.invalidate();
			
			Log.i(TAG, "position:"+position);
			
			
		}
		
	};
	
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
				
				int i=0;
				while(i<listCategory.size()){
					if(listCategory.get(i).getSelect()){
						position = i;
						break;
					}
					i++;
				}
				category = Common.positionToCategory(position);
				
				
				finish();
				break;
				
			}
		}
	};

}
