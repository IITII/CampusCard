<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="cn.edu.nchu.stu.data.model.Pos" %>
<%@ page import="cn.edu.nchu.stu.data.model.Card" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 4:57 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>补助发放</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>

<body>
<div class="background2"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/danweizengshangaicha.jsp">单位管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/shuakajizengshanchagai.jsp">刷卡机管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/banka.jsp" class="active">办卡中心</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/liushuitongji.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Admin/banka.jsp">办卡</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/guashi.jsp">挂失</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/dongjie.jsp">冻结</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/huifu.jsp">恢复</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/chongzhi.jsp">充值</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/buzhufafang.jsp" class="active">补助发放</a></li>
</ul>
<div class="leftPanel">
    <% List<Card> cardList = Dao.getInstance().findAllCards(); %>
    <form action="${pageContext.request.contextPath}/transfer.do" method="post" class="form">
        <input name="redirect" value="Admin/buzhufafang.jsp" hidden>
        <input name="from_card_id" value="0" hidden>
        <table border="0" style="width: 200px;height: 40px;margin: auto;">
            <tr>
                <td style="text-align: right"><label>卡号：</label></td>
                <td><select name="to_card_id">
                    <% for (Card card : cardList){ %>
                    <option value="<%= String.format("%06d",card.getId()) %>"><%= String.format("%06d",card.getId()) %>
                    </option>
                    <% } %>
                </select></td>
            </tr>
            <tr>
                <td style="text-align: right"><label>金额：</label></td>
                <td><input type="number" name="amount"></td>
            </tr>
            <tr>
                <td style="text-align: right"><label>pos机：</label></td>
                <td><select name="pos_id">
                    <% for (Pos pos : Dao.getInstance().findAllPoses()) { %>
                    <option value="<%= pos.getId()%>"><%=pos.getName()%></option>
                    <%}%>
                </select></td>
            </tr>
        </table>
        <br>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancel" value="取消">
    </form>
    <br><% String error = (String)session.getAttribute("error");
    if (error!=null){%>
    <h3><%=error %></h3>
    <%}%>
    <% session.setAttribute("error",null); %>
</div>
</body>

</html>