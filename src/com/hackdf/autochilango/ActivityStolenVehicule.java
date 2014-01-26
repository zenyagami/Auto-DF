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
		Fragment fragment= FragmentInfoPlaca.newInstance(true);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame_holder, fragment).commit();
	}

}
