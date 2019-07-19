import java.util.Random;

public class Threads extends Thread {
    Rooms room;
    int size;
    Threads(Rooms rooms, int m){
        this.room = rooms;
        this.size = m;
    }
    public boolean stillThreads = true;
    Random random = new Random();
    public synchronized void run(){
        int n = random.nextInt(this.size);
        stillThreads = true;
        this.room.enter(n);
        boolean result = this.room.exit();
        if (result == true){
            stillThreads = false;
            this.notifyAll();
        }
        while (stillThreads){
            this.wait();
        }
    }
}