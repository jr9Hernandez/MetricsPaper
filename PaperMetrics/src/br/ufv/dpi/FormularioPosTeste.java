package br.ufv.dpi;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import br.ufv.willian.GeraSequenciaAleatoria;
import br.ufv.willian.auxiliares.VariaveisGlobais;

public class FormularioPosTeste extends JFrame implements ActionListener{
	
	JButton button_jogar;
	JLabel label_conteudo;
	String idioma;
	
	public FormularioPosTeste(){
		String titulo = "", botao = "", msg = "";	

		DadosFormulario dados = DadosFormulario.getInstancia();
		idioma = dados.getIdioma();
		//idioma = "Ingles";

		if(idioma == "Ingles"){
			titulo = "Automatic Content Generation";
			botao = "Play";
			//botao2 ="";
			msg =  "<html><center><h1>Practice is over - be ready for the real thing!</h1><br>" +
					"<h2>Click Play to Start.</h2></center></html>";	
			
			
		}else{
			titulo = "Geraçãoo Automática de Conteúdo";
			botao = "JOGAR";
			msg = "<html><center><h1>Agora é pra valer!!!</h1><br>" +
					"<h2>Clique no botão para começar a jogar.</h2></center></html>";
		}



		setSize(640, 480);
		//JTextArea textArea = new JTextArea(termos);
		setTitle(titulo);
		label_conteudo = new JLabel(msg);
		button_jogar = new JButton(botao);		

		//label_conteudo.setBounds(150, 20, 600, 300);
		
		if(idioma == "Ingles")
			label_conteudo.setBounds(55, 20, 520, 300);
		else
			label_conteudo.setBounds(120, 20, 410, 300);
		
		button_jogar.setBounds(250, 300, 100, 30);
		
		
		button_jogar.addActionListener(this);
		//button_testar.addActionListener(this);
		
		getContentPane().setLayout(null);
		getContentPane().add(label_conteudo);
		getContentPane().add(button_jogar);
		
	}

	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource() == button_jogar){
			//JOptionPane.showMessageDialog(null, "Clicou Jogar" ,"Confirmaï¿½ï¿½o", JOptionPane.INFORMATION_MESSAGE);
				VariaveisGlobais.gerador = GeraSequenciaAleatoria.geraSequencia(VariaveisGlobais.quant_geradores);
				Controle controle = Controle.getInstancia();
				controle.InciarJogo();
				this.dispose();
			}
			//else{
				//button_continuar.setEnabled(true);
				//JOptionPane.showMessageDialog(null, "<html><center>Ir para teste</center></html>" ,"Alerta", JOptionPane.INFORMATION_MESSAGE);
			//}
		
	}
	
	public static void main(String[] args){

		final FormularioPosTeste posTeste = new FormularioPosTeste();

		posTeste.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  

		//Adaptador para o fechamento da janela, matando o processo  
		posTeste.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing (WindowEvent e)  
			{  

				if(posTeste.idioma == "Portugues" || posTeste.idioma == null){
					Object[] options = { "Sim", "Nï¿½o" };    
					int n = JOptionPane.showOptionDialog(null,    
							"Tem certeza que deseja fechar? ",    
							"Sair" , JOptionPane.YES_NO_OPTION,    
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if(n == 0){
						//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						//form.setVisible(true);
						//Controle controle = Controle.getInstancia();
						//controle.InciarJogo();
						posTeste.dispose() ;
					}
				}else{
					if(posTeste.idioma == "Ingles"){
						Object[] options = { "yes", "no" };    
						int n = JOptionPane.showOptionDialog(null,    
								"Are you sure you want to quit? ",    
								"Quit" , JOptionPane.YES_NO_OPTION,    
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if(n == 0){
							posTeste.dispose() ;
						}
					}
				}		

			}   
		});
		posTeste.setLocationRelativeTo(null);
		posTeste.setVisible(true);
	}
}
