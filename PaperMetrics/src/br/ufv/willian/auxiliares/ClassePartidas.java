package br.ufv.willian.auxiliares;

import java.io.Serializable;

public class ClassePartidas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4326166879988235423L;
	
	public int fase1, fase2;
	//public String fase1, fase2;
	
	public ClassePartidas(int fase1, int fase2){		
	//public ClassePartidas(String fase1, String fase2){
		this.fase1 = fase1;
		this.fase2 = fase2;
	}

}
