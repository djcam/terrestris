var Font = function () {
	var _timer = null;

	var Timer = function (refill, rate, inStat, timerId) {
	    var _$timer = $(timerId),
	    	_min = 0,
	        _sec = 0,
	        _stat = inStat || 'mana',
	        _rate = rate || 0,
	        _refill = refill || 0,
	        _update = 1000,
	        _interval = null;

	    var pad = function (n, width, z) {
	        z = z || '0';
	        n = n + '';
	        return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
	    }

	    var replenishStats = function (stat, type) {
	        clearInterval(_interval);
	        var params = {stat: $.trim(stat.toLowerCase()), type: type};
	        $.ajax({
	            type: 'POST',
	            url: '/api/profile/stats',
	            headers: _headers,
	            data: params,
	            dataType: 'JSON',
	            success: function (data) {
	                timerCallback(data);
	            },
	            error: function (data) {
	                console.log(data.responseText);
	            }
	        });
	    };

	    var updateVals = function () {
	        _$timer.find('#t_min').html(_min);
	        _$timer.find('#t_sec').html(pad(_sec, 2, '0'));
	    };

	    var timerCallback = function (data) {
	        var statData = data.statMap[_stat.toUpperCase()];
	        clearInterval(_interval);
	        updateTimer(new Date(statData.refill));	            
			if (statData.value === statData.max) {
	            startTimer();
	        } else {
	            startTimer();
	        }
        	$('#font_inactive').removeClass('active');
        	$('#font_inactive').addClass('inactive');
        	$('#font_replenish').removeClass('inactive');
        	$('#font_replenish').addClass('active');
	    };

	    var updateTimer = function (refill) {
	        var d1 = refill,
	            d2 = new Date();
	        var mod = ((d2 - d1) / 1000) % _rate,
	            twait = _rate - mod;
	        _min = Math.floor(twait / 60);
	        _sec = Math.round(twait % 60, 0);

	        updateVals();
	    };

	    var startTimer = function () {
	        _interval = setInterval( function () {
	            _sec--;
	            if (_sec < 0) {
	                _min--;
	                if (_min < 0) {
	                	$('#font_inactive').removeClass('inactive');
	                	$('#font_inactive').addClass('active');
	                	$('#font_replenish').removeClass('active');
	                	$('#font_replenish').addClass('inactive');
	                } else {
	                    _sec = 59;
	                }
	            }

	            if (_min >= 0) {
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
	        replenishStats: replenishStats
	    };
	};


	var init = function () {
		var refill = new Date($('#font_timer').data('refill')),
			rate = parseInt($('#font_timer').data('rate'));
		_timer = new Timer(refill, rate, 'mana', '#font_timer');
		
		$('#font_replenish').on('click', function (e) {
			_timer.replenishStats('mana', 0);
		});
	}

	init();
};

$( function () {
	var font = new Font();
});