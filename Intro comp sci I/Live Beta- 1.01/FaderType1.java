import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Parent class of first type of faders for screen transitional purposes.
 * 
 * @author (Jasper Tu) 
 * @version (January 2015)
 */
public class FaderType1 extends Actor
{
    protected int fadeLevel = 0;

    /**
     * Decrease transparency value of Black 1280x720 filler at certain increments.
     */
    protected void fade(int f)
    {
        if (f > 255)
        {
            f = 255;
        }
        else if (f < 0)
        {
            f = 0;
        }

        GreenfootImage fade = new GreenfootImage(1280, 720);
        Color fadeColor;
        fadeColor = new Color(0, 0, 0, f);
        fade.clear();
        fade.setColor(fadeColor);
        fade.fillRect(0, 0, 1280, 720);
        setImage(fade);
    }
}
