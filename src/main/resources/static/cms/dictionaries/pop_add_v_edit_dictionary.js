$(document).ready(function () {

    formValidator();

    ChosenUtils.initChosen();

    /**
     * Title: 保存<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 17:13<br>
     */
    $("#_saveDict").on("click", function () {
        if (BootstrapModalUtil.fromValid($('#_dict_add_v_edit_form'))) {
            var params = JsonUtil.serializeObject($("#_dict_add_v_edit_form"));
            HttpUtil.ajaxAsynchronizationRequest(PathUtil.dictionaries() + "/do/save_v_update_dictionary", params, function (data) {
                $("#_base_template").modal("hide");
                BootstrapTableUtil.refreshBootstrapTable($("#_dictionary_table"));
                if (params.id) {
                    BootstrapTreeUtil.refreshEditTreeview(EntityUtil.dictTreeJson(data.data));
                } else {
                    BootstrapTreeUtil.refreshAddTreeview(EntityUtil.dictTreeJson(data.data));
                }
            });
        }
    });
});

/**
 * Title: 表单验证<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/5 19:48<br>
 */
function formValidator() {

    $("#_dict_add_v_edit_form").bootstrapValidator({
        message: 'This value is not valid',
        excluded: [':disabled'],//默认禁用/隐藏控件不验证，这里只手动排除禁用控件，即除了禁用控件外其他控件都校验
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {

            parentId: {
                message: '父节点验证失败 .',
                validators: {
                    notEmpty: {
                        message: '父节点名称不能为空 .'
                    }
                }
            },

            itemKey: {
                message: '字典Key验证失败 .',
                validators: {
                    notEmpty: {
                        message: '字典Key不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '字典Key不能超过 100 个字符 .'
                    },
                    remote: {
                        url: PathUtil.dictionaries() + '/get/uniqueness_dictionary',
                        message: '字典Key已存在 , 请重新输入 .',
                        delay: 1000,
                        type: 'POST',
                        dataType: "json",
                        data: {
                            id: $('#_id').val(),
                            itemKey: $('#itemKey').val(),
                        },
                    },
                }
            },

            itemValue: {
                message: '字典Value 验证失败 .',
                validators: {
                    notEmpty: {
                        message: '字典Value不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '字典Value不能超过100个字符 .'
                    }
                }
            },

            itemNamecn: {
                message: '字典名称验证失败 .',
                validators: {
                    notEmpty: {
                        message: '字典名称不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '字典名称不能超过 100 个字符 .'
                    }
                }
            },

            sort: {
                message: '排序验证失败 .',
                validators: {
                    notEmpty: {
                        message: '排序不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 10,
                        message: '排序不能超过 10 个字符 .'
                    },
                    regexp: {//正则验证
                        regexp: /^[1-9]\d{0,7}$/,
                        message: '只能输入小于9位数的正整数 (不能以0开头) .'
                    },
                }
            },

            status: {
                message: '状态验证失败 .',
                validators: {
                    notEmpty: {
                        message: '状态不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '状态不能超过 100 个字符 .'
                    }
                }
            },

            description: {
                message: '描述验证失败 .',
                validators: {
                    notEmpty: {
                        message: '描述不能为空 .'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '描述不能超过 100 个字符 .'
                    }
                }
            }
        }
    })
}
