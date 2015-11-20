package glentakahashi;

import dk.itu.mario.MarioInterface.Constraints;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.level.Level;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class UltraCustomizedLevel.
 */
public class UltraCustomizedLevel extends Level implements LevelInterface {

	/** The player metrics. */
	private GamePlay playerMetrics;

	/** The rand gen. */
	private Random randGen;

	/** The level map. */
	private byte[][] levelMap;

	/** The map list. */
	private Map mapList;

	/** The ceiling list. */
	private Map ceilingList;

	/** The block list. */
	private LinkedList<ItemBlock> blockList;

	/** The hill list. */
	private LinkedList<HillBlock> hillList;

	/** The enemy list. */
	private LinkedList<EnemyBlock> enemyList;

	/** The pipe list. */
	private LinkedList<PipeBlock> pipeList;

	/** The theme. */
	private int theme;

	/** The Constant EMPTY. */
	private static final byte EMPTY = 0;

	/** The Constant TYPE_EMPTY. */
	public static final int TYPE_EMPTY = 0;

	/** The Constant TYPE_GROUND. */
	public static final int TYPE_GROUND = 1;

	/** The Constant TYPE_CEILING. */
	public static final int TYPE_CEILING = 2;

	/** The Constant TYPE_COINS. */
	public static final int TYPE_COINS = 0;

	public static final int TYPE_HIDDEN_COINS = 1;

	/** The Constant TYPE_BLOCKS. */
	public static final int TYPE_BLOCKS = 2;

	public static final int TYPE_BLOCKS_COINS = 3;

	public static final int TYPE_BLOCKS_COINS_FILLED = 4;

	/** The Constant TYPE_BLOCKS_POWERUP. */
	public static final int TYPE_BLOCKS_POWERUP = 5;

	public static final int TYPE_BLOCKS_ONLY_POWERUP = 6;

	public static final int TYPE_BLOCKS_POWERUP_FILLED = 7;

	/** The Constant ENEMY_GOOMBA. */
	public static final int ENEMY_GOOMBA = SpriteTemplate.GOOMPA;

	/** The Constant ENEMY_GREEN_KOOPA. */
	public static final int ENEMY_GREEN_KOOPA = SpriteTemplate.GREEN_TURTLE;

	/** The Constant ENEMY_RED_KOOPA. */
	public static final int ENEMY_RED_KOOPA = SpriteTemplate.RED_TURTLE;

	/** The Constant ENEMY_SPIKY_KOOPA. */
	public static final int ENEMY_SPIKY_KOOPA = SpriteTemplate.ARMORED_TURTLE;

	/** The Constant ENEMY_JUMP_FLOWER. */
	public static final int ENEMY_JUMP_FLOWER = SpriteTemplate.JUMP_FLOWER;

	/** The Constant ENEMY_CHOMP_FLOWER. */
	public static final int ENEMY_CHOMP_FLOWER = SpriteTemplate.CHOMP_FLOWER;

	/** The height. */
	private final int height;

	/** The width. */
	private final int width;

	/** The disable hill. */
	private final boolean disableHill;

	/** The disable ceiling. */
	private final boolean disableCeiling;

	private final int endBuffer;

	/** The Constant GROUND_LEFT_UP_BOUNDED. */
	private static final byte GROUND_LEFT_UP_BOUNDED = (byte) (11 * 16);

	/** The Constant GROUND_RIGHT_UP_BOUNDED. */
	private static final byte GROUND_RIGHT_UP_BOUNDED = (byte) (2 + 11 * 16);

	/** The Constant GROUND_LEFT_UP_UNBOUNDED. */
	private static final byte GROUND_LEFT_UP_UNBOUNDED = (byte) (8 * 16);

	/** The Constant GROUND_RIGHT_UP_UNBOUNDED. */
	private static final byte GROUND_RIGHT_UP_UNBOUNDED = (byte) (2 + 8 * 16);

	/** The Constant HILL_LEFT_UP_BOUNDED. */
	private static final byte HILL_LEFT_UP_BOUNDED = (byte) (4 + 11 * 16);

	/** The Constant HILL_RIGHT_UP_BOUNDED. */
	private static final byte HILL_RIGHT_UP_BOUNDED = (byte) (6 + 11 * 16);

	/** The Constant HILL_LEFT_UP_UNBOUNDED. */
	private static final byte HILL_LEFT_UP_UNBOUNDED = (byte) (4 + 8 * 16);

	/** The Constant HILL_RIGHT_UP_UNBOUNDED. */
	private static final byte HILL_RIGHT_UP_UNBOUNDED = (byte) (6 + 8 * 16);

	private int numGaps;
	private int numKoopas;
	private int numCoinBlocks;

	/**
	 * Instantiates a new ultra customized level.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param seed
	 *            the seed
	 * @param difficulty
	 *            the difficulty
	 * @param theme
	 *            the theme
	 * @param playerMetrics
	 *            the player metrics
	 */
	public UltraCustomizedLevel(int width, int height, long seed,
			int difficulty, int theme, GamePlay playerMetrics) {
		super(width, height);
		this.width = width;
		this.height = height;
		this.playerMetrics = playerMetrics;
		randGen = new Random(seed);

		mapList = new Map(width);
		// give us enough buffer in case there the previous block flows over
		endBuffer = 15;
		// End position
		xExit = width - 3;
		yExit = height - 1;

		// constraints
		numGaps = 0;
		numCoinBlocks = 0;
		numKoopas = 0;

		blockList = new LinkedList<ItemBlock>();
		enemyList = new LinkedList<EnemyBlock>();
		pipeList = new LinkedList<PipeBlock>();

		// OVERRIDE THEME. MAUAHAHAHA.
		theme = LevelInterface.TYPE_OVERGROUND;
		// System.out.println(playerMetrics.toString());
		this.theme = theme;
		if (theme == LevelInterface.TYPE_OVERGROUND) {
			hillList = new LinkedList<HillBlock>();
			ceilingList = null;
			disableHill = false;
			disableCeiling = true;

		} else {
			hillList = null;
			ceilingList = new Map(width);
			disableHill = true;
			disableCeiling = false;
		}
		createLevel(difficulty);
	}

	// TODO IMPLEMENT CONSTRAINTSSSS
	private void checkConstraints(byte[][] levelMap) {
		// /////////////
		// constraints//
		// /////////////

		// number of gaps
		int numGapsC = Constraints.gaps;
		// number of total koopas
		int numKoopasC = Constraints.turtels;
		// number of coinBlocks
		int numCoinBlocksC = Constraints.coinBlocks;

		// checkGaps
//		if (numGaps > numGapsC) {
//			while (numGaps > numGapsC) {
//				for (Integer i : mapList.keySet()) {
//					Block b = mapList.get(i);
//					if (b.getType() == TYPE_EMPTY && Math.random() < .5) {
//						b.setType(TYPE_GROUND);
//						numGaps--;
//					}
//				}
//			}
//		} else {
//			while (numGaps < numGapsC) {
//				for (Object o : mapList.keySet().toArray()) {
//					Integer i = (Integer) o;
//					Block b = mapList.get(i);
//					if (b.getType() == TYPE_GROUND && Math.random() < .5) {
//						mapList.remove(i);
//						mapList.put(i, new Block(3, b.getHeight(), TYPE_EMPTY,
//								theme, this));
//						if(b.getWidth()>3)
//						{
//						mapList.put(i + 3, new Block(b.getWidth() - 3, b
//								.getHeight(), TYPE_GROUND, theme, this));
//						}
//						numGaps++;
//					}
//				}
//			}
//		}

		// check coinBlocks
		for (int x = 0; x < levelMap.length; x++) {
			for (int y = 0; y < levelMap[0].length; y++) {
				if (this.getBlock(x, y) == (byte) (4 + 1 + 1 * 16)) {
					numCoinBlocks++;
				}
			}
		}
		// too many
		while (numCoinBlocks > numCoinBlocksC) {
			for (int x = 0; x < levelMap.length; x++) {
				for (int y = 0; y < levelMap[0].length; y++) {
					if (this.getBlock(x, y) == (byte) (4 + 1 + 1 * 16)
							&& Math.random() < .5
							&& numCoinBlocks > numCoinBlocksC) {
						numCoinBlocks--;
						this.setBlock(x, y,
								Math.random() < .5 ? (byte) (1 + 1 * 16)
										: (byte) (0 + 1 * 16));
					}
				}
			}
		}
		// too few
		while (numCoinBlocks < numCoinBlocksC) {
			for (int x = 0; x < levelMap.length; x++) {
				for (int y = 0; y < levelMap[0].length; y++) {
					if (this.getBlock(x, y) == (byte) (0 + 1 * 16)
							&& Math.random() < .2
							&& numCoinBlocks < numCoinBlocksC) {
						numCoinBlocks++;
						this.setBlock(x, y, (byte) (4 + 1 + 1 * 16));
					}
				}
			}
		}
		// check koopas
		// too few
		int i = 0;
		while (enemyList.size() < numKoopasC) {
			enemyList.add(new EnemyBlock(10 + i, 5, ENEMY_GOOMBA, false));
			i += 2;
		}
		while (numKoopas < numKoopasC) {
			Iterator<EnemyBlock> iter = enemyList.descendingIterator();
			EnemyBlock e;
			while (iter.hasNext() && numKoopas < numKoopasC) {
				e = iter.next();
				if (e.getType() != ENEMY_RED_KOOPA
						|| e.getType() != ENEMY_GREEN_KOOPA
						& Math.random() < .5) {
					numKoopas++;
					e.setType(Math.random() < .5 ? ENEMY_GREEN_KOOPA
							: ENEMY_RED_KOOPA);
				}
			}
		}
		// too many
		while (numKoopas > numKoopasC) {
			Iterator<EnemyBlock> iter = enemyList.descendingIterator();
			EnemyBlock e;
			while (iter.hasNext() && numKoopas > numKoopasC) {
				e = iter.next();
				if (e.getType() == ENEMY_RED_KOOPA
						|| e.getType() == ENEMY_GREEN_KOOPA
						& Math.random() < .5) {
					numKoopas--;
					e.setType(ENEMY_GOOMBA);
				}
			}
		}

		// not a contraint but check anyway
		// check start and end
		for (int x = 0; x < levelMap[0].length; x++) {
			if (levelMap[0][x] == (byte) (8 * 16)) {
				levelMap[0][x] = (byte) (1 + 8 * 16);
			}
			if (levelMap[0][x] == (byte) (9 * 16)) {
				levelMap[0][x] = (byte) (1 + 9 * 16);
			}
			if (levelMap[width - 1][x] == (byte) (2 + 8 * 16)) {
				levelMap[width - 1][x] = (byte) (1 + 8 * 16);
			}
			if (levelMap[width - 1][x] == (byte) (2 + 9 * 16)) {
				levelMap[width - 1][x] = (byte) (1 + 9 * 16);
			}
		}
	}

	/**
	 * Creates the level.
	 * 
	 * @param difficulty
	 *            the difficulty
	 */
	private void createLevel(int difficulty) {
		// print out data
		/*
		 * System.out.println("jumps " + playerMetrics.jumpsNumber +"  aimless "
		 * + playerMetrics.aimlessJumps);
		 * System.out.println("time total:"+playerMetrics
		 * .totalTime+" completion:" + playerMetrics.completionTime
		 * +" running:"+playerMetrics.timeSpentRunning);
		 * System.out.println("coins:" + playerMetrics.coinsCollected +" blocks"
		 * + playerMetrics.percentageBlocksDestroyed +"%");
		 * System.out.println("enemieskilled:"
		 * +playerMetrics.GoombasKilled+" deaths by enemies:"
		 * +playerMetrics.timesOfDeathByGoomba);
		 */
		// /////////////////////////
		// begin level seed data //
		// /////////////////////////

		// random caluclated data
		double percentRunning = ((double) playerMetrics.timeSpentRunning / (double) playerMetrics.totalTime);

		// /////////// easy medium hard//////////////
		// 0 = easy; 1=medium;2=hard
		int numDeaths = (int) (playerMetrics.timesOfDeathByFallingIntoGap
				+ playerMetrics.timesOfDeathByArmoredTurtle
				+ playerMetrics.timesOfDeathByCannonBall
				+ playerMetrics.timesOfDeathByChompFlower
				+ playerMetrics.timesOfDeathByGoomba
				+ playerMetrics.timesOfDeathByGreenTurtle
				+ playerMetrics.timesOfDeathByJumpFlower + playerMetrics.timesOfDeathByRedTurtle);
		int actualDifficulty;
		// if(playerMetrics.totalTime>200||numDeaths>=2)
		if (numDeaths <= 1 && playerMetrics.totalTime < 140)
			actualDifficulty = 2;
		// else if(playerMetrics.totalTime>100||numDeaths>=1)
		else if (numDeaths >= 2)
			actualDifficulty = 0;
		else
			actualDifficulty = 1;

		// //////////////// level gen///////////////////
		// tells if there is a jump section
		boolean superJump;
		if (actualDifficulty >= 1 && playerMetrics.jumpsNumber > 30)
			superJump = true;
		else
			superJump = false;
		// max height of any block
		// jumps+runtime
		int maxHeight;
		if (actualDifficulty == 2 || playerMetrics.jumpsNumber > 30)
			maxHeight = 6;
		else if (actualDifficulty == 1 || playerMetrics.jumpsNumber > 20)
			maxHeight = 5;
		else
			maxHeight = 4;
		// max height variance between separate blocks
		int maxHeightVariance;
		if (actualDifficulty == 2 || playerMetrics.jumpsNumber > 30)
			maxHeightVariance = 4;
		else if (actualDifficulty == 1 || playerMetrics.jumpsNumber > 20)
			maxHeightVariance = 3;
		else
			maxHeightVariance = 2;
		// minimum width for a block
		int minWidth;
		if (percentRunning > .2 || playerMetrics.completionTime < 80)
			minWidth = 8;
		else if (percentRunning > .15)
			minWidth = 7;
		else if (percentRunning > .1 && actualDifficulty <= 1)
			minWidth = 6;
		else
			minWidth = 5;
		// maximum width for a block
		int maxWidth;
		if (minWidth >= 8)
			maxWidth = 14;
		else if (minWidth >= 6)
			maxWidth = 12;
		else if (minWidth >= 5)
			maxWidth = 12;
		else
			maxWidth = 10;
		// minimum width of a gap
		int minGapWidth;
		if (playerMetrics.timesOfDeathByFallingIntoGap >= 1 || numDeaths >= 2)
			minGapWidth = 1;
		else if (numDeaths >= 1)
			minGapWidth = 2;
		else
			minGapWidth = 3;
		// maximum width of a gap
		int maxGapWidth;
		if (playerMetrics.timesOfDeathByFallingIntoGap >= 1 || numDeaths >= 2)
			maxGapWidth = 3;
		else if (numDeaths >= 1)
			maxGapWidth = 4;
		else
			maxGapWidth = 5;
		double probGap;
		if (playerMetrics.timesOfDeathByFallingIntoGap >= 1 || numDeaths >= 2)
			probGap = .1;
		else if (numDeaths >= 1)
			probGap = .13;
		else
			probGap = .16;
		// decide if there is a gapJump special
		boolean gapJump;
		if (actualDifficulty >= 1 || playerMetrics.jumpsNumber > 30
				&& playerMetrics.timesOfDeathByFallingIntoGap <= 1)
			gapJump = true;
		else
			gapJump = false;

		// /////////////blocks+extras gen/////////////
		// a secret area that contains lots of coins
		boolean secretArea;
		if (((double) playerMetrics.coinsCollected / (double) playerMetrics.totalCoins) > .7)
			secretArea = true;
		else
			secretArea = false;
		// to place a section of blocks on top of more blocks. like a pyramid.
		boolean doubleLayerBlocks;
		if (playerMetrics.percentageBlocksDestroyed > .3)
			doubleLayerBlocks = true;
		else
			doubleLayerBlocks = false;
		// decide to do it twice
		boolean doubleLayerBlocks2;
		if (playerMetrics.percentageBlocksDestroyed > .6)
			doubleLayerBlocks2 = true;
		else
			doubleLayerBlocks2 = false;
		// decide on the number of pipes to add
		double probPipes;
		if (playerMetrics.aimlessJumps > 20 && playerMetrics.jumpsNumber > 35)
			probPipes = .3;
		else if (playerMetrics.jumpsNumber > 20 || actualDifficulty >= 1)
			probPipes = .2;
		else
			probPipes = .1;
		// decide on the probability of block sections to add
		double probBlocks;
		if (playerMetrics.percentageBlocksDestroyed > .7)
			probBlocks = .5;
		else if (playerMetrics.percentageBlocksDestroyed > .5)
			probBlocks = .4;
		else if (playerMetrics.percentageBlocksDestroyed > .3)
			probBlocks = .33;
		else
			probBlocks = .25;
		// decide on the probability of power ups
		double probPowerups;
		if ((playerMetrics.totalTimeLittleMode > playerMetrics.totalTimeFireMode && playerMetrics.totalTimeLittleMode > playerMetrics.totalTimeLargeMode)
				|| playerMetrics.timesSwichingPower > 10
				|| actualDifficulty == 0)
			probPowerups = .3;
		else if (((double) playerMetrics.powerBlocksDestroyed / (double) playerMetrics.totalpowerBlocks) > .7)
			probPowerups = .2;
		else if (((double) playerMetrics.powerBlocksDestroyed / (double) playerMetrics.totalpowerBlocks) > .5
				|| playerMetrics.timesSwichingPower > 5
				|| actualDifficulty == 1)
			probPowerups = .4;
		else
			probPowerups = .1;
		// decide on the probability of adding coins
		double probCoins;
		if (((double) playerMetrics.totalCoins / (double) playerMetrics.coinsCollected) > .65)
			probCoins = .4;
		else if (((double) playerMetrics.totalCoins / (double) playerMetrics.coinsCollected) > .45)
			probCoins = .3;
		else
			probCoins = .2;

		// //////////enemies gen///////////////
		// decide the probability of goombas
		double probGoombas;
		int numKilled = playerMetrics.ArmoredTurtlesKilled
				+ playerMetrics.CannonBallKilled
				+ playerMetrics.ChompFlowersKilled
				+ playerMetrics.GoombasKilled + playerMetrics.RedTurtlesKilled
				+ playerMetrics.GreenTurtlesKilled
				+ playerMetrics.JumpFlowersKilled;
		int numDeathsByEnemies = playerMetrics.timesOfDeathByArmoredTurtle
				+ playerMetrics.timesOfDeathByCannonBall
				+ playerMetrics.timesOfDeathByChompFlower
				+ playerMetrics.timesOfDeathByGoomba
				+ playerMetrics.timesOfDeathByGreenTurtle
				+ playerMetrics.timesOfDeathByJumpFlower
				+ playerMetrics.timesOfDeathByRedTurtle;
		if (((double) numKilled / (double) playerMetrics.totalEnemies) > .5)
			probGoombas = .6;
		else if (numDeathsByEnemies > 1)
			probGoombas = .2;
		else if (((double) numKilled / (double) playerMetrics.totalEnemies) < .33)
			probGoombas = .4;
		else if (playerMetrics.aimlessJumps > 20)
			probGoombas = .5;
		else
			probGoombas = .3;
		// decide if there is a zone ideal for shells
		boolean shellZone;
		if (probGoombas >= .2
				&& (numDeathsByEnemies <= 2 || playerMetrics.kickedShells >= 2 || playerMetrics.enemyKillByKickingShell >= 1))
			shellZone = true;
		else
			shellZone = false;
		// decide if there are two
		boolean shellZone2;
		if (probGoombas >= .4
				&& (numDeathsByEnemies <= 1 || playerMetrics.kickedShells >= 3 || playerMetrics.enemyKillByKickingShell >= 3))
			shellZone2 = true;
		else
			shellZone2 = false;
		// decide if there a zone ideal for fire
		boolean fireZone;
		if (probGoombas >= .3
				&& (numDeathsByEnemies <= 2 || playerMetrics.enemyKillByFire > 3))
			fireZone = true;
		else
			fireZone = false;
		// i'll not use this for now XD
		double probPipeFlowers;
		if (playerMetrics.timesOfDeathByJumpFlower >= 1)
			probPipeFlowers = 0;
		else if (((double) numKilled / (double) playerMetrics.totalEnemies) > .7)
			probPipeFlowers = .4;
		else
			probPipeFlowers = .2;

		// instantiate the level
		levelMap = new byte[width][height];

		// add entrace blocks
		int startWidth = 6;
		mapList.add(new Block(startWidth, 1, TYPE_GROUND, theme, this));
		if (!disableCeiling)
			ceilingList.add(new Block(startWidth, 1, TYPE_EMPTY, theme, this));
		// add starting blocks (contains powerup to make it easier)
		mapList.add(new Block(5, 2, TYPE_GROUND, theme, this));
		if (!disableCeiling)
			ceilingList.add(new Block(5, 1, TYPE_CEILING, theme, this));
		if (actualDifficulty == 0) {
			blockList.add(new ItemBlock(startWidth + 1, 3, 2 + 4,
					TYPE_BLOCKS_ONLY_POWERUP));
		} else if (actualDifficulty == 1) {
			blockList.add(new ItemBlock(startWidth + 1, 3, 2 + 4,
					TYPE_BLOCKS_COINS));
		}
		mapList.add(new Block(maxWidth, 1, TYPE_GROUND, theme, this));
		if (!disableCeiling)
			ceilingList.add(new Block(maxWidth, 1, TYPE_CEILING, theme, this));

		// ///////////////////////////////////////////////////////////////
		// ///////////////////////////
		// shellZone = true;
		// shellZone2 = true;
		// secretArea = true;
		// superJump = true;
		// fireZone = true;
		// doubleLayerBlocks = true;
		// doubleLayerBlocks2 = true;
		// gapJump = true;
		// System.out.println("sa " + secretArea);
		// System.out.println("sz " + shellZone);
		// System.out.println("sz2 " + shellZone2);
		// System.out.println("fz " + fireZone);
		// System.out.println("dlb " + doubleLayerBlocks);
		// System.out.println("dlb2 " + doubleLayerBlocks2);
		// System.out.println("gj " + gapJump);
		// /////////////////////////////
		// ///////////////////////////////////////////////////////////////
		// add other blocks for level
		int index = mapList.realLength();
		int previousHeight = mapList.lastBlock().getHeight();
		boolean previousGap = false;
		boolean previousBlocks = false;
		boolean previousEnemies = false;
		boolean previousPipe = false;
		boolean previousSpecial = false;
		boolean pipe = false;
		boolean hill = false;
		boolean empty = true;
		int width = 0;
		int height = 0;
		int numTimesEmpty = 0;
		Block block1 = null;
		Block block2 = null;
		Block block3 = null;
		while (index < this.getWidth()) {
			// check index to see if there is room for special zones
			if (((getWidth() - endBuffer) - index) < maxWidth * 3) {
				// System.out.println("test");
				shellZone = false;
				shellZone2 = false;
				fireZone = false;
				secretArea = false;
				superJump = false;
				gapJump = false;
			}
			// reset variables
			block1 = null;
			block2 = null;
			block3 = null;
			pipe = true;
			hill = true;
			empty = true;
			width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
			if (width <= 6) {
				pipe = false;
				hill = false;
			}
			if (previousSpecial) {
				height = previousHeight
						+ randGen.nextInt(maxHeightVariance - 1) + 1;
			} else if (previousGap && mapList.lastBlock() != null
					&& mapList.entryBefore(mapList.lastBlock()) != null) {
				height = mapList.entryBefore(mapList.lastBlock()).getHeight()
						- randGen.nextInt(maxHeightVariance + 1);
			} else if (previousGap) {
				height = 1 + randGen.nextInt(maxHeightVariance + 1);
			} else {
				height = previousHeight
						+ randGen.nextInt(maxHeightVariance * 2 + 1)
						- maxHeightVariance;
			}
			if (height > maxHeight)
				height = maxHeight;
			else if (height < 1)
				height = 1;
			// if its at the end, add the end block
			if (index >= this.getWidth() - endBuffer - 1) {
				mapList.add(new Block(this.getWidth() - index, height,
						TYPE_GROUND, theme, this));
				yExit = this.height - height;
				break;
			}
			// setup block(s)
			// detect special patterns
			// shellzone
			if (Math.random() < .1 && shellZone && !previousSpecial
					&& !previousGap) {
				height = previousHeight + randGen.nextInt(maxHeightVariance)
						+ 1;
				// System.out.println("p"+previousHeight);
				// System.out.println("h"+height);
				previousSpecial = true;
				shellZone = false;
				previousGap = false;
				// first block
				block1 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block1);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// second block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height -= 2;
				if (height < 1)
					height = 1;
				block2 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block2);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// third block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height += 2;
				if (height < 1)
					height = 1;
				block3 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block3);
				if (!disableCeiling) {
					int temp = (-3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// enemies
				numKoopas++;
				enemyList.add(new EnemyBlock(index + block1.getWidth() - 1,
						block1.getHeight(), ENEMY_RED_KOOPA, false));
				// enemies
				for (int i = index + block1.getWidth() + 1; i < index
						+ block1.getWidth() + block2.getWidth(); i++) {
					int temp = (Math.random() < .3 && numKoopas < Constraints.turtels) ? ENEMY_GREEN_KOOPA
							: ENEMY_GOOMBA;
					if (temp == ENEMY_GREEN_KOOPA) {
						numKoopas++;
					}
					enemyList.add(new EnemyBlock(i, height + 1, temp, false));
				}
				// block platform /hill platform
				if (Math.random() < .5) {
					blockList.add(new ItemBlock(index + block1.getWidth() + 1,
							block2.getWidth() - 2, block1.getHeight() + 3,
							TYPE_BLOCKS_COINS));
				} else {
					if (!disableHill)
						hillList.add(new HillBlock(index + block1.getWidth()
								+ 2, block2.getWidth() - 3 >= 2 ? block2
								.getWidth() - 3 : 2, block1.getHeight() + 2));
				}
			}
			// shellzone2
			else if (Math.random() < .1 && shellZone2 && !previousSpecial
					&& !previousGap) {
				height = previousHeight + randGen.nextInt(maxHeightVariance)
						+ 1;
				// System.out.println("p"+previousHeight);
				// System.out.println("h"+height);
				previousSpecial = true;
				shellZone2 = false;
				previousGap = false;
				// first block
				block1 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block1);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// second block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height -= 2;
				if (height < 1)
					height = 1;
				block2 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block2);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// third block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height += 2;
				if (height < 1)
					height = 1;
				block3 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block3);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// enemies
				numKoopas++;
				enemyList.add(new EnemyBlock(index + block1.getWidth() - 1,
						block1.getHeight(), ENEMY_RED_KOOPA, false));
				// enemies
				for (int i = index + block1.getWidth() + 1; i < index
						+ block1.getWidth() + block2.getWidth(); i++) {
					int temp = (Math.random() < .3 && numKoopas < Constraints.turtels) ? ENEMY_GREEN_KOOPA
							: ENEMY_GOOMBA;
					if (temp == ENEMY_GREEN_KOOPA) {
						numKoopas++;
					}
					enemyList.add(new EnemyBlock(i, height + 1, temp, false));
				}
				// block platform /hill platform
				if (Math.random() < .5) {
					blockList.add(new ItemBlock(index + block1.getWidth() + 1,
							block2.getWidth() - 2, block1.getHeight() + 3,
							TYPE_BLOCKS_COINS));
				} else {
					if (!disableHill)
						hillList.add(new HillBlock(index + block1.getWidth()
								+ 2, block2.getWidth() - 3 >= 2 ? block2
								.getWidth() - 3 : 2, block1.getHeight() + 2));
				}
			}
			// firezone
			else if (Math.random() < .1 && fireZone && !previousSpecial) {
				previousSpecial = true;
				fireZone = false;
				previousGap = false;
				height = previousHeight + randGen.nextInt(maxHeightVariance)
						+ 1;
				// first block
				block1 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block1);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				blockList.add(new ItemBlock(index + block1.getWidth() - 1 - 3,
						1, height + 4, TYPE_BLOCKS_POWERUP));
				// second block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height -= 3;
				if (height < 1)
					height = 1;
				block2 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block2);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// third block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height += 2;
				if (height < 1)
					height = 1;
				block3 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block3);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				// enemies
				for (int i = index + block1.getWidth(); i < index
						+ block1.getWidth() + block2.getWidth(); i++) {
					int temp = (Math.random() < .3 && numKoopas < Constraints.turtels) ? ENEMY_GREEN_KOOPA
							: ENEMY_GOOMBA;
					if (temp == ENEMY_GREEN_KOOPA) {
						numKoopas++;
					}
					enemyList.add(new EnemyBlock(i, height + 1, temp, false));
				}
				// block platform /hill platform
				if (Math.random() < .5) {
					blockList.add(new ItemBlock(index + block1.getWidth() + 2,
							block2.getWidth() - 3, block1.getHeight() + 3,
							TYPE_BLOCKS_COINS));
				} else {
					if (!disableHill)
						hillList.add(new HillBlock(index + block1.getWidth()
								+ 2, block2.getWidth() - 4 >= 2 ? block2
								.getWidth() - 4 : 2, block1.getHeight() + 2));
				}
			}
			// secretarea
			else if (Math.random() < .1 && secretArea && !previousSpecial
					&& !previousGap) {
				previousSpecial = true;
				secretArea = false;
				previousGap = false;
				// coins on top of hill
				// first block
				block1 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block1);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				int asdf = width / 4;
				blockList.add(new ItemBlock(index + asdf * 2, width - asdf - 1,
						height + 4, TYPE_BLOCKS));
				// second block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height -= randGen.nextInt(maxHeightVariance + 1);
				if (height < 1)
					height = 1;
				block2 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block2);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				blockList.add(new ItemBlock(index + block1.getWidth(),
						width - 1, block1.getHeight() + 6, TYPE_BLOCKS_COINS));
				// third block
				width = minWidth + 2
						+ randGen.nextInt(maxWidth - minWidth + 1 - 2);
				int height1 = block1.getHeight() + 7;
				if (height1 >= getHeight() - 4)
					height1 = getHeight() - 4;
				height -= randGen.nextInt(maxHeightVariance + 1);
				if (height < 1)
					height = 1;
				hillList.add(new HillBlock(index + block1.getWidth()
						+ block2.getWidth() - 1, width, height1));
				block3 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block3);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				blockList.add(new ItemBlock(index + block1.getWidth()
						+ block2.getWidth() - 1, width, height1 + 1,
						TYPE_HIDDEN_COINS));
				blockList.add(new ItemBlock(index + block1.getWidth()
						+ block2.getWidth() - 1, width, height1 + 2,
						TYPE_HIDDEN_COINS));
				blockList.add(new ItemBlock(index + block1.getWidth()
						+ block2.getWidth() - 1, width, height1 + 3,
						TYPE_HIDDEN_COINS));
				blockList.add(new ItemBlock(index + block1.getWidth()
						+ block2.getWidth() - 1, width, height1 + 4,
						TYPE_HIDDEN_COINS));
			}
			// superjump
			else if (Math.random() < .1 && superJump && !previousSpecial
					&& !previousGap) {
				// or randomize it
				previousSpecial = true;
				superJump = false;
				previousGap = false;
				block1 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block1);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				int asdf = width / 4;
				blockList.add(new ItemBlock(index + asdf * 2, width - asdf - 1,
						height + 4, TYPE_BLOCKS_POWERUP));
				// second block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height -= randGen.nextInt(maxHeightVariance + 1);
				if (height < 1)
					height = 1;
				block2 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block2);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				blockList.add(new ItemBlock(index + block1.getWidth(), width,
						block1.getHeight() + 6, TYPE_BLOCKS_POWERUP));
				// third block
				width = minWidth + randGen.nextInt(maxWidth - minWidth + 1);
				height = block1.getHeight() + 6;
				if (height > getHeight())
					height = getHeight() - 1;
				block3 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block3);
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
			}
			// doubleLayerBlocks
			else if (Math.random() < .1 && doubleLayerBlocks
					&& !previousSpecial && !previousGap && !previousBlocks) {
				previousSpecial = true;
				previousBlocks = true;
				doubleLayerBlocks = false;
				// add ground
				previousGap = false;
				width = maxWidth;
				if (width % 2 == 1) {
					width++;
				}
				block1 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block1);
				// handle blocks/coins
				// bb
				//
				// bb bbbb bb
				//
				// gggggggggggggggggg
				if (block1.getWidth() > 12) {
					blockList.add(new ItemBlock(index + 1, 2, height + 4,
							TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + block1.getWidth() - 1
							- 2, 2, height + 4, TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + 4,
							block1.getWidth() - 8, height + 4,
							TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + 5,
							block1.getWidth() - 10, height + 7,
							TYPE_BLOCKS_POWERUP_FILLED));
				} else {
					// System.out.println("AWESOME");
					blockList.add(new ItemBlock(index + 2,
							block1.getWidth() - 4, height + 4,
							TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + 3,
							block1.getWidth() - 6, height + 7,
							TYPE_BLOCKS_POWERUP_FILLED));
				}

				// add Ceiling
				if (!disableCeiling) {
					Block ceiling = new Block(width, 1, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
			}
			// doubleLayerBlocks2
			else if (Math.random() < .1 && doubleLayerBlocks2
					&& !previousSpecial && !previousBlocks) {
				previousSpecial = true;
				doubleLayerBlocks2 = false;
				// add ground
				previousGap = false;
				width = maxWidth;
				if (width % 2 == 1) {
					width++;
				}
				block1 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block1);
				// handle blocks/coins
				// bb
				//
				// bb bbbb bb
				//
				// gggggggggggggggggg
				if (block1.getWidth() > 12) {
					blockList.add(new ItemBlock(index + 1, 2, height + 4,
							TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + block1.getWidth() - 1
							- 2, 2, height + 4, TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + 4,
							block1.getWidth() - 8, height + 4,
							TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + 5,
							block1.getWidth() - 10, height + 7,
							TYPE_BLOCKS_POWERUP_FILLED));
				} else {
					// System.out.println("AWESOME");
					blockList.add(new ItemBlock(index + 2,
							block1.getWidth() - 4, height + 4,
							TYPE_BLOCKS_COINS_FILLED));
					blockList.add(new ItemBlock(index + 3,
							block1.getWidth() - 6, height + 7,
							TYPE_BLOCKS_POWERUP_FILLED));
				}

				// add Ceiling
				if (!disableCeiling) {
					Block ceiling = new Block(width, 1, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
			}
			// gapjump
			else if (Math.random() < .1 && gapJump && !previousSpecial
					&& !previousGap && numGaps + 2 < Constraints.gaps) {
				// System.out.println("BALKSDFJLADFS");
				previousSpecial = true;
				gapJump = false;
				previousGap = true;
				width = maxGapWidth;
				block1 = new Block(width, previousHeight, TYPE_EMPTY, theme,
						this);
				numGaps++;
				mapList.add(block1);
				if (!disableCeiling) {
					Block ceiling = new Block(width, 1, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				width = 3;
				height = 1 + randGen.nextInt(maxHeightVariance);
				block2 = new Block(width, height, TYPE_GROUND, theme, this);
				mapList.add(block2);
				if (!disableCeiling) {
					Block ceiling = new Block(width, 1, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				width = minGapWidth + 1;
				block3 = new Block(width, previousHeight, TYPE_EMPTY, theme,
						this);
				numGaps++;
				mapList.add(block3);
				if (!disableCeiling) {
					Block ceiling = new Block(width, 1, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
			}
			// do the normal blocks
			else {
				int maxGoombas = 2;
				int goombas = 0;
				// handle enemies
				if (Math.random() < probGoombas && !previousEnemies) {
					enemyList.add(new EnemyBlock(index + 1, height + 1,
							ENEMY_GOOMBA, false));
					for (int i = index + 2; i < index + width; i += randGen
							.nextInt(3) + 1) {
						if (Math.random() < .2 && goombas < maxGoombas) {
							goombas++;
							enemyList.add(new EnemyBlock(i, height + 1,
									ENEMY_GOOMBA, false));
						}
					}
					previousEnemies = true;
					pipe = false;
					empty = false;
					numTimesEmpty = 0;
				} else {
					previousEnemies = false;
				}
				// add ground/gap
				if (Math.random() < probGap
						* ((double) Constraints.gaps / (double) numGaps)
						&& !previousGap
						&& !previousPipe
						&& numGaps + 1 < Constraints.gaps) {
					previousGap = true;
					width = minGapWidth
							+ randGen.nextInt(maxGapWidth - minGapWidth + 1);
					block1 = new Block(width, previousHeight, TYPE_EMPTY,
							theme, this);
					numGaps++;
					mapList.add(block1);
				} else {
					previousGap = false;
					block1 = new Block(width, height, TYPE_GROUND, theme, this);
					mapList.add(block1);
					// handle blocks/coins
					if (Math.random() < probCoins && !previousBlocks) {
						blockList.add(new ItemBlock(index, width, height + 2,
								TYPE_COINS));
						pipe = false;
						hill = false;
						empty = false;
						numTimesEmpty = 0;
					}
					if (Math.random() < probBlocks && !previousBlocks
							&& !previousGap && previousHeight - height >= 0) {
						blockList.add(new ItemBlock(index + 1, width - 2,
								height + 4, TYPE_BLOCKS_COINS));
						previousBlocks = true;
						pipe = false;
						hill = false;
						empty = false;
						numTimesEmpty = 0;
					} else if (Math.random() < probPowerups && !previousBlocks
							&& !previousGap && previousHeight - height >= 0) {
						blockList.add(new ItemBlock(index + 1, width - 2,
								height + 4, TYPE_BLOCKS_POWERUP));
						previousBlocks = true;
						pipe = false;
						hill = false;
						empty = false;
						numTimesEmpty = 0;
					} else {
						// add random hills
						if (Math.random() < .8 && width >= 8 && !previousBlocks
								&& !previousGap && !previousSpecial && hill
								&& empty) {
							// previousHill = true;
							boolean level2 = false;
							int width1 = width / 2 + 1;
							if (index + 2 + width1 == index + width) {
								width1--;
							}
							int height1 = height + randGen.nextInt(3) + 2;
							int newIndex = 0;
							int width2 = 0;
							int height2 = 0;
							if (Math.random() < .3) {
								width2 = width1 / (randGen.nextInt(2) + 2)
										+ randGen.nextInt(2) + 1;
								height2 = height + randGen.nextInt(4) + 1;
								newIndex = index + randGen.nextInt(3) + 1;
								while (height1 == height2) {
									height2 = height + randGen.nextInt(4) + 1;
								}
								while (index + 2 == newIndex) {
									newIndex = index + randGen.nextInt(3) + 1;
								}
								while (newIndex + width2 == index + width1) {
									width2 = width / (randGen.nextInt(2) + 2)
											+ randGen.nextInt(2) + 1;
								}
								if (height2 < height1) {
									hillList.add(new HillBlock(index + 2,
											width1, height1));
									hillList.add(new HillBlock(newIndex,
											width2, height2));
								} else {
									hillList.add(new HillBlock(newIndex,
											width2, height2));
									hillList.add(new HillBlock(index + 2,
											width1, height1));
								}
								level2 = true;
							} else {
								hillList.add(new HillBlock(index + 2, width1,
										height1));
							}
							// System.out.println(height1+","+height2+", "+level2);
							if (level2 && Math.random() < probCoins + .4) {
								if (height2 > height1) {
									blockList.add(new ItemBlock(newIndex,
											width2, height2 + 2, TYPE_COINS));
								} else {
									blockList.add(new ItemBlock(index + 2,
											width1, height1 + 2, TYPE_COINS));
								}
							} else if (Math.random() < probCoins + .4) {
								blockList.add(new ItemBlock(index + 2, width1,
										height1 + 2, TYPE_COINS));
							} else if (level2 && Math.random() < probBlocks) {
								if (height2 > height1) {
									blockList.add(new ItemBlock(newIndex,
											width2, height2 + 4,
											TYPE_BLOCKS_COINS));
								} else {
									blockList.add(new ItemBlock(index + 2,
											width1, height1 + 4,
											TYPE_BLOCKS_COINS));
								}
							} else if (Math.random() < probBlocks) {
								blockList.add(new ItemBlock(index + 2, width1,
										height1 + 4, TYPE_BLOCKS_COINS));
							} else if (level2 && Math.random() < probGoombas) {
								if (height2 > height1) {
									enemyList.add(new EnemyBlock(newIndex + 2,
											height2 + 1, ENEMY_GOOMBA, false));
								} else {
									enemyList.add(new EnemyBlock(index + 2 + 2,
											height1 + 1, ENEMY_GOOMBA, false));
								}
							} else if (Math.random() < probGoombas) {
								enemyList.add(new EnemyBlock(index + 2 + 2,
										height1 + 1, ENEMY_GOOMBA, false));
							}
							empty = false;
							numTimesEmpty = 0;
							// System.out.println("how many times is this called?");
							// previousHill = true;
							// random pipes
						} else if (width >= 6 && Math.random() < probPipes
								&& !previousPipe && !previousBlocks && pipe) {
							if (Math.random() < probPipeFlowers) {
								addPipeFlower(index + width / 2, height + 1,
										randGen.nextInt(2) + 2);
							} else {
								pipeList.add(new PipeBlock(index + width / 2,
										height + 1, randGen.nextInt(2) + 2));
							}
							empty = false;
							numTimesEmpty = 0;
						}
						previousBlocks = false;
					}
				}
				if (empty) {
					// higher chance of enemies
					if (Math.random() < probGoombas + .05 && !previousEnemies) {
						for (int i = index; i < index + width; i += randGen
								.nextInt(3) + 1) {
							if (Math.random() < .2 && goombas < maxGoombas) {
								goombas++;
								enemyList.add(new EnemyBlock(i, height + 1,
										ENEMY_GOOMBA, false));
							}
						}
						previousEnemies = true;
						pipe = false;
						empty = false;
						numTimesEmpty = 0;
					}
					// System.out.println(previousBlocks+" "+previousGap+" "+(previousHeight-height>=0));
					if (Math.random() < .2 && !previousBlocks && !previousGap) {
						blockList.add(new ItemBlock(index, width, height + 2,
								TYPE_COINS));
						pipe = false;
						hill = false;
						empty = false;
						numTimesEmpty = 0;
					} else if (Math.random() < .6 && !previousBlocks
							&& !previousGap && previousHeight - height >= 0) {
						blockList.add(new ItemBlock(index + 1,
								width - 2 > 0 ? width - 2 : 1, height + 4,
								TYPE_BLOCKS_COINS));
						previousBlocks = true;
						pipe = false;
						hill = false;
						empty = false;
						numTimesEmpty = 0;
					}
				}
				while (numTimesEmpty >= 2) {
					// higher chance of enemies
					if (Math.random() < probGoombas + .1 && !previousEnemies) {
						for (int i = index; i < index + width; i += randGen
								.nextInt(3) + 1) {
							if (Math.random() < .2 && goombas < maxGoombas) {
								goombas++;
								enemyList.add(new EnemyBlock(i, height + 1,
										ENEMY_GOOMBA, false));
							}
						}
						previousEnemies = true;
						pipe = false;
						empty = false;
						numTimesEmpty = 0;
					}
					// System.out.println(previousBlocks+" "+previousGap+" "+(previousHeight-height>=0));
					if (Math.random() < .2 && !previousBlocks && !previousGap) {
						blockList.add(new ItemBlock(index, width, height + 2,
								TYPE_COINS));
						pipe = false;
						hill = false;
						empty = false;
						numTimesEmpty = 0;
					} else if (Math.random() < .8 && !previousBlocks
							&& !previousGap && previousHeight - height >= 0) {
						blockList.add(new ItemBlock(index + 1,
								width - 2 > 0 ? width - 2 : 1, height + 4,
								TYPE_BLOCKS_COINS));
						previousBlocks = true;
						pipe = false;
						hill = false;
						empty = false;
						numTimesEmpty = 0;
					}
				}
				// add Ceiling
				if (!disableCeiling) {
					int temp = (3 - height) > 0 ? (3 - height) : 1;
					Block ceiling = new Block(width, temp, TYPE_CEILING, theme,
							this);
					ceilingList.add(ceiling);
				}
				previousSpecial = false;
			}

			// increment the index and set up the previous Height variable
			if (block3 != null) {
				if (block3.getType() != TYPE_EMPTY)
					previousHeight = block3.getHeight();
				else
					previousHeight = 0;
			} else if (block2 != null) {
				if (block2.getType() != TYPE_EMPTY)
					previousHeight = block2.getHeight();
				else
					previousHeight = 0;
			} else if (block1 != null) {
				if (block1.getType() != TYPE_EMPTY)
					previousHeight = block1.getHeight();
				else
					previousHeight = 0;
			} else {
				previousHeight = 0;
			}
			if (block1 != null)
				index += block1.getWidth();
			if (block2 != null)
				index += block2.getWidth();
			if (block3 != null)
				index += block3.getWidth();
			// index += width;
			if (empty)
				numTimesEmpty++;
		}

		// Fill in the blocks based on the level map
		update();
		finalizeBlocks(levelMap);
		checkConstraints(levelMap);
		finalize(levelMap);
	}

	/**
	 * Update.
	 */
	private void update() {
		updateHillBlocks();
		if (!disableCeiling) {
			ceilingList.finalize();
			updateCeilingBlocks();
		}
		mapList.finalize();
		updateGroundBlocks();
	}

	/**
	 * Update hill blocks.
	 */
	private void updateHillBlocks() {
		byte temp = 0;
		if (disableHill) {
			return;
		}
		for (HillBlock hill : hillList) {
			int k = 0;
			for (int i = hill.getPos(); i < hill.getPos() + hill.getWidth(); i++) {
				for (int j = hill.getHeight() - 1; j >= 0; j--) {
					temp = hill.getTile(k, j);
					if (temp == HILL_LEFT_UP_UNBOUNDED
							&& topFilled(i, height - 1 - j)) {
						temp = HILL_LEFT_UP_BOUNDED;
					} else if (temp == HILL_RIGHT_UP_UNBOUNDED
							&& topFilled(i, height - 1 - j)) {
						temp = HILL_RIGHT_UP_BOUNDED;
					}
					levelMap[i][height - 1 - j] = temp;
				}
				k++;
			}
		}
	}

	/**
	 * Update ceiling blocks.
	 */
	private void updateCeilingBlocks() {
		if (disableCeiling) {
			return;
		}
		int index = 0;
		Block block;
		Iterator<Integer> iter = ceilingList.keySet().iterator();
		while (iter.hasNext()) {
			block = ceilingList.get(iter.next());
			for (int i = 0; i < block.getWidth(); i++) {
				if (index >= getWidth()) {
					return;
				}
				for (int j = block.getHeight() - 1; j >= 0; j--) {
					if (block.getTile(i, j) != EMPTY) {
						levelMap[index][j] = block.getTile(i, j);
					}
				}
				index++;
			}
		}
	}

	/**
	 * Update ground blocks.
	 */
	private void updateGroundBlocks() {
		int index = 0;
		byte temp = 0;
		Block block;
		Iterator<Integer> iter = mapList.keySet().iterator();
		while (iter.hasNext()) {
			block = mapList.get(iter.next());
			for (int i = 0; i < block.getWidth(); i++) {
				if (index >= getWidth()) {
					return;
				}
				for (int j = block.getHeight() - 1; j >= 0; j--) {
					if (block.getTile(i, j) != EMPTY) {
						temp = block.getTile(i, j);
						if (temp == GROUND_LEFT_UP_UNBOUNDED
								&& topFilled(index, height - 1 - j)) {
							temp = GROUND_LEFT_UP_BOUNDED;
						} else if (temp == GROUND_RIGHT_UP_UNBOUNDED
								&& topFilled(index, height - 1 - j)) {
							temp = GROUND_RIGHT_UP_BOUNDED;
						}
						levelMap[index][height - 1 - j] = temp;
					}
				}
				index++;
			}
		}
	}

	private void finalizeBlocks(byte[][] levelMap) {
		for (ItemBlock item : blockList) {
			int k = item.getPos();
			int height = item.getHeight();
			for (int i = 0; i < item.getWidth(); i++) {
				setBlock(k, this.height - height, item.getTile(i));
				k++;
			}
		}
	}

	/**
	 * Finalize.
	 * 
	 * @param levelMap
	 *            the level map
	 */
	private void finalize(byte[][] levelMap) {
		for (PipeBlock pipe : pipeList) {
			int x = pipe.getX();
			int y = pipe.getY();
			int height = pipe.getHeight();
			int width = pipe.getWidth();
			for (int i = 0; i < width; i++) {
				for (int j = height - 1; j >= 0; j--) {
					setBlock(x + i, this.height - (y + j), pipe.getTile(i, j));
				}
			}
		}
		for (EnemyBlock e : enemyList) {
			setSpriteTemplate(e.getX(), height - e.getY() - 1, e.getTile());
		}
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (levelMap[i][j] != 0)
					setBlock(i, j, levelMap[i][j]);
			}
		}
	}

	/**
	 * Adds the pipe flower.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param height
	 *            the height
	 */
	private void addPipeFlower(int x, int y, int height) {
		if (height > 4 || height < 2) {
			height = 2;
		}
		pipeList.add(new PipeBlock(x, y, height));
		enemyList.add(new EnemyBlock(x, y, ENEMY_JUMP_FLOWER, false));
	}

	/**
	 * Top filled.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	private boolean topFilled(int x, int y) {
		y -= 1;
		if (x >= width || y >= height || x < 0 || y < 0) {
			return false;
		}
		// return that the block above is land
		return levelMap[x][y] >= (byte) (8 * 16)
				&& levelMap[x][y] < (byte) (13 * 16);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dk.itu.mario.level.Level#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dk.itu.mario.level.Level#getWidth()
	 */
	public int getWidth() {
		return width;
	}
}
