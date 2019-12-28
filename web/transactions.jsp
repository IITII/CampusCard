<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="cn.edu.nchu.stu.data.model.Card" %>
<%@ page import="cn.edu.nchu.stu.data.model.User" %>
<%@ page import="cn.edu.nchu.stu.data.model.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) session.getAttribute("user");
    List<Transaction> transactions = new ArrayList<>();
    int size = 0;
    Long cardId = null;
    Integer pageNumber = null, pageSize = null;
    if (user != null) {
        try {
            cardId = Long.parseLong(request.getParameter("card_id"));
            pageNumber = Integer.parseInt(request.getParameter("page_number"));
            pageSize = Integer.parseInt(request.getParameter("page_size"));
        } catch (NumberFormatException | NullPointerException ignore) {
        }

        if (cardId != null) {
            Dao dao = Dao.getInstance();
            Card card = dao.findCardById(cardId);
            if (card != null) {
                if (card.getUserId() == user.getId() || user.getType() == User.ADMINISTRATOR) {
                    size = dao.countTransactionsByCardId(cardId);
                    if (pageNumber == null || pageSize == null || pageSize == 0) {
                        transactions = dao.findTransactionsByCardId(cardId);
                    } else {
                        transactions = dao.findTransactionsByCardId(cardId, pageNumber, pageSize);
                    }
                }
                else {
                    session.setAttribute("error", "权限不足");
                }
            } else {
                session.setAttribute("error", "找不到卡号");
            }
        } else {
            session.setAttribute("error", "卡号为空");
        }
    }
    else {
        session.setAttribute("error", "会话过期，请重新登录");
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>交易记录</title>
</head>
<body>
<%
    String error = (String) session.getAttribute("error");
    if (error == null) {
%>

<table border="1">
    <tr>
        <th>交易内容</th>
        <th>金额</th>
    </tr>
    <% for (Transaction transaction : transactions) { %>
    <tr>
        <td>
            <% if (transaction.getFromCardId() == 0) { %>
            <span>充值</span>
            <% } else if (transaction.getToCardId() == 0) { %>
            <span>消费</span>
            <% } else if (transaction.getFromCardId() == cardId) { %>
            <span>转账给卡号 <%= transaction.getToCardId() %></span>
            <% } else { %>
            <span>给卡号 <%= transaction.getToCardId() %> 转账</span>
            <% } %>
        </td>
        <td>
            <span><%= String.format(Locale.getDefault(), "¥ %.2f", transaction.getAmount()) %></span>
        </td>
    </tr>
    <% } %>
    <tr>
        <td colspan="2">
            <span>
                <%
                    if (pageNumber == null) {
                        pageNumber = 1;
                    }
                    if (pageSize == null) {
                        pageSize = size;
                    }

                    int pageNumbers = size / pageSize;
                    if (size % pageSize != 0) {
                        pageNumbers++;
                    }
                %>

                <% if (pageNumber == 1) { %>
                <<
                <% } else { %>
                <a href="transactions.jsp?page_number=1&page_size=<%= pageSize %>"><<</a>
                <% } %>

                &nbsp;
                <% for (int i = 0; i < pageNumbers; i++) { %>
                <% if (i == pageNumber) { %>
                <%= i + 1 %>
                <% } else { %>
                <a href="transactions.jsp?page_number=<%= i + 1 %>&page_size=<%= pageSize %>"><%= i + 1 %></a>
                <% } %>
                &nbsp;
                <% } %>

                <% if (pageNumber == pageNumbers) { %>
                >>
                <% } else { %>
                <a href="transactions.jsp?page_number=<%= pageNumbers %>&page_size=<%= pageSize %>">>></a>
                <% } %>
            </span>
        </td>
    </tr>
</table>
<% } else { %>
<h3 class="error"><%= error %></h3>
<% } %>
</body>
</html>