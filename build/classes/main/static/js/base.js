var _csrfToken = '',
    _csrfHeader = '',
    _headers = {};


$( function () {
    _csrfToken = $("meta[name='_csrf']").attr("content");
    _csrfHeader = $("meta[name='_csrf_header']").attr("content");
    _headers[_csrfHeader] = _csrfToken;
});