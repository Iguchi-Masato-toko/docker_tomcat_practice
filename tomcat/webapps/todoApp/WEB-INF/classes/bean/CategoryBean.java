package bean;

import java.io.Serializable;

public class CategoryBean implements Serializable {

    private int id;
    private String categoryName;
    private String categoryClass;

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryClass(String categoryClass) {
        this.categoryClass = categoryClass;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryClass() {
        return categoryClass;
    }
}
