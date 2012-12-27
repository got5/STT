function checkForm($message) {

	$('#submit_0')
			.click(
					function() {

						$('#client-error').remove();

						if ($(":checked").length != $('.k-radio').length) {
							$("form")
									.prepend(
											'<div id="client-error" class="k-error-messages"><ul><li>'+$message+'</li></ul></div>');
							$('html,body').animate({scrollTop: $("form").offset().top},'slow');
							return false;
						}
					});
}