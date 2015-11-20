package br.ufv.willian.auxiliares;

import java.io.Serializable;
import java.util.ArrayList;



public class VariaveisGlobais {
	
	public static class acoes implements Serializable{
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//public boolean action[] = new boolean[Environment.numberOfButtons]; //Acoes tomadas
		public int posXmario; // Posicao no eixo X do mapa em que o Mario se encontra
		public int posYmario; //Posicao no eixo Y do mapa em que o Mario se encontra
	}
			
	public static boolean aux = false; //
	//public static Level level;
	public static acoes a = new acoes();
	public static ArrayList<acoes> lista = new ArrayList<acoes>(); 
	public static int quant_buracos;
	public static int quant_canhoes;
	public static final int MAX_CANNONS_LEVEL = 1;
	public static final int MAX_POWER_UP = 1;
	
	//Variaveis que marca tipos de elementos presentes nas telas
	public static boolean tag_platform;
	public static boolean tag_straigth;
	public static boolean tag_hill_straigth;
	public static boolean tag_tubes;
	public static boolean tag_jump;
	public static boolean tag_cannos;
	
	//Variaveis utilizadas para a escolha do gerador a ser utilizado
	public final static int quant_geradores = 6;
	public static int cont = 0; 
	//public static int[] gerador = new int [quant_geradores];
	public static int[] gerador = {2, 1, 4, 5};
	public static int nada = 0;
	public static int cont_jogadas = 0;
	//Variaveis para escolha dos geradores em pares
	public static int fase = 0; // 0, 1, 2 - nenhuma fase, fase 1 ou fase 2
	public static ClassePartidas partidas = null;
	
	
	/**
	 * Quando esta variável é true, é gerado o fase para teste
	 */
	public static boolean testando = false;
	public static int morreuEm = -1; //Coordenada x onde o Mario morreu
	public static int num_mortes = 0; //Coordenada x onde o Mario morreu
	
	
	/*
	public static boolean compara(acoes vet_acoes){
		
		if(lista.size() > 0){
			acoes extra = lista.get(lista.size()-1);
			for(int i=0; i < Environment.numberOfButtons; i++)
				if(vet_acoes.action[i] != extra.action[i]) return false;
			if(vet_acoes.posXmario != extra.posXmario) return false;
			
			return true;
		}
		return false;
	}
	*/
}
