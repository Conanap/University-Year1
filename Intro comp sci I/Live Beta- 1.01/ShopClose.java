import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShopClose here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopClose extends Buttons
{    
    public void updateImage() {
        if (isMouseOn)setImage("1280x720 Close Button Hover.png");
         else if (!isMouseOn)setImage("1280x720 Close Button Normal.png");
    }
}
