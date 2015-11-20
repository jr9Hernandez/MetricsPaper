package pet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 *	As plataformas possuem altura e lagura 
*/
public class PlatformsMap extends ItensMapa{
	
	private int altura;
	private int largura;
	
	public void setAltura(int altura){		
		this.altura = altura;
	}
	
	public void setLargura(int largura){		
		this.largura = largura;
	}
	
	public int getAltura(){
		
		return this.altura;
	}
	
	public int getLargura(){
		
		return this.largura;
	}
	
	public void SalvaArquivo(File arquivo){
		
		int x = this.coordenadas.getX();
		int y = this.coordenadas.getY();
		
		FileWriter fw;
		try {
			fw = new FileWriter(arquivo, true);
			fw.write( x + "\t" + y + "\t" + this.largura + "\t" + this.altura+ "\n");		
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
