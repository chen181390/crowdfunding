<%--
  Created by IntelliJ IDEA.
  User: chentianyu
  Date: 2021/6/15
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" tabindex="-1" role="dialog" id="confirmModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">尚筹网系统弹框</h4>
            </div>
            <div class="modal-body">
                <h4>请确认是否要删除下列角色：</h4>
                <div id="roleNameDiv" style="text-align: center;color: red"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="removeRoleBtn">确认删除</button>
            </div>
        </div>
    </div>
</div>