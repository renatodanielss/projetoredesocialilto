var ww = document.body.clientWidth;

$(document).ready(function() {
	$(".nav1 li a").each(function() {
		if ($(this).next().length > 0) {
			$(this).addClass("parent");
		};
	})
	
	$(".toggleMenu1").click(function(e) {
		e.preventDefault();
		$(this).toggleClass("active");
		$(".nav1").toggle();
	});
	adjustMenu();
})

$(window).bind('resize orientationchange', function() {
	ww = document.body.clientWidth;
	adjustMenu();
});

var adjustMenu = function() {
	if (ww <2200) {
		$(".toggleMenu1").css("display", "inline-block");
		if (!$(".toggleMenu1").hasClass("active")) {
			$(".nav1").hide();
		} else {
			$(".nav1").show();
		}
		$(".nav1 li").unbind('mouseenter mouseleave');
		$(".nav1 li a.parent").unbind('click').bind('click', function(e) {
			// must be attached to anchor element to prevent bubbling
			e.preventDefault();
			$(this).parent("li").toggleClass("hover");
		});
	} 
	else if (ww >= 2200) {
		$(".toggleMenu1").css("display", "none");
		$(".nav1").show();
		$(".nav1 li").removeClass("hover");
		$(".nav1 li a").unbind('click');
		$(".nav1 li").unbind('mouseenter mouseleave').bind('mouseenter mouseleave', function() {
		 	// must be attached to li so that mouseleave is not triggered when hover over submenu
		 	$(this).toggleClass('hover');
		});
	}
}

