/**
 * Created by valmir.massoni on 01/12/2016.
 */
$(function () {
    $("#btnLogin").click(function () {
        $("#loader").attr("style", "visibility: visible");
        var data = $("#formLogin").serialize();
        $.ajax({
            url: "/AutoBank/web/login-collaborator",
            type: "POST",
            data: data,
            success: function (data, text) {
                $(location).attr("href", "/AutoBank/main.jsp");
            },
            error: function (request, status, error) {
                $("#loader").attr("style", "visibility: hidden");
                if(status.status == 501) {
                    $("#error").attr("style", "display: block");
                }
            }

        });
    })
});