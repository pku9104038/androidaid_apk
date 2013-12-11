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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
 * for recentActivity
 */
public class GridAdapterPackage extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private String defType, defPackage;
	private ArrayList<String> gridlist;
	private ArrayList<Bitmap> bmpList = new ArrayList<Bitmap>();
	private PackageManager packageManager;

	/**
	 * 
	 */
	public GridAdapterPackage(Context context,ArrayList<String> gridlist,PackageManager packageManager) {
		// TODO Auto-generated constructor stub
		this.context =context;
		inflater = LayoutInflater.from(context);
		defType = new String("drawable");
		defPackage = new String(context.getPackageName());
		
		this.gridlist = gridlist;
		this.packageManager = packageManager;

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
		TextView tv = (TextView)convertView.findViewById(R.id.gridText);
		convertView.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
		convertView.findViewById(R.id.progressBarIcon).setVisibility(View.INVISIBLE);
		convertView.findViewById(R.id.textViewProgress).setVisibility(View.INVISIBLE);
		
		ProgressBar pbInstalled = (ProgressBar)convertView.findViewById(R.id.progressBarInstalled);
		pbInstalled.setProgress(100);
		
		Drawable icon;
		CharSequence lable;
		try {
			icon = packageManager.getApplicationIcon(gridlist.get(position));
			ApplicationInfo appInfo = packageManager.getApplicationInfo(gridlist.get(position), PackageManager.GET_META_DATA);
			lable = packageManager.getApplicationLabel(appInfo);
			//iv.setBackgroundDrawable(icon);
			iv.setImageDrawable(icon);
			tv.setText(lable);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	
	private boolean restoreIcon(String icon, String path){
		
		File dir = new File(context.getFilesDir()+"/icon");
		if(!dir.exists())
			dir.mkdir();
	
		try {
			
			InputStream iStream = context.getAssets().open(icon);
			OutputStream oStream  = new FileOutputStream(path,false);
			
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = iStream.read(buffer))>0){
				oStream.write(buffer, 0, length);
			}
			
			oStream.flush();
			oStream.close();
			iStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File fileIcon = new File(path);
	
		return fileIcon.exists();
		
		
	}
	
	public void bitmapRecycle(){
		Iterator<Bitmap> i = bmpList.iterator();
		while(i.hasNext()){
			i.next().recycle();
		}
	}



}
