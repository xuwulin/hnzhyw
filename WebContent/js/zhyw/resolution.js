$(document).ready(function(){
		$('.content').height($('body').height()-40);
		$('.information_list').width($('.contents_details').width()-280);
		$(window).resize(function(){
  			$('.content').height($('body').height()-40);
		$('.information_list').width($('.contents_details').width()-280);
});
});
