/**
 * 
 */
package com.android.aid;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * @author wangpeifeng
 *
 */
public class ThreadDownloadReporter extends Thread {
	
	private static final String TAG = "ThreadDownloadReporter";
	
	private Context context;
	private DataProxy dataproxy;
	private static boolean running = false;
	
	public ThreadDownloadReporter(Context context, DataProxy dataproxy) {
		super();
		this.context = context;
		this.dataproxy = dataproxy;
		running = true;
	}
	
	public static boolean isRunning(){
		return running;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpProxy httpproxy = new HttpProxy(context);
		AppInfoData info = dataproxy.queryDownloadReporter();

		while(info!=null){
			
			String resp = httpproxy.postDownloadReport(info);
			try {
			
				JSONObject obj = new JSONObject(resp);
				boolean api_resp = obj.getBoolean(Data.API_JSON_RESPONSE);

				if(api_resp){
					dataproxy.deleteDownloadReporter(info);
					sleep(500);
				}
				else{
					running = false;
				}

			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(running){
				info = dataproxy.queryDownloadReporter();
			}
			else{
				info = null;
			}
		}
		
		running = false;
		
	}
	
	
	
	

}
