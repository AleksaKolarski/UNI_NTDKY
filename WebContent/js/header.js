/* Fajl za gornju navigaciju.
** -Pri ucitavanju stranice proveravamo da li je trenutna sesija validna. Ako
**  jeste, prikazujemo u desnom delu padajuci meni sa opcijama za ulogovanog 
**  korisnika. Ako nije, prikazujemo padajuce menije za login i registraciju. 
**  Sva polja za unos se proveravaju pre slanja serveru. 
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function(e){
    /* Proveravamo da li je korisnik ulogovan.
    ** Ako nije, prikazujemo dva dugmeta (login|register). 
    ** Ako jeste, prikazujemo padajuci meni sa opcijama.
    */

    $.get('KorisnikServlet', null, function(data){
        var polje = $("#navigacija-gore-polje");
        polje.empty();

        if(data.status == 'success'){ // success
            // ulogovan je, prikazi padajuci meni za profil
            var korisnik = data.korisnik;
            polje.append(
                '<div class="btn-group btn-gore-group-login-register-profil">' + 
                    '<div class="btn-group">' + 
                        '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">' + 
                            korisnik.korisnickoIme  + ' ' + 
                            '<span class="caret"></span>' + 
                        '</button>' + 
                        '<ul class="dropdown-menu dropdown-menu-right" id="profil-dropdown">' + 
                            '<li><a href="profil.html?user=' + korisnik.korisnickoIme + '">Moj profil</a></li>' + 
                            '<li class="divider"></li>' + 
                            '<li><a href="LogoutServlet">Izloguj se</a></li>' + 
                        '</ul>' + 
                    '</div>' + 
                '</div>'
            );
        }
        else{   // unauthenticated
            // nije ulogovan, prikazi dugme za login i registraciju

            // polja i dugmic za login
            var login_korisnicko_ime;
            var login_lozinka;
            var login_dugme;
            var login_log;

            // polja i dugmic za registraciju
            var register_ime;
            var register_prezime;
            var register_email;
            var register_korisnicko_ime;
            var register_lozinka;
            var register_dugme;
            var register_log;


            // prikazi login i register dugmice
            polje.append(
                '<div class="btn-group btn-gore-group-login-register-profil">' + 
                    '<div class="btn-group">' + 
                        '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">' + 
                            'Login' + ' ' + 
                            '<span class="caret"></span>' + 
                        '</button>' + 
                        '<ul class="dropdown-menu dropdown-menu-right" id="login-dropdown">' + 
                            '<li>' + 
                                '<input type="text" class="form-control" id="login_korisnicko_ime" placeholder="Korisnicko ime">' + 
                            '</li>' + 
                            '<li>' + 
                                '<input type="password" class="form-control" id="login_lozinka" placeholder="Lozinka">' + 
                            '</li>' + 
                            '<li><button type="button" class="btn btn-default" id="login_button">Login</button> <p id="login_log"></p></li>' + 
                        '</ul>' + 
                    '</div>' + 
                    '<div class="btn-group">' + 
                        '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">' + 
                            'Register' + ' ' + 
                            '<span class="caret"></span>' + 
                        '</button>' + 
                        '<ul class="dropdown-menu dropdown-menu-right" id="register-dropdown">' + 
                            '<li>' + 
                                '<input type="text" class="form-control" id="register_ime" placeholder="Ime">' + 
                            '</li>' + 
                            '<li>' + 
                                '<input type="text" class="form-control" id="register_prezime" placeholder="Prezime">' + 
                            '</li>' + 
                            '<li>' + 
                                '<input type="email" class="form-control" id="register_email" placeholder="Email">' + 
                            '</li>' + 
                            '<li>' + 
                                '<input type="text" class="form-control" id="register_korisnicko_ime" placeholder="Korisnicko ime">' + 
                            '</li>' + 
                            '<li>' + 
                                '<input type="password" class="form-control" id="register_lozinka" placeholder="Lozinka">' + 
                            '</li>' + 
                            '<li><button type="button" class="btn btn-default" id="register_button">Register</button> <p id="register_log"></p></li>' + 
                        '</ul>' + 
                    '</div>' + 
                '</div>'
            );

            login_korisnicko_ime = $("#login_korisnicko_ime");
            login_lozinka = $("#login_lozinka");
            login_dugme = $("#login_button");
            login_log = $('#login_log');

            register_ime = $("#register_ime");
            register_prezime = $("#register_prezime");
            register_email = $("#register_email");
            register_korisnicko_ime = $("#register_korisnicko_ime");
            register_lozinka = $("#register_lozinka");
            register_dugme = $("#register_button");
            register_log = $('#register_log');

            // Sprecavamo nestanak dropdown-a ako kliknemo nesto (dugme) na njemu
            $(document).on('click', '.btn-gore-group-login-register-profil .dropdown-menu', function (e) {
                e.stopPropagation();
            });

            // kacimo validaciju na input polja
            validacija_input(login_korisnicko_ime, 4, 16);
            validacija_input(login_lozinka, 4, 16);
            validacija_input(register_ime, 0, 16);
            validacija_input(register_prezime, 0, 16);
            validacija_email(register_email);
            validacija_input(register_korisnicko_ime, 4, 16);
            validacija_input(register_lozinka, 4, 16);

            // na pritisak login dugmeta
            $(login_dugme).on('click', function(event){
                // vrsimo proveru trenutnog stanja polja
                login_log.empty();
                if(check_input(login_korisnicko_ime, 4, 16) == true){
                    if(check_input(login_lozinka, 4, 16) == true){
                        $.post('LoginServlet', {'korisnickoIme': login_korisnicko_ime.val(), 'lozinka': login_lozinka.val()}, function(data){
                            if(data.status == 'success'){
                                // dobar login, refresuj stranicu
                                window.location.reload(true);
                                return;
                            }
                            // pogresni kredencijali
                            login_log.append('Pogresni podaci!');
                        });
                        return;
                    }
                    // greska pri unosu lozinke
                    login_log.append('Lozinka mora biti duza od 3 i kraca od 17 karaktera!');
                    return;
                }
                // greska pri unosu korisnickog imena
                login_log.append('Korisnicko ime mora biti duze od 3 i krace od 17 karaktera!');
            });

            $(register_dugme).on('click', function(event){
                // vrsimo proveru trenutnog stanja polja
                register_log.empty();
                if(check_input(register_ime, 4, 16) == true){
                    if(check_input(register_prezime, 4, 16) == true){
                        if(check_email(register_email) == true){
                            if(check_input(register_korisnicko_ime, 4, 16) == true){
                                if(check_input(register_lozinka, 4, 16) == true){
                                    $.post('RegisterServlet', {'ime': register_ime.val(), 'prezime': register_prezime.val(), 'email': register_email.val(), 'korisnickoIme': register_korisnicko_ime.val(), 'lozinka': register_lozinka.val()}, function(data){
                                        if(data.status == 'success'){
                                            // dobra registracija
                                            window.location.reload(true);
                                            window.alert('Uspesno ste se registrovali! Ulogujte se sa vasim podacima.');
                                            return;
                                        }
                                        else if(data.status == 'taken'){
                                            // korisnicko ime vec zauzeto
                                            register_log.append('Korisnicko ime je vec zauzeto, odaberite drugo.');
                                            return;
                                        }
                                        // greska pri registraciji
                                        register_log.append('Greska pri registraciji!');
                                    });
                                    return;
                                }
                                // greska pri unosu lozinke
                                register_log.append('Lozinka mora biti duza od 3 i kraca od 17 karaktera!');
                                return;
                            }
                            // greska pri unosu korisnickog imena
                            register_log.append('Korisnicko ime mora biti duze od 3 i krace od 17 karaktera!');
                            return;
                        }
                        // greska pri unosu email-a
                        register_log.append('Pogresan format email-a!');
                        return;
                    }
                    // greska pri unosu prezimena
                    register_log.append('Prezime mora biti duze od 3 i krace od 17 karaktera!');
                    return;
                }
                // greska pri unosu imena
                register_log.append('Ime mora biti duze od 3 i krace od 17 karaktera!');
            });
        }
    });
});