package com.hackdf.autochilango.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class NetClient {
	private static final String PLATES_INFO = "http://dev.datos.labplc.mx/movilidad/vehiculos/%s.json";

	public static JSONObject getPlatesInfo(String plates) throws JSONException,
			IOException {
		return new JSONObject(execute(String.format(PLATES_INFO, plates)));
	}

	public static JSONObject getPlatesInfo(String plates) throws JSONException,
			IOException {
		return new JSONObject(execute(String.format(PLATES_INFO, plates)));
	}

	private static String execute(String nombreurl) throws IOException {
		String data = "";
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;

		try {
			URL url = new URL(nombreurl);

			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.connect();
			inputStream = httpURLConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			inputStream.close();
			httpURLConnection.disconnect();
		}

		return data;
	}
}
