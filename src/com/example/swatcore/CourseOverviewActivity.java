package com.example.swatcore;

import java.util.List;
import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CourseOverviewActivity extends ListActivity {
	
	private ListView insLV;
	private String[] instructors;
	public static final String NO_RESULTS_MSG = "No Results Found";
	
	ParseQuery<ParseObject> query;
	private static final String COURSE_TABLE = "Course";
	private String courseTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courseoverview);
		
		Log.v("CourseOverviewActivity", "I'm in the courseoverviewactivity!");
		//Set Course Title
		Intent i = this.getIntent();
		courseTitle = i.getStringExtra("title");
		
		TextView titleValueView = (TextView) findViewById(R.id.courseoverview_titleValue);
		titleValueView.setText(courseTitle);
		
		Log.v("CourseOverviewActivity", "I set the TextView!");
		
		//Set Teacher List
		insLV = getListView();
		
		Log.v("CourseOverviewActivity", "I'm about to make the query!");
		
		query = createQuery();
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				
				if (objects.size() == 0) {
					instructors = new String[1];
					instructors[0] = NO_RESULTS_MSG;
				}
				else {
					instructors = new String[objects.size()];
					//Log.d("SEARCHRESULT", "Im in the function! " + objects.size());
					for (int i=0; i<objects.size(); i++) {
						instructors[i] = objects.get(i).getString("fullName");
						Log.d("COURSEOVERVIEW", objects.get(i).getString("fullName"));
					}
				}
				ArrayAdapter<String> insListAdapter = new ArrayAdapter<String>(CourseOverviewActivity.this, R.layout.search_result_item, instructors);
				insLV.setAdapter(insListAdapter);
			}
		});
	}
	
	private ParseQuery<ParseObject> createQuery() {

		query = ParseQuery.getQuery(COURSE_TABLE); 
		query.whereEqualTo("title", courseTitle);
		Log.v("COURSEOVERVIEW", "Finding Instructors for" + courseTitle);

		query.orderByAscending("fullName");

		return query;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		if (!instructors[position].equals(NO_RESULTS_MSG)) {
			Intent i = new Intent (this, ICProfileActivity.class);
			i.putExtra("title", courseTitle);
			i.putExtra("fullName", instructors[position]);
			Log.v("COURSEOVERVIEW", "Initializing ICProfileActivity");
			startActivity(i);
		}
	}
	
}
