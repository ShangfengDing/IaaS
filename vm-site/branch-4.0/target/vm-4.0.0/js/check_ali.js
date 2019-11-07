/**
 * Created by rain on 2016/10/11.
 * 检查阿里云的名称和描述是否合法
 */

//检查阿里云名称，名称，不填则为空，默认值为空，[2, 20] 英文或中文字符，
//必须以大小字母或中文开头，可包含数字，”.”，”_”或”-”，磁盘名称会展示在控制台。
//不能以 http:// 和 https:// 开头
function checkName(name) {
    if (name.length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得为空'});
        return false;
    }
    if (name.length > 20){
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得超过20字'});
        return false;
    }
    //匹配http://和https://
    var Gnum1 = name.search("^https://|http://");
    if (Gnum1 == 0) {
        $.fillTipBox({type: 'danger', icon: "glyphicon-alert", content: '名称命名不合法!'});
        return false;
    }
    //必须以大小字母或中文开头
    var Gnum2 = name.search("^[a-zA-Z]|[\u4e00-\u9fa5]");
    if (Gnum2 == 0) {
        return true;
    } else {
        $.fillTipBox({type: 'danger', icon: "glyphicon-alert", content: '名称命名不合法!'});
        return false;
    }
}
//安全组描述信息，[2, 100] 个字符。不能以 http:// 和 https:// 开头。
function checkDescription(description) {
    if (description.length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得为空'});
        return false;
    }
    if (description.length > 20){
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得超过20字'});
        return false;
    }
    //匹配http://和https://
    var Gnum = description.search("^https://|http://");
    if (Gnum == 0) {
        $.fillTipBox({type: 'danger', icon: "glyphicon-alert", content: "描述命名不合法!"});
        return false;
    } else {
        return true;
    }
}