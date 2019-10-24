package com.catalana.proceso;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.catalana.method.LoadMethods;
import com.catalana.modelo.ModelData;
import com.catalana.utils.Constantes;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;
 
public class LoadLPU {

	public static void execute() throws ExceptionLPU {

		//Recuperamos los datos del archivo RawData procedente del Excel
		ArrayList<String> rawData = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
		ModelData dataModel = LoadMethods.getProperties(rawData);
		
		//Creamos el fichero lanzador donde escribiremos las n llamadas de pruebas.
		BufferedWriter lanzador = TratamientoFicheros.createLanzador(dataModel.getEntorno());
		
		//Copiamos el inicio, a partir de la plantilla		
		LoadMethods.copiaInicio(lanzador, dataModel.getEntorno(), dataModel.getCopys());
		
		//Extraemos los diferentes casos de prueba del archivo rawData y la cabecera para hacer el CALL
		ArrayList<HashMap<String, String>> pruebas = LoadMethods.getTestCases(rawData);
		for(int i = 0; i < pruebas.size(); i++) {
			LoadMethods.writeTestCases(lanzador, pruebas.get(i), dataModel);
		}
		
		LoadMethods.writeFinal(lanzador, dataModel.getEntorno());
		
		TratamientoFicheros.bwClose(lanzador);
		
		TratamientoFicheros.moveDll(dataModel.getModulo(), "Before");
		
		
	
	}
}
