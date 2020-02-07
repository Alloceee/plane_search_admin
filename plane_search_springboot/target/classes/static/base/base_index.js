layui.use(['laytpl', 'layer'], function () {
    var laytpl = layui.laytpl,
        $ = layui.jquery,
        layer = layui.layer;

    //页面数据初始化
    var data_init = {
        plugins_init: function () {
            App.initHelpers(['datepicker', 'slick']);
        },
        china_plane: function () {
            $.ajax({
                type: 'get',
                dataType: 'text',
                data: {
                    startCity: "上海",
                    endCity: '深圳'
                },
                url: '/china_plane',
                success: function (res) {
                    //渲染模板数据
                    var getTpl = china_plane.innerHTML
                        , view = document.getElementById('china_plane_show');
                    laytpl(getTpl).render(JSON.parse(res).data, function (html) {
                        view.innerHTML = html;
                    });
                }
            });
        },
        abroad_plane: function () {
            $.ajax({
                type: 'get',
                dataType: 'text',
                data: {
                    startCity: "上海",
                    endCity: '深圳'
                },
                url: '/abroad_plane',
                success: function (res) {
                    //渲染模板数据
                    var getTpl = abroad_plane.innerHTML
                        , view = document.getElementById('abroad_plane_show');
                    laytpl(getTpl).render(JSON.parse(res).data, function (html) {
                        view.innerHTML = html;
                    });
                }
            });
        }
    };

    //获取ip地址
    var get_ip = function () {
        //获取ip地址
        $.ajax({
            url: 'http://api.map.baidu.com/location/ip?ak=ia6HfFL660Bvh43exmH9LrI6',
            type: 'POST',
            dataType: 'jsonp',
            success: function (data) {
                return data.content.address_detail.city;
            }
        });
    };

    //搜索城市交换
    var change = {
        china_change: function () {
            $('#china_change').on('click', function () {
                var china_start = $("#china_start").val();
                $("#china_start").val($('#china_end').val());
                $("#china_end").val(china_start);
            });
        },
        abroad_change: function () {
            $('#abroad_change').on('click', function () {
                var abroad_start = $("#abroad_start").val();
                $("#abroad_start").val($('#abroad_end').val());
                $("#abroad_end").val(abroad_start);
            });
        }
    };

    //搜索功能
    var search = {
        china_search: function () {
            $('#china_search').on('click', function () {
                $('#china_form').submit();
            });
        },
        abroad_search: function () {
            $('#abroad_search').on('click', function () {
                var data = $('#abroad_form').serializeArray();
                console.log(data);
            })
        }
    };

    //展示详情
    var des = {
        china: function () {
            $('#china_plane_show').on('click', '.block', function () {
                //捕获页
                layer.open({
                    type: 1,
                    shade: false,
                    title: false, //不显示标题
                    area: ['430px', '240px'], //宽高
                    content: $(this).find('.china_des').html() //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                });
            })
        },
        abroad: function () {
            $('#abroad_plane_show').on('click', '.block', function () {
                //捕获页
                layer.open({
                    type: 1,
                    shade: false,
                    title: false, //不显示标题
                    area: ['430px', '240px'], //宽高
                    content: $(this).find('.abroad_des').html() //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                });
            })
        }
    };

    var init = function () {
        data_init.plugins_init();
        data_init.china_plane();
        data_init.abroad_plane();
        change.china_change();
        change.abroad_change();
        search.china_search();
        search.abroad_search();
        des.china();
        des.abroad();
    };

    $(function () {
        init();
    });
});