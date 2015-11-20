package br.ufv.willian.auxiliares;

import java.io.IOException;

/**
 * Classe para gerar os arquivos em defalt necessários para o funcionamento do sistema
 * @author WILLIAN
 *
 */
public class GeradorArquivosAuxiliares {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		VetorSequencia vetor = new VetorSequencia();
		ClassUser usuario = new ClassUser();
		
		vetor.salvaVetorSequencia("sequencia.txt");
		usuario.salvaUser();
		
		System.out.println("Pronto");
		
	}

}
