package com.catalana.proceso;

import java.io.BufferedWriter;
import java.util.ArrayList;

import com.catalana.method.LoadMethods;
import com.catalana.utils.Constantes;
import com.catalana.utils.TratamientoFicheros;

public class LoadLPU {

	public static void execute() throws Exception {
		ArrayList<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
		String tipo = LoadMethods.getEntorno(archivo);
		BufferedWriter lanzador = TratamientoFicheros.createLanzador(tipo);

	}
}
