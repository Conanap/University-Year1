import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * The class for all the blocks in the game.
 * 2‘d’ designates dirt
 * 1‘c’ designates cobble stone
 * 3‘i’ designates iron ore block
 * 4‘g’ designates gold ore block
 * 5‘s’ designates silver ore block
 * 6‘a’ designates diamond block
 * 7‘e’ designates emerald ore block
 * 8 'l'  designates lapis ore block
 * 
 * @author (Tyler Zhang), modified by Albion Fung and Jasper Tu
 * @version (January 2015)
 */
public class Block extends Actor
{
    private int type; // Indict the type of block.
    private static int TOTAL_TYPE = 9; // The total number of types.
    private int health; // The current health of the block
    private int healthMax; // The max health.
    private int xCor, yCor;
    private String name; // The hover display name for the block.
    private GreenfootImage bg;
    private GreenfootImage fg;
    private GreenfootImage bm;
    private boolean mouseOver;
    private int trans; // The transparency of the black mask.
    private int orgTrans;
    private int resetImage = 3;

    private GreenfootSound stoneSound = new GreenfootSound("StoneBreak.mp3");
    private GreenfootSound dirtSound = new GreenfootSound("DirtBreak.mp3");

    // Instantiation of all the different types of block drops available in-game ...
    private Drops oreDrop;

    /**
     * Construct a block with default type and default darkness.
     */
    public Block()
    {
        this(1,1,1,255);
    }

    public Block(int yCoor, int xCoor, int tp)
    {
        xCor=xCoor;
        yCor=yCoor;
        blockCon(tp);
        orgTrans = 255;
        trans = orgTrans;
    }

    public Block(int yCoor, int xCoor, int tp, int tr)
    {
        xCor=xCoor;
        yCor=yCoor;
        blockCon(tp);
        orgTrans = tr;
        trans = orgTrans;
    }

    /**
     * Construct a block with a type.
     * @param tp The type of the block.
     * @param tr The transparency of the block.
     */
    public Block(int tp, int tr)
    {
        blockCon(tp);
        orgTrans = tr;
        trans = orgTrans;
    }

    public void blockCon(int tp)
    {
        if (tp < 0){
            tp = 0;
        }else if (tp > TOTAL_TYPE){
            tp = TOTAL_TYPE;
        }

        switch(tp){
            case 2:
            name = "dirt_topmost_layer";
            healthMax = 5;
            type = 2;
            break;

            case 1:
            name = "cobble";
            healthMax = 10;
            type = 1;
            break;

            case 3:
            name = "iron";
            healthMax = 15;
            type = 3;
            break;

            case 4:
            name = "silver";
            healthMax = 25;
            type = 4;
            break;

            case 5:
            name = "gold";
            healthMax = 40;
            type = 5;
            break;

            case 6:
            name = "diamond";
            healthMax = 100;
            type = 6;
            break;

            case 7:
            name = "emerald";
            healthMax = 200;
            type = 7;
            break;

            case 8:
            name = "lapis";
            healthMax = 500;
            type = 8;
            break;
            
            case 9:
            name = "coal";
            healthMax = 8;
            type = 9;

            default:
            break;
        }

        if(tp==2 && yCor!=0){
            name="dirt";
            type = 2;
        }
        health = healthMax;
        mouseOver = false;

        bg = new GreenfootImage(name + ".png");
        fg = new GreenfootImage(bg.getWidth(), bg.getHeight());
        bm = new GreenfootImage(bg.getWidth(), bg.getHeight());
    }

    public void addedToWorld(World currentWorld){
        updateImg();
    }

    /**
     * Do defualt damage to the block, take away 1 hp.
     */
    public void damage()
    {
        damage(1);
    }
    
    //Block has no act methods to reduce lack

    /**
     * Do a certain hamage to the block, take away a certain hp.
     * @para dmg The amount of damage.
     */
    public void damage(int dmg)
    {
        if (name.equals("iron") && dmg > 2 || name.equals("silver") && dmg > 4 || name.equals("gold") && dmg > 5
        || name.equals("diamond") && dmg > 10 || name.equals("emerald") && dmg > 2 || name.equals("lapis") && dmg > 2
        || name.equals("cobble") || name.equals("dirt") || name.equals("dirt_topmost_layer")||name.equals("coal")) {
            health -= dmg;
            int healthPer = (health * 100)/ healthMax;
            if (healthPer <= 30)
            { // If the hp % is less than 30, draw fracture1;
                bg.drawImage(new GreenfootImage("frac3.png"),0,0);
            }else if (healthPer <= 60)
            { // If the hp % is less than 60, draw fracture2;
                bg.drawImage(new GreenfootImage("frac2.png"),0,0);
            }else if (healthPer <= 90)
            { // If the hp % is less than 90, draw fracture3;
                bg.drawImage(new GreenfootImage("frac1.png"),0,0);
            }
            updateImg();
            if (health <= 0)
            {
                destroy();
            }
        }
    }

    /**
     * Destroy this block.
     */
    private void destroy()
    {
        Test test = (Test)getWorld();
        test.mapMod(yCor,xCor,0);
        dropItem(type);
        if (name == "dirt" || name == "dirt_topmost_layer"){
            dirtSound.play();
        }
        else{
            stoneSound.play();
        }
        getWorld().removeObject(this);
    }

    /**
     * Basing on the type of the block, item might be dropped.
     */
    private void dropItem(int type)
    {
        if (type > 2 && type < 10){
            oreDrop = new Drops(type);
            getWorld().addObject(oreDrop, getX(), getY());
        }
    }

    /**
     * Highlight the current block with red or green.
     * @param green Highlight the color of the block green if true, red if false.
     */
    public void highlight(boolean green)
    {
        if(green){
            fg.setColor(Color.GREEN);
            fg.fill();
            fg.setTransparency(100);
            updateImg();
        }else{
            fg.setColor(Color.RED);
            fg.fill();
            fg.setTransparency(100);
            updateImg();
        }
        mouseOver = true;
        resetImage = 1; //Reset counter to refresh image
    }

    public void unhighlight(){
        fg.setTransparency(0);
        updateImg();
    }

    public void resetImg()
    {
        setImage(bg);
        mouseOver = false;
    }

    /**
     * Set the image of the block.
     */
    private void updateImg()
    {
        GreenfootImage img = new GreenfootImage(bg);
        bm.setColor(Color.BLACK);
        bm.fill();
        bm.setTransparency(trans);

        img.drawImage(bm, 0, 0);
        img.drawImage(fg, 0, 0);
        setImage(img);
    }

    public void increaseTrans(int num)
    {
        trans -= num;
        if(trans<0){
            trans = 0;
        }
        updateImg();
    }

    /**
     * Return the distance from this to another object.
     * 
     * @return double the distance of between two actors.
     */
    protected double getDistance(Actor actor) {
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());
    }
}