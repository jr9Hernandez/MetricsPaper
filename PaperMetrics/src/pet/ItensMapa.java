package pet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * Esta classe representa todos atributos e métodos que os objetos no mapa tem em comum
*/

public class ItensMapa {
	
	protected Coordenadas coordenadas; //vetor contendo as coordenadas de cada item
	

	/*
	 * Classe  para salvar as coordenadas dos objetos
	 * 
	*/
	public void setCoordenadas(int x, int y){
		
		coordenadas = new Coordenadas();
		coordenadas.setCoordenadas(x, y);
		
	}
	
	/*
	 * Classe para retornar as coordenadas 
	*/
	public Coordenadas getCoordenadas(){
		
		return this.coordenadas;
	}
	
	/*
	 * Método para salvar a quantidade de elementos em um arquivo Txt 
	*/

	public void SalvaArquivo(File arquivo){
		
		int x = this.coordenadas.getX();
		int y = this.coordenadas.getY();
		
		FileWriter fw;
		try {
			fw = new FileWriter(arquivo, true);
			fw.write( x + "\t" + y + "\n");		
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
