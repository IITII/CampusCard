<%@ page import="cn.edu.nchu.stu.data.model.Department" %>
<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 4:49 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>对单位信息增删改查</title>
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
<div class="background2"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/danweizengshangaicha.jsp" class="active">单位管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/shuakajizengshanchagai.jsp">刷卡机管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/banka.jsp">办卡中心</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/liushuitongji.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Admin/danweizengshangaicha.jsp" class="active">对单位信息增删改查</a>
    </li>
</ul>
<div class="leftPanel">
    <div>
        <script src="../js/form.js"></script>
        <button onclick="removeHidden(1)">增加</button>
        <button onclick="removeHidden(2)">修改</button>
        <button onclick="removeHidden(3)">删除</button>
    </div>
    <br>
    <div>
        <table border="1" class="centers" style="table-layout: fixed">
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>联系方式</th>
            </tr>
            <%
                List<Department> posList = Dao.getInstance().findAllDepartments();
                for (Department pos : posList) { %>
            <tr>
                <td><%= pos.getId() %>
                </td>
                <td><%= pos.getName()%>
                </td>
                <td><%= pos.getDLink()%>
                </td>
            </tr>
            <%}%>
        </table>
    </div><br>
    <%
        //for ()
    %>
    <%
        List<Department> departmentList = Dao.getInstance().findAllDepartments();
    %>
    <div id="add" hidden="hidden">
        <form action="${pageContext.request.contextPath}/new_department.do" method="post" class="form">
            <input hidden name="redirect" value="Admin/danweizengshangaicha.jsp" />
            <table border="0" style="width: 200px;height: 40px;margin: auto;">
                <tr>
                    <td style="text-align: right"><label>单位名称：</label></td>
                    <td><input type="text" name="name" placeholder="单位名称"></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>单位联系方式：</label></td>
                    <td><input type="text" name="contact" placeholder="单位联系方式"></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>单位类型：</label></td>
                    <td><select name="type">
                        <%for (Department department : departmentList) {  %>
                        <option value=<%= department.getId() %>><%= department.getDTypeName() %>
                        </option>
                        <%}%>
                    </select></td>
                </tr>
            </table><br>
            <input type="submit">
        </form>
    </div>
    <div id="update" hidden="hidden">
        <form action="${pageContext.request.contextPath}/change_department.do" method="post" class="form">
            <input hidden name="redirect" value="Admin/danweizengshangaicha.jsp" />
            <table border="0" style="width: 200px;height: 40px;margin: auto;">
                <tr>
                    <td style="text-align: right"><label>单位ID：</label></td>
                    <td><select name="id">
                        <%for (Department department : departmentList) {  %>
                        <option value=<%= department.getId() %>><%= department.getId() %>
                        </option>
                        <%}%>
                    </select></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>单位名称：</label></td>
                    <td><input type="text" name="name" placeholder="单位名称" value=""></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>单位联系方式：</label></td>
                    <td><input type="text" name="contact" placeholder="单位联系方式" value=""></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>单位类型：</label></td>
                    <td><select name="type">
                        <%for (Department department : departmentList) {  %>
                        <option value=<%= department.getId() %>><%= department.getDTypeName() %>
                        </option>
                        <%}%>
                    </select></td>
                </tr>
            </table>
            <br>
            <input type="submit">
        </form>
    </div>
    <div id="remove" hidden="hidden">
        <form action="/delete_department.do" method="post" class="form">
            <input hidden name="redirect" value="Admin/danweizengshangaicha.jsp" />
            <label>
                欲删除单位ID：<select name="id">
                <%for (Department department : departmentList) {  %>
                <option value=<%= department.getId() %>><%= department.getId() %>
                </option>
                <%}%>
            </select>
            </label>
            <input type="submit">
        </form>
    </div>

    <br><% String error = (String)session.getAttribute("error");
    if (error!=null){
%>
    <h3><%=error %></h3>
    <%}%>
    <% session.setAttribute("error",null);
    %>
</div>
</body>

</html>