package ex3;

import java.util.HashMap;
import java.util.List;

public class Journal {
    public HashMap<String, Group> groups = new HashMap<>();

    public Journal() {
        Group group1 = new Group("ІT-01", 25);
        Group group2 = new Group("ІT-02", 30);
        Group group3 = new Group("ІT-03", 26);

        this.groups.put(group1.getGroupName(), group1);
        this.groups.put(group2.getGroupName(), group2);
        this.groups.put(group3.getGroupName(), group3);
    }

    public synchronized void addMark(String groupName, int id, String mark) {
        List<Student> studentList = this.groups.get(groupName).groupList;
        for (Student student : studentList) {
            if (student.getId() == id) {
                student.arrayList.add(mark);
            }
        }
    }

    @Override
    public String toString() {
        return "" + groups;
    }
}
