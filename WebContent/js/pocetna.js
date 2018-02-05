/* Fajl za pocetnu stranu.
** -Pri ucitavanju stranice 
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function(e){
    $.get('VideoFilterServlet', null, function(data){
        if(data.status == 'success'){
            var main = $('.main');
            var videi = data.videi;
            for(var i in videi){
                main.append(videoPanel(videi[i]));
            }
        }
    });
});