$( function () {

    $("#login_form input").keypress( function (e) {
        if (e.which == 13) {
            e.preventDefault();
            $("#login_form").submit();
        }
    });
});
