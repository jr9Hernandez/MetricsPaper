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

public class Metric5 {

	private int width;
	private int height;
	Level level;
	Level levelToCompare;
	
public Metric5(int width, int height, Level level, Level levelToCompare) {
		
		this.width = width;
		this.height = height;
		this.level = level;
		this.levelToCompare=levelToCompare;
	}

public double Metric5M(){

	String string = level.toString();
	String string2 = levelToCompare.toString();
	String string3=string+string2;
    System.out.println("after compress:");
    System.out.println(compress(string3).length());
    return 0;
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
	
}
