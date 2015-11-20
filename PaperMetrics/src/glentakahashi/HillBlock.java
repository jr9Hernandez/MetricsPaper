package glentakahashi;

// TODO: Auto-generated Javadoc
/**
 * The Class HillBlock.
 * the 
 */
public class HillBlock {
	
	/** The data. */
	private byte[][] data;
	
	/** The pos. */
	private final int width, height, pos;

	/* The constants for the tiles of the hill */
	private static final byte HILL_LEFT_UP_UNBOUNDED = (byte) (4 + 8 * 16);
	private static final byte HILL_TOP = (byte) (5 + 8 * 16);
	private static final byte HILL_RIGHT_UP_UNBOUNDED = (byte) (6 + 8 * 16);
	private static final byte HILL_LEFT = (byte) (4 + 9 * 16);
	private static final byte HILL_FILL = (byte) (5 + 9 * 16);
	private static final byte HILL_RIGHT = (byte) (6 + 9 * 16);


	/**
	 * Instantiates a new hill block.
	 *
	 * @param width the width
	 * @param height the height
	 * @param pos the pos
	 * @param level the level
	 */
	public HillBlock(int pos, int width, int height) {
		this.width = width;
		this.height = height;
		this.pos = pos;
		data = new byte[width][height];
		fill();
	}

	/**
	 * Fill.
	 */
	private void fill() {
		for (int i = 0; i < width; i++) {
			for (int j = height - 1; j >= 0; j--) {
				data[i][j] = HILL_FILL;
			}
		}
		for (int i = 0; i < width; i++) {
			data[i][height - 1] = HILL_TOP;
		}
		for (int j = height - 1; j >= 0; j--) {
			data[0][j] = HILL_LEFT;
			data[width - 1][j] = HILL_RIGHT;
		}
			data[width - 1][height - 1] = HILL_RIGHT_UP_UNBOUNDED;
			data[0][height - 1] = HILL_LEFT_UP_UNBOUNDED;
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
		return data[x][y];
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

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public int getPos() {
		return pos;
	}
}