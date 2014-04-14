package com.example.swatcore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SearchResultActivity extends ListActivity {

	public static String category;
	public static final String NO_RESULTS_MSG = "No Results Found";
	private String[] buffCourseTitles, courseTitles;	
	private ListView courseLV;
	
	private ParseQuery<ParseObject> query;
	private static final String COURSE_TABLE = "Course";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		//mListView = (ListView) findViewById(R.id.searchResults);
		courseLV = getListView();
		
		Log.v("SearchResultActivity", "I'm in the searchresultactivity!");
		
		query = createQuery();
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				
				if (objects.size() == 0) {
					buffCourseTitles = new String[1];
					buffCourseTitles[0] = NO_RESULTS_MSG;
				}
				else {
					Set<String> courseSet = new HashSet<String>();
					String courseTitle;
					buffCourseTitles = new String[objects.size()];
					
					int i = 0;
					for (ParseObject object : objects) {
						courseTitle = object.getString("title");
						if (courseSet.add(courseTitle)) {
							buffCourseTitles[i] = courseTitle;
							Log.d("SEARCHRESULT", courseTitle + " " + i + " " + buffCourseTitles[i]);
							i++;
						}
					}
					
					Log.d("SEARCHRESULT", "courseSet Size: " + courseSet.size() + "  buffCourseTitles[0] = " + buffCourseTitles[0]);
					courseTitles = new String[courseSet.size()];
					for (int j = 0; j < courseSet.size(); j++) {
						Log.d("SEARCHRESULT", "j: " + j);
						courseTitles[j] = buffCourseTitles[j];
						Log.d("SEARCHRESULT", "courseTitles[" + j + "] = "+ courseTitles[j]);
					}
				}
				ArrayAdapter<String> courseListAdapter = new ArrayAdapter<String>(SearchResultActivity.this, R.layout.search_result_item, courseTitles);
				courseLV.setAdapter(courseListAdapter);
			}
		});
		
	}

	private ParseQuery<ParseObject> createQuery() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		Set<String> searchCategories = extras.keySet();

		query = ParseQuery.getQuery(COURSE_TABLE); 
		for (String category : searchCategories) {
			String searchQuery = extras.getString(category);
			query.whereEqualTo(category, searchQuery);
			Log.v("For-Loop for keys", "item is" + category + ", searchQuery is" + searchQuery);
		}
		
		query.orderByAscending("title");
		
		return query;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		if (!buffCourseTitles[position].equals(NO_RESULTS_MSG)) {
			Intent i = new Intent (this, CourseOverviewActivity.class);
			i.putExtra("title", buffCourseTitles[position]);
			Log.v("SEARCHRESULT", "Initializing CourseOverView");
			startActivity(i);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}
	

}
