package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import br.ufv.willian.GeraSequenciaAleatoria;
import br.ufv.willian.auxiliares.IdClasse;
import br.ufv.willian.auxiliares.VariaveisGlobais;

public class FormularioProblemas extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton button_proximo;
	private JLabel label_conteudo;
	private String idioma;
	private JTextArea txtAreaSugestao;
	private JScrollPane areaScrollPane;
	private DadosFormulario dados = DadosFormulario.getInstancia();
	private String msg_Jopition;
	
	public FormularioProblemas(){
		
		String titulo = "", botao = "", msg = "";		
		idioma = dados.getIdioma();
		//idioma = "Ingles";

		if(idioma == "Ingles"){
			titulo = "Automatic Content Generation";
			botao = "Next";
			//botao2 ="";
			msg =  "<html><center><h2>Please let us know if you ran into any technical issue while playing the game (optional).</h2></center></html>";	
			msg_Jopition = "Thanks for participating.";
			
		}else{
			titulo = "Geraçao Automática de Conteúdo";
			botao = "PROXIMO";
			msg = "<html><center><h2>Por favor, deixe-nos saber se você teve qualquer tipo de problema técnico durante o jogo (opcional).</h2></center></html>";
			msg_Jopition = "Obrigado pela participação";
		}



		setSize(640, 480);
		//JTextArea textArea = new JTextArea(termos);
		setTitle(titulo);
		label_conteudo = new JLabel(msg);
		label_conteudo.setFont(getFont());
		button_proximo = new JButton(botao);
		txtAreaSugestao = new JTextArea();
		txtAreaSugestao.setLineWrap(true);
		areaScrollPane = new JScrollPane(txtAreaSugestao);

		//label_conteudo.setBounds(150, 20, 600, 300);
		
		
		label_conteudo.setBounds(30, 10, 560, 50);
		areaScrollPane.setBounds(40, 100, 540, 250);
		button_proximo.setBounds(270, 370, 100, 30);		
		
		button_proximo.addActionListener(this);
		//button_testar.addActionListener(this);
		
		
		getContentPane().setLayout(null);
		getContentPane().add(label_conteudo);
		getContentPane().add(areaScrollPane);
		getContentPane().add(button_proximo);
		
	}

	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource() == button_proximo){
			fechar();
		}
			
		
	}
	
	public void fechar(){
		dados.setSugestaoGeral(txtAreaSugestao.getText());
		dados.salvarArquivo();
		try {
			dados.enviarDadosServidor();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String msg = "<html>Thanks for participating.<br>Your participation id is:<br>"+ dados.getUser() + "</html>";
		//JOptionPane.showMessageDialog(null,msg,"", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, msg_Jopition ,"", JOptionPane.INFORMATION_MESSAGE);
		//IdClasse.main(null);
		this.dispose();
	}
	
	public static void main(String[] args){

		final FormularioProblemas form = new FormularioProblemas();

		form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  

		//Adaptador para o fechamento da janela, matando o processo  
		form.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing (WindowEvent e)  
			{  

				if(form.idioma == "Portugues" || form.idioma == null){
					Object[] options = { "Sim", "Não" };    
					int n = JOptionPane.showOptionDialog(null,    
							"Tem certeza que deseja fechar? ",    
							"Sair" , JOptionPane.YES_NO_OPTION,    
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if(n == 0){						
						form.fechar();
					}
				}else{
					if(form.idioma == "Ingles"){
						Object[] options = { "yes", "no" };    
						int n = JOptionPane.showOptionDialog(null,    
								"Are you sure you want to quit? ",    
								"Quit" , JOptionPane.YES_NO_OPTION,    
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if(n == 0){
							form.fechar();
						}
					}
				}		

			}   
		});
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
	

}
