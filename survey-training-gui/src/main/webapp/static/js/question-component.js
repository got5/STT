
function loading() {
	$(".trigger").click(function() {
		$(this).parent().parent().parent().next().show()
	});

	$(".notrigger").click(function() {
		$(this).parent().parent().parent().next().hide()
	});
}
