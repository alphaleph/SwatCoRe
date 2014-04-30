package com.example.swatcore;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {
   
	public String category1 = null;
	public String category2 = null;
	private final static String SUBJ_TABLE = "Subject";
	private List<String> subjList = new ArrayList<String>();
	private Spinner spin;
	private ImageButton imageButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("MAIN","at onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spin = (Spinner) findViewById(R.id.subjSpin);
		populateSubjSpinner();
		Log.v("MAIN","Populated Subject Spinner");
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	public void onSearchButtonClick(View v) {
		category1 = "subject";
		category2 = "insL";
		Intent intent = new Intent(this, SearchResultActivity.class);
		EditText insView = (EditText) findViewById(R.id.insSearch);
		String subQuery = spin.getSelectedItem().toString();
		String insQuery = insView.getText().toString();
		if (!insQuery.equals("")) {
			insQuery.toLowerCase();
			Log.v("First letter upper case before", insQuery);
			insQuery = insQuery.substring(0,1).toUpperCase() + insQuery.substring(1);
			Log.v("First letter upper case after", insQuery);
		}
		
		Log.v("subQuery", subQuery);
		Log.v("insQuery", insQuery);


		if (!subQuery.equals("All")) {
			Log.v("Subject name", "The subject is " + subQuery);
			intent.putExtra(category1, subQuery);
		}
		if (!insQuery.equals("")) {
			intent.putExtra(category2, insQuery);
		}
		startActivity(intent);		
	}
	*/
	
	public void addListenerOnButton() {
		 
		imageButton = (ImageButton) findViewById(R.id.find_button);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				category1 = "subject";
				category2 = "insL";
				Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
				EditText insView = (EditText) findViewById(R.id.insSearch);
				String subQuery = spin.getSelectedItem().toString();
				String insQuery = insView.getText().toString();
				if (!insQuery.equals("")) {
					insQuery.toLowerCase();
					Log.v("First letter upper case before", insQuery);
					insQuery = insQuery.substring(0,1).toUpperCase() + insQuery.substring(1);
					Log.v("First letter upper case after", insQuery);
				}
				
				Log.v("subQuery", subQuery);
				Log.v("insQuery", insQuery);


				if (!subQuery.equals("All")) {
					Log.v("Subject name", "The subject is " + subQuery);
					intent.putExtra(category1, subQuery);
				}
				if (!insQuery.equals("")) {
					intent.putExtra(category2, insQuery);
				}
				startActivity(intent);		
			}
		});
	}


	public void populateSubjSpinner() {
		ParseQuery<ParseObject> subjQuery = ParseQuery.getQuery(SUBJ_TABLE);
		//subjQuery.whereExists("subject");
		subjQuery.orderByAscending("subject");
		Log.v("MainActivity", "I'm about to make the subjQuery!");
		
		subjQuery.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				if (objects.size() == 0) {
					subjList.add("No Results Found"); //TODO
				}
				else{
					subjList.add("All");
					for (ParseObject object: objects) {
						subjList.add(object.getString("subject"));
						Log.v("MainActivity", "Added" + object.getString("subject") + "to list!");
					}
				}
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, 
						android.R.layout.simple_spinner_item, subjList);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spin.setAdapter(adapter);
			}
		});

	}
}

