import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.awt.Color;
/**
 * Write a description of class PlyrInv here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlyrInv extends Inventory
{
    private InventoryItem[][] itemArray; // Array of InventoryItems
    private InventorySlot[][] slotArray;
    private TrashSlot trash;
    private RepairSlot repair;
    private StringDisplay coinDisplay;
    private boolean repairOpen;
    public PlyrInv() {
        super();
        repairOpen = false;
        trash = new TrashSlot();
        repair = new RepairSlot();
        coinDisplay = new StringDisplay(100,40,40,Color.YELLOW);
        itemArray = new InventoryItem[6][10];
        slotArray = new InventorySlot[6][10];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                slotArray[i][j] = new InventorySlot();
            }
        }
    }

    public void addedToWorld(World Test) {
        fillInvSlots(Test);
        fillInvItems(Test);
        Test.addObject(trash, 638, 235);
        Test.addObject(coinDisplay, 644, 380);
    }

    public void setShowing(boolean showing){
        this.showing = showing;
    }

    public void removeAssets() {
        emptyInvSlots();
        trash.clearAsset();
        getWorld().removeObject(trash);
        getWorld().removeObject(coinDisplay);
    }

    private void fillInvSlots(World Test) {
        int x = 69;
        int y = 185;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 6; j++){
                if (i >= 7 && i <= 9) {
                    Test.addObject(slotArray[j][i], 69 + 50*(i)+(-6+i), 185 + 50*(j));
                } 
                else
                {
                    Test.addObject(slotArray[j][i], 69 + 50*(i), 185 + 50*(j));
                }     
            }
        }
    }

    private void fillInvItems(World Test) {
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 6; j++){
                if (itemArray[j][i] != null) {
                    Test.addObject(itemArray[j][i], slotArray[j][i].getX(), slotArray[j][i].getY());
                }
            }
        }
    }

    private void emptyInvSlots() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                if (itemArray[i][j] != null) {itemArray[i][j].clearStackDisplay(); itemArray[i][j].clearDurationDisplay(); getWorld().removeObject(itemArray[i][j]);}
                getWorld().removeObject(slotArray[i][j]);
            }
        }
    }

    public void removeItem(InventoryItem item) {
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++) {
                if (itemArray[i][j] == item) {
                    itemArray[i][j] = null;
                }
            }
        }
    }

    public boolean addItem(int ID) {
        InventoryItem newItem = new InventoryItem(ID);
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++) {
                if (newItem.isStackable() && itemArray[i][j] != null && itemArray[i][j].getID() == ID) {
                    itemArray[i][j].addToStack(1);
                    return true;
                }
            }
        }  
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++) {
                if  (itemArray[i][j] == null) {
                    itemArray[i][j] = newItem;
                    getWorld().addObject(itemArray[i][j], slotArray[i][j].getX(), slotArray[i][j].getY());
                    return true;
                }
            }
        }
        return false;
    }

    public void addItem(InventoryItem item) {
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++) {
                if (item.isStackable() && itemArray[i][j] != null && itemArray[i][j].getID() == item.getID()) {
                    InventoryItem newItem = item;
                    itemArray[i][j].addToStack(item.getStacks());
                    item.clearStackDisplay();
                    item.clearDurationDisplay();
                    getWorld().removeObject(item);
                    return;
                }
            }
        }  
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++) {
                if (slotArray[i][j].getX() == item.getX() && slotArray[i][j].getY() == item.getY()) {
                    itemArray[i][j] = item;
                    return;
                }
            }
        }
    }
    
    public void addItem(int x, int y, int ID, int stacks, int duration) {
        InventoryItem newItem = new InventoryItem(ID);
        itemArray[x][y] = newItem;
        newItem.setStacks(stacks);
        newItem.setDuration(duration);
    }

    public void updateCoinDisplay(int coins) {
        coinDisplay.updateValue(coins);
    }

    public boolean getRepairOpen() {
        return repairOpen;
    }
    
    public RepairSlot getRepair() {
        return repair;
    }
    
    public InventoryItem[][] getItemArray() {
        return itemArray;
    }

    public void toggleRepairShop(boolean trueFalse) {
        if (trueFalse) { 
            this.setImage("Repair Pop-up.png");
            trash.clearAsset();
            getWorld().removeObject(trash);
            getWorld().addObject(repair, 637,238);
            repairOpen = true;
        } else {
            this.setImage("1280x720 Player Inventory for Shop.png");
            getWorld().addObject(trash, 638, 235);
            repair.clearAsset();
            getWorld().removeObject(repair);            
            repairOpen = false;
        }
    } 
        
    public void deleteAllItems() {
         for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++) {
                itemArray[i][j] = null;
            }
        }
    }
}