package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import br.ufv.willian.GeraSequenciaAleatoria;
import br.ufv.willian.auxiliares.VariaveisGlobais;

public class FormularioComparacaoFases extends JFrame implements ActionListener {
/**
* 
*/
	private static final long serialVersionUID = 1L;
	protected JButton b1;
	private JFrame form;
	
	private DadosFormulario dados = DadosFormulario.getInstancia();	
	
	private ClassRadioButtons diversaoPane, frustacaoPane, desafiadorPane, dificuldadePane, desenvolvidoPane, 
		jogariaNovamentePane, notaPane;
	private JLabel label1, label2, lb_sugestao, lb_nota, lb_diversao, lb_frustacao, 
		lb_desafiador, lb_dificuldade, lb_desenvolvidopor, lb_jogariaNovamente;  
    //JTextField tf_resposta1, tf_resposta2, tf_resposta3;
	private JTextArea txtAreaSugestao;
    //JComboBox<String> cb_nota;  
    private JButton bt_continuar; //, bt_fechar;
    private JScrollPane areaScrollPane;
    private String idioma;
    
	public FormularioComparacaoFases() {
	
		//dados.setIdioma("Ingles");
		//this.setLocationRelativeTo(null); //Centraliza frame
		setSize(640, 480); 
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); 
		
		
		idioma = dados.getIdioma();
		
		if(dados.getIdioma() == "Ingles"){
			
			//setTitle("The questionnaire on the Game");  
			setTitle("Automatic Content Generation");
			
			label1             = new JLabel("<html><font size=5>Rate 1-7 for each statement, where:</font><br>" +
					"(1) Strongly disagree - (2) Disagree - (3) Disagree somewhat - (4) Neither agree nor disagree - <br>" +
					"(5) Agree somewhat - (6) Agree - (7) Strongly agree"); 
	        //label2             = new JLabel("Where: (1)Strongly disagree - (2)Disagree - (3)Disagree somewhat - (4)Neither agree nor disagree - (5)Agree somewhat - (6)Agree - (7)Strongly agree");
	        lb_diversao        = new JLabel("This level is fun.....................................................................");
	        lb_frustacao       = new JLabel("This level is frustrating.....................................................................");
	        lb_desafiador      = new JLabel("This level is challenging.....................................................................");
	        lb_dificuldade     = new JLabel("This level is very difficult.....................................................................");
	        lb_desenvolvidopor = new JLabel("This level was developed by a machine.....................................................................");
	        lb_jogariaNovamente= new JLabel("I would play this level again.....................................................................");
	        //lb_nota            = new JLabel("What grade would you give to this level? ");
	        lb_sugestao        = new JLabel("Suggestions/ Opinions: ");
	        
	        bt_continuar       = new JButton("Continue Playing"); 
	        //bt_fechar       = new JButton("Quit");
			
		}
		else{			
	        setTitle("Geração Automática de Conteúdo");   
	        
	        label1             = new JLabel("<html><font size=5>Selecione uma opção para cada uma das alternativas abaixo</font></html>"); 
	        //label2             = new JLabel("Onde : (1)Discordo totalmente - (2)Discordo - (3)Não concordo nem discordo - (4)Concordo - (5)Concordo totalmente");
	        lb_diversao        = new JLabel("A fase mais divertida .......................................................................");
	        lb_frustacao       = new JLabel("A fase mais frustrante ......................................................................");
	        lb_desafiador      = new JLabel("A fase mais desafiadora .....................................................................");
	        lb_dificuldade     = new JLabel("A fase mais dificil .........................................................................");
	        lb_desenvolvidopor = new JLabel("<html>A fase que foi desenvolvida por um programa de computador......................................................................<html>");
	        lb_jogariaNovamente= new JLabel("A fase que mais gostou ......................................................................");
	        //lb_nota            = new JLabel("Que nota você daria para esta fase? .....................................................................");
	        lb_sugestao        = new JLabel("Sugestões/Opiniões : ");
	        
	        bt_continuar       = new JButton("Continuar Jogando");
	        //bt_fechar       = new JButton("Fechar");
		}
	       
	        //Painel de Diversao
	        diversaoPane = new ClassRadioButtons("", 4);
	        
	        //Painel de Frustacao
			frustacaoPane = new ClassRadioButtons("", 4);
			
			//Painel de Desafiador
			desafiadorPane = new ClassRadioButtons("", 4);
			
			//Painel de Dificuldade
			dificuldadePane = new ClassRadioButtons("", 4);
			
			//Painel de opiniao sobre desenvolvedor
			desenvolvidoPane = new ClassRadioButtons("", 4);
			
			//Painel de Jogaria novamente
			jogariaNovamentePane = new ClassRadioButtons("", 4);
			
			//Painel da Nota
			//notaPane = new ClassRadioButtons("");
	          
	        
	        bt_continuar.addActionListener(this);
	        //bt_fechar.addActionListener(this);
	          
	        //cb_nota          = new JComboBox();
	        // cb_nota.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Selecione","0", "1", "2", "3","4", "5"}));  
	        
	        txtAreaSugestao = new JTextArea();
	        txtAreaSugestao.setLineWrap(true);
	        areaScrollPane = new JScrollPane(txtAreaSugestao);
	        //areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        //areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        
	        
	        
	        getContentPane().setLayout(null);  
	  
	        getContentPane().add(label1);  
	        //getContentPane().add(label2); 
	        getContentPane().add(lb_diversao);
	        getContentPane().add(diversaoPane);
	        getContentPane().add(lb_frustacao);
	        getContentPane().add(frustacaoPane);
	        getContentPane().add(lb_desafiador);
	        getContentPane().add(desafiadorPane);
	        getContentPane().add(lb_dificuldade);
	        getContentPane().add(dificuldadePane);
	        getContentPane().add(lb_desenvolvidopor);
	        getContentPane().add(desenvolvidoPane);
	        getContentPane().add(lb_jogariaNovamente);
	        getContentPane().add(jogariaNovamentePane);
	        //getContentPane().add(lb_nota);
	        //getContentPane().add(notaPane);
	        getContentPane().add(lb_sugestao);
	        getContentPane().add(areaScrollPane);
	        //getContentPane().add(lb_pergunta3);
	        //getContentPane().add(lb_nota);
	         
	        //getContentPane().add(cb_nota); 
	        
	       // getContentPane().add(tf_resposta1);  
	        //getContentPane().add(tf_resposta2);  
	        //getContentPane().add(tf_resposta3);
	        
	        getContentPane().add(bt_continuar); 
	        //getContentPane().add(bt_fechar);  
	  
	  
	        label1              .setBounds(10, 0, 600, 70);
	        //label2              .setBounds(20, 20, 500, 20);
	        lb_diversao         .setBounds(20, 70, 280, 30);
	        diversaoPane        .setBounds(200, 70, 450, 30);
	        lb_frustacao        .setBounds(20, 100, 280, 30);
	        frustacaoPane       .setBounds(200, 100, 450, 30);
	        lb_desafiador       .setBounds(20, 130, 280, 30);
	        desafiadorPane      .setBounds(200, 130, 450, 30);
	        lb_dificuldade      .setBounds(20, 160, 280, 30);
	        dificuldadePane     .setBounds(200, 160, 450, 30);
	        lb_desenvolvidopor  .setBounds(20, 190, 280, 30);
	        desenvolvidoPane    .setBounds(200, 190, 450, 30);
	        lb_jogariaNovamente .setBounds(20, 220, 280, 30);
	        jogariaNovamentePane.setBounds(200, 220, 450, 30);
	        //lb_nota             .setBounds(20, 250, 230, 30);
	        //notaPane            .setBounds(200, 250, 450, 30);
	        lb_sugestao         .setBounds(20, 250, 150, 20);
	        areaScrollPane      .setBounds(180, 260, 370, 80);
	         
	  
	        bt_continuar       .setBounds(250,370,145, 30);  
	       // bt_fechar       .setBounds(30,370,80, 30);  
	        
	        //bt_salvar.setMnemonic(KeyEvent.VK_D);
	        bt_continuar.setActionCommand("continuar");
	        	        	        
	        //bt_fechar.setMnemonic(KeyEvent.VK_D);
	        //bt_fechar.setActionCommand("fechar");
	        
	        /*
	        ClassRadioButtons teste = new ClassRadioButtons("Este é um Teste");
	        getContentPane().add(teste); 
	        teste.setBounds(5, 140, 600, 30);
	        
	        ClassRadioButtons teste2 = new ClassRadioButtons("Que nota você daria para esta fase? ");
	        getContentPane().add(teste2); 
	        teste2.setBounds(5, 180, 600, 30);
	        */
	        
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
        
    }  
  
	public void fazCopia() throws IOException, ClassNotFoundException{ 
    	//Se der erro nao copia o arquivo para a pasta
    	DadosFormulario d = new DadosFormulario();
    	//DadosFormulario d = new DadosAvaliacaoTelas();
        d = d.carregarArquivo("level_data.obj");            
    	//***********************************************
    	Calendar calendar = Calendar.getInstance();
    	int ano = calendar.get(Calendar.YEAR);
    	int mes = calendar.get(Calendar.MONTH);
    	int dia  = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);     
		int minute = calendar.get(Calendar.MINUTE);     
		int second = calendar.get(Calendar.SECOND);
		int mili = calendar.get(Calendar.MILLISECOND);
    	
    	Random randon = new Random();
    	File arquivo_recebido = new File("level_data.obj"); 
        
        File copia_arquivo_recebido = new File("ArquivosRecebidos/arq" + randon.nextInt() + "-" + dia + "-" + mes + "-" + 
        		ano + "-" + hour + "h" + minute + "m" + second + "s" + mili + "ms"); 
        
        //System.out.println("Salvando aquivos no diretorio");
        //Salva a Tela
        FileInputStream fisOrigemArquivo = new FileInputStream(arquivo_recebido);  
        FileOutputStream fisDestinoArquivo = new FileOutputStream(copia_arquivo_recebido);  
        FileChannel fcOrigem = fisOrigemArquivo.getChannel();    
        FileChannel fcDestino = fisDestinoArquivo.getChannel();    
        fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);    
        fisOrigemArquivo.close();    
        fisDestinoArquivo.close();  
        
              
        //System.out.println("Finalizado!!!");  
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
			JOptionPane.showMessageDialog(null, opcoes ,"Confirmação", JOptionPane.INFORMATION_MESSAGE);
			*/
			
			//txtAreaSugestao.setText("Testando a inserção de texto");
			if(diversaoPane.opcao_selecionada == -1 ||  frustacaoPane.opcao_selecionada == -1 || desafiadorPane.opcao_selecionada == -1  || 
					dificuldadePane.opcao_selecionada == -1 || desenvolvidoPane.opcao_selecionada == -1 || jogariaNovamentePane.opcao_selecionada == -1){
				if(dados.getIdioma() == "Ingles")
					JOptionPane.showMessageDialog(null, "All options must be selected" ,"Caution!", JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Todas as opçoes precisam estar marcadas" ,"Atenção!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			dados.setQuestionarioFinal(diversaoPane.opcao_selecionada, frustacaoPane.opcao_selecionada, 
					desafiadorPane.opcao_selecionada, dificuldadePane.opcao_selecionada, desenvolvidoPane.opcao_selecionada,
					jogariaNovamentePane.opcao_selecionada, 0, txtAreaSugestao.getText(), "", "", "");
					//jogariaNovamentePane.opcao_selecionada, notaPane.opcao_selecionada, txtAreaSugestao.getText());
			
			dados.setMorreu(VariaveisGlobais.morreuEm);
			dados.salvarArquivo();
			
			try {
				fazCopia();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			try {
				dados.enviarDadosServidor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			//VariaveisGlobais.cont_jogadas++;
			//if(VariaveisGlobais.cont_jogadas < 3){
				//Controle controle = Controle.getInstancia();
				//controle.InciarJogo();
				TelaA.main(null);
			
			/*}else{
				VariaveisGlobais.cont_jogadas = 0;
				VariaveisGlobais.cont++;
				
				if(dados.getIdioma() == "Portugues" || dados.getIdioma() == null){
					Object[] options = { "Sim", "Não" };    
				    int n = JOptionPane.showOptionDialog(null,    
				                     "Obrigado pela participação. Gostaria de continuar jogando?",    
				                     null , JOptionPane.YES_NO_OPTION,    
				                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				    if(n == 0){
				    	//int x = VariaveisGlobais.cont % VariaveisGlobais.quant_geradores;
				    	//if(x == 0 && VariaveisGlobais.cont > 0)
				    	//	VariaveisGlobais.gerador = GeraSequenciaAleatoria.geraSequencia(VariaveisGlobais.quant_geradores);
				    	//dados.setParticipante(1);
				    	//Controle controle = Controle.getInstancia();
						//controle.InciarJogo();
				    	TelaA.main(null);
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
					    	//int x = VariaveisGlobais.cont % VariaveisGlobais.quant_geradores;
					    	//if(x == 0 && VariaveisGlobais.cont > 0)
					    	//	VariaveisGlobais.gerador = GeraSequenciaAleatoria.geraSequencia(VariaveisGlobais.quant_geradores);
					    	//Controle controle = Controle.getInstancia();
							//controle.InciarJogo();
					    	TelaA.main(null);
						}
					}
				}
				
			}
		*/
			this.dispose() ;
		}
		/*else{
			//JOptionPane.showMessageDialog(null,"Você clicou em Fechar","Fechar", JOptionPane.CLOSED_OPTION);
				//this.setVisible(false);
				/*
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
				*/
			    /*
			    if(dados.getIdioma() == "Portugues" || dados.getIdioma() == null){
					Object[] options = { "Sim", "Não" };    
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
		if(diversaoPane.opcao_selecionada != -1 ||  frustacaoPane.opcao_selecionada != -1 || 
				desafiadorPane.opcao_selecionada != -1  || dificuldadePane.opcao_selecionada != -1 || 
				desenvolvidoPane.opcao_selecionada != -1 || jogariaNovamentePane.opcao_selecionada != -1){
		
			dados.setQuestionarioFinal(diversaoPane.opcao_selecionada, frustacaoPane.opcao_selecionada, 
					desafiadorPane.opcao_selecionada, dificuldadePane.opcao_selecionada, desenvolvidoPane.opcao_selecionada,
					jogariaNovamentePane.opcao_selecionada, 0, txtAreaSugestao.getText(), "", "", "");
					//jogariaNovamentePane.opcao_selecionada, notaPane.opcao_selecionada, txtAreaSugestao.getText());
			
			dados.setMorreu(VariaveisGlobais.morreuEm);
			/*
			dados.salvarArquivo();
			try {
				dados.enviarDadosServidor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			this.dispose();
		}
		else
			this.dispose();
	}
	
	public static void main(String[] args) {
		
		//new FormularioFinal().setVisible(true);
		//JFrame form = new FormularioFinal();
		final FormularioComparacaoFases formulario = new FormularioComparacaoFases();
		
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
				    	formulario.fechar();
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
					    	formulario.fechar();
						}
					}
				}
            	
                //caixa de dialogo retorna um inteiro 
            	
            	/*
                int resposta = JOptionPane.showConfirmDialog(null,"Deseja finalizar essa operação?","Finalizar",JOptionPane.YES_NO_OPTION);  
                  
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
