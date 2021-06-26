import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class for handling Sound Effects.
 * @author William Playle-de Vries
 */
public class Audio {

    // to store current position
    Long currentFrame;
    Clip clip;

    // current status of clip
    String status;

    AudioInputStream inStream;

    MediaPlayer mediaPlayer;

    // File paths for where .wav files are located and what they're called
    final private static String SOUND_FOLDER = "assets/sfx/";
    final private static String WALK = "Walk.wav";
    final private static String DEATH = "Death.wav";
    final private static String EXPLOSION = "Explosion.wav";
    final private static String BASH_WALL = "BashWall.wav";
    final private static String COLLECT = "Collect.wav";
    final private static String CHOP = "Chop.wav";
    final private static String CRUNCH = "Crunch.wav";
    final private static String WOOSH = "Woosh.wav";

    /**
     * Plays Walk sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void walk() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + WALK)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

    }

    /**
     * Plays Death sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void death() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + DEATH)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * Plays Explosion sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void explosion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + EXPLOSION)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * Plays Bashing Wall sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void bashWall() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + BASH_WALL)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * Plays Collection sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void collect() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + COLLECT)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * Plays Chooping sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void chop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + CHOP)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

    }

    /**
     * Plays Crunching sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void crunch() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + CRUNCH)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

    }

    /**
     * Plays Wooshing sound
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void woosh() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media sound = new Media((new File(SOUND_FOLDER + WOOSH)).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

    }

}