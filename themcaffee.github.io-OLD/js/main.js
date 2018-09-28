// Affix the navbar to the top of the page after scrolling down
$(function() {
    //$('#nav-wrapper').height($("#nav").height());

    $('#nav').affix({
        offset: { top: $('#nav').offset().top }
    });
});