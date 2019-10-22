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
		String[] lineas;
		String[] lineasBuena = null;
		List<String> cabecera = new ArrayList<String>();
		
		try {
			BufferedReader reader = TratamientoFicheros.openReaderFile("C:\\COBOL\\"+modulo+"\\"+modulo+"\\"+modulo+".CBL");
			
			linea = reader.readLine();
			linea.substring(7 ,72);
			
			while(linea != null) {
				
				if (linea.contains("PROCEDURE DIVISION")) {
					
					while (!linea.contains(".")) {
						if (linea != null) linea = linea + reader.readLine().substring(7, 72);
						linea.trim();
					}
					
					linea = format(linea);
					cabecera = Arrays.asList(linea.split(" "));
					//System.out.println(linea + "\n\n\n\n");
					
					for (int i = 0; i < cabecera.size(); i++) {
						System.out.print(cabecera.get(i) + " ");
					}
					System.out.println("\n\n");
					
				}
				linea = reader.readLine();
				if (linea != null) linea = linea.substring(7, 72);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
				
		
				
		return cabecera;
	}

	private String format(String linea) {
		linea = linea.replaceAll(" +", " ").replace(".", "").replace("PROCEDURE DIVISION", "").replace(" USING ", "");
		return linea;
	}

}
