package glentakahashi;

import dk.itu.mario.MarioInterface.LevelInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class Block.
 */
public class Block {
	
	/** The Constant EMPTY. */
	private static final int EMPTY = 0;
	
	/** The theme. */
	private int type, theme;
	
	/** The height. */
	private int width, height;
	
	/** The data. */
	private byte[][] data;
	
	/** The level. */
	private UltraCustomizedLevel level;
	
	/** The Constant TYPE_EMPTY. */
	public static final int TYPE_EMPTY = UltraCustomizedLevel.TYPE_EMPTY;
	
	/** The Constant TYPE_GROUND. */
	public static final int TYPE_GROUND = UltraCustomizedLevel.TYPE_GROUND;
	
	/** The Constant TYPE_CEILING. */
	public static final int TYPE_CEILING = UltraCustomizedLevel.TYPE_CEILING;

	/** The Constant GROUND_LEFT_UP_UNBOUNDED. */
	private static final byte GROUND_LEFT_UP_UNBOUNDED = (byte) (8 * 16);
	
	/** The Constant GROUND_TOP. */
	private static final byte GROUND_TOP = (byte) (1 + 8 * 16);
	
	/** The Constant GROUND_RIGHT_UP_UNBOUNDED. */
	private static final byte GROUND_RIGHT_UP_UNBOUNDED = (byte) (2 + 8 * 16);
	
	/** The Constant GROUND_LEFT_SIDE. */
	private static final byte GROUND_LEFT_SIDE = (byte) (9 * 16);
	
	/** The Constant GROUND_FILL. */
	private static final byte GROUND_FILL = (byte) (1 + 9 * 16);
	
	/** The Constant GROUND_RIGHT_SIDE. */
	private static final byte GROUND_RIGHT_SIDE = (byte) (2 + 9 * 16);
	
	/** The Constant GROUND_LEFT_POCKET. */
	private static final byte GROUND_LEFT_POCKET = (byte) (3 + 8 * 16);
	
	/** The Constant GROUND_RIGHT_POCKET. */
	private static final byte GROUND_RIGHT_POCKET = (byte) (3 + 9 * 16);
	// ceiling stuff
	/** The Constant GROUND_LEFT_DOWN_POCKET. */
	private static final byte GROUND_LEFT_DOWN_POCKET = (byte) (3 + 11 * 16);
	
	/** The Constant GROUND_RIGHT_DOWN_POCKET. */
	private static final byte GROUND_RIGHT_DOWN_POCKET = (byte) (3 + 10 * 16);
	
	/** The Constant GROUND_LEFT_DOWN_UNBOUNDED. */
	private static final byte GROUND_LEFT_DOWN_UNBOUNDED = (byte) (0 + 10 * 16);
	
	/** The Constant GROUND_RIGHT_DOWN_UNBOUNDED. */
	private static final byte GROUND_RIGHT_DOWN_UNBOUNDED = (byte) (2 + 10 * 16);
	
	/** The Constant GROUND_BOTTOM. */
	private static final byte GROUND_BOTTOM = (byte) (1 + 10 * 16);

	/**
	 * Instantiates a new block.
	 *
	 * @param width the width
	 * @param height the height
	 * @param type the type
	 * @param theme the theme
	 * @param level the level
	 */
	public Block(int width, int height, int type, int theme,
			UltraCustomizedLevel level) {
		this.level = level;
		// theme is tileset
		if (theme == LevelInterface.TYPE_UNDERGROUND) {
			this.theme = 3 * 4;
		} else if (theme == LevelInterface.TYPE_CASTLE) {
			this.theme = 2 * 4;
		} else {
			this.theme = 0;
		}
		// type is empty or ground
		this.type = type;
		this.width = width;
		this.height = height;
		data = new byte[width][height];
		if (type != TYPE_EMPTY) {
			fill();
		}
	}

	/**
	 * Fill.
	 */
	private void fill() {
		for (int i = 0; i < width; i++) {
			for (int j = height - 1; j >= 0; j--) {
				data[i][j] = GROUND_FILL;
			}
		}
		for (int i = 0; i < width; i++) {
			data[i][height - 1] = (type == TYPE_GROUND ? GROUND_TOP
					: GROUND_BOTTOM);
		}
		for (int j = height - 1; j >= 0; j--) {
			data[0][j] = GROUND_LEFT_SIDE;
			data[width - 1][j] = GROUND_RIGHT_SIDE;
		}
			data[width - 1][height - 1] = (type == TYPE_GROUND ? GROUND_RIGHT_UP_UNBOUNDED
					: GROUND_RIGHT_DOWN_UNBOUNDED);
			data[0][height - 1] = (type == TYPE_GROUND ? GROUND_LEFT_UP_UNBOUNDED
					: GROUND_LEFT_DOWN_UNBOUNDED);
	}

	// merges right side of first block
	/**
	 * Merge.
	 *
	 * @param block the block
	 */
	public void merge(Block block) {
		if (block == null || block.getType()==TYPE_EMPTY || this.getType() == TYPE_EMPTY) {
			return;
		}
		int _height = block.getHeight();
		if (_height == height || _height > height) {
			for (int i = height - 1; i >= 0; i--) {
				data[width - 1][i] = GROUND_FILL;
			}
			data[width - 1][height - 1] = (type == TYPE_GROUND ? GROUND_TOP
					: GROUND_BOTTOM);
		} else {
			for (int i = height - 2; i >= 0; i--) {
				data[width - 1][i] = GROUND_FILL;
			}
			for (int i = height - 2; i >= _height; i--) {
				data[width - 1][i] = GROUND_RIGHT_SIDE;
			}
			data[width - 1][_height - 1] = (type == TYPE_GROUND ? GROUND_RIGHT_POCKET
					: GROUND_RIGHT_DOWN_POCKET);
			// data[width - 1][height - 1] = GROUND_RIGHT_UP_UNBOUNDED;
		}
		block.merge2(this);
	}

	// merges left side of second block
	/**
	 * Merge2.
	 *
	 * @param block the block
	 */
	private void merge2(Block block) {
		int _height = block.getHeight();
		if (_height == height || _height > height) {
			for (int i = height - 2; i >= 0; i--) {
				data[0][i] = GROUND_FILL;
			}
			data[0][height - 1] = (type == TYPE_GROUND ? GROUND_TOP
					: GROUND_BOTTOM);
		} else {
			for (int i = height - 2; i >= 0; i--) {
				data[0][i] = GROUND_FILL;
			}
			for (int i = height - 2; i >= _height; i--) {
				data[0][i] = GROUND_LEFT_SIDE;
			}
			data[0][_height - 1] = (type == TYPE_GROUND ? GROUND_LEFT_POCKET
					: GROUND_LEFT_DOWN_POCKET);
			// data[0][height - 1] = GROUND_LEFT_UP_UNBOUNDED;
		}
	}

	/**
	 * Change height.
	 *
	 * @param amt the amt
	 * @return true, if successful
	 */
	public boolean changeHeight(int amt) {
		if (amt == 0) {
			return false;
		} else {
			if (height + amt >= level.getHeight() || height + amt < 0) {
				return false;
			}
			height += amt;
			data = new byte[width][height];
			fill();
			return true;
		}
	}

	/**
	 * Change width.
	 *
	 * @param amt the amt
	 * @return true, if successful
	 */
	public boolean changeWidth(int amt) {
		if (amt == 0) {
			return false;
		} else {
			if (width + amt >= level.getWidth() || width + amt < 2) {
				return false;
			}
			width += amt;
			data = new byte[width][height];
			fill();
			return true;
		}
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Gets the tile.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the tile
	 */
	public byte getTile(int x, int y) {
		if (x >= width || y >= height || x < 0 || y < 0) {
			return 0;
		}
		if (type == TYPE_EMPTY) {
			return EMPTY;
		} else {
			return (byte) (data[x][y] + theme);
		}
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

}