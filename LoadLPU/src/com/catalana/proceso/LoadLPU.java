package com.catalana.proceso;

import java.io.BufferedWriter;

import com.catalana.method.LoadMethods;
import com.catalana.utils.TratamientoFicheros;

public class LoadLPU {

	public static void execute() throws Exception {

		String tipo = LoadMethods.getEntorno();
		BufferedWriter lanzador = TratamientoFicheros.createLanzador(tipo);
	
		
	
	}
}
