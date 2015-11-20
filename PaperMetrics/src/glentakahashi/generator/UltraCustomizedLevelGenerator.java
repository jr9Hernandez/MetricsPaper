package glentakahashi.generator;

import glentakahashi.UltraCustomizedLevel;

import java.util.Random;

import dk.itu.mario.MarioInterface.Constraints;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelGenerator;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.LevelFactory;
import dk.itu.mario.level.CustomizedLevel;
import dk.itu.mario.level.Level;
import dk.itu.mario.level.RandomLevel;

public class UltraCustomizedLevelGenerator implements LevelGenerator {

	/* (non-Javadoc)
	 * @see dk.itu.mario.MarioInterface.LevelGenerator#generateLevel(dk.itu.mario.MarioInterface.GamePlay)
	 */
	//@Override
	public LevelInterface generateLevel(GamePlay playerMetrics) {
		LevelInterface level = new UltraCustomizedLevel(Constraints.levelWidth,15,new Random().nextLong(), 5 ,LevelInterface.TYPE_OVERGROUND,playerMetrics);
		LevelFactory.register("GlenLevel", level);

		return level;
	}

}
