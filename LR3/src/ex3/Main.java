package ex3;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Journal journal = new Journal();
        int nWeeks = 6;

        Teacher teacher = new Teacher("Lecturer 1", List.of("ІT-01", "ІT-02", "ІT-03"), nWeeks, journal);
        Teacher assistant1 = new Teacher("Assistant 1", List.of("ІT-01", "ІT-02", "ІT-03"), nWeeks, journal);
        Teacher assistant2 = new Teacher("Assistant 2", List.of("ІT-01", "ІT-02", "ІT-03"), nWeeks, journal);
        Teacher assistant3 = new Teacher("Assistant 3", List.of("ІT-01", "ІT-02", "ІT-03"), nWeeks, journal);

        teacher.start();
        assistant1.start();
        assistant2.start();
        assistant3.start();

        teacher.join();
        assistant1.join();
        assistant2.join();
        assistant3.join();

        System.out.println(journal);
    }
}
