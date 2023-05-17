package ex3;

import java.util.List;

public class Teacher extends Thread {
    private final String teacherName;
    private final List<String> groupNames;
    private final Journal journal;
    private final int nWeeks;

    public Teacher(String teacherName, List<String> groupNames, int nWeeks, Journal journal) {
        this.teacherName = teacherName;
        this.groupNames = groupNames;
        this.journal = journal;
        this.nWeeks = nWeeks;
    }

    @Override
    public void run() {
        for (int i = 0; i < nWeeks; i++) {
            for (String groupName : groupNames) {
                for (int j = 1; j <= this.journal.groups.get(groupName).groupList.size(); j++) {
                    int mark = (int) ((Math.round(100 * Math.random() * 100)) / 100);
                    journal.addMark(groupName, j, mark + " (" + this.teacherName + " - Week "+ (i+1) + ")");
                }
            }
        }
    }
}