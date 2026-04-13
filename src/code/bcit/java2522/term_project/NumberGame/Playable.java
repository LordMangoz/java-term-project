package bcit.java2522.term_project.NumberGame;

/**
 * Represents something that can be played.
 *
 * @author Umanga Bajgai
 * @version 1.0
 */
public interface Playable
{
    /**
     * Starts the game session.
     */
    void play();

    /**
     * Ends the session.
     */
    void stopPlaying();
}