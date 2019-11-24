
import java.util.Random;

/*
    Jordan class will be doing the digging in the garden.
    It must be noted that a sleep function is implemented
    before the for loop that calls the dig function. This
    sleep function will be assigned a random int value that
    represents the time before jordan can start to dig.
    Jordan will take 100ms to dig a hole.
 */

class Jordan implements Runnable {

    Garden garden;
    Random rand = new Random();

    public Jordan(Garden g){
        this.garden = g;
    }

    public void run(){
        try {
            Thread.sleep(rand.nextInt(1000)); // makes the execution more random
            for (int i = 0; i < 10; i++) {
                garden.dig(i);
                Thread.sleep(rand.nextInt(100)); // digging
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
