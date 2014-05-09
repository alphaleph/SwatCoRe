package com.example.swatcore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
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
	private static final int MAX_RESULTS = 1000;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		courseLV = getListView();
		
		query = createQuery();
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (objects.size() == 0) {
					courseTitles = new String[1];
					courseTitles[0] = NO_RESULTS_MSG;
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
							i++;
						}
					}
					
					courseTitles = new String[courseSet.size()];
					for (int j = 0; j < courseSet.size(); j++) {
						courseTitles[j] = buffCourseTitles[j];
					}
				}
				ArrayAdapter<String> courseListAdapter = new ArrayAdapter<String>(SearchResultActivity.this, R.layout.search_result_item, courseTitles);
				courseLV.setAdapter(courseListAdapter);
			}
		});
		
	}

	private ParseQuery<ParseObject> createQuery() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		query = ParseQuery.getQuery(COURSE_TABLE); 
		if (extras == null) {
			query.setLimit(MAX_RESULTS);
			query.whereExists("subject");
		}
		else {
			Set<String> searchCategories = extras.keySet();

			for (String category : searchCategories) {
				String searchQuery = extras.getString(category);
				query.whereEqualTo(category, searchQuery);
			}
		}

		query.orderByAscending("title");
		
		return query;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		if (!buffCourseTitles[position].equals(NO_RESULTS_MSG)) {
			Intent i = new Intent (this, CourseOverviewActivity.class);
			i.putExtra("title", buffCourseTitles[position]);
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
