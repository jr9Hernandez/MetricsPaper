package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import br.ufv.willian.auxiliares.VariaveisGlobais;
import br.ufv.willian.auxiliares.VetorTelasParaTeste;

public class FormularioInstrucoes extends JFrame implements ActionListener{
	
	JButton button_jogar, button_testar;
	JLabel label_titulo, label_conteudo, label_img1, label_img2;
	String idioma, msg;
	
	public FormularioInstrucoes(){
		String titulo = "", label = "", botao = "", botao2 = "", instrucoes = "", img1 = "", img2 = "";	

		DadosFormulario dados = DadosFormulario.getInstancia();
		idioma = dados.getIdioma();
		//idioma = "Ingles";

		if(idioma == "Ingles"){
			titulo = "Automatic Content Generation";
			label = "<html><h1>Instructions</h1></html>";
			botao = "Practice";
			//botao2 ="";
			/*instrucoes = "<html><b>Controls</b><br>" + 
					"Use the arrow keys to move mario around.<br>" +
					"Press A to run faster and to pick up shells.<br>" + 
					"Press S to jump.</html>";	*/
			img1 = "img/as-en.png";
			img2 = "img/setas-en.png";
			msg = "This first level is for you test the game's controls";
			
		}else{
			//titulo = "Mario MiniFase";			
			titulo = "Geração Automática de Fases";
			label = "<html><h2>Comandos</h2></html>";			
			botao = "Pratique";
			//titulo = "Geração Automática de Conteúdo";			
			//label = "<html><h2>Instruções</h2></html>";
			//botao = "Treinar";
			//botao2 = "Ir direto para o jogo";
			/*instrucoes = "<html><b>Controles</b><br>" + 
					"Use as setas do teclado para movimentar o mario.<br>" +
					"Pressione A para correr mais rápido e pegar conchas.<br>" + 
					"Pressione S para pular.</html>"; */
			img1 = "img/as-pt.png";
			img2 = "img/setas-pt.png";
			msg = "Esta primeira fase é para você testar os comandos do jogo";
		}



		setSize(640, 480);
		//JTextArea textArea = new JTextArea(termos);
		setTitle(titulo);
		//label_conteudo = new JLabel(instrucoes);
		label_titulo = new JLabel(label);
		button_jogar = new JButton(botao);
		//button_testar = new JButton(botao);
		label_img1 = new JLabel(new ImageIcon(img1));
		label_img2 = new JLabel(new ImageIcon(img2));
		
		label_titulo.setBounds(250, 5, 300, 20);
		//areaScrollPane.setBounds(10, 40, 600, 330);
		//label_conteudo.setBounds(150, 50, 400, 100);
		label_img1.setBounds(20, 40, 412, 133);
		label_img2.setBounds(230, 190, 356, 123);
		//button_testar.setBounds(160, 250, 150, 20);
		button_jogar.setBounds(250, 350, 130, 30);
		
		button_jogar.addActionListener(this);
		//button_testar.addActionListener(this);
		
		getContentPane().setLayout(null);
		getContentPane().add(label_titulo);
		//getContentPane().add(label_conteudo);
		getContentPane().add(button_jogar);        
		//getContentPane().add(button_testar);
		getContentPane().add(label_img1);
		getContentPane().add(label_img2);
		
	}

	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		/*
		if(ae.getSource() == button_jogar){
			VetorTelasParaTeste teste = new VetorTelasParaTeste();
			try {
				teste = teste.carregaVetorTelas("vetorTelas");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(teste.size());
			if(teste.size() != 0){
				FormularioInicialAvaliadorTelas.main(null);
			}
			else{
				FormularioInstrucoesTelas.main(null);
			}
			this.dispose();
		}
		*/
		
		if(ae.getSource() == button_jogar){
			JOptionPane.showMessageDialog(null, msg ,"", JOptionPane.INFORMATION_MESSAGE);
				//FormularioPosTeste.main(null); 
			VariaveisGlobais.testando = true;
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

		final FormularioInstrucoes instrucoes = new FormularioInstrucoes();

		instrucoes.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  

		//Adaptador para o fechamento da janela, matando o processo  
		instrucoes.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing (WindowEvent e)  
			{  

				if(instrucoes.idioma == "Portugues" || instrucoes.idioma == null){
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
						instrucoes.dispose() ;
					}
				}else{
					if(instrucoes.idioma == "Ingles"){
						Object[] options = { "yes", "no" };    
						int n = JOptionPane.showOptionDialog(null,    
								"Are you sure you want to quit? ",    
								"Quit" , JOptionPane.YES_NO_OPTION,    
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if(n == 0){
							instrucoes.dispose() ;
						}
					}
				}		

			}   
		});
		instrucoes.setLocationRelativeTo(null);
		instrucoes.setVisible(true);
	}
}
