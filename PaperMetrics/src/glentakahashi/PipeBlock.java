package glentakahashi;

// TODO: Auto-generated Javadoc
/**
 * The Class PipeBlock.
 */
public class PipeBlock {
	
	/** The data. */
	private byte[][] data;
	
	/** The height. */
	private final int x, y, height;
	
    /** The Constant TUBE_TOP_LEFT. */
    private static final byte TUBE_TOP_LEFT = (byte) (10 + 0 * 16);
    
    /** The Constant TUBE_TOP_RIGHT. */
    private static final byte TUBE_TOP_RIGHT = (byte) (11 + 0 * 16);

    /** The Constant TUBE_SIDE_LEFT. */
    private static final byte TUBE_SIDE_LEFT = (byte) (10 + 1 * 16);
    
    /** The Constant TUBE_SIDE_RIGHT. */
    private static final byte TUBE_SIDE_RIGHT = (byte) (11 + 1 * 16);
	
	/**
	 * Instantiates a new pipe block.
	 *
	 * @param x the x
	 * @param y the y
	 * @param height the height
	 */
	public PipeBlock(int x,int y,int height)
	{
		this.x = x;
		this.y = y;
		this.height = height;
		data = new byte[2][height];
		fill();
	}
	
	/**
	 * Fill.
	 */
	private void fill()
	{
			for(int j = 0;j<height;j++)
			{
				data[0][j]=TUBE_SIDE_LEFT;
				data[1][j]=TUBE_SIDE_RIGHT;
			}
			data[0][height-1]=TUBE_TOP_LEFT;
			data[1][height-1]=TUBE_TOP_RIGHT;
	}
	
	/**
	 * Gets the tile.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the tile
	 */
	public byte getTile(int x, int y)
	{
		if(x<0||x>=2||y<0||y>=height)
		{
			return 0;
		}
		return data[x][y]; 
	}
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth()
	{
		return 2;
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

}
