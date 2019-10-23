package com.catalana.proceso;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.catalana.method.LoadMethods;
import com.catalana.utils.Constantes;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;

public class LoadLPU {

	public static void execute() throws ExceptionLPU {

		ArrayList<String> rawData = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
		String tipo = LoadMethods.getEntorno();
		BufferedWriter lanzador = TratamientoFicheros.createLanzador(tipo);
		
		
		ArrayList<HashMap<String, String>> pruebas = LoadMethods.getTestCases(rawData);
		String modulo;
		for(int i = 0; i < pruebas.size(); i++) {
			LoadMethods.writeTestCases(lanzador, pruebas.get(i), modulo);
		}
		
		LoadMethods.writeFinal(lanzador, tipo);
		
		
		
		
	
	}
}
