package com.example.swatcore;

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
	public String[] titles;
	private ListView mListView;
	
	private ParseQuery<ParseObject> query;
	private static final String COURSE_TABLE = "Course";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		//mListView = (ListView) findViewById(R.id.searchResults);
		mListView = getListView();
		
		Log.v("SearchResultActivity", "I'm in the searchresultactivity!");
		
		query = createQuery();
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				
				if (objects.size() == 0) {
					titles = new String[1];
					titles[0] = NO_RESULTS_MSG;
				}
				else {
					titles = new String[objects.size()];
					//Log.d("SEARCHRESULT", "Im in the function! " + objects.size());
					for (int i=0; i<objects.size(); i++) {
						titles[i] = objects.get(i).getString("title");
						Log.d("SEARCHRESULT", objects.get(i).getString("title"));
					}
				}
				ArrayAdapter<String> mListAdapter = new ArrayAdapter<String>(SearchResultActivity.this, R.layout.search_result_item, titles);
				mListView.setAdapter(mListAdapter);
			}
		});
		
	}

	private ParseQuery<ParseObject> createQuery() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		Set<String> keys = extras.keySet();

		query = ParseQuery.getQuery(COURSE_TABLE); 
		for (String item : keys) {
			String searchQuery = extras.getString(item);
			query.whereEqualTo(item, searchQuery);
			Log.v("For-Loop for keys", "item is" + item + ", searchQuery is" + searchQuery);
		}
		
		query.orderByAscending("title");
		
		return query;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		if (!titles[position].equals(NO_RESULTS_MSG)) {
			Intent i = new Intent (this, CourseOverviewActivity.class);
			i.putExtra("title", titles[position]);
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
