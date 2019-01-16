/**
 *
 * <h3>index.html页面公用方法，不包含业务逻辑</h3>
 *
 * 2018/12/20
 * @since ES5
 */

/**
 * 适应子页面高度
 */
function iFrameHeight() {

    var ifm = document.getElementById("iframe");
    var subweb = document.frames ? document.frames["iframe"].document : ifm.contentDocument;

    if (ifm != null && subweb != null) {
// 	            ifm.height=subweb.body.scrollHeight;
        if (subweb.body.scrollHeight == 0) { // 解决登录后首页显示空白的问题，原因是登录后首页iframe的高度为0
            $('#iframe').css('height', '1423px');
        } else if (subweb.body.scrollHeight == '2000') { // 个人绩效指标配置，公诉，页面不够高
            $('#iframe').css('height', '3000px');
        } else {
            $('#iframe').css('height', subweb.body.scrollHeight + 'px');
        }
    }
}

/**
 * 隐藏Banner
 */
function hideBanner(){
    $('.logo').css("display", "none");
    $('.header').css("height", "41");
}

function min_width() {
    $('.wrapper').css('min-width', '1200px')
}


function min_width2() {
    $('.wrapper').css('min-width', '1200px')
}


function ifenone() {
    $('.back_beiji').css('display', 'none')
}


function ifeblick() {
    $('.back_beiji').css('display', 'block')
}

function msgProgressTip(info) {
    $.messager.progress({
        msg: info
    });
}

function msgProgressTipClose() {
    $.messager.progress('close');
}

function msgAlertInfo(info) {
    $.messager.alert("提示", info, "info");
}

function msgAlertError(info) {
    $.messager.alert("提示", info, "error");
}

function msgAlertWarning(info) {
    $.messager.alert("提示", info, "warning");
}

/**
 * 在iframe中加载某个页面
 * @param url
 */
function loadPage(url){
    $("#iframe").attr("src", url);
}