layui.use('table', function () {
    var table = layui.table;

    var dataInit = {
        //表格数据
        table: function () {
            table.render({
                elem: '#test'
                , url: '/admin/task/all'
                , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
                , defaultToolbar: ['filter', 'exports', 'print']
                , title: '任务信息表'
                , cols: [
                    [
                        {type: 'checkbox', fixed: 'left'}
                        , {field: 'id', title: 'ID', width: 80, fixed: 'left', unresize: true, sort: true}
                        , {field: 'name', title: '公司名称', width: 120, edit: 'text', sort: true, event: 'setName'}
                        , {field: 'description', title: '公司详细', edit: 'text', event: 'setDes'}
                        , {
                        field: 'icon', title: '公司图标', width: 80, templet: function (d) {
                            if ((typeof d.icon) === "undefined" || d.icon === "") {
                                return "无";
                            } else {
                                return '<img style="width: 50px" src="http://www.yewenshu.top/' + d.icon + '"  alt="http://www.yewenshu.top/' + d.icon + '"/>'
                            }
                        }
                    }
                        , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
                    ]
                ]
                , id: 'testReload'
                , page: true
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
                            companies: JSON.stringify(data)
                        },
                        dataType: 'json',
                        type: 'post',
                        url: '/admin/company/del',
                        success: function (res) {
                            if (res.code === 0) {
                                layer.msg("删除成功", {icon: 1});
                                checkStatus.del();
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
                //console.log(obj)
                if (obj.event === 'del') {
                    layer.confirm('真的删除行么', function (index) {
                        $.ajax({
                            data: {
                                companies: data
                            },
                            dataType: 'json',
                            type: 'post',
                            url: '/admin/company/del',
                            success: function () {
                                layer.msg("删除成功");
                                obj.del();
                            }
                        });
                        obj.del();
                        layer.close(index);
                    });
                } else if (obj.event === 'modify') {
                    layer.msg('请直接在表格中修改');
                } else if (obj.event === 'setName') {
                    layer.prompt({
                        formType: 2
                        , title: '修改 ID 为 [' + data.id + '] 的公司名称'
                        , value: data.name
                    }, function (value, index) {
                        layer.close(index);
                        //这里一般是发送修改的Ajax请求
                        $.ajax({
                            data: {
                                id: data.id,
                                name: value
                            },
                            url: "/admin/company/update",
                            dataType: 'json',
                            type: 'post',
                            success: function (res) {
                                if (res > 0) {
                                    layer.msg("修改失败", {icon: 5});
                                } else {
                                    layer.msg("修改成功", {icon: 1});
                                    //同步更新表格和缓存对应的值
                                    obj.update({
                                        name: value
                                    });
                                }
                            }
                        });
                    });
                } else if (obj.event === 'setDes') {
                    layer.prompt({
                        formType: 2
                        , title: '修改 ID 为 [' + data.id + '] 的公司详情'
                        , value: data.description
                    }, function (value, index) {
                        layer.close(index);
                        //这里一般是发送修改的Ajax请求
                        $.ajax({
                            data: {
                                id: data.id,
                                description: value
                            },
                            url: "/admin/company/update",
                            dataType: 'json',
                            type: 'post',
                            success: function (res) {
                                if (res > 0) {
                                    layer.msg("修改失败", {icon: 5});
                                } else {
                                    layer.msg("修改成功", {icon: 1});
                                    //同步更新表格和缓存对应的值
                                    obj.update({
                                        description: value
                                    });
                                }
                            }
                        });
                    });
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