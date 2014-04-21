package com.example.swatcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseObject;

public class AddReviewActivity extends Activity {

	private String objID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addreview);
		Log.v("ADDREVIEW","at onCreate");
		
		Intent i = this.getIntent();
		objID = i.getStringExtra("objectID");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onSubmitButtonClick(View v) {
		// TODO Auto-generated method stub
		EditText scoreView = (EditText) findViewById(R.id.addReview_scoreValue);
		EditText emailView = (EditText) findViewById(R.id.addReview_emailValue);
		EditText reviewView = (EditText) findViewById(R.id.addReview_reviewValue);
		String scoreValue = scoreView.getText().toString();
		//Integer scoreValue = Integer.parseInt(scoreView.getText().toString());
		String emailValue = emailView.getText().toString().toLowerCase(); //TODO: Check if email ends with swat.edu
		String reviewValue = reviewView.getText().toString();
		
		Log.v("AddReview: ", "Preparing Review Submission");
		ParseObject review = new ParseObject("Review");
		
		review.put("parentID", objID);
		Log.v("AddReview: ", "Put objID into parentID");	
		review.put("score", scoreValue);
		review.put("email", emailValue);
		review.put("content", reviewValue);
		Log.v("AddReview: ", "About to send the Review Submission");
		review.saveInBackground();
		
		Log.v("AddReview: ", "Sending Review Submission");
		
		finish();
	}
	
}
