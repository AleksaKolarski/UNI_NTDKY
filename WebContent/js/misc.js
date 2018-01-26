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
function validacija_input(polje){
    $(polje).keyup(function(e){
        check_input(polje);
    });
}

// kacenje na email input polja
function validacija_email(polje){
    $(polje).keyup(function(e){
        check_email(polje);
    });
}

// validacija input polja
function check_input(polje){
    var duzina_sadrzaja = polje.val().length;
    if(duzina_sadrzaja >= 4 && duzina_sadrzaja <= 16){
        polje.css('border', '1px solid #ccc');
        return true;
    }
    polje.css('border', '1px solid #ff0000');
    return false;
}

// validacija email input polja
function check_email(polje){
    if(/.+\@.+\..+/.test(polje.val().toLowerCase()) && polje.val().length <= 30){
        polje.css('border', '1px solid #ccc');
        return true;
    }
    polje.css('border', '1px solid #ff0000');
    return false;
}