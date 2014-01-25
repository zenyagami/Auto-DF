package com.hackdf.autochilango.db;

public class Estacionamiento {
	
	private String _id;
	private String del;
	private String calle;
	private String num;
	private String bis;
	private String col;
	private String edif;
	private String struct;
	private String superf;
	private int cajones;
	private double lat;
    private  double lng;
    
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}	
	public String getBis() {
		return bis;
	}
	public void setBis(String bis) {
		this.bis = bis;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getEdif() {
		return edif;
	}
	public void setEdif(String edif) {
		this.edif = edif;
	}
	public String getStruct() {
		return struct;
	}
	public void setStruct(String struct) {
		this.struct = struct;
	}
	public String getSuperf() {
		return superf;
	}
	public void setSuperf(String superf) {
		this.superf = superf;
	}
	public int getCajones() {
		return cajones;
	}
	public void setCajones(int cajones) {
		this.cajones = cajones;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	
}
