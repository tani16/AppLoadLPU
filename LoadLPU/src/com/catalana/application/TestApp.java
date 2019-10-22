package com.catalana.application;

import com.catalana.method.LoadMethods;

public class TestApp {

	public static void main(String[] args) {
		LoadMethods loadMethods = new LoadMethods();
		
		//Pruebas varias
		/*loadMethods.getCabecera("GEN038AC");
		loadMethods.getCabecera("GEN038AS");
		loadMethods.getCabecera("GEN038BC");
		loadMethods.getCabecera("GEN038BS");
		loadMethods.getCabecera("GEN038CC");
		loadMethods.getCabecera("GEN038CS");
		loadMethods.getCabecera("GEN038IC");
		loadMethods.getCabecera("GEN038IS");
		loadMethods.getCabecera("GEN038LC");
		loadMethods.getCabecera("GEN038LS");*/
		System.out.println(loadMethods.getModulo());
		System.out.println(loadMethods.getEntorno());
		System.out.println(loadMethods.needRollback());
		for (int i = 0; i < (loadMethods.getCopys()).size(); i++) {
			System.err.println(loadMethods.getCopys().get(i));
		}
	}

}
