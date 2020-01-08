package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.CardType;
import cn.edu.nchu.stu.data.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewDepartmentServlet", displayName = "NewDepartment", urlPatterns = "/new_department.do")
public class NewDepartmentServlet extends HttpServlet {
    @Override
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
                String contact = request.getParameter("contact");
                String typeText = request.getParameter("type");
                int type = 0;
                try {
                    type = Integer.parseInt(typeText);
                } catch (Exception ignore) {
                }
                if (type != 0 && name != null && !name.trim().isEmpty() && contact != null && !contact.trim().isEmpty()) {
                    dao.insertDepartment(name.trim(), contact.trim(), type);
                    session.setAttribute("error", "添加成功");
                }
                else if (type == 0) {
                    session.setAttribute("error", "请选择单位类型");
                }
                else if (name.equals("") && !name.trim().isEmpty()) {
                    session.setAttribute("error", "请输入单位名称");
                }
                else {
                    session.setAttribute("error", "请输入单位联系方式");
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
