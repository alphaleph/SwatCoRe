package com.example.swatcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
		
		boolean valid = true;
		
		EditText qualityView = (EditText) findViewById(R.id.addReview_qualityValue);
		EditText difficultyView = (EditText) findViewById(R.id.addReview_difficultyValue);
		EditText emailView = (EditText) findViewById(R.id.addReview_emailValue);
		EditText reviewView = (EditText) findViewById(R.id.addReview_reviewValue);
		
		String qualityValue = qualityView.getText().toString();
		valid = checkScoreValue(qualityValue);
		
		String difficultyValue = difficultyView.getText().toString();
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
			Toast.makeText(this, "Your review submission was not valid", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean checkScoreValue(String value) {
		Integer scoreIntValue = Integer.parseInt(value);
		if (scoreIntValue < 0 || scoreIntValue > 10) return false;
		else return true;
	}
	
}
