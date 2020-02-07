layui.use('table', function () {
    var table = layui.table;

    var dataInit = {
        //表格数据
        table: function () {
            table.render({
                elem: '#test'
                , url: '/admin/plane/all'
                , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
                , defaultToolbar: ['filter', 'exports', 'print']
                , title: '航班客机表'
                , cols: [
                    [
                        {type: 'checkbox', fixed: 'left'}
                        , {field: 'id', title: 'ID', width: 80, fixed: 'left', unresize: true, sort: true}
                        , {
                        field: 'company',
                        title: '所属公司',
                        width: 120,
                        edit: 'text',
                        sort: true,
                        event: 'setCompany',
                        templet: function (d) {
                            return d.company.name;
                        }
                    }
                        , {field: 'model', title: '客机机型', edit: 'text', event: 'setDes'}
                        , {field: 'number', title: '客机序号', width: 100}
                        , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
                    ]
                ]
                , page: true
                , id: 'testReload'
            });
        },
        //头工具栏事件
        toolbar: function () {
            table.on('toolbar(test)', function (obj) {
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if (obj.event === 'delAll') {
                    $.ajax({
                        data: {
                            planes: JSON.stringify(data)
                        },
                        dataType: 'json',
                        type: 'post',
                        url: '/admin/plane/del',
                        success: function (res) {
                            if (res.code === 0) {
                                layer.msg("删除成功", {icon: 1});
                                checkStatus.del();
                                table.reload('testReload');
                            } else {
                                layer.msg("删除失败", {icon: 5})
                            }
                        }
                    });
                }
            });
        },
        //监听行工具事件
        row: function () {
            table.on('tool(test)', function (obj) {
                var data = obj.data;
                console.log(obj.event);
                if (obj.event === 'del') {
                    layer.confirm('真的删除行么', function (index) {
                        $.ajax({
                            data: {
                                id: data.id
                            },
                            dataType: 'json',
                            type: 'post',
                            url: '/admin/plane/del',
                            success: function () {
                                layer.msg("删除成功", {icon: 1});
                                obj.del();
                            }
                        });
                        layer.close(index);
                    });
                } else if (obj.event === 'modify') {
                    layer.msg('请直接在表格中修改');
                }
            });
        }
    };

    //搜索功能
    var search = function () {
        var $ = layui.$, active = {
            reload: function () {
                var demoReload = $('#demoReload');
                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        key: demoReload.val()
                    }
                }, 'data');
            }
        };
        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    };


    var init = function () {
        dataInit.table();
        dataInit.row();
        dataInit.toolbar();
    };

    $(function () {
        init();
        search();
    })
});