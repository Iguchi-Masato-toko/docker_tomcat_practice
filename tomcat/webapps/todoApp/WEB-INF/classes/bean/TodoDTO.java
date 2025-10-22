package bean;

import java.io.Serializable;
import java.util.ArrayList;

public class TodoDTO implements Serializable {

    private ArrayList<TodoBean> list;

    public TodoDTO() {
        list = new ArrayList<TodoBean>();
    }

    public void add(TodoBean tb) {
        list.add(tb);
    }

    public TodoBean get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }
}
