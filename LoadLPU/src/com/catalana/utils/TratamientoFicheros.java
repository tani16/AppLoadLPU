package com.catalana.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TratamientoFicheros {

	private TratamientoFicheros() {
	    throw new IllegalStateException("Utility class");
	}
	
	
	/**
	 * 
	 * @param tipo - Indica si se realizará la prueba en Preproducción o en Desarrollo
	 * @return BuffereReader, listo para ir leyendo línea a línea.
	 * @throws FileNotFoundException
	 */
	public static BufferedReader openTemplateLanzador (String tipo) throws FileNotFoundException {
		
		String file;
		
		if (("PRE").equals(tipo)) {
			file = Constantes.FILE_LANZADOR_DESA;
		}else {
			file = Constantes.FILE_LANZADOR_PRE;
		}
				
		return openReaderFile(file);
		
	}
	
	/**
	 * 
	 * @param tipo - Indica si se realizará la prueba en Preproducción o en Desarrollo
	 * @return BufferedWriter listo para ser escrito linea a linea
	 * @throws Exception
	 */
	public static BufferedWriter createLanzador(String tipo) throws Exception {
		
		BufferedWriter lanzador;
		FileWriter fileWriter;
		String fileName = ("PRE").equals(tipo) ? "LANZADOR.cbl" : "LANZ_PRE.cbl";
		
		
		try {
			fileWriter = new FileWriter(Constantes.RUTA_TEMPORAL + fileName);
			lanzador = new BufferedWriter(fileWriter);
		}catch (Exception e) {
			throw new Exception();
		}
		
		return lanzador;
		
	}
	
	/**
	 * 
	 * @param file - Ruta + nombre del fichero a abrir para leer
	 * @return BuffereReader, listo para ir leyendo línea a línea.
	 * @throws FileNotFoundException
	 */
	public static BufferedReader openReaderFile(String file) throws FileNotFoundException {
		
		FileReader fileReader;
		BufferedReader bufferReader; 
				
		try {
			fileReader = new FileReader(file);
			bufferReader = new BufferedReader(fileReader);
		}catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
				
		return bufferReader;
		
	}
	
	public static ArrayList<String> getArrayFromFile(String file) throws FileNotFoundException {
		
		ArrayList<String> archivo = new ArrayList<String>();
		String linea;
		BufferedReader reader = openReaderFile(file);
		
		try {
			linea = reader.readLine();
			while(linea != null) {
				archivo.add(linea);
				linea = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return archivo;
	}
	
	
}
