import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * An enemy that flies around the world in randomly set directions. Shoots GooShot at the
 * player if they are within range.
 * 
 * @author Roland Li
 * @version 0.46
 */
public class Dragonfly extends Characters
{
    GreenfootSound cry = new GreenfootSound("Screech.mp3");
    GreenfootSound buzz = new GreenfootSound("FlyBuzz.mp3");
    private int moveMethod; //from 0 to 3
    private int moveCounter; //to switch methods
    private int shootCounter; //to limit firerate

    private boolean aggro = false; //To player sound file when the pig initally finds the player in range
    public Dragonfly(){
        //Stats
        maxHP = 44; //Lower hp because hard to hit- pretty tough still
        curHP = maxHP;

        //Movement
        maxSpeed = 4;
        maxSlowDelay = 1; //Very fast acceleration
        maxMoveDelay = 1; //Slows down quickly

        curSpeed = 0; 
        curSlowDelay = 0; 
        curMoveDelay = 0;

        base = new GreenfootImage("Dragonfly.png");
        cry.setVolume(30);
        buzz.setVolume(10);
        setImage(base);
    }

    /**
     * Act - do whatever the Dragonfly wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        wander();
        attackPlayer();
        //Not inhibited by gravity due to flight
    }

    /**
     * Randomly move in one of 8 predefined directions. Includes basic left, right, up, down,
     * and diagonal options.
     *
     */
    public void wander(){
        moveCounter++; //Increase counter
        if (moveCounter >= 10){
            moveCounter = 0; //Reset counter
            moveMethod = Greenfoot.getRandomNumber(8);
            buzz.play();
        }
        //Based off the random number, move in one of 8 possible direction combinations
        if (moveMethod == 0 || moveMethod == 4 || moveMethod == 6){
            moveX(1); //Right
        }
        if (moveMethod == 1 || moveMethod == 5 || moveMethod == 7){
            moveX(-1); //Left
        }
        if (moveMethod == 2 || moveMethod == 4 || moveMethod == 7){
            moveY(-1); //Up
        }
        if (moveMethod == 3 || moveMethod == 5 || moveMethod == 6) {
            moveY(1); //Down
        }
    }

    /**
     * Dragon fly's version of moveX. 
     *
     * @param num Positive for right, negative for left
     */
    public void moveX(int num){
        //Move if there is no block directly ahead
        if (!moveCollision(num) && !edgeOfWorld(num)){ 
            //Move if there is space ahead
            curMoveDelay++;
            if(curMoveDelay >= maxMoveDelay){ //Slows acceleration rate for a smoother/slower increase
                accelerate(num);
                curMoveDelay = 0; //Reset counter
            }
            setLocation(getX()+ curSpeed, getY());
        }
        else { //Set enemy to facing the other direction
            GreenfootImage flipImage = getImage(); //Get image
            flipImage.mirrorHorizontally(); //Flip image
            setImage(flipImage); //Set image (sadly cannot do in less steps)
        }
    }

    /**
     * Moves in vertically if it doesn't collide with the ground or ceiling
     * Based off moveX()
     *
     * @param num Negatives move up, Positives move down
     */
    private void moveY(int num){
        //Move if there is no block directly ahead
        if (!headCollision() && !touchGround()){ 
            //Move if there is space ahead
            curMoveDelay++;
            if(curMoveDelay >= maxMoveDelay){ //Slows acceleration rate for a smoother/slower increase
                accelerate(num);
                curMoveDelay = 0; //Reset counter
            }
            setLocation(getX(), getY()+curSpeed);
        }
    }

    /**
     * Target's player location and checks if they are within range. If so, speed increases and it 
     * calls the spitGoo method
     *
     */
    private void attackPlayer(){
        
        List <Actor> players = getWorld().getObjects(Player.class);
        if (players.size() !=0){
        Actor player = players.get(0); //Find the player in the world
        //Sets which direction to look for
        int yOffset = player.getY() - this.getY();
        int xOffset = player.getX() - this.getX(); ;

        if (xOffset < 200 && xOffset > -200 && yOffset < 200 && yOffset >-200 ){ //If within range
            if (!aggro){
                cry.play();
                aggro = true;
            }
            maxSpeed = 6; //Go faster!
            spitGoo(player.getX(), player.getY());
        }
        else {
            aggro = false;
            maxSpeed = 4;
        }
    }
}	

    /**
     * Spawns and fires a GooShot object aimed at the inputted location
     *
     * @param x Coordinate
     * @param y Coordinate
     */
    private void spitGoo(int x, int y){
        shootCounter++;
        if (shootCounter >= 30){
            shootCounter = 0; //Reset counter
            World currentWorld = getWorld();//Finds current world
            ImpactBomb bomb = new GooShot(x, y-5, 7, 10);//Creates weak bomb, aimed slightly inputted location
            currentWorld.addObject(bomb, this.getX(), this.getY()); //Spawns bomb at current location
        }
    }
}
