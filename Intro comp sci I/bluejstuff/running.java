import java.lang.InterruptedException;
public class running extends Thread{
    private Thread t;
    private String thread;

    running( String name){
        thread = name;
        System.out.println("creating" + thread);
    }

    public void run(){
        System.out.println("Running " + thread);
        try{
            for(int i = 0; 4>i; i++){
                System.out.println("Thread "+thread+", "+i);
                Thread.sleep(50);
            }
        }
        catch (InterruptedException e){
            System.out.println("Thread " + thread + " farted");
        }
        System.out.println("Thread " + thread + " exiting");
    }
    
    public void start(){
        System.out.println("starting "+thread);
        if(t==null)
        {
            t= new Thread(this, thread);
            t.start();
        }
    }
}