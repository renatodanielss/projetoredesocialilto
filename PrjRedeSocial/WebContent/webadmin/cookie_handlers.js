//
//  Copyright (c) 1998-9 Steven Champeon. All rights reserved.
// 
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program; if not, write to the Free Software
//  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
//  The author may be contacted via email at <steve@dhtml-guis.com>;
//  other information may be found on the Web at http://dhtml-guis.com
//
//
//  Author: Steven Champeon <steve@dhtml-guis.com>
//  Maintainer: same
//  Release: 
//
//  $RCSfile: cookie_handlers.js,v $
//  $Revision: 1.1 $
//  $Date: 2006/02/02 23:20:53 $
//
//
//  Purpose:
//   allow for cross-platform setting and reading of cookies.
//
//  Dependencies:
//   none.
//
//  Usage:
//

var max_cookies = 20; // no more than 20 cookies per server allowed
var cookie_name = new Array(max_cookies);

// Listing 13-3.
function get_cookie( Name ) {
	var search = Name + "=";
	if( document.cookie.length > 0 ) { // if there are any cookies
		offset = document.cookie.indexOf( search );
		if( offset != -1 ) { // if cookie exists 
			offset += search.length;
			// set index of beginning of value
			end = document.cookie.indexOf(";", offset);
			// set index of end of cookie value
			if( end == -1 ) {
				end = document.cookie.length;
			}
			return unescape( document.cookie.substring( offset, end ));
		}
	} else {
		return false;
	}
}

// Listing 13-2.
//function set_cookie( name, value, expire ) {
//	document.cookie = name + "=" + escape(value)
//		+ ((expire == null) ? "" : ("; expires=" + expire.toGMTString()));
//}
// replaced by this function, which adds all possible cookie name/value
// pairs, to discourage changing the order of arguments, which might 
// confuse MSIE on the Mac, which expects name, then expiration, then
// the other arguments.
function set_cookie (name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") +
    ((secure) ? "; secure" : "");
}

// Listing 13-4.
function get_all_cookies() {
	var cookie_jar = document.cookie;
	var my_cookies = cookie_jar.split(';');
	var crumbs;

	for( var x = 0; x < my_cookies.length; x++ ) {
		crumbs = my_cookies[x].split('=');
		if( crumbs[0] != "" ) {
			cookie_name[x] = crumbs[0];
		}
	}
	return cookie_name.join(',');
}

// Listing 13-1.
function expiration(days) {
	var expires = new Date();
	var today = new Date();
	expires.setTime( today.getTime() + (1000*60*60*24*parseInt(days)) );
	return expires;
}

