/* Fajl za pocetnu stranu.
** -Pri ucitavanju stranice 
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function (e) {
    $.get('VideoFilterServlet', null, function (data) {
        if (data.status == 'success') {
            var main = $('.main');
            var videi = data.videi;
            for (var i in videi) {
                main.append(videoPanel(videi[i]));
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
    });
});