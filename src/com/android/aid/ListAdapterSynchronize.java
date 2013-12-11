/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author wangpeifeng
 *
 */
public class ListAdapterSynchronize extends BaseAdapter {
	private Context context;
	private ArrayList<AppInfoData> list;
	private LayoutInflater inflater;

	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	
	public ListAdapterSynchronize(Context context, ArrayList<AppInfoData> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		


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
			
				convertView = inflater.inflate(R.layout.listitemsynchronize, null);
		
			}
			catch(InflateException e){
				e.printStackTrace();
			}
		}
		 
		TextView tv = (TextView)convertView.findViewById(R.id.textView);
		
		tv.setText(list.get(position).getLabel());
		
		ImageView iv =(ImageView)convertView.findViewById(R.id.imageView);
		
		iv.setImageDrawable(list.get(position).getIcon());	
		ImageView ivStatus = (ImageView)convertView.findViewById(R.id.imageViewStatus);
		ProgressBar pb = (ProgressBar)convertView.findViewById(R.id.progressBar);
		
		if(Data.APP_SYNCHRONIZATION_CONFIG.equals(list.get(position).getLabel())){
			if(ThreadCloudSync.isConfigChecked()){
				pb.setVisibility(View.GONE);
				ivStatus.setImageResource(R.drawable.checked);
			}
			else{
				pb.setVisibility(View.VISIBLE);
				ivStatus.setImageResource(R.drawable.timer);
				
			}
			
		}
		else if(Data.APP_SYNCHRONIZATION_REPO.equals(list.get(position).getLabel())){
			if(ThreadCloudSync.isRepoChecked()){
				pb.setVisibility(View.GONE);
				ivStatus.setImageResource(R.drawable.checked);
			}
			else{
				pb.setVisibility(View.VISIBLE);
				ivStatus.setImageResource(R.drawable.timer);
				
			}
				
		}
		else if(Data.APP_SYNCHRONIZATION_VERSION.equals(list.get(position).getLabel())){
			if(ThreadCloudSync.isVersionChecked()){
				pb.setVisibility(View.GONE);
				ivStatus.setImageResource(R.drawable.checked);
			}
			else{
				pb.setVisibility(View.VISIBLE);
				ivStatus.setImageResource(R.drawable.timer);
				
			}
				
		}
		return convertView;
	}

}
