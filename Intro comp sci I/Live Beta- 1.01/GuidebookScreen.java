import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GuidebookScreen here.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class GuidebookScreen extends GameWorlds
{
    private Tracker tracker; 

    /**
     * Constructor for objects of class GuidebookScreen.
     * 
     */
    public GuidebookScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        tracker = new Tracker();
        prepare();
    }

    /**
     * When the game is run, embers are generated! Yay!
     */
    public void act () 
    {
        if (tracker.hit (10)) {
            generateEmbers();
            tracker.clear ();
        } else {
            tracker.increase ();
        }
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        Guidebook guidebook2 = new Guidebook();
        addObject(guidebook2, 198, 72);
        removeObject(guidebook2);
        ReturnToMenu returntomenu = new ReturnToMenu();
        addObject(returntomenu, 121, 87);
        returntomenu.setLocation(150, 53);
    }
}
