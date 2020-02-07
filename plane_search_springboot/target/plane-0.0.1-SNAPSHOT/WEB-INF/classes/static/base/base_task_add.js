layui.use(['upload', 'layer'], function () {
    var $ = layui.jquery
        , upload = layui.upload
        , layer = layui.layer;
    //普通图片上传
    var uploadInst = upload.render({
        elem: '#icon'
        , url: '/admin/company/icon'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            if (res.code > 0) {
                layer.msg(res.msg);
            } else {
                $("#icon_url").val(res.data);
                layer.msg(res.msg, {icon: 1});
            }
        }
        , error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });
    $('form').submit(function () {
        $.ajax({
            data: $(this).serializeArray(),
            url: '/admin/company/add',
            type: 'POST',
            dataType: 'json',
            success: function (res) {
                if (res.code > 0) {
                    layer.msg(res.msg);
                } else {
                    layer.msg(res.msg);
                    location.reload();
                }
            }
        })
    })
});