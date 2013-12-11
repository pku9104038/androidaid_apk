/**
 * 
 */
package com.android.aid;

import org.json.JSONObject;

import android.content.Context;

/**
 * @author wangpeifeng
 *
 */
public class ThreadInstallReporter extends Thread {
	
	private static final String TAG = "ThreadInstallReporter";
	
	private Context context;
	private DataProxy dataproxy;
	private static boolean running = false;
	
	public ThreadInstallReporter(Context context, DataProxy dataproxy) {
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
		AppInfoData info = dataproxy.queryInstallReporter();

		while(info!=null){
			
			String resp = httpproxy.postInstallReport(info);
			try {
			
				JSONObject obj = new JSONObject(resp);
				boolean api_resp = obj.getBoolean(Data.API_JSON_RESPONSE);

				if(api_resp){
					dataproxy.deleteInstallReporter(info);
					sleep(500);
				}
				else{
					running = false;
				}

			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(running){
				info = dataproxy.queryInstallReporter();
			}
			else{
				info = null;
			}
		}
		
		running = false;
		
	}
	
	
	
	
}
