import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.ArrayList;

/**
 * Create a hovering description
 * 
 * @author Albion Fung
 * @version 0.0.1
 */
public class HoverText extends Actor
{
    private final int coal_ingot = 9, iron_ingot = 10, silver_ingot =11, gold_ingot =12, diamond_ingot=13,emerald_ingot=14, lapis_ingot=15,
    torch=0,bomb=1,rope=2,iron_sword=28,silver_sword=29,gold_sword=30,diamond_sword=31,iron_pick=20,silver_pick=22,gold_pick=24,
    diamond_pick=26,iron_drill=21,silver_drill=23,gold_drill=25,diamond_drill=27;
    GreenfootImage base;
    private ArrayList<String> description = new ArrayList<String>();
    /**
     * Creates new hover text specific to the item.
     * @param itemID The ID of the item. (type)
     */
    public HoverText(int itemID)
    {
        switch(itemID)
        {
            case coal_ingot:
            description.add("Coal: About as cheap as you can sell it");
            description.add("for. No one really wants it, but the");
            description.add("shop has to buy it from you.");
            break;

            case iron_ingot:
            description.add("You're literally selling Earth's core.");
            description.add("I mean, it's iron too.");
            break;

            case silver_ingot:
            description.add("In about 14 other languages, you're");
            description.add("pretty much selling money for");
            description.add("money. Another reason to use English.");
            description.add("(This is silver.)");
            break;

            case gold_ingot:
            description.add("Aren't you the happy one there...");
            description.add("but I have to tell you... this is pyrite.");
            description.add("JOKES it's gold.");
            break;

            case diamond_ingot:
            description.add("If you compress the coal good enough,");
            description.add("it becomes diamond. Everyone wants it");
            description.add("now. Especially De Beers.");
            break;

            case emerald_ingot:
            description.add("Emerald: A highly sought gem after");
            description.add("pocket gremlins became famous");
            description.add("for it at Nindenda.");
            break;

            case lapis_ingot:
            description.add("Lapis wasn't so useful in Minecraft (well it");
            description.add("enchants items). But you get money here!");
            break;

            case torch:
            description.add("Remember that coal you sold us?");
            description.add("Yeah. It's in this torch. Guess");
            description.add("who's making profit now?");
            break;

            case bomb:
            description.add("Same bomb bomberman used to used");
            description.add("before his killed him.");
            break;

            case rope:
            description.add("If you ever want to get back up to the");
            description.add("surface, get these. The alternate is to");
            description.add("die and rot down there.");
            break;

            case iron_sword:
            description.add("We hate spiders as much as you do.");
            description.add("Especially big ones. So get an iron");
            description.add("sword and clean 'em up.");
            break;

            case silver_sword:
            description.add("The silver sword is softer, but creatures");
            description.add("in this world dont like silver.");
            break;

            case gold_sword:
            description.add("Gold is even softer... don't ask how who");
            description.add("decided to make a sword with gold.");
            description.add("Must be magic.");
            break;

            case diamond_sword:
            description.add("De Beers in action! Do you like the high");
            description.add("price on the diamond swords? We do. So");
            description.add("does De Beer.");
            break;

            case iron_pick:
            description.add("Well, hopefully this iron pickaxe will stop");
            description.add("your hands from bleeding.");
            break;

            case silver_pick:
            description.add("If you ever want gold, you'll want the silver");
            description.add("pickaxe- and who wouldn't want gold?");
            break;

            case gold_pick:
            description.add("Some how the softest metal will get you");
            description.add("the hardest rock. Here's a gold pickaxe.");
            break;

            case diamond_pick:
            description.add("GOD SPEED MINING GOOOOOOOOO!");
            description.add("(Hint: Diamond pickaxe)");
            break;

            case iron_drill:
            description.add("Tired of mining block by block? Time for");
            description.add("some iron drill!");
            break;

            case silver_drill:
            description.add("Nobody panic, this is just a drill. A silver drill.");
            description.add("(Punny I know)");
            break;

            case gold_drill:
            description.add("If Big Daddies used these gold drill, Jack ");
            description.add("woud've had a much easier job fighting.");
            break;

            case diamond_drill:
            description.add("Battlecast Skarner was supposed to have");
            description.add("a diamond tail, but we stole it.");
            description.add("Warning: May pierce the heavens.");
            break;

            default:
            description.add("We don't know what you're pointing at.");
            break;
        }

        base= new GreenfootImage(238,description.size()*15);//create a backgtround suitably sized for the text.
        setImage(base);
        base.fillRect(0,0,238,base.getHeight());
        base.setTransparency(187);
        base.setColor(Color.WHITE);
        int y = 13;
        for(String desc : description)//draw the description into the box
        {
            base.drawString(desc, 8,y);
            y+=13;
        }
    }    
    
    /*
     * Called to retrieve the height of the hover text box.
     */
    public int getHeight()
    {
        return base.getHeight();
    }
}
