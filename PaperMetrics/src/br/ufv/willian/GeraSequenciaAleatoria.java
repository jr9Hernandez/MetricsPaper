package br.ufv.willian;

import java.util.ArrayList;
import java.util.Random;

public class GeraSequenciaAleatoria {
	/*
	 * quantidade de numeros
	 */
	public static int[] geraSequencia(int quantidade){
		
		Random rand = new Random();
		int sequencia[] = new int[quantidade];
		ArrayList<Integer> numeros= new ArrayList<Integer>();
		int tamanho = quantidade;
		
		for(int i = 0; i< quantidade; i++)
			numeros.add(i);
		
		int indice_aux, num_aux;
		for(int i=0; i< quantidade; i++){
			indice_aux = rand.nextInt(tamanho);
			num_aux = numeros.get(indice_aux);
			sequencia[i] = num_aux;
			numeros.remove(indice_aux);
			tamanho--;
		}		
		return sequencia;
	}
	
	
	public static void main(String[] arg){
		GeraSequenciaAleatoria gera = new GeraSequenciaAleatoria();
		final int quant = 7;
		int sequencia[] = new int[quant];
		for(int y = 0; y < 100; y++){
			sequencia = gera.geraSequencia(quant);
		
			System.out.print("Sequencia " + y + ": ");
			for(int i =0; i< quant; i++)
				System.out.print(sequencia[i] + " ");
			System.out.println();
		}
		
	}
	
	
}
