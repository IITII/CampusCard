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

@WebServlet(name = "SetStaffInfoServlet", displayName = "SetStaffInfo", urlPatterns = "/set_staff_info.do")
public class SetStaffInfoServlet extends HttpServlet {
    @Override
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
                        if (user.getType() == User.STAFF) {
                            String gender = request.getParameter("gender");
                            String department = user.getDepartment();
                            String jobTitle = request.getParameter("job_title");
                            String telNumber = request.getParameter("tel_number");
                            if (gender != null && !gender.trim().isEmpty() && department != null && !department.trim().isEmpty() && jobTitle != null && !jobTitle.trim().isEmpty() && telNumber != null && !telNumber.trim().isEmpty()) {
                                dao.updateStaffInfo(userId, department.trim(), jobTitle.trim(), gender.trim(), telNumber.trim());
                                session.setAttribute("error", "更新成功");
                            } else {
                                if (gender == null || gender.trim().isEmpty()) {
                                    session.setAttribute("error", "请输入性别");
                                }
                                else if (jobTitle == null || jobTitle.trim().isEmpty()) {
                                    session.setAttribute("error", "请输入职称");
                                }
                                else {
                                    session.setAttribute("error", "请输入联系方式");
                                }
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
