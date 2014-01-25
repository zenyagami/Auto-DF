package com.hackdf.autochilango.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
	private static final String PREF_NAME ="Pref";
	public static String getCurrentPlate(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getString("pref_current_car", "");
	}
	public static void setCurrentPlate(Context context,String plates)
	{
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		Editor ed =sp.edit();
		ed.putString("pref_current_car", plates);
		ed.commit();
	}

}
