package com.example.swatcore;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

public class ICProfileActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icprofile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icprofile, menu);
		return true;
	}

}
