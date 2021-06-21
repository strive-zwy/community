function postComm(questionId,type) {
    var content;
    if (type === 1){
        content = $("#content_id").val();
    }else{
        content = $("#"+questionId+"comm_content_id").val();
    }
    console.log(type);
    console.log(content);
    $.ajax({
        type : "POST",
        dataType : "json",
        url : "/comment",
        contentType : "application/json",
        data : JSON.stringify({
            "parentId" : questionId,
            "content" : content,
            "type" : type
        }),
        success:function (response) {
            if (response.code === 200){
                if (type === 1){
                    $("#comment_section").hide();
                }else{
                    $("#"+questionId+"_show").hide();
                    $("#"+questionId+"_icon_right").attr("value","show");
                }
                window.location.reload();
            }else {
                if (response.code === 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        // window.open("https://github.com/login/oauth/authorize?client_id=d85ef952e55a5e761214&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.open("https://github.com/login/oauth/authorize?client_id=d85ef952e55a5e761214&redirect_uri=http://114.215.208.150:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message)
                }
            }
            console.log(response);
        }
    });
}

function like(cId) {
    console.log(cId);

}

function isShow(commId) {
    var isShow = $("#"+commId+"_icon_right").attr("value");
    if (isShow === "show"){
        $("#"+commId+"_show").show();
        $("#"+commId+"_icon_right").attr("value","hide");
    }else{
        $("#"+commId+"_show").hide();
        $("#"+commId+"_icon_right").attr("value","show");
    }
}

function showTagList() {
    $("#tag_list").show();
}

function hideTagList() {
    $("#tag_list").hide();
}

function addTag(newTag) {
    var tagValue = $("#tag").val();
    var tags = tagValue.split("、");
    if (tagValue){
        for (var i = 0; i < tags.length;i++){
            if (tags[i] === newTag){
                return;
            }
        }
        $("#tag").val(tagValue + "、" + newTag);
    }else{
        $("#tag").val(newTag);
    }
}

var commonUtil = {
    /**
     * 弹出消息框
     * @param msg 消息内容
     * @param type 消息框类型（参考bootstrap的alert）
     */
    alert: function(msg, type){
        if(typeof(type) =="undefined") { // 未传入type则默认为success类型的消息框
            type = "success";
        }
        // 创建bootstrap的alert元素
        var divElement = $("<div></div>").addClass('alert').addClass('alert-'+type).addClass('alert-dismissible').addClass('col-md-4').addClass('col-md-offset-4');
        divElement.css({ // 消息框的定位样式
            "position": "absolute",
            "top": "100px",
            "left":"30%"
        });
        divElement.text(msg); // 设置消息框的内容
        // 消息框添加可以关闭按钮
        var closeBtn = $('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
        $(divElement).append(closeBtn);
        // 消息框放入到页面中
        $('body').append(divElement);
        return divElement;
    },

    /**
     * 短暂显示后上浮消失的消息框
     * @param msg 消息内容
     * @param type 消息框类型
     */
    message: function(msg, type) {
        var divElement = commonUtil.alert(msg, type); // 生成Alert消息框
        var isIn = false; // 鼠标是否在消息框中

        divElement.on({ // 在setTimeout执行之前先判定鼠标是否在消息框中
            mouseover : function(){isIn = true;},
            mouseout : function(){isIn = false;}
        });

        // 短暂延时后上浮消失
        setTimeout(function() {
            var IntervalMS = 20; // 每次上浮的间隔毫秒
            var floatSpace = 60; // 上浮的空间(px)
            var nowTop = divElement.offset().top; // 获取元素当前的top值
            var stopTop = nowTop - floatSpace;  // 上浮停止时的top值
            divElement.fadeOut(IntervalMS * floatSpace); // 设置元素淡出

            var upFloat = setInterval(function(){ // 开始上浮
                if (nowTop >= stopTop) { // 判断当前消息框top是否还在可上升的范围内
                    divElement.css({"top": nowTop--}); // 消息框的top上升1px
                } else {
                    clearInterval(upFloat); // 关闭上浮
                    divElement.remove();  // 移除元素
                }
            }, IntervalMS);

            if (isIn) { // 如果鼠标在setTimeout之前已经放在的消息框中，则停止上浮
                clearInterval(upFloat);
                divElement.stop();
            }

            divElement.hover(function() { // 鼠标悬浮时停止上浮和淡出效果，过后恢复
                clearInterval(upFloat);
                divElement.stop();
            },function() {
                divElement.fadeOut(IntervalMS * (nowTop - stopTop)); // 这里设置元素淡出的时间应该为：间隔毫秒*剩余可以上浮空间
                upFloat = setInterval(function(){ // 继续上浮
                    if (nowTop >= stopTop) {
                        divElement.css({"top": nowTop--});
                    } else {
                        clearInterval(upFloat); // 关闭上浮
                        divElement.remove();  // 移除元素
                    }
                }, IntervalMS);
            });
        }, 1500);
    }
};
