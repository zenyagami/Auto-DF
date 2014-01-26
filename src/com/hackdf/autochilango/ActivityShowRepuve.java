package com.hackdf.autochilango;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ActivityShowRepuve extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_webview);
		WebView wv = (WebView)findViewById(R.id.wbFull);
		wv.loadData(getIntent().getExtras().getString("html"), "text/html", "UTF-8");
	}


}
