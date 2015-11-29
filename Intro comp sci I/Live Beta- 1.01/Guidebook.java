import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Guidebook here.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class Guidebook extends Buttons2
{
    private GreenfootImage button;
    private GreenfootImage buttonHover;

    /**
     * Constructor for the "StartClick" class.
     */
    public Guidebook () 
    {
        button = new GreenfootImage ("Guidebook Button Normal.png");      // Import picture into Greenfoot.
        buttonHover = new GreenfootImage ("Guidebook Button Hover.png");
        setImage (button);    
    }    

    public void act () {
        if (Greenfoot.mouseMoved (this)) {      // If the mouse hovered on the button, change the picture to make it glow.
            setImage (buttonHover);
        }

        if (Greenfoot.mouseMoved (getWorld ())) {      // If the mouse moved off the button, restore the button to its original appearance.
            setImage (button);
        }

        if (Greenfoot.mouseClicked (this)) 
        {   // If the button was clicked, variable becomes true and image is changed.
            clicked = true;
            Greenfoot.delay(100);
            getWorld().addObject(new FaderToGuidebookScreen(), getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        }
    }
}
