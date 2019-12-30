<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 2:39 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>挂失</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>
<body>
<div class="background1"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/shuakaxiaofei.jsp">刷卡管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/guashi.jsp" class="active">校园卡管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/chaxunlishiliushui.jsp">查询统计</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Student/guashi.jsp" class="active">挂失</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/xiugaimima.jsp">修改密码</a></li>
</ul>
<div class="leftPanel">
<form action="" method="post" class="form">
    <label>挂失卡号：</label>
    <input type="text" name="card_id"><br>
    <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;密码：</label>
    <input type="password" name="password"><br>
    <input type="submit" name="sure" value="确定">
    &nbsp;&nbsp;
    <input type="reset" name="cancel" value="取消">
</form>
</div>
</body>
</html>