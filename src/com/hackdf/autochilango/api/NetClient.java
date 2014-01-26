package com.hackdf.autochilango.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetClient {
	private static final String PLATES_INFO = "http://dev.datos.labplc.mx/movilidad/vehiculos/%s.json";
	private static final String ENVIROMENT_INFO="http://datos.labplc.mx/aire.json";
	public static JSONObject getPlatesInfo(String plates) throws JSONException,
			IOException {
		return new JSONObject(execute(String.format(PLATES_INFO, plates)));
	}

	public static JSONObject getVrificentroInfo(String plates) throws JSONException,
			IOException {
		return new JSONObject(execute(String.format(PLATES_INFO, plates)));
	}
	public static JSONObject getAireInfo() throws JSONException,
	IOException {
return new JSONObject(execute(ENVIROMENT_INFO));
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
	public static String getHtmlRepuve(String captcha) throws IOException
	{
		String url =String.format("http://www2.repuve.gob.mx:8080/ciudadania/servletconsulta?placa=%s&vin=&folio=&nrpv=&captcha=k2p&pageSource=index.jsp", captcha);
		return execute(url);
	}
	
	public static Bitmap getCaptcha()
	{
		 URL url;
			try {
					
				url = new URL("http://www2.repuve.gob.mx:8080/ciudadania/jcaptcha");
		        //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
		        HttpGet httpRequest = null;

		        httpRequest = new HttpGet(url.toURI());

		        HttpClient httpclient = new DefaultHttpClient();
		        HttpResponse response = (HttpResponse) httpclient
		                .execute(httpRequest);

		        HttpEntity entity = response.getEntity();
		        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
		        InputStream input = b_entity.getContent();
		        
		        return BitmapFactory.decodeStream(input);
		    
				 
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return null;
	}
}
