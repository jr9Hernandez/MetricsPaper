package dk.itu.mario.engine;

import java.util.*;

import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.level.Level;

public abstract class LevelFactory {

	private static Map<String, LevelInterface> levels = new HashMap<String, LevelInterface>();

	public static void register(String name,LevelInterface level){
		levels.put(name, level);
	}

	public static LevelInterface retrieve(String name){
		return levels.get(name);
	}

	public static Iterator<String> getKeyset(){
		return levels.keySet().iterator();
	}

	public static Map getLevelsMap(){
		return levels;
	}
}