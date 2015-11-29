import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button from transporting player home
 * 
 * @author Albion Fung
 * @version Jan 2015
 */
public class SOS extends Buttons
{
    /**
     * Act - do whatever the SOS wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void updateImage() 
    {
        if (isMouseOn)setImage("SOS Button Hover.png");
         else if (!isMouseOn)setImage("SOS Button Normal.png");
    }    
}
