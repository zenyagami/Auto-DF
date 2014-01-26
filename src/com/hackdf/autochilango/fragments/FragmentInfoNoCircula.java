package com.hackdf.autochilango.fragments;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.graphics.Color;
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
	private TextView causas;
	private TextView placatxt;
	private TextView lblColor;
	private static final String NO_CIRCULA ="No Circula";
	private static final String SI_CIRCULA ="Si Circula";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.fragment_info_no_circula, null);
	
		causas=(TextView)v.findViewById(R.id.textViewCausas);
		placatxt=(TextView)v.findViewById(R.id.textPlaca);
		lblColor=(TextView)v.findViewById(R.id.textColor);
		circula=(TextView)v.findViewById(R.id.textViewCircula);
		circulacion();
		return v;
	}	
	
public void circulacion() {
	String v="";
	String placa= AppPreferences.getCurrentPlate(this.getActivity());
	String diasNoCirulacion="";
	
Calendar c = new GregorianCalendar();
int dia;
	dia = (c.get(Calendar.DAY_OF_WEEK));
		 int numero=0;
	try
	{
		 numero=Integer.parseInt(placa.substring(2,3));
		
	}catch
	(Exception ex)
	{
		numero=Integer.parseInt(placa.substring(placa.length()-2,placa.length()-1));
	}
	
	//engomado, 
	
	switch(numero){
	case 5: case 6:
		
		lblColor.setBackgroundColor(Color.rgb(255,255,0));
		
		if(dia==Calendar.MONDAY){
			v=NO_CIRCULA;	
				
		}else{
			
			v=NO_CIRCULA;
			diasNoCirulacion="Su matrícula no circula los dias Lunes y el primer Sabado de cada mes";
			
		}
		break;
	case 7: case 8:
		
		lblColor.setBackgroundColor(Color.rgb(255,192,203));
		
		if(dia==Calendar.TUESDAY){
			v=SI_CIRCULA;	
				
		}else{
			v=NO_CIRCULA; 
			diasNoCirulacion="Su matrícula no circula los dias Martes y el segundo Sabado de cada mes";
			
		}
		break;
	case 3: case 4:
		
		lblColor.setBackgroundColor(Color.rgb(255,0,0));
		
		if(dia==Calendar.WEDNESDAY){
			v=NO_CIRCULA;	
				
		}else{
			v=SI_CIRCULA;
			diasNoCirulacion="Su matrícula no circula los dias Miercoles y el tercer Sabado de cada mes";
			
		}
		break;
	case 1: case 2:
		
		lblColor.setBackgroundColor(Color.rgb(34,139,34));
		
		if(dia==Calendar.THURSDAY){
			v=NO_CIRCULA;	
				
		}else{
			v=SI_CIRCULA; 
			diasNoCirulacion="Su matrícula no circula los dias Jueves y el cuarto Sabado de cada mes";
			
		}
		break;
	case 0: case 9:
		
		lblColor.setBackgroundColor(Color.rgb(30,144,255));
		
		if(dia==Calendar.FRIDAY){
			v=NO_CIRCULA;	
				
		}else{
			v=SI_CIRCULA; 
			diasNoCirulacion="Su matrícula no circula los dias Viernes y el quinto Sabado de cada mes";
			
		}
		break;
		default:

			v=SI_CIRCULA;
			diasNoCirulacion="Su matrícula no circula los dias ";
		break;
	}
	placatxt.setText(placa);
	circula.setText("Tu Auto "+v + " el día de Hoy");
	if(v.equals(NO_CIRCULA))
	{
		circula.setTextColor(getResources().getColor(R.color.holo_red_dark));
	}
	causas.setText(diasNoCirulacion);
	
	
	}
	
 }
