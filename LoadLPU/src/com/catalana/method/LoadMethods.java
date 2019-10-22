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
	 * @return áreas de la linkage, para utilizar cuando se escriba el CALL al programa
	 */
	public List<String> getCabecera(String modulo) {
		
		String linea;
		List<String> cabecera = new ArrayList<String>();
		
		try{
			BufferedReader reader = TratamientoFicheros.openReaderFile("C:\\COBOL\\"+modulo+"\\"+modulo+"\\"+modulo+".CBL");	
			//Para cuando todo este listo se supone que estara
			//BufferedReader reader = TratamientoFicheros.openReaderFile(Constantes.RUTA_ORIGEN + "\\" + modulo + ".CBL");
			linea = reader.readLine();
			
			
			while(linea != null) {
				linea = linea.substring(7 ,72);
				
				if (linea.contains("PROCEDURE DIVISION")) {
					
					while (!linea.contains(".")) {
						linea = linea + reader.readLine().substring(7, 72);
						
					}
					//En este punto ya tiene la línea de procedure al punto entera
					linea = linea.replaceAll(" +", " ").replace(".", "").replace("PROCEDURE DIVISION", "").replace(" USING ", "");
					cabecera = Arrays.asList(linea.split(" "));
					
					/* Debug
					for (int i = 0; i < cabecera.size(); i++) {
						System.out.print(cabecera.get(i) + " ");
						
					}
					System.out.println("\n\n");
					*/
					
				}
				linea = reader.readLine();
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cabecera;
	}
	
	/**
	 * 
	 * @return modulo del archivo rawData
	 */
	public String getModulo() {
		
		String modulo = null;
		
		try {
			BufferedReader reader = TratamientoFicheros.openReaderFile(Constantes.FILE_RAWDATA);
			
			String linea;
			linea = reader.readLine();
			
			if (linea.contains("Modulo-")) {
				modulo = linea.substring(linea.indexOf('-') + 1);
				
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return modulo;
	}
	
	/**
	 * 
	 * @return entorno en el que se llevara a cabo la ejecución
	 */
	public String getEntorno() {
		
		String entorno = null;
		
		try {
			BufferedReader reader = TratamientoFicheros.openReaderFile(Constantes.FILE_RAWDATA);
			String linea;
			linea = reader.readLine();
			
			while (linea != null) {
				if (linea.contains("Entorno-")) {
					entorno = linea.substring(linea.indexOf('-') + 1);
					break;
				}
				linea = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return entorno;
	}
	
	/**
	 * 
	 * @return booleano indicando la necesidad de rollback o no
	 */
	public boolean needRollback() {
			
		boolean rollback = false;
			
		try {
			BufferedReader reader = TratamientoFicheros.openReaderFile(Constantes.FILE_RAWDATA);
				
			String linea;
			linea = reader.readLine();
			while (linea != null) {
				if (linea.contains("Rollback-")) {
					if (linea.contains("Si")) rollback = true;
					if (linea.contains("No")) rollback = false;
					break;
				}
				linea = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return rollback;
		}

	
	/**
	 * 
	 * @return devuelve las copys quese van a usar ne un ArrayList
	 */
	public ArrayList<String> getCopys() {
		
		ArrayList<String> copys = new ArrayList<String>();
		
		try {
			BufferedReader reader = TratamientoFicheros.openReaderFile(Constantes.FILE_RAWDATA);
			
			String linea;
			linea = reader.readLine();
			while (linea != null) {
				if (linea.contains("Copy-")) {
					copys.add(linea.substring(linea.indexOf('-') + 1));
				}
				
				linea = reader.readLine();
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return copys;
	}
	
	/**
	 * 
	 * @return devuelve en un array lo mismo que los cuatro métodos anteriores, para mayor comodidad 
	 */
	public ArrayList<String> getProperties() {
		ArrayList<String> properties = new ArrayList<String>();
		
		try {
			BufferedReader reader = TratamientoFicheros.openReaderFile(Constantes.FILE_RAWDATA);
			
			String linea;
			linea = reader.readLine();
			
			while(linea != null) {
				if (linea.contains("Modulo-")) properties.add(linea.substring(linea.indexOf("-") + 1));
				if (linea.contains("Entorno-")) properties.add(linea.substring(linea.indexOf("-") + 1));
				if (linea.contains("Rollback-")) {
					if (linea.substring(linea.indexOf("-") + 1).equals("Si")) properties.add("true");
					if (linea.substring(linea.indexOf("-") + 1).equals("No")) properties.add("false");
				}
				if (linea.contains("Copy-")) properties.add(linea.substring(linea.indexOf("-") + 1));
				
				linea = reader.readLine();
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return properties;
	}

}
