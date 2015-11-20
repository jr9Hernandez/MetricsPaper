package br.ufv.julian;

import java.util.Arrays;

import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SimpleLinearRegression;
import br.ufv.willian.LevelGenerator;
import br.ufv.willian.auxiliares.Parabola;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.level.Level;

public class Metrics extends LevelGenerator {

	public Metrics(int width, int height, Level level) {
	
		this.width = width;
		this.height = height;
		this.level = level;
		this.width =AdjustWidth(width);
		//Metric2 objMetric2= new Metric2();
	}
	
	public Metrics(){}


	
	public double MetricsCalc(int metric, Level toCompare) {

		/*		
		Level leveltoCompare = new Level();
		try {
			leveltoCompare=level.clone();
			leveltoCompare=toCompare;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

    	leveltoCompare.setSpriteTemplate(0, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(6, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setSpriteTemplate(12, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(18, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(24, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(32, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setSpriteTemplate(38, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(40, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(48, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(49, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setSpriteTemplate(56, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(60, 6, new SpriteTemplate(SpriteTemplate.CANNON_BALL, true));
    	leveltoCompare.setSpriteTemplate(61, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setSpriteTemplate(62, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setSpriteTemplate(63, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setSpriteTemplate(64, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setSpriteTemplate(65, 6, new SpriteTemplate(SpriteTemplate.GREEN_TURTLE, true));
    	leveltoCompare.setMap(0, 8, (byte) (0 + 1 * 16));
    	leveltoCompare.setMap(6, 8, (byte) (0 + 1 * 16));
    	leveltoCompare.setMap(12, 8, (byte) (0 + 1 * 16));
    	leveltoCompare.setMap(14, 8, (byte) (0 + 1 * 16));
    	leveltoCompare.setMap(16, 8, (byte) (0 + 1 * 16));
    	leveltoCompare.setMap(18, 8, (byte) (0 + 1 * 16));	*/
		
		switch (metric) {
		case 1:
			Metric1 objMetric1= new Metric1(width, height, level);
			return objMetric1.Metric1M();
		case 2:
			Metric2 objMetric2= new Metric2(width, height, level);
			return objMetric2.Metric2M();
		case 3:
			Metric3 objMetric3= new Metric3(width, height, level);
			return objMetric3.Metric3M();		
	    case 4:
		    Metric4 objMetric4= new Metric4(width, height, level, toCompare);
		    return objMetric4.Metric4M();

		}
		return 0;
	}



	private int AdjustWidth(int width) {
		int last=0;
		for (int i = width-1; i >= 0; i--) {
			
			
				if(level.getMap()[i][height-1]!= (byte) (0))
				{
					break;
				}
				else
				{
					last++;
				}
				

		}
		//System.out.println("chamuzer"+last);
		return width-last;
	}




}
