
function loading() {
	$(".trigger").click(function() {
		$(this).parent().parent().parent().next().show();
	});

	$(".notrigger").click(function() {
		
		var $myP = $(this).parent().parent().parent().next();
		if($myP.is("p")){
			$myP.children().last().val('');
			$myP.hide();
		}
	});
}
