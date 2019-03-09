//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
/*             Bootstrap相关公共方法                 */
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////

//处理BootstrapTable数据列表的公共方法
var BootstrapTableUtil = {
    /**
     * Title: 请求成功方法<br>
     * Description: 渲染Table<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 19:45<br>
     */
    responseHandler: function (result) {
        return {
            total: result.total,   //总页数,前面的key必须为"total"
            data: result.data      //行数据，前面的key要与之前设置的dataField的值一致.
        };
    },

    /**
     * Title: 获取ajax分页options设置<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 13:27<br>
     */
    getAjaxPagingOptions: function (settings) {
        var paramsData = settings.data;
        var options = {
            url: settings.ajaxUrl,
            method: 'post',
            contentType: "application/x-www-form-urlencoded",//必须要有！！！！
            toolbar: '#exampleToolbar',//可以在table上方显示的一条工具栏，
            iconSize: 'outline',
            singleSelect: false,//单选
            clickToSelect: false,//是否启用点击选中行
            data_local: "zh-US",//表格汉化
            pageSize: 5,//如果设置了分页，页面数据条数
            pageList: [5, 10, 20],	//如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录
            pageNumber: 1, //初始化加载第一页，默认第一页
            striped: true,//设置为 true 会有隔行变色效果
            undefinedText: " -- ",//当数据为 undefined 时显示的字符
            pagination: true,//是否分页
            sidePagination: "server", //服务端处理分页
            queryParamsType: 'limit',
            dataField: "data",//这是返回的json数组的key.默认好像是"rows".这里只有前后端约定好就行
            queryParams: function (settings) {
                paramsData.pageSize = settings.limit;
                paramsData.pageIndex = settings.offset / settings.limit + 1;
                return paramsData;
            },//请求服务器时所传的参数
            responseHandler: BootstrapTableUtil.responseHandler,//请求数据成功后，渲染表格前的方法
            columns: settings.colums,			//列配置数组
            //search: true,//是否显示搜索功能
            //showToggle: true,//是否显示 切换试图（table/card）按钮
            //showColumns: true,//是否显示 内容列下拉框
            //showRefresh: true,//是否显示刷新功能
            //queryParams: queryParams,//请求服务器时所传的参数
        };
        return options;
    },

    /**
     * Title: 获取table数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 13:27<br>
     */
    getDataTable: function (settings) {
        /**
         * 解决多次请求无效的问题
         * 在初始化table之前，要将table销毁，否则会保留上次加载的内容
         */
        $(settings.id).bootstrapTable('destroy');
        $(settings.id).bootstrapTable(BootstrapTableUtil.getAjaxPagingOptions({
            ajaxUrl: settings.url,
            data: settings.params,
            colums: settings.array
        }));
    },

    /**
     * Title: 返回Table操作类型<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 19:34<br>
     */
    actionFormatter: function (value, row, index) {
        var result = "";
        result += "<a href='#' class='btn-xs' onclick='viewInfo(" + JSON.stringify(row) + ");' style='color: #269abc' title='查看'><span class='glyphicon glyphicon-search'></span></a>";
        result += "<a href='#' class='btn-xs' onclick='viewUpdate(" + JSON.stringify(row) + ");' title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
        result += "<a href='#' class='btn-xs' onclick='viewRemove(" + JSON.stringify(row) + ");' style='color: red' title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
        return result;
    },

    /**
     * Title: 刷新BootstrapTable<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 19:50<br>
     */
    refreshBootstrapTable: function (_this) {
        _this.bootstrapTable('refresh');
    },

    /**
     * Title: 获取选中的数据<br>
     * Description: 单选情况下<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 13:55<br>
     */
    getChooseTableDataOne: function (_this) {
        return _this.bootstrapTable('getSelections')[0];
    },
    /**
     * Title: 获取选中的数据<br>
     * Description: 多选<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 19:43<br>
     */
    getChooseTableDataMulti: function (_this) {
        return _this.bootstrapTable('getSelections');
    }
};

//处理BootstrapTree数据列表的公共方法
var BootstrapTreeUtil = {
    /**
     * Title: 选中的节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/8 10:53<br>
     */
    treeChooseArray: new Array(),
    /**
     * Title: 选中节点的子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/8 10:45<br>
     */
    treeChooseChildArray: new Array(),
    /**
     * Title: 初始化数据字典后元对象<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/8 10:45<br>
     */
    treeDom: {},
    /**
     * Title: 删除刷新Tree<br>
     * Description: 接收数组id<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 19:51<br>
     */
    refreshRemoveTreeview: function (arr) {
        var nodes = BootstrapTreeUtil.treeChooseChildArray;
        var treeArr = new Array();
        $.each(nodes, function (index, obj) {
            if (arr.indexOf(obj.id) > -1) {
                treeArr.push(obj);
                if ((arr.length) === (treeArr.length)) {
                    $.each(treeArr, function (idx, o) {
                        BootstrapTreeUtil.treeDom.treeview('removeNode', [o, {silent: true}]);
                    });
                    return false;
                }
            }
        });
    },
    /**
     * Title: 更新后刷新<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 19:34<br>
     */
    refreshEditTreeview: function (json) {
        var nodes = BootstrapTreeUtil.treeChooseChildArray;
        $.each(nodes, function (index, obj) {
            if (obj.id == json.id) {
                BootstrapTreeUtil.treeDom.treeview('updateNode', [obj, json]);
                return false;
            }
        });
    },
    /**
     * Title: 新增后刷新<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 19:34<br>
     */
    refreshAddTreeview: function (json) {
        var nodes = BootstrapTreeUtil.treeChooseChildArray;
        $.each(nodes, function (index, obj) {
            if (obj.id == json.parentId) {
                BootstrapTreeUtil.treeDom.treeview('addNode', [json, obj]);
                return false;
            }
        });
    },
    /**
     * Title: 渲染tree<br>
     * Description: 不显示单选<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 14:29<br>
     */
    showTreeview: function (id, json) {
        BootstrapTreeUtil.treeDom = $(id).treeview({
            data: json,
            levels: 2,
            showCheckbox: false,//不显示单选
        });
        $("#1").click();
        BootstrapTreeUtil.treeChooseArray = BootstrapTreeUtil.treeDom.treeview('getSelected');
        BootstrapTreeUtil.treeChooseChildArray = BootstrapTreeUtil.treeChooseArray[0].nodes;
    },
    /**
     * Title: 渲染tree<br>
     * Description: 显示单选<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 14:29<br>
     */
    showTreeviewNoCheckbox: function (id, json) {
        $(id).treeview({
            data: json,
            levels: 2,
            showCheckbox: true,
        });
    },
    /**
     * Title: 点击字典树节点<br>
     * Description: 按当前节点查询所属字典<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:56<br>
     */
    monitorTree: function (id) {
        $(id).on('nodeSelected', function (event, data) {
            BootstrapTreeUtil.treeChooseArray = BootstrapTreeUtil.treeDom.treeview('getSelected');
            BootstrapTreeUtil.treeChooseChildArray = BootstrapTreeUtil.treeChooseArray[0].nodes;
            DictionaryTableUtil.getdictChildList({id: data.id});
        });
    }
};

//处理BootstrapModal的公共方法
var BootstrapModalUtil = {
    /**
     * Title: 基础模板中<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 14:45<br>
     */
    openBaseTemplate: function (url) {
        $("#_base_template").modal({
            remote: url,
            backdrop: "static",
            keyboard: true,
        })
    },
    /**
     * Title: 基础模板大<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 14:45<br>
     */
    openBaseTemplateWinMax: function (url) {
        $("#_base_template_max").modal({
            remote: url,
            backdrop: "static",
            keyboard: true,
        })
    },
    /**
     * Title: 基础模板小<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 14:44<br>
     */
    openBaseTemplateWinMin: function (url) {
        $("#_base_template_min").modal({
            remote: url,
            backdrop: "static",
            keyboard: true,
        })
    },

    /**
     * Title: Bootstrap手动验证表单<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 17:22<br>
     */
    fromValid: function (_this) {
        //启用验证
        _this.data('bootstrapValidator').validate();
        //验证是否通过true/false
        return _this.data('bootstrapValidator').isValid();
    },
    /**
     * Title: 移除模态框<br>
     * Description:
     *
     * modal页面加载$()错误,
     * 由于移除缓存时加载到<div class="modal-content"></div>未移除的数据
     * 手动移除加载的内容br>
     *
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:18<br>
     */
    removeData: function () {
        $(".modal").on("hidden.bs.modal", function () {
            $(this).removeData("bs.modal");
            $(this).find(".modal-content").children().remove();
        });
    }
};

//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
/*                其余相关公共方法                   */
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////

/**
 * Title: 路径<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
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
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/2 10:12<br>
 */
var POP_UP_Util = {
    remove: function (callback) {
        swal({
                title: "您确定要删除这条信息吗",
                text: "删除后将无法恢复，请谨慎操作",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是的，我要删除",
                cancelButtonText: "让我再考虑一下",
                closeOnConfirm: false,
                closeOnCancel: false
            },
            function (isConfirm) {
                if (isConfirm) {
                    callback();
                    swal.close();
                } else {
                    swal("已取消", "您取消了删除操作", "error");
                }
            });
    }
};

/**
 * Title: 提示<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
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
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/2 10:12<br>
 */
var HttpUtil = {
    /**
     * Title: 同步请求<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
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
     * Mail: summerpunch@163.com<br>
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
                        callback(data);
                    }
                }
                ToastOptionsUtil.show(data);
            }
        });
    }
};

/**
 * Title: JSON<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/2 10:12<br>
 */
var JsonUtil = {
    /**
     * Title: 验证json对象是否为空<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 13:58<br>
     */
    isEmptyObject: function (e) {
        var t;
        for (t in e) {
            return true;
        }
        return false;
    },

    /**
     * Title: 序列化表单为JSON对象<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:28<br>
     */
    serializeObject: function (_this) {
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
};

/**
 * Title: 清除<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/5 19:36<br>
 */
var ClearUtil = {

    /**
     * Title: 清空搜索条件<br>
     * Description: 这种方式虽然可以重置表单，但是不能重置隐藏字段。隐藏字段要单独处理<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 17:21<br>
     */
    fromClears: function (id) {
        $('.chosen-select').val('').trigger("chosen:updated");
        $(id)[0].reset();
    }
};

/**
 * Title: 实体类工具类<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/5 19:40<br>
 */
var EntityUtil = {

    /**
     * Title: BootstrapTree新增修改删除Json工具类<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 19:38<br>
     */
    dictTreeJson: function (json) {
        return {
            id: json.id,
            parentId: json.parentId,
            text: json.itemNamecn
        }
    }
};

var ArrayUtil = {

    /**
     * Title: 将数组或对象转为数据<br>
     * Description: 返回id数组<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 19:55<br>
     */
    transformArray: function (data) {
        var arr = new Array();
        if (Array.isArray(data)) {
            $.each(data, (index, value) => {
                arr.push(value.id);
            });
        } else {
            arr.push(data.id);
        }
        return arr;
    },
    /**
     * Title: 基础弹窗模板样式<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/8 15:23<br>
     */
    modelTemplateStyle: function () {
        var styleArr = ['flipInY', 'fadeIn', 'bounceInRight'];
        return styleArr[Math.floor(Math.random() * styleArr.length)];
    }

};

var ChosenUtils = {
    config: function () {
        return {
            '.chosen-select': {},
            '.chosen-select-deselect': {
                allow_single_deselect: true
            },
            '.chosen-select-no-single': {
                disable_search_threshold: 10
            },
            '.chosen-select-no-results': {
                no_results_text: 'Oops, nothing found!'
            },
            '.chosen-select-width': {
                width: "95%"
            }
        }
    },
    /**
     * Title: 初始化多选框<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/9 12:57<br>
     */
    initChosen: function () {
        var config = ChosenUtils.config();
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
    }
};


