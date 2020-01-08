<%@ page import="cn.edu.nchu.stu.data.model.User" %>
<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="java.util.List" %>
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
    <title>人员信息增删改查</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
    <style>
        div {
            text-align: center;
            align-content: center;
        }

        td,
        th,
        .centers {
            text-align: center;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            width: 200px;
            height: 40px;
            margin: auto;
        }
    </style>
</head>

<body>
<div class="background"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/renyuanxinxizengshangaicha.jsp"
           class="active">单位人员管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/shuakaxiaofei.jsp">收费管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/liushuichaxun.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Teacher/renyuanxinxizengshangaicha.jsp"
           class="active">人员信息增删改查</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/renyuanpaiban.jsp">人员排班</a></li>

</ul>
<div class="leftPanel">
    <div>
        <script src="../js/form.js"></script>
        <button onclick="removeHidden(1)">增加</button>
        <button onclick="removeHidden(2)">修改</button>
        <button onclick="removeHidden(3)">删除</button>
    </div>
    <br>
    <form action="" method="post" class="form">
        <table border="1" class="centers" style="table-layout: fixed">
            <tr>
                <th>用户ID</th>
                <th>用户姓名</th>
                <th>用户职称</th>
                <th>用户性别</th>
                <th>联系方式</th>
            </tr>
            <%
                User user = (User) session.getAttribute("user");
                List<User> userList = Dao.getInstance()
                        .findAllStaffByDptID(
                                (Dao.getInstance()
                                        .findDepartmentIdByUsername(user.getUsername())
                                ));
                for (User user1 : userList) { %>
            <tr>
                <td><%= user1.getId() %>
                </td>
                <td><%= user1.getUsername()%>
                </td>
                <td><%= user1.getJobTitle()%>
                </td>
                <td><%= user1.getGender()%>
                </td>
                <td><%= user1.getTelNumber()%>
                </td>
            </tr>
            <%}%>
        </table>
    </form>
    <div id="add" hidden="hidden">
        <form action="${pageContext.request.contextPath}/new_user.do" method="post" class="form">
            <input hidden name="redirect" value="Teacher/renyuanxinxizengshangaicha.jsp" />
            <label>
                用户ID：<select name="user_id">
                <%
                    List<User> userList1 = Dao.getInstance().findAllUser();
                    for (User user1 : userList1) {
                %>
                <option value=<%= user1.getId() %>><%= user1.getId() %>
                </option>
                <%}%>
            </select>
            </label>
            <br><br>
            <input type="submit">
        </form>
    </div>
    <div id="update" hidden="hidden">
        <form action="${pageContext.request.contextPath}/set_staff_info.do" method="post" class="form">
            <input hidden name="redirect" value="Teacher/renyuanxinxizengshangaicha.jsp" />
            <table border="0" style="width: 200px;height: 40px;margin: auto;">
                <tr>
                    <td style="text-align: right"><label>用户ID：</label></td>
                    <td><select name="user_id">
                        <% for (User u : userList) { %>
                        <option value=<%=u.getId()%>><%=u.getId()%></option>
                        <%}%>
                    </select></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>职称：</label></td>
                    <td><input type="text" name="job_title" value=""></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>联系方式：</label></td>
                    <td><input type="text" name="tel_number" value=""></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>性别：</label></td>
                    <td><select name="gender">
                        <option value="male">male</option>
                        <option value="female">female</option>
                    </select></td>
                </tr>
            </table>
            <br>
            <input type="submit">
        </form>
    </div>
    <div id="remove" hidden="hidden">
        <form action="${pageContext.request.contextPath}/delete_user.do" method="post" class="form">
            <input hidden name="redirect" value="Teacher/renyuanxinxizengshangaicha.jsp" />
            <label>
                用户ID：<select name="user_id">
                <% for (User u : userList) { %>
                <option value=<%=u.getId()%>><%=u.getId()%></option>
                <%}%>
            </select>
            </label>
            <br><br>
            <input type="submit">
        </form>
    </div>
    <% String error = (String)session.getAttribute("error");
        if (error!=null){
    %>
    <h3><%=error %></h3>
    <%}%>
    <% session.setAttribute("error",null); %>
</div>
</body>

</html>