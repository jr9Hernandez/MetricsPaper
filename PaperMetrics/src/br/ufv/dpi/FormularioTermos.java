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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class FormularioTermos extends JFrame implements ActionListener{

	JButton button_continuar;
	JLabel label_titulo, label_conteudo;
	JCheckBox check_concordo;
	String idioma;

	public FormularioTermos (){

		String titulo = "", label = "", botao = "", termos = "", check = "";	

		DadosFormulario dados = DadosFormulario.getInstancia();
		idioma = dados.getIdioma();

		if(idioma == "Ingles"){
			titulo = "Automatic Content Generation";
			label = "<html><h3>Terms of Use</h3></html>";
			check = "<html>By ticking this box you will be agreeing in participating in the study. Your participation is voluntary and you may leave at any time by closing this window.</html>";
			botao = "Next";			
			termos = "<html>" +
					"<br><p>We have developed an automatic level generator for the game of Infinite Mario and would like you to play with our software. While you have fun playing Mario you will be contributing with our research; at the end of each level played you will answer 6 straightforward questions.</p>" +
					"<br><p><b>DURATION: </b>It depends on how much you want to play and on your skill level, but we estimated that it will take approximately 3 minutes to play an entire level and to answer our 7-question questionnaire.</p>" +
					"<br><p><b>RISKS:</b> There are no associated risks with this game, and you can stop playing it at any time just by closing the game's window. </p>" +
					"<br><p><b>PRIVACY:</b>  Your responses will be kept anonymous. We will not collect any information that could allow us to discover your identity." +
					"If you have any questions, please contact us: Willian Reis (willian.reis.mario@gmail.com) or Levi Lelis (levilelis@gmail.com).</p>";
			
		}else{
			titulo = "Geração Automática de Conteúdo";
			label = "<html><h3>Termos de Uso</h3></html>";
			check = "<html>Ao assinalar esta caixa, você está concordando em participar do estudo. Sua participação é voluntária e você é livre para deixar o experimento a qualquer momento, basta fechar o jogo.</html>";
			botao = "Próximo";			
			termos = "<html>Você está prestes a contribuir para um projeto de pesquisa se divertindo. A sua contribuição é muito importante para nós.<br>" + 
					"<br><b>O QUE ESTAMOS FAZENDO:</b> Estamos estudando maneiras diferentes de criação automática de conteúdos para jogos eletrônicos. Neste caso a geração de fases.<br>" + 
					"<br><b>O QUE VOCÊ TERÁ QUE FAZER:</b> Preencher um pequeno formulário com 7 questões e depois jogar. Isto mesmo, jogar. Após o jogo responder um pequeno questionário sobre a fase que acabou de jogar.<br>" +
					"<br><b>DURAÇÃO: </b>Com cerca de 3 min você é capaz de jogar uma fase e responder o questionário.<br>" + 
					"<br><b>RISCOS:</b> Não existem riscos associados a este estudo, pois a coleta de dados é completamente anônima e o tema não é sensível.<b>Você pode deixar o experimento a qualquer momento caso queira.</b><br>" + 
					"<br><b>PRIVACIDADE E COLETA DE DADOS:</b> Suas respostas serão mantidas em anonimato. Nós não recolhemos qualquer informação que possa ser usada para estabelecer a sua identidade.<br>" +  
					"<br><b>CONTATO:</b> Em caso de dúvidas sobre esta pesquisa, entre em contato com: Willian Reis (willian.reis.mario@gmail.com) ou Levi Lelis (levilelis@gmail.com)</html>";
		}



		setSize(640, 480);
		//JTextArea textArea = new JTextArea(termos);
		setTitle(titulo);
		label_conteudo = new JLabel(termos);
		label_titulo = new JLabel(label);
		check_concordo = new JCheckBox(check);
		button_continuar = new JButton(botao);


		//textArea.setLineWrap(true);
		//JScrollPane areaScrollPane = new JScrollPane(textArea);
		//areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//textArea.setEditable(false);

		label_titulo.setBounds(250, 5, 300, 20);
		//areaScrollPane.setBounds(10, 40, 600, 330);
		label_conteudo.setBounds(10, 25, 600, 330);
		check_concordo.setBounds(10, 370, 620, 40);
		button_continuar.setBounds(270, 410, 100, 20);

		check_concordo.setActionCommand("checked");
		//button_continuar.setActionCommand("Jogar");

		button_continuar.addActionListener(this);
		//button_continuar.setEnabled(true);


		getContentPane().setLayout(null);
		getContentPane().add(label_titulo);
		//getContentPane().add(areaScrollPane);
		getContentPane().add(label_conteudo);
		getContentPane().add(check_concordo);        
		getContentPane().add(button_continuar);



	}
	public static void main(String[] args){

		final FormularioTermos termos = new FormularioTermos();

		termos.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  

		//Adaptador para o fechamento da janela, matando o processo  
		termos.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing (WindowEvent e)  
			{  

				if(termos.idioma == "Portugues" || termos.idioma == null){
					Object[] options = { "Sim", "Não" };    
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
		termos.setLocationRelativeTo(null);
		termos.setVisible(true);
	}

	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub

		if(ae.getSource() == button_continuar){
			//JOptionPane.showMessageDialog(null, "Clicou Jogar" ,"Confirmação", JOptionPane.INFORMATION_MESSAGE);
			if(check_concordo.isSelected()){
				//button_continuar.setEnabled(false);
				//JOptionPane.showMessageDialog(null, "Marcado" ,"Confirmação", JOptionPane.INFORMATION_MESSAGE);
				FormularioInicial.main(null);
				this.dispose();
			}
			else{
				//button_continuar.setEnabled(true);
				JOptionPane.showMessageDialog(null, "<html><center>É necessário marcar a caixa de seleção concordando com os termos para continuar</center></html>" ,"Alerta", JOptionPane.INFORMATION_MESSAGE);
			}
		}		
	}	
}

