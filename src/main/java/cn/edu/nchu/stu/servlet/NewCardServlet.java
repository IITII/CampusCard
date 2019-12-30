package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.Card;
import cn.edu.nchu.stu.data.model.CardType;
import cn.edu.nchu.stu.data.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewCardServlet", displayName = "NewCard", urlPatterns = "/new_card.do")
public class NewCardServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Dao dao = Dao.getInstance();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String redirectUrl = request.getParameter("redirect");
            redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
            String username = request.getParameter("username");
            if (username != null) {
                User inputUser = dao.findUserByUsername(username);
                if (inputUser != null) {
                    if (user.getType() == User.ADMINISTRATOR) {
                        String typeText = request.getParameter("type");
                        try {
                            int type = Integer.parseInt("type");
                            List<CardType> cardTypes = dao.findAllCardTypes();
                            boolean found = false;
                            for (CardType cardType : cardTypes) {
                                if (cardType.getId() == type) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found) {
                                Long cardId = dao.insertCard(inputUser.getId(), type, true);
                                session.setAttribute("card_id", cardId);
                            } else {
                                session.setAttribute("error", "找不到指定的卡类型");
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            if (typeText == null) {
                                session.setAttribute("error", "请选择卡类型");
                            }
                        }
                    } else {
                        session.setAttribute("error", "权限不足");
                        response.sendRedirect(redirectUrl);
                    }
                } else {
                    session.setAttribute("error", "用户名输入错误：查无此人");
                    response.sendRedirect(redirectUrl);
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
