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
import java.sql.Date;

@WebServlet(name = "CancelScheduleServlet", displayName = "CancelSchedule", urlPatterns = "/cancel_schedule.do")
public class CancelScheduleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String redirectUrl = request.getParameter("redirect");
            redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
            String userIdText = request.getParameter("user_id");
            if (userIdText != null) {
                try {
                    Long userId = Long.parseLong(userIdText);
                    User inputUser = dao.findUserById(userId);
                    if (inputUser != null) {
                        if (user.getType() == User.STAFF && inputUser.getDepartment().equals(user.getDepartment())) {
                            String dateText = request.getParameter("date");
                            if (dateText != null) {
                                try {
                                    Date date = Date.valueOf(dateText);
                                    dao.deleteScheduleByDateUserId(dateText, userId);
                                    session.setAttribute("error", "取消排班成功");
                                } catch (IllegalArgumentException e) {
                                    session.setAttribute("error", "日期格式错误");
                                }
                            } else {
                                session.setAttribute("error", "请选择日期");
                            }
                        } else {
                            session.setAttribute("error", "权限不足");
                        }
                    } else {
                        session.setAttribute("error", "用户名输入错误：查无此人");
                    }
                } catch (NumberFormatException e) {
                    session.setAttribute("error", "用户名输入错误：查无此人");
                }
            } else {
                session.setAttribute("error", "请输入用户名");
            }
            response.sendRedirect(redirectUrl);
        } else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
        }
    }
}
