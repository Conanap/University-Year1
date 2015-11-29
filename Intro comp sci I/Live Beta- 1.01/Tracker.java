import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tracker here.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class Tracker extends Actor
{
    private int value;      // Tracker's value. 

    /**
     * The constructor for the Tracker object.
     */
    public Tracker ()
    {
        value = 0;
    }

    /**
     * The method that adds the Tracker's value.
     */
    public void increase ()
    {
        value++;    // Add the counter by 1 each time.
    }

    /**
     * Checks to see if the value has hit the target .
     * It returns "true" and clears the Tracker when the target value is reached; otherwise, "false" is returned.
     */
    public boolean hit (int target)
    {
        if (value >= target) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Exists to clear the value stored in the value varible (int value).
     */
    public void clear ()
    {
        value = 0;
    }
}
