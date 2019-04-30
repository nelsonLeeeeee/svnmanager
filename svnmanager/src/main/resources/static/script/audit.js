/**
 * 跳转到查看详细信息(修改)页面
 * @param referId
 */
function getReferInfo(referId) {
    window.location.href = "/audit/api/getReferInfoById?referId="+referId;
}

/**
 * 审批
 * @param referId
 * @param status
 */
function audit(referId,status) {
    var userName = getCookieValue("userName");
    var data = {"referId":referId,"status":status,"auditor":userName};
    var url = "/audit/api/audit";

    $.ajax({
        type : "post",
        url : url,
        data : data,
        dataType : "json",
        async : true,
        success: function (result) {
            if(result.status == "OK"){
                window.location.href="/audit/api/getRefers";
            }
        }
    });

}

function getCookieValue(name){
    var strcookie = document.cookie;//获取cookie字符串
    var arrcookie = strcookie.split("; ");//分割
    //遍历匹配
    for ( var i = 0; i < arrcookie.length; i++) {
        var arr = arrcookie[i].split("=");
        if (arr[0] == name){
            return arr[1];
        }
    }
    return "";
}