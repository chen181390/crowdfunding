<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" type="text/css" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="js/menu.js"></script>
<script type="text/javascript">
    $(function () {
        generateTree();

        $("#treeDemo").on("click", ".addBtn", function () {
            $("#menuResetBtn").click();
            window.pid = $(this).attr("nodeId");
            $("#menuAddModal").modal("show");
            return false;
        });

        $("#menuSaveBtn").click(function () {
            let name = $("#menuAddModal [name=name]").val().trim();
            let url = $("#menuAddModal [name=url]").val().trim();
            let icon = $("#menuAddModal [name=icon]:checked").val();
            if (!name || !url || !icon) {
                layer.msg("填入数据不可为空");
                return;
            }
            $.ajax({
                url: "menu/save.json",
                type: "post",
                data: {
                    pid: window.pid,
                    name: name,
                    url: url,
                    icon: icon
                },
                dataType: "json",
                success: function (response) {
                    let result = response.result;
                    if (result === "SUCCESS") {
                        layer.msg("操作成功!");
                        generateTree();
                    } else
                        layer.msg("操作失败!" + response.message);
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuAddModal").modal("hide");
        });

        $("#treeDemo").on("click", ".editBtn", function () {
            window.id = $(this).attr("nodeId");
            let zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            let currentNode = zTreeObj.getNodeByParam("id", window.id);
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);
            $("#menuEditModal").modal("show");
            return false;
        });

        $("#menuEditBtn").click(function () {
            let name = $("#menuEditModal [name=name]").val().trim();
            let url = $("#menuEditModal [name=url]").val().trim();
            let icon = $("#menuEditModal [name=icon]:checked").val();
            if (!name || !url || !icon) {
                layer.msg("填入数据不可为空");
                return;
            }
            $.ajax({
                url: "menu/update.json",
                type: "post",
                data: {
                    id: window.id,
                    name: name,
                    url: url,
                    icon: icon
                },
                dataType: "json",
                success: function (response) {
                    let result = response.result;
                    if (result === "SUCCESS") {
                        layer.msg("操作成功!");
                        generateTree();
                    } else
                        layer.msg("操作失败!" + response.message);
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuEditModal").modal("hide");
        });

        $("#treeDemo").on("click", ".removeBtn", function () {
            window.id = $(this).attr("nodeId");
            let currentNode = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam("id", window.id);
            $("#removeNodeSpan").html("&nbsp;<i class='" + currentNode.icon + "'></i>" + currentNode.name + "&nbsp;");
            $("#menuConfirmModal").modal("show");
            return false;
        });

        $("#confirmBtn").click(function () {
            $.ajax({
                url: "menu/remove.json",
                type: "post",
                data: {
                    id: window.id
                },
                dataType: "json",
                success: function (response) {
                    let result = response.result;
                    if (result === "SUCCESS") {
                        layer.msg("删除成功!");
                        generateTree();
                    } else
                        layer.msg("操作失败!" + response.message);
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuConfirmModal").modal("hide");
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
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div
                            style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp" %>
<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>
<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
</body>

</html>