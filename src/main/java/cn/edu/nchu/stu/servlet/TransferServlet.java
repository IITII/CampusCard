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

@WebServlet(name = "TransferServlet", displayName = "Transfer", urlPatterns = "/transfer.do")
public class TransferServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dao dao = Dao.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            long fromCardId = 0, toCardId = 0;
            float amount = 0;
            try {
                fromCardId = Long.parseLong(request.getParameter("from_card_id"));
                toCardId = Long.parseLong(request.getParameter("to_card_id"));
                amount = Float.parseFloat(request.getParameter("amount"));
            } catch (NumberFormatException | NullPointerException ignore) {
            }

            if (fromCardId != 0 || toCardId != 0 && amount != 0) {
                boolean fromCardBelongToUser = false;
                for (Card card : dao.findCardsByUserId(user.getId())) {
                    if (card.getId() == fromCardId) {
                        fromCardBelongToUser = true;
                        break;
                    }
                }

                if (fromCardId == 0) {  /// 存款
                    if (user.getType() == User.ADMINISTRATOR) {
                        dao.transfer(fromCardId, toCardId, amount);
                    }
                    else {
                        session.setAttribute("error", "权限不足");
                    }
                } else { /// 取款 / 消费 / 转账
                    if (fromCardBelongToUser) {

                    } else {

                    }
                }
            } else {
                if (amount == 0) {
                    session.setAttribute("error", "请输入转账金额");
                }
                else {
                    session.setAttribute("error", "请输入卡号");
                }
            }
        }
        else {
            session.setAttribute("error", "会话过期，请重新登录");
        }
        response.sendRedirect("login.jsp");
    }
}
