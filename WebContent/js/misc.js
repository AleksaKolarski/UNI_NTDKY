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
function validacija_input(polje, min, max){
    $(polje).keyup(function(e){
        check_input(polje, min, max);
    });
}

// kacenje na email input polja
function validacija_email(polje){
    $(polje).keyup(function(e){
        check_email(polje);
    });
}

// kacenje na password reset polja
function validacija_password_reset(polje1, polje2){
    $(polje1).keyup(function(e){
        check_password_reset(polje1, polje2);
    });
    $(polje2).keyup(function(e){
        check_password_reset(polje1, polje2);
    });
}

// validacija input polja
function check_input(polje, min, max){
    var duzina_sadrzaja = polje.val().length;
    if(duzina_sadrzaja >= min && duzina_sadrzaja <= max){
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

function check_password_reset(polje1, polje2){
    if(polje1.val() == polje2.val()){
        polje2.css('border', '1px solid #ccc');
        return true;
    }
    polje2.css('border', '1px solid #ff0000');
    return false;
}