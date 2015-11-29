import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
// import java.awt.Color;
/**
 * Write a description of class PlayerArm here.
 * 
 * @author Tyler Zhang
 * @version Jan 2015
 */
public class PlayerArm extends Tools
{
    //     Color color = new Color(0,0,0);
    GreenfootImage currentImage;
    int imageX;
    int imageY;
    int flippedImageX;
    int flippedImageY;
    public PlayerArm(Player tar)
    {
        currentImage = new GreenfootImage("Initial Arm.png");
        name = "bare hands";
        dmg = 2;
        isWeapon = false;
        range = 100;
        speed = 5;
        holdCounter = 0;
        target = tar;
        img = new GreenfootImage(48, 28);
        imageX = 18;
        imageY = 0;
        flippedImageX = 18;
        flippedImageY = 0;
        img.drawImage(currentImage, 18, 0);
        setImage(img);
        drillSound.setVolume(40);
        pickSound.setVolume(40);
        fistSound.setVolume(40);
        swordSSound.setVolume(50);
        swordHSound.setVolume(70);
    }

    public void act() 
    {
        GreenfootImage tmpt = currentImage;
        img.clear();
        //         img.setColor(color);
        //         img.fill();
        if(!target.getMoveDir()){
            tmpt.mirrorVertically();
            img.drawImage(tmpt, flippedImageX, flippedImageY);
        } else { 
            img.drawImage(tmpt, imageX, imageY);
        }
        setImage(img);
        followMouse();
    }

    public void update(int tp)
    {
        switch(tp){
            case 0:
            name = "bare hands";
            isWeapon = false;
            dmg = 2;
            currentImage = new GreenfootImage("Initial Arm.png");
            imageX = 18;
            imageY = 13;
            flippedImageX = 18;
            flippedImageY = 9;
            break;

            case 1:
            name = "iron pick";
            isWeapon = false;
            dmg = 3;
            currentImage = new GreenfootImage("Arm + Pickaxe Iron.png");
            imageX = 18;
            imageY = 2;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 2:
            name = "iron drill";
            isWeapon = false;
            dmg = 5;
            currentImage = new GreenfootImage("Arm + Drill Iron.png");
            imageX = 18;
            imageY = 10;
            flippedImageX = 18;
            flippedImageY = 4;
            break;

            case 3:
            name = "silver pick";
            isWeapon = false;
            dmg = 6;
            currentImage = new GreenfootImage("Arm + Pickaxe Silver.png");
            imageX = 18;
            imageY = 2;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 4:
            name = "silver drill";
            isWeapon = false;
            dmg = 8;
            currentImage = new GreenfootImage("Arm + Drill Silver.png");
            imageX = 18;
            imageY = 10;
            flippedImageX = 18;
            flippedImageY = 4;
            break;

            case 5:
            name = "gold pick";
            isWeapon = false;
            dmg = 10;
            currentImage = new GreenfootImage("Arm + Pickaxe Gold.png");
            imageX = 18;
            imageY = 2;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 6:
            name = "gold drill";
            isWeapon = false;
            dmg = 15;
            currentImage = new GreenfootImage("Arm + Drill Gold.png");
            imageX = 18;
            imageY = 10;
            flippedImageX = 18;
            flippedImageY = 4;
            break;

            case 7:
            name = "diamond pick";
            isWeapon = false;
            dmg = 20;
            currentImage = new GreenfootImage("Arm + Pickaxe Diamond.png");
            imageX = 18;
            imageY = 2;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 8:
            name = "diamond drill";
            isWeapon = false;
            dmg = 30;        
            currentImage = new GreenfootImage("Arm + Drill Diamond.png");
            imageX = 18;
            imageY = 8;
            flippedImageX = 18;
            flippedImageY = 4;
            break;

            case 9:
            name = "torch";
            isWeapon = false;
            dmg = 0;        
            currentImage = new GreenfootImage("Arm + Torch.png");
            imageX = 18;
            imageY = -4;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 10:
            name = "bomb";
            isWeapon = false;
            dmg = 0;        
            currentImage = new GreenfootImage("Arm + Bomb.png");
            imageX = 18;
            imageY = 4;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 11:
            name = "rope";
            isWeapon = false;
            dmg = 0;        
            currentImage = new GreenfootImage("Arm + Rope.png");
            imageX = 18;
            imageY = 6;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 12:
            name = "Iron Sword";
            isWeapon = true;
            dmg = 1;        
            currentImage = new GreenfootImage("Arm + Sword Iron.png");
            imageX = 19;
            imageY = -4;
            flippedImageX = 19;
            flippedImageY = 7;
            break;

            case 13:
            name = "Silver Sword";
            isWeapon = true;
            dmg = 2;        
            currentImage = new GreenfootImage("Arm + Sword Silver.png");
            imageX = 19;
            imageY = -4;
            flippedImageX = 19;
            flippedImageY = 7;
            break;

            case 14:
            name = "Gold Sword";
            isWeapon = true;
            dmg = 3;        
            currentImage = new GreenfootImage("Arm + Sword Gold.png");
            imageX = 19;
            imageY = -4;
            flippedImageX = 19;
            flippedImageY = 7;
            break;

            case 15:
            name = "Diamond Sword";
            isWeapon = true;
            dmg = 5;        
            currentImage = new GreenfootImage("Arm + Sword Diamond.png");
            imageX = 19;
            imageY = -4;
            flippedImageX = 19;
            flippedImageY = 7;
            break;
            
                        case 16:
            name = "hp potion";
            isWeapon = false;
            dmg = 0;        
            currentImage = new GreenfootImage("Arm + Heal Potion.png");
            imageX = 18;
            imageY = 6;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 17:
            name = "speed potion";
            isWeapon = false;
            dmg = 0;        
            currentImage = new GreenfootImage("Arm + Speed Potion.png");
            imageX = 18;
            imageY = 6;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            case 18:
            name = "jump potion";
            isWeapon = false;
            dmg = 0;        
            currentImage = new GreenfootImage("Arm + Jump Potion.png");
            imageX = 18;
            imageY = 6;
            flippedImageX = 18;
            flippedImageY = 6;
            break;

            default:
            break;
        }
    }
}