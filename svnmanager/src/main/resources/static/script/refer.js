/**
 * 打开添加申请页面
 */
function openAddRefer() {
    window.location= "/refer/api/toAddReferPage";
}

/**
 * 添加申请
 */
function refer() {

    var userName = document.getElementById("userName").value;

    if(document.cookie.length > 0){
        userName = getCookieValue("userName");
    }

    var path = document.getElementById("path").value;
    var remark = document.getElementById("remark").value;
    var url = "/refer/api/addRefer";
    var data = {"userName":userName,"path":path,"remark":remark};
    $.ajax({
        type : "post",
        url : url,
        data : data,
        dataType : "json",
        async : true,
        success: function (result) {
            if(result.status == "OK"){
                alert("提交成功");
                window.location.href="/refer/api/findReferInfo?userName="+userName;
            }
        }
    });

}

/**
 * 重新提交申请
 */
function reRefer() {
    var referId = document.getElementById("referId").value;
    var userName = document.getElementById("userName").value;
    var path = document.getElementById("path").value;
    var remark = document.getElementById("remark").value;
    var url = "/refer/api/reRefer";
    var data = {"referId":parseFloat(referId),"path":path,"remark":remark};

    $.ajax({
        type : "post",
        url : url,
        data : data,
        dataType : "json",
        async : true,
        success: function (result) {
            if(result.status == "OK"){
                alert("提交成功");
                window.location.href="/refer/api/findReferInfo?userName="+userName;
            }
        },
        error: function () {
            alert("提交失败");
        }
    });
}

/**
 * 跳转到查看详细信息(修改)页面
 * @param referId
 */
function getReferInfo(referId) {
    window.location.href = "/refer/api/getReferInfoById?referId="+referId;
}

/**
 * 删除申请信息，跳转到当前页面
 * @param referId
 */
function deleteRefer(referId) {
    if(!confirm("确定删除？")){
         return;
    }

    var url = "/refer/api/deleteRefer";
    var data = {"referId":referId};
    $.ajax({
        type : "post",
        url : url,
        data : data,
        dataType : "json",
        async : true,
        success: function (result) {
            if(result.status == "OK"){
                alert("删除成功");
                location.reload();
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

function getFinishReferInfo(referId) {
    window.location.href = "/refer/api/getFinishReferInfo?referId="+referId;
}