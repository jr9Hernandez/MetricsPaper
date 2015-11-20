package br.ufv.willian.auxiliares;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import dk.itu.mario.level.Level;

public class TabelaTelas extends HashMap<String, Level>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SaveAndLoadFile carregaArquivo(String nome) throws IOException, ClassNotFoundException{		
		
		FileInputStream fis = new FileInputStream(nome);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		SaveAndLoadFile slf   = (SaveAndLoadFile) obj;
		ois.close();
		 
		return slf;
	}
	
	public void salvaArquivo(String nome) throws IOException, ClassNotFoundException{		
		
		FileOutputStream fos = new FileOutputStream(nome);
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();	
	}	
	
	public SaveAndLoadFile carregaArquivo() throws IOException, ClassNotFoundException{		
		
		FileInputStream fis = new FileInputStream("");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		SaveAndLoadFile slf   = (SaveAndLoadFile) obj;
		ois.close();
		 
		return slf;
	}
	
	public void salvaArquivo() throws IOException, ClassNotFoundException{		
		
		FileOutputStream fos = new FileOutputStream("");
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();	
	}
	
	

}
