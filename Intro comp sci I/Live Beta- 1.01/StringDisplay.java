import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class CoinDisplay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StringDisplay extends Display
{
    private String valuesString = "";
    private GreenfootImage valuesImage;
    private Color bgColor = new Color (0, 0, 0, 0); // transparent background  
    private Color mainColor;
    private GreenfootImage baseImage;
    private int size;
    public StringDisplay(int x, int y, int font, Color color) {
        baseImage = new GreenfootImage(x,y);
        mainColor = color;
        size = font;
        update();
    }

    /**
     * Updates the coins display image to the current number of coins
     */
    public void updateValue(int value) {
        valuesString = Integer.toString(value);
        update();
    }

    /**
     * Adds or removes the coins display to the world 
     */
    private void update() {
        valuesImage = new GreenfootImage(valuesString, size, mainColor, bgColor);
        baseImage.clear();
        baseImage.drawImage(valuesImage, 0, 0);
        setImage(baseImage);
    } 
}
