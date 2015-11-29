import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * EA transitional screen that fades into the Main Menu screen. Simply for aesthetics.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class Intro extends GameWorlds
{
    private FadeToMainMenu img = new FadeToMainMenu();
    //     private int vol;
    //     private long nextTime, timeInterval;

    /**
     * Constructor for objects of class Intro.
     * 
     */
    public Intro()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        prepare();
    }

    /**
     * Act - do whatever LoadingScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        img.fade();

    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        addObject(img, 640, 360);
    }
}