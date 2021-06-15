<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="js/my-role.js" charset="UTF-8"></script>
<script type="text/javascript" charset="UTF-8">
    $(() => {
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        generatePage();

        $("#searchBtn").click(() => {
            window.keyword = $("#keywordInput").val();
            window.pageNum = 1;
            generatePage();
        });
    });
</script>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件"
                                       id="keywordInput">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" id="searchBtn"><i
                                class="glyphicon glyphicon-search"></i>
                            查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;" id="showAddModalBtn"><i
                            class="glyphicon glyphicon-plus"></i>
                        新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
<%--                            <tr>--%>
<%--                                <td>1</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>PM - 项目经理</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i--%>
<%--                                            class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i--%>
<%--                                            class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i--%>
<%--                                            class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination">
                                    </div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>
</body>

</html>