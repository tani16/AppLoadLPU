package com.catalana.method;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
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
	public static ArrayList<String> getCabecera(String modulo) throws ExceptionLPU {
		
		//si empieza por numeros una cosas sino no
		//puede tener numeros al principio pero no al final

		String linea;
		String lineaBuena = " ";
		ArrayList<String> cabecera = new ArrayList<String>();

		List<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.RUTA_ORIGEN + modulo + "\\" + modulo + ".CBL");
		
		for (int i = 0; i < archivo.size(); i++) {
			if (archivo.get(i).length() > 72) linea = archivo.get(i).substring(7, 72);
			else linea = archivo.get(i).substring(7, archivo.get(i).length()-1);

			if (linea.contains("PROCEDURE DIVISION")) {

				for (int e = i; e < archivo.size(); e++) {
					if (archivo.get(e).length() > 72) lineaBuena = lineaBuena + archivo.get(e).substring(7, 72);
					else lineaBuena = lineaBuena + archivo.get(e).substring(7, archivo.get(e).length());
					
					if (lineaBuena.contains(".")) {
						i = archivo.size() + 1;
						break;
					}
				}
			}
		}
		lineaBuena = lineaBuena.replaceAll(" +", " ").replace(".", "").replace("PROCEDURE DIVISION", "").replace(" USING ", "").trim();
		String[] aux = lineaBuena.split(" ");
		
		for (String area : aux) {
			cabecera.add(area);
		}
				
		
		
		for (int i = 0; i < cabecera.size(); i++) {
			System.out.print(cabecera.get(i) + ", ");
		}
		System.out.println("\n\n");
 		

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
	 * @return devuelve las copys que se van a usar en un ArrayList
	 */
	public static ArrayList<String> getCopys(ArrayList<String> archivo) {

		ArrayList<String> copys = new ArrayList<String>();
		String linea;

		for (int i = 0; i < archivo.size(); i++) {
			linea = archivo.get(i);
			if (linea.contains("Copy-")) {
				copys.add(linea.substring(linea.indexOf("-") + 1));
			}
			if (linea.contains("----- Next Area:")) break;
		}

		return copys;
	}
	
	public static ArrayList<String> getAreas (ArrayList<String> archivo) {
		
		ArrayList<String> areas = new ArrayList<String>();
		
		for (int i = 0; i < archivo.size(); i++) {
			if (archivo.get(i).contains("----- Next Area:")) {
				areas.add(archivo.get(i).substring(archivo.get(i).indexOf(": ") + 1, archivo.get(i).indexOf(".") + 1));
				
			}
		}
		
		return areas;
		
		
		
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
		
		if (tipo.equals("DESA")) archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_TEMPLATE_DESA);
		if (tipo.equals("PRE"))  archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_TEMPLATE_PRE);

		try {

			for (int i = 0; i < archivo.size(); i++) {
				if (archivo.get(i).contains("COPY")) {
					lanzador.write(archivo.get(i).replace("COPY", ""));
					lanzador.newLine();
					for (int e = 0; e < copys.size(); e++) {
						lanzador.write(Constantes.SPACES_7 + "COPY " + copys.get(e) + ".");
						lanzador.newLine();
					}
					lanzador.write(Constantes.COPY_GENRETOR);
					lanzador.newLine();
					lanzador.write(Constantes.COPY_GENRETSP);
					lanzador.newLine();
				} else {
					lanzador.write(archivo.get(i));
					lanzador.newLine();
				}
			}
			

		} catch (IOException e) {
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al escribir la plantilla", "E");
		}

	}
	
	/**
	 * 
	 * @param archivo archivo en array a leer
	 * @return objeto ModelData con los campos llenos excepto areas
	 * @throws ExceptionLPU 
	 */
	public static ModelData getProperties(ArrayList<String> archivo) throws ExceptionLPU {
		
		ModelData modelData = new ModelData();
		
		String modulo = getModulo(archivo);
		modelData.setModulo(modulo);
		modelData.setEntorno(getEntorno(archivo));
		modelData.setRollback(needRollback(archivo));
		modelData.setCopys(getCopys(archivo));
		modelData.setCabeceras(getCabecera(modulo));
		modelData.setAreas(getAreas(archivo));
		
		return modelData;
	}
	
	/**
	 * Clasifica Variable-Valor según el caso de pruebas.
	 * @param rawData
	 * @return
	 */
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

	/**
	 * Escribe los initialize de las areas
	 * @param lanzador
	 * @param areas
	 * @throws ExceptionLPU 
	 */
	public static void writeInitialices (BufferedWriter lanzador, ArrayList<String> areas) throws ExceptionLPU {
		
		try {
			for (int i = 0; i < areas.size(); i++) {
				lanzador.write(Constantes.SPACES_11 + "INITIALIZE " + areas.get(i));
				lanzador.newLine();
			}
		} catch (IOException e) {
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al escribir los initialize en el lanzador", "E");
		}
	}
	
	/**
	 * Escribe el call en el lanzador
	 * @param lanzador
	 * @param cabeceras
	 * @throws ExceptionLPU 
	 */
	public static void writeCallProgram (BufferedWriter lanzador, ArrayList<String> cabeceras) throws ExceptionLPU {
		
		try {
			
			lanzador.write(Constantes.SPACES_11 + "CALL PROGRLPU USING " + cabeceras.get(0));
			lanzador.newLine();
			for (int i = 1; i < cabeceras.size(); i++) {
				lanzador.write(Constantes.SPACES_31 + cabeceras.get(i));
				if (i == cabeceras.size() - 1) {
					lanzador.write(".");
					lanzador.newLine();
				}
				else lanzador.newLine();
			}
		} catch (IOException e) {
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al escribir el CALL en el lanzador", "E");
		}
		
	}
	
	
	
	
	
	
	/**
	 * Escribe los diferentes casos de pruebas en el programa Lanzador.
	 * @param lanzador
	 * @param test
	 * @param dataModel
	 * @throws ExceptionLPU
	 */
	public static void writeTestCases(BufferedWriter lanzador, HashMap<String, String> test, ModelData dataModel) throws ExceptionLPU {
		
		try {
			lanzador.write(Constantes.NEW_TEST_COMMENT);
			lanzador.newLine();
			
			writeInitialices(lanzador, dataModel.getAreas());
			writeMoveInLanzador(lanzador, "PROGRLPU", dataModel.getModulo());
		
			for(Map.Entry<String, String> entry : test.entrySet()) {
				String key = entry.getKey();
				String value =  entry.getValue();
				
				writeMoveInLanzador(lanzador, key, value);				
			}
			
			writeCallProgram (lanzador, dataModel.getCabeceras());
			
		} catch (IOException e) {
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al escribir los casos de prueba", "E");
		}
		
	}
	
	/**
	 * Escribe la sentencia move en dos líneas.
	 * @param lanzador 
	 * @param variable - Variable que recibirá el valor.
	 * @param valor - Valor que se moverá a la variable.
	 * @throws IOException
	 */
	private static void writeMoveInLanzador(BufferedWriter lanzador, String variable, String valor) throws IOException {
			
			String lineaAux = Constantes.SPACES_11 + "MOVE '" + valor + "'";
			String[] aux = new String[2];
			lanzador.write(lineaAux);
			lanzador.newLine();
			
			if (variable.contains(" OF ")) {
				aux = variable.split(" OF ");
				lineaAux = Constantes.SPACES_15 + "TO " + aux[0];
				lanzador.write(lineaAux);
				lanzador.newLine();
				lineaAux = Constantes.SPACES_18 + "OF " + aux[1];
				lanzador.write(lineaAux);
				lanzador.newLine();
				
			} else {
				lineaAux = Constantes.SPACES_15 + "TO " + variable;
				lanzador.write(lineaAux);
				lanzador.newLine();
			}
			
		}

	/**
	 * Escribe las dós últimas líneas del programa Lanzador.
	 * @param lanzador
	 * @param tipo
	 * @throws ExceptionLPU
	 */
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

	/**
	 * En el archivo after escribe la string after de parámetro
	 * @param after string a escribir
	 * @param before
	 * @throws ExceptionLPU 
	 */
	public static void addDisplayFail (String after, String before) throws ExceptionLPU {
		
		try {
			
			BufferedWriter writerAfter = TratamientoFicheros.openWriterFile(Constantes.FILE_RESULT_AFTER, true);
			BufferedWriter writerBefore = TratamientoFicheros.openWriterFile(Constantes.FILE_RESULT_BEFORE, true);
			
			
			String[] auxAfter = after.split(Constantes.SPLIT_LINE_KEY);
			for (String string : auxAfter) {
				writerAfter.newLine();
				writerAfter.write(string);
			}
			writerAfter.close();
			
			
			String[] auxBefore = before.split(Constantes.SPLIT_LINE_KEY);
			for (String string : auxBefore) {
				writerBefore.newLine();
				writerBefore.write(string);
			}
			writerBefore.close();
			
		} catch (ExceptionLPU e) {
			// TODO Auto-generated catch block
			throw new ExceptionLPU(Constantes.ERROR, "Se ha producido un error al el archivo after", "E");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void moveLanzadorToRecompile(String entorno) throws ExceptionLPU {
		
		Path origen;
		Path destino;
		
		if("DESA".equals(entorno)) {
			origen = FileSystems.getDefault().getPath(Constantes.FILE_LANZADOR_DESA);
			destino = FileSystems.getDefault().getPath(Constantes.FILE_DEST_DESA);
		}else {
			origen = FileSystems.getDefault().getPath(Constantes.FILE_LANZADOR_PRE);
			destino = FileSystems.getDefault().getPath(Constantes.FILE_DEST_PRE);
		}
		
		TratamientoFicheros.moveFile(origen, destino);
		TratamientoFicheros.deteleFile(origen);
		
	}
	
}






























