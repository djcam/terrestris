$( function () {

    $(".pricing-header").on('click', function (e) {
        var pid = parseInt($(this).data('pid'));
        $('#profile_form #pid').val(pid);
        $('#profile_form').submit();
    });

})
