package com.example.arogyamitra;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;


public class FormPage3 extends Activity {
	
	private Button next;
	private Spinner question1, question3;// question2;
	private MultiSelectionSpinner  question2;
	private String subOptions[];
	private String oldItem;
	private int validationFlags[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_page3);
		
		next = (Button) findViewById(R.id.button1);
		//next.setEnabled(false);
		question1 = (Spinner) findViewById(R.id.spinner1);
		question2 = (MultiSelectionSpinner) findViewById(R.id.spinner2);
		question3 = (Spinner) findViewById(R.id.spinner3);
		//question1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		//question2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		//question3.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		question2.setItems(getResources().getStringArray(R.array.vas_benefits));
		next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("info", "Its working");
				Log.d("Question 1", question1.getSelectedItem().toString());
				Log.d("Question 2", question2.getSelectedItemsAsString());
				Log.d("Question 3", question3.getSelectedItem().toString());
				String answer1 = question1.getSelectedItem().toString();
				String answer2 = question2.getSelectedItemsAsString();
				String answer3 = question3.getSelectedItem().toString();
				Intent i = new Intent(FormPage3.this, FormPage4.class);
				i.putExtra("answer1",answer1);
				i.putExtra("answer2",answer2);
				i.putExtra("answer3",answer3);
            	startActivity(i);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_page3, menu);
		return true;
	}

}
