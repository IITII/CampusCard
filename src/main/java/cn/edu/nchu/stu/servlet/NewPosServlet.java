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

@WebServlet(name = "NewPosServlet", displayName = "NewPos", urlPatterns = "/new_pos.do")
public class NewPosServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getType() == User.ADMINISTRATOR) {
                String redirectUrl = request.getParameter("redirect");
                redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
                String name = request.getParameter("name");
                String position = request.getParameter("position");
                String departmentIdText = request.getParameter("department_id");
                long departmentId = 0;
                try {
                    departmentId = Long.parseLong(departmentIdText);
                } catch (Exception ignore) {
                }
                if (departmentId != 0 && name != null && !name.trim().isEmpty() && position != null && !position.trim().isEmpty()) {
                    dao.insertPos(departmentId, name.trim(), position.trim());
                    session.setAttribute("error", "添加成功");
                }
                else if (departmentId == 0) {
                    session.setAttribute("error", "请选择单位");
                }
                else if (name.equals("") || name.trim().isEmpty()) {
                    session.setAttribute("error", "请输入刷卡机名称");
                }
                else {
                    session.setAttribute("error", "请输入刷卡机位置");
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
