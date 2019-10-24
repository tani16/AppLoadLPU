package com.catalana.application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.catalana.method.LoadMethods;
import com.catalana.utils.Constantes;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;

public class TestApp {

	public static void main(String[] args) throws ExceptionLPU, FileNotFoundException {

		//ArrayList<String> rawData = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
		//List<HashMap<String, String>> prueba = LoadMethods.getTestCases(rawData);
		
		System.out.println(LoadMethods.getProperties(TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA)));
	}
}