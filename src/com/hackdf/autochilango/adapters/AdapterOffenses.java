package com.hackdf.autochilango.adapters;

import java.util.ArrayList;

import com.hackdf.autochilango.R;
import com.hackdf.autochilango.entities.Offenses;
import com.hackdf.autochilango.entities.Verificacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterOffenses extends ArrayAdapter<Offenses>{

	private Context context;
	private ArrayList<Offenses> offenses;
	public AdapterOffenses(Context context,
			ArrayList<Offenses> objects) {
		super(context, R.layout.adapter_detail_list_verification, objects);
		this.context = context;
		this.offenses = objects;
	}

	@Override
	public Offenses getItem(int position) {
		return offenses.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
		{
			convertView= ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_detail_list_offenses, null);
		}
		Offenses verif = getItem(position);
		TextView folio = (TextView)convertView.findViewById(R.id.txtFolioOffense);
		TextView date = (TextView)convertView.findViewById(R.id.txtDateOffense);
		TextView status = (TextView)convertView.findViewById(R.id.txtStatusOffense);
		TextView reason= (TextView)convertView.findViewById(R.id.txtReasonOffense);
		TextView sancion= (TextView)convertView.findViewById(R.id.txtSancion);
		
		folio.setText(String.format("Folio: %s", verif.getFolio()));
		date.setText(String.format("Fecha: %s", verif.getDateTime()));
		status.setText(String.format("Situación: %s",verif.getSituation()));
		reason.setText(String.format("Razon:%s Fundamento:%s", verif.getReason(),verif.getBase()));
		sancion.setText(String.format("Sancion: %s", verif.getSanction()));
		return convertView;
	}

}
