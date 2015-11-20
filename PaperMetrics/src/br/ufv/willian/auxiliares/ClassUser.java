package br.ufv.willian.auxiliares;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class ClassUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user = null;
	private int completo = 0;
	
	public ClassUser carregaUser() throws IOException, ClassNotFoundException{		
		
		//System.out.print("Carregando...");
		//1 - Crie um objeto FileInputStream
		FileInputStream fis = new FileInputStream("user.txt");
		 //2 - Crie um ObjectInputStream
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ClassUser usuario   = (ClassUser) obj;
		ois.close();
		//System.out.println(" Pronto.");
		 
		return usuario;
	}
	
	public void salvaUser() throws IOException, ClassNotFoundException{		
		
		//System.out.print("Salvando...");
		FileOutputStream fos = new FileOutputStream("user.txt");
	    ObjectOutputStream os = new ObjectOutputStream(fos); 
	    os.writeObject(this);
	    os.close();	
	    //System.out.println(" Pronto.");
		
	}
	
	public void setUser(String user){
		this.user = user;		 
	}
	
	public String getUser(){		
		return user;
	}
	
	public void geraUser(){
		if(user == null){
			Calendar calendar = Calendar.getInstance();
        	int ano = calendar.get(Calendar.YEAR);
        	int mes = calendar.get(Calendar.MONTH);
        	int dia  = calendar.get(Calendar.DAY_OF_MONTH);
    		int hour = calendar.get(Calendar.HOUR_OF_DAY);     
    		int minute = calendar.get(Calendar.MINUTE);     
    		int second = calendar.get(Calendar.SECOND);
    		int mili = calendar.get(Calendar.MILLISECOND);
    		
    		Random randon = new Random();
    		
    		user = "user" + randon.nextInt() + "-" + dia + "-" + mes + "-" + 
            		ano + "-" + hour + minute + second + mili;  		
    		
		}
	}
	
	public int getCompleto(){
		return completo;
	}
	/**
	 * 1 para completo, 0 para não
	 * @param completo
	 */
	public void setCompleto(int completo){
		this.completo = completo;
	}
	
	/*
	public static void main(String agrs[]){
		ClassUser usuario = new ClassUser();
		try {
			usuario.salvaUser();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("FIM");
	}*/

}
