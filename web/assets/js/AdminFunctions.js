$(function () {
    $("#comboOrdenation").on('change', function(){
        $("#formListClient").submit();
    });
    $("input[type=radio][name=direction]").change(function() {
        $("#formListClient").submit();
    });
});
