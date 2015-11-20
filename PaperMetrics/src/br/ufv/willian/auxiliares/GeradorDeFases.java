package br.ufv.willian.auxiliares;

import java.io.IOException;
import java.util.Random;

import dk.itu.mario.level.Level;
import br.ufv.willian.LevelGenerator;

//Classe para funcionar como o gerador de fases

public class GeradorDeFases {	
	
	public void geraUmaTela(String args[]){
		Level level; //Level para ser criado
		int levelLength = Integer.parseInt(args[0]); //Largura
		int levelSeed = Integer.parseInt(args[0]); //Semente para o Aleatorio
		//int levelSeed = (int) (Math.random () * Integer.MAX_VALUE);
		int levelDifficulty = Integer.parseInt(args[0]); //Dificuldade 
		int levelType = Integer.parseInt(args[0]); //tipo
		level = LevelGenerator.createLevel(levelLength, 15, levelSeed, levelDifficulty, levelType);
	}
	
	public void geraNTelas(int quantidadeTelas, int largura, int tipo){
					
    	int seed;
    	int dificuldade;
    	Level level;
    	Random r = new Random();
    	    	
    	for(int i=0; i<quantidadeTelas; i++){
    		System.out.println("\n***************** GERANDO TELA " + i + "*****************");
    		
    		seed = (int) (Math.random () * Integer.MAX_VALUE);
        	//dificuldade = (int) (Math.random () * 8);
    		dificuldade = r.nextInt(8);
        	System.out.println("\nDificuldade: " + dificuldade + "\nSemente: " + seed);
        	level = LevelGenerator.createLevel(largura, 15, seed, dificuldade, tipo);
	        InformacoesTelas info = new InformacoesTelas();
	        CopiaArquivos copiador = new CopiaArquivos();
	        try {
	        	
				info = info.carregaInfoTela("infoTela");
				info.setOutrasVariaveis(dificuldade, seed); // Salva outras informacoes da Tela
				//System.out.print("Tentando Imprimir informações do Mario pelo InformacoesTela");
				//info2.imprimeGlobais();
				info.salvaInfoTela("infoTela", info);
				
				//copiador.copy("" + i); //Diretorio padrão
				copiador.copy("" + i, "telasExtras/ComPlataforma");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //SaidaInformacoes imprime = new SaidaInformacoes();
	        //imprime.imprimeGlobais();
	        System.out.println("\n***************** FIM DE GERAÇÃO " + i + "*****************");
        }	
			
	}

	public static void main(String args[]){
		GeradorDeFases gerador = new GeradorDeFases();
		System.out.println("Iniciando Sistema\n\n");
		
		//gerador.geraUmaTela(args);
		gerador.geraNTelas(5000, 89, 0);
		
		System.out.println("\n\nFim da execução");
	}

}
