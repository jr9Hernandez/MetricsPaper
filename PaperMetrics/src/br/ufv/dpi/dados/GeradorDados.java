package br.ufv.dpi.dados;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import br.ufv.dpi.DadosFormulario;


public class GeradorDados {

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
						"<tr><th>Usuario</th><th>Idade</th><th>Sexo</th><th>Escolaridade</th><th>Nacionalidade</th><th>Joga Jogos de Computador</th><th>Jogou Mario antes</th><th>" +
						"Participou experimento</th><th>Diversao</th><th>Frustacao</th><th>Desafiador</th><th>Dificuldade</th><th>" +
						"Desenvolvido por maquina</th><th>Nota</th><th>Jogaria Novamente</th><th>Sugestao</th><th>Fase</th><th>Equacao usada</th>" +
						"<th>Morreu em </th>" + 
						"<th>Justificativa Diversao</th><th>JustificativaDesenvolvidoPor</th>" +
						"<th>JustificativaDificuldade</th><th>JustificativaVisual</th>"+
						"<th>Sugestão Geral</th><th>QuadradoLatino:</th></tr>";
				
				escreveNoArquivo(arquivo, cabecalho);
				
				String linha = "";
				DadosFormulario dados = new DadosFormulario();
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
				
				escreveNoArquivo(arquivo, "</table></table>");
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
	
	public String geraLinha(DadosFormulario dados){
		String linha = "", fase = "";
		
		ArrayList<String> lista = dados.getListaTelasFase();
		
		if(lista != null)
		for(int i =0; i < lista.size(); i++)
			fase += lista.get(i) + " ";
		
		linha = "<tr><td>" + dados.getUser() + "</td><td>" +  dados.getIdade() + "</td><td>" + dados.getSexo() + "</td><td>" +  dados.getEscolaridade() + "</td><td>" +  
				dados.getNacionalidade() + "</td><td>" +  dados.getJogaJogos() + "</td><td>" + dados.getJogou_mario() + "</td><td>" +  dados.getParticipante() + "</td><td>" +  
				dados.getDiversao() + "</td><td>" +  dados.getFrustacao() + "</td><td>" +  dados.getDesafiador() + "</td><td>" + 
				dados.getDificuldade() + "</td><td>" + dados.getDesenvolvido() + "</td><td>" + dados.getNota() + "</td><td>" +  
				dados.getVisual() + "</td><td>" + dados.getJustificativaDiversao() + "</td><td>" + fase + "</td><td>" + dados.getEquacao() +
				 "</td><td>" + dados.getMorreu() + "</td><td>" + 
					dados.getJustificativaDiversao()+ "</td><td>" +  dados.getJustificativaDesenvolvidoPor() + "</td><td>" +
					dados.getJustificativaDificuldade() + "</td><td>" + dados.getJustificativaVisual() +
					"</td><td>" + dados.getSugestaoGeral() + "</td><td>" + dados.getQuadradoLatino() + "</td></tr>";
		
		
		
		return linha;
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
	
	/**
	 * Converte a opcao que é um inteiro em um nome na forma de uma String
	 * @param opcao opção escolhida
	 * @return nome de acordo com a opção
	 */
	public String nomeParaArquivo(int opcao){
		
		switch (opcao) {
		case 0:
			return "arquivo_dados_jogos";			
		case 1:
			return "separado_por_sexo";
		case 2:
			return "separado_por_escolaridade";
		case 3:
			return "separado_por_nacionalidade";
		case 4:
			return "separado_por_ja_jogaram_antes";
		case 5:
			return "separado_por_participante";
		default:
			return "null";
		}	
		
	}
	
	public void main(String[] args) throws ClassNotFoundException, IOException{
		
		DadosFormulario dados = new DadosFormulario();
		dados = dados.carregarArquivo();
		ArrayList<String> lista = dados.getListaTelasFase();
		
		
		System.out.println("Dados lidos do arquivo: ");
		System.out.println( dados.getIdade() + "\n" + dados.getSexo() + "\n" +  dados.getEscolaridade() + "\n" +  
				dados.getNacionalidade() + "\n" +  dados.getJogou_mario() + "\n" +  dados.getParticipante() + "\n" +  
				dados.getDiversao() + "\n" +  dados.getFrustacao() + "\n" +  dados.getDesafiador() + "\n" +  dados.getDificuldade() + "\n" +  
				dados.getDesenvolvido() + "\n" + dados.getNota() + "\n" +  dados.getVisual() + "\n" +  dados.getJustificativaDiversao());
		System.out.print("FASE: ");
		for(int i =0; i < lista.size(); i++)
			System.out.print(lista.get(i)+ " ");
		System.out.println();
		
	}
}
