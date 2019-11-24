
import java.util.Random;

/*
    Tracy class will be doing the filling in the garden.
    It must be noted that a sleep function is implemented
    before the for loop that calls the fill function. This
    sleep function will be assigned a random int value that
    represents the time before Tracy can starts to fill.
    Tracy will take 500ms to fill a hole.
 */

public class Tracy implements Runnable {

    Garden garden;
    Random rand = new Random();

    public Tracy(Garden g){
        this.garden = g;
    }

    public void run(){
        try {
            Thread.sleep(rand.nextInt(1000)); // makes the execution more random
            for (int i = 0; i < 10; i++) {
                garden.Fill(i);
                Thread.sleep(rand.nextInt(500)); // Filling
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
