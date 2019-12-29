<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
<link rel="stylesheet" href="css/navBar.css">
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="" class="active">刷卡管路</a></li>
    <li><a href="">校园卡管理</a></li>
    <li><a href="">查询统计</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="javascript:void(0)" class="active">刷卡管理</a></li>
    <li><a href="javascript:void(0)">新闻</a></li>
    <li><a href="javascript:void(0)">联系</a></li>
    <li><a href="javascript:void(0)">关于</a></li>
</ul>
</body>
</html>
