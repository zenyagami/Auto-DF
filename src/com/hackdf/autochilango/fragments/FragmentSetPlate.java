package com.hackdf.autochilango.fragments;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.hackdf.autochilango.MainActivity;
import com.hackdf.autochilango.R;
import com.hackdf.autochilango.api.NetClient;
import com.hackdf.autochilango.preferences.AppPreferences;
import com.hackdf.autochilango.utils.Dialogs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentSetPlate extends Fragment implements OnClickListener {

	private GetPlateInfo getPlateInfo;
	private EditText etPlates;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_set_current_plate, null);
		((Button) v.findViewById(R.id.btnAcceptPlate)).setOnClickListener(this);
		((Button) v.findViewById(R.id.btnCanelPlate)).setOnClickListener(this);
		etPlates = (EditText) v.findViewById(R.id.etPlates);
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCanelPlate:

			break;
		case R.id.btnAcceptPlate:
			// manda a llamar API que obtiene la info de la placa :)
			if (getPlateInfo == null) {
				getPlateInfo = new GetPlateInfo();
				getPlateInfo.execute("");

			}
			break;
		default:
			break;
		}

	}

	private class GetPlateInfo extends AsyncTask<String, Void, JSONObject> {
		String plates ="";
		@Override
		protected JSONObject doInBackground(String... params) {

			plates = etPlates.getText().toString().replace("-", "").trim();
			try {
				return NetClient.getPlatesInfo(plates);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			if (getActivity() == null || isDetached() || !isAdded()) {
				return;
			}
			getPlateInfo=null;
			Dialogs.dismissLoadingDialog(getActivity());
			if(result==null)
			{
				Toast.makeText(getActivity(), "Error al obtener datos :(", Toast.LENGTH_SHORT).show();
			}
			try {
				AppPreferences.setJsonCurrentPlate(getActivity(), result.toString());
				AppPreferences.setCurrentPlate(getActivity(), plates);
				startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK) );
				getActivity().finish();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), "Error al Parsear la informacion", Toast.LENGTH_SHORT).show();
			}
			

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Dialogs.showDialog(getActivity());
		}

	}

}
