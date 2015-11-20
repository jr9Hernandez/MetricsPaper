
package br.ufv.dpi;
 
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dk.itu.mario.engine.MarioComponent;
 
/*
 * ButtonDemo.java requires the following files:
 *   images/right.gif
 *   images/middle.gif
 *   images/left.gif
 */
public class Experimento extends JPanel
                        implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton b1;
	private JFrame game;
	private JFrame form;
	private JFrame form2;
	private DadosFormulario dados = DadosFormulario.getInstancia();
 
    public Experimento() {
    	
    	game = new JFrame("Mario Experience Showcase");
    	
        Dimension size = new Dimension(640, 480);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    	
        if(dados.getIdioma() == "Ingles")
        	b1 = new JButton("Start Game");
    	else
    		b1 = new JButton("Iniciar Jogo");
        
        b1.setActionCommand("Executar");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setMnemonic(KeyEvent.VK_D);

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
 
        b1.setToolTipText("Click this button to start running game.");

        //Add Components to this container, using the default FlowLayout.
        add(b1);
    }
    
    public void setFormFrame(JFrame f)
    {
    	this.form = f;
    }
 
    public void actionPerformed(ActionEvent e) {
    	System.out.println(e.getActionCommand());
        if ("Executar".equals(e.getActionCommand())) {
	    	MarioComponent mario = new MarioComponent(640, 480, true);
        	//MarioComponent mario = new MarioComponent(640, 480, false);
	    	//mario.setJFrames(this.game, this.form);
	    	
	    	ArrayList<JFrame> listaFrames = new ArrayList<JFrame>();
	    	form2 = new FormularioFinal();
	    	listaFrames.add(game);
	    	listaFrames.add(form);
	    	listaFrames.add(form2);
	    	
	    	mario.setJFrames(listaFrames);

	    	game.setContentPane(mario);
	    	game.setResizable(false);
	    	game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	game.pack();
	    	game.setVisible(true);
	        
	        this.form.setVisible(false);

	        mario.start();	        
        } 
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Experimento.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
 
    	DadosFormulario dados = DadosFormulario.getInstancia();
        //Create and set up the window.
        JFrame frame;
        if(dados.getIdioma() == "Ingles")
        	frame= new JFrame("Mario's Experiment");
        else
        	frame= new JFrame("Experimento Mario");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       // frame.setLocation((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2);
        frame.setLocation(0, 0);
 
        //Create and set up the content pane.
        Experimento newContentPane = new Experimento();
        newContentPane.setFormFrame(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);        
        
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
