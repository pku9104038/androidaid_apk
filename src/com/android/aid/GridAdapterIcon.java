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
public class GridAdapterIcon extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private String defType, defPackage;
	private ArrayList<AppInfoData> gridlist;
	private ArrayList<Bitmap> bmpList = new ArrayList<Bitmap>();

	/**
	 * 
	 */
	public GridAdapterIcon(Context context,ArrayList<AppInfoData> gridlist) {
		// TODO Auto-generated constructor stub
		this.context =context;
		inflater = LayoutInflater.from(context);
		defType = new String("drawable");
		defPackage = new String(context.getPackageName());
		
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
			
				convertView = inflater.inflate(R.layout.griditem, null);
		
			}
			catch(InflateException e){
				e.printStackTrace();
			}
		}
		 
		ImageView iv = (ImageView)convertView.findViewById(R.id.gridImage);
		ProgressBar pb = (ProgressBar)convertView.findViewById(R.id.progressBar);
		TextView tv = (TextView)convertView.findViewById(R.id.gridText);
		
		ProgressBar pbIcon = (ProgressBar)convertView.findViewById(R.id.progressBarIcon);
		TextView tvProgress = (TextView)convertView.findViewById(R.id.textViewProgress);
		
		ProgressBar pbGreen = (ProgressBar)convertView.findViewById(R.id.progressBarInstalled);
		pbGreen.setVisibility(View.GONE);
		
		iv.setImageDrawable(gridlist.get(position).getIcon());
		
		if(Data.DB_VALUE_LOCATION_DOWNLOADING.equals(gridlist.get(position).getLocation())){
		//	pbGreen.setVisibility(View.GONE);
			
			pbIcon.setVisibility(View.VISIBLE);
			tvProgress.setVisibility(View.VISIBLE);
			
			pb.setMax(100);
			pb.setSecondaryProgress(100);
			pb.setProgress(0);
			
			long filelength = gridlist.get(position).getFileLength();
			long savedlength = 0;
			File file = new File(gridlist.get(position).getPath());
			if(file.exists()){
				savedlength = file.length();
				if(filelength==0){
					pb.setProgress(0);
					tvProgress.setText("0%");
				}
				else{
					int progress = Math.max(1, (int)(savedlength*100/filelength));
					pb.setProgress(progress);
					tvProgress.setText(progress+"%");
				}
			}
			else {
				file = new File(gridlist.get(position).getSavedPath());
				if(file.exists()){
					savedlength = filelength;
					pb.setProgress(100);
					tvProgress.setText("100%");
				}
				else{
					pb.setProgress(0);
					tvProgress.setText("0%");
				}
			}
			tvProgress.setTextColor(Color.GREEN);
			
		}
		
		else if(Data.DB_VALUE_LOCATION_DOWNLOADED.equals(gridlist.get(position).getLocation())){
			//pbGreen.setVisibility(View.GONE);
			pb.setMax(100);
			pb.setProgress(100);
			pbIcon.setVisibility(View.INVISIBLE);
			tvProgress.setVisibility(View.INVISIBLE);
			
		}
		
		else if(Data.DB_VALUE_LOCATION_INSTALLED.equals(gridlist.get(position).getLocation())){
			pbIcon.setVisibility(View.INVISIBLE);
			tvProgress.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.INVISIBLE);
			pbGreen.setVisibility(View.VISIBLE);
			pbGreen.setProgress(100);
			
		}
		
		else if(Data.DB_VALUE_LOCATION_CLOUD.equals(gridlist.get(position).getLocation())){
			pbIcon.setVisibility(View.INVISIBLE);
			tvProgress.setVisibility(View.INVISIBLE);
			
			//pbInstalled.setVisibility(View.GONE);
			//pb.setVisibility(View.INVISIBLE);
			//pbGreen.setProgress(0);
			pb.setProgress(0);
			
		}
		
		else if(Data.DB_VALUE_LOCATION_INTERNAL.equals(gridlist.get(position).getLocation())){
			pbIcon.setVisibility(View.INVISIBLE);
			tvProgress.setVisibility(View.INVISIBLE);
			
			//pbInstalled.setVisibility(View.GONE);
			//pb.setVisibility(View.INVISIBLE);
			//pbGreen.setProgress(0);
			pb.setVisibility(View.INVISIBLE);
			pbGreen.setVisibility(View.VISIBLE);
			pbGreen.setProgress(0);
			pbGreen.setSecondaryProgress(0);
			
			
		}
		
		tv.setText(gridlist.get(position).getLabel());
		
		return convertView;
	}

}
