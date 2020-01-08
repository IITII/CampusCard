<%@ page import="cn.edu.nchu.stu.data.Dao" %>
<%@ page import="cn.edu.nchu.stu.data.model.Pos" %>
<%@ page import="cn.edu.nchu.stu.data.model.User" %>
<%@ page import="cn.edu.nchu.stu.data.model.Card" %><%--
  Created by IntelliJ IDEA.
  User: zxf
  Date: 2019/12/29
  Time: 3:35 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>刷卡消费</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
</head>

<body>
<div class="background"></div>
<ul class="horizontal gray">
    <li><a href="">校园一卡通</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/renyuanxinxizengshangaicha.jsp">单位人员管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/shuakaxiaofei.jsp" class="active">收费管理</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/liushuichaxun.jsp">统计报表</a></li>
    <li class="rightLi" style="float: right"><a href="${pageContext.request.contextPath}/logout.do">注销</a></li>
</ul>
<ul class="vertical">
    <li><a href="${pageContext.request.contextPath}/Teacher/shuakaxiaofei.jsp" class="active">刷卡消费</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/shezhishangxian.jsp">设置用户消费上限</a></li>
    <li><a href="${pageContext.request.contextPath}/Teacher/jinzhixiaofei.jsp">禁止异常用户消费</a></li>
</ul>
<div class="leftPanel">
    <form action="${pageContext.request.contextPath}/transfer.do" method="post" class="form">
        <input name="redirect" value="Teacher/shuakaxiaofei.jsp" hidden />
        <input name="to_card_id" value="0" hidden />
        <table border="0" style="width: 200px;height: 40px;margin: auto;">
            <tr>
                <td style="text-align: right"><label>卡号：</label></td>
                <td><select name="from_card_id">
                    <% for (Card card : Dao.getInstance().findCardsByUserId(((User)session.getAttribute("user")).getId())){ %>
                    <option value="<%=String.format("%06d",card.getId())%>"> <%=String.format("%06d",card.getId())%>
                    </option>
                    <%}%>
                </select></td>
            </tr>
            <tr>
                <td style="text-align: right"><label>消费金额：</label></td>
                <td><input type="number" name="amount"></td>
            </tr>
            <tr>
                <td style="text-align: right"><label>pos机：</label></td>
                <td><select name="pos_id">
                    <% for (Pos pos : Dao.getInstance().findAllPoses()) { %>
                    <option value="<%= pos.getId()%>"><%=pos.getName()%></option>
                    <%}%>
                </select></td>
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