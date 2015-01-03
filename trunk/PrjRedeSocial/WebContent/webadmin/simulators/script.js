$(document).ready(function(){
});

$.fn.preload = function() {
	this.each(function(){
		$('<img/>')[0].src = this;
	});
}

var setFrameUrl = function(url,form) {
	if ($(form).length) {
		$(form).submit();
	} else if (url) {
		if (!url || url == 'undefined') return;
		if (!url.match('^https?://')) {
			url = 'http://' + url;
		}
		$('#url').val(url);
		$('#frame').attr('src',url);
	} else if ($('#contenturl').length) {
		$('#frame').attr('src',$('#contenturl').attr('value'));
	}
};

var rotate = function() {
  $('#device').toggleClass('landscape').toggleClass('portrait');
}

$(function(){

setFrameUrl($.url.param('url'), "#contentform");

if ($.url.param('portrait')) rotate();

$('#rotate').click(rotate);

$('#reload').click(function(){
  $('#frame').attr('src',$('#frame').attr('src'));
});

$('#url').focus(function(){
  $('#kbd').show();
});

$('#url').blur(function(){
  $('#kbd').hide();
});

$('#url').keyup(function(evt){
  if (evt.keyCode != 13) return;
  $('#url').blur();
  setFrameUrl($(this).val());
});

$('#search').focus(function(){
  $('#kbd').show();
});

$('#search').blur(function(){
  $('#kbd').hide();
});

$('#search').keyup(function(evt){
  if (evt.keyCode != 13) return;
  $('#search').blur();
  setFrameUrl("http://www.google.com/search?q="+escape($(this).val()));
});

});
