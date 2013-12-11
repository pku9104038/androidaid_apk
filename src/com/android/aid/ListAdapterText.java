/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author wangpeifeng
 * for tools
 */
public class ListAdapterText extends BaseAdapter {
	private Context context;
	private ArrayList<AppInfoData> list;
	private LayoutInflater inflater;


	public ListAdapterText(Context context, ArrayList<AppInfoData> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			try{
			
				convertView = inflater.inflate(R.layout.listitem, null);
		
			}
			catch(InflateException e){
				e.printStackTrace();
			}
		}
		 
		TextView tv = (TextView)convertView.findViewById(R.id.textView);
		
		tv.setText(list.get(position).getNotes());
		
		ImageView iv =(ImageView)convertView.findViewById(R.id.imageView);
		
		iv.setImageDrawable(list.get(position).getIcon());
		
		return convertView;
	}

}
