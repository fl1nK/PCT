package ex6;

public class Counter {
    private  int count = 0;

    public void increment(){
        count++;
    }

    public void decrement(){
        count--;
    }

    public synchronized void incrementSync(){
        count++;
    }

    public synchronized void decrementSync(){
        count--;
    }

    public int getCount() {
        return count;
    }
}
