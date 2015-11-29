import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class ShopInv here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopInv extends Inventory
{    
    private Test world;
    private ShopItem[][] itemArray;
    private ShopClose closeButton;
    private BuyButton buyButton;
    private SellButton sellButton;
    private InventoryItem sellItem;
    private ShopItem buyItem;
    private StringDisplay priceDisplay;
    public ShopInv() {
        super();
        priceDisplay = new StringDisplay(55,40,40,Color.YELLOW);
        closeButton = new ShopClose();
        buyButton = new BuyButton();
        sellButton = new SellButton();
        itemArray = new ShopItem[6][7];
        itemArray[0][0] = new ShopItem(0);
        itemArray[0][1] = new ShopItem(1);
        itemArray[0][2] = new ShopItem(2);
        itemArray[0][3] = new ShopItem(3);
        itemArray[1][0] = new ShopItem(28);
        itemArray[1][1] = new ShopItem(29);
        itemArray[1][2] = new ShopItem(30);
        itemArray[1][3] = new ShopItem(31);
        itemArray[2][0] = new ShopItem(20);
        itemArray[2][1] = new ShopItem(22);
        itemArray[2][2] = new ShopItem(24);
        itemArray[2][3] = new ShopItem(26);
        itemArray[3][0] = new ShopItem(21);
        itemArray[3][1] = new ShopItem(23);
        itemArray[3][2] = new ShopItem(25);
        itemArray[3][3] = new ShopItem(27);
        itemArray[4][0] = new ShopItem(32);
        itemArray[4][1] = new ShopItem(33);
        itemArray[4][2] = new ShopItem(34);	
    }

    public void addedToWorld(World Test) {
        fillInvSlots(Test);
        Test.addObject(priceDisplay, 969, 598);
        Test.addObject(closeButton,1250,46);
        Test.addObject(buyButton,1047,596);
        Test.addObject(sellButton,1147,597);
    }

    public void setShowing(boolean showing){
        this.showing = showing;
    }

    public void removeShopItems() {
        removeButtons();
        emptyInvSlots();
        getWorld().removeObject(priceDisplay);
    }

    private void fillInvSlots(World Test) {
        int x = 882;
        int y = 280;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                if (j > 0) {
                    if (j%2 == 0) {
                        x+=51;
                    }
                    else {
                        x+=52;
                    }
                }
                if (itemArray[i][j] != null) Test.addObject(itemArray[i][j], x, y);  
            }
            if (i%2 == 0) {
                y+=52;
            }
            else {
                y+=51;
            }
            x=882;
        }
    }

    private void emptyInvSlots() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                getWorld().removeObject(itemArray[i][j]);
            }
        }
    }

    private void removeButtons() {
        getWorld().removeObject(closeButton);
        getWorld().removeObject(buyButton);
        getWorld().removeObject(sellButton);
    }

    public void buying(ShopItem buyItem) {
        this.buyItem = buyItem;
        updatePriceDisplay(buyItem.getPrice());
    }

    public void selling(InventoryItem sellItem){
        this.sellItem = sellItem;
        updatePriceDisplay(sellItem.getPrice()*sellItem.getStacks());
    }

    public void updatePriceDisplay(int price){
        priceDisplay.updateValue(price);
    }

    /**
     * Act - do whatever the ShopInv wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(closeButton)) {
            Test world = (Test)getWorld();
            setShowing(false);
            removeShopItems();
            world.getPlayer().getToolBar().setShowing(true);
            world.getPlayer().toggleInventory();
            world.removeObject(this);
        }
        if (Greenfoot.mouseClicked(sellButton)) {
            if (sellItem != null) {
                updatePriceDisplay(0);
                world = (Test)getWorld();
                if (world.getPlayer().addCoins(sellItem.getPrice())){
                    world.getPlayer().getInventory().removeItem(sellItem);
                    world.removeObject(sellItem);
                    sellItem = null;
                }
            }
        }
        if (Greenfoot.mouseClicked(buyButton)) {
            if (buyItem != null) {
                world = (Test)getWorld();
                world.getPlayer().addCoins(buyItem.getPrice()*-1);
                world.getPlayer().getInventory().addItem(buyItem.getID());
            }
        }
    }
}    
