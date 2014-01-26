package com.hackdf.autochilango.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetClient {
	private static final String PLATES_INFO = "http://dev.datos.labplc.mx/movilidad/vehiculos/%s.json";
	private static final String ENVIROMENT_INFO="http://datos.labplc.mx/aire.json";
	private static String cookies="";
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
	public static BasicCookieStore getCookieStore(String cookies, String domain) {
	    String[] cookieValues = cookies.split(";");
	    BasicCookieStore cs = new BasicCookieStore();

	    BasicClientCookie cookie;
	    for (int i = 0; i < cookieValues.length; i++) {
	        String[] split = cookieValues[i].split("=");
	        if (split.length == 2)
	            cookie = new BasicClientCookie(split[0], split[1]);
	        else
	            cookie = new BasicClientCookie(split[0], null);

	        cookie.setDomain(domain);
	        cs.addCookie(cookie);
	    }
	    return cs;
	}
	//TODO hacer uno solo get/post
	private static String executePost(String endpoint,List<NameValuePair> params)
	{
		try {
			URL url = new URL(endpoint);
			 HttpURLConnection urlConnection = null;
	         urlConnection = (HttpURLConnection) url.openConnection();
	         urlConnection.setUseCaches(false);
	         urlConnection.setDoOutput(true);
	         urlConnection.setRequestMethod("POST");
	         urlConnection.setRequestProperty("Cookie", cookies);
	         OutputStreamWriter out = new OutputStreamWriter(
	                 urlConnection.getOutputStream());
	        
	         out.write(getQuery(params));
	         out.close();	
	         try {
	             InputStream in = new BufferedInputStream(urlConnection.getInputStream());
	             String parseString = streamToString(in);

	             in.close();
	             return parseString;
	         }
	         finally {
	             urlConnection.disconnect();
	         }
		} catch (Exception e) {
		}
		return null;
		 
	}
	 private static String streamToString(InputStream is){
	        Scanner s = new Scanner(is).useDelimiter("\\A");
	        return s.hasNext()?s.next():"";
	    }
	   private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	    {
	        StringBuilder result = new StringBuilder();
	        boolean first = true;

	        for (NameValuePair pair : params)
	        {
	            if (first)
	                first = false;
	            else
	                result.append("&");

	            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	            result.append("=");
	            if(pair.getValue()!=null)
	                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	            else
	                result.append("");
	        }

	        return result.toString();
	    }
	public static String getHtmlRepuve(String captcha,String plates) throws IOException
	{
		String url ="http://www2.repuve.gob.mx:8080/ciudadania/servletconsulta";
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("placa", plates));
		params.add(new BasicNameValuePair("vin", ""));
		params.add(new BasicNameValuePair("folio", ""));
		params.add(new BasicNameValuePair("nrpv", ""));
		params.add(new BasicNameValuePair("captcha", captcha));
		params.add(new BasicNameValuePair("pageSource", "index.jsp"));
		return executePost(url, params);
	}
	
	public static Bitmap getCaptcha()
	{
		 URL url;
			try {
				String endpoint ="http://www2.repuve.gob.mx:8080/ciudadania/jcaptcha"; 
				url = new URL(endpoint);
		        //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
		        HttpGet httpRequest = null;
		        CookieStore cookieStore = new BasicCookieStore();
		        httpRequest = new HttpGet(url.toURI());
		        HttpContext ctx = new BasicHttpContext();
		        ctx.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpResponse response = (HttpResponse) httpclient
		                .execute(httpRequest,ctx);
		        
		        List<Cookie> cookies = cookieStore.getCookies();
		        if( !cookies.isEmpty() ){
		            for (Cookie cookie : cookies){
		                 NetClient.cookies= cookie.toString();
		                 break;
		            }
		        }
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
