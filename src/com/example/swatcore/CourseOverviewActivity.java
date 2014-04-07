package com.example.swatcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CourseOverviewActivity extends Activity {
	
	String subject;
	String num;
	String section;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courseoverview);
		
		Intent i = this.getIntent();
		String title = i.getStringExtra("title");
		
		TextView titleValueView = (TextView) findViewById(R.id.courseoverview_titleValue);
		titleValueView.setText(title);
	}
}
