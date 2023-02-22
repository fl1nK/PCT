package ex1_4;

public class BallJoinThread extends Thread{
    private Ball b;

    public BallJoinThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{

            for (int i = 0; i <= 1000; i++){
                b.move();

                // System.out.println("Thread name = "+ Thread.currentThread().getName()+"status = "  + Thread.currentThread().getState()+"isAlive = " +Thread.currentThread().isAlive()+"isInterrupted = " +Thread.currentThread().isInterrupted());
                Thread.sleep(2);

            }
        } catch(InterruptedException ex){
            System.out.println("ERROR" + ex);
        }
    }
}
