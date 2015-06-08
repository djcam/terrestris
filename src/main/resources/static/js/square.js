var Square = function () {
	var _bountyParams = {
		url: null,
		data: { bid: null },
		callback: null
	};

	var bountyCallback = function (data) {
		var bounty = data.bounty,
			profile = data.profile,
			bountyHtml = '',
			message = '';

		console.log(data);
		if (bounty.pid.pid == profile.pid) {
			if (bounty.complete == 1) {
				if (bounty.cashed == 1) {
					bountyHtml = '<span>Cashed</span>';
				} else {
					bountyHtml = '<a href="#" class="bounty_cash">Cash</a>';
				}
			} else {
				if (bounty.xpos.rid == profile.xpos.rid && bounty.ypos.rid == profile.ypos.rid) {
					bountyHtml = '<a href="#" class="bounty_strike">Strike</a>' +
							     '<a href="#" class="bounty_cast">Cast</a>';
				} else {
					bountyHtml = '<span>The ' + bounty.bname + ' has moved! Hunt it down.</span>'
				}
			}
		}

		$('#bounty_list > li[data-bid="' + bounty.bid + '"] > .bounty_action').html(bountyHtml);
	};

	var postBounty = function (params) {
        $.ajax({
            type: 'POST',
            url: '/api/bounty/' + params.url,
            headers: _headers,
            data: params.data,
            dataType: 'JSON',
            success: function (data) {
                params.callback(data);
            },
            error: function (data) {
                console.log(data.responseText);
            },
            complete: function (data) {
            }
        });
	};

	var init = function () {
		$('#bounty_list').on('click', '.bounty_claim', function (e) {
			e.preventDefault();
			_bountyParams.data.bid = parseInt($(this).parent().parent().data('bid'));
			_bountyParams.url = 'claim';
			_bountyParams.callback = bountyCallback;
			postBounty(_bountyParams);
		});

		$('#bounty_list').on('click', '.bounty_strike', function (e) {
			e.preventDefault();
			_bountyParams.data.bid = parseInt($(this).parent().parent().data('bid'));
			_bountyParams.url = 'strike';
			_bountyParams.callback = bountyCallback;
			postBounty(_bountyParams);
		});

		$('#bounty_list').on('click', '.bounty_cast', function (e) {
			e.preventDefault();
			_bountyParams.data.bid = parseInt($(this).parent().parent().data('bid'));
			_bountyParams.url = 'cast';
			_bountyParams.callback = bountyCallback;
			postBounty(_bountyParams);
		});

		$('#bounty_list').on('click', '.bounty_cash', function (e) {
			e.preventDefault();
			_bountyParams.data.bid = parseInt($(this).parent().parent().data('bid'));
			_bountyParams.url = 'cash';
			_bountyParams.callback = bountyCallback;
			postBounty(_bountyParams);
		});
	};

	init();
};



$( function () {

    var square = new Square();

});