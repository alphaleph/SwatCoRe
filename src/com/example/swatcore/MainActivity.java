package com.example.swatcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button searchButton = (Button) findViewById(R.id.searchButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onSearchButtonClick(View v) {
		Intent intent = new Intent(this, SearchResultActivity.class);
		
		TextView subjView = (TextView) findViewById(R.id.subjSearch);
		String query = subjView.getText().toString();
		intent.putExtra("subject", query);
		startActivity(intent);
	}

}
