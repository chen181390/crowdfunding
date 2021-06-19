<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        initPagination();
        $("#keyword").attr("value", ${param.keyword});
    });

    function initPagination() {
        let totalRecord = ${requestScope.pageInfo.total};
        let properties = {
            num_edge_entries: 3,
            num_display_entries: 5,
            callback: pageSelectCallback,
            items_per_page: ${requestScope.pageInfo.pageSize},
            current_page: ${requestScope.pageInfo.pageNum - 1},  // pagination的pageIndex从0开始，而pageInfo.pageNum从1开始
            prev_text: "上一页",
            next_text: "下一页"
        };
        $("#Pagination").pagination(totalRecord, properties);
    }

    // 点击回调，实现页面跳转
    function pageSelectCallback(pageIndex) {
        let pageNum = ++pageIndex;
        window.location.href = "admin/get/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";
        // 由于每一个页码都是超链接，所以函数最后需要取消超链接的默认行为
        return false;
    }
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
                    <form class="form-inline" role="form" style="float:left;" action="admin/get/page.html" method="post">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件" name="keyword" id="keyword">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i>
                            查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <a type="button" class="btn btn-primary" style="float:right" href="admin/to/add/page.html"><i class="glyphicon glyphicon-plus"></i>
                        新增
                    </a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                            <tr>
                                <td colspan="6">抱歉！没有查询到您要的数据！</td>
                            </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                            <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                            <tr>
                                <td>${myStatus.count}</td>
                                <td><input type="checkbox"></td>
                                <td>${admin.loginAcct}</td>
                                <td>${admin.userName}</td>
                                <td>${admin.email}</td>
                                <td>
                                    <a type="button" class="btn btn-success btn-xs" href="assign/to/assign/role/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"><i
                                            class=" glyphicon glyphicon-check" ></i></a>
                                    <a type="button" class="btn btn-primary btn-xs" href="admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"><i
                                            class=" glyphicon glyphicon-pencil"></i></a>
                                    <a type="button" class="btn btn-danger btn-xs"  href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html"><i
                                            class=" glyphicon glyphicon-remove"></i></a>
                                </td>
                            </tr>
                            </c:forEach>
                            </c:if>
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
</body>

</html>