import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button for buying items from the shop
 * 
 * @author Marco Ly
 * @version Jan 2015
 */
public class BuyButton extends Buttons
{
    public void updateImage() {
        if (isMouseOn)setImage("1280x720 Buy Button Hover.png");
        else if (!isMouseOn)setImage("1280x720 Buy Button Normal.png");
    }
}
