import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * A space in the player inventory which Inventory Items can snap to. It has an empty image, but
 * hold absolute x and y coordinates which are important in identifying positions of InventoryItems
 * to the playerInventory of player
 * 
 * @author Marco Ly 
 * @version Jan 2015
 */
public class InventorySlot extends Slots
{
    public InventorySlot() {
        super();
        setImage(emptyImage);
    }
}
