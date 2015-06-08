var Shop = function () {

	var updateProfile = function (profile) {
		$('#triste_value').html(profile.triste);
	}

	var updateInventory = function (inventory, removed) {
		var odd = ($('#Sell').find('tr:last-child').hasClass('odd') ? true : false);
		for (var i = 0; i < inventory.length; i++) {
			var $row = $('#Sell').find('tr[data-iid="' + inventory[i].iid.iid + '"]');
			if ($row.length < 1) {
				var item = inventory[i].iid,
					inv = inventory[i];
					newRow = 
						'<tr class="' + (odd ? 'even' : 'odd') + '" data-iid="' + item.iid + '">' +
							'<td>' + item.item + '</td>' +
							'<td class="item_value" data-value="' + inv.price + '">' + inv.price + '</td>' +
							'<td class="item_count" data-count="' + inv.count + '">' + inv.count + '</td>' +
							'<td><form><div class="drop_wrapper clearfix">' +
								'<input type="hidden" name="type" value="' + inv.ACTION + '" />' +
								'<button class="drop_button">' + inv.ACTION + '</button>' +
								'<select class="drop_select" name="' + inv.invid + '">';
					for (var j = 1; j <= inv.count; j++) {
						var val = (inv.ACTION === 'Buy' ? j : inv.count - j + 1);
						newRow += '<option value="' + val + '">' + val + '</option>';
					}
					newRow += 	'</select></div></form></td>' +
							'<td class="total_target"><span>' +
								(inv.ACTION === 'Buy' ? inv.price : inv.price * inv.count) +
							'</span></td>'
						'</tr>';
				$('#Sell').find('tbody').append(newRow);
				odd = (odd ? false : true);
			} else {
				$select = $row.find('select.drop_select');
				$tdCount = $row.find('.item_count');
				
				var oldCount = $tdCount.data('count'),
					newCount = inventory[i].count;
				$tdCount.data('count', newCount);
				$tdCount.html(newCount);

				for (var i = oldCount + 1; i <= newCount; i++) {
					$select.prepend('<option value="' + i + '">' + i + '</option>');
				}
				$select.val(newCount);
			}
		}
		
		for (var i = 0; i < removed.length; i++) {
			console.log(removed[i]);
			$('#Sell').find('tr[data-iid="' + removed[i].iid + '"]').remove();
		}
	};

	var submitTransaction = function ($button) {
		var $form = $button.parents('form'),
			params = $form.serializeArray();
		console.log(params);
		$.ajax({
            type: 'POST',
            url: '/api/shop',
            headers: _headers,
            data: params,
            dataType: 'JSON',
            success: function (data) {
            	if (data.valid) {
            		console.log(data);
               		updateInventory(data.inventory, data.removed);
               		updateProfile(data.profile);
               	} else {
               		console.log(data.error);
               	}
            },
            error: function (data) {
                console.log(data.responseText);
            },
            complete: function (data) {
            }
        });
	};

	var updateTotal = function ($select) {
		var $row = $select.parents('tr'),
			$target = $row.find('td.total_target > span'),
			quantity = parseInt($select.val()),
			unitVal = $row.find('td.item_value').data('value');
		$target.html((quantity > 0 ? unitVal * quantity : ''));
	};

	var init = function () {
		$('table.inventory_table').on('change', 'select', function (e) {
			updateTotal($(this));
		});
		$('table.inventory_table').on('click', 'button.drop_button', function (e) {
			e.preventDefault();
			submitTransaction($(this));
		});
	};

	init();
};

$( function () {
	var shop = new Shop();
});