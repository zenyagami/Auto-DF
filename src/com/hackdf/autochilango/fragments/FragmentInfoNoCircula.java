package com.hackdf.autochilango.fragments;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackdf.autochilango.R;
import com.hackdf.autochilango.preferences.AppPreferences;

public class FragmentInfoNoCircula extends Fragment {
	private TextView circula;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.fragment_info_no_circula, null);
	
		circula=(TextView)getActivity().findViewById(R.id.textViewCirculacion);
		circulacion();
		return v;
	}	
	
public void circulacion() {
	String ter,v="";
	String placa= AppPreferences.getCurrentPlate(this.getActivity());
	placa="542WAD";
Calendar c = new GregorianCalendar();
    String dia;
	dia = Integer.toString(c.get(Calendar.DATE));
	placa="";
	ter=placa.substring(0, placa.length());
	
	switch(Integer.parseInt(ter)){
	case 5: case 6:
		if(dia=="lunes"){
			v="Hoy no circula";	
				
		}else{
			v="Circula";
			
		}
		break;
	case 7: case 8:
		if(dia=="martes"){
			v="Hoy no circula";	
				
		}else{
			v="Circula";
			
		}
		break;
	case 3: case 4:
		if(dia=="miercoles"){
			v="Hoy no circula";	
				
		}else{
			v="Circula";
			
		}
		break;
	case 1: case 2:
		if(dia=="jueves"){
			v="Hoy no circula";	
				
		}else{
			v="Circula";
			
		}
		break;
	case 0: case 9:
		if(dia=="viernes"){
			v="Hoy no circula";	
				
		}else{
			v="Circula";
			
		}
		break;
	}
	circula.setText(v);
	
	}
	
 }
