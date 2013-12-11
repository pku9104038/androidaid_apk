/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * @author wangpeifeng
 *
 */
public class ListAdapterAppAdd extends BaseAdapter {
	
	private static final String TAG = "ListAdapterAppAdd";
	private Context context;
	private ArrayList<AppInfoData> list;
	private LayoutInflater inflater;
	private String location;

	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	
	public ListAdapterAppAdd(Context context, ArrayList<AppInfoData> list, String  location) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		
		this.location = location;

	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			try{
			
				convertView = inflater.inflate(R.layout.listitemappadd, null);
		
			}
			catch(InflateException e){
				e.printStackTrace();
			}
		}
		 
		TextView tv = (TextView)convertView.findViewById(R.id.textView);
		
		tv.setText(list.get(position).getLabel());
		
		ImageView iv =(ImageView)convertView.findViewById(R.id.imageView);
		
		iv.setImageDrawable(list.get(position).getIcon());
		/*
		CheckBox cb = (CheckBox)convertView.findViewById(R.id.checkBox);
		
		cb.setChecked(list.get(position).getAppMy());
		
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
			public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
			// TODO Auto-generated method stub
					list.get(position).setAppMy(isChecked);
					Log.i(TAG, list.get(position).getLabel()+":"+list.get(position).getAppMy());
			}

			
			});
		*/
		ImageView ivCategory = (ImageView)convertView.findViewById(R.id.imageViewCategory);
		ivCategory.setVisibility(View.GONE);
		
		if(list.get(position).getAppMy()){
			int id = Common.categoryToImageID(list.get(position).getAppMyCategory());
			if(id>0){
				ivCategory.setVisibility(View.VISIBLE);
				ivCategory.setImageResource(id);
				//iBtnCategory.setBackgroundResource(id);
			}
		}
		
/*		
		iBtnCategory.setOnClickListener(
				new OnClickListener(){

					public void onClick(View v) {
						// TODO Auto-generated method stub
		
						Log.i(TAG, position+":"+list.get(position).getLabel()+":"+list.get(position).getAppMy());
							
							Intent intent = new Intent(context,AppManagerAddMyCategory.class);
							//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							String category;
							if(list.get(position).getAppMy()){
								category = list.get(position).getAppMyCategory();
								
							}
							else{
								category =  Data.DB_VALUE_CATEGORY_MY;
							}
							intent.putExtra(Data.DB_COLUMN_APP_CATEGORY, category);
							intent.putExtra(Data.INTENT_DATA_LIST_INDEX, position);
							intent.putExtra(Data.INTENT_DATA_APP_LABEL, list.get(position).getLabel());
							
							
							((Activity) context).startActivityForResult(intent,Data.ACTIVITY_REQUEST_MY_CATEGORY);
					}
					
				}
				
				);
	
*/		
		ImageView ivCheck = (ImageView)convertView.findViewById(R.id.imageViewCheck);
		if(list.get(position).getAppMy()){
			ivCheck.setImageResource(R.drawable.btn_check_on);
			//iBtnCheck.setBackgroundResource(R.drawable.btn_check_on);
		}
		else{
			ivCheck.setImageResource(R.drawable.btn_check_off);
			//iBtnCheck.setBackgroundResource(R.drawable.btn_check_off);
		}
		
/*		
		iBtnCategory.setOnClickListener(
				new OnClickListener(){

					public void onClick(View v) {
						// TODO Auto-generated method stub
						list.get(position).setAppMy(!list.get(position).getAppMy());
						if(Data.DB_VALUE_CATEGORY_NULL.equals(list.get(position).getAppMyCategory())){
							list.get(position).setAppMyCategory(Data.DB_VALUE_CATEGORY_MY);
						
						}
					}
				}
			);
			
	*/	
		return convertView;
	}

}
