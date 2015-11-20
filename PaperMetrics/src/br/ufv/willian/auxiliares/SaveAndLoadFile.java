package br.ufv.willian.auxiliares;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveAndLoadFile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome_arquivo;

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
		
		FileInputStream fis = new FileInputStream(nome_arquivo);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		SaveAndLoadFile slf   = (SaveAndLoadFile) obj;
		ois.close();
		 
		return slf;
	}
	
	public void salvaArquivo() throws IOException, ClassNotFoundException{		
		
		FileOutputStream fos = new FileOutputStream(nome_arquivo);
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();	
	}
	
	public void setNomeArquivo(String nome_arquivo){
		this.nome_arquivo = nome_arquivo;		
	}
	
	public String getNomeArquivo(){
		return nome_arquivo;
	}
	

}
