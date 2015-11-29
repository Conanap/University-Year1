import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Toolbar on the side of the screen that has selectable slots for item usage by user
 * 
 * @author Marco Ly
 * @author Bombs,Ropes,Torch functions: Roland Li
 * @version Jan 2015
 */
public class ToolBar extends Inventory
{
    private int actionDelay;

    private int curAction;
    InventoryItem selected;
    InventoryItem[] itemArray;
    ToolbarSlot[] slotArray;

    private Color bgColor;
    private GreenfootImage bgImage;
    private GreenfootImage baseImg = new GreenfootImage("1280x720 Hotbar.png");

    private GreenfootSound soundTorch = new GreenfootSound("TorchLight.mp3");
    private GreenfootSound soundBomb = new GreenfootSound("BombLight.mp3");
    private GreenfootSound soundRope = new GreenfootSound("RopeThrow.mp3");
    private GreenfootSound drinkPotion = new GreenfootSound("Drink.mp3");

    private int ID = 0;
    public ToolBar(){
        bgImage = new GreenfootImage("Sidebar.png");
        bgImage.drawImage(baseImg,0,180);
        setImage(bgImage);
        itemArray = new InventoryItem[5];
        slotArray = new ToolbarSlot[5];
        for (int i = 0; i < 5; i++) {
            slotArray[i] = new ToolbarSlot();
        }
        itemArray[1] = new InventoryItem(2, 15);
        itemArray[2] = new InventoryItem(1, 8);
        itemArray[3] = new InventoryItem(0, 20);

        soundRope.setVolume(30);
        soundBomb.setVolume(60);
        soundTorch.setVolume(50);
    }

    /**
     * Act - do whatever the ToolBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        actionDelay--;
        checkTool();
    }

    /**
     * Fills inventory slots with items and slots
     */
    public void addedToWorld(World Test) {
        fillInvSlots(Test);
        fillInvItems(Test);
    }

    /**
     * 
     */
    public void setID(int newID)
    {
        this.ID=newID;
    }

    /**
     * Attempts to remove an item from the itemArray
     * 
     * @param item  the InventoryItem to be removed
     */
    public void removeItem(InventoryItem item) {
        for (int i = 0; i < 5; i++){
            if (itemArray[i] == item) {
                itemArray[i] = null;
            }
        }
        if (selected == item) {
            selected = null;
            changeAction(curAction);
        }
    }

    /**
     * Adds an item to the itemArray. The position of the item is based on if it is on a certain toolbar slot
     * 
     * @param item  the item to be added
     */
    public void addItem(InventoryItem item) {
        for (int i = 0; i < 5; i++){
            if (slotArray[i].getX() == item.getX() && slotArray[i].getY() == item.getY()) {
                itemArray[i] = item;
            }
        }
        changeAction(curAction);
    }

    /**
     * Sets if this toolbar should have it's items hidden (this is used for when shop pops up, so that inventory items held in the toolbar do not overlap over the shop)
     * 
     * @param showing    whether or not the toolbar should be showing
     */
    public void setShowing(boolean showing){
        this.showing = showing;
        if (showing == false) {
            for (int i = 0; i < 5; i++) {
                if (itemArray[i] != null) {itemArray[i].hideStackDisplay(true); itemArray[i].hideDurationDisplay(true);}
            }
            updateBar(-1,0);
        } 
        else
        {
            for (int i = 0; i < 5; i++) {
                if (itemArray[i] != null) {
                    if (itemArray[i].isStackable()) itemArray[i].hideStackDisplay(false); 
                    if (itemArray[i].isBreakable()) itemArray[i].hideDurationDisplay(false);
                }
            }
            updateBar(curAction, 100);
        }
    }

    /**
     * This is used to remove any assets associated with the toolbar
     */
    public void removeAssets() {
        emptyInvSlots();
    }

    /**
     * Fills the toolbar with toolbar slots
     * 
     * @param Test  the world
     */
    private void fillInvSlots(World Test) {
        int x = 1132;
        int y = 314;
        for (int j = 0; j < 5; j++){
            if (j == 1) y+=83;
            else if (j > 1 && j < 4) y+=85;
            else if (j == 4) y+=84;
            Test.addObject(slotArray[j],x,y);   
        }
    }

    /**
     * Fills the inventory with any starting inventory items
     * 
     * @param Test  the world
     */
    private void fillInvItems(World Test) {
        for (int j = 0; j < 5; j++){
            if (itemArray[j] != null) {
                Test.addObject(itemArray[j], slotArray[j].getX(), slotArray[j].getY());
            }
        }
    }

    /**
     * Removes all inventory items of the toolbar from the world
     */
    private void emptyInvSlots() {
        for (int i = 0; i < 5; i++) {
            if (itemArray[i] != null) {itemArray[i].clearStackDisplay(); itemArray[i].clearDurationDisplay(); getWorld().removeObject(itemArray[i]);}
            getWorld().removeObject(slotArray[i]);
        }
    }

    /**
     * returns the currently selected inventory item
     */
    public InventoryItem getSelected() {
        return selected;
    }

    /**
     * Attempts to use the selected inventory item in the world
     * 
     * @param x the x coordinate for any used item, if successful
     * @param y the y coordinate for any used item, if successful
     */
    public void action(int x, int y){
        Test world = (Test)getWorld();
        if (itemArray[curAction-1] != null) {
            world.getPlayer().getPlayerArm().changeDig(false);
            if (actionDelay <= 0){
                if (itemArray[curAction-1].getID() == 0) {
                    placeTorch(x,y);
                    itemArray[curAction-1].addToStack(-1);
                } else if (itemArray[curAction-1].getID() == 1) {
                    throwBomb(x,y);
                    itemArray[curAction-1].addToStack(-1);
                } else if (itemArray[curAction-1].getID() == 2) {
                    throwRope(x,y);
                    itemArray[curAction-1].addToStack(-1);
                }
                else if (itemArray[curAction-1].getID() == 32) {
                    drinkHP();
                    itemArray[curAction-1].addToStack(-1);
                }
                else if (itemArray[curAction-1].getID() == 33) {
                    drinkSpeed();
                    itemArray[curAction-1].addToStack(-1);
                }
                else if (itemArray[curAction-1].getID() == 34) {
                    drinkJump();
                    itemArray[curAction-1].addToStack(-1);
                }
                actionDelay = 15;
            }
        }        
    }

    /**
     * Attempts to use the selected inventory item in the world, but with an alternate function
     * 
     * @param x the x coordinate for any used item, if successful
     * @param y the y coordinate for any used item, if successful
     */
    public void altAction(int x, int y){
        Test world = (Test)getWorld();
        if (itemArray[curAction-1] != null) {
            world.getPlayer().getPlayerArm().changeDig(false);
            if (actionDelay <= 0){
                if (itemArray[curAction-1].getID() == 0) {
                    placeTorch(x,y);
                    itemArray[curAction-1].addToStack(-1);
                } else if (itemArray[curAction-1].getID() == 1) {
                    placeBomb(x,y);
                    itemArray[curAction-1].addToStack(-1);
                } else if (itemArray[curAction-1].getID() == 2) {
                    placeRope(x,y);
                    itemArray[curAction-1].addToStack(-1);
                }
                else if (itemArray[curAction-1].getID() == 32) {
                    drinkHP();
                    itemArray[curAction-1].addToStack(-1);
                }
                else if (itemArray[curAction-1].getID() == 33) {
                    drinkSpeed();
                    itemArray[curAction-1].addToStack(-1);
                }
                else if (itemArray[curAction-1].getID() == 34) {
                    drinkJump();
                    itemArray[curAction-1].addToStack(-1);
                }
                actionDelay = 15;
            }
        } 
    }

    /**
     * Switches the currently selected item slot
     */
    public void changeAction(int action){
        if (action >= 1 && action <= 5){
            curAction = action;
            updateBar(action, 100);
        }
    }

    public void drinkHP(){
        Player player = (Player) getWorld().getObjects(Player.class).get(0);
        player.heal(100);
        drinkPotion.play();
    }

    public void drinkSpeed(){
        Player player = (Player) getWorld().getObjects(Player.class).get(0);
        player.movePotion(3,1200);
        drinkPotion.play();
    }

    public void drinkJump(){
        Player player = (Player) getWorld().getObjects(Player.class).get(0);
        player.jumpPotion(5,1200);
        drinkPotion.play();
    }

    /**
     * Throws a rope upwards from player
     *
     * @param x Coordinate
     * @param y Coordinate
     */
    private void throwRope(int x, int y){
        World currentWorld = getWorld();//Finds current world
        FlyRope rope = new FlyRope(250, ID);//Creates a rope object
        Test test = (Test) getWorld();
        test.newItemIn(x,y,0);
        ID++;
        currentWorld.addObject(rope, x, y); //Spawns rope at current location
        soundRope.play();
    }

    /**
     * places a rope at coordinates of player
     */
    private void placeRope(int x, int y){
        World currentWorld = getWorld();//Finds current world
        SetRope rope = new SetRope(250, 0, ID);//Creates a rope object
        Test test = (Test) getWorld();
        test.newItemIn(x,y,0);
        ID++;
        currentWorld.addObject(rope, x, y); //Spawns rope at current location
        soundRope.play();
    }

    /**
     * Throws a bomb that detonates on impact
     */
    private void throwBomb(int x, int y){

        World currentWorld = getWorld();//Finds current world
        MouseInfo mouse = Greenfoot.getMouseInfo(); //To get mouse coordinates
        try{
            ImpactBomb bomb = new ImpactBomb(mouse.getX(), mouse.getY());//Creates a bomb object aiming at mouse coordinates
            currentWorld.addObject(bomb, x, y); //Spawns bomb at current location
        }
        catch(NullPointerException e){
            //Play sound?
        }
        actionDelay = 20; //Sets a longer delay to prevent accidental throwing
        soundBomb.play();
    }

    /**
     * Places a timed bomb at players coordinate
     */
    private void placeBomb(int x, int y){
        World currentWorld = getWorld();//Finds current world
        TimerBomb bomb = new TimerBomb();
        currentWorld.addObject(bomb, x, y); //Spawns bomb at current location
        soundBomb.play();
    }

    /**
     * Places a torch at the players x and y coordinate
     */
    private void placeTorch(int x, int y){
        World currentWorld = getWorld();//Finds current world
        Torch torch = new Torch(400, ID);
        Test test = (Test) getWorld();
        test.newItemIn(x,y,1);
        ID++;
        currentWorld.addObject(torch, x,y); //Spawns torch at current location
        soundTorch.play();
    }

    /**
     * Updates the bar according to which item is selected, and lowers the image transparency of the other items, updates the selected inventoryItem
     */
    public void updateBar(int action, int transparency){
        if (action == -1) {
            for (int i = 0; i < 5; i++) {
                if (itemArray[i] != null) {itemArray[i].getImage().setTransparency(transparency);}
            }
        } else {
            if (itemArray[action-1] != null) {
                selected = itemArray[action-1];
            } else {
                selected = null;
            }
            for (int i = 0; i < 5; i++) {
                if (itemArray[i] != null){
                    itemArray[i].getImage().setTransparency(transparency);
                }
                slotArray[i].getImage().setTransparency(110);
            }
            if (itemArray[action-1] != null) itemArray[action-1].getImage().setTransparency(255);
            else slotArray[action-1].getImage().setTransparency(190);
        }
    }

    /**
     * Checks to see if the selected item is a certain ID that has a corresponding playerArm image, and updates the playerArm accordingly
     */
    private void checkTool() {
        Test world = (Test)getWorld();
        if (itemArray[curAction-1] != null) {
            if (itemArray[curAction-1].getID() == 20) {
                world.getPlayer().getPlayerArm().update(1);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 21) {
                world.getPlayer().getPlayerArm().update(2);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 22) {
                world.getPlayer().getPlayerArm().update(3);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 23) {
                world.getPlayer().getPlayerArm().update(4);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 24) {
                world.getPlayer().getPlayerArm().update(5);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 25) {
                world.getPlayer().getPlayerArm().update(6);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 26) {
                world.getPlayer().getPlayerArm().update(7);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 27) {
                world.getPlayer().getPlayerArm().update(8);
                world.getPlayer().getPlayerArm().changeDig(true);
            } else if (itemArray[curAction-1].getID() == 0) {
                world.getPlayer().getPlayerArm().update(9);
                world.getPlayer().getPlayerArm().changeDig(false);
            } else if (itemArray[curAction-1].getID() == 1) {
                world.getPlayer().getPlayerArm().update(10);
                world.getPlayer().getPlayerArm().changeDig(false);
            } else if (itemArray[curAction-1].getID() == 2) {
                world.getPlayer().getPlayerArm().update(11);
                world.getPlayer().getPlayerArm().changeDig(false);
            } else if (itemArray[curAction-1].getID() == 28) {
                world.getPlayer().getPlayerArm().update(12);
                world.getPlayer().getPlayerArm().changeDig(false);
            } else if (itemArray[curAction-1].getID() == 29) {
                world.getPlayer().getPlayerArm().update(13);
                world.getPlayer().getPlayerArm().changeDig(false);
            } else if (itemArray[curAction-1].getID() == 30) {
                world.getPlayer().getPlayerArm().update(14);
                world.getPlayer().getPlayerArm().changeDig(false);
            } else if (itemArray[curAction-1].getID() == 31) {
                world.getPlayer().getPlayerArm().update(15);
                world.getPlayer().getPlayerArm().changeDig(false);
            }
            else if (itemArray[curAction-1].getID() == 32) {
                world.getPlayer().getPlayerArm().update(16);
                world.getPlayer().getPlayerArm().changeDig(false);
            }
            else if (itemArray[curAction-1].getID() == 33) {
                world.getPlayer().getPlayerArm().update(17);
                world.getPlayer().getPlayerArm().changeDig(false);
            }
            else if (itemArray[curAction-1].getID() == 34) {
                world.getPlayer().getPlayerArm().update(18);
                world.getPlayer().getPlayerArm().changeDig(false);
            }
        } else {
            world.getPlayer().getPlayerArm().update(0);
            world.getPlayer().getPlayerArm().changeDig(true);
        }
    }
}