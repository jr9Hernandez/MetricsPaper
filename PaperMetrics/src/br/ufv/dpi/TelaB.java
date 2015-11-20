package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TelaB extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton button_continuar;
	JLabel label_titulo, label_conteudo;
	JCheckBox check_concordo;
	String idioma;
	boolean chamada = false;
	
	public TelaB(){
		String titulo = "", label = "", botao = "", tempo = "", check = "";	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		titulo = "";
		label = "<html><font size = 7>Agora irá jogar fase <b>B</b> em </font></html>";
		//check = "<html>Ao assinalar esta caixa, você está concordando em participar do estudo. Sua participação é voluntária e você é livre para deixar o experimento a qualquer momento, basta fechar o jogo.</html>";
		botao = "Jogar Proxima";			
		tempo = "<html><center><font size = 8><b>3</b></font></center>" ;



		setSize(640, 480);
		setLocationRelativeTo(null);
		//JTextArea textArea = new JTextArea(termos);
		setTitle(titulo);
		label_conteudo = new JLabel(tempo);
		label_conteudo.setFont(getFont());
		label_titulo = new JLabel(label);
		label_titulo.setFont(getFont());
		check_concordo = new JCheckBox(check);
		button_continuar = new JButton(botao);
	
	
		//textArea.setLineWrap(true);
		//JScrollPane areaScrollPane = new JScrollPane(textArea);
		//areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//textArea.setEditable(false);
	
		label_titulo.setBounds(130, 150, 400, 50);
		//areaScrollPane.setBounds(10, 40, 600, 330);
		label_conteudo.setBounds(300, 220, 20, 50);
		//check_concordo.setBounds(10, 375, 620, 40);
		button_continuar.setBounds(230, 320, 180, 30);
	
		//check_concordo.setActionCommand("checked");
		button_continuar.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	proximaTela();
            }
        });
	
		//button_continuar.addActionListener(this);
		//button_continuar.setEnabled(true);
	
	
		getContentPane().setLayout(null);
		getContentPane().add(label_titulo);
		getContentPane().add(label_conteudo);
		//getContentPane().add(button_continuar);
		
		//alteraTempo();
		
	}
	
	public void proximaTela(){
		if(!chamada) {
			Controle c = Controle.getInstancia();
			c.InciarJogo();
			this.dispose();
			chamada = true;
		}		
	}
	
	public void alteraTempo(){
		int cont = 3;
		int aux;
		while(cont > 0){
			//System.out.println(cont);
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cont--;
			
			label_conteudo.setText("<html><center><font size = 8><b>" + cont +"</b></font></center>");
		}
		
		
		//System.out.println("FIM");
		proximaTela();
	}
	
	public static void main(String[] args){
		
		TelaB t = new TelaB();
		t.setVisible(true);
		t.alteraTempo();
		
	}

}
