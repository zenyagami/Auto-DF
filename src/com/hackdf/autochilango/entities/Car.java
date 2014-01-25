package com.hackdf.autochilango.entities;

public class Car {
	//placas
	private String plates;
	//debe tenencia
	private boolean hasDebts;
	public String getPlates() {
		return plates;
	}
	public void setPlates(String plates) {
		this.plates = plates;
	}
	public boolean isHasDebts() {
		return hasDebts;
	}
	public void setHasDebts(boolean hasDebts) {
		this.hasDebts = hasDebts;
	}
	public Car(String plates, boolean hasDebts) {
		super();
		this.plates = plates;
		this.hasDebts = hasDebts;
	} 
}
