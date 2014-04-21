package com.example.swatcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ICProfileActivity extends ListActivity {

	private String courseTitle, instructor, objID;
	ParseQuery<ParseObject> revQuery;
	private static final String REVIEW_TABLE = "Review";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icprofile);

		Intent i = this.getIntent();
		courseTitle = i.getStringExtra("title");
		instructor = i.getStringExtra("fullName");
		objID = i.getStringExtra("objectId");

		TextView titleValueView = (TextView) findViewById(R.id.icprofile_titleValue);
		titleValueView.setText(courseTitle);

		TextView insValueView = (TextView) findViewById(R.id.icprofile_insValue);
		insValueView.setText(instructor);

		populateList();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		populateList();		
	}

	private void populateList() {
		Log.v("ICProfileActivity", "I'm about to make the revQuery!");
		revQuery = createRevQuery(objID);
		revQuery.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				/*if (objects.size() == 0) {
					 = new String[1];
					instructors[0] = NO_RESULTS_MSG;
				}*/
				ArrayList<HashMap<String,String>> revList = new ArrayList<HashMap<String,String>>();

				for (ParseObject object: objects) {
					HashMap<String,String> temp = new HashMap<String,String>();	
					temp.put("score",object.getString("score"));
					temp.put("content", object.getString("content"));
					Log.v("ICPROFILE", object.getString("content"));
					revList.add(temp);
				}
				SimpleAdapter adapter = new SimpleAdapter(ICProfileActivity.this, 
						revList, 
						R.layout.revlist_item, 
						new String[] {"score","content"},
						new int[] {R.id.scoreValue,R.id.contentValue}
						);	
				setListAdapter(adapter);
			}
		});
		
	}
	
	private ParseQuery<ParseObject> createRevQuery(String course) {
		ParseQuery<ParseObject> revQuery = ParseQuery.getQuery(REVIEW_TABLE);
		revQuery.whereEqualTo("parentID", objID);
		revQuery.orderByAscending("");
		return revQuery;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icprofile, menu);
		return true;
	}

	public void onAddButtonClick(View v) {
		// TODO Auto-generated method stub
		Log.v("ICProfile: ", "Preparing AddReview Intent");
		Intent intent = new Intent(this, AddReviewActivity.class);
		intent.putExtra("objectID", objID);
		Log.v("ICProfile: ", "Sending AddReview Intent");
		startActivity(intent);

	}

}
