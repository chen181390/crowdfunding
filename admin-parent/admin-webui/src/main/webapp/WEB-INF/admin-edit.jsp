<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include-head.jsp" %>

<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/get/page.html">数据列表</a></li>
                <li class="active">更新</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal"
                         data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <form role="form" action="admin/update.html" method="post">
                        <input type="hidden" name="id" value="${requestScope.admin.id}"/>
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <input type="hidden" name="keyword" value="${param.keyword}">
                        <p style="color: red">${requestScope.exception.message}</p>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录账号</label>
                            <input type="text" class="form-control" id="exampleInputPassword1"
                                   placeholder="请输入登陆账号" name="loginAcct" value="${requestScope.admin.loginAcct}"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword3">用户昵称</label>
                            <input type="text" class="form-control" id="exampleInputPassword3"
                                   placeholder="请输入用户昵称" name="userName" value="${requestScope.admin.userName}"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email" class="form-control" id="exampleInputEmail1" placeholder="请输入邮箱地址"
                                   name="email" value="${requestScope.admin.email}"/>
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i>
                            更新
                        </button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i>
                            重置
                        </button>
                    </form>
                </div>
            </div>
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span
                                    aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                            <h4 class="modal-title" id="myModalLabel">帮助</h4>
                        </div>
                        <div class="modal-body">
                            <div class="bs-callout bs-callout-info">
                                <h4>测试标题1</h4>
                                <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                            </div>
                            <div class="bs-callout bs-callout-info">
                                <h4>测试标题2</h4>
                                <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>