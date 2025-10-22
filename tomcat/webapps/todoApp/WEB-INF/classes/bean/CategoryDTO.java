package bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryDTO implements Serializable {

    private ArrayList<CategoryBean> list;

    public CategoryDTO() {
        list = new ArrayList<CategoryBean>();
    }

    public void add(CategoryBean cb) {
        list.add(cb);
    }

    public CategoryBean get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }
}
