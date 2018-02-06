/* Fajl sa raznim js funkcijama:
** -kacenje na input polja
** -kacenje na email input polja
** -validacija input polja
** -validacija email polja
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

// kacenje na input polja
function validacija_input(polje, min, max) {
    $(polje).keyup(function (e) {
        check_input(polje, min, max);
    });
}

// kacenje na email input polja
function validacija_email(polje) {
    $(polje).keyup(function (e) {
        check_email(polje);
    });
}

// kacenje na password reset polja
function validacija_password_reset(polje1, polje2) {
    $(polje1).keyup(function (e) {
        check_password_reset(polje1, polje2);
    });
    $(polje2).keyup(function (e) {
        check_password_reset(polje1, polje2);
    });
}

// validacija input polja
function check_input(polje, min, max) {
    var duzina_sadrzaja = polje.val().length;
    if (duzina_sadrzaja >= min && duzina_sadrzaja <= max) {
        polje.css('border', '1px solid #ccc');
        return true;
    }
    polje.css('border', '1px solid #ff0000');
    return false;
}

// validacija email input polja
function check_email(polje) {
    if (/.+\@.+\..+/.test(polje.val().toLowerCase()) && polje.val().length <= 30) {
        polje.css('border', '1px solid #ccc');
        return true;
    }
    polje.css('border', '1px solid #ff0000');
    return false;
}

function check_password_reset(polje1, polje2) {
    if (polje1.val() == polje2.val()) {
        polje2.css('border', '1px solid #ccc');
        return true;
    }
    polje2.css('border', '1px solid #ff0000');
    return false;
}

function videoPanel(video) {
    return  '<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 video-panel">' +
                    '<div class="panel panel-default">' +
                        '<a href="VideoServlet?id=' + video.id + '">' +
                            '<div class="video-panel-body">' +
                                '<img src="img/video/' + video.putanjaSlike + '">' +
                            '</div>' +
                        '</a>' +
                        '<div class="video-panel-footer">' + 
                            '<a href="VideoServlet?id=' + video.id + '">' +
                                '<p id="video-panel-naziv">' + video.naziv + '</p>' +
                            '</a>' +
                            '<a href="profil.html?user=' + video.vlasnik + '">' +
                                '<p id="video-panel-vlasnik">' + video.vlasnik + '</p>' +
                            '</a>' + 
                            '<p id="video-panel-pregledi">' + video.brojPregleda + ' <span class="glyphicon glyphicon-play-circle"></span></p>' +
                            '<br>' + 
                            '<p id="video-panel-datum">' + $.format.date(video.datum, "dd.MM.yyyy. HH:mm") + '</p>' +
                            '<p id="video-panel-lajkovi">' + (video.brojLajkova[0] - video.brojLajkova[1]) + ' <span class="glyphicon glyphicon-thumbs-up"></span></p>' +
                        '</div>' +
                    '</div>' +
            '</div>';
}

String.prototype.trunc =
     function( n, useWordBoundary ){
         if (this.length <= n) { return this; }
         var subString = this.substr(0, n-1);
         return (useWordBoundary 
            ? subString.substr(0, subString.lastIndexOf(' ')) 
            : subString) + "&hellip";
      };