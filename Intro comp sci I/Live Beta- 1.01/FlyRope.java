import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FlyRope here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FlyRope extends Rope
{
    private boolean attached;
    private GreenfootSound attachSound = new GreenfootSound("RopeAttach.mp3");
    public FlyRope () {
        maxSpeed = 25;
        maxTime = 20;
        mobile = true;
        extraFallTime = 20; //Extra time the rope spends falling, not flying up

        maxLength = 250;

        curSpeed = maxSpeed;
        curTime = 0;
        flightDist = 0;
        
        attachSound.setVolume(30);
    }

    public FlyRope(int length){
        this();
        maxLength = length;
    }

    public FlyRope(int length, int ID){
        this(length);
        IDNum = ID;
    }

    /**
     * Act - do whatever the Rope wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(mobile){ 
            fly();
        } //Only flies while moble
        attachBlock(); //Checks for blocks or timer end
    }    

    public void fly(){
        curTime++;
        if (curTime <= maxTime){ //If the rope should still be flying
            curSpeed = curSpeed - maxSpeed/maxTime; //Increase speed upwards
            extend(curSpeed);
        }
        else{
            curSpeed--; //Start falling downwards
        }
        setLocation(getX(), getY() - curSpeed); //Change location
    }

    public void attachBlock(){
        if (mobile){ //Checks if statements only if mobile is true, reduces a bit of processing
            if (curTime >= maxTime + extraFallTime){ 
                //Once timer runs out, latches onto wall
                mobile = false;
                attachSound.play();
            }
            if (getOneObjectAtOffset(0 , this.getImage().getHeight()/-2, Block.class) != null){
                mobile = false;
                attachSound.play();
                if (!attached){
                    attached = true;
                    xCo = getX(); //Store the x and y locations of the place
                    yCo = getY(); //Where the rope attached to (if it's on a block)
                }
            }
        }
        if (attached){ //Cant be put in above if statement as height changes
            extend(8); //Make the rope extend the final length
            resetPosition(xCo, yCo);
        }
    }
}

