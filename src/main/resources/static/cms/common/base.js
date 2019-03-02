//处理BootstrapTable数据列表的公共方法
var BootstrapTableUtil = {
    /**
     * Title: 请求成功方法<br>
     * Description: 渲染Table<br>
     * Author: XiaChong<br>
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
            singleSelect: true,//单选
            clickToSelect: true,//是否启用点击选中行
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
     * Date: 2019/3/1 19:34<br>
     */
    actionFormatter: function (value, row, index) {
        var result = "";
        result += "<a href='#' class='btn-xs' onclick='viewSearch(" + JSON.stringify(row) + ");' style='color: #269abc' title='查看'><span class='glyphicon glyphicon-search'></span></a>";
        result += "<a href='#' class='btn-xs' onclick='viewUpdate(" + JSON.stringify(row) + ");' title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
        result += "<a href='#' class='btn-xs' onclick='viewRemove(" + JSON.stringify(row) + ");' style='color: red' title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
        return result;
    },

    /**
     * Title: 刷新BootstrapTable<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/1 19:50<br>
     */
    refreshBootstrapTable: function (_this) {
        _this.bootstrapTable('refresh');
    }
};

//处理BootstrapTree数据列表的公共方法
var BootstrapTreeUtil = {
    /**
     * Title: 删除刷新Tree<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/1 19:51<br>
     */
    refreshRemoveTreeview: function (_this, row) {
        var node = _this.treeview('getSelected');
        var nodes = node[0].nodes;
        $.each(nodes, function (index, obj) {
            if (obj.id == row.id) {
                _this.treeview('removeNode', [obj, {silent: true}]);
                return false;
            }
        })
    },
};
