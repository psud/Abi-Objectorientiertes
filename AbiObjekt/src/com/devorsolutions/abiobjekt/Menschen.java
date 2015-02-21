package com.devorsolutions.abiobjekt;

import java.util.ArrayList;
import java.util.List;

public class Menschen {

	private String vorname;
	private String nachname;
	private String geburtsdatum;
	private String herkunft;
	private double gewicht;
	private Menschen bestFriend;
	
	public enum attributes {VORNAME, NACHNAME, GEBURTSDATUM, GEWICHT, HERKUNFT};

	public Menschen(String vorname, String nachname){
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public Menschen(String vorname, String nachname, String geburtsdatum, String herkunft){
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
		this.herkunft = herkunft;

	}

	public List<String> hasExtraData(){
		List<String> datas = new ArrayList<String>();
		if (hasGeburtsdatum())
			datas.add("geburtsdatum");
		if (hasGewicht())
			datas.add("gewicht");
		if (hasHerkunft())
			datas.add("herkunft");
		if (hasFriend())
			datas.add("friend");
		return datas;
	}
	
	public int sizeExtras(){
		return hasExtraData().size();
	}
	
	public boolean hasGeburtsdatum(){
		return geburtsdatum != null;
	}
	public boolean hasHerkunft(){
		return herkunft != null;
	}
	public boolean hasGewicht(){
		return gewicht != 0;
	}
	public boolean hasFriend(){
		return bestFriend != null;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public String getHerkunft() {
		return herkunft;
	}

	public void setHerkunft(String herkunft) {
		this.herkunft = herkunft;
	}

	public double getGewicht() {
		return gewicht;
	}

	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}

	public Menschen getBestFriend() {
		return bestFriend;
	}
	public void setBestFriend(Menschen bestFriend) {
		this.bestFriend = bestFriend;
	}

	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Vorname: " + vorname);
		str.append(" - ");
		str.append("Nachname: " + nachname);
		str.append(" - ");
		str.append("Geburtsdatum: " + geburtsdatum);
		str.append(" - ");
		str.append("Gewicht: " + gewicht);
		str.append(" - ");
		str.append("Herkunftsland: " + herkunft);
		str.append(" - ");
		if (hasFriend())
		str.append("Best Friend: " + bestFriend.getVorname() + " " + bestFriend.getNachname());
		return str.toString();
	}

}
