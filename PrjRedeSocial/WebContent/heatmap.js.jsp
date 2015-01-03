
if (typeof(h337) == 'undefined') {

	window.heatmaplog = function(id) {
		if (id) {
			window.heatmapid = id || document.location.href.substring(0,50);
		} else {
			window.heatmapid = document.location.href.substring(0,50);
		}
		document.body.onclick = function(e) {
			var x = e.pageX || e.layerX;
			var y = e.pageY || e.layerY;
			var w = Math.max(document.documentElement.clientWidth, document.body.scrollWidth, document.documentElement.scrollWidth, document.body.offsetWidth, document.documentElement.offsetWidth);
			var h = Math.max(document.documentElement.clientHeight, document.body.scrollHeight, document.documentElement.scrollHeight, document.body.offsetHeight, document.documentElement.offsetHeight);
			var url = "/heatmap.jsp?action=click&x="+x+"&y="+y+"&w="+w+"&h="+h+"&id="+encodeURIComponent(window.heatmapid)+"&"+Math.random();
			if (false) {
				var image = new Image(1,1); image.src = url;
			} else {
				if (window.XMLHttpRequest) {
					xmlhttp=new XMLHttpRequest();
				} else {
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				xmlhttp.open("GET", url, false);
				xmlhttp.send();
				var dummy = xmlhttp.responseText;
			}
		};
	}

	window.onload = function() {
		heatmaplog('<%= (""+request.getParameter("id")).replaceAll("[^0-9]", "") %>');
	};

}
