<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 3:35 下午
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
    <li><a href="/campus_card_war_exploded/Teacher/renyuanxinxizengshangaicha.jsp">单位人员管理</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/shuakaxiaofei.jsp" class="active">收费管理</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/liushuichaxun.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="/campus_card_war_exploded/Teacher/shuakaxiaofei.jsp" class="active">刷卡消费</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/shezhishangxian.jsp">设置用户消费上限</a></li>
    <li><a href="/campus_card_war_exploded/Teacher/jinzhixiaofei.jsp">禁止异常用户消费</a></li>
</ul>
<div class="leftPanel">
    <form action="" method="post" class="form">
        <label>卡号：</label>
        <input type="text" name="card_id"><br>
        <label>密码：</label>
        <input type="passsword" name="password"><br>
        <label>消费金额：</label>
        <input type="text" name="money"><br>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancel" value="取消">
    </form>
</div>
</body>
</html>