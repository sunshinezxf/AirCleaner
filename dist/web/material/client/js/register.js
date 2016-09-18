$(document).ready(function(){
	$("#first_step").show();
    $("#second_step").hide();


    $("#phone").on("input propertychange",function(){
        if(first_validate())
            first_active();
        else
            first_inactive();
    });

    $("#name").on("input propertychange",function(){
        if(second_validate())
            second_active();
        else
            second_inactive();
    });

    $("#gender").change(function(){
        if(second_validate())
            second_active();
        else
            second_inactive();
    });

    $("#address").on("input propertychange",function(){
        if(second_validate())
            second_active();
        else
            second_inactive();
    });

})

$("#next_btn").click(function(){
    var phone = $("#phone").val();
    if (!(/^1[3|4|5|7|8]\d{9}$/.test(phone))) {
        $("#dialog").find(".weui_dialog_bd").html("手机号码有误，请重填！");
        $("#dialog").fadeIn();
        $("#phone").val("");
        first_inactive();
    }else{
        // var url="";
        // $.get(url,function(result){

        // })
        $("#first_step").hide();
        $("#second_step").show();
    }
});

$("#register_btn").click(function(){
    // var url="";
    $("#equipment_info_form").attr("action", url);
    $("#equipment_info_form").attr("method", "post");
    $("#equipment_info_form").submit();
});

$("#dialog").find(".weui_btn_dialog.primary").click(function () {
    $("#dialog").hide();
});

function first_validate(){
    var phone=$("#phone").val();
    if(not_null(phone))
        return true;
    return false;
}

function first_active() {
    $("#next_btn").removeAttr("disabled");
    $("#next_btn").removeClass("weui_btn_disabled");
    $("#next_btn").removeClass("weui_btn_default");
    $("#next_btn").addClass("weui_btn_primary");
}

function first_inactive() {
    $("#next_btn").attr("disabled", "disabled");
    $("#next_btn").removeClass("weui_btn_primary");
    $("#next_btn").addClass("weui_btn_disabled");
    $("#next_btn").addClass("weui_btn_default");
}

function second_validate(){
    var name=$("#name").val();
    var gender=$("#gender").val();
    var address=$("#address").val();
    if(not_null(name)&&not_null(gender)&&not_null(address))
        return true;
    return false;
}

function second_active() {
    $("#register_btn").removeAttr("disabled");
    $("#register_btn").removeClass("weui_btn_disabled");
    $("#register_btn").removeClass("weui_btn_default");
    $("#register_btn").addClass("weui_btn_primary");
}

function second_inactive() {
    $("#register_btn").attr("disabled", "disabled");
    $("#register_btn").removeClass("weui_btn_primary");
    $("#register_btn").addClass("weui_btn_disabled");
    $("#register_btn").addClass("weui_btn_default");
}

function not_null(item) {
    if (item == null || item == "" || item.length <= 0) {
        return false;
    }
    return true;
}
