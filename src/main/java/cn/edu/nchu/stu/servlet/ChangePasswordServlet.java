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

@WebServlet(name = "ChangePasswordServlet", displayName = "Change Password", urlPatterns = "/change_password.do")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectUrl = request.getParameter("redirect");
        redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
        if (user != null) {
            String oldPassword = request.getParameter("old_password");
            String repeatPassword = request.getParameter("repeat_password");
            String newPassword = request.getParameter("new_password");
            if (oldPassword != null && repeatPassword != null && newPassword != null) {
                if (newPassword.equals(repeatPassword)) {
                    if (oldPassword.equals(user.getPassword())) {
                        if (newPassword.equals("")){
                            session.setAttribute("error", "请输入新密码");
                            response.sendRedirect(redirectUrl);
                            return;
                        }
                        //if (newPassword.matches("\\d{6}")) {
                            Dao.getInstance().updatePasswordByUserId(user.getId(), newPassword);
                            session.setAttribute("user", Dao.getInstance().findUserById(user.getId()));
                            session.setAttribute("error", "密码修改成功");
                        //}
                        //else {
                            //session.setAttribute("error", "密码必须为六位数字");
                        //}
                    }
                    else {
                        session.setAttribute("error", "旧密码输入错误");
                    }
                }
                else {
                    session.setAttribute("error", "两次密码输入不一致");
                }
            }
            else {
                if (oldPassword == null) {
                    session.setAttribute("error", "请输入旧密码");
                }
                else if (repeatPassword == null) {
                    session.setAttribute("error", "请再次输入新密码");
                }
                else {
                    session.setAttribute("error", "请输入新密码");
                }
            }
        }
        else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
        }
        response.sendRedirect(redirectUrl);
    }
}


