package com.catalana.modelo;

import java.util.ArrayList;

public class ModelData {
	
	String modulo;
	String entorno;
	boolean rollback;
	ArrayList<String> copys;
	ArrayList<String> areas;
	
	
	/**
	 * Constructor de la clase ModelData
	 */
	public ModelData() {
		this.modulo = "";
		this.entorno = "";
		this.rollback = false;
		this.copys = null;
		this.areas = null;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}

	public boolean isRollback() {
		return rollback;
	}

	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}

	public ArrayList<String> getCopys() {
		return copys;
	}

	public void setCopys(ArrayList<String> copys) {
		this.copys = copys;
	}

	public ArrayList<String> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<String> areas) {
		this.areas = areas;
	}

	@Override
	public String toString() {
		return "ModelData [modulo=" + modulo + ", entorno=" + entorno + ", rollback=" + rollback + ", copys=" + copys
				+ ", areas=" + areas + "]";
	}
	
	

}
