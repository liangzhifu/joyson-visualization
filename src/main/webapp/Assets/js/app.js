//打开窗口
function openWin(title, url, width, height) {
    $('#dataForm').window("open");
    var opt = {}
    opt.width = $(window).width() - 150;
    opt.height = $(window).height() - 150;
    if (width) {
        opt.width = width;
    }
    if (height) {
        opt.height = height;
    }
    $('#dataForm').window('resize', opt);
    $('#dataForm').window('setTitle', title);
    $('#dataForm').window('move', {
        left: 240,
        top: 60
    });
    document.getElementById("winSrc").src = url;
}

//关闭窗口
function closeWin() {
    $('#dataForm').window("close");
}