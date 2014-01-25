package com.hackdf.autochilango.db;

import java.util.ArrayList;
import java.util.List;

import com.hackdf.autochilango.db.DatasetsDBHelper.TablaVerificentro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DatasetsDataSource {
	
	private SQLiteDatabase db;
    private DatasetsDBHelper dbHelper;
    
    private String[] columnasVeri = { TablaVerificentro.COLUMNA_ID,
            TablaVerificentro.COLUMNA_RS,TablaVerificentro.COLUMNA_DIRECCION,
            TablaVerificentro.COLUMNA_DELEGACION,TablaVerificentro.COLUMNA_TEL,
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
    
    public List<Verificentro> getVeris() { 
        Cursor cursor = db.query(TablaVerificentro.TABLA_VERIFICENTRO, columnasVeri, null, null,
                null, null, null);
       
        List<Verificentro> lista=new ArrayList<Verificentro>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
        	Verificentro dato=cursorToVerifi(cursor);
        	lista.add(dato);
        	cursor.moveToNext();
        }
        cursor.close();
        
        return lista;
    }
    
    private Verificentro cursorToVerifi(Cursor cursor) {
        Verificentro veri = new Verificentro();
        veri.setId(cursor.getLong(0));//posicion 0 id
        veri.setRs(cursor.getString(1));
        veri.setDir(cursor.getString(2));
        veri.setDel(cursor.getString(3));
        veri.setTel(cursor.getString(4));
        veri.setLat(cursor.getDouble(5));
        veri.setLng(cursor.getDouble(6));
        return veri;
    }
   

}
