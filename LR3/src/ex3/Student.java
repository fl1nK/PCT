package ex3;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public int id;
    public List<String> arrayList = new ArrayList<>();

    public Student(int id) {
        this.id = id;
    }
    public Student(int id, List<String> arrayList) {
        this.id = id;
        this.arrayList = arrayList;
    }

    public int getId() {
        return id;
    }

    public List<String> getArrayList() {
        return arrayList;
    }
}
