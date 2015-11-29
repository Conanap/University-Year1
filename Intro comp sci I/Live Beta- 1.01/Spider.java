import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Automatically climbs up walls- not meant for above ground. If they player is below the spider,
 * the spider falls down towards the player and tries to attach itself. If it does, the spider
 * does continous damage to the player until killed.
 * 
 * @author Roland Li 
 * @version 0.45
 */
public class Spider extends Characters
{
    GreenfootSound hiss = new GreenfootSound("SpiderHiss.mp3");

    private boolean aggro = false; //To attack player if in range
    public Spider(){
        //Stats
        maxHP = 24; //Low hp
        curHP = maxHP;

        //Movement
        //Does not follow typical vertical/horizontal movement conventions

        base = new GreenfootImage("Spider.png");
        base.scale(30,20);
        setImage(base);

        hiss.setVolume(30);
    }

    /**
     * Act - do whatever the Enemies wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {      
        chargePlayer();
        climbUp();
        dealDamage();
    }

    public void moveX(int num){
        //Does not move horizontally
    }

    /**
     * Moves vertically upwards if the player is not below.
     *
     */
    public void climbUp(){
        if(!aggro){
            if (!headCollision()){ //Keep climbing up until head collides
                setLocation(getX(), getY()-2); //Move up slowly
            }
        }
    }

    /**
     * Checks for player location and sees if it is directly below. If so, moves downwards rapidly.
     *
     */
    public void chargePlayer(){
        Actor player = (Actor) getWorld().getObjects(Player.class).get(0); //Find the player in the world

        int yOffset = player.getY() - this.getY(); //Get relative location
        int xOffset = player.getX() - this.getX();
        if (yOffset < 400 && yOffset > 0  && xOffset < 20 && xOffset > -20){ //If within range and directly below
            if (!aggro){
                hiss.play(); //Sound only plays once per aggro switch
                aggro = true; //Turns on boolean
            }
            setLocation(getX(), getY()+6); //Move down quickly
        }
        else {
            aggro = false; //Turns off boolean, spider will move back up in climbUp again
        }
    }

    /**
     * Deals damage to character if the intersect the spider. Sets location to be on top of the player
     * if they do.
     *
     */
    public void dealDamage(){
        Player player = (Player)getOneIntersectingObject(Player.class);
        if (player!=null){
            player.damage(5);
            hitDamage.play();
            //Attach to player
            setLocation(player.getX(), player.getY()); 
        }
    }  
}
