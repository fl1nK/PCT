package ex5;

public class Sync {
    private boolean flag;
    private int num;
    public Sync(){
        flag = true;
        num=0;
    }

    public boolean getFlag() {
        return flag;
    }

    public synchronized void waitSync(boolean control, String s){
            while (getFlag() != control){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.print(s);
            flag = !flag;
            num++;

            if (num%100 == 0){
                System.out.println();
            }
            notifyAll();
    }
}
