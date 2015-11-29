import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classes that hold arrays of inventory/shop items and slots
 * 
 * @author  Marco Ly
 * @version Jan 2015
 */
public abstract class Inventory extends Actor
{
    protected boolean showing;
    
    public abstract void setShowing(boolean showing);
    
    public Inventory() {
        showing = false;
    } 

    public boolean isShowing() {
        return showing;  
    }
}
