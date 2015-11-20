package br.ufv.willian;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import br.ufv.willian.auxiliares.ConectorDeTelas;
import br.ufv.willian.auxiliares.EquacaoFases;
import br.ufv.willian.auxiliares.InformacoesTelas;
import br.ufv.willian.auxiliares.MedidorDeDificuldade;
import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.level.Level;

public class WillianLevel extends Level{
	
	public static final int TYPE_OVERGROUND = 0;
    public static final int TYPE_UNDERGROUND = 1;
    public static final int TYPE_CASTLE = 2;

    private static Random levelSeedRandom = new Random();
    public static long lastSeed;
    public static final int LevelLengthMinThreshold = 50;

    public static Level createLevel(int width, int height, long seed, int difficulty, int type)
    {
    	WillianLevel levelGenerator = new WillianLevel(width, height);
        return levelGenerator.createLevel(seed, difficulty, type);
    }

    //private int width; //Herdado da classe Pai
    //private int height; //Herdado da classe Pai
    Level level = new Level(width, height);
    Random random;

    private static final int ODDS_STRAIGHT = 0;
    private static final int ODDS_HILL_STRAIGHT = 1;
    private static final int ODDS_TUBES = 2;
    private static final int ODDS_JUMP = 3;
    private static final int ODDS_CANNONS = 4;
    private int[] odds = new int[5];
    private int totalOdds;
    private int difficulty;
    private int type;

    private WillianLevel(int width, int height)
    {
    	super(width, height);
        //System.out.println("ENTROU NO LevelGeneration");
    }

    private Level createLevel(long seed, int difficulty, int type)
    {   
    	byte EstoueditandoAqui;
    	
    	level = createLevelOriginal(seed, difficulty, type);
       /*         
        salvaTela(level, "tela2"); //Teste ado metodo que salva a tela
        salvaInfoTela(level);
        */
        //level = conectaTelas(level);        
        //level =  retornaTela(level, "telas/tela2");
        //level = controiLevel(level);
        //level = controiLevelPorFuncao(level);
    	//EquacaoFases equacao = new Parabola(-1/8, 3/3, 3);
    	//level = controiLevelPorFuncao(level, equacao);
        
        //corrigeFase(level);
    	return level;
    	
        
    }
    
    private Level createLevelOriginal(long seed, int difficulty, int type)
    {   
    	//Classe original do createLevel
    	
    	this.type = type;
        this.difficulty = difficulty;
        odds[ODDS_STRAIGHT] = 20;
        odds[ODDS_HILL_STRAIGHT] = 10;
        odds[ODDS_TUBES] = 2 + 1 * difficulty;
        odds[ODDS_JUMP] = 2 * difficulty;
        odds[ODDS_CANNONS] = -10 + 5 * difficulty;

        if (type != LevelGenerator.TYPE_OVERGROUND)
        {
            odds[ODDS_HILL_STRAIGHT] = 0;
        }

        for (int i = 0; i < odds.length; i++)
        {
            if (odds[i] < 0) odds[i] = 0;
            totalOdds += odds[i];
            odds[i] = totalOdds - odds[i];
        }

        lastSeed = seed;
        level = new Level(width, height);
        random = new Random(seed);

        int length = 0;
        length += buildStraight(0, level.getWidth(), true);
        while (length < level.getWidth() - 64)   
        {
            length += buildZone(length, level.getWidth() - length);
        }

        int floor = height - 1 - random.nextInt(4);

        //level.xExit = length + 8; //A saida é colocada a 8 pixels do ultimo obstaculo
        //level.yExit = floor;
        level.setxExit(length + 8);
        level.setyExit(floor);

        for (int x = length; x < level.getWidth(); x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16));
                }
            }
        }

        if (type == LevelGenerator.TYPE_CASTLE || type == LevelGenerator.TYPE_UNDERGROUND)
        {
            int ceiling = 0;
            int run = 0;
            for (int x = 0; x < level.getWidth(); x++)
            {
                if (run-- <= 0 && x > 4)
                {
                    ceiling = random.nextInt(4);
                    run = random.nextInt(4) + 4;
                }
                for (int y = 0; y < level.getHeight(); y++)
                {
                    if ((x > 4 && y <= ceiling) || x < 1)
                    {
                        level.setBlock(x, y, (byte) (1 + 9 * 16));
                    }
                }
            }
        }

        fixWalls();
        
    	return level;    	
        
    }
    
    private int buildZone(int x, int maxLength)
    {
    	//System.out.println("Entrou no buildZone");
    	
        int t = random.nextInt(totalOdds);
        int type = 0;
        for (int i = 0; i < odds.length; i++)
        {
            if (odds[i] <= t)
            {
                type = i;
            }
        }
        
        //if(aux % 3 == 0) type = ODDS_JUMP;
        //else type = ODDS_CANNONS;
        //type = ODDS_JUMP;
        //System.out.println("Valor de aux: " + aux);
        //aux++;
        
        switch (type)
        {
            case ODDS_STRAIGHT:
                return buildStraight(x, maxLength, false);           	
                //return 0;
            case ODDS_HILL_STRAIGHT:
                return buildHillStraight(x, maxLength);
            case ODDS_TUBES:   //Tubos
                return buildTubes(x, maxLength);
            case ODDS_JUMP:   //Buracos
                return buildJump(x, maxLength);
            case ODDS_CANNONS:    //Canhoes
                return buildCannons(x, maxLength);
        }
        
                
        return 0;
    }

    int cont_buracos = 0;
    private int buildJump(int xo, int maxLength)
    {    	
    	boolean trava = false;
    	int js = random.nextInt(4) + 2;
        int jl = random.nextInt(2) + 2;
        int length = js * 2 + jl;
        //int length = 10;

        boolean hasStairs = random.nextInt(3) == 0;
        //hasStairs = true;

        int floor = height - 1 - random.nextInt(4);
        for (int x = xo; x < xo + length; x++)
        {
            if (x < xo + js || x > xo + length - js - 1)
            {
                for (int y = 0; y < height; y++)
                {
                    if (y >= floor)
                    {
                        level.setBlock(x, y, (byte) (1 + 9 * 16));
                    }
                    else if (hasStairs)
                    {
                        if (x < xo + js)
                        {
                            if (y >= floor - (x - xo) + 1)
                            {
                                level.setBlock(x, y, (byte) (9 + 0 * 16));
                            }
                        }
                        else
                        {
                            if (y >= floor - ((xo + length) - x) + 2)
                            {
                                level.setBlock(x, y, (byte) (9 + 0 * 16));
                            }
                        }
                    }
                }         
                
            }
            trava = true;
        }
        
        if(trava)cont_buracos++;
        //System.out.println("Entrou no buildJump: " + length);
        //return 10;
        return length;
    }

    int cont_canhoes = 0;    
    private int buildCannons(int xo, int maxLength)
    {    	
        int length = random.nextInt(10) + 2;
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        int xCannon = xo + 1 + random.nextInt(4);
        for (int x = xo; x < xo + length; x++)
        {
            if (x > xCannon)
            {
                xCannon += 2 + random.nextInt(4);
                
            }
            if (xCannon == xo + length - 1) xCannon += 10;
            int cannonHeight = floor - random.nextInt(4) - 1;

            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16));                    
                }
                else
                {
                    if (x == xCannon && y >= cannonHeight)
                    {
                        if (y == cannonHeight)
                        {
                            level.setBlock(x, y, (byte) (14 + 0 * 16));
                            cont_canhoes++;
                        }
                        else if (y == cannonHeight + 1)
                        {
                        	level.setBlock(x, y, (byte) (14 + 1 * 16));
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (14 + 2 * 16));
                        }
                    }
                }
            }
            
            
        }

        //System.out.println("Entrou no buildCannons: " + length);
        return length;
    }

    private int buildHillStraight(int xo, int maxLength)
    {
        int length = random.nextInt(10) + 10;
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        for (int x = xo; x < xo + length; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16));
                }
            }
        }

        addEnemyLine(xo + 1, xo + length - 1, floor - 1);

        int h = floor;

        boolean keepGoing = true;

        boolean[] occupied = new boolean[length];
        while (keepGoing)
        {
            h = h - 2 - random.nextInt(3);

            if (h <= 0)
            {
                keepGoing = false;
            }
            else
            {
                int l = random.nextInt(5) + 3;
                int xxo = random.nextInt(length - l - 2) + xo + 1;

                if (occupied[xxo - xo] || occupied[xxo - xo + l] || occupied[xxo - xo - 1] || occupied[xxo - xo + l + 1])
                {
                    keepGoing = false;
                }
                else
                {
                    occupied[xxo - xo] = true;
                    occupied[xxo - xo + l] = true;
                    addEnemyLine(xxo, xxo + l, h - 1);
                    if (random.nextInt(4) == 0)
                    {
                        decorate(xxo - 1, xxo + l + 1, h);
                        keepGoing = false;
                    }
                    for (int x = xxo; x < xxo + l; x++)
                    {
                        for (int y = h; y < floor; y++)
                        {
                            int xx = 5;
                            if (x == xxo) xx = 4;
                            if (x == xxo + l - 1) xx = 6;
                            int yy = 9;
                            if (y == h) yy = 8;

                            if (level.getBlock(x, y) == 0)
                            {
                                level.setBlock(x, y, (byte) (xx + yy * 16));
                            }
                            else
                            {
                                if (level.getBlock(x, y) == (byte) (4 + 8 * 16)) level.setBlock(x, y, (byte) (4 + 11 * 16));
                                if (level.getBlock(x, y) == (byte) (6 + 8 * 16)) level.setBlock(x, y, (byte) (6 + 11 * 16));
                            }
                        }
                    }
                }
            }
        }

        //System.out.println("Entrou nobuildHillStraight");
        return length;
    }

    
    int marcador_inimigo;
    
    private void addEnemyLine(int x0, int x1, int y)
    {
        for (int x = x0; x < x1; x++)
        {
            if (random.nextInt(35) < difficulty + 1)
            {
                int type = random.nextInt(4);
                if (difficulty < 1)
                {
                    type = Enemy.ENEMY_GOOMBA;
                }
                else if (difficulty < 3)
                {
                    type = random.nextInt(3);
                }
                level.setSpriteTemplate(x, y, new SpriteTemplate(type, random.nextInt(35) < difficulty));
            }
        }
        
        //System.out.println("Entrou no addEnemyLine");
    }

    private int buildTubes(int xo, int maxLength)
    {
        int length = random.nextInt(10) + 5;
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        int xTube = xo + 1 + random.nextInt(4);
        int tubeHeight = floor - random.nextInt(2) - 2;
        for (int x = xo; x < xo + length; x++)
        {
            if (x > xTube + 1)
            {
                xTube += 3 + random.nextInt(4);
                tubeHeight = floor - random.nextInt(2) - 2;
            }
            if (xTube >= xo + length - 2) xTube += 10;

            if (x == xTube && random.nextInt(11) < difficulty + 1)
            {
                level.setSpriteTemplate(x, tubeHeight, new SpriteTemplate(Enemy.ENEMY_FLOWER, false));
            }

            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16));
                }
                else
                {
                    if ((x == xTube || x == xTube + 1) && y >= tubeHeight)
                    {
                        int xPic = 10 + x - xTube;
                        if (y == tubeHeight)
                        {
                            level.setBlock(x, y, (byte) (xPic + 0 * 16));
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (xPic + 1 * 16));
                        }
                    }
                }
            }
        }
        
        //System.out.println("Entrou no buildTubes: " + length);
        return length;
        //return 18;
    }

    private int buildStraight(int xo, int maxLength, boolean safe)
    {
        int length = random.nextInt(10) + 2;
        
        //if (safe) length = 10 + random.nextInt(5); // O inicio da fase e no minimo 10
        if (safe) length = 13; //Eu coloquei este valor fixo. Usei 13, para descontar 12 e deixar 1 para evitar possíveis bugs.
        
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        //int floor = height - 6;
        for (int x = xo; x < xo + length; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16));
                }
            }
        }

        if (!safe)
        {
            if (length > 5)
            {
                decorate(xo, xo + length, floor);
            }
        }
        
        //System.out.println("Entrou no buildStraight: " + length);
        return length;
        //return 19;
    }

    private void decorate(int x0, int x1, int floor)
    {
        if (floor < 1) return;

        //        boolean coins = random.nextInt(3) == 0;
        boolean rocks = true;

        addEnemyLine(x0 + 1, x1 - 1, floor - 1);

        int s = random.nextInt(4);
        int e = random.nextInt(4);
                  

        if (floor - 2 > 0)
        {
            if ((x1 - 1 - e) - (x0 + 1 + s) > 1)
            {
                for (int x = x0 + 1 + s; x < x1 - 1 - e; x++)
                {
                    level.setBlock(x, floor - 2, (byte) (2 + 2 * 16));
                }
            }
        }

        s = random.nextInt(4);
        e = random.nextInt(4);

        if (floor - 4 > 0)
        {
            if ((x1 - 1 - e) - (x0 + 1 + s) > 2)
            {
                for (int x = x0 + 1 + s; x < x1 - 1 - e; x++)
                {
                    if (rocks)
                    {
                        if (x != x0 + 1 && x != x1 - 2 && random.nextInt(3) == 0)
                        {
                            if (random.nextInt(4) == 0)
                            {
                                level.setBlock(x, floor - 4, (byte) (4 + 2 + 1 * 16));
                            }
                            else
                            {
                                level.setBlock(x, floor - 4, (byte) (4 + 1 + 1 * 16));
                            }
                        }
                        else if (random.nextInt(4) == 0)
                        {
                            if (random.nextInt(4) == 0)
                            {
                                level.setBlock(x, floor - 4, (byte) (2 + 1 * 16));
                            }
                            else
                            {
                                level.setBlock(x, floor - 4, (byte) (1 + 1 * 16));
                            }
                        }
                        else
                        {
                            level.setBlock(x, floor - 4, (byte) (0 + 1 * 16));
                        }
                    }
                }
            }
        }

        
        
        //System.out.println("Entrou no decorate");
        /*
         int length = x1 - x0 - 2;
         if (length > 5 && rocks)
         {
         decorate(x0, x1, floor - 4);
         }
         */
    }

    private void fixWalls()
    {
        boolean[][] blockMap = new boolean[width + 1][height + 1];
        for (int x = 0; x < width + 1; x++)
        //for (int x = 0; x < width + 1 ; x+=10)
        {
            for (int y = 0; y < height + 1; y++)
            {
                int blocks = 0;
                for (int xx = x - 1; xx < x + 1; xx++)
                {
                    for (int yy = y - 1; yy < y + 1; yy++)
                    {
                        if (level.getBlockCapped(xx, yy) == (byte) (1 + 9 * 16)) blocks++;
                    }
                }
                blockMap[x][y] = blocks == 4;                
            }
        }
       blockify(level, blockMap, width + 1, height + 1);
        
        //System.out.println("Entrou no fixWalls");
    }
    

    private void blockify(Level level, boolean[][] blocks, int width, int height)
    {
    	
    	boolean EditandoAqui;
    	
        int to = 0;
        if (type == LevelGenerator.TYPE_CASTLE)
        {
            to = 4 * 2;
        }
        else if (type == LevelGenerator.TYPE_UNDERGROUND)
        {
            to = 4 * 3;
        }

        boolean[][] b = new boolean[2][2];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int xx = x; xx <= x + 1; xx++)
                {
                    for (int yy = y; yy <= y + 1; yy++)
                    {
                        int _xx = xx;
                        int _yy = yy;
                        if (_xx < 0) _xx = 0;
                        if (_yy < 0) _yy = 0;
                        if (_xx > width - 1) _xx = width - 1;
                        if (_yy > height - 1) _yy = height - 1;
                        b[xx - x][yy - y] = blocks[_xx][_yy];
                    }
                }

                if (b[0][0] == b[1][0] && b[0][1] == b[1][1])
                {
                    if (b[0][0] == b[0][1])
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (1 + 9 * 16 + to)); //bloco marron do meio. Chão.
                            //System.out.println("Yes");                            
                        }
                        else
                        {
                            // KEEP OLD BLOCK!
                        }
                    }
                    else
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (1 + 10 * 16 + to));//Bloco com grama inferior
                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (1 + 8 * 16 + to)); //Bloco com grama superior
                        }
                    }
                }
                else if (b[0][0] == b[0][1] && b[1][0] == b[1][1])
                {
                    if (b[0][0])
                    {
                        level.setBlock(x, y, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
                    }
                    else
                    {
                        level.setBlock(x, y, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
                    }
                }
                else if (b[0][0] == b[1][1] && b[0][1] == b[1][0])
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16 + to)); //Bloco todo marron. Chão sem grama.                    
                }
                else if (b[0][0] == b[1][0])
                {
                    if (b[0][0])
                    {
                        if (b[0][1])
                        {
                            level.setBlock(x, y, (byte) (3 + 10 * 16 + to)); //Pequena quina inferior direita.                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (3 + 11 * 16 + to)); //Pequena quina inferior esquerda                            
                        }
                    }
                    else
                    {
                        if (b[0][1])
                        {
                            level.setBlock(x, y, (byte) (2 + 8 * 16 + to)); //Quina superior direita. Inicio de um buraco                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco                             
                        }
                    }
                }
                else if (b[0][1] == b[1][1])
                {
                    if (b[0][1])
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita                            
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (3 + 8 * 16 + to)); //Pequena quina superior esquerda 
                        }
                    }
                    else
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (2 + 10 * 16 + to)); //Quina inferior direita. Oposto a um buraco
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (0 + 10 * 16 + to)); //Quina inferior esquerda. Oposto a um buraco
                        }
                    }
                }
                else
                {
                    level.setBlock(x, y, (byte) (0 + 1 * 16 + to)); //Blocos amarelos                    
                }
            }
        }
        
        //System.out.println("Entrou no blockify");
    }
    
    public void corrigeFase(Level level){
    	int to = 0;
        if (type == LevelGenerator.TYPE_CASTLE)
        {
            to = 4 * 2;
        }
        else if (type == LevelGenerator.TYPE_UNDERGROUND)
        {
            to = 4 * 3;
        }
        
    	int yTela1 = 0, yTela2 = 0;
    	Point p;
    	for(int z=0; z < level.listaInicioFimTelas.size()-1; z++){
	    	p = level.listaInicioFimTelas.get(z);
	    	int xTela1 = (int) p.getY();
	    	p = level.listaInicioFimTelas.get(z + 1);
	    	int xTela2 = (int) p.getX();
	    	
	    	
	    	//System.out.println("\nPasso: " + z + " xTela1: " + xTela1 + " xTela2: " + xTela2);
	    	for(int y = level.getHeight(); y > 0; y--){
	    		if(level.getBlock(xTela1, y) == (byte) (1 + 8 * 16 + to)){ //Bloco com grama superior
	    			yTela1 = y;
	    			//System.out.println("\tEntrou no primeiro if do for. y = " + y);
	    		}
	    		if(level.getBlock(xTela2, y) == (byte) (1 + 8 * 16 + to)){ //Bloco com grama superior
	    			yTela2 = y;
	    			//System.out.println("\tEntrou no segundo if do for. y = " + y);
	    		}
	    	}
	    	
	    	level.setBlock(xTela2 - 1, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    		level.setBlock(xTela2 - 2, 5, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
    		
    		
    		if(yTela1 == 0){
    			
    			for(int y = level.getHeight(); y > 0; y--){
    		    	if(level.getBlock(xTela1, y) == (byte) (0 + 8 * 16 + to)) //Quina superior esquerda. Outro lado do buraco
    		    	{
    		    		yTela1 = y; 
    		    		//System.out.println("\tEntrou no if(yTela1 == 0) e atualizou yTela1 para " + y);
    		    	}
    		    	if(level.getBlock(xTela2 + 1, y) == (byte) (1 + 8 * 16 + to)){ //Bloco com grama superior
    	    			yTela2 = y;
    	    			//System.out.println("\tEntrou no if(yTela1 == 0) e atualizou yTela2 para" + y);
    	    		}
    			}
    			level.setBlock(xTela2, yTela2, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita
	    		for(int i = yTela1 + 1; i < yTela2; i++){
	    			level.setBlock(xTela2, i, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		}
	    		level.setBlock(xTela2 , yTela1, (byte) (2 + 8 * 16 + to)); //Quina superior direita.. Outro lado do buraco
	    		//level.setBlock(xTela2 - 1 , yTela1, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		
    			yTela2 = yTela1;
    		}
    		
    		
    		    		
	    	if(yTela1 > yTela2){
	    		//System.out.println("\tCaso 1 em " + z);
	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
	    		level.setBlock(xTela2 - 1, yTela1, (byte) (3 + 8 * 16 + to)); //Pequena quina superior esquerda
	    		for(int i = yTela2 + 1; i < yTela1; i++){
	    			level.setBlock(xTela2 - 1, i, (byte) (0 + 9 * 16 + to));//Bloco com grama lateral na esquerda
	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		}
	    		level.setBlock(xTela2 - 1, yTela2, (byte) (0 + 8 * 16 + to)); //Quina superior esquerda. Outro lado do buraco
	    		//level.setBlock(xTela2 - 1, yTela2, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    	}
	    	if(yTela1 < yTela2){
	    		//System.out.println("\tCaso 2 em " + z);	
	    		//System.out.println("\ty1: " + yTela1 + " y2: " + yTela2);
	    		level.setBlock(xTela2-1, yTela2, (byte) (3 + 9 * 16 + to));  //Pequena quina superior direita
	    		for(int i = yTela1 + 1; i < yTela2; i++){
	    			level.setBlock(xTela2 - 1, i, (byte) (2 + 9 * 16 + to));//Bloco de Grama lateral na direita
	    			//level.setBlock(xTela2 - 1, i, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    		}
	    		level.setBlock(xTela2 - 1 , yTela1, (byte) (2 + 8 * 16 + to)); //Quina superior direita.. Outro lado do buraco
	    		//level.setBlock(xTela2 - 1 , yTela1, (byte) (0 + 1 * 16 + to)); //Blocos amarelos
	    	}
    	}
    	
    }
    
    private void salvaInfoTela(Level level){
    	
    	InformacoesTelas info = new InformacoesTelas();
        info.setQuantBuracos(cont_buracos);
        info.setQuantCanhoes(cont_canhoes);
        for(int i = 0; i < level.tam_spritePrimitivo; i++)
        	info.addInimigo(level.spritePrimitivo[i]);        
        
        try {
			info.salvaInfoTela("infoTela2", info);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    private Level conectaTelas(Level level){
    	
    	ArrayList<String> nomeTelas = new ArrayList<String>();
    	ConectorDeTelas fase = new ConectorDeTelas();	
    	fase.lerArquivosDiretorio(nomeTelas);
    	
    	System.out.print("Fase: ");
    	for(int i=0; i< nomeTelas.size(); i++)
    		System.out.print(nomeTelas.get(i) + " ");
    	System.out.println();
    	
    	ArrayList<Level> listaTelas = new ArrayList<Level>();
    	String diretorio = "";
    	
    	FileInputStream fis;
    	
		try {
			for(int i=0; i < nomeTelas.size(); i++){
				diretorio = "telas/" + nomeTelas.get(i);
				System.out.println(diretorio);
				fis = new FileInputStream(diretorio);
				DataInputStream dis = new DataInputStream(fis);
				//System.out.println("\n\tRetornando Tela");
				//Level level2 = Level.load(dis);
				listaTelas.add(Level.load(dis));
			}	
			return fase.conectaTelas(listaTelas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		
    	
        	
    		
    		//***************************************************
        	return level;
        	
        
    }
    
    
    
    private Level retornaTela(Level level, String nomeTela){    	
    	
    	FileInputStream fis;
		try {
			fis = new FileInputStream(nomeTela);
			DataInputStream dis = new DataInputStream(fis);			
			level = Level.load(dis);
			
			System.out.println("\nCARREGADO COM SUCESSO\n\tRetornando Tela");
			return level;
			
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		System.out.println("\nFALHOU AO CARREGAR\n\tRetornando");
		return level;
		
    }
    
    private void salvaTela(Level tela, String nome){
    	//nome = nome+".tel";
    	FileOutputStream fos;
		try {
			fos = new FileOutputStream(nome);
			DataOutputStream dos = new DataOutputStream(fos);
			level.save(dos);
			System.out.println("\n\tFase salva com sucesso");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private Level controiLevel(Level level){
    	
    	ArrayList<String> nomeTelas = new ArrayList<String>();
    	MedidorDeDificuldade medidor = new MedidorDeDificuldade();
    	try {
			medidor.montaTabelaDificuldade();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	System.out.print("FASE: ");
		for (int i=0; i< medidor.getQuantidadeTelas(); i++){
			nomeTelas.add(medidor.retornaTelaDoBalde(i));
			System.out.print(nomeTelas.get(i) + " ");
		}
    	System.out.println();
    	
    	ConectorDeTelas fase = new ConectorDeTelas();	
    	//fase.lerArquivosDiretorio(nomeTelas);
    	ArrayList<Level> listaTelas = new ArrayList<Level>();
    	String diretorio = "";
    	
    	FileInputStream fis;
    	
		try {
			for(int i=0;i < nomeTelas.size(); i++){
				diretorio = "telasEinfo/Telas/" + nomeTelas.get(i);
				fis = new FileInputStream(diretorio);
				DataInputStream dis = new DataInputStream(fis);
				//System.out.println("\n\tRetornando Tela");
				//Level level2 = Level.load(dis);
				listaTelas.add(Level.load(dis));
			}	
			return fase.conectaTelas(listaTelas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		
    	
        	
    		
    		//***************************************************
        	return level;
        	
        
    }
    
    private Level controiLevelPorFuncao(Level level, EquacaoFases equacaoPrincipal){
    	
    	ArrayList<String> nomeTelas = new ArrayList<String>();
    	MedidorDeDificuldade medidor = new MedidorDeDificuldade();
    	
    	try {
			//medidor.montaTabelaDificuldade();
    		medidor = medidor.carregaTabelaTelas("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		    	
    	
    	int dificuldade;
    	//ClassEquacoes funcao = new ClassEquacoes();
    	//EquacaoFases equacao = new Parabola(-1/8, 3/3, 3);  /*-x^2/8 + 3x//2 + 3 */ equacaoPrincipal
    	EquacaoFases equacao = equacaoPrincipal;
    	
    	System.out.print("FASE: ");
		for (int i=0; i< medidor.getQuantidadeTelas(); i++){
			dificuldade = (int)equacao.resultadoFuncao(i);
			nomeTelas.add(medidor.retornaTelaEspecifica(dificuldade, nomeTelas));
			System.out.print(nomeTelas.get(i) + " ");
		}
    	System.out.println();
    	
    	ConectorDeTelas fase = new ConectorDeTelas();	
    	//fase.lerArquivosDiretorio(nomeTelas);
    	ArrayList<Level> listaTelas = new ArrayList<Level>();
    	String diretorio = "";
    	
    	FileInputStream fis;
    	
		try {
			for(int i=0;i < nomeTelas.size(); i++){
				diretorio = "telasEinfo/Telas/" + nomeTelas.get(i);
				fis = new FileInputStream(diretorio);
				DataInputStream dis = new DataInputStream(fis);
				//System.out.println("\n\tRetornando Tela");
				//Level level2 = Level.load(dis);
				listaTelas.add(Level.load(dis));
			}	
			return fase.conectaTelas(listaTelas);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		
    	
        	
    		
    		//***************************************************
        	return level;
        	
        
    }

}
