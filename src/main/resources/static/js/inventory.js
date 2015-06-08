var Inventory = function (type, id) {
	var _type = type,
		_$table = $('#' + id);
	
	var useItem = function (invid) {
		$.ajax({
			type: 'POST',
			url: '/api/inventory/use',
			headers: _headers,
			data: {invid: invid},
			success: function (data) {
				console.log(data);
			},
			error: function (data) {
				console.log(data);
			}
		});
	};
	
	var filterInventory = function ($item) {
		var newType = parseInt($item.data('type-id'));
		$item.parent().find('li').removeClass('active');
		$item.addClass('active');

		if (newType !== _type && newType >= 0) {
			_$table.find('tbody > tr[data-type-id!="' + newType + '"]').hide();
			_$table.find('tr[data-type-id="' + newType + '"]').show();
		} else if (newType !== _type && newType === -1) {
			_$table.find('tbody > tr').show()
		}
		_type = newType;
	};

	var init = function () {
		$('li.inventory_type_item').on('click', function (e) {
			e.preventDefault();
			filterInventory($(this));
		});
		$('.use_cell button').on('click', function (e) {
			e.preventDefault();
			useItem(parseInt($(this).data('invid')));
		});
	};

	init();
};

$( function () {
	var inventory = new Inventory(-1, 'inventory_table');
});