/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class ThreadDownload extends Thread {
	
	private static final String TAG = "ThreadDownload";
	private AppInfoData appInfoData;
	private DataProxy dataproxy;
	private Context context;
	private boolean running = true;
	private boolean pause = false;
	
	//public static HashMap<String,ThreadDownload> mapThreadDownload;
	
	public static ArrayList<ThreadDownload> listThreadDownload; 

	public ThreadDownload(Context context,AppInfoData appInfoData, DataProxy dataproxy) {
		super();
		this.appInfoData = appInfoData;
		//this.dataproxy = dataproxy;
		this.context = context;
		this.dataproxy = dataproxy;
		running = true;
		Log.i(TAG, "ThreadDownload "+ appInfoData.getUrl());
	}
	
	public void stopRunning(){
		running = false;
	}
	
	public void pauseDownload(){
		pause = true;
	}
	public void resumeDownload(){
		pause = false;
	}
	
	public static boolean isDownloading(String packageName){
//		if(mapThreadDownload==null){
//			mapThreadDownload = new HashMap<String,ThreadDownload>();
//		}
		return DataProxy.mapThreadDownload.containsKey(packageName);
	}
	/*
	public static boolean isDownloading(String packageName){
		
		if(listThreadDownload==null){
			listThreadDownload = new ArrayList<ThreadDownload>();
		}
		
		
		Iterator iterator = listThreadDownload.iterator();
		if(iterator.hasNext()){
			ThreadDownload threadDownload  = (ThreadDownload) iterator.next();
			AppInfoData appInfoData = threadDownload.getAppInfoData();
			
			if(packageName.equals(appInfoData.getPackage())){
				return true;
			}
			
		}
		
		return false;		
		
	}
	*/
	
	public long getFileLength(){
		return appInfoData.getFileLength();
	}
	
	public static long getFileLength(String packageName){
		AppInfoData appInfoData = getAppInfoData(packageName);
		if(appInfoData == null ){
			return -1;
		}
		else{
			return appInfoData.getFileLength();
		}
	}
	
	public long getDownloadLength(){
		return appInfoData.getDownloadedLength();
	}
	
	public static long getDownloadLength(String packageName){
		AppInfoData appInfoData = getAppInfoData(packageName);
		if(appInfoData == null ){
			return -1;
		}
		else{
			return appInfoData.getDownloadedLength();
		}
	}
	
	public static int getIndexOf(String packageName ){

		if(listThreadDownload==null){
			listThreadDownload = new ArrayList<ThreadDownload>();
		}
		
		Iterator iterator = listThreadDownload.iterator();
		int i = 0;
		if(iterator.hasNext()){
			ThreadDownload threadDownload  = (ThreadDownload) iterator.next();
		
			if(packageName.equals(threadDownload.getAppInfoData().getPackage())){
				return i;
			}
			i++;
			
		}
		
		return -1;
	}
	
	public static AppInfoData getAppInfoData(String packageName){
		
		if(listThreadDownload==null){
			listThreadDownload = new ArrayList<ThreadDownload>();
		}
		
		Iterator iterator = listThreadDownload.iterator();
		if(iterator.hasNext()){
			ThreadDownload threadDownload  = (ThreadDownload) iterator.next();
			AppInfoData infoData = threadDownload.getAppInfoData();
			
			if(packageName.equals(infoData.getPackage())){
				return infoData;
			}
			
		}
		
		return null;
	}

	public static void addThread(ThreadDownload thread){
		if(listThreadDownload==null){
			listThreadDownload = new ArrayList<ThreadDownload>();
		}
		listThreadDownload.add(thread);

//		if(mapThreadDownload==null){
//			mapThreadDownload = new HashMap<String,ThreadDownload>();
//		}
		DataProxy.mapThreadDownload.put(thread.getAppInfoData().getPackage(), thread);
	}
	
	private void removeThread(ThreadDownload thread){
		DataProxy.mapThreadDownload.remove(thread.getAppInfoData().getPackage());
		listThreadDownload.remove(thread);
	}
	

	public static ThreadDownload getThread(int index){
		if(listThreadDownload==null){
			listThreadDownload = new ArrayList<ThreadDownload>();
		}
		if(listThreadDownload.size()>index){
			return listThreadDownload.get(index);
		}
		else{
			return null;
		}
			
		
	}
	public static int getListSize(){
		if(listThreadDownload==null){
			listThreadDownload = new ArrayList<ThreadDownload>();
		}
		
		return listThreadDownload.size();
		
	}
	
	public AppInfoData getAppInfoData(){
		return appInfoData;
	}
/*	
	public String getPath(){
		return appInfoData.getPath();
	}
*/
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try{
			
		
			String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
			
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			
			file = new File(file.getAbsoluteFile()+"/icon");
			if(!file.exists()){
				file.mkdir();
			}
					
			FileOutputStream fos;
			
			long savedLength =0;//= appInfoData.getDownloadedLength();
			long fileLength = 0;//appInfoData.getFileLength(); 
/*
			String strUrl = appInfoData.getUrl();
			Log.i(TAG, "strUrl:"+strUrl);
			Log.i(TAG, "strUrlHex:"+Common.stringToHex(strUrl));
			
					
			String str_utf8 = new String(strUrl.getBytes("UTF-8"), "GBK");
			Log.i(TAG, "str_utf8:"+str_utf8);
			Log.i(TAG, "str_utf8Hex:"+Common.stringToHex(str_utf8));
			//String str_gbk = new String(strUrl.getBytes("iso8859-1"), "GBK");
			String str_gbk = new String(strUrl.getBytes("UTF-8"), "iso8859-1");
			Log.i(TAG, str_gbk);
			Log.i(TAG, Common.stringToHex(str_gbk));
			
			
			try{
				File filestr = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/stroriginal.json");
				FileOutputStream strOS = new FileOutputStream(filestr,false);
				byte[] buffer = strUrl.getBytes();
				strOS.write(buffer, 0, buffer.length);
				strOS.close();
			
				filestr = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/strutf8gbk.json");
				strOS = new FileOutputStream(filestr,false);
				buffer = str_utf8.getBytes();
				strOS.write(buffer, 0, buffer.length);
				strOS.close();
			
				filestr = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/strutf8iso.json");
				strOS = new FileOutputStream(filestr,false);
				buffer = str_gbk.getBytes();
				strOS.write(buffer, 0, buffer.length);
				strOS.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
	*/		
			//URL url = new URL(appInfoData.getUrl());
			String urlpath = appInfoData.getUrl().substring(0, appInfoData.getUrl().lastIndexOf("/")+1);
			String urlfile = appInfoData.getUrl().substring(appInfoData.getUrl().lastIndexOf("/")+1);
			
			URL url = new URL(urlpath+java.net.URLEncoder.encode(urlfile));

			HttpURLConnection httpConnection = null;
			try{
				httpConnection = (HttpURLConnection) url.openConnection();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
			
			
			httpConnection.setAllowUserInteraction(true);

			fileLength = httpConnection.getContentLength();
			appInfoData.setFileLength(fileLength);

			File downloadFile = new File(appInfoData.getPath());

			if(downloadFile.exists()){
				savedLength = (new RandomAccessFile(downloadFile,"rw")).length();
				appInfoData.setDownloadedLength(savedLength);
			}
			
			if(fileLength>1 && savedLength > 0 && savedLength < fileLength )
//				&& (new RandomAccessFile(downloadFile,"rw")).length() == savedLength)
			{
//				fileLength = httpConnection.getContentLength();
//				appInfoData.setFileLength(fileLength);
//				dataproxy.updateAppDownloadFileLength(appInfoData);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setAllowUserInteraction(true);
				httpConnection.setRequestProperty("Range", "bytes=" + savedLength + "-" + fileLength);
				fos = new FileOutputStream(downloadFile,true);
				
			}
			else{
				
//				fileLength = httpConnection.getContentLength();
//				appInfoData.setFileLength(fileLength);
				savedLength = 0;
				appInfoData.setDownloadedLength(0);
				
				dataproxy.updateAppDownloadFileLength(appInfoData);
//				dataproxy.updateAppDownloadSavedLength(appInfoData);
				
				fos = new FileOutputStream(downloadFile,false);
				
			}
			
			int readLength = 0;
		
			InputStream is = httpConnection.getInputStream();
			
			byte[] buffer = new byte[8192];
			Log.i(TAG, url.toString());
		
/*			
			Log.i(TAG, appInfoData.getUrl());
			String url_original = appInfoData.getUrl();
			Log.i(TAG, Common.stringToHex(url_original));
			
			String url_utf8 = new String(url_original.getBytes("gbk"), "UTF-8");
			Log.i(TAG, url_utf8);
			Log.i(TAG, Common.stringToHex(url_utf8));
			String url_gbk = new String(url_original.getBytes("iso8859-1"), "GBK");
			Log.i(TAG, url_gbk);
			Log.i(TAG, Common.stringToHex(url_gbk));
*/			
			while(running && savedLength<fileLength){
				
				if(pause){
					Thread.sleep(1000);
				}
				else{
					Thread.sleep(10);
						
				}
				
				if(!pause){
					readLength=is.read(buffer);
				
//				while(running && (readLength=is.read(buffer))>0 && savedLength<fileLength){
//					Log.i(TAG, "download thread readlength "+ readLength);
				
					try{
						fos.write(buffer, 0, readLength);
						savedLength += readLength;
						appInfoData.setDownloadedLength(savedLength);
					
						Log.i(TAG, fileLength+"/"+savedLength+" "+((int)(savedLength*100/fileLength))+"% :"+appInfoData.getLabel());
//						Log.i(TAG, url.toString());
//						Log.i(TAG, downloadFile.toString());
//						dataproxy.updateAppDownloadSavedLength(appInfoData);
				
					}
					catch(Exception foswriteException){
						removeThread(this);
						Log.i(TAG, "file write exception, download thread remove "+ appInfoData.getUrl());

					}
				
//				}
			
				}
			}
			
			if(savedLength >= fileLength && fileLength > 0){
				Log.i(TAG,"finishDownloading" + appInfoData.getFileName());
				dataproxy.finishDownloading(appInfoData);
				if(".apk".equals(appInfoData.getPath().substring(appInfoData.getPath().length()-".apk".length()).toLowerCase())){
					if(Data.DB_VALUE_PACKAGE_ANDROIDAID.equals(appInfoData.getPackage())){
						Common.notifyNewVersion(context, appInfoData.getPath());
					}
					else{
	       				Intent startIntent = new Intent();
	       				startIntent.setAction(android.content.Intent.ACTION_VIEW);
	       				startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	       				startIntent.setDataAndType(Uri.fromFile(new File(appInfoData.getPath())),    
	       		                "application/vnd.android.package-archive");    
	       		        context.startActivity(startIntent); 
					}
				}
			}
			else{
				Log.i(TAG,"Download stoped!" + appInfoData.getFileName());
				
			}
			
			
			fos.close();
			is.close();
			
			removeThread(this);

		
		}catch(Exception e){
			
			Log.i(TAG, "download exception cause: "+e.getCause()+", msg: "+e.getMessage()+", LocMsg: "+e.getLocalizedMessage());
			e.printStackTrace();
			removeThread(this);
			Log.i(TAG, "download thread remove "+ appInfoData.getUrl());

		}


	}
	
	

}
