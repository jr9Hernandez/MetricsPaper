package glentakahashi;

import dk.itu.mario.engine.sprites.SpriteTemplate;

// TODO: Auto-generated Javadoc
/**
 * The Class EnemyBlock.
 */
public class EnemyBlock {
	
	/** The Constant ENEMY_GOOMBA. */
	public static final int ENEMY_GOOMBA = UltraCustomizedLevel.ENEMY_GOOMBA;
	
	/** The Constant ENEMY_GREEN_KOOPA. */
	public static final int ENEMY_GREEN_KOOPA = UltraCustomizedLevel.ENEMY_GREEN_KOOPA;
	
	/** The Constant ENEMY_RED_KOOPA. */
	public static final int ENEMY_RED_KOOPA = UltraCustomizedLevel.ENEMY_RED_KOOPA;
	
	/** The Constant ENEMY_SPIKY_KOOPA. */
	public static final int ENEMY_SPIKY_KOOPA = UltraCustomizedLevel.ENEMY_SPIKY_KOOPA;
	
	/** The Constant ENEMY_JUMP_FLOWER. */
	public static final int ENEMY_JUMP_FLOWER = UltraCustomizedLevel.ENEMY_JUMP_FLOWER;
	
	/** The Constant ENEMY_CHOMP_FLOWER. */
	public static final int ENEMY_CHOMP_FLOWER = UltraCustomizedLevel.ENEMY_CHOMP_FLOWER;

	/** The y. */
	private final int x, y;
	
	/** The type. */
	private int type;
	
	/** The flying. */
	private final boolean flying;

	/**
	 * Instantiates a new enemy block.
	 *
	 * @param x the x
	 * @param y the y
	 * @param type the type
	 * @param flying the flying
	 */
	public EnemyBlock(int x, int y, int type, boolean flying) {
		this.x = x;
		this.y = y;
		setType(type);
		if(type!=ENEMY_GOOMBA&&type!=ENEMY_GREEN_KOOPA&&type!=ENEMY_RED_KOOPA)
		{
			this.flying = false;
		}
		else
		{
			this.flying = flying;
		}
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		if (type < 0) {
			this.type = ENEMY_GOOMBA;
			if (Math.random() < 1.0 / 6)
				this.type = ENEMY_GOOMBA;
			else if (Math.random() < 1.0 / 6)
				this.type = ENEMY_GREEN_KOOPA;
			else if (Math.random() < 1.0 / 6)
				this.type = ENEMY_RED_KOOPA;
			else if (Math.random() < 1.0 / 6)
				this.type = ENEMY_SPIKY_KOOPA;
			else if (Math.random() < 1.0 / 6)
				this.type = ENEMY_JUMP_FLOWER;
			else if (Math.random() < 1.0 / 6)
				this.type = ENEMY_CHOMP_FLOWER;
		} else {
			this.type = type;
		}
	}

	/**
	 * Gets the tile.
	 *
	 * @return the tile
	 */
	public SpriteTemplate getTile() {
		return new SpriteTemplate(type, flying);
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}
}
