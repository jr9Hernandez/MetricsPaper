package br.ufv.willian;

import glentakahashi.generator.UltraCustomizedLevelGenerator;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.xml.sax.ext.LexicalHandler;

import petermawhorter.PeterLevelGenerator;

import br.ufv.dpi.DadosAvaliacaoTelas;
import br.ufv.dpi.DadosFormulario;
import br.ufv.willian.auxiliares.ClassePartidas;
import br.ufv.willian.auxiliares.ConectorDeTelas;
import br.ufv.willian.auxiliares.EquacaoEspecial;
import br.ufv.willian.auxiliares.EquacaoFases;
import br.ufv.willian.auxiliares.EquacaoRandomica;
import br.ufv.willian.auxiliares.IdClasse;
import br.ufv.willian.auxiliares.InformacoesTelas;
import br.ufv.willian.auxiliares.MedidorDeDificuldade;
import br.ufv.willian.auxiliares.Parabola;
import br.ufv.willian.auxiliares.VariaveisGlobais;
import br.ufv.willian.auxiliares.VetorFasesPartida;
import br.ufv.willian.auxiliares.VetorSequencia;
import br.ufv.willian.auxiliares.VetorTelasParaTeste;

import dk.itu.mario.level.Level;
import dk.itu.mario.level.RandomLevel;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.engine.sprites.*;

public class LevelGenerator {
	
	public static final int TYPE_OVERGROUND = 0;
    public static final int TYPE_UNDERGROUND = 1;
    public static final int TYPE_CASTLE = 2;

    private static Random levelSeedRandom = new Random();
    public static long lastSeed;
    public static final int LevelLengthMinThreshold = 50;

    public static Level createLevel(int width, int height, long seed, int difficulty, int type)
    {
    	//type = TYPE_OVERGROUND;
        LevelGenerator levelGenerator = new LevelGenerator(width, height);
        return levelGenerator.createLevel(seed, difficulty, type);
    }
    public static Level createLevelMetrics(int width, int height, long seed, int difficulty, int type, int gerador, String [] tiles)
    {
    	//type = TYPE_OVERGROUND;
        LevelGenerator levelGenerator = new LevelGenerator(width, height);
        return levelGenerator.createLevelMetrics(seed, difficulty, type, gerador,tiles);
    }

    protected int width;
    protected int height;
    protected Level level = new Level(width, height);
    Random random;

    private static final int ODDS_STRAIGHT = 0; //0
    private static final int ODDS_HILL_STRAIGHT = 1;
    private static final int ODDS_TUBES = 2;
    private static final int ODDS_JUMP = 3;
    private static final int ODDS_CANNONS = 4;//4
    private int[] odds = new int[5];
    private int totalOdds;
    private int difficulty;
    private int type;
    private int num_powerUp;
    private String msg;
    
    private int cont_aux = 0; //Auxiliar
    
    public int NUM_TELAS_POR_FASE = 8;
    public int DIFICULDADE_MAX = 6;
    
    private ArrayList<Point> coordPiso = new ArrayList<Point>();

    private LevelGenerator(int width, int height)
    {
        this.width = width;
        this.height = height;
        //System.out.println("Instanciou minha classe!!! :p");
    }
    protected LevelGenerator()
    {
    	
    }
    
    /**
     * Classe auxiliar para remover as "verrugas" que aparecem no mapa
     * @author WILLIAN
     * caso 0: nao ha verrugas /n
     * caso 1: verrugas para baixo /n
     * caso 2: verrugas para cima
     *
     */
    class Juncoes{
    	int caso = -1;
    	ArrayList<Integer> lista = new ArrayList<Integer>();
    	int y1, y2, y3;
    	
    	void put(int caso, ArrayList<Integer> lista){
    		this.caso = caso;
    		this.lista = lista;
    	}
    	
    	void passaListaVariaveis(){
    		y1 = lista.get(0);
    		y2 = lista.get(1);
    		y3 = lista.get(2);
    	}
    }

    private Level createLevel(long seed, int difficulty, int type)
    {   
    	DadosFormulario dados = DadosFormulario.getInstancia();
    	if(dados.getIdioma()== "Ingles"){
    		msg = "Thanks for participating.";
    	}
    	else{
    		msg = "Obrigado pela participação."; 
    	}

    	//VariaveisGlobais.testando = true;
    	if(VariaveisGlobais.testando){    		
    		random = new Random();
			seed = random.nextInt(Integer.MAX_VALUE);
			difficulty = 3 + random.nextInt(4);
			//System.out.println("!!! Fase de Teste !!!\n" + "seed: " + seed + " difficulty: " + difficulty +"\n");
			return level = new RandomLevel(220, 15, seed, difficulty, type);    		
			//return level = new RandomLevel(94, 15, seed, difficulty, type);
    	}
    	
    	//level = createLevelOriginal(seed, difficulty, type);
        /*        
        salvaTela(level, "tela2"); //Teste ado metodo que salva a tela
        salvaInfoTela(level);
        */
        //level = conectaTelas(level);        
        //level =  retornaTela(level, "tela2");
        //level = controiLevel(level);
        //level = controiLevelPorFuncao(level);
    	//EquacaoFases equacao = new Parabola(-1/8, 3/3, 3);
    	//DadosFormulario dados = DadosFormulario.getInstancia();
    	//DadosFormulario dados = DadosFormulario.getInstancia();
    	EquacaoFases equacao = null;
    	VariaveisGlobais.num_mortes = 0; //Numero de vezes que o usuario morreu nesta tela
    	//Random randon = new Random();
    	//int x = randon.nextInt(4);
    	int x = VariaveisGlobais.cont % VariaveisGlobais.quant_geradores;
    	//if(x == 0){
    		//VariaveisGlobais.gerador = GeraSequenciaAleatoria.geraSequencia(VariaveisGlobais.quant_geradores);
    		
    		//for(int i = 0; i < VariaveisGlobais.quant_geradores; i++)
    			//System.out.print(VariaveisGlobais.gerador[i] + " ");
    		//System.out.println("\n");
    	//}
    	
    	int gerador;
    	VariaveisGlobais.morreuEm = -1;
    	//gerador = VariaveisGlobais.gerador[x];
    	//VariaveisGlobais.cont++; //Incrementa o contador para as proximas fases
    	
    	
    	/*
    	VetorFasesPartida partidas = new VetorFasesPartida();
    	try {
			partidas = partidas.carregaVetorFases("partidas");
		} catch (ClassNotFoundException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(partidas.size() == 0){
			try {
				
				ArrayList<String> fases = new ArrayList<String>();
				//fases.add("0");
				fases.add("1");
				fases.add("2");
				//fases.add("3");
				fases.add("4");
				fases.add("5");
				
				partidas.constroiVetorTelas(fases);
				ClassePartidas aux;
				System.out.println("Partidas montadas\n");
				for(int i=0; i < partidas.size(); i ++){
					aux = partidas.get(i);
					System.out.println(aux.fase1 +" x " + aux.fase2);
				}
				System.out.println();
				partidas.salvaVetorFases("partidas");
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
		
		if(VariaveisGlobais.fase == 0){
			VariaveisGlobais.partidas = partidas.remove(0);
			try {
				partidas.salvaVetorFases("partidas");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		VariaveisGlobais.fase++;
		
		if(VariaveisGlobais.fase == 1)
			gerador = VariaveisGlobais.partidas.fase1;
		else{
			gerador = VariaveisGlobais.partidas.fase2;
			VariaveisGlobais.fase = 0;
		}
			
    	
		System.out.println(VariaveisGlobais.partidas.fase1 + " x " + VariaveisGlobais.partidas.fase2);
		System.out.println("JOGANDO: " + gerador + "\n");
		dados.setPartida(VariaveisGlobais.partidas.fase1 + " x " + VariaveisGlobais.partidas.fase2);
    	*/
    	
    	/*
    	gerador = VariaveisGlobais.gerador[VariaveisGlobais.nada];
    	VariaveisGlobais.nada++;
    	
    	if(VariaveisGlobais.nada > 2)
    		VariaveisGlobais.nada = 0;
    	*/
    	/*
    	Scanner s = new Scanner(System.in);
    	while(gerador < 0 || gerador > 5){
    		System.out.print("\nDigite a opï¿½ï¿½o da funï¿½ï¿½o desejada: " +
    				"\n\t(0) Constante - dificuldade 5" +
    				"\n\t(1) Parabola - -0.25x^2 + 3x + 4" +
    				"\n\t(2) Aleatï¿½rio - RandomLevel" +
    				"\n\t(3) Linear decrescente - -1,1x + 13" +
    				"\n\t(4) Peter - PeterLevelGenerator" +
    				"\n\t(5) Glen Takahashi - UltraCustomizedLevelGenerator"+
    				"\nSua opï¿½ï¿½o: ");
    		gerador = s.nextInt();
    		if(gerador < 0 || gerador > 5)
    			System.out.println("Opcï¿½o Invï¿½lida!");
    		
    	}
    	*/
    	
    	
    	//************************ IMPORTANTE **************************************
    	
    	/*
    	
    	VetorSequencia sequencia = new VetorSequencia();
    	File file = new File("sequencia.txt");
    	if(file.exists()){
	    	try {
				sequencia = sequencia.carregaVetorSequencia("sequencia.txt");
				if(sequencia.size() == 0){
					//String msg = "<html>Thanks for participating.<br>Your participation id is:<br>"+ dados.getUser() + "</html>";
		    		//JOptionPane.showMessageDialog(null,msg,"", JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(null, msg,"", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
					//System.out.println("End");
		    		//IdClasse.main(null);
		    		//System.in.read();
		    		//return null;
				}
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	    	catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
			}
    	}
    	
    	dados.setQuadradoLatino(1);
    	
    	if(sequencia.size() == 0){    		
    		sequencia.montaVetorSequencia(dados.getQuadradoLatino()); 
    		
    	}    
    	
    	gerador = sequencia.remove(0);  
    	//sequencia.remove(0);
    	//sequencia.remove(0);
    	//sequencia.remove(0);
    	    	
    	
    	try {
			sequencia.salvaVetorSequencia("sequencia.txt");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//********************************************
    	//gerador = 1;
    	System.out.println("Jogando: " + gerador);
    	
    	dados.setPartida(gerador);
    	
    	*/
    	
    	gerador = 2;
    	
    	
    	switch (gerador) {
		case 0:
			equacao = new Parabola(0, 0, 2);
			dados.setEquacao("Parabola(0, 0, 5)");			
			break;		
		case 1:
			//equacao = new Parabola(-0.25 , 3 , 4);
			//dados.setEquacao("-0.25x^2 + 3x + 4");
			//-0.25x^2 + 2.8x + 1
			//equacao = new Parabola(-0.25, 2.8, 1);
			//dados.setEquacao("-0.25x^2 + 2.8x + 2");
			equacao = new EquacaoEspecial(0, 1, 1);
			dados.setEquacao("EquacaoEspecial(0, 1, 1)");
			break;
		case 2:
			random = new Random();
			seed = random.nextInt(Integer.MAX_VALUE);
			difficulty = 3 + random.nextInt(3);
			dados.setEquacao("RandomLevel("+ seed + ", " + difficulty + ", " + type +")");
			//System.out.println(dados.getEquacao());
			return level = new RandomLevel(220, 15, seed, difficulty, type);  //RandomLevel(320, 15, seed, difficulty, type);			
		case 3:
			//equacao = new Parabola(0, -1.1, 13); 
			//dados.setEquacao("Parabola(0, -1,1, 13)");
			equacao = new EquacaoRandomica();
			dados.setEquacao("EquacaoRandomica()");
			break;
		case 4:
			//width = 60;
			dados.setEquacao("PeterLevelGenerator()");
			//System.out.println(dados.getEquacao());
			petermawhorter.PeterLevelGenerator clg = new PeterLevelGenerator();
    		GamePlay gp = new GamePlay();
    		gp = gp.read("player.txt");
            return (Level)clg.generateLevel(gp);   	
		
		case 5:
			glentakahashi.generator.UltraCustomizedLevelGenerator uclg= new UltraCustomizedLevelGenerator();
			GamePlay gp2 = new GamePlay();
    		gp2 = gp2.read("player.txt");
    		dados.setEquacao("UltraCustomizedLevelGenerator()");
    		//System.out.println(dados.getEquacao());
            return (Level)uclg.generateLevel(gp2);
            
		case 7:
			//Retornando tela especï¿½fica
			level = retornaTela(level, "TelasSelecionadas/Telas/" + "tela1599");
			fixWalls();
			return level;
		case 8:
			VetorTelasParaTeste telas = new VetorTelasParaTeste();
			try {
				telas = telas.carregaVetorTelas("vetorTelas");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(telas.size() == 0)
				try {
					telas.constroiVetorTelas();
					telas.salvaVetorTelas("vetorTelas");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			String nome = telas.get(0);
			System.out.println(nome);
			//for(int i=0; i < (telas.size()) - 2; i++)
				//System.out.println("Removido: " + telas.remove(0));
			System.out.println("Nao avaliadas: " + telas.size());
			
			
			level = retornaTela(level, "TelasSelecionadas/Telas/" + nome);
			DadosAvaliacaoTelas dadosTelas = DadosAvaliacaoTelas.getInstancia();
			dadosTelas.setTela(nome);
			fixWalls();			
			return level;
		case 9:
			level = conectaTelas(level);
			width = level.getxExit() + 64;
			//System.out.println("Width: " + width);
			fixWalls();
			
			//corrigeFase(level);
			return level;
		case 10:
			difficulty = (int) (Math.random () * 8);
			level = createLevelOriginal(seed, difficulty, type);			        
			System.out.println("\ncreateLevelOriginal(" + seed + ", " + difficulty + ", " + type + ");");
			int cont = 0;
			for (int xi = 0; xi < level.getWidth(); xi++) {
				for(int y = 0; y < level.getHeight(); y++){
					if(level.getBlock(xi, y) == (byte) (4 + 2 + 1 * 16) || //Block PowerUp 
					   level.getBlock(xi, y) == (byte) (2 + 1 * 16)//Bloco amarelo com PowerUp
					){
						cont++;
						//System.out.println("PowerUp em " + xi + ", " + y);
					}
				}				
			}
			
			//System.out.println("Quantidade de PowerUp: " + cont);
			return level;
		case 11: //salva a Tela
			
			VariaveisGlobais.tag_platform = false;
			VariaveisGlobais.tag_straigth = false;
			VariaveisGlobais.tag_hill_straigth = false;
			VariaveisGlobais.tag_tubes = false;
			VariaveisGlobais.tag_jump = false;
			VariaveisGlobais.tag_cannos = false;
			
			//width = 89;
			//level = new Level(width, 15);
			level = createLevelTela(seed, difficulty, type);
	        salvaTela(level, "tela");
	        salvaInfoTela(level);
	        //fixWalls();
	        return level;
		}   
    	
    	//System.out.println(dados.getEquacao());   	
    	level = controiLevelPorFuncao(level, equacao);
		width = level.getxExit() + 64;
		
		//level.setBlock(1, 5, (byte) 28); //bloco azul-rocheado
		//level.setBlock(1, 5, (byte) 9); //bloco rocha cinza
		//level.setBlock(2, 5, (byte) 12); //bloco de madeira		
		/*
		 *  //bandeirinha na entrada indicando o caminho
		    level.setBlock(0, floor-2, (byte) 67);
    		level.setBlock(0, floor-1, (byte) 83);
    		level.setBlock(1, floor-2, (byte) 68);
    		level.setBlock(1, floor-1, (byte) 84);
		/* */
			
		
		//byte block_marron = (byte) (1 + 9 * 16); //bloco marron
		byte moeda = (byte) 32; //moeda
		//int inicio = 0, fim = 0;
		for(int xi = 0; xi < level.getWidth(); xi++){
			for (int y = 1; y < 8; y++) {
				if(level.getBlock(xi, y) != (byte)0 && level.getBlock(xi, y) != moeda){
					int y0 = 0;
					if(y - 5 > 0)
						y0 = y - 4;
					//System.out.println("Bloco nï¿½o vazio em " + xi + ", " + y);
					int yi = y;
					if(yi > 3)
						yi--;
					for(int z= y0; z < yi; z++){
						if(level.getBlock(xi, z) == (byte)0)
							level.setBlock(xi, z, moeda);
					}
					y=8;
					//break;
					//for(in)
					
					
					//System.out.println("PowerUp em " + xi + ", " + y);
				}
			}
			//level.setBlock(xi, 5, moeda);
		}
		
		//Adicionar um cogumelo		
		int cont = 0;
		for (int xi = 0; xi < 100; xi++) {
			for(int y = 0; y < level.getHeight(); y++){
				if(level.getBlock(xi, y) == (byte) (4 + 2 + 1 * 16) || //Block PowerUp 
				   level.getBlock(xi, y) == (byte) (2 + 1 * 16)//Bloco amarelo com PowerUp
				){
					cont++;
					//System.out.println("PowerUp em " + xi + ", " + y);
				}
			}				
		}
		
		//System.out.println("Quantidade de PowerUp: " + cont);
		
		if(cont < 1){
			random = new Random();
			int xi = random.nextInt(100) + 1;
			int y;
			byte block_ant = (byte) (1 + 9 * 16); //bloco marron
			for(; xi > 0; xi--)
			for(y = level.getHeight(); y > 4; y--){
				if(level.getBlock(xi, y) == 0 && level.getBlock(xi, y + 1) == block_ant){
					if(level.getBlock(xi,  y - 3) == 0 && level.getBlock(xi - 1,  y - 3) == 0 &&
							level.getBlock(xi + 1,  y - 3) == 0){
						//level.setBlock(xi - 1,  y - 3, (byte) 28); //bloco azul-rocheado
						level.setBlock(xi,  y - 3, (byte) (4 + 2 + 1 * 16));
						//level.setBlock(xi + 1,  y - 3, (byte) 12); //bloco de madeira
						//System.out.println("Inserido PowerUp em " + xi + ", " + (y-3));
						xi = 0;
						break;
					}else break;
				}
			}
			
			
			
		}			
        //corrigeFase(level);
    	fixWalls();
        
        //System.out.println("Level com equacao " + dados.getEquacao());
    	return level;
    	
        
    }
    
    

	//level.setBlock(0, 5, (byte)-106); //bloco marron-fim plataforma		
	//level.setBlock(3, 5, (byte) 14); //canhao
	//level.setBlock(4, 5, (byte) 21); //caixa interrogacao
	//level.setBlock(5, 5, (byte) 22); //caixa interrogacao
	//level.setBlock(7, 5, (byte) 30);
	//level.setBlock(8, 5, (byte) 46);
	//level.setBlock(9, 5, (byte) 180);//level.setBlock(1, 3, (byte) 114); //nada
	//level.setBlock(2, 3, (byte) 115); //nada
	/*//Chao, plataformas
	level.setBlock(0, 2, (byte) 128);
	level.setBlock(1, 2, (byte) 129);
	level.setBlock(2, 2, (byte) 130);
	level.setBlock(3, 2, (byte) 131);
	level.setBlock(4, 2, (byte) 132);
	level.setBlock(5, 2, (byte) 133);
	level.setBlock(6, 2, (byte) 134);
	level.setBlock(0, 3, (byte) 144);
	level.setBlock(1, 3, (byte) 145);
	level.setBlock(2, 3, (byte) 146);
	level.setBlock(3, 3, (byte) 147);
	level.setBlock(4, 3, (byte) 148);
	level.setBlock(5, 3, (byte) 149);
	level.setBlock(6, 3, (byte) 150);
	level.setBlock(7, 3, (byte) 160);
	level.setBlock(8, 3, (byte) 161);
	level.setBlock(9, 3, (byte) 162); 
	level.setBlock(10,3, (byte) 163);
	level.setBlock(0, 4, (byte) 176);
	level.setBlock(1, 4, (byte) 177);
	level.setBlock(2, 4, (byte) 178);
	level.setBlock(3, 4, (byte) 179); */
	//level.setBlock(2, 5, (byte) -108); //bloco marron
	//level.setBlock(3, 5, (byte) -107); //bloco marron
	//level.setBlock(0, 5, (byte) 10); //Cano
	//level.setBlock(1, 5, (byte) 11); //Cano
	//level.setBlock(2, 5, (byte) 26); //cano
	//level.setBlock(3, 5, (byte) 27); //cano
	//level.setBlock(2, 5, (byte) 16); //Bloco amarelo
	//level.setBlock(3, 5, (byte) 17); //Bloco amarelo
	//level.setBlock(4, 5, (byte) 18); //Bloco amarelo
	//level.setBlock(4, 5, (byte) 32); //moeda
	
    
    /*
    public void addBloco(int x, int y){
    	byte Block_Coin = (byte) (4 + 1 + 1 * 16); //Block_Coin
    	
    }
    
    public boolean varre(int x0, int y0, int x1, int y1){
    	for(int x = x0; x < x1; x++){
    		for(int y = y0; y < y1; y++){
    			if(level.getBlock(x, y) != (byte)0)
    				return false;
    		}
    	}
    	return true;
    }*/
    
    private Level createLevelTela(long seed, int difficulty, int type)
    {   
    	///Classe original do createLevel
    	//System.out.println("Contruindo um Level Original");
    	//Random rand = new Random();    	
    	
    	this.type = type;
        this.difficulty = difficulty;
        odds[ODDS_STRAIGHT] = 20;
        odds[ODDS_HILL_STRAIGHT] = 10;
        odds[ODDS_TUBES] = 2 + 1 * difficulty;
        odds[ODDS_JUMP] = 2 * difficulty;
        odds[ODDS_CANNONS] = -10 + 5 * difficulty;

        if (type != LevelGenerator.TYPE_OVERGROUND)
        {
            odds[ODDS_HILL_STRAIGHT] = 0;
        }

        for (int i = 0; i < odds.length; i++)
        {
            if (odds[i] < 0) odds[i] = 0;
            totalOdds += odds[i];
            odds[i] = totalOdds - odds[i];
        }

        lastSeed = seed;
        level = new Level(width, height);
        random = new Random(seed);

        int length = 0;
        
        length += buildStraight(0, level.getWidth(), true);
        
        //level.setBlock(length, 8, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
        
        while (length < level.getWidth() - 64)   
        {
            length += buildZone(length, level.getWidth() - length);
        }

        int floor = height - 1 - random.nextInt(4);

        //level.xExit= length + 8; //A saida ï¿½ colocada a 8 pixels do ultimo obstaculo = 9
        //level.yExit = floor;
        //level.setBlock(length, floor - 4, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
        level.setxExit(length + 8);
        level.setyExit(floor);
        
        
        

        for (int x = length; x < level.getWidth(); x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16)); //GROUND (Bloco marron)
                }
            }
        }

        if (type == LevelGenerator.TYPE_CASTLE || type == LevelGenerator.TYPE_UNDERGROUND)
        {
            int ceiling = 0;
            int run = 0;
            for (int x = 0; x < level.getWidth(); x++)
            {
                if (run-- <= 0 && x > 4)
                {
                    ceiling = random.nextInt(4);
                    run = random.nextInt(4) + 4;
                }
                for (int y = 0; y < level.getHeight(); y++)
                {
                    if ((x > 4 && y <= ceiling) || x < 1)
                    {
                        level.setBlock(x, y, (byte) (1 + 9 * 16)); //GROUND (Bloco marron)
                    }
                }
            }
        }
        
        
        //fixWalls() comentado para tentar fazer o teste de correï¿½ï¿½o de telas
        //fixWalls();
        //colocaPowerUp(13, level.getxExit()); //(valor usado no buildStraight, largura mï¿½xima do level)
        
    	return level;      	
        
    }
    
    private Level createLevelOriginal(long seed, int difficulty, int type)
    {   
    	//Classe original do createLevel
    	//System.out.println("Contruindo um Level Original");
    	//Random rand = new Random();    	
    	
    	this.type = type;
        this.difficulty = difficulty;
        odds[ODDS_STRAIGHT] = 20;
        odds[ODDS_HILL_STRAIGHT] = 10;
        odds[ODDS_TUBES] = 2 + 1 * difficulty;
        odds[ODDS_JUMP] = 2 * difficulty;
        odds[ODDS_CANNONS] = -10 + 5 * difficulty;

        if (type != LevelGenerator.TYPE_OVERGROUND)
        {
            odds[ODDS_HILL_STRAIGHT] = 0;
        }

        for (int i = 0; i < odds.length; i++)
        {
            if (odds[i] < 0) odds[i] = 0;
            totalOdds += odds[i];
            odds[i] = totalOdds - odds[i];
        }

        lastSeed = seed;
        level = new Level(width, height);
        random = new Random(seed);

        int length = 0;
        length += buildStraight(0, level.getWidth(), true);
        while (length < level.getWidth() - 64)   
        {
            length += buildZone(length, level.getWidth() - length);
        }

        int floor = height - 1 - random.nextInt(4);

        //level.xExit= length + 8; //A saida ï¿½ colocada a 8 pixels do ultimo obstaculo
        //level.yExit = floor;
        level.setxExit(length + 8);
        level.setyExit(floor);
        
        
        

        for (int x = length; x < level.getWidth(); x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16)); //GROUND (Bloco marron)
                }
            }
        }

        if (type == LevelGenerator.TYPE_CASTLE || type == LevelGenerator.TYPE_UNDERGROUND)
        {
            int ceiling = 0;
            int run = 0;
            for (int x = 0; x < level.getWidth(); x++)
            {
                if (run-- <= 0 && x > 4)
                {
                    ceiling = random.nextInt(4);
                    run = random.nextInt(4) + 4;
                }
                for (int y = 0; y < level.getHeight(); y++)
                {
                    if ((x > 4 && y <= ceiling) || x < 1)
                    {
                        level.setBlock(x, y, (byte) (1 + 9 * 16)); //GROUND (Bloco marron)
                    }
                }
            }
        }
        
        
        //fixWalls() comentado para tentar fazer o teste de correï¿½ï¿½o de telas
        fixWalls();
        //colocaPowerUp(13, level.getxExit()); //(valor usado no buildStraight, largura mï¿½xima do level)
        
    	return level;    	
        
    }
    
    private int buildZone(int x, int maxLength)
    {
    	//System.out.println("Entrou no buildZone");
    	
        int t = random.nextInt(totalOdds);
        int type = 0;
        for (int i = 0; i < odds.length; i++)
        {
            if (odds[i] <= t)
            {
                type = i;
            }
        }
                
        type = random.nextInt(6);
        //type = ODDS_STRAIGHT;
        //System.out.print("type = " + type + " ");
        
        switch (type)
        {
            case ODDS_STRAIGHT:
                return buildStraight(x, maxLength, false);           	
                //return 0;
            case ODDS_HILL_STRAIGHT:
                return buildHillStraight(x, maxLength);
            case ODDS_TUBES:   //Tubos
                return buildTubes(x, maxLength);
            case ODDS_JUMP:   //Buracos
                return buildJump(x, maxLength);
            case ODDS_CANNONS:    //Canhoes
            	//System.out.println("Quantidade de canhoes: " + cont_canhoes);
            	if(cont_canhoes < VariaveisGlobais.MAX_CANNONS_LEVEL){
            		//System.out.println("Retornado buildCannons");
            		return buildCannons(x, maxLength);
            	}
            	else{
            		//System.out.println("Retornado buildStraight");
            		if(random.nextBoolean())
            			return buildStraight(x, maxLength, false);
            		else
            			return buildHillStraight(x, maxLength);
            	}
            case 5:
                return buildHillStraight(x, maxLength);
        }
        
                
        return 0;
    }

    int cont_buracos = 0;
    
    private int buildJump(int xo, int maxLength)
    {    	
    	boolean trava = false;
    	int js = random.nextInt(4) + 2;
        int jl = random.nextInt(2) + 2;
        int length = js * 2 + jl;

        boolean hasStairs = random.nextInt(3) == 0;
        //hasStairs = false;

        int floor = height - 1 - random.nextInt(4);
        if(hasStairs)
        	floor =  height - 1 - random.nextInt(2);
        
        for (int x = xo; x < xo + length; x++)
        {
            if (x < xo + js || x > xo + length - js - 1)
            {
                for (int y = 0; y < height; y++)
                {
                    if (y >= floor)
                    {
                        level.setBlock(x, y, (byte) (1 + 9 * 16)); //GROUND (Bloco marron)
                    }
                    else if (hasStairs)
                    {
                        if (x < xo + js)
                        {
                            if (y >= floor - (x - xo) + 1)
                            {
                                level.setBlock(x, y, (byte) (9 + 0 * 16));
                            }
                        }
                        else
                        {
                            if (y >= floor - ((xo + length) - x) + 2)
                            {
                                level.setBlock(x, y, (byte) (9 + 0 * 16));
                            }
                        }
                    }
                }         
                
            }
            trava = true;
        }
        
        //System.out.println("\nPlataforma em buildJump");
        
        int chao;
        if(hasStairs){        	
        	chao = floor - js + 1;
        }
        else
        	chao = floor;
        
        //System.out.println("js: " + js + " floor: " + floor + " Passado: " + chao);
        
        VariaveisGlobais.tag_jump = true;
        
        buildPlatform(xo, xo + length, chao);
        
        if(trava)cont_buracos++;
        //System.out.println("Entrou no buildJump: " + length);
        //return 10;
        return length;
    }

    int cont_canhoes = 0;   
    
    private int buildCannons(int xo, int maxLength)
    {    	
        //int length = random.nextInt(10) + 2;
    	int length = 3 + random.nextInt(3);
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        //int xCannon = xo + 1 + random.nextInt(4);
        int xCannon = xo + 1;
        
        for (int x = xo; x < xo + length; x++)
        {
            if (x > xCannon)
            {
                xCannon += 2 + random.nextInt(4);
                
            }
            if (xCannon == xo + length - 1) xCannon += 10;
            int cannonHeight = floor - random.nextInt(4) - 1;

            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16));     //GROUND (Bloco marron)               
                }
                else
                {
                    if (x == xCannon && y >= cannonHeight)
                    {
                        if (y == cannonHeight)
                        {
                            VariaveisGlobais.tag_cannos = true;
                        	level.setBlock(x, y, (byte) (14 + 0 * 16)); //Canhao
                            cont_canhoes++;
                        }
                        else if (y == cannonHeight + 1)
                        {
                        	level.setBlock(x, y, (byte) (14 + 1 * 16)); //Conector base-canhao
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (14 + 2 * 16)); //Base do canhao
                        }
                    }
                }
            }
            
            
        }

        //System.out.println("\nPlataforma em buildCannons");
        if(length > 3)
        	buildPlatform(xo + 1, xo + length, floor - 2);
        
        
        return length;
    }

    private int buildHillStraight(int xo, int maxLength)
    {
        int length = random.nextInt(10) + 10;
        //length = 15;
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        for (int x = xo; x < xo + length; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16)); //GROUND (Bloco marron)
                }
            }
        }

        addEnemyLine(xo + 1, xo + length - 1, floor - 1);

        int h = floor;
        int min_h = h;

        boolean keepGoing = true;
        //keepGoing = false;

        boolean[] occupied = new boolean[length];
        while (keepGoing)
        {
            h = h - 2 - random.nextInt(3);
            if(min_h > h)
            	min_h = h;

            if (h <= 0)
            {
                keepGoing = false;
            }
            else
            {
                int l = random.nextInt(5) + 3;
                int xxo = random.nextInt(length - l - 2) + xo + 1;

                if (occupied[xxo - xo] || occupied[xxo - xo + l] || occupied[xxo - xo - 1] || occupied[xxo - xo + l + 1])
                {
                    keepGoing = false;
                }
                else
                {
                    occupied[xxo - xo] = true;
                    occupied[xxo - xo + l] = true;
                    addEnemyLine(xxo, xxo + l, h - 1);
                    //if (random.nextInt(4) == 0)
                    if (random.nextInt(3) == 0)
                    {
                        decorate(xxo - 1, xxo + l + 1, h); 
                        keepGoing = false;
                        min_h -= 2;
                    }
                    for (int x = xxo; x < xxo + l; x++)
                    {
                        for (int y = h; y < floor; y++)
                        {
                            int xx = 5;
                            if (x == xxo) xx = 4;
                            if (x == xxo + l - 1) xx = 6;
                            int yy = 9;
                            if (y == h) yy = 8;

                            if (level.getBlock(x, y) == 0)
                            {
                                level.setBlock(x, y, (byte) (xx + yy * 16));
                            }
                            else
                            {
                                if (level.getBlock(x, y) == (byte) (4 + 8 * 16)) {
                                	level.setBlock(x, y, (byte) (4 + 11 * 16));
                                }
                                if (level.getBlock(x, y) == (byte) (6 + 8 * 16)) {
                                	level.setBlock(x, y, (byte) (6 + 11 * 16));
                                }
                            }
                            /*
                            if(
                            		level.getBlock(x, y) == (byte) (4 + 8 * 16)
                            		|| level.getBlock(x, y) == (byte) (5 + 8 * 16)
                            		|| level.getBlock(x, y) == (byte) (6 + 8 * 16)
                              ){
                            	if(min_h > y)
                            		min_h = y;                            	
                            } 
                            */
                            
                        }
                    }
                }
            }
        }

        //level.setBlock(xo + 1, min_h, (byte) (11 + 0 * 16));//Entrada direita do Cano
		//level.setBlock(xo , min_h, (byte) (10 + 0 * 16 ));//Entrada esquerda do Cano
        
        //System.out.println("\nPlataforma em buildHillStraight");
        //System.out.println("floor: "+ floor + " min_h: " + min_h);
        /*
        if(floor - min_h > 5){
        	min_h = min_h + 2;
        	System.out.println("floor - min_h > 5. Retornando: " + min_h);
        }
        else{
        	min_h = floor;
        	System.out.println("floor - min_h <= 5. Retornando: " + min_h);
        }
        */
        
        
        
        
        if(length > 6){
        	buildPlatform(xo + 2, xo + length - 2, min_h + 2);
        }
        else        	
        	buildPlatform(xo, xo + length, min_h + 2); 
        
        //Setando que foi colocado montanhas na tela
        VariaveisGlobais.tag_hill_straigth = true;
        
        if(random.nextInt(3) == 0){
	        int x = random.nextInt(length);
	        int y = random.nextInt(4);
	        int quant = random.nextInt(3);
	        if(x + quant > length)
	        	quant = length - x;
	        System.out.println(quant);
	        byte block;     
	           
	        if(random.nextBoolean())
	        	block = (byte) 28; //bloco azul-rocheado
	        else
	        	block = (byte) 12; //bloco de madeira   
	        	     
	        for(int i = 0; i < quant; i++){
	        	if(level.getBlock(x + xo + i, min_h - y) == 0 ||
	        			level.getBlock(x + xo + i, min_h - y - 1) == 0 ||
	        			level.getBlock(x + xo + i, min_h - y + 1) == 0)
	        		level.setBlock(x + xo + i, min_h - y, block);
	        }
        }
        
        return length;
    }

    
    int marcador_inimigo;
    
    private void addEnemyLine(int x0, int x1, int y)
    {
        for (int x = x0; x < x1; x++)
        {
            if (random.nextInt(35) < difficulty + 1)
            {
                int type = random.nextInt(4);
                if (difficulty < 1)
                {
                    type = Enemy.ENEMY_GOOMBA;
                }
                //else if (difficulty < 3) //Era esse no dia 27-10-2014
                else if (difficulty < 5)
                {
                    type = random.nextInt(3);
                }
                
                level.setSpriteTemplate(x, y, new SpriteTemplate(type, random.nextInt(35) < difficulty));
                level.ENEMIES++;
            }
        }
        
        //System.out.println("Entrou no addEnemyLine");
    }

    
    private int buildTubes(int xo, int maxLength)
    {
        int length = random.nextInt(10) + 5;
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        int xTube = xo + 1 + random.nextInt(4);
        int tubeHeight = floor - random.nextInt(2) - 2;
        for (int x = xo; x < xo + length; x++)
        {
            if (x > xTube + 1)
            {
                xTube += 3 + random.nextInt(4);
                tubeHeight = floor - random.nextInt(2) - 2;
            }
            if (xTube >= xo + length - 2) xTube += 10;

            if (x == xTube && random.nextInt(11) < difficulty + 1)
            {
                level.setSpriteTemplate(x, tubeHeight, new SpriteTemplate(Enemy.ENEMY_FLOWER, false));
                level.ENEMIES++;
            }

            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16)); //GROUND (Bloco marron)
                }
                else
                {
                    if ((x == xTube || x == xTube + 1) && y >= tubeHeight)
                    {
                        int xPic = 10 + x - xTube;
                        if (y == tubeHeight)
                        {
                            level.setBlock(x, y, (byte) (xPic + 0 * 16));
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (xPic + 1 * 16));
                        }
                    }
                }
            }
        }
        
        
        VariaveisGlobais.tag_tubes = true;
        
        //System.out.println("\nPlataforma em buildTubes");
        buildPlatform(xo, xo + length, floor);
        
        //System.out.println("Entrou no buildTubes: " + length);
        return length;
        //return 18;
    }

    
    private int buildStraight(int xo, int maxLength, boolean safe)
    {
        int length = random.nextInt(10) + 3;
        
        //if (safe) length = 10 + random.nextInt(5); // O inicio da fase e no minimo 10
        if (safe) length = 10; //Eu coloquei este valor fixo. Usei 13, para descontar 12 e deixar 1 para evitar possï¿½veis bugs.
        
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        //int floor = height - 3;
        
        for (int x = xo; x < xo + length; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16));  //GROUND (Bloco marron)
                }
            }
        }
        
        //System.out.println("\nPlataforma em buildStraight");
        if(safe){ 
        	 
        	/*
        	int num_block = 0;
        	if(random.nextInt(2) == 0){
        		
        		num_block = random.nextInt(3) + 1;
        		
        		int aux = random.nextInt(3);
	    		byte block = 0;
	    		switch (aux) {
				case 0:
					block = (byte) (0 + 1 * 16); //Bloco vazio
					break;
				case 1:
					block = (byte) (1 + 1 * 16); //Bloco amarelo com Moeda
					break;
				case 2:
					block = (byte) (4 + 1 + 1 * 16); //Block_Coin
					break;
				
				}    			
	            
	    		aux = random.nextInt(3) + 3;
	    		int inicio = 3 + random.nextInt(4);
    			for(int x = inicio; x < inicio + num_block; x++){
    				//System.out.println("floor: " + floor + " aux: " + aux);
    				level.setBlock(x, floor - aux , block);
    				if(block == (byte) (1 + 1 * 16) || block == (4 + 1 + 1 * 16))
    					cont_coin++;
    			}
    			
    			num_block = random.nextInt(3) + 1;
    			inicio = 5 + random.nextInt(3);
    			for(int x = inicio; x < inicio + num_block; x++)
    				level.setBlock(x, floor - (aux + 4) , block);
        		
        	} 
        	
        	*/
        	          	
        	 
        	level.setBlock(2, floor-2, (byte) 67);
    		level.setBlock(2, floor-1, (byte) 83);
    		level.setBlock(3, floor-2, (byte) 68);
    		level.setBlock(3, floor-1, (byte) 84);
        	
        }
        else{
        	
        	buildPlatform(xo, xo + length, floor);

            if (length > 5)
            {
                decorate(xo, xo + length, floor);
            }
            
            //Coloca o mini cano e a cerca de madeira
            if(random.nextInt(4) == 0){            	
            	int x = xo + random.nextInt(length - 1) + 1;
            	int y = floor - random.nextInt(3);
            	if(level.getBlock(x, y - 1) == 0 && level.getBlock(x, y - 2) == 0 && level.getBlock(x, y - 3) == 0){
            		if(random.nextBoolean()){
            			level.setBlock(x, y - 3, (byte) 24); //caninho cima
            			level.setBlock(x, y - 2, (byte) 40); //caninho meio
            			level.setBlock(x, y - 1, (byte) 56); //caninho baixo
            		}else{
            			level.setBlock(x, y - 3, (byte) 25); //Madeira cima
            			level.setBlock(x, y - 2, (byte) 41);//Madeira meio
            			level.setBlock(x, y - 1, (byte) 57); //Madeira baixo
            		}
            	}
            }
            
            VariaveisGlobais.tag_straigth = true;
        }
        
        
        //System.out.println("Entrou no buildStraight: " + length);
        return length;
        //return 19;
    }
    
    public void buildPlatform(int x0, int x1, int floor){
    	
    	if(random.nextBoolean())
    	//if(x0 >= 0)
    		return;
    	
    	//Largura mï¿½nima da plataforma tem de ser de 3: 1 para bloco superior e 1 para bloco inferior e um para o meio
    	int length = x1 - x0; //Comprimento da plataforma

    	Random rand = new Random();
    	int x_aux_plataforma = 0;
    	if(length > 3){
	    	if(rand.nextInt(4) != 0){
	    		x_aux_plataforma = rand.nextInt(length - 3);
	    		if(x_aux_plataforma > 3)
	    			x_aux_plataforma = 3;
	    		x0 += x_aux_plataforma;
	    	}else{
	    		x0++;
	    	}
	    	length = x1 - x0;	    	
    	}
    	
    	if(length > 3){
	    	if(rand.nextInt(4) != 0){
	    		x_aux_plataforma = rand.nextInt(length - 3);
	    		if(x_aux_plataforma > 3)
	    			x_aux_plataforma = 3;
	    		x1 -= x_aux_plataforma;
	    	}
	    	else
	    		x1--;
	    	length = x1 - x0;	    	
    	}	
    	
    	
    	/**
    	 * Mï¿½ximo que a parte de baixo da plataforma pode chegar
    	 */
    	int max_plataforma = floor - 6; // 7: Espaï¿½o de 4 acima do chao + 1 para possï¿½veis blocos + 1 acima dos blocos
    	
    	//if(x0 == 0) x0 = 3;
    	if(length < 3){
    		
    		if(max_plataforma >= 2){
    			
    			int y = rand.nextInt(max_plataforma - 1);
    			max_plataforma = y + 2;
    			
    			int type_block = rand.nextInt(4);
	    		byte block = 0;
	    		switch (type_block) {
				case 0:
					block = (byte) (0 + 1 * 16); //Bloco vazio
					break;
				case 1:
					block = (byte) (1 + 1 * 16); //Bloco amarelo com Moeda
					break;
				case 2:
					block = (byte) 28;  //bloco azul-rocheado
					break;
				case 3:
					block = (byte) 12; //bloco de madeira
					break;
				
				} 
	    		   		
	            		
    			for(int x = x0; x < x1; x++){
    				level.setBlock(x, max_plataforma , block);
    				if(block == (byte) (1 + 1 * 16))
    					cont_coin++;
    			}
    			
    			VariaveisGlobais.tag_platform = true;
    			
    		}
    		//System.out.println("Largura insuficiente para plataforma.");
    		return;
    		
    	}
    	
    	if(max_plataforma < 5) { //Coordenada em y. 
    		if(max_plataforma >= 2){ 
    			//System.out.println("Contruindo plataforma de blocos. max_plataforma retornado: " + max_plataforma );
    			if(length > 6){
    				x0 += 2;
    				x1 -= 2;
    			}
    			
    			int y = rand.nextInt(max_plataforma - 1);
    			max_plataforma = 2 + y;
    			
    			int type_block = rand.nextInt(4);
	    		byte block = 0;
	    		switch (type_block) {
				case 0:
					block = (byte) (0 + 1 * 16); //Bloco vazio
					break;
				case 1:
					block = (byte) (1 + 1 * 16); //Bloco amarelo com Moeda
					break;
				case 2:
					block = (byte) 28;  //bloco azul-rocheado
					break;
				case 3:
					block = (byte) 12; //bloco de madeira
					break;
				
				}    			
	            		
    			for(int x = x0; x < x1; x++){
    				level.setBlock(x, max_plataforma , block);
    				if(block == (byte) (1 + 1 * 16))
    					cont_coin++;
    			}
    			
    			VariaveisGlobais.tag_platform = true;
    		}
    		
    		return;
    	}    	
    	
    	    	
    	int begin_platform = 3; 
    	begin_platform -= rand.nextInt(2);
    	int aux = max_plataforma - 4;
    	begin_platform += rand.nextInt(aux);
    	
    	if(begin_platform > max_plataforma - 3)
    		begin_platform = max_plataforma - 3;
    	    	
    	int widthPlatform = rand.nextInt(max_plataforma - begin_platform);
    	if(widthPlatform < 3)
    		widthPlatform = 3;    	    	
    	
    	
    	//System.out.println("Contruindo uma plataforma entre " + x0 +" e  "+ x1 + " com comprimento " + length +
    		//	". Onde o y_MAX ï¿½ " + max_plataforma +  " de lagura " + widthPlatform + " a partir de " + begin_platform);
    	
    	if(rand.nextInt(4) == 3){
			int y = rand.nextInt(widthPlatform) + begin_platform;
			int type_block = rand.nextInt(4);
    		byte block = 0;
    		switch (type_block) {
			case 0:
				block = (byte) (0 + 1 * 16); //Bloco vazio
				break;
			case 1:
				block = (byte) (1 + 1 * 16); //Bloco amarelo com Moeda
				break;
			case 2:
				block = (byte) 28;  //bloco azul-rocheado
				break;
			case 3:
				block = (byte) 12; //bloco de madeira
				break;
			
			}    			
    		for (int x = x0; x < x1; x++)
            {
                level.setBlock(x, y, (byte) block); 
                if(block == (byte) (1 + 1 * 16))
                	cont_coin++;
            }
            
		}else{
			for (int x = x0; x < x1; x++)
        	{
    			for (int y = begin_platform; y < begin_platform + widthPlatform; y++)
	        	{
	            	level.setBlock(x, y, (byte) (1 + 9 * 16));  //GROUND (Bloco marron)
	            	//level.setBlock(x, y , (byte)(4 + 1 + 1 * 16));
	        	}

        	}
    	}
    	//level.setBlock(x1, begin_platform , (byte)(4 + 1 + 1 * 16));
    	//level.setBlock(x1 + 1, begin_platform, (byte) (11 + 0 * 16 ));//Entrada direita do Cano
    	//level.setBlock(x1 + 1, begin_platform + widthPlatform, (byte) (11 + 0 * 16));//Entrada direita do Cano
		//level.setBlock(x0 - 1, begin_platform, (byte) (10 + 0 * 16 ));//Entrada esquerda do Cano
		//level.setBlock(x0 - 1, begin_platform + widthPlatform, (byte) (10 + 0 * 16));//Entrada esquerda do Cano
    	
    	VariaveisGlobais.tag_platform = true;
    	
    	if(x1 - x0 > 4)
            decorate(x0, x1, begin_platform - 1);        
    	
    }

    int cont_power_up = 0;
    int cont_coin = 0;
    
    private void decorate(int x0, int x1, int floor)
    {
    	Random r = new Random();
        if (floor < 1) return;
        
        boolean rocks = true;

        if(r.nextInt(3) > 0) //Acrescentei esse aleatorio
        	addEnemyLine(x0 + 1, x1 - 1, floor - 1);

        int s = random.nextInt(4);
        int e = random.nextInt(4);
                  

        if (floor - 2 > 0)
        {
            if ((x1 - 1 - e) - (x0 + 1 + s) > 1)
            {
                for (int x = x0 + 1 + s; x < x1 - 1 - e; x++)
                {
                    level.setBlock(x, floor - 2, (byte) (2 + 2 * 16)); ////Moeda
                    level.COINS++;
                    cont_coin++;
                }
            }
        }

        s = random.nextInt(4);
        e = random.nextInt(4);

        if (floor - 4 > 0)
        {
            if ((x1 - 1 - e) - (x0 + 1 + s) > 2)
            {
                for (int x = x0 + 1 + s; x < x1 - 1 - e; x++)
                {
                    if (rocks)
                    {
                        if (x != x0 + 1 && x != x1 - 2 && random.nextInt(3) == 0)
                        {
                            if (random.nextInt(4) == 0 && cont_power_up < VariaveisGlobais.MAX_POWER_UP)
                            {
                                level.setBlock(x, floor - 4, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
                                level.BLOCKS_POWER++;
                                cont_power_up++;
                            }
                            else
                            {
                                level.setBlock(x, floor - 4, (byte) (4 + 1 + 1 * 16)); //Block_Coin
                                cont_coin++;
                                level.BLOCKS_COINS++;
                            }
                        }
                        else if (random.nextInt(4) == 0)
                        {
                            if (random.nextInt(4) == 0 && cont_power_up < VariaveisGlobais.MAX_POWER_UP)
                            {
                                level.setBlock(x, floor - 4, (byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
                                cont_power_up++;
                            }
                            else
                            {
                                level.setBlock(x, floor - 4, (byte) (1 + 1 * 16)); //Bloco amarelo com Moeda
                                cont_coin++;
                            }
                        }
                        else
                        {
                            level.setBlock(x, floor - 4, (byte) (0 + 1 * 16)); //Bloco vazio
                            level.BLOCKS_EMPTY++;
                        }
                    }
                }
            }
        }

        
        
        //System.out.println("Entrou no decorate");
        /*
         int length = x1 - x0 - 2;
         if (length > 5 && rocks)
         {
         decorate(x0, x1, floor - 4);
         }
         */
    }

    protected void fixWalls()
    {
        boolean[][] blockMap = new boolean[width + 1][height + 1];
        for (int x = 0; x < width + 1; x++)
        //for (int x = 0; x < width + 1 ; x+=10)
        {
            for (int y = 0; y < height + 1; y++)
            {
                int blocks = 0;
                for (int xx = x - 1; xx < x + 1; xx++)
                {
                    for (int yy = y - 1; yy < y + 1; yy++)
                    {
                        if (level.getBlockCapped(xx, yy) == (byte) (1 + 9 * 16)) blocks++;  //GROUND (Bloco marron)
                    }
                }
                blockMap[x][y] = blocks == 4;                
            }
        }
       blockify(level, blockMap, width + 1, height + 1);
        
        //System.out.println("Entrou no fixWalls");
    }
    

    private void blockify(Level level, boolean[][] blocks, int width, int height)
    {
        int to = 0;
        if (type == LevelGenerator.TYPE_CASTLE)
        {
            to = 4 * 2;
        }
        else if (type == LevelGenerator.TYPE_UNDERGROUND)
        {
            to = 4 * 3;
        }

        boolean[][] b = new boolean[2][2];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int xx = x; xx <= x + 1; xx++)
                {
                    for (int yy = y; yy <= y + 1; yy++)
                    {
                        int _xx = xx;
                        int _yy = yy;
                        if (_xx < 0) _xx = 0;
                        if (_yy < 0) _yy = 0;
                        if (_xx > width - 1) _xx = width - 1;
                        if (_yy > height - 1) _yy = height - 1;
                        b[xx - x][yy - y] = blocks[_xx][_yy];
                    }
                }

                if (b[0][0] == b[1][0] && b[0][1] == b[1][1])
                {
                    if (b[0][0] == b[0][1])
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (1 + 9 * 16 + to)); //bloco marron do meio. Chï¿½o
                        }
                        else
                        {
                            // KEEP OLD BLOCK!
                        }
                    }
                    else
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (1 + 10 * 16 + to));//Bloco com grama inferior
                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (1 + 8 * 16 + to)); //Bloco com grama superior
                            Point p = new Point(x, y);
                            coordPiso.add(p);
                            //System.out.println("Piso em " + x + ", " + y);
                        }
                    }
                }
                else if (b[0][0] == b[0][1] && b[1][0] == b[1][1])
                {
                    if (b[0][0])
                    {
                        level.setBlock(x, y, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
                    }
                    else
                    {
                        level.setBlock(x, y, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
                    }
                }
                else if (b[0][0] == b[1][1] && b[0][1] == b[1][0])
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16 + to)); //Bloco todo marron. Chï¿½o sem grama.                    
                }
                else if (b[0][0] == b[1][0])
                {
                    if (b[0][0])
                    {
                        if (b[0][1])
                        {
                            level.setBlock(x, y, (byte) (3 + 10 * 16 + to)); //Pequena quina inferior direita.                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (3 + 11 * 16 + to)); //Pequena quina inferior esquerda                            
                        }
                    }
                    else
                    {
                        if (b[0][1])
                        {
                            level.setBlock(x, y, (byte) (2 + 8 * 16 + to)); //Quina superior direita. Inicio de um buraco                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco                             
                        }
                    }
                }
                else if (b[0][1] == b[1][1])
                {
                    if (b[0][1])
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (3 + 8 * 16 + to)); //Pequena quina superior esquerda 
                        }
                    }
                    else
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (2 + 10 * 16 + to)); //Quina inferior direita. Oposto a um buraco
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (0 + 10 * 16 + to)); //Quina inferior esquerda. Oposto a um buraco
                        }
                    }
                }
                else
                {
                    level.setBlock(x, y, (byte) (0 + 1 * 16 + to)); //Blocos amarelos                    
                }
            }
        }
        
        //System.out.println("Entrou no blockify");
    }
    
    public void colocaPowerUp(int laguraMin, int larguraMax){
    	
    	Random rand = new Random();
    	
    	boolean encontrouPiso = false; //Variavel para encontrar um piso. Garante nao ser um Buraco
    	//System.out.println("\nlaguraMin: " + laguraMin + " larguraMax: " + larguraMax);
    	//int x = laguraMin + rand.nextInt(10);
    	int x = laguraMin;
    	//int x = 25;
    	//System.out.println("x sorteado: " + x);
    	//if(x > coordPiso.size()-1) x = coordPiso.size()-1;
    	//Point p = coordPiso.get(x);
    	//int floor = (int) p.getY();
    	//x = (int) p.getX();
    	int floor = 0;
    	
    	while(!encontrouPiso){
	    	for(int y = height; y > 0; y--){
	    		if(level.getBlock(x, y) == (byte) (1 + 8 * 16)){ //Bloco com grama superior
	    			floor = y;
	    			encontrouPiso = true;
	    			break;
	    		}else{
	    			System.out.println("Nao encontrou o piso no x = " + x + " e y = "+ y);
	    		}    		
	    	}
	    	x++;
	    	if(x >= larguraMax){	    	
	    		System.out.println("Saindo do colocaPowerUp");    	
	    		return;
	    	}
	    	
    	}
    	
    	
    	int quantidade = 1 + rand.nextInt(3); //Quantidade de blocos a serem inseridos
        int num_powerUp = 0;

        if (floor - 4 > 0)
        {
            if(quantidade == 1 || x >= larguraMax){            	
            	level.setBlock(x, floor - 4, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
            	num_powerUp++;
            }
            else{
            	for (int quant = 0, x_corrente = x; quant < quantidade; quant++)
                {
                   if (rand.nextInt(3) == 0)
                   {
                            if (rand.nextInt(4) == 0)
                            {
                                level.setBlock(x_corrente, floor - 4, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
                                num_powerUp++;
                                x_corrente++;
                            }
                            else
                            {
                                level.setBlock(x_corrente, floor - 4, (byte) (4 + 1 + 1 * 16)); //Block_Coin
                                level.BLOCKS_COINS++;
                                x_corrente++;
                            }
                        }
                        else if (rand.nextInt(3) == 0)
                        {
                            if (rand.nextInt(4) == 0)
                            {
                                level.setBlock(x_corrente, floor - 4, (byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
                                num_powerUp++;
                                x_corrente++;
                            }
                            else
                            {
                                level.setBlock(x_corrente, floor - 4, (byte) (1 + 1 * 16)); //Bloco amarelo com Moeda
                                x_corrente++;
                            }
                        }
                        else
                        {
                            level.setBlock(x_corrente, floor - 4, (byte) (0 + 1 * 16)); //Bloco vazio
                            x_corrente++;
                        }
                }
            	}
            }
        if(num_powerUp == 0)
        	level.setBlock(x, floor - 4, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
    }
    
    public void corrigeFase(Level level){
    	
    	num_powerUp = 0;
    	int to = 0;
        if (type == LevelGenerator.TYPE_CASTLE)
        {
            to = 4 * 2;
        }
        else if (type == LevelGenerator.TYPE_UNDERGROUND)
        {
            to = 4 * 3;
        }
        
    	int yTela1 = 0, yTela2 = 0;
    	Point p;
    	for(int z=0; z < level.listaInicioFimTelas.size()-1; z++){
	    	
    		yTela1 = 0;
    		yTela2 = 0;
    		
    		p = level.listaInicioFimTelas.get(z);
	    	
	    	//System.out.println("colocaPowerUp(" + p.getX() + ", " + p.getY() + ")");
	    	//colocaPowerUp((int)p.getX(), (int)p.getY());
	    	int xInicioTela1 = (int) p.getX();
	    	int xTela1 = (int) p.getY();
	    	p = level.listaInicioFimTelas.get(z + 1);
	    	int xTela2 = (int) p.getX();
	    	int xFimTela2 = (int) p.getY();
	    	    	
	    	nivelarTrechoTela(xTela1, xTela2, to);
	    	
	    	
	    	/*System.out.println("\nTela1: \n\tInicio: "  + xInicioTela1 + " \tFim: " + xTela1 + 
	    			"\nTela2: \n\tInicio: "+ xTela2 + " \tFim: " + xFimTela2);*/
	    	
	    	for(int y = level.getHeight(); y > 0; y--){
	    		//Testa se ï¿½ bloco com grama superior, quina a esquerda ou quina a direita
	    		//if(level.getBlock(xTela1, y) == (byte) (1 + 8 * 16 + to) || level.getBlock(xTela1, y) == (byte)(2 + 8 * 16 + to) || level.getBlock(xTela1, y) == (byte) (0 + 8 * 16 + to)){
	    		if(level.getBlock(xTela1, y) == (byte) (1 + 8 * 16 + to)){
	    			yTela1 = y;	    			
	    			//System.out.println("\tEntrou no primeiro if do for. y = " + y);
	    		}
	    		//else{ level.setBlock(xTela1, y, (byte) (0 + 1 * 16 + to));} //Blocos amarelos
	    		//if(level.getBlock(xTela2, y) == (byte) (1 + 8 * 16 + to) || level.getBlock(xTela2, y) == (byte)(2 + 8 * 16 + to) || level.getBlock(xTela2, y) == (byte) (0 + 8 * 16 + to)){ 
	    		if(level.getBlock(xTela2, y) == (byte) (1 + 8 * 16 + to)){
	    			yTela2 = y;
	    			//System.out.println("\tEntrou no segundo if do for. y = " + y);
	    		}
	    	}
	    	
	    	/*
	    	if(yTela1 != 0){
	    		if(level.getBlock(xTela1 - 3, yTela1) != (byte) (1 + 8 * 16 + to)){
	    			for(int x = xTela1 - 3; x <= xTela1; x++)
	    				level.setBlock(x, yTela1, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    				//level.setBlock(x, yTela1, (byte) 0); //Inventado
	    		}
	    	}
	    	
	    	if(level.getBlock(xTela2 + 3, yTela2) != (byte) (1 + 8 * 16 + to)){
    			for(int x = xTela2; x <= xTela2 + 3; x++)
    				level.setBlock(x, yTela1, (byte) (4 + 1 + 1 * 16 + to)); //BLOCK_COIN
    		}
	    	*/
	    	
	    	
	    	
	    	
	    	/*int aux = 0;
	    	ArrayList<Point> pontos = new ArrayList<Point>();
	    	for(int x = xTela1 - 2; x <= xTela2 + 2; x++){	    		
	    		for(int y = level.getHeight() - 1; y >= 0; y--){	    			
	    			if(level.getBlock(x, y) == (byte) (1 + 8 * 16 + to) ||  //chï¿½o
	    					level.getBlock(x, y) == (byte) (2 + 8 * 16 + to) || // Quina direita
	    					level.getBlock(x, y) == (byte) (0 + 8 * 16 + to) //|| // Quina esquerda
	    					//level.getBlock(x, y) == (byte) (3 + 11 * 16 + to)|| //Pequena quina inferior esquerda
	    					//level.getBlock(x, y) == (byte) (3 + 10 * 16 + to)|| //Pequena quina inferior direita
	    					//level.getBlock(x, y) == (byte) (3 + 8 * 16 + to) || //Pequena quina superior esquerda
	    					//level.getBlock(x, y) == (byte) (3 + 9 * 16 + to)    //Pequena quina superior direita	    					
	    					){    
	    				Point ponto = new Point(x , y);
	    				pontos.add(ponto);
	    				aux++;
	    				//y = level.getHeight();
	    			}	    			
	    		}
	    	}
	    	
	    	cont_aux++;
	    	System.out.println("Caso " + cont_aux + " -> aux: " + aux  +"-> Tamanho: " + pontos.size() + ": ");
	    	for(int i = 0; i < pontos.size(); i++){
	    		Point ponto = pontos.get(i);
	    		System.out.print(ponto.getX() + ", " + ponto.getY() + " - " );
	    		//if(i % 2 == 0)
	    			//level.setBlock((int)ponto.getX(), (int)ponto.getY(), (byte) (5 + 0 * 16 + to)); 
	    		//else
	    			//level.setBlock((int)ponto.getX(), (int)ponto.getY(), (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
	    	}
	    	System.out.println("");
	    	
	    	//level.setBlock(xTela1, yTela1, (byte) (5 + 0 * 16 + to)); //bloco usado
	    	//level.setBlock(xTela2, yTela2, (byte) (9 + 0 * 16 + to)); //Pedra
	    	
	    	Juncoes mapa = retornaCaso(pontos, cont_aux);
	    	//Juncoes mapa = null;
	    	
	    	if(mapa != null){	
    		
		    	System.out.print("\tNeste caso: " + mapa.caso + " => ");
		    	for(int i = 0; i < mapa.lista.size(); i++){
		    		System.out.print(mapa.lista.get(i) + ", " );
		    	}
		    	System.out.println("\n\n");
		    	
		    	ArrayList<Point> blocosAlterados = new ArrayList<Point>();		    	
		    	
		    	if(mapa.caso == 1){
		    		mapa.passaListaVariaveis();
		    		yTela1 = mapa.y1;
	    			yTela2 = mapa.y3;
	    			
		    		for(int i = 0; i < pontos.size(); i++){
		    			Point ponto = pontos.get(i);
		    			if((int)ponto.getY() == mapa.y2){		    				
		    				int temp;
		    				//if(mapa.y1 > mapa.y3)
		    					//temp = mapa.y1;
		    				//else
		    					temp = mapa.y3;
		    				
		    				
		    				blocosAlterados.add(new Point((int) ponto.getX(), temp));
		    				for(int y = mapa.y2; y < temp; y++)
		    					level.setBlock((int)ponto.getX(), y,(byte) 0); //retira o bloco setado
		    					//level.setBlock((int)ponto.getX(), y, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
		    				level.setBlock((int)ponto.getX(), temp, (byte) (1 + 8 * 16 + to)); //Chao
		    				//level.setBlock((int)ponto.getX(), temp, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
		    				//level.setBlock((int)ponto.getX(), temp, (byte) (5 + 0 * 16 + to));
		    				
		    				 
		    			}		    			
		    		}	    		
		    		
		    	}else if(mapa.caso == 2){
		    		mapa.passaListaVariaveis();
		    		
		    		yTela1 = mapa.y1;
	    			yTela2 = mapa.y3;
	    			
		    		for(int i = 0; i < pontos.size(); i++){
		    			Point ponto = pontos.get(i);
		    			if((int)ponto.getY() == mapa.y2){
		    				int temp;
		    				if(mapa.y1 < mapa.y3) // (mapa.y1 > mapa.y3)
		    					temp = mapa.y1;
		    				else
		    					temp = mapa.y3;
		    				
		    				blocosAlterados.add(new Point((int) ponto.getX(), temp));
		    				for(int y = mapa.y2; y > temp; y--){
		    					if(testaSeBlocoParede((int)ponto.getX() - 1 , y, to)) level.setBlock((int)ponto.getX() - 1 , y, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
		    					//level.setBlock((int)ponto.getX() - 1 , y, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
		    					level.setBlock((int)ponto.getX(), y, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
		    					if(testaSeBlocoParede((int)ponto.getX() + 1 , y, to)) level.setBlock((int)ponto.getX() + 1, y, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
		    					//level.setBlock((int)ponto.getX() + 1, y, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
		    					
		    					
		    					level.setBlock((int)ponto.getX() - 1, y, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
		    					level.setBlock((int)ponto.getX(), y, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
		    					level.setBlock((int)ponto.getX() + 1, y, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
		    					
		    				}
		    				if(testaSeBlocoParede((int)ponto.getX() - 1 , temp , to))level.setBlock((int)ponto.getX() - 1, temp, (byte) (1 + 8 * 16 + to)); //Chao
		    				//level.setBlock((int)ponto.getX() - 1, temp, (byte) (1 + 8 * 16 + to)); //Chao
		    				level.setBlock((int)ponto.getX(), temp, (byte) (1 + 8 * 16 + to)); //Chao
		    				if(testaSeBlocoParede((int)ponto.getX() + 1 , temp , to)) level.setBlock((int)ponto.getX() + 1, temp, (byte) (1 + 8 * 16 + to)); //Chao
		    				//level.setBlock((int)ponto.getX() + 1, temp, (byte) (1 + 8 * 16 + to)); //Chao
		    				//level.setBlock((int)ponto.getX(), temp, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
		    				
		    				level.setBlock((int)ponto.getX() - 1, temp, (byte) (5 + 0 * 16 + to));
		    				level.setBlock((int)ponto.getX(), temp, (byte) (5 + 0 * 16 + to));
		    				level.setBlock((int)ponto.getX() + 1, temp, (byte) (5 + 0 * 16 + to));
		    				
		    			}
		    		}
		    		
		    		
		    		System.out.println("No caso 2, blocosAlterados.size() = " + blocosAlterados.size());
		    		for (int i = 0 ; i < blocosAlterados.size(); i++){
		    			Point ponto = blocosAlterados.get(i);
		    			//level.setBlock((int)ponto.getX() - 1, (int)ponto.getY(), (byte) (0 + 1 * 16 + to)); //BLOCK_EMPTY
		    			//ponto = blocosAlterados.get(blocosAlterados.size() - 1);
		    			level.setBlock((int)ponto.getX() + 1, (int)ponto.getY(), (byte) (4 + 1 + 1 * 16 + to)); //BLOCK_COIN
		    		}
		    		
		    	}   	
		    	
	    	} */
	    	
	    	
	    	//level.setBlock(xTela2, 5, (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
	    	//level.setBlock(xTela2 + 3, 5, (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
    		//level.setBlock(xTela1, 5, (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
    		//level.setBlock(xTela1 - 3, 5, (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
	    	 level.setBlock(xTela2, 5 , (byte) (1 + 9 * 16));
	    	    		
	    	//System.out.println("\ty1:"+ yTela1 + " y2:" + yTela2);
	    	//colocaPowerUp(xTela2 - 4, xTela2 - 1);
	    	marcadorJuncaoTela(xInicioTela1, xTela1);
	    	//level.setBlock(xTela2 - 1, 5, (byte) (14 + 0 * 16 + to));//Canhao
	    	//level.setBlock(xTela2 - 1, 5, (byte) (11 + 1 * 16 + to));//Lado direito do Cano
    		//level.setBlock(xTela2 - 2, 5, (byte) (10 + 1 * 16 + to));//Lado esquerdo do cano
    		//level.setBlock(xTela1, 5, (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
    		//level.setBlock(xTela1 - 1, 5, (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
	    	//level.setBlock(xTela2 - 1, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    		//level.setBlock(xTela2 - 2, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    	//level.setBlock(xTela2 - 1, 5, (byte) (0 + 1 * 16 + to)); //BLOCK_EMPTY
    		//level.setBlock(xTela2 - 2, 5, (byte) (4 + 2 + 1 * 16 + to)); //BLOCK_POWERUP
    		//level.setBlock(xTela2 - 3, 5, (byte) (4 + 1 + 1 * 16 + to)); //BLOCK_COIN
    		//level.setBlock(xTela2 - 4, 6, (byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
    		//level.setBlock(xTela2 - 2, 5, (byte) (1 + 1 * 16)); //Bloco amarelo com moeda
    		//level.setBlock(xTela2 - 6, 8, (byte) (2 + 2 * 16)); //Moeda
    		//level.setBlock(xTela2 - 4, 7, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
    		//level.setBlock(xTela2 - 5, 7, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
    		//level.setBlock(xTela1, 5, (byte) (2 + 8 * 16 + to));// Quina direita
    		//level.setBlock(xTela2, 5, (byte) (0 + 8 * 16 + to)); //Quina esquerda
    		
    		
    		if(yTela1 == 0  && yTela2 != 0){
    			
    			//level.setBlock(xTela1, yTela2, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    			
    			boolean quinaDireita = false, quinaEsquerda = false, trava = false;
    			
    			for(int y = level.getHeight(); y > 0; y--){
    	    		//Testa se ï¿½ bloco de quina a esquerda ou quina a direita
    	    		if(level.getBlock(xTela1, y) == (byte)(2 + 8 * 16 + to)){
    	    			quinaDireita = true;
    	    			yTela1 = y;	
    	    			//System.out.println("quina direita em " + y);
    	    		}    	    		
    	    		if(level.getBlock(xTela1, y) == (byte) (0 + 8 * 16 + to)){
    	    			quinaEsquerda = true;
    	    			yTela1 = y;
    	    			//System.out.println("quina esquerda em " + y);
    	    		}
    	    	}
    			
    			//System.out.println("y1: " + yTela1 + " y2: " + yTela2);
    			int x = 0;
    			if(quinaEsquerda && !quinaDireita)
    				x = xTela1 + 1;
    			if(!quinaEsquerda && quinaDireita)    				
    				x = xTela1;
    			if(quinaEsquerda && quinaDireita)
    				break;
    			    			
    			if(!quinaEsquerda && !quinaDireita){
    				yTela1 = 15;
    				x = xTela2;
    				trava = true;
    				
    	    		for(int i = yTela2; i <= yTela1; i++){
    	    			level.setBlock(x, i, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
    	    		}
    	    		level.setBlock(x, yTela2, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco
    			}
    				
    			if(yTela1 > yTela2 && !trava){
    				if(quinaEsquerda && !quinaDireita){
    					x--;
    					System.out.println("\tCaso 1-1 em " + z);
	    	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
	    	    		//level.setBlock(x, yTela1, (byte) (3 + 8 * 16 + to)); //Pequena quina superior esquerda
	    	    		//for(int i = yTela2 + 1; i < yTela1; i++){
	    	    		for(int i = yTela2 + 1; i <= yTela1; i++){
	    	    			level.setBlock(x, i, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
	    	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    	    		} 
	    	    		level.setBlock(x, yTela2, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco
	    	    		//level.setBlock(xTela2 - 1, yTela2, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    				}
    				else{
	    	    		System.out.println("\tCaso 1-2 em " + z);
	    	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
	    	    		level.setBlock(x, yTela1, (byte) (3 + 8 * 16 + to)); //Pequena quina superior esquerda
	    	    		//for(int i = yTela2 + 1; i < yTela1; i++){
	    	    		for(int i = yTela2; i <= yTela1; i++){
	    	    			level.setBlock(x, i, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
	    	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    	    		}
	    	    		level.setBlock(x - 1, yTela2, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco
	    	    		//level.setBlock(xTela2 - 1, yTela2, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	    		}
    	    	}
    	    	if(yTela1 < yTela2 && !trava){
    	    		System.out.println("\tCaso 2 em " + z);	
    	    		System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
    	    		level.setBlock(x, yTela2, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita
    	    		for(int i = yTela1 + 1; i < yTela2; i++){
    	    		//for(int i = yTela1; i < yTela2; i++){
    	    			level.setBlock(x, i, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
    	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	    		}
    	    		level.setBlock(x, yTela1, (byte) (2 + 8 * 16 + to)); //Quina superior direita.. Outro lado do buraco
    	    		//level.setBlock(xTela2 - 1 , yTela1, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	    	}
    	    	
    			
    			yTela1 = yTela2;
    		}
    		
    		
    		if(yTela1 != 0  && yTela2 == 0){
    			
    			for(int y = level.getHeight(); y > 0; y--){
    	    		//Testa se ï¿½ bloco de quina a esquerda ou quina a direita
    				if(level.getBlock(xTela2, y) == (byte)(2 + 8 * 16 + to) || level.getBlock(xTela2, y) == (byte) (0 + 8 * 16 + to)){ 
    	    				yTela2 = y;
    	    				System.out.println("\tEntrou no if do yTela1 != 0  && yTela2 == 0. y = " + y);
    	    		}
    	    	}
    			
    			if(yTela1 > yTela2){
    	    		System.out.println("\tCaso 3 em " + z);
    	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
    	    		level.setBlock(xTela2 - 1 , yTela1, (byte) (3 + 8 * 16 + to)); //Pequena quina superior esquerda
    	    		//for(int i = yTela2 + 1; i < yTela1; i++){
    	    		for(int i = yTela2; i <= yTela1; i++){
    	    			level.setBlock(xTela2 - 1, i, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
    	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	    		}
    	    		level.setBlock(xTela2 - 1, yTela2, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco
    	    		//level.setBlock(xTela2 - 1, yTela2, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	    	}
    	    	if(yTela1 < yTela2){
    	    		System.out.println("\tCaso 4 em " + z);	
    	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
    	    		level.setBlock(xTela2 - 1, yTela2, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita
    	    		for(int i = yTela1 + 1; i < yTela2; i++){
    	    		//for(int i = yTela1; i < yTela2; i++){
    	    			level.setBlock(xTela2 - 1, i, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
    	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	    		}
    	    		level.setBlock(xTela2 - 1, yTela1, (byte) (2 + 8 * 16 + to)); //Quina superior direita.. Outro lado do buraco
    	    		//level.setBlock(xTela2 - 1 , yTela1, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	    	}
    			
    			yTela1 = yTela2;
    		}
    		
    		
    		if(yTela1 == 0 && yTela2 == 0){
    			
    			System.out.println("Entrou no caso 0 em " + z);
    			
    			for(int y = level.getHeight(); y > 0; y--){
    		    	if(level.getBlock(xTela1, y) == (byte) (0 + 8 * 16 + to)) //Quina superior esquerda. Outro lado do buraco
    		    	{
    		    		yTela1 = y; 
    		    		//System.out.println("\tEntrou no if(yTela1 == 0) e atualizou yTela1 para " + y);
    		    	}
    		    	if(level.getBlock(xTela2 + 1, y) == (byte) (1 + 8 * 16 + to)){ //Bloco com grama superior
    	    			yTela2 = y;
    	    			//System.out.println("\tEntrou no if(yTela1 == 0) e atualizou yTela2 para" + y);
    	    		}
    			}
    			level.setBlock(xTela2, yTela2, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita
	    		for(int i = yTela1 + 1; i < yTela2; i++){
	    			level.setBlock(xTela2, i, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		}
	    		level.setBlock(xTela2 , yTela1, (byte) (2 + 8 * 16 + to)); //Quina superior direita.. Outro lado do buraco
	    		//level.setBlock(xTela2 - 1 , yTela1, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		
    			yTela2 = yTela1;
    		}
    		
    		
    		    		
	    	if(yTela1 > yTela2){
	    		System.out.println("\tCaso 5 em " + z);
	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
	    		level.setBlock(xTela2 , yTela1, (byte) (3 + 8 * 16 + to)); //Pequena quina superior esquerda
	    		for(int i = yTela2 + 1; i < yTela1; i++){
	    		//for(int i = yTela2; i <= yTela1; i++){
	    			level.setBlock(xTela2 , i, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		}
	    		level.setBlock(xTela2 , yTela2, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco
	    		//level.setBlock(xTela2 - 1, yTela2, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    	}
	    	if(yTela1 < yTela2){
	    		System.out.println("\tCaso 6 em " + z);	
	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
	    		level.setBlock(xTela2 , yTela2, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita
	    		for(int i = yTela1 + 1; i < yTela2; i++){
	    		//for(int i = yTela1; i < yTela2; i++){
	    			level.setBlock(xTela2 , i, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		}
	    		level.setBlock(xTela2  , yTela1, (byte) (2 + 8 * 16 + to)); //Quina superior direita.. Outro lado do buraco
	    		//level.setBlock(xTela2 - 1 , yTela1, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    	}
	    	 
	    	
	    	
    	}
    	
    	
    }
    
    /**
     * Mï¿½todo para nivelar o chï¿½o de um trecho da fase
     * @param xTela1: valor da coordenada x da tela 1, ou seja, a mais da esquerda
     * @param xTela2: valor da coordenada x na tela 2, ou seja a mais da direita
     */
    public void nivelarTrechoTela(int xTela1, int xTela2, int to){
    	
    	
    // *************** Correï¿½ï¿½o da Tela 1 ********************************************************
    	int y1 = level.getHeight();
    	int x1 = xTela1 - 3; //Tem que ser no mï¿½ximo 2, para evitar erros onde existem buracos
    	
    	while(y1 == level.getHeight() && x1 != xTela1){
	    	for(int y = level.getHeight() - 1; y >= 0; y--){	    			
	    		if(
	    			level.getBlock(x1, y) == (byte) (1 + 8 * 16 + to) ||  //chï¿½o
	    			level.getBlock(x1, y) == (byte) (2 + 8 * 16 + to) || // Quina direita
	    			level.getBlock(x1, y) == (byte) (0 + 8 * 16 + to)    // Quina esquerda 					
	    		  ){
	    			y1 = y;
	    		}    		
	    	}
	    	
	    	if(y1 == level.getHeight())
	    		x1++;
    	}
    	

		//level.setBlock(x1, y1, (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
    	
    	if(level.getBlock(x1, y1) == (byte) (0 + 8 * 16 + to))  // Quina esquerdar
    		x1++;    	
    	
    	for(int x = x1; x <= xTela1; x++){
    		for(int y = 0; y <= y1; y++){
    			if(
     				   level.getBlock(x, y) == (byte) (1 + 9 * 16 + to)  //GROUND (Bloco marron)
     				|| level.getBlock(x, y) == (byte) (1 + 8 * 16 + to)  //chï¿½o
     				|| level.getBlock(x, y) == (byte) (2 + 8 * 16 + to)  // Quina direita
     				|| level.getBlock(x, y) == (byte) (0 + 8 * 16 + to)  // Quina esquerda 
     				|| level.getBlock(x, y) == (byte) (3 + 11 * 16 + to)  //Pequena quina inferior esquerda
     				|| level.getBlock(x, y) == (byte) (3 + 10 * 16 + to) //Pequena quina inferior direita
     				|| level.getBlock(x, y) == (byte) (3 + 8 * 16 + to)  //Pequena quina superior esquerda
     				|| level.getBlock(x, y) == (byte) (3 + 9 * 16 + to)  //Pequena quina superior direita	
     				|| level.getBlock(x, y) == (byte) (2 + 9 * 16 + to)  //Bloco de Grama lateral na direita
     				|| level.getBlock(x, y) == (byte) (0 + 9 * 16 + to)  //Bloco com grama lateral na esquerda
     			)
    			level.setBlock(x, y,(byte) 0); //retira o bloco setado
    		}
    	}
    	
    	for(int x = x1; x <= xTela1; x++){
    		for(int y = level.getHeight() - 1; y >= y1; y--){
    			level.setBlock(x, y, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
    			//level.setBlock(x, y, (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
    		}
    		level.setBlock(x, y1, (byte) (1 + 8 * 16 + to)); //Chao
    		//level.setBlock(x, y1, (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
    	}
    	
    // *************** Fim da Correï¿½ï¿½o da Tela 1 *************************************************
    	
    // *************** Correï¿½ï¿½o da Tela 2 ********************************************************
    	
    	int y2 = level.getHeight();
    	int x2 = xTela1 + 3;
    	boolean quinaDireita = false;
    	
    	while(y2 == level.getHeight() && x2!= xTela1){
	    	for(int y = level.getHeight() - 1; y >= 0; y--){	    			
	    		if(
	    			level.getBlock(x2, y) == (byte) (1 + 8 * 16 + to) ||  //chï¿½o
	    			level.getBlock(x2, y) == (byte) (2 + 8 * 16 + to) || // Quina direita
	    			level.getBlock(x2, y) == (byte) (0 + 8 * 16 + to)    // Quina esquerda 					
	    		  ){
	    			y2 = y;
	    			if(level.getBlock(x2, y) == (byte) (2 + 8 * 16 + to))
	    				quinaDireita = true;
	    		}    		
	    	}
	    	
	    	if(y2 == level.getHeight())
	    		x2--;
    	}
    	
    	//level.setBlock(x2, y2, (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
    	
    	//if(level.getBlock(x2 + 1, y2) == (byte) (2 + 8 * 16 + to))
    		//x2--;    	
    	
    	for(int x = xTela2; x <= x2; x++){
    		for(int y = 0; y <= y2; y++){    			
    			if(
    				   level.getBlock(x, y) == (byte) (1 + 9 * 16 + to)  //GROUND (Bloco marron)
    				|| level.getBlock(x, y) == (byte) (1 + 8 * 16 + to)  //chï¿½o
    				|| level.getBlock(x, y) == (byte) (2 + 8 * 16 + to)  // Quina direita
    				|| level.getBlock(x, y) == (byte) (0 + 8 * 16 + to)  // Quina esquerda 
    				|| level.getBlock(x, y) == (byte) (3 + 11 * 16 + to)  //Pequena quina inferior esquerda
    				|| level.getBlock(x, y) == (byte) (3 + 10 * 16 + to) //Pequena quina inferior direita
    				|| level.getBlock(x, y) == (byte) (3 + 8 * 16 + to)  //Pequena quina superior esquerda
    				|| level.getBlock(x, y) == (byte) (3 + 9 * 16 + to)  //Pequena quina superior direita	
    				|| level.getBlock(x, y) == (byte) (2 + 9 * 16 + to)  //Bloco de Grama lateral na direita
    				|| level.getBlock(x, y) == (byte) (0 + 9 * 16 + to)  //Bloco com grama lateral na esquerda
    			)
    			level.setBlock(x, y,(byte) 0); //retira o bloco setado
    		}
    	}
    	
    	for(int x = xTela2; x <= x2; x++){
    		for(int y = level.getHeight() - 1; y >= y2; y--){
    			level.setBlock(x, y, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
    			//level.setBlock(x, y, (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
    		}
    		level.setBlock(x, y2, (byte) (1 + 8 * 16 + to)); //Chao
    		//level.setBlock(x, y2, (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
    	}
    	
    // *************** Fim da Correï¿½ï¿½o da Tela 2 *************************************************
    	
    	//level.setBlock(x1, y1, (byte) (10 + 0 * 16 + to));//Entrada direita do Cano
		//level.setBlock(x2, y2, (byte) (11 + 0 * 16 + to));//Entrada esquerda do Cano
    	
    }
    
    /**
     * 
     * @param x
     * 	Valor do indice X
     * @param y
     * 	Valor do indice Y
     * @param to
     *  Parï¿½metro para escolha do tipo da tela
     * @return
     * 	True para se encontra algum tipo de parede /n
     *  False para caso nï¿½o encontre
     */
    public boolean testaSeBlocoParede(int x, int y, int to){
    	if( level.getBlock(x, y) == (byte) (3 + 11 * 16 + to)        //Pequena quina inferior esquerda
    			|| level.getBlock(x, y) == (byte) (3 + 10 * 16 + to) //Pequena quina inferior direita
    			|| level.getBlock(x, y) == (byte) (3 + 8 * 16 + to)  //Pequena quina superior esquerda
    			|| level.getBlock(x, y) == (byte) (3 + 9 * 16 + to)  //Pequena quina superior direita	
    			|| level.getBlock(x, y) == (byte) (2 + 9 * 16 + to)  //Bloco de Grama lateral na direita
    			|| level.getBlock(x, y) == (byte) (0 + 9 * 16 + to)  //Bloco com grama lateral na esquerda
				|| level.getBlock(x, y) == (byte) (0 + 8 * 16 + to)  //Quina superior esquerda.
				|| level.getBlock(x, y) == (byte) (2 + 8 * 16 + to)  //Quina superior direita
    			){
    		return true;
    	}
    	return false;
    }
    
    public Juncoes retornaCaso(ArrayList<Point> array, int numerador){
    	
    	System.out.print("Executando retornaCaso para o caso " + numerador + ". ");
    	
    	ArrayList<Integer> listaAux = new ArrayList<Integer>();
    	Point aux  = array.get(0); 
    	int y = (int) aux.getY();    	
    	listaAux.add(y);
    	
    	for(int i = 1; i < array.size(); i++){
    		aux  = array.get(i);
    		if(y != (int) aux.getY()){
    			y = (int) aux.getY();
    			listaAux.add(y);
    		}    			
    	}
    	
    	System.out.print("listaAux.size()= " +  listaAux.size());
    	//Map<Integer, ArrayList<Integer>> mapa = new HashMap<Integer, ArrayList<Integer>>();
    	Juncoes mapa = new Juncoes();
    	
    	if(listaAux.size() == 2)
    		/**
    		 * Caso 0 equivale a nï¿½o existir "verruga"
    		 */
    		mapa.put(0, listaAux);
    	else{
    		if(listaAux.size() == 3){
    			int n1 , n2, n3;
    			
    			n1 = listaAux.get(0);
    			
    			n2 = listaAux.get(1);
    			
    			n3 = listaAux.get(2); 			
    			
    			System.out.print(". n1 = " + n1 + ". n2 = " + n2 + ". n3 = " + n3);
    			if(n1 > n2 && n2 < n3)
    				/**
    	    		 * Caso 1 equivalente a verruga para cima
    	    		 */
    				mapa.put(1, listaAux);
    			if(n1 < n2 && n2 > n3)
    			/**
	    		 * Caso 2 equivalente a verruga pra baixo
	    		 */
    				mapa.put(2, listaAux);
    		}
    		else{
    			System.err.println("Nenhum dos casos. Deveria ser: " + listaAux.size());
    			return null;
    		}
    		
    	}
    	
    	System.out.println(". mapa.lista.size()= " + mapa.lista.size());
    	//System.out.println("\t...Caso identificado: " + mapa.caso + ". Tamanho do array retornado = " + mapa.lista.size() + ". Deveria ser = " + listaAux.size());
    	return mapa;
    }
    
    public void ordenaArrayListPoints(ArrayList<Point> array){
    	ArrayList<Point> arrayAux = new ArrayList<Point>();
    	Point pointAux, pointAux2;
    	arrayAux.add(array.get(0));
    	
    	for(int i = 1; i < array.size(); i++){
    		pointAux = arrayAux.get(i - 1);
    		pointAux = array.get(i);
    	}
    }
    
    public void marcadorJuncaoTela(int larguraMin, int larguraMax){
    	
    	//System.out.println("\nLevelGenetator - marcadorJuncaoTela(" + larguraMin +", "+ larguraMax + ")");

    	Random rand = new Random();
    	/*    	
    	level.setBlock(x - 1, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
		//level.setBlock(xTela2 - 2, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	//level.setBlock(xTela2 - 1, 5, (byte) (0 + 1 * 16 + to)); //BLOCK_EMPTY
		//level.setBlock(xTela2 - 2, 5, (byte) (4 + 2 + 1 * 16 + to)); //BLOCK_POWERUP
		//level.setBlock(xTela2 - 3, 5, (byte) (4 + 1 + 1 * 16 + to)); //BLOCK_COIN
		//level.setBlock(xTela2 - 4, 6, (byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
		level.setBlock(x - 2, 5, (byte) (1 + 1 * 16)); //Bloco amarelo com moeda
		//level.setBlock(xTela2 - 6, 8, (byte) (2 + 2 * 16)); //Moeda
		//level.setBlock(xTela2 - 4, 7, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
		//level.setBlock(xTela2 - 5, 7, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)

		/* 
    	 * (byte) (14 + 0 * 16 + to));//Canhao
    	 * (byte) (11 + 1 * 16 + to));//Lado direito do Cano
    	 * (byte) (10 + 1 * 16 + to));//Lado esquerdo do cano
    	 * (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
    	 * (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
    	 */

    	boolean encontrouPiso = false; //Variavel para encontrar um piso. Garante nao ser um Buraco
    	//System.out.println("\nlaguraMin: " + larguraMin + " larguraMax: " + larguraMax);
    	//int x = laguraMin + rand.nextInt(10);
    	int x = larguraMax;
    	//int x = 25;
    	//System.out.println("x sorteado: " + x);


    	int floor = 0;

    	while(!encontrouPiso){
    		for(int y = height; y > 0; y--){
    			if(level.getBlock(x, y) == (byte) (1 + 8 * 16)){ //Bloco com grama superior
    				floor = y;
    				encontrouPiso = true;
    				break;
    			}  		
    		}
    		x--;
    		if(x <= larguraMin){	    	
    			System.out.println("Saindo do colocaPowerUp");    	
    			return;
    		}

    	}

    	int cont = 0;
    	if(x == larguraMax - 1)
    		for(int y = x; y > x - 3; y --){
    			//System.out.println("y: " + y);
    			if(level.getBlock(y, floor) == (byte) (1 + 8 * 16)){ //Bloco com grama superior)
    				cont++;    		
    			}

    		}

    	//System.out.println("Quantidade de blocos extras: " + cont);

    	int quantidade = rand.nextInt(cont + 1);;
    	//if(cont == 0) quantidade = 1;
    	//else quantidade = 1 + rand.nextInt(cont + 1); //Quantidade de blocos a serem inseridos
    	
    	//System.out.println("Quantidade de blocos possiveis: " + cont + "\nQuantidade de blocos a ser colocados: " + quantidade);
    	class Posicao{
    		int x, y;        
    	}

    	ArrayList<Posicao> listaPosicoes = new ArrayList<Posicao>(); //Lista contendo todas as coordenadas do mapa que recebera algum bloco
    	Posicao posicao;    	
    	x++;
    	boolean b;
    	int y = floor - 4;
    	
    	if (floor - 5 > 0)
    	{
    		//System.out.println("\nExecutando mï¿½todo LevelGenerator=>marcadorJuncaoTela");
    		int temp;
    		for(int bloco=0; bloco < quantidade; bloco++){
    			temp = x - bloco;
    			if(insereBloco(temp, y)){
    				posicao = new Posicao();
    				posicao.x = temp;
    				posicao.y = y;
    				listaPosicoes.add(posicao);
    				//System.out.println("\t\t\tinsereBloco(" + temp  + ", " +  y + ") retornou true");
    			}
    			else{
    				
    				//System.out.println("\t\t\tinsereBloco(" + temp  + ", " +  y + ") retornou falso");
    				break;
    			}
    		}

    		
    		
    		/*
            if(quantidade == 1){ 
            	if(rand.nextInt(2) == 0){

            		if(insereBloco(x, y))level.setBlock(x, y, (byte) (4 + 2 + 1 * 16));
            		else if(insereBloco(x, y - 1))level.setBlock(x, y - 1, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
            		else System.out.println("Nao foi possivel inserir um bloco em " + x + ", " + y);
            	}
            	else{

            		if(insereBloco(x, y))level.setBlock(x, y, (byte) (4 + 1 + 1 * 16));
            		else if(insereBloco(x, y - 1))level.setBlock(x, y - 1, (byte) (4 + 1 + 1 * 16)); //Block_Coin
            		else System.out.println("Nao foi possivel inserir um bloco em " + x + ", " + y);
            	}

            }
            else{
            	for (int q = 0, x_corrente = x; q < quantidade; q++)
                {
                   if (rand.nextInt(3) == 0)
                   {
                            if (rand.nextInt(4) == 0)
                            {
                            	if(insereBloco(x_corrente, y))level.setBlock(x_corrente, y, (byte) (4 + 2 + 1 * 16));
                        		else if(insereBloco(x_corrente, y - 1))level.setBlock(x_corrente, y - 1, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
                        		else System.out.println("Nao foi possivel inserir um bloco em " + x + ", " + y);
                                x_corrente--;
                            }
                            else
                            {
                            	if(insereBloco(x_corrente, y))level.setBlock(x_corrente, y, (byte) (4 + 1 + 1 * 16));
                        		else if(insereBloco(x_corrente, y - 1))level.setBlock(x_corrente, y - 1, (byte) (4 + 1 + 1 * 16)); //Block_Coin
                        		else System.out.println("Nao foi possivel inserir um bloco em " + x + ", " + y);
                            	x_corrente--;
                            }
                        }
                        else if (rand.nextInt(3) == 0)
                        {
                            if (rand.nextInt(4) == 0)
                            {
                            	if(insereBloco(x_corrente, y))level.setBlock(x_corrente, y, (byte) (2 + 1 * 16));
                        		else if(insereBloco(x_corrente, y - 1))level.setBlock(x_corrente, y - 1, (byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
                        		else System.out.println("Nao foi possivel inserir um bloco em " + x + ", " + y);
                                x_corrente--;
                            }
                            else
                            {
                            	if(insereBloco(x_corrente, y))level.setBlock(x_corrente, y, (byte) (1 + 1 * 16));
                        		else if(insereBloco(x_corrente, y - 1))level.setBlock(x_corrente, y - 1, (byte) (1 + 1 * 16)); //Bloco amarelo com Moeda
                        		else System.out.println("Nao foi possivel inserir um bloco em " + x + ", " + y);
                            	x_corrente--;
                            }
                        }
                        else
                        {
                        	if(insereBloco(x_corrente, y))level.setBlock(x_corrente, y, (byte) (0 + 1 * 16));
                    		else if(insereBloco(x_corrente, y - 1))level.setBlock(x_corrente, y - 1, (byte) (0 + 1 * 16)); //Bloco vazio
                    		else System.out.println("Nao foi possivel inserir um bloco em " + x + ", " + y);
                        	x_corrente--;
                        }
                }
            	}*/
    	}
    	
    	int prob;
    	//System.out.print("\t\t\t\tlistaPosicoes.size(): " + listaPosicoes.size() + " = ");
    	for(int q = 0; q < listaPosicoes.size(); q++){    		
    		Posicao p = listaPosicoes.get(q);
    		//System.out.print(p.x + ", "  + p.y +  " ");
    		//level.setBlock(p.x, p.y, (byte) (0 + 1 * 16)); //Bloco vazio
    		//level.setBlock(p.x, p.y, (byte) (11 + 0 * 16)); //Entrada direita do Cano
    		
    		
    		prob = rand.nextInt(2);
        	if(prob == 0){
        		prob = rand.nextInt(3);
        		if(prob == 2){
        			if(num_powerUp < 2){
        				level.setBlock(p.x, p.y,(byte) (4 + 2 + 1 * 16)); //Block PowerUp
        				num_powerUp++;
        			}
        			else
        				level.setBlock(p.x, p.y, (byte) (4 + 1 + 1 * 16)); //Block_Coin        						
        		}
        		else{
        			level.setBlock(p.x, p.y, (byte) (4 + 1 + 1 * 16)); //Block_Coin        			
        		}
        	}
        	else{        		
        		prob = rand.nextInt(3);
        		if(prob == 2){
        			if(num_powerUp < 2){
        				level.setBlock(p.x, p.y,(byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
        				num_powerUp++;
        			}
        			else
        				level.setBlock(p.x, p.y, (byte) (1 + 1 * 16)); //Bloco amarelo com Moeda        						
        		}
        		else{
        			prob = rand.nextInt(3);
        			if(prob == 0)
        				level.setBlock(p.x, p.y, (byte) (4 + 1 + 1 * 16)); //Block_Coin 
        			else
        				level.setBlock(p.x, p.y,(byte) (0 + 1 * 16)); //Bloco vazio
        		}
        		
        	}
        	
    	}
    	//System.out.println();
    	
    	

    	/*
        if (floor - 4 > 0)
        {
            if(quantidade == 1){ 
            	if(rand.nextInt(2) == 0){
            		b = level.getBlock(x, floor - 4) == (byte) (14 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (10 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (11 + 0 * 16);
            		if(b)level.setBlock(x, floor - 5, (byte) (4 + 2 + 1 * 16));
            		else level.setBlock(x, floor - 4, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
            	}
            	else{
            		b = level.getBlock(x, floor - 4) == (byte) (14 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (10 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (11 + 0 * 16);
            		if(b)level.setBlock(x, floor - 5, (byte) (4 + 1 + 1 * 16));
            		else level.setBlock(x, floor - 4, (byte) (4 + 1 + 1 * 16)); //Block_Coin
            	}

            }
            else{
            	for (int q = 0, x_corrente = x; q < quantidade; q++)
                {
                   if (rand.nextInt(3) == 0)
                   {
                            if (rand.nextInt(4) == 0)
                            {
                            	b = level.getBlock(x, floor - 4) == (byte) (14 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (10 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (11 + 0 * 16);
                        		if(b)level.setBlock(x_corrente, floor - 5, (byte) (4 + 2 + 1 * 16));
                        		else level.setBlock(x_corrente, floor - 4, (byte) (4 + 2 + 1 * 16)); //Block PowerUp
                                num_powerUp++;
                                x_corrente--;
                            }
                            else
                            {
                            	b = level.getBlock(x, floor - 4) == (byte) (14 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (10 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (11 + 0 * 16);
                        		if(b)level.setBlock(x_corrente, floor - 5, (byte) (4 + 1 + 1 * 16));
                        		else level.setBlock(x_corrente, floor - 4, (byte) (4 + 1 + 1 * 16)); //Block_Coin
                                x_corrente--;
                            }
                        }
                        else if (rand.nextInt(3) == 0)
                        {
                            if (rand.nextInt(4) == 0)
                            {
                            	b = level.getBlock(x, floor - 4) == (byte) (14 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (10 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (11 + 0 * 16);
                        		if(b)level.setBlock(x_corrente, floor - 5, (byte) (2 + 1 * 16));
                        		else level.setBlock(x_corrente, floor - 4, (byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
                                num_powerUp++;
                                x_corrente--;
                            }
                            else
                            {
                            	b = level.getBlock(x, floor - 4) == (byte) (14 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (10 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (11 + 0 * 16);
                        		if(b)level.setBlock(x_corrente, floor - 5, (byte) (1 + 1 * 16));
                        		else level.setBlock(x_corrente, floor - 4, (byte) (1 + 1 * 16)); //Bloco amarelo com Moeda
                                x_corrente--;
                            }
                        }
                        else
                        {
                        	b = level.getBlock(x, floor - 4) == (byte) (14 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (10 + 0 * 16) || level.getBlock(x, floor - 4) == (byte) (11 + 0 * 16);
                    		if(b)level.setBlock(x_corrente, floor - 5, (byte) (0 + 1 * 16));
                    		else level.setBlock(x_corrente, floor - 4, (byte) (0 + 1 * 16)); //Bloco vazio
                            x_corrente--;
                        }
                }
            	}
            }
    	 */

    }

    /* Mï¿½todo para testar se ï¿½ possï¿½vel inserir um bloco 
     * Testa se a posicao x, y estï¿½ livre
     * */
    public boolean testaBloco(int x, int y){
    	    	
    	/*if(
    		level.getBlock(x, y) == (byte) (14 + 0 * 16)//Canhao
    		|| level.getBlock(x, y) == (byte) (11 + 1 * 16)//Lado direito do Cano
    		|| level.getBlock(x, y) == (byte) (10 + 1 * 16)//Lado esquerdo do cano
    		|| level.getBlock(x, y) == (byte) (11 + 0 * 16)//Entrada direita do Cano
    		|| level.getBlock(x, y) == (byte) (10 + 0 * 16)//Entrada esquerda do Cano
    	)
    	return false;*/
    	//level.setBlock(xTela2 - 1, 5, (byte) (14 + 0 * 16 + to));//Canhao
    	//level.setBlock(xTela2 - 1, 5, (byte) (11 + 1 * 16 + to));//Lado direito do Cano
		//level.setBlock(xTela2 - 2, 5, (byte) (10 + 1 * 16 + to));//Lado esquerdo do cano
		//level.setBlock(xTela1, 5, (byte) (11 + 0 * 16 + to));//Entrada direita do Cano
		//level.setBlock(xTela1 - 1, 5, (byte) (10 + 0 * 16 + to));//Entrada esquerda do Cano
    	//level.setBlock(xTela2 - 1, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
		//level.setBlock(xTela2 - 2, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    	//level.setBlock(xTela2 - 1, 5, (byte) (0 + 1 * 16 + to)); //BLOCK_EMPTY
		//level.setBlock(xTela2 - 2, 5, (byte) (4 + 2 + 1 * 16 + to)); //BLOCK_POWERUP
		//level.setBlock(xTela2 - 3, 5, (byte) (4 + 1 + 1 * 16 + to)); //BLOCK_COIN
		//level.setBlock(xTela2 - 4, 6, (byte) (2 + 1 * 16)); //Bloco amarelo com PowerUp
		//level.setBlock(xTela2 - 2, 5, (byte) (1 + 1 * 16)); //Bloco amarelo com moeda
		//level.setBlock(xTela2 - 6, 8, (byte) (2 + 2 * 16)); //Moeda
		//level.setBlock(xTela2 - 4, 7, (byte) (1 + 9 * 16 + to)); //GROUND (Bloco marron)
		//level.setBlock(xTela2 - 5, 7, (byte) (9 + 0 * 16 + to)); //ROCK(Rocha, pedra)
		//level.setBlock(xTela1, 5, (byte) (2 + 8 * 16 + to));// Quina direita
		//level.setBlock(xTela2, 5, (byte) (0 + 8 * 16 + to)); //Quina esquerda
    	
    	if(level.getBlock(x, y) == (byte) (14 + 0 * 16)){ System.out.println("Canhao em " + x + ", " + y);/*Canhao*/ return false; }
        if(level.getBlock(x, y) == (byte) (11 + 1 * 16)){ System.out.println("Lado direito do Cano em " + x + ", " + y);/*Lado direito do Cano*/ return false; }
        if(level.getBlock(x, y) == (byte) (10 + 1 * 16)){ System.out.println("Lado esquerdo do cano em " + x + ", " + y);/*Lado esquerdo do cano*/ return false; }
        if(level.getBlock(x, y) == (byte) (11 + 0 * 16)){ System.out.println("Entrada direita do Cano em " + x + ", " + y);/*Entrada direita do Cano*/ return false; }
        if(level.getBlock(x, y) == (byte) (10 + 0 * 16)){ System.out.println("Entrada esquerda do Cano em " + x + ", " + y);/*Entrada esquerda do Cano*/ return false; };
        if(level.getBlock(x, y) == (byte) (0 + 1 * 16)){ System.out.println("Blocos amarelos em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (0 + 1 * 16)){ System.out.println("Blocos amarelos em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (0 + 1 * 16)){ System.out.println("BLOCK_EMPTY em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (4 + 2 + 1 * 16)){ System.out.println("BLOCK_POWERUP em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (4 + 1 + 1 * 16)){ System.out.println("BLOCK_COIN em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (2 + 1 * 16)){ System.out.println("Bloco amarelo com PowerUp em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (1 + 1 * 16)){ System.out.println("Bloco amarelo com moeda em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (9 + 0 * 16)){ System.out.println("ROCK(Rocha, pedra) em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (2 + 8 * 16)){ System.out.println("Quina direita em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (0 + 8 * 16)){ System.out.println("Quina esquerda em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (3 + 11 * 16)){ System.out.println("Pequena quina inferior esquerda em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (3 + 10 * 16)){ System.out.println("Pequena quina inferior direita em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (1 + 8 * 16)){ System.out.println("Bloco com grama superior em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (2 + 9 * 16)){ System.out.println("Bloco de Grama lateral na direita em " + x + ", " + y); return false; };
        if(level.getBlock(x, y) == (byte) (0 + 9 * 16)){ System.out.println("Bloco com grama lateral na esquerdaem " + x + ", " + y); return false; };
        
        //System.out.println("\t\ttestaBloco(" +  x + ", " + y + ") retornando true");
    	return true;
    }
    
    /*
     * Testa se os vizinhos do ponto estï¿½o livres
    */
    public boolean insereBloco(int x, int y){
    	
    	//System.out.println("\tExecutando mï¿½todo LevelGenerator=>insereBloco(" + x + ", " + y + ")");    	
    	
    	/*if(!testaBloco(x, y) ||
    			!testaBloco(x + 1, y) ||
    			!testaBloco(x - 1, y) ||
    			!testaBloco(x, y + 1) ||
    			!testaBloco(x, y - 1) ||
    			!testaBloco(x + 1, y + 1) ||
    			!testaBloco(x + 1, y + 2) ||
    			!testaBloco(x + 1, y - 1)
    			)*/
    		if(testaBloco(x, y))
        			if(testaBloco(x + 1, y))
        				if(testaBloco(x - 1, y))
        					if(testaBloco(x, y + 1))
        						if(testaBloco(x, y - 1))
        							if(testaBloco(x + 1, y + 1))
        									if(testaBloco(x + 1, y + 2))
        										if(testaBloco(x + 1, y - 1))return true;
        										else return false;
        									else return false;
        							else return false;
        						else return false;
        					else return false;
        				else return false;
        			else return false;
    		else return false;
        			
    	
    }
    
    private void salvaInfoTela(Level level){
    	
    	InformacoesTelas info = new InformacoesTelas();
    	
    	info.setTagPlatform(VariaveisGlobais.tag_platform);
    	info.setTagStraigth(VariaveisGlobais.tag_straigth);
    	info.setTagHillStraigth(VariaveisGlobais.tag_hill_straigth);
    	info.setTagTubes(VariaveisGlobais.tag_tubes);
    	info.setTagJump(VariaveisGlobais.tag_jump);
    	info.setTagCannos(VariaveisGlobais.tag_cannos);
    	
        info.setQuantBuracos(cont_buracos);
        info.setQuantCanhoes(cont_canhoes);
        info.setQuantMoedas(cont_coin);
        for(int i = 0; i < level.tam_spritePrimitivo; i++)
        	info.addInimigo(level.spritePrimitivo[i]);        
        
        try {
			info.salvaInfoTela("infoTela", info);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    private Level conectaTelas(Level level){
    	
    	ArrayList<String> nomeTelas = new ArrayList<String>();
    	ConectorDeTelas fase = new ConectorDeTelas();	
    	fase.lerArquivosDiretorio(nomeTelas);
    	
    	String telasFase = "";
    	//System.out.print("Fase: ");
    	for(int i=0; i< nomeTelas.size(); i++){
    		//System.out.print(nomeTelas.get(i) + " ");
    		telasFase += nomeTelas.get(i) + " ";
    	}
    	//System.out.println();
    	//System.out.println("telasFase: " + telasFase);
    	
    	ArrayList<Level> listaTelas = new ArrayList<Level>();
    	String diretorio = "";
    	
    	FileInputStream fis;
    	
		try {
			for(int i=0; i < nomeTelas.size(); i++){
				diretorio = "telas/" + nomeTelas.get(i);
				//System.out.println(diretorio);
				fis = new FileInputStream(diretorio);
				DataInputStream dis = new DataInputStream(fis);
				//System.out.println("\n\tRetornando Tela");
				//Level level2 = Level.load(dis);
				listaTelas.add(Level.load(dis));
			}	
			return fase.conectaTelas(listaTelas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		
    	
        	
    		
    		//***************************************************
        	return level;
        	
        
    }
    
    
    
    protected Level retornaTela(Level level, String nomeTela){    	
    	
    	FileInputStream fis;
		try {
			fis = new FileInputStream(nomeTela);
			DataInputStream dis = new DataInputStream(fis);			
			level = Level.load(dis);
			
			//System.out.println("\nCARREGADO COM SUCESSO\n\tRetornando Tela");
			return level;
			
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		System.out.println("\nFALHOU AO CARREGAR\n\tRetornando");
		return level;
		
    }
    
    private void salvaTela(Level tela, String nome){
    	//nome = nome+".tel";
    	FileOutputStream fos;
		try {
			fos = new FileOutputStream(nome);
			DataOutputStream dos = new DataOutputStream(fos);
			level.save(dos);
			System.out.println("\n\tFase salva com sucesso");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private Level controiLevel(Level level){
    	
    	ArrayList<String> nomeTelas = new ArrayList<String>();
    	MedidorDeDificuldade medidor = new MedidorDeDificuldade();
    	try {
			medidor.montaTabelaDificuldade();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	//System.out.print("FASE: ");
		for (int i=0; i< medidor.getQuantidadeTelas(); i++){
			nomeTelas.add(medidor.retornaTelaDoBalde(i));
			//System.out.print(nomeTelas.get(i) + " ");
		}
    	//System.out.println();
    	//System.out.print("FASE: ");
    	//for (int i=0; i< nomeTelas.size(); i++){
			//System.out.print(nomeTelas.get(i) + " ");
		//}
    	
    	ConectorDeTelas fase = new ConectorDeTelas();	
    	//fase.lerArquivosDiretorio(nomeTelas);
    	ArrayList<Level> listaTelas = new ArrayList<Level>();
    	String diretorio = "";
    	
    	FileInputStream fis;
    	
		try {
			for(int i=0;i < nomeTelas.size(); i++){
				diretorio = "telasEinfo/Telas/" + nomeTelas.get(i);
				fis = new FileInputStream(diretorio);
				DataInputStream dis = new DataInputStream(fis);
				//System.out.println("\n\tRetornando Tela");
				//Level level2 = Level.load(dis);
				listaTelas.add(Level.load(dis));
			}	
			return fase.conectaTelas(listaTelas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	        	
    		
    		//***************************************************
       System.out.println("Retornando Level Default!"); 	
       return conectaTelas(level);
        	
        
    }
    
    private Level controiLevelPorFuncao(Level level, EquacaoFases equacaoPrincipal){
    	
    	//System.out.println("Valores da funcao: \na: " + equacaoPrincipal.getA() + " b: " + equacaoPrincipal.getB() + " c: " + equacaoPrincipal.getC());
    	
    	ArrayList<String> nomeTelas = new ArrayList<String>();
    	MedidorDeDificuldade medidor = new MedidorDeDificuldade();
    	
    	try {
			//medidor.montaTabelaDificuldade();
    		medidor = medidor.carregaTabelaTelas("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		    	
    	
    	int dificuldade;
    	//ClassEquacoes funcao = new ClassEquacoes();
    	//EquacaoFases equacao = new Parabola(-1/8, 3/3, 3);  /*-x^2/8 + 3x//2 + 3 */ equacaoPrincipal
    	//EquacaoFases equacao = equacaoPrincipal;   	
    	
    	//System.out.print("FASE: ");
    	//String nomeTela = "";
    	
		//for (int i=0; i< medidor.getQuantidadeTelas(); i++){
    	for (int i=0; i < NUM_TELAS_POR_FASE; i++){
			//dificuldade = (int)equacao.resultadoFuncao(i);
			dificuldade = (int)equacaoPrincipal.resultadoFuncao(i);
			//nomeTela = (medidor.retornaTelaEspecifica(dificuldade, nomeTelas));
			nomeTelas.add(medidor.retornaTelaEspecifica(dificuldade, nomeTelas));
			System.out.print(i + ": " + nomeTelas.get(i) + "(" + dificuldade +")   " );
		}
    	//System.out.println();
    	
    	DadosFormulario salvaTelasFase = DadosFormulario.getInstancia();
		salvaTelasFase.setListaTelasFase(nomeTelas);
		
		//ArrayList lista = salvaTelasFase.getListaTelasFase();
		/*
		 System.out.print("FASE salva no Formulario de Dados: ");
		for (int i=0; i< medidor.getQuantidadeTelas(); i++){
			System.out.print(lista.get(i) + " ");
		}
    	System.out.println();
    	*/
    	ConectorDeTelas fase = new ConectorDeTelas();	
    	//fase.lerArquivosDiretorio(nomeTelas);
    	ArrayList<Level> listaTelas = new ArrayList<Level>();
    	String diretorio = "";
    	
    	FileInputStream fis;
    	
		try {
			for(int i=0;i < nomeTelas.size(); i++){
				//diretorio = "telasEinfo/Telas/" + nomeTelas.get(i);
				diretorio = "TelasSelecionadas/Telas/" + nomeTelas.get(i);
				fis = new FileInputStream(diretorio);
				DataInputStream dis = new DataInputStream(fis);
				//System.out.print(nomeTelas.get(i) + " ");
				//Level level2 = Level.load(dis);
				listaTelas.add(Level.load(dis));
			}
			System.out.println();
			
			return fase.conectaTelas(listaTelas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		
    	    		
    		//***************************************************
        System.out.println("Retornando Default");	
		return conectaTelas(level);       	
        
    }

    private Level createLevelMetrics(long seed, int difficulty, int type, int gerador, String [] tiles)
    {   
    	DadosFormulario dados = DadosFormulario.getInstancia();
    	if(dados.getIdioma()== "Ingles"){
    		msg = "Thanks for participating.";
    	}
    	else{
    		msg = "Obrigado pela participação."; 
    	}

    	//VariaveisGlobais.testando = true;
    	if(VariaveisGlobais.testando){    		
    		random = new Random();
			seed = random.nextInt(Integer.MAX_VALUE);
			difficulty = 3 + random.nextInt(4);
			//System.out.println("!!! Fase de Teste !!!\n" + "seed: " + seed + " difficulty: " + difficulty +"\n");
			return level = new RandomLevel(220, 15, seed, difficulty, type);    		
			//return level = new RandomLevel(94, 15, seed, difficulty, type);
    	}
    	
    	//level = createLevelOriginal(seed, difficulty, type);
        /*        
        salvaTela(level, "tela2"); //Teste ado metodo que salva a tela
        salvaInfoTela(level);
        */
        //level = conectaTelas(level);        
        //level =  retornaTela(level, "tela2");
        //level = controiLevel(level);
        //level = controiLevelPorFuncao(level);
    	//EquacaoFases equacao = new Parabola(-1/8, 3/3, 3);
    	//DadosFormulario dados = DadosFormulario.getInstancia();
    	//DadosFormulario dados = DadosFormulario.getInstancia();
    	EquacaoFases equacao = null;
    	VariaveisGlobais.num_mortes = 0; //Numero de vezes que o usuario morreu nesta tela
    	//Random randon = new Random();
    	//int x = randon.nextInt(4);
    	int x = VariaveisGlobais.cont % VariaveisGlobais.quant_geradores;
    	//if(x == 0){
    		//VariaveisGlobais.gerador = GeraSequenciaAleatoria.geraSequencia(VariaveisGlobais.quant_geradores);
    		
    		//for(int i = 0; i < VariaveisGlobais.quant_geradores; i++)
    			//System.out.print(VariaveisGlobais.gerador[i] + " ");
    		//System.out.println("\n");
    	//}
    	
    	
    	
    	VariaveisGlobais.morreuEm = -1;
    	//gerador = VariaveisGlobais.gerador[x];
    	//VariaveisGlobais.cont++; //Incrementa o contador para as proximas fases
    	
    	
    	/*
    	VetorFasesPartida partidas = new VetorFasesPartida();
    	try {
			partidas = partidas.carregaVetorFases("partidas");
		} catch (ClassNotFoundException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(partidas.size() == 0){
			try {
				
				ArrayList<String> fases = new ArrayList<String>();
				//fases.add("0");
				fases.add("1");
				fases.add("2");
				//fases.add("3");
				fases.add("4");
				fases.add("5");
				
				partidas.constroiVetorTelas(fases);
				ClassePartidas aux;
				System.out.println("Partidas montadas\n");
				for(int i=0; i < partidas.size(); i ++){
					aux = partidas.get(i);
					System.out.println(aux.fase1 +" x " + aux.fase2);
				}
				System.out.println();
				partidas.salvaVetorFases("partidas");
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
		
		if(VariaveisGlobais.fase == 0){
			VariaveisGlobais.partidas = partidas.remove(0);
			try {
				partidas.salvaVetorFases("partidas");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		VariaveisGlobais.fase++;
		
		if(VariaveisGlobais.fase == 1)
			gerador = VariaveisGlobais.partidas.fase1;
		else{
			gerador = VariaveisGlobais.partidas.fase2;
			VariaveisGlobais.fase = 0;
		}
			
    	
		System.out.println(VariaveisGlobais.partidas.fase1 + " x " + VariaveisGlobais.partidas.fase2);
		System.out.println("JOGANDO: " + gerador + "\n");
		dados.setPartida(VariaveisGlobais.partidas.fase1 + " x " + VariaveisGlobais.partidas.fase2);
    	*/
    	
    	/*
    	gerador = VariaveisGlobais.gerador[VariaveisGlobais.nada];
    	VariaveisGlobais.nada++;
    	
    	if(VariaveisGlobais.nada > 2)
    		VariaveisGlobais.nada = 0;
    	*/
    	/*
    	Scanner s = new Scanner(System.in);
    	while(gerador < 0 || gerador > 5){
    		System.out.print("\nDigite a opï¿½ï¿½o da funï¿½ï¿½o desejada: " +
    				"\n\t(0) Constante - dificuldade 5" +
    				"\n\t(1) Parabola - -0.25x^2 + 3x + 4" +
    				"\n\t(2) Aleatï¿½rio - RandomLevel" +
    				"\n\t(3) Linear decrescente - -1,1x + 13" +
    				"\n\t(4) Peter - PeterLevelGenerator" +
    				"\n\t(5) Glen Takahashi - UltraCustomizedLevelGenerator"+
    				"\nSua opï¿½ï¿½o: ");
    		gerador = s.nextInt();
    		if(gerador < 0 || gerador > 5)
    			System.out.println("Opcï¿½o Invï¿½lida!");
    		
    	}
    	*/
    	
    	
    	//************************ IMPORTANTE **************************************
    	
    	/*
    	
    	VetorSequencia sequencia = new VetorSequencia();
    	File file = new File("sequencia.txt");
    	if(file.exists()){
	    	try {
				sequencia = sequencia.carregaVetorSequencia("sequencia.txt");
				if(sequencia.size() == 0){
					//String msg = "<html>Thanks for participating.<br>Your participation id is:<br>"+ dados.getUser() + "</html>";
		    		//JOptionPane.showMessageDialog(null,msg,"", JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(null, msg,"", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
					//System.out.println("End");
		    		//IdClasse.main(null);
		    		//System.in.read();
		    		//return null;
				}
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	    	catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
			}
    	}
    	
    	dados.setQuadradoLatino(1);
    	
    	if(sequencia.size() == 0){    		
    		sequencia.montaVetorSequencia(dados.getQuadradoLatino()); 
    		
    	}    
    	
    	gerador = sequencia.remove(0);  
    	//sequencia.remove(0);
    	//sequencia.remove(0);
    	//sequencia.remove(0);
    	    	
    	
    	try {
			sequencia.salvaVetorSequencia("sequencia.txt");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//********************************************
    	//gerador = 1;
    	System.out.println("Jogando: " + gerador);
    	
    	dados.setPartida(gerador);
    	
    	*/
    	
    	//gerador = 7;
    	
    	
    	switch (gerador) {
		case 0:
			equacao = new Parabola(0, 0, 2);
			dados.setEquacao("Parabola(0, 0, 5)");			
			break;		
		case 1:
			//equacao = new Parabola(-0.25 , 3 , 4);
			//dados.setEquacao("-0.25x^2 + 3x + 4");
			//-0.25x^2 + 2.8x + 1
			//equacao = new Parabola(-0.25, 2.8, 1);
			//dados.setEquacao("-0.25x^2 + 2.8x + 2");
			equacao = new EquacaoEspecial(0, 1, 1);
			dados.setEquacao("EquacaoEspecial(0, 1, 1)");
			break;
		case 2:
			random = new Random();
			//seed = random.nextInt(Integer.MAX_VALUE);
			//difficulty = 3 + random.nextInt(3);
			dados.setEquacao("RandomLevel("+ seed + ", " + difficulty + ", " + type +")");
			//System.out.println(dados.getEquacao());
			return level = new RandomLevel(220, 15, seed, difficulty, type);  //RandomLevel(320, 15, seed, difficulty, type);			
		case 3:
			//equacao = new Parabola(0, -1.1, 13); 
			//dados.setEquacao("Parabola(0, -1,1, 13)");
			equacao = new EquacaoRandomica();
			dados.setEquacao("EquacaoRandomica()");
			break;
		case 4:
			System.out.println("aca entramos1");
			//width = 60;
			dados.setEquacao("PeterLevelGenerator()");
			//System.out.println(dados.getEquacao());
			petermawhorter.PeterLevelGenerator clg = new PeterLevelGenerator();
    		GamePlay gp = new GamePlay();
    		gp = gp.read(tiles[0]);
    		System.out.println("aca entramos2 "+tiles[0]);
            return (Level)clg.generateLevel(gp);   	
		
		case 5:
			glentakahashi.generator.UltraCustomizedLevelGenerator uclg= new UltraCustomizedLevelGenerator();
			GamePlay gp2 = new GamePlay();
    		gp2 = gp2.read("player.txt");
    		dados.setEquacao("UltraCustomizedLevelGenerator()");
    		//System.out.println(dados.getEquacao());
            return (Level)uclg.generateLevel(gp2);
            
		case 7:
			//Retornando tela especï¿½fica
			level = retornaTela(level, "TelasSelecionadas/Telas/" + "tela1599");
			fixWalls();
			return level;
		case 8:
			VetorTelasParaTeste telas = new VetorTelasParaTeste();
			try {
				telas = telas.carregaVetorTelas("vetorTelas");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(telas.size() == 0)
				try {
					telas.constroiVetorTelas();
					telas.salvaVetorTelas("vetorTelas");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			String nome = telas.get(0);
			System.out.println(nome);
			//for(int i=0; i < (telas.size()) - 2; i++)
				//System.out.println("Removido: " + telas.remove(0));
			System.out.println("Nao avaliadas: " + telas.size());
			
			
			level = retornaTela(level, "TelasSelecionadas/Telas/" + nome);
			DadosAvaliacaoTelas dadosTelas = DadosAvaliacaoTelas.getInstancia();
			dadosTelas.setTela(nome);
			fixWalls();			
			return level;
		case 9:
			level = conectaTelas(level);
			width = level.getxExit() + 64;
			//System.out.println("Width: " + width);
			fixWalls();
			
			//corrigeFase(level);
			return level;
		case 10:
			difficulty = (int) (Math.random () * 8);
			level = createLevelOriginal(seed, difficulty, type);			        
			System.out.println("\ncreateLevelOriginal(" + seed + ", " + difficulty + ", " + type + ");");
			int cont = 0;
			for (int xi = 0; xi < level.getWidth(); xi++) {
				for(int y = 0; y < level.getHeight(); y++){
					if(level.getBlock(xi, y) == (byte) (4 + 2 + 1 * 16) || //Block PowerUp 
					   level.getBlock(xi, y) == (byte) (2 + 1 * 16)//Bloco amarelo com PowerUp
					){
						cont++;
						//System.out.println("PowerUp em " + xi + ", " + y);
					}
				}				
			}
			
			//System.out.println("Quantidade de PowerUp: " + cont);
			return level;
		case 11: //salva a Tela
			
			VariaveisGlobais.tag_platform = false;
			VariaveisGlobais.tag_straigth = false;
			VariaveisGlobais.tag_hill_straigth = false;
			VariaveisGlobais.tag_tubes = false;
			VariaveisGlobais.tag_jump = false;
			VariaveisGlobais.tag_cannos = false;
			
			//width = 89;
			//level = new Level(width, 15);
			level = createLevelTela(seed, difficulty, type);
	        salvaTela(level, "tela");
	        salvaInfoTela(level);
	        //fixWalls();
	        return level;
		}   
    	
    	//System.out.println(dados.getEquacao());   	
    	level = controiLevelPorFuncaoMetrics(level, equacao,tiles);
		width = level.getxExit() + 64;
		
		//level.setBlock(1, 5, (byte) 28); //bloco azul-rocheado
		//level.setBlock(1, 5, (byte) 9); //bloco rocha cinza
		//level.setBlock(2, 5, (byte) 12); //bloco de madeira		
		/*
		 *  //bandeirinha na entrada indicando o caminho
		    level.setBlock(0, floor-2, (byte) 67);
    		level.setBlock(0, floor-1, (byte) 83);
    		level.setBlock(1, floor-2, (byte) 68);
    		level.setBlock(1, floor-1, (byte) 84);
		/* */
			
		
		//byte block_marron = (byte) (1 + 9 * 16); //bloco marron
		byte moeda = (byte) 32; //moeda
		//int inicio = 0, fim = 0;
		for(int xi = 0; xi < level.getWidth(); xi++){
			for (int y = 1; y < 8; y++) {
				if(level.getBlock(xi, y) != (byte)0 && level.getBlock(xi, y) != moeda){
					int y0 = 0;
					if(y - 5 > 0)
						y0 = y - 4;
					//System.out.println("Bloco nï¿½o vazio em " + xi + ", " + y);
					int yi = y;
					if(yi > 3)
						yi--;
					for(int z= y0; z < yi; z++){
						if(level.getBlock(xi, z) == (byte)0)
							level.setBlock(xi, z, moeda);
					}
					y=8;
					//break;
					//for(in)
					
					
					//System.out.println("PowerUp em " + xi + ", " + y);
				}
			}
			//level.setBlock(xi, 5, moeda);
		}
		
		//Adicionar um cogumelo		
		int cont = 0;
		for (int xi = 0; xi < 100; xi++) {
			for(int y = 0; y < level.getHeight(); y++){
				if(level.getBlock(xi, y) == (byte) (4 + 2 + 1 * 16) || //Block PowerUp 
				   level.getBlock(xi, y) == (byte) (2 + 1 * 16)//Bloco amarelo com PowerUp
				){
					cont++;
					//System.out.println("PowerUp em " + xi + ", " + y);
				}
			}				
		}
		
		//System.out.println("Quantidade de PowerUp: " + cont);
		
		if(cont < 1){
			random = new Random();
			int xi = random.nextInt(100) + 1;
			int y;
			byte block_ant = (byte) (1 + 9 * 16); //bloco marron
			for(; xi > 0; xi--)
			for(y = level.getHeight(); y > 4; y--){
				if(level.getBlock(xi, y) == 0 && level.getBlock(xi, y + 1) == block_ant){
					if(level.getBlock(xi,  y - 3) == 0 && level.getBlock(xi - 1,  y - 3) == 0 &&
							level.getBlock(xi + 1,  y - 3) == 0){
						//level.setBlock(xi - 1,  y - 3, (byte) 28); //bloco azul-rocheado
						level.setBlock(xi,  y - 3, (byte) (4 + 2 + 1 * 16));
						//level.setBlock(xi + 1,  y - 3, (byte) 12); //bloco de madeira
						//System.out.println("Inserido PowerUp em " + xi + ", " + (y-3));
						xi = 0;
						break;
					}else break;
				}
			}
			
			
			
		}			
        //corrigeFase(level);
    	fixWalls();
        
        //System.out.println("Level com equacao " + dados.getEquacao());
    	return level;
    	
        
    }
    
    private Level controiLevelPorFuncaoMetrics(Level level, EquacaoFases equacaoPrincipal, String [] tiles){
    	
    	//System.out.println("Valores da funcao: \na: " + equacaoPrincipal.getA() + " b: " + equacaoPrincipal.getB() + " c: " + equacaoPrincipal.getC());
    	
    	ArrayList<String> nomeTelas = new ArrayList<String>();
    	MedidorDeDificuldade medidor = new MedidorDeDificuldade();
    	
    	try {
			//medidor.montaTabelaDificuldade();
    		medidor = medidor.carregaTabelaTelas("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		    	
    	
    	int dificuldade;
    	//ClassEquacoes funcao = new ClassEquacoes();
    	//EquacaoFases equacao = new Parabola(-1/8, 3/3, 3);  /*-x^2/8 + 3x//2 + 3 */ equacaoPrincipal
    	//EquacaoFases equacao = equacaoPrincipal;   	
    	
    	//System.out.print("FASE: ");
    	//String nomeTela = "";
    	
		//for (int i=0; i< medidor.getQuantidadeTelas(); i++){
    	for (int i=0; i < NUM_TELAS_POR_FASE; i++){
			//dificuldade = (int)equacao.resultadoFuncao(i);
			dificuldade = (int)equacaoPrincipal.resultadoFuncao(i);
			//nomeTela = (medidor.retornaTelaEspecifica(dificuldade, nomeTelas));
			nomeTelas.add(tiles[i]);
			System.out.print(i + ": " + nomeTelas.get(i) + "(" + dificuldade +")   " );
		}
    	//System.out.println();
    	
    	DadosFormulario salvaTelasFase = DadosFormulario.getInstancia();
		salvaTelasFase.setListaTelasFase(nomeTelas);
		
		//ArrayList lista = salvaTelasFase.getListaTelasFase();
		/*
		 System.out.print("FASE salva no Formulario de Dados: ");
		for (int i=0; i< medidor.getQuantidadeTelas(); i++){
			System.out.print(lista.get(i) + " ");
		}
    	System.out.println();
    	*/
    	ConectorDeTelas fase = new ConectorDeTelas();	
    	//fase.lerArquivosDiretorio(nomeTelas);
    	ArrayList<Level> listaTelas = new ArrayList<Level>();
    	String diretorio = "";
    	
    	FileInputStream fis;
    	
		try {
			for(int i=0;i < nomeTelas.size(); i++){
				//diretorio = "telasEinfo/Telas/" + nomeTelas.get(i);
				diretorio = "TelasSelecionadas/Telas/" + nomeTelas.get(i);
				fis = new FileInputStream(diretorio);
				DataInputStream dis = new DataInputStream(fis);
				//System.out.print(nomeTelas.get(i) + " ");
				//Level level2 = Level.load(dis);
				listaTelas.add(Level.load(dis));
			}
			System.out.println();
			
			return fase.conectaTelas(listaTelas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		
    	    		
    		//***************************************************
        System.out.println("Retornando Default");	
		return conectaTelas(level);       	
        
    }

}
