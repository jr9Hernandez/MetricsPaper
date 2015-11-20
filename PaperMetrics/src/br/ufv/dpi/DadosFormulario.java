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
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import dk.itu.mario.MarioInterface.GamePlay;

public class DadosFormulario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static DadosFormulario dados;
	
	//private String nome;
	private String idade;
	private int sexo;
	private int escolaridade;
	private int nacionalidade;
	private int joga_jogos;
	private int jogou_mario;	
	private int participante; //Participa ou não dos experimentos
	private String idioma;
	private String partida;
	private String fase_jogada;
	
	//Variaveis do questionario final
	private int diversao, frustacao, desafiador, dificuldade, desenvolvido, visual, nota, x_que_morreu; 
	private String justificativaDiversao, justificativaDificuldade, justificativaVisual, justificativaDesenvolvidoPor;
	
	private String equacao, fase;
	private ArrayList<String> listaTelasFase;
		
	private int quadradoLatino;

	private String user;

	private String sugestaoGeral;
	
	private GamePlay game_play;
	
	
	public static DadosFormulario getInstancia(){
		if(dados == null)
			dados = new DadosFormulario();
		
		return dados;
	}
	
	/**
	 * Método para guardar informações do questionário final
	 * @param diversao
	 * @param frustacao
	 * @param desafiador
	 * @param dificuldade
	 * @param desenvolvido
	 * @param visual
	 * @param nota
	 * @param justificativaDiversao
	 * @param justificativaDificuldade, 
	 * @param justificativaVisual
	 * @param justificativaDesenvolvidoPor
	 */
	public void setQuestionarioFinal(int diversao, int frustacao, int desafiador, int dificuldade, 
			int desenvolvido, int visual, int nota, String justificativaDiversao, String justificativaDificuldade, 
			String justificativaVisual, String justificativaDesenvolvidoPor){
		this.diversao = diversao;
		this.frustacao = frustacao;
		this.desafiador = desafiador;
		this.dificuldade = dificuldade;
		this.desenvolvido = desenvolvido;
		this.visual = visual;
		this.nota = nota; 
		this.justificativaDiversao = justificativaDiversao;
		this.justificativaDificuldade = justificativaDificuldade;
		this.justificativaVisual = justificativaVisual;
		this.justificativaDesenvolvidoPor = justificativaDesenvolvidoPor;
	}
	
	/**
	 * Método para guardar as informações do formulário inicial
	 * @param idade
	 * @param sexo
	 * @param escolaridade
	 * @param nacionalidade
	 * @param joga jogos de computador
	 * @param jogou_mario
	 * @param participante
	 */
	public void salvaDados(String idade, int sexo, int escolaridade, int nacionalidade, int joga, int jogou_mario, int participante){
		//this.nome = nome;
		this.idade = idade;
		this.sexo = sexo;
		this.escolaridade = escolaridade;
		this.nacionalidade = nacionalidade;
		this.joga_jogos = joga;
		this.jogou_mario = jogou_mario;
		this.participante = participante;
	}	
	
	public void setListaTelasFase(ArrayList<String> lista){
		listaTelasFase = lista;
	}
	
	/**
	 * 	Método para retorna um ArrayList com o nome das Telas usadas na fase
	 * @return listaTelasFase
	 */
	public ArrayList<String> getListaTelasFase(){
		return listaTelasFase;
	}
	
	/**
	 * 
	 * @param linha : corresponde a linha do quadrado latino que está sendo utilizada
	 * o quadrado é:
	 * //(1)1 2 4 3
	 * //(2)2 3 1 4
	 * //(3)3 4 2 1
	 * //(4)4 1 3 2
	 */
	public void setQuadradoLatino(int linha){		
		//(1)1 2 4 3
		//(2)2 3 1 4
		//(3)3 4 2 1
		//(4)4 1 3 2
		
		this.quadradoLatino = linha;
		
	}
	
	public void setGamePlay(){		
		game_play = GamePlay.read("player.txt");
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
	
	public int getQuadradoLatino(){		
		return quadradoLatino;
	}
	
	public void setIdioma(String idioma){		
		this.idioma = idioma;
	}
	
	public String getPartida(){
		return partida;
	}
	
	public void setPartida(int partida){		
		this.partida = "" + partida;
	}
	
	public String getFaseJogada(){
		return fase_jogada;
	}
	
	public void setFaseJogada(String fase){		
		this.fase_jogada = fase;
	}
	
	public String getIdioma(){
		return idioma;
	}
		
	public int getMorreu(){
		return x_que_morreu;
	}
	
	public void setMorreu(int x){
		x_que_morreu = x;		
	}
	
	public void setEquacao(String equacao){
		this.equacao = equacao;
	}
	
	public String getEquacao(){
		return equacao;
	}
	
	public void setFase(String telasFase){
		this.fase = telasFase;
	}
	
	
	
	public static DadosFormulario getDados() {
		return dados;
	}

	public static void setDados(DadosFormulario dados) {
		DadosFormulario.dados = dados;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	public int getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(int escolaridade) {
		this.escolaridade = escolaridade;
	}

	public int getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(int nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public int getJogou_mario() {
		return jogou_mario;
	}

	public void setJogou_mario(int jogou_mario) {
		this.jogou_mario = jogou_mario;
	}
	
	public int getJogaJogos() {
		return joga_jogos;
	}

	public void setJogaJogos(int joga) {
		this.joga_jogos = joga;
	}

	public int getParticipante() {
		return participante;
	}

	public void setParticipante(int participante) {
		this.participante = participante;
	}

	public int getDiversao() {
		return diversao;
	}

	public void setDiversao(int diversao) {
		this.diversao = diversao;
	}

	public int getFrustacao() {
		return frustacao;
	}

	public void setFrustacao(int frustacao) {
		this.frustacao = frustacao;
	}

	public int getDesafiador() {
		return desafiador;
	}

	public void setDesafiador(int desafiador) {
		this.desafiador = desafiador;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}

	public int getDesenvolvido() {
		return desenvolvido;
	}

	public void setDesenvolvido(int desenvolvido) {
		this.desenvolvido = desenvolvido;
	}

	public int getVisual() {
		return visual;
	}

	public void setVisual(int visual) {
		this.visual = visual;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getJustificativaDiversao() {
		return justificativaDiversao;
	}

	public void setJustificativaDiversao(String sugestao) {
		this.justificativaDiversao = sugestao;
	}
	
	public String getJustificativaDificuldade() {
			return justificativaDificuldade;
	}

	public void setJustificativaDificuldade(String sugestao) {
		this.justificativaDificuldade = sugestao;
	}
	
	public String getJustificativaVisual() {
		return justificativaVisual;
	}

	public void setJustificativaVisual(String sugestao) {
		this.justificativaVisual = sugestao;
	}
	public String getJustificativaDesenvolvidoPor() {
		return justificativaDesenvolvidoPor;
	}

	public void setJustificativaDesenvolvidoPor(String sugestao) {
		this.justificativaDesenvolvidoPor = sugestao;
	}	
	public String getFase() {
		return fase;
	}
	
	public void setUser(String user) {
		// TODO Auto-generated method stub
		this.user = user;		
	}
	
	public String getUser(){
		return user;
	}
	
	public void setSugestaoGeral(String text) {
		// TODO Auto-generated method stub
		this.sugestaoGeral = text;
	}	
	public String getSugestaoGeral(){
		return sugestaoGeral;
	}

	public DadosFormulario carregarArquivo() throws IOException, ClassNotFoundException{		
		
		//1 - Crie um objeto FileInputStream
		 FileInputStream fileStream = new FileInputStream("level_data.obj");
		 //2 - Crie um ObjectInputStream
		 ObjectInputStream os = new ObjectInputStream(fileStream);
		 Object obj = os.readObject();
		 
		 DadosFormulario salvaCarrega  = (DadosFormulario) obj;
		 System.out.println("Carregado com sucesso");
			 
		
		 os.close();
		 
		 return salvaCarrega;
	}
	
	public DadosFormulario carregarArquivo(String nome) throws IOException, ClassNotFoundException{		
		
		//1 - Crie um objeto FileInputStream
		 FileInputStream fileStream = new FileInputStream(nome);
		 //2 - Crie um ObjectInputStream
		 ObjectInputStream os = new ObjectInputStream(fileStream);
		 Object obj = os.readObject();
		 
		 DadosFormulario salvaCarrega  = (DadosFormulario) obj;
		 //System.out.println("Carregado com sucesso");
			 
		
		 os.close();
		 
		 return salvaCarrega;
	}
	 
	public void salvarArquivo() {		
		 
	   try { //operação de E/S pode lançar excessões.
	     FileOutputStream fs = new FileOutputStream("level_data.obj");//caso não encontre cria novo arquivo chamado tela.nvl (LeVeL)
	     ObjectOutputStream os = new ObjectOutputStream(fs); //fs encadeado ao fluxo de conexão
	     os.writeObject(this);
	     os.close();
	     //System.out.println("Salvo com sucesso!!!");
	   }catch (Exception e) {
	     e.printStackTrace();
	   }
	 
	 }
	
	public static void fazCopia() throws IOException, ClassNotFoundException{ 
    	
    	Calendar calendar = Calendar.getInstance();
    	int ano = calendar.get(Calendar.YEAR);
    	int mes = calendar.get(Calendar.MONTH);
    	int dia  = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);     
		int minute = calendar.get(Calendar.MINUTE);     
		int second = calendar.get(Calendar.SECOND);
		int mili = calendar.get(Calendar.MILLISECOND);
    	
    	Random randon = new Random();
    	File arquivo_recebido = new File("level_data.obj");  
        
        File copia_arquivo_recebido = new File("files_sent/arq" + randon.nextInt() + "-" + dia + "-" + mes + "-" + 
        		ano + "-" + hour + "h" + minute + "m" + second + "s" + mili + "ms"); 
        
        //System.out.println("Salvando aquivos no diretorio");
        //Salva a Tela
        FileInputStream fisOrigemArquivo = new FileInputStream(arquivo_recebido);  
        FileOutputStream fisDestinoArquivo = new FileOutputStream(copia_arquivo_recebido);  
        FileChannel fcOrigem = fisOrigemArquivo.getChannel();    
        FileChannel fcDestino = fisDestinoArquivo.getChannel();    
        fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);    
        fisOrigemArquivo.close();    
        fisDestinoArquivo.close();  
        
              
        //System.out.println("Finalizado!!!");
    }
	
	public static void enviarDadosServidor() throws UnknownHostException{
		
		/*
		try {
			fazCopia();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		
		try {
			Socket socket = new Socket("200.235.131.136",15123); //Servidor Levi
			//Socket socket = new Socket("127.0.0.1",15123); //Servidor Local
			File transferFile = new File ("level_data.obj"); 
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
