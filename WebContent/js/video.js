/* Fajl za video.
** -
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function (e) {
    var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];

    // inicijalizacija padajuceg menija za detalje videa
    var linkZaPadajuci = $('#video-details-toggle');
    var padajuci = $('#collapseOne');
    padajuci.on('shown.bs.collapse', function () {
        linkZaPadajuci.empty();
        linkZaPadajuci.append('<span class="glyphicon glyphicon-menu-up"></span>');
    });
    padajuci.on('hidden.bs.collapse', function () {
        linkZaPadajuci.empty();
        linkZaPadajuci.append('<span class="glyphicon glyphicon-menu-down"></span>');
    });

    // preuzimanje lajkova
    refreshVideoLikes(videoId);

    $('#video-dugme-like-link').on('click', null, function(){
        $.post('LajkServlet?target=VIDEO&id=' + videoId + '&tip=like', function(data){
            if(data.status == 'error'){
                console.log('greska pri lajkovanju');
            }
            else{
                refreshVideoLikes(videoId);
            }
        });
    });
    $('#video-dugme-dislike-link').on('click', null, function(){
        $.post('LajkServlet?target=VIDEO&id=' + videoId + '&tip=dislike', function(data){
            if(data.status == 'error'){
                console.log('greska pri dislajkovanju');
            }
            else{
                refreshVideoLikes(videoId);
            }
        });
    });

});

function refreshVideoLikes(videoId) {
    var linijaLike = $('#video-details-like-line');
    var linijaDislike = $('#video-details-dislike-line');
    var videoPoljeBrojLajkova = $('#video-details-broj-lajkova');
    var videoPoljeBrojDislajkova = $('#video-details-broj-dislajkova');
    $.get('LajkServlet?target=VIDEO&id=' + videoId, function (data) {
        if (data.status == 'success') {
            var lajkovi = data.lajkovi[0];
            var dislajkovi = data.lajkovi[1];
            var zbir = lajkovi + dislajkovi;
            if (zbir == 0) {
                linijaLike.css('width', '100%');
                linijaLike.css('background-color', 'gray');
                linijaDislike.css('width', '0%');
            }
            linijaLike.css('width', ((1 / zbir) * lajkovi * 100) + '%');
            linijaDislike.css('width', ((1 / zbir) * dislajkovi * 100) + '%');

            videoPoljeBrojLajkova.empty();
            videoPoljeBrojDislajkova.empty();
            videoPoljeBrojLajkova.append(lajkovi);
            videoPoljeBrojDislajkova.append(dislajkovi);

            if (data.loginStatus != 'unauthenticated') {
                // 0 nije
                if (data.lajkovan == 1) { // lajkovan
                    $('#video-dugme-like').css('color', 'green');
                    $('#video-dugme-dislike').css('color', '#555');
                }
                else if (data.lajkovan == 2) { // dislajkovan
                    $('#video-dugme-dislike').css('color', 'red');
                    $('#video-dugme-like').css('color', '#555');
                }
                else{
                    $('#video-dugme-like').css('color', '#555');
                    $('#video-dugme-dislike').css('color', '#555');
                }
            }
        }
        else {
            console.log('Greska pri ucitavanju lajkova sa servera.');
            console.log(data);
        }
    });
}