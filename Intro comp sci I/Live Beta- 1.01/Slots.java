import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color; 
/**
 * 41x41 slots that can either be item placeholders, trash slots, repair slots, or items for player
 * and shop. 
 * 
 * @author Marco Ly 
 * @version Jan 2015
 */
public class Slots extends Actor
{
    protected GreenfootImage emptyImage;
    protected Color emptyColor;
    public Slots() {
        emptyImage = new GreenfootImage(41,41);
        emptyColor = new Color(245,222,179);
        emptyImage.setTransparency(110);
        emptyImage.setColor(emptyColor);
        emptyImage.fill();
    }
}
