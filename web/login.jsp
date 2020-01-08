<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="css/image.css">
    <link rel="stylesheet" href="css/navBar.css">
    <link rel="stylesheet" href="css/tableCenter.css">
</head>

<body>
<div class="background"></div>
<div class="login">
    <form action="${pageContext.request.contextPath}/login.do" method="post">
        <h2>校园一卡通</h2>
        <br>
        <table border="0" style="width: 200px;height: 40px;margin: auto;">
            <tr>
                <td style="text-align: right"><label>用户名：</label></td>
                <td><input type="text" value="" name="username" placeholder="请输入用户名："></td>
            </tr>
            <tr>
                <td style="text-align: right"><label>密码：</label></td>
                <td><input type="password" value="" name="password" placeholder="请输入密码:"></td>
            </tr>
        </table>
        <br>
        <button type="submit">登录</button>
    </form>
    <br><% String error = (String)session.getAttribute("error");
    if (error!=null){%>
    <h3><%=error %></h3>
    <%}%>
    <% session.setAttribute("error",null); %>
</div>
</body>
</html>