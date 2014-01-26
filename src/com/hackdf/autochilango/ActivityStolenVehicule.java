package com.hackdf.autochilango;

import com.hackdf.autochilango.fragments.FragmentInfoPlaca;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ActivityStolenVehicule extends FragmentActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragment_holder);
		getActionBar().setTitle("Busqueda de Reporte de Robo");
		String html= (getIntent().getExtras()!=null && getIntent().hasExtra("html"))? getIntent().getExtras().getString("html"): "";
		String json = getIntent().getExtras().getString("json");
		Bundle b = new Bundle();
		b.putString("html", html);
		b.putBoolean("stolen", true);
		b.putString("json", json);
		Fragment fragment= FragmentInfoPlaca.newInstance(b);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame_holder, fragment).commit();
	}

}
