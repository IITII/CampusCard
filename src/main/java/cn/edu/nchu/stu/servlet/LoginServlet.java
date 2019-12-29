package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", displayName = "Login", urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null) {
            session.setAttribute("error", "用户名不能为空");
        }
        else if (password == null) {
            session.setAttribute("error", "密码不能为空");
        }
        else {
            User user = dao.findUserByUsername(username);
            if (user != null) {
                session.setAttribute("user", user);
                if (user.getType()==User.ADMINISTRATOR)
                    response.sendRedirect("Admin/danweizengshangaicha.jsp");
                else if (user.getType()==User.STAFF)
                    response.sendRedirect("Teacher/renyuanxinxizengshangaicha.jsp");
                else
                    response.sendRedirect("Student/shuakaxiaofei.jsp");
                return;
            }
        }
        session.setAttribute("error", "用户名或密码错误");
        response.sendRedirect("login.jsp");
    }
}
