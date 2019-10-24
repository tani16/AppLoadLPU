package com.catalana.method;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catalana.modelo.ModelData;
import com.catalana.utils.Constantes;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;

public class LoadMethods {
	
	private LoadMethods() {
		throw new IllegalStateException("Utility class");
	}
	
	 /** @param modulo - Programa a examinar
	 * @return áreas de la linkage, para utilizar cuando se escriba el CALL al programa
	 * @throws FileNotFoundException 
	 **/
	public static List<String> getCabecera(String modulo) throws ExceptionLPU, FileNotFoundException {

		String linea;
		String lineaBuena = " ";
		List<String> cabecera;

		List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.RUTA_ORIGEN + "\\" + modulo + "//.CBL");

		for (int i = 0; i < archivo.size(); i++) {
			linea = archivo.get(i).substring(7, 72);

			if (linea.contains("PROCEDURE DIVISION")) {

				for (int e = i; e < archivo.size(); e++) {
					lineaBuena = lineaBuena + archivo.get(e).substring(7, 72);
					if (lineaBuena.contains("."))
						break;

				}
			}
		}
		lineaBuena = lineaBuena.replaceAll(" +", " ").replace(".", "").replace("PROCEDURE DIVISION", "").replace(" USING ", "").trim();
		cabecera = Arrays.asList(lineaBuena.split(" "));

		/*
		 * Debug for (int i = 0; i < cabecera.size(); i++) {
		 * System.out.print(cabecera.get(i) + ", ");
		 * 
		 * } System.out.println("\n\n");
		 */

		return cabecera;
	}

	/**
	 * 
	 * @return modulo del archivo rawData
	 */
	public static String getModulo(ArrayList<String> archivo) {

		String modulo = null;

		String linea = archivo.get(0);
		modulo = linea.substring(linea.indexOf("-") + 1);

		return modulo;
	}
	
	/**
	 * 
	 * @return entorno en el que se llevara a cabo la ejecución
	 */
	public static String getEntorno(ArrayList<String> archivo) {

		String entorno = null;

		String linea = archivo.get(1);
		entorno = linea.substring(linea.indexOf("-") + 1);

		return entorno;
	}
	
	/**
	 * 
	 * @return booleano indicando la necesidad de rollback o no
	 */
	public static boolean needRollback(ArrayList<String> archivo) {

		boolean rollback = false;

		String linea = archivo.get(2);

		if (linea.substring(linea.indexOf("-") + 1).equals("Si"))
			rollback = true;
		if (linea.substring(linea.indexOf("-") + 1).equals("No"))
			rollback = false;

		return rollback;
	}

	
	/**
	 * 
	 * @return devuelve las copys quese van a usar ne un ArrayList
	 */
	public static ArrayList<String> getCopys(ArrayList<String> archivo) {

		ArrayList<String> copys = new ArrayList<String>();
		String linea;

		for (int i = 0; i < archivo.size(); i++) {
			linea = archivo.get(i);
			if (linea.contains("Copy-")) {
				copys.add(linea.substring(linea.indexOf("-") + 1));
			}
		}

		return copys;
	}
	
	/**
	 * Copia la plantilla lanzador en función si es de desarrollo o preproducción
	 * @param lanzador - Archivo salida
	 * @param tipo - Entorno en el que se ejecuta
	 * @param copys - Listado de copys a añadir
	 * @throws ExceptionLPU
	 */
	public static void copiaInicio(BufferedWriter lanzador, String tipo, ArrayList<String> copys) throws ExceptionLPU {

		ArrayList<String> archivo = new ArrayList<>();
		
		if (tipo.equals("DESA")) archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_LANZADOR_DESA);
		if (tipo.equals("PRE"))  archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_LANZADOR_PRE);

		try {

			for (int i = 0; i < archivo.size(); i++) {
				if (archivo.get(i).contains("COPY")) {
					lanzador.write(archivo.get(i).replace("COPY", ""));
					lanzador.newLine();
					for (int e = 0; e < copys.size(); e++) {
						lanzador.write("       " + "COPY " + copys.get(e) + ".");
						lanzador.newLine();
					}
				} else {
					lanzador.write(archivo.get(i));
					lanzador.newLine();
				}
			}
			lanzador.close();
		} catch (IOException e) {
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al escribir la plantilla", "E");
		}

	}
	
	/**
	 * 
	 * @param archivo archivo en array a leer
	 * @return objeto ModelData con los campos llenos excepto areas
	 */
	public static ModelData getProperties(ArrayList<String> archivo) {
		
		ModelData modeldata = new ModelData();
		
		modeldata.setModulo(getModulo(archivo));
		modeldata.setEntorno(getEntorno(archivo));
		modeldata.setRollback(needRollback(archivo));
		modeldata.setCopys(getCopys(archivo));
		
		return modeldata;
	}
	
	public static ArrayList<HashMap<String, String>> getTestCases(ArrayList<String> rawData){
			
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
					prueba = linea.substring(4, 13).trim();
				}
				if(linea.contains("----- Next Area:")) {
					activeShearch = false;
				}
				if(activeShearch && !linea.contains("Prueba")) {
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
	
	public static void writeTestCases(BufferedWriter lanzador, HashMap<String, String> test, String modulo) throws ExceptionLPU {
		
		try {
			writeMoveInLanzador(lanzador, "PROGRAMA", modulo);
		
			for(Map.Entry<String, String> entry : test.entrySet()) {
				String key = entry.getKey();
				String value =  entry.getValue();
				
				writeMoveInLanzador(lanzador, key, value);
				
			}
		} catch (IOException e) {
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al escribir los casos de prueba", "E");
		}
		
	}
	
	private static void writeMoveInLanzador(BufferedWriter lanzador, String variable, String valor) throws IOException {
		
		String lineaAux = Constantes.SPACES_11 + "MOVE " + variable;
		lanzador.write(lineaAux);
		lanzador.newLine();
		
		lineaAux = Constantes.SPACES_15 + "TO " + valor;
		lanzador.write(lineaAux);
		lanzador.newLine();
		
	}

	public static void writeFinal(BufferedWriter lanzador, String tipo) throws ExceptionLPU {
		
		
		try {
			lanzador.write(Constantes.GOBACK);
			lanzador.newLine();
			if("PRE".equals(tipo)) {
				lanzador.write(Constantes.FINAL_PRE);	
				lanzador.newLine();
			}else {
				lanzador.write(Constantes.FINAL_DESA);
				lanzador.newLine();
			}
		} catch (IOException e) {
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al escribir el final del Lanzador", "E");
		}
		
	}

}