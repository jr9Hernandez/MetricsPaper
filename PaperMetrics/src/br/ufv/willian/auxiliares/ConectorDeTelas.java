package br.ufv.willian.auxiliares;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import dk.itu.mario.level.Level;

public class ConectorDeTelas {
	
	private Level level;
	private int largura;
	private int altura;
	
	public Level conectaTelas(Level level1, Level level2){
		//Level l1 = level1;
		//Level l2 = level2;
		ArrayList<Level> lista = new ArrayList<Level>();
		lista.add(level1);
		lista.add(level2);
		lista.add(level1);
		//lista.add(l2);
		
		return conectaTelas(lista);
		
		/*
		altura = level1.height;
		largura = level1.width + level2.width;
		level = new Level(largura, altura);
		level.map = new byte[largura][altura];
        level.data = new byte[largura][altura];
		
        
		for(int i=0; i < level1.xExit; i++){
			for(int j=0; j < level1.height; j++){
				
				level.map[i][j] = level1.map[i][j];
				level.data[i][j] = level1.data[i][j];
				level.spriteTemplates[i][j] = level1.spriteTemplates[i][j];
			}
		}
		
		for(int i=level1.xExit; i < level1.xExit + level2.width; i++){
			for(int j=0; j < level2.height; j++){
				
				level.map[i][j] = level2.map[i-level1.xExit][j];
				level.data[i][j] = level2.data[i-level1.xExit][j];
				level.spriteTemplates[i][j] = level2.spriteTemplates[i-level1.xExit][j];
			}
		}
		
		level.xExit = level1.xExit + level2.xExit;
		level.yExit = level2.yExit;
		*/
		
		//return level;
	}
	
	public Level conectaTelas(ArrayList<Level> listaTelas){	
		
		
		Level telaCorrente = listaTelas.get(0); 
		
		largura = listaTelas.size() * telaCorrente.getWidth(); 
		
		altura = telaCorrente.getHeight();
		level = new Level(largura, altura); //Cria-se um um novo level
		int indice_level = 0; //Guarda o indice de onde a fase deve começar a ser preenchida
				
		int inicioTela = 0, FimTela = 0;
		int limite = 0; //valor máximo de x a ser adicionado a matriz principal
		
		//Tela zero
		
		//limite = telaCorrente.getxExit() - 10; //15-10-14
		limite = telaCorrente.getxExit() - 9; //lenght + 8
		for(int i = indice_level, z = 1; z <= limite; i++, z++){ 
			for(int j=0; j < telaCorrente.getHeight(); j++){
				
				//level.map[i][j] = telaCorrente.map[z][j];
				level.setMap(i, j ,telaCorrente.getMap(z, j));
				level.data[i][j] = telaCorrente.data[z][j];
				//level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[z][j];
				//level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j));
				level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j), telaCorrente.getspritePrimitivo(z));
			}
			inicioTela = indice_level;
			FimTela = z + indice_level - 1;
		}	
		Point inicioFim = new Point(inicioTela, FimTela);
		level.listaInicioFimTelas.add(inicioFim);			
		
		//level.xExit = telaCorrente.xExit  + inicio;
		//level.yExit = telaCorrente.yExit;
		level.setxExit(telaCorrente.getxExit() + indice_level);
		level.setyExit(telaCorrente.getyExit());
		//inicio += limite; 
		indice_level += limite;
		indice_level--;
		
		
		telaCorrente = listaTelas.get(1);
		
		//Fim Tela zero
		//int aux = 0;
		for (int l = 1; l < listaTelas.size(); l++) {
			//limite = telaCorrente.xExit - 10;
			//limite = telaCorrente.getxExit() - 10; //15-10-2014
			limite = telaCorrente.getxExit() - 9; //lenght + 8
			//for(int i = inicio, z = 11; z <= limite; i++, z++){  //15-10-2014
			for(int i = indice_level + 1, z = 10; z <= limite; i++, z++){ //z começa em 10, pq é o tamanho da entrada de cada tela
				for(int j=0; j < telaCorrente.getHeight(); j++){
					
					//level.map[i][j] = telaCorrente.map[z][j];
					level.setMap(i, j ,telaCorrente.getMap(z, j));
					//level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[z][j];
					//level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j));
					level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j), telaCorrente.getspritePrimitivo(z));
				}
				inicioTela = indice_level + 1;
				//FimTela = z + inicio - 11;//15-10-14 			
				FimTela = z + indice_level - 10;
			}	
			inicioFim = new Point(inicioTela, FimTela);
			level.listaInicioFimTelas.add(inicioFim);			
			
			//level.xExit = telaCorrente.xExit  + inicio;
			//level.yExit = telaCorrente.yExit;
			level.setxExit(telaCorrente.getxExit() + indice_level - 10);
			level.setyExit(telaCorrente.getyExit());
			indice_level += (limite - 10);//inicio += (limite - 11);
			//inicio = FimTela;
			
			if(l < listaTelas.size()-1){
				telaCorrente = listaTelas.get(l+1);
			}else{
				 
				//for(int y = limite - 1, i = level.getxExit() - 22; y < telaCorrente.getWidth() ; i++, y++){ //15-10-14
				for(int y = limite, i = level.getxExit() - 8; y < telaCorrente.getWidth() ; i++, y++){ 
					for(int j=0; j < telaCorrente.getHeight(); j++){
						
						//level.map[i][j] = telaCorrente.map[y][j];
						level.setMap(i, j ,telaCorrente.getMap(y, j));
						level.data[i][j] = telaCorrente.data[y][j];
						//level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[y][j];
						//level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(y, j));
						level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(y, j), telaCorrente.getspritePrimitivo(y));
					}
				}
				//level.xExit += 5;
			}
			
		}
		
		
		
		
		
		
		/*
		//Alterei para adaptar a nova realidade mas nao funcionou direito. Alterações em 15-10-14
		Level telaCorrente = listaTelas.get(0); 
		
		largura = listaTelas.size() * telaCorrente.getWidth(); 
		
		altura = telaCorrente.getHeight();
		level = new Level(largura, altura); //Cria-se um um novo level
		int inicio = 0; //Guarda o indice de onde a fase deve começar a ser preenchida
				
		int inicioTela = 0, FimTela = 0;
		int limite = 0; //valor máximo de x a ser adicionado a matriz principal
		
		//Tela zero
		
		limite = telaCorrente.getxExit() - 10;
		for(int i = inicio, z = 1; z <= limite; i++, z++){ 
			for(int j=0; j < telaCorrente.getHeight(); j++){
				
				//level.map[i][j] = telaCorrente.map[z][j];
				level.setMap(i, j ,telaCorrente.getMap(z, j));
				level.data[i][j] = telaCorrente.data[z][j];
				//level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[z][j];
				//level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j));
				level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j), telaCorrente.getspritePrimitivo(z));
			}
			inicioTela = inicio;
			FimTela = z + inicio - 1;
		}	
		Point inicioFim = new Point(inicioTela, FimTela);
		level.listaInicioFimTelas.add(inicioFim);			
		
		//level.xExit = telaCorrente.xExit  + inicio;
		//level.yExit = telaCorrente.yExit;
		level.setxExit(telaCorrente.getxExit() + inicio);
		level.setyExit(telaCorrente.getyExit());
		//inicio += limite; 
		inicio += limite;
		
		
		telaCorrente = listaTelas.get(1);
		
		//Fim Tela zero
		//int aux = 0;
		for (int l = 1; l < listaTelas.size(); l++) {
			//limite = telaCorrente.xExit - 10;
			//limite = telaCorrente.getxExit() - 10; //15-10-2014
			limite = telaCorrente.getxExit() - 8;
			//for(int i = inicio, z = 11; z <= limite; i++, z++){  //15-10-2014
			for(int i = inicio + 1, z = 11; z <= limite; i++, z++){
				for(int j=0; j < telaCorrente.getHeight(); j++){
					
					//level.map[i][j] = telaCorrente.map[z][j];
					level.setMap(i, j ,telaCorrente.getMap(z, j));
					//level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[z][j];
					//level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j));
					level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(z, j), telaCorrente.getspritePrimitivo(z));
				}
				inicioTela = inicio;
				//FimTela = z + inicio - 11;//15-10-14 			
				FimTela = z + inicio - 10;//14-10-14
			}	
			inicioFim = new Point(inicioTela, FimTela);
			level.listaInicioFimTelas.add(inicioFim);			
			
			//level.xExit = telaCorrente.xExit  + inicio;
			//level.yExit = telaCorrente.yExit;
			level.setxExit(telaCorrente.getxExit() + inicio);
			level.setyExit(telaCorrente.getyExit());
			inicio += (limite - 10);//inicio += (limite - 11);
			//inicio = FimTela;
			
			if(l < listaTelas.size()-1){
				telaCorrente = listaTelas.get(l+1);
			}else{
				 
				//for(int y = limite - 1, i = level.getxExit() - 22; y < telaCorrente.getWidth() ; i++, y++){ //15-10-14
				for(int y = limite - 1, i = level.getxExit() - 18; y < telaCorrente.getWidth() ; i++, y++){
					for(int j=0; j < telaCorrente.getHeight(); j++){
						
						//level.map[i][j] = telaCorrente.map[y][j];
						level.setMap(i, j ,telaCorrente.getMap(y, j));
						level.data[i][j] = telaCorrente.data[y][j];
						//level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[y][j];
						//level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(y, j));
						level.setSpriteTemplate(i, j, telaCorrente.getSpriteTemplate(y, j), telaCorrente.getspritePrimitivo(y));
					}
				}
				//level.xExit += 5;
			}
			
		}
		
		*/
		
		
		/*
		 * FUNCIONANDO MAS SEM CORTAR OS EXCESSOS
		 * 

		Level telaCorrente = listaTelas.get(0); //Pegar as informações da primeira Tela
		
		
		largura = listaTelas.size() * telaCorrente.width; /* Partindo do pressuposto de que todas as telas tenham 
															as mesmas características como altura e largura, 
															utilizamos essas informações da primeira tela*/
		/*
		altura = telaCorrente.height;
		level = new Level(largura, altura); //Cria-se um um novo level
		int inicio = 0; //Guarda o indice de onde a fase deve começar a ser preenchida
				
		int inicioTela = 0, FimTela = 0;
		for (int l = 0; l < listaTelas.size(); l++) {
			//O inicio em 12, para descontar a primeira reta presente em todas as fases
			//O fim em xExit - 8 para descontar os 8 a mais acrescentados para colocar o xExit no LevelGenerator
			//if(l==0){ z = 1;} else { z = 14;} //Garante a entrada ao menos na primeira tela.
			for(int i = inicio, z = 1; z  <= telaCorrente.xExit; i++, z++){ //for(int i = inicio, y = 1 ; y < telaCorrente.xExit ; i++, y++){
				for(int j=0; j < telaCorrente.height; j++){
					
					level.map[i][j] = telaCorrente.map[z][j];
					level.data[i][j] = telaCorrente.data[z][j];
					level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[z][j];
				}
				inicioTela = inicio;
				FimTela = z + inicio - 1;
			}	
			Point inicioFim = new Point(inicioTela, FimTela);
			level.listaInicioFimTelas.add(inicioFim);			
			
			level.xExit = telaCorrente.xExit  + inicio;
			level.yExit = telaCorrente.yExit;
			inicio += (telaCorrente.xExit); // O (-1) é para descontar 1 coluna de cada fase a ser acrescentada
			//telaCorrente = (Level) it.next();
			if(l < listaTelas.size()-1){
				telaCorrente = listaTelas.get(l+1);
			}else{
				 
				for(int y = telaCorrente.xExit   , i = level.xExit ; y < telaCorrente.width ; i++, y++){
					for(int j=0; j < telaCorrente.height; j++){
						
						level.map[i][j] = telaCorrente.map[y][j];
						level.data[i][j] = telaCorrente.data[y][j];
						level.spriteTemplates[i][j] = telaCorrente.spriteTemplates[y][j];
					}
				}
			}
			
		}
		*/
		
		//System.out.println("\nRetornado Level Criado!!!");
		return level;
	}
	
	public void lerArquivosDiretorio(ArrayList<String> lista){
		
		File dir = new File("telas");  //Pasta contendo as fases
		  
		String[] arquivos = dir.list();  //Array para guardar o nome de todas as fases
		if (arquivos == null) {   
			System.out.println("Diretorio vazio");
		} else { 
		    for (int i=0; i<arquivos.length; i++) { 
		        lista.add(arquivos[i]);
		    }  
		}
	}

}
