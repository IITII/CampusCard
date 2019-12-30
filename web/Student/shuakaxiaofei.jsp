<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>刷卡消费</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>
<body>
<div class="background1"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/shuakaxiaofei.jsp" class="active">刷卡管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/guashi.jsp">校园卡管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/chaxunlishiliushui.jsp">查询统计</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Student/shuakaxiaofei.jsp" class="active">刷卡消费</a></li>
</ul>
<div class="leftPanel">
    <form action="${pageContext.request.contextPath}/transfer.do" method="post" class="form">
        <input type="text" name="redirect" value="Student/shuakaxiaofei.jsp" hidden>
        <input type="text" name="to_card_id" value="0" hidden>
        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡号：</label>
        <input type="text" name="from_card_id"><br>
        <label>消费金额：</label>
        <input type="number" name="amount"><br>
        <br>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancel" value="取消">
    </form>
    <br><% String error = (String)session.getAttribute("error");
        if (error!=null){
    %>
    <h3><%=error %></h3>
    <%}%>
</div>
</body>
</html>
