package com.catalana.application;

import com.catalana.method.LoadMethods;
import com.catalana.utils.ExceptionLPU;

public class TestApp {

	public static void main(String[] args) throws ExceptionLPU {
		LoadMethods loadMethods = new LoadMethods();
		
		throw new ExceptionLPU("Error", "No se encuentra el fichero","W");
	}
}
