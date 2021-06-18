function generateTree() {
    $("#treeDemo").empty();
    $.ajax({
        url: "menu/get/whole/tree.json",
        type: "post",
        dataType: "json",
        success: function (response) {
            let result = response.result;
            if (result === "SUCCESS") {
                let setting = {
                    view: {
                        addDiyDom: myAddDiyDom,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom
                    },
                    data: {
                        key: {
                            url: "wdnmd"
                        }
                    }
                };
                let zNodes = response.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            } else {
                layer.msg(response.message);
            }
        },
        error: function (response) {
            layer.msg(response.status + " " + response.statusText);
        }
    });
}

function myAddDiyDom(treeId, treeNode) {
    let spanId = treeNode.tId + "_ico";
    $("#" + spanId).removeClass().addClass(treeNode.icon);
}

function myAddHoverDom(treeId, treeNode) {
    let btnGroupId = treeNode.tId + "_btnGroup";
    if ($("#" + btnGroupId).length > 0) return;

    let addBtn = `<a nodeId="${treeNode.id}" class="addBtn btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg"></i></a>`;
    let editBtn = `<a nodeId="${treeNode.id}" class="editBtn btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg"></i></a>`;
    let removeBtn = `<a nodeId="${treeNode.id}" class="removeBtn btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>`;
    let btnHTML;
    switch (treeNode.level) {
        case 0:
            btnHTML = addBtn;
            break;
        case 1:
            btnHTML = addBtn + editBtn;
            if (treeNode.children.length === 0) btnHTML += removeBtn;
            break;

        case 2:
            btnHTML = editBtn + removeBtn;
            break;
    }

    let anchorId = treeNode.tId + "_a";
    $("#" + anchorId).after(`<span id="${btnGroupId}">${btnHTML}</span>`);
}

function myRemoveHoverDom(treeId, treeNode) {
    let btnGroupId = treeNode.tId + "_btnGroup";
    $("#" + btnGroupId).remove();
}
