import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;
import java.io.File;

/**
 * Button that transitions to 1st Prologue screen if clicked by player.
 * 
 * Jasper Tu 
 * January 2015 
 */
public class NewGame extends Buttons2
{
    private GreenfootImage button;
    private GreenfootImage buttonHover;

    /**
     * Constructor for the "NewGame" class.
     */
    public NewGame () 
    {
        button = new GreenfootImage ("New Game Button Normal.png");      // Import picture into Greenfoot.
        buttonHover = new GreenfootImage ("New Game Button Hover.png");
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
            File f = new File ("world.txt");
            File j = new File("itemSave.txt");
                f.delete();
                j.delete();
            Greenfoot.delay(100);
            getWorld().addObject(new FaderToPrologue1(), getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        }
    }
}
