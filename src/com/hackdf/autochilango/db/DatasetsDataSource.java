package com.hackdf.autochilango.db;

import com.hackdf.autochilango.db.DatasetsDBHelper.TablaVerificentro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatasetsDataSource {
	
	private SQLiteDatabase db;
    private DatasetsDBHelper dbHelper;
    
    private String[] columnasVeri = { TablaVerificentro.COLUMNA_ID,
            TablaVerificentro.COLUMNA_RS,TablaVerificentro.COLUMNA_TEL,
            TablaVerificentro.COLUMNA_LAT,TablaVerificentro.COLUMNA_LNG};
    
    public DatasetsDataSource(Context context) {
        dbHelper = new DatasetsDBHelper(context);
    }
 
    public void open() {
        db = dbHelper.getWritableDatabase();
    }
 
    public void close() {
        dbHelper.close();
    }
    
    public Verificentro getScores() { 
        Cursor cursor = db.query(TablaVerificentro.TABLA_VERIFICENTRO, columnasVeri, null, null,
                null, null, null);
        Verificentro nuevoVerificentro = null;
        if( cursor != null && cursor.moveToFirst() ){
        	nuevoVerificentro = cursorToVerifi(cursor);
        }
        cursor.close();
        
        return nuevoVerificentro;
    }
    
    private Verificentro cursorToVerifi(Cursor cursor) {
        Verificentro veri = new Verificentro();
        veri.setId(cursor.getLong(0));//posicion 0 id
        veri.setRs(cursor.getString(1));
        veri.setDir(cursor.getString(1));
        veri.setDel(cursor.getString(1));
        veri.setTel(cursor.getString(1));
        veri.setLat(cursor.getString(1));
        veri.setLng(cursor.getString(1));
        return veri;
    }

}
