
function customAutocomplete(specs) {


	$("#training").on("autocompleteselect", function(event, ui) {
		$(this).prev().val(ui.item.id);
	});

	$("#instructor").on("autocompleteselect", function(event, ui) {
		$(this).prev().val(ui.item.id);
	});

    $("#room").on("autocompleteselect", function(event, ui) {
        $(this).prev().val(ui.item.id);
    });
};

