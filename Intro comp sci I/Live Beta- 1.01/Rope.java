import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class of object that allows the player to move up and down it.
 * 
 * 
 * @author Roland Li
 * @version 0.45
 */
public abstract class Rope extends Consumables
{
    protected boolean mobile;
    protected int extraFallTime;//Amount of time rope spends decelerating/fall downwards
    protected int curLength, maxLength;
    protected int bounceCount;
    protected int IDNum;

    protected int xCo,yCo; 
    GreenfootImage baseImg = new GreenfootImage("rope.png");

    /**
     * Makes the rope extend downwards until it reaches it's maximum length. Rate
     * decided by parameter.
     *
     * @param amount Amount to extend
     */
    protected void extend(int amount){
        if (!touchGround() && amount > 0){
            curLength += amount;
            if (curLength > maxLength){ 
                curLength = maxLength; //Resets value to max
                //Don't call bounce here to prevent flying ropes from bouncing in air
            }
            GreenfootImage newImage = new GreenfootImage (baseImg.getWidth(), curLength); //Create a new image of the appropriate length
            newImage.drawImage(baseImg, 0, 0); //Draw the base image onto it
            this.setImage(newImage); //Set the rope's image to this image
        }
    }

    /**
     * Basic method to check if the rope is touching a block directly below it
     *
     * @return True if yes, false if no
     */
    protected boolean touchGround(){
        int offset = this.getImage().getHeight()/2;

        if (getOneObjectAtOffset(0, offset, Block.class) != null){    
            return true; //Touching a block because object found below 
        }

        return false; //Not touching a block
    }

    /**
     * Resets the rope position to lock back onto the inputted coordinates.
     * 
     * @param x Coordinate
     * @param y Coordinate
     */
    protected void resetPosition(int x, int y){ //Makes sure the rope latches to the spot it was made
        int adjust = this.getImage().getHeight()/2;
        setLocation(x, y + adjust); //Puts the top of the image to the inputted point
    }
    
    //Excessive amounts of accessors for the world method to use in order to store data

    public int returnX(){
        return getX();
    }
    
    public int returnY(){
        return getY();
    }
    
    public int returnCLength(){
        return curLength;
    }
    
    public int returnMLength(){
        return maxLength;
    }
    
    public int returnID(){
        return IDNum;
    }
}
