package petermawhorter;

import java.util.Random;

import dk.itu.mario.MarioInterface.Constraints;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelGenerator;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.LevelFactory;

public class PeterLevelGenerator implements LevelGenerator{
  public LevelInterface generateLevel(GamePlay playerMetrics) {
    System.out.println("entramos 2.3");
	  LevelInterface level = new CustomizedLevel(320, 15,  new Random().nextLong(), 1, 0, playerMetrics );
	  System.out.println("entramos 2.6");
    
    LevelFactory.register("PeterLevel", level);
    return level;
  }
}
