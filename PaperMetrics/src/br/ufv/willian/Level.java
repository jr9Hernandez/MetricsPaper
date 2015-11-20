package br.ufv.willian;
/*
 * Uma adaptação da classe Level do framework de GamePlaying e TestTuring
 * */
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sprites.SpriteTemplate;

import br.ufv.willian.auxiliares.SpriteTemplatePrimitivo;


public class Level implements Serializable, LevelInterface
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String[] BIT_DESCRIPTIONS = {//
    "BLOCK UPPER", //
            "BLOCK ALL", //
            "BLOCK LOWER", //
            "SPECIAL", //
            "BUMPABLE", //
            "BREAKABLE", //
            "PICKUPABLE", //
            "ANIMATED",//
    };

	/*
	 * Já estão declarados na classe LevelInterface
	 * 
    public static byte[] TILE_BEHAVIORS = new byte[256];

    public static final int BIT_BLOCK_UPPER = 1 << 0;
    public static final int BIT_BLOCK_ALL = 1 << 1;
    public static final int BIT_BLOCK_LOWER = 1 << 2;
    public static final int BIT_SPECIAL = 1 << 3;
    public static final int BIT_BUMPABLE = 1 << 4;
    public static final int BIT_BREAKABLE = 1 << 5;
    public static final int BIT_PICKUPABLE = 1 << 6;
    public static final int BIT_ANIMATED = 1 << 7;
    
    */
    
    //**********************************************************
    
    private static final int FILE_HEADER = 0x271c4178;
    public int width;
    public int height;

    public byte[][] map;
    public byte[][] data;
    public byte[][] observation;

    public SpriteTemplate[][] spriteTemplates;

    public int xExit;
    public int yExit;

// Alterei ********* 29/05/2014 ******************
    public SpriteTemplatePrimitivo spritePrimitivo[]; // Vetor para guardar todas as informações dos sprites adicionados
    public int tam_spritePrimitivo = 0; // Guarda o tamanho do template
    public ArrayList<Point>listaInicioFimTelas = new ArrayList<Point>();
    
// *********************************************

    public Level(int width, int height)
    {
        this.width = width;
        this.height = height;

        xExit = 10;
        yExit = 10;
        map = new byte[width][height];
        data = new byte[width][height];
        spriteTemplates = new SpriteTemplate[width][height];
        observation = new byte[width][height];
        
        //***************************** Inicializando com o tamanho da largura da tela
       spritePrimitivo = new SpriteTemplatePrimitivo[width];
        
        //System.out.println("\n\tENTROU NO CONTRUTOR DA CLASSE Level\n\t\tLargura: " + this.width + "\tAltura: " + this.height);
    }


    public static void loadBehaviors(DataInputStream dis) throws IOException
    {
        dis.readFully(Level.TILE_BEHAVIORS);
    }

    public static void saveBehaviors(DataOutputStream dos) throws IOException
    {
        dos.write(Level.TILE_BEHAVIORS);
    }

    public static Level load(DataInputStream dis) throws IOException
    {
        long header = dis.readLong();
        if (header != Level.FILE_HEADER) throw new IOException("Bad level header");
        int version = dis.read() & 0xff;

        int width = dis.readShort() & 0xffff;
        int height = dis.readShort() & 0xffff;      
        
        Level level = new Level(width, height);
        /*
         * Modifiquei o código aqui para salvar outras informações sobre o LEVEl
         * */
        level.xExit = dis.readInt();
        level.yExit = dis.readInt();
        
        level.map = new byte[width][height];
        level.data = new byte[width][height];
        for (int i = 0; i < width; i++)
        {
            dis.readFully(level.map[i]);
            dis.readFully(level.data[i]);
        }
        
        level.tam_spritePrimitivo = dis.readInt(); //Carregar o tamanho do vetor de sprites
        
        //System.out.println("Tamanho carregado: " + level.tam_spritePrimitivo);
        
        int x, y, tipo, alado;
      
        for (int i = 0; i < level.tam_spritePrimitivo; i++)
        {
        	x = dis.readInt();
        	y = dis.readInt();
            tipo = dis.readInt();
            alado = dis.readInt();
            /*System.out.println("\tx: " + x + " y: " + y + 
            		" type: " + tipo + " alado: " + alado); */
            
            level.spritePrimitivo[i] = new SpriteTemplatePrimitivo(x, y, tipo, alado == 1 ? true : false);
        }
        
        level.setSpritePrimitivo();
        
        return level;
    }

    public void save(DataOutputStream dos) throws IOException
    {
        dos.writeLong(Level.FILE_HEADER);
        dos.write((byte) 0);

        dos.writeShort((short) width);
        dos.writeShort((short) height);

        dos.writeInt(xExit);
        dos.writeInt(yExit);
        
        int Willian; // Apenas para marcar o ponto onde estou editando
        
        for (int i = 0; i < width; i++)
        {
            dos.write(map[i]);
            dos.write(data[i]);
        }
        dos.writeInt(tam_spritePrimitivo);
        
        System.out.println("Tamanho salvo: " + tam_spritePrimitivo);
 

        for (int i = 0; i < tam_spritePrimitivo; i++)
        {
        	dos.writeInt(spritePrimitivo[i].getX());
            dos.writeInt(spritePrimitivo[i].getY());
            dos.writeInt(spritePrimitivo[i].getType());
            dos.writeInt(spritePrimitivo[i].getWinged());
            
            System.out.println("\tx: " + spritePrimitivo[i].getX() + " y: " + spritePrimitivo[i].getY() + 
            		" type: " + spritePrimitivo[i].getType() + " alado: " + spritePrimitivo[i].getWinged());
            
        }
        
    }

    public void setSpritePrimitivo()
    {
    	for (int i = 0; i < tam_spritePrimitivo; i++)
        {
    		spriteTemplates[spritePrimitivo[i].getX()][spritePrimitivo[i].getY()] = 
    				new SpriteTemplate(spritePrimitivo[i].getType(), spritePrimitivo[i].getWinged() == 1 ? true : false);
        }
    }
    
    public void tick()
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (data[x][y] > 0) data[x][y]--;
            }
        }
    }

    public byte getBlockCapped(int x, int y)
    {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x >= width) x = width - 1;
        if (y >= height) y = height - 1;
        return map[x][y];
    }

    public byte getBlock(int x, int y)
    {
        if (x < 0) x = 0;
        if (y < 0) return 0;
        if (x >= width) x = width - 1;
        if (y >= height) y = height - 1;
        return map[x][y];
    }

    public void setBlock(int x, int y, byte b)
    {
        if (x < 0) return;
        if (y < 0) return;
        if (x >= width) return;
        if (y >= height) return;
        map[x][y] = b;
    }

    public void setBlockData(int x, int y, byte b)
    {
        if (x < 0) return;
        if (y < 0) return;
        if (x >= width) return;
        if (y >= height) return;
        data[x][y] = b;
    }

    public boolean isBlocking(int x, int y, float xa, float ya)
    {
        byte block = getBlock(x, y);
        boolean blocking = ((TILE_BEHAVIORS[block & 0xff]) & BIT_BLOCK_ALL) > 0;
        blocking |= (ya > 0) && ((TILE_BEHAVIORS[block & 0xff]) & BIT_BLOCK_UPPER) > 0;
        blocking |= (ya < 0) && ((TILE_BEHAVIORS[block & 0xff]) & BIT_BLOCK_LOWER) > 0;

        return blocking;
    }


    public SpriteTemplate getSpriteTemplate(int x, int y)
    {
        if (x < 0) return null;
        if (y < 0) return null;
        if (x >= width) return null;
        if (y >= height) return null;
        return spriteTemplates[x][y];
    }

    public void setSpriteTemplate(int x, int y, SpriteTemplate spriteTemplate)
    {
        if (x < 0) return;
        if (y < 0) return;
        if (x >= width) return;
        if (y >= height) return;
        spriteTemplates[x][y] = spriteTemplate;
        
//Maneira para salvar os tipos primitipos dos sprites        29/05/2014
        spritePrimitivo[tam_spritePrimitivo] = new SpriteTemplatePrimitivo(x, y, spriteTemplate.getType(), spriteTemplate.getWinged());
        tam_spritePrimitivo++;
//********************************************************
    }    

    public int getWidthCells() {         return width;    }

    public double getWidthPhys() {         return width * 16;    }

	//@Override
	public byte[][] getMap() {
		// TODO Auto-generated method stub
		return map;
		//return null;
	}

	//@Override
	public SpriteTemplate[][] getSpriteTemplates() {
		// TODO Auto-generated method stub
		//return null;
		return spriteTemplates;
	}

	//@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		//return 0;
		return width;
	}

	//@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		//return 0;
		return height;
	}

	//@Override
	public int getxExit() {
		// TODO Auto-generated method stub
		//return 0;
		return xExit;
	}

	//@Override
	public int getyExit() {
		// TODO Auto-generated method stub
		//return 0;
		return yExit;
	}

	//@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
		
	}
}