<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018-6-14
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/layui-v2.3.0/layui/css/layui.css"/>
    <script type="text/javascript" src="/layui-v2.3.0/layui/layui.js"></script>
</head>
<body>
<table id="demo" lay-filter="test"></table>
<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#demo',
            height: 315,
            url: 'http://www.layui.com/demo/table/user',
            page: true,
            cols: [[
                {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left'}
                , {field: 'username', title: '用户名', width: 80}
                , {field: 'sex', title: '性别', width: 80, sort: true}
                , {field: 'city', title: '城市', width: 80}
                , {field: 'sign', title: '签名', width: 177}
                , {field: 'experience', title: '积分', width: 80, sort: true}
                , {field: 'score', title: '评分', width: 80, sort: true}
                , {field: 'classify', title: '职业', width: 80}
                , {field: 'wealth', title: '财富', width: 135, sort: true}
            ]
            ]
        });
    })
</script>
</body>
</html>
