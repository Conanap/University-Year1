public class testThread{
    public static void main(String args[]){
        running r1 = new running("thread-1");
        r1.start();
        running r2 = new running("thread-2");
        r2.start();
    }
}