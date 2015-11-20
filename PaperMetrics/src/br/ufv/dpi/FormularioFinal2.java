package br.ufv.dpi;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormularioFinal2 {
	
	public String idioma;
	
	public void addComponentToPane(Container pane) {
		
		if(idioma == "Ingles"){
			//Begin Pane Label 1
			JPanel labelPane = new JPanel();
			JLabel label;
			label = new JLabel("Rate 0-9 for each criterion");		
			labelPane.add(label);
			//End Pane Label 1
			
			//Begin Pane Label2
			JPanel labelPane2 = new JPanel();
			JLabel label2;
			label2 = new JLabel("Where: 0 means totally unsatisfied and 9 totally satisfied");
			labelPane2.add(label2);
			//End Label2
		}
		else{
			//Begin Pane Label 1
			JPanel labelPane = new JPanel();
			JLabel label1;
			label1 = new JLabel("Dê uma nota de 0 a 9 para cada critério");			
			labelPane.add(label1);
			//End Pane Label 1
			
			//Begin Pane Label2
			JPanel labelPane2 = new JPanel();
			JLabel label2;
			label2 = new JLabel("Onde : 0 equivale a totalmente insatisfeito e 9 totalmente satisfeito");
			labelPane2.add(label2);
			//End Label2
			
			//Inicio Painel de Diversao
			ClassRadioButtons diveraoPane = new ClassRadioButtons("Diversão: ");			
			//Fim do Painel de Diversao
			
			//Inicio do Painel de Frustacao
			ClassRadioButtons frustacaoPane = new ClassRadioButtons("Frustrante: ");
			//Fim do Painel de Frustração
			
			//Inicio do Painel de Desafiador
			ClassRadioButtons desafiadorPane = new ClassRadioButtons("Desafiante: ");
			//Fim do Painel de Desafiador
			
			//Inicio do Painel de Dificuldade
			ClassRadioButtons dificuldadePane = new ClassRadioButtons("Dificuldade: ");
			//Fim do Painel de Dificuldade
			
			//Inicio do Painel de opiniao sobre desenvolvedor
			ClassRadioButtons desenvolvidoPane = new ClassRadioButtons("Acha que foi desenvolvido por humano? ");
			//Fim do Painel de opiniao sobre desenvolvedor
			
			//Inicio do Painel de Jogaria novamente
			ClassRadioButtons jogariaNovamentePane = new ClassRadioButtons("Jogaria Novamente?");
			//Fim do Painel de jogaria novamente
			
			//Inicio do Painel de sugestoes
			JPanel sugestoesPane = new JPanel();
			//Fim do Painel de sugestoes
			
			pane.add(labelPane, BorderLayout.PAGE_START);
			pane.add(labelPane2);
			//pane.add(diveraoPane, BorderLayout.AFTER_LAST_LINE);
			//pane.add(frustacaoPane, BorderLayout.AFTER_LAST_LINE);
			//pane.add(desafiadorPane);
			//pane.add(dificuldadePane);
			//pane.add(desenvolvidoPane);
			//pane.add(jogariaNovamentePane);
			pane.add(sugestoesPane, BorderLayout.PAGE_END);
			/*
			pane.add(labelPane, BorderLayout.PAGE_START);
	        pane.add(comboBoxPane, BorderLayout.CENTER);
	        pane.add(buttonPane, BorderLayout.PAGE_END); 
			*/	
		}
	}
	
	public static void main(String args[]){
		
		JFrame frame; 
		DadosFormulario dadosForm = DadosFormulario.getInstancia();
		FormularioFinal2 form = new FormularioFinal2();
		form.idioma = dadosForm.getIdioma();
		
		if(form.idioma == "Ingles"){
			frame = new JFrame("The questionnaire on the Game");
		}else{			
			frame = new JFrame("Questionário sobre o Jogo");
		}
		
		frame.setSize(550, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setTitle("Experimento");
        //setSize(550, 450);  
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        
        //Create and set up the content pane.
        
        form.addComponentToPane(frame.getContentPane());
        
        //Display the window.
        //frame.pack();
        frame.setVisible(true);
		//new FormularioLingua().setVisible(true);
	}

}
