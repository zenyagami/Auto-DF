package com.hackdf.autochilango.fragments;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackdf.autochilango.R;
import com.hackdf.autochilango.db.DatasetsDataSource;
import com.hackdf.autochilango.db.Verificentro;

public class FragmentInfoVerificentro extends Fragment {
	private GoogleMap gMap;
	private DatasetsDataSource dataSource;
	private final LatLng CoordenadaInicia = new LatLng(19.42761, -99.16795);

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
			dataSource = new DatasetsDataSource(main.getContext());
			dataSource.open();

			gMap = fragment.getMap();
			gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			gMap.setMyLocationEnabled(true);
			gMap.getUiSettings().setCompassEnabled(true);
			gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CoordenadaInicia,
					20));
			gMap.setMyLocationEnabled(true);
			gMap.getUiSettings().setZoomControlsEnabled(false);
			gMap.getUiSettings().setCompassEnabled(true);
			LatLng ll = new LatLng(19.4435307, -99.1824155);
			gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));
			new SetupMarker().execute();
		}

		return main;
	}

	private MarkerOptions ponerMarcador(LatLng sitio, String titulo,
			String descripcion, int personaje) {

		MarkerOptions marcador = new MarkerOptions().position(sitio)
				.title(titulo).snippet(descripcion)
				.icon(BitmapDescriptorFactory.fromResource(personaje))
				.anchor(0.5f, 0.5f);

		return marcador;
	}

	private class SetupMarker extends AsyncTask<Void, Verificentro, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			List<Verificentro> lista = dataSource.getVeris();
			for (Verificentro verificentro : lista) {
				publishProgress(verificentro);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Verificentro... values) {
			Verificentro verificentro = values[0];
			gMap.addMarker(ponerMarcador(
					new LatLng(verificentro.getLat(), verificentro.getLng()),
					verificentro.getRs(), verificentro.getDir() + "\n"
							+ verificentro.getDir(), R.drawable.ic_launcher));
			Log.i("latLong:", ("" + verificentro));
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		/*
		 * Fragment f = getFragmentManager().findFragmentById(R.id.map); if (f
		 * != null) getFragmentManager().beginTransaction().remove(f).commit();
		 */
	}

	public void conectar(View view) {

	}
}
