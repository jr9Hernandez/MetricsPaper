package br.ufv.willian.auxiliares;

import java.io.IOException;


public class ClasseParaTestes {
	
	public void imprimeTodas() throws IOException, ClassNotFoundException{
		//InformacoesTelas info2 = info.carregaInfoTela("infoTela2");
		InformacoesTelas info = new InformacoesTelas();
				info = info.carregaInfoTela("infoTela2");
				info.contaTiposInimigos();
				System.out.println("Tela "+ info.getNomeTela() +
						"\nQuantidade:\n\t\tBuracos: " + info.getQuantBuracos() + "\n\t\tCanhoes: " + info.getQuantCanhoes() +
						"\n\t\tInimigos nao alados: " + info.getQuantInimigo() + "\n\t\tInimigos alados: " + info.getQuantAlado());
				
				MedidorDeDificuldade medidor = new MedidorDeDificuldade();
				System.out.println("Dificuldade calculada: " + medidor.calculaDificuldade(info));
				info.listaInimigos();
				
				//info.imprimeGlobais();
	}
	
	public void testaMedidor() throws IOException, ClassNotFoundException{
		MedidorDeDificuldade medidor = new MedidorDeDificuldade();
		medidor.montaTabelaDificuldade();
		medidor.imprimeTodos();
		
	}
	
	public void testaUmaTela() throws IOException, ClassNotFoundException{
		imprimeTodas();
		MedidorDeDificuldade medidor = new MedidorDeDificuldade();
		medidor.montaTabelaDificuldade(1);
		medidor.imprimeTodos();
		
	}
	
	public void testaSequenciaPossivelFase() throws IOException, ClassNotFoundException{
		MedidorDeDificuldade medidor = new MedidorDeDificuldade();
		medidor.montaTabelaDificuldade();
		System.out.println("Possivel Fase: ");
		for (int i=0; i< 10; i++){
			System.out.print(medidor.retornaTelaDoBalde(i) + " - ");
		}
	}
	
	public void testaSalvarMedidor() throws IOException, ClassNotFoundException{
		
		MedidorDeDificuldade medidor = new MedidorDeDificuldade();
		System.out.println("Executando  'testaSalvarMedidor'");
		medidor.montaTabelaDificuldade();
		medidor.salvaTabelaTelas("");
		System.out.println("Finalizado...");
		
	}
	
	public void testaCarregarMedidorSalvo() throws IOException, ClassNotFoundException{
		
		MedidorDeDificuldade medidor = new MedidorDeDificuldade();
		System.out.println("Executando  'testaCarregarMedidorSalvo'");
		medidor = medidor.carregaTabelaTelas("");
		medidor.imprimeTodos();
		System.out.println("Finalizado...");
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{	
		
		ClasseParaTestes teste = new ClasseParaTestes();
		
		teste.testaMedidor();
		//teste.testaUmaTela();
		//teste.testaSequenciaPossivelFase();
		//teste.testaSalvarMedidor();
		//teste.testaCarregarMedidorSalvo();
		
	}
}
