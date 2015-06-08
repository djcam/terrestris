var Bounty = function () {
	var _bountyParams = {
		url: null,
		data: null,
		method: null,
		callback: null
	};

	var postBounty = function (params) {
        $.ajax({
            type: params.method,
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

	var createCallback = function (data) {
		console.log(data);
		$('#bounties_list li.status_3').remove();
		$('#new_bounties, .empty_list').slideUp();
		
		var odd = $('#bounties_list li:last-child').hasClass('even'),
			rows = '';
		
		for (var i = 0; i < data.length; i++) {
			var bounty = data[i];
			rows += 
				'<li class="bounty_item white_grad status_' + bounty.status + '" ' +
				    'data-bid=' + bounty.bid + '">' + 		
	    			'<div class="bounty_item_title clearfix">' +
						'<div class="table_title_section title_left">' +
							'<span class="capitalize">' + bounty.bname + '</span>' +
						'</div>' +	
						'<div class="table_title_section title_right">' +
							'<span>' + bounty.value + '</span>' +
							'<span class="icon icon-heart-broken"></span>' +
						'</div>' +
					'</div>' +
					'<div class="bounty_location">' +
						'<span class="bounty_road">' + bounty.xpos.rname + '</span>' +
						'<span class="bounty_conjunction">and</span>' +
						'<span class="bounty_road">' + bounty.ypos.rname + '</span>' +
					'</div>' +
					'<ul class="bounty_stats_list">' +
						'<li class="bounty_stat clearfix">' +
			    			'<label>Stamina</label>' +
			    			'<span>' + bounty.sp + '/' + bounty.maxsp + '</span>' +
			    		'</li>' +
			    		'<li class="bounty_stat clearfix">' +
			    			'<label>Evasion</label>' +
			    			'<span>' + bounty.evasion + '</span>' +
			    		'</li>' +
			    		'<li class="bounty_stat clearfix">' +
			    			'<label>Mvcost</label>' +
			    			'<span>' + bounty.mvcost + '</span>' +
			    		'</li>' +
						'<li class="bounty_stat clearfix">' +
			    			'<label>Experience</label>' +
			    			'<span>' + bounty.xp + '</span>' +
			    		'</li>' +
			    	'</ul>' +
	 		    	'<span class="bounty_status_wrap">' +
	 		    		(bounty.status === 2 ?
		    				'<form action="/bounty/cash" method="post">' +
					        	'<input type="hidden" value="' + bounty.bid + '" name="bid" />' +
					            '<button class="icon_button green_grad" type="submit">' +
					            	'<label>Cash</label>' +
					            	'<span class="icon icon-download"></span>' +
					            '</button>' +
					    	'</form>'
					   	: 	(
					   		bounty.status === 1 ? 
						   		'<div class="bounty_status active clear_fix">' +
			    					'<label>Active</label>' +
			    					'<span class="icon icon-target"></span>' +
						    	'</div>'
					    	:
							    '<div class="bounty_status cashed clearfix">' +
							    	'<label>Cashed</label>' +
				    				'<span class="icon icon-checkmark"></span>' +
							    '</div>'
							)
					   	) +
				    '</span>' +
		    	'</li>';
		}
		$('#bounties_list').append(rows);
		var i = 0;
		$('#bounties_list > li').each( function () {
			$(this).removeClass('odd').removeClass('even');
			$(this).addClass((i % 2 === 0 ? 'odd' : 'even'));
			i++;
		});
	};

	var engageCallback = function (data) {
		console.log(data);
		if (data.moved) {
			$('#engage_wrapper').html('The bounty has moved. Hunt it down.');
		} else {
			if (typeof data.errors == 'undefined' || data.errors === null || data.errors.length < 1) {
				$('#engage_wrapper').html('Got it.');
			} else {
				console.log(data.errors);
			}
		}
	};

	var init = function () {
		$('#engage_wrapper').on('click', '.engage_action', function (e) {
			e.preventDefault();
			_bountyParams.data = { 
				bid: parseInt($('#engage_info').data('bid')),
				etype: parseInt($(this).data('etype'))
			};
			_bountyParams.method = 'POST';
			_bountyParams.url = 'engage';
			_bountyParams.callback = engageCallback;
			postBounty(_bountyParams);
		});

		$('#new_bounties').on('click', function (e) {
			e.preventDefault();
			_bountyParams.data = null;
			_bountyParams.method = 'GET';
			_bountyParams.url = 'create';
			_bountyParams.callback = createCallback;
			postBounty(_bountyParams);
		});
	};

	init();
};

$( function () {
	var bounty = new Bounty();
});