var videoFilterSlider;

$(document).ready(function (e) {
    var prvobitnaPretraga = window.location.search.slice(1).split('&')[0].split('=')[1];

    $.get('VideoFilterServlet', {"nazivFilter": prvobitnaPretraga}, function(data){
        if(data.status == 'success'){
            var naziv = $('#naziv-filter-polje').val(prvobitnaPretraga);
            
            var brojSlider = document.getElementById('broj-slider');

            videoFilterSlider = noUiSlider.create(brojSlider, {
                start: [0, data.preglediMinMax[1] + (data.preglediMinMax[1] * 0.1 + data.preglediMinMax[1]%10)],
                tooltips: true, 
                format: {
                    to: function (value) {
                        return Math.round(value);
                    },
                    from: function (value) {
                        return Math.round(value);
                    }
                }, 
                range: {
                    'min': 0,
                    'max': data.preglediMinMax[1] + (data.preglediMinMax[1] * 0.1 + data.preglediMinMax[1]%10)
                },
                connect: true,
                step: 1
            });

            popuniRezultatePretrageVidea(data.videi, '.main');
        }
    });
    

    $('#datum-filter').datepicker({
        format: "dd.m.yyyy."
    });

    $('#pretraga-btn').on('click', null, function(){
        novaPretragaVidea();
    });

    $('#datum-filter-clear-btn').on('click', null, function(){
        $('#datum-filter input').each(function() {
            $(this).datepicker('clearDates');
        });
    });
});

function novaPretragaVidea(){
    
    var naziv = $('#naziv-filter-polje').val();
    var vlasnik = $('#vlasnik-filter-polje').val();
    
    var brojPregleda = videoFilterSlider.get();

    var datumMin = $('#datum-filter1').val();
    var datumMax = $('#datum-filter2').val();

    var sortBy = $('#sort-filter-select').val();
    var sortDirection = $('#sort-by-filter input:radio:checked').val();

    $.get('VideoFilterServlet', {"nazivFilter":naziv, "vlasnikFilter":vlasnik, "datumFilterMin":datumMin, "datumFilterMax":datumMax, "brojFilterMin":brojPregleda[0], "brojFilterMax":brojPregleda[1], "sortBy":sortBy, "sortDirection":sortDirection}, function(data){
        if(data.status == 'success'){
            popuniRezultatePretrageVidea(data.videi, '.main');
        }
    });

    /*
    console.log('naziv:');
    console.log(naziv);
    console.log('vlasnik:');
    console.log(vlasnik);
    console.log('brMin:');
    console.log(brojPregleda[0]);
    console.log('brMax:');
    console.log(brojPregleda[1]);
    console.log('datumMin:');
    console.log(datumMin);
    console.log('datumMax:');
    console.log(datumMax);
    console.log('sortFilter:');
    console.log(sortBy);
    console.log('sortDirection:');
    console.log(sortDirection);
    */
}

