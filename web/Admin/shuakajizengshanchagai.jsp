<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="cn.edu.nchu.stu.data.model.Pos" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.edu.nchu.stu.data.model.Department" %><%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 4:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>对刷卡机增删改查</title>
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
    <li><a href="${pageContext.request.contextPath}/Admin/danweizengshangaicha.jsp">单位管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/shuakajizengshanchagai.jsp" class="active">刷卡机管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/banka.jsp">办卡中心</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/liushuitongji.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Admin/shuakajizengshanchagai.jsp" class="active">对刷卡机信息增删查改</a>
    </li>
    <li><a href="${pageContext.request.contextPath}/Admin/chaxunxiaofeijilu.jsp">查询消费记录</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/piliangdaoruxiaofeijilu.jsp">批量导入消费记录</a></li>
</ul>
<div class="leftPanel" style="padding: 5% 16px 1px">
    <div>
        <script src="../js/form.js"></script>
        <button onclick="removeHidden(1)">增加</button>
        <button onclick="removeHidden(2)">修改</button>
        <button onclick="removeHidden(3)">删除</button>
    </div>

    <% List<Department> departmentList = Dao.getInstance().findAllDepartments();%>

    <br>
    <form action="${pageContext.request.contextPath}/new_pos.do" method="post" class="form">
        <input hidden value="Admin/shuakajizengshanchagai.jsp">
        <table border="1" class="centers" style="table-layout: fixed">
            <tr>
                <th>编号</th>
                <th>使用单位</th>
                <th>放置地点</th>
            </tr>
            <%
                List<Pos> posList = Dao.getInstance().findAllPoses();
                for (Pos pos : posList) { %>
            <tr>
                <td><%= pos.getId() %>
                </td>
                <td><%= pos.getdName()%>
                </td>
                <td><%= pos.getAddress()%>
                </td>
            </tr>
            <%}%>
        </table>
        <div hidden="hidden" id="add">
            <input type="text" name="redirect" value="Admin/shuakajizengshanchagai.jsp" hidden>
            <table border="0" style="width: 200px;height: 40px;margin: auto;">
                <tr>
                    <td style="text-align: right"><label>使用单位名称：</label></td>
                    <td><select name="department_id">
                        <%for (Department department : departmentList) { %>
                        <option value=<%= department.getId() %>><%= department.getName() %>
                        </option>
                        <%}%>
                    </select></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>刷卡机名称：</label></td>
                    <td><input type="text" name="name"></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>放置地点：</label></td>
                    <td><input id="addressAdd" name="position" value=""></td>
                </tr>
            </table>
            <br>
            <button type="submit">提交</button>
        </div>
    </form>

    <div hidden="hidden" id="update">
        <form action="${pageContext.request.contextPath}/change_pos.do" method="post">
            <input type="text" name="redirect" value="Admin/shuakajizengshanchagai.jsp" hidden>
            <input hidden value="Admin/shuakajizengshanchagai.jsp">
            <table border="0" style="width: 200px;height: 40px;margin: auto;">
                <tr>
                    <td style="text-align: right"><label>使用单位名称：</label></td>
                    <td><select name="department_id">
                        <%for (Department department : departmentList) { %>
                        <option value=<%= department.getId() %>><%= department.getName() %>
                        </option>
                        <%}%>
                    </select></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>刷卡机ID：</label></td>
                    <td><select name="id">
                        <%for (Pos pos: posList){%>
                        <option value=<%=pos.getId()%>><%=pos.getId()%></option><%}%>
                    </select></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>刷卡机名字：</label></td>
                    <td><input type="text" name="name" value=""></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label>放置地点：</label></td>
                    <td><input id="addressUpdate" name="position" value=""></td>
                </tr>
            </table>
            <br>
            <button type="submit">提交</button>
        </form>
    </div>
    <div hidden="hidden" id="remove">
        <form action="${pageContext.request.contextPath}/delete_pos.do" method="post">
            <input type="text" name="redirect" value="Admin/shuakajizengshanchagai.jsp" hidden>
            <input hidden value="Admin/shuakajizengshanchagai.jsp">
            <label>
                刷卡机ID：<select name="id">
                <%
                    for (Pos pos1: posList){
                %>
                <option value=<%=pos1.getId()%>><%=pos1.getId()%></option>
                <%}%>
            </select>
            </label>
            <button type="submit">提交</button>
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