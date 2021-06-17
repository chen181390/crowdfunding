// 执行分页，生成页面
function generatePage() {
    getPageInfoRemote();
}

function getPageInfoRemote() {
    $.ajax({
        url: "role/get/page/info.json",
        type: "post",
        data: {
            pageNum: window.pageNum,
            pageSize: window.pageSize,
            keyword: window.keyword
        },
        dataType: "json",
        success: response => {
            console.log(response);
            if (response.result === "FAILED") {
                layer.msg(response.message);
                return;
            }
            fillTableBody(response.data);

        },
        error: response => {
            layer.msg(decodeURI(`失败! 响应状态码=${response.status} 错误信息=${response.statusText}`));
        }
    });
}

function fillTableBody(pageInfo) {
    $("#summaryBox").prop("checked", false);
    $("#rolePageBody").empty();
    $("#Pagination").empty();
    if (!pageInfo || !pageInfo || pageInfo.list.length === 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索到的数据！</td></tr>")
        return;
    }
    for (let i = 0; i < pageInfo.list.length; i++) {
        let role = pageInfo.list[i];
        let id = role.id;
        let name = role.name;

        let numberTd = `<td>${(id)}</td>`;
        let checkboxTd = `<td><input type='checkbox' class='itemBox' rolename=${name} id=${id}></td>`;
        let roleNameTd = `<td>${name}</td>`;
        let checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class='glyphicon glyphicon-check'></i></button>";
        let pencilBtn = `<button type='button' class='btn btn-primary btn-xs pencilBtn' rolename=${name} roleid=${id}><i class='glyphicon glyphicon-pencil'></i></button>`;
        let removeBtn = `<button type='button' class='btn btn-danger btn-xs removeBtn' rolename=${name} id=${id}><i class='glyphicon glyphicon-remove'></i></button>`;
        let buttonTd = `<td>${checkBtn} ${pencilBtn} ${removeBtn}</td>`;
        let tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}

function generateNavigator(pageInfo) {
    let totalRecord = pageInfo.total;
    let properties = {
        num_edge_entries: 3,
        num_display_entries: 5,
        callback: paginationCallBack,
        items_per_page: pageInfo.pageSize,
        current_page: pageInfo.pageNum - 1,
        prev_text: "上一页",
        next_text: "下一页"
    };
    $("#Pagination").pagination(totalRecord, properties);
}

function paginationCallBack(pageIndex, jQuery) {
    window.pageNum = pageIndex + 1;
    generatePage();
    return false;
}

function showConfirmModal(roleArray) {
    $("#roleNameDiv").empty();
    $("#confirmModal").modal("show");
    window.roleIdArray = [];
    for(let i = 0; i < roleArray.length; i++) {
        let role = roleArray[i];
        let roleName = role.roleName;
        $("#roleNameDiv").append(roleName+"<br/>");
        window.roleIdArray.push(role.roleId);
    }
}