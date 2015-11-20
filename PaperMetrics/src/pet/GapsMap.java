package pet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Classe que representa os buracos
 * 
 *  Além da coordenadas, buracos possuem uma largura
*/
public class GapsMap extends ItensMapa{
	
	private int largura;
	
	public void setLargura(int largura){
		
		this.largura = largura; 
	}
	
	public int getLargura(){
		
		return largura;
	}
	
	public void SalvaArquivo(File arquivo){
		
		int x = this.coordenadas.getX();
		int y = this.coordenadas.getY();
		
		FileWriter fw;
		try {
			fw = new FileWriter(arquivo, true);
			fw.write( x + "\t" + y + "\t" + this.largura + "\n");		
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
