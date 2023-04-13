package ex3;

import java.util.HashMap;

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

    public void addMark(String groupName, Integer studentID, String mark) {
        synchronized (this.groups.get(groupName).groupList.get(studentID)) {
            this.groups.get(groupName).groupList.get(studentID).add(mark);
        }
    }

    @Override
    public String toString() {
        return "" + groups;
    }
}
