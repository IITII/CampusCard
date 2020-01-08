<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="cn.edu.nchu.stu.data.model.Card" %>
<%@ page import="cn.edu.nchu.stu.data.model.User" %><%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 2:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询历史流水</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>
<body>
<div class="background1"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/shuakaxiaofei.jsp">刷卡管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/guashi.jsp">校园卡管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/chaxunlishiliushui.jsp" class="active">查询统计</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Student/chaxunlishiliushui.jsp" class="active">查询历史流水</a></li>
</ul>
<div class="leftPanel">
    <form action="${pageContext.request.contextPath}/transactions.jsp" method="get" class="form">
        <input type="text" name="redirect" value="Student/chaxunlishiliushui.jsp" hidden>
        <label>卡号：</label>
        <select name="card_id">
            <% for (Card card : Dao.getInstance().findCardsByUserId(((User)session.getAttribute("user")).getId())){ %>
            <option value="<%=String.format("%06d",card.getId())%>"> <%=String.format("%06d",card.getId())%> </option>
            <%}%>
        </select><br><br>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancel" value="取消">
    </form>
    <br><% String error = (String)session.getAttribute("error");
    if (error!=null){
%>
    <h3><%=error %></h3>
    <%}%>
    <% session.setAttribute("error",null); %>
</div>
</body>
</html>