package ex3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Group {
    private String groupName;
    public HashMap<Integer, List<String>> groupList = new HashMap<>();

    public Group(String groupName, int sizeOfGroup) {
        this.groupName = groupName;
        generateGroupList(sizeOfGroup);
    }

    public String getGroupName() {
        return groupName;
    }

    private void generateGroupList(int sizeOfGroup){
        for (int i = 0; i < sizeOfGroup; i++) {
            this.groupList.put(i+1, new ArrayList<>());
        }
    }

    @Override
    public String toString() {
        System.out.println(groupName);
        for (int i = 1; i <= groupList.size(); i++) {
            System.out.println("studentID = " + i + " " + groupList.get(i));
        }
        return "";
    }
}
