package com.hackdf.autochilango.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.hackdf.autochilango.ActivityDetailList;
import com.hackdf.autochilango.ActivityShowRepuve;
import com.hackdf.autochilango.R;
import com.hackdf.autochilango.entities.Car;
import com.hackdf.autochilango.entities.Offenses;
import com.hackdf.autochilango.entities.Verificacion;
import com.hackdf.autochilango.preferences.AppPreferences;
import com.hackdf.autochilango.utils.Parser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentInfoPlaca extends Fragment implements OnClickListener{
	
	public static FragmentInfoPlaca newInstance(Bundle args) {
		FragmentInfoPlaca f = new FragmentInfoPlaca();
		f.setArguments(args);
		return f;
	}
	private View v;
	private boolean isStolen;
	private String html;
	private String json=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v =inflater.inflate(R.layout.fragment_info_placa, null);
		((TextView)v.findViewById(R.id.txtDetailVerifications)).setOnClickListener(this);
		((TextView)v.findViewById(R.id.txtDetailOffenses)).setOnClickListener(this);
		((Button)v.findViewById(R.id.btnChecaRObado)).setOnClickListener(this);
		//obtenemos datos de preferencias :D
		String data = AppPreferences.getJsonCurrentPlate(getActivity());
		try {
			Car carInfo;
			if(getArguments()!=null && getArguments().containsKey("json"))
			{
				json = getArguments().getString("json");
				carInfo = Parser.ParseCarInfoFromJson(new JSONObject(json));
				
			}else
			{
				carInfo = Parser.ParseCarInfoFromJson(new JSONObject(data));
			}
			
			setupUI(carInfo);
		} catch (JSONException e) {
			Toast.makeText(getActivity(), "hubo un error al generar la Info :(", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		//si es robado mostramos la vista de roboo
		if(getArguments()!=null && getArguments().containsKey("stolen"))
		{
			isStolen=true;
			html = getArguments().getString("html");
			v.findViewById(R.id.lyRobo).setVisibility(View.VISIBLE);
		}
		return v;
	}
	private void setupUI(Car carInfo) {
		setupVerification(carInfo);
		setupOffenses(carInfo);
		
	}
	private void setupOffenses(Car carInfo) {
		
		TextView txtLastOffense = (TextView)v.findViewById(R.id.txtLastOffense);
		TextView txtOffenseStatus= (TextView)v.findViewById(R.id.txtOffenseStatus);
		TextView txtNumberOffenses= (TextView)v.findViewById(R.id.txtNumberOffenses);
		String  lastOffense;
		String offenseStatus;
		ArrayList<Offenses> offenseList = carInfo.getOffenseList();
		if(offenseList!=null && offenseList.size()>0)
		{
			Offenses offense = offenseList.get(0);
			txtNumberOffenses.setText(""+offenseList.size());
			lastOffense = String.format("Última Infracción: %s", offense.getDateTime());
			offenseStatus = String.format("Estado: %s", offense.getSituation());
		}else
		{
			lastOffense = "Sin Infracciones";
			offenseStatus = "";
		}
		txtLastOffense.setText(lastOffense);
		txtOffenseStatus.setText(offenseStatus);
		
	}
	private void setupVerification(Car carInfo) {
		TextView txtModeloCarro = (TextView)v.findViewById(R.id.txtModeloCarro);
		TextView txtLastVerification = (TextView)v.findViewById(R.id.txtLastVerification);
		TextView txtVerificationResult = (TextView)v.findViewById(R.id.txtVerificationResult);
		ArrayList<Verificacion> verifList = carInfo.getVerificationList();
		
		String modeloCarro;
		String lastVerification;
		String verificationResult;
		if(verifList!=null && verifList.size()>0)
		{
			((TextView)v.findViewById(R.id.txtNumberOfVerification)).setText(""+verifList.size());
			Verificacion verif = verifList.get(0);
			modeloCarro =   "Modelo :"+verif.getBrand()+ " "+verif.getSubBrand()+ " "+ verif.getAnio();
			lastVerification = "Última verificación: "+verif.getVerificationDate() + " Hora: "+verif.getVerificationTime();
			verificationResult = "Resultado: "+ verif.getResult();
		}else
		{
			//no Data
			modeloCarro = "No Disponible :(";
			lastVerification ="--";
			verificationResult = "No Aplica";
		}
		txtModeloCarro.setText(modeloCarro);
		txtLastVerification.setText(lastVerification);
		txtVerificationResult.setText(verificationResult);	
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtDetailOffenses:
			Intent i = new Intent(getActivity(), ActivityDetailList.class);
			i.putExtra("title", "Infracciones");
			i.putExtra("fragment_resource", 1);
			if(json!=null)
			{
				i.putExtra("json", json);
			}
			startActivity(i);
			break;
		case R.id.txtDetailVerifications:
			Intent in = new Intent(getActivity(), ActivityDetailList.class);
			in.putExtra("title", "Infracciones");
			in.putExtra("fragment_resource", 0);
			if(json!=null)
			{
				in.putExtra("json", json);
			}
			startActivity(in);
			break;
		case R.id.btnChecaRObado:
			startActivity(new Intent(getActivity(), ActivityShowRepuve.class).putExtra("html", html) );
		default:
			break;
		}
		
	}

}
