import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Repairs inventory items that are placed inside of it, and charge the player money for repairs
 * 
 * @author Marco Ly
 * @version Jan 2015
 */
public class RepairSlot extends Slots
{
    private GreenfootImage repairIcon;
    private InventoryItem item;
    private Test world;
    private int delay;
    public RepairSlot() {
        super();
        setImage("Repair Icon.png");        
    }

    /**
     * Repairs any item placed inside the repair slot, and charges 1 coin for every 2 duration repaired
     */
    public void act() {
        delay++;
        if (item != null && item.isBreakable() && delay >= 3 && item.decreaseDuration(-1)) {
            world = (Test)getWorld();
            if (item.getDuration()%2 == 0) world.getPlayer().addCoins(-1);
            delay = 0;
        }
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
     * with the item. It also adds the item into the inventory before it deletes it from repair slot
     */
    public void clearAsset() {
        if (item != null) {
            world = (Test)getWorld();  
            item.clearStackDisplay();
            item.clearDurationDisplay();
            world.getPlayer().getInventory().addItem(item.getID());
            world.removeObject(item);
            item = null;
        }
    }

    /**
     * Remove the reference to the item in repair slot, if the parameter and the current item match
     * 
     * @param newItem   the item that is being checked to be the same as the current item
     */
    public void removeItem(InventoryItem newItem) {
        if (item == newItem) item = null;
    }
      
    /**
     * Sets a new item to be stored in repairSlot
     * 
     * @param newItem   the new item
     */
    public void newItem (InventoryItem newItem) {
        item = newItem;
    }
}