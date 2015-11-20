package br.ufv.willian.auxiliares;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import br.ufv.willian.Level;

//Classe para testar salvar e carregar um arquivo
public class PersistirObjeto {
	
	//private static final long serialVersionUID = 8025398001697101078L;
	
	public Level desserializar() throws IOException, ClassNotFoundException{
		
		
		//1 - Crie um objeto FileInputStream
		 FileInputStream fileStream = new FileInputStream("teste.tel");
		 //2 - Crie um ObjectInputStream
		 ObjectInputStream os = new ObjectInputStream(fileStream);
		 Object obj = os.readObject();
		 
		 Level level  = (Level) obj;
		 System.out.println("Carregado com sucesso");
			 
		
		 os.close();
		 
		 return level;
	}
	 
	public void serializar(Level level) {		
		 
	   try { //operação de E/S pode lançar excessões.
	     FileOutputStream fs = new FileOutputStream("tela.lvl");//caso não encontre cria novo arquivo chamado tela.nvl (LeVeL)
	     ObjectOutputStream os = new ObjectOutputStream(fs); //fs encadeado ao fluxo de conexão
	     os.writeObject(level);
	     os.close();
	     System.out.println("Salvo com sucesso!!!");
	   }catch (Exception e) {
	     e.printStackTrace();
	   }
	 
	 }	

}
