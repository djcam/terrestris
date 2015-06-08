var Pagination = function (selector) {
	var _csrfToken = '',
	    _csrfHeader = '',
	    _headers = {};

	var _$table = $(selector);

	var refreshReport = function (data) {
		var headers = [
			{field:'email', display:'E-mail', link: 1}, 
			{field:'first', display:'First', link: 0}, 
			{field:'last', display:'Last', link: 0}, 
			{field:'gender', display:'Gender', link: 0}, 
			{field:'status', display:'Status', link: 0}, 
			{field:'role', display:'Role', link: 0}
		];
		var rows = data.content;
			
		//Begin The Header
		var tableHtml = '<thead><tr>';
		for (var i = 0; i < headers.length; i++) {
           	tableHtml +=
                '<th class="' + 
                	(i === 0 ? 'first' : '') + 
                	(i === headers.length - 1 ? 'last' : '') +
				'">' + 
					headers[i].display +
				'</th>';
		}
        tableHtml += '</tr></thead>';
        
        //Begin The Body
        tableHtml += '<tbody>';
		for (var i = 0; i < rows.length; i++) {
			var fields = rows[i],
				classes = (i === 0 ? 'first ' : '') +
					(i === rows.length - 1 ? 'last ' : '') +
					(i % 2 === 0 ? 'even' : 'odd');
			console.log(fields);
            tableHtml += '<tr class="' + classes + '">';

            //Begin The Data Cells
            for (var j = 0; j < headers.length; j++) {
            	tableHtml +=
                	'<td class="' + (j === 0 ? 'first' : '') + 
                		(i === headers.length - 1 ? 'last' : '') +
					'">';	
                if (headers[j].link === 1) {
                	tableHtml += '<a href="/user/' + fields.uid + '">' +
                		fields[headers[j].field] +
                	'</a>';
                } else {
					tableHtml += fields[headers[j].field];
                }
                tableHtml += '</td>';
            }
            tableHtml += '</tr>';
		}
		tableHtml += '</tbody>';

		_$table.html(tableHtml);
	};

    var getData = function (start, pageSize) {
         var params = {
            start: start,
            pageSize: pageSize
        };

        $.ajax({
            type: 'POST',
            url: '/api/users',
            headers: _headers,
            data: params,
            dataType: 'JSON',
            success: function (data) {
            	console.log(data);
                refreshReport(data);
            },
            error: function (data) {
                console.log(data.responseText);
            },
            complete: function (data) {
                console.log('Complete');
            }
        });
    };

	var init = function () {
		_csrfToken = $("meta[name='_csrf']").attr("content");
        _csrfHeader = $("meta[name='_csrf_header']").attr("content");
        _headers[_csrfHeader] = _csrfToken;

		$('#users_form button').on('click', function (e) {
			e.preventDefault();
			console.log('whatever');
			getData($('#start').val(), $('#page_size').val());
		});
	};

	init();
};

$( function () {
	var pagination = new Pagination('.t-table');
});