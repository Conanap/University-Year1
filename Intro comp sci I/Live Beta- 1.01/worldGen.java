import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a world in a .txt file. Grid base.
 * 
 * @author Albion Fung 
 * @version 0.0.1b
 */
public class worldGen extends Actor
{
    private String path = "world.txt";
    private boolean append = false;
    private int hWorld = 3000, wWorld=600;
    private int [] [] map = new int[hWorld][wWorld];
    private double dirtChance=0, ironChance=0, goldChance=0, silverChance=0, diamondChance=0, emeraldChance=0,
    lapisChance=0, caveChance=0, coalChance=0;
    private final double cobbleChance = 1.0;
    private final int space = 0, cobble = 1, dirt = 2, iron = 3, gold = 5, silver = 4, diamond = 6, emerald = 7,lapis = 8, coal = 9;
    private final int rope = 0, torch = 1;
    private ArrayList<Integer> itemxSpawn = new ArrayList<Integer>(), itemySpawn = new ArrayList<Integer>(), itemx = new ArrayList<Integer>(),itemy = new ArrayList<Integer>(),item = new ArrayList<Integer>();

    /*
     * Spawns world using chance created blocks. Exports to .txt
     */
    public void createWorld()
    {
        for(int i = 0; i < hWorld; i++)
        {
            //Setting chances of spawn
            if (i <= 5)
                dirtChance = 1.0;
            else if(i <= 30)
            {
                dirtChance=.78;
                coalChance=.04;
                ironChance=.02;
            }
            else if(i <=80)
            {
                dirtChance=0.75;
                coalChance=.06;
                ironChance=0.03;
            }
            else if (i <=200)
            {
                dirtChance=0.7;
                coalChance=.1;
                ironChance=0.07;
                silverChance=0.02;
            }
            else if (i <= 300)
            {
                dirtChance=0.65;
                coalChance=.11;
                ironChance=0.09;
                silverChance=0.05;
                goldChance=0.007;
            }

            else if (i <= 450)
            {
                dirtChance=0.4;
                ironChance=0.12;
                silverChance=0.062;
                goldChance=0.009;
            }
            else if (i <= 700)
            {
                dirtChance=0.3;
                ironChance=0.13;
                silverChance=0.07;
                goldChance=0.012;
                diamondChance=0.001;
            }
            else if (i <= 1050)
            {
                dirtChance=0.2;
                ironChance=0.13;
                silverChance=0.07;
                goldChance=0.017;
                diamondChance=0.002;
                emeraldChance=0.001;
            }
            else if (i <=2000)
            {
                dirtChance=0;
                ironChance=0.14;
                silverChance=0.08;
                goldChance=0.020;
                diamondChance=0.005;
                emeraldChance=0.0019;
                lapisChance=0.0005;
            }

            for(int x = 0; x < wWorld; x++)
            {
                double a = Math.random();//determine what spawns
                if(a < lapisChance)
                    map[i][x]=lapis;
                else if (a < emeraldChance)
                    map[i][x]=emerald;
                else if (a< diamondChance)
                    map[i][x]=diamond;
                else if (a < goldChance)
                    map[i][x]=gold;
                else if (a < silverChance)
                    map[i][x]=silver;
                else if (a < ironChance)
                    map[i][x]=iron;
                else if (a < coalChance)
                    map[i][x]=coal;
                else if (a< dirtChance)
                    map[i][x]=dirt;
                else if (a<=cobbleChance)
                    map[i][x]=cobble;
            }
        }

        genVein();
        genCave();
        writing();
    }

    //generates vaein, using flood map method
    private void genVein()
    {
        for(int i = 20; i<hWorld-20;i++)
        {
            for(int j = 10; j<wWorld-10;j++)
            {
                if(map[i][j]!=0&&map[i][j]!=1&&map[i][j]!=2)
                {
                    try
                    {
                        floodVein(j,i,map[i][j]);//recursion usage
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {}
                }
            }
        }
    }

    //flooding the veins, using flood map method
    private void floodVein(int x, int y, int type) throws ArrayIndexOutOfBoundsException
    {
        switch(type)
        {
            case 3:
            if(Math.random()<=0.5)
            {
                map[y][x]=type;
                if(Math.random()<.15)
                    floodVein(x+1,y, 3);
                if(Math.random()<.15)
                    floodVein(x-1,y,3);
                if(Math.random()<.15)
                    floodVein(x,y+1,3);
                if(Math.random()<.15)
                    floodVein(x,y-1,3);
            }
            break;

            case 4:
            if(Math.random()<=0.31)
            {
                map[y][x]=type;
                if(Math.random()<.15)
                    floodVein(x+1,y, 4);
                if(Math.random()<.15)
                    floodVein(x-1,y,4);
                if(Math.random()<.15)
                    floodVein(x,y+1,4);
                if(Math.random()<.15)
                    floodVein(x,y-1,4);
            }
            break;

            case 5:
            if(Math.random()<=0.23)
            {
                map[y][x]=type;
                if(Math.random()<.15)
                    floodVein(x+1,y, 5);
                if(Math.random()<.15)
                    floodVein(x-1,y,5);
                if(Math.random()<.15)
                    floodVein(x,y+1,5);
                if(Math.random()<.15)
                    floodVein(x,y-1,5);
            }
            break;

            case 6:
            if(Math.random()<=0.19)
            {
                map[y][x]=type;
                if(Math.random()<.15)
                    floodVein(x+1,y, 6);
                if(Math.random()<.15)
                    floodVein(x-1,y,6);
                if(Math.random()<.15)
                    floodVein(x,y+1,6);
                if(Math.random()<.15)
                    floodVein(x,y-1,6);
            }
            break;

            case 7:
            if(Math.random()<=0.08)
            {
                map[y][x]=type;
                if(Math.random()<.15)
                    floodVein(x+1,y, 7);
                if(Math.random()<.15)
                    floodVein(x-1,y,7);
                if(Math.random()<.15)
                    floodVein(x,y+1,7);
                if(Math.random()<.15)
                    floodVein(x,y-1,7);
            }
            break;

            case 8:
            if(Math.random()<=0.021)
            {
                map[y][x]=type;
                if(Math.random()<.5)
                    floodVein(x+1,y, 8);
                if(Math.random()<.5)
                    floodVein(x-1,y,8);
                if(Math.random()<.5)
                    floodVein(x,y+1,8);
                if(Math.random()<.5)
                    floodVein(x,y-1,8);
            }
            break;

            case 9:
            if(Math.random()<=0.5)
            {
                map[y][x]=type;
                if(Math.random()<.3)
                    floodVein(x+1,y, 8);
                if(Math.random()<.3)
                    floodVein(x-1,y,8);
                if(Math.random()<.3)
                    floodVein(x,y+1,8);
                if(Math.random()<.3)
                    floodVein(x,y-1,8);
            }
            break;

            default:
            break;
        }//recursion used to generate veins of ore
    }

    //same as genvein but caves
    private void genCave()
    {
        for(int i = 20; i<hWorld-20;i++)
        {
            for(int j = 10;j<wWorld-10;j++)
            {
                if(Math.random()<=.01)
                {
                    try
                    {
                        floodCave(j,i);
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {}
                }
            }
        }
    }

    private void floodCave(int x, int y) throws ArrayIndexOutOfBoundsException
    {
        if(map[y][x]!=0&&y!=0&&x!=0&&y!=2999&&x!=599)
        {
            map[y][x]=0;
            if(Math.random()<.3)
                floodCave(x+1,y);
            if(Math.random()<.3)
                floodCave(x-1,y);
            if(Math.random()<.3)
                floodCave(x,y+1);
            if(Math.random()<.3)
                floodCave(x,y-1);
        }
    }

    //saving map
    private void writing()
    {
        try
        {
            int [] [] newMap;
            try
            {
                Test test = (Test)getWorld();
                newMap=test.getMap();
            }
            catch(NullPointerException e)
            {
                newMap=this.map;
            }
            append=false;
            String layer;
            for(int i = 0; i<hWorld; i++)
            {
                layer="";
                for(int j = 0; j<wWorld; j++)
                {
                    if(i==hWorld-1&&j==hWorld-1)
                        layer +=newMap[i][j];
                    else
                        layer +=(newMap[i][j]+"\n");
                }
                WriteToFile(layer,append, path);
                append=true;
            }
        }
        catch(IOException e)
        {
            System.out.println("An error has occurred when writing to the world save file. Please contact the developers for more help.");
        }
    }

    //actual file writer
    private void WriteToFile(String text, boolean append, String path) throws IOException
    {
        FileWriter write = new FileWriter (path,append);
        PrintWriter printLine = new PrintWriter (write);

        printLine.printf("%s",text);
        printLine.close();
    }

    /*
     * for the save items, changing their x and y coordinate as necessary.
     * @param newx new Item's location on screen (x)
     * @param newy new Item's location on screen (y)
     * @param id The ID of the item
     */
    public void changeItemxy(int newx, int newy, int id)
    {
        itemx.set(id,newx);
        itemy.set(id,newy);
    }

    //save items currently in the world
    private void saveItemWorld()
    {
        getItems();
        boolean change = false;
        for(int i = 0; i<item.size(); i++)
        {
            String chocolate = "";
            chocolate+=item.get(i)+"\n"+itemxSpawn.get(i)+"\n"+itemySpawn.get(i)+"\n"+itemx.get(i)+"\n"+itemy.get(i);
            if(i!=item.size()-1)
                chocolate+="\n";
            try{
                WriteToFile(chocolate,change,"itemSave.txt");
            }
            catch(IOException e)
            {
                System.out.println("An error occurred when saving the items from the world");
            }
            change = true;
        }
    }

    //get current items in world
    private void getItems()
    {
        Test test = (Test) getWorld();
        item=test.item();
        itemx=test.itemx();
        itemy=test.itemy();
        itemxSpawn=test.itemxSpawn();
        itemySpawn=test.itemySpawn();
    }

    //saving world
    private void saveWorld()
    {
        writing();
    }

    /*
     * Called when clicked on hotel to save. Saves using 2 .txt files.
     * 
     */
    public void saveEverything()
    {
        saveItemWorld();
        saveWorld();
    }
}