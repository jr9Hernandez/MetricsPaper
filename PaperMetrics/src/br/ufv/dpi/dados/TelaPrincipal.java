package br.ufv.dpi.dados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TelaPrincipal extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lb_title, lb_diretorio, lb_opcoes_usuario, lb_opcoes_equacao;
	private JComboBox<String> cb_opcoes_usuario, cb_opcoes_equacao;
	private JButton btn_selecionar, btn_gerar;
	GeradorDados gerador = new GeradorDados();
	//GeradorDadosTelas gerador = new GeradorDadosTelas();

	private File diretorio;
	
	public TelaPrincipal(){
		
		setTitle("Gerador de dados");
		setSize(640, 420);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); //Centraliza frame
		getContentPane().setLayout(null);
		
		String [] opcoes_usuario  =   { "Um unico arquivo", "Separado por idade",  "Separado por sexo", "Separado por escolaridade", 
				"Separado por nacionalidade", "Separado por ja jogaram antes", "Separado por participante", "Um arquivo para cada"};
		
		String [] opcoes_equacao  =   {"Todos os casos",
				"case 0: Parabola(0, 0, 4)",
				"case 1: Parabola(0, 0, 15)",
				"case 2: Parabola(0, 1, 1)",
				"case 3: Parabola(-0.15 , 1.8 , 5)",
				"case 4: RandomLevel()",
				"case 5: Parabola(0, -2, 20)",
				"case 6: PeterLevelGenerator()",
				"case 7: Parabola(-0.35, 4.2, 3)",
				"case 8: UltraCustomizedLevelGenerator()"};
		
		
		
		lb_title = new JLabel("<html><center><h2>Gerador de arquivos de dados</h2></center></html>");
		lb_diretorio = new JLabel("Selecione o diretorio ");
		lb_opcoes_usuario = new JLabel("<html>Como quer os arquivos<br>(Opções do Usuário)? </html>");		
		cb_opcoes_usuario = new JComboBox<String>();
		cb_opcoes_usuario.setModel(new javax.swing.DefaultComboBoxModel<String>(opcoes_usuario));
		lb_opcoes_equacao = new JLabel("<html>Como quer os arquivos<br>(Opções de Equações)? </html>");
		cb_opcoes_equacao = new JComboBox<String>();
		cb_opcoes_equacao.setModel(new javax.swing.DefaultComboBoxModel<String>(opcoes_equacao));
		btn_selecionar = new JButton("Selecionar");
        btn_selecionar.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JFileChooser fc = new JFileChooser();
                    
                    // restringe a amostra a diretorios apenas
                    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    
                    int res = fc.showOpenDialog(null);
                    
                    if(res == JFileChooser.APPROVE_OPTION){
                        diretorio = fc.getSelectedFile();
                        //JOptionPane.showMessageDialog(null, "Voce escolheu o diretório: " + diretorio.getAbsolutePath());                        
                        ArrayList<String> lista  = gerador.retornaListaArquivos(diretorio.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "O diretorio que escolheu possui " + lista.size() + " arquivos.");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Voce nao selecionou nenhum diretorio."); 
                }
            }   
        );
        
        btn_gerar = new JButton("Gerar");
        btn_gerar.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                	if(gerador.getDiretorio()){
                		//int option_user = cb_opcoes_usuario.getSelectedIndex();
                		//int option_equation = cb_opcoes_equacao.getSelectedIndex();
                		//gerador.geraArquivo(option_user, option_equation);
                		gerador.geraArquivo(0, 0);
                	}
                	else{
                		JOptionPane.showMessageDialog(null, "Você precisa escolher o diretório");
                	}
                }
            }   
        );
        
        getContentPane().add(lb_title);
        getContentPane().add(lb_diretorio);
        getContentPane().add(btn_selecionar);
        getContentPane().add(lb_opcoes_usuario);
        getContentPane().add(cb_opcoes_usuario);
        getContentPane().add(lb_opcoes_equacao);
        getContentPane().add(cb_opcoes_equacao);
        getContentPane().add(btn_gerar);
		
		lb_title         .setBounds(150, 0,  300, 30);
		lb_diretorio     .setBounds(20,  40,  200, 35);
		btn_selecionar   .setBounds(200, 40,  100, 25);
		lb_opcoes_usuario.setBounds(20,  80,  200, 35);
		cb_opcoes_usuario.setBounds(200, 80,  200, 25);
		lb_opcoes_equacao.setBounds(20,  120, 200, 35);
		cb_opcoes_equacao.setBounds(200, 120, 260, 25);
		btn_gerar        .setBounds(280, 300, 80, 30);
	}
	
	public static void main(String [] args){
		
		new TelaPrincipal().setVisible(true);
		
	}

	//@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
