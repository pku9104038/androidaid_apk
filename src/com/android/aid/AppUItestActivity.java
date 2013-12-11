/**
 * 
 */
package com.android.aid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author wangpeifeng
 *
 */
public class AppUItestActivity extends Activity {
	private static final String TAG = "AppUItestActivity";
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setTheme(R.style.Transparent);
		setContentView(R.layout.appmanager);
		
		super.onCreate(savedInstanceState);
	}
	
	
}
