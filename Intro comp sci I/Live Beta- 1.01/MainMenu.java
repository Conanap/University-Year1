import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Main Menu screen.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class MainMenu extends GameWorlds
{
    private Tracker tracker; 
    /**
     * Constructor for objects of class Testing.
     * 
     */
    public MainMenu(GreenfootSound menu)
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
            generateEmbers ();
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
        ClickToPlay clicktoplay = new ClickToPlay();
        addObject(clicktoplay, 656, 622);
        clicktoplay.setLocation(647, 599);
        GameControls gamecontrols = new GameControls();
        addObject(gamecontrols, 290, 604);
        gamecontrols.setLocation(287, 597);
        clicktoplay.setLocation(641, 598);
        Guidebook guidebook = new Guidebook();
        addObject(guidebook, 996, 594);
        guidebook.setLocation(1008, 597);
    }
}

