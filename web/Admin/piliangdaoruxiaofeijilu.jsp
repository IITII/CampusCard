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
    <title>批量导入消费记录</title>
    <link rel="stylesheet" href="../css/image.css">
    <link rel="stylesheet" href="../css/navBar.css">
    <link rel="stylesheet" href="../css/tableCenter.css">
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
    <li><a href="${pageContext.request.contextPath}/Admin/shuakajizengshanchagai.jsp">对刷卡机信息增删查改</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/chaxunxiaofeijilu.jsp">查询消费记录</a></li>
    <li><a href="${pageContext.request.contextPath}/Admin/piliangdaoruxiaofeijilu.jsp" class="active">批量导入消费记录</a></li>
</ul>
<div class="leftPanel">
    <form action="${pageContext.request.contextPath}/import_csv.do" method="post" class="form" enctype="multipart/form-data">
        <input type="text" name="redirect" value="Admin/piliangdaoruxiaofeijilu.jsp" hidden>
        <input type="file" name="csv" id="fileField" size="28" /><br><br>
        <input type="submit" name="submit" value="上传" /><br>
    </form>
    <br><% String error = (String)session.getAttribute("error");
    if (error!=null){%>
    <h3><%=error %></h3>
    <%}%>
    <% session.setAttribute("error",null); %>
</div>
</body>
</html>