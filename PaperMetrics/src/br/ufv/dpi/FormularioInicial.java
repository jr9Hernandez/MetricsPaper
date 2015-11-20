package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle.Control;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.ufv.willian.auxiliares.ClassUser;
import br.ufv.willian.auxiliares.IdClasse;
import br.ufv.willian.auxiliares.VetorSequencia;

public class FormularioInicial  extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	protected JButton b1;
	private JFrame form;
	private DadosFormulario dados = DadosFormulario.getInstancia();
	private String idioma;
	
	
	JLabel lb_abertura, lb_nacionalidade, lb_idade, lb_escolaridade, lb_sexo, lb_participou_antes,
		lb_paises, lb_linguas, lb_paisesmorou, lb_joga, lb_jogou_mario, lb_idioma; 
	
    JTextField tf_idade, tf_paisesmorou, tf_resposta3;  
    
    JComboBox<String> cb_sexo, cb_nacionalidade, cb_escolaridade, cb_joga , cb_jogou_mario, cb_participou_antes; 
    
    JButton bt_salvar; //, bt_limpar, bt_fechar;
    
	public FormularioInicial() {
		
		//setLocationRelativeTo(null); //Centraliza frame
	    setVisible(true);
	    
	    dados.setIdioma("Portugues"); 	     
	  	idioma = dados.getIdioma();
	  	
	  	String msg_erro, msg_aviso;
	  	
	  	if(idioma == "Ingles"){
	  		msg_erro = "ERROR! 'user.txt' not found."; 
	  		msg_aviso = "Thanks for participating.";
	  	}else{
	  		msg_erro = "ERRO! 'user.txt' não encontrado."; 
	  		msg_aviso = "Obrigado pela participação.";	  		
	  	}
		
		File file;
					
		ClassUser user = new ClassUser();
	     try {
	    	file = new File("user.txt");
	    	if(file.exists()){
	    		user = user.carregaUser();
	    	}else{
	    		JOptionPane.showMessageDialog(null,msg_erro,"ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
	    	}
	    	
	    	if(user.getUser() != null && user.getCompleto() == 1){
	    		dados.setUser(user.getUser());
	    		//String msg = "<html>Thanks for participating.<br>Your participation id is:<br>"+ dados.getUser() + "</html>";
	    		//JOptionPane.showMessageDialog(null,msg,"", JOptionPane.INFORMATION_MESSAGE);
	    		JOptionPane.showMessageDialog(null, msg_aviso,"", JOptionPane.INFORMATION_MESSAGE);
				//System.exit(0);
	    		//IdClasse.main(null);
	    		this.dispose();
	    		return;
	    	}
	    		
	    	user.geraUser();
	    	user.salvaUser();
	    	dados.setUser(user.getUser());
			
	     } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	     } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	     }    
	     
	     
		//******** Instanciando ****************
		 tf_idade           = new JTextField("");          
	     tf_resposta3       = new JTextField("");  
	          
	     cb_sexo            = new JComboBox<String>(); 
	     cb_participou_antes= new JComboBox<String>();
	     //cb_nacionalidade   = new JComboBox<String>();
	     //cb_escolaridade   = new JComboBox<String>();
	     cb_joga           = new JComboBox<String>();
	     cb_jogou_mario    = new JComboBox<String>();
		//**************************
	    
	     
		 
		if(idioma == "Portugues" || idioma == null){
	        //setTitle("Dados Pessoais");
			setTitle("Geração Automática de Conteúdo");
	        setSize(640, 480);  
	        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	        lb_abertura       = new JLabel("POR FAVOR, PREENCHA OS CAMPOS ABAIXO"); 
	        //lb_idioma = new JLabel("SELECIONE O IDIOMA");
	        //lb_idade          = new JLabel("1 - Qual sua idade?");
	        lb_idade          = new JLabel("1 - Idade");
	        lb_idade.setFont(getFont());
	        //lb_sexo           = new JLabel("2 - Qual ã o seu sexo?");        
	        lb_sexo           = new JLabel("2 - Sexo");
	        lb_sexo.setFont(getFont());
	        //lb_escolaridade   = new JLabel("3 - Qual ã o nãvel mais alto de educaãão que vocã possui ou estã cursando?");
	        //lb_escolaridade   = new JLabel("3 - Escolaridade");
	        //lb_nacionalidade  = new JLabel("4 - Nacionalidade");
	        //lb_paisesmorou    = new JLabel("Jã morou em outro(s) paãs(es)? Se sim, qual(quais)?");
	        //lb_joga           = new JLabel("3 - Joga jogos de computador?");
	        lb_jogou_mario          = new JLabel("3 - Já jogou Mario antes?");
	        lb_jogou_mario.setFont(getFont());
	        //lb_participou_antes = new JLabel("7 - Jã participou deste teste antes?");
	        
	        
	        //lb_participacao      = new JLabel("Deseja participar de nossos experimentos?");          
	       
	        
	        bt_salvar       = new JButton("PROXIMO");   bt_salvar.addActionListener(this);
	        //bt_limpar       = new JButton("LIMPAR");   bt_limpar.addActionListener(this);
	        //bt_fechar       = new JButton("SAIR");   bt_fechar.addActionListener(this);
	        
	        String[] paises = listaPaises(null);
	        paises[0] = "Selecione";
	          
	        cb_sexo.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Selecione","Masculino", "Feminino"}));  
	        //cb_participou_antes.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Selecione", "Sim", "Não"}));
	        //cb_nacionalidade.setModel(new javax.swing.DefaultComboBoxModel<String>(paises));
	        //cb_idioma.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Selecione", "Portuguãs", "English"}));
	        //cb_escolaridade.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Selecione", "Fundamental", 
	        //		"Mãdio", "Superior", "Mestrado", "Doutorado"}));
	        cb_joga.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Selecione", "Nunca", "Raramente", 
	        		"Algumas Vezes", "Frequentemente", "Muito Frequentemente"}));
	        cb_jogou_mario.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Selecione", "Nunca", "Raramente", 
	        		"Algumas Vezes", "Frequentemente", "Muito Frequentemente"}));
        }
		else{
			setTitle("Automatic Content Generation");
	        setSize(640, 480);  
	        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	        lb_abertura       = new JLabel("<html><b>PLEASE, COMPLETE THE FIELDS BELOW</b></html>"); 
	        lb_abertura.setFont(getFont());
	        //lb_jogou_antes = new JLabel("Have you taken this test before?");
	        lb_idade          = new JLabel("1 - Age");
	        lb_idade.setFont(getFont());
	        lb_sexo           = new JLabel("2 - Gender");  
	        lb_sexo.setFont(getFont());
	        //lb_escolaridade   = new JLabel("3 - Education");
	        //lb_nacionalidade  = new JLabel("4 - Nationality");
	        //lb_paisesmorou    = new JLabel("Jã morou em outro(s) paãs(es)? Se sim, qual(quais)?");
	        //lb_joga           = new JLabel("3 - Have you play PC gaming?");
	        //lb_joga.setFont(getFont());
	        lb_jogou_mario          = new JLabel("3 - Have you played Mario before?");
	        lb_jogou_mario.setFont(getFont());
	        //lb_participou_antes = new JLabel("7 - Have you taken this test before?");
	        
	        //lb_participacao      = new JLabel("Wish to participate in our experiments?");
	          
	       	        
	        bt_salvar       = new JButton("NEXT");   bt_salvar.addActionListener(this);
	        //bt_limpar       = new JButton("CLEAR");   bt_limpar.addActionListener(this);
	        //bt_fechar       = new JButton("QUIT");   bt_fechar.addActionListener(this);
	        
	        String[] paises = listaPaises(Locale.ENGLISH);
	        paises[0] = "Select"; 
	        cb_sexo.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Select","Male", "Female"}));  
	        //cb_participou_antes.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Select", "Yes", "No"}));
	        //cb_nacionalidade.setModel(new javax.swing.DefaultComboBoxModel<String>(paises));
	        //cb_idioma.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Selecione", "Portuguãs", "English"}));
	        //cb_escolaridade.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Select", "Elementary School", "High School", 
			//		"College", "Master of Science", "PhD"}));
	        //cb_joga.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Select", "Never", "Rarely", "Sometimes", "Often", "Very Often"}));
	        cb_jogou_mario.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Select", "never played", "played a little",
					"played some Mario", "played a lot of Mario"}));
		}
  
        getContentPane().setLayout(null);  
  
        getContentPane().add(lb_abertura); 
        //getContentPane().add(lb_idioma);  
        getContentPane().add(lb_idade);  
        getContentPane().add(lb_sexo); 
        //getContentPane().add(lb_escolaridade);
        //getContentPane().add(lb_nacionalidade);
        //getContentPane().add(lb_joga);
        getContentPane().add(lb_jogou_mario);
        //getContentPane().add(lb_participou_antes);
         
        getContentPane().add(cb_sexo); 
        //getContentPane().add(cb_participacao);
        //getContentPane().add(cb_nacionalidade);
        //getContentPane().add(cb_idioma); 
        //getContentPane().add(cb_escolaridade);
        //getContentPane().add(cb_joga);
        getContentPane().add(cb_jogou_mario);
        //getContentPane().add(cb_participou_antes);
        
        getContentPane().add(tf_idade);  
        //getContentPane().add(tf_paisesmorou);  
        getContentPane().add(tf_resposta3);
        
        getContentPane().add(bt_salvar);  
        //getContentPane().add(bt_limpar);  
        //getContentPane().add(bt_fechar);  
  
 
        lb_abertura     .setBounds(20, 20, 400, 15);
        //lb_idioma       .setBounds(20, 50, 150, 15);  
        //cb_idioma       .setBounds(150,50, 100, 25);
        lb_idade        .setBounds(20, 70, 150, 15);  
        tf_idade        .setBounds(170, 65, 50, 25);  
        lb_sexo         .setBounds(20, 120, 250, 15);  
        cb_sexo         .setBounds(170,115, 100, 25);  
        // lb_escolaridade .setBounds(20, 140, 500, 15);  
        //cb_escolaridade .setBounds(170, 140, 150, 25);  
        //lb_nacionalidade.setBounds(20, 170, 200, 15);  
        //cb_nacionalidade.setBounds(170, 170, 200, 25);
        //lb_joga         .setBounds(20, 170, 300, 15);  //(20, 200, 300, 15);
        //cb_joga         .setBounds(210, 165, 170, 25); //(200, 200, 170, 25);
        lb_jogou_mario  .setBounds(20, 170, 220, 15);  //(20, 230, 300, 15);
        cb_jogou_mario  .setBounds(235, 165, 180, 25); //(170, 230, 170, 25);   
        //lb_participou_antes .setBounds(20, 260, 310, 15);
        //cb_participou_antes .setBounds(230, 260, 100, 25);
        //lb_paisesmorou       .setBounds(20, 200, 300, 15);
        //cb_nacionalidade.setBounds(280, 200, 100, 25);
         
  
        bt_salvar       .setBounds(270, 300, 100, 30);  
       //bt_limpar       .setBounds(105,340,80, 30);  
        //bt_fechar       .setBounds(190,340,80, 30);  
        
        bt_salvar.setMnemonic(KeyEvent.VK_D);
        bt_salvar.setActionCommand("salvar");
        
        //bt_limpar.setMnemonic(KeyEvent.VK_D);
        //bt_limpar.setActionCommand("limpar");
        
        //bt_fechar.setMnemonic(KeyEvent.VK_D);
        //bt_fechar.setActionCommand("fechar");
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }  
  
    	
	
	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == bt_salvar) {
			//JOptionPane.showMessageDialog(null,"Obrigado por enviar os dados","Confirmaãão", JOptionPane.INFORMATION_MESSAGE);
			//form.setVisible(true);
			if(validaForm()){
				DadosFormulario dados = DadosFormulario.getInstancia();	
				//salvaDados(String idade, int sexo, int escolaridade, int nacionalidade, int jogou_mario, int participante)
				//dados.salvaDados(tf_idade.getText(), cb_sexo.getSelectedIndex(), cb_escolaridade.getSelectedIndex(), 
				//		cb_nacionalidade.getSelectedIndex(), cb_joga.getSelectedIndex(), cb_jogou_mario.getSelectedIndex(), cb_participou_antes.getSelectedIndex());
				dados.salvaDados(tf_idade.getText(), cb_sexo.getSelectedIndex(), -1, -1, -1, cb_jogou_mario.getSelectedIndex(), -1);
				//Experimento.main(null);
				FormularioInstrucoes.main(null);
				this.dispose();	
			}
		}else/*{
			if("limpar".equals(ae.getActionCommand())){
				tf_idade.setText("");				
				cb_sexo.setSelectedIndex(0);
				cb_escolaridade.setSelectedIndex(0);
				cb_nacionalidade.setSelectedIndex(0);
				cb_jogou_mario.setSelectedIndex(0);
				cb_participacao.setSelectedIndex(0);
			}
			else{ */
				//JOptionPane.showMessageDialog(null,"Vocã clicou em Fechar","Fechar", JOptionPane.CLOSED_OPTION);
				//this.setVisible(false);
				if(dados.getIdioma() == "Portugues"){
					Object[] options = { "Sim", "Não" };    
				    int n = JOptionPane.showOptionDialog(null,    
				                     "Tem certeza que deseja fechar? ",    
				                     null , JOptionPane.YES_NO_OPTION,    
				                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				    if(n == 0){
				    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    	//form.setVisible(true);
				    	this.dispose() ;
					}
				}else{
					if(dados.getIdioma() == "Ingles"){
						Object[] options = { "yes", "no" };    
					    int n = JOptionPane.showOptionDialog(null,    
					                     "Are you sure you want to quit? ",    
					                     null , JOptionPane.YES_NO_OPTION,    
					                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					    if(n == 0){
					    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					    	//form.setVisible(true);
					    	this.dispose() ;
						}
					}
				/*}
			}*/
		}
	}
	
	/**
	 * Testa se os campos estão preenchidos
	 * @return
	 * False se um campo estã em branco
	 * True se todos campos estão preenchidos
	 */
	public boolean validaForm(){
		String titulo, msg;
		if(dados.getIdioma() == "Ingles") titulo = "Alert";
		else titulo = "Alerta";
		
		if(tf_idade.getText().isEmpty()){
			if(dados.getIdioma() == "Ingles") msg = "Please, fill the question 1";
			else msg = "Por favor, preencha a questão 1";
			JOptionPane.showMessageDialog(null, msg ,titulo, JOptionPane.CLOSED_OPTION);
			return false;
		}
		if(cb_sexo.getSelectedIndex() == 0){
			if(dados.getIdioma() == "Ingles") msg = "Please select an alternative for question 2";
			else msg = "Por favor, selecione uma alternativa para a questão 2";
			JOptionPane.showMessageDialog(null, msg ,titulo, JOptionPane.CLOSED_OPTION);
			return false;
		}
		/*if(cb_escolaridade.getSelectedIndex() == 0){
			if(dados.getIdioma() == "Ingles") msg = "Please select an alternative for question 3";
			else msg = "Por favor, selecione uma alternativa para a questão 3";
			JOptionPane.showMessageDialog(null, msg ,titulo, JOptionPane.CLOSED_OPTION);
			return false;
		}		
		if(cb_nacionalidade.getSelectedIndex() == 0){
			if(dados.getIdioma() == "Ingles") msg = "Please select an alternative for question 4";
			else msg = "Por favor, selecione uma alternativa para a questão 4";
			JOptionPane.showMessageDialog(null, msg ,titulo, JOptionPane.CLOSED_OPTION);
			return false;
		}
		if(cb_joga.getSelectedIndex() == 0){
			if(dados.getIdioma() == "Ingles") msg = "Please select an alternative for question 3";
			else msg = "Por favor, selecione uma alternativa para a questão 5";
			JOptionPane.showMessageDialog(null, msg ,titulo, JOptionPane.CLOSED_OPTION);
			return false;
		}*/
		if(cb_jogou_mario.getSelectedIndex() == 0){
			if(dados.getIdioma() == "Ingles") msg = "Please select an alternative for question 3";
			else msg = "Por favor, selecione uma alternativa para a questão 3";
			JOptionPane.showMessageDialog(null, msg ,titulo, JOptionPane.CLOSED_OPTION);
			return false;
		}
		/*if(cb_participou_antes.getSelectedIndex() == 0){
			if(dados.getIdioma() == "Ingles") msg = "Please select an alternative for question 7";
			else msg = "Por favor, selecione uma alternativa para a questão 7";
			JOptionPane.showMessageDialog(null, msg ,titulo, JOptionPane.CLOSED_OPTION);
			return false;
		}
		*/
		return true;
	}
	
	public String[] listaPaises(Locale locale){
		
		ArrayList<String> lista = StringsPaisesLinguagens.getListOfCountries(locale);
		String[] paises = new String[lista.size() + 1];
		
		for(int i=1; i < lista.size(); i++)
			paises[i] = lista.get(i);
			
		return paises;
	}
	
	public void setJFrames(JFrame form)
    {    	
    	this.form = form;
    }
	
	public static void main(String[] args) {		
		
		//new FormularioInicial().setVisible(true);
		final FormularioInicial formulario =  new FormularioInicial();
		formulario.setLocationRelativeTo(null);
		
		//formulario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  
        
        //Adaptador para o fechamento da janela, matando o processo  
		formulario.addWindowListener(new WindowAdapter()  
        {  
            public void windowClosing (WindowEvent e)  
            {  
            	
            	if(formulario.idioma == "Portugues" || formulario.idioma == null){
					Object[] options = { "Sim", "Não" };    
				    int n = JOptionPane.showOptionDialog(null,    
				                     "Tem certeza que deseja fechar? ",    
				                     "Sair" , JOptionPane.YES_NO_OPTION,    
				                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				    if(n == 0){
						//formulario.dispose() ;
				    	System.exit(0); 
					}
				}else{
					if(formulario.idioma == "Ingles"){
						Object[] options = { "yes", "no" };    
					    int n = JOptionPane.showOptionDialog(null,    
					                     "Are you sure you want to quit? ",    
					                     "Quit" , JOptionPane.YES_NO_OPTION,    
					                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					    if(n == 0){
					    	//formulario.dispose() ;
					    	System.exit(0); 
						}
					}
				}               
            }  
        });
				
    }
}
