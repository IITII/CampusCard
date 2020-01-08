package cn.edu.nchu.stu.servlet;

import cn.edu.nchu.stu.data.Dao;
import cn.edu.nchu.stu.data.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Scanner;

@MultipartConfig
@WebServlet(name = "ImportCsvServlet", displayName = "ImportCsv", urlPatterns = "/import_csv.do")
public class ImportCsvServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectUrl = request.getParameter("redirect");
        redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
        if (user.getType() == User.ADMINISTRATOR) {
            Dao dao = Dao.getInstance();
            Part url = request.getPart("csv");
            Scanner scanner = null;
            try {
                scanner = new Scanner(url.getInputStream());
                scanner.nextLine();
            } catch (Exception e) {
                session.setAttribute("error", "未选择文件");
                response.sendRedirect(redirectUrl);
                return;
            }
            while (scanner.hasNextLine()) {
                try {
                    String[] split = scanner.nextLine().split(", ");
                    long cardId = Long.parseLong(split[1]);
                    long posId = dao.findPosIdByName(split[3]);
                    float amount = Float.parseFloat(split[4]);
                    Timestamp createAt = Timestamp.valueOf(split[5]);
                    if (split[2].equals("充值")) {
                        //dao.insertTransaction(posId, 0, cardId, amount, createAt);
                    } else if (split[2].equals("消费")) {
                        dao.insertTransaction(posId, cardId, 0, amount, createAt);
                    } else {
                        long fromCardId = Long.parseLong(split[2].substring(0, 6));
                        long toCardId = Long.parseLong(split[2].substring(split[2].length() - 6));
                        dao.insertTransaction(posId, fromCardId, toCardId, amount, createAt);
                    }
                } catch (Exception e) {
                    session.setAttribute("error", "文件格式错误，请参考导出格式");
                    response.sendRedirect(redirectUrl);
                    return;
                }
            }
            session.setAttribute("error", "导入成功");
            response.sendRedirect(redirectUrl);
            return;
        }
        else {
            session.setAttribute("error", "会话过期，请重新登录");
            response.sendRedirect("login.jsp");
            return;
        }
    }
}
