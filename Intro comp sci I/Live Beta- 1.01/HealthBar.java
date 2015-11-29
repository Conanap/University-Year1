import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

/**
 * A custom hp bar created specifically for this project. Relies on drawing images of various sizes.
 * 
 * @author Roland Li
 * @version 0.21
 */
public class HealthBar extends Actor
{
    private int xCo, yCo; //Coordinates that the bottom of the image should stay on
    private int maxSize, curSize;
    private int baseSize; //The amount of the bar that will be
    //remaining even if curSize = 0
    private int baseWidth;//Width of bar   

    private int frameNum; //Goes from 1-6
    private int curLoop, maxLoop; //Used to reduce animation speed

    private GreenfootImage base = new GreenfootImage("candle (1).png");
    private GreenfootImage sizedImg; //To initalize images of new sizes to draw the base onto

    private Font font = new Font ("Century Gothic", Font.BOLD, 20); //Font displayed in

    public HealthBar(int max, int x, int y){
        maxSize = max;
        curSize = maxSize;
        baseSize = 100;
        baseWidth = 80;
        frameNum = 1;
        maxLoop = 4;

        xCo = x;
        yCo = y;

        setImage(base);
        update(0);
    }

    public void act(){
        update(0);
        //Put here instead of in update, so if it is called by the player,
        //the framerate stays constant
        frameNum++;
        curLoop++;
    }

    /**
     * Updates image size based off the change in size as stated by the inputted parameter amount.
     * Also updates the image, creating an animation affect.
     *
     * @param num The charge to the current size
     */
    public void update(int num){
        curSize += num;
        if (curSize > maxSize){
            curSize = maxSize;
        }
        else if (curSize < 0){
            curSize = 0;
        }

        sizedImg = new GreenfootImage (baseWidth, baseSize+curSize);

        double sizeRatio;
        if (curSize != 0){
            sizeRatio = maxSize/curSize;}
        else { sizeRatio = 0;}
        GreenfootImage frame; //The new frame to update to
        //Change image if the loop reaches 4
        if(curLoop == 4){
            frame = new GreenfootImage("candle (" + frameNum + ").png");
            sizedImg.drawImage(frame, 0,0);
            curLoop = 0; //Reset loop
        }
        else { 
            //To immediately change the size of the candle even if this act the candle's animation doesn't change
            sizedImg.drawImage(this.getImage(), 0, 0); 
        }

        this.setImage(sizedImg); //Sets image to the re-sized version
        //Draw the number display
        sizedImg.setFont(font);
        if (curSize > maxSize/4){
            sizedImg.setColor(Color.WHITE);
        } 
        else{
            sizedImg.setColor(Color.RED);
        }
        sizedImg.drawString(""+curSize, 5, sizedImg.getHeight() - 10); //Draw the number value on the bottom
        resetLocation();
        if (frameNum >= 6){ //Reset frame number for next loop
            frameNum = 1;
        }
    }

    /**
     * Resets location based on spawn location. Needed because of change in the hp bar size.
     *
     */
    public void resetLocation(){
        int y = yCo -(curSize + baseSize)/2; //Finds the point where the base should lie
        setLocation(xCo, y);
    }
}