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
import java.util.Comparator;
import java.util.Random;

public class VetorTelasParaTeste extends ArrayList<String> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Random rand;
	//ArrayList<String> lista = new ArrayList();
	
	public VetorTelasParaTeste carregaVetorTelas(String nome) throws IOException, ClassNotFoundException{		
		
		//System.out.print("Carregando...");
		//1 - Crie um objeto FileInputStream
		FileInputStream fis = new FileInputStream(nome);
		 //2 - Crie um ObjectInputStream
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		VetorTelasParaTeste vetorTelas   = (VetorTelasParaTeste) obj;
		ois.close();
		//System.out.println(" Pronto.");
		 
		return vetorTelas;
	}
	
	public void salvaVetorTelas(String nome) throws IOException, ClassNotFoundException{		
		
		//System.out.print("Salvando...");
		FileOutputStream fos = new FileOutputStream(nome);
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();	
	    //System.out.println(" Pronto.");
		
	}
	
	public void constroiVetorTelas(String diretorio){
		
		File dir = new File(diretorio);  //Pasta onde estão as telas		
		  
		String[] arquivos = dir.list();  //Array para guardar o nome de todas as fases
		if (arquivos == null) {   
			System.out.println("Diretorio vazio");
		} else { 
		    for (int i=0; i<arquivos.length; i++) { 
		        add(arquivos[i]);
		    }  
		}	
		
	}
	
	public void selecionarArquivos(ArrayList<ArrayList<String>> arrayDificuldade){
		
		int auxiliar;
		Random rand = new Random();
		
		for(int i = 0; i < arrayDificuldade.size(); i++){
			//System.out.println(i + ": " + arrayDificuldade.get(i).size());						
			
			 if(i < 11)
				auxiliar = i*5;
			else 
				auxiliar = 10;
			 
			auxiliar += 30;
			
			//Remover excessos
			ArrayList<String> a =  arrayDificuldade.get(i);
			while(a.size() > auxiliar){
				a.remove(rand.nextInt(a.size() - 1));
			}
			arrayDificuldade.set(i, a);
		}
	}
	
	public void copiaSelecionadasNovoDiretorio(ArrayList<String> arquivos, String diretorioOrigem, String diretorioDestino) throws IOException{

		System.out.println("INICIANDO COPIA DE ARQUIVOS SELECIONADOS PARA NOVO DIRETORIO...\n");
		CopiaArquivos copiador_de_arquivos = new CopiaArquivos();		
		for(int i = 0; i < arquivos.size(); i++){
			
			copiador_de_arquivos.copy(this.get(i), diretorioOrigem, diretorioDestino, "");
		}
		System.out.println("Arquivos copiados com sucesso!");
		
		
	}
	
	public void constroiVetorTelas() throws ClassNotFoundException, IOException{
		
		rand = new Random();
		ArrayList<ArrayList<String>> arrayDificuldade = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> aux; 
		MedidorDeDificuldade tabela = new MedidorDeDificuldade();		
		
		tabela = tabela.carregaTabelaTelas("TelasSelecionadas/");
		
		for(int i = 0; i < tabela.balde.size(); i++){
			aux = tabela.balde.get(i);
			for(int j = 0; j < aux.size(); j++){				
				arrayDificuldade.add(aux.get(j));
			}
			aux = null;
		}
		
		//selecionarArquivos(arrayDificuldade);
		ArrayList<Integer> quantidade_telas_dificuldade = new ArrayList<Integer>();
		ArrayList<String> auxiliar = new ArrayList<String>();
		ArrayList<String> temporario = new ArrayList<String>();
		for(int i = 0; i < arrayDificuldade.size(); i++){	
			//System.out.println("\tnovo: " + arrayDificuldade.get(i).size());
			//System.out.println(i + ": " + arrayDificuldade.get(i).size());
			if(arrayDificuldade.get(i).size() != 0){
				quantidade_telas_dificuldade.add(arrayDificuldade.get(i).size());
				auxiliar.addAll(arrayDificuldade.get(i));
				
				while(auxiliar.size() > 1)
					temporario.add(auxiliar.remove(rand.nextInt(auxiliar.size() - 1)));				
				
				temporario.add(auxiliar.remove(0));
					
			}
		}		
			
		//System.out.println("Tamanho do arrayDificuldade: " + arrayDificuldade.size());
		//System.out.println("Tamanho do vetorTelas: " + temporario.size() + "\n");
		/*
		System.out.println("Temporario");
		for(int i = 0; i < temporario.size(); i++)
			System.out.println(i + ": " + temporario.get(i));
		System.out.println();
		*/
		//copiaSelecionadasNovoDiretorio(temporario, "TodasTelas/", "TelasSelecionadas/");
		
		int indice_inicio = 0, indice_fim = 0;
		int indice_quantidade = 0;
		int a, b;
		
		
		while(temporario.size() > 0){
		
			//System.out.println("indice_inicio: " + indice_inicio + " - indice_fim: " + indice_fim);
			
			if(indice_inicio >= temporario.size())
				indice_inicio = temporario.size() - 1;			
			this.add(temporario.remove(indice_inicio));
			
			//System.out.println(temporario.size() + ": "+ this.get(this.size() - 1));
			//Modificar o codigo para nao remover o vetor temporizador e só apaga-lo no final
			//Acrecentar um inicializador nas duas pontas pra contar apartir de qual indice começar
			
			if(temporario.size() == 0)
				break;
			
			if(indice_fim >= temporario.size())
				indice_fim = temporario.size() - 1;
			this.add(temporario.remove(temporario.size() - (1 + indice_fim)));
			
			//System.out.println(temporario.size() + ": "+ this.get(this.size() - 1));
			
			a = quantidade_telas_dificuldade.get(indice_quantidade) - 1;
			if(a <= 0)
				quantidade_telas_dificuldade.remove(indice_quantidade);
			else{
				quantidade_telas_dificuldade.set(indice_quantidade, a);
			}
			
			b = quantidade_telas_dificuldade.get(quantidade_telas_dificuldade.size() - (1 +indice_quantidade)) - 1;
			if(b <= 0)
				quantidade_telas_dificuldade.remove(quantidade_telas_dificuldade.size() - (1 +indice_quantidade));
			else{				
				quantidade_telas_dificuldade.set(quantidade_telas_dificuldade.size() - (1 +indice_quantidade), b);
			}
			
			
			indice_inicio += a;
			indice_fim += b;
			indice_quantidade++;
			if(quantidade_telas_dificuldade.size() - (1 + indice_quantidade) < indice_quantidade)
				indice_quantidade = 0;			
			
			if(indice_inicio > temporario.size() - (1 + indice_fim)){
				indice_inicio = 0;
				indice_fim = 0;
			}
		}
		
		Collections.shuffle(this);
		
		/*
		System.out.println("VetorTelas");
		for(int i = 0; i < this.size(); i++)
			System.out.println(i + ": " + this.get(i));
		*/
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		VetorTelasParaTeste teste = new VetorTelasParaTeste();
		teste.salvaVetorTelas("vetorTelas");
		//teste.constroiVetorTelas();
		//File f = new File("teste/arq2.txt");
		//boolean b = f.delete();
		/*
		File folder = new File("teste/");  
		if (folder.isDirectory()) {  
		    File[] sun = folder.listFiles();  
		    for (File toDelete : sun) {
		    	if(toDelete.getName().endsWith("arq1.txt"))
		    		toDelete.delete();  
		    }  
		} 
		*/
		System.out.println("PRONTO");
	}

}
