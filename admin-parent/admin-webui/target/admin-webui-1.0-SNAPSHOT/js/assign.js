function initList(data) {
    let unAssignedRoleList = data.unAssignedRoleList;
    let assignedRoleList = data.assignedRoleList;
    let unAssignedRoleStr = "";
    let assignedRoleStr = "";
    window.unAssignedRoleMap = {};
    window.assignedRoleMap = {};
    for (let item of unAssignedRoleList) {
        unAssignedRoleStr += ` <option value="${item.id}">${item.name}</option>`;
        window.unAssignedRoleMap[item.id] = item;
        item.modifyNum = 0;
    }
    for (let item of assignedRoleList) {
        assignedRoleStr += `<option value="${item.id}">${item.name}</option>`;
        window.assignedRoleMap[item.id] = item;
        item.modifyNum = 0;
    }
    $("#unAssignedRoleSelect").html(unAssignedRoleStr);
    $("#assignedRoleSelect").html(assignedRoleStr);
}

function refreshList() {
    let unAssingnedRoleStr = "";
    let assingnedRoleStr = "";

    for (let key in window.unAssignedRoleMap) {
        let item = window.unAssignedRoleMap[key];
        if (!item) continue;
        unAssingnedRoleStr += ` <option value="${item.id}" modified="${item.modifyNum % 2 != 0}">${item.name}</option>`;
    }
    for (let key in window.assignedRoleMap) {
        let item = window.assignedRoleMap[key];
        if (!item) continue;
        assingnedRoleStr += ` <option value="${item.id}" modified="${item.modifyNum % 2 != 0}">${item.name}</option>`;
    }
    $("#unAssignedRoleSelect").empty().html(unAssingnedRoleStr);
    $("#assignedRoleSelect").empty().html(assingnedRoleStr);
}