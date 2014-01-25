package com.hackdf.autochilango.adapters;

import java.util.ArrayList;

import com.hackdf.autochilango.R;
import com.hackdf.autochilango.entities.Verificacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterVerifications extends ArrayAdapter<Verificacion>{

	private Context context;
	private ArrayList<Verificacion> verifList;
	private String plates;
	public AdapterVerifications(Context context,String plates,
			ArrayList<Verificacion> objects) {
		super(context, R.layout.adapter_detail_list_verification, objects);
		this.context = context;
		this.verifList = objects;
		this.plates = plates;
	}

	@Override
	public Verificacion getItem(int position) {
		return verifList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
		{
			convertView= ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_detail_list_verification, null);
		}
		Verificacion verif = getItem(position);
		TextView txtPlacaVigencia = (TextView)convertView.findViewById(R.id.txtPlacaVigencia);
		TextView txtSerie= (TextView)convertView.findViewById(R.id.SerieVerif);
		TextView txtMarcaGas= (TextView)convertView.findViewById(R.id.txtMarcaGasolina);
		TextView verificentro = (TextView)convertView.findViewById(R.id.txtVerificentroInfo);
		TextView txtVerifiResultado = (TextView)convertView.findViewById(R.id.txtVerifiResultado);
		TextView txtVerifiRechazado = (TextView)convertView.findViewById(R.id.txtVerifiRechazado);
		if(verif.getRejectCause()!=null && verif.getRejectCause().equalsIgnoreCase("No aplica") )
		{
			txtVerifiRechazado.setVisibility(View.GONE);
		}else
		{
			txtVerifiRechazado.setText(String.format("Motivo de Rechazo: %s", verif.getRejectCause()));
		}
		txtVerifiResultado.setText(String.format("Resultado: %s", verif.getResult()));
		verificentro.setText(String.format("Verificentro: %s Fecha:%s %s",verif.getVerifyFacility(),verif.getVerificationDate(),verif.getVerificationTime() ));
		txtMarcaGas.setText(String.format("Modelo: %s %s %s %s", verif.getBrand(),verif.getSubBrand(),verif.getAnio(),verif.getCombustible()));
		txtSerie.setText(String.format("No. Serie: %s", verif.getVin()));
		txtPlacaVigencia.setText(String.format("Placa: %s Vigencia: %s",plates,verif.getValidity() ));
		return convertView;
	}

}
