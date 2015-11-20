package br.ufv.willian.auxiliares;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import dk.itu.mario.engine.sprites.SpriteTemplate;


//Classe para guardar informações da tela
public class InformacoesTelas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public class Coordenadas{		
		 public int posX; // Posicao no eixo X do mapa
		 public int posY; //Posicao no eixo Y do mapa
	}
		
	
	//public int numero_inimigos = 0; // O TAMANHO DA LISTA É A QUANTIDADE DE INIMIGOS
	//public ArrayList<Coordenadas> lista_inimigos = new ArrayList<Coordenadas>();
	public ArrayList<SpriteTemplatePrimitivo> listadeinimigos = new ArrayList<SpriteTemplatePrimitivo>();
	public ArrayList<VariaveisGlobais.acoes> listadeacoes = new ArrayList<VariaveisGlobais.acoes>(); 
	//public SpriteTemplatePrimitivo[] lista_inimigos;
	public int tamSpriteTemplate;
	
	private int dificuldade;
	private int seed;
	private int tipo;
	private int quant_buracos;
	private int quant_canhoes;
	private int quant_moedas;
	private String nome_tela;
	private int dificuldade_calculada;
	private int quant_inimigo_alado;
	private int quant_inimigo_nao_alado;
	private int quant_spiky; //Tartaruga de espinho
	private int quant_spiky_turtle_alado;
	
	private boolean tag_platform;
	private boolean tag_straigth;
	private boolean tag_hill_straigth;
	private boolean tag_tubes;
	private boolean tag_jump;
	private boolean tag_cannos;
	
	public InformacoesTelas carregaInfoTela(String nome_fase) throws IOException, ClassNotFoundException{		
		
		//1 - Crie um objeto FileInputStream
		FileInputStream fis = new FileInputStream(nome_fase);
		 //2 - Crie um ObjectInputStream
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		InformacoesTelas info_tela   = (InformacoesTelas) obj;
		ois.close();
		 
		return info_tela;
	}
	
	public void salvaInfoTela(String nome_fase, InformacoesTelas info) throws IOException, ClassNotFoundException{
		
		//FileOutputStream fos = new FileOutputStream(nome_fase);
		/*
		 * Todas as telas terão o mesmo nome
		nome_tela = "tela";
		char[] nomeArray = nome_fase.toCharArray(); 
		for(int i = 4; i < nomeArray.length; i++)
			nome_tela += nomeArray[i];
		*/
		
		FileOutputStream fos = new FileOutputStream(nome_fase);
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(info);
	    os.close();		 
		
	}
	
	public void salvaInfoTela(String nome_fase) throws IOException, ClassNotFoundException{
		
		FileOutputStream fos = new FileOutputStream(nome_fase);
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();		 
		
	}
	
	public void setOutrasVariaveis(int dificuldade, int seed){
		this.dificuldade = dificuldade;
		this.seed = seed;
		//this.tipo = tipo;
	}
	
	public void setQuantMoedas(int quant){
		quant_moedas = quant;
	}
	
	public void setQuantBuracos(int quant){
		quant_buracos = quant;
	}
	
	public void setQuantCanhoes(int quant){
		quant_canhoes = quant;
	}
	
	public void setQuantArmoredTurtleAlado(int quant){
		quant_spiky_turtle_alado = quant;
	}
	
	public void setQuantArmoredTurtle(int quant){
		quant_spiky = quant;
	}
	
	public int getQuantMoedas(){
		return quant_moedas;
	}
	public int getQuantBuracos(){
		return quant_buracos;
	}
	
	public int getQuantCanhoes(){
		return quant_canhoes;
	}
	
	public String getNomeTela(){
		return nome_tela;
	}	
	
	public void setNomeTela(String nome_tela) {
		this.nome_tela = nome_tela;
	}

	public int getDificuldadeCalculada() {
		return dificuldade_calculada;
	}

	public void setDificuldadeCalculada(int dificuldade_calculada) {
		this.dificuldade_calculada = dificuldade_calculada;
	}
	
	public int getQuantInimigo() {
		return quant_inimigo_nao_alado;
	}
	
	public int getQuantAlado() {
		return quant_inimigo_alado;
	}
	
	/**
	 * Método que retorna a quantidade de tartarugas com espinhos não alado
	 * @return Número de tartarugas com espinhos nao alados
	 */
	public int getQuantArmoredTurtle(){
		return quant_spiky;
	}
	
	/**
	 * Método que retorna a quantidade de tartarugas com espinhos alado
	 * @return Número de tartarugas com espinhos alados
	 */
	public int getQuantArmoredTurtleAlado(){
		return quant_spiky_turtle_alado;
	}
	
	public void addInimigo(int x, int y){
		
		/* A Classe SpritePrimitivo contem todas as informacoes dos inimigos. 
		 * Ela é instanciada na Classe Level
		 * É possível utilizar as informações desta classe para ter as informaçoes dos inimigos
		 *  */
		
		Coordenadas posicao = new Coordenadas();
		posicao.posX = x;
		posicao.posY = y;
		
		//lista_inimigos.add(posicao);
		
	}
	
	public void addInimigo(SpriteTemplatePrimitivo inimigo){
		
		//lista_inimigos.add(inimigo);
		//for(int i=0; i < tamSpriteTemplate; i++){
			//lista_inimigos[i] = inimigo[i];
			//listadeinimigos.add(inimigo[i]);
		listadeinimigos.add(inimigo);
		//}
	}
	
	public void contaTiposInimigos(){
		quant_inimigo_nao_alado = 0;
		quant_inimigo_alado = 0;
		quant_spiky = 0;
		quant_spiky_turtle_alado = 0;
		//quant_
		SpriteTemplatePrimitivo inimigo;
		for(int i = 0; i < listadeinimigos.size(); i++){
			inimigo = listadeinimigos.get(i);
			if(inimigo.getWinged() == 1){
				if(inimigo.getType() == SpriteTemplate.ARMORED_TURTLE)
					quant_spiky ++;
				else
					quant_inimigo_alado++;
			}
			else{
				if(inimigo.getType() == SpriteTemplate.ARMORED_TURTLE)
					quant_spiky_turtle_alado ++;
				else
					quant_inimigo_nao_alado++;
			}
		}
		
	}
	
	
	
	public void addAcoesMario(ArrayList<VariaveisGlobais.acoes> l){
		
		listadeacoes = l;
	}
	
	/**
	 * Imprime a lista de inimigos, seu tipo(alado ou nao) e a posição
	 */
	public void listaInimigos(){
		System.out.println("LISTA DE INIMIGOS");
		SpriteTemplatePrimitivo inimigo;
		//for(int i=0; i< lista_inimigos.length; i++){
			//inimigo = lista_inimigos[i];
		for(int i=0; i< listadeinimigos.size(); i++){
			inimigo = listadeinimigos.get(i);
			System.out.println("\tPosicao:\n\t\tx: " + inimigo.getX() + "\ty: " + inimigo.getY());
		}
	}

	public void setTagPlatform(boolean tag_platform) {
		this.tag_platform = tag_platform;
	}	
	
	public boolean getTagPlatform() {
		return tag_platform;
	}

	public void setTagStraigth(boolean tag_straigth) {
		this.tag_straigth = tag_straigth;
	}
	
	public boolean getTagStraigth() {
		return tag_straigth;
	}
	
	public void setTagHillStraigth(boolean tag_hill_straigth) {
		this.tag_hill_straigth = tag_hill_straigth;
	}
	
	public boolean getTagHillStraigth() {
		return tag_hill_straigth;
	}

	public void setTagTubes(boolean tag_tubes) {
		this.tag_tubes = tag_tubes;
	}
	
	public boolean getTagTubes() {
		return  tag_tubes;
	}
	
	public void setTagJump(boolean tag_jump) {
		this.tag_jump = tag_jump;
	}
	
	public boolean getTagJump() {
		return  tag_jump;
	}
	
	public void setTagCannos(boolean tag_cannos){
		this.tag_cannos = tag_cannos;
	}
	
	public boolean getTagCannos(){
		return  tag_cannos;
	}
}
