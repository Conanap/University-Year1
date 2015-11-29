import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.lang.NullPointerException;
/**
 * Inventory Item is the object that stores all information for any item manipulated or gained by the player
 * 
 * @author Marco Ly
 * @version Jan 2015
 */
public class InventoryItem extends Slots
{
    private Test world; // reference to world
    private StringDisplay stackDisplay; // the display for number of stacks the inventory item has
    private DurationDisplay durationDisplay; // the display for the duration of this item, if it can have a duration
    private HoverText hover; // description of this item

    private int itemID; // Number for identifying this item. Allows it to gain certain properties
    private int stacks; // Stacks of this item
    private int maxDuration; // Maximum duration it can have
    private int duration; // Current duration of the item
    private int price; // The sell price of the item
    private boolean pickedUp; // Whether or not the item has been picked up
    private boolean stackable; // Whether or not this item can be stacked 
    private boolean breakable; // Can have a duration and be used
    private boolean hovering = false; // If the mouse is hovering over this item
    private int originalX; // Original x and y coordinates for picking up and moving/swaping around this item
    private int originalY;
    /**
     * Constructor for an inventory item based on an ID
     */
    public InventoryItem(int ID) {
        super();
        stackDisplay = new StringDisplay(20,18,22,Color.BLACK);
        durationDisplay = new DurationDisplay();
        pickedUp = false;
        hovering = false;
        itemID = ID;
        stacks = 1;
        update();
        duration = maxDuration;         
    }

    /**
     * Constructor for an inventory item based on an ID and a starting number of stacks
     */
    public InventoryItem(int ID, int stack) {
        super();
        stackDisplay = new StringDisplay(20,18,22,Color.BLACK);
        durationDisplay = new DurationDisplay();
        pickedUp = false;
        hovering = false;
        itemID = ID;
        stacks = stack;
        update();
        duration = maxDuration;
        updateStacks();
    }

    /**
     * Act - do whatever the Inventory wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        world = (Test)getWorld();
        // Updates positions of objects held by this item
        stackDisplay.setLocation(getX()-10,getY()-14);
        durationDisplay.setLocation(getX(),getY()+26);
        if (Greenfoot.mouseClicked(this)) {
            if (!world.getShopInv().isShowing()){ // Does not allow for inventory items to move if the shop is open
                if (pickedUp == false) { // Picks up the object
                    originalX = this.getX();
                    originalY = this.getY();
                    pickedUp = true;
                    getWorld().removeObject(hover);
                    this.hovering=false;
                    world.removeObject(this);
                    world.addObject(this,originalX,originalY);
                } else { // If the item is clicked on but is already picked up
                    if (getOneIntersectingObject(InventoryItem.class) != null) { // If it is on top of another inventoryItem, creates a reference to that item
                        InventoryItem swap = (InventoryItem)getOneIntersectingObject(InventoryItem.class);
                        if (swap.getOneIntersectingObject(TrashSlot.class) != null) { // If the item below this item is also touching a trashSlot, delete that item and replace it with this in the trashSlot
                            swap.clearStackDisplay();
                            swap.clearDurationDisplay();
                            world.removeObject(swap);                            
                            trashInteraction();
                        } else if (swap.getID() == itemID && isStackable()){ // If the intersecting item is the same type as this and is stackable, add to its stack
                            swap.addToStack(stacks);
                            removeReferences();                         
                            clearStackDisplay();
                            getWorld().removeObject(this);
                        } else { // If this else runs, then this is just a regular inventory item. Swap all relevant information with this object to "swap" the items
                            int myID = itemID;
                            int myStack = stacks;
                            int myDuration = duration;
                            setID(swap.getID());
                            swap.setID(myID);                            
                            setStacks(swap.getStacks());
                            swap.setStacks(myStack);
                            setDuration(swap.getDuration());
                            swap.setDuration(myDuration);
                        }
                    } else if (getOneIntersectingObject (InventorySlot.class) != null) { // If this is clicked on and it is above an inventory slot, snap to it and add itself to playerInv
                        inventorySlotInteraction();
                    } else if (getOneIntersectingObject (ToolbarSlot.class) != null) { // Snap to the toolbar
                        toolbarSlotInteraction();
                    } else if (getOneIntersectingObject (TrashSlot.class) != null) { // Snap to the trashslot. This means the trash slot will be empty
                        trashInteraction();
                    } else if (getOneIntersectingObject (RepairSlot.class) != null) {
                        repairInteraction();
                    }
                }
            } else { // If shop is open, set a new selling item for shop
                world.getShopInv().selling(this);
            }            
        }
        checkPickedUp();
        checkDepleted();
    }   

    /**
     * Add and update all related objects to this item
     */
    public void addedToWorld(World Test) {
        Test.addObject(stackDisplay,getX()-10,getY()-14);
        Test.addObject(durationDisplay,getX(),getY()+26);
        updateDuration();
        updateStacks();
    }

    /**
     * Removes the stackDisplay from the world
     */
    public void clearStackDisplay() {
        try
        {
            if (stackDisplay != null) getWorld().removeObject(stackDisplay);
        }
        catch(NullPointerException e){}
    }

    /**
     * Removes the durationDisplay from the world
     */
    public void clearDurationDisplay() {
        try
        {
            if (durationDisplay != null) getWorld().removeObject(durationDisplay);
        }
        catch(NullPointerException e){}
    }

    /**
     * Makes the stack display invisible or visible, but does not remove it from the world
     * 
     * @param trueFalse whether or not the stack display should be hidden
     */
    public void hideStackDisplay(boolean trueFalse){
        if (trueFalse) {
            stackDisplay.getImage().setTransparency(0);
        } else {
            stackDisplay.getImage().setTransparency(255);
        }
    }

    /**
     * Makes the duration display invisble or visible, but does not delete it from the world
     * 
     * @param trueFalse whether or not the duration display should be hidden
     */
    public void hideDurationDisplay(boolean trueFalse){
        if (trueFalse) {
            durationDisplay.getImage().setTransparency(0);
        } else {
            durationDisplay.getImage().setTransparency(255);
        }
    }

    /**
     * Snaps to the trash slot and adds itself to the trashSlot item holding. removes all references from any other possible inventory or slot
     */
    private void trashInteraction() {
        TrashSlot trash = (TrashSlot)getOneIntersectingObject(TrashSlot.class);
        setLocation(trash.getX(), trash.getY());
        trash.newItem(this);
        pickedUp = false;
        removeReferences();
        if (world.getPlayer().getInventory().getRepair() != null)world.getPlayer().getInventory().getRepair().removeItem(this);
    }

    /**
     * Snaps to the repair slot and adds itself to the repairSlot item holding. removes all references from any other possible inventory or slot
     */
    private void repairInteraction() {
        RepairSlot repair = (RepairSlot)getOneIntersectingObject(RepairSlot.class);
        setLocation(repair.getX(), repair.getY());
        world.getPlayer().getToolBar().removeItem(this);
        repair.newItem(this);
        removeReferences();
        pickedUp = false;
    }

    /**
     * Snaps to the given inventorySlot. Removes any references to any inventory (even Player inventory) and adds itself to the player inventory
     */
    private void inventorySlotInteraction() {
        InventorySlot emptySlot = (InventorySlot)getOneIntersectingObject(InventorySlot.class);
        setLocation(emptySlot.getX(), emptySlot.getY());
        pickedUp = false;
        removeReferences();
        world.getPlayer().getInventory().addItem(this);
        if (world.getPlayer().getInventory().getRepair() != null)world.getPlayer().getInventory().getRepair().removeItem(this);
    }

    /**
     * Snaps to toolbar slot, removes all references, adds itself to the toolbar InventoryItem array
     */
    private void toolbarSlotInteraction() {
        ToolbarSlot emptySlot = (ToolbarSlot)getOneIntersectingObject(ToolbarSlot.class);
        setLocation(emptySlot.getX(), emptySlot.getY());
        pickedUp = false;
        removeReferences();
        world.getPlayer().getToolBar().addItem(this);
        if (world.getPlayer().getInventory().getRepair() != null)world.getPlayer().getInventory().getRepair().removeItem(this);
    }

    /**
     * Removes references to player inventory and toolbar, if they exist
     */
    private void removeReferences() {
        world.getPlayer().getInventory().removeItem(this);
        world.getPlayer().getToolBar().removeItem(this);
    }

    /**
     * Returns if this item can be stacked
     */
    public boolean isStackable() {
        return stackable;
    }

    /**
     * Returns if this item is breakable, meaning it has a duration to it
     */
    public boolean isBreakable() {
        return breakable;  
    } 

    /**
     * Returns true if the item's duration is not it's max duration
     */
    public boolean isDamaged() {
        if (duration != maxDuration) return true;
        return false;              
    }

    /**
     * Returns the sell price of this item
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the item ID
     */
    public int getID() {
        return itemID;
    }

    /**
     * Returns the number of stacks
     */
    public int getStacks() {
        return stacks;
    }

    /**
     * Returns the item duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets a new ID for the item and updates it accordingly
     */
    public void setID(int ID) {
        itemID = ID;
        update();
    }

    /**
     * Sets a new stack, updates the stacks display
     */
    public void setStacks(int stack) {
        stacks = stack;
        updateStacks();
    }

    /**
     * Sets a new duration, updates the duration display
     */
    public void setDuration(int value) {
        duration = value;
        updateDuration();
    }

    /**
     * Adds a number to the current stack, if it can stack items
     */
    public void addToStack(int value) {
        if (stacks + value >= 0) { stacks+=value; updateStacks();}
        checkDepleted();
    }

    /**
     * Decreases the duration value by value if it does not exceed the maximum duration. Returns whether or not it was successful
     */
    public boolean decreaseDuration(int value) {
        if (duration - value <= maxDuration) {
            duration-=value;            
            updateDuration();
            checkDepleted();
            return true;
        }
        return false;
    }

    /**
     * Checks if the item is picked up, if it is, follows the mouse. Ignores if the mouse goes offscreen
     */
    private void checkPickedUp() throws NullPointerException {
        try {
            if (pickedUp) {
                getImage().setTransparency(255);
                setLocation(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
            }
        } catch (NullPointerException e){}
    }

    /**
     * Checks if the item is depleted of duration or stacks. If it is, it promptly deletes itself and removes any references to it.
     */
    private void checkDepleted() {
        world = (Test)getWorld();
        if (stacks <= 0) {
            removeReferences();
            world.removeObject(this);
        }
        if (duration <= 0 && breakable) {
            removeReferences();
            clearDurationDisplay();
            world.removeObject(this);
        }
    }

    /**
     * Updates price, stackability, breakability, image, and max duration based on the itemID
     */
    private void update() {
        hover = new HoverText(itemID);
        if (itemID == 0) {
            this.setImage("Torch.png");
            stackable = true;
            breakable = false;
            price = 2;
            maxDuration = 0;
        } else if (itemID == 1) {
            this.setImage("Bomb.png");
            stackable = true;
            breakable = false;
            price = 10;
            maxDuration = 0;
        } else if (itemID == 2) {
            this.setImage("rope slot.png");
            stackable = true;
            breakable = false;
            price = 1;
            maxDuration = 0;
        } else if (itemID == 9) {
            this.setImage("Charcoal.png");
            stackable = false;
            breakable = false;
            price = 1;
            maxDuration = 0;
        } else if (itemID == 10) {
            this.setImage("iron ingot.png");
            stackable = false;
            breakable = false;
            price = 2;
            maxDuration = 0;
        } else if (itemID == 11) {
            this.setImage("silver ingot.png");
            stackable = false;
            breakable = false;
            price = 5;
            maxDuration = 0;
        } else if (itemID == 12) {
            this.setImage("gold ingot.png");
            stackable = false;
            breakable = false;
            price = 12;
            maxDuration = 0;
        } else if (itemID == 13) {
            this.setImage("diamond ingot.png");
            stackable = false;
            breakable = false;
            price = 28;
            maxDuration = 0;
        } else if (itemID == 14) {
            this.setImage("Emerald Pickup.png");
            stackable = false;
            breakable = false;
            price = 50;
            maxDuration = 0;
        } else if (itemID == 15) {
            this.setImage("lapis pickup.png");
            stackable = false;
            breakable = false;
            price = 95;
            maxDuration = 0;
        } else if (itemID == 20) {
            this.setImage("Iron Pickaxe.png");
            stackable = false;
            breakable = true;
            price = 6;
            maxDuration = 40;
        } else if (itemID == 22) {
            this.setImage("Silver Pickaxe.png");
            stackable = false;
            breakable = true;
            price = 10;
            maxDuration = 84;
        } else if (itemID == 24) {
            this.setImage("Gold Pickaxe.png");
            stackable = false;
            breakable = true;
            price = 21;
            maxDuration = 132;
        } else if (itemID == 26) {
            this.setImage("Diamond Pickaxe.png");
            stackable = false;
            breakable = true;
            price = 30;
            maxDuration = 220;
        } else if (itemID == 21) {
            this.setImage("Iron Drill.png");
            stackable = false;
            breakable = true;
            price = 8;
            maxDuration = 60;
        } else if (itemID == 23) {
            this.setImage("Silver Drill.png");
            stackable = false;
            breakable = true;
            price = 13;
            maxDuration = 106;
        } else if (itemID == 25) {
            this.setImage("Gold Drill.png");
            stackable = false;
            breakable = true;
            price = 21;
            maxDuration = 180;
        } else if (itemID == 27) {
            this.setImage("Diamond Drill.png");
            stackable = false;
            breakable = true;
            price = 50;
            maxDuration = 290;
        } else if (itemID == 28) {
            this.setImage("Iron Sword.png");
            stackable = false;
            breakable = true;
            price = 7;
            maxDuration = 75;
        } else if (itemID == 29) {
            this.setImage("Silver Sword.png");
            stackable = false;
            breakable = true;
            price = 10;
            maxDuration = 150;
        } else if (itemID == 30) {
            this.setImage("Gold Sword.png");
            stackable = false;
            breakable = true;
            price = 17;
            maxDuration = 240;
        } else if (itemID == 31) {
            this.setImage("Diamond Sword.png");
            stackable = false;
            breakable = true;
            price = 25;
            maxDuration = 300;
        }
                    else if (itemID == 32) {
            this.setImage("Health Potion.png");
            stackable = false;
            breakable = false;
            price = 2;
            maxDuration = 0;
        }
        else if (itemID == 33) {
            this.setImage("Speed Potion.png");
            stackable = false;
            breakable = false;
            price = 5;
            maxDuration = 0;
        }
        else if (itemID == 34) {
            this.setImage("Jump Potion.png");
            stackable = false;
            breakable = false;
            price = 5;
            maxDuration = 0;
        } else {
            this.setImage(emptyImage);
            stackable = false;
            price = 0;
            maxDuration = 0;
        }
    }

    /**
     * Updates the StacksDisplay
     */
    private void updateStacks() {
        if (stackable && stacks > 1) {
            stackDisplay.updateValue(stacks);
            hideStackDisplay(false);
        } else {
            stackDisplay.updateValue(1);
            hideStackDisplay(true);
        }
    }

    /**
     * Updates the durationDisplay
     */
    private void updateDuration() {
        if (breakable && duration > 0) {
            durationDisplay.setPercentage((double)duration/(double)maxDuration);
            hideDurationDisplay(false);
        } else {
            durationDisplay.setPercentage(0);
            hideDurationDisplay(true);
        }
    }

    public void checkHover()
    {
        if(Greenfoot.mouseMoved(this)&&!this.hovering)
        {
            this.hovering=true;	
            getWorld().addObject(hover,this.getX()+122,this.getY()+hover.getHeight());
        }
        else if(Greenfoot.mouseMoved(null)&&hovering&&!Greenfoot.mouseMoved(this))
        {
            getWorld().removeObject(hover);
            this.hovering=false;
        }
    }
}
