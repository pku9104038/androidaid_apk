/**
 * 
 */
package com.android.aid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout;

/**
 * @author wangpeifeng
 *
 */
public class AndroidAidActivity extends Activity {
	
	private static final String TAG = "AndroidAidActivity";
	
	Context context;
	
	RelativeLayout imageButtonIM, imageButtonGame,imageButtonMusic,imageButtonVideo,
		imageButtonShopping,imageButtonTransport,imageButtonWeather,imageButtonWeb,
		imageButtonReading,imageButtonTools,imageButtonPicture,imageButtonLife,
		imageButtonCamera,imageButtonNews,imageButtonRecent,imageButtonMy;
	
	RelativeLayout clickIM, clickGame, clickMusic, clickVideo, clickShopping, clickTransport,
		clickWeather,clickWeb,clickReading,clickTools,clickPicture,clickLife,clickCamera,clickNews,
		clickRecent,clickMy;
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		context = this;
		
		setContentView(R.layout.main);
		//imageButtonIM = (RelativeLayout)findViewById(R.id.RelativeLayoutIM);
		//imageButtonIM.setOnClickListener(listenerClick);
		clickIM = (RelativeLayout)findViewById(R.id.RelativeLayoutIM);
		clickIM.setOnClickListener(listenerClick);
		
		clickGame = (RelativeLayout)findViewById(R.id.RelativeLayoutGame);
		clickGame.setOnClickListener(listenerClick);
		
		clickMusic = (RelativeLayout)findViewById(R.id.RelativeLayoutMusic);
		clickMusic.setOnClickListener(listenerClick);
		
		clickVideo = (RelativeLayout)findViewById(R.id.RelativeLayoutVideo);
		clickVideo.setOnClickListener(listenerClick);
		

		clickShopping = (RelativeLayout)findViewById(R.id.RelativeLayoutShopping);
		clickShopping.setOnClickListener(listenerClick);
		

		clickTransport = (RelativeLayout)findViewById(R.id.RelativeLayoutTransport);
		clickTransport.setOnClickListener(listenerClick);
		

		clickWeather = (RelativeLayout)findViewById(R.id.RelativeLayoutWeather);
		clickWeather.setOnClickListener(listenerClick);
		

		clickWeb = (RelativeLayout)findViewById(R.id.RelativeLayoutWeb);
		clickWeb.setOnClickListener(listenerClick);
		

		clickReading = (RelativeLayout)findViewById(R.id.RelativeLayoutReading);
		clickReading.setOnClickListener(listenerClick);
		

		clickTools = (RelativeLayout)findViewById(R.id.RelativeLayoutTools);
		clickTools.setOnClickListener(listenerClick);
		

		clickPicture = (RelativeLayout)findViewById(R.id.RelativeLayoutPicture);
		clickPicture.setOnClickListener(listenerClick);
		

		clickLife = (RelativeLayout)findViewById(R.id.RelativeLayoutLife);
		clickLife.setOnClickListener(listenerClick);
		

		clickCamera = (RelativeLayout)findViewById(R.id.RelativeLayoutCamera);
		clickCamera.setOnClickListener(listenerClick);
		

		clickNews = (RelativeLayout)findViewById(R.id.RelativeLayoutNews);
		clickNews.setOnClickListener(listenerClick);
		
		clickRecent = (RelativeLayout)findViewById(R.id.RelativeLayoutRecent);
		clickRecent.setOnClickListener(listenerClick);
		
		clickMy = (RelativeLayout)findViewById(R.id.RelativeLayoutMy);
		clickMy.setOnClickListener(listenerClick);
		
		Intent startIntent = new Intent();
		Log.i(TAG, "start AndroidAidService");
		startIntent.setClass(context, AndroidAidService.class);
		
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_CLOUD, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_INSTALLED, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADING, true);
		startIntent.putExtra(Data.SERVICE_REQUEST_DOWNLOAD_REPORTER, true);
				
		context.startService(startIntent);	
		
		super.onCreate(savedInstanceState);
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		finish();
		super.onStop();
	}



	OnClickListener listenerClick = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch(v.getId()){
			case R.id.RelativeLayoutIM:
				intent.setClass(getApplicationContext(), ButtonIMActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_IM);
				break;
				
			case R.id.RelativeLayoutGame:
				intent.setClass(getApplicationContext(), ButtonGameActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_GAME);
				
				break;
				
				
			case R.id.RelativeLayoutMusic:
				intent.setClass(getApplicationContext(), ButtonMusicActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_MUSIC);
				
				break;
				
				
			case R.id.RelativeLayoutVideo:
				intent.setClass(getApplicationContext(), ButtonVideoActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_VIDEO);
				
				break;
				
				
			case R.id.RelativeLayoutShopping:
				intent.setClass(getApplicationContext(), ButtonShoppingActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_SHOPPING);
				
				break;
				
				
			case R.id.RelativeLayoutTransport:
				intent.setClass(getApplicationContext(), ButtonTransportActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_TRANSPORT);
				
				break;
				
				
			case R.id.RelativeLayoutWeather:
				intent.setClass(getApplicationContext(), ButtonWeatherActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_WEATHER);
				
				break;
				
				
			case R.id.RelativeLayoutWeb:
				intent.setClass(getApplicationContext(), ButtonWebActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_WEB);
				
				break;
				
				
			case R.id.RelativeLayoutReading:
				intent.setClass(getApplicationContext(), ButtonReadingActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_READING);
				
				break;
				
				
			case R.id.RelativeLayoutTools:
				intent.setClass(getApplicationContext(), ButtonToolsActivity.class);
				//intent.setClass(getApplicationContext(), AppManagerTools.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_TOOLS);
				
				break;
				
				
			case R.id.RelativeLayoutPicture:
				intent.setClass(getApplicationContext(), ButtonPictureActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_PICTURE);
				
				break;
				
				
			case R.id.RelativeLayoutLife:
				intent.setClass(getApplicationContext(), ButtonLifeActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_LIFE);
				
				break;
				
				
			case R.id.RelativeLayoutCamera:
				intent.setClass(getApplicationContext(), ButtonCameraActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_CAMERA);
				
				break;
				
				
			case R.id.RelativeLayoutNews:
				intent.setClass(getApplicationContext(), ButtonNewsActivity.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_NEWS);
				
				break;
				
				
			case R.id.RelativeLayoutRecent:
				intent.setClass(getApplicationContext(), AppManagerRecent.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_RECENT);
				
				break;
				
			case R.id.RelativeLayoutMy:
				intent.setClass(getApplicationContext(), AppManagerMy.class);
				intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_MY);
				
				break;
			
			
			}
			
			startActivityForResult(intent, 0);
			
			
		}
		
	};

}
