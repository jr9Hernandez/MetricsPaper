package br.ufv.julian;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;

import dk.itu.mario.level.Level;

public class Metric4 {

	private String plataformandgaps;
	private String enemiesItems;

	private int width;
	private int height;
	Level level;
	Level levelToCompare;
	private int tamCompresed1,tamCompresed2,tamCompresed3;

	public Metric4(int width, int height, Level level, Level levelToCompare) {

		this.width = width;
		this.height = height;
		this.level = level;
		this.levelToCompare = levelToCompare;
	}

	public double Metric4M() {

		String string1 = countOtherElements(level.getMap(), level);
		String string2 = countOtherElements(levelToCompare.getMap(),levelToCompare);

		string1 = string1 + CountenemCoins(level.getMap(), level);
		string2 = string2 + CountenemCoins(levelToCompare.getMap(),levelToCompare);
		
		String string3 = string1 + string2;

		System.out.println("string1 " + string1);
		System.out.println("string2 "+string2);
		System.out.println("string3 "+string3);

		
		 System.out.println("before compress:");
		 System.out.println("1 "+string1.length());
		 System.out.println("2 "+string2.length());
		 System.out.println("Conc "+string3.length());
		  
		 System.out.println("after compress:");
		 
		 tamCompresed1=compress(string1).length();
		 tamCompresed2=compress(string2).length();
		 tamCompresed3=compress(string3).length();
		 
		 System.out.println("1 "+tamCompresed1);
		 System.out.println("2 "+tamCompresed2);
		 System.out.println("3 "+tamCompresed3);
		 
		 return distance(tamCompresed1,tamCompresed2,tamCompresed3);
		 
	}

	public String compress(String str) {

		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes());
			gzip.close();
			return out.toString("ISO-8859-1");
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
			return "e3";
		}
	}

	public String countOtherElements(byte[][] array, Level lvl) {
		String str = "";
		int change=0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < array[i].length; j++) {

				if (array[i][j] == (byte) (2 + 8 * 16)) {// RIGHT_UP_GRASS_EDGE
					
					if(j!=change)
					{
						change=j;
						str = str + String.valueOf(2);
					}
					else
					{
						str = str + String.valueOf(1);
					}
					break;

				} else if (array[i][j] == (byte) (0 + 8 * 16)) {// LEFT_UP_GRASS_EDGE
					if(j!=change)
					{
						change=j;
						str = str + String.valueOf(2);
					}
					else
					{
						str = str + String.valueOf(1);
					}
					break;

				} else if (array[i][j] == (byte) (1 + 8 * 16)) {// HILL_TOP_W
					if(j!=change)
					{
						change=j;
						str = str + String.valueOf(2);
					}
					else
					{
						str = str + String.valueOf(1);
					}
					break;
				}
				if (j == (array[i].length) - 1) {
					change=0;
					str = str + String.valueOf(0);
				}
			}

		}

		return str;
	}

	public String CountenemCoins(byte[][] array, Level lvl) {

		int caractertoSet = 0;
		String str = "";
		boolean enemy;
		boolean coin;
		boolean blocks;
		for (int i = 0; i < width; i++) {
			enemy = false;
			coin = false;
			blocks = false;
			for (int j = 0; j < array[i].length; j++) {

				if ((level.getSpriteTemplate(i, j) != null) && enemy == false) {// existence
																				// of enemy																				// enemy
					enemy = true;

				}
				if (array[i][j] == (byte) (0 + 1 * 16) && blocks == false) {// BLOCK_EMPTY
					blocks = true;

				} else if (array[i][j] == (byte) (4 + 2 + 1 * 16) && blocks == false) {// BLOCK_POWERUP
					blocks = true;
					
				} else if (array[i][j] == (byte) (4 + 1 + 1 * 16) && blocks == false) {// BLOCK_COIN
					blocks = true;
				}
				if (array[i][j] == (byte) (2 + 2 * 16) && coin == false) {// COIN
					coin = true;
				}

			}

			if (enemy == true && blocks == false && coin == false) {
				str = str + String.valueOf(1);
			}
			else if (enemy == false && blocks == true && coin == false) {
				str = str + String.valueOf(2);
			}
			else if (enemy == false && blocks == false && coin == true) {
				str = str + String.valueOf(3);
			}
			else if (enemy == true && blocks == true && coin == false) {
				str = str + String.valueOf(4);
			}
			else if (enemy == true && blocks == false && coin == true) {
				str = str + String.valueOf(5);
			}
			else if (enemy == false && blocks == true && coin == true) {
				str = str + String.valueOf(6);
			}
			else if (enemy == true && blocks == true && coin == true) {
				str = str + String.valueOf(7);
			}
			else if (enemy == false && blocks == false && coin == false) {
				str = str + String.valueOf(0);
			}
		}

		return str;
	}

	public double distance(int str1, int str2, int srt3 ) {
		 // Determine C(xy)-min{C(x),C(y)}
        double max_C;
        if (str1 > str2)
        {
        	max_C = srt3 - str2;
        }
        else
        {
        max_C = srt3 - str1;
        }

        // Determine max{C(x), C(y)}           
        double max_Cx_Cy;
        if (str1 > str2)
        {
                 max_Cx_Cy = str1;
        }
        else
        {
        max_Cx_Cy = str2;
        }

        // NCD =  C(xy)-min{C(x),C(y)} / max{C(x), C(y)}
        double NCD = max_C /max_Cx_Cy;
        
        System.out.println(NCD);
		return NCD;
	}

}
