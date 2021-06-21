<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" type="text/css" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="js/role.js" charset="UTF-8"></script>
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

        $("#showAddModalBtn").click(() => $("#addModal").modal("show"));

        $("#saveRoleBtn").click(() => {
            let roleName = $.trim($("#addModal [name=roleName]").val());
            $.ajax({
                url: "role/save.json",
                type: "post",
                data: {
                    name: roleName
                },
                dataType: "json",
                success: response => {
                    let result = response.result;
                    if (result === "SUCCESS") {
                        layer.msg("操作成功!");
                        window.pageNum = 999999;
                        generatePage();
                    } else {
                        layer.msg("操作失败!" + response.message);
                    }
                },
                error: response => {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#addModal").modal("hide");
            $("#addModal [name=roleName]").val("");
        });

        $("#rolePageBody").on('click', ".pencilBtn", function () {
            let thisJquery = $(this);
            let roleName = thisJquery.attr("rolename");
            window.roleId = thisJquery.attr("roleid");
            $("#editModal [name=roleName]").val(roleName);
            $("#editModal").modal("show");
        });

        $("#updateRoleBtn").click(function () {
            let roleName =  $("#editModal [name=roleName]").val();
            $.ajax({
                url: "role/update.json",
                type: "post",
                data: {
                    id: window.roleId,
                    name: roleName
                },
                dataType: "json",
                success: function (response) {
                    layer.msg("操作成功");
                    generatePage();
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#editModal").modal("hide");
        });

        $("#removeRoleBtn").click(function () {
           $.ajax({
               url: "role/remove/by/role/id/array.json",
               type: "post",
               data: JSON.stringify(window.roleIdArray),
               contentType: "application/json;charset=UTF-8",
               dataType: "json",
               success: function (response) {
                   layer.msg("操作成功");
                   generatePage();
               },
               error: function (response) {
                   layer.msg(response.status + " " + response.statusText);
               }
           });
           $("#confirmModal").modal("hide");
        });

        $("#rolePageBody").on("click", ".removeBtn", function () {
            let roleArray = [{
                roleId: $(this).attr("roleid"),
                roleName: $(this).attr("rolename")
            }];
            showConfirmModal(roleArray);
        });

        $("#summaryBox").click(function () {
            $(".itemBox").prop("checked", this.checked);
        });

        $('#rolePageBody').on("click", ".itemBox", function () {
            let checkedCount = $(".itemBox:checked").length;
            let allCount =  $(".itemBox").length;
            $("#summaryBox").prop("checked", checkedCount === allCount);
        });

        $("#batchRemoveBtn").click(function () {
            let roleArray = [];
            $(".itemBox:checked").each(function () {
                let role = {
                    roleId: this.id,
                    roleName: $(this).attr("roleName")
                }
                roleArray.push(role);
            });
            if (roleArray.length === 0) {
                layer.msg("请至少选择一个执行删除");
                return;
            }
            showConfirmModal(roleArray);
        });
        
        $("#rolePageBody").on("click", ".checkBtn", function () {
           $("#assignAuthModal").modal("show");
           window.roleId = $(this).attr("roleid");
           fillAuthTree();
        });

        $("#assignAuthBtn").click(function () {
            let zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            let checkedNodes = zTreeObj.getCheckedNodes();
            let authIdArray = checkedNodes.map(function (val) {
                return val.id;
            });
            $.ajax({
               url: "assign/do/auth/assign.html",
                type: "post",
                dataType: "json",
                data: {
                   roleId: window.roleId,
                   authIdArray: authIdArray
                },
                success: function (response) {
                   if (response.result !== "SUCCESS") {
                       layer.msg(response.message);
                       return;
                   }
                   layer.msg("操作成功！");
                   $("#assignAuthModal").modal("close");
                   fillAuthTree();
                },
                error: function (response) {
                   layer.msg(response.status + " " + response.statusText);
                   console.log(response);
                }
            });
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
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="batchRemoveBtn"><i
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
                                <th width="30"><input type="checkbox" id="summaryBox"></th>
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
<%@include file="/WEB-INF/modal-role-add.jsp"%>
<%@include file="/WEB-INF/modal-role-edit.jsp"%>
<%@include file="/WEB-INF/modal-role-confirm.jsp"%>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp"%>
</body>

</html>