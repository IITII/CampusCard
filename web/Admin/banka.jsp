<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 4:56 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>办卡</title>
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
    <li><a href="${pageContext.request.contextPath}/Admin/banka.jsp" class="active">办卡</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/guashi.jsp">挂失</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/dongjie.jsp">冻结</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/huifu.jsp">恢复</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/chongzhi.jsp">充值</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/buzhufafang.jsp">补助发放</a></li>
</ul>
<div class="leftPanel">
    <form action="" method="post" class="form">
        <label>用户ID：</label>
        <input type="text" name="card_id"><br>
        <label>密码：</label>
        <input type="password" name="password"><br>
        <label>卡类型：</label>
        <select name="card_type">
            <option>1</option>
        </select>
        <input type="submit" name="sure" value="确定">
        &nbsp;&nbsp;
        <input type="reset" name="cancel" value="取消">
    </form>
</div>
</body>
</html>