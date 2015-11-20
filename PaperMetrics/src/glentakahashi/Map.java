package glentakahashi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;

import dk.itu.mario.MarioInterface.Constraints;

// TODO: Auto-generated Javadoc
/**
 * The Class Map.
 */
public class Map extends TreeMap<Integer, Block> {
	
	/** The max length. */
	private final int maxLength;
	
	/** The reverse map. */
	private HashMap<Block, Integer> reverseMap;
	

	/** The full. */
	private boolean full;
	
	/** The Constant TYPE_EMPTY. */
	public static final int TYPE_EMPTY = UltraCustomizedLevel.TYPE_EMPTY;
	
	/** The Constant TYPE_GROUND. */
	public static final int TYPE_GROUND = UltraCustomizedLevel.TYPE_GROUND;
	
	/** The Constant TYPE_CEILING. */
	public static final int TYPE_CEILING = UltraCustomizedLevel.TYPE_CEILING;

	/**
	 * Instantiates a new map.
	 *
	 * @param level the level
	 */
	public Map() {
		this(Constraints.levelWidth);
	}

	/**
	 * Instantiates a new map.
	 *
	 * @param length the length
	 * @param level the level
	 */
	public Map(int length) {
		super();
		reverseMap = new HashMap<Block, Integer>();
		maxLength = length;
		full = false;
	}

	/**
	 * Gets the height at pos.
	 *
	 * @param pos the pos
	 * @return the height at pos
	 */
	public int getHeightAtPos(int pos) {
		return get(floorKey(pos)).getHeight();
	}

	/**
	 * Adds the.
	 *
	 * @param block the block
	 * @return true, if successful
	 */
	public boolean add(Block block) {
		if (full) {
			return false;
		}
		int pos = realLength() + 1;
		return add(block, pos);
	}

	/**
	 * Adds the.
	 *
	 * @param block the block
	 * @param pos the pos
	 * @return true, if successful
	 */
	private boolean add(Block block, int pos) {
		if (filled(pos + block.getWidth() - 1)) {
			full = true;
		} else if (size() > 0 && realLength() + block.getWidth() >= maxLength) {
			return false;
		} else if (this.size() > 0 && get(lastKey()).getType() == TYPE_EMPTY
				&& block.getType() == TYPE_EMPTY) {
			return false;
		}
		// if (this.size() > 0 && ((get(lastKey()).getType() == TYPE_GROUND
		// && block.getType() == TYPE_GROUND) || (get(lastKey()).getType() ==
		// TYPE_CEILING
		// && block.getType() == TYPE_CEILING))) {
		// get(lastKey()).merge(block);
		// }
		super.put(pos, block);
		reverseMap.put(block, pos);
		return true;
	}

	/**
	 * Filled.
	 *
	 * @param pos the pos
	 * @return true, if successful
	 */
	private boolean filled(int pos) {
		if (pos < 0 || pos >= maxLength) {
			return true;
		} else if (get(pos) != null && get(pos).getType() == TYPE_GROUND) {
			return true;
		} else if (floorKey(pos) != null && get(floorKey(pos)) != null
				&& ceilingKey(pos) != null && floorKey(pos) < pos
				&& pos <= ceilingKey(pos)
				&& get(floorKey(pos)).getType() == TYPE_GROUND) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	public void finalize() {
//		/*disabled for debugging purposes
		Block block = null;
		Block block2 = null;
		Iterator<Integer> it = keySet().iterator();
		while (it.hasNext()) {
			block = get(it.next());
			if (block2 != null) {
				block2.merge(block);
			}
			if (it.hasNext()) {
				block2 = get(it.next());
			} else {
				block2 = null;
			}
			block.merge(block2);
		}
//		*/
	}

	/**
	 * Gets the pos of block.
	 *
	 * @param block the block
	 * @return the pos of block
	 */
	public int getPosOfBlock(Block block) {
		if (!reverseMap.containsKey(block) || block == null) {
			return 0;
		}
		return reverseMap.get(block);
	}

	/**
	 * Real length.
	 *
	 * @return the int
	 */
	public int realLength() {
		if (this.size() <= 0) {
			return 0;
		}
		int lastPos = lastKey() + get(lastKey()).getWidth() - 1;
		return lastPos;
	}

	/**
	 * Entry before.
	 *
	 * @param block the block
	 * @return the block
	 */
	public Block entryBefore(Block block) {
		NavigableMap<Integer,Block> set = this.descendingMap();
		Iterator<Integer> iter = set.keySet().iterator();
		Block b = null;
		Block finalBlock = null;
		boolean nextOne = false;
		while(iter.hasNext())
		{
			b = set.get(iter.next());
			if(nextOne)
			{
				finalBlock = b;
				nextOne = false;
				break;
			}
			if(b==block)
			{
				nextOne = true;
			}
		}
			return finalBlock;
	}
	
	/**
	 * Full.
	 *
	 * @return true, if successful
	 */
	public boolean full()
	{
		return full;
	}
	
	public Block lastBlock()
	{
		return this.lastEntry().getValue();
	}
}
