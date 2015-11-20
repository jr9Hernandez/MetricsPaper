package pet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 *	Esta classe equeivale aos Tubos no mapa
 *
 * Os tudos além das coordenadas ainda possuem um altura
*/
public class TubeMap extends ItensMapa{
	
	private int altura;
	
	public void setAltura(int altura){
		
		this.altura = altura;
	}
	
	public int getAltura(){
		
		return this.altura;
	}
	
public void SalvaArquivo(File arquivo){
		
		int x = this.coordenadas.getX();
		int y = this.coordenadas.getY();
		
		FileWriter fw;
		try {
			fw = new FileWriter(arquivo, true);
			fw.write( x + "\t" + y + "\t" + this.altura+ "\n");		
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
