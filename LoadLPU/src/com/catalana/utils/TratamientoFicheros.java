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
	 * @param tipo - Indica si se realizará la prueba en Preproducción o en
	 *             Desarrollo
	 * @return BuffereReader, listo para ir leyendo línea a línea.
	 * @throws FileNotFoundException
	 * @throws ExceptionLPU
	 * 
	 */
	public static BufferedReader openTemplateLanzador(String tipo) {

		String file;

		if (("PRE").equals(tipo)) {
			file = Constantes.FILE_LANZADOR_DESA;
		} else {
			file = Constantes.FILE_LANZADOR_PRE;
		}

		return openReaderFile(file);

	}

	/**
	 * 
	 * @param tipo - Indica si se realizará la prueba en Preproducción o en
	 *             Desarrollo
	 * @return BufferedWriter listo para ser escrito linea a linea
	 * @throws Exception
	 */
	public static BufferedWriter createLanzador(String tipo) {

		BufferedWriter lanzador = null;
		FileWriter fileWriter;
		String fileName = ("PRE").equals(tipo) ? "LANZADOR.cbl" : "LANZ_PRE.cbl";

		try {
			fileWriter = new FileWriter(Constantes.RUTA_TEMPORAL + fileName);
			lanzador = new BufferedWriter(fileWriter);
		} catch (Exception e) {
			new ExceptionLPU("Error", "No se encuentra el fichero", "W");
		}

		return lanzador;

	}

	/**
	 * 
	 * @param file - Ruta + nombre del fichero a abrir para leer
	 * @return BuffereReader, listo para ir leyendo línea a línea.
	 * @throws FileNotFoundException
	 * @throws ExceptionLPU
	 */
	public static BufferedReader openReaderFile(String file) {

		FileReader fileReader;
		BufferedReader bufferReader = null;

		try {
			fileReader = new FileReader(file);
			bufferReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			new ExceptionLPU("Error", "No se encuentra el fichero", "W");
		}

		return bufferReader;

	}

	public static ArrayList<String> getArrayFromFile(String file) {

		ArrayList<String> archivo = new ArrayList<String>();
		String linea;
		try {
			BufferedReader reader = openReaderFile(file);
			linea = reader.readLine();
			while (linea != null) {
				archivo.add(linea);
				linea = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			new ExceptionLPU("Error", "Error de I/O", "W");
		}

		return archivo;
	}

}
