function improveaccordion(id) {
	$("ul.trainee-menu").hide();
	$("ul.level3").hide();

	
	$("#"+id+" .year > a").click(function() {
		$(this).next().slideToggle();
		
		
	});
	
	$("#"+id+" li.training-session-title > a").click(function() {

		if ($(this).has('ul')) {
			
			select($(this),$("#"+id+" li.training-session-title > a"),"selected-session");
			
			$(this).nextAll().slideToggle();
			
		}
		return false;
	});
	
	$("#"+id+" li.trainee-title > a").click(function() {
		
		select($(this),$("#"+id+" li.trainee-title > a"),"selected-trainee");
	});

}

function select(el,li,css) {

	selected = el.hasClass(css);

	li.each(function() {
		$(this).removeClass(css);
	});
	if (!selected) {
		el.addClass(css);
	}
}