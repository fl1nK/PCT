package ex3;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupName;
    public List<Student> groupList = new ArrayList<Student>();

    public Group(String groupName, int sizeOfGroup) {
        this.groupName = groupName;
        generateGroupList(sizeOfGroup);
    }

    public String getGroupName() {
        return groupName;
    }

    private void generateGroupList(int sizeOfGroup){
        for (int i = 0; i < sizeOfGroup; i++) {
            this.groupList.add(new Student(i+1));
        }
    }

    @Override
    public String toString() {
        List<Student> studentList = groupList;

        System.out.println(groupName);
            for (Student student : studentList) {
                System.out.println("studentID = " + student.getId() + " " + student.getArrayList());
            }
        return "";
    }
}
