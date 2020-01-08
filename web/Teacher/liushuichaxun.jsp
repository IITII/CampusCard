<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="cn.edu.nchu.stu.data.model.Pos" %>
<%@ page import="cn.edu.nchu.stu.data.model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 3:37 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>流水查询</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>
<body>
<div class="background"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/renyuanxinxizengshangaicha.jsp">单位人员管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/shuakaxiaofei.jsp">收费管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/liushuichaxun.jsp" class="active">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Teacher/liushuichaxun.jsp" class="active">流水查询</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/baobiaoshengcheng.jsp">报表生成</a></li>
</ul>
<div class="leftPanel">
    <form action="${pageContext.request.contextPath}/charges.jsp" method="post" class="form">
        <input type="text" name="redirect" value="Teacher/liushuichaxun.jsp" hidden>
        <label>刷卡机：</label>
        <select name="post_id">
            <% for (Pos pos : Dao.getInstance().findPosesOfDepartmentOfUserByUserId( ((User)session.getAttribute("user")).getId() )){ %>
            <option value="<%=pos.getId()%>"> <%=pos.getAddress()%> </option>
            <%}%>
            <option value="">本单位所有刷卡机</option>
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