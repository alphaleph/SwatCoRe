package com.example.swatcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

public class AddReviewActivity extends Activity {

	private String objID;
	private ImageButton imageButton;
	private RatingBar qualityRatingBar;
	private RatingBar difficultyRatingBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addreview);
		Log.v("ADDREVIEW","at onCreate");
		
		Intent i = this.getIntent();
		objID = i.getStringExtra("objectID");
		addListenerOnButton();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	
	public void addListenerOnButton() {
		 
		imageButton = (ImageButton) findViewById(R.id.submit_button);
		qualityRatingBar = (RatingBar) findViewById(R.id.qualityRatingBar);
		difficultyRatingBar = (RatingBar) findViewById(R.id.difficultyRatingBar);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				boolean valid = true;
				
				EditText emailView = (EditText) findViewById(R.id.addReview_emailValue);
				EditText reviewView = (EditText) findViewById(R.id.addReview_reviewValue);
				
				
				String qualityValue = ""+ (int)(qualityRatingBar.getRating());
				valid = checkScoreValue(qualityValue);
				
				
				String difficultyValue = "" + (int)(difficultyRatingBar.getRating());
				valid = checkScoreValue(difficultyValue);
			
				String emailValue = emailView.getText().toString().toLowerCase();
				if (!emailValue.endsWith("@swarthmore.edu")) valid = false;
				
				String reviewValue = reviewView.getText().toString();
				if (reviewValue.equals("")) valid = false;
				
				if (valid) {

					Log.v("AddReview: ", "Preparing Review Submission");
					ParseObject review = new ParseObject("Review");

					review.put("parentID", objID);
					Log.v("AddReview: ", "Put objID into parentID");	
					review.put("quality", qualityValue);
					review.put("difficulty", difficultyValue);
					review.put("email", emailValue);
					review.put("content", reviewValue);
					Log.v("AddReview: ", "About to send the Review Submission");
					review.saveInBackground();

					Log.v("AddReview: ", "Sending Review Submission");

					finish();
				}
				else {
					Toast.makeText(AddReviewActivity.this, "Your review submission was not valid", Toast.LENGTH_SHORT).show();
				}
			}
		});	
	}
	
	
	private boolean checkScoreValue(String value) {
		Integer scoreIntValue = Integer.parseInt(value);
		if (scoreIntValue < 0 || scoreIntValue > 10) return false;
		else return true;
	}
	
}
