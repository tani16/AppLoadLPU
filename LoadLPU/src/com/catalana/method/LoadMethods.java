package com.catalana.method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.catalana.utils.Constantes;
import com.catalana.utils.TratamientoFicheros;

public class LoadMethods {

	/**
	 * 
	 * @param modulo - Programa a examinar
	 * @return áreas de la linkage, para utilizar cuando se escriba el CALL al
	 *         programa
	 */
	public static List<String> getCabecera(String modulo) {

		String linea;
		String lineaBuena = " ";
		List<String> cabecera = new ArrayList<String>();

		List<String> archivo = TratamientoFicheros
				.getArrayFromFile("C:\\COBOL\\" + modulo + "\\" + modulo + "\\" + modulo + ".CBL");
		// Para cuando todo este listo se supone que estara
		// List<String> archivo =
		// TratamientoFicheros.getArrayFromFile(Constantes.RUTA_ORIGEN + "\\" + modulo +
		// ".CBL");

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
		lineaBuena = lineaBuena.replaceAll(" +", " ").replace(".", "").replace("PROCEDURE DIVISION", "")
				.replace(" USING ", "").trim();
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
	 * 
	 * @return devuelve en un array lo mismo que los cuatro métodos anteriores, para
	 *         mayor comodidad
	 */
	public static ArrayList<String> getProperties(ArrayList<String> archivo) {
		ArrayList<String> properties = new ArrayList<String>();

		String linea;

		for (int i = 0; i < archivo.size(); i++) {
			linea = archivo.get(i);
			if (linea.contains("Modulo-"))
				properties.add(linea.substring(linea.indexOf("-") + 1));
			if (linea.contains("Entorno-"))
				properties.add(linea.substring(linea.indexOf("-") + 1));
			if (linea.contains("Rollback-")) {
				if (linea.substring(linea.indexOf("-") + 1).equals("Si"))
					properties.add("true");
				if (linea.substring(linea.indexOf("-") + 1).equals("No"))
					properties.add("false");
			}

			if (linea.contains("Copy-"))
				properties.add(linea.substring(linea.indexOf("-") + 1));
		}

		return properties;
	}

}
