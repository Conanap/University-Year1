import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class building here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Building extends Actor
{
    private GreenfootImage structure;
    private final int shop = 0, save = 1, repair = 2;
    private String name;
    public Building(int type)
    {
        switch(type)
        {
            case 0:
                name="shop";
                break;
            case 1:
                name="hotel";
                break;
            case 2:
                name = "repairShop";
                break;
        }
        structure = new GreenfootImage (name+".png");
        setImage(structure);
    }
}
