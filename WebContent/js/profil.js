/* Fajl za profil.
** -Pri ucitavanju se iz linka preuzima korisnicko ime profila koji 
**  treba da se prikaze. Na osnovu toga se prikazuje taj profil i 
**  u zavisnosti od toga da li trenutno ulogovani korisnik gleda 
**  svoj profil prikazujemo dugme za izmenu ili dugme za subscribe. 
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function (e) {

    // uzimamo korisnickoIme profila iz url-a
    var profil = window.location.search.slice(1).split('&')[0].split('=')[1];
    var poljeDetails = $('div.profile-details');

    $.get('ProfilServlet', { 'profil': profil }, function (data) {

        var poljeKorisnickoIme = $('#polje-korisnicko-ime');
        var poljeIme = $('#polje-ime');
        var poljePrezime = $('#polje-prezime');
        var poljeOpis = $('#polje-opis');
        var poljeDatum = $('#polje-datum');
        var poljeUloga = $('#polje-uloga');
        var poljeBrojPratilaca = $('#polje-broj-pratilaca');

        var inputIme;
        var inputPrezime;
        var inputOpis;
        var inputLozinka1;
        var inputLozinka2;
        var poljeLog;


        if (data.status_profile == 'found') {
            $('title').append(data.korisnickoIme);
            poljeKorisnickoIme.append(data.korisnickoIme);
            poljeIme.append((data.ime) ? data.ime : '/');
            poljePrezime.append((data.prezime) ? data.prezime : '/');
            poljeOpis.append('<p>' + ((data.opis) ? data.opis : '/') + '</p>');
            poljeDatum.append(data.datum);
            poljeUloga.append(data.uloga);
            poljeBrojPratilaca.append(data.brojPratilaca);

            // dodati videe
        }
        else {
            poljeDetails.empty();
            poljeDetails.append('Trazeni korisnik ne postoji!');
        }

        if (data.status_login == 'success') {
            if (data.ulogovani && data.ulogovani == profil) {
                poljeDetails.append('<button type="button" class="btn btn-default btn-edit-profile">Edit profile</button>');
                poljeDetails.on('click', '.btn-edit-profile', function () {
                    $('.btn-edit-profile').remove();

                    // polja se pretvaraju u input polja
                    poljeIme.empty();
                    poljeIme.append('<input type="text" class="form-control">');
                    inputIme = poljeIme.find('input');
                    inputIme.val(data.ime);
                    validacija_input(inputIme, 0, 16);

                    poljePrezime.empty();
                    poljePrezime.append('<input type="text" class="form-control">');
                    inputPrezime = poljePrezime.find('input');
                    inputPrezime.val(data.prezime);
                    validacija_input(inputPrezime, 0, 16);

                    poljeOpis.empty();
                    //poljeOpis.append('<input type="text" class="form-control" id="input-opis-profil">');
                    poljeOpis.append('<textarea class="form-control" rows="4" id="input-opis-profil"></textarea>');
                    poljeOpis.css('height', 'auto');
                    inputOpis = poljeOpis.find('textarea');
                    inputOpis.val(data.opis);
                    validacija_input(inputOpis, 0, 1024);

                    poljeDetails.append('Nova lozinka: <div class="details-polje"><input type="password" class="form-control" id="input-lozinka1"></div> <br>');
                    poljeDetails.append('Ponovite lozinku: <div class="details-polje"><input type="password" class="form-control" id="input-lozinka2"></div> <br>');

                    inputLozinka1 = poljeDetails.find('#input-lozinka1');
                    inputLozinka2 = poljeDetails.find('#input-lozinka2');
                    validacija_input(inputLozinka1, 4, 16);
                    validacija_input(inputLozinka2, 4, 16);
                    validacija_password_reset(inputLozinka1, inputLozinka2);

                    poljeDetails.append('<button type="button" class="btn btn-default btn-save-profile">Save</button>');
                    poljeDetails.append('<button type="button" class="btn btn-default btn-cancel-profile">Cancel</button>');
                    poljeDetails.append('<p id="save-profile-log"></p>');

                    poljeLog = poljeDetails.find('#save-profile-log');


                    var saveProfileButton = poljeDetails.find('button.btn-save-profile');
                    saveProfileButton.on('click', null, function () {
                        // pritisnuto save dugme

                        poljeLog.empty();
                        if (check_input(inputIme, 0, 16) == true) {
                            if (check_input(inputPrezime, 0, 16) == true) {
                                if (check_input(inputOpis, 0, 1024) == true) {
                                    if (inputLozinka1.val().length != 0 || inputLozinka2.val().length != 0) {
                                        if (check_input(inputLozinka1, 4, 16) == true && check_input(inputLozinka2, 4, 16) == true) {
                                            if (check_password_reset(inputLozinka1, inputLozinka2) == true) {
                                                $.post('ProfilServlet', { 'ime': inputIme.val(), 'prezime': inputPrezime.val(), 'opis': inputOpis.val(), 'lozinka': inputLozinka1.val() }, function (data2) {
                                                    if (data2.status == 'success') {
                                                        window.location.reload(true);
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
                                    $.post('ProfilServlet', { 'ime': inputIme.val(), 'prezime': inputPrezime.val(), 'opis': inputOpis.val() }, function (data2) {
                                        if (data2.status == 'success') {
                                            window.location.reload(true);
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
                    var cancelProfileButton = poljeDetails.find('button.btn-cancel-profile');
                    cancelProfileButton.on('click', null, function () {
                        // pritisnuto cancel dugme
                        window.location.reload(true);
                    });
                });
            }
            else {
                // ako je ulogovani korisnik subscribovan prikazi unsubscribe ako ne onda prikazi subscribe
                poljeDetails.append(
                    '<button type="button" class="btn btn-default btn-subscribe-profile">' +
                    ((data.pretplacen == 'true') ? 'Unsubscribe' : 'Subscribe') +
                    '</button>');
                poljeDetails.on('click', '.btn-subscribe-profile', function () {
                    console.log('subscribe');
                });
            }
        }

        /*
        if(data.status == 'success'){ // success
            
        }
        else{   // unauthenticated

        }
        */
    });
});