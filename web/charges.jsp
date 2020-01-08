<%--
  Created by IntelliJ IDEA.
  User: 江智康-HP
  Date: 2020/1/5
  Time: 21:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="cn.edu.nchu.stu.data.model.User" %>
<%@ page import="cn.edu.nchu.stu.data.model.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="cn.edu.nchu.stu.data.model.Pos" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    String redirectUrl = request.getParameter("redirect");
    redirectUrl = redirectUrl == null ? "index.jsp" : redirectUrl;
    List<Transaction> transactions = null;
    int size = 0;
    Long postId = null;
    Integer pageNumber = null, pageSize = null;
    if (user != null) {
        try {
            postId = Long.parseLong(request.getParameter("post_id"));
        } catch (NullPointerException | NumberFormatException ignore) {
        }
        try {
            pageNumber = Integer.parseInt(request.getParameter("page_number"));
        } catch (NullPointerException | NumberFormatException ignore) {
        }
        try {
            pageSize = Integer.parseInt(request.getParameter("page_size"));
        } catch (NullPointerException | NumberFormatException ignore) {
        }

        Dao dao = Dao.getInstance();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        if (postId != null) {
            Pos pos = dao.findPosById(postId.intValue());
            if (pos != null) {
                if (pos.getDid() == Dao.getInstance().findDepartmentIdByUsername(user.getUsername())) {
                    transactions = dao.findTransactionsByPosId(pos.getId(), pageNumber, pageSize);
                    size = (int) dao.countTransactionsByPosId(pos.getId());
                }
                else {
                    session.setAttribute("error", "权限不足");
                    response.sendRedirect(redirectUrl);
                }
            } else {
                session.setAttribute("error", "找不到卡号");
                response.sendRedirect(redirectUrl);
            }
        } else {
            if (user.getType() == User.STAFF) {
                transactions = dao.findTransactionsByPosesOfDepartmentOfUser(user.getUsername(),pageNumber,pageSize);
                size = (int) dao.countTransactionsByPosesOfDepartmentOfUser(user.getUsername());
            }
            else {
                session.setAttribute("error", "卡号为空");
                response.sendRedirect(redirectUrl);
                return;
            }
        }
    }
    else {
        session.setAttribute("error", "会话过期，请重新登录");
        response.sendRedirect("login.jsp");
        return;
    }
    assert transactions != null;
%>

<html>
<head>
    <title>交易记录</title>
    <link rel="stylesheet" href="css/image.css">
    <script src="js/form.js"></script>
    <style>
        div {
            text-align: center;
            align-content: center;

        }
        td, th, .centers {
            text-align: center;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            width: 200px;
            height: 40px;
            margin: auto;
        }
    </style>
</head>
<body>
<div class="background2"></div>
<%
    String error = (String) session.getAttribute("error");
    if (error == null) {
%>
<div >
    <table border="1" class="centers" style="table-layout: fixed">
        <tr>
            <th>交易内容</th>
            <th>交易 POS 机名称</th>
            <th>金额</th>
            <th>交易时间</th>
        </tr>
        <% if (transactions.size() != 0) { %>
        <% for (Transaction transaction : transactions) { %>
        <tr>
            <td>
                <% if (transaction.getFromCardId() == 0) { %>
                <span>给卡号 <%= String.format(Locale.getDefault(), "%06d", transaction.getToCardId()) %> 充值</span>
                <% } else if (transaction.getToCardId() == 0) { %>
                <span>卡号 <%= String.format(Locale.getDefault(), "%06d", transaction.getFromCardId()) %> 消费</span>
                <% } else { %>
                <span><%= String.format("%06d 转账给卡号 %06d", transaction.getFromCardId(), transaction.getToCardId()) %></span>
                <% } %>
            </td>
            <td>
                <span><%= transaction.getPosName() %></span>
            </td>
            <td>
                <span><%= String.format(Locale.getDefault(), "¥ %.2f", transaction.getAmount()) %></span>
            </td>
            <td>
                <span><%= transaction.getCreateAt().toString() %></span>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="4">暂无交易记录</td>
        </tr>
        <% } %>
        <tr>
            <td colspan="4">
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
                <a href="transactions.jsp?page_number=1&page_size=<%= pageSize %>&card_id=<%= postId %>"><<</a>
                <% } %>

                &nbsp;
                <% for (int i = 1; i <= pageNumbers; i++) { %>
                <% if (i == pageNumber) { %>
                <%= i %>
                <% } else { %>
                <a href="transactions.jsp?page_number=<%= i %>&page_size=<%= pageSize %>&card_id=<%= postId %>"><%= i %></a>
                <% } %>
                &nbsp;
                <% } %>

                <% if (pageNumber == pageNumbers) { %>
                >>
                <% } else { %>
                <a href="transactions.jsp?page_number=<%= pageNumbers %>&page_size=<%= pageSize %>&card_id=<%= postId %>">>></a>
                <% } %>
            </span>
            </td>
        </tr>
    </table>
    <br><br>
    <label hidden>
        <input type="text" id="abs" value="${pageContext.request.contextPath}">
        <input type="text" id="type" value=<%=user.getType()%>>
    </label>
    <script src="js/form.js"></script>
    <button onclick="back()" id="back">确认</button>

</div>
<% } else { %>
<h3 class="error"><%= error %></h3>
<% } %>
</body>
</html>