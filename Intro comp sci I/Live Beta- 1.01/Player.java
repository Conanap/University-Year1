import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class player here.
 * 
 * @author Movement/Base: Roland Li
 * @author PlayerArm/Tools: Tyler Zhang, Marco Ly
 * @author Inventory System, pickup interaction: Marco Ly
 * @author Animations: Jasper Tu
 * @version Jan 2015
 */
public class Player extends Characters
{
    private PlayerArm pArm;

    private HealthBar hpbar = new HealthBar(100, 1245, 720);
    private ToolBar toolbar = new ToolBar();

    private int moveBuff, moveBuffCount;
    private int jumpBuff, jumpBuffCount;

    private int curInvinc, maxInvinc; //Invinsibility "frames" after taking damage
    private GreenfootSound grunt = new GreenfootSound ("PlayerHurt.mp3");
    private GreenfootSound step = new GreenfootSound ("Step.mp3");
    private GreenfootSound pickup = new GreenfootSound ("PickUpPop.mp3");

    // Needed for the Walking Animation ...
    private int frame = 1;
    private int delay = 10;
    private GreenfootImage run2r = new GreenfootImage("Right 2.png");
    private GreenfootImage run3r = new GreenfootImage("Right 3.png");
    private GreenfootImage run4r = new GreenfootImage("Right 4.png");

    //Inventory
    private int coins; // amoount of coins player owns
    PlyrInv playerInv; // the inventory of the player
    private int[] holdingArray; // a queue of item IDs to be transferred into player's inventory whenever it is convenient
    public Player(){
        //Stats
        maxHP = 100;
        curHP = maxHP;
        onRope = false;
        coins = 0; // starting amount of coins for the player
        maxInvinc = 20; //Player can't take damage for this long after taking damage

        //Movement
        maxSpeed = 3;
        maxSlowDelay = 4; //Causes increase in forward acceleration to happen once per 4 acts
        maxMoveDelay = 4;
        maxFallSpeed = 30;
        maxFallDelay = 2; //Causes increase of downwards acceleration to happen only once per 2 acts
        maxJumpCount = 16;
        jumpSpeed = 9;
        moveDir = true;

        curSpeed = 0; 
        curSlowDelay = 0; 
        curMoveDelay = 0;
        curFallSpeed = 1;
        curFallDelay = 0;
        curJumpCount = 0;

        //Inventory
        playerInv = new PlyrInv();
        pArm = new PlayerArm(this);

        holdingArray = new int[60];
        for (int i = 0; i < 60; i++) {         
            holdingArray[i] = -1;
        }

        //Misc
        grunt.setVolume(40);
        step.setVolume(40);
        pickup.setVolume(30);
        base = new GreenfootImage("Right 1.png");
        setImage(base);
    }

    public void addedToWorld(World thisWorld){
        thisWorld.addObject(hpbar, 1232, 720);
        thisWorld.addObject(pArm, getX(), getY());
        getWorld().addObject(toolbar, 1180, 360);
        changeAction(1);
    }

    /**
     * Act - do whatever the player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {        
        action();
        curInvinc--; 
        resetOnRope(); //Turns on Rope off if the player is on the rope

        decelerate();
        gravity();
        detectPickup();
    }

    public void setID(int newID)
    {
        toolbar.setID(newID);
    }

    /**
     * Attempts to use an item
     */
    public void action(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!clickingInventory()) {
            if (mouse != null){
                if (mouse.getButton() == 1){
                    toolbar.action(getX(), getY());
                }
                if (mouse.getButton() == 3){
                    toolbar.altAction(getX(), getY());
                }
            }
        }
    }

    /**
     * Tells the toolbar to change actions (select a different inventory item in toolbar)
     * 
     * @param action    the action number
     */
    public void changeAction(int action){
        toolbar.changeAction(action);
    }

    public void damage(int num){
        if (curInvinc < 0){
            super.damage(num);
            hpbar.update(-num); //Decreases hp bar amount
            curInvinc = maxInvinc; //Resset counter
            grunt.play();
        }
    }

    public void heal(int num){
        super.heal(num);
        hpbar.update(num); //Increases hp bar amount
    }

    public void movePotion(int buff, int duration){
        moveBuff = buff;
        maxSpeed = maxSpeed + moveBuff;
        moveBuffCount = duration;
    }

    public void jumpPotion(int buff, int duration){
        jumpBuff = buff;
        jumpBuffCount = duration;
    }

    private void buffDurations(){
        if (jumpBuffCount > 0){
            jumpBuffCount--;
        }
        else{ jumpBuff = 0;}
        if (moveBuffCount > 0){
            moveBuffCount--;
        }
        else{ 
            maxSpeed -= moveBuff;
            moveBuff = 0;}
    }

    /**
     * Positive for move right, negative for left
     *
     * @param num A parameter
     */
    public void moveX(int num){
        if (num > 0){
            moveDir = true;
        }else if (num <0){
            moveDir = false;
        }

        //Move if there is no block directly ahead
        //Horizontal movement also prevented if player is on a rope
        if (!moveCollision(num) && onRope == false){ 
            //Move if there is space ahead
            curMoveDelay++;
            if(curMoveDelay == maxMoveDelay){ //Slows acceleration rate for a smoother/slower increase
                accelerate(num);

                curMoveDelay = 0; //Reset counter
            }
            setLocation(getX()+curSpeed + moveBuff,getY());
            if (touchGround()){
                step.play();
            }
        }
    }

    public void jump(int num){
        //If the player is on the ground or currently in a jump, OR on a rope, jump
        if(touchGround() || curJumpCount > 0 || onRope == true){ 
            if(num > curJumpCount && num <= maxJumpCount){ //Checks if the jump button has been held, and if it is below the max
                curJumpCount = num; //Jump count stores the entered number to check if the next jump command
                //is from the same previous jump

                //Moves upward if a block is not directly above
                if (!headCollision()){//If the head is not about to collide into a block, then move
                    setLocation(getX(), getY() - jumpSpeed - jumpBuff);
                }       
                else { resetJump();} //Will no longer move upwards, even if the button continues to be held.
            }
            onRope = false; //Resets to false since a jump occured
        }
    }

    public void resetJump(){
        curJumpCount = 0;
    }

    public void die(){
        //Game over screen?
        pArm.destroy();
        getWorld().removeObject(this);
    }

    /**
     * Method climbRope
     *
     * @param num Amount to move. Positive for up, negative for down.
     */
    public void climbRope(int num){
        Actor rope = getOneIntersectingObject(Rope.class);
        if (rope != null){
            curSpeed = 0; //Prevents player from flying off rope from momentum
            onRope = true;
            if ( num > 0 && (rope.getY() - rope.getImage().getHeight()/2) - getY() < -30){ //Only allows up movement if the player is under the top of the rope
                setLocation(getX(), getY() - num);
            }
            //If the player moves down the rope, off the length of the rope, the player
            //is considered off the rope
            else if (num < 0){
                setLocation(getX(), getY() - num);
                if (getY() - (rope.getY() + rope.getImage().getHeight()/2) > 0){
                    onRope = false;                
                }
            }
        }
    }

    /**
     * Resets onRope to false if the player is touching the round
     * Helps account for when the player moves down the rope to the ground,
     * and also fixes a certain bug
     *
     */
    public void resetOnRope(){
        if (touchGround()){
            onRope = false;
        }
    }

    /**
     * Toggles the inventory of the player to be visible or invisible
     */
    public void toggleInventory(){
        if (!playerInv.isShowing()) {
            getWorld().addObject(playerInv, 379, 259);
            playerInv.setShowing(true);
            playerInv.updateCoinDisplay(coins);
            transferItems();
        } else if (playerInv.isShowing()) {
            List<HoverText> hoves = getWorld().getObjects(HoverText.class);
            for(HoverText hovers : hoves)
                getWorld().removeObject(hovers);
            playerInv.setShowing(false);
            playerInv.removeAssets();
            getWorld().removeObject(playerInv);
        }
    }

    /**
     * Transfers itemIDs held in the holdingArray queue into the player inventory by adding new items of that itemID
     */
    private void transferItems() {
        for (int i = 0; i < 60; i++) {
            if (holdingArray[i] != -1) {playerInv.addItem(holdingArray[i]); holdingArray[i] = -1;}
        }
    }

    /**
     * Changes the number of coins by adding a certain value
     * @param change    the change in the total number of coins. can be positive or negative.
     */
    public boolean addCoins(int change){
        if (coins+change >= 0) {coins+=change; playerInv.updateCoinDisplay(coins); return true;}
        return false;
    }

    //For the world to maintain the player's speeds when transitioning screens
    public int getFallSpeed(){
        return curFallSpeed;
    }

    public void setFallSpeed(int num){
        if (num > 0){
            curFallSpeed = num;
        }
    }

    public int getMoveSpeed(){
        return curSpeed;
    }

    public void curMoveSpeed(int num){
        if (num > 0){
            curSpeed = num;
        }
    }

    /**
     * Returns the inventory of this player
     */
    public PlyrInv getInventory() {
        return playerInv;
    }

    /**
     * Returns the toolbar of this player
     */
    public ToolBar getToolBar() {
        return toolbar;
    }

    /**
     * Returns the player arm of this player
     */
    public PlayerArm getPlayerArm() {
        return pArm;
    }

    /**
     * Checks to see if any buildings are in range of this player. range is set to 250
     */
    public boolean shopInRange() {
        if (getObjectsInRange(250, Building.class).size() == 0) return false;
        return true;
    }

    /**
     * Checks if the mouse is clicking on an inventory class or any of their slots or buttons (this is to prevent the usage of items whilst interacting with the inventory systems)
     * 
     * @return boolean  if mouse is clicking an inventory
     */
    private boolean clickingInventory() 
    {
        List<Inventory> invList=getWorld().getObjects(Inventory.class);
        List<Slots> slotList=getWorld().getObjects(Slots.class);
        List<Buttons> buttonList=getWorld().getObjects(Buttons.class);
        List<Building> buildingList=getWorld().getObjects(Building.class);
        if(invList.size() != 0)
        {
            for(Inventory inven : invList)
            {
                if(Greenfoot.getMouseInfo() != null && inven != null && Greenfoot.getMouseInfo().getActor() == (Inventory)inven)
                    return true;
            }
        }
        if(slotList.size() != 0)
        {
            for(Slots slots : slotList)
            {
                if(Greenfoot.getMouseInfo() != null && slots != null && Greenfoot.getMouseInfo().getActor() == (Slots)slots)
                    return true;
            }
        }
        if(buttonList.size() != 0)
        {
            for(Buttons buttons : buttonList)
            {
                if(Greenfoot.getMouseInfo() != null && buttons != null && Greenfoot.getMouseInfo().getActor() == (Buttons)buttons)
                    return true;
            }
        }
        if(buildingList.size() != 0)
        {
            for(Building building : buildingList)
            {
                if(Greenfoot.getMouseInfo() != null && building != null && Greenfoot.getMouseInfo().getActor() == (Building)building)
                    return true;
            }
        }
        return false;
    } 

    /**
     * Detects a collision with a drop class and changes values to allow for the corresponding itemID value of the drop to be stored into the holdingArray which will later be transfered into playerinventory 
     * if it is not currently open
     */
    private void detectPickup() {
        if (getOneIntersectingObject(Drops.class) != null) {
            int type = 0;
            Drops drop = (Drops)(getOneIntersectingObject(Drops.class));
            switch(drop.getType()){                
                case 3:
                type = 10;
                break;

                case 4:
                type = 11;
                break;

                case 5:
                type = 12;
                break;

                case 6:
                type = 13;
                break;

                case 7:
                type = 14;
                break;

                case 8:
                type = 15;
                break;

                case 9:
                type = 9;
                break;

                default:
                break;
            }
            if (playerInv.isShowing()) playerInv.addItem(type);
            else 
            {
                for (int i = 0; i < 60; i++) {
                    if (holdingArray[i] == -1) {holdingArray[i] = type;  break;}
                }  
            }
            getWorld().removeObject(drop);
            pickup.play();
        }
    }

    //Animations

    public void updateImg()
    {
        if(moveDir){
            base = new GreenfootImage("Right 1.png");
            this.setImage(base);   
            animatePlayer(moveDir);
        }else{
            base = new GreenfootImage("Right 1.png");
            base.mirrorHorizontally();
            this.setImage(base);
            animatePlayer(moveDir);
        }
    }

    private void animatePlayer(boolean moveDir)
    {
        if(moveDir == true  && Greenfoot.isKeyDown("d"))
        {
            if (frame < 1 * delay) {
                setImage (base);
            }
            else  if (frame < 2 * delay) {
                setImage (run2r);
            }
            else  if (frame < 3 * delay) {
                setImage (run3r);
            }
            else  if (frame < 4 * delay) {
                setImage (run4r);
                frame = 1;
                return;
            }
            frame++;
        }

        if(moveDir == false && Greenfoot.isKeyDown("a"))
        {
            if (frame < 1 * delay) {
                setImage(base);
            }
            else  if (frame < 2 * delay) {
                GreenfootImage run2l = new GreenfootImage(run2r);
                run2l.mirrorHorizontally();
                setImage (run2l);
            }
            else  if (frame < 3 * delay) {
                GreenfootImage run3l = new GreenfootImage(run3r);
                run3l.mirrorHorizontally();
                setImage (run3l);
            }
            else  if (frame < 4 * delay) {
                GreenfootImage run4l = new GreenfootImage(run4r);
                run4l.mirrorHorizontally();
                setImage (run4l);
                frame = 1;
                return;
            }
            frame++;
        }
    }

    public void setMoveDir(boolean newDir)
    {
        moveDir = newDir;
        updateImg();
    }

    /**
     * Demolishes everything that the player holds near and dear to their heart. This is the for when the player hits SOS without the coinage to save their soul. This is why you buy ropes
     */
    public void destroyEverything() {
        coins = 0;
        playerInv.updateCoinDisplay(coins);
        playerInv.deleteAllItems();
    }
}