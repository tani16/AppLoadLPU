package com.catalana.application;

import com.catalana.proceso.LoadLPU;
import com.catalana.utils.ExceptionLPU;

public class MainApp {

	public static void main(String[] args) throws ExceptionLPU  {
		LoadLPU.execute();
	}
}