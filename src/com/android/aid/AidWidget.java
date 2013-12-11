/**
 * 
 */
package com.android.aid;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.GridView;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;

/**
 * @author wangpeifeng
 *
 */
public class AidWidget extends AppWidgetProvider {

	private static final String TAG = "AidWidget";
	
	private static RemoteViews remoteViews;
	
	
	
	/**
	 * 
	 */
	public AidWidget() {
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onDeleted(android.content.Context, int[])
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
        if (remoteViews == null) {
        	remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
        }
        if (intent.getAction().equals("Recent")) {

        }
        /*
        AppWidgetManager appWidgetManger = AppWidgetManager
                .getInstance(context);
        int[] appIds = appWidgetManger.getAppWidgetIds(new ComponentName(
                context, widgetProvider.class));
        appWidgetManger.updateAppWidget(appIds, rv);		
		*/
		super.onReceive(context, intent);
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onUpdate(android.content.Context, android.appwidget.AppWidgetManager, int[])
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
	    final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        
		Intent startIntent = new Intent();
		startIntent.setClass(context, AndroidAidService.class);
		startIntent.putExtra(Data.SERVICE_REQUEST_APP_DOWNLOADED, true);
		context.startService(startIntent);	
		Log.i(TAG, "start AndroidAidService");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
	}
	
	public static void updateAppWidget(Context context,
            AppWidgetManager appWidgeManger, int appWidgetId) {
/*		
		String[] idName = {
			"RelativeLayoutIM",	
			"RelativeLayoutGame",	
			"RelativeLayoutMusic",	
			"RelativeLayoutIVideo",	
			"RelativeLayoutIShopping",	
			"RelativeLayoutTransport",	
			"RelativeLayoutWeather",	
			"RelativeLayoutWeb",	
			"RelativeLayoutReading",	
			"RelativeLayoutTools",	
			"RelativeLayoutPicture",	
			"RelativeLayoutLife",	
			"RelativeLayoutCamera",	
			"RelativeLayoutNews",	
			"RelativeLayoutRecent",	
			"RelativeLayoutMy"	
		};
		
*/		
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
        Intent intent;
        PendingIntent pendingIntent;
/*
        for(int i=0;i<16;i++){
        	intent = new Intent();
        	intent.setAction(Data.INTENT_ACTION);
        	intent.putExtra(Data.APP_REQUEST,(long)(15-i));
        	pendingIntent = PendingIntent.getBroadcast(context, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT);
        	int id = context.getResources().getIdentifier(idName[i], "id", "com.android.aid");
        	remoteViews.setOnClickPendingIntent(id, pendingIntent);
        }
*/
		intent = new Intent(context,ButtonIMActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_IM);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutIM, pendingIntent);

        
        intent = new Intent(context,ButtonGameActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_GAME);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutGame, pendingIntent);
        
        intent = new Intent(context,ButtonMusicActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_MUSIC);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutMusic, pendingIntent);
 
        intent = new Intent(context,ButtonVideoActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_VIDEO);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutVideo, pendingIntent);
        
        intent = new Intent(context,ButtonShoppingActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_SHOPPING);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutShopping, pendingIntent);
        
        intent = new Intent(context,ButtonTransportActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_TRANSPORT);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutTransport, pendingIntent);
        

        intent = new Intent(context,ButtonWeatherActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_WEATHER);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutWeather, pendingIntent);
        

        intent = new Intent(context,ButtonWebActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_WEB);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutWeb, pendingIntent);
        

        intent = new Intent(context,ButtonReadingActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_READING);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutReading, pendingIntent);
        

        intent = new Intent(context,ButtonToolsActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_TOOLS);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutTools, pendingIntent);
        

        intent = new Intent(context,ButtonPictureActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_PICTURE);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutPicture, pendingIntent);
        

        intent = new Intent(context,ButtonLifeActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_LIFE);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutLife, pendingIntent);
        

        intent = new Intent(context,ButtonCameraActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_CAMERA);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutCamera, pendingIntent);
        

        intent = new Intent(context,ButtonNewsActivity.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_NEWS);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutNews, pendingIntent);
        
        
        intent = new Intent(context,AppManagerRecent.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_RECENT);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutRecent, pendingIntent);
        
        intent = new Intent(context,AppManagerMy.class);
        intent.putExtra(Data.APP_REQUEST, Data.ACTIVITY_MY);
        pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.RelativeLayoutMy, pendingIntent);
        
        
        appWidgeManger.updateAppWidget(appWidgetId, remoteViews);
        
    }

	
}
