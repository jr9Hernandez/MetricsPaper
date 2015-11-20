package br.ufv.dpi.dados;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import br.ufv.dpi.DadosAvaliacaoTelas;
import br.ufv.willian.auxiliares.MedidorDeDificuldade;

/**
 * Classe para calcular a m�dia, separar e realizar demais opera��es necess�rias com 
 * as informa��es colhidas de usu�rios sobre as telas 
 * @author WILLIAN
 *
 */
public class CalculaMediasDadosTelas {
	
	class Tela{
		public double quantidade;
		public double jogabilidade;
		public double diversao;
		public double dificuldade;
		
		public Tela(){
			quantidade   = 0;
			jogabilidade = 0;
			diversao     = 0;
			dificuldade  = 0;
		}
		
		public void setDados(int jogabilidade, int diversao, int dificuldade ){
			if(quantidade == 0){
				this.jogabilidade = jogabilidade * 1.0;
				this.diversao     = diversao * 1.0;
				this.dificuldade  = dificuldade * 1.0;
				quantidade++;
			}else{
				double aux;
				
				aux = this.jogabilidade * quantidade * 1.0 + jogabilidade;
				quantidade++;
				this.jogabilidade = aux / quantidade;
				quantidade--;
				
				aux = this.diversao * quantidade * 1.0 + diversao;
				quantidade++;
				this.diversao = aux / quantidade;
				quantidade--;
				
				aux = this.dificuldade * quantidade * 1.0 + dificuldade;
				quantidade++;
				this.dificuldade = aux / quantidade;				
			}			
		}
		
	}//Fim classe local Teste
	
	Map<String,Tela> example = new HashMap<String,Tela>();
	
	
	public ArrayList<String> retornaListaArquivos(String diretorio){
		
		ArrayList<String> lista_arquivos = new ArrayList<String>();
		File dir = new File(diretorio + "/");	
			  
		String[] children = dir.list();  
		if (children == null) {  
		    // Either dir does not exist or is not a directory  
			System.out.println("Diretorio vazio");
		} else {  
			//System.out.println("Tamanho: " + children.length);
		    for (int i=0; i<children.length; i++) {
		        lista_arquivos.add(children[i]);
		    }  
		}
			
		return lista_arquivos;
	}
	
	public void setaInformacaoTela(){
		
		String diretorio = "ArquivosRecebidos05-01-14";
		ArrayList<String> lista_arquivos = retornaListaArquivos(diretorio);
		DadosAvaliacaoTelas dados = new DadosAvaliacaoTelas();
		Tela tela;
		for(int i=0; i < lista_arquivos.size(); i++){			
			try {
				
				dados = dados.carregarArquivo(diretorio + "/" +lista_arquivos.get(i));
				
				if (example.containsKey(dados.getTela())){
					tela = example.get(dados.getTela());
					tela.setDados(dados.getJogabilidade(), dados.getDiversao(), dados.getDificuldade());
					example.put(dados.getTela(), tela);
				}else{ 
					tela = new Tela();
					tela.setDados(dados.getJogabilidade(), dados.getDiversao(), dados.getDificuldade());
					example.put(dados.getTela(), tela);
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//int i = 0;
		Tela saida;
		MedidorDeDificuldade montador = new MedidorDeDificuldade();
		try {
			montador.salvaTabelaTelas("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Montando a tabela dificuldade...");
		for (String key : example.keySet()) {
			//System.out.println(i + ": " + key); 
			//i++;
			saida = example.get(key);
			
			if(saida.quantidade > 1){
				switch ((int)saida.dificuldade) {
				case 9:
					if(saida.jogabilidade > 5 && saida.diversao > 5){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}				
					break;
				case 8:
					if(saida.jogabilidade > 6 && saida.diversao > 6){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}				
					break;
				case 7:
					if(saida.jogabilidade > 6 && saida.diversao > 7){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}				
					break;
				case 6:
					if(saida.jogabilidade > 6 && saida.diversao > 7){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}				
					break;
				case 5:
					if(saida.jogabilidade > 6 && saida.diversao > 6){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						catch (IOException e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}				
					break;
				case 4:
					if(saida.jogabilidade > 6 && saida.diversao > 6){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}				
					break;
				case 3:
					if(saida.jogabilidade > 6 && saida.diversao > 5){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}				
					break;
				case 2:
					if(saida.jogabilidade > 5 && saida.diversao > 5){
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					
					break;
				case 1:
					if(saida.jogabilidade > 5 && saida.diversao > 3){
						//System.out.println("saida.jogabilidade > 6 && saida.diversao > 5");
						try {
							montador = montador.carregaTabelaTelas("");
							montador.salvaTelaArrayList(key, (int)saida.dificuldade); //Salva o nome da tela em seu ArrayList correspondente	
							montador.salvaTabelaTelas("");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					
					break;
				}					
			}
		}	
		System.out.println("Tabela montada.");
		//montador.imprimeTodos();
		montador.imprimeQuantidadeDeTelas();
		/*
		 
		System.out.println();
		//Scanner s = new Scanner(System.in);
		System.out.print("Escolha uma tela para imprimir informa��es: ");
		String key =  new Scanner(System.in).next();
		do{
			key = "tela" + key;
			System.out.println("\nTela " + key);
			Tela saida = example.get(key);
			System.out.println("Diversao: " + saida.diversao);
			System.out.println("Jogabilidade: " + saida.jogabilidade);
			System.out.println("Dificuldade: " + saida.dificuldade);
			System.out.print("\n\nEscolha uma tela para imprimir informa��es: ");
			key =  new Scanner(System.in).next();
		}while(key.compareTo("-1") != 0);
		
		*/
		
	}
	
	public static void main(String[] args){
		
		CalculaMediasDadosTelas executor = new CalculaMediasDadosTelas();
		System.out.println("Iniciando...");
		executor.setaInformacaoTela();
		System.out.println("\nFim.");


	}


}
