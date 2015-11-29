import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Used by DragonFly. A much weaker version of impact bomb.
 * 
 * @author Roland Li
 * @version 0.46
 */
public class GooShot extends ImpactBomb
{
    GreenfootImage baseImg = new GreenfootImage("Ooze.png");
    GreenfootSound splat = new GreenfootSound("Splat.mp3");
    public GooShot () {
        maxSpeed = 8;
        maxTime = 150; //Time before bomb automatically explodes
        baseDamage = 5;
        radius = 30;

        curSpeed = maxSpeed;
        curTime = 0;
        grav = 1;
        setImage(baseImg);
    }

    public GooShot (int x, int y) {
        this();
        xCo = x;
        yCo = y;
    }

    public GooShot (int x, int y, int damage, int radius) {
        this(x,y);
        this.baseDamage = damage;
        this.radius = radius;
    }

    public void addedToWorld(World currentWorld){
        turnTowards (xCo, yCo); //Aims shot towards coordinates set during 
        //construction
    }

    /**
     * Act - do whatever the GooShot wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!exploding){fly();}
        else{ explode();}
    }

    private void fly(){
        move(curSpeed);
        gravity();
        checkCollision();
        curTime++;
    }

    /**
     * Explodes if touching certain objects, except for certain exceptions.
     * Exceptions are slightly differnt that the standard checkCollision.
     *
     */
    private void checkCollision(){
        if (curTime >  3){ //Prevents shot from being immediately destroyed
            List<Actor> objects = getIntersectingObjects(Actor.class);
            if (objects.size() != 0){
                boolean blowUp = true;  
                //To check if the objects in the list are on the "safe list"
                for (Actor entities: objects){
                    if (entities instanceof Rope){ blowUp = false; }
                    if (entities instanceof Torch){ blowUp = false; }
                    if (entities instanceof Dragonfly){ blowUp= false;}
                }
                if (blowUp){ 
                    dealDamage();
                    exploding = true;
                    explode();
                }
            }
        }
    }

    /**
     * Deals damage to only players and blocks
     * Less range and damage than regular bomb dealDamage()
     */
    protected void dealDamage(){
        for (int i = 0; i < 3; i++){
            int range = radius + i*10;
            List<Block> blocks = getObjectsInRange(range,Block.class);
            if (blocks.size() != 0)
            {
                for (Block blo: blocks)
                {
                    blo.damage(baseDamage-i*2); //Deal damage to the blocks
                }
            }
            int damage = baseDamage*2;
            Player player = (Player) getOneIntersectingObject(Player.class);
            if (player != null)
            {
                player.damage(damage); 
            }  
        } 
    }

    /**
     * Custom explode without animation.
     *
     */
    protected void explode(){
        splat.setVolume(30);
        splat.play();
        getWorld().removeObject(this);
    }
}
