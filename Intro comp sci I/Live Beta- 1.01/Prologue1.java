import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Write a description of class Prologue1 here.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class Prologue1 extends GameWorlds
{  
    private FadeToPrologue2 img = new FadeToPrologue2();
    private Tracker tracker;

    /**
     * Constructor for objects of class Prologue1.
     * 
     */
    public Prologue1()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        tracker = new Tracker();
        prepare();
    }

    /**
     * Act - do whatever LoadingScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
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

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        addObject(img, 640, 360);
    }
}