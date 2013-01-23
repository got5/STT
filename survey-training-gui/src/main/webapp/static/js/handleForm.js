function disableForm(id){

$('input[type=radio]:not(:checked)','#'+id).parent().remove();	
$('input[type=submit]','#'+id).remove();
$('textarea','#'+id).attr("disabled", "disabled");
$('input','#'+id).attr("disabled", "disabled");


$('.elseclause > textarea').each(function(){
	if( $(this).val() ) {
        $(this).parent().show();
	}
});

}