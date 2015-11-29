import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Trash slot that deletes unwanted items. Works like the Terraria trash system, where it allows you to 
 * save one item, just incase you trashed it but actually wanted it
 * 
 * @author Marco Ly
 * @version Jan 2015
 */
public class TrashSlot extends Slots
{
    GreenfootImage trashIcon;
    InventoryItem item; // The item that this trash slot may hold
    public TrashSlot() {
        super();
        trashIcon = new GreenfootImage("1280x720 Trash Button Normal.png");
        setImage(emptyImage);
        getImage().setTransparency(255);
        getImage().drawImage(trashIcon, 0, 0);
    }
    
    /**
     * Adds it's item, if it exists
     * 
     * @param Test  the world
     */
    public void addedToWorld(World Test) {
        if (item != null)Test.addObject(item, getX(), getY());
    }
    
    /**
     * Clears the associated item of trash slot by removing it from the world as well as any objects associated
     * with the item
     */
    public void clearAsset() {
        if (item != null) {item.clearStackDisplay(); item.clearDurationDisplay(); getWorld().removeObject(item);}
    }
        
    /**
     * Sets a new item
     * 
     * @param newItem   the new item to be stored in trashSlot
     */
    public void newItem (InventoryItem newItem) {
        item = newItem;
    }
}