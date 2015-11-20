package br.ufv.dpi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import dk.itu.mario.MarioInterface.GamePlay;

public class DadosAvaliacaoTelas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static DadosAvaliacaoTelas dados;
	
	private GamePlay game_play;
	
	private String usuario, tela, idioma;
	
	//Variaveis do questionario final
	private int diversao, jogabilidade, dificuldade, num_mortes; 
	private String sugestao;

	private int motivoFechar = -1; //Motivo pelo qual o jogador parou de jogar. -1 é default
	private String adm; // Variavel usada para diferenciar Levi e eu dos demais usuários.
		
	public static DadosAvaliacaoTelas getInstancia(){
		if(dados == null)
			dados = new DadosAvaliacaoTelas();
		
		return dados;
	}	
	/**
	 * Método para guardar informações sobre a tela
	 * @param diversao
	 * @param jogabilidade
	 * @param dificuldade
	 * @param nota
	 * @param sugestao
	 */
	public void setQuestionarioFinal(int diversao, int jogabilidade, int dificuldade, String sugestao){
		this.diversao = diversao;
		this.jogabilidade = jogabilidade;
		this.dificuldade = dificuldade;
		this.sugestao = sugestao;
	}
	
	public void setGamePlay(){
		game_play = new GamePlay();
		game_play = game_play.read("player.txt");
	}
	
	public void salvaGamePlay(String fileName){
		if(game_play == null)
			System.out.println("O arquivo não está disponível");
		else
			game_play.write(fileName);
	}	
	
	public GamePlay getGamePlay(){
		return game_play;
	}
	
	public String getAdm() {
		return adm;
	}
	public void setAdm(String adm) {
		this.adm = adm;
	}
	
	public void setUsuario(String usuario){		
		this.usuario = usuario;
	}
	
	public String getUsuario(){
		return usuario;
	}
	
	public void setIdioma(String idioma){		
		this.idioma = idioma;
	}
	
	public String getIdioma(){
		return idioma;
	}
		
		
	public void setTela(String tela){
		this.tela = tela;
	}	
	
	public String getTela() {
		return tela;
	}
	
	public static DadosAvaliacaoTelas getDados() {
		return dados;
	}

	public static void setDados(DadosAvaliacaoTelas dados) {
		DadosAvaliacaoTelas.dados = dados;
	}
	
	public int getDiversao() {
		return diversao;
	}

	public void setDiversao(int diversao) {
		this.diversao = diversao;
	}

	public int getJogabilidade() {
		return jogabilidade;
	}

	public void setJogabilidade(int jogabilidade) {
		this.jogabilidade = jogabilidade;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}
	
	public int getNumMortes() {
		return num_mortes;
	}

	public void setNumMortes(int death_toll) {
		this.num_mortes = death_toll;
	}

	public String getSugestao() {
		return sugestao;
	}

	public void setSugestao(String sugestao) {
		this.sugestao = sugestao;
	}
	
	public void setMotivoFechar(int opcao_escolhida) {		
		motivoFechar = opcao_escolhida;		
	}
	public int getMotivoFechar() {		
		return motivoFechar;		
	}
	
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

	public DadosAvaliacaoTelas carregarArquivo() throws IOException, ClassNotFoundException{		
		
		//1 - Crie um objeto FileInputStream
		 FileInputStream fileStream = new FileInputStream("level_data.obj");
		 //2 - Crie um ObjectInputStream
		 ObjectInputStream os = new ObjectInputStream(fileStream);
		 Object obj = os.readObject();
		 
		 DadosAvaliacaoTelas salvaCarrega  = (DadosAvaliacaoTelas) obj;
		 System.out.println("Carregado com sucesso");
			 
		
		 os.close();
		 
		 return salvaCarrega;
	}
	
	public DadosAvaliacaoTelas carregarArquivo(String nome) throws IOException, ClassNotFoundException{		
		
		//1 - Crie um objeto FileInputStream
		 FileInputStream fileStream = new FileInputStream(nome);
		 //2 - Crie um ObjectInputStream
		 ObjectInputStream os = new ObjectInputStream(fileStream);
		 Object obj = os.readObject();
		 
		 DadosAvaliacaoTelas salvaCarrega  = (DadosAvaliacaoTelas) obj;
		 //System.out.println("Carregado com sucesso");
			 
		
		 os.close();
		 
		 return salvaCarrega;
	}
	 
	public void salvarArquivo() {		
		 
	   try { //operação de E/S pode lançar excessões.
	     FileOutputStream fs = new FileOutputStream("tela_data");//caso não encontre cria novo arquivo chamado tela.nvl (LeVeL)
	     ObjectOutputStream os = new ObjectOutputStream(fs); //fs encadeado ao fluxo de conexão
	     os.writeObject(this);
	     os.close();
	     System.out.println("Salvo com sucesso!!!");
	   }catch (Exception e) {
	     e.printStackTrace();
	   }
	 
	 }
	
	public static void enviarDadosServidor() throws UnknownHostException{
		
		
		try {
			//Socket socket = new Socket("200.235.131.136",15123); //Servidor Levi
			Socket socket = new Socket("127.0.0.1",15123); //Servidor Local
			File transferFile = new File ("tela_data"); 
			byte [] bytearray = new byte [(int)transferFile.length()];
			FileInputStream fin = new FileInputStream(transferFile); 
			BufferedInputStream bin = new BufferedInputStream(fin); 
			bin.read(bytearray,0,bytearray.length); 
			OutputStream os = socket.getOutputStream(); 
			//System.out.println("Sending Files..."); 
			os.write(bytearray,0,bytearray.length); 
			os.flush(); 
			socket.close(); 
			//System.out.println("File transfer complete");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("\n\nFalha ao tentar enviar dados! O Servidor pode estar offline! \n\n");
			e.printStackTrace();
		} 
		
	}
}
