
//提交评论
function post() {
    var questionId =$("#question_id").val();
    var content = $("#content").val();

    if(!content){
        alert("评论不能为空哦");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type" :1
        }),success:function (response) {

            if(response.code==200){
              window.location.reload();
            }else {
                if(response.code=201){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=656d9511cb760dfc72fc&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }

            }
            console.log(response);
        },
        dataType:"json"
    });
}

//开闭二级评论
function collapseComment(e) {
    var id =e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    var collapse=e.getAttribute("data-collapse");
    if(collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
    }else {
        comments.addClass("in");
        e.setAttribute("data-collapse","in");
    }

}
//选择标签
function selectTag(e) {
    value=e.getAttribute("data-tag");
    var previous=$("#tag").val();
    if(previous.indexOf(value) == -1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value)
        }
    }
}
//展示标签组
function showSelectTag() {
    $("#SelectTag").show();
}