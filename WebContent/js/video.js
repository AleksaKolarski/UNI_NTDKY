/* Fajl za video.
** -
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function (e) {
    
    var linkZaPadajuci = $('#video-details-toggle');
    var padajuci = $('#collapseOne');
    padajuci.on('shown.bs.collapse', function(){
        linkZaPadajuci.empty();
        linkZaPadajuci.append('<span class="glyphicon glyphicon-menu-up"></span>');
    });
    padajuci.on('hidden.bs.collapse', function(){
        linkZaPadajuci.empty();
        linkZaPadajuci.append('<span class="glyphicon glyphicon-menu-down"></span>');
    });




    //$.get('VideoServlet', { 'profil': profil }, function (data) {

    //});
});