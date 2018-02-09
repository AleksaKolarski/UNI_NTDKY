$(document).ready(function (e) {
    var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];

    var fileInput = $('#video-new-image-chooser');
    var nazivInput = $('#video-new-naziv');
    var putanjaInput = $('#video-new-putanja');
    var opisInput = $('#video-new-opis');
    var blokiranInput = $('#video-new-blokiran');
    var vidljivostVideaInput = $('#video-new-vidljivost-videa');
    var vidljivostKomentaraInput = $('#video-new-vidljivost-komentara');
    var vidljivostRejtingaInput = $('#video-new-vidljivost-rejtinga');

    var poljeLog = $('#video-new-log');

    var deleteBtn = $('#video-new-btn-delete');
    var saveBtn = $('#video-new-btn-save');

    validacija_input(nazivInput, 4, 80);
    validacija_input(putanjaInput, 11, 11);
    validacija_input(opisInput, 0, 1024);


    // na dugme save uradi sta treba
    saveBtn.on('click', null, function(){
        poljeLog.empty();
        if (check_input(nazivInput, 4, 80) == true) {
            if (check_input(putanjaInput, 11, 11) == true) {
                if (check_input(opisInput, 0, 1024) == true) {
                    $.post('VideoNewServlet', { 'videoId':videoId, 'naziv':nazivInput.val(), 'putanja':putanjaInput.val(), 'opis':opisInput.val(), 'blokiran':blokiranInput.is(":checked"), 'vidljivostVidea':vidljivostVideaInput.val(), 'vidljivostKomentara':vidljivostKomentaraInput.is(":checked"), 'vidljivostRejtinga':vidljivostRejtingaInput.is(":checked")}, function (data) {
                        if (data.status == 'success') {
                            if($('#video-new-image-chooser').val()){
                                sendFile('video-new-image-chooser', videoId, 'VideoImageServlet');
                            }
                            if(deleteBtn.val() != null){
                                window.location.href = '/NTDKY/VideoServlet?id=' + videoId;
                            }
                            else{
                                window.location.href = '/NTDKY';
                            }
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
            poljeLog.append('Putanja mora biti dugacka tacno 11 karaktera!');
            return;
        }
        poljeLog.append('Naziv mora biti duzine od 4 do 80 karaktera!');
    });

    deleteBtn.on('click', null, function(){
        $.post('VideoNewServlet', { 'videoId':videoId, 'obrisan':true}, function (data) {
            if(data.status == 'success'){
                window.location.href = '/NTDKY';
            }
            else{
                poljeLog.append('Greska!');
            }
        });
    });
});

function promenjenaSlika(event){
    var file = event.target.files[0];
    if(file){
        var reader = new FileReader();
        reader.onload = function(e){
            $('#video-new-image').attr('src', e.target.result);
        };
        reader.readAsDataURL(file);
    }
}