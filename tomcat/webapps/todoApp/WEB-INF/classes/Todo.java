
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import bean.*;

@WebServlet("/todo")
public class Todo extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        String msg = "テスト";
        req.setCharacterEncoding("utf-8");
        TodoDao tdo = new TodoDao();
        // 追加作業]
        if (req.getParameter("task") != null && req.getParameter("category") != null) {
            String task = req.getParameter("task");
            int category = Integer.parseInt(req.getParameter("category"));

            tdo.insert(task, category);
            // msg = "追加しました";
            res.sendRedirect("todo");
            return;
        }
        String noStr = req.getParameter("no");
        if (noStr != null) {
            int no = Integer.parseInt(noStr);
            tdo.deleteTable(no);
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("text/plain; charset=UTF-8");
            res.getWriter().write("OK");
            return;
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        // 全件取得
        TodoDTO tdto = tdo.selectAll();
        req.setAttribute("tdto", tdto);

        CategoryDTO cdto = tdo.selectCategoryAll();
        req.setAttribute("cdto", cdto);

        req.setAttribute("msg", msg);
        RequestDispatcher rd = req.getRequestDispatcher("/todo.jsp");
        rd.forward(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        TodoDao tdo = new TodoDao();

        TodoDTO tdto = tdo.selectAll();
        req.setAttribute("tdto", tdto);

        CategoryDTO cdto = tdo.selectCategoryAll();
        req.setAttribute("cdto", cdto);

        RequestDispatcher rd = req.getRequestDispatcher("/todo.jsp");
        rd.forward(req, res);

    }
}
