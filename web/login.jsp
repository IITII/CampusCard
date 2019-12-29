<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>登录</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login.do" method="post">
    <table>
        <tr>
            <td><label for="username-input">卡号：</label></td>
            <td><input id="username-input" name="username" placeholder="请输入用户名"/></td>
        </tr>
        <tr>
            <td><label for="password-input">密码：</label></td>
            <td><input id="password-input" name="password" type="password" placeholder="请输入密码"/></td>
        </tr>
        <tr>
            <td><label for="password-input">用户类型：</label></td>
            <td>
            <select>
                <option>学生</option>
                <option>管理员</option>
                <option>老师</option>
            </select>
            </td>
        </tr>
        <tr>
            <td colspan="2"><span class="error">${sessionScope.error}</span></td>
        </tr>
        <tr>
            <td colspan="2"><button type="submit">登录</button></td>
        </tr>
    </table>
</form>
</body>
</html>
