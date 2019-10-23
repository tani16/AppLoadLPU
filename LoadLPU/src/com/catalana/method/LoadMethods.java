package com.catalana.method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.catalana.utils.Constantes;
import com.catalana.utils.TratamientoFicheros;

public class LoadMethods {
	
	/**
	 * 
	 * @param modulo - Programa a examinar
	 * @return �reas de la linkage, para utilizar cuando se escriba el CALL al programa
	 */
	public static List<String> getCabecera(String modulo) {
		
		String linea;
		String lineaBuena = " ";
		List<String> cabecera = new ArrayList<String>();
		
		try{
			List<String> archivo = TratamientoFicheros.getArrayFromFile("C:\\COBOL\\"+modulo+"\\"+modulo+"\\"+modulo+".CBL");	
			//Para cuando todo este listo se supone que estara
			//List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.RUTA_ORIGEN + "\\" + modulo + ".CBL");
			
			for (int i = 0; i < archivo.size(); i++) {
				linea = archivo.get(i).substring(7 , 72);
				
				if (linea.contains("PROCEDURE DIVISION")) {
					
					for (int e = i; e < archivo.size(); e++) {
						lineaBuena = lineaBuena + archivo.get(e).substring(7 , 72);
						if (lineaBuena.contains(".")) break;
						
					}
				}
			}
			lineaBuena = lineaBuena.replaceAll(" +", " ").replace(".", "").replace("PROCEDURE DIVISION", "").replace(" USING ", "").trim();
			cabecera = Arrays.asList(lineaBuena.split(" "));
					
					/* Debug
					for (int i = 0; i < cabecera.size(); i++) {
						System.out.print(cabecera.get(i) + ", ");
						
					}
					System.out.println("\n\n");
					*/
								
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return cabecera;
	}
	
	/**
	 * 
	 * @return modulo del archivo rawData
	 */
	public static String getModulo() {
		
		String modulo = null;
		
		try {
			List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
			String linea = archivo.get(0);
			modulo = linea.substring(linea.indexOf("-") + 1);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return modulo;
	}
	
	/**
	 * 
	 * @return entorno en el que se llevara a cabo la ejecuci�n
	 */
	public static String getEntorno() {
		
		String entorno = null;
		
		try {
			List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
			String linea = archivo.get(1);
			entorno = linea.substring(linea.indexOf("-") + 1);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return entorno;
	}
	
	/**
	 * 
	 * @return booleano indicando la necesidad de rollback o no
	 */
	public static boolean needRollback() {
			
		boolean rollback = false;
			
		try {
			List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
			String linea = archivo.get(2);
			
			if (linea.substring(linea.indexOf("-") + 1).equals("Si")) rollback = true;
			if (linea.substring(linea.indexOf("-") + 1).equals("No")) rollback = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
		return rollback;
		}

	
	/**
	 * 
	 * @return devuelve las copys quese van a usar ne un ArrayList
	 */
	public static ArrayList<String> getCopys() {
		
		ArrayList<String> copys = new ArrayList<String>();
		String linea;
		
		try {
			List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
			
			for (int i = 0; i < archivo.size(); i++) {
				linea = archivo.get(i);
				if (linea.contains("Copy-")) {
					copys.add(linea.substring(linea.indexOf("-") + 1));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return copys;
	}
	
	/**
	 * 
	 * @return devuelve en un array lo mismo que los cuatro m�todos anteriores, para mayor comodidad 
	 */
	public static ArrayList<String> getProperties() {
		ArrayList<String> properties = new ArrayList<String>();
		
		try {
			List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
			
			String linea;
			
			for (int i = 0; i < archivo.size(); i++){
				linea = archivo.get(i);
				if (linea.contains("Modulo-")) properties.add(linea.substring(linea.indexOf("-") + 1));
				if (linea.contains("Entorno-")) properties.add(linea.substring(linea.indexOf("-") + 1));
				if (linea.contains("Rollback-")) {
					if (linea.substring(linea.indexOf("-") + 1).equals("Si")) properties.add("true");
					if (linea.substring(linea.indexOf("-") + 1).equals("No")) properties.add("false");
				}
				
				if (linea.contains("Copy-")) properties.add(linea.substring(linea.indexOf("-") + 1));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return properties;
	}
	
public static List<HashMap<String, String>> getTestCases(ArrayList<String> rawData){
		
		ArrayList<HashMap<String, String>> pruebas = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> test1 = new HashMap<>();
		HashMap<String,String> test2 = new HashMap<>();
		HashMap<String,String> test3 = new HashMap<>();
		HashMap<String,String> test4 = new HashMap<>();
		HashMap<String,String> test5 = new HashMap<>();
		HashMap<String,String> test6 = new HashMap<>();
		HashMap<String,String> test7 = new HashMap<>();
		HashMap<String,String> test8 = new HashMap<>();
		HashMap<String,String> test9 = new HashMap<>();
		HashMap<String,String> test10 = new HashMap<>();
		
		boolean activeShearch = false;		
		String linea;
		String prueba = "";
		String[] valores = null;
		
		for (int i=0; i < rawData.size(); i++) {
			linea = rawData.get(i);
			if(linea.contains("--- Prueba")) {
				activeShearch = true;
				prueba = linea.substring(4, 13);
			}
			if(linea.contains("----- Next Area:")) {
				activeShearch = false;
			}
			if(activeShearch) {
				valores = new String[2];
				valores = linea.split("---");
				switch (prueba) {
					case "Prueba 1":					
						test1.put(valores[0],valores[1]);
						break;
					case "Prueba 2":
						test2.put(valores[0],valores[1]);
						break;
					case "Prueba 3":
						test3.put(valores[0],valores[1]);
						break;
					case "Prueba 4":
						test4.put(valores[0],valores[1]);
						break;
					case "Prueba 5":
						test5.put(valores[0],valores[1]);
						break;
					case "Prueba 6":
						test6.put(valores[0],valores[1]);
						break;
					case "Prueba 7":
						test7.put(valores[0],valores[1]);
						break;
					case "Prueba 8":
						test8.put(valores[0],valores[1]);
						break;
					case "Prueba 9":
						test9.put(valores[0],valores[1]);
						break;
					case "Prueba 10":
						test10.put(valores[0],valores[1]);
						break;						
					default:
						break;
				}
			}
		}
		
		pruebas.add(test1);
		pruebas.add(test2);
		pruebas.add(test3);
		pruebas.add(test4);
		pruebas.add(test5);
		pruebas.add(test6);
		pruebas.add(test7);
		pruebas.add(test8);
		pruebas.add(test9);
		pruebas.add(test10);

		return pruebas;		
	}

}