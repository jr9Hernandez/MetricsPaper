package pet;

import java.util.Random;
import java.util.Scanner;

import pet.Exceptions.AnotherGapException;
import pet.Exceptions.ExistingElementException;
import pet.Exceptions.OverTubeException;
import pet.Exceptions.WithoutFloorException;
import pet.Interfaces.PetLevelInterface;

import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.level.Level;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;

import javax.swing.JOptionPane;

/**
 * @author Rodrigo
 */
public class LevelMap extends Level implements PetLevelInterface {

	int fase[][] = new int[15][40];
	
	// Contador dos elementos do cenário
	private int gaps[] = new int[3];
	private ObjetosMapa gap = new ObjetosMapa(); //Guardar as coordenadas dos buracos
	private int tubes[] = new int[3];
	private ObjetosMapa tubo = new ObjetosMapa(); //Guardar as coordenadas dos tudos
	private int coins;
	private ObjetosMapa moedas[] = new ObjetosMapa[10]; //Guardar as coordenadas das moedas
	private int quests;
	private ObjetosMapa blocos[] = new ObjetosMapa[10];//Guardar as coordenadas dos blocos
	private int platforms;
	private ObjetosMapa plataforma = new ObjetosMapa(); //Coordenada da plataforma
	private int mountains;

	// Contadores dos bichos
	private int goombas[] = new int[2];
	private ObjetosMapa goomba1 = new ObjetosMapa();
	private ObjetosMapa goomba2 = new ObjetosMapa();
	private int green_koopas[] = new int[2];
	private ObjetosMapa koopa_verde1 = new ObjetosMapa();
	private ObjetosMapa koopa_verde2 = new ObjetosMapa();
	private int red_koopas[] = new int[2];
	private ObjetosMapa koopa_vermelha1 = new ObjetosMapa();
	private ObjetosMapa koopa_vermelha2 = new ObjetosMapa();
	private int plants;
	private ObjetosMapa planta = new ObjetosMapa(); //Cordenadas das plantas
	private int spikis;
	
	private ElementosTela tela;
	

	// Nota
	private int result;

	// Quantidades padrão
	private static int default_gaps[] = { 0, 0, 1 };
	//private static int default_tubes[] = { 2, 2, 2 };
	private static int default_tubes[] = { 0, 1, 1 };
	//private static int default_coins = 30;
	private static int default_coins = 10;
	//private static int default_quests = 10;
	private static int default_quests = 5;
	//private static int default_platforms = 5;
	private static int default_platforms = 1;
	private static int default_mountains = 0;
	//private static int default_mountains = 1; //Alterado por Willian

	//private static int default_goombas[] = { 3, 3 };
	private static int default_goombas[] = { 1, 1 };
	//private static int default_green_koopas[] = { 3, 3 };
	private static int default_green_koopas[] = { 1, 1 };
	//private static int default_red_koopas[] = { 3, 3 };
	private static int default_red_koopas[] = { 1, 1 };
	//private static int default_plants = 2;
	private static int default_plants = 1; //Alterado por Willian
	//private static int default_spikis = 3;
	private static int default_spikis = 0;

	/**
	 * Enumeração dos tipos de manipulação de arquivo.
	 * 
	 * @author rodrigo
	 */
	public static enum FileHandlingType {
		LOAD, SAVE;
	}

	/**
	 * Construtor básico do mapa.
	 * 
	 * @param width
	 *            Largura do mapa.
	 * 
	 * @param heightca
	 *            Altura do mapa.
	 */
	public LevelMap(int width, int height) {
		super(width, height);

		this.LoadDefaultQuantities();

		this.CreateMap();
		// this.addEnemies();
	}

	/**
	 * Rotina para construção do mapa a partir de um arquivo texto
	 * pré-existente.
	 * 
	 * @param file
	 *            Objeto Path descrevendo o diretório do arquivo.
	 * 
	 * @param width
	 *            Largura do mapa.
	 * 
	 * @param height
	 *            Altura do mapa.
	 * 
	 * @param type
	 *            Enumeração representando o tipo de manipulação do arquivo.
	 */
	public LevelMap(Path file, int width, int height, FileHandlingType type) {

		super(width, height);

		if (type == FileHandlingType.LOAD){
			this.LoadFromFile(file);		
		}
		else{
			//this.LoadRandomQuantities();
			//this.LoadQuantitiesFromFile();//Willian
			this.LoadDefaultQuantities();
		}
		
		/*
		try{
			this.LoadFromFile(file);
		}
		finally{
			
		}
		*/
		this.CreateMap3();
		//this.CreateMap3();
		System.out.println("FASE GERADA COM SUCESSO!! \\O/\\O/\\O/");
		
		/*

		if (type == FileHandlingType.SAVE){
			this.SaveLevelOnFile(file);
			JOptionPane.showMessageDialog(null,"Salvou o arquivo");
		}
		else
			JOptionPane.showMessageDialog(null,"Nao salvou o arquivo");
		
		*/
	}

	//@Override
	public void CreateFloor() {  //Cria o ch�o(piso)
		for (int x = 0; x < this.width; x++) {
			super.setBlock(x, this.height - 1, Level.HILL_TOP);
		}
	}

	//@Override
	public void CreateMap() {

		// Reproduzindo a rotina definida no papel
		this.CreateFloor();

		int i = 0;
		Random n = new Random();

		// Criando o portal da sa�da
		super.xExit = super.width - 8;
		super.yExit = super.height - 1;
		
		
		int gap = this.gaps[0] + this.gaps[1] + this.gaps[2];
		//int gap = 10;
		int tube = tubes[0]+tubes[1]+tubes[2];
		
		
		tela = new ElementosTela(gap, tube, coins, quests, platforms, 
				mountains, goombas, green_koopas, red_koopas,plants, spikis);

		int i_gaps[] = { 0, 0, 0 };
		
		//Quantidade de buracos
		//int quant_gaps = this.gaps[0]+this.gaps[1]+this.gaps[2];
		
		for (i = 0; i < 3; i++) {
			while (i_gaps[i] != this.gaps[i]) {

				int offset = n.nextInt(super.width - 10); //Gera um valor aleat�rio entre 0 e largura-10
				// int width = n.nextInt(5);
				int width = i + 3;

				if (offset > 10) {

					try {
						this.CreateGap(offset, width);
						i_gaps[i]++;
						System.out.println("Buraco Inserido em ("+ offset+")");

					} catch (AnotherGapException e) {
						System.err.print(e.getMessage());
					}
				}
			}
		}

		i = 0;
		int i_tubes[] = { 0, 0, 0 };

		for (i = 0; i < 3; i++) {
			while (i_tubes[i] != this.tubes[i]) {

				int offset[] = { n.nextInt(super.width - 10),
						n.nextInt(super.height - 1) };
				int height = i + 2;

				if (offset[0] > 8) {
					try {

						this.CreateTube(offset, height);
						tubo.setCoordenadas(offset[0], offset[1]);
						tubo.altura = height;
						//Tubos						
						i_tubes[i]++;
						System.out.println("Tubo Inserido em ("+ offset[0]+", "+ offset[1] + ")");

					} catch (WithoutFloorException e) {
						System.err.println(e.getMessage());
					}catch (ExistingElementException e) {
						System.err.println(e.getMessage());
					}catch (OverTubeException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}

		i = 0;
		
		
		while (i != this.platforms) {

			int offset[] = { n.nextInt(super.width - 10),
					n.nextInt(super.height - 1) };

			int height = n.nextInt(5);
			int width = n.nextInt(10);

			if (offset[0] > 8) {

				if ((width > 1) && (height > 0)) {

					try {

						this.CreatePlatform(offset, width, height);
						//plataforma.setCoordenadas(offset[0], offset[1]);
						i++;
						System.out.println("Plataforma Inserida em ("+ offset[0]+", "+ offset[1] + ")");
						tela.setPlatform(offset[0], offset[1], width, height);

					} catch (WithoutFloorException  e) {
						System.err.println(e.getMessage());
					}catch (ExistingElementException e) {
						System.err.println(e.getMessage());
					}catch (OverTubeException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
		
		
		//Inseri para criar as montanhas
		i = 0;

		while (i != this.mountains) {

			int offset[] = { n.nextInt(super.width - 10),
					n.nextInt(super.height - 1) };

			int height = n.nextInt(5);
			int width = n.nextInt(10);

			if (offset[0] > 8) {

				if ((width > 1) && (height > 0)) {

					try {

						this.CreateMountain(offset, width, height);
						i++;

					} catch (WithoutFloorException e) {
						System.err.println(e.getMessage());
					}
					catch (OverTubeException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
		
		this.CreateBlockMap();
		this.CreateCoinMap();

		this.addEnemies2();
		System.out.println("Todos Inimigo, moedas e blocos pronto. Imprimindo coordenadas ... ");
		
		this.imprimeCoordenadas();
		tela.salvaEmTxt();
		//this.preencheMatriz();
		//this.SalvarFaseArquivo();
	}

	//@Override
	public void CreatePlatform(int[] offset, int width, int height)
			throws ExistingElementException, WithoutFloorException,
			OverTubeException {

		this.SearchExistingElements(offset, width, height);
		this.SearchGapsUnderElements(offset, width);
		this.SearchTubesUnderElements(offset);

		for (int y = offset[1]; y > offset[1] - height; y--) {
			for (int x = offset[0]; x < offset[0] + width; x++) {
				// Plataforma superior
				if (y == offset[1] - height + 1) {
					// Canto superior esquerdo
					if (x == offset[0])
						super.setBlock(x, y, Level.HILL_TOP_LEFT);

					// Canto superior direito
					else if (x == offset[0] + width - 1)
						super.setBlock(x, y, Level.HILL_TOP_RIGHT);

					// Meio
					else
						super.setBlock(x, y, Level.HILL_TOP);
				}

				// Parte de baixo
				else {
					// Borda esquerda
					if (x == offset[0])
						super.setBlock(x, y, Level.HILL_LEFT);

					// Borda direita
					else if (x == offset[0] + width - 1)
						super.setBlock(x, y, Level.HILL_RIGHT);

					// Meio
					else
						super.setBlock(x, y, Level.HILL_FILL);
				}
			}
			
			plataforma.altura = height;
			plataforma.lagura = width;
			plataforma.setCoordenadas(offset[0], offset[1]);
			
			//Salvando as plataformas
			
		}

	}

	//@Override
	public void CreateGap(int offset, int width) throws AnotherGapException {

		/*
		 * TODO: Fazer a verificação de buracos vizinhos para evitar bugs
		 * gráficos (chamar a rotina SearchNeighboringGaps()).
		 */

		this.SearchAnotherGap(offset, width);

		for (int y = this.height - 1; y < this.height; y++) {
			for (int x = offset; x < offset + width; x++)
				super.setBlock(x, y, (byte) 0);
		}
		
		gap.setCoordenadas(offset, this.height-1);
		gap.lagura = width;
		
		
		
		super.setBlock(offset - 1, this.height - 1, Level.RIGHT_UP_GRASS_EDGE);
		super.setBlock(offset + width - 1, this.height - 1,
				Level.LEFT_UP_GRASS_EDGE);
		
		//Guardando as coordenadas dos buracos
		tela.setGap(offset, this.height-1, width);
		
		//System.out.println("Buraco Criado em (" + offset + ", " +  width +")" );
	}

	//@Override
	public void CreateTube(int[] offset, int height)
			throws ExistingElementException, WithoutFloorException,
			OverTubeException {	

		this.SearchExistingElements(offset, 2, height);
		this.SearchGapsUnderElements(offset, 2);
		this.SearchTubesUnderElements(offset);

		for (int y = offset[1]; y > offset[1] - height; y--) {
			for (int x = offset[0]; x < offset[0] + 2; x++) {

				if (y == offset[1] - height + 1) {
					// Canto superior esquerdo
					if (x == offset[0])
						super.setBlock(x, y, Level.TUBE_TOP_LEFT);

					// Canto superior direito
					else
						super.setBlock(x, y, Level.TUBE_TOP_RIGHT);
				} else {
					// Lateral esquerda
					if (x == offset[0])
						super.setBlock(x, y, Level.TUBE_SIDE_LEFT);

					// Lateral direita
					else
						super.setBlock(x, y, Level.TUBE_SIDE_RIGHT);
				}
			}
		}
		
		tela.setTube(offset[0], offset[1], height);
	}

	//@Override
	public void SearchExistingElements(int[] offset, int width, int height)
			throws ExistingElementException {
		for (int y = offset[1]; y > offset[1] - height; y--) {

			for (int x = offset[0]; x < offset[0] + width; x++) {

				if (super.getBlock(x, y) != (byte) 0)
					/*
					 * && (super.getBlock(x, y) != Level.HILL_TOP) &&
					 * (super.getBlock(x, y) != Level.HILL_TOP_LEFT) &&
					 * (super.getBlock(x, y) != Level.HILL_TOP_RIGHT) &&
					 * (super.getBlock(x, y) != Level.LEFT_UP_GRASS_EDGE) &&
					 * (super.getBlock(x, y) != Level.RIGHT_UP_GRASS_EDGE) &&
					 * (super.getBlock(x, y) != Level.LEFT_POCKET_GRASS) &&
					 * (super.getBlock(x, y) != Level.RIGHT_POCKET_GRASS))
					 */
					throw new ExistingElementException(super.getBlock(x, y),
							offset);
			}
		}

	}

	//@Override
	public void CreateBlockMap() {

		Random n = new Random();
		int qtd_quests = 0;
		
		//Instanciando o vetor de blocos ***********
		for(int i= 0;i<this.quests; i++)
			blocos[i] = new ObjetosMapa();
		//******************************************
		
		while (qtd_quests != this.quests) {

			int offset[] = { n.nextInt(this.width - 10), n.nextInt(this.height) };

			int h = this.CalculateHeight(offset);

			if (h == 3) {
				boolean aux = n.nextBoolean();

				if (aux){
					this.setBlock(offset[0], offset[1], BLOCK_POWERUP);
					//Salvando os blocos com power-ups
					tela.setBlocoPowerUp(offset[0], offset[1]);
				}
				else{
					this.setBlock(offset[0], offset[1], BLOCK_COIN);
					//Salvando os blocos com moedas
					tela.setBlocoCoin(offset[0], offset[1]);
				}

				blocos[qtd_quests].setCoordenadas(offset[0], offset[1]);
				qtd_quests++;
			}
		}
	}

	//@Override
	public void CreateCoinMap() {

		Random n = new Random();
		int qtd_coins = 0;
		
		//************************************
		for(int i = 0 ;i<this.coins; i++)
			moedas[i] = new ObjetosMapa();
		//**********************************

		while (qtd_coins != this.coins) {

			int offset[] = { n.nextInt(this.width - 10), n.nextInt(this.height) };

			int h = this.CalculateHeight(offset);

			if (h == 3) {
				this.setBlock(offset[0], offset[1], Level.COIN);
				moedas[qtd_coins].setCoordenadas(offset[0], offset[1]);
				qtd_coins++;
				//Salvando as moedas
				tela.setCoin(offset[0], offset[1]);
			}
		}
	}

	public void SearchGapsUnderElements(int offset[], int width) throws WithoutFloorException {

		for (int x = offset[0]; x < offset[0] + width; x++) {
			if (super.getBlock(x, offset[1] + 1) == (byte) 0)
				throw new WithoutFloorException(offset, width);
		}
	}

	/**
	 * Rotina para calcular a altura para um elemento de largura 1x1 (blocos,
	 * moedas)
	 * 
	 * @param offset
	 *            Posição {x,y} do elemento.
	 * 
	 * @return A altura do elemento em relação a alguma plataforma.
	 */
	private int CalculateHeight(int offset[]) {
		int h = 0;

		for (int y = offset[1]; y < offset[1] + height; y++) {

			if ((this.getBlock(offset[0], y) == Level.HILL_TOP_LEFT)
					|| (this.getBlock(offset[0], y) == Level.HILL_TOP_RIGHT)
					|| (this.getBlock(offset[0], y) == Level.HILL_TOP)
					|| (this.getBlock(offset[0], y) == Level.GROUND)
					|| (this.getBlock(offset[0], y) == Level.BLOCK_COIN)
					|| (this.getBlock(offset[0], y) == Level.BLOCK_EMPTY)
					|| (this.getBlock(offset[0], y) == Level.BLOCK_POWERUP)
					|| (this.getBlock(offset[0], y) == Level.TUBE_TOP_LEFT)
					|| (this.getBlock(offset[0], y) == Level.TUBE_TOP_RIGHT)
					|| (this.getBlock(offset[0], y) == Level.ROCK)
					// || (this.getBlock(offset[0], y) == Level.COIN)
					|| (this.getBlock(offset[0], y) == Level.HILL_FILL)
					|| (this.getBlock(offset[0], y) == Level.HILL_LEFT)
					|| (this.getBlock(offset[0], y) == Level.HILL_RIGHT)
					|| (this.getBlock(offset[0], y) == Level.TUBE_SIDE_LEFT)
					|| (this.getBlock(offset[0], y) == Level.TUBE_SIDE_RIGHT)
					|| (this.getBlock(offset[0], y) == Level.LEFT_POCKET_GRASS)
					|| (this.getBlock(offset[0], y) == Level.RIGHT_POCKET_GRASS))
				break;

			h++;
		}

		return h;
	}

	/**
	 * Função para criar os inimigos aleatóriamente no mapa.
	 */
	public void addEnemies() {
		/*
		 * Criando contadores para os inimigos já colocados no mapa
		 */
		int add_gkoopas[] = { 0, 0 };
		int add_rkoopas[] = { 0, 0 };
		int add_goombas[] = { 0, 0 };
		int add_plants = 0;
		int add_spikis = 0;

		boolean completo = false;
		int tipo;
		int x;
		int y;
		boolean asas;
		Random random = new Random();

		/*
		 * Utilizando a classe Random para gerar números aleatórios
		 */
		while (!completo) {
			/*
			 * Definindo aleatóriamente o tipo e as coordenadas
			 */
			tipo = random.nextInt(5);
			//System.out.println("Add inimigos. Tipo:" + tipo);
			//tipo = 0;
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);

			/*
			 * Verificando se não existe um inimigo nessa coordenada
			 */
			SpriteTemplate aux = getSpriteTemplate(x, y);
			if (aux == null) {
				switch (tipo) {
				/*
				 * Nos inimigos com asas, verificamos se há um bloco de chão. Se
				 * houver, ele será instanciado no mapa :^D
				 */

				/*
				 * Green Koopa
				 */
				case 0:
					asas = random.nextBoolean();

					if (!asas) {
						if (add_gkoopas[0] != green_koopas[0]) {

							/*
							 * Verificando se o bloco não é uma parte de um cano
							 */
							boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
									|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
									|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
									|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

							if (getBlock(x, y + 1) == Level.HILL_TOP && !cano) {
								add_gkoopas[0]++;
								setSpriteTemplate(x, y, new SpriteTemplate(
										Enemy.ENEMY_GREEN_KOOPA, asas));
								
								koopa_verde1.setCoordenadas(x, y); //Coordenada da Koopa verde sem asa.
								//Salvando gKoopas sem asas
								tela.setKoopaVerdeSemAsa(x, y);
								System.out.println("Koopa sem asa Inserida em ("+ x+", "+ y + ")");
							}
						}
					} else {
						if (add_gkoopas[1] != green_koopas[1]) {
							boolean plataforma = false;
							int i_y;

							for (i_y = y + 1; i_y < super.height - 1; i_y++) {
								plataforma = getBlock(x, i_y) == Level.HILL_TOP;

								if (plataforma)
									break;
							}

							for (i_y = 0; i_y < super.height - 1; i_y++)
								plataforma = plataforma
										&& (getSpriteTemplate(x, i_y) == null);

							if (plataforma) {
								add_gkoopas[1]++;
								setSpriteTemplate(x, y, new SpriteTemplate(
										Enemy.ENEMY_GREEN_KOOPA, asas));
								
								koopa_verde2.setCoordenadas(x, y); //Coordenada da Koopa verde com asa.
								//Salvando gKoopa com asas
								tela.setKoopaVerdeComAsa(x, y);
								System.out.println("Koopa verde com asa Inserida em ("+ x+", "+ y + ")");
							}
						}
					}
					break;

				/*
				 * Red Koopa
				 */
				case 1:
					asas = random.nextBoolean();

					if (!asas) {
						if (add_rkoopas[0] != red_koopas[0]) {
							boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
									|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
									|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
									|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

							if ((getBlock(x, y + 1) == Level.HILL_TOP) && !cano) {
								add_rkoopas[0]++;
								setSpriteTemplate(x, y, new SpriteTemplate(
										Enemy.ENEMY_RED_KOOPA, asas));
								
								koopa_vermelha1.setCoordenadas(x, y); //Coordenadas da Koopa vermalha sem asa
								//Salvando rKoopa sem asa
								tela.setKoopaVermelhaSemAsa(x, y);
								System.out.println("Koopa Vermelha sem asa Inserida em ("+ x+", "+ y + ")");
							}
						}
					} else {
						if (add_rkoopas[1] != red_koopas[1]) {
							boolean plataforma = false;
							int i_y;

							for (i_y = y + 1; i_y < super.height - 1; i_y++) {
								plataforma = (getBlock(x, i_y) == Level.HILL_TOP);

								if (plataforma)
									break;
							}

							for (i_y = 0; i_y < super.height - 1; i_y++)
								plataforma = plataforma
										&& (getSpriteTemplate(x, i_y) == null);

							if (plataforma) {
								add_rkoopas[1]++;
								setSpriteTemplate(x, y, new SpriteTemplate(
										Enemy.ENEMY_RED_KOOPA, asas));
								
								koopa_vermelha2.setCoordenadas(x, y); //Coordenadas da Koopa vermalha com asa
								//Salvando rKoopa com asa
								tela.setKoopaVermelhaComAsa(x, y);
								System.out.println("Koopa Vermelha com asa Inserida em ("+ x+", "+ y + ")");
							}

						}
					}
					break;

				/*
				 * Goomba
				 */
				case 2:
					asas = random.nextBoolean();

					if (!asas) {
						if (add_goombas[0] != goombas[0]) {
							boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
									|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
									|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
									|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

							if (getBlock(x, y + 1) == Level.HILL_TOP && !cano) {
								add_goombas[0]++;
								setSpriteTemplate(x, y, new SpriteTemplate(
										Enemy.ENEMY_GOOMBA, asas));
								
								goomba1.setCoordenadas(x, y); //Coordenadas da Goomba sem asa
								//Salvando Gomba sem asa
								tela.setGombaSemAsa(x, y);
								System.out.println("Goomba sem asa Inserida em ("+ x+", "+ y + ")");
							}
						}
					} else {
						if (add_goombas[1] != goombas[1]) {
							boolean plataforma = false;
							int i_y;

							for (i_y = y + 1; i_y < super.height - 1; i_y++) {
								plataforma = (getBlock(x, i_y) == Level.HILL_TOP);

								if (plataforma)
									break;
							}

							for (i_y = 0; i_y < super.height - 1; i_y++)
								plataforma = plataforma
										&& (getSpriteTemplate(x, i_y) == null);

							if (plataforma) {
								add_goombas[1]++;
								setSpriteTemplate(x, y, new SpriteTemplate(
										Enemy.ENEMY_GOOMBA, asas));
								
								goomba2.setCoordenadas(x, y); //Coordenadas da Goomba com asa
								//Salvando Gomba com asas
								tela.setGombaComAsa(x, y);
								System.out.println("Goomba com asa Inserida em ("+ x+", "+ y + ")");
							}
						}
					}

					break;

				/*
				 * Plant
				 */
				case 3:
					if (add_plants != plants) {
						//int c = tubes[0] + tubes [1] + tubes [2];
						//c = random.nextInt(c);
						//Coordenadas coor = new Coordenadas();
						//coor = tela.getTube(c);
						//x = coor.getX();
						//y =coor.getY();
						boolean tubo = getBlock(x , y + 1) == Level.TUBE_TOP_LEFT
								|| getBlock(x, y + 1) == Level.TUBE_TOP_RIGHT;
						
						//tubo = true;

						if (tubo) {
							add_plants++;
							setSpriteTemplate(x, y, new SpriteTemplate(
									Enemy.ENEMY_FLOWER, false));
							
							planta.setCoordenadas(x, y);
							//Salvando plantas
							tela.setPlant(x, y);
							System.out.println("Planta Inserida em ("+ x+", "+ y + ")");
						}
					}
					break;

				/*
				 * Spiky
				 */
				case 4:
					if (add_spikis != spikis) {
						boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
								|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
								|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
								|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

						if (getBlock(x, y + 1) == Level.HILL_TOP && !cano) {
							add_spikis++;
							setSpriteTemplate(x, y, new SpriteTemplate(
									Enemy.ENEMY_SPIKY, false));
							
							//Salvando Spike
							tela.setSpike(x, y);
							System.out.println("Spike Inserido em ("+ x +", "+ y+1 + ")");
						}
					}
					break;
				}
			}			

			completo = (add_gkoopas[0] == green_koopas[0])
					&& (add_gkoopas[1] == green_koopas[1])
					&& (add_rkoopas[0] == red_koopas[0])
					&& (add_rkoopas[1] == red_koopas[1])
					&& (add_goombas[0] == goombas[0])
					&& (add_goombas[1] == goombas[1]) && (add_plants == plants)
					&& (add_spikis == spikis);
			
			if(completo) System.out.println("**** Todos Inimigos Inseridos ****");
		}
	}

	/**
	 * Rotina para verificar se não há um tubo debaixo da área a ser desenhada.
	 * 
	 * @param offset
	 *            Posição {x,y} do canto inferior esquerdo do objeto.
	 * 
	 * @throws OverTubeException
	 *             Ela é disparada quando há um tubo embaixo da área.
	 */
	private void SearchTubesUnderElements(int offset[])
			throws OverTubeException {

		if ((this.getBlock(offset[0], offset[1] + 1) == Level.TUBE_TOP_LEFT)
				|| (this.getBlock(offset[0], offset[1] + 1) == Level.TUBE_TOP_RIGHT))
			throw new OverTubeException(offset);
	}

	/**
	 * Rotina para verificar se não há um buraco ocupando toda ou parte da área
	 * reservada para um novo buraco.
	 * 
	 * @param offset
	 *            Posição {x} do canto superior esquerdo do buraco.
	 * 
	 * @param width
	 *            Largura do buraco.
	 * 
	 * @throws AnotherGapException
	 *             Ela é disparado quando há um outro buraco ocupando a área.
	 */
	private void SearchAnotherGap(int offset, int width)
			throws AnotherGapException {

		for (int y = this.height - 1; y < this.height; y++) {

			for (int x = offset; x < offset + width; x++) {

				if ((this.getBlock(x, y) == Level.LEFT_UP_GRASS_EDGE)
						|| (this.getBlock(x, y) == Level.RIGHT_UP_GRASS_EDGE)
						|| (this.getBlock(x, y) == (byte) 0))
					throw new AnotherGapException(offset);
			}
		}
	}

	/**
	 * Rotina para verificar se há um buraco vizinho.
	 * 
	 * @param offset
	 *            Posição {x} do canto esquerdo do buraco.
	 * 
	 * @param width
	 *            Largura do buraco.
	 * 
	 * @return 0: Nenhum vizinho; 1: Um vizinho na direita; 2: Um vizinho na
	 *         esquerda; 3: Dois vizinhos nos dois lados.
	 */
	private int SearchNeighboringGaps(int offset, int width) {
		int res = 0;

		for (int aux = offset - 1; aux != offset + width + 1; aux += width + 1) {
			if (this.getBlock(aux, this.height - 1) == Level.RIGHT_UP_GRASS_EDGE)
				res++;
			else if (this.getBlock(aux, this.height - 1) == Level.LEFT_UP_GRASS_EDGE)
				res += 2;
		}

		return res;
	}

	/**
	 * Rotina para criar uma montanha (plataforma que o Mario não pode passar
	 * por dentro).
	 * 
	 * @param offset
	 *            Posição {x,y} do canto inferior esquerdo.
	 * 
	 * @param width
	 *            Largura do objeto.
	 * 
	 * @param height
	 *            Altura do objeto.
	 * 
	 * @throws WithoutFloorException
	 *             Ela é disparada quando não há um chão para construí-la.
	 * 
	 * @throws OverTubeException
	 *             Ela é disparada quando há um tubo em baixo da área
	 *             delimitada.
	 */
	private void CreateMountain(int offset[], int width, int height)
			throws WithoutFloorException, OverTubeException {

		/*
		 * TODO: Necessita ser feita a verificação de montanhas vizinhas e o
		 * desenho nesse caso.
		 */

		for (int y = offset[1]; y > offset[1] - height; y--) {
			for (int x = offset[0]; x < offset[0] + width; x++) {

				// Plataforma superior
				if (y == offset[1] - height + 1) {

					// Canto superior esquerdo
					if (x == offset[0])
						this.setBlock(x, y, Level.LEFT_UP_GRASS_EDGE);

					// Canto superior direito
					else if (x == offset[0] + height)
						this.setBlock(x, y, Level.RIGHT_UP_GRASS_EDGE);

					// Meio
					else
						this.setBlock(x, y, Level.HILL_TOP);
				}

				// Plataforma inferior
				else if (y == offset[1]) {

					// Canto inferior esquerdo
					if (x == offset[0])
						this.setBlock(x, y, Level.LEFT_POCKET_GRASS);

					// Canto inferior direito
					else if (x == offset[0] + width)
						this.setBlock(x, y, Level.RIGHT_POCKET_GRASS);

					// Meio
					else
						this.setBlock(x, y, Level.HILL_FILL);
				}

				// Plataforma intermediária
				else {
				}
			}
		}
	}

	/**
	 * Rotina para salvar os metadados em um arquivo texto.
	 * 
	 * @param file
	 *            Objeto Path descrevendo o diretório do arquivo.
	 */
	private void SaveLevelOnFile(Path file) {

		// Definindo a codificação do arquivo (padrão da máquina virtual
		Charset cod = Charset.defaultCharset();

		// Criando uma stream ligada ao arquivo
		/*try (BufferedWriter stream = Files.newBufferedWriter(file, cod,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
		*/
		
		BufferedWriter stream;
		try {
			stream = Files.newBufferedWriter(file, cod,
					StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.TRUNCATE_EXISTING);
		

			// "Escrevendo" nessa stream
			// stream.write('\n');

			//Willian --- Auterei aqui
			
			/*
			for (int i : this.gaps)
				stream.write(Integer.toString(i) + '\n');

			for (int i : this.tubes)
				stream.write(Integer.toString(i) + '\n');
				
			*/
			// --- ---- --- ---- --- ---- --- ----
			
		
			for (int i : this.gaps){
				//stream.write("Gaps: ");
				stream.write(Integer.toString(i) + '\n');
			}

			for (int i : this.tubes){
				//stream.write("Tubes: ");
				stream.write(Integer.toString(i) + '\n');
			}
			
			//stream.write("Platforms: ");
			stream.write(Integer.toString(this.platforms) + '\n');
			//stream.write("Mountains: ");
			stream.write(Integer.toString(this.mountains) + '\n');
			//stream.write("Coins: ");
			stream.write(Integer.toString(this.coins) + '\n');
			//stream.write("Quests: ");
			stream.write(Integer.toString(this.quests) + '\n');

			for (int i : this.red_koopas){
				//stream.write("red_koopas: ");
				stream.write(Integer.toString(i) + '\n');
			}

			for (int i : this.green_koopas){
				//stream.write("green_koopas: ");
				stream.write(Integer.toString(i) + '\n');
			}

			for (int i : this.goombas){
				//stream.write("goombas: ");
				stream.write(Integer.toString(i) + '\n');
			}

			//stream.write("Spikis: ");
			stream.write(Integer.toString(this.spikis) + '\n');
			//stream.write("Plants: ");
			stream.write(Integer.toString(this.plants) + '\n');

			
			
		} catch (IOException e) {

			System.err.println(e.getMessage());
		}
	}

	/**
	 * Rotina para carregar os metadados a partir de um arquivo texto.
	 * 
	 * @param file
	 *            Objeto Path descrevendo o diretório do arquivo.
	 */
	 private void LoadFromFile(Path file) {
		Charset cod = Charset.defaultCharset();
		JOptionPane.showMessageDialog(null,"Carregou o Arquivo");
		//try (BufferedReader stream = Files.newBufferedReader(file, cod)) {
		BufferedReader stream;
		try{
			stream = Files.newBufferedReader(file, cod);
			
			// Fazendo a leitura propriamente dita
			for (int i = 0; i < 3; i++)
				this.gaps[i] = Integer.parseInt(stream.readLine());

			for (int i = 0; i < 3; i++)
				this.tubes[i] = Integer.parseInt(stream.readLine());

			this.platforms = Integer.parseInt(stream.readLine());
			this.mountains = Integer.parseInt(stream.readLine());
			this.coins = Integer.parseInt(stream.readLine());
			this.quests = Integer.parseInt(stream.readLine());

			for (int i = 0; i < 2; i++)
				this.red_koopas[i] = Integer.parseInt(stream.readLine());

			for (int i = 0; i < 2; i++)
				this.green_koopas[i] = Integer.parseInt(stream.readLine());

			for (int i = 0; i < 2; i++)
				this.goombas[i] = Integer.parseInt(stream.readLine());

			this.spikis = Integer.parseInt(stream.readLine());
			this.plants = Integer.parseInt(stream.readLine());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.err
					.println("An IOException was ocurred at the reading of the file. Default quantities loaded.");

			this.LoadDefaultQuantities();

		} catch (NullPointerException e) {

			System.err
					.println("The opened file has wrong format. Can't read the quantities. Default quantities loaded.");
			this.LoadDefaultQuantities();
		}
	}

	/**
	 * Rotina para carregar as quantidades padrão dos elementos do cenário.
	 */
	private void LoadDefaultQuantities() {
		this.gaps = LevelMap.default_gaps;
		this.tubes = LevelMap.default_tubes;
		this.platforms = LevelMap.default_platforms;
		this.mountains = LevelMap.default_mountains;
		this.coins = LevelMap.default_coins;
		this.quests = LevelMap.default_quests;

		this.red_koopas = LevelMap.default_red_koopas;
		this.green_koopas = LevelMap.default_green_koopas;
		this.goombas = LevelMap.default_goombas;
		this.plants = LevelMap.default_plants;
		this.spikis = LevelMap.default_spikis;
	}
	
	/**
	 * Rotina para gerar uma quantidade aleatória de elementos do cenário.
	 */
	private void LoadRandomQuantities()
	{
		JOptionPane.showMessageDialog(null,"Carregou o Randon");
		Random random = new Random();
		
		for (int i=0;i<3;i++)
			this.gaps[i] = random.nextInt(2) + 1;
		
		for (int i=0;i<3;i++)
			this.tubes[i] = random.nextInt(2) + 2;
		
		this.platforms = random.nextInt(3) + 2;
		this.mountains = 0; // Alterado por Willian
		//this.mountains = random.nextInt(3) + 2;
		this.coins = random.nextInt(50) + 20;
		this.quests = random.nextInt(10) + 5;
		
		for (int i=0;i<2;i++)
			this.red_koopas[i] = random.nextInt(3) + 2;
		
		for (int i=0;i<2;i++)
			this.green_koopas[i] = random.nextInt(3) + 2;
		
		for (int i=0;i<2;i++)
			this.goombas[i] = random.nextInt(3) + 2;
		
		this.spikis = random.nextInt(5) + 2;
		this.plants = random.nextInt(this.tubes[0] + this.tubes[1] + this.tubes[2]);
			
	}
	
	//Altera��o para ler valores a partir de um arquivo txt
	public void LoadQuantitiesFromFile() {  
		JOptionPane.showMessageDialog(null,"Carregou meu metodo");
        int [] vetor = new int [100];
        try { 
        	int i = 0;
        	String aux;
        	char c = 0;
        	Scanner scanner = new Scanner(new FileReader("valores.txt")).useDelimiter(",||\\n");
        	//Scanner scanner = new Scanner(new FileReader("valores.txt")).useDelimiter("\\n");
        		
        	while (scanner.hasNext()) {
        		aux = scanner.next();
        		//if(aux.charAt(0) == '\n'){
        		c = aux.charAt(0); 
        		vetor[i] = Character.getNumericValue(c);
        		System.out.print(vetor[i] + ", ");
        		i++;
        		//}
        	}
        	
        	for (int j=0;j<3;j++)
        		this.gaps[j] = vetor[j];
        	
        	for (int j=0;j<3;j++)
        		this.tubes[j] = vetor[j+3];
        	
        	this.platforms = vetor[6];
        	this.mountains = vetor[7];
        	this.coins = vetor[8];
        	this.quests = vetor[9];
        	
        	for (int j=0;j<2;j++)
        		this.red_koopas[j] = vetor[j+10];
        	
        	for (int j=0;j<2;j++)
        		this.green_koopas[j] = vetor[j+12];
        	
        	for (int j=0;j<2;j++)
        		this.goombas[j] = vetor[j+14];
        	
        	this.spikis = vetor[16];
        	this.plants = vetor[17];
        			
        	        	
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }

	private void SalvarFaseArquivo() {
		
		JOptionPane.showMessageDialog(null,"Entrou no m�todo pra savar");
		
		Path dir = FileSystems.getDefault().getPath(
				System.getProperty("user.home"), "mario");

		if (Files.notExists(dir)){
			try{
				Files.createDirectory(dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{}
		}
		Path file = Paths.get(dir.toString()).resolve("faseGerada.html");
		
		// Definindo a codificação do arquivo (padrão da máquina virtual
		Charset cod = Charset.defaultCharset();

		// Criando uma stream ligada ao arquivo
		/*
		try (BufferedWriter stream = Files.newBufferedWriter(file, cod,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
		*/

		BufferedWriter stream;
		try {
			stream = Files.newBufferedWriter(file, cod,
					StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.TRUNCATE_EXISTING);
				
			
			//int aux = 0;
			stream.write("<table>");
			for (int i = 0; i < super.height; i ++){
				stream.write("<tr>");
				for (int j= 0 ; j < super.width; j++){
					//stream.write(Byte.toString(this.getBlock(i, j)) + ' ');
					stream.write("<td>"+Integer.toString(fase[i][j])+"</td>");
					//aux++;
				}
				//aux = 0;
				stream.write("</tr><tr>");
			}
			stream.write("</table>");
		} catch (IOException e) {

			System.err.println(e.getMessage());
		}
		
		JOptionPane.showMessageDialog(null,"Saiu do m�todo de salvar");
	}
	
		
	public void imprimeCoordenadas(){
		
		System.out.println("Gap = x: " + gap.getX() + " y: "+  gap.getY() +" largura: " + gap.lagura);
		System.out.println("Tubo =  x: "+ tubo.getX() + " y: "+ tubo.getY() + " altura: "+ tubo.altura);
		System.out.println("Plataformas = x: " + plataforma.getX() + " y: " + plataforma.getY() + " largura: "+ plataforma.lagura + " altura: "+ plataforma.altura);
		System.out.println("Moedas: ");
		for(int i = 0; i < this.coins; i++)
			System.out.println("\t x: " + moedas[i].getX() + " y : " + moedas[i].getY());
		System.out.println("Blocos: ");
		for(int i = 0; i < this.quests; i++)
			System.out.println("\t x: " + blocos[i].getX() + " y : " + blocos[i].getY());
		System.out.println("Gomba sem asa = x: " + goomba1.getX()+ " y:" + goomba1.getY());
		System.out.println("Gomba com asa = x: " + goomba2.getX()+ " y:" + goomba2.getY());
		System.out.println("Koopa Verde sem asa = x: " + koopa_verde1.getX()+ " y:" + koopa_verde1.getY());
		System.out.println("Koopa Verde com asa = x: " + koopa_verde2.getX()+ " y:" + koopa_verde2.getY());
		System.out.println("Koopa Vermelha sem asa = x: " + koopa_vermelha1.getX()+ " y:" + koopa_vermelha1.getY());
		System.out.println("Koopa Vermelha com asa = x: " + koopa_vermelha2.getX()+ " y:" + koopa_vermelha2.getY());
		System.out.println("Plantas = x: "+ planta.getX() + " y: "+ planta.getY());
		
		
		Path dir = FileSystems.getDefault().getPath(
				System.getProperty("user.home"), "mario");

		if (Files.notExists(dir)){
			try{
				Files.createDirectory(dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{}
		}
		Path file = Paths.get(dir.toString()).resolve("QuantidadesItensFase.txt");
		
		// Definindo a codificação do arquivo (padrão da máquina virtual
		Charset cod = Charset.defaultCharset();

		// Criando uma stream ligada ao arquivo
		/*
		try (BufferedWriter stream = Files.newBufferedWriter(file, cod,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
		*/
		try {
			BufferedWriter stream = Files.newBufferedWriter(file, cod,
					StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.TRUNCATE_EXISTING);
			
			stream.write(gap.getX() + "\t"+  gap.getY() + "\t" + gap.lagura + "\n");
			stream.write(tubo.getX() + "\t"+ tubo.getY() + "\t" + tubo.altura+ "\n");
			stream.write( plataforma.getX() + "\t" + plataforma.getY() + "\t" + plataforma.lagura + "\t" + plataforma.altura + "\n");
			stream.write(this.coins + "\n");
			for(int i = 0; i < this.coins; i++)
				stream.write( moedas[i].getX() + "\t" + moedas[i].getY()+ "\n");
			stream.write(this.quests + "\n");
			for(int i = 0; i < this.quests; i++)
				stream.write(blocos[i].getX() + "\t" + blocos[i].getY()+ "\n");
			stream.write(goomba1.getX()+ "\t" + goomba1.getY()+ "\n");
			stream.write(goomba2.getX()+ "\t" + goomba2.getY()+ "\n");
			stream.write(koopa_verde1.getX()+ "\t" + koopa_verde1.getY()+ "\n");
			stream.write(koopa_verde2.getX()+ "\t" + koopa_verde2.getY()+ "\n");
			stream.write(koopa_vermelha1.getX()+ "\t" + koopa_vermelha1.getY()+ "\n");
			stream.write(koopa_vermelha2.getX()+ "\t" + koopa_vermelha2.getY()+ "\n");
			stream.write(planta.getX() + "\t" + planta.getY()+ "\n");			
			
		} catch (IOException e) {

			System.err.println(e.getMessage());
		}
		
	}

	public void CreateMap2() {
		
		//********** Lendo o arquivo de texto
		
		int vetor[];
	    int cont = 0;
	    int tamanho = 0;
	    String nome = "fase.txt";
	    String aux = "";
	    
	    /*Lendo a quantidade de numeros no txt para calcular o 
	     * tamanho do vetor
	     * */ 	    
	    try {
	    	FileReader arquivo = new FileReader(nome); 
	    	BufferedReader lerArq = new BufferedReader(arquivo); 
	    	String linha = lerArq.readLine();
	    	while (linha != null) { 
	    		for (int i=0; i< linha.length(); i++){
	    			if(linha.charAt(i)=='\t')
	    				tamanho++;    			
	    		}
	    		tamanho++; 			
	    		linha = lerArq.readLine(); 
	    	} 
	    	arquivo.close(); 	    	
	    } catch (IOException e) {
	    	System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
	    }
	    
	    
	    vetor = new int [tamanho+1];
	    
	    try {
	    	FileReader arq = new FileReader(nome); 
	    	BufferedReader lerArq = new BufferedReader(arq); 
	    	String linha = lerArq.readLine();
	    	while (linha != null) { 
	    		//System.out.printf("%s\n", linha);
	    		for (int i=0; i< linha.length(); i++){
	    			if(linha.charAt(i)!='\t')
	    				aux+=linha.charAt(i);
	    			else{
	    				vetor[cont] = Integer.parseInt(aux);
	    				aux="";
	    				cont++;
	    			}   			
	    			
	    		}
	    		vetor[cont] = Integer.parseInt(aux);
				aux="";
				cont++;    			
	    		linha = lerArq.readLine(); 
	    	} 
	    	arq.close(); 	    	
	    } catch (IOException e) {
	    	System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
	    }
	    
	    System.out.println("Imprimindo o vetor lido: \nTamanho: "+ tamanho);
	    for(int i = 0; i < cont; i++)
	    	System.out.println(i+": "+ vetor[i]);
	    System.out.println();
	    
	    
		
		//Fim da leitura do arquivo de texto

		
	    /*
	    this.CreateFloor();

		int i = 0;
		Random n = new Random();

		// Criando o portal da sa�da
		super.xExit = super.width - 8;
		super.yExit = super.height - 1;

		int i_gaps[] = { 0, 0, 0 };

		for (i = 0; i < 3; i++) {
			while (i_gaps[i] != this.gaps[i]) {

				//int offset = vetor[0]; //Gera um valor aleat�rio entre 0 e largura-10
				// int width = n.nextInt(5);
				//int width = i + 3;

				//if (offset > 10) {

					try {
						this.CreateGap(vetor[0], vetor[2]);
						i_gaps[i]++;

					} catch (AnotherGapException e) {
						System.err.print(e.getMessage());
					}
				//}
			}
		}

		i = 0;
		int i_tubes[] = { 0, 0, 0 };

		for (i = 0; i < 3; i++) {
			while (i_tubes[i] != this.tubes[i]) {

				int offset[] = { vetor[3],vetor[4] };
				int height = i + 2;

				if (offset[0] > 8) {
					try {

						this.CreateTube(offset, height);
						tubo.setCoordenadas(offset[0], offset[1]);
						tubo.altura = height;
						i_tubes[i]++;

					} catch (WithoutFloorException | ExistingElementException
							| OverTubeException e) {

						System.err.println(e.getMessage());
					}
				}
			}
		}

		i = 0;

		while (i != this.platforms) {

			int offset[] = { vetor[6], vetor[7] };

			int height = vetor[9];
			int width = vetor[8];

			if (offset[0] > 8) {

				if ((width > 1) && (height > 0)) {

					try {

						this.CreatePlatform(offset, width, height);
						//plataforma.setCoordenadas(offset[0], offset[1]);
						i++;

					} catch (WithoutFloorException | ExistingElementException
							| OverTubeException e) {

						System.err.println(e.getMessage());
					}
				}
			}
		}
		*/
		
		//Inseri para criar as montanhas
		/*
		i = 0;

		while (i != this.mountains) {

			int offset[] = { n.nextInt(super.width - 10),
					n.nextInt(super.height - 1) };

			int height = n.nextInt(5);
			int width = n.nextInt(10);

			if (offset[0] > 8) {

				if ((width > 1) && (height > 0)) {

					try {

						this.CreateMountain(offset, width, height);
						i++;

					} catch (WithoutFloorException | OverTubeException e) {

						System.err.println(e.getMessage());
					}
				}
			}
		}
		
		*/
		
		//this.addEnemies(vetor);
		
		//this.imprimeCoordenadas();
		//this.preencheMatriz();
		//this.SalvarFaseArquivo();
	}
	
	public void addItens(int vetor[], int i) {
		i = this.CriaMoedas(vetor, i);
		i = this.CriarBlocosCoin(vetor, i);
		i = this.CriarBlocosPW(vetor, i);
		i = this.AddGoomba(vetor, i);
		i = this.AddKoopa(vetor, i);
		i = this.AddPlantas(vetor, i);
		i = this.AddSpiky(vetor, i);
		
	}
	
	public int  AddKoopa(int vetor[], int i){
		
		/*************** Koopa Verde sem asas ***************/
		int j;
		int t = vetor[i]*2 + i;
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(Enemy.ENEMY_GREEN_KOOPA, false));			
			System.out.println("Koopa Verde sem asa inserida em ("+vetor[j]+", "+vetor[j+1]+")");
		}
		
		/*************** Koopa Verde com asas ***************/
		i = j;
		t = vetor[i]*2 + i;
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(	Enemy.ENEMY_GREEN_KOOPA, true));			
			System.out.println("Koopa Verde com asa inserida em ("+vetor[j]+", "+vetor[j+1]+")");
		}
		
		/*************** Koopa Vermelha sem asas ***************/					
		i = j;
		t = vetor[i]*2 + i;	
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(Enemy.ENEMY_RED_KOOPA, false));			
			System.out.println("Koopa Vermelha sem asa inserida em ("+vetor[j]+", "+vetor[j+1]+")");
		}					
		
		/*************** Koopa Vermelha com asas ***************/
		i = j;
		t = vetor[i]*2 + i;
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(Enemy.ENEMY_RED_KOOPA, true));			
			System.out.println("Koopa Verde com asa inserida em ("+vetor[j]+", "+vetor[j+1]+")");
		}
			
		return j;
			
	}
	
	public int AddGoomba(int vetor[], int i){		

		/************************ Goombas sem asas  ********************/
		
		int j;
		int t = vetor[i]*2 + i;
		
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(
					Enemy.ENEMY_GOOMBA, false));
			System.out.println("Goomba sem asas em  ("+vetor[j]+", "+vetor[j+1]+")");
		}		
		
		/************************ Goombas com asas  ********************/
							
		i = j;
		t = vetor[i]*2 + i;
				
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(
								Enemy.ENEMY_GOOMBA, true));
			System.out.println("Goomba com asas em  ("+vetor[j]+", "+vetor[j+1]+")");
		}
			
		return j;
	}

	public int AddPlantas(int vetor[], int i){
		
		int j;
		int t = vetor[i]*2 + i;
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(
					Enemy.ENEMY_FLOWER, false));
			System.out.println("Planta em  ("+vetor[j]+", "+vetor[j+1]+")");
		}	
					
		return j;
	}
	
	public int AddSpiky(int vetor[], int i ){
		
		int j;
		int t = vetor[i]*2 + i;
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			setSpriteTemplate(vetor[j], vetor[j+1], new SpriteTemplate(Enemy.ENEMY_SPIKY, false));
			System.out.println("Spike em  ("+vetor[j]+", "+vetor[j+1]+")");
		}	
		
		return j;			
	}
	
	public void CriarBlocos(int vetor[]) {

		//Instanciando o vetor de blocos ***********
		for(int i= 0;i<this.quests; i++)
			blocos[i] = new ObjetosMapa();
		//******************************************
		Random n = new Random();
		int cont = 0;
		for(int i = 0; i < vetor[31]*2; i+=2){
			
			boolean aux = n.nextBoolean();

				if (aux)
					this.setBlock(vetor[32+i], vetor[33+i], BLOCK_POWERUP);
				else
					this.setBlock(vetor[32+i], vetor[33+i], BLOCK_COIN);

				blocos[cont].setCoordenadas(vetor[32+i], vetor[33+i]);
				cont++;
		}
	}
	
	public int CriarBlocosPW(int vetor[], int i) {

		int j;
		int t = vetor[i]*2 + i;
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			this.setBlock(vetor[j], vetor[j+1], Level.BLOCK_POWERUP);
			System.out.println("Blocos Power-Up em ("+vetor[j]+", "+vetor[j+1]+")");
		}
			
		return j;	
	}
	
	public int CriarBlocosCoin(int vetor[], int i) {

		int j;
		int t = vetor[i]*2 + i;
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			this.setBlock(vetor[j], vetor[j+1], Level.BLOCK_COIN);
			System.out.println("Blocos Moedas em ("+vetor[j]+", "+vetor[j+1]+")");
		}
			
		return j;			
	}

	public int CriaMoedas (int vetor[], int i) {		
		int j;
		int t = vetor[i]*2 + i;
		
		System.out.println("i: "+ i +" - t: "+ t);
		for(j = i + 1; j < t; j+=2){
			this.setBlock(vetor[j], vetor[j+1], Level.COIN);
			System.out.println("Moedas em ("+vetor[j]+", "+vetor[j+1]+")");
		}
			
		return j;
	}
	
public void CreateMap3() {
		
		//********** Lendo o arquivo de texto
		
		int vetor[];
	    int cont = 0;
	    int tamanho = 0;
	    String nome = "fase.txt";
	    String aux = "";
	    
	    /*Lendo a quantidade de numeros no txt para calcular o 
	     * tamanho do vetor
	     * */ 	    
	    try {
	    	FileReader arquivo = new FileReader(nome); 
	    	BufferedReader lerArq = new BufferedReader(arquivo); 
	    	String linha = lerArq.readLine();
	    	while (linha != null) { 
	    		for (int i=0; i< linha.length(); i++){
	    			if(linha.charAt(i)=='\t')
	    				tamanho++;    			
	    		}
	    		tamanho++; 			
	    		linha = lerArq.readLine(); 
	    	} 
	    	arquivo.close(); 	    	
	    } catch (IOException e) {
	    	System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
	    }
	    
	    
	    vetor = new int [tamanho+1];
	    
	    try {
	    	FileReader arq = new FileReader(nome); 
	    	BufferedReader lerArq = new BufferedReader(arq); 
	    	String linha = lerArq.readLine();
	    	while (linha != null) { 
	    		//System.out.printf("%s\n", linha);
	    		for (int i=0; i< linha.length(); i++){
	    			if(linha.charAt(i)!='\t')
	    				aux+=linha.charAt(i);
	    			else{
	    				vetor[cont] = Integer.parseInt(aux);
	    				aux="";
	    				cont++;
	    			}   			
	    			
	    		}
	    		vetor[cont] = Integer.parseInt(aux);
				aux="";
				cont++;    			
	    		linha = lerArq.readLine(); 
	    	} 
	    	arq.close(); 	    	
	    } catch (IOException e) {
	    	System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
	    }
	    
	    System.out.println("Imprimindo o vetor lido: \nTamanho: "+ tamanho);
	    for(int i = 0; i < cont; i++)
	    	System.out.println(i+": "+ vetor[i]);
	    System.out.println();	    
		
		//Fim da leitura do arquivo de texto		
	    
	    this.CreateFloor(); //Criando o ch�o
	    
	    // Criando o portal da sa�da
	    super.xExit = super.width - 8;
	 	super.yExit = super.height - 1;
	 	
	 	int gap = vetor[0];
		int tube = vetor[0]+1+vetor[0]*3;
		
		
		tela = new ElementosTela(gap, tube, coins, quests, platforms, 
				mountains, goombas, green_koopas, red_koopas,plants, spikis);

	    
		int indice = 0;
		int i = 0;
		
		for (i = indice + 1; i < vetor[indice]*3;) {
			try {
					this.CreateGap(vetor[i], vetor[i+2]);
					i+=3;
				} catch (AnotherGapException e) {
						System.err.print(e.getMessage());
				}
		}
	
		indice += i;
		int t = vetor[indice]*3 + indice;
		
		for (i = indice + 1; i < t; ) {
			int offset[] = { vetor[i], vetor[i+1] };
			try {
				this.CreateTube(offset, vetor[i+2]);
				i+=3;
			} catch (WithoutFloorException e) {
					System.err.println(e.getMessage());
			}catch (ExistingElementException e) {
				System.err.println(e.getMessage());
			}catch (OverTubeException e) {
				System.err.println(e.getMessage());
		}
		}
	
		indice = i;
		i = indice + 1;
		t = vetor[indice]*4 + indice;

		while (i < t) {

			int offset[] = { vetor[i], vetor[i+1] };
			try {
				System.out.println("indice: "+ i);
				System.out.println("x: "+ vetor[i]+ " y: "+ vetor[i+1]+ " l: "+ vetor[i+2]+" a: "+vetor[i+3]);
				this.CreatePlatform(offset, vetor[i+2], vetor[i+3]);
				i+=4;
			} catch (WithoutFloorException e) {
						System.err.println(e.getMessage());
			}catch (ExistingElementException e) {
				System.err.println(e.getMessage());
			}catch (OverTubeException e) {
				System.err.println(e.getMessage());
	}
		}
		
		indice = i;
		this.addItens(vetor, indice);

	/**/
		
		//Inseri para criar as montanhas
		/*
		i = 0;

		while (i != this.mountains) {

			int offset[] = { n.nextInt(super.width - 10),
					n.nextInt(super.height - 1) };

			int height = n.nextInt(5);
			int width = n.nextInt(10);

			if (offset[0] > 8) {

				if ((width > 1) && (height > 0)) {

					try {

						this.CreateMountain(offset, width, height);
						i++;

					} catch (WithoutFloorException | OverTubeException e) {

						System.err.println(e.getMessage());
					}
				}
			}
		}
		
		*/
		
		
		
		//this.imprimeCoordenadas();
		//this.preencheMatriz();
		//this.SalvarFaseArquivo();
	}

public void addEnemies2() {
	/*
	 * Criando contadores para os inimigos já colocados no mapa
	 */
	int add_gkoopas[] = { 0, 0 };
	int add_rkoopas[] = { 0, 0 };
	int add_goombas[] = { 0, 0 };
	int add_plants = 0;
	int add_spikis = 0;

	int x;
	int y;
	Random random = new Random();

		//System.out.println("Add inimigos. Tipo:" + tipo);
		//tipo = 0;
		x = random.nextInt(super.width);
		y = random.nextInt(super.height - 1);
		/*
		 * Verificando se não existe um inimigo nessa coordenada
		 */
		SpriteTemplate aux = getSpriteTemplate(x, y);
		
		while (add_gkoopas[0] < green_koopas[0]) {
			
			if(aux == null){
				boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
					|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
					|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
					|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

				if (getBlock(x, y + 1) == Level.HILL_TOP && !cano) {
					add_gkoopas[0]++;
					setSpriteTemplate(x, y, new SpriteTemplate(
						Enemy.ENEMY_GREEN_KOOPA, false));
				
					koopa_verde1.setCoordenadas(x, y); //Coordenada da Koopa verde sem asa.
					//Salvando gKoopas sem asas
					tela.setKoopaVerdeSemAsa(x, y);
					System.out.println("Koopa  verde sem asa Inserida em ("+ x+", "+ y + ")");
				}
			}			
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
		}
		
		System.out.println("Inseriu Koopa verde sem asa. Tentando inserir a com asa");
		
		int cont = 0;
		while (add_gkoopas[1] < green_koopas[1]) {
			
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
			if(cont < 20){
				System.out.println("x: "+ x +", y: "+ y + ")");
				cont++;
				if(aux == null) System.out.println("Aux null");
				else System.out.println("Aux n�o null");
			}
			
			
			if(aux == null){
			
				boolean plataforma = false;
				int i_y;
				
				for (i_y = y + 1; i_y < super.height - 1; i_y++) {
					plataforma = getBlock(x, i_y) == Level.HILL_TOP;

					if (plataforma)
						break;
				}
				
				if (plataforma)
					System.out.println("INSERINDO KOOPA VERDE COM ASA. plataforma=true");
				else
					System.out.println("INSERINDO KOOPA VERDE COM ASA. plataforma=false");
				
				for (i_y = 0; i_y < super.height - 1; i_y++)
					plataforma = plataforma && (getSpriteTemplate(x, i_y) == null);
				
				plataforma = true;

				if (plataforma) {
					System.out.println("Entrou no IF");
					add_gkoopas[1]++;
					setSpriteTemplate(x, y, new SpriteTemplate(Enemy.ENEMY_GREEN_KOOPA, true));
				
					koopa_verde2.setCoordenadas(x, y); //Coordenada da Koopa verde com asa.
					//Salvando gKoopa com asas
					tela.setKoopaVerdeComAsa(x, y);
					System.out.println("Koopa verde com asa Inserida em ("+ x+", "+ y + ")");
				}else System.out.println("Nao entrou no IF");
			}
		}
		
		while (add_rkoopas[0] < red_koopas[0]) {
			
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
			
			if(aux == null){
				boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
					|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
					|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
					|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

				if ((getBlock(x, y + 1) == Level.HILL_TOP) && !cano) {
					add_rkoopas[0]++;
					setSpriteTemplate(x, y, new SpriteTemplate(Enemy.ENEMY_RED_KOOPA, false));
				
					koopa_vermelha1.setCoordenadas(x, y); //Coordenadas da Koopa vermalha sem asa
					//Salvando rKoopa sem asa
					tela.setKoopaVermelhaSemAsa(x, y);
					System.out.println("Koopa Vermelha sem asa Inserida em ("+ x+", "+ y + ")");
				}
			}
		}
	
		while (add_rkoopas[1] < red_koopas[1]) {
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
			
			if(aux == null){
				boolean plataforma = false;
				int i_y;

				for (i_y = y + 1; i_y < super.height - 1; i_y++) {
					plataforma = (getBlock(x, i_y) == Level.HILL_TOP);

					if (plataforma)
						break;
				}

				for (i_y = 0; i_y < super.height - 1; i_y++)
					plataforma = plataforma && (getSpriteTemplate(x, i_y) == null);
				
				plataforma = true;
				if (plataforma) {
					add_rkoopas[1]++;
					setSpriteTemplate(x, y, new SpriteTemplate(
						Enemy.ENEMY_RED_KOOPA, true));
				
					koopa_vermelha2.setCoordenadas(x, y); //Coordenadas da Koopa vermalha com asa
					//Salvando rKoopa com asa
					tela.setKoopaVermelhaComAsa(x, y);
					System.out.println("Koopa Vermelha com asa Inserida em ("+ x+", "+ y + ")");
				}
			}
		}
		
		while (add_goombas[0] < goombas[0]) {
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
			
			if(aux == null){
				boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
					|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
					|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
					|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

				if (getBlock(x, y + 1) == Level.HILL_TOP && !cano) {
					add_goombas[0]++;
					setSpriteTemplate(x, y, new SpriteTemplate(
						Enemy.ENEMY_GOOMBA, false));
				
					goomba1.setCoordenadas(x, y); //Coordenadas da Goomba sem asa
					//Salvando Gomba sem asa
					tela.setGombaSemAsa(x, y);
					System.out.println("Goomba sem asa Inserida em ("+ x+", "+ y + ")");
				}
			}
		}

		while (add_goombas[1] < goombas[1]) {
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
			
			if(aux == null){
				boolean plataforma = false;
				int i_y;

				for (i_y = y + 1; i_y < super.height - 1; i_y++) {
					plataforma = (getBlock(x, i_y) == Level.HILL_TOP);

					if (plataforma)
						break;
				}

				for (i_y = 0; i_y < super.height - 1; i_y++)
					plataforma = plataforma
						&& (getSpriteTemplate(x, i_y) == null);
				
				plataforma = true;

				if (plataforma) {
					add_goombas[1]++;
					setSpriteTemplate(x, y, new SpriteTemplate(
						Enemy.ENEMY_GOOMBA, true));
				
					goomba2.setCoordenadas(x, y); //Coordenadas da Goomba com asa
					//Salvando Gomba com asas
					tela.setGombaComAsa(x, y);
					System.out.println("Goomba com asa Inserida em ("+ x+", "+ y + ")");
				}
			}
		}
		
		while (add_plants < plants) {
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
			
			if(aux == null){
			
			boolean tubo = getBlock(x , y + 1) == Level.TUBE_TOP_LEFT
					|| getBlock(x, y + 1) == Level.TUBE_TOP_RIGHT;
			
			//tubo = true;

			if (tubo) {
				add_plants++;
				setSpriteTemplate(x , y, new SpriteTemplate(
						Enemy.ENEMY_FLOWER, false));
				
				planta.setCoordenadas(x-1, y);
				//Salvando plantas
				tela.setPlant(x-1, y);
				System.out.println("Planta Inserida em ("+ x+", "+ y + ")");
			}
		}
		}

		while (add_spikis < spikis) {
			x = random.nextInt(super.width);
			y = random.nextInt(super.height - 1);
			aux = getSpriteTemplate(x, y);
			
			if(aux == null){
				boolean cano = (getBlock(x, y) == Level.TUBE_SIDE_LEFT)
					|| (getBlock(x, y) == Level.TUBE_SIDE_RIGHT)
					|| (getBlock(x, y) == Level.TUBE_TOP_LEFT)
					|| (getBlock(x, y) == Level.TUBE_TOP_RIGHT);

				if (getBlock(x, y + 1) == Level.HILL_TOP && !cano) {
					add_spikis++;
					setSpriteTemplate(x, y, new SpriteTemplate(
						Enemy.ENEMY_SPIKY, false));
				
					//Salvando Spike
					tela.setSpike(x, y);
					System.out.println("Spike Inserido em ("+ x +", "+ y+1 + ")");
				}
			}
		}
		
		
		System.out.println("**** Todos Inimigos Inseridos ****");

	}
	

}
