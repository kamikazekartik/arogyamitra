package com.example.arogyamitra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FormPage4 extends Activity implements OnItemSelectedListener {

	private Button next;
	private Spinner question4, question5, question6;
	private String oldItem;
	private int validationFlags[];
	private String answer5Hospital = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_page4);
		next = (Button) findViewById(R.id.button1);
		question4 = (Spinner) findViewById(R.id.spinner1);
		question5 = (Spinner) findViewById(R.id.spinner2);
		question6 = (Spinner) findViewById(R.id.spinner2);
		question5.setOnItemSelectedListener(this);
		question5.setSelection(1);
		oldItem = question5.getItemAtPosition(question5.getSelectedItemPosition()).toString();

		next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String answer4 = question4.getSelectedItem().toString();
				String answer5 = question5.getSelectedItem().toString();
				String answer6 = question6.getSelectedItem().toString();
				Intent i = new Intent(FormPage4.this, FormPage5.class);
				String answer1 = getIntent().getExtras().getString("answer1");
				String answer2 = getIntent().getExtras().getString("answer2");
				String answer3 = getIntent().getExtras().getString("answer3");
				i.putExtra("answer1",answer1);
				i.putExtra("answer2",answer2);
				i.putExtra("answer3",answer3);
				i.putExtra("answer4",answer4);
				i.putExtra("answer5",answer5);
				i.putExtra("answer5Hospital",answer5Hospital);
				i.putExtra("answer6",answer6);
            	startActivity(i);

			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_page4, menu);
		return true;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		String currentItem = parent.getItemAtPosition(pos).toString();
		Log.d("Old item", oldItem);
		Log.d("Current Item", currentItem);

		//TODO: This is a hack.. It works but rather change it using notifyDataSetChanged with baseAdapter implementation
		if (!currentItem.equals(oldItem)){
			if(currentItem.equals("Yes")){
				//Prompt for hospital name
				AlertDialog.Builder builder= new AlertDialog.Builder(FormPage4.this);
				LayoutInflater inflater= getLayoutInflater();
				final View myView= inflater.inflate(R.layout.alert_dialog_page, null);
				builder.setTitle("Mitra");
				builder.setMessage("What is the name of the hospital");
				builder.setView(myView);

				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
					public void onClick(DialogInterface dialog, int whichButton) {  
						//String value = input.getText().toString();
						EditText input = (EditText) myView.findViewById(R.id.EditText01);
						answer5Hospital = input.getText().toString();
						Log.d("info", answer5Hospital);
						return;                  
					}  
				});

				AlertDialog alert= builder.create();
				alert.show();
			}
		}
		oldItem = currentItem;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

}
