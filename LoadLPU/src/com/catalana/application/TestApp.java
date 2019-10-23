package com.catalana.application;

import java.io.FileNotFoundException;
import com.catalana.method.LoadMethods;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;

public class TestApp {

	public static void main(String[] args) throws ExceptionLPU {


		LoadMethods loadMethods = new LoadMethods();
		String file = "C:\\COBOL\\GEN038AC\\GEN038AC\\GEN038AC.CBL";

		throw new ExceptionLPU("Error", "No se encuentra el fichero","W");
	}
}
