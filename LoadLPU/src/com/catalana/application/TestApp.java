package com.catalana.application;

import java.io.FileNotFoundException;
import com.catalana.method.LoadMethods;
import com.catalana.utils.ExceptionLPU;
import com.catalana.utils.TratamientoFicheros;

public class TestApp {

	public static void main(String[] args) throws ExceptionLPU {

		String linea = "AGECDEPC-PAG-IND-AGRU OF AGECDEPC-R-PAGOS(3)---agrupag3";
		String valores[] = linea.split("---");
		String aux = "--- Prueba 10 ---";
		String aux2 = aux.substring(4, 13).trim();
	}
}
