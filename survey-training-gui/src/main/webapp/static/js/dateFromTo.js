function onSelectClosure() {

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