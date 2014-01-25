package com.hackdf.autochilango;

import com.hackdf.autochilango.fragments.FragmentVerificationList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ActivityDetailList extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragment_holder);
		int resourceId = getIntent().getExtras().getInt("fragment_resource");
		String title = getIntent().getExtras().getString("title");
		getActionBar().setTitle(title);
		Fragment fragment;
		if (resourceId == 0) {
<<<<<<< HEAD
			fragment = new FragmentVerificationList();
		} else {
			fragment = new FragmentVerificationList();
=======
			fragment = FragmentVerificationList.newInstance(title);
		} else {
			fragment = FragmentVerificationList.newInstance(title);
>>>>>>> 27ffbbee2694ed820763983d255da2ebf9d51f48
		}
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame_holder, fragment).commit();

	}

}
