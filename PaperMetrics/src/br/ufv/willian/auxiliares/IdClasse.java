package br.ufv.willian.auxiliares;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.ufv.dpi.DadosFormulario;

public class IdClasse extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdClasse(){
		setSize(640, 140);
		setTitle("Your id");
		setLocationRelativeTo(null);
		DadosFormulario dados = DadosFormulario.getInstancia();
		
		JLabel label = new JLabel("<html><font size=5>Your participation id is:<font></html>");
		label.setFont(getFont());
		
		JTextField id = new JTextField("" + dados.getUser());
		//id.setEnabled(false);
		//id.add
		
		label.setBounds(30, 10, 600, 30);
		id.setBounds(15, 50, 600, 30);
		
		
		getContentPane().setLayout(null);
		getContentPane().add(label);
		getContentPane().add(id);
		
	}
		
	public static void main(String [] args){
		
		IdClasse form = new IdClasse();
		
		form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  

		//Adaptador para o fechamento da janela, matando o processo  
		form.addWindowListener(new WindowAdapter()  
		{  
			public void windowClosing (WindowEvent e)  
			{  

				/*if(form.idioma == "Portugues" || form.idioma == null){
					Object[] options = { "Sim", "Não" };    
					int n = JOptionPane.showOptionDialog(null,    
							"Tem certeza que deseja fechar? ",    
							"Sair" , JOptionPane.YES_NO_OPTION,    
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if(n == 0){						
						form.fechar();
					}
				}else{
					if(form.idioma == "Ingles"){*/
						Object[] options = { "yes", "no" };    
						int n = JOptionPane.showOptionDialog(null,    
								"Are you sure you want to quit? ",    
								"Quit" , JOptionPane.YES_NO_OPTION,    
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if(n == 0){
							System.exit(0);
						}
					//}
				//}		

			}   
		});
		form.setLocationRelativeTo(null);
		form.setVisible(true);
		
	}

}
