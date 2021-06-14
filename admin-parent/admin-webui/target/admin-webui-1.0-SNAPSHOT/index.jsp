<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                url: "admin/do/login.html",
                type: "post",
                data: {
                    loginAcct: "awei",
                    userPswd: "123123"
                }
            });

            $("#btn1").click(function () {
                $.ajax({
                    url: "send/array.html",
                    type: "post",
                    data: {
                        array: [5, 8, 12]
                    },
                    dataType: "text",
                    success: function (response) {
                        alert(response);
                    },
                    error: function (response) {
                        alert(response);
                    }
                });
            });
            $("#btn2").click(function () {
                $.ajax({
                    url: "send/array2.html",
                    type: "post",
                    data: JSON.stringify([5, 8, 12]),
                    dataType: "text",
                    contentType: "application/json;charset=UTF-8",
                    success: function (response) {
                        alert(response.toString());
                    },
                    error: function (response) {
                        alert(response);
                    }
                })
            });
            $("#btn3").click(function (){
                layer.msg("我是你哥哥");
            });
            $("#btnToAdmin").click(function () {
                window.open("admin/to/login/page.html");
            });
            $("#btnAddAdmin").click(function () {
                $.ajax({
                   url: "insert/example.html",
                    type: "get",
                    dataType: "text",
                    success: function (response) {
                       layer.msg("成功新增" + response + "条数据");
                    },
                    error: function () {
                       layer.error("新增失败");
                    }
                });
            });

            $(window).unload(() => {
                $.get("admin/do/logout.html");
            });
        });
    </script>
</head>
<body>
<a href="test/ssm.html">测试SSM整合环境</a>
<br/>
<button id="btn1">send [5,8,12]</button>
<br/>
<button id="btn2">send2 [5,18,12]</button>
<br/>
<button id="btn3">点我弹窗</button>
<hr/>
<button id="btnToAdmin">进入后台管理页面</button>
<br/>
<button id="btnAddAdmin">插入Admin数据</button>
</body>
</html>
