package ex5;

public class Thread1 extends Thread{

    private String text = "";

    public Thread1(String text){
        this.text = text;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.print(this.text);
            }
            System.out.println();
        }
    }
}
