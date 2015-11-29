import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Walks left to right until it hits a block, then walks back. Charges at players that are in front of them,
 * dealing damage.
 * 
 * @author Roland Li 
 * @version 0.45
 */
public class Charger extends Characters
{
    GreenfootSound grunt = new GreenfootSound("PigGrunt.mp3");
    private int chargeTurnCount; //Boar turns around after hitting the player to run him over again
    private boolean chargeTurn; //Whether or not the charger is about to turn

    private boolean aggro = false; //To player sound file when the pig initally finds the player in range
    public Charger(){
        //Stats
        maxHP = 104; //Pretty dang tough without a good sword!
        curHP = maxHP;

        //Movement
        maxSpeed = 2;
        maxSlowDelay = 8; //Causes increase in forward acceleration to happen once per 8 acts (hard to slow down)
        maxMoveDelay = 5;
        maxFallSpeed = 30;
        maxFallDelay = 1; //No delay, falls downward faster because heavy

        moveDir = true;

        curSpeed = 0;  //Initalizing stuff, mostly uneeded
        curSlowDelay = 0; 
        curMoveDelay = 0;
        curFallSpeed = 1;
        curFallDelay = 0;
        curJumpCount = 0;

        base = new GreenfootImage("Hound.png");
        grunt.setVolume(30);
        setImage(base);
    }

    /**
     * Act - do whatever the Enemies wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (moveDir){
            moveX(1); //Move left
        }
        else {
            moveX(-1); //Move right
        }
        chargePlayer(); //Check for player to charge at
        decelerate(); //Slow down from friction
        gravity(); //Fall down
    }

    /**
     * Charger's special way to move horizontally
     *
     * @param num Direction to move, + for right , - for left
     */
    public void moveX(int num){ //Inherited from abstract, cant be public
        //Move if there is no block directly aheah
        if (chargeTurnCount > 0){ //If the counter was started to turn after collision with player
            chargeTurnCount--; 
            if (chargeTurnCount == 0){ //Once counter reaches 0, turn
                chargeTurn = true;
            }
        }
        if (!moveCollision(num) && !edgeOfWorld(num) && chargeTurn == false){ 
            //Move if there is space ahead
            curMoveDelay++;
            if(curMoveDelay >= maxMoveDelay){ //Slows acceleration rate for a smoother/slower increase
                accelerate(num);
                curMoveDelay = 0; //Reset counter
            }
            setLocation(getX()+ curSpeed, getY());
        }
        else { //Set enemy to be moving in the other direction
            if (moveDir) {
                moveDir = false; //Switch direction boolean
            }
            else {moveDir = true;}
            GreenfootImage flipImage = getImage(); //Get image
            flipImage.mirrorHorizontally(); //Flip image
            setImage(flipImage); //Set image (sadly cannot do in less steps)
            chargeTurn = false; //Reset boolean if it was triggered, stops turning
        }
    }

    /**
     * Checks if player is in front of the charger based off current movement direction. If so, 
     * increase speed and charge towards them, calling dealDamage() to check for collisions. 
     *
     */
    private void chargePlayer(){
        Actor player = (Actor) getWorld().getObjects(Player.class).get(0); //Find the player in the world
        //Sets which direction to look for
        int yOffset = player.getY() - this.getY();
        int xOffset;
        if (moveDir){ //Moving right
            xOffset = player.getX() - this.getX();
        }
        else {
            xOffset = this.getX() - player.getX();
        }
        if (xOffset < 400 && xOffset > 0 && yOffset < 20 && yOffset >-20 ){ //If within range
            if (!aggro){
                grunt.play();
                aggro = true;
            }
            maxSpeed = 5; //Go faster!
            maxMoveDelay = 3; //Accelerate faster!
            maxSlowDelay = 20; //Harder to slow down!
            dealDamage(); //Hurt player!
        }
        else {
            aggro = false;
            maxSpeed = 2;
            maxMoveDelay = 5; //Accelerate slower
            maxSlowDelay = 4;
        }
    }

    /**
     * Deals damage to the player if they are intersecting. If they collide, the charger
     * will prepare to turn around.
     *
     */
    private void dealDamage(){
        Player player = (Player)getOneIntersectingObject(Player.class); //Check intersection
        if (player!=null){
            player.damage(25); //Deal damage
            hitDamage.play(); //Sound
            chargeTurnCount = 15; //Set counter so that charger turns in this amount of time
        }
    }
}
