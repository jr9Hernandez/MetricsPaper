package dk.itu.mario.scene;

import java.awt.GraphicsConfiguration;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import br.ufv.julian.Metrics;
import br.ufv.julian.ReadXsl;
import br.ufv.willian.LevelGenerator;
import br.ufv.willian.WillianLevelGenerator;
import br.ufv.willian.auxiliares.VariaveisGlobais;
import pet.PetLevelGenerator;
import muller.MuellerLevelGenerator;
import dk.itu.mario.level.BgLevelGenerator;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sonar.FixedSoundSource;
import dk.itu.mario.engine.sprites.CoinAnim;
import dk.itu.mario.engine.sprites.FireFlower;
import dk.itu.mario.engine.sprites.Mario;
import dk.itu.mario.engine.sprites.Mushroom;
import dk.itu.mario.engine.sprites.Particle;
import dk.itu.mario.engine.sprites.Sprite;
import dk.itu.mario.engine.Art;
import dk.itu.mario.engine.BgRenderer;
import dk.itu.mario.engine.DataRecorder;
import dk.itu.mario.engine.LevelRenderer;
import dk.itu.mario.engine.MarioComponent;
import dk.itu.mario.level.CustomizedLevel;
import dk.itu.mario.level.Level;
import dk.itu.mario.level.RandomLevel;
import dk.itu.mario.level.generator.CustomizedLevelGenerator;
import dk.itu.mario.engine.Play;
import dk.itu.mario.res.ResourcesManager;

public class LevelSceneTest extends LevelScene {

	ArrayList<Double> switchPoints;
	private double thresshold; // how large the distance from point to mario
								// should be before switching
	private int point = -1;
	private int[] checkPoints;
	private boolean isCustom;

	public LevelSceneTest(GraphicsConfiguration graphicsConfiguration,
			MarioComponent renderer, long seed, int levelDifficulty, int type,
			boolean isCustom) {
		super(graphicsConfiguration, renderer, seed, levelDifficulty, type);
		this.isCustom = isCustom;
	}

	public void init() {
		try {
			Level.loadBehaviors(new DataInputStream(ResourcesManager.class
					.getResourceAsStream("res/tiles.dat")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		if (level == null)
			if (isCustom) {
				// CustomizedLevelGenerator clg = new
				// CustomizedLevelGenerator();
				// GamePlay gp = new GamePlay();
				// gp = gp.read("player.txt");
				// currentLevel = (Level)clg.generateLevel(gp);

				//MuellerLevelGenerator mlg = new MuellerLevelGenerator();

				//GamePlay gp = new GamePlay();
				//gp = gp.read("player.txt");
				//currentLevel = (Level) mlg.generateLevel(gp);

				// Instanciando minha classe LevelMap :^D
				//PetLevelGenerator level = new PetLevelGenerator();
				//currentLevel = (Level) level.generateLevel(gp);		
				
				
				//test gral
				ReadXsl objRead=new ReadXsl();
				objRead.reading(320, 15, levelSeed, levelDifficulty, levelType);
				//objRead.compresionDistance(320, 15, levelSeed, levelDifficulty, levelType);
			    currentLevel = LevelGenerator.createLevel(320, 15, levelSeed, levelDifficulty, levelType);
			    //System.out.println("El width é"+currentLevel.getWidth());
			    //test individual
				//Metrics objMetrics=new Metrics(currentLevel.getWidth(), currentLevel.getHeight(), currentLevel);
				//objMetrics.MetricsCalc(1);
				//objMetrics.MetricsCalc(2);
				//objMetrics.MetricsCalc(3);
				//objMetrics.MetricsCalc(4);
			    
			    
			    /*test metric4
				String delimitadores = "\\s+";
				String[] cadenasSeparadas;
				
				String telas="tela971 tela119 tela593 tela56 tela1232 tela1474 tela140 tela985 ";
				cadenasSeparadas = telas.split(delimitadores);
				currentLevel = LevelGenerator.createLevelMetrics(
						320, 15, levelSeed, levelDifficulty, levelType, 0,
						cadenasSeparadas);
				Level currentLevel2;
				try {
					currentLevel2=currentLevel.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String telas2="tela1825 tela38 tela1788 tela2043 tela1622 tela1232 tela43 tela2488 ";
				cadenasSeparadas = telas2.split(delimitadores);
				currentLevel2 = LevelGenerator.createLevelMetrics(
						320, 15, levelSeed, levelDifficulty, levelType, 0,
						cadenasSeparadas);
			    //System.out.println("El width é"+currentLevel.getWidth());
				Metrics objMetrics=new Metrics(currentLevel.getWidth(), currentLevel.getHeight(), currentLevel);
				//objMetrics.MetricsCalc(1);
				//objMetrics.MetricsCalc(2);
				//objMetrics.MetricsCalc(3);
				objMetrics.MetricsCalc(4,currentLevel2);*/
			} else
				currentLevel = new RandomLevel(320, 15, levelSeed, levelDifficulty, levelType);
				

		try {
			level = currentLevel.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		// level is always overground
		Art.startMusic(1);

		paused = false;
		Sprite.spriteContext = this;
		sprites.clear();

		layer = new LevelRenderer(level, graphicsConfiguration, 320, 240);
		for (int i = 0; i < 2; i++) {
			int scrollSpeed = 4 >> i;
			int w = ((level.getWidth() * 16) - 320) / scrollSpeed + 320;
			int h = ((level.getHeight() * 16) - 240) / scrollSpeed + 240;
			Level bgLevel = BgLevelGenerator.createLevel(w / 32 + 1,
					h / 32 + 1, i == 0, levelType);
			bgLayer[i] = new BgRenderer(bgLevel, graphicsConfiguration, 320,
					240, scrollSpeed);
		}

		double oldX = 0;
		if (mario != null)
			oldX = mario.x;

		mario = new Mario(this);
		sprites.add(mario);
		startTime = 1;

		//timeLeft = 200 * 15;
		timeLeft = 300 * 15;

		tick = 0;

		/*
		 * SETS UP ALL OF THE CHECKPOINTS TO CHECK FOR SWITCHING
		 */
		switchPoints = new ArrayList<Double>();

		// first pick a random starting waypoint from among ten positions
		int squareSize = 16; // size of one square in pixels
		int sections = 10;

		double startX = 32; // mario start position
		double endX = level.getxExit() * squareSize; // position of the end on
														// the level
		if (!isCustom && recorder == null)
			recorder = new DataRecorder(this, (RandomLevel) level, keys);
		else{
			if (recorder2 == null)
			  recorder2 = new DataRecorder(this, level, keys);
		}

		gameStarted = false;
	}

	public void tick() {
		super.tick();

		if (recorder != null && !gameStarted) {
			recorder.startLittleRecord();
			recorder.startTime();
			gameStarted = true;
		}
		else{
			if (recorder2 != null && !gameStarted) {
				recorder2.startLittleRecord();
				recorder2.startTime();
				gameStarted = true;
			}
		}
		if (recorder != null)
			recorder.tickRecord();
		else
			if (recorder2 != null)
				recorder2.tickRecord();
	}

	
	public void winActions() {
		if (recorder != null)
			recorder.fillGamePlayMetrics((RandomLevel) level);
		else{
			if (recorder2 != null){
				recorder2.fillGamePlayMetrics(level);
				recorder2 = new DataRecorder(this, level, keys);
			}
			
		}
		
		VariaveisGlobais.morreuEm = -1;
		marioComponent.win();
	}

	public void deathActions() {
		
		//System.out.println("Morreu em " + mario.x);
		if((int)mario.x > VariaveisGlobais.morreuEm)
			VariaveisGlobais.morreuEm = (int)mario.x;
		VariaveisGlobais.num_mortes++;
		
		if (Mario.lives <= 0) {// has no more lives
			if (recorder != null)
				recorder.fillGamePlayMetrics((RandomLevel) level);
			else
				if (recorder2 != null)
					recorder2.fillGamePlayMetrics(level);
			marioComponent.lose();
		} else
			// mario still has lives to play :)--> have a new beginning
			reset();
	}

	public void bump(int x, int y, boolean canBreakBricks) {
		byte block = level.getBlock(x, y);

		if ((Level.TILE_BEHAVIORS[block & 0xff] & Level.BIT_BUMPABLE) > 0) {
			bumpInto(x, y - 1);
			level.setBlock(x, y, (byte) 4);

			if (((Level.TILE_BEHAVIORS[block & 0xff]) & Level.BIT_SPECIAL) > 0) {
				sound.play(Art.samples[Art.SAMPLE_ITEM_SPROUT],
						new FixedSoundSource(x * 16 + 8, y * 16 + 8), 1, 1, 1);
				if (!Mario.large) {
					addSprite(new Mushroom(this, x * 16 + 8, y * 16 + 8));
				} else {
					addSprite(new FireFlower(this, x * 16 + 8, y * 16 + 8));
				}

				if (recorder != null) {
					recorder.blockPowerDestroyRecord();
				}
				else
					if (recorder2 != null) 
						recorder2.blockPowerDestroyRecord();
			} else {
				// TODO should only record hidden coins (in boxes)
				if (recorder != null) {
					recorder.blockCoinDestroyRecord();
				}
				else
					if (recorder2 != null) {
						recorder2.blockCoinDestroyRecord();
					}

				Mario.getCoin();
				sound.play(Art.samples[Art.SAMPLE_GET_COIN],
						new FixedSoundSource(x * 16 + 8, y * 16 + 8), 1, 1, 1);
				addSprite(new CoinAnim(x, y));
			}
		}

		if ((Level.TILE_BEHAVIORS[block & 0xff] & Level.BIT_BREAKABLE) > 0) {
			bumpInto(x, y - 1);
			if (canBreakBricks) {
				if (recorder != null) {
					recorder.blockEmptyDestroyRecord();
				}else
					if (recorder2 != null) {
						recorder2.blockEmptyDestroyRecord();
					}

				sound.play(Art.samples[Art.SAMPLE_BREAK_BLOCK],
						new FixedSoundSource(x * 16 + 8, y * 16 + 8), 1, 1, 1);
				level.setBlock(x, y, (byte) 0);
				for (int xx = 0; xx < 2; xx++)
					for (int yy = 0; yy < 2; yy++)
						addSprite(new Particle(x * 16 + xx * 8 + 4, y * 16 + yy
								* 8 + 4, (xx * 2 - 1) * 4, (yy * 2 - 1) * 4 - 8));
			}

		}
	}

	public void bumpInto(int x, int y) {
		byte block = level.getBlock(x, y);
		if (((Level.TILE_BEHAVIORS[block & 0xff]) & Level.BIT_PICKUPABLE) > 0) {
			Mario.getCoin();
			sound.play(Art.samples[Art.SAMPLE_GET_COIN], new FixedSoundSource(
					x * 16 + 8, y * 16 + 8), 1, 1, 1);
			level.setBlock(x, y, (byte) 0);
			addSprite(new CoinAnim(x, y + 1));

			// TODO no idea when this happens... maybe remove coin count
			if (recorder != null)
				recorder.recordCoin();
			else
				if (recorder2 != null)
					recorder2.recordCoin();
		}

		for (Sprite sprite : sprites) {
			sprite.bumpCheck(x, y);
		}
	}

	private int randomNumber(int low, int high) {
		return new Random(new Random().nextLong()).nextInt(high - low) + low;
	}

	private int toBlock(float n) {
		return (int) (n / 16);
	}

	private int toBlock(double n) {
		return (int) (n / 16);
	}

	private float toReal(int b) {
		return b * 16;
	}

}
