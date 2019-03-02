$(function () {

    /**
     * 初始化表单验证
     */
    formValidator();

});

/**
 * form验证规则
 */
function formValidator() {

    $("#_dict_add_v_edit_form").bootstrapValidator({//根据自己的formid进行更改
        message: 'This value is not valid',//默认提示信息
        feedbackIcons: {//提示图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {

            parentId: {
                message: '父节点 验证失败 .',
                validators: {//验证条件
                    notEmpty: {
                        message: '父节点名称 不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '父节点名称 不能超过 100 个字符 .'
                    }
                }
            },

            itemKey: {
                message: '字典Key 验证失败 .',
                validators: {//验证条件
                    notEmpty: {
                        message: '字典Key 不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '字典Key 不能超过 100 个字符 .'
                    },
                    remote: {//验证唯一性
                        url: '/casSysDictionary/do_verify_dictionary',
                        message: '字典Key已存在 , 请重新输入 .',
                        delay: 1000,
                        type: 'POST',
                        dataType:"json",
                        data: {
                            id: $('#_id').val(),
                            itemKey: $('#itemKey').val(),
                            fields: 'itemKey',
                        },
                    },
                }
            },

            itemValue: {
                message: '字典Value 验证失败 .',
                validators: {//验证条件
                    notEmpty: {
                        message: '字典Value 不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '字典Value 不能超过 100 个字符 .'
                    }
                }
            },

            itemNamecn: {
                message: '字典名称 验证失败 .',
                validators: {//验证条件
                    notEmpty: {
                        message: '字典名称 不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '字典名称 不能超过 100 个字符 .'
                    }
                }
            },

            sort: {
                message: '排序 验证失败 .',
                validators: {//验证条件
                    notEmpty: {
                        message: '排序 不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 10,
                        message: '排序 不能超过 10 个字符 .'
                    },
                    regexp: {//正则验证
                        regexp: /^[1-9]\d{0,7}$/,
                        message: '只能输入小于9位数的正整数 (不能以0开头) .'
                    },
                }
            },

            status: {
                message: '状态 验证失败 .',
                validators: {//验证条件
                    notEmpty: {
                        message: '状态 不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '状态 不能超过 100 个字符 .'
                    }
                }
            },

            description: {
                message: '描述 验证失败 .',
                validators: {//验证条件
                    notEmpty: {
                        message: '描述 不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '描述 不能超过 100 个字符 .'
                    }
                }
            },

        },
    })

}

/**
 * 保存字典
 */
$("#_saveDict").on("click", function () {
    var _this = $('#_dict_add_v_edit_form');
    if (fromValid(_this)) {
        var params = serializeObject($("#_dict_add_v_edit_form"));
        ajaxRequest("/casSysDictionary/do_save_v_update_dictionary", params, function () {
            //关闭模态框
            $("#_pop_add_v_edit_dictionary").modal("hide");
            refreshBootstrapTable($("#exampleTableToolbar"));
            if( params.id != '' && params.id != null && params.id != undefined ){
                var newNode={
                    text:params.itemNamecn
                };
                refreshEditTreeview($('#dictJson'),newNode);
            } else {
                var newNode={
                    id:responseId,
                    text:params.itemNamecn
                };
                refreshAddTreeview($('#dictJson'),newNode);
            }
        });
    }
});