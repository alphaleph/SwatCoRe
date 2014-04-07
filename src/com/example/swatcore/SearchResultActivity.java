package com.example.swatcore;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SearchResultActivity extends Activity {

	public static String category = "subject";
	public String[] titles;
	private ListView mListView;
	
	private static boolean dbinit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		mListView = (ListView) findViewById(R.id.searchResults);
		
		Log.v("SearchResultActivity", "I'm in the searchresultactivity!");
		
		Intent intent = getIntent();
		String searchquery = intent.getStringExtra(category);
		Log.d("INTENT", searchquery);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Spring2014");
		query.whereEqualTo(category, searchquery);
		
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				
				titles = new String[objects.size()];
				//Log.d("SEARCHRESULT", "Im in the function! " + objects.size());
				for (int i=0; i<objects.size(); i++) {
					titles[i] = objects.get(i).getString("title");
					Log.d("SEARCHRESULT", objects.get(i).getString("title"));
				}
				
				ArrayAdapter<String> mListAdapter = new ArrayAdapter<String>(SearchResultActivity.this, R.layout.search_result_item, titles);
				mListView.setAdapter(mListAdapter);
				
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
