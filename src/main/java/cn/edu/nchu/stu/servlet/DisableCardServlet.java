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

@WebServlet(name = "DisableCardServlet", displayName = "DisableCard", urlPatterns = "/disable_card.do")
public class DisableCardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String redirectUrl = request.getParameter("redirect");
            redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
            try {
                Long cardId = Long.parseLong(request.getParameter("card_id"));
                Card card = dao.findCardById(cardId);
                if (card != null) {
                    if (card.getUserId() == user.getId() || user.getType() == User.ADMINISTRATOR || user.getType() == User.STAFF) {
                        dao.updateEnabledByCardId(cardId, false);
                        response.sendRedirect(redirectUrl);
                    } else {
                        session.setAttribute("error", "权限不足");
                        response.sendRedirect(redirectUrl);
                    }
                } else {
                    session.setAttribute("error", "卡号输入错误");
                    response.sendRedirect(redirectUrl);
                }
            } catch (NumberFormatException | NullPointerException e) {
                response.sendRedirect("index.jsp");
            }
        } else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
        }
    }
}
