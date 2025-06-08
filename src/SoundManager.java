import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	
	private static Clip backgroundClip;
	
	public static boolean musicEnabled = true;
    public static boolean soundEffectsEnabled = true;
	
	public static void playSound(String filePath) {     
		 if (!soundEffectsEnabled) return;
		
	    try {                                                                                      
	        File soundFile = new File(filePath);                                             
	        if (!soundFile.exists()) {                                                             
	            System.err.println("Sound file not found: " + filePath);                     
	            return;                                                                            
	        }                                                                                      
	                                                                                               
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);             
	        Clip clip = AudioSystem.getClip();                                                     
	        clip.open(audioStream);                                                                
	        clip.start();                                                                          
	                                                                                               
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {       
	        e.printStackTrace();                                                                   
	    }                                                                                          
	}
	
	 public static void playBackgroundMusic(String filePath) {
	        if (!musicEnabled) return;

	        try {
	            // Stop and release any previous clip
	            if (backgroundClip != null && backgroundClip.isRunning()) {
	                backgroundClip.stop();
	            }
	            if (backgroundClip != null) {
	                backgroundClip.close();
	            }

	            // Load new audio clip
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
	            backgroundClip = AudioSystem.getClip();
	            backgroundClip.open(audioStream);
	            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
	            backgroundClip.start();

	            System.out.println("Music started.");

	        } catch (Exception e) {
	            System.err.println("Failed to play background music: " + filePath);
	            e.printStackTrace();
	        }
	    }

	    public static void stopBackgroundMusic() {
	        if (backgroundClip != null && backgroundClip.isRunning()) {
	            backgroundClip.stop();
	        }
	    }
}

