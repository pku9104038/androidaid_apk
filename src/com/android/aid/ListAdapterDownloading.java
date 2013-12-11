/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author wangpeifeng
 *
 */
public class ListAdapterDownloading extends BaseAdapter {
	
	private static final String TAG = "ListAdapterDownloading";
	
	private Context context;
	private ArrayList<AppInfoData> list;
	private LayoutInflater inflater;
	private String location;
	
	
	
	public static ArrayList<ThreadDownload> listThreadDownload; 
	
	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	
	public ListAdapterDownloading(Context context, ArrayList<AppInfoData> list, String  location) {
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
			
				convertView = inflater.inflate(R.layout.listitemdownloading, null);
		
			}
			catch(InflateException e){
				e.printStackTrace();
			}
		}
		 
		TextView tv = (TextView)convertView.findViewById(R.id.textView);
		
		tv.setText(list.get(position).getLabel());
		
		ImageView iv =(ImageView)convertView.findViewById(R.id.imageView);
		ImageButton imageButtonStatus = (ImageButton) convertView.findViewById(R.id.ImageButtonStatus);
		
		if(Data.DB_VALUE_LOCATION_INSTALLED.equals(location)){
			iv.setImageDrawable(list.get(position).getIcon());
		}
		else if(Data.DB_VALUE_LOCATION_DOWNLOADED.equals(location)){
			if(list.get(position).getIcon()!=null)
				iv.setImageDrawable(list.get(position).getIcon());	
		}
		else if(Data.DB_VALUE_LOCATION_DOWNLOADING.equals(location)){
			
			if(list.get(position).getIcon()==null){
				FileProxy.setAppFileIcon(context, list.get(position).getIconPath(), list.get(position));
			}
			
			if(list.get(position).getIcon()!=null){
				iv.setImageDrawable(list.get(position).getIcon());	
						
			}
			else{
				iv.setImageResource(R.drawable.appicon_default);
			}
			
			
			// add for progress bar
			ProgressBar pb = (ProgressBar) convertView.findViewById(R.id.progressBar);
			TextView tvProgress = (TextView)convertView.findViewById(R.id.textViewProgress);
			long filelength = list.get(position).getFileLength();
			long savedlength = 0;
			File file = new File(list.get(position).getPath());
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
				file = new File(list.get(position).getSavedPath());
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
			
			if(Data.DB_VALUE_DOWNLOAD_PAUSE.equals(list.get(position).getStatus())){
				imageButtonStatus.setBackgroundResource(R.drawable.icon_download_manage_pause);
				imageButtonStatus.setImageResource(R.drawable.icon_download_manage_pause);
			}
			else if(Data.DB_VALUE_DOWNLOAD_CANCEL.equals(list.get(position).getStatus())) {
				imageButtonStatus.setBackgroundResource(R.drawable.icon_download_manage_fail);
				imageButtonStatus.setImageResource(R.drawable.icon_download_manage_fail);
			}
			else if(Data.DB_VALUE_DOWNLOAD_DOWNLOADING.equals(list.get(position).getStatus())) {
				imageButtonStatus.setBackgroundResource(R.drawable.icon_download_manage_downloading);
				imageButtonStatus.setImageResource(R.drawable.icon_download_manage_downloading);
			}
			
		}
		
		CheckBox cb = (CheckBox)convertView.findViewById(R.id.checkBox);
		
		cb.setOnCheckedChangeListener(
			
				new OnCheckedChangeListener() {
		
					public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
						// TODO Auto-generated method stub
						list.get(position).setDownloadStop(isChecked);
						
						if(isChecked){
							if(DataProxy.mapThreadDownload.containsKey(list.get(position).getPackage())){
								ThreadDownload thread = DataProxy.mapThreadDownload.get(list.get(position).getPackage());
								thread.pauseDownload();
//								convertView.findViewById(R.id.ImageButtonStatus).setBackgroundResource(R.drawable.icon_download_manage_fail);
								
							}

						}
						else{
							list.get(position).setStatus(Data.DB_VALUE_DOWNLOAD_DOWNLOADING);
							if(DataProxy.mapThreadDownload.containsKey(list.get(position).getPackage())){
								ThreadDownload thread = DataProxy.mapThreadDownload.get(list.get(position).getPackage());
								thread.resumeDownload();
							}
						}
						
					}
				}
			);
		
		cb.setChecked(list.get(position).getDownloadStop());
		
//		ImageButton imageButtonStatus = (ImageButton)convertView.findViewById(R.id.ImageButtonStatus);
		
		imageButtonStatus.setOnClickListener(
				
				new OnClickListener(){

					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Log.i(TAG, "button click");
						
						int step = 0;
						if(Data.DB_VALUE_DOWNLOAD_DOWNLOADING.equals(list.get(position).getStatus())){
							step = 2;
						}
						else if(Data.DB_VALUE_DOWNLOAD_PAUSE.equals(list.get(position).getStatus())){
							step = 2;
						}
						else if(Data.DB_VALUE_DOWNLOAD_CANCEL.equals(list.get(position).getStatus())){
							step = 3;
						}
						
						
						switch(step){
						
						case 1:
							if(DataProxy.mapThreadDownload.containsKey(list.get(position).getPackage())){
								ThreadDownload thread = DataProxy.mapThreadDownload.get(list.get(position).getPackage());
								thread.pauseDownload();
							}
							list.get(position).setDownloadStop(true);
							list.get(position).setStatus(Data.DB_VALUE_DOWNLOAD_PAUSE);
							((ImageButton)v).setBackgroundResource(R.drawable.icon_download_manage_pause);
							((ImageButton)v).setImageResource(R.drawable.icon_download_manage_pause);
							break;
							
						case 2:
							if(DataProxy.mapThreadDownload.containsKey(list.get(position).getPackage())){
								ThreadDownload thread = DataProxy.mapThreadDownload.get(list.get(position).getPackage());
								thread.pauseDownload();
							}
							list.get(position).setDownloadStop(true);
							list.get(position).setStatus(Data.DB_VALUE_DOWNLOAD_CANCEL);
							((ImageButton)v).setBackgroundResource(R.drawable.icon_download_manage_fail);
							((ImageButton)v).setImageResource(R.drawable.icon_download_manage_fail);
							break;
							
						case 3:
							if(DataProxy.mapThreadDownload.containsKey(list.get(position).getPackage())){
								ThreadDownload thread = DataProxy.mapThreadDownload.get(list.get(position).getPackage());
								thread.resumeDownload();
							}
							list.get(position).setDownloadStop(false);
							
							list.get(position).setStatus(Data.DB_VALUE_DOWNLOAD_DOWNLOADING);
							((ImageButton)v).setBackgroundResource(R.drawable.icon_download_manage_downloading);
							((ImageButton)v).setImageResource(R.drawable.icon_download_manage_downloading);
							break;
						}
					}
					
				}
				
			);
		
		return convertView;
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#notifyDataSetChanged()
	 */
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	

}
