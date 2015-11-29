import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BurningEmbers here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BurningEmbers extends Actor
{
    private int size;      // Randomly geenrate size of the ember picture.
    private int count;      // The counter variable representing the motion of ember image.

    /**
     * The constructor of ember.
     */
    public BurningEmbers ()
    {
        size = Greenfoot.getRandomNumber(30);
        setImage();     // Set image of the ember.
    }

    /**
     * Act - do whatever the BurningEmbers wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setLocation(getY() - Greenfoot.getRandomNumber(10) + 5, getX() - 10);
        if (getY() <= 20){
            getWorld().removeObject(this);
        }
    }    

    /**
     * Set the image of the ember.
     */
    public void setImage()
    {
        GreenfootImage img = new GreenfootImage("Ember.png");
        img.scale(size + 1, size + 1);
        setImage(img);
    }
}
