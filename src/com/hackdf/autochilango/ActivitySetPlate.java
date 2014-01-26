package com.hackdf.autochilango;

import com.hackdf.autochilango.fragments.FragmentSetPlate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ActivitySetPlate extends FragmentActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragment_holder);
		getActionBar().setTitle("Registro de Placa");
		Bundle b=new Bundle();
		if(getIntent().hasExtra("edit"))
		{
			b.putBoolean("edit", true);
		}
		Fragment fragment= FragmentSetPlate.newInstance(b);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame_holder, fragment).commit();
	}

}
