import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ropes that simply stay where they are put down and extend downwards until
 * they collide with a block.
 * 
 * @author Roland Li 
 * @version 0.45
 */
public class SetRope extends Rope
{
    //Extra mutators to give group mates options in spawning it
    public SetRope () {
        curSpeed = 5; //Speed of rope lowering
        maxSpeed = 10;
        maxLength = 250;
    }
    
    public SetRope (boolean changeAdd, int ID) {
        curSpeed = 5; //Speed of rope lowering
        maxSpeed = 10;
        maxLength = 250;
        IDNum=ID;
    }

    public SetRope(int mLength){
        curSpeed = 5; //Speed of rope lowering
        maxLength = mLength;
    }

    public SetRope(int mLength, int cLength){
        this(mLength);
        curLength = cLength;
    }

    public SetRope(int mLength, int cLength, int ID){
        this(mLength,cLength);
        IDNum = ID;
    }

    public void addedToWorld(World currentWorld){
        xCo = getX();
        yCo = getY();
    }

    /**
     * Act - do whatever the SetRope wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //Makes rope accelerate in dropping speed
        if (curSpeed < maxSpeed){curSpeed++;} 
        extend(curSpeed); //Extend
        resetPosition(xCo, yCo); //Reset position since length changed
    }    
}
