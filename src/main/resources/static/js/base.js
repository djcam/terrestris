var _csrfToken = '',
    _csrfHeader = '',
    _headers = {};


var Timer = function (refill, rate, id, callback) {
    var _min = 0,
        _sec = 0,
        _hour = 0,
        _rate = (rate || 0),
        _refill = (refill || 0),
        _update = 1000,
        _interval = null,
        _$timer = $('#' + id),
        _callback = callback;

    var pad = function (n, width, z) {
        z = z || '0';
        n = n + '';
        return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
    }

    var resetTimer = function () {
        clearInterval(_interval);
    }

    var updateVals = function () {
        if (_rate >= 60 * 60) {
        	_$timer.find('.t_hour').html(_hour);
			_$timer.find('.t_min').html(pad(_min, 2, '0'));
        } else {
        	_$timer.find('.t_min').html(_min);
        }
        _$timer.find('.t_sec').html(pad(_sec, 2, '0'));
    };

    var updateTimer = function (refill) {
        var d1 = refill,
            d2 = new Date();

        var mod = ((d2 - d1) / 1000) % _rate,
            twait = _rate - mod;
        _hour = Math.floor(twait / (60 * 60));
        _min = Math.floor((twait - (_hour * (60 * 60))) / 60);
        _sec = Math.round(twait % 60, 0);

        updateVals();
    };

    var startTimer = function () {
        _interval = setInterval( function () {
            _sec--;
            if (_sec < 0) {
                _min--;
                if (_min < 0) {
                    _hour--;
                    if (_hour < 0) {
                    	callback();
                    } else {
                    	_min = 59;
                    	_sec = 59;
                    }
                } else {
                    _sec = 59;
                }
            }

            if (_hour >= 0) {
                updateVals();
            }
        }, _update);
    };

    var init = function () {
        updateTimer(_refill);
        startTimer();
    };

    init();

    return {
        resetTimer: resetTimer,
        updateTimer: updateTimer,
        startTimer: startTimer
    };
};

$( function () {
    _csrfToken = $("meta[name='_csrf']").attr("content");
    _csrfHeader = $("meta[name='_csrf_header']").attr("content");
    _headers[_csrfHeader] = _csrfToken;
});