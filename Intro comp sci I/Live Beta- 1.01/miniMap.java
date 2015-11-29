import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.lang.IllegalStateException;
/**
 * MiniMap.
 * 
 * @author Albion Fung 
 * @version 0.01
 */
public class miniMap extends Actor
{
    private GreenfootImage mini = new GreenfootImage(151,100);
    private int xSpawn, ySpawn,xMax,yMax, playerX, playerY,drawPx, drawPy;
    private int[][] map;
    private boolean mapLoaded=false;
    /**
     * Construct a new minimap
     */
    public miniMap() 
    {
        base();//draw base
    }

    /*
     * Called to build the rest of the minimap
     */
    public void buildMap()
    {
        spawnLoc();
    }

    private void base()//draw the base
    {
        setImage(mini);
        mini.setColor(Color.BLACK);
        mini.fillRect(0,0,151,79);
    }

    /*
     * called to update the minimap.
     */
    public void updateMap()
    {
        int yDraw,x,y;
        spawnLoc();//get current spawn location and other information
        player();//get player info
        base();
        if(ySpawn==0)//at surface blocks start lower
        {
            yDraw=14;
            y =20;
        }
        else
        {//undergorund setting
            yDraw=19;
            y=0;
        }
        boolean space;
        for(int i = ySpawn; i < (ySpawn+yDraw); i++)
        {
            x = 1;
            for(int j = xSpawn; j < (xSpawn+37);j++)
            {
                if(map[i][j]!=0)//if the block is not a space, draw a green block
                    space=false;
                else//otherwise draw a black block
                    space=true;
                drawMinRect(x,y,space);
                x+=4;
            }
            y+=4;
        }
        drawPlayer();
    }

    private void player()//get player related information
    {
        Test test = (Test)getWorld();
        try
        {
            playerX=test.playerX();
            playerY=test.playerY();
            if(xSpawn>=5)
                drawPx=21;
            else
                drawPx=1;
            if(ySpawn==0)
                drawPy=8;
            else
                drawPy=7;
            for(int i = 3;i <= 1074; i+=40)
            {
                if(playerX >=i&& playerX<=(i+40))//if the player's x coordinate is larger, then we add 4 pixels. This is to find which block the player is on horizontally
                    break;
                else
                    drawPx+=4;
            }

            for(int i = 68;i <= 696; i+=40)
            {
                if(ySpawn==0&&playerY<=67)//same logic as above, but vertically
                    drawPy=8;
                else if(playerY >=i&& playerY<=(i+40))
                    break;
                else
                    drawPy+=4;
            }
        }
        catch(IllegalStateException e)//in case player no longer exist
        {
        }
    }

    private void spawnLoc()//getting informations required to properly make the minimap
    {
        Test test = (Test)getWorld();
        xSpawn=test.mapx();
        ySpawn=test.mapy();
        if(xSpawn>=5)
            xSpawn-=5;
        if(xSpawn<=562)
            xMax=xSpawn+37;
        else
            xMax=599;

        if(!mapLoaded)
        {
            this.map=test.getMap();
            mapLoaded=true;
        }
    }

    private void drawMinRect(int blockX, int blockY,boolean space)//used to draw mini blocks in the minimap
    {
        setImage(mini);
        if(!space)
            mini.setColor(Color.GREEN);//non-space blocks
        else
            mini.setColor(Color.BLACK);//space blocks eg caves
        mini.fillRect(blockX,blockY,4,4);
    }

    private void drawPlayer()//draw the player on to the minimap
    {
        mini.setColor(Color.RED);
        mini.fillOval(drawPx,drawPy, 4,4);
    }

    /*
     * Called when a block is modified (removed specifically) from the map.
     * @param xInt The x coordinate of the block
     * @param yInt The y coordinate of the block
     * @param type The new block type to occupy that block space
     */
    public void mapMod (int xInt, int yInt, int type)
    {
        this.map[xInt][yInt]= type;
    }
}
