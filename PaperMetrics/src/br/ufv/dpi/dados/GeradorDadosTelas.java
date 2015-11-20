package br.ufv.dpi.dados;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import br.ufv.dpi.DadosAvaliacaoTelas;
import br.ufv.dpi.DadosFormulario;


public class GeradorDadosTelas {

	private ArrayList<String> lista_arquivos = new ArrayList<String>();
	private File dir;
	private Map mapa_opcaoe_usuario = new HashMap<String, ArrayList>();
	
	public ArrayList<String> retornaListaArquivos(String diretorio){
		
		dir = new File(diretorio + "/");	
		  
		String[] children = dir.list();  
		if (children == null) {  
		    // Either dir does not exist or is not a directory  
			System.out.println("Diretorio vazio");
		} else {  
			System.out.println("Tamanho: " + children.length);
		    for (int i=0; i<children.length; i++) {  
		        // Get filename of file or directory  
		        //String filename = children[i];
		        lista_arquivos.add(children[i]);
		        //System.out.println(children[i]);
		    }  
		}
		
		return lista_arquivos;
	}
	
	/**
	 * Testa de o diretorio já foi selecionado
	 * @return
	 * True caso tenha sido escolhido um diretório, e False, caso nenhum diretório tenha sido selecionado.
	 */
	public boolean getDiretorio(){
		if(dir == null) return false;
		else return true;
	}
	
	public void geraArquivo(int opcao_usuario, int opcao_equacao){
		
		if(opcao_usuario == 0){
			File arquivo = criaArquivo(nomeParaArquivo(opcao_usuario));
			escreveNoArquivo(arquivo, "<html><table>");
			if(opcao_equacao == 0){	
				
				String cabecalho = 
						"<tr><th>Usuario</th><th>Tela</th><th>Diversao</th><th>Jogabilidade</th><th>Dificuldade</th><th>Numero de Mortes</th><th>" +
						"Sugestao</th><th>Motivo de Fechar</th><th>Administrador</td><td>Moedas Coletadas</td><td>Inimigos Mortos</td><td>Mortes do GamePlay</th></tr>";
				
				escreveNoArquivo(arquivo, cabecalho);
				
				String linha = "";
				DadosAvaliacaoTelas dados = new DadosAvaliacaoTelas();
				for(int i=0; i < lista_arquivos.size(); i++){
					
					try {
						System.out.print("Tentando escrever o arquivo " + i);
						dados = dados.carregarArquivo(dir + "/" +lista_arquivos.get(i));
						linha = geraLinha(dados);
						escreveNoArquivo(arquivo, linha);
						System.out.println("... Arquivo escrito com sucesso!!!");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				escreveNoArquivo(arquivo, "</table></html>");
			}else{
				JOptionPane.showMessageDialog(null, "Opcao ainda nao implementada");	
				
				return;
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Opcao ainda nao implementada");
			
			if(mapa_opcaoe_usuario.get(opcao_usuario) == null){
				
			}
			
			return;
			/*
			if(opcao_equacao == 0){
				
			}else{
				
			}
			*/
		}
		
		JOptionPane.showMessageDialog(null, "Arquivos gerados com sucesso");
	}
	
	public void carregaMapaDeListaArquivos(int opcao){
		
		
		
	}
	
	public File criaArquivo(String nome){
		
		//File arquivo = new File( "C:/Users/WILLIAN/mario/fase.txt" ); //Para salvar em um diretório expecífico
		File arquivo = new File(nome + ".html"); //Para salvar na pasta do projeto
		//Verifivar se o arquivo ou diretório existe
		boolean existe = arquivo.exists();
		
		if(!existe){
			//cria um arquivo (vazio)
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			//cria um diretório
			//arquivo.mkdir();
		}
		
		//Caso o arquivo já exista, este trecho irá reseta-lo, ou seja torna-lo vazio
		try {
			FileWriter str = new FileWriter(arquivo);
			str.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return arquivo;
	}
	
	
	public void escreveNoArquivo(File arquivo, String linha){		
			
		FileWriter fw;
		try {
			fw = new FileWriter(arquivo, true);
			fw.write(linha);		
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String geraLinha(DadosAvaliacaoTelas dados){
		
		String linha = "<tr><td>" +  dados.getUsuario() + "</td><td>" + dados.getTela() + "</td><td>" +  dados.getDiversao() + "</td><td>" +  
				dados.getJogabilidade() + "</td><td>" + dados.getDificuldade() + "</td><td>" + dados.getNumMortes() + "</td><td>" + dados.getSugestao() + 
				"</td><td>" + dados.getMotivoFechar() + "</td><td>" + dados.getAdm() + "</td><td>" + dados.getNumMoedasColetadasGamePlay() + "</td><td>" + dados.getNumInimigosMortosGamePlay() +
				 "</td><td>" + dados.getNumMortesGamePlay() + "</td><tr>";
		
		
		
		return linha;
	}
	
	/**
	 * Converte a opcao que é um inteiro em um nome na forma de uma String
	 * @param opcao opção escolhida
	 * @return nome de acordo com a opção
	 */
	public String nomeParaArquivo(int opcao){
		
		switch (opcao) {
		case 0:
			return "DadosRecebidosTelas";			
		case 1:
			return "separado_por_sexo";
		case 2:
			return "separado_por_escolaridadel";
		case 3:
			return "separado_por_nacionalidadel";
		case 4:
			return "separado_por_ja_jogaram_antes";
		case 5:
			return "separado_por_participante";
		default:
			return "null";
		}	
		
	}
	
	public void main(String[] args) throws ClassNotFoundException, IOException{
		
		DadosAvaliacaoTelas dados = new DadosAvaliacaoTelas();
		dados = dados.carregarArquivo();		
		
		System.out.println("Dados lidos do arquivo: ");
		System.out.println("<tr><td>" +  dados.getUsuario() + "</td><td>" + dados.getTela() + "</td><td>" +  dados.getDiversao() + "</td><td>" +  
				dados.getJogabilidade() + "</td><td>" + dados.getDificuldade() + "</td><td>" + dados.getNumMortes() + "</td><td>" + dados.getSugestao() + 
				"</td><td>" + dados.getMotivoFechar() + "</td><td>" + dados.getAdm() + "</td><td>" + dados.getNumMoedasColetadasGamePlay() + "</td><td>" + dados.getNumInimigosMortosGamePlay() +
				 "</td><td>" + dados.getNumMortesGamePlay() + "</td><tr>");
		
	}
}

