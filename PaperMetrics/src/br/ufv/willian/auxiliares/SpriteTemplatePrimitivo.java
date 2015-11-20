package br.ufv.willian.auxiliares;

import java.io.Serializable;
import java.util.Random;

public class SpriteTemplatePrimitivo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private int winged;
	private int x;
	private int y;
	
	public int getType() {
		Random rand = new Random();
		if(type == 3){
			if(winged == 1)
				type = rand.nextInt(3);
			else
				type = rand.nextInt(4);
			
			//System.out.println("Era 3, agora é " + type);
		}
			
		return type;
	}

	public int getWinged() {
		if(type == 3){
			//System.out.println("Alterado");
			return 0;
		}
		Random r = new Random();
		
		if(winged == 1)
			if(r.nextBoolean()){
				//System.out.println("Alterado");
				return 0;
			}		
			
		
		return winged;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public SpriteTemplatePrimitivo(int x, int y, int tipo, boolean alado){
		type =  tipo;
		winged = alado ? 1 : 0;
		this.x = x;
		this.y = y;
	}
	
	

}
