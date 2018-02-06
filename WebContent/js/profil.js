$(document).ready(function (e) {
    var profil = window.location.search.slice(1).split('&')[0].split('=')[1];

    refreshsubscription(profil);

    $('#btn-subscribe').on('click', null, function () {
        $.post('PretplataServlet?korisnik=' + profil, function (data) {
            if (data.status == 'error') {
                console.log('greska pri subskrajbovanju');
            }
            else {
                refreshsubscription(profil);
            }
        });
    });


    $('#btn-profile-edit').on('click', null, function(){
        window.location.href = '/NTDKY/ProfilServlet?user=' + profil + '&edit=true';
    });

    $('#btn-profile-cancel').on('click', null, function(){
        window.location.href = '/NTDKY/ProfilServlet?user=' + profil;
    });
});

function refreshsubscription(profil) {
    $.get('PretplataServlet?korisnik=' + profil, function (data) {
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