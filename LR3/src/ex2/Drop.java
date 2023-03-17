package ex2;

public class Drop {
    // Повідомлення, надіслане від виробника
    // до споживача.

    private int message;
    // True, якщо споживач повинен чекати
    // поки виробник надішле повідомлення,
    // false, якщо виробник повинен чекати,
    // поки споживач отримає повідомлення.
    private boolean empty = true;

    public synchronized int take() {
        // Зачекайте, поки повідомлення
        // стане доступним.
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Перемикання стану.
        empty = true;
        // Повідомити споживача, що статус
        // змінився.
        notifyAll();
        return message;
    }

    public synchronized void put(int message) {
        // Зачекайте, доки
        // повідомлення буде отримано.
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Перемикання стану.
        empty = false;
        // Зберегти повідомлення.
        this.message = message;
        // Повідомити споживача, що статус
        // змінився.
        notifyAll();
    }
}