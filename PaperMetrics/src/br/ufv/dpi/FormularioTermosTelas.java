package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class FormularioTermosTelas extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2753025728324122658L;
	JButton button_continuar;
	JLabel label_titulo, label_conteudo;
	JCheckBox check_concordo;
	String idioma;

	public FormularioTermosTelas (){

		String titulo = "", label = "", botao = "", termos = "", check = "";	

		
			titulo = "Mario MiniFase";
			//label = "<html><h2>Sobre o Mario MiniFase</h2></html>";
			//check = "<html>Ao assinalar esta caixa, voc� est� concordando em participar do estudo. Sua participa��o � volunt�ria e voc� � livre para deixar o experimento a qualquer momento, basta fechar o jogo.</html>";
			botao = "Pr�ximo";			
			termos = //"<html>Voc� est� prestes a contribuir para um projeto de pesquisa se divertindo. A sua contribui��o � muito importante para n�s.<br>" + 
					"<html><h2>Sobre o Mario MiniFase</h2>" +
					"<br><b>O QUE ESTAMOS FAZENDO:</b> Estamos estudando maneiras diferentes de cria��o autom�tica de conte�dos para jogos eletr�nicos. Neste estudo queremos sua opini�o sobre pequenas telas para o jogo do Mario.<br>" + 
					"<br><b>O QUE VOC� TER� QUE FAZER:</b> Jogar. Isto mesmo, jogar. Ap�s o jogo, voc� dever� responder um question�rio com 3 perguntas sobre a fase que acabou de jogar. Antes de come�ar o jogo, ser� apresentado alguns exemplos de marca��o, conforme esperamos que voc� fa�a sobre a fase jogada. Nestes exemplos, est�o algumas respostas que marcamos para algumas fases jogadas." +
					"<br><br>Quanto mais voc� jogar, mais estar� contribuindo conosco. <b>Obrigado.</b>"; // +
					//"<li><b>DURA��O E RISCOS: </b>Com cerca de 30 segundos voc� � capaz de jogar uma fase e responder o question�rio. N�o existem riscos associados a este estudo, pois os dados coletados s�o relacionados apenas a fase e o tema n�o � sens�vel.<b>Voc� pode deixar o experimento a qualquer momento caso queira.</b></li>" + 
					//"<li><b>PRIVACIDADE E COLETA DE DADOS:</b> Suas respostas usadas para dados estat�sticos. N�o coletamos nenhuma informa��o sobre voc� ou que possa ser usada para identificar sua identidade.</li></ul>" +  
					//"<b>CONTATO:</b> Em caso de d�vidas sobre esta pesquisa, entre em contato com: Willian Reis (willian.reis.mario@gmail.com) ou Levi Lelis (levilelis@gmail.com).</html>";
	


		setSize(640, 480);
		//JTextArea textArea = new JTextArea(termos);
		setTitle(titulo);
		label_conteudo = new JLabel(termos);
		label_conteudo.setFont(getFont());
		//label_titulo = new JLabel(label);
		check_concordo = new JCheckBox(check);
		button_continuar = new JButton(botao);


		//textArea.setLineWrap(true);
		//JScrollPane areaScrollPane = new JScrollPane(textArea);
		//areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//textArea.setEditable(false);

		//label_titulo.setBounds(230, 0, 300, 20);
		//areaScrollPane.setBounds(10, 40, 600, 330);
		label_conteudo.setBounds(20, 30, 590, 270);
		//check_concordo.setBounds(10, 375, 620, 40);
		button_continuar.setBounds(270, 320, 100, 30);

		//check_concordo.setActionCommand("checked");
		//button_continuar.setActionCommand("Jogar");

		button_continuar.addActionListener(this);
		//button_continuar.setEnabled(true);


		getContentPane().setLayout(null);
		//getContentPane().add(label_titulo);
		//getContentPane().add(areaScrollPane);
		getContentPane().add(label_conteudo);
		//getContentPane().add(check_concordo);        
		getContentPane().add(button_continuar);



	}
	public static void main(String[] args){

		final FormularioTermosTelas termos = new FormularioTermosTelas();

		termos.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  

		//Adaptador para o fechamento da janela, matando o processo  
		/*
		termos.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing (WindowEvent e)  
			{  

				if(termos.idioma == "Portugues" || termos.idioma == null){
					Object[] options = { "Sim", "N�o" };    
					int n = JOptionPane.showOptionDialog(null,    
							"Tem certeza que deseja fechar? ",    
							"Sair" , JOptionPane.YES_NO_OPTION,    
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if(n == 0){
						//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						//form.setVisible(true);
						//Controle controle = Controle.getInstancia();
						//controle.InciarJogo();
						termos.dispose() ;
					}
				}else{
					if(termos.idioma == "Ingles"){
						Object[] options = { "yes", "no" };    
						int n = JOptionPane.showOptionDialog(null,    
								"Are you sure you want to quit? ",    
								"Quit" , JOptionPane.YES_NO_OPTION,    
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if(n == 0){
							termos.dispose() ;
						}
					}
				}		

			}   
		});
		*/
		termos.setLocationRelativeTo(null);
		termos.setVisible(true);
	}

	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub

		if(ae.getSource() == button_continuar){
			//JOptionPane.showMessageDialog(null, "Clicou Jogar" ,"Confirma��o", JOptionPane.INFORMATION_MESSAGE);
			//if(check_concordo.isSelected()){
				//button_continuar.setEnabled(false);
				//JOptionPane.showMessageDialog(null, "Marcado" ,"Confirma��o", JOptionPane.INFORMATION_MESSAGE);
				FormularioInstrucoes.main(null);
				this.dispose();
			//}
			//else{
				//button_continuar.setEnabled(true);
				//JOptionPane.showMessageDialog(null, "<html><center>� necess�rio marcar a caixa de sele��o concordando com os termos para continuar</center></html>" ,"Alerta", JOptionPane.INFORMATION_MESSAGE);
			//}
		}		
	}	
}

