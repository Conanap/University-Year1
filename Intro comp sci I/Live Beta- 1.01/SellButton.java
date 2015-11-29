import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button for selling itmes from inventory
 * 
 * @author Marco Ly
 * @version Jan 15
 */
public class SellButton extends Buttons
{
    public void updateImage() {
        if (isMouseOn)setImage("1280x720 Sell Button Hover.png");
         else if (!isMouseOn)setImage("1280x720 Sell Button Normal.png");
    }    
}
