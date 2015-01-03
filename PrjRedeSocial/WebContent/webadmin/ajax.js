
var ajaxQueue = new Array();
var ajaxRunning = false;

function GET(url,target,callback) {
	return AJAX('GET',url,false,target,false,false,callback);
}

function POST(url,data,target,enctype,charset,callback) {
	return AJAX('POST',url,data,target,enctype,charset,callback);
}

function AJAX(method,url,data,target,enctype,charset,callback) {
	// initialize
	if (typeof(ajaxQueue) == 'undefined') ajaxQueue = new Array();
	if (typeof(ajaxRunning) == 'undefined') ajaxRunning = false;

	// add task to queue
	if (method && url) {
		var ajaxTask = new Object();
		ajaxTask.method = method;
		ajaxTask.url = url;
		ajaxTask.data = '';
		ajaxTask.formdata = false;
		ajaxTask.enctype = false;
		ajaxTask.callback = callback;
		if (typeof(data) == 'string') {
			// simple string parameters
			ajaxTask.enctype = "application/x-www-form-urlencoded";
			ajaxTask.data = data;
		} else if (data && data.nodeName && (data.nodeName == 'FORM')) {
			// html form parameters
			ajaxTask.enctype = "multipart/form-data";
			ajaxTask.formdata = new Object();
			for (var i=0; i<data.elements.length; i++) {
				if (ajaxTask.data) ajaxTask.data += '&';
				ajaxTask.data += escape(data.elements[i].name) + '=' + escape(data.elements[i].value);
				ajaxTask.formdata[data.elements[i].name] = data.elements[i].value;
			}
		} else if (typeof(data) == 'object') {
			// array/object parameters
			ajaxTask.enctype = "multipart/form-data";
			ajaxTask.formdata = new Object();
			for (var key in data) {
				if (ajaxTask.data) ajaxTask.data += '&';
				ajaxTask.data += escape(key) + '=' + escape(data[key]);
				ajaxTask.formdata[key] = data[key];
			}
		}
		if (enctype) {
			ajaxTask.enctype = enctype;
		}
		if (charset) {
			ajaxTask.enctype = ajaxTask.enctype + "; charset=" + charset;
		}
		if (target) {
			// asynchronous task - add to queue
			ajaxTask.target = target;
			ajaxQueue[target] = ajaxTask;
		} else {
			// synchronous task - clear queue
// TODO: if existing queue then wait until queue has completed
			ajaxQueue = new Array(ajaxTask);
			ajaxRunning = false;
		}
	}

	// process task queue
	if (! ajaxRunning) {
		ajaxRunning = true;
		for (var task in ajaxQueue) {
			var ajaxTask = ajaxQueue[task];
			delete ajaxQueue[task];

			//document.getElementById(ajaxTask.target).innerHTML = 'sending...';

			if (window.XMLHttpRequest) {
				req = new XMLHttpRequest();
				//req.overrideMimeType('text/xml');
				//req.overrideMimeType('text/html');
			} else if (window.ActiveXObject) {
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}
			if (req) {
				if (ajaxTask.target) {
					// asynchronous task
					req.onreadystatechange = function() {
						if (req.readyState == 4) {
							if (req.status == 200) {
								try {
									document.getElementById(ajaxTask.target).innerHTML = req.responseText;
								} catch(e) {
									// MSIE may fail to set target's innerHTML
									try {
										var mytarget = document.getElementById(ajaxTask.target);
										while (mytarget.hasChildNodes()) {
											mytarget.removeChild(mytarget.lastChild);
										}
										var myhtml = mytarget.outerHTML;
										myhtml = myhtml.replace(new RegExp("(<\/.*?>)$", "gi"), req.responseText + "$1");
										mytarget.outerHTML = myhtml;
									} catch(e) {
									}
								}
							} else {
								try {
									document.getElementById(ajaxTask.target).innerHTML="ERROR: " + req.statusText;
								} catch(e) {
									// MSIE may fail to set target's innerHTML
									try {
										var mytarget = document.getElementById(ajaxTask.target);
										while (mytarget.hasChildNodes()) {
											mytarget.removeChild(mytarget.lastChild);
										}
										var myhtml = mytarget.outerHTML;
										myhtml = myhtml.replace(new RegExp("(<\/.*?>)$", "gi"), req.statusText + "$1");
										mytarget.outerHTML = myhtml;
									} catch(e) {
									}
								}
							}
							if (typeof(ajaxTask.callback) == 'function') {
								ajaxTask.callback(ajaxTask.method, ajaxTask.url, ajaxTask.data, ajaxTask.target);
							}
							ajaxRunning = false;
							AJAX();
						}
					};
					req.open(ajaxTask.method, ajaxTask.url, true);
				} else {
					// synchronous task
					ajaxRunning = false;
					req.open(ajaxTask.method, ajaxTask.url, false);
				}
				if (ajaxTask.method == 'GET') {
					req.send(null);
				} else if ((ajaxTask.method == 'POST') && ajaxTask.data) {
					if ((! ajaxTask.formdata) || (! ajaxTask.enctype) || (ajaxTask.enctype == "application/x-www-form-urlencoded")) {
						req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
						req.setRequestHeader("Content-length", ajaxTask.data.length);
						req.setRequestHeader("Connection", "close");
						req.send(ajaxTask.data);
					} else if (ajaxTask.enctype.indexOf("application/x-www-form-urlencoded") >= 0) {
						req.setRequestHeader("Content-type", ajaxTask.enctype);
						req.setRequestHeader("Content-length", ajaxTask.data.length);
						req.setRequestHeader("Connection", "close");
						req.send(ajaxTask.data);
					} else {
						var boundary = "AsbruAJAX" + (Math.floor(Math.random()*10000000000)+1000000000);
						var formdata = "";
						for (var key in ajaxTask.formdata) {
							formdata += "--" + boundary + "\n";
							formdata += "Content-Disposition: form-data; name=\"" + key + "\"" + "\n\n";
							formdata += "" + ajaxTask.formdata[key] + "\n";
						}
						formdata += "--" + boundary;
						req.setRequestHeader("Content-type", ajaxTask.enctype + "; boundary=\"" + boundary + "\"");
						req.setRequestHeader("Content-length", formdata.length);
						req.setRequestHeader("Connection", "close");
						req.send(formdata);
					}
				}
				try {
					return req.responseText;
				} catch(e) {
					return "";
				}
			}
		}
		ajaxRunning = false;
	}
}	
