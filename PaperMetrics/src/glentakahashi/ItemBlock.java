package glentakahashi;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class ItemBlock.
 */
public class ItemBlock {

	/** The data. */
	private byte[] data;

	/** The height. */
	private final int width, type, pos, height;

	/** The Constant BLOCK_EMPTY. */
	private static final byte BLOCK_EMPTY = (byte) (0 + 1 * 16);

	/** The Constant BLOCK_POWERUP. */
	private static final byte BLOCK_POWERUP = (byte) (4 + 2 + 1 * 16);

	/** The Constant BLOCK_COIN. */
	private static final byte BLOCK_COIN = (byte) (4 + 1 + 1 * 16);

	/** The Constant BLOCK_HIDDEN_COIN. */
	private static final byte BLOCK_HIDDEN_COIN = (byte) (1 + 1 * 16);

	/** The Constant COIN. */
	private static final byte COIN = (byte) (2 + 2 * 16);

	/** The Constant TYPE_COINS. */
	public static final int TYPE_COINS = UltraCustomizedLevel.TYPE_COINS;

	public static final int TYPE_HIDDEN_COINS = UltraCustomizedLevel.TYPE_HIDDEN_COINS;

	/** The Constant TYPE_BLOCKS. */
	public static final int TYPE_BLOCKS = UltraCustomizedLevel.TYPE_BLOCKS;

	public static final int TYPE_BLOCKS_COINS = UltraCustomizedLevel.TYPE_BLOCKS_COINS;

	public static final int TYPE_BLOCKS_COINS_FILLED = UltraCustomizedLevel.TYPE_BLOCKS_COINS_FILLED;

	/** The Constant TYPE_BLOCKS_ITEMS. */
	public static final int TYPE_BLOCKS_POWERUP = UltraCustomizedLevel.TYPE_BLOCKS_POWERUP;

	public static final int TYPE_BLOCKS_ONLY_POWERUP = UltraCustomizedLevel.TYPE_BLOCKS_ONLY_POWERUP;

	public static final int TYPE_BLOCKS_POWERUP_FILLED = UltraCustomizedLevel.TYPE_BLOCKS_POWERUP_FILLED;

	/**
	 * Instantiates a new item block.
	 * 
	 * @param pos
	 *            the pos
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param type
	 *            the type
	 */
	public ItemBlock(int pos, int width, int height, int type) {
		this.height = height;
		this.pos = pos;
		this.type = type;
		this.width = width;
		data = new byte[width];
		fill();
	}

	/**
	 * Fill.
	 */
	private void fill() {
		// random padding
		int temp1 = 0;
		int temp2 = 0;
		int start = 0;
		int end = 0;
		if (width <= 2) {
			temp1 = 0;
			temp2 = 0;
		} else if (width <= 4) {
			temp1 = 1;
			temp2 = 1;
		} else {
			temp1 = 2;
			temp2 = 2;
			start = 1;
			end = 1;
		}
		if (width > temp1 + temp2) {
			start += (int) (Math.random() * temp1);
			end += (int) (Math.random() * temp2);
		}
		if (type == TYPE_HIDDEN_COINS) {
			for (int i = 0; i < width; i++) {
				data[i] = COIN;
			}
		} else if (type == TYPE_COINS) {
			for (int i = start; i < width - end; i++) {
				data[i] = COIN;
			}
		} else if (type == TYPE_BLOCKS) {
			for (int i = start; i < width - end; i++) {
				data[i] = BLOCK_EMPTY;
			}
		} else if (type == TYPE_BLOCKS_COINS) {
			for (int i = start; i < width - end; i++) {
				byte temp = BLOCK_EMPTY;
				if (Math.random() < .20) {
					temp = BLOCK_COIN;
				} else if (Math.random() < .20) {
					temp = BLOCK_HIDDEN_COIN;
				}

				data[i] = temp;
			}
		} else if (type == TYPE_BLOCKS_COINS_FILLED) {
			for (int i = 0; i < width; i++) {
				byte temp = BLOCK_EMPTY;
				if (Math.random() < .20) {
					temp = BLOCK_COIN;
				} else if (Math.random() < .20) {
					temp = BLOCK_HIDDEN_COIN;
				}

				data[i] = temp;
			}
		} else if (type == TYPE_BLOCKS_POWERUP) {

			for (int i = start; i < width - end; i++) {
				byte temp = BLOCK_EMPTY;
				if (Math.random() < .20) {
					temp = BLOCK_COIN;
				} else if (Math.random() < .20) {
					temp = BLOCK_HIDDEN_COIN;
				}

				data[i] = temp;
			}
			Random rand = new Random();
			data[start + rand.nextInt(width - end - start)] = BLOCK_POWERUP;
		} else if (type == TYPE_BLOCKS_POWERUP_FILLED) {
			for (int i = 0; i < width; i++) {
				byte temp = BLOCK_EMPTY;
				if (Math.random() < .20) {
					temp = BLOCK_COIN;
				} else if (Math.random() < .20) {
					temp = BLOCK_HIDDEN_COIN;
				}

				data[i] = temp;
			}
			Random rand = new Random();
			data[rand.nextInt(width)] = BLOCK_POWERUP;
		} else if (type==TYPE_BLOCKS_ONLY_POWERUP)
		{
			Random rand = new Random();
			data[start + rand.nextInt(width - end - start)] = BLOCK_POWERUP;
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

	/**
	 * Gets the pos.
	 * 
	 * @return the pos
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the tile.
	 * 
	 * @param x
	 *            the x
	 * @return the tile
	 */
	public byte getTile(int x) {
		if (x >= width || x < 0) {
			return 0;
		}
//		System.out.println(data[x]);
		return data[x];
	}

}
