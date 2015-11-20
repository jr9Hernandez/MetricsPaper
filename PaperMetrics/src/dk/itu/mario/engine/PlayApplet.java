package dk.itu.mario.engine;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

/**

 * <p>Title: </p>

 *

 * <p>Description: </p>

 *

 * <p>Copyright: Copyright (c) 2010</p>

 *

 * <p>Company: </p>

 *

 * @author not attributable

 * @version 1.0

 */





public class PlayApplet extends JApplet{

    private boolean started = false;

    private static final long serialVersionUID = -2238077255106243788L;



    //Construct the applet

    public PlayApplet() {
    	

    }



    public String getAppletInfo() {

        return "Applet Information";

    }





    public void start()

    {

        if (!started)

        {

            started = true;

            mario = new MarioComponent(640, 480,true);
            //mario = new MarioComponent(1280, 960,false);

            mario.requestFocus();

            addKeyListener(mario);

            setContentPane(mario);

            setFocusable(true);

            SwingUtilities.invokeLater(new Runnable() {  

                public void run() {  

                      

                    mario.requestFocusInWindow();  

                }  

            }); 

            //mario.skipCam = true;

            mario.start();

            

        }

    }



    public void init() {
    	setSize(640, 480);
    }

     
    

    public void stop()

    {

        if (started)

        {

            started = false;

            mario.stop();

            //mario.finilize();

        }

    }

    

    public void paint(Graphics g){

    	Graphics2D g2 = (Graphics2D)g;

    	String text = "Loading... Please Wait..";

    	Font font = new Font("Arial",Font.PLAIN,16);

    	Rectangle2D rec = font.getStringBounds(text,g2.getFontRenderContext());

    	

    	g.setColor(Color.BLACK);

    	g.setFont(font);

    	g.drawString(text,320-(int)rec.getWidth()/2,getHeight()/2);
    	//g.drawString(text,660,500);
    	
    }    

    
    MarioComponent mario;


}