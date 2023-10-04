package shens.racinggame.Game;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
public class GamePlay extends JPanel implements Runnable{

    
    //constants and default variables
    public static boolean gamepause = false;
    public static int gamescore = 0;
    private static final float scalefactor = 1.7f;
    private static final int gamespeed = 10;
    
    //car locations and randoms
    private Point maincarlocation;
    private Point[] opponentslocations;
    private Integer[] oponentcolor;
    Random random;
    int maincarcolor;
    
  
    
    //for the car array
    ImageIcon[] carimages;
    ImageIcon blast;
    JLabel countlabel;
    
    public GamePlay(int width){
        //for the window
        this.setBounds(0,40,width,(int)(width * scalefactor)-40);
        this.setSize(width,(int)(scalefactor *  width)-40);
        this.setLayout(null);
        this.setOpaque(false);
        this.setFocusable(true);
        random = new Random();
        
        //set the main car location
        maincarlocation = new Point(150,510);
        
         //intializing the car images
         carimages = new ImageIcon[7];
         try{
            carimages[0] = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/CarBlue.png");
            carimages[1] = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/CarGreen.png");
            carimages[2] = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/CarMagenta.png");
            carimages[3] = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/CarOrange.png");
            carimages[4] = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/CarPink.png");
            carimages[5] = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/CarRed.png");
            carimages[6] = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/CarYellow.png");
         
            blast = new ImageIcon("src/main/java/shens/racinggame/Game/Component/images/blast.png");
         }catch(Exception ex){
             
         }

         maincarcolor = random.nextInt(7);
         
         //set the default oponent car location
         opponentslocations = new Point[4];
         opponentslocations[0] = new Point(random.nextInt(40) + 20,random.nextInt(40)-340);
         opponentslocations[1] = new Point(random.nextInt(60) + 100,random.nextInt(40)-120);
         opponentslocations[2] = new Point(random.nextInt(60) + 200 ,random.nextInt(40)-340);
         opponentslocations[3] = new Point(random.nextInt(40) + 300,random.nextInt(40)-120);
         
         oponentcolor = new Integer[4];
         oponentcolor[0] = random.nextInt(7);
         oponentcolor[1] = random.nextInt(7);
         oponentcolor[2] = random.nextInt(7);
         oponentcolor[3] = random.nextInt(7);
         
         //for the count label
        countlabel = new JLabel();
        countlabel.setBounds(0,200,400,150);
        countlabel.setVerticalAlignment(JLabel.CENTER);
        countlabel.setHorizontalAlignment(JLabel.CENTER);
        countlabel.setForeground(Color.RED);
        countlabel.setFont(new Font("Arial",Font.BOLD,80));
        countlabel.setVisible(false);
        this.add(countlabel);
         
        
    }
    
    //For painting the graphic
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        //main cars
        if(!gamepause)
            g2d.drawImage(carimages[maincarcolor].getImage(),maincarlocation.x,maincarlocation.y,40,80,this);
        else
            g2d.drawImage(blast.getImage(),maincarlocation.x,maincarlocation.y,60,53,this);
        //oponents 
        if(opponentslocations[0].y > -80)
            g2d.drawImage(carimages[oponentcolor[0]].getImage(),opponentslocations[0].x,opponentslocations[0].y,40,80,this);
         if(opponentslocations[1].y > -80)
             g2d.drawImage(carimages[oponentcolor[1]].getImage(),opponentslocations[1].x,opponentslocations[1].y,40,80,this);
        if(opponentslocations[2].y > -80)
            g2d.drawImage(carimages[oponentcolor[2]].getImage(),opponentslocations[2].x,opponentslocations[2].y,40,80,this);
        if(opponentslocations[3].y > -80)
            g2d.drawImage(carimages[oponentcolor[3]].getImage(),opponentslocations[3].x,opponentslocations[3].y,40,80,this);


    }
    
    //set the main car location
    public void setCarLocation(Point carlocation){
        if(carlocation.x >= 20 && carlocation.x <= 310){
           this.maincarlocation = carlocation;
           repaint(); 
        }
        
    }
    
    public Point getCarLocation(){ return maincarlocation; }
    
    //for multithreading
    @Override
    public void run() {
        
        //countdown label
        countlabel.setVisible(true);
        countlabel.setText("Ready");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        int j = 3;
        while(j>=1){
            countlabel.setText("" + j);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
            }
            j--;
        }
        countlabel.setVisible(false);
        
        
        //endless loop for create opponents
        while(!gamepause){
            for(int i =0;i<4;i++){
                if(opponentslocations[i].y <600 ){
                    opponentslocations[i].y += gamespeed;
                }else{
                    //if one or more car goes out of the bounds then it will re create the new car
                    oponentcolor[i] = random.nextInt(7);
                    switch(i){
                        case 0:
                            opponentslocations[i] = new Point(random.nextInt(40) + 20,random.nextInt(40)-80);
                            break;
                        case 1:
                            opponentslocations[i] = new Point(random.nextInt(60) + 100,random.nextInt(40)-140);
                            break;
                        case 2:
                            opponentslocations[i] = new Point(random.nextInt(60) + 200,random.nextInt(40)-180);
                            break;
                        case 3:
                            opponentslocations[i] = new Point(random.nextInt(40) + 300,random.nextInt(40)-80);
                            break;
                    }
                }
            }
            repaint();
            //if intersect with any car location this will stop the map and game play
            for(int i= 0; i<4; i++){
                if(new Rectangle(opponentslocations[i], new Dimension(40,80)).intersects(new Rectangle(maincarlocation,new Dimension(40,80)))){
                    gamepause = true;
                    GameMap.pausegame = true;
                    
                    //play a sound after blasting
                    File f = new File("src/main/java/shens/racinggame/Game/Component/sounds/blast.wav");
                    AudioInputStream audioIn;  
                    try {
                        audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioIn);
                        clip.start();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                   
                }
            }
            //for the animation
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
            }
            gamescore += gamespeed;
        }
    }
    
}

