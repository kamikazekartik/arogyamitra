package com.example.arogyamitra;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FormPage5 extends Activity {
	
	private Button next;
	private Spinner question7;
	private EditText question8, question9;
	private int validationFlags[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_page5);
		
		next = (Button) findViewById(R.id.button1);
		question7 = (Spinner) findViewById(R.id.spinner1);
		question8 = (EditText) findViewById(R.id.editText1);
		question9 = (EditText) findViewById(R.id.editText2);
		
		next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String answer7 = question7.getSelectedItem().toString();
				String answer8 = question8.getText().toString();
				String answer9 = question9.getText().toString();
				Intent i = new Intent(FormPage5.this, FormPage6.class);
				String answer1 = getIntent().getExtras().getString("answer1");
				String answer2 = getIntent().getExtras().getString("answer2");
				String answer3 = getIntent().getExtras().getString("answer3");
				String answer4 = getIntent().getExtras().getString("answer4");
				String answer5 = getIntent().getExtras().getString("answer5");
				String answer6 = getIntent().getExtras().getString("answer6");
				String answer5Hospital = getIntent().getExtras().getString("answer5Hospital");
				i.putExtra("answer1",answer1);
				i.putExtra("answer2",answer2);
				i.putExtra("answer3",answer3);
				i.putExtra("answer4",answer4);
				i.putExtra("answer5",answer5);
				i.putExtra("answer5Hospital",answer5Hospital);
				i.putExtra("answer6",answer6);
				i.putExtra("answer7",answer7);
				i.putExtra("answer8",answer8);
				i.putExtra("answer9",answer9);
            	startActivity(i);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_page5, menu);
		return true;
	}

}
