package br.ufv.julian;

import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.level.Level;

public class Metric1  {
	
	private int width; 
	private int height;
	Level level; 
	//lastcom2
	private double[] levelElements = new double[5]; 
	private static final int levelElementsGaps = 0;
	private static final int levelElementsAvGaps = 1;
	private static final int levelElementsEnemies = 2;
	private static final int levelElementsCannnFlowers = 3;
	private static final int levelElementsPowerUps = 4;
	private double metric1, nomrMetric1;

	
	public Metric1(int width, int height, Level level) {
		
		this.width = width;
		this.height = height;
		this.level = level;

	}
	
	public double Metric1M() {
		countOtherElements(level.getMap());
		// level.setSpriteTemplate(0, 0, new
		// SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
		countEnemies(level.getMap());

		//Testando quantity of each thing
		System.out.println("levelElements[levelElementsGaps] "+levelElements[levelElementsGaps]);
		System.out.println("levelElements[levelElementsAvGaps] "+levelElements[levelElementsAvGaps]);
		System.out.println("levelElements[levelElementsEnemies] "+levelElements[levelElementsEnemies]);
		System.out.println("levelElements[levelElementsCannnFlowers] "+levelElements[levelElementsCannnFlowers]);
		System.out.println("levelElements[levelElementsPowerUps] "+levelElements[levelElementsPowerUps]);
		//end test
		// Setting the weights of elements
		levelElements[levelElementsGaps] = levelElements[levelElementsGaps]
				* -0.5;
		levelElements[levelElementsAvGaps] = levelElements[levelElementsAvGaps]
				* -1;
		levelElements[levelElementsEnemies] = levelElements[levelElementsEnemies]
				* -1;
		levelElements[levelElementsCannnFlowers] = levelElements[levelElementsCannnFlowers]
				* -0.5;
		levelElements[levelElementsPowerUps] = levelElements[levelElementsPowerUps] * 1;

		metric1 = levelElements[levelElementsGaps]
				+ levelElements[levelElementsAvGaps]
						+ levelElements[levelElementsEnemies]
								+ levelElements[levelElementsCannnFlowers]
										+ levelElements[levelElementsPowerUps];
		System.out.println("Metric 1: " + metric1);

		/*
		 * System.out.println("Huecos" + levelElements[levelElementsGaps]);
		 * System.out.println("Av Huecos" + levelElements[levelElementsAvGaps]);
		 * System.out.println("Enem" + levelElements[levelElementsEnemies]);
		 * System.out.println("canon y flores" +
		 * levelElements[levelElementsCannnFlowers]);
		 * System.out.println("powerUps" +
		 * levelElements[levelElementsPowerUps]);
		 */

		return metric1;
	}
	
	public void countOtherElements(byte[][] array) {
		levelElements[levelElementsGaps]=0;
		levelElements[levelElementsAvGaps]=0;
		levelElements[levelElementsEnemies]=0;
		levelElements[levelElementsCannnFlowers]=0;
		levelElements[levelElementsPowerUps]=0;
		boolean gap = false;
		int widthGap = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < array[i].length; j++) {

				if (array[i][j] == (byte) (14 + 0 * 16)) {
					levelElements[levelElementsCannnFlowers] = levelElements[levelElementsCannnFlowers] + 1;// canhao
				}
				if (array[i][j] == (byte) (4 + 2 + 1 * 16)) {
					levelElements[levelElementsPowerUps] = levelElements[levelElementsPowerUps] + 1;// canhao
				}

				if ((j == (array[i].length) - 1) && (array[i][j] == (byte) (0))) {
					widthGap = widthGap + 1;
					if (gap == false) {
						gap = true;
					}
				} else if ((j == (array[i].length) - 1)
						&& (array[i][j] != (byte) (0)) && (gap == true)) {
					levelElements[levelElementsGaps] = levelElements[levelElementsGaps] + 1;
					levelElements[levelElementsAvGaps] = levelElements[levelElementsAvGaps]
							+ widthGap;
					widthGap = 0;
					gap = false;

				}

			}

		}
		if(levelElements[levelElementsGaps]!=0)
		{
		levelElements[levelElementsAvGaps] = levelElements[levelElementsAvGaps]
				/ levelElements[levelElementsGaps];
		}
	}

	public void countEnemies(byte[][] array) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (level.getSpriteTemplate(i, j) != null) {

					if (level.getSpriteTemplate(i, j).getType() == SpriteTemplate.JUMP_FLOWER) {
						levelElements[levelElementsCannnFlowers] = levelElements[levelElementsCannnFlowers] + 1;
					} else if (level.getSpriteTemplate(i, j).getType() == SpriteTemplate.GOOMPA) {
						levelElements[levelElementsEnemies] = levelElements[levelElementsEnemies] + 1;
					} else if (level.getSpriteTemplate(i, j).getType() == SpriteTemplate.GREEN_TURTLE) {
						levelElements[levelElementsEnemies] = levelElements[levelElementsEnemies] + 1;
					} else if (level.getSpriteTemplate(i, j).getType() == SpriteTemplate.RED_TURTLE) {
						levelElements[levelElementsEnemies] = levelElements[levelElementsEnemies] + 1;
					} else if (level.getSpriteTemplate(i, j).getType() == SpriteTemplate.ARMORED_TURTLE) {
						levelElements[levelElementsEnemies] = levelElements[levelElementsEnemies] + 1;
					} else if (level.getSpriteTemplate(i, j).getType() == SpriteTemplate.CHOMP_FLOWER) {
						levelElements[levelElementsEnemies] = levelElements[levelElementsEnemies] + 1;
					} else if (level.getSpriteTemplate(i, j).getType() == SpriteTemplate.CANNON_BALL) {
						levelElements[levelElementsEnemies] = levelElements[levelElementsEnemies] + 1;
					}

				}

			}

		}

	}
}
