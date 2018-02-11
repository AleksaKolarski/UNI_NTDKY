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

function sendFile(odakle, naziv, gde){
    var formData = new FormData();
    formData.append(naziv, document.getElementById(odakle).files[0]);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", gde, false);
    xhr.send(formData);
    console.log(xhr);
}

function videoPanel(video) {
    return  '<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 video-panel">' +
                    '<div class="panel panel-default" id="video-panel-' + video.id + '">' +
                        '<a href="VideoServlet?id=' + video.id + '">' +
                            '<div class="video-panel-body">' +
                                '<img class="video-panel-img" src="img/video/icon/' + video.putanjaSlike + '" onerror="nemaSlike(event)" />' +
                                //'<img src="img/video/icon/' + video.putanjaSlike + '" >' +
                                
                            '</div>' +
                        '</a>' +
                        '<div class="video-panel-footer">' + 
                            '<a href="VideoServlet?id=' + video.id + '">' +
                                '<p id="video-panel-naziv">' + video.naziv + '</p>' +
                            '</a>' +
                            '<a href="ProfilServlet?user=' + video.vlasnik + '">' +
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

function videoPanelImgResize() {
    var mapElements = document.querySelectorAll('.video-panel-img');
    var width;
    if(mapElements[0]){
        width = mapElements[0].offsetWidth;
    }
    mapElements.forEach(element => {
        element.style.height = (width * 0.5625) + 'px';
    });
}

function nemaSlike(event){
    event.target.src="./img/video/icon/_.png";
}


function popuniRezultatePretrageVidea(videi, gde){
    var poljeZaPopunjavanje = $(gde);
    poljeZaPopunjavanje.empty();
    for(var i in videi){
        poljeZaPopunjavanje.append(videoPanel(videi[i]));
        if(videi[i].vidljivostVidea == 'UNLISTED'){
            $('#video-panel-' + videi[i].id).css('background', '#F3F3F3');
        }
        if(videi[i].vidljivostVidea == 'PRIVATE'){
            $('#video-panel-' + videi[i].id).css('background', '#EAF6FF'); //d3eafF
        }
        if(videi[i].blokiran == '1'){
            $('#video-panel-' + videi[i].id).css('background', '#FCC');
        }
    }

    videoPanelImgResize();
    window.addEventListener("resize", videoPanelImgResize);
}