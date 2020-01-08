package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.Card;
import cn.edu.nchu.stu.data.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SetDailyLimitServlet", displayName = "SetDailyLimit", urlPatterns = "/set_daily_limit.do")
public class SetDailyLimitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        User user = (User) session.getAttribute("user");
        String redirectUrl = request.getParameter("redirect");
        redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
        if (user != null) {
            String cardIdText = request.getParameter("card_id");
            String dailyLimitText = request.getParameter("daily_limit");
            try {
                long cardId = Long.parseLong(cardIdText);
                float dailyLimit = Float.parseFloat(dailyLimitText);
                Card card = dao.findCardById(cardId);
                if (card != null) {
                    if (card.getUserId() == user.getId() || user.getType() == User.ADMINISTRATOR || user.getType() == User.STAFF) {
                        dao.updateDailyLimit(cardId, dailyLimit);
                        session.setAttribute("error", "设置成功");
                    } else {
                        session.setAttribute("error", "权限不足");
                    }
                } else {
                    session.setAttribute("error", "卡号输入错误");
                }
            } catch (NumberFormatException | NullPointerException ignore) {
                if (dailyLimitText == null) {
                    session.setAttribute("error", "请输入消费上限");
                }
                else {
                    session.setAttribute("error", "请输入卡号");
                }
            }
            response.sendRedirect(redirectUrl);
        } else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
        }
    }
}
