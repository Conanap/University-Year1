import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The "Buttons2" class, containing the various buttons in the game.
 * 
 * Jasper Tu
 * January 2015
 */
public class Buttons2 extends Actor
{
    protected boolean clicked;      // A boolean, that is supposed to contain information of whether or not the button has been pressed.

    /**
     * The default constructor for the "Buttons2" class.
     */
    public Buttons2 ()
    {
        clicked = false;        // Upon creation, the button in the game starts off as unclicked.
    }

    /**
     * This method is supposed to return a boolean that tells whether or not the button has been clicked.
     */
    public boolean isPressed ()
    {
        if (clicked == true) 
        {
            clicked = false; 
            return true;      
        } 
        else 
        {
            return false;
        }
    }
}