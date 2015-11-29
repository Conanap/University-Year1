import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * The tool superclass.
 * 
 * @author (Tyler Zhang) 
 * @version (Jan 2015)
 */
public abstract class Tools extends Actor
{
    protected String name;
    protected int dmg;
    protected int range;
    protected int speed;
    protected int holdCounter;
    protected boolean mouseDown;
    protected boolean canDig;
    protected boolean isWeapon;
    protected int swingCount, swingX;

    protected GreenfootImage img;
    protected Player target;

    private Block previousBlock;
    protected GreenfootSound drillSound = new GreenfootSound("DrillSound.mp3");
    protected GreenfootSound pickSound = new GreenfootSound("PickSound.mp3");
    protected GreenfootSound fistSound = new GreenfootSound("FistSound.mp3");
    protected GreenfootSound swordSSound = new GreenfootSound ("SwordSwoosh.wav");
    protected GreenfootSound swordHSound = new GreenfootSound ("SwordHit.wav");

    /**
     * Follow the cursor.
     */
    protected void followMouse()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (target != null){
            if(target.getMoveDir()){
                setLocation(target.getX() -2, target.getY()+5);
            }else{
                setLocation(target.getX() +2, target.getY()+5);
            }
            if(getRotation() <= 90 || getRotation() >= 270){
                target.setMoveDir(true);
            }else{
                target.setMoveDir(false);
            }
        }else{
            destroy();
        }

        if (mouse != null) 
        {  
            if(isWeapon)
            {
                turnTowards(mouse.getX(), mouse.getY());
                if(Greenfoot.mousePressed(null) || swingCount > 0){
                    swing();
                }
            }else
            {
                swingCount = 0; //To prevent possible visual bug if player switches from weapon mid-swing
                if (previousBlock != null){ //Prevent nullpointer
                    previousBlock.unhighlight(); //Update the previously highlighted block
                }
                turnTowards(mouse.getX(), mouse.getY());
                List <Block> blocks = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), Block.class);
                if(blocks.size() != 0)
                {
                    previousBlock = blocks.get(0); //Store the current block the mouse is on
                    if(getDistance(previousBlock) < range)
                    {
                        previousBlock.highlight(true);
                    }else{
                        previousBlock.highlight(false);
                    }

                    if(canDig){ //Only allows block destruction if this boolean is true; changed by toolbar through mutator
                        if(mouseDown && getDistance(previousBlock) < range){
                            if(holdCounter > speed){
                                previousBlock.damage(dmg);
                                animate();
                                if (target.getToolBar().getSelected() != null) target.getToolBar().getSelected().decreaseDuration(1);
                                holdCounter = 0;
                            }else{
                                holdCounter++;
                            }
                            if (name.endsWith("drill")){
                                drillSound.play();
                            }
                            else if (name.endsWith("pick")){
                                pickSound.play();
                            }
                            else if (name.endsWith("hands")){
                                fistSound.play();
                            }
                        }else if (Greenfoot.mouseClicked(previousBlock) && getDistance(previousBlock) < range){
                            previousBlock.damage(dmg);
                            animate();
                            if (target.getToolBar().getSelected() != null) target.getToolBar().getSelected().decreaseDuration(1);
                        }
                    }
                    if(Greenfoot.mousePressed(previousBlock)) {
                        mouseDown = true;
                    }
                    else if(Greenfoot.mouseClicked(null) || Greenfoot.mouseMoved(null)) {
                        mouseDown = false;
                        holdCounter = 0;
                    }
                }
            }
        }
    }  

    protected void animate()
    {
        if(this.getRotation() <= 90 || this.getRotation() >= 270){
            turn(10);
        }else{
            turn(-10);
        }
    }

    protected void swing(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (swingCount == 0){
            swingX = mouse.getX(); //Store the location at the start of swing. This fixes visual bugs where player moves mouse mid-swing
        }
        swingCount++;
        turnTowards(mouse.getX(), swingX + swingCount*16);
        if(swingCount >= 7){
            swingCount = 0; //Reset counter and position
        }
        swordSSound.play();
        //Detect enemies to deal damage
        List <Characters> enemies = getIntersectingObjects(Characters.class);
        if (enemies !=null){
            for (Characters mob: enemies){
                if (!(mob instanceof Player)){ //To avoid dealing damage to player since he is a character
                    mob.damage(dmg);
                    if (target.getToolBar().getSelected() != null) target.getToolBar().getSelected().decreaseDuration(1);
                    swordHSound.play(); //Hit sound
                }
            }
            if (target.getToolBar().getSelected() != null) target.getToolBar().getSelected().decreaseDuration(1);
        }
    }

    public void changeDig (boolean diggable){
        canDig = diggable;
    }

    /**
     * Destroy the tool.
     */
    public void destroy()
    {
        getWorld().removeObject(this);
    }

    /**
     * Return the distance from this to another object.
     * 
     * @return double the distance of between two actors.
     */
    protected double getDistance(Actor actor) {
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());
    }
}
