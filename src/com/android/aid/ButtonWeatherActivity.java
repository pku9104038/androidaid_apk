/**
 * 
 */
package com.android.aid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author wangpeifeng
 *
 */
public class ButtonWeatherActivity extends Activity {
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Intent intent;
		intent = getIntent();
		String name = intent.getStringExtra(Data.APP_REQUEST);
		intent = new Intent(this,AppManagerActivity.class);
		intent.putExtra(Data.APP_REQUEST,name);
		startActivityForResult(intent, 0);
		
		super.onCreate(savedInstanceState);
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		this.setResult(0);
		finish();
	}
	
	
}
