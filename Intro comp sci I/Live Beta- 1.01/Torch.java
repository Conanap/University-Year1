import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * The torch class.
 * Torch will light up nearby blocks by increasing the transparency of the black mask.
 * 
 * @author Tyler Zhang
 * @version (a version number or a date)
 */
public class Torch extends Consumables
{
    private int range;
    private boolean firstRun;
    private int IDNum;

    /**
     * Create a torch with a certein range.
     */
    public Torch(int rg)
    {
        baseImg = new GreenfootImage("Torch Object.png");
        setImage(baseImg);
        range = rg;
        firstRun = true;
    }
    
    public Torch(int rg, int id){
        this(rg);
        IDNum = id;
    }

    public int getRange(){
        return range;
    }
    
    public void act()
    {
        if(firstRun)
        {
            notifyBlocks();
            firstRun = false;
        }
    }

    /**
     * Notifiy the block around the torch to adjust brightness.
     */
    private void notifyBlocks()
    {
        for (int i = 0; i < 4; i++){
            int radius = range - 90 + i*30;
            List<Block> blocks = getObjectsInRange(radius,Block.class);
            if (blocks.size() != 0)
            {
                for (Block blo: blocks)
                {
                    blo.increaseTrans(50);
                }
            }
        } 
    }
    
    public int returnID(){
        return IDNum;
    }
}
