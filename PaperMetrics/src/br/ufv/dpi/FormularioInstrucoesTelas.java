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
				"Este é um sistema que serve para avaliar telas de um jogo do Mario. São pequenas telas que deverão ser avaliadas em 3 quesitos, em uma escala de 1 a 9, no final de cada rodada:<br>" +
				"<ol>" +
					"<li><b>Diversão</b>, onde 1 não é nada divertido e 9 muito divertido.</li>" +
					"<li><b>Jogabilidade/Visual</b>, 1 onde os elementos não fazem sentido, ou são impossíveis de alcançar e 9 quando é muito bom de jogar e o visual é muito bom.</li>" +
					"<li><b>Dificuldade</b>, onde 1 é muito fácil e 9 é muito difícil.</li>" +
				"</ol>" +
				"<br>Em seguida iremos mostrar a avaliação de 7 fases distintas. Mostraremos uma imagem da fase, as notas e justificaremos nossas avaliações. " +
				"<br>A ideia desses exemplos é de mostrar a você o que esperamos de sua avaliação. Por favor <b>LEIA</b> os exemplos com <b>atenção</b>." +
				"</html>",
			"<html><b>Diversão: 1</b> - Não é divertido jogar uma tela com alguns canos e nenhum desafio." +
				"<br><br><b>Jogabilidade/visual: 1</b> - Existe uma plataforma na parte de cima da figura que Mario não consegue atingir. O terreno possui degraus desnecessários e os canos não parecem estar relacionados.<br>" +
				"<b><br>Dificuldade:</b> 1</b> - Não existe nenhuma ameaça ao jogador.</html>", 
			"<html><b>Diversão: 2</b> - A tela não apresenta nenhum desafio, mas existem moedas para coletar e caixas para abrir." +
					"<br><br><b>Jogabilidade/visual: 4 </b> - Como pontos positivos, todos os elementos da tela são atingíveis e as montanhas formam um degrau para o Mario alcançar as caixas e moedas. No entanto, as caixas e moedas não estão alinhadas com a montanha maior, o que deteriora o visual da tela." +
					"<br><br><b>Dificuldade: 1</b>  - Não existe nenhuma ameaça ao jogador.</html>", 
			"<html><b>Diversão: 1</b> - A tela não apresenta nenhum desafio e não existe nenhum objeto para coletar." +
					"<br><br><b>Jogabilidade/visual: 1</b> - As plataformas suspensas não podem ser atingidas." +
					"<br><br><b>Dificuldade: 1</b>  - Não existe nenhuma ameaça ao jogador.</html>",
			"<html><b>Diversão: 2</b> - A tela não apresenta nenhum desafio interessante." +
					"<br><br><b>Jogabilidade/visual: 1</b> - Os elementos não se complementam, isto é, o cano suspenso e a plataforma não interferem na jogabilidade." +
					"<br><br><b>Dificuldade: 3</b>  - O buraco relativamente largo oferece perigo ao personagem.</html>",
			"<html><b>Diversão: 6</b> - A tela apresenta desafios interessantes. Por exemplo, a tartaruga alada desce da plataforma, oferecendo perigo ao personagem. Dois inimigos abaixo contribuem para a diversão da tela." +
					"<br><br><b>Jogabilidade/visual: 7</b> - Os elementos se complementam bem nessa tela. Por exemplo, a plataforma segura a tartaruga alada por um tempo antes de ela descer atrás do Mario. Uma das caixas pode conter um cogumelo que o aumentará de tamanho e, assim, ele não poderá passar pelo bloco azul facilmente." +
					"<br><br><b>Dificuldade: 5 </b> - Embora existam 3 inimigos e um deles seja alado, o jogador pode tentar evita-los ao esperar na plataforma no centro da tela.</html>",
			"<html><b>Diversão: 9</b> - A tela apresenta desafios interessantes. Por exemplo, ao tentar coletar as moedas e a caixa na montanha, o personagem estará na linha de fogo do canhão." +
					"<br><br><b>Jogabilidade/visual: 9</b> - Os elementos se complementam excepcionalmente bem nessa tela. Por exemplo, o personagem entra na linha de fogo do canhão ao tentar alcançar as moedas. Os lados da depressão no cenário estão perfeitamente alinhados, causando uma ótima impressão no visual da tela." +
					"<br><br><b>Dificuldade: 6 </b> - O conjunto formado por inimigos e canhão elevam a dificuldade da tela, mas não ao ponto de deixa-la muito difícil. Um jogador poderia, por exemplo, evitar o canhão e os inimigos e seguir livremente para a próxima etapa do jogo.</html>",
			"<html><b>Diversão: 4</b> - A tela apresenta o desafio de lidar com três inimigos alados e dois terrestres. Para alguns jogadores a tela é tão difícil que não será divertida." +
					"<br><br><b>Jogabilidade/visual: 7</b> - Os elementos estão bem distribuídos oferecendo chances ao jogador." +
					"<br><br><b>Dificuldade: 9</b> - A tela é extremamente difícil. Os três inimigos alados irão atrás do Mario mesmo se o jogador decidir esperar. Além disso, o inimigo alado do meio só é destruído caso Mario utilize um casco capturado de uma tartaruga.</html>",
			"<html><center><h2>RANKING</h2></center>" +
			"Após jogar uma fase, um <b>ranking</b> será mostrado junto com o Questionário." +
			"<br>Na montagem deste ranking <b>não</b> é levado em consideração os dados da ultima fase jogada. Estes dados apenas serão contabilizados depois de jogar a próxima fase." +
			"<br><br>O ranking possui 4 modalidades: " +
			"<ul>" +
				"<li><b>Completude</b>: Número de fases completadas. Quanto <b>mais fases completar</b>, mais bem colocado você estará.</li>" +
				"<li><b>Mortes</b>: Razão do número de vezes que o personagem Mario <b>morreu</b>, pelo número de fases jogadas.</li>" +
				"<li><b>Moedas</b>: Razão do número de <b>moedas</b> coletadas, pelo número de fases jogadas. As moedas coletadas ao destruir blocos também são contabilizadas.</li>" +
				"<li><b>Inimigos Mortos</b>: Razão do número de <b>inimigos que você matou</b>, pelo número de fases jogadas.</li>" +
			"</ul>" +
			"<br>Quanto <b>mais vezes jogar, mais moedas coletar, mais inimigos matar e menos deixar o Mario morrer</b>, mais bem colocado estará no RANKING." +
			"<br><br><b>Atenção</b>" +
			"<br><br>O ranking é gerado a cada vez que você joga. Não é acumulativo. Ou seja, cada vez que abrir a aplicação, será um novo usuário no ranking.</html>"};
	
	//private String[] textoButton = {"Texto tela 2", "Texto tela 3", "Texto tela 4", "Texto tela 5", "Texto tela 6", "Texto tela 7", "Texto tela 1"};
	private String [] imgs = {"tela6.png", "tela1.png", "tela2.png", "tela3.png", "tela4.png", "tela5.png", "tela6.png", "tela7.png", "tela6.png"};
	private Image img_original, img_modificada;
	private BufferedImage bi;
	private final int NUM_TELAS_INSTRUCOES = 9;
	
	
	
	public FormularioInstrucoesTelas(){
		
		setSize(640, 480);
		setTitle("Instruções");	
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
