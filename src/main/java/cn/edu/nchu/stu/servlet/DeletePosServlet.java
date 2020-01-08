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

@WebServlet(name = "DeletePosServlet", displayName = "DeletePos", urlPatterns = "/delete_pos.do")
public class DeletePosServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getType() == User.ADMINISTRATOR) {
                String redirectUrl = request.getParameter("redirect");
                redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
                String idText = request.getParameter("id");
                int id = 0;
                try {
                    id = Integer.parseInt(idText);
                } catch (Exception ignore) {
                }
                if (id != 0) {
                    dao.deletePos(id);
                    session.setAttribute("error", "删除成功");
                }
                else {
                    session.setAttribute("error", "请选择单位");
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
