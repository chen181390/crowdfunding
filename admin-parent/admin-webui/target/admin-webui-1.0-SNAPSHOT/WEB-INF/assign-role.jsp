<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="text/javascript" src="js/assign.js"></script>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url: "assign/get/whole/assign.json",
            type: "post",
            data: {
                adminId: ${param.adminId}
            },
            dataType: "json",
            success: function (response) {
                if (response.result !== "SUCCESS") {
                    layer.msg("获取数据失败！ " + response.message);
                    return;
                }
                let data = JSON.parse(response.data);
                initList(data);
            },
            error: function (response) {
                layer.msg(response.status + " " + response.statusText);
            }
        });
        $("#btnLeft").click(function () {
            let selectedIds = [];
            $("#unAssignedRoleSelect option:selected").each(function (index, element) {
                selectedIds.push($(element).attr("value"));
            });
            selectedIds.forEach((val) => {
                let temp = window.unAssignedRoleMap[val];
                temp.modifyNum++;
                window.unAssignedRoleMap[val] = null;
                window.assignedRoleMap[val] = temp;
            });
            refreshList();
        });
        $("#btnRight").click(function () {
            let selectedIds = [];
            $("#assignedRoleSelect option:selected").each(function (index, element) {
                selectedIds.push($(element).attr("value"));
            });
            selectedIds.forEach((val) => {
                let temp = window.assignedRoleMap[val];
                temp.modifyNum++;
                window.assignedRoleMap[val] = null;
                window.unAssignedRoleMap[val] = temp;
            });
            refreshList();
        });
        $("#submitBtn").click(function () {
            $("#assignedRoleSelect option").prop("selected", true);
        });
    });
</script>
<style type="text/css">
    option[modified="true"] {
        color: #00B83F;
    }
</style>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/get/page.html?pageNum=${param.pageNum}&keyword=${param.keyword}">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline" action="assign/do/role/assign.html" method="post">
                        <input type="hidden" value="${param.adminId}" name="adminId">
                        <input type="hidden" value="${param.pageNum}" name="pageNum">
                        <input type="hidden" value="${param.keyword}" name="keyword">
                        <div class="form-group">
                            <label for="unAssignedRoleSelect">未分配角色列表</label><br>
                            <select class="form-control" multiple="multiple" size="10"
                                    style="width:100px;overflow-y:auto;" id="unAssignedRoleSelect">
                                <%--                                <option value="pm">PM</option>--%>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li class="btn btn-default glyphicon glyphicon-chevron-right" id="btnLeft"></li>
                                <br>
                                <li class="btn btn-default glyphicon glyphicon-chevron-left" id="btnRight"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="assignedRoleSelect">已分配角色列表</label><br>
                            <select class="form-control" multiple="multiple" size="10"
                                    style="width:100px;overflow-y:auto;" id="assignedRoleSelect"
                                    name="assignedRoleList">
                                <%--                                <option value="qa">QA</option>--%>
                            </select>
                        </div>
                        <br/><br/>
                        <button type="submit" id="submitBtn" class="btn btn-lg btn-success btn-block"
                                style="width: 150px;margin-left: 179px">保存
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>