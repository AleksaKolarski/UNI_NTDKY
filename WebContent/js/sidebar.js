/* Fajl za levu navigaciju.
** -Pri ucitavanju stranice proveravamo da li je trenutna sesija validna. Ako
**  jeste, prikazujemo u levoj navigaciji link za pretplate i pojedinacne 
**  pretplate ulogovanog korisnika. Ako nije prikazujemo link ka popularnim 
**  kanalima i izlistavamo nekoliko najpopularnijih.  
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function(e){
    /* Proveravamo da li je korisnik ulogovan.
    */

    $.get('SidebarServlet', null, function(data){
        /* Vraca success ili unauthenticated. Takodje vraca i listu kanala 
        ** koju treba izlistati, u slucaju ulogovanog to su njegove pretplate 
        ** a u slucaju neulogovanog onda najpopularnije. 
        */

        var polje = $("#navigacija-levo > ul");
        var kanali = data.kanali;

        if(data.status == 'success'){ // success
            // ulogovan je, prikazi pretplate
            var korisnik = data.korisnik;
            polje.append(
                '<a href="ProfilServlet?user=' + korisnik + '">' + 
                    '<li class="sidebar-menu-item">' + 
                        'Moj kanal' + 
                    '</li>' + 
                '</a>'
            );
            // prikazi listu pretplata
            if(kanali.length > 0){
                polje.append(
                    '<a href="#">' + 
                        '<li class="sidebar-menu-item">' + 
                            'Pretplate:' + 
                        '</li>' + 
                    '</a>'
                );
                for(var i in kanali){
                    polje.append(
                        '<a href="ProfilServlet?user=' + kanali[i] + '">' + 
                            '<li class="sidebar-subscribed-channel">' + 
                                kanali[i] + 
                                '<img src="img/profile/profile-photo.png" alt="profile photo">' + 
                            '</li>' + 
                        '</a>'
                    );
                }
            }
            else{
                polje.append(
                    '<a href="#">' + 
                        '<li class="sidebar-menu-item">' + 
                            'Nema pretplata.' + 
                        '</li>' + 
                    '</a>'
                );
            }
        }
        else{   // unauthenticated

            // nije ulogovan, prikazi popularne kanale
            polje.append(
                '<a href="#">' + 
                    '<li class="sidebar-menu-item">' + 
                        'Popularni kanali:' + 
                    '</li>' +  
                '</a>'
            );
            for(var i in kanali){
                polje.append(
                    '<a href="ProfilServlet?user=' + kanali[i] + '">' + 
                        '<li class="sidebar-subscribed-channel">' + 
                            kanali[i] + 
                            '<img src="img/profile/profile-photo.png" alt="profile photo">' + 
                        '</li>' + 
                    '</a>'
                );
            }
        }
    });
});