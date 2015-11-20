package br.ufv.julian;

import dk.itu.mario.level.Level;

public class Metric3 {

	private int width;
	private int height;
	Level level;
	int counterHills;
	double sumaHills=0;
	double avgHills=0;
	
public Metric3(int width, int height, Level level) {
		
		this.width = width;
		this.height = height;
		this.level = level;
	}

public double Metric3M(){

	countOtherElements(level.getMap());
	return avgHills;
}

public void countOtherElements(byte[][] array) {
	for (int i = 0; i < width; i++) {
		counterHills=0;
		for (int j = 0; j < array[i].length; j++) {
			
			if (array[i][j] == (byte) (2+8*16)) {// RIGHT_UP_GRASS_EDGE
				counterHills=counterHills+1;				
			}
			else if (array[i][j] == (byte) (0+8*16)) {// LEFT_UP_GRASS_EDGE
				counterHills=counterHills+1;				
			}
			else if (array[i][j] == (byte) (5 + 8 * 16)) {// HILL_TOP
				counterHills=counterHills+1;
			}
			else if (array[i][j] == (byte) (4 + 8 * 16)) {//HILL_TOP_LEFT
				counterHills=counterHills+1;				
			}
		    else if (array[i][j] == (byte) (6 + 8 * 16)) {// HILL_TOP_RIGHT
		    	counterHills=counterHills+1;				
			}
			else if (array[i][j] == (byte) (4 + 11 * 16)) {// HILL_TOP_LEFT_IN
				counterHills=counterHills+1;				
			}
			else if (array[i][j] == (byte) (6 + 11 * 16)) {// HILL_TOP_RIGHT_IN
				counterHills=counterHills+1;
			}
			else if (array[i][j] == (byte) (1 + 8 * 16)) {// HILL_TOP_W
				counterHills=counterHills+1;
			}
		}
		System.out.println("counterHills "+counterHills);
		sumaHills=sumaHills+counterHills;
		}
	avgHills=sumaHills/width;
	System.out.println("sum"+sumaHills);
	System.out.println("width"+width);
	System.out.println("avg"+avgHills);
	}
}
