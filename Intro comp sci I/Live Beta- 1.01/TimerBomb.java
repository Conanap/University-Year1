import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * A bomb that doesn't explode on collision but when the timer set on it runs out.
 * 
 * @author Roland 
 * @version 0.45
 */
public class TimerBomb extends Bomb
{
    GreenfootImage baseImg = new GreenfootImage("tnt.png");
    public TimerBomb () {
        maxTime = 120; //Time before bomb automatically explodes
        curTime = 0;
        grav = 2;
        baseDamage = 9;
        radius = 30;

        setImage(baseImg);
        explosion.setVolume(30);
    }

    public TimerBomb (int damage, int radius) {
        this();
        this.baseDamage = damage;
        this.radius = radius;
    }

    /**
     * Act - do whatever the TimerBomb wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        gravity();
        curTime++;
        if (curTime == maxTime){
            dealDamage();
        }
        if(curTime >= maxTime){
            explode();
        }
    }

    /**
     * Simple gravity method that slowly moves the bomb downwards, accelerating
     * if not touching the ground.
     *
     */
    public void gravity(){
        List<Actor> objects = getIntersectingObjects(Block.class);
        if (objects.size() == 0){
            grav++;
            setLocation(getX(), getY() + grav);
        }
    }
}