import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Parent class of all drops for the game.  Allows block drops to have physics functionality (namely, gravity).
 * 
 * @author Jasper Tu
 * @version January 2015
 */
public class Drops extends Actor
{
    private static int velocity = 0;
    private int type;
    private GreenfootImage bg;
    private String name;

    public Drops(int tp)
    {
        type = tp;
        switch(tp){
            case 3:
            name = "iron";
            break;

            case 4:
            name = "silver";
            break;

            case 5:
            name = "gold";
            break;

            case 6:
            name = "diamond";
            break;

            case 7:
            name = "emerald";
            break;

            case 8:
            name = "lapis";
            break;

            case 9:
            name = "coal";
            break;
            
            default:
            break;
        }
        
        bg = new GreenfootImage(name + ".png");
        bg.scale(15, 15);
        setImage(bg);
    }
    
    /**
     * Allows all different types of block drops to travel downwards after block has been broken, for player pick-up.
     * In essence, this accurately emulates the force of gravity.
     */
    public void dropStuff()
    {
        Actor block = getOneObjectAtOffset(0, getImage().getHeight() / 2, Block.class);
        Actor blockTwo = getOneObjectAtOffset(0, - getImage().getHeight() / 2, Block.class);
        Actor blockThree = getOneObjectAtOffset(0, getImage().getHeight() / 2 - 1, Block.class);
        if(block != null)
        {
            velocity = 0;
        }
        else if (blockTwo != null)
        {
            if(velocity <= 0)
            {
                setLocation(getX(), getY() - velocity + 1);
            }
            else
            {
                setLocation (getX(), getY() + velocity);
            }
        }
        else
        {
            if(velocity <= 4)
            {
                velocity += 2;
            }
            else if (velocity == 6)
            {
                velocity += 6;
            }
            setLocation(getX(), getY() + velocity);
        }
        if(blockThree != null)
        {
            setLocation(getX(), getY() - 3);
        }
    }
    
    public void act()
    {
        dropStuff();
    }
    
    public int getType()
    {
        return type;
    }
}