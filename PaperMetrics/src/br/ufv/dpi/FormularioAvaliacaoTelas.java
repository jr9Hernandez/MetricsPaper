package br.ufv.dpi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import br.ufv.willian.GeraSequenciaAleatoria;
import br.ufv.willian.auxiliares.RankingJogador;
import br.ufv.willian.auxiliares.VariaveisGlobais;
import br.ufv.willian.auxiliares.VetorTelasParaTeste;

public class FormularioAvaliacaoTelas extends JFrame implements ActionListener{
/**
* 
*/
	private static final long serialVersionUID = 1L;
	protected JButton b1;
	private JFrame form;
	
	private DadosAvaliacaoTelas dados = DadosAvaliacaoTelas.getInstancia();	
	
	private ClassRadioButtons diversaoPane, jogabilidadePane, dificuldadePane; //, notaPane;
	private JLabel label1, lb_sugestao, lb_ranking, lb_diversao, lb_jogabilidade, lb_dificuldade; 
	private JTextArea txtAreaSugestao;
    private JButton bt_continuar; 
    private JScrollPane areaScrollPane;
    private String idioma;
    
	public FormularioAvaliacaoTelas() {
		
		dados.setAdm("Willian");
		VetorTelasParaTeste telas = new VetorTelasParaTeste();
		try {
			telas = telas.carregaVetorTelas("vetorTelas");
			telas.remove(0);
			telas.salvaVetorTelas("vetorTelas");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		//dados.setIdioma("Ingles");
		//this.setLocationRelativeTo(null); //Centraliza frame
		setSize(640, 480); 
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); 	
		
		/*
		idioma = dados.getIdioma();
		
		if(dados.getIdioma() == "Ingles"){
			
			//setTitle("The questionnaire on the Game");  
			setTitle("Automatic Content Generation");
			
			label1             = new JLabel("<html><font size=5>Rate 1-7 for each statement, where:</font><br>" +
					"(1) Strongly disagree - (2) Disagree - (3) Disagree somewhat - (4) Neither agree nor disagree - <br>" +
					"(5) Agree somewhat - (6) Agree - (7) Strongly agree"); 
	        //label2             = new JLabel("Where: (1)Strongly disagree - (2)Disagree - (3)Disagree somewhat - (4)Neither agree nor disagree - (5)Agree somewhat - (6)Agree - (7)Strongly agree");
	        lb_diversao        = new JLabel("This level is fun.....................................................................");
	        lb_jogabilidade       = new JLabel("This level is frustrating.....................................................................");
	        //lb_desafiador      = new JLabel("This level is challenging.....................................................................");
	        lb_dificuldade     = new JLabel("This level is very difficult.....................................................................");	        
	        //lb_ranking            = new JLabel("What grade would you give to this level? ");
	        lb_sugestao        = new JLabel("Suggestions/ Opinions: ");
	        
	        bt_continuar       = new JButton("Continue Playing"); 
	        //bt_fechar       = new JButton("Quit");
			
		}*/
		//else{			
	        setTitle("Mario MiniFase");   
	        
	        label1             = new JLabel("<html><b><font size=4>De 1 a 9, avalie a tela quanto as questões abaixo:</font></b></html>");
	        lb_diversao        = new JLabel("Diversão............................................................................");
	        lb_jogabilidade    = new JLabel("Jogabilidade/Visual........................................................................");
	        lb_dificuldade     = new JLabel("Dificuldade.........................................................................");	        
	        lb_sugestao        = new JLabel("Sugestões/Opiniões (opcional): ");
	        lb_ranking         = new JLabel(textoRanking());
	        
	        
	        bt_continuar       = new JButton("Jogar proxima tela");
	        //bt_fechar       = new JButton("Fechar");
		//}
	       
	        //Painel de Diversao
	        diversaoPane = new ClassRadioButtons("");
	        
	        //Painel de Frustacao
			jogabilidadePane = new ClassRadioButtons("");
						
			//Painel de Dificuldade
			dificuldadePane = new ClassRadioButtons("");
			
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
	        getContentPane().add(lb_jogabilidade);
	        getContentPane().add(jogabilidadePane);
	        getContentPane().add(lb_dificuldade);
	        getContentPane().add(dificuldadePane);	        
	        getContentPane().add(lb_sugestao);
	        getContentPane().add(areaScrollPane);
	        getContentPane().add(lb_ranking);
	        	         
	        //getContentPane().add(cb_nota); 
	        
	             
	        getContentPane().add(bt_continuar); 
	        //getContentPane().add(bt_fechar);  
	  
	        
	        //label1         .setFont(getFont());	        
	        //lb_diversao    .setFont(getFont());        
	        //lb_jogabilidade.setFont(getFont());	        
	        //lb_dificuldade .setFont(getFont());	        
	        //lb_sugestao    .setFont(getFont());	        
	        lb_ranking     .setFont(getFont());
	        
	        label1              .setBounds(10, 0, 600, 20);	        
	        lb_diversao         .setBounds(30, 30, 210, 30);
	        diversaoPane        .setBounds(190, 30, 450, 30);
	        lb_jogabilidade     .setBounds(30, 55, 210, 30);
	        jogabilidadePane    .setBounds(190, 55, 450, 30);
	        lb_dificuldade      .setBounds(30, 80, 210, 30);
	        dificuldadePane     .setBounds(190, 80, 450, 30);
	        lb_sugestao         .setBounds(30, 110, 180, 20);
	        areaScrollPane      .setBounds(220, 115, 380, 80);
	        lb_ranking          .setBounds(15, 165, 600, 230);
	  
	        bt_continuar        .setBounds(250, 400, 200, 30);  
	       // bt_fechar       .setBounds(30,370,80, 30);  
	        
	        //bt_salvar.setMnemonic(KeyEvent.VK_D);
	        bt_continuar.setActionCommand("continuar");
	          	        
	        
	        
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
        
    }  
  
    public String textoRanking(){
    	String texto = "";
    	ArrayList<String> resultado;
    	RankingJogador rankingJogador = new RankingJogador();
    	try {
			resultado = rankingJogador.copiaRanking(dados.getUsuario());
    		//resultado = rankingJogador.copiaRanking("user2009224431-18-10-2014-14h17m32s721ms");
			
			if(resultado.size()>1){
				return "<html>NÃO FOI POSSÍVEL MONTAR O RANKING! Provavelmente há algum problema com o SERVIDOR</html>";				
			}
			
			rankingJogador.separaRanking(resultado.get(0));
			
			texto  = "<html>" +
					"<h2>Rankings</h2>";
			if(rankingJogador.num_jogadores > 1)
				texto += "Até o momento, " + rankingJogador.num_jogadores + " pessoas já jogaram.<br>";
			else if(rankingJogador.num_jogadores == 1)
				texto += "Até o momento, apenas 1 pessoa jogou.<br>";
			else{
				texto += "Ainda não há um Ranking. Você é o primeiro a jogar.<br></html>";
				return texto;
			}
				
			if(rankingJogador.partidas_total_jog == 0){
				texto += "<br>Está ainda é sua primeira fase.<br><br><b>Completude</b>: O 1º colocado jogou ";
				
				//Fases
				if(rankingJogador.partidas_1_total == 1)
					texto += "1 fase.<br>";
				else
					texto += rankingJogador.partidas_1_total + " fases.<br>";
				
				
				//Mortes
				if(rankingJogador.mortes_1_mortes == 0)
					texto +="<br><b>Mortes</b>: O 1º colocado não morreu e jogou ";
				else if(rankingJogador.mortes_1_mortes == 1)
					texto +="<br><b>Mortes</b>: O 1º colocado morreu 1 vez em ";
				else
					texto +="<br><b>Mortes</b>: O 1º colocado morreu " + rankingJogador.mortes_1_mortes + " vezes em ";
				
				if(rankingJogador.mortes_1_fases == 1)
					texto += "1 fase.<br>";
				else
					texto += rankingJogador.mortes_1_fases + " fases.<br>";
				
			//Moedas ******************************************************************
				if(rankingJogador.moedas_1_moedas == 0){
					texto += "<br><b>Moedas</b>: Ninguém coletou moedas ainda. <br>";				
				}
				else{
					if(rankingJogador.moedas_1_moedas == 1)				
						texto += "<br><b>Moedas</b>: O 1º colocado coletou 1 moeda em ";
					else
						texto += "<br><b>Moedas</b>: O 1º colocado coletou " + rankingJogador.moedas_1_moedas + " moedas em ";
				
					if(rankingJogador.moedas_1_fases == 1)
						texto += "1 fase.<br>";
					else
						texto += rankingJogador.moedas_1_fases + " fases.<br>";
				}
				
				
			//Inimigos ******************************************************************
				if(rankingJogador.inimigos_1_inimigos == 0)
					texto += "<br><b>Inimigos Mortos</b>: Ninguém matou inimigos ainda.<br> ";
				else{
					if(rankingJogador.inimigos_1_inimigos == 1)
						texto += "<br><b>Inimigos Mortos</b>: O 1º colocado matou 1 inimigo em ";
					else					
						texto += "<br><b>Inimigos Mortos</b>: O 1º colocado matou " + rankingJogador.inimigos_1_inimigos + " inimigos em ";
					
					if(rankingJogador.inimigos_1_fases == 1)
						texto += "1 fase.";
					else
						texto += rankingJogador.inimigos_1_fases + " fases.";
					
					texto += "</html>";
				}
				
			}
			
			
			//Numero de Fases
			if(rankingJogador.partidas_pos_jog == 1){
				texto += "<br><b>Completude</b>: Você é o "+ rankingJogador.partidas_pos_jog+"º colocado. Você já jogou ";
			}else if(rankingJogador.partidas_total_jog == 1)
				texto += "<br><b>Completude</b>: Você é o "+ rankingJogador.partidas_pos_jog+"º colocado. Jogou apenas 1 fase e o 1º jogou ";
			else
				texto += "<br><b>Completude</b>: Você é o "+ rankingJogador.partidas_pos_jog+"º colocado. Já jogou " + rankingJogador.partidas_total_jog + " fases e o 1º jogou ";
			 
			if(rankingJogador.partidas_1_total == 1)
				texto += "1 fase.<br>";
			else
				texto += rankingJogador.partidas_1_total + " fases.<br>";
			
			//Mortes		
			texto += "<br><b>Mortes</b>: Você é o " + rankingJogador.mortes_pos_jog + "º colocado. ";
			if(rankingJogador.mortes_total_jog == 0)
				texto += "Você ainda não morreu.";
			else if(rankingJogador.mortes_total_jog == 1)
				texto += "Você morreu 1 vez.";
			else
				texto += "Você morreu " + rankingJogador.mortes_total_jog + " vezes.";
			if(rankingJogador.mortes_pos_jog != 1){
				if(rankingJogador.mortes_1_mortes == 0)
					texto += " O 1º não morreu em ";
				else
					if(rankingJogador.mortes_1_mortes == 1)
						texto +=" O 1º morreu 1 vez em <br>";
					else 
						texto +=" O 1º morreu " + rankingJogador.mortes_1_mortes + " vezes em ";
				
				if(rankingJogador.mortes_1_fases == 1)
					texto +=" 1 fase. <br>";
				else
					texto += rankingJogador.mortes_1_fases + " fases. <br>";
			}
			else{
				texto += "<br>";
			}
			
			//Moedas			
			if(rankingJogador.moedas_total_jog == 0)
				texto += "<br><b>Moedas</b>: Você ainda não coletou moedas.";
			else {
				texto +="<br><b>Moedas</b>: Você é o " + rankingJogador.moedas_pos_jogador + "º colocado. ";			
				if(rankingJogador.moedas_total_jog == 1)
					texto += " Você coletou 1 moeda.";
				else
					texto += " Você coletou " + rankingJogador.moedas_total_jog + " moedas.";
			}
			
			if(rankingJogador.moedas_pos_jogador != 1){
				texto += " O 1º coletou " + rankingJogador.moedas_1_moedas + " em ";
				
				if(rankingJogador.moedas_1_fases == 1)
					texto += "1 fase.<br>";
				else
					texto += rankingJogador.moedas_1_fases + ".<br>";
			}
			else
				texto += "<br>";
					
			
			//Inimigos
			if(rankingJogador.inimigos_total_jog == 0)
				texto += "<br><b>Inimigos Mortos</b>: Você ainda não matou nenhum inimigo.";
			else {
				texto +="<br><b>Inimigos Mortos</b>: Você é o " + rankingJogador.inimigos_pos_jogador + "º colocado.";
				if(rankingJogador.inimigos_total_jog == 1){
					texto += " Você matou 1 inimigo. ";
				}else{
					texto +=  "Você matou " + rankingJogador.inimigos_total_jog + " inimigos. ";
				} 
			}
			
			if(rankingJogador.inimigos_pos_jogador != 1){
				texto += " O 1º colocado matou " + rankingJogador.inimigos_1_inimigos + " em ";	
				if(rankingJogador.inimigos_1_fases == 1)
					texto += "1 fase.";
				else
					texto += rankingJogador.inimigos_1_fases + " fases.";
			}
			
			texto +="</html>";
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "<html><h1>NÃO FOI POSSÍVEL MONTAR O RANKING!</h1></html>";
		}
    	
    	return texto;
    }	
	
	//@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == bt_continuar) {
			
			if(diversaoPane.opcao_selecionada == -1 ||  jogabilidadePane.opcao_selecionada == -1 ||  
					dificuldadePane.opcao_selecionada == -1 ){
				if(dados.getIdioma() == "Ingles")
					JOptionPane.showMessageDialog(null, "All options must be selected" ,"Caution!", JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Todas as opçoes precisam estar marcadas" ,"Atenção!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			dados.setGamePlay(); //Infomações sobre o usuário
			dados.setNumMortes(VariaveisGlobais.num_mortes);
			
			dados.setQuestionarioFinal(diversaoPane.opcao_selecionada, jogabilidadePane.opcao_selecionada, 
					dificuldadePane.opcao_selecionada, txtAreaSugestao.getText());
						
			dados.salvarArquivo();
			/*System.out.println("Dados salvos: \n" +
					"tela: " + dados.getTela() + "\n" +
					"diversao: " + dados.getDiversao() + "\n" +
					"jogabilidade: " + dados.getJogabilidade() + "\n" +
					"dificuldade: " + dados.getDificuldade() + "\n" +
					"sugestoes: " +  dados.getSugestao() + "\n"
					);*/
			
			try {
				dados.enviarDadosServidor();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			new Controle().InciarJogo();
			this.dispose() ;
		}
	}
	
	public void setJFrames(JFrame form)
    {    	
    	this.form = form;
    }
	
	public boolean fechar(){
		if(diversaoPane.opcao_selecionada != -1 &&  jogabilidadePane.opcao_selecionada != -1 &&
				 dificuldadePane.opcao_selecionada != -1 ){
			
			dados.setQuestionarioFinal(diversaoPane.opcao_selecionada, jogabilidadePane.opcao_selecionada, 
					dificuldadePane.opcao_selecionada, txtAreaSugestao.getText());			
			
			return true;
		}
		else
			return false;
	}
	
	public static void main(String[] args) {
		
		//new FormularioFinal().setVisible(true);
		//JFrame form = new FormularioFinal();
		final FormularioAvaliacaoTelas formulario = new FormularioAvaliacaoTelas();
		
		formulario.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  
        
        //Adaptador para o fechamento da janela, matando o processo  
		formulario.addWindowListener(new WindowAdapter()  
        {  
            @SuppressWarnings("static-access")
			public void windowClosing (WindowEvent e)  
            {  
            	
            	if(formulario.idioma == "Portugues" || formulario.idioma == null){
            		if(formulario.fechar()){
            			Object[] options = { "<html>i) cansaço</html>", "<html>ii) tédio</html>", "<html>iii) preocupação com <br>a qualidade das marcações</html>",
							"<html>iv) por outra<br>atividade</html>"};    
				    	int n = JOptionPane.showOptionDialog(null,    
				                     "Por que resolveu fechar? ",    
				                     "Fechar" , JOptionPane.YES_NO_OPTION,    
				                     JOptionPane.QUESTION_MESSAGE, null, options, null);				    	
				    	
				    	DadosAvaliacaoTelas d = DadosAvaliacaoTelas.getInstancia();
				    	d.setMotivoFechar(n);
				    	//System.out.println("Você escolheu a opção " + d.getMotivoFechar());
				    	
				    	d.setGamePlay(); //Infomações sobre o usuário
						d.setNumMortes(VariaveisGlobais.num_mortes);						
						
				    	d.salvarArquivo();
						try {
							d.enviarDadosServidor();
						} catch (IOException ioe) {
							// TODO Auto-generated catch block
							ioe.printStackTrace();
						}
						formulario.dispose();
				    }else{
				    	JOptionPane.showMessageDialog(null, "Por favor, selecione as opções antes de fechar" ,"Atenção!", JOptionPane.WARNING_MESSAGE);
				    	return;
				    }
				}else{
					if(formulario.idioma == "Ingles"){
						Object[] options = { "yes", "no" };    
					    int n = JOptionPane.showOptionDialog(null,    
					                     "Are you sure you want to quit? ",    
					                     "Quit" , JOptionPane.YES_NO_OPTION,    
					                     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					    if(n == 0){					    	
					    	formulario.dispose();
						}
					}
				}  
            }  
        });
		
		formulario.setLocationRelativeTo(null); //Centraliza frame
		formulario.setVisible(true);
    }
}

