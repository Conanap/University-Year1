import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button that transitions to Game Controls screen if clicked by player.
 * 
 * Jasper Tu 
 * January 2015 
 */
public class GameControls extends Buttons2
{
    private GreenfootImage button;
    private GreenfootImage buttonHover;

    /**
     * Constructor for the "GameControls" class.
     */
    public GameControls () 
    {
        button = new GreenfootImage ("Game Controls Button Normal.png");      // Import picture into Greenfoot.
        buttonHover = new GreenfootImage ("Game Controls Button Hover.png");
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
            getWorld().addObject(new FaderToControls(), getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        }
    }
}