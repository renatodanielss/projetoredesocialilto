<script src='/<%= mytext.display("adminpath") %>/rico3/js/rico.js' type='text/javascript'></script>
<script src='/<%= mytext.display("adminpath") %>/rico3/js/rico2jQuery.js' type='text/javascript'></script>
<script src='/<%= mytext.display("adminpath") %>/rico3/js/translations/ricoLocale_en.js' type='text/javascript'></script>
<link href='/<%= mytext.display("adminpath") %>/rico3/css/rico.css' type='text/css' rel='stylesheet' />
<script src='/<%= mytext.display("adminpath") %>/rico3/js/ricoUI.js' type='text/javascript'></script>
<script type="text/javascript">

Rico.setPaths(document.location.protocol + '//' + document.location.host + '/<%= mytext.display("adminpath") %>/rico3/js/', document.location.protocol + '//' + document.location.host + '/<%= mytext.display("adminpath") %>/rico3/css/', document.location.protocol + '//' + document.location.host + '/<%= mytext.display("adminpath") %>/rico3/images/');
Rico.loadModule('LiveGridAjax','LiveGridMenu');

var myLiveGrid;

function initLiveGrid() {
	var opts = {
		offset: 0 + <%= Common.intnumber(myrequest.getParameter("offset")) %>,
//		columnSpecs : [,,,,,{type:'date'},{type:'date'}],
		adjustHeight : -100,
		adjustWidth : +25,
		frozenColumns : 0,
		largeBufferSize : 5.0,			// default: 7.0
		prefetchBuffer : true,
		allowColResize : true,
		saveColumnInfo : true,
		highlightElem : 'cursorRow',		// 'cursorRow' 'cursorCell' 'menuRow' 'menuCell' 'selection' 'none')
		highlightClass : 'WCMindex_highlight',

		menuEvent : 'none',			// 'none'   'click'   'dblclick'
		canHideDefault : true,
		canSortDefault : true,
		canFilterDefault : false,
		maxPrint : 0,				// 0 disables export/print

		onRefreshComplete: updateCheckboxes,
		onscroll: updateLiveGrid
	};

	buffer=new Rico.Buffer.AjaxSQL(document.location.protocol + '//' + document.location.host + '/<%= mytext.display("adminpath") %>/content/index.xml.jsp?' + Math.random() + '&ecommerce=<%= html.encodeHtmlEntities(myrequest.getParameter("ecommerce")) %>&', {
//		requestParameters: ["database=<%= html.encodeHtmlEntities(myrequest.getParameter("database")) %>"],
		TimeOut: 60,
		sortParmFmt: 'headerId'
	});

	myLiveGrid = new Rico.LiveGrid('data_grid_header', buffer, opts);
	myLiveGrid.menu = new Rico.GridMenu({});
}

function updateLiveGrid( liveGrid, offset ) {
	if ($('#bookmark')) {
		if (offset+liveGrid.pageSize < liveGrid.buffer.totalRows) {
			$('#bookmark').html( "<%= mytext.display("content.index.listing.records") %> " + (offset+1) + "-" + (offset+liveGrid.pageSize) + " <%= mytext.display("content.index.listing.of") %> " + liveGrid.buffer.totalRows );
		} else {
			$('#bookmark').html( "<%= mytext.display("content.index.listing.records") %> " + (offset+1) + "-" + liveGrid.buffer.totalRows + " <%= mytext.display("content.index.listing.of") %> " + liveGrid.buffer.totalRows );
		}
	}
	var sortInfo = "";
//	if (liveGrid.sortCol) {
//		sortInfo = "&data_grid_sort_col=" + liveGrid.sortCol + "&data_grid_sort_dir=" +	liveGrid.sortDir;
//	}
	var bookmarkhref = "";
	if (document.location.href.match("[\?&]offset=[0-9]+")) {
		bookmarkhref = "" + document.location.href.replace("([\?&]offset=).*$", "\\1" + offset + sortInfo);
	} else if (document.location.search) {
		bookmarkhref = "" + document.location.href + "&offset=" + offset + sortInfo;
	} else {
		bookmarkhref = "" + document.location.href + "?offset=" + offset + sortInfo;
	}
	if ($('#bookmark').length) $('#bookmark').attr('href', bookmarkhref);
	var firstoffset = 0;
	var lastoffset = 0 + liveGrid.buffer.totalRows - liveGrid.pageSize;
	var backoffset = 0 + offset - liveGrid.pageSize;
	if (backoffset < 0) backoffset = firstoffset;
	var nextoffset = 0 + offset + liveGrid.pageSize;
	if (nextoffset > lastoffset) nextoffset = lastoffset;
	if ($('#first').length) $('#first').attr('href', "javascript:myLiveGrid.scrollToRow(" + firstoffset + ")");
	if ($('#back').length) $('#back').attr('href', "javascript:myLiveGrid.scrollToRow(" + backoffset + ")");
	if ($('#next').length) $('#next').attr('href', "javascript:myLiveGrid.scrollToRow(" + nextoffset + ")");
	if ($('#last').length) $('#last').attr('href', "javascript:myLiveGrid.scrollToRow(" + lastoffset + ")");
	var pages = "";
	for (var i=0; i<liveGrid.buffer.totalRows; i+=liveGrid.pageSize) {
		if (pages != '') pages += " ";
		if ((offset > i-liveGrid.pageSize) && (offset < i+liveGrid.pageSize)) {
			if (liveGrid.buffer.totalRows < liveGrid.pageSize) {
				pages += '<a style="color:#cc0000;text-decoration:none;" href="javascript:myLiveGrid.scrollToRow(' + 0 + ')">' + ((i/liveGrid.pageSize)+1) + '</a>';
			} else if (i+liveGrid.pageSize > liveGrid.buffer.totalRows) {
				pages += '<a style="color:#cc0000;text-decoration:none;" href="javascript:myLiveGrid.scrollToRow(' + (liveGrid.buffer.totalRows - liveGrid.pageSize) + ')">' + ((i/liveGrid.pageSize)+1) + '</a>';
			} else {
				pages += '<a style="color:#cc0000;text-decoration:none;" href="javascript:myLiveGrid.scrollToRow(' + i + ')">' + ((i/liveGrid.pageSize)+1) + '</a>';
			}
		} else {
			if (liveGrid.buffer.totalRows < liveGrid.pageSize) {
				pages += '<a style="text-decoration:none;" href="javascript:myLiveGrid.scrollToRow(' + 0 + ')">' + ((i/liveGrid.pageSize)+1) + '</a>';
			} else if (i+liveGrid.pageSize > liveGrid.buffer.totalRows) {
				pages += '<a style="text-decoration:none;" href="javascript:myLiveGrid.scrollToRow(' + (liveGrid.buffer.totalRows - liveGrid.pageSize) + ')">' + ((i/liveGrid.pageSize)+1) + '</a>';
			} else {
				pages += '<a style="text-decoration:none;" href="javascript:myLiveGrid.scrollToRow(' + i + ')">' + ((i/liveGrid.pageSize)+1) + '</a>';
			}
		}
	}
	if ($('#pages').length) $('#pages').html( pages );
}

var checkboxes = new Array();

function toggleCheckbox( id, checked ) {
	if (checked && (! checkboxes[id])) {
		$('<input type="hidden" id="id_' + id + '" name="id" value="' + id + '">').insertBefore($('#container'));
	} else if ((! checked) && checkboxes[id]) {
		$("#id_"+id).remove();
	}
	checkboxes[id] = checked;
}

function updateCheckboxes() {
	var inputs = $('#container').find("input");
	var checkbox = inputs.length ? inputs.map(function() { return this }).toArray() : [];
	for (var i=0; i<checkbox.length; i++) {
		checkbox[i].checked = checkboxes[checkbox[i].value];
	}
}

</script>
