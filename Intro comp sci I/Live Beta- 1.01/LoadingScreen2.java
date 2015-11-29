import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Acts as a timed Loading Screen that loads contents of the game World.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class LoadingScreen2 extends GameWorlds
{
    private int counter = 0;  
    private boolean exists = true;  
    private Tracker tracker;

    /**
     * Constructor for objects of class LoadingScreen.
     * 
     */
    public LoadingScreen2()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        tracker = new Tracker();
    }

    public void act()
    {
        timer();
        if (tracker.hit (10)) {
            generateEmbers ();
            tracker.clear ();
        } else {
            tracker.increase ();
        }
    }
    
    /**
     * Counts up until a particular integer value, and then switches the screen into another world.
     * Essentially determines how long the loading screen will last.
     */
    public void timer()
    {
        if(exists == true)
        {  
            if(counter < 500)  
            {  
                counter++;  
            }  
            else 
            {  
                Greenfoot.setWorld(new Test());  
                exists = false;  
            }  
        }  
    }
}
