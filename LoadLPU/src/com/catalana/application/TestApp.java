package com.catalana.application;


import java.util.ArrayList;

import com.catalana.method.LoadMethods;
import com.catalana.proceso.LoadLPU;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;


public class TestApp {

	public static void main(String[] args) throws ExceptionLPU {
		//LoadLPU.execute();
		
		ArrayList<String> cabeceras = LoadMethods.getCabecera("GEN038AC");
		LoadMethods.writeCallProgram(TratamientoFicheros.createLanzador("DESA"), cabeceras);
	}
}