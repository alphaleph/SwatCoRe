package com.example.swatcore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
   
	public String category1 = null;
	public String category2 = null;
	//private Context currContext = getApplicationContext();
	private final static String EMPTY_MSG = "Please enter something.";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.v("onCreate","at onCreate");
		
		//Button searchButton = (Button) findViewById(R.id.searchButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onSearchButtonClick(View v) {
		category1 = "subject";
		category2 = "insL";
		Intent intent = new Intent(this, SearchResultActivity.class);
		EditText subjView = (EditText) findViewById(R.id.subjSearch);
		EditText insView = (EditText) findViewById(R.id.insSearch);
		String subQuery = subjView.getText().toString().toUpperCase();
		String insQuery = insView.getText().toString();
		
		Log.v("subQuery", subQuery);
		Log.v("insQuery", insQuery);
		
		if (subQuery.equals("") && insQuery.equals("")) {
			return;
		}
		else {
			if (!subQuery.equals("")) {
				intent.putExtra(category1, subQuery);
			}
			if (!insQuery.equals("")) {
				intent.putExtra(category2, insQuery);
			}
			startActivity(intent);
		}
	}

}
