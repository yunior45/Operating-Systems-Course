
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    The garden class will act as the garden where
    Jordan, Charles, and Tracy will be doing the
    gardening.
 */

public class Garden {

    /*
        Lock and condition variables set within the Garden
        class that will be signaled or stopped, depending on
        the condition.
     */

    final Lock lock = new ReentrantLock();
    final Condition notDug = lock.newCondition();
    final Condition notPlanted = lock.newCondition();
    final Condition notFilled = lock.newCondition();

    /*
        Array holes is used to represent a garden of 10 holes.
        Flags 0, 1, and 2 will be used to represent the stage
        the hole is at. flag 0 represents that the hole is ready
        to be dug. flag 1 represents that the hole is ready to be
        planted. flag 2 represents that the hole is ready to be
        filled. The array initializes all 10 holes with flag 0.
     */

    final int[] holes = new int[10];

    /*
        Variables countHoles, countPlants, countFilled will
        be keeping track of how many holes have been filled,
        how many plants have been planted, as well as how many
        holes have been filled. Note that count holes will also
        be used to control the number of holes that are open
        at any given time which will be 5.
     */

    int countHoles, countPlants, countFilled;

    /*
        Dig function is exclusively called by the Jordan class.
        To control the number of holes open at any given time,
        the variable countHoles is used. To start, i first acquire
        the lock. I then check the value of countHoles. Only 5 holes
        can be open at any given time so if countHoles variable
        reaches 5, then the process will have to wait till a hole is
        available again. if the hole im currently at has a value of 0,
        i will dig the hole and increment countHoles. I then signal
        notPlanted in case there are any processes waiting for a hole
        to plant. I then release the lock.
     */

    void dig(int i) throws InterruptedException{
        lock.lock();
        try{
            while(countHoles==5){
                notDug.await();
                System.out.println("Jordan is waiting to dig a hole.");
            }
            if(holes[i] == 0){
                countHoles++;
                holes[i] = 1;
                System.out.println("Jordan dug a hole." + "\t\t\t\t\t\t\t\t" + ++i);
            }
            notPlanted.signal();
        } finally {
            lock.unlock();
        }
    }

    /*
        Plant function is exclusively called by the Charles class. I start
        by acquiring the lock. I then go to check the value of countHoles.
        if the value of countHoles is 0 then i means that there are no holes
        available to plant so the process must wait till a hole becomes available.
        I then check if the hole i am currently at has a value of 1. If it does,
        i increment countPlants meaning i have planted and charge the holes flag
        to 2. I then signal notFilled in case there is a process waiting to fill
        a hole that has been planted. I then release the lock.
     */

    void plant(int i) throws InterruptedException {
        lock.lock();
        try{
            while(countHoles == 0){
                notPlanted.await();
                System.out.println("Charles is waiting to plant a hole.");
            }
            if(holes[i] == 1){
                countPlants++;
                holes[i] = 2;
                System.out.println("Charles planted a hole." + "\t\t\t\t\t\t" + ++i);
            }
            notFilled.signal();
        } finally {
            lock.unlock();
        }
    }

    /*
        Fill function is exclusively called by the Tracy class. I first acquire the
        lock. I then check if countPlants equals 0. If it does, then it means that
        there are no holes that are planted ready to be filled. The process must then
        wait till a hole that is planted becomes available. I then check if the hole
        i am at has a flag value of 2. If it does then i increment countFilled meaning
        a hole has been filled, Decrement countPlants meaning that a plant has been filled,
        and decrement countHoles which means that a hole has been completed. Note that the
        most important variable here is countHoles as it controls the number of holes
        that can be open at any given time. Once a hole is filled then that means that
        jordan can go ahead and open another. I also change the flag of that whole back to 0.
        I then signal notDug in case there is a process waiting to dig a hole. I then release
        the lock.
     */

    void Fill(int i) throws InterruptedException {
        lock.lock();
        try{
            while(countPlants == 0){
                notFilled.await();
                System.out.println("Tracy is waiting to fill a hole.");
            }
            if(holes[i] == 2){
                countFilled++;
                countPlants--;
                countHoles--;
                holes[i] = 0;
                System.out.println("Tracy filled a hole." + "\t\t\t\t\t" + countFilled);
            }
            notDug.signal();
        } finally {
            lock.unlock();
        }
    }
}
