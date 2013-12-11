/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
 * for app manager, my activity
 */
public class GridAdapterCategory extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private ArrayList<CategoryInfoData> gridlist;

	/**
	 * 
	 */
	public GridAdapterCategory(Context context,ArrayList<CategoryInfoData> gridlist) {
		
		// TODO Auto-generated constructor stub
		this.context =context;
		inflater = LayoutInflater.from(context);
		
		this.gridlist = gridlist;

	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		
		return gridlist.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return gridlist.get(position);
		
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
			
				convertView = inflater.inflate(R.layout.griditemcategory, null);
		
			}
			catch(InflateException e){
				e.printStackTrace();
			}
		}
		 
		ImageView iv = (ImageView)convertView.findViewById(R.id.gridImage);
		TextView tv = (TextView)convertView.findViewById(R.id.gridText);
		
		ProgressBar pb = (ProgressBar)convertView.findViewById(R.id.progressBar);
		
		((ProgressBar)(convertView.findViewById(R.id.progressBarIcon))).setVisibility(View.INVISIBLE);
		((TextView)(convertView.findViewById(R.id.textViewProgress))).setVisibility(View.INVISIBLE);
		
		((ProgressBar)(convertView.findViewById(R.id.progressBarInstalled))).setVisibility(View.INVISIBLE);
		
		iv.setImageDrawable(gridlist.get(position).getIcon());
		
		if(gridlist.get(position).getSelect()){
			
			pb.setMax(100);
			pb.setSecondaryProgress(100);
			pb.setProgress(100);
			
		}
		else{
			pb.setMax(100);
			pb.setProgress(0);
			
		}
				
		tv.setText(gridlist.get(position).getCategory());
		
		return convertView;
	}

}
