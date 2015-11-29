import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Selection screen for playing a new game, loading from a previous save point, or returning to menu.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class LoadGame extends GameWorlds
{
    private Tracker tracker; 

    /**
     * Constructor for objects of class LoadGame.
     * 
     */
    public LoadGame()
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
        NewGame newgame = new NewGame();
        addObject(newgame, 639, 200);
        ReturnToMenu returntomenu = new ReturnToMenu();
        addObject(returntomenu, 645, 504);
        returntomenu.setLocation(643, 501);
        LoadLastSave loadlastsave = new LoadLastSave();
        addObject(loadlastsave, 654, 377);
        loadlastsave.setLocation(638, 350);
        returntomenu.setLocation(639, 501);
    }
}