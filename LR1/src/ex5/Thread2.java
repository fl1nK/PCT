package ex5;

public class Thread2 extends Thread{
    private Sync sync;
    private final boolean flag;
    private final String text;

    public Thread2(Sync sync, boolean flag, String text){
        this.sync = sync;
        this.flag = flag;
        this.text = text;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            sync.waitSync(this.flag,this.text);
        }
    }
}
