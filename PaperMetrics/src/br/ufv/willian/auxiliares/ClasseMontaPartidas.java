package br.ufv.willian.auxiliares;

import java.util.ArrayList;
import java.util.Collections;

public class ClasseMontaPartidas {
	
	
	public ArrayList<ClassePartidas> retornaPartidas(ArrayList<String> fases){
		Collections.shuffle(fases);
		ArrayList<ClassePartidas> partidas = new ArrayList<ClassePartidas>();
				  
	        if (fases.size() % 2 == 1) {  
	            fases.add(0, "");  
	        }  
	  
	        int t = fases.size();  
	        int m = fases.size() / 2;  
	        for (int i = 0; i < t - 1; i++) {   
	            for (int j = 0; j < m; j++) {  
	                //Clube está de fora nessa rodada?                
	                if (fases.get(j).isEmpty())  
	                    continue;  
	  
	                //Teste para ajustar o mando de campo  
	                if (j % 2 == 1 || i % 2 == 1 && j == 0) {
	                    //System.out.print(fases.get(t - j - 1) + " x " + fases.get(j) + "   ");
	                	//partida = new ClassePartidas(Integer.parseInt(fases.get(t - j - 1)) ,Integer.parseInt(fases.get(j)));
	                	partidas.add(new ClassePartidas(Integer.parseInt(fases.get(t - j - 1)) ,Integer.parseInt(fases.get(j))));
	                	partidas.add(new ClassePartidas(Integer.parseInt(fases.get(j)), Integer.parseInt(fases.get(t - j - 1))));
	                	//partidas.add(new ClassePartidas(fases.get(t - j - 1) ,fases.get(j)));
	                }
	                else {
	                    //System.out.print(fases.get(j) + " x " + fases.get(t - j - 1) + "   "); 
	                	partidas.add(new ClassePartidas(Integer.parseInt(fases.get(j)), Integer.parseInt(fases.get(t - j - 1))));
	                	partidas.add(new ClassePartidas(Integer.parseInt(fases.get(t - j - 1)) ,Integer.parseInt(fases.get(j))));
	                	//partidas.add(new ClassePartidas(fases.get(j), fases.get(t - j - 1)));
	                }
	            }  
	            
	            //Gira os clubes no sentido horário, mantendo o primeiro no lugar  
	            fases.add(1, fases.remove(fases.size()-1));  
	        }
	        
	        return partidas;
	}
	
	public static void main(String [] args){
		
		ClasseMontaPartidas obj = new ClasseMontaPartidas();
		ArrayList<ClassePartidas> partidas;
		ArrayList<String> array = new ArrayList<String>();
		ClassePartidas partida;
		
		array.add("0");
		array.add("1");
		array.add("2");
		array.add("3");
		array.add("4");
		array.add("5");
		
		for(int i = 0; i < 5; i++){
			//Collections.shuffle(array);
			partidas = obj.retornaPartidas(array); 
			System.out.println("RODADA " + i);
			for(int j = 0; j < partidas.size(); j++){
				partida = partidas.get(j);
				System.out.println(partida.fase1 + " vs " + partida.fase2);
			}
			System.out.println();
		
		}
		
		
	}

}
