import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class DurationDisplay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DurationDisplay extends Display
{
    private GreenfootImage image;
    private Color color;
    private int transparency;
    private int size;
    private int green;
    private int red;
    private int blue;
    public DurationDisplay() {
        size = 41;
        transparency = 255;
        image = new GreenfootImage(41,2);
        color = new Color(0,255,0);
        image.setColor(color);
        image.setTransparency(transparency);
        image.fill();
        setImage(image);
    }   
       
    public void setPercentage(double percentage) {
        if (percentage != 0 && (int)(41*percentage) != 0){size = (int)(41*percentage); transparency = 255;}
        else transparency = 0;
        image = new GreenfootImage(size,2);
        image.setColor(color);
        image.setTransparency(transparency);
        image.fill();
        setImage(image);
    }
}
