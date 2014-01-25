package com.hackdf.autochilango.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.hackdf.autochilango.R;
import com.hackdf.autochilango.adapters.AdapterVerifications;
import com.hackdf.autochilango.entities.Car;
import com.hackdf.autochilango.entities.Verificacion;
import com.hackdf.autochilango.preferences.AppPreferences;
import com.hackdf.autochilango.utils.Parser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentVerificationList extends Fragment {

	public static FragmentVerificationList newInstance(String title) {
		FragmentVerificationList f = new FragmentVerificationList();
		Bundle args = new Bundle();
		args.putString("title", title);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_detail_list, null);
		TextView txtTitle = (TextView) v.findViewById(R.id.txtListDetailTitle);
		String title = getArguments().getString("title");
		txtTitle.setText(title);
		ListView lv = (ListView) v.findViewById(R.id.lvCarInfo);
		try {
			Car carInfo = Parser.ParseCarInfoFromJson(new JSONObject(
					AppPreferences.getJsonCurrentPlate(getActivity()) ));
			if (carInfo != null) {
				ArrayList<Verificacion> verifList = carInfo
						.getVerificationList();
				if (verifList != null) {
					AdapterVerifications adapter = new AdapterVerifications(
							getActivity(),
							AppPreferences.getCurrentPlate(getActivity()),
							verifList);
					lv.setAdapter(adapter);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "Error al obtener info de la placa",
					Toast.LENGTH_SHORT).show();
			getActivity().finish();
		}

		return v;
	}

}
