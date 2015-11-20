package br.ufv.dpi;

import java.io.IOException;

import br.ufv.willian.auxiliares.VetorTelasParaTeste;

public class InicialTesteTelas {
	
	public static void main(String [] args) throws ClassNotFoundException, IOException{
		
		VetorTelasParaTeste vetor = new VetorTelasParaTeste();
		vetor = vetor.carregaVetorTelas("vetorTelas");
		if(vetor.size() == 0)
			FormularioTermosTelas.main(null);
		else
			FormularioInstrucoes.main(null);
		
	}

}
