package br.ufv.dpi;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Classe inicial para fazer avaliação das pequenas telas
 * @author WILLIAN
 *
 */
public class FormularioInicialComparacaoFases extends JFrame implements ActionListener{

	public FormularioInicialComparacaoFases(){
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Calendar calendar = Calendar.getInstance();
    	int ano = calendar.get(Calendar.YEAR);
    	int mes = calendar.get(Calendar.MONTH);
    	int dia  = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);     
		int minute = calendar.get(Calendar.MINUTE);     
		int second = calendar.get(Calendar.SECOND);
		int mili = calendar.get(Calendar.MILLISECOND);
		Random random = new Random();
		
		String user  = "user" + random.nextInt(Integer.MAX_VALUE) + "-" + dia + "-" + mes + "-" + 
        		ano + "-" + hour + "h" + minute + "m" + second + "s" + mili + "ms"; 
		
		DadosAvaliacaoTelas dados = DadosAvaliacaoTelas.getInstancia();
		dados.setUsuario(user);
		
		setTitle("Geração Automática de Conteúdo");
		setSize(640, 480);
		setLocationRelativeTo(null);
		
		
		JLabel lb_msg = new JLabel("<html><h3>MarioMiniFase</h3>" +
				"Lembre-se de avaliar a fase no final quanto a:<br>" +
				"<ol><li>Diversão</li><li>Jogabilidade/Visual</li><li>Dificuldade</li></ol>" +
				"<br>Você pode tentar ser o melhor no <b>ranking</b>." +
				"<br>Quanto <b>mais jogar, mais moedas coletar, mais inimigos matar e menos deixar o Mario morrer</b>, mais bem colocado estará no RANKING." +
				"<br><br>Lembre-se que o ranking é gerado cada vez que você joga. Não é acumulativo. Ou seja, cada vez que abrir a aplicação, será um novo usuário no ranking.</html>");
		lb_msg.setFont(getFont());
		
		lb_msg.setBounds(10, 10, 600, 290);
		
		JButton jb_avaliar = new JButton("Jogar");		
		jb_avaliar.addActionListener(
	            new ActionListener(){
	                public void actionPerformed(ActionEvent e){
	                	Controle controle = Controle.getInstancia();
	    				controle.InciarJogo();
	    				dispose();
	                }
	            }   
	        );
		JButton jb_instrucoes = new JButton("Reler Instruções");
		jb_instrucoes.addActionListener(
	            new ActionListener(){
	                public void actionPerformed(ActionEvent e){
	                	FormularioInstrucoesTelas.main(null);
	    				dispose();
	                }
	            }   
	        );
		
		jb_instrucoes.setBounds(150, 350, 150, 30);
		jb_avaliar.setBounds(310, 350, 150, 30);
		
		getContentPane().setLayout(null);		
		getContentPane().add(lb_msg);
		getContentPane().add(jb_instrucoes);
		getContentPane().add(jb_avaliar);
		
	}
	
	//@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String [] args){
		
		new FormularioInicialComparacaoFases().setVisible(true);
		
	}

}
