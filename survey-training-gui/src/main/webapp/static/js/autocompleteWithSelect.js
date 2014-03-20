function onSelectedAutocompleterClosure() {

    var onSelect = function (event, ui) {

        var url = jQuery(event.target).autocomplete('option').url+'/'+ui.item.id;
        var splittedUrl = url.split('?');
        url = splittedUrl[0] + '/' + ui.item.id +'?' + splittedUrl[1];
        $('#zone1').tapestryZone("update",{url : url});
    };

    return onSelect;
};

