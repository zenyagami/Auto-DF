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
	private TextView causas;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.fragment_info_no_circula, null);
	
		circula=(TextView)v.findViewById(R.id.textViewCirculacion);
		causas=(TextView)v.findViewById(R.id.textViewCausas);
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
	
	switch(numero){
	case 5: case 6:
		if(dia==Calendar.MONDAY){
			v="no circula";	
				
		}else{
			
			v="si circula"; diasNoCirulacion=" su matricula no circula los dias Lunes y el primer Sabado de cada mes";
			
		}
		break;
	case 7: case 8:
		if(dia==Calendar.TUESDAY){
			v="no circula";	
				
		}else{
			v="si circula"; diasNoCirulacion=" su matricula no circula los dias Martes y el segundo Sabado de cada mes";
			
		}
		break;
	case 3: case 4:
		if(dia==Calendar.WEDNESDAY){
			v="no circula";	
				
		}else{
			v="si circula"; diasNoCirulacion=" su matricula no circula los dias Miercoles y el tercer Sabado de cada mes";
			
		}
		break;
	case 1: case 2:
		if(dia==Calendar.THURSDAY){
			v="no circula";	
				
		}else{
			v="si circula"; diasNoCirulacion=" su matricula no circula los dias Jueves y el cuarto Sabado de cada mes";
			
		}
		break;
	case 0: case 9:
		if(dia==Calendar.FRIDAY){
			v="no circula";	
				
		}else{
			v="si circula"; diasNoCirulacion=" su matricula no circula los dias Viernes y el quinto Sabado de cada mes";
			
		}
		break;
		default:

			v="si circula"; diasNoCirulacion=" su matricula no circula los dias ";
		break;
	}
	circula.setText("Placa numero "+placa+" el dia de hoy: "+v);
	causas.setText(diasNoCirulacion);
	
	
	}
	
 }
