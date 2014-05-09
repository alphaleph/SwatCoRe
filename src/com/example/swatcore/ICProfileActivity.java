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
import android.view.View.OnClickListener;
import android.widget.ImageButton;
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
	private ImageButton imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icprofile);

		Intent i = this.getIntent();
		courseTitle = i.getStringExtra("title");
		instructor = i.getStringExtra("fullName");
		objID = i.getStringExtra("objectId");
		addListenerOnButton();

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
		revQuery = createRevQuery(objID);
		revQuery.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				ArrayList<HashMap<String,String>> revList = new ArrayList<HashMap<String,String>>();
				if (objects.size() == 0) {
					HashMap<String,String> empty = new HashMap<String,String>();
					empty.put("scoreMessage", "No reviews yet!");
					empty.put("content", "");
					revList.add(empty);
				}
				else {
					double qualitySum = 0;
					double difficultySum = 0;
					int numRev = 0;
					for (ParseObject object: objects) {
						numRev++;
						HashMap<String,String> temp = new HashMap<String,String>();	
						String tempQuality = object.getString("quality");
						qualitySum += Integer.parseInt(tempQuality);
						String tempDifficulty = object.getString("difficulty");
						difficultySum += Integer.parseInt(tempDifficulty);
						String scoreMessage = "Course Quality: " + tempQuality + ", Difficulty: " + tempDifficulty;
						temp.put("scoreMessage",  scoreMessage);
						temp.put("content", object.getString("content"));
						revList.add(temp);
					}
					
					double avQuality = qualitySum / numRev;
					double avDifficulty = difficultySum / numRev;
					
					TextView reviewLabel = (TextView) findViewById(R.id.icprofile_ReviewsLabel);
					reviewLabel.setText("Reviews (" + numRev + "): ");
					
					if (numRev == 0) {
						TextView avRatingsMessage = (TextView) findViewById(R.id.icprofile_ratingsMessage);
						avRatingsMessage.setText("No reviews yet!");
					}
					else {
						TextView avRatingsMessage = (TextView) findViewById(R.id.icprofile_ratingsMessage);
						avRatingsMessage.setText("Course Quality: " + avQuality + ", Difficulty: " + avDifficulty);
					}
					
				}
				SimpleAdapter adapter = new SimpleAdapter(ICProfileActivity.this, 
						revList, 
						R.layout.revlist_item, 
						new String[] {"scoreMessage","content"},
						new int[] {R.id.scoreMessage,R.id.contentValue}
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
	
	
	public void addListenerOnButton() {
		 
		imageButton = (ImageButton) findViewById(R.id.add_button);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ICProfileActivity.this, AddReviewActivity.class);
				intent.putExtra("objectID", objID);
				startActivity(intent);
			}
		});
	}
}
