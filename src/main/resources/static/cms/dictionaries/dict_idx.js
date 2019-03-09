function viewInfo(row) {
    var url = PathUtil.dictionaries() + '/pop/info_dictionary?id=' + row.id;
    BootstrapModalUtil.openBaseTemplate(url);
}

function viewUpdate(row) {
    var url = PathUtil.dictionaries() + '/pop/edit_dictionary?id=' + row.id;
    BootstrapModalUtil.openBaseTemplate(url);
};

function viewRemove(row) {
    POP_UP_Util.remove(function () {
            var url = PathUtil.dictionaries() + '/remove/dictionaryById';
            var params = {ids: ArrayUtil.transformArray(row)};
            HttpUtil.ajaxAsynchronizationRequest(url, params, function () {
                BootstrapTableUtil.refreshBootstrapTable($("#_dictionary_table"));
                BootstrapTreeUtil.refreshRemoveTreeview(ArrayUtil.transformArray(row));
            });
        }
    );
};

var DictionaryTableUtil = {

    /**
     * Title: 获取字典树结构树<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:33<br>
     */
    getDictionaryTree: function () {
        $.ajax({
            type: 'POST',
            url: PathUtil.dictionaries() + '/ajax/dictionary/tree',
            async: false,
            dataType: 'json',
            success: function (data) {
                BootstrapTreeUtil.showTreeview('#_dictJson', data.data);
            }
        });
    },

    /**
     * Title: 查询数据字典列表<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:31<br>
     */
    getdictChildList: function (params) {
        BootstrapTableUtil.getDataTable({
            id: '#_dictionary_table',
            url: PathUtil.dictionaries() + '/get/dictionaryList',
            params: params,
            array: [{
                checkbox: true
            }, {
                field: 'id',
                title: 'id'
            }, {
                field: 'itemNamecn',
                title: '中文名称'
            }, {

                field: 'itemKey',
                title: '键名称'
            }, {

                field: 'status',
                title: '状态'
            }, {

                field: 'sort',
                title: '顺序'
            }, {
                field: 'id',
                title: '操作',
                width: 100,
                align: 'center',
                valign: 'middle',
                formatter: BootstrapTableUtil.actionFormatter
            }
            ]
        });
    },

    /**
     * Title: 按条件查询<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:34<br>
     */
    dictSearch: function () {
        DictionaryTableUtil.getdictChildList(JsonUtil.serializeObject($("#_dictFormSearch")));
    }
};


$(document).ready(function () {
    DictionaryTableUtil.getDictionaryTree();

    DictionaryTableUtil.getdictChildList({id: 1});

    BootstrapTreeUtil.monitorTree('#_dictJson');

    ChosenUtils.initChosen();

    /**
     * Title: 新增数据字典页面<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/5 14:18<br>
     */
    $("#_addDictionary").on("click", function () {
        var data = BootstrapTableUtil.getChooseTableDataMulti($('#_dictionary_table'));
        if (data.length == 1) {
            var url = PathUtil.dictionaries() + '/pop/add_dictionary?parentId=' + data[0].id;
            BootstrapModalUtil.openBaseTemplate(url);
        } else {
            parent.layer.msg('请仅选择一项作为父节点 . ', {icon: 6});
        }
    });

    /**
     * Title: 批量删除<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/7 19:38<br>
     */
    $("#_delDictionary").on("click", function () {
        var data = BootstrapTableUtil.getChooseTableDataMulti($('#_dictionary_table'));
        if (data.length > 0) {
            viewRemove(data);
        } else {
            parent.layer.msg('请选择需要删除的节点 . ', {icon: 6});
        }
    });


    BootstrapModalUtil.removeData();
});

