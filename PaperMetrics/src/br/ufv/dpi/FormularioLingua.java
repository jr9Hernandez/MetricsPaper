package br.ufv.dpi;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.Normalizer.Form;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FormularioLingua implements ActionListener{
	
	//private JComboBox cb_idioma;
	private static JFrame frame;
	private JButton portugues, ingles;

	/*
	JLabel label;// label_eng;
	JComboBox<String> cb_idioma;
	JButton bt_proximo;
	
	public FormularioLingua(){
		
		setTitle("Linguagem / Language");
		setSize(640, 480);  
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("Selecione o Idioma / Select Language");
		//label_eng = new JLabel("Select Language");
		
		cb_idioma = new JComboBox();
		cb_idioma.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Português", "English"}));
		
		bt_proximo = new JButton("PROXIMO/NEXT");   bt_proximo.addActionListener(this);
		//bt_proximo       .setBounds(20,340,80, 30);  
        
        
		bt_proximo.setMnemonic(KeyEvent.VK_D);
		bt_proximo.setActionCommand("Proximo");
		
		getContentPane().setLayout(null);  
		  
        getContentPane().add(label, BorderLayout.PAGE_START);
        getContentPane().add(cb_idioma, BorderLayout.CENTER);
        getContentPane().add(bt_proximo, BorderLayout.CENTER);
        
        //add(label, BorderLayout.PAGE_START);
        //add(cb_idioma, BorderLayout.CENTER);
        //add(bt_proximo, )
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		//if (arg0.getSource() == bt_proximo) {
		String lingua = "";
		if(cb_idioma.getSelectedIndex() == 0)
			lingua = "Portugues";
		else if(cb_idioma.getSelectedIndex() == 1)
			lingua = "Ingles";
			
			DadosFormulario dados = DadosFormulario.getInstancia();	
			dados.setIdima(lingua);
			Experimento.main(null);
			this.dispose() ;
		//}
		
	}*/
	
	public void addComponentToPane(Container pane) {
		
		/*
		//Painel do JLabel
		JPanel labelPane = new JPanel();
		JLabel label = new JLabel("Selecione o Idioma / Select Language");
		labelPane.add(label);		
				
		//Painel do JComboBox
		JPanel comboBoxPane = new JPanel();
		String[] linguas = {"Português", "English"};
		cb_idioma = new JComboBox(linguas);
		cb_idioma.setEditable(false);		
        comboBoxPane.add(cb_idioma); */
		
		int largura_frame = pane.getWidth();
		int altura_frame = pane.getHeight();
        
        //Paineldo JButton
        JPanel buttonPane = new JPanel();
        //ImageIcon imgPort = new ImageIcon("icons/brasil.gif");
        portugues = new JButton("Português", new ImageIcon("img/brasil.gif"));
        portugues.addActionListener(this);
        portugues.setActionCommand("Portugues");
        
        ingles = new JButton("English", new ImageIcon("img/usa.png"));
        ingles.addActionListener(this);
        ingles.setActionCommand("Ingles");
		//JButton bt_proximo = new JButton("Próximo/Next");   
		//bt_proximo.addActionListener(this);
		//bt_proximo.setMnemonic(KeyEvent.VK_D);
		//bt_proximo.setActionCommand("Proximo");
		//buttonPane.add(bt_proximo);
        //portugues .setBounds(440, 140, 150, 25);
        //ingles    .setBounds(440, 140, 150, 25);
		buttonPane.add(portugues);
		buttonPane.add(ingles);
		
		int largura_painel = buttonPane.getWidth();
		int altura_painel = buttonPane.getHeight();
		
		int novoX = (largura_frame - largura_painel)/2;
		int novoY = (altura_frame - altura_painel)/2;
		
		//pane.add(labelPane, BorderLayout.PAGE_START);
        //pane.add(comboBoxPane, BorderLayout.CENTER);
        //pane.add(buttonPane, BorderLayout.PAGE_END);
        
		//pane.add(buttonPane, BorderLayout.CENTER);		
		pane.add(buttonPane);
		//buttonPane.setLocation(novoX, novoY);
    }
	
	public static void createAndShowGUI(){
		
		frame = new JFrame("Linguagem / Language");
		frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        FormularioLingua form = new FormularioLingua();
        form.addComponentToPane(frame.getContentPane());
        
        //Display the window.
        //frame.pack();
        frame.setLocationRelativeTo(null); //Centralizar frame 
        frame.setVisible(true);
		//new FormularioLingua().setVisible(true);
	}
	
	public static void main(String [] args){		
		//new FormularioLingua().setVisible(true);
		
		try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

	
	//@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String idioma = "";
		/*
		if(cb_idioma.getSelectedIndex() == 0){
			idioma = "Portugues";
			//JOptionPane.showMessageDialog(null,"Selecionou Portugues","Confirmação", JOptionPane.INFORMATION_MESSAGE);
		}else{
			//JOptionPane.showMessageDialog(null,"Selecionou Ingles","Confirmação", JOptionPane.INFORMATION_MESSAGE);
			idioma = "Ingles";
		}*/
		if("Portugues".equals(e.getActionCommand())){
			idioma = "Portugues";
		}
		if("Ingles".equals(e.getActionCommand())){
			idioma = "Ingles";
		}
		
		DadosFormulario form = DadosFormulario.getInstancia();
		form.setIdioma(idioma);
		FormularioTermos.main(null);
		//FormularioTermosTelas.main(null);
		frame.dispose();
	}

}
