package br.ufv.dpi;

import java.util.ArrayList;

import javax.swing.JFrame;

import dk.itu.mario.engine.MarioComponent;

public class Controle {
	
	public static Controle controlador;
	private JFrame formularioFinal, game;
	
	public static Controle getInstancia(){
		if(controlador == null)
			controlador = new Controle();
		return controlador;
	}
	
	public void InciarJogo(){
		//game = new JFrame("Mario Experience Showcase");
		DadosFormulario dados = DadosFormulario.getInstancia();
		if(dados.getIdioma() == "Ingles")
			game = new JFrame("Automatic Content Generation");
		else
			//game = new JFrame("Mario MiniFase");
			game = new JFrame("Geração Automática de Fases");
		
		MarioComponent mario = new MarioComponent(640, 480, true);
    	//MarioComponent mario = new MarioComponent(640, 480, false);
    	//mario.setJFrames(this.game, this.form);
    	
    	//ArrayList<JFrame> listaFrames = new ArrayList<JFrame>();
    	formularioFinal = new FormularioFinal();
    	//listaFrames.add(game);
    	//listaFrames.add(formularioFinal);
    	
    	mario.setJFrames(this.game, formularioFinal);
    	//mario.setJFrames(listaFrames);

    	game.setContentPane(mario);
    	game.setResizable(false);
    	game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	game.pack();
    	game.setLocationRelativeTo(null); //Centraliza Frame 
    	game.setVisible(true);

        mario.start();
	}  
}