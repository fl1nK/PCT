package ex1_4;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            while (!Thread.currentThread().isInterrupted()){
                b.move();
                if (b.isInPool) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread name = "+ Thread.currentThread().getName()+"status = "  + Thread.currentThread().getState()+"isAlive = " +Thread.currentThread().isAlive()+"isInterrupted = " +Thread.currentThread().isInterrupted());
                    break;
                }
               // System.out.println("Thread name = "+ Thread.currentThread().getName()+"status = "  + Thread.currentThread().getState()+"isAlive = " +Thread.currentThread().isAlive()+"isInterrupted = " +Thread.currentThread().isInterrupted());
                Thread.sleep(5);

            }
        } catch(InterruptedException ex){
            System.out.println("ERROR" + ex);
        }
    }
}