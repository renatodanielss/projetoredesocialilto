
function dTree(objName, imgFolder) {
	this.defaultCookiePath	= '/';
	if (top && top.opener && top.opener.webeditor && top.opener.webeditor.rootpath) {
		this.defaultImgFolder = top.opener.webeditor.rootpath + "dtree/";
	} else {
		this.defaultImgFolder = "dtree/";
	}

	this.indent = [];
	this.pid = [];
	this.icon = [];
	this.nextSibling = [];
	this.previousSibling = [];
	this.hasChildren = [];
	this.isOpen = [];
	this.document = document;

	this.objName = objName;
	if (imgFolder) {
		this.imgFolder = imgFolder;
	} else {
		this.imgFolder = this.defaultImgFolder;
	}

//	// start tree
//	this.document.writeln('<div class="dtree" id="' + this.objName + '">');

	// Add a new node to the tree
	this.add = function(id, pid, name, url, title, target, isopen, img) {
		this.pid[id] = pid;
		this.icon[id] = img;

		// find previous sibling
		if (id > 0) {
			var previousSibling = this.pid[id-1];
			while (previousSibling && (pid != this.pid[previousSibling])) {
				previousSibling = this.pid[previousSibling];
			}
			if (previousSibling) {
				this.previousSibling[id] = previousSibling;
				this.nextSibling[previousSibling] = id;
			}
		}

		// determine if to open or close item
		if (this.hasCookieValue('open' + this.objName, id)) {
			this.isOpen[id] = true;
		} else if (this.hasCookieValue('close' + this.objName, id)) {
			this.isOpen[id] = false;
		} else {
			this.isOpen[id] = isopen;
		}

		// check if this is the first item under parent item
		if ((pid > 0) && (! this.hasChildren[pid])) {
			// use fold/unfold icon
			var parentJoin = this.document.getElementById(this.objName + 'Join' + pid);
			if (parentJoin) {
				if (this.isOpen[pid]) {
					parentJoin.src = this.imgFolder + 'minus.gif';
				} else {
					parentJoin.src = this.imgFolder + 'plus.gif';
				}
			}
			var parentIcon = this.document.getElementById(this.objName + 'Icon' + pid);
			if (parentIcon) {
				if (this.icon[pid]) {
					// keep icon
				} else if ((this.isOpen[pid]) && (parentIcon.src.indexOf(this.imgFolder + 'folder.gif') >= 0)) {
					parentIcon.src = this.imgFolder + 'folderopen.gif';
				} else if ((! this.isOpen[pid]) && (parentIcon.src.indexOf(this.imgFolder + 'folderopen.gif') >= 0)) {
					parentIcon.src = this.imgFolder + 'folder.gif';
				} else if ((this.isOpen[pid]) && (parentIcon.src.indexOf(this.imgFolder + 'page.gif') >= 0)) {
					parentIcon.src = this.imgFolder + 'folderopen.gif';
				} else if ((! this.isOpen[pid]) && (parentIcon.src.indexOf(this.imgFolder + 'page.gif') >= 0)) {
					parentIcon.src = this.imgFolder + 'folder.gif';
				} else if (parentIcon.src.indexOf(this.imgFolder + 'page.gif') >= 0) {
					parentIcon.src = this.imgFolder + 'imgfolder.gif';
				}
			}

			// start sub-items block
			this.hasChildren[pid] = true;
			if (this.isOpen[pid]) {
				this.document.write('<span id="' + this.objName + pid + '" style="display: block">');
			} else {
				this.document.write('<span id="' + this.objName + pid + '" style="display: none">');
			}
		}

		// determine indentation for this item
		if (pid >= 0) {
			this.indent[id] = this.indent[pid] + 1;
		} else {
			this.indent[id] = 0;
		}

		// end previous sub-items blocks
		if (this.indent[id] < this.indent[id-1]) {
			var myid = this.pid[id-1];
			for (var outdent = this.indent[id-1] - this.indent[id]; outdent>0; outdent--) {
				if ((myid >= 0) && (this.hasChildren[myid])) {
					this.document.writeln('</span>');
				}
				myid = this.pid[myid];
			}
		}

		// start this item
		this.document.write('<span style="white-space: nowrap;">');

		// indent this item
		if (pid >= 0) {
			var indent = '';
			for (var mypid = pid; mypid; mypid = this.pid[mypid]) {
				indent = '<img id="' + this.objName + 'Indent' + mypid + '_' + id + '" src="' + this.imgFolder + 'line.gif">' + indent;
			}
			this.document.write(indent);
			this.document.write('<a href="javascript:' + this.objName + '.toggle(\'' + this.objName + '\',' + id + ')">');
			this.document.write('<img id="' + this.objName + 'Join' + id + '" src="' + this.imgFolder + 'join.gif">');
			this.document.write('</a>');
		}

		// determine image icon to be used
		if (! img) {
			if (id == 0) {
				img = "base.gif";
			} else if (! url) {
				if (isopen) {
					img = "folderopen.gif";
				} else {
					img = "folder.gif";
				}
			} else {
				img = "page.gif";
			}
		}

		// output this item
		if (url) {
			var classname = '';
			if (this.isCookie('select' + this.objName, url)) {
				classname = 'nodeSel';
			} else {
				classname = 'node';
			}
			this.document.write('<a href="' + url + '" onClick="' + this.objName + '.select(\'' + this.objName + '\',' + id + ',\'' + url.replace(/([^\\]?)'/g, "\\1\\'") + '\')">');
			this.document.write('<img id="' + this.objName + 'Icon' + id + '" src="' + this.imgFolder + img + '">');
			this.document.write('</a>');
			this.document.write('<a id="' + this.objName + 'Link' + id + '" class="' + classname + '" href="' + url + '" onClick="' + this.objName + '.select(\'' + this.objName + '\',' + id + ',\'' + url.replace(/([^\\]?)'/g, "\\1\\'") + '\')">');
			this.document.write(name);
			this.document.write('</a>');
		} else {
			this.document.write('<a href="javascript:' + this.objName + '.toggle(\'' + this.objName + '\',' + id + ')">');
			this.document.write('<img id="' + this.objName + 'Icon' + id + '" src="' + this.imgFolder + img + '">');
			this.document.write('</a>');
			this.document.write('<a id="' + this.objName + 'Link' + id + '" class="node" href="javascript:' + this.objName + '.toggle(\'' + this.objName + '\',' + id + ')">');
			this.document.write(name);
			this.document.write('</a>');
		}

		// end this item
		this.document.writeln('<br></span>');

		// adjust previous indentation
		if (this.indent[id] < this.indent[id-1]) {
			this.terminateBranch(id-1, this.indent[id-1] - this.indent[id]);
		}
	}



	// Clean up the tree after the last node in a branch
	this.terminateBranch = function(id, branches) {
		// adjust bottom of previous indentation
		var prevJoin = this.document.getElementById(this.objName + 'Join' + id);
		if (prevJoin) {
			prevJoin.src = this.imgFolder + 'joinbottom.gif';
		}

		// remove indentation lines after the last branches
		var mypid = this.pid[id];
		for (var outdent = branches; outdent>0; outdent--) {
			if (! this.nextSibling[mypid]) {
				var parentJoin = this.document.getElementById(this.objName + 'Join' + mypid);
				if (parentJoin) {
					if (parentJoin.src.indexOf(this.imgFolder + 'minus.gif') >= 0) {
						parentJoin.src = this.imgFolder + 'minusbottom.gif';
					} else if (parentJoin.src.indexOf(this.imgFolder + 'plus.gif') >= 0) {
						parentJoin.src = this.imgFolder + 'plusbottom.gif';
					}
				}
				if (! this.nextSibling[mypid]) {
					for (var indentid = id; indentid != mypid; indentid--) {
						var parentIndent = this.document.getElementById(this.objName + 'Indent' + mypid + '_' + indentid);
						if (parentIndent) {
							parentIndent.src = this.imgFolder + 'empty.gif';
						}
					}
				}
			}
			mypid = this.pid[mypid];
		}
	}



	// Finalise the tree
	this.draw = function() {
		// end sub-items blocks
		var myid = this.pid[this.pid.length-1];
		while (myid >= 0) {
			if ((myid >= 0) && (this.hasChildren[myid])) {
				this.document.writeln('</span>');
			}
			myid = this.pid[myid];
		}

		// adjust last indentation
		this.terminateBranch(this.indent.length-1, this.indent[this.indent.length-1]);

//		// end tree
//		this.document.writeln('</div>');
	}



	// Open/unfold a branch
	this.open = function(objName, id) {
		var node = this.document.getElementById(objName + id);
		if (node) {
			node.style.display = "block";
			var nodeJoin = this.document.getElementById(objName + 'Join' + id);
			if (nodeJoin) {
				if (this.nextSibling[id]) {
					nodeJoin.src = this.imgFolder + 'minus.gif';
				} else {
					nodeJoin.src = this.imgFolder + 'minusbottom.gif';
				}
			}
			var nodeIcon = this.document.getElementById(objName + 'Icon' + id);
			if (nodeIcon && (nodeIcon.src.indexOf(this.imgFolder + 'folder.gif') >= 0)) {
				nodeIcon.src = this.imgFolder + 'folderopen.gif';
			}
			this.removeCookieValue('close' + objName, id);
			this.addCookieValue('open' + objName, id);
		}
		if (this.pid[id]) {
			this.open(objName, this.pid[id]);
		}
	}



	// Close/fold a branch
	this.close = function(objName, id) {
		var node = this.document.getElementById(objName + id);
		if (node) {
			node.style.display = "none";
			var nodeJoin = this.document.getElementById(objName + 'Join' + id);
			if (nodeJoin) {
				if (this.nextSibling[id]) {
					nodeJoin.src = this.imgFolder + 'plus.gif';
				} else {
					nodeJoin.src = this.imgFolder + 'plusbottom.gif';
				}
			}
			var nodeIcon = this.document.getElementById(objName + 'Icon' + id);
			if (nodeIcon && (nodeIcon.src.indexOf(this.imgFolder + 'folderopen.gif') >= 0)) {
				nodeIcon.src = this.imgFolder + 'folder.gif';
			}
			this.removeCookieValue('open' + objName, id);
			this.addCookieValue('close' + objName, id);
		}
	}



	// Display or hide a branch
	this.toggle = function(objName, id) {
		var node = this.document.getElementById(objName + id);
		if (node) {
			if (node.style.display == "none") {
				this.open(objName, id);
			} else {
				this.close(objName, id);
			}
		}
	}



	// Remember, highlight and open selected item
	this.select = function(objName, id, url) {
		this.setCookie('select' + objName, url);

		// unhighlight old selections
		for (var myid=0; myid<this.pid.length; myid++) {
			var node = this.document.getElementById(objName + 'Link' + myid);
			if (node && node.className && (node.className == 'nodeSel')) {
				node.className = "node";
			}
		}

		// highlight new selection
		var node = this.document.getElementById(objName + 'Link' + id);
		if (node) {
			node.className = "nodeSel";
		}

		// display sub-items
		if (this.hasChildren[id]) {
			this.open(objName, id);
		}
	}



	// Select menuitem with a given url
	this.selectURL = function(url) {
		for (var myid=0; myid < this.pid.length; myid++) {
			var node = this.document.getElementById(this.objName + 'Link' + myid);
			if (node && node.href && (node.href == url)) {
				this.select(this.objName, myid, url);
				while (myid > 0) {
					this.open(this.objName, myid);
					myid = this.pid[myid];
					return true;
				}
			}
		}
		return false;
	}



	// Select menuitem with a given url substring
	this.selectURLsubstring = function(url) {
		for (var myid=0; myid < this.pid.length; myid++) {
			var node = this.document.getElementById(this.objName + 'Link' + myid);
			if (node && node.href && (node.href.indexOf(url) >= 0)) {
				this.select(this.objName, myid, url);
				while (myid > 0) {
					this.open(this.objName, myid);
					myid = this.pid[myid];
					return true;
				}
			}
		}
		return false;
	}



	this.clearCookie = function() {
		var now = new Date();
		var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
		this.setCookie('close' + this.objName, '', yesterday);
		this.setCookie('open' + this.objName, '', yesterday);
		this.setCookie('select' + this.objName, '', yesterday);
	}



	this.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
		this.document.cookie =
			escape(cookieName) + '=' + escape(cookieValue)
			+ (expires ? '; expires=' + expires.toGMTString() : '')
			+ (path ? '; path=' + path : (this.defaultCookiePath ? '; path=' + this.defaultCookiePath : ''))
			+ (domain ? '; domain=' + domain : '')
			+ (secure ? '; secure' : '');
	}



	this.getCookie = function(cookieName) {
		var cookieValue = '';
		var posName = this.document.cookie.indexOf(escape(cookieName) + '=');
		if (posName != -1) {
			var posValue = posName + (escape(cookieName) + '=').length;
			var endPos = this.document.cookie.indexOf(';', posValue);
			if (endPos != -1) {
				cookieValue = unescape(this.document.cookie.substring(posValue, endPos));
			} else {
				cookieValue = unescape(this.document.cookie.substring(posValue));
			}
		}
		return (cookieValue);
	}



	this.isCookie = function(cookieName, cookieValue) {
		var cookie = this.getCookie(cookieName);
		if (cookie == cookieValue) {
			return true;
		} else {
			return false;
		}
	}



	this.addCookieValue = function(cookieName, cookieValue) {
		var cookie = this.getCookie(cookieName);
		cookie = cookie.replace('-' + cookieValue + '-', '-');
		if (cookie == '') {
			cookie = '-';
		}
		cookie = '-' + cookieValue + cookie;
		this.setCookie(cookieName, cookie);
	}



	this.removeCookieValue = function(cookieName, cookieValue) {
		var cookie = this.getCookie(cookieName);
		cookie = cookie.replace('-' + cookieValue + '-', '-');
		if (cookie == '') {
			cookie = '-';
		}
		this.setCookie(cookieName, cookie);
	}



	this.hasCookieValue = function(cookieName, cookieValue) {
		var cookie = this.getCookie(cookieName);
		if (cookie.indexOf('-' + cookieValue + '-') >= 0) {
			return true;
		} else {
			return false;
		}
	}


}
