package br.ufv.willian.auxiliares;

import java.io.IOException;

public class ImprimeInformacoesTela {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		
		InformacoesTelas infoTela = new InformacoesTelas();
		infoTela = infoTela.carregaInfoTela("infoTela");
		infoTela.contaTiposInimigos();
		
		System.out.println("Informações da Tela lida:\n" +
					"\tplatform: " + infoTela.getTagPlatform() + "\n" +
					"\tstraigth: " + infoTela.getTagStraigth() + "\n" +
					"\thill_straigth: " + infoTela.getTagHillStraigth() + "\n" +
					"\ttubes: " + infoTela.getTagTubes() + "\n" +
					"\tjump: " + infoTela.getTagJump() + "\n" +
					"\tcannos: " + infoTela.getTagCannos() + "\n" +
					"\tnumero de moedas: " + infoTela.getQuantMoedas() + "\n" +
					"\tnumero de buracos: " + infoTela.getQuantBuracos() + "\n" +
					"\tnumero de canhoes: " + infoTela.getQuantCanhoes() + "\n" +
					"\tnumero de inimigos: " + infoTela.getQuantInimigo() + "\n" +
						"\t\tnumero de alados: " + infoTela.getQuantAlado() + "\n" + 
						"\t\tnumero de spikes: " + infoTela.getQuantArmoredTurtle() + "\n" + 
						"\t\tnumero de spikes alados:" + infoTela.getQuantArmoredTurtleAlado() + "\n"						
				);
		
		infoTela.listaInimigos();
		
		
	}

}
