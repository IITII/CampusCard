<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("user") == null) {
        session.setAttribute("error", "会话过期，请重新登录");
        response.sendRedirect("login.jsp");
    }
%><html>
<head>
    <title>修改密码</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/change_password.do" method="post">
        <table>
            <tr>
                <td><label for="old-password-input"></label></td>
                <td><input id="old-password-input" name="old_password" type="password"></td>
            </tr>
            <tr>
                <td><label for="repeat-password-input"></label></td>
                <td><input id="repeat-password-input" name="repeat_password" type="password"></td>
            </tr>
            <tr>
                <td><label for="new-password-input"></label></td>
                <td><input id="new-password-input" name="new_password" type="password"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <span class="error">${sessionScope.error}</span>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button type="submit">修改密码</button>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
