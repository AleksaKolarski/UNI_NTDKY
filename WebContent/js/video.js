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

    refreshsubscription(videoId);

    $('#btn-subscribe').on('click', null, function () {
        $.post('PretplataServlet?videoId=' + videoId, function (data) {
            if (data.status == 'error') {
                console.log('greska pri subskrajbovanju');
            }
            else {
                refreshsubscription(videoId);
                refreshSidebar();
            }
        });
    });

    // preuzimanje lajkova
    refreshVideoLikes(videoId);

    $('#video-dugme-like-link').on('click', null, function () {
        $.post('LajkServlet?target=VIDEO&id=' + videoId + '&tip=like', function (data) {
            if (data.status == 'error') {
                console.log('greska pri lajkovanju');
            }
            else {
                refreshVideoLikes(videoId);
            }
        });
    });
    $('#video-dugme-dislike-link').on('click', null, function () {
        $.post('LajkServlet?target=VIDEO&id=' + videoId + '&tip=dislike', function (data) {
            if (data.status == 'error') {
                console.log('greska pri dislajkovanju');
            }
            else {
                refreshVideoLikes(videoId);
            }
        });
    });

    refreshComments(videoId, 0, 5);

    if ($('#komentar-novi-button').length > 0) {
        $('#komentar-novi-button').on('click', null, function () {
            var komentarInput = $('#komentar-novi-text');
            if (komentarInput.val().length > 0) {
                addComment(komentarInput.val(), videoId);
            }
        });
    }
});


function addComment(sadrzaj, videoId) {
    $.post('KomentarServlet', { 'sadrzaj': sadrzaj, 'videoId': videoId }, function (data) {
        if (data.status == 'success') {
            refreshComments(videoId, 0, 5);
            $('#komentar-novi-text').val('');
        }
        else {
            console.log('greska pri kreiranju komentara');
        }
    });
}


function refreshsubscription(videoId) {
    $.get('PretplataServlet?videoId=' + videoId, function (data) {
        var dugme = $("#btn-subscribe");
        if (data.status == "success") {
            if (data.pretplacen == true) {
                dugme.css("background-color", "#DDD");
                dugme.text("Unsubscribe");
                dugme.css("visibility", "visible");
            }
            else if (data.pretplacen == false) {
                // dugme.css();
                dugme.css("background-color", "#FFF");
                dugme.text("Subscribe");
                dugme.css("visibility", "visible");
            }
        }
    });
}


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
                    $('#video-dugme-like').css('color', '#0CB71B');
                    $('#video-dugme-dislike').css('color', '#555');
                }
                else if (data.lajkovan == 2) { // dislajkovan
                    $('#video-dugme-dislike').css('color', 'red');
                    $('#video-dugme-like').css('color', '#555');
                }
                else {
                    $('#video-dugme-like').css('color', '#555');
                    $('#video-dugme-dislike').css('color', '#555');
                }
            }
        }
        else {
            console.log('Greska pri ucitavanju lajkova sa servera.');
        }
    });
}


function refreshCommentsBezPaginacije(videoId) {
    $.get('KomentarServlet?videoId=' + videoId, function (data) {
        var ulogovani = data.ulogovani;
        if (data.status == 'success') {
            var komentari = data.komentari;
            var poljeKomentara = $('#komentari-lista');
            poljeKomentara.empty();
            for (var i in komentari) {
                ispisiKomentar(komentari[i], poljeKomentara);
                if (i != komentari.length - 1) {
                    poljeKomentara.append('<hr>');
                }
            }
        }
    });
}

function refreshComments(videoId, page, count) {
    $.get('KomentarServlet?videoId=' + videoId, function (data) {
        var ulogovani = data.ulogovani;
        if (data.status == 'success') {
            var komentari = data.komentari;
            var poljeKomentara = $('#komentari-lista');
            poljeKomentara.empty();
            for (var i in komentari) {
                if (i >= page * count && i < (page + 1) * count) {
                    ispisiKomentar(komentari[i], poljeKomentara);
                    if (i != komentari.length - 1 && i != (page + 1) * count - 1) {
                        poljeKomentara.append('<hr>');
                    }
                }
            }
            $('#paginacija').empty();
            if (komentari.length > count) {
                $('#paginacija-nav').css('height', 'auto');
                for (var i = 0; i < komentari.length / count; i++) {
                    dodajStranicuPaginacije(videoId, i, count, page);
                }
            }
            else{
                $('#paginacija-nav').css('height', '0px');
            }
        }
    });
}

function dodajStranicuPaginacije(videoId, page, count, currentPage) {
    $('#paginacija').append('<li><a href="#komentari-container" id="paginacija-komentar-' + page + '">' + (page + 1) + '</a></li>');
    $('#paginacija-komentar-' + page).on('click', function () {
        refreshComments(videoId, page, count);
    });
    if (page == currentPage) {
        $('#paginacija-komentar-' + page).css('background-color', '#337ab7');
        $('#paginacija-komentar-' + page).css('color', '#FFF');
    }
    else {
        $('#paginacija-komentar-' + page).css('background-color', '#FFF');
        $('#paginacija-komentar-' + page).css('color', '#337ab7');
    }
}

function ispisiKomentar(komentar, poljeKomentara) {

    poljeKomentara.append(
        '<div class="komentar" id="komentar-' + komentar.id + '">' +
        '<a href="profil.html?user=' + komentar.vlasnik + '">' +
        '<p class="komentar-ime">' + komentar.vlasnik + '</p>' +
        '</a>' +
        '<p class="komentar-datum">' + $.format.date(komentar.datum, "dd.MM.yyyy. HH:mm") + '</p>' +
        '<p class="komentar-sadrzaj">' + komentar.sadrzaj + '</p>' +
        '<a href="#komentar-like-' + komentar.id + '" id="komentar-like-' + komentar.id + '" class="komentar-dugme-link">' +
        '<span id="komentar-like-dugme-' + komentar.id + '" class="glyphicon glyphicon-thumbs-up komentar-button-like"></span>' +
        '</a>' +
        '<p class="komentar-broj-like" id="komentar-broj-like-' + komentar.id + '"></p>' +
        '<a href="#komentar-dislike-' + komentar.id + '" id="komentar-dislike-' + komentar.id + '" class="komentar-dugme-link">' +
        '<span id="komentar-dislike-dugme-' + komentar.id + '" class="glyphicon glyphicon-thumbs-down komentar-button-dislike"></span>' +
        '</a>' +
        '<p class="komentar-broj-dislike" id="komentar-broj-dislike-' + komentar.id + '"></p>' +
        '</div>');
    refreshCommentLikes(komentar.id);

    $('#komentar-like-' + komentar.id).on('click', null, function () {
        $.post('LajkServlet?target=KOMENTAR&id=' + komentar.id + '&tip=like', function (data) {
            if (data.status == 'error') {
                console.log('greska pri lajkovanju');
            }
            else {
                refreshCommentLikes(komentar.id);
            }
        });
    });
    $('#komentar-dislike-' + komentar.id).on('click', null, function () {
        $.post('LajkServlet?target=KOMENTAR&id=' + komentar.id + '&tip=dislike', function (data) {
            if (data.status == 'error') {
                console.log('greska pri lajkovanju');
            }
            else {
                refreshCommentLikes(komentar.id);
            }
        });
    });
}


function refreshCommentLikes(komentarId) {
    $.get('LajkServlet?target=KOMENTAR&id=' + komentarId, function (data) {
        if (data.status == 'success') {
            if (data.loginStatus != 'unauthenticated') {
                // 0 nije
                if (data.lajkovan == 1) { // lajkovan
                    $('#komentar-like-dugme-' + komentarId).css('color', '#0CB71B');
                    $('#komentar-dislike-dugme-' + komentarId).css('color', '#555');
                }
                else if (data.lajkovan == 2) { // dislajkovan
                    $('#komentar-dislike-dugme-' + komentarId).css('color', 'red');
                    $('#komentar-like-dugme-' + komentarId).css('color', '#555');
                }
                else {
                    $('#komentar-like-dugme-' + komentarId).css('color', '#555');
                    $('#komentar-dislike-dugme-' + komentarId).css('color', '#555');
                }
            }
            $('#komentar-broj-like-' + komentarId).text(data.lajkovi[0]);
            $('#komentar-broj-dislike-' + komentarId).text(data.lajkovi[1]);
        }
    });
}