<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 2:48 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
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
    <li class="rightLi" style="float: right"><a href="">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Student/guashi.jsp">挂失</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/chongzhi.jsp">充值</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/xiugaimima.jsp" class="active">修改密码</a></li>
</ul>
<div class="leftPanel">
    <form action="" method="post" class="form">
        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡号：</label>
        <input type="text" name="card_id"><br>
        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原密码：</label>
        <input type="password" name="old_password"><br>
        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新密码：</label>
        <input type="password" name="new_password1"><br>
        <label>重复新密码：</label>
        <input type="password" name="new_password2"><br>
        <br>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancel" value="取消">
    </form>
</div>
</body>
</html>