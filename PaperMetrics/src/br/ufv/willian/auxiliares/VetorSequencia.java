package br.ufv.willian.auxiliares;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class VetorSequencia extends ArrayList<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VetorSequencia carregaVetorSequencia(String nome) throws IOException, ClassNotFoundException{		
		
		//System.out.print("Carregando...");
		//1 - Crie um objeto FileInputStream
		FileInputStream fis = new FileInputStream(nome);
		 //2 - Crie um ObjectInputStream
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		VetorSequencia vetorTelas   = (VetorSequencia) obj;
		ois.close();
		//System.out.println(" Pronto.");
		 
		return vetorTelas;
	}
	
	public void salvaVetorSequencia(String nome) throws IOException, ClassNotFoundException{		
		
		//System.out.print("Salvando...");
		FileOutputStream fos = new FileOutputStream(nome);
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();	
	    //System.out.println(" Pronto.");
		
	}	
	
	public void montaVetorSequencia(int quadradoLatino){
		if(quadradoLatino == 1){
			//1 2 4 3    		
			add(1);
    		add(2);
    		add(4);
    		add(3);
    	}
		else if(quadradoLatino == 2){
			//2 3 1 4	    		
    		add(2);
    		add(3);
    		add(1);
    		add(4);	    		
		}
		else if(quadradoLatino == 3){
			//3 4 2 1
    		add(3);
    		add(4);
    		add(2);
    		add(1);
		}
		else if(quadradoLatino == 4){
			//4 1 3 2
    		add(4);
    		add(1);
    		add(3);
    		add(2);
		}
	}

}
