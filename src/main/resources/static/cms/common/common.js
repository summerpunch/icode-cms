/**
 * Title: 路径<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2019/3/2 10:12<br>
 */
var PathUtil = {
    dictionaries: function () {
        return '/cms/dictionary';
    }
};

/**
 * Title: 弹窗(删除提示)<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2019/3/2 10:12<br>
 */
var POP_UP_Util = {
    remove: function (callback) {
        swal({
                title: "您确定要删除这条信息吗",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是的，我要删除！",
                cancelButtonText: "让我再考虑一下…",
                closeOnConfirm: false,
                closeOnCancel: false
            },
            function (isConfirm) {
                if (isConfirm) {
                    callback();
                    swal.close();
                } else {
                    swal("已取消", "您取消了删除操作！", "error");
                }
            });
    }
};

/**
 * Title: 提示<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2019/3/2 10:12<br>
 */
var ToastOptionsUtil = {
    show: function (data) {
        toastr.options = {
            closeButton: true,
            debug: false,
            progressBar: true,
            positionClass: 'toast-top-right',
            onclick: null
        };
        toastr[data.status](data.msg, "Hi, warm prompt .");
    }
};

/**
 * Title: http请求<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2019/3/2 10:12<br>
 */
var HttpUtil = {
    /**
     * Title: 同步请求<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/2 10:04<br>
     */
    ajaxSynchronizationRequest: function (url, params, callback) {
        $.ajax({
            type: "POST",
            url: url,
            data: params,
            async: true,
            success: function (data) {
                if (data.code == 1000) {
                    if (callback != undefined && callback != null) {
                        callback();
                    }
                }
                ToastOptionsUtil.show(data);
            }
        });
    },

    /**
     * Title: 异步请求<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/2 10:04<br>
     */
    ajaxAsynchronizationRequest: function (url, params, callback) {
        $.ajax({
            type: "POST",
            url: url,
            data: params,
            success: function (data) {
                if (data.code == 1000) {
                    if (callback != undefined && callback != null) {
                        callback();
                    }
                }
                ToastOptionsUtil.show(data);
            }
        });
    }
};


/**
 * Title: 响应返回的数据id<br>
 * Description: 通常用在Treeview刷新<br>
 * Author: XiaChong<br>
 * Date: 2018/8/14 11:04<br>
 */
var responseId;


/////////////////////////////////////
/**
 * Bootstrap 相关 start .
 */

/////////////////////////////////////

/**
 * Title: 编辑刷新Tree<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/14 10:25<br>
 */
function refreshEditTreeview(_this, newNode) {
    var node = _this.treeview('getSelected');
    var treeData = new Array()
    treeData.push(node[0].nodes[tableIndex]);
    _this.treeview('updateNode', [treeData, newNode]);
}

/**
 * Title: 新增刷新Tree<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/14 10:25<br>
 */
function refreshAddTreeview(_this, newNode) {
    var node = _this.treeview('getSelected');
    var treeData = new Array()
    treeData.push(node[0].nodes[tableIndex]);
    _this.treeview('addNode', [newNode, treeData]);
}


/**
 * Title: 获取选中的Tree数据<br>
 * Description: 选中Tree获取选中的数据<br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 18:46<br>
 * Param: <br>
 * Return:
 */
function getTreeData(_this) {
    return _this.treeview('getChecked', '');
}


/**
 * Title: 获取选中的数据<br>
 * Description: 选中Table获取选中的数据<br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 18:45<br>
 * Param: <br>
 * Return:
 */
function getTableData(_this) {
    return _this.bootstrapTable('getSelections')[0];
}

/**
 * Title: Bootstrap手动验证表单<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 18:45<br>
 */
function fromValid(_this) {

    //启用验证
    _this.data('bootstrapValidator').validate();

    //验证是否通过true/false
    return _this.data('bootstrapValidator').isValid();
}

/////////////////////////////////////
/**
 * Bootstrap 相关 end .
 */

/////////////////////////////////////


/**
 * Title: 清空搜索条件<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 18:45<br>
 */
function fromClears(id) {
    $(id)[0].reset();//这种方式虽然可以重置表单，但是不能重置隐藏字段。隐藏字段要单独处理
}

/**
 * Title: 序列化表单为 JSON 对象 <br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 18:45<br>
 */
function serializeObject(_this) {
    var o = {};
    var a = _this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

/**
 * Title: 验证json对象中是否存在属性<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 18:44<br>
 */
function isEmptyObject(e) {
    var t;
    for (t in e)
        return true;
    return false;
}

/**
 * Title: ajax请求<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 18:44<br>
 */
function ajaxRequest(url, params, callback) {
    $.ajax({
        type: "POST",
        url: url,
        data: params,
        success: function (data) {
            switch (data.code) {
                case 1000:
                    if (data.data != null) {
                        responseId = data.data.id;
                    }
                    parent.layer.msg(data.msg, {icon: 6});
                    if (callback != undefined && callback != null) {
                        callback();
                    }
                    break;
                case 4001:
                    parent.layer.msg(data.msg, {icon: 3});
                    break;
                case 5000:
                    parent.layer.msg(data.msg, {icon: 2});
                    break;
            }
        }
    });
}

///////////////////////////////////////////////////////////////
