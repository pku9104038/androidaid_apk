/**
 * 
 */
package com.android.aid;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;



import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class HttpProxy {
	private static final String TAG = "HttpProxy";
	
	private Context context;
	
	public HttpProxy(Context context) {
		super();
		this.context = context;
	}
	
	public String postLaunchReport(AppInfoData info){
		
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_PACKAGE, info.getPackage()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_LABEL, info.getLabel()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_VERSION_CODE, ""+info.getVersionCode()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_LAUNCH_BY_ME, info.getLaunchByMe()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_LAUNCH_TIME, info.getLaunchTime()));
        
		String strUrl = FileProxy.getApiRootUrl(context)+Data.API_LAUNCH_REPORTER;
        
		return httpPost(strUrl,params);
		
	}

	public String postInstallReport(AppInfoData info){
		
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_PACKAGE, info.getPackage()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_LABEL, info.getLabel()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_VERSION_CODE, ""+info.getVersionCode()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_INSTALL_ACTION, info.getInstallAction()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_INSTALL_ACTION_TIME, info.getInstallActionTime()));
        
		String strUrl = FileProxy.getApiRootUrl(context)+Data.API_INSTALL_REPORTER;
        
		return httpPost(strUrl,params);
		
	}

	public String postDownloadReport(AppInfoData info){
		
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_PACKAGE, info.getPackage()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_LABEL, info.getLabel()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_VERSION_CODE, ""+info.getVersionCode()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_URL, info.getUrl()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_DOWNLOAD_START_TIME, info.getStartTime()));
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_DOWNLOAD_STOP_TIME, info.getStopTime()));
        
		
		String strUrl = FileProxy.getApiRootUrl(context)+Data.API_DOWNLOAD_REPORTER;
        
		return httpPost(strUrl,params);
		
	}

	public String postVersionRepoQuery(){
		    
	    Build build = new Build();
	    String brand=Build.BRAND;
	    String model = Build.MODEL;
	        
	    VERSION version = new VERSION();
	    String SDK_Level = String.valueOf(VERSION.SDK_INT);
	    String ReleaseVersion = VERSION.RELEASE;
	    
	    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair(Data.DB_COLUMN_DEVICE_BRAND, brand));
        params.add(new BasicNameValuePair(Data.DB_COLUMN_DEVICE_MODEL, model));
        params.add(new BasicNameValuePair(Data.DB_COLUMN_DEVICE_SDK, SDK_Level));
        params.add(new BasicNameValuePair(Data.DB_COLUMN_DEVICE_RELEASE, ReleaseVersion));
        
        
        String strUrl = FileProxy.getApiRootUrl(context)+Data.API_VERSION_REPO_QUERY;
        
		return httpPost(strUrl,params);
		
	}
	public String postCategoryQuery(String stamp){
		
		String strUrl  = FileProxy.getApiRootUrl(context)
				+Data.API_CATEGORY_MANAGER_QUERY;
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Data.DB_COLUMN_CATEGORY_STAMP, stamp));
		
		return httpPost(strUrl,params);
		
		
	}
	
	public String postAppRepoQuery(String stamp){
		//postQueryApiAddress();
		
//		String strUrl = Data.API_URL+Data.API_APP_REPO_QUERY;
		
		String strUrl = FileProxy.getApiRootUrl(context)+Data.API_APP_REPO_QUERY;
		// change to original file
		//String strUrl = FileProxy.getApiRootUrl(context)+Data.API_APP_ONLINE_QUERY;
			
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Data.DB_COLUMN_APP_UPDATE_STAMP, stamp));
		
		return httpPost(strUrl,params);
		
	}
	
	
	public void updateConfigFile(){
		
		try{
			
			URL url = new URL(Data.ANDROIDAID_CONFIG_ADDRESS+Data.ANDROIDAID_CONFIG_FILE);
			
		

			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setAllowUserInteraction(true);
			
			File downloadFile = new File(context.getFilesDir().getAbsolutePath()+"/"+Data.ANDROIDAID_CONFIG_FILE);
		
			FileOutputStream fos;
			int savedLength = 0;
			int fileLength = 0; 

			fileLength = httpConnection.getContentLength();
			
			fos = new FileOutputStream(downloadFile,false);
				
			int readLength = 0;
		
			InputStream is = httpConnection.getInputStream();
			byte[] buffer = new byte[1024];
		
			while((readLength=is.read(buffer))>0){
//				Log.i(TAG, "download thread readlength "+ readLength);
				
				try{
					fos.write(buffer, 0, readLength);
					savedLength += readLength;
//					Log.i(TAG, "savedLength = "+savedLength);
				
				}
				catch(Exception foswriteException){
					foswriteException.printStackTrace();	
				}
			}
			
			
			Log.i(TAG, "updateConfigFile:"+url.toString()+" "+ savedLength + " bytes");
			
			fos.close();
			is.close();
/*			
				FileInputStream inStream = context.openFileInput(Data.ANDROIDAID_CONFIG_FILE);  
				StringBuffer sb = new StringBuffer();
				int c;
				while((c=inStream.read())!=-1){
					sb.append((char)c);
					
				}
				inStream.close();
				
			
				//				byte[] data = readData(inStream);  
//				return new String(data);  
				  
//				private byte[] readData(FileInputStream inStream) throws Exception{  
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
//				byte[] buffer = new byte[1024];  
				int len = 0;  
				while( (len = inStream.read(buffer))!= -1){  
				outStream.write(buffer, 0, len);  
				}  
				outStream.close();  
				inStream.close();  
				byte[] data = outStream.toByteArray();  
*/
//				String verjson = sb.toString();
				
//				Log.i(TAG,verjson);
		
		}catch(Exception e){
			Log.i(TAG, "download exception cause: "+e.getCause()+", msg: "+e.getMessage()+", LocMsg: "+e.getLocalizedMessage());
			e.printStackTrace();
	
		}



	}
	
	
	private String httpPost(String strUrl, ArrayList<NameValuePair> params){
		
		//String TAG = "httpPost";
		
		String response = null;
		
		

		TelephonyManager mgrTelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

		//add system parameters
		params.add(new BasicNameValuePair(Data.DB_COLUMN_DEVICE_IMEI, mgrTelephony.getDeviceId()));
		
		params.add(new BasicNameValuePair(Data.API_VERSION, Data.API_VERSION_VALUE));
		
		params.add(new BasicNameValuePair(Data.CHAR_SET, Data.CHAR_SET_GBK));
		
		int verCode=0;
		try {
			verCode = context.getPackageManager().getPackageInfo("com.android.aid", 0).versionCode;
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		params.add(new BasicNameValuePair(Data.DB_COLUMN_VERSION_CODE,""+verCode));

		
		
	          		
	    try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost(strUrl);
	    
	    	httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
	    
	    	Log.i(TAG, "post = "+strUrl);
	    	HttpResponse httpResp = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResp.getEntity();

		    if(httpEntity != null){
				BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
				String line = null;
				response = new String();
				while((line = br.readLine()) != null){
					response += line;
				}
		    	Log.i(TAG, "resp = "+response);
			}
		    
	    }catch (IOException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    	Log.i(TAG, "cause:"+e.getCause()+",msg:"+e.getMessage());
		}
		
		return response;
		
	}
	
	/*
	public void checkServerCode(){
		
		String response = null;
		String strUrl = "http://192.168.1.250:8080/qk_shuttle/admin/";
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				
	          		
	    try {
	    	
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost(strUrl);
	    
	    	httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.ISO_8859_1));
	    
	    	Log.i(TAG, "post = "+strUrl);
	    	HttpResponse httpResp = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResp.getEntity();
			String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/" + "servercode.html";
			
			FileOutputStream fos = new FileOutputStream(new File(path),false);

		    if(httpEntity != null){
				BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
				String line = null;
				byte[] buf;
				response = new String();
				while((line = br.readLine()) != null){
					response += line;
					buf = line.getBytes("GBK");
					fos.write(buf, 0, line.length());
					
					
				}
		    	Log.i(TAG, "resp = "+response);
			}
		    
	    }catch (Exception e){
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    	Log.i(TAG, "cause:"+e.getCause()+",msg:"+e.getMessage()+",locMsg:"+e.getLocalizedMessage());
	    }		
		
	}
*/
}
