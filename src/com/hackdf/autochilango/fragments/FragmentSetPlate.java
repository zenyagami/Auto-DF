package com.hackdf.autochilango.fragments;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.hackdf.autochilango.MainActivity;
import com.hackdf.autochilango.R;
import com.hackdf.autochilango.api.NetClient;
import com.hackdf.autochilango.preferences.AppPreferences;
import com.hackdf.autochilango.utils.Dialogs;
import com.hackdf.autochilango.utils.Utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSetPlate extends Fragment implements OnClickListener {

	private GetPlateInfo getPlateInfo;
	private EditText etPlates;
	private EditText etCaptcha; 
	private boolean stoledFragment;
	private ImageView imgCaptcha;
	public static FragmentSetPlate newInstance(boolean isStolen) {
		FragmentSetPlate f = new FragmentSetPlate();
		Bundle args = new Bundle();
		args.putBoolean("stolen", isStolen);
		f.setArguments(args);
		return f;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_set_current_plate, null);
		((Button) v.findViewById(R.id.btnAcceptPlate)).setOnClickListener(this);
		((Button) v.findViewById(R.id.btnCanelPlate)).setOnClickListener(this);
		etPlates = (EditText) v.findViewById(R.id.etPlates);
		etCaptcha = (EditText)v.findViewById(R.id.etCaptcha);
		imgCaptcha = (ImageView)v.findViewById(R.id.imgCaptcha);
		if(getArguments()!=null && getArguments().getBoolean("stolen"))
		{
			((TextView)v.findViewById(R.id.txtTitleSetCurrentPlate)).setText("Consultar Placa por Robo");
			stoledFragment = getArguments().getBoolean("stolen");
			v.findViewById(R.id.captcha).setVisibility(View.VISIBLE);
			v.findViewById(R.id.btnCanelPlate).setVisibility(View.GONE);
		}
		new GetCaptcha().execute();
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCanelPlate:

			break;
		case R.id.btnAcceptPlate:
			if(!Utils.isOnline(getActivity()))
			{
				Toast.makeText(getActivity(), "No se tiene acceso a Internet :(", Toast.LENGTH_SHORT).show();
				return;
			}
			if(stoledFragment && etCaptcha.getText().toString().isEmpty())
			{
				Toast.makeText(getActivity(), "Se nececita el captcha", Toast.LENGTH_SHORT).show();
				return;
			}
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

	private class GetCaptcha extends AsyncTask<Void, Void, Bitmap>
	{

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				return NetClient.getCaptcha();
			} catch (Exception e) {
			}
			return null;
			
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if(getActivity()==null || isDetached() || !isAdded())
			{
				return;
			}
			Dialogs.dismissLoadingDialog(getActivity());
			if(result!=null)
			{
				imgCaptcha.setImageBitmap(result);
			}else
			{
				Toast.makeText(getActivity(), "Error al obtener el captcha :(", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			Dialogs.showDialog(getActivity());
		}
		
	}
	private class GetRepuveData extends AsyncTask<String, Void, Void>
	{

		@Override
		protected Void doInBackground(String... params) {
			
			try {
				NetClient.getHtmlRepuve(etCaptcha.getText().toString().trim(),params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(getActivity()==null || isDetached() || !isAdded())
			{
				return;
			}
			
		}

		@Override
		protected void onPreExecute() {
			Dialogs.showDialog(getActivity());
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
			if(etPlates.getText().toString().isEmpty())
			{
				Toast.makeText(getActivity(), "Necesitamos la placa", Toast.LENGTH_SHORT).show();
			}
			
			try {
				if(stoledFragment)
				{
					//mostramos pantalla de "robo" e info de placas
					//mandamos el "objeto"a robo
					new GetRepuveData().execute(plates);
					
				}else
				{
					AppPreferences.setJsonCurrentPlate(getActivity(), result.toString());
					AppPreferences.setCurrentPlate(getActivity(), plates);
					startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK) );
					getActivity().finish();
				}
				
				
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
