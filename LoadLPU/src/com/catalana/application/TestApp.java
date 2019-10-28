package com.catalana.application;



import com.catalana.method.LoadMethods;
import com.catalana.proceso.LoadLPU;
import com.catalana.utils.ExceptionLPU;


public class TestApp {

	public static void main(String[] args) throws ExceptionLPU {
		//LoadLPU.execute();
		
		LoadMethods.getCabecera("AGECDEPC");
		
	}
}