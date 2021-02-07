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
