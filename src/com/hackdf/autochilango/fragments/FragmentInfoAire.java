package com.hackdf.autochilango.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hackdf.autochilango.R;
import com.hackdf.autochilango.entities.Aire;
import com.hackdf.autochilango.preferences.AppPreferences;
import com.hackdf.autochilango.utils.ParserAmbiente;

public class FragmentInfoAire extends Fragment{
	
	TextView txtaire;
	Aire aire;
	private String json=null;

	public static FragmentInfoAire newInstance(String title) {
		 FragmentInfoAire f = new  FragmentInfoAire();
		Bundle args = new Bundle();
		args.putString("title", title);
		f.setArguments(args);
		return f;
	}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_info_aire, null);
			
//			aire = new Aire();
////			
//			txtaire = (TextView) v.findViewById(R.id.cataire);
////			
//			txtaire.setText(aire.getCategoria());
			String data = AppPreferences.getJsonCurrentPlate(getActivity());
			try {
				Aire aireInfo;
				if(getArguments()!=null && getArguments().containsKey("json"))
				{
					json = getArguments().getString("json");
					aireInfo = ParserAmbiente.ParseAmbienteInfoFromJson(new JSONObject(json));

					
										
				}else
				{
					aireInfo = ParserAmbiente.ParseAmbienteInfoFromJson(new JSONObject(data));
				}
				
			} catch (JSONException e) {
				Toast.makeText(getActivity(), "hubo un error al generar la Info :(", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			
			return v;
		}
}
