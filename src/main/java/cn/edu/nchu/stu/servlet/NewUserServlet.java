package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "NewUserServlet", displayName = "NewUser", urlPatterns = "/new_user.do")
public class NewUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getType() == User.STAFF) {
                String redirectUrl = request.getParameter("redirect");
                redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
                String userIdText = request.getParameter("user_id");
                Long userId = null;
                try {
                    userId = Long.parseLong(userIdText);
                } catch (Exception ignore) {
                }
                if (userId != null) {
                    User inputUser = dao.findUserById(userId);
                    dao.updateStaffInfo(userId, user.getDepartment(), inputUser.getJobTitle(), inputUser.getGender(), inputUser.getTelNumber());
                    session.setAttribute("error", "添加成功");
                }
                else {
                    session.setAttribute("error", "请输入位置");
                }
                response.sendRedirect(redirectUrl);
            }
            else {
                session.setAttribute("error", "权限不足");
            }
        } else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
        }
    }
}
