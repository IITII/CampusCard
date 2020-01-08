package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.Card;
import cn.edu.nchu.stu.data.model.Pos;
import cn.edu.nchu.stu.data.model.Transaction;
import cn.edu.nchu.stu.data.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "GenerateCsvServlet", displayName = "GenerateCsv", urlPatterns = "/generate_csv.do")
public class GenerateCsvServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectUrl = request.getParameter("redirect");
        redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
        Long cardId = null, posId = null;
        Dao dao = Dao.getInstance();
        if (user != null) {
            try {
                posId = Long.parseLong(request.getParameter("pos_id"));
            } catch (Exception ignore) {
            }
            try {
                cardId = Long.parseLong(request.getParameter("card_id"));
            } catch (NullPointerException | NumberFormatException ignore) {
            }

            List<Transaction> transactions = null;
            if (cardId != null) {
                Card card = dao.findCardById(cardId);
                if (card != null) {
                    if (card.getUserId() == user.getId() || user.getType() == User.ADMINISTRATOR || user.getType() == User.STAFF) {
                        transactions = dao.findTransactionsByCardId(cardId);
                    }
                    else {
                        session.setAttribute("error", "权限不足");
                    }
                } else {
                    session.setAttribute("error", "找不到卡号");
                }
            } else if (posId != null) {
                List<Pos> posList = dao.findAllPoses();
                long did = dao.findDepartmentIdByUsername(user.getUsername());
                boolean found = false;
                for (Pos pos : posList) {
                    if (pos.getDid() == did && pos.getId() == posId) {
                        found = true;
                        break;
                    }
                }
                if (user.getType() == User.STAFF && found) {
                    transactions = dao.findTransactionsByPosId(posId);
                }
                else {
                    session.setAttribute("error", "权限不足");
                }
            } else {
                if (user.getType() == User.ADMINISTRATOR) {
                    transactions = dao.findAllTransactions();
                }
                else if (user.getType() == User.STAFF) {
                    transactions = dao.findTransactionsByPosesOfDepartmentOfUser(user.getUsername());
                }
                else {
                    session.setAttribute("error", "卡号为空");
                }
            }
            if (transactions != null) {
                response.setCharacterEncoding("gbk");
                response.setContentType("text/csv;charset=gbk");
                response.setHeader("Content-disposition", "attachment;filename=transactions.csv");
                PrintWriter output = response.getWriter();
                output.println("编号, 卡号, 交易内容, 交易 POS 机名称, 金额, 创建时间");
                for (Transaction transaction : transactions) {
                    String details;
                    if (transaction.getFromCardId() == 0) {
                        details = "充值";
                    } else if (transaction.getToCardId() == 0) {
                        details = "消费";
                    } else {
                        details = String.format("%06d 转账给卡号 %06d", transaction.getFromCardId(), transaction.getToCardId());
                    }
                    output.print(String.format(Locale.getDefault(), "%d, %06d, %s, %s, %.2f, %s", transaction.getId(), transaction.getFromCardId(), details, transaction.getPosName(), transaction.getAmount(), DateFormat.getDateTimeInstance().format(transaction.getCreateAt())));
                    output.println();
                }
                output.close();
            }
            else {
                response.sendRedirect(redirectUrl);
                return;
            }
        }
        else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
            return;
        }
    }
}
