package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.Card;
import cn.edu.nchu.stu.data.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;

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
        request.setCharacterEncoding("UTF-8");
        Dao dao = Dao.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectUrl = request.getParameter("redirect");
        if (user != null) {
            long fromCardId = 0, toCardId = 0, posId = 0;
            float amount = 0;
            try {
                fromCardId = Long.parseLong(request.getParameter("from_card_id"));
            } catch (NumberFormatException | NullPointerException ignore) {
            }
            try {
                toCardId = Long.parseLong(request.getParameter("to_card_id"));
            } catch (NumberFormatException | NullPointerException ignore) {
            }
            try {
                amount = Float.parseFloat(request.getParameter("amount"));
            } catch (NumberFormatException | NullPointerException ignore) {
            }
            try {
                posId = Long.parseLong(request.getParameter("pos_id"));
            } catch (NumberFormatException | NullPointerException ignore) {
            }

            if ((fromCardId != 0 || toCardId != 0) && amount > 0 && posId != 0) {
                if (fromCardId == 0) {  /// 存款
                    if (user.getType() == User.ADMINISTRATOR) {
                        boolean status = dao.transfer(posId ,fromCardId, toCardId, amount);
                        if (status) {
                            session.setAttribute("error", "存款成功");
                            response.sendRedirect(redirectUrl == null ? "index.jsp" : redirectUrl);
                            return;
                        } else {
                            session.setAttribute("error","此卡已被禁用");
                        }
                    }
                    else {
                        session.setAttribute("error", "权限不足");
                    }
                } else { /// 取款 / 消费 / 转账
                    Card card = dao.findCardById(fromCardId);
                    if (card == null) {
                        session.setAttribute("error", "卡号输入错误");
                    }
                    else if (card.getUserId() == user.getId()) {
                        if (dao.transfer(posId, fromCardId, toCardId, amount)) {
                            session.setAttribute("error", "消费成功");
                        } else {
                            session.setAttribute("error", "余额不足或超过每日消费上限");
                        }
                    } else {
                        session.setAttribute("error", "权限不足");
                    }
                }
            } else {
                if (amount <= 0) {
                    session.setAttribute("error", "金额输入错误");
                }
                else if (posId == 0) {
                    session.setAttribute("error", "请选择 POS 机");
                }
                else {
                    session.setAttribute("error", "请输入卡号");
                }
            }
            response.sendRedirect(redirectUrl);
        }
        else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
        }
    }
}
