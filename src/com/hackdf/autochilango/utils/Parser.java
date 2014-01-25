package com.hackdf.autochilango.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hackdf.autochilango.entities.Car;
import com.hackdf.autochilango.entities.Offenses;
import com.hackdf.autochilango.entities.Verificacion;

public class Parser {
	public static Car ParseCarInfoFromJson(JSONObject response)
	{
		if(response==null || !response.has("consulta"))
		{
			return null;
		}
		Car carInfo = new Car();
		try {
			response = response.getJSONObject("consulta");
			carInfo.setPlates(response.getString("placa"));
			if(response.has("tenencias") && response.getJSONObject("tenencias").has("tieneadeudos"))
			{
				carInfo.setHasDebts(Integer.valueOf(response.getJSONObject("tenencias").getString("tieneadeudos")));
			}
			if(response.has("infracciones"))
			{
				ArrayList<Offenses> offenseList = new ArrayList<Offenses>();
				JSONArray array= response.getJSONArray("infracciones");
				for (int i = 0; i < array.length(); i++) {
					Offenses offenses = new Offenses();
					JSONObject obj = array.getJSONObject(i);
					try {
						offenses.setFolio(obj.getString("folio"));
						offenses.setDateTime(obj.getString("fecha"));
						offenses.setSituation(obj.getString("situacion"));
						offenses.setReason(obj.getString("motivo"));
						offenses.setBase(obj.getString("fundamento"));
						offenses.setSanction(obj.getString("sancion"));
						offenseList.add(offenses);	
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				carInfo.setOffenseList(offenseList);
			}
			if(response.has("verificaciones"))
			{
				ArrayList<Verificacion> verifList= new ArrayList<Verificacion>();
				JSONArray array = response.getJSONArray("verificaciones");
				for (int i = 0; i < array.length(); i++) {
					Verificacion ver = new Verificacion();
					JSONObject obj = array.getJSONObject(i);
					ver.setVin(obj.getString("vin"));
					ver.setBrand(obj.getString("marca"));
					ver.setSubBrand(obj.getString("submarca"));
					ver.setAnio(obj.getString("modelo"));
					ver.setCombustible(obj.getString("combustible"));
					ver.setCertificate(obj.getString("certificado"));
					ver.setCanceled(!obj.getString("cancelado").equals("NO"));
					ver.setValidity(obj.getString("vigencia"));
					ver.setVerifyFacility(obj.getString("verificentro"));
					ver.setVerificationDate(obj.getString("fecha_verificacion"));
					ver.setVerificationTime(obj.getString("hora_verificacion"));
					ver.setResult(obj.getString("resultado"));
					ver.setRejectCause(obj.getString("casua_rechazo"));
					verifList.add(ver);
				}
				
				carInfo.setVerificationList(verifList);
				
			}
			
			
		} catch (Exception e) {
		}
		
		
		
		
		
		return carInfo;
		
	}

}
