package shens.racinggame.Game;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;


public class GameSound extends Thread{
     Clip clip;
    public GameSound() {
        
    }

    @Override
    public void run() {
        File f = new File("src/main/java/shens/racinggame/Game/Component/sounds/background.wav");
        AudioInputStream audioIn;  
        try {
            //for audio
                audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                }
        
    }
    
    public void stopClip(){
        clip.stop(); 
    }
    
    
    
}

