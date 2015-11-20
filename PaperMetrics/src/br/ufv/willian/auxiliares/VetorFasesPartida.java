package br.ufv.willian.auxiliares;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class VetorFasesPartida extends ArrayList<ClassePartidas> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Random rand;
	//ArrayList<String> lista = new ArrayList();
	
	public VetorFasesPartida carregaVetorFases(String nome) throws IOException, ClassNotFoundException{		
		
		//System.out.print("Carregando...");
		//1 - Crie um objeto FileInputStream
		FileInputStream fis = new FileInputStream(nome);
		 //2 - Crie um ObjectInputStream
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		VetorFasesPartida vetorTelas   = (VetorFasesPartida) obj;
		ois.close();
		//System.out.println(" Pronto.");
		 
		return vetorTelas;
	}
	
	public void salvaVetorFases(String nome) throws IOException, ClassNotFoundException{		
		
		//System.out.print("Salvando...");
		FileOutputStream fos = new FileOutputStream(nome);
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();	
	    //System.out.println(" Pronto.");
		
	}	
		
	public void constroiVetorTelas(ArrayList<String> fases) throws ClassNotFoundException, IOException{
		
		ClasseMontaPartidas campeonato = new ClasseMontaPartidas();
		/*
		ArrayList<ClassePartidas> partidas = campeonato.retornaPartidas(fases);
		for(int j = 0; j < partidas.size(); j++){
			add(partidas.get(j));
		}*/
		this.addAll(campeonato.retornaPartidas(fases));
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		VetorFasesPartida teste = new VetorFasesPartida();
		//teste.salvaVetorFases("fasesParaPartidas");
		//teste.constroiVetorTelas();
		//File f = new File("teste/arq2.txt");
		//boolean b = f.delete();
		
		//File folder = new File("teste/");  
		//if (folder.isDirectory()) {  
		//    File[] sun = folder.listFiles();  
		//    for (File toDelete : sun) {
		//    	if(toDelete.getName().endsWith("arq1.txt"))
		//    		toDelete.delete();  
		//    }  
		//} 
		
		//System.out.println("PRONTO");
		ArrayList<String> array = new ArrayList<String>();
		ClassePartidas partida;
		
		array.add("0");
		array.add("1");
		array.add("2");
		//array.add("3");
		//array.add("4");
		//array.add("5");
		
		Collections.shuffle(array);
		teste.constroiVetorTelas(array);
		array.remove(0);
		int quociente = array.size()/2;
		System.out.println("Tamanho do array: " + array.size());
		System.out.println("Resultado: " + quociente);
		for(int i = 0; i < teste.size(); ){	
			for(int j = 0; j < quociente; j++, i++){
				partida = teste.get(i);
				System.out.print(partida.fase1 + " X " + partida.fase2 + "    ");
			}
			System.out.println();
			
		}		
		
	}
	
}