var Driver = function () {
    _map = null,
    _inventory = null,
    _bounty = null,
    _perks = null,
    _panels = null;

    var checkNull = function (obj) {
        if (typeof obj === 'undefined' || obj === null || obj === '') return true;
        return false;
    };

    /***********************************************/
    /******************* THE MAP *******************/
    /***********************************************/
    var Map = function () {
        var _log = [],
            _timer = null;

        var replenishStats = function (stat, type) {
            _timer.resetTimer();
            var stat = (stat || 'stamina'),
                type = (type === 0 ? 0 : (type || 1));
            
            var params = {stat: $.trim(stat.toLowerCase()), type: type};
            console.log(params);
            $.ajax({
                type: 'POST',
                url: '/api/profile/stats',
                headers: _headers,
                data: params,
                dataType: 'JSON',
                success: function (data) {
                    updateStats(data);
                    updateActivityLog(data.activityLog);
                },
                error: function (data) {
                    console.log(data.responseText);
                }
            });
        };

        var updateStats = function (data) {
            $('#profile_level').html(data.level);
            var statMap = data.statMap;
            $('div.stat_wrapper').each( function () {
                var statId = $(this).attr('id').split('_').pop(),
                    stat = statMap[statId.toUpperCase()];
                
                if (stat.stid === 'STAMINA') {
                    _timer.resetTimer();
                    _timer.updateTimer(new Date(stat.refill));
                    _timer.startTimer();
                }
                
                $(this).find('.progress_content').animate({width: stat.ratio}, 300);
                $(this).find('.stats_label.digits').html(
                    stat.value + '/' + stat.max
                );
            });
        };

        var updateActivityLog = function (activityLog) {
            var logData = '';
            for (var i = 0; i < activityLog.length; i++) {
                if (checkNull(activityLog[i].alid)) {
                   logData += 
                        '<li class="log_item hidden">' +
                            activityLog[i] +
                        '</li>';
                } else {
                    if (_log.indexOf(activityLog[i].alid) < 0) {
                        logData += 
                            '<li class="log_item hidden" data-alid="' + activityLog[i].alid + '">' +
                                activityLog[i].html +
                            '</li>';
                        _log.push(parseInt(activityLog[i].alid));
                    }
                }
            }
            $('#activity_log').prepend(logData);
            $('#activity_log .log_item.hidden').slideDown(300);
        };

        var updateTravelers = function (travelerData) {
            $('#traveler_count').html(travelerData.numberOfElements);
        };

        var updateBounty = function (bounties) {
            var text = '';
            for (var i = 0; i < bounties.length; i++) {
                text += '<div data-id="' + bounties[i].bid + '" class="profile_bounty_wrap">' + 
                            '<a href="#" id="tab_' + bounties[i].bid + '_bounty" class="tab_panel">' + 
                                '<span class="bounty_name">' +
                                    bounties[i].bname + 
                                '</span>' +
                                '<span class="status">' + ( 
                                    bounties[i].status === 1 ? '[Engage]' : (
                                        bounties[i].status === 2 ? '[Dead]' :
                                            '[Cashed]'
                                        )
                                    ) +
                                '</span>' +
                            '</a>' + 
                        '</div>';
            }
            $('#profile_bounty').html(text);
        };

        var updateLocation = function (location, profile) {
            if (!checkNull(location)) {
                var text =  '<span class="profile_location_wrap" data-id="' + location.lid + '">';
                if (checkNull(location.rpgcid)) {
                    text += '<a href="#" id="location_link">' + location.lname + '</a>';
                } else if (location.rpgcid === profile.rpgcid) {
                    text += '<a href="#" id="location_link">' + location.lname + '</a>';
                } else {
                    text += '<span class="restricted_class">' + location.lname + ' [' + location.rpgcid +']</span>';
                }
                text += '</span>';
            } else {
                var text = '<span class="profile_location_wrap"><span>Nothing of Note</span></span>';
            }
            $('#profile_location > span').html(text);
        };

        var generateMap = function (data) {
            var lats = data.lats,
                lons = data.lons,
                xpos = data.xpos,
                ypos = data.ypos;

            var mapHtml = '<div class="row clearfix">';

            //Top Left Blank Square
            mapHtml += '<div class="col-1-3 latitude_placeholder"><div></div></div>';
            
            //Top Row Titles
            for (var i = 0; i < lons.length; i++) {
                mapHtml += 
                    '<div class="col-1-3" data-rid="' + lons[i].rid + '">'+
                        '<div class="longitude">' + lons[i].rname + ' ' + lons[i].suffix + '</div>' +
                    '</div>';
            }
            //Close Top Row
            mapHtml += '</div>';
            
            //The Real Rows
            for (var i = 0; i < lats.length; i++) {
                //Left Road Name
                mapHtml += 
                    '<div class="row clearfix"  data-rid="' + lats[i].rid + '">' +
                        '<div class="col-1-3 latitude">' +
                            '<div class="latitude_text">' + 
                                lats[i].rname + ' ' + lats[i].suffix + 
                            '</div>' +
                        '</div>';
                //The Squares
                for (var j = 0; j < lons.length; j++) {
                    var user = ((lats[i].rid == ypos.rid && lons[j].rid == xpos.rid) ? true : false);
                    mapHtml +=
                        '<div class="col-1-3" data-rid="' + lons[j].rid + '">' +
                            '<div class="square' + (user ? ' user' : '') + '">' +
                                (user ? '<span class="icon icon-star-full"></span>' : '') +
                            '</div>' +
                        '</div>';
                }
                mapHtml += '</div>';
            }

            //Replace With New Map
            $('#panel_map').html(mapHtml);
        };

        var getNewMap = function (x, y) {
            var params = { lon: x, lat: y };
            $.ajax({
                type: 'POST',
                url: '/api/map',
                headers: _headers,
                data: params,
                dataType: 'JSON',
                success: function (data) {
                    if (data.errors.length > 0) {
                        updateActivityLog(data.errors);
                    } else {
                        generateMap(data);
                        updateTravelers(data.userProfiles);
                        updateBounty(data.bounties);
                        updateLocation(data.location, data.profile);
                        updateStats(data);
                        updateActivityLog(data.activityLog.content);
                    }
                },
                error: function (data) {
                    console.log(data.responseText);
                }
            });
        };

        var bountyNav = function ($link) {
            window.location = 'bounty?bid=' + parseInt($link.parent().data('id'));
        };

        var locationNav = function ($link) {
            window.location = 'location?lid=' + parseInt($link.parent().data('id'));
        };

        var attachActions = function () {
            $('#panel_map').on('click', 'div.square', function (e) {
                e.preventDefault();
                if ($(this).hasClass('user')) return;
                var x = $(this).parent().data('rid'),
                    y = $(this).parent().parent().data('rid');
                getNewMap(x, y);
            });

            $('#panel_map').on('click', 'div.square.user', function (e) {
                e.preventDefault();
                window.location = '/square';
            });

            $('#profile_stats').on('click', '.stats_label.text', function (e) {
                e.preventDefault();
                replenishStats($(this).html(), 0);
            });

            $('#profile_inv').on('click', '#location_link', function (e) {
                e.preventDefault();
                locationNav($(this));
            });
        };

        var init = function () {
            var refill = new Date($('#stat_Stamina').data('refill')),
                rate = parseInt($('#stat_Stamina').data('rate'));
            _timer = new Timer(refill, rate, 'profile_timer', replenishStats);

            $('.log_item').each( function () {
                _log.push(parseInt($(this).data('alid')));
            });
            attachActions();
        };

        init();

        return {
            updateStats: updateStats,
            updateActivityLog: updateActivityLog
        };
    };

    /************************************************/
    /******************* BOUNTIES *******************/
    /************************************************/
    var Bounty = function (id) {
        var _$target = $('#' + id);

        var _bountyParams = {
            url: null,
            data: null,
            method: null,
            callback: null
        };

        var renderBounty = function (bounty) {
            var bountyHtml = '<div id="engage_wrapper">',
                statusHtml = '';
            if (typeof bounty === 'undefined' || bounty === null) {
                bountyHtml += 
                    '<div id="engage_info">' +
                        '<p class="engage_info_main">' +
                            'You failed to subdue the bounty. After negating your attempt, the bounty moved. ' +
                        '</p>' +
                        '<p class="engage_info_minor">' +
                            'Hunt it down.' +
                        '</p>' +
                    '</div>';
                $('#profile_bounty').html('');
            } else {
                switch (bounty.status) {
                    case 1: //ACTIVE
                        bountyHtml +=
                            '<div id="engage_info" data-bid="' + bounty.bid + '">' +
                                '<p class="engage_desc">' +
                                    'You\'ve found the ' +
                                    '<span class="keyword">' + bounty.bname + '</span>. ' +
                                    'Your knuckles turn white as you approach the evil beast, but your resolve remains firm. ' +
                                    'The ' +
                                    '<span class="keyword">' + bounty.value + '</span> ' +
                                    'reward drifts from your thoughts. ' +
                                    'You require no compensation to pursue the unparalleled thrill of battle. ' +
                                    'Focusing on the task at hand, you comtemplate how to proceed.' +
                                '</p>' +
                            '</div>' +
                            '<div id="engage_actions_wrapper">' +
                                '<div id="engage_strike" class="engage_action white_grad" data-etype="1">' +
                                    '<span class="engage_text">Strike</span>' +
                                    '<span class="icon icon-eyedropper"></span>' +
                                '</div>' +
                                '<div id="engage_cast" class="engage_action white_grad" data-etype="2">' +
                                    '<span class="engage_text">Cast</span>' +
                                    '<span class="icon icon-magic-wand"></span>' +
                                '</div>' +
                            '</div>';
                        statusHtml = '[Engage]';
                        break;
                    case 2: //COMPLETE
                        bountyHtml +=
                            '<div id="engage_info">' +
                                '<p class="engage_info_main">' +
                                    'You gaze unemotionally at the mangled remains of the ' +
                                    '<span class="keyword">' + bounty.bname + '</span> you slaughtered. ' +
                                    'The world is a safer place to travel thanks to your hard work.' +
                                '</p>' +
                                '<p class="engage_info_minor">' +
                                    'Return to <span class="keyword">' + bounty.lid.xpos.rname + '</span> and ' +
                                    '<span class="keyword">' + bounty.lid.ypos.rname + '</span> to collect your reward.' +
                                '</p>' +
                            '</div>';
                        statusHtml = '[Dead]';
                        break;
                    case 3: //CASHED
                        bountyHtml +=
                            '<div id="engage_info">' +
                                '<p class="engage_info_main">' +
                                    'As you walk these streets you recall the ' +
                                    '<span class="keyword">' + bounty.bname + '</span> ' +
                                    'you had once encountered here. ' +
                                    'The task has been completed and your reward has been collected.' +
                                '</p>' +
                                '<p class="engage_info_minor">' +
                                    'Visit the bounty board at <span class="keyword">' + bounty.lid.xpos.rname + '</span> and ' +
                                    '<span class="keyword">' + bounty.lid.ypos.rname + '</span> to acquire a new mission.' +
                                '</p>' +
                            '</div>';
                        statusHtml = '[Cashed]';
                        break;
                }
                $('#profile_bounty .status').html(statusHtml);
            }
            bountyHtml += '</div>';
            _$target.html(bountyHtml);
        };

        var postBounty = function (params) {
            $.ajax({
                type: params.method,
                url: '/api/' + params.url,
                headers: _headers,
                data: params.data,
                dataType: 'JSON',
                success: function (data) {
                    console.log(data);
                    if (data.errors != null && data.errors.length > 0) {
                        console.log(data.errors);
                    } else {
                        renderBounty(data.bounty);
                        if (!checkNull(data.profile)) {
                            _map.updateStats(data.profile);
                        }
                    }
                },
                error: function (data) {
                    console.log(data.responseText);
                },
                complete: function (data) {
                }
            });
        };

        var getBounty = function (bid) {
            postBounty({
                method: 'POST', 
                data: {bid: parseInt(bid)}, 
                url: 'bounty'
            });
        };

        var init = function () {
            _$target.on('click', '.engage_action', function (e) {
                e.preventDefault();
                _bountyParams.data = { 
                    bid: parseInt($('#engage_info').data('bid')),
                    etype: parseInt($(this).data('etype'))
                };
                _bountyParams.method = 'POST';
                _bountyParams.url = 'bounty/engage';
                postBounty(_bountyParams);
            });
        };

        init();
    
        return {
            getBounty: getBounty
        };
    };


    /*************************************************/
    /******************* Inventory *******************/
    /*************************************************/
    var Inventory = function (type, id) {
        var _type = type,
            _$table = $('#' + id),
            _itypes = {};

        var renderInventory = function (inventory, removed) {
            for (var i = 0; i < inventory.length; i++) {
                var invItem = inventory[i],
                    $row = _$table.find('tr[data-iid="' + invItem.iid.iid + '"]');
                if ($row.length < 1) {
                    var rowsHtml = 
                        '<tr data-iid="' + invItem.iid.iid + '" data-type-id="' + _itypes[invItem.iid.itype].id + '">' +
                            '<td class="item_cell">' +
                                '<span class="item_name">' + invItem.iid.item + '</span>' +
                                '<span class="item_count" data-count="' + invItem.count + '">(' + invItem.count + ')</span>' +
                            '</td>' +
                            '<td class="value_cell">' +
                                '<span class="item_name">' + invItem.iid.value + '</span>' +
                                '<span class="item_count">(' + (invItem.count * invItem.iid.value) + ')</span>' +
                            '</td>' +
                            '<td>' +
                                '<span class="item_name">' + _itypes[invItem.iid.itype].type + '</span>' +
                            '</td>' +
                            '<td class="use_cell">' +
                                (invItem.iid.itype === 'CONSUMABLE' ? 
                                    '<button class="center use_button" data-invid="' + invItem.invid + '">' +
                                        'Use' +
                                    '</button>' :
                                    ''
                                ) +
                            '</td>' +
                            '<td class="drop_cell">' +
                                '<form>' +
                                    '<input type="hidden" name="invid" value="' + invItem.invid + '" />' +
                                    '<div class="drop_wrapper clearfix">' +
                                        '<button class="drop_button" type="submit">Drop</button>' +
                                        '<select class="drop_select" name="count">';
                    for (var j = 1; j <= invItem.count; j++) {
                        rowsHtml += '<option value="' + j + '">' + j + '</option>';
                    }
                    rowsHtml += '</select></div></form></td></tr>';
                    _$table.find('tbody').append(rowsHtml);
                } else {
                    var $select = $row.find('td.drop_cell select.drop_select'),
                        $tdCount = $row.find('.item_cell'),
                        $tdValue = $row.find('.value_cell');
                    
                    var oldCount = $tdCount.find('.item_count').data('count'),
                        newCount = invItem.count;
                    $tdCount.find('.item_count').data('count', newCount);
                    $tdCount.find('.item_count').html('(' + newCount + ')');
                    $tdValue.find('.item_count').html('(' + (newCount * invItem.iid.value) + ')');
                    for (var i = oldCount + 1; i <= newCount; i++) {
                        $select.prepend('<option value="' + i + '">' + i + '</option>');
                    }
                }
            }

            for (var i = 0; i < removed.length; i++) {
                _$table.find('tr[data-iid="' + removed[i].iid + '"]').remove();
            }

            var itr = 0;
            _$table.find('tbody > tr').each( function () {
                $(this).removeClass('odd').removeClass('even');
                $(this).addClass((itr % 2 === 0 ? 'even' : 'odd'));
                itr++;
            });
        };

        var getInventory = function () {
            $.ajax({
                type: 'GET',
                url: '/api/inventory',
                headers: _headers,
                dataType: 'JSON',
                success: function (data) {
                    console.log(data);
                    //renderInventory(data, []);
                },
                error: function (data) {
                    console.log(data);
                }
            });
        };
        
        var inventoryAction = function (options) {
            $.ajax({
                type: 'POST',
                url: options.url,
                headers: _headers,
                data: options.params,
                dataType: 'JSON',
                success: function (data) {
                    console.log(data);
                    renderInventory(data.inventory, data.removed);
                    _map.updateStats(data.profile);
                    _map.updateActivityLog(data.logs);
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

        var buildItypes = function () {
            $('table#inventory_table > thead li.inventory_type_item').each( function () {
                var key = $(this).html();
                key = key.toUpperCase();
                _itypes[key] = {
                    type: $.trim($(this).html()),
                    id: parseInt($(this).data('type-id'))
                };
            });
        };

        var init = function () {
            buildItypes();
            $('li.inventory_type_item').on('click', function (e) {
                e.preventDefault();
                filterInventory($(this));
            });
            $('table#inventory_table').on('click', 'button.use_button', function (e) {
                e.preventDefault();
                var options = { 
                    url: '/api/inventory/use', 
                    params: { invid: parseInt($(this).data('invid'))} 
                };
                inventoryAction(options);
            });
            $('table#inventory_table').on('click', 'button.drop_button', function (e) {
                e.preventDefault();
                var options = { 
                    url: '/api/inventory/remove', 
                    params: $(this).parents('form').serializeArray()
                };
                inventoryAction(options);
            });
        };

        init();

        return {
            getInventory: getInventory
        };
    };

/*************************************************/
/************** STATS ****************************/
/*************************************************/
    var Perks = function () {
        var _timers = {};

        var timerCallback = function () {
            console.log('done');
        };

        var loadRoads = function (roads, $select) {
            for (var i = 0; i < roads.length; i++) {
                $select.append(
                    '<option value="' + roads[i].rid + '">' + 
                        roads[i].rname + ' ' + roads[i].suffix + 
                    '</option>'
                );
            }
        };

        var getRoads = function () {
            $.ajax({
                type: 'GET',
                url: '/api/roads',
                headers: _headers,
                dataType: 'JSON',
                success: function (data) {
                    loadRoads(data.latitudes, $('select#ypos'));
                    loadRoads(data.longitudes, $('select#xpos'));
                },
                error: function (data) {
                    console.log(data);
                }
            });
        };

        var updatePerks = function (perks) {
            console.log(perks);
        };

        var buyPerk = function (perkid) {
            $.ajax({
                type: 'POST',
                url: '/api/perk/buy',
                headers: _headers,
                data: {perkid: perkid},
                dataType: 'JSON',
                success: function (data) {
                    updatePerks(data);
                },
                error: function (data) {
                    console.log(data);
                }
            });
        };

        var usePerk = function (params) {
            $.ajax({
                type: 'POST',
                url: '/api/perk/use',
                headers: _headers,
                data: params,
                dataType: 'JSON',
                success: function (data) {
                    console.log(data);
                },
                error: function (data) {
                    console.log(data);
                }
            });
        };

        var getPerks = function () {

        };

        var init = function () {
            $('#buy_perk').on('click', function (e) {
                e.preventDefault();
                var perkid = parseInt($(this).data('perkid'));
                buyPerk(perkid);
            });

            $('.perk_timer').each( function () {
                var $content = $(this).find('.timer_content');
                var refill = new Date($content.data('refill')),
                    rate = parseInt($content.data('rate'));
                _timers[$(this).attr('id')] = new Timer(refill, rate, $(this).attr('id'), timerCallback);
            });

            if ($('#teleport_action').length) {
                getRoads();
            }

            $('.use_perk').on('click', function (e) {
                e.preventDefault();
                usePerk($(this).parents('form').serializeArray());
            });
        };

        init();

        return {
            getPerks: getPerks
        };
    };




/****************** PANELS **********************/
    var Panels = function () {
        var current = 'map';

        var switchPanel = function (panel, val) {
            if (panel === current) {
                return;
            }
            $('.panel').hide();
            switch (panel) {
                case 'inventory':
                    _inventory.getInventory();
                    break;
                case 'bounty':
                    _bounty.getBounty(val);
                    break;
                case 'perks':
                    _perks.getPerks();
                    break;
            }
            $('#panel_' + panel).show();
            current = panel;
        };

        var init = function () {
            $('.tab_panel').on('click', function (e) {
                e.preventDefault();
                var paramsArr = $(this).attr('id').split('_'),
                    panel = paramsArr.pop(),
                    id = paramsArr.pop();
                switchPanel(panel, id);
            });
            $('#profile_inv').on('click', '.tab_panel', function (e) {
                e.preventDefault();
                var paramsArr = $(this).attr('id').split('_'),
                    panel = paramsArr.pop(),
                    id = paramsArr.pop();                
                switchPanel(panel, id);
            });
            switchPanel('users');
        };

        init();
    };


    var init = function () {
        _map = new Map();
        _inventory = new Inventory(-1, 'inventory_table');
        _bounty = new Bounty('panel_bounty');
        _perks = new Perks();
        _panels = new Panels();
    };

    init();

};

$( function () {
    var driver = new Driver();
});