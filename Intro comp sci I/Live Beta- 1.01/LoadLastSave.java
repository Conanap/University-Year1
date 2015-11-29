import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button that transitions quickly to the game World (without Prologue portion) if clicked by player.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class LoadLastSave extends Buttons2
{
    private GreenfootImage button;
    private GreenfootImage buttonHover;
    private FadeToLoadingScreen img = new FadeToLoadingScreen();
    private GreenfootSound menu = new GreenfootSound("MainMenuTrack.mp3");
    private Intro myWorld;

    /**
     * Constructor for the "LoadLastSave" class.
     */
    public LoadLastSave () 
    {
        button = new GreenfootImage ("Load Last Save Button Normal.png");      // Import picture into Greenfoot.
        buttonHover = new GreenfootImage ("Load Last Save Button Hover.png");
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
            Greenfoot.setWorld(new LoadingScreen2()); // (myWorld.getTheme()));
        }
    }
}
