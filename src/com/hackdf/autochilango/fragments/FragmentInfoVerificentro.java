package com.hackdf.autochilango.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.hackdf.autochilango.R;

public class FragmentInfoVerificentro extends Fragment {
	private GoogleMap gMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_verificentro, null);
		if (main != null) {
			ViewGroup parent = (ViewGroup) main.getParent();
			if (parent != null)
				parent.removeView(main);
		}
		try {
			main = inflater.inflate(R.layout.fragment_verificentro, null);
		} catch (InflateException e) {
		}

		int googlePlayServicesResult = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());
		if (googlePlayServicesResult != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(googlePlayServicesResult,
					getActivity(), 0).show();
		} else {
			// mapa
			FragmentManager fm = getActivity().getSupportFragmentManager();
			SupportMapFragment fragment = (SupportMapFragment) fm
					.findFragmentById(R.id.map);
			if (fragment == null) {
				fragment = SupportMapFragment.newInstance();
				fm.beginTransaction().replace(R.id.map, fragment).commit();
			}

			gMap = fragment.getMap();
		}
		return main;
	}
}
