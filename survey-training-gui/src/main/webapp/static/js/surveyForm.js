function checkForm(message1,message2) {

	$('#submit_0')
			.click(
					function() {
						
						var errors = []; 
						
						ret = true;
						$('.k-error-messages').remove();
						if ($(":checked").length != $('.k-radio').length)  {
							errors.push(message1);
							
							ret = false;
						}
						$('textarea').parent().filter(function() { 
							return $(this).css("display") != "none" 
							}).find('textarea').each(function(){
									if($(this).val()==""){
										ret = false;
										errors.push(message2);
										return false;
									}
						});
						
						if(!ret){
							$("form").prepend('<div class="k-error-messages"><ul></ul></div>');
							$.each(errors, function(i,v){
								$(".k-error-messages ul").append("<li>"+v+"</li>");
							});
								
							$('html,body').animate({scrollTop: $("form").offset().top},'slow');
						}
						return ret;
					});
}