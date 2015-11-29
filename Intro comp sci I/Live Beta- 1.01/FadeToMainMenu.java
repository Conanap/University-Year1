import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Write a description of class FaderToMainMenu here.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class FadeToMainMenu extends Actor
{
    private GreenfootImage img = new GreenfootImage("Main Menu.png");
    private GreenfootSound menu = new GreenfootSound("MainMenuTrack.mp3");
    private Intro myWorld = (Intro)getWorld();
    private int transparency = 0;

    public FadeToMainMenu()
    {
        img.setTransparency(0);
    }

    public void fade()
    {
        try
        {
            if(transparency < 255)
            {
                transparency++;
                // img.setTransparency(transparency * 15);
                img.setTransparency(transparency * 2 );
                setImage(img);
            }
            else if (transparency == 255)
            {
                Greenfoot.setWorld(new MainMenu(menu));// (myWorld.getTheme()));
            }
        }
        catch (IllegalArgumentException e)
        {
        }
    }
}
