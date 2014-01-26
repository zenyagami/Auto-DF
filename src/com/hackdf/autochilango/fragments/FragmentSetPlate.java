package com.hackdf.autochilango.fragments;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.hackdf.autochilango.ActivityStolenVehicule;
import com.hackdf.autochilango.MainActivity;
import com.hackdf.autochilango.R;
import com.hackdf.autochilango.api.NetClient;
import com.hackdf.autochilango.preferences.AppPreferences;
import com.hackdf.autochilango.utils.Dialogs;
import com.hackdf.autochilango.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSetPlate extends Fragment implements OnClickListener {

	private static final String REPUVE ="http://www2.repuve.gob.mx:8080/ciudadania/servletconsulta?placa=%s&vin=&folio=&nrpv=&captcha=%s&pageSource=index.jsp";
	private GetPlateInfo getPlateInfo;
	private EditText etPlates;
	private EditText etCaptcha; 
	private boolean stoledFragment;
	private WebView wbCaptcha;
	private String htmlRepuve;
	private boolean isEdit;
	
	public static FragmentSetPlate newInstance(boolean isStolen) {
		FragmentSetPlate f = new FragmentSetPlate();
		Bundle args = new Bundle();
		args.putBoolean("stolen", isStolen);
		f.setArguments(args);
		return f;
	}
	public static FragmentSetPlate newInstance(Bundle args) {
		FragmentSetPlate f = new FragmentSetPlate();
		f.setArguments(args);
		return f;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_set_current_plate, null);
		((Button) v.findViewById(R.id.btnAcceptPlate)).setOnClickListener(this);
		((Button) v.findViewById(R.id.btnCanelPlate)).setOnClickListener(this);
		etPlates = (EditText) v.findViewById(R.id.etPlates);
		etCaptcha = (EditText)v.findViewById(R.id.etCaptcha);
		wbCaptcha = (WebView)v.findViewById(R.id.wbCaptcha);
		if(getArguments()!=null && getArguments().getBoolean("stolen"))
		{
			((TextView)v.findViewById(R.id.txtTitleSetCurrentPlate)).setText("Consultar Placa por Robo");
			stoledFragment = getArguments().getBoolean("stolen");
			v.findViewById(R.id.captcha).setVisibility(View.VISIBLE);
			v.findViewById(R.id.btnCanelPlate).setVisibility(View.GONE);
			setupCaptcha();
		}
		if(getArguments()!=null && getArguments().containsKey("edit"))
		{
			isEdit=true;
		}
		return v;
	}

	private void setupCaptcha() {
		wbCaptcha.getSettings().setJavaScriptEnabled(true);
		MyJavaScriptInterface interface1 = new MyJavaScriptInterface(getActivity());
		wbCaptcha.addJavascriptInterface(interface1, "HtmlViewer");
		
		wbCaptcha.setWebViewClient(new WebViewClient() {
	        @Override
	        public void onPageFinished(WebView view, String url)
	        {
	            /* This call inject JavaScript into the page which just finished loading. */
	        	wbCaptcha.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
	        }
	    });
		wbCaptcha.loadUrl("http://www2.repuve.gob.mx:8080/ciudadania/jcaptcha");
		
	}
	   class MyJavaScriptInterface {

	        private Context ctx;
	        MyJavaScriptInterface(Context ctx) {
	            this.ctx = ctx;
	        }

	        @JavascriptInterface
	        public void showHTML(String html) {
	            Log.v("test", html);
	            if(html.contains("logoSEGOB"))
	            {
	            	if (getPlateInfo == null) {
	    				wbCaptcha.setVisibility(View.INVISIBLE);
	    				getPlateInfo = new GetPlateInfo();
	    				getPlateInfo.execute("");
	    			}
	            	
	            	htmlRepuve = html;
	            }
	        }

	    }
	   private void startStealActivity(String json)
	   {
		   startActivity(new Intent(getActivity(), ActivityStolenVehicule.class).putExtra("html", htmlRepuve).putExtra("json", json));
	   }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCanelPlate:
			if(isEdit)
			{
				startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK));
				getActivity().finish();
			}
			break;
		case R.id.btnAcceptPlate:
			if(!Utils.isOnline(getActivity()))
			{
				Toast.makeText(getActivity(), "No se tiene acceso a Internet :(", Toast.LENGTH_SHORT).show();
				return;
			}
			if(stoledFragment && etCaptcha.getText().toString().isEmpty())
			{
				Toast.makeText(getActivity(), "Se nececita el captcha", Toast.LENGTH_SHORT).show();
				return;
			}if(etPlates.getText().toString().isEmpty())
			{
				Toast.makeText(getActivity(), "Se nececita la placa", Toast.LENGTH_SHORT).show();
				return;
			}
			if(stoledFragment)
			{
				// manda a llamar API que obtiene la info de la placa :)
				Dialogs.showDialog(getActivity());
				String plates = etPlates.getText().toString().replace("-", "").trim();
				wbCaptcha.loadUrl(String.format(REPUVE, plates,etCaptcha.getText().toString()));
				wbCaptcha.setVisibility(View.INVISIBLE);
			}else
			{
				if (getPlateInfo == null) {
    				getPlateInfo = new GetPlateInfo();
    				getPlateInfo.execute("");
    			}
            	
			}
			
			break;
		default:
			break;
		}

	}

	private class GetPlateInfo extends AsyncTask<String, Void, JSONObject> {
		String plates ="";
		@Override
		protected JSONObject doInBackground(String... params) {
			//empezamos a cargar wl webview con el post :D
			
			try {
				plates = etPlates.getText().toString();
				return NetClient.getPlatesInfo(plates);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			if (getActivity() == null || isDetached() || !isAdded()) {
				return;
			}
			Dialogs.dismissLoadingDialog(getActivity());
			getPlateInfo=null;
			if(result==null)
			{
				Toast.makeText(getActivity(), "Error al obtener datos :(", Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				if(result.has("consulta") && result.getString("consulta").equals("placa_invalida"))
				{
					Toast.makeText(getActivity(), "Placa Inválida", Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
				Toast.makeText(getActivity(), "Placa Inválida", Toast.LENGTH_SHORT).show();
				return;
			}
			
			try {
				if(stoledFragment)
				{
					startStealActivity(result.toString());
				}else
				{
					AppPreferences.setJsonCurrentPlate(getActivity(), result.toString());
					AppPreferences.setCurrentPlate(getActivity(), plates);
					startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK) );
					getActivity().finish();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), "Error al Parsear la informacion", Toast.LENGTH_SHORT).show();
			}
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!stoledFragment) {
				Dialogs.showDialog(getActivity());
			}
			
		}

	}

}
