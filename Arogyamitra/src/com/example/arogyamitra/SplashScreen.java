package com.example.arogyamitra;

import com.example.arogyamitra.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SplashScreen extends Activity {

	Thread splashTread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_scrren);
		//Hack to convert all fonts to Raleway (based on Andy/Arka's link)
		//Add all TextViews, as and when you add them to xml, here too.
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.otf");
		int [] textViewList = new int [] {
			R.id.appSubTitle,
		};
		for (int i : textViewList ){
			((TextView) findViewById(i)).setTypeface(tf);
		}
		tf = Typeface.createFromAsset(getAssets(), "fonts/Steinerlight.ttf");
		textViewList = new int [] {
			R.id.appTitle,
		};
		for (int i : textViewList ){
			((TextView) findViewById(i)).setTypeface(tf);
		}
		
		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 2000) {
						sleep(100);
						waited += 100;
					}
					Intent intent = new Intent(SplashScreen.this,
							FormPage3.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					
					startActivity(intent);
					SplashScreen.this.finish();
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					SplashScreen.this.finish();
				}

			}
		};
		splashTread.start();
	}

}
