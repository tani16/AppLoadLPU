package com.catalana.application;

import java.util.ArrayList;
import java.util.List;

import com.catalana.method.LoadMethods;
import com.catalana.utils.Constantes;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;

public class TestApp {

	public static void main(String[] args) throws ExceptionLPU {
		String file = "C:\\COBOL\\GEN038AC\\GEN038AC\\GEN038AC.CBL";
		ArrayList<String> archivo = TratamientoFicheros.getArrayFromFile(Constantes.FILE_RAWDATA);
		LoadMethods.copiaInicio(TratamientoFicheros.createLanzador("DESA"), "DESA", LoadMethods.getCopys(archivo));

		// Pruebas varias

		// LoadMethods.getCabecera("GEN038AC");
		//System.out.println(LoadMethods.getModulo(archivo));
		//System.out.println(LoadMethods.getEntorno(archivo));
		//System.out.println(LoadMethods.needRollback(archivo));

		//for (int i = 0; i < (LoadMethods.getCopys(archivo)).size(); i++)
			//System.err.println(LoadMethods.getCopys(archivo).get(i));

		//for (int i = 0; i < (LoadMethods.getProperties(archivo)).size(); i++)
			//System.err.println(LoadMethods.getProperties(archivo).get(i));

		// for (int i = 0; i < archivo.size(); i++) System.out.println(archivo.get(i));

		//throw new ExceptionLPU("Error", "No se encuentra el fichero","W");
	}
}
