$(document).ready(function (e) {
    var profil = window.location.search.slice(1).split('&')[0].split('=')[1];

    var inputIme = $('#profile-input-ime');
    var inputPrezime = $('#profile-input-prezime');
    var inputOpis = $('#profile-input-opis');
    var inputTipKorisnika = $('#profile-user-type');
    var inputLozinka1 = $('#profile-lozinka1');
    var inputLozinka2 = $('#profile-lozinka2');
    var inputBlokiranje = $('#profile-user-block');

    var buttonEdit = $('#btn-profile-edit');
    var buttonSubscribe = $('#btn-subscribe');
    var buttonSave = $('#btn-profile-save-modal');
    var buttonCancel = $('#btn-profile-cancel');
    var buttonDelete = $('#btn-profile-delete-modal');

    var poljeLog = $('#profile-log');


    validacija_input(inputIme, 0, 16);
    validacija_input(inputPrezime, 0, 16);
    validacija_input(inputOpis, 0, 1024);
    validacija_input(inputLozinka1, 4, 16);
    validacija_input(inputLozinka2, 4, 16);
    validacija_password_reset(inputLozinka1, inputLozinka2);


    refreshsubscription(profil);
    buttonSubscribe.on('click', null, function () {
        $.post('PretplataServlet?korisnik=' + profil, function (data) {
            if (data.status == 'error') {
                console.log('greska pri subskrajbovanju');
            }
            else {
                refreshsubscription(profil);
                refreshSidebar();
            }
        });
    });

    
    buttonDelete.on('click', null, function(){
        $.post('ProfilServlet', { 'profil': profil, 'obrisan': 'true' }, function (data2) {
            if(data2.status == 'success'){
                window.location.href = '/NTDKY';
            }else{
                poljeLog.append('Greska pri brisanju korisnika!');
            }
        });
    });
    

    buttonEdit.on('click', null, function(){
        window.location.href = '/NTDKY/ProfilServlet?user=' + profil + '&edit=true';
    });

    buttonCancel.on('click', null, function(){
        window.location.href = '/NTDKY/ProfilServlet?user=' + profil;
    });

    buttonSave.on('click', null, function(){
        poljeLog.empty();
        if (check_input(inputIme, 0, 16) == true) {
            if (check_input(inputPrezime, 0, 16) == true) {
                if (check_input(inputOpis, 0, 1024) == true) {
                    if (inputLozinka1.val().length != 0 || inputLozinka2.val().length != 0) {
                        if (check_input(inputLozinka1, 4, 16) == true && check_input(inputLozinka2, 4, 16) == true) {
                            if (check_password_reset(inputLozinka1, inputLozinka2) == true) {
                                $.post('ProfilServlet', { 'profil': profil, 'ime': inputIme.val(), 'prezime': inputPrezime.val(), 'opis': inputOpis.val(), 'lozinka': inputLozinka1.val(), 'blokiran':inputBlokiranje.val(), 'uloga':inputTipKorisnika.val() }, function (data2) {
                                    if (data2.status == 'success') {
                                        if($('#profile-image-chooser').val()){
                                            sendFile('profile-image-chooser', profil, 'ImageServlet');
                                        }
                                        window.location.href = '/NTDKY/ProfilServlet?user=' + profil;
                                        return;
                                    }
                                    else {
                                        poljeLog.append('Greska!');
                                    }
                                });
                                return;
                            }
                            poljeLog.append('Morate uneti istu lozinku u oba polja!');
                            return;
                        }
                        poljeLog.append('Nova lozinka mora biti duzine izmedju 4 i 16!');
                        return;
                    }
                    $.post('ProfilServlet', { 'profil': profil, 'ime': inputIme.val(), 'prezime': inputPrezime.val(), 'opis': inputOpis.val(), 'blokiran':inputBlokiranje.val(), 'uloga':inputTipKorisnika.val() }, function (data2) {
                        if (data2.status == 'success') {
                            if($('#profile-image-chooser').val()){
                                sendFile('profile-image-chooser', profil, 'ImageServlet');
                            }
                            window.location.href = '/NTDKY/ProfilServlet?user=' + profil;
                            return;
                        }
                        else {
                            poljeLog.append('Greska!');
                        }
                    });
                    return;
                }
                poljeLog.append('Opis mora biti kraci od 1025 karaktera!');
                return;
            }
            poljeLog.append('Prezime mora biti krace od 17 karaktera!');
            return;
        }
        poljeLog.append('Ime mora biti krace od 17 karaktera!');
    });


    $.get('VideoFilterServlet', {"vlasnikFilter":profil}, function(data){
        if(data.status == 'success'){
            popuniRezultatePretrageVidea(data.videi, '#profile-videos');
        }
    });

    $('#sortiraj-btn').on('click', null, function(){
        sortirajVideeKorisnika(profil);
    });
});

function sortirajVideeKorisnika(profil){            
    var sortBy = $('#sort-filter-select').val();
    var sortDirection = $('#sort-by-filter input:radio:checked').val();
    
     $.get('VideoFilterServlet', {"vlasnikFilter":profil, "sortBy":sortBy, "sortDirection":sortDirection}, function(data){
        if(data.status == 'success'){
            popuniRezultatePretrageVidea(data.videi, '#profile-videos');
        }
    });
}

function promenjenaSlika(event){
    var file = event.target.files[0];
    if(file){
        var reader = new FileReader();
        reader.onload = function(e){
            $('#profile-image').attr('src', e.target.result);
        };
        reader.readAsDataURL(file);
    }
}

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