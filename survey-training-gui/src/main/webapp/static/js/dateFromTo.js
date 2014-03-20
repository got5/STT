
function onSelectClosure() {

    var select = function(selectedDate, instance) {
        var myself = instance.input;
        brother = myself.parent().parent().parent().find(
            '.k-datepick[id!="' + instance.id + '"]')[0];
        var brotherId = brother.id;

        idFirstDatepicker = myself.parent().parent().parent().find(
            ".k-datepick:first")[0].id;

        var option = this.id == idFirstDatepicker ? "minDate" : "maxDate";
        var date = $.datepicker.parseDate("dd/mm/yy", selectedDate);
        //var date = $('#'+instance.input).datepicker("getDate");
        $('#'+brotherId).datepicker("option", option, date);
    }
    return select;
}



/*function onSelectClosure() {


	var select = function(selectedDate, instance) {
		myself = $("#" + instance.id);
		brother = myself.parent().parent('.oneLine').find(
				'.k-datepick[id!="' + instance.id + '"]');

		idFirstDatepicker = myself.parent().parent('.oneLine').find(
				".k-datepick:first").attr("id");

		var option = this.id == idFirstDatepicker ? "minDate" : "maxDate";
		//var date = $.datepicker.parseDate("dd/mm/yy", selectedDate);
		var date = myself.datepicker("getDate");
		brother.datepicker("option", option, date);
	}
	return select;
}
*/