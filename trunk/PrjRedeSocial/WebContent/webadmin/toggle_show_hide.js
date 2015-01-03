function setMenu(menuid, iconid, showicon, hideicon, value) {
	var menu = document.getElementById(menuid);
	if (menu) {
		if ((value == 'hide') || (value == 'none')) {
			hideMenu(menuid, iconid, showicon, hideicon);
		} else {
			showMenu(menuid, iconid, showicon, hideicon);
		}
	}
}
function toggleMenu(menuid, iconid, showicon, hideicon, value) {
	var menu = document.getElementById(menuid);
	if (menu) {
		if (menu.style.display == 'none') {
			showMenu(menuid, iconid, showicon, hideicon);
		} else {
			hideMenu(menuid, iconid, showicon, hideicon);
		}
	}
}
function showMenu(menuid, iconid, showicon, hideicon, value) {
	var menu = document.getElementById(menuid);
	if (menu) {
		menu.style.display = 'block';
		if (iconid && document.images[iconid]) {
			document.images[iconid].src = showicon;
		}
	}
	set_cookie(menuid,'show','','/');
}
function hideMenu(menuid, iconid, showicon, hideicon, value) {
	var menu = document.getElementById(menuid);
	if (menu) {
		menu.style.display = 'none';
		if (iconid && document.images[iconid]) {
			document.images[iconid].src = hideicon;
		}
	}
	set_cookie(menuid,'hide','','/');
}
