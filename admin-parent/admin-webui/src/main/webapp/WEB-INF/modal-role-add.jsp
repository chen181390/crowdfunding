<%--
  Created by IntelliJ IDEA.
  User: chentianyu
  Date: 2021/6/15
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(() => {
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
    });
</script>
<div class="modal fade" tabindex="-1" role="dialog" id="addModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">尚筹网系统弹框</h4>
            </div>
            <div class="modal-body">
                <form class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                        <input type="text" class="form-control" name="roleName" id="inputSuccess4"
                               placeholder="请输入新增角色名称" autofocus>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveRoleBtn">保存</button>
            </div>
        </div>
    </div>
</div>