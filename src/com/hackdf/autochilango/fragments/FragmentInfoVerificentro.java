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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hackdf.autochilango.R;

public class FragmentInfoVerificentro extends Fragment {
	private GoogleMap gMap;
	private Marker marcador = null;
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
			gMap.getUiSettings().setZoomControlsEnabled(false);
			gMap.getUiSettings().setCompassEnabled(true);
			marcador = mapa
					.addMarker(ponerMarcador(new LatLng(19.42761, -99.16795), "",
							"", R.drawable.ic_launcher));
		}
		return main;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		Fragment f = getFragmentManager().findFragmentById(R.id.map);
		if (f != null)
			getFragmentManager().beginTransaction().remove(f).commit();
	}
}
