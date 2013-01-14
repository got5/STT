function improveaccordion() {
	$("ul.trainee-menu").hide();
	$("ul.level3").hide();

	
	$('.year > a').click(function() {
		$(this).next().slideToggle();
		
		
	});
	
	$('li.training-session-title > a').click(function() {

		if ($(this).has('ul')) {
			
			select($(this),$('li.training-session-title > a'),"selected-session");
			
			$(this).nextAll().slideToggle();
			
		}
		return false;
	});
	
	$('li.trainee-title > a').click(function() {
		
		select($(this),$('li.trainee-title > a'),"selected-trainee");
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