package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import br.ufv.willian.GeraSequenciaAleatoria;
import br.ufv.willian.auxiliares.ClassUser;
import br.ufv.willian.auxiliares.VariaveisGlobais;
import br.ufv.willian.auxiliares.VetorSequencia;

public class FormularioFinal extends JFrame implements ActionListener {
/**
* 
*/
	private static final long serialVersionUID = 1L;
	protected JButton b1;
	private JFrame form;
	
	private DadosFormulario dados = DadosFormulario.getInstancia();	
	
	private ClassRadioButtons diversaoPane, frustacaoPane, desafiadorPane, dificuldadePane, desenvolvidoPane, 
		visualPane, notaPane;
	private JLabel label1, label2, lb_sugestao, lb_nota, lb_diversao, lb_frustacao, 
		lb_desafiador, lb_dificuldade, lb_desenvolvidopor, lb_visual, lb_justif_diversao, lb_justif_dificuldade, lb_justif_visual, lb_justif_desenvolvido;  
    //JTextField tf_resposta1, tf_resposta2, tf_resposta3;
	private JTextArea txtAreaSugestaoDiversao, txtAreaSugestaoDificuldade, txtAreaSugestaoVisual, txtAreaSugestaoDesenvolvidoPor;
    //JComboBox<String> cb_nota;  
    private JButton bt_continuar; //, bt_fechar;
    private JScrollPane areaScrollPaneDiversao, areaScrollPaneDificuldade, areaScrollPaneVisual, areaScrollPaneDesenvolvidoPor;
    private String idioma;
    
	public FormularioFinal() {
	
		//dados.setIdioma("Ingles");
		//this.setLocationRelativeTo(null); //Centraliza frame
		setSize(640, 480); 
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); 
		
		//dados.setIdioma("Ingles");
		idioma = dados.getIdioma();
		
		if(dados.getIdioma() == "Ingles"){
			
			//setTitle("The questionnaire on the Game");  
			setTitle("Automatic Content Generation");
			
			label1             = new JLabel("<html><b><font size=5>Rate 1-7 for each statement, where:</font></b><br>" +
					"(1) Strongly agree - (2) Agree - (3) Agree somewhat - (4) Neither agree nor agree - <br>" +
					"(5) Disagree somewhat - (6) Disagree - (7) Strongly disagree"); 
			
	        //label2             = new JLabel("Where: (1)Strongly disagree - (2)Disagree - (3)Disagree somewhat - (4)Neither agree nor disagree - (5)Agree somewhat - (6)Agree - (7)Strongly agree");
	        lb_diversao           = new JLabel("<html>I enjoyed playing this level</html>");
	        
	        lb_justif_diversao    = new JLabel("Justify (optional)");
	        
	        //lb_frustacao       = new JLabel("This level is frustrating..........................................................................................");
	        //I enjoyed playing this level
	        //lb_desafiador      = new JLabel("This level is challenging..........................................................................................");
	        lb_dificuldade        = new JLabel("<html>This level is difficult</html>");	        
	        lb_justif_dificuldade = new JLabel("Justify (optional)");
	        
	        
	        lb_desenvolvidopor    = new JLabel("<html>This level was developed by a computer program</html>");	        
	        lb_justif_desenvolvido = new JLabel("Justify (optional)");
	        
	        
	        lb_visual             = new JLabel("<html>This level has good aesthetics</html>");	        
	        lb_justif_visual = new JLabel("Justify (optional)");
	        
	        
	        //lb_nota            = new JLabel("What grade would you give to this level? ");
	        lb_sugestao        = new JLabel("Suggestions/ Opinions: ");
	        //
	        bt_continuar       = new JButton("<html><b>Continue Playing</b></html>"); 
	        
	        //bt_fechar       = new JButton("Quit");
	        //Justify(optional)
			
		}
		else{			
	        setTitle("Geração Automática de Conteúdo");   
	        
	        label1             = new JLabel("<html><font size=5>Dê uma nota de 1 a 7 para cada afirmação, onde:</font><br>" +
	        		"<font size=3>(1) Concordo totalmente - (2) Concordo - (3) Concordo fracamente  - (4)  Não concordo nem discordo - <br>" +
	        		"(5) Discordo fracamente - (6) Discordo - (7) Discordo totalmente</font></html>"); 
	        //label2             = new JLabel("Onde : (1)Discordo totalmente - (2)Discordo - (3)Nï¿½o concordo nem discordo - (4)Concordo - (5)Concordo totalmente");
	        //lb_diversao        = new JLabel("Esta fase ï¿½ divertida........................................................................................");
	        lb_diversao        = new JLabel("<html>Gostei de jogar esta fase");
	        lb_justif_diversao = new JLabel("Justifique (opcional)");
	        
	        //lb_frustacao       = new JLabel("Esta fase é frustrante.........................................................................................");
	        //lb_desafiador      = new JLabel("Esta fase é desafiadora........................................................................................");
	        
	        lb_dificuldade        = new JLabel("<html>Esta fase é difícil</html>");
	        lb_justif_dificuldade = new JLabel("Justifique (opcional)");
	        
	        lb_desenvolvidopor = new JLabel("<html>Esta fase foi desenvolvida por um programa de computador</html>");
	        lb_justif_desenvolvido = new JLabel("Justifique (opcional)");
	        
	        lb_visual          = new JLabel("<html>Esta fase tem um bom visual (estética)</html>");
	        lb_justif_visual   = new JLabel("Justifique (opcional)");
	        
	        //lb_nota            = new JLabel("Que nota vocï¿½ daria para esta fase? .....................................................................");
	        lb_sugestao        = new JLabel("Sugestões/Opiniões : ");
	        
	        bt_continuar       = new JButton("Continuar Jogando");
	        //bt_fechar       = new JButton("Fechar");
		}
		
		label1.setFont(getFont());
		lb_diversao.setFont(getFont());
		lb_justif_diversao.setFont(getFont());
		lb_dificuldade.setFont(getFont());
		lb_justif_dificuldade.setFont(getFont());
		lb_desenvolvidopor.setFont(getFont());
		lb_justif_desenvolvido.setFont(getFont());
		lb_visual.setFont(getFont());
		lb_justif_visual.setFont(getFont());
		bt_continuar.setFont(getFont());
		
	       
	    //Painel de Diversao
	    diversaoPane = new ClassRadioButtons("");
	    diversaoPane.setFont(getFont());
	        
	    //Painel de Frustacao
		frustacaoPane = new ClassRadioButtons("");
		frustacaoPane.setFont(getFont());
		
		//Painel de Desafiador
		desafiadorPane = new ClassRadioButtons("");
		desafiadorPane.setFont(getFont());
			
		//Painel de Dificuldade
		dificuldadePane = new ClassRadioButtons("");
		dificuldadePane.setFont(getFont());
			
		//Painel de opiniao sobre desenvolvedor
		desenvolvidoPane = new ClassRadioButtons("");
		desenvolvidoPane.setFont(getFont());
			
		//Painel de Jogaria novamente
		visualPane = new ClassRadioButtons("");
		visualPane.setFont(getFont());
			
			//Painel da Nota
			//notaPane = new ClassRadioButtons("");
	          
	        
	        bt_continuar.addActionListener(this);
	        //bt_fechar.addActionListener(this);
	          
	        //cb_nota          = new JComboBox();
	        // cb_nota.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Selecione","0", "1", "2", "3","4", "5"}));  
	        
	        txtAreaSugestaoDiversao = new JTextArea();
	        txtAreaSugestaoDiversao.setLineWrap(true);
	        areaScrollPaneDiversao = new JScrollPane(txtAreaSugestaoDiversao);
	        
	        txtAreaSugestaoDificuldade = new JTextArea();
	        txtAreaSugestaoDificuldade.setLineWrap(true);
	        areaScrollPaneDificuldade = new JScrollPane(txtAreaSugestaoDificuldade);
	        
	        txtAreaSugestaoVisual = new JTextArea();
	        txtAreaSugestaoVisual.setLineWrap(true);
	        areaScrollPaneVisual = new JScrollPane(txtAreaSugestaoVisual);
	        
	        txtAreaSugestaoDesenvolvidoPor = new JTextArea();
	        txtAreaSugestaoDesenvolvidoPor.setLineWrap(true);
	        areaScrollPaneDesenvolvidoPor = new JScrollPane(txtAreaSugestaoDesenvolvidoPor);
	        //areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        //areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        
	        
	        
	        getContentPane().setLayout(null);  
	  
	        getContentPane().add(label1);  
	        //getContentPane().add(label2); 
	        getContentPane().add(lb_diversao);
	        getContentPane().add(diversaoPane);
	        getContentPane().add(lb_justif_diversao);	        
	        getContentPane().add(areaScrollPaneDiversao);
	        //getContentPane().add(lb_frustacao);
	        //getContentPane().add(frustacaoPane);
	        //getContentPane().add(lb_desafiador);
	        //getContentPane().add(desafiadorPane);
	        getContentPane().add(lb_dificuldade);
	        getContentPane().add(dificuldadePane);
	        getContentPane().add(lb_justif_dificuldade);
	        getContentPane().add(areaScrollPaneDificuldade);
	        
	        getContentPane().add(lb_desenvolvidopor);
	        getContentPane().add(desenvolvidoPane);
	        getContentPane().add(lb_justif_desenvolvido);
	        getContentPane().add(areaScrollPaneDesenvolvidoPor);
	        
	        getContentPane().add(lb_visual);
	        getContentPane().add(visualPane);
	        getContentPane().add(lb_justif_visual);
	        getContentPane().add(areaScrollPaneVisual);
	        
	        //getContentPane().add(lb_nota);
	        //getContentPane().add(notaPane);
	        //getContentPane().add(lb_sugestao);
	        //getContentPane().add(areaScrollPane);
	        //getContentPane().add(lb_pergunta3);
	        //getContentPane().add(lb_nota);
	         
	        //getContentPane().add(cb_nota); 
	        
	       // getContentPane().add(tf_resposta1);  
	        //getContentPane().add(tf_resposta2);  
	        //getContentPane().add(tf_resposta3);
	        
	        getContentPane().add(bt_continuar); 
	        //getContentPane().add(bt_fechar);  
	  
	  
	        label1              .setBounds(10, 0, 600, 71);
	        //label2              .setBounds(20, 20, 500, 20);
	        //lb_diversao         .setBounds(20, 70, 280, 30);
	        //diversaoPane        .setBounds(240, 70, 450, 30);
	        lb_diversao         .setBounds(10, 80, 230, 35);
	        diversaoPane        .setBounds(200, 80, 450, 40);
	        lb_justif_diversao  .setBounds(30, 110, 350, 30);
	        areaScrollPaneDiversao.setBounds(150, 120, 420, 35);
	        
	        //lb_frustacao        .setBounds(10, 100, 350, 30);
	        //frustacaoPane       .setBounds(265, 100, 450, 30);
	        //lb_desafiador       .setBounds(10, 130, 350, 30);
	        //desafiadorPane      .setBounds(265, 130, 450, 30);
	        lb_dificuldade      .setBounds(10, 160, 230, 35);
	        dificuldadePane     .setBounds(200, 160, 450, 40);
	        lb_justif_dificuldade  .setBounds(30, 190, 350, 30);
	        areaScrollPaneDificuldade.setBounds(150, 200, 420, 35);
	        
	        lb_visual .setBounds(10, 240, 230, 35);
	        visualPane.setBounds(200, 240, 450, 40);
	        lb_justif_visual  .setBounds(30, 270, 350, 30);
	        areaScrollPaneVisual.setBounds(150, 280, 420, 35);
	        
	        lb_desenvolvidopor  .setBounds(10, 320, 230, 40);
	        desenvolvidoPane    .setBounds(200, 320, 450, 40);
	        lb_justif_desenvolvido.setBounds(30, 355, 350, 30);
	        areaScrollPaneDesenvolvidoPor.setBounds(150, 360, 420, 35);
	        
	        //lb_nota             .setBounds(20, 250, 230, 30);
	        //notaPane            .setBounds(200, 250, 450, 30);
	        //lb_sugestao         .setBounds(10, 250, 150, 20);
	        //areaScrollPane      .setBounds(180, 260, 370, 80);
	         
	  
	        bt_continuar       .setBounds(240, 405,170, 30);  
	        // bt_fechar       .setBounds(30,370,80, 30);  
	        
	        //bt_salvar.setMnemonic(KeyEvent.VK_D);
	        bt_continuar.setActionCommand("continuar");
	        	        	        
	        //bt_fechar.setMnemonic(KeyEvent.VK_D);
	        //bt_fechar.setActionCommand("fechar");
	        
	        /*
	        ClassRadioButtons teste = new ClassRadioButtons("Este ï¿½ um Teste");
	        getContentPane().add(teste); 
	        teste.setBounds(5, 140, 600, 30);
	        
	        ClassRadioButtons teste2 = new ClassRadioButtons("Que nota vocï¿½ daria para esta fase? ");
	        getContentPane().add(teste2); 
	        teste2.setBounds(5, 180, 600, 30);
	        */
	        
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
        
    }  
  
    	
	
	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == bt_continuar) {
			
			
			/*
			String opcoes = "" + diversaoPane.opcao_selecionada + ", " + frustacaoPane.opcao_selecionada + ", " + 
					desafiadorPane.opcao_selecionada + ", " + dificuldadePane.opcao_selecionada + ", " + 
					desenvolvidoPane.opcao_selecionada + ", " + jogariaNovamentePane.opcao_selecionada + ", " + 
					notaPane.opcao_selecionada + ", " + txtAreaSugestao.getText(); 
			JOptionPane.showMessageDialog(null, opcoes ,"Confirmaï¿½ï¿½o", JOptionPane.INFORMATION_MESSAGE);
			*/
			
			//txtAreaSugestao.setText("Testando a inserï¿½ï¿½o de texto");
			if(diversaoPane.opcao_selecionada == -1 || dificuldadePane.opcao_selecionada == -1 || desenvolvidoPane.opcao_selecionada == -1 ||
					visualPane.opcao_selecionada == -1){
				if(dados.getIdioma() == "Ingles")
					JOptionPane.showMessageDialog(null, "All options must be selected" ,"Caution!", JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Todas as opições precisam estar marcadas" ,"Atenção!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			dados.setQuestionarioFinal(diversaoPane.opcao_selecionada, -1, -1 , dificuldadePane.opcao_selecionada, 
					desenvolvidoPane.opcao_selecionada, visualPane.opcao_selecionada, -1, txtAreaSugestaoDiversao.getText(), txtAreaSugestaoDificuldade.getText(),
					txtAreaSugestaoVisual.getText(), txtAreaSugestaoDesenvolvidoPor.getText());
					//jogariaNovamentePane.opcao_selecionada, notaPane.opcao_selecionada, txtAreaSugestao.getText());
			
			dados.setMorreu(VariaveisGlobais.morreuEm);
			dados.setGamePlay();
			dados.salvarArquivo();
			
			VetorSequencia sequencia = new VetorSequencia();
	    	File file = new File("sequencia.txt");
	    	if(file.exists()){
		    	try {
					sequencia = sequencia.carregaVetorSequencia("sequencia.txt");
					if(sequencia.size() == 0){
						ClassUser user = new ClassUser();
						user = user.carregaUser();
						user.setCompleto(1);
						user.salvaUser();
						FormularioProblemas.main(null);
						this.dispose();
						return;
					}
				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		    	catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
				}
	    	}
			
			try {
				dados.enviarDadosServidor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			VariaveisGlobais.cont_jogadas++;
			if(VariaveisGlobais.cont_jogadas < 3){
				Controle controle = Controle.getInstancia();
				controle.InciarJogo();
			}else{
				VariaveisGlobais.cont_jogadas = 0;
				VariaveisGlobais.cont++;
			
				
				if(dados.getIdioma() == "Portugues" || dados.getIdioma() == null){
					Object[] options = { "Sim", "Nï¿½o" };    
				    int n = JOptionPane.showOptionDialog(null,    
				                     "Obrigado pela participaï¿½ï¿½o. Gostaria de continuar jogando?",    
				                     null , JOptionPane.YES_NO_OPTION,    
				                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				    if(n == 0){
				    	int x = VariaveisGlobais.cont % VariaveisGlobais.quant_geradores;
				    	if(x == 0 && VariaveisGlobais.cont > 0)
				    		VariaveisGlobais.gerador = GeraSequenciaAleatoria.geraSequencia(VariaveisGlobais.quant_geradores);
				    	dados.setParticipante(1);
				    	Controle controle = Controle.getInstancia();
						controle.InciarJogo();				    	
					}				    
				}else{
					if(dados.getIdioma() == "Ingles"){
						Object[] options = { "yes", "no" };    
					    int n = JOptionPane.showOptionDialog(null,    
					                     "Thanks for participating. Do you like to continue playing?",    
					                     null , JOptionPane.YES_NO_OPTION,    
					                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					    if(n == 0){
					    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					    	//form.setVisible(true);
					    	int x = VariaveisGlobais.cont % VariaveisGlobais.quant_geradores;
					    	if(x == 0 && VariaveisGlobais.cont > 0)
					    		VariaveisGlobais.gerador = GeraSequenciaAleatoria.geraSequencia(VariaveisGlobais.quant_geradores);
					    	Controle controle = Controle.getInstancia();
							controle.InciarJogo();					    	
						}
					}
				}
				
			}*/
			
			
			Controle controle = Controle.getInstancia();
			controle.InciarJogo();
			this.dispose() ;
		}
		/*else{
			//JOptionPane.showMessageDialog(null,"Vocï¿½ clicou em Fechar","Fechar", JOptionPane.CLOSED_OPTION);
				//this.setVisible(false);
				/*
				Object[] options = { "Sim", "Nï¿½o" };    
			    int n = JOptionPane.showOptionDialog(null,    
			                     "Tem certeza que deseja fechar? ",    
			                     null , JOptionPane.YES_NO_OPTION,    
			                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			    if(n == 0){
			    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    	//form.setVisible(true);
			    	this.dispose() ;
				}
				*/
			    /*
			    if(dados.getIdioma() == "Portugues" || dados.getIdioma() == null){
					Object[] options = { "Sim", "Nï¿½o" };    
				    int n = JOptionPane.showOptionDialog(null,    
				                     "Tem certeza que deseja fechar? ",    
				                     null , JOptionPane.YES_NO_OPTION,    
				                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				    if(n == 0){
				    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    	//form.setVisible(true);
				    	Controle controle = Controle.getInstancia();
						controle.InciarJogo();
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
					    	Controle controle = Controle.getInstancia();
							controle.InciarJogo();
					    	this.dispose() ;
						}
					}
				}
			}
	*/

	}
	
	public void setJFrames(JFrame form)
    {    	
    	this.form = form;
    }
	
	public void fechar(){
		if(diversaoPane.opcao_selecionada != -1 ||  dificuldadePane.opcao_selecionada != -1 || 
				desenvolvidoPane.opcao_selecionada != -1 || visualPane.opcao_selecionada != -1){
		
			dados.setQuestionarioFinal(diversaoPane.opcao_selecionada, -1, 
					-1, dificuldadePane.opcao_selecionada, desenvolvidoPane.opcao_selecionada,
					visualPane.opcao_selecionada, -1, txtAreaSugestaoDiversao.getText(), txtAreaSugestaoDificuldade.getText(),
					txtAreaSugestaoVisual.getText(), txtAreaSugestaoDesenvolvidoPor.getText());
					//jogariaNovamentePane.opcao_selecionada, notaPane.opcao_selecionada, txtAreaSugestao.getText());
			
			dados.setMorreu(VariaveisGlobais.morreuEm);
			dados.setGamePlay();
			dados.salvarArquivo();
			
			try {
				dados.enviarDadosServidor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.dispose();
		}
		else
			this.dispose();
	}
	
	public boolean valida(){
		if(diversaoPane.opcao_selecionada == -1 || dificuldadePane.opcao_selecionada == -1 || desenvolvidoPane.opcao_selecionada == -1 ||
				visualPane.opcao_selecionada == -1){
			if(dados.getIdioma() == "Ingles"){
				JOptionPane.showMessageDialog(null, "All options must be selected" ,"Caution!", JOptionPane.WARNING_MESSAGE);
				return false;
			}else{
				JOptionPane.showMessageDialog(null, "Todas as opï¿½oes precisam estar marcadas" ,"Atenï¿½ï¿½o!", JOptionPane.WARNING_MESSAGE);
				return false;
			}		
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		//new FormularioFinal().setVisible(true);
		//JFrame form = new FormularioFinal();
		final FormularioFinal formulario = new FormularioFinal();
		
		formulario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  
        
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
					    	if(formulario.valida()){
					    		formulario.fechar();
					    	}
						}
					}else{
						if(formulario.idioma == "Ingles"){
							Object[] options = { "yes", "no" };    
						    int n = JOptionPane.showOptionDialog(null,    
						                     "Are you sure you want to quit? ",    
						                     "Quit" , JOptionPane.YES_NO_OPTION,    
						                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						    if(n == 0){
						    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						    	//form.setVisible(true);
						    	//Controle controle = Controle.getInstancia();
								//controle.InciarJogo();
						    	//formulario.dispose() ;
						    	if(formulario.valida()){
						    		formulario.fechar();
						    	}
							}
						}
					}
            	
                //caixa de dialogo retorna um inteiro 
            	
            	/*
                int resposta = JOptionPane.showConfirmDialog(null,"Deseja finalizar essa operaãão?","Finalizar",JOptionPane.YES_NO_OPTION);  
                  
                //sim = 0, nao = 1  
                if (resposta == 0)  
                {  
                    System.exit(0);  
                }
                */  
            }
        });
		
		formulario.setLocationRelativeTo(null); //Centraliza frame
		formulario.setVisible(true);
    }
}
