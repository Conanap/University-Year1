import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Explosives.
 * 
 * @author Roland Li
 * @version (a version number or a date)
 */
public abstract class Bomb extends Consumables
{
    protected boolean exploding; //For animations
    protected int frameNum = 0;
    protected int grav;
    protected int baseDamage;
    protected int radius;

    protected GreenfootSound explosion = new GreenfootSound("Explosion.mp3");

    /**
     * Deals a set amount of damage to blocks and characters within a set radius
     *
     */
    protected void dealDamage(){
        //Makes it so that blocks/characters closest to the center of the explosion takes the most damage
        //If you are in the epicenter of the explosion, you take all "waves" of damage;
        //ex. If you throw a bomb at your feet, you take 20+15+10+5
        for (int i = 0; i < 4; i++){
            int range = radius + i*30;
            List<Block> blocks = getObjectsInRange(range,Block.class);
            if (blocks.size() != 0)
            {
                for (Block blo: blocks)
                {
                    blo.damage(baseDamage*2 - i*5); //Deal damage to the blocks
                }
            }
            int damage = baseDamage*4;
            List<Characters> mobs = getObjectsInRange(range,Characters.class);
            if (mobs.size() != 0)
            {
                for (Characters mob: mobs){
                    mob.damage(damage-5*i); 
                }
            }  
        } 
    }

    /**
     * Quickly changes it's frames to make it appear as if the explosion
     * is animated.
     * Removes itself once the animation is over.
     *
     */
    protected void explode() {
        if (frameNum == 0){
            explosion.play();
        }
        if (frameNum >= 16){
            getWorld().removeObject(this);
        }
        else{
            frameNum++;
            GreenfootImage frame = new GreenfootImage("explosion (" + frameNum + ").png");
            frame.scale(110,110);
            this.setImage(frame);
        }
    }
    
    abstract void gravity();
}
