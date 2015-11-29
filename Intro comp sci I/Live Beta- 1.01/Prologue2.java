import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Prologue2 here.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class Prologue2 extends GameWorlds
{
    private FadeToPrologue3 img = new FadeToPrologue3();
    private Tracker tracker;

    /**
     * Constructor for objects of class Prologue2.
     * 
     */
    public Prologue2()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        tracker = new Tracker();
        prepare();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        addObject(img, 640, 360);
    }

    public void act()
    {
        img.fade();
        if (tracker.hit (10)) {
            generateEmbers ();
            tracker.clear ();
        } else {
            tracker.increase ();
        }
    }
}
