package shens.racinggame.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


//this will manage the full game window
public class GameWindow extends JFrame implements KeyListener,ActionListener{
    
    //for the game constants
    private final static float scalefactor = 1.7f;
    private static final int gamespeed = 10;
   
    
    //for the declaring the component
    JLabel scorelbl;
    GamePlay newgameplay;
    JPanel head;
    JCheckBox sound;
    ImageIcon soundon;
    ImageIcon soundoff;
    JLabel scoreBanner;
    
    //for the game sound
    GameSound backsound;
    
    public GameWindow(int width, String title, Color backcolor) throws HeadlessException{
        
        //for this window
        this.setLayout(null);
        this.setSize(width,(int)(width * scalefactor));
        this.getContentPane().setBackground(backcolor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setResizable(false);
        this.addKeyListener(this);
        
        //head panel
        head = new JPanel();
        head.setBounds(0,0,400,40);
        head.setLayout(null);
        head.setBackground(new Color(0X272340));
        this.add(head);
        
        //for the score label
        scorelbl = new JLabel("Score");
        scorelbl.setFont(new Font("AlphaMaleModern",Font.PLAIN,20));
        scorelbl.setBounds(5,10,250,20);
        scorelbl.setForeground(new Color(0Xffd414));
        scorelbl.setLocation(5, 10);
        scorelbl.setVerticalTextPosition(JLabel.TOP);
        scorelbl.setHorizontalTextPosition(JLabel.LEFT);
        head.add(scorelbl);
                
        //sound button
        try{
            soundon = new ImageIcon("D:\\my project\\java\\CarGame\\src\\main\\java\\shens\\cargame\\Game\\Component\\images\\SoundOn.png");
            soundoff = new ImageIcon("D:\\my project\\java\\CarGame\\src\\main\\java\\shens\\cargame\\Game\\Component\\images\\SoundOff.png");
        }catch(Exception ex){
            
        }

        
        //sound check box
        sound = new JCheckBox();
        sound.setOpaque(false);
        sound.setFocusable(false);
        sound.setSelectedIcon(soundon);
        sound.setIcon(soundoff);
        sound.addActionListener(this);
        sound.setLocation(250, 5);
        sound.setSize(30,28);
        sound.setSelected(true);
        head.add(sound);
        
        scoreBanner = new JLabel();
        scoreBanner.setBounds(0,200,400,150);
        scoreBanner.setForeground(Color.RED);
        scoreBanner.setBackground(Color.YELLOW);
        scoreBanner.setFont(new Font("Arial",Font.PLAIN,36));
        scoreBanner.setVisible(false);
        scoreBanner.setOpaque(true);
        scoreBanner.setVerticalAlignment(JLabel.CENTER);
        scoreBanner.setHorizontalAlignment(JLabel.CENTER);
        this.add(scoreBanner);
        
        

        
        this.setVisible(true);
        
        //adding main component to the main window
        //Intilizing the new game play
        newgameplay = new GamePlay(400);
        Thread gameplaythread = new Thread(newgameplay);
        this.add(newgameplay);
        
        //for the game map
        GameMap newmap = new GameMap(400);
        Thread gamemapThread = new Thread(newmap);
        this.add(newmap);
       
        backsound = new GameSound();
        backsound.start();
        

        
        
        //priority
        gamemapThread.setPriority(1);
        gameplaythread.setPriority(2);
        gameplaythread.start();
        gamemapThread.start();
        
        while(gameplaythread.isAlive()){
            scorelbl.setText("Score " + GamePlay.gamescore);
        }
        
        //display the total marks
        scoreBanner.setText("Score " + GamePlay.gamescore );
        scorelbl.setVisible(false);
        scoreBanner.setVisible(true);
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
                
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!GamePlay.gamepause){
            if(e.getKeyCode() == 37){
                newgameplay.setCarLocation(new Point((newgameplay.getCarLocation().x - gamespeed ),newgameplay.getCarLocation().y));
            }
        if(e.getKeyCode() == 39){
            newgameplay.setCarLocation(new Point((newgameplay.getCarLocation().x + gamespeed ),newgameplay.getCarLocation().y));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sound){
           //for the sound
            if(sound.isSelected() == true){
                backsound = new GameSound();
                backsound.start();
            }
            else
                backsound.stopClip(); 
        }
    }
    
}