import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math; //For Math.pow

/**
 * The abstract parent class for all the characters in this game.
 * Including player and different types of enemies.
 * 
 * @author Roland Li
 * @version 0.45
 */
public abstract class Characters extends Actor
{
    //Stats
    protected int maxHP, curHP; //Health points

    //Movement
    protected int maxSpeed, curSpeed; //Current speed; positives means moving right, negatives left.
    protected int maxMoveDelay, curMoveDelay; //To help smoothen/slow movement- only moves once cur = max
    protected int maxSlowDelay, curSlowDelay; //To help smoothen/slow rate of deceleration- only decelerates when cur = max
    protected int moveFrame;
    protected boolean moveDir; // Indicate the direction of character movement. True for right, false for left
    protected boolean newDir;

    protected int maxFallSpeed, curFallSpeed;
    protected int maxFallDelay, curFallDelay; //To help smoothen/slow rate of acceleration downwards

    protected int maxJumpCount, curJumpCount; //Can countinue to gain altitude as long as curJumpCount is under the max
    protected int jumpSpeed; //Constant number; counteracted by gravity (increasing curFallSpeed)
    private int blockSize = 40; //Used in block detection calculations

    protected boolean onRope; //To prevent gravity from acting when player is on a rope
    //Even though it only affects player, all characters share the same
    //gravity method

    protected GreenfootImage base; //Base image 

    //Sounds
    protected GreenfootSound fall = new GreenfootSound("Fall.mp3");
    protected GreenfootSound hitDamage = new GreenfootSound("HitDamage.mp3");

    abstract void moveX(int amount); //Move horizontally; not called simply move(int amount) because Greenfoot already uses it

    /**
     * Do damage to a character. If the character has no more hp, calls the die method.
     *
     * @param num The amount of hp to subtract
     */
    protected void damage(int num){
        if (num > 0){
            curHP -= num;
            if (curHP <= 0){
                curHP = 0;
                die();
            }
        }
    }

    /**
     * Give health to a character
     *
     * @param num The amount of hp to add
     */
    protected void heal(int num){
        if (num > 0){
            curHP += num;
            if (curHP > maxHP){
                curHP = maxHP;
            }
        }
    }

    /**
     * Removes object from world
     */
    protected void die(){
        getWorld().removeObject(this);
    }

    /**
     * Increases character's movement speed. Speeds cannot exceed limits. Speed appropriately distributed
     * depending on the direction of movement.
     *
     * @param change The amount to change by
     */
    protected void accelerate(int change){
        curSpeed += change;
        if (curSpeed > maxSpeed){
            curSpeed = maxSpeed;
        }
        else if (curSpeed <= maxSpeed*-1){
            curSpeed = maxSpeed*-1;
        }
    }

    /**
     * Reduces horizontal movement speed caused by moveX().
     * 
     * *Note, counteracts move() if the parameters entered in move() is 1
     */
    protected void decelerate(){
        if (curSpeed != 0){
            if (!moveCollision(curSpeed)){ //Checks if block is directly ahead of current path
                //If there is space infront
                setLocation(getX()+curSpeed, getY());
                curSlowDelay++;

                if (curSlowDelay >= maxSlowDelay){
                    if (curSpeed > 0){
                        curSpeed--;
                    }
                    else if(curSpeed < 0){
                        curSpeed++;
                    }
                    curSlowDelay = 0; //Reset counter
                }
            }
        }
    }

    /**
     * Causes characters to fall down, accelerating at a defined rate if the player is not touching
     * the ground or on a rope. Characters cannot exceed their maximum fall speeds. Also deals damage to
     * characters that are touching the ground, based off the fallspeed they have.
     *
     */
    protected void gravity(){
        if (!touchGround() && onRope == false){//Not touching a block on the ground below
            if (curFallDelay == maxFallDelay){
                curFallSpeed++; //Accelerates falling rate
                curFallDelay = 0; //Resets counter
            }
            else{ curFallDelay++;}

            if (curFallSpeed > maxFallSpeed){
                curFallSpeed = maxFallSpeed; //Resets falling rate to max if it exceeds it
            }
            setLocation(getX(), getY()+curFallSpeed); //Changes player position at current rate
        }
        else{ //Touching a block on the ground below
            if(curFallSpeed > 14){ //Does damage to the player in relation to the fall speed
                damage((int)Math.pow(4.0, curFallSpeed*0.13)); //Makes the damage exponentially get higher
                fall.play();
            }
            curFallSpeed = 0;
        }
    }

    /**
     * Checks if the player is tourching the ground based off of their size and positioning
     *
     * @return True if touching, false if not
     */
    protected boolean touchGround(){
        double temp = base.getHeight()*0.55; //Rough distance from player center; to detect for ground block.
        int offset = (int) temp;
        Actor object = new Block(); //Stores the object to check for. Can vary from blocks to spikes etc. Initalized to prevent error

        for (int i = 0; i < 3; i++){//Checks for each condition where the player touches the ground
            if (i == 0) {
                object = getOneObjectAtOffset(0, offset, Block.class);} //Checks directly below player
            else if (i == 1){
                object = getOneObjectAtOffset(base.getWidth()/2 - 6, offset, Block.class); } //Checks for blocks at the bottom right corner
            else if (i == 2){ 
                object = getOneObjectAtOffset(base.getWidth()/2*-1 + 6, offset, Block.class);}//Checks for blocks at the bottom left corner

            if (object != null){ //Sets the player to the proper distance from the ground
                setLocation(getX(), object.getY() - blockSize/2 - base.getHeight()/2);            
                return true; //Touching a block because object found below feet
            }
        }
        return false; //Not touching a block
    }

    /**
     * Checks if the player is about to collide with a block based on direction and current movement speed.
     *
     * @param direction The direction the player is moving in. Positive = towards the right, Negative = towards the left
     * @return True if the player collides, False if not
     */
    protected boolean moveCollision(int direction){
        int offset; //Distance to detect for edge of block
        if (direction < 0){
            offset = base.getWidth()/2*-1 - curSpeed - 6; //Looks to the left of the player; -6 to work around a bug
        }
        else {
            offset = base.getWidth()/2 + curSpeed + 3; //Looks to the right of the player; + 3 to work around a bug
        }

        Actor object = new Block(); //Stores the object to check for. Can vary from blocks to spikes etc. Initalized to prevent error
        for (int i = 0; i < 3; i++){ //Checks for each condition that could collide
            if (i == 0){ 
                object = getOneObjectAtOffset(offset, 0, Block.class);} //Checks for nearby block straight ahead path
            else if (i == 1){
                object = getOneObjectAtOffset(offset, base.getHeight()/2*-1 - 1, Block.class);} //Checks for block infront of head
            else if (i == 2){
                object = getOneObjectAtOffset(offset, base.getHeight()/2 - 1, Block.class);} //Checks for block infront of legs
            //If there is a block the player is about to collide with

            if (object != null){ 
                curSpeed = 0; //Set speed to zero, since there is a collision
                return true; //There is a collision
            }
            //Include any other object collisions
            //Include other block collision checks if he clips into corners
        }
        return false; //There is no collision
    }

    /**
     * Detects if the character has collided with a block based on its size and position
     * and then returns a boolean to state whether or not it collided
     * 
     * @return True for collide, false for not
     */
    protected boolean headCollision() {
        //Distance the player can move without hitting a block
        int moveableDist =  base.getHeight()/2*-1 + jumpSpeed - 5; //-5 to improve visual look

        //Makes sure no blocks are above head
        if (getOneObjectAtOffset(0 , moveableDist, Block.class) == null && //Directly above
        getOneObjectAtOffset(base.getWidth()/2 - 4, moveableDist, Block.class) == null &&  //To the top-right
        getOneObjectAtOffset(base.getWidth()/2*-1 + 4, moveableDist + jumpSpeed, Block.class) == null){ //To the top-left
            return false;
        }
        return true;
    }

    /**
     * Checks if character is about to collide with edge of the world based off the character's move direction
     *
     * @param direction The direction to check. Positive for right, negative for left 
     * @return True for outside world, false for not
     */
    protected boolean edgeOfWorld(int direction){
        int offset; //Distance to detect for edge of block
        if (direction < 0 && this.getX() < 10){
            return true;
        }
        else if (direction > 0 && this.getX() > 1060){
            return true;
        }
        return false;
    }

    /**
     * Returns the direction the player is facing
     *
     * @return True for right, false for left
     */
    protected boolean getMoveDir()
    {
        return moveDir;
    }
}