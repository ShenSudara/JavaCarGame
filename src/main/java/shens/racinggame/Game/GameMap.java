package shens.racinggame.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class GameMap  extends  JPanel implements Runnable{
       
    //Constants and variabele for the actions
    private static final float scalefacttor = 1.7f;
    private static final int gamespeed = 10;
    public static boolean pausegame = false;
    
    Rectangle[] leftlines;
    Rectangle[] rightlines;
    
    public GameMap(int width) {
        
        //for the game map pane
        this.setLayout(null);
        this.setSize(width,(int)(width * scalefacttor) -40);
        this.setBounds(0,40,width,(int)(scalefacttor * width) - 40);
        this.setOpaque(false);
        this.setFocusable(false);
        
        
        //for the road lines
        leftlines = new Rectangle[6];
        rightlines = new Rectangle[6];
        for (int i = 0; i < 6; i++) {
            leftlines[i] = new Rectangle(20,(i*120),8,80);
            rightlines[i] = new Rectangle(370,(i*120),8,80);
        }
        repaint();
    }
    
    
    @Override
    public void run() {
        //endless loop for continuos running
        while (!pausegame) {            
            //manipulate the rectangles
            for(int i = 0; i<6; i++){
                if(leftlines[i].y >= 680){
                    leftlines[i].y = -80;
                    rightlines[i].y = -80;
                }
                leftlines[i].y +=gamespeed;
                rightlines[i].y += gamespeed;
            }
            
            try {
                Thread.sleep(80);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameMap.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            repaint();
         
        }

    }
    
    @Override
    public void paint(Graphics g) {
        //drawing the road lines
        super.paint(g); 
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(Color.WHITE);
        for (int i = 0; i < 6; i++) {
            g2d.fillRect(leftlines[i].x,leftlines[i].y,leftlines[i].width,leftlines[i].height);
            g2d.fillRect(rightlines[i].x, rightlines[i].y, rightlines[i].width, rightlines[i].height);
        } 
    }
}
