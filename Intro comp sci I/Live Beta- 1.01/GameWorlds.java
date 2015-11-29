import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class meant to create an easy way to manage the music files within the game, 
 * so that all the menu screens has access to them and can start/stop them with ease.
 */
public class GameWorlds extends World
{
    static protected GreenfootSound menu = new GreenfootSound ("MainMenuTrack.mp3");
    static protected GreenfootSound bgMusic1 = new GreenfootSound ("Alone in Silence.mp3");
    /**
     * Constructor for objects of class GameWorld.
     * 
     */
    public GameWorlds(int x, int y, int cell)
    {    
        super(x, y, cell); //To make the other screens calling super work
    }

    /**
     * Mimics the animation of burning embers.
     */
    protected void generateEmbers ()
    {
        int ranNum = Greenfoot.getRandomNumber (3);
        BurningEmbers [] w = new BurningEmbers [ranNum];

        for (int i = 0; i < ranNum; i++) {
            int ranX = getWidth ();
            int ranY = Greenfoot.getRandomNumber (getHeight ());
            w [i] = new BurningEmbers ();
            addObject (w [i], ranX, ranY);
        }   
    }
 
    /**
     * Starts playing the main menu theme.
     */
    public void started()  
    {  
        if (!menu.isPlaying()){
            menu.setVolume(30);
            menu.playLoop();  
        }
    }

    /**
     * Stops playing the main menu theme when the simulation is not running.
     */
    public void stopped()
    {
        menu.pause();
    }
}
