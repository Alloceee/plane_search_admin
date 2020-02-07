layui.use(['layer'], function () {
    var $ = layui.jquery
        , layer = layui.layer;
    $.ajax({
        url: "/admin/company/all",
        dataType: 'json',
        type: 'get',
        success: function (res) {
            var data = res.data;
            for (var i = 0; i < data.length; i++) {
                $('#company_id').append('<option value="' + data[i]['id'] + '">' + data[i]['name'] + '</option>');
            }
        }
    });

    $('form').submit(function () {
        $.ajax({
            data: $(this).serializeArray(),
            url: '/admin/plane/add',
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
    })
});