package Audio;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/* I'm gonna be honest: most of this class was written by AI, and then studied; yes, I understand and could have inferred some of 
 * the code below, but there are other parts that because of time constraints, I didn't bother to fully understand before 
 * implementing. If I have enough time, I'll take care of it but in the mean time, I already got enough to deal with.
 * (currently, I've re-written the code THREE time from the ground... ow)
 */
public class Sound {

    // ==== Fields ==== :
    
    // Instances:
    private Clip clip;
    private String fileString;



    // ==== Methods ==== :

    // Instances:
    public void play(){                        // I.M.S. 0 ( play )
        if (this.clip != null) {

            if (this.clip.isRunning()) {
                this.clip.stop();
            }
            this.clip.setFramePosition(0);
            this.clip.start();

        } else {
            System.err.println("Clip not loaded.");
        }
    }
    public void stop(){
        if (this.clip != null && this.clip.isRunning()) {
            this.clip.stop();
        }
    }
    public void setLoop(boolean loopable){
        if (this.clip != null) {
            if (loopable) {
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                this.clip.loop(0);
            }
        }
    }
    
    public void close(){                       // I.M.S. 1 ( general utility )
        if (this.clip != null) {
            this.clip.close();
        }
    }
    public boolean isPlaying(){
        return this.clip != null && this.clip.isRunning();
    }
    public String toString(){
        return String.format( "name=%s, playing=%b, state=%b", this.fileString, this.isPlaying(), this.clip != null );
    }

    // ==== Constructor ==== :

    public Sound(String filepath) {
        this.fileString = filepath;
        try {
            File audioFile = new File(filepath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
        }
        catch (UnsupportedAudioFileException e) {
            System.err.println("The specified audio file is not supported: " + filepath);
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("Error reading the audio file: " + filepath);
            e.printStackTrace();
        }
        catch (LineUnavailableException e) {
            System.err.println("Audio line for playback is unavailable.");
            e.printStackTrace();
        }
    }
}