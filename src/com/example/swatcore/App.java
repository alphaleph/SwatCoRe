package com.example.swatcore;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Parse.initialize(this, "3eWLBFPyLhUcb6VRbFbqqKukgSd0rgdDDb59SoVp", "syH8jfou3mlJta9ELmyy0xmzNuKskOgKVU5BhH4j");
	}
}
