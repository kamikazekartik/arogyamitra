package com.example.arogyamitra;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.arogyamitra.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageGallery extends Activity{

	private static final int PICK_IMAGE = 1;
	private static final int PICK_CAMERA_IMAGE = 2;
	private ImageView imgView;
	private Button upload,cancel;
	private Button flagBtn, aboutBtn, faqsBtn;
	private ScrollView aboutLayout, faqsLayout;
	private RelativeLayout imageGalleryLayout;
	private Button clickImage;
	private LinearLayout btnsLayout;
	private Bitmap bitmap;
	private ProgressDialog dialog;
	Uri imageUri;
	Uri selectedImageUri = null;
	private String selectedImagePath = null;
	private LinearLayout nextStageButtons;
	MediaPlayer mp=new MediaPlayer();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_gallery);
		/*
		 * The 4 btn menu system
		 * 
		 * So there are 4 buttons (Flag, About, FAQs, Review)
		 * And then there are 3 different layouts that are stacked one on
		 * another countesy the root layout: A FrameLayout. (A FrameLayout
		 * just stacks it's children one over the other) Now our job is to
		 * bring one of the children layouts front when every button is clicked.
		 * I'll try to program the buttons, but the guide I'm using 
		 * is: http://blog.neteril.org/blog/2013/10/10/framelayout-your-best-ui-friend/,
		 * which is very nicely written. Maybe someone else will understand
		 * better than my sleepyhead. (it's 2:32!)
		 * 
		 * TODO:
		 * Arrange the btnsLayout so it doesn't overlap on other layouts (decide where it can fit)
		 * Prettify
		 * (Get rid of the top black bar that shows the app name?)
		 */
		nextStageButtons = (LinearLayout) findViewById(R.id.nextStageButtons);
		nextStageButtons.setVisibility(View.INVISIBLE);
		btnsLayout = (LinearLayout) findViewById(R.id.btnsLayout);
		clickImage = (Button)findViewById(R.id.clickimagebtn);
		clickImage.setVisibility(View.VISIBLE);
		flagBtn = (Button) findViewById(R.id.flagBtn);
		imageGalleryLayout = (RelativeLayout) findViewById(R.id.imageGalleryLayout); 
		//oops, flagBtn is for imageGalleryLayout. Could've called it imageGalleryBtn, but :/ didn't
		aboutBtn = (Button) findViewById(R.id.aboutBtn);
		aboutLayout = (ScrollView) findViewById(R.id.aboutScroller);
		faqsBtn = (Button) findViewById(R.id.faqsBtn);
//		Log.d("Hi, reached here", "text");
		faqsLayout = (ScrollView) findViewById(R.id.faqsScroller);
		TextView faqsTextBox = (TextView)findViewById(R.id.TextView01);
		String formattedText = getString(R.string.faqString);
		Spanned result = Html.fromHtml(formattedText);
		faqsTextBox.setText(result);
		
		TextView aboutTextBox = (TextView)findViewById(R.id.textView1);
		formattedText = getString(R.string.aboutString);
		result = Html.fromHtml(formattedText);
		aboutTextBox.setText(result);
		

		//Hack to convert all fonts to Raleway (based on Andy/Arka's link)
		//Add all TextViews, as and when you add them to xml, here too.
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.otf");
		int [] textViewList = new int [] {
			R.id.TextView01,
			R.id.TextView02,
			R.id.textView1,
			R.id.aboutTitle,
		};
		for (int i : textViewList ){
			((TextView) findViewById(i)).setTypeface(tf);
		}
		
		flagBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutLayout.setVisibility(View.INVISIBLE);
				faqsLayout.setVisibility(View.INVISIBLE);
				imageGalleryLayout.setVisibility(View.VISIBLE);
				imageGalleryLayout.bringToFront();
				btnsLayout.bringToFront();
			}
		});
		aboutBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				faqsLayout.setVisibility(View.INVISIBLE);
				imageGalleryLayout.setVisibility(View.INVISIBLE);
				aboutLayout.setVisibility(View.VISIBLE);
				aboutLayout.bringToFront();
				btnsLayout.bringToFront();
			}
		});
		faqsBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutLayout.setVisibility(View.INVISIBLE);
				imageGalleryLayout.setVisibility(View.INVISIBLE);
				faqsLayout.setVisibility(View.VISIBLE);
				faqsLayout.bringToFront();
				btnsLayout.bringToFront();
			}
		});
		
		
		
		
		imgView = (ImageView) findViewById(R.id.picSlot);
		upload = (Button) findViewById(R.id.imguploadbtn);
		cancel = (Button) findViewById(R.id.imgcancelbtn);
		
		upload.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (bitmap == null) {
					Toast.makeText(getApplicationContext(),
							"Please select image", Toast.LENGTH_SHORT).show();
				} else {
					dialog = ProgressDialog.show(ImageGallery.this, "Uploading",
							"Please wait...", true);
					new ImageGalleryTask().execute();
					Intent sendMailIntent = new Intent(ImageGallery.this, FormPage1.class);
	            	sendMailIntent.putExtra("imageUri",getPath(selectedImageUri));
	            	startActivity(sendMailIntent);
				}
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ImageGallery.this.finish();
				bitmap = null;
				imageUri = null;
				imgView.setImageResource(android.R.color.transparent);
				Toast.makeText(getApplicationContext(),"Image has been cancelled. Click another.", Toast.LENGTH_SHORT).show();
				clickImage.setVisibility(View.VISIBLE);
				nextStageButtons.setVisibility(View.INVISIBLE);
			}
			
		});
		
		clickImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//define the file-name to save photo taken by Camera activity
				String fileName = "new-photo-name.jpg";
				//create parameters for Intent with filename
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.TITLE, fileName);
				values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
				//imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
				imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				//create new Intent
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
				startActivityForResult(intent, PICK_CAMERA_IMAGE);
			}
		});

		/*next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (selectedImageUri == null) {
					Toast.makeText(getApplicationContext(),
							"Please select image", Toast.LENGTH_SHORT).show();
				} else {
					//Log.d("msg", "hello, world");
					Intent sendMailIntent = new Intent(ImageGallery.this, SendMailScreen.class);
					sendMailIntent.putExtra("imageUri","file://");
	            	sendMailIntent.putExtra("imageUri","file://" + getPath(selectedImageUri));
	            	
	            	
	            	startActivity(sendMailIntent);
					//Intent i = new Intent(Intent.ACTION_SEND);
					//i.setType("message/rfc822");
					//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"anindita.ravikum@gmail.com"});
					//i.putExtra(Intent.EXTRA_SUBJECT, "WAZAAAAAAA!!!");
					//i.putExtra(Intent.EXTRA_TEXT   , "Whos your daddy! -sent from android :P");
					//Log.d("Image Path", getPath(selectedImageUri));
					//i.setType("application/image");
					//i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + getPath(selectedImageUri)));
					//i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///storage/emulated/11/DCIM/Camera/1401372834496.jpg"));
					//Not sure about this section
					//TODO: Fix this
					//i.putExtra(Intent.EXTRA_STREAM, Uri.parse(getPath(selectedImageUri)));
					/*try {
						startActivity(Intent.createChooser(i, "Send mail..."));
					} catch (android.content.ActivityNotFoundException ex) {
						Toast.makeText(ImageGallery.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});*/

	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_image_gallery, menu);
		return true;
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.camera:
			//define the file-name to save photo taken by Camera activity
			String fileName = "new-photo-name.jpg";
			//create parameters for Intent with filename
			ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, fileName);
			values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
			//imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
			imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			//create new Intent
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(intent, PICK_CAMERA_IMAGE);
			return true;

//TODO: Gallery section
/*		case R.id.gallery:
			try {
				Intent gintent = new Intent();
				gintent.setType("image/*");
				gintent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(gintent, "Select Picture"),
						PICK_IMAGE);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						e.getMessage(),
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
			return true;*/
		}
		return false;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String filePath = null;
		switch (requestCode) {
		case PICK_IMAGE:
			if (resultCode == Activity.RESULT_OK) {
				selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
			}
			break;
		case PICK_CAMERA_IMAGE:
			if (resultCode == RESULT_OK) {
				//use imageUri here to access the image
				selectedImageUri = imageUri;
				clickImage.setVisibility(View.INVISIBLE);
				nextStageButtons.setVisibility(View.VISIBLE);
				/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
			}
			break;
		}

		if(selectedImageUri != null){
			try {
				// OI FILE Manager
				String filemanagerstring = selectedImageUri.getPath();

				// MEDIA GALLERY
				String selectedImagePath = getPath(selectedImageUri);

				if (selectedImagePath != null) {
					filePath = selectedImagePath;
				} else if (filemanagerstring != null) {
					filePath = filemanagerstring;
				} else {
					Toast.makeText(getApplicationContext(), "Unknown path",
							Toast.LENGTH_LONG).show();
					Log.e("Bitmap", "Unknown path");
				}

				if (filePath != null) {
					decodeFile(filePath);
				} else {
					bitmap = null;
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Internal error",
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
		}

	}

	class ImageGalleryTask extends AsyncTask<Void, Void, String> {
		@SuppressWarnings("unused")
		@Override
		protected String doInBackground(Void... unsued) {
			InputStream is;
			BitmapFactory.Options bfo;
			Bitmap bitmapOrg;
			ByteArrayOutputStream bao ;

			bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 2;
			//bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);

			bao = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte [] ba = bao.toByteArray();
			String ba1 = Base64.encodeBytes(ba);
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("image",ba1));
			nameValuePairs.add(new BasicNameValuePair("cmd","image_android"));
			Log.v("log_tag", System.currentTimeMillis()+".jpg");	       
			try{
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new 
						//  Here you need to put your server file address
						HttpPost("http://www.picsily.com/upload_photo.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				Log.v("log_tag", "In the try Loop" );
			}catch(Exception e){
				Log.v("log_tag", "Error in http connection "+e.toString());
			}
			return "Success";
			// (null);
		}

		@Override
		protected void onProgressUpdate(Void... unsued) {

		}

		@Override
		protected void onPostExecute(String sResponse) {
			try {
				if (dialog.isShowing())
					dialog.dismiss();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						e.getMessage(),
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
		}

	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	public void decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 1024;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(filePath, o2);

		imgView.setImageBitmap(bitmap);

	}

}

