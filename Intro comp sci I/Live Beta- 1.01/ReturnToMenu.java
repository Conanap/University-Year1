import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button that transitions back to Main Menu screen if clicked by player.
 * 
 * Jasper Tu 
 * January 2015 
 */
public class ReturnToMenu extends Buttons2
{
    private GreenfootImage button;
    private GreenfootImage buttonHover;
    private GreenfootSound menu = new GreenfootSound("MainMenuTrack.mp3");
    private Intro myWorld;

    /**
     * Constructor for the "ReturnToMenu" class.
     */
    public ReturnToMenu () 
    {
        button = new GreenfootImage ("Return to Menu Button Normal.png");      // Import picture into Greenfoot.
        buttonHover = new GreenfootImage ("Return to Menu Button Hover.png");
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
            Greenfoot.setWorld(new MainMenu(menu)); // (myWorld.getTheme()));
        }
    }
}