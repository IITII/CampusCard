<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 3:36 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>报表生成</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>
<body>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/renyuanxinxizengshangaicha.jsp" class="active">单位人员管理</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/shuakaxiaofei.jsp">收费管理</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/liushuichaxun.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="/campus_card_war_exploded/Teacher/renyuanxinxizengshangaicha.jsp" class="active">人员信息增删改查</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/renyuanpaiban.jsp">人员排班</a></li>
</ul>
<div class="leftPanel">
    <form action="" method="post" class="form">
        <label>卡号：</label>
        <input type="input" name="card_id"><br>
        <label>密码：</label>
        <input type="password" name="password"><br>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancle" value="取消">
    </form>
</div>
</body>
</html>