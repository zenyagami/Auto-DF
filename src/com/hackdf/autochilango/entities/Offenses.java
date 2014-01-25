package com.hackdf.autochilango.entities;

public class Offenses {
	private String folio;
	private String dateTime;
	private String situation;
	private String fundamento;
	public String getFundamento() {
		return fundamento;
	}
	public void setFundamento(String fundamento) {
		this.fundamento = fundamento;
	}
	private String reason;
	private String base;
	private String sanction;
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getSanction() {
		return sanction;
	}
	public void setSanction(String sanction) {
		this.sanction = sanction;
	}
}
