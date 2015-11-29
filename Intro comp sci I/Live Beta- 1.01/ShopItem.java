import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.lang.NullPointerException;
/**
 * Write a description of class InventoryItem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopItem extends Slots
{
    private Test world;
    private int itemID;
    private int price;
    
    private boolean hovering = false;
    private HoverText hover;
    public ShopItem(int ID) {
        super();
        itemID = ID;
        hover = new HoverText(ID);
        update();
    }

    /**
     * Act - do whatever the Inventory wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        world = (Test)getWorld();
        if (Greenfoot.mouseClicked(this)) {
            world.getShopInv().buying(this);
        }
    }   

    public int getPrice() {
        return price;
    }

    public int getID() {
        return itemID;
    }

    public void setID(int ID) {
        itemID = ID;
        update();
    }

    private void update() {
        if (itemID == 0) {
            this.setImage("Torch.png");
            price = 4;
        } else if (itemID == 1) {
            this.setImage("Bomb.png");
            price = 26;
        } else if (itemID == 2) {
            this.setImage("rope slot.png");
            price = 2;
        } else if (itemID == 20) {
            this.setImage("Iron Pickaxe.png");            
            price = 20;
        } else if (itemID == 22) {
            this.setImage("Silver Pickaxe.png");            
            price = 35;
        } else if (itemID == 24) {
            this.setImage("Gold Pickaxe.png");            
            price = 61;
        } else if (itemID == 26) {
            this.setImage("Diamond Pickaxe.png");            
            price = 90;
        } else if (itemID == 21) {
            this.setImage("Iron Drill.png");
            price = 27;
        } else if (itemID == 23) {
            this.setImage("Silver Drill.png");            
            price = 43;
        } else if (itemID == 25) {
            this.setImage("Gold Drill.png");            
            price = 70;
        } else if (itemID == 27) {
            this.setImage("Diamond Drill.png");            
            price = 150;
        } else if (itemID == 28) {
            this.setImage("Iron Sword.png");
            price = 21;
        } else if (itemID == 29) {
            this.setImage("Silver Sword.png");
            price = 32;
        } else if (itemID == 30) {
            this.setImage("Gold Sword.png");
            price = 55;
        } else if (itemID == 31) {
            this.setImage("Diamond Sword.png");
            price = 82;
        }
                    else if (itemID == 32) {
            this.setImage("Health Potion.png");
            price = 5;
        }
        else if (itemID == 33) {
            this.setImage("Speed Potion.png");
            price = 10;
        }
        else if (itemID == 34) {
            this.setImage("Jump Potion.png");
            price = 10;       
        } else {
            this.setImage(emptyImage);
            price = 0;
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
