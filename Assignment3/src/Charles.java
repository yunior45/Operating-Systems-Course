
import java.util.Random;

/*
    Charles class will be doing the planting in the garden.
    It must be noted that a sleep function is implemented
    before the for loop that calls the plant function. This
    sleep function will be assigned a random int value that
    represents the time before Charles starts to planting.
    Charles will take 500ms to plant a hole.
 */

public class Charles implements Runnable {

    Garden garden;
    Random rand = new Random();

    public Charles(Garden g){
        this.garden = g;
    }

    public void run(){
        try {
            Thread.sleep(rand.nextInt(1000)); // makes the execution more random
            for (int i = 0; i < 10; i++) {
                garden.plant(i);
                Thread.sleep(rand.nextInt(500)); // planting
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
