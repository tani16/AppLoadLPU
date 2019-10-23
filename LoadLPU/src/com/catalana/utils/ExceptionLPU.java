package com.catalana.utils;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ExceptionLPU extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 490862119786996218L;
	private final String titulo;
	private final String mensaje;
	private final String tipo;
	
	public ExceptionLPU(String titulo, String mensaje, String tipo) {
		super();
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.tipo = tipo;
		createPanel();
	}
	
	private void createPanel() {
//		UIManager.put("OptionPane.minimumSize",new Dimension(400,150)); 
//	    UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 20));
//	    UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 20)); 
//	    UIManager.put("TextField.font", new Font("System", Font.PLAIN, 20)); 
	    
	    switch (tipo) {
		case "E":
			JOptionPane.showMessageDialog(null,mensaje,titulo,JOptionPane.ERROR_MESSAGE); 
			break;
		case "I":
			JOptionPane.showMessageDialog(null,mensaje,titulo,JOptionPane.INFORMATION_MESSAGE); 
			break;
		case "W":
			JOptionPane.showMessageDialog(null,mensaje,titulo,JOptionPane.WARNING_MESSAGE); 
			break;
		default:
			break;
		}
	    
	}

}
