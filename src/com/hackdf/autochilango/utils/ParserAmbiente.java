package com.hackdf.autochilango.utils;

import org.json.JSONObject;

import android.util.Log;

import com.hackdf.autochilango.entities.Aire;


public class ParserAmbiente {
	
	public static Aire ParseAmbienteInfoFromJson(JSONObject informacion)
	{
		if(informacion==null || !informacion.has("consulta"))
		{
			return null;
		}
		Aire air= new Aire();
		try {
			informacion = informacion.getJSONObject("consulta");
				
			
			if(informacion.has("consulta")){

				
			JSONObject obj = informacion.getJSONObject("consulta");
			
			
			
				
				air.setReporte(obj.getString("reporte"));
				
				air.setCategoria(obj.getJSONObject("calidad").getString("categoria"));
				Log.i("", air.getCategoria());
				
				air.setColor(obj.getJSONObject("calidad").getString("color"));
				Log.i("", air.getColor());
				
				air.setRecomendaciones(obj.getJSONObject("calidad").getString("recomendaciones"));
				air.setTemperatura(obj.getJSONObject("clima").getString("temperatura"));
				air.setCondicion(obj.getJSONObject("clima").getString("condicion"));
				air.setIndiceUV(obj.getJSONObject("uv").getString("indiceUV"));
				air.setColorUV(obj.getJSONObject("uv").getString("colorUV"));
				air.setRecomendacionesUV(obj.getJSONObject("uv").getString("recomiendacionesUV"));
				air.setCategoriaNO(obj.getJSONObject("zona").getJSONObject("noroeste").getString("categoriaNO"));
				air.setColorNO(obj.getJSONObject("zona").getJSONObject("noroeste").getString("colorNO"));
				air.setCategoriaNE(obj.getJSONObject("zona").getJSONObject("noreste").getString("categoriaNE"));
				air.setColorNE(obj.getJSONObject("zona").getJSONObject("soreste").getString("colorNE"));
				air.setCategoriaSE(obj.getJSONObject("zona").getJSONObject("sureste").getString("categoriaSE"));
				air.setColorSE(obj.getJSONObject("zona").getJSONObject("sureste").getString("colorSE"));
				air.setCategoriaSO(obj.getJSONObject("zona").getJSONObject("suroeste").getString("categoriaSO"));
				air.setColorSO(obj.getJSONObject("zona").getJSONObject("suroeste").getString("colorSO"));
				
	}
//			
		} catch (Exception e) {
		}
		
		return air;
		
	}
}
