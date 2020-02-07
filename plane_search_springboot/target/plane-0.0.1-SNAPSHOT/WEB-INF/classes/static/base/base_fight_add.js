layui.use(['layer', 'laydate'], function () {
    var layer = layui.layer
        , laydate = layui.laydate;

    //加载插件
    var pluginsInit = {
        layuiInit: function () {
            //日期时间范围
            laydate.render({
                elem: '#time'
                , type: 'datetime'
                , range: true
            });
        }
    };
    //数据初始化
    var dataInit = {
        //获取公司信息
        getCompany: function () {
            $.ajax({
                url: "/admin/company/all",
                dataType: 'json',
                type: 'get',
                success: function (res) {
                    var data = res.data;
                    for (var i = 0; i < data.length; i++) {
                        $('#company').append('<option value="' + data[i]['id'] + '">' + data[i]['name'] + '</option>');
                    }
                }
            });
        },
        //获取客机信息
        getPlane: function () {
            $('#company').on('change', function () {
                var id = $(this).find('option:selected').val();
                $('#number').html("");
                $.ajax({
                    data: {
                        companyId: id
                    },
                    dataType: 'json',
                    type: 'post',
                    url: '/admin/plane/getByCompany',
                    success: function (res) {
                        if (res.code === 0) {
                            var data = res.data;
                            for (var i = 0; i < data.length; i++) {
                                $('#number').append('<option value="' + data[i]['id'] + '">' + data[i]['number'] + '</option>');
                            }
                        }
                    }
                });
            });
        }
    };
    //表单提交
    var submit = {
        formSubmit: function () {
            $('form').submit(function () {
                var form = $(this).serializeArray();
                var type;
                for (var i = 0; i < form.length; i++) {
                    if (form[i].name === "type") {
                        if (form[i].value === "0") {
                            type = "china";
                        } else {
                            type = "abroad";
                        }
                    }
                }
                $.ajax({
                    data: $(this).serializeArray(),
                    url: '/admin/fight/' + type + '/add',
                    type: 'POST',
                    dataType: 'json',
                    success: function (res) {
                        if (res.code > 0) {
                            layer.msg(res.msg);
                        } else {
                            layer.msg(res.msg, {icon: 1});
                            location.reload();
                        }
                    }
                })
            });
        }
    };
    var init = function () {
        pluginsInit.layuiInit();
        dataInit.getCompany();
        dataInit.getPlane();
        submit.formSubmit();
    };
    $(function () {
        init();
    });
});