package bean;

import java.io.Serializable;

public class TodoBean implements Serializable {

    private int no;
    private String task;
    private String category;
    private int categoryId;
    private String date;
    private String categoryClass;

    public void setNo(int no) {
        this.no = no;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategoryClass(String categoryClass) {
        this.categoryClass = categoryClass;
    }

    public int getNo() {
        return no;
    }

    public String getTask() {
        return task;
    }

    // public String getCategory() {
    //     Map<Integer, String> categories = Map.of(
    //             1, "着手前",
    //             2, "作業中",
    //             3, "完了"
    //     );
    //     return categories.getOrDefault(category, "");
    // }
    public String getCategory() {
        return category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    // public String getCategoryClass() {
    //     Map<Integer, String> categoryClasses = Map.of(
    //             1, "bg-red-100 text-red-600",
    //             2, "bg-blue-100 text-blue-600",
    //             3, "bg-green-100 text-green-600"
    //     );
    //     return categoryClasses.getOrDefault(category, "bg-red-100 text-red-600");
    // }
    public String getCategoryClass() {
        return categoryClass;
    }

    public String getDate() {
        return date;
    }
}
