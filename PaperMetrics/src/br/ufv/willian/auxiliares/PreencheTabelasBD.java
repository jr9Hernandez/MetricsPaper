package br.ufv.willian.auxiliares;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreencheTabelasBD {
	
	private ArrayList<String> lista;
	public String caminho = "TodasTelas/InfoTelas/";
	
	public void lerArquivosInfo(){
		lista = new ArrayList<String>();		
		File dir = new File(caminho);  //Pasta os arquivos de informacao das telas
		  
		String[] arquivos = dir.list();  //Array para guardar o nome de todas as fases
		if (arquivos == null) {   
			System.out.println("Diretorio vazio");
		} else { 
		    for (int i=0; i<arquivos.length; i++) { 
		        lista.add(arquivos[i]);
		    }  
		}
	}
	
	public int getNumeroTela(String nomeInfo){		
		String numTela = "";
		char[] nomeArray = nomeInfo.toCharArray(); 
		for(int i = 4; i < nomeArray.length; i++)
			numTela += nomeArray[i];
		
		return Integer.parseInt(numTela);
	}	
		
	public static void main (String [] args){
		PreencheTabelasBD preencheTbTelas = new PreencheTabelasBD();
		ConexaoMySQL conexao = ConexaoMySQL.getInstance();
		InsereDadosBanco insere = new InsereDadosBanco();
		
		preencheTbTelas.lerArquivosInfo();
		int numeroTela;
		InformacoesTelas info_aux;

		System.out.println("Iniciando...");
		for (int y= 0; y < preencheTbTelas.lista.size(); y++){	
		//for (int y=0; y < 10; y++){
			info_aux = new InformacoesTelas();
			numeroTela = preencheTbTelas.getNumeroTela(preencheTbTelas.lista.get(y));
			try {
				info_aux = info_aux.carregaInfoTela(preencheTbTelas.caminho + preencheTbTelas.lista.get(y));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			info_aux.contaTiposInimigos();
			try{
				insere.inserirTela(conexao.getConexaoMySQL(), numeroTela, "tela"+ numeroTela, info_aux.getQuantMoedas(),info_aux.getQuantInimigo());
			}catch(Exception e){
				System.out.println("\n*********Falha ao tentar inserir tela " + numeroTela + "*********\n");
			}				
		}
		System.out.println("Finalizado.");
	 }

}
