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
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Student/guashi.jsp">挂失</a></li>
    <li><a href="${pageContext.request.contextPath}/Student/xiugaimima.jsp" class="active">修改密码</a></li>
</ul>
<div class="leftPanel">
    <form action="${pageContext.request.contextPath}/change_password.do" method="post" class="form">
        <input name="redirect" value="Student/xiugaimima.jsp" hidden />
        <table border="0" style="width: 200px;height: 40px;margin: auto;">
            <tr>
                <td style="text-align: right"><label>原密码：</label></td>
                <td><input type="password" name="old_password"><br></td>
            </tr>
            <tr>
                <td style="text-align: right"><label>新密码：</label></td>
                <td><input type="password" name="new_password"><br></td>
            </tr>
            <tr>
                <td style="text-align: right"><label>重复新密码：</label></td>
                <td><input type="password" name="repeat_password"><br></td>
            </tr>
        </table>
        <br>
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