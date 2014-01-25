package com.hackdf.autochilango.entities;

import java.util.ArrayList;

public class Car {
	//placas
	private String plates;
	//debe tenencia
	private int hasDebts;
	private ArrayList<Offenses> offenseList;
	private ArrayList<Verificacion> VerificationList;
	public ArrayList<Offenses> getOffenseList() {
		return offenseList;
	}
	public void setOffenseList(ArrayList<Offenses> offenseList) {
		this.offenseList = offenseList;
	}
	public ArrayList<Verificacion> getVerificationList() {
		return VerificationList;
	}
	public void setVerificationList(ArrayList<Verificacion> verificationList) {
		VerificationList = verificationList;
	}
	public int getHasDebts() {
		return hasDebts;
	}
	public String getPlates() {
		return plates;
	}
	public void setPlates(String plates) {
		this.plates = plates;
	}
	public int isHasDebts() {
		return hasDebts;
	}
	public void setHasDebts(int hasDebts) {
		this.hasDebts = hasDebts;
	}
	public Car(String plates, int hasDebts) {
		super();
		this.plates = plates;
		this.hasDebts = hasDebts;
	}
	public Car() {
		// TODO Auto-generated constructor stub
	} 
}
