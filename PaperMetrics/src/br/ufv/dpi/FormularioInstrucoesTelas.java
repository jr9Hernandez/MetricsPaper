package br.ufv.dpi;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormularioInstrucoesTelas extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton button_next, button_back;
	private JLabel label_texto, label_img;
	private int cont;
	private String[] textoLabel = {"<html><h3>MarioMiniFase</h3>" +
				"Este � um sistema que serve para avaliar telas de um jogo do Mario. S�o pequenas telas que dever�o ser avaliadas em 3 quesitos, em uma escala de 1 a 9, no final de cada rodada:<br>" +
				"<ol>" +
					"<li><b>Divers�o</b>, onde 1 n�o � nada divertido e 9 muito divertido.</li>" +
					"<li><b>Jogabilidade/Visual</b>, 1 onde os elementos n�o fazem sentido, ou s�o imposs�veis de alcan�ar e 9 quando � muito bom de jogar e o visual � muito bom.</li>" +
					"<li><b>Dificuldade</b>, onde 1 � muito f�cil e 9 � muito dif�cil.</li>" +
				"</ol>" +
				"<br>Em seguida iremos mostrar a avalia��o de 7 fases distintas. Mostraremos uma imagem da fase, as notas e justificaremos nossas avalia��es. " +
				"<br>A ideia desses exemplos � de mostrar a voc� o que esperamos de sua avalia��o. Por favor <b>LEIA</b> os exemplos com <b>aten��o</b>." +
				"</html>",
			"<html><b>Divers�o: 1</b> - N�o � divertido jogar uma tela com alguns canos e nenhum desafio." +
				"<br><br><b>Jogabilidade/visual: 1</b> - Existe uma plataforma na parte de cima da figura que Mario n�o consegue atingir. O terreno possui degraus desnecess�rios e os canos n�o parecem estar relacionados.<br>" +
				"<b><br>Dificuldade:</b> 1</b> - N�o existe nenhuma amea�a ao jogador.</html>", 
			"<html><b>Divers�o: 2</b> - A tela n�o apresenta nenhum desafio, mas existem moedas para coletar e caixas para abrir." +
					"<br><br><b>Jogabilidade/visual: 4 </b> - Como pontos positivos, todos os elementos da tela s�o ating�veis e as montanhas formam um degrau para o Mario alcan�ar as caixas e moedas. No entanto, as caixas e moedas n�o est�o alinhadas com a montanha maior, o que deteriora o visual da tela." +
					"<br><br><b>Dificuldade: 1</b>  - N�o existe nenhuma amea�a ao jogador.</html>", 
			"<html><b>Divers�o: 1</b> - A tela n�o apresenta nenhum desafio e n�o existe nenhum objeto para coletar." +
					"<br><br><b>Jogabilidade/visual: 1</b> - As plataformas suspensas n�o podem ser atingidas." +
					"<br><br><b>Dificuldade: 1</b>  - N�o existe nenhuma amea�a ao jogador.</html>",
			"<html><b>Divers�o: 2</b> - A tela n�o apresenta nenhum desafio interessante." +
					"<br><br><b>Jogabilidade/visual: 1</b> - Os elementos n�o se complementam, isto �, o cano suspenso e a plataforma n�o interferem na jogabilidade." +
					"<br><br><b>Dificuldade: 3</b>  - O buraco relativamente largo oferece perigo ao personagem.</html>",
			"<html><b>Divers�o: 6</b> - A tela apresenta desafios interessantes. Por exemplo, a tartaruga alada desce da plataforma, oferecendo perigo ao personagem. Dois inimigos abaixo contribuem para a divers�o da tela." +
					"<br><br><b>Jogabilidade/visual: 7</b> - Os elementos se complementam bem nessa tela. Por exemplo, a plataforma segura a tartaruga alada por um tempo antes de ela descer atr�s do Mario. Uma das caixas pode conter um cogumelo que o aumentar� de tamanho e, assim, ele n�o poder� passar pelo bloco azul facilmente." +
					"<br><br><b>Dificuldade: 5 </b> - Embora existam 3 inimigos e um deles seja alado, o jogador pode tentar evita-los ao esperar na plataforma no centro da tela.</html>",
			"<html><b>Divers�o: 9</b> - A tela apresenta desafios interessantes. Por exemplo, ao tentar coletar as moedas e a caixa na montanha, o personagem estar� na linha de fogo do canh�o." +
					"<br><br><b>Jogabilidade/visual: 9</b> - Os elementos se complementam excepcionalmente bem nessa tela. Por exemplo, o personagem entra na linha de fogo do canh�o ao tentar alcan�ar as moedas. Os lados da depress�o no cen�rio est�o perfeitamente alinhados, causando uma �tima impress�o no visual da tela." +
					"<br><br><b>Dificuldade: 6 </b> - O conjunto formado por inimigos e canh�o elevam a dificuldade da tela, mas n�o ao ponto de deixa-la muito dif�cil. Um jogador poderia, por exemplo, evitar o canh�o e os inimigos e seguir livremente para a pr�xima etapa do jogo.</html>",
			"<html><b>Divers�o: 4</b> - A tela apresenta o desafio de lidar com tr�s inimigos alados e dois terrestres. Para alguns jogadores a tela � t�o dif�cil que n�o ser� divertida." +
					"<br><br><b>Jogabilidade/visual: 7</b> - Os elementos est�o bem distribu�dos oferecendo chances ao jogador." +
					"<br><br><b>Dificuldade: 9</b> - A tela � extremamente dif�cil. Os tr�s inimigos alados ir�o atr�s do Mario mesmo se o jogador decidir esperar. Al�m disso, o inimigo alado do meio s� � destru�do caso Mario utilize um casco capturado de uma tartaruga.</html>",
			"<html><center><h2>RANKING</h2></center>" +
			"Ap�s jogar uma fase, um <b>ranking</b> ser� mostrado junto com o Question�rio." +
			"<br>Na montagem deste ranking <b>n�o</b> � levado em considera��o os dados da ultima fase jogada. Estes dados apenas ser�o contabilizados depois de jogar a pr�xima fase." +
			"<br><br>O ranking possui 4 modalidades: " +
			"<ul>" +
				"<li><b>Completude</b>: N�mero de fases completadas. Quanto <b>mais fases completar</b>, mais bem colocado voc� estar�.</li>" +
				"<li><b>Mortes</b>: Raz�o do n�mero de vezes que o personagem Mario <b>morreu</b>, pelo n�mero de fases jogadas.</li>" +
				"<li><b>Moedas</b>: Raz�o do n�mero de <b>moedas</b> coletadas, pelo n�mero de fases jogadas. As moedas coletadas ao destruir blocos tamb�m s�o contabilizadas.</li>" +
				"<li><b>Inimigos Mortos</b>: Raz�o do n�mero de <b>inimigos que voc� matou</b>, pelo n�mero de fases jogadas.</li>" +
			"</ul>" +
			"<br>Quanto <b>mais vezes jogar, mais moedas coletar, mais inimigos matar e menos deixar o Mario morrer</b>, mais bem colocado estar� no RANKING." +
			"<br><br><b>Aten��o</b>" +
			"<br><br>O ranking � gerado a cada vez que voc� joga. N�o � acumulativo. Ou seja, cada vez que abrir a aplica��o, ser� um novo usu�rio no ranking.</html>"};
	
	//private String[] textoButton = {"Texto tela 2", "Texto tela 3", "Texto tela 4", "Texto tela 5", "Texto tela 6", "Texto tela 7", "Texto tela 1"};
	private String [] imgs = {"tela6.png", "tela1.png", "tela2.png", "tela3.png", "tela4.png", "tela5.png", "tela6.png", "tela7.png", "tela6.png"};
	private Image img_original, img_modificada;
	private BufferedImage bi;
	private final int NUM_TELAS_INSTRUCOES = 9;
	
	
	
	public FormularioInstrucoesTelas(){
		
		setSize(640, 480);
		setTitle("Instru��es");	
		setLocationRelativeTo(null);
		cont = 0;
		//label = new JLabel(new ImageIcon(""));
		label_texto = new JLabel(textoLabel[0]);
		label_texto.setFont(getFont());
		img_original = Toolkit.getDefaultToolkit().getImage("img/" + imgs[0]);		
		try {
			bi = ImageIO.read(new File("img/" + imgs[0]));
			img_modificada = img_original.getScaledInstance(bi.getWidth() - 4*bi.getWidth()/9, bi.getHeight() - 4*bi.getHeight()/9, Image.SCALE_DEFAULT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//label_img = new JLabel(new ImageIcon("img/" + imgs[cont]));
		label_img = new JLabel(new ImageIcon(img_modificada));
		button_next = new JButton("Proximo");
		button_back = new JButton("Voltar");
		button_back.setEnabled(false);
		
		//label_img.setBounds(120, 0, 354, 267);
		label_img.setBounds(160, 10, 295, 223);
		label_texto.setBounds(20, 10, 595, 300);
		button_next.setBounds(320, 405, 200, 30);
		button_back.setBounds(100, 405, 200, 30);
		
		button_next.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){                    	
                    	cont++;
                    	if(cont == NUM_TELAS_INSTRUCOES){
                    		FormularioInicialAvaliadorTelas.main(null);
                    		dispose();                    		
                    	}
                    	if(cont == 0){
                    		label_img.setBounds(160, 10, 295, 223);
                    		label_texto.setBounds(20, 10, 595, 300);
                    		getContentPane().remove(label_img);
                    		button_back.setEnabled(false);
                    	}
                    	else if(cont == 8){
                    		label_img.setBounds(160, 10, 295, 223);
                    		label_texto.setBounds(20, 5, 595, 380);
                    		getContentPane().remove(label_img);
                    		button_back.setEnabled(true);
                    	}else{
                    		label_img.setBounds(160, 10, 295, 223);
                    		label_texto.setBounds(10, 170, 600, 300);
                    		getContentPane().add(label_img);
                    		button_back.setEnabled(true);
                    	}
                    	
                    	int i = cont % NUM_TELAS_INSTRUCOES;
                    	label_texto.setText(textoLabel[i]);
                    	img_original = Toolkit.getDefaultToolkit().getImage("img/" + imgs[i]);		
                		try {
                			bi = ImageIO.read(new File("img/" + imgs[i]));
                			img_modificada = img_original.getScaledInstance(bi.getWidth() - 4*bi.getWidth()/9, bi.getHeight() - 4*bi.getHeight()/9, Image.SCALE_DEFAULT);
                		} catch (IOException e1) {
                			// TODO Auto-generated catch block
                			e1.printStackTrace();
                		}
                		label_img.setIcon(new ImageIcon(img_modificada));	
                    }
                }   
            );
		
		button_back.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                    	cont--;                    	                   		                   		
	                    int i = cont % NUM_TELAS_INSTRUCOES;
	                    label_texto.setText(textoLabel[i]);
	                    img_original = Toolkit.getDefaultToolkit().getImage("img/" + imgs[i]);		
	                	try {
	                		bi = ImageIO.read(new File("img/" + imgs[i]));
	                		img_modificada = img_original.getScaledInstance(bi.getWidth() - 4*bi.getWidth()/9, bi.getHeight() - 4*bi.getHeight()/9, Image.SCALE_DEFAULT);
	                	} catch (IOException e1) {
	                		// TODO Auto-generated catch block
	                		e1.printStackTrace();
	                	}
	                	label_img.setIcon(new ImageIcon(img_modificada));	                    	
	                	
	                	if(cont == 0){ 
	                		label_img.setBounds(160, 10, 295, 223);
	                		label_texto.setBounds(20, 10, 595, 300);
                    		getContentPane().remove(label_img);
                    		button_back.setEnabled(false);
                    	}else{
                    		label_img.setBounds(160, 10, 295, 223);
                    		label_texto.setBounds(10, 170, 600, 300);
                    		getContentPane().add(label_img);
                    		button_back.setEnabled(true);
                    	}
                    } 
                }  
            );
		
		getContentPane().setLayout(null);
		getContentPane().add(label_texto);
		//getContentPane().add(label_img);
		getContentPane().add(button_next); 
		getContentPane().add(button_back);
		
	}
	
	public static void main(String[] args){
		
		new FormularioInstrucoesTelas().setVisible(true);
	}

	//@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
