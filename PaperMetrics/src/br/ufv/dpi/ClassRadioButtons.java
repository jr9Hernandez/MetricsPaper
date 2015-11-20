package br.ufv.dpi;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.security.auth.callback.TextInputCallback;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/*
 * Classe para automatizar a criação de um JFrame contendo as opçoes de 0 - 9 
 * em forma de RadioButtons 
*/

//public class ClassRadioButtons extends JFrame implements ActionListener{
public class ClassRadioButtons extends JPanel implements ActionListener{
	
	public int opcao_selecionada = -1;
	private JLabel labelpergunta, textoUm, textoDois;
	//private JRadioButton zeroButton   = new JRadioButton("0");
	private JRadioButton umButton;
	private JRadioButton doisButton;
	private JRadioButton tresButton;
	private JRadioButton quatroButton;
	private JRadioButton cincoButton;
	private JRadioButton seisButton;
	private JRadioButton seteButton;
	private JRadioButton oitoButton;
	private JRadioButton noveButton;
	//private JRadioButton dezButton    = new JRadioButton("10");
	
    ButtonGroup buttonGroup = new ButtonGroup();
    
	public ClassRadioButtons(String texto){	
		
		umButton     = new JRadioButton("1");
		doisButton   = new JRadioButton("2");
		tresButton   = new JRadioButton("3");
		quatroButton = new JRadioButton("4");
		cincoButton  = new JRadioButton("5");
		seisButton   = new JRadioButton("6");
		seteButton   = new JRadioButton("7");
		//oitoButton   = new JRadioButton("8");
		//noveButton   = new JRadioButton("9");
		
		labelpergunta = new JLabel(texto);    
		textoUm  = new JLabel("<html>Concordo<br>totalmente</html>");
		textoDois  = new JLabel("<html>Discordo<br>totalmente</hmtl>");
		//textoUm  = new JLabel("<html>Strongly<br>agree</html>");
		textoUm.setFont(getFont());
		//textoDois  = new JLabel("<html>Strongly<br>disagree</hmtl>");
		textoDois.setFont(getFont());
		        
        //buttonGroup.add(zeroButton);
        buttonGroup.add(umButton);
        buttonGroup.add(doisButton);
	    buttonGroup.add(tresButton);
	    buttonGroup.add(quatroButton);
	    buttonGroup.add(cincoButton);
        buttonGroup.add(seisButton);
        buttonGroup.add(seteButton);
	    //buttonGroup.add(oitoButton);
	    //buttonGroup.add(noveButton);
	    
        //zeroButton  .addActionListener(this);   zeroButton.setActionCommand("0");
        umButton    .addActionListener(this);   umButton.setActionCommand("1");
        doisButton  .addActionListener(this);   doisButton.setActionCommand("2");
	    tresButton  .addActionListener(this);   tresButton.setActionCommand("3");
	    quatroButton.addActionListener(this);   quatroButton.setActionCommand("4");
	    cincoButton .addActionListener(this);   cincoButton.setActionCommand("5");
        seisButton  .addActionListener(this);   seisButton.setActionCommand("6");
        seteButton  .addActionListener(this);   seteButton.setActionCommand("7");
	    //oitoButton  .addActionListener(this);   oitoButton.setActionCommand("8");
	    //noveButton  .addActionListener(this);   noveButton.setActionCommand("9");
	    //dezButton  .addActionListener(this);   dezButton.setActionCommand("10");
	   
	    //add(labelpergunta);
        add(textoUm);
	    //add(zeroButton);
        add(umButton);
        add(doisButton);
	    add(tresButton);
	    add(quatroButton);
	    add(cincoButton);
        add(seisButton);
        add(seteButton);
	    //add(oitoButton);
	    //add(noveButton);  
        //add(dezButton);
        add(textoDois);    
        
	}	
	
	public ClassRadioButtons(String texto, int numeroOpcoes){	
		
		umButton     = new JRadioButton("A");
		doisButton   = new JRadioButton("B");
		
		labelpergunta = new JLabel(texto);    
		//casoUm  = new JLabel("<html>Discordo<br>totalmente</hmtl>");
		//casoDois  = new JLabel("<html>Concordo<br>totalmente</html>");
		        
        //buttonGroup.add(zeroButton);
        buttonGroup.add(umButton);
        buttonGroup.add(doisButton);
	    
        //zeroButton  .addActionListener(this);   zeroButton.setActionCommand("0");
        umButton    .addActionListener(this);   umButton.setActionCommand("1");
        doisButton  .addActionListener(this);   doisButton.setActionCommand("2");
	   
	    //add(labelpergunta);
        //add(casoUm);
	    //add(zeroButton);
        add(umButton);
        add(doisButton);
	    
	    
	    if(numeroOpcoes > 2){    	

			tresButton   = new JRadioButton("Ambas");
			quatroButton = new JRadioButton("Nenhuma");
		    
			buttonGroup.add(tresButton);
		    buttonGroup.add(quatroButton);			
		    
		    tresButton  .addActionListener(this);   tresButton.setActionCommand("3");
		    quatroButton.addActionListener(this);   quatroButton.setActionCommand("4");
		    
		    add(tresButton);
		    add(quatroButton);   
	    	
	    }
	}
	
	//@Override
	public void actionPerformed(ActionEvent e) {		// TODO Auto-generated method stub
		
		/*
		switch (e.getActionCommand()) {
		/*
		case "0":
			//JOptionPane.showMessageDialog(null,"Selecionou o ZERO","", JOptionPane.INFORMATION_MESSAGE);
			opcao_selecionada = 0;
			break;*	
		case "1":
			//JOptionPane.showMessageDialog(null,"Selecionou o UM","", JOptionPane.INFORMATION_MESSAGE);
			opcao_selecionada = 1;
			break;
		case "2":
			//JOptionPane.showMessageDialog(null,"Voce nao selecionou nem 1 e nem 0","", JOptionPane.INFORMATION_MESSAGE);
			opcao_selecionada = 2;
			break;
		case "3":
			opcao_selecionada = 3;
			break;
		case "4":
			opcao_selecionada = 4;
			break;
		case "5":
			opcao_selecionada = 5;
			break;
		case "6":
			opcao_selecionada = 6;
			break;
		case "7":
			opcao_selecionada = 7;
			break;
		case "8":
			opcao_selecionada = 8;
			break;
		case "9":
			opcao_selecionada = 9;
			break;
		case "10":
			opcao_selecionada = 9;
			break; 
			
		}
		*/
		
	if(e.getActionCommand() == "1"){
		//JOptionPane.showMessageDialog(null,"Selecionou o UM","", JOptionPane.INFORMATION_MESSAGE);
		opcao_selecionada = 1;
		return;
	}
		
	if(e.getActionCommand() == "2"){
		//JOptionPane.showMessageDialog(null,"Voce nao selecionou nem 1 e nem 0","", JOptionPane.INFORMATION_MESSAGE);
		opcao_selecionada = 2;
		return;
	}
		
	if(e.getActionCommand() == "3"){
		opcao_selecionada = 3;
		return;
	}
		
	if(e.getActionCommand() == "4"){
		opcao_selecionada = 4;
		return;
	}
		
	if(e.getActionCommand() == "5"){
		opcao_selecionada = 5;
		return;
	}
		
	if(e.getActionCommand() == "6"){
		opcao_selecionada = 6;
		return;
	}
		
	if(e.getActionCommand() == "7"){
		opcao_selecionada = 7;
		return;
	}
		
	}
		
	public void addRadio(JPanel panel){
		//panel.add(zeroButton);
		panel.add(umButton);
		panel.add(doisButton);
		panel.add(tresButton);
		panel.add(quatroButton);
		panel.add(cincoButton);
		panel.add(seisButton);
		panel.add(seteButton);
		panel.add(oitoButton);
		panel.add(noveButton);
		//panel.add(dezButton);
	}
	

}
