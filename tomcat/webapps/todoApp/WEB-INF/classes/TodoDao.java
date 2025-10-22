
import bean.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TodoDao {

    private final String DB_HOST = System.getenv("DB_HOST");
    private final String DBNAME = System.getenv("DB_NAME");
    private final String USER = System.getenv("DB_USER");
    private final String PASS = System.getenv("DB_PASS");
    private final String TODO_TABLE_NAME = System.getenv("DB_TABLE");
    private final String CATEGORY_TABLE_NAME = System.getenv("DB_CATEGORY_TABLE");
    private final String URL = "jdbc:mysql://" + DB_HOST + ":3306/" + DBNAME;
    private Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // 初期設定
    public TodoDao() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String fullUrl = URL + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";
        con = DriverManager.getConnection(fullUrl, USER, PASS);

        // todo テーブル作成
        ps = con.prepareStatement(
            "CREATE TABLE IF NOT EXISTS " + TODO_TABLE_NAME +
            " (id INT PRIMARY KEY AUTO_INCREMENT, task VARCHAR(255), category INT, date DATE)"
        );
        ps.executeUpdate();

        // category テーブル作成
        ps = con.prepareStatement(
            "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE_NAME +
            " (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), className VARCHAR(100))"
        );
        ps.executeUpdate();

        // category テーブルが空なら初期データ投入（存在する場合はスキップ）
        try (PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM " + CATEGORY_TABLE_NAME);
             ResultSet r = check.executeQuery()) {
            int cnt = 0;
            if (r.next()) {
                cnt = r.getInt(1);
            }
            if (cnt == 0) {
                initCategoryInsert();
            }
        } catch (SQLException e) {
            System.out.println("TodoDao: category テーブル確認エラー: " + e.getMessage());
            e.printStackTrace();
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        disconnect();
    }
}


    public TodoDTO selectAll() {
        String sql
                = "SELECT T1.id,T1.task,T1.date,T1.category,T2.name,T2.className from "
                + TODO_TABLE_NAME + " AS T1 INNER JOIN "
                + CATEGORY_TABLE_NAME + " AS T2"
                + " ON T1.category = T2.id";
        TodoDTO tdto = new TodoDTO();
        try {
            connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TodoBean tb = new TodoBean();
                tb.setNo(rs.getInt("id"));
                tb.setTask(rs.getString("task"));
                tb.setCategory(rs.getString("name"));
                tb.setCategoryId(rs.getInt("category"));
                tb.setCategoryClass(rs.getString("className"));
                tb.setDate(rs.getString("date"));
                tdto.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        disconnect();
        return tdto;
    }

    public CategoryDTO selectCategoryAll() {
        String sql = "SELECT * FROM " + CATEGORY_TABLE_NAME;
        CategoryDTO cdto = new CategoryDTO();
        try {
            connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CategoryBean cb = new CategoryBean();
                cb.setId(rs.getInt("id"));
                cb.setCategoryName(rs.getString("name"));
                cb.setCategoryClass(rs.getString("className"));
                cdto.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        disconnect();
        return cdto;
    }

    // DB接続
    public void connect() {
        try {
            //DB接続
            Class.forName("com.mysql.cj.jdbc.Driver");
            String fullUrl = URL + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";
            con = DriverManager.getConnection(fullUrl, USER, PASS);
            // ps = con.prepareStatement("USE " + DBNAME);
            // ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // サンプルデータ挿入
    public void insert(String task, int num) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sql = "INSERT INTO " + TODO_TABLE_NAME + " (task, category, date) VALUES ('" + task + "', " + num + ", '" + date + "')";
        executeSql(sql);
    }

    public void initCategoryInsert() {
        String sql1 = "INSERT INTO " + CATEGORY_TABLE_NAME + " (name, className) VALUES ('未着手', 'bg-red-100 text-red-600')";
        String sql2 = "INSERT INTO " + CATEGORY_TABLE_NAME + " (name, className) VALUES ('進行中', 'bg-blue-100 text-blue-600')";
        String sql3 = "INSERT INTO " + CATEGORY_TABLE_NAME + " (name, className) VALUES ('完了', 'bg-green-100 text-green-600')";
        executeSql(sql1);
        executeSql(sql2);
        executeSql(sql3);
    }

    public void executeSql(String sql) {
        int result = 0;
        try {
            connect();
            //SQL実行
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        disconnect();
    }

    public void delete() {
        String sql = "DELETE FROM " + TODO_TABLE_NAME;
        executeSql(sql);
    }

    public void deleteTable(int no) {
        String sql = "DELETE FROM " + TODO_TABLE_NAME + " WHERE id = ?";
        try {
            connect();
            if (ps != null) {
                ps.close();
            }
            ps = con.prepareStatement(sql);
            ps.setInt(1, no);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        disconnect();
    }

    public void disconnect() {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
