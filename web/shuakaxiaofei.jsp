<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 2:27 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>刷卡管理</title>
    <link rel="stylesheet" href="css/image.css">
    <link rel="stylesheet" href="css/navBar.css">
    <link rel="stylesheet" href="css/tableCenter.css">
</head>
<body>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="shuakaxiaofei.jsp" class="active">刷卡管理</a></li>
    <li><a href="guashi.jsp">校园卡管理</a></li>
    <li><a href="chaxunlishiliushui.jsp">查询统计</a></li>
    <li class="rightLi" style="float: right"><a href="">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="shuakaxiaofei.jsp" class="active">刷卡消费</a></li>
</ul>
<div class="leftPanel">
    <form action="" method="post" class="form">
        <label>消费金额：</label>
        <input type="input" name="money">
        <br>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancle" value="取消">
    </form>
</div>
</body>
</html>
