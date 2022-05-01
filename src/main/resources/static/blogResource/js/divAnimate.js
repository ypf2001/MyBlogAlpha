$('#div-append').hover(function () {

    $("#user-logo").stop(true, true).animate({"width": "80px", "height": "80px"}, 200);
    $(this).find('#logo-fram').stop(true,true).fadeIn(200);
}, function () {
    $("#user-logo").stop(true, true).animate({"width": "50px", "height": "50px"}, 200)
    $(this).find('#logo-fram').stop(true,true).fadeOut(200);


})