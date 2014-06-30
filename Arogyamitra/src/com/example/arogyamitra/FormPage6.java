package com.example.arogyamitra;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FormPage6 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_page6);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_page6, menu);
		return true;
	}

}
