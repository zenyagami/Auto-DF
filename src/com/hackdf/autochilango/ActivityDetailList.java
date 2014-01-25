package com.hackdf.autochilango;

import com.hackdf.autochilango.fragments.FragmentOffensesList;
import com.hackdf.autochilango.fragments.FragmentVerificationList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

public class ActivityDetailList extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragment_holder);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		int resourceId = getIntent().getExtras().getInt("fragment_resource");
		String title = getIntent().getExtras().getString("title");
		getActionBar().setTitle(title);
		Fragment fragment;
		if (resourceId == 0) {
			fragment = FragmentVerificationList.newInstance(title);
		} else {
			fragment = FragmentOffensesList.newInstance(title);
		}
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame_holder, fragment).commit();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
