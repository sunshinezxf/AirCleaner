var RADIUS = 4;
var WINDOW_WIDTH = $("body").width();
var WINDOW_HEIGHT = $("body").height();

var canvas = document.getElementById("mycanvas");
canvas.width = WINDOW_WIDTH;
var ctx = canvas.getContext("2d");
var count;
var forbidden = false;

$(document).ready(function () {
    showImage();
    initialPage();
    count = setInterval(function () {
        image_slider();
    }, 3000);


    $('#customer_phone').blur(function () {
        var phone = $("#customer_phone").val();
        if (!(/^1[3|4|5|7|8]\d{9}$/.test(phone))) {
            $("#dialog").find(".weui_dialog_bd").html("手机号码有误，请重填！");
            $("#dialog").fadeIn();
            $("#customer_phone").val("");
            inactive();
        }
    });

    check_num();

    $('#province').citySelect({
        url: '${path.concat("/plugins/jquery/city.min.js")}',
        required: false,
        nodata: 'none',//当子集无数据时，隐藏select
    });

    $("#prov").change(function () {
        $("#city").empty();
        $("#dist").empty();
        if (addressInfo_validate()) {
            active();
        } else {
            inactive();
        }
    });

    $("#city").change(function () {
        $("#dist").empty();
        if (addressInfo_validate()) {
            active();
        } else {
            inactive();
        }
    });

    $("#dist").change(function () {
        if (addressInfo_validate()) {
            active();
        } else {
            inactive();
        }
    });
})


$("#image-slider").on("touchstart", function (e) {
    var touch = e.originalEvent;
    var startX = touch.changedTouches[0].pageX;
    $("#image-slider").on("touchmove", function (e) {
        e.preventDefault();
        touch = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];
        if (touch.pageX - startX > 10) {
            $("#image-slider").off("touchmove");
            clearInterval(count);
            image_slider();
            count = setInterval(function () {
                image_slider();
            }, 3000);
        } else if (touch.pageX - startX < -10) {
            $("#image-slider").off("touchmove");
            clearInterval(count);
            reverse_image_slider();
            count = setInterval(function () {
                image_slider();
            }, 3000);
        }
    });
}).on("touchend", function (e) {
    $("#image-slider").off("touchmove");
})

$('#purchase').click(function () {
    forbidden = true;
    $.toggleNumModifySheet();
});

$('#customer_name').on('input propertychange', function () {
    if (addressInfo_validate()) {
        active();
    } else {
        inactive();
    }
});

$('#customer_phone').on('input propertychange', function () {
    if (addressInfo_validate()) {
        active();
    } else {
        inactive();
    }
});


$('#customer_address').on('input propertychange', function () {
    if (addressInfo_validate()) {
        active();
    } else {
        inactive();
    }
});

$('#min_btn').click(function () {
    var num = $('#goods_num').val();
    var goods_price = $("#goods_price").html();
    num--;
    $('#goods_num').attr('value', num);
    check_num();
    $('#total_num').text(num);
    $('#total_price').text(num * goods_price + '元');
});

$('#add_btn').click(function () {
    var num = $('#goods_num').val();
    var goods_price = $("#goods_price").html();
    num++;
    $('#goods_num').attr('value', num);
    check_num();
    $('#total_num').text(num);
    $('#total_price').text(num * goods_price + '元');
});

$("#dialog").find(".weui_btn_dialog.primary").click(function () {
    $("#dialog").hide();
});


function image_slider() {
    for (var i = 0; i < $(".slider").length; i++) {
        if ($(".slider").eq(i).hasClass("active")) {
            $(".slider").eq(i).removeClass("active");
            $(".slider").eq(i).fadeOut("fast", function () {
                clearPage();
                if ((i + 1) == $(".slider").length) {
                    $(".slider").eq(0).addClass("active");
                    $(".slider").eq(0).fadeIn("fast");
                } else {
                    $(".slider").eq(i + 1).addClass("active");
                    $(".slider").eq(i + 1).fadeIn("fast");
                }
                initialPage();
            });
            break;
        }
    }
}

function reverse_image_slider() {
    for (var i = 0; i < $(".slider").length; i++) {
        if ($(".slider").eq(i).hasClass("active")) {
            $(".slider").eq(i).removeClass("active");
            $(".slider").eq(i).fadeOut("fast", function () {
                clearPage();
                if ((i - 1) < 0) {
                    $(".slider").eq($(".slider").length - 1).addClass("active");
                    $(".slider").eq($(".slider").length - 1).fadeIn("fast");
                } else {
                    $(".slider").eq(i - 1).addClass("active");
                    $(".slider").eq(i - 1).fadeIn("fast");
                }
                initialPage();
            });
            break;
        }
    }
}

function showImage() {
    $(".slider.active").show();
    $(".slider:not(.active)").hide();
}

function initialPage() {
    var image_width = $("#canvas").width();
    var image_height = $("#mycanvas").height();
    var num = $(".slider").length;
    var remain_width = image_width - (RADIUS + 5) * 2 * num;
    for (var i = 0; i < num; i++) {
        ctx.beginPath();
        ctx.arc(remain_width / 2 + (i * 2 + 1) * (RADIUS + 5), image_height - 20, RADIUS, 0, 2 * Math.PI);
        ctx.closePath();
        if ($(".slider").eq(i).hasClass("active")) {
            ctx.fillStyle = "#21BBF0";
        } else {
            ctx.fillStyle = "#ccc";
        }
        ctx.fill();
    }
}

function clearPage() {
    ctx.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
}

$.toggleNumModifySheet = function () {
    var number_div = $('#number_div');
    var weuiActionsheet = $('#weui_actionsheet');
    weuiActionsheet.addClass('weui_actionsheet_toggle');
    number_div.show()
        .focus()
        .addClass('weui_fade_toggle').one('click', function () {
            forbidden = false;
            hideActionSheet(weuiActionsheet, number_div);
        });
    number_div.unbind('transitionend').unbind('webkitTransitionEnd');

    function hideActionSheet(weuiActionsheet, number_div) {
        weuiActionsheet.removeClass('weui_actionsheet_toggle');
        number_div.removeClass('weui_fade_toggle');
        number_div.on('transitionend', function () {
            number_div.hide();
        }).on('webkitTransitionEnd', function () {
            number_div.hide();
        })
    }
}

function check_num() {
    var goodsNum = $('#goods_num').val();
    if (goodsNum == 1) {
        $('#min_btn').attr("disabled", "disabled");
        $('#min_btn').addClass("btn_disabled");
    } else {
        $('#min_btn').removeAttr("disabled");
        $('#min_btn').removeClass("btn_disabled");
    }
}

function addressInfo_validate() {
    var name = $('#customer_name').val();
    var phone = $('#customer_phone').val();
    var address = $('#customer_address').val();
    if (second_time_purchase) {
        address = $('#history_address').val();
    }
    var area;
    if ($("#prov").val() == "北京" || $("#prov").val() == "天津" || $("#prov").val() == "上海" || $("#prov").val() == "重庆" || $("#prov").val() == "香港" || $("#prov").val() == "澳门" || $("#prov").val() == "台湾") {
        area = $("#city").val();
    } else {
        if ($("#prov").val() == "国外") {
            area = $("#prov").val();
        } else {
            area = $("#dist").val();
        }
    }
    if (not_null(name) && not_null(phone) && not_null(address) && (not_null(area) || second_time_purchase)) {
        return true;
    } else {
        return false;
    }
}

function active() {
    $("#confirm").removeAttr("disabled");
    $("#confirm").removeClass("weui_btn_disabled");
    $("#confirm").removeClass("weui_btn_default");
    $("#confirm").addClass("weui_btn_primary");
}

function inactive() {
    $("#confirm").attr("disabled", "disabled");
    $("#confirm").removeClass("weui_btn_primary");
    $("#confirm").addClass("weui_btn_disabled");
    $("#confirm").addClass("weui_btn_default");
}

function not_null(item) {
    if (item == null || item == "" || item.length <= 0) {
        return false;
    }
    return true;
}


document.addEventListener("touchmove", function (e) {
    if (forbidden) {
        e.preventDefault();
        e.stopPropagation();
    }
}, false);