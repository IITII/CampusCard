<%@ page import="cn.edu.nchu.stu.data.model.Card" %>
<%@ page import="cn.edu.nchu.stu.data.Dao" %><%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 4:56 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询消费记录</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>
<body>
<div class="background2"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/danweizengshangaicha.jsp">单位管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/shuakajizengshanchagai.jsp" class="active">刷卡机管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/banka.jsp">办卡中心</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/liushuitongji.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Admin/shuakajizengshanchagai.jsp">对刷卡机信息增删查改</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/chaxunxiaofeijilu.jsp" class="active">查询消费记录</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/piliangdaoruxiaofeijilu.jsp">批量导入消费记录</a></li>
</ul>
<div class="leftPanel">
    <form action="${pageContext.request.contextPath}/transactions.jsp" method="get" class="form">
        <input name="redirect" value="Admin/chaxunxiaofeijilu.jsp" hidden>
        <label>卡号：</label>
        <select name="card_id">
            <% for (Card card : Dao.getInstance().findAllCards()){ %>
            <option value="<%=String.format("%06d",card.getId())%>"> <%=String.format("%06d",card.getId())%> </option>
            <%}%>
            <option value=""> 所有卡 </option>
        </select><br><br>
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