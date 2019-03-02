function viewSearch(id) {
    console.info(id);
    console.info('查看');
}

function viewUpdate(id) {
    console.info(id);
    console.info('更新');
}

function viewRemove(row) {
    POP_UP_Util.remove(function () {
            var url = PathUtil.dictionaries() + '/remove/dictionaryById';
            var params = {id: row.id};
            HttpUtil.ajaxAsynchronizationRequest(url, params, function () {
                BootstrapTableUtil.refreshBootstrapTable($("#_dictionary_table"));
                BootstrapTreeUtil.refreshRemoveTreeview($('#_dictJson'), row);
            });
        }
    );
};

var DictionaryTableUtil = {

    /**
     * Title: 获取字典树结构树<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/1 11:33<br>
     */
    getDictionaryTree: function () {
        var json = new Array();
        $.ajax({
            type: 'POST',
            url: PathUtil.dictionaries() + '/ajax/dictionary/tree',
            async: false,
            dataType: 'json',
            success: function (data) {
                json = data.data;
            }
        });
        $('#_dictJson').treeview({
            data: json,
            levels: 2,
            showCheckbox: false, //不显示单选
        });
    },

    /**
     * Title: 查询数据字典列表<br>
     * Description: <br>
     * Author: XiaChong<br>
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
                field: 'id',
                title: '删除',
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
     * Date: 2019/3/1 11:34<br>
     */
    dictSearch: function () {
        DictionaryTableUtil.getdictChildList(serializeObject($("#_dictFormSearch")));
    }
};


$(function () {
    DictionaryTableUtil.getDictionaryTree();
    DictionaryTableUtil.getdictChildList({id: 1});

    /**
     * Title: 点击字典树节点<br>
     * Description: 按当前节点查询所属字典<br>
     * Author: XiaChong<br>
     * Date: 2019/3/1 11:56<br>
     */
    $('#_dictJson').on('nodeSelected', function (event, data) {
        DictionaryTableUtil.getdictChildList({id: data.id});
    });


    /**
     * Title: 获取当前选中Table的行的下标<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/14 10:07<br>

     $('#_dictionary_table').on("click-row.bs.table", function (e, row, $element) {
            tableIndex = $element.data('index');
        });

     */

    /**
     * Title: 移除模态框<br>
     * Description: modal页面加载$()错误,
     *              由于移除缓存时加载到<div class="modal-content"></div>未移除的数据<
     *              手动移除加载的内容br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 19:15<br>
     */
    $("#_pop_add_v_edit_dictionary").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
        $(this).find(".modal-content").children().remove();
    });
});

