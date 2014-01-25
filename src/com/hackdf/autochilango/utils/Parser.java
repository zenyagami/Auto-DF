package com.hackdf.autochilango.utils;

import org.json.JSONObject;

import com.hackdf.autochilango.entities.Car;

public class Parser {
	public static Car ParseCarInfoFromJson(JSONObject response)
	{
		if(response==null || !response.has("consulta"))
		{
			return null;
		}
		Car carInfo = new Car();
		try {
			carInfo.setPlates(response.getString("placa"));
			if(response.has("tenencias") && response.getJSONObject("tenencias").has("tieneadeudos"))
			{
				carInfo.setHasDebts(Integer.valueOf(response.getJSONObject("tenencias").getString("tieneadeudos")));
			}
			
			
		} catch (Exception e) {
		}
		
		
		
		
		
		return carInfo;
		
	}

}
