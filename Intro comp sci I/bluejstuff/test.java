
/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class test
     */
    public static void main()
    {
        int bday[]= {1,1,1,1};
        for(int x=3;x>=0;x--)
        {
            for(int j=3;j>=x;j--)
            {
                for(int i=1;i<365;i++)
                {
                    for(int k=0;k<4;k++)
                        System.out.print(bday[k]+",");
                    System.out.println();
                    bday[j]+=1;
                }
                for(int g=3;g>=j;g--)
                    bday[g]=1;
            }
            bday[x]=1;
        }
    }
}
