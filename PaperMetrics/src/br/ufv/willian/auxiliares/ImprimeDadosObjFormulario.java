package br.ufv.willian.auxiliares;

import java.io.IOException;
import java.util.ArrayList;

import dk.itu.mario.MarioInterface.GamePlay;

import br.ufv.dpi.DadosAvaliacaoTelas;
import br.ufv.dpi.DadosFormulario;


public class ImprimeDadosObjFormulario {
	
	class GamePlayAuxiliar{
		GamePlay game_play;
		
		public int getNumInimigosMortosGamePlay(){
			
			return (game_play.RedTurtlesKilled + game_play.GreenTurtlesKilled + game_play.ArmoredTurtlesKilled +
					game_play.GoombasKilled + game_play.CannonBallKilled + game_play.JumpFlowersKilled + game_play.ChompFlowersKilled);
		}
		
		public int getNumMoedasColetadasGamePlay(){
			
			return (game_play.coinBlocksDestroyed + game_play.coinsCollected);
		}
		
		public int getNumMortesGamePlay(){
			
			return (game_play.timesOfDeathByRedTurtle + game_play.timesOfDeathByGoomba + game_play.timesOfDeathByGreenTurtle +
						game_play.timesOfDeathByArmoredTurtle + game_play.timesOfDeathByJumpFlower + 
						game_play.timesOfDeathByCannonBall + game_play.timesOfDeathByChompFlower);
		}
		
	}
	
	public void imprimeDadosFormulario() throws ClassNotFoundException, IOException{
		DadosFormulario dados = new DadosFormulario();
		dados = dados.carregarArquivo("level_data.obj");
		ArrayList<String> lista = dados.getListaTelasFase();
		
		
		System.out.println("Dados lidos do arquivo: ");
		System.out.println( "Usuario: " + dados.getUser() + "\nIdade: " + dados.getIdade() + "\n" + "Sexo: " + dados.getSexo() + "\n" + "Escolaridade: " + dados.getEscolaridade() + "\n" +  
				"Nacionalidade: " + dados.getNacionalidade() + "\n" +  "JogaJogos: " + dados.getJogaJogos() +"\nJogou_mario: " + dados.getJogou_mario() + "\n" +  "Participante: " + dados.getParticipante() + "\n" +  
				"Diversao: " + dados.getDiversao() + "\n" +  "Frustacao: " + dados.getFrustacao() + "\n" +  "Desafiador: " + dados.getDesafiador() + "\n" +  "dificuldade: " + dados.getDificuldade() + "\n" +  
				"Visual: " + dados.getVisual() + "\n" + "Nota: " + dados.getNota()  + "\n" +  "Desenvolvido por: " + dados.getDesenvolvido() + "\n" + 
				"Justificativa Diversao: " + dados.getJustificativaDiversao()+ "\n" +  "JustificativaDesenvolvidoPor: " + dados.getJustificativaDesenvolvidoPor() + "\n" +
				"JustificativaDificuldade: " + dados.getJustificativaDificuldade() + "\n" +  "JustificativaVisual: " + dados.getJustificativaVisual() +
				"\nSugestão Geral: " + dados.getSugestaoGeral() + "\nmorreu em: " + dados.getMorreu() + "\nQuadradoLatino: " + dados.getQuadradoLatino()
				 );		
		if(lista != null){
			System.out.print("FASE: ");
			for(int i =0; i < lista.size(); i++)
				System.out.print(lista.get(i)+ " ");
		}
		
		System.out.println("\n" + dados.getEquacao());
		
		if(dados.getGamePlay() != null){
			GamePlayAuxiliar gpa = new GamePlayAuxiliar();
			gpa.game_play = dados.getGamePlay();
			System.out.println("\nMoedas Coletadas: " + gpa.getNumMoedasColetadasGamePlay() + "\n" +  "Mortes do GP: " + gpa.getNumMortesGamePlay() + "\n" 
			+ "Inimigos mortos: " +  gpa.getNumInimigosMortosGamePlay() + "\n");
		}
	}
	
	public void imprimeDadosAvaliacaoTelas() throws ClassNotFoundException, IOException{
		DadosAvaliacaoTelas dados = new DadosAvaliacaoTelas();
		dados = dados.carregarArquivo("tela_data");
		
		System.out.println("Dados lidos do arquivo: ");
		System.out.println("Usuario: " + dados.getUsuario() + "\n" +  "Tela: " + dados.getTela()+ "\n" + 
				"Moedas Coletadas: " + dados.getNumMoedasColetadasGamePlay() + "\n" +  "Numero de Mortes: " + dados.getNumMortes() + "\n" +  
				"Mortes do GP: " + dados.getNumMortesGamePlay() + "\n" + "Inimigos mortos: " +  dados.getNumInimigosMortosGamePlay() + "\n" +  
				"Dificuldade: " + dados.getDificuldade() + "\n" +  "Diversao: " + dados.getDiversao() + "\n" +  
				"Jogabilidade: " + dados.getJogabilidade() + "\n" +  "Sugestão: " + dados.getSugestao() + "\n" );
	}
	
	public void imprimeComparacoesFases() throws ClassNotFoundException, IOException{
		DadosFormulario dados = new DadosFormulario();
		dados = dados.carregarArquivo("ArquivosRecebidos/arq828029166-6-0-2015-19h49m55s76ms");//
		ArrayList<String> lista = dados.getListaTelasFase();
		System.out.println("Dados lidos do arquivo: ");
		System.out.println( dados.getPartida() + "\n" +
				dados.getDiversao() + "\n" +  dados.getFrustacao() + "\n" +  dados.getDesafiador() + "\n" +  dados.getDificuldade() + "\n" +  
				dados.getDesenvolvido() + "\n" + dados.getVisual() + "\n" +  dados.getJustificativaDiversao());
		if(lista != null){
			System.out.print("FASE: ");
			for(int i =0; i < lista.size(); i++)
				System.out.print(lista.get(i)+ " ");
		}
		//System.out.println("\n" + dados.getEquacao());
	}	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		ImprimeDadosObjFormulario impressao = new ImprimeDadosObjFormulario();
		//impressao.imprimeDadosAvaliacaoTelas();
		//impressao.imprimeComparacoesFases();
		impressao.imprimeDadosFormulario();
		
	}

}
