package br.ufv.willian.auxiliares;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe para selecionar um conjunto de telas e copiar para um diretório a parte
 * @author WILLIAN
 *
 */
public class SelecionaCopiaTelas {
	
	public void selecionaCopia() throws ClassNotFoundException, IOException{
		System.out.println(" ******** INCIANDO ******** \n\n");
		
		MedidorDeDificuldade tabela1 = new MedidorDeDificuldade();
		MedidorDeDificuldade tabela2 = new MedidorDeDificuldade();
		CopiaArquivos copiador = new CopiaArquivos();
		ArrayList<String> telasComPlat = new ArrayList<String>();
		ArrayList<String> telasSemPlat = new ArrayList<String>();
		ArrayList<Integer> quantidadeTelasNivelComPlat = new ArrayList<Integer>();
		ArrayList<Integer> quantidadeTelasNivelSemPlat = new ArrayList<Integer>();
		VetorTelasParaTeste todasTelas = new VetorTelasParaTeste();
		int numTela = 0;
		
		tabela1 = tabela1.carregaTabelaTelas("telasEinfoSemPlataforma/");//Telas sem plataforma
		tabela2  = tabela2.carregaTabelaTelas("telasEinfoComPlataforma/"); //Telas com plataforma
		int aux = 0;
		Random rand = new Random();
		int indice = 0;
		
		for(int i = 0; i < tabela1.balde.size(); i++){
			ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>();			
				array2.addAll(tabela1.balde.get(i));
				for(int j = 0; j < array2.size(); j++){
					ArrayList<String> array = new ArrayList<String>();
					array.addAll(array2.get(j));
					aux = array.size();
					if(aux > 50)
						aux = 50;
					quantidadeTelasNivelSemPlat.add(aux);
					while(aux > 0){
						indice = rand.nextInt(array.size());
						telasSemPlat.add(array.remove(indice));
						aux--;						
					}
					array = null;					
				}
				array2 = null;	
		}	
		
		for(int i = 0; i < tabela2.balde.size(); i++){
			ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>();			
				array2.addAll(tabela2.balde.get(i));
				for(int j = 0; j < array2.size(); j++){
					ArrayList<String> array = new ArrayList<String>();
					array.addAll(array2.get(j));
					aux = array.size();
					if(aux > 50)
						aux = 50;
					quantidadeTelasNivelComPlat.add(aux);
					while(aux > 0){
						indice = rand.nextInt(array.size());
						telasComPlat.add(array.remove(indice));
						aux--;
					}
					array = null;					
				}
				array2 = null;	
		}
		
		
		int indiceComPlat = 0;
		int indiceSemPlat = 0;
		aux = 50;
		int cont = 0;
		
		while(telasComPlat.size() > 0 || telasSemPlat.size() > 0){
			if(telasComPlat.size() > 0){
				System.out.println("telasComPlat: " + telasComPlat.size() + " - indice: " + indiceComPlat);
				todasTelas.add(telasComPlat.get(indiceComPlat));				
				copiador.copy(telasComPlat.remove(indiceComPlat), "telasEinfoComPlataforma/", "TodasTelas/", ""+ cont);
				System.out.println("tela" + cont + ": Com plataforma\n");
				cont++;
				
				if(telasComPlat.size() > 0){
					System.out.println("telasComPlat: " + telasComPlat.size() + " - indice: " + indiceComPlat);
					todasTelas.add(telasComPlat.get(indiceComPlat));
					copiador.copy(telasComPlat.remove(indiceComPlat), "telasEinfoComPlataforma/", "TodasTelas/", ""+ cont);
					System.out.println("tela" + cont + ": Com plataforma\n");
					cont++;
				}
				
				indiceComPlat+= aux;
				
				if(indiceComPlat >= (telasComPlat.size() - 2)){
					indiceComPlat = 0;
					if(aux > 1)
						aux -= 2;
					if(aux < 1)
						aux = 1;
					
				}
				
			}
			
			if(telasSemPlat.size() > 0){
				System.out.println("telasSemPlat: " + telasSemPlat.size() + " - indice: " + indiceSemPlat);
				todasTelas.add(telasSemPlat.get(indiceSemPlat));
				copiador.copy(telasSemPlat.remove(indiceSemPlat), "telasEinfoSemPlataforma/", "TodasTelas/", ""+ cont);
				System.out.println("tela" + cont + ": Sem plataforma\n");
				cont++;
				if(telasSemPlat.size() > 0){
					System.out.println("telasSemPlat: " + telasSemPlat.size() + " - indice: " + indiceSemPlat);
					todasTelas.add(telasSemPlat.get(indiceSemPlat));
					copiador.copy(telasSemPlat.remove(indiceSemPlat), "telasEinfoSemPlataforma/", "TodasTelas/", ""+ cont);
					System.out.println("tela" + cont + ": Sem plataforma\n");
					cont++;
				}
				indiceSemPlat+= aux;
				
				if(indiceSemPlat >= (telasSemPlat.size() - 2)){
					indiceSemPlat = 0;
					if(aux > 1)
						aux -= 2;
					if(aux < 1)
						aux = 1;
					
				}
			}
			
		}
		
		
		System.out.println(" \n\n******** FINALIZADO ******** ");
		
		
		
	}
	
	public static void main(String args[]) throws ClassNotFoundException, IOException{
		
		new SelecionaCopiaTelas().selecionaCopia(); 
		
	}

}
