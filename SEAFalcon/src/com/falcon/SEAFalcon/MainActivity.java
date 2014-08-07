package com.falcon.SEAFalcon;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
	
	public static MainActivity instance;
	public static FragmentManager fragmentmanager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;

		fragmentmanager = getSupportFragmentManager();
		Login_Fragment loginfragment;

		if (findViewById(R.id.fragment_container) != null) {

			if (savedInstanceState != null) {
				return;
			}
			loginfragment = new Login_Fragment(this);
			fragmentmanager.beginTransaction()
					.add(R.id.fragment_container, loginfragment).commit();
		}
	}
}
