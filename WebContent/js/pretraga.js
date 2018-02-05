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

            popuniRezultatePretrageVidea(data.videi);
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

    var sortByRaw = $('#sort-filter-select').val();
    var sortDirectionRaw = $('#sort-by-filter input:radio:checked').val();

    var sortBy;
    switch(sortByRaw){
        case 'Nazivu':
            sortBy = 'naziv';
            break;
        case 'Vlasniku':
            sortBy = 'vlasnik';
            break;
        case 'Broju pregleda':
            sortBy = 'brojPregleda';
            break;
        case 'Datumu':
            sortBy = 'datum';
            break;
        default:
            sortBy = 'datum';
    }

    var sortDirection;
    switch(sortDirectionRaw){
        case 'option1':
            sortDirection = 'DESC';
            break;
        case 'option2':
            sortDirection = 'ASC';
            break;
        default:
            sortDirection = 'DESC';
    }


    $.get('VideoFilterServlet', {"nazivFilter":naziv, "vlasnikFilter":vlasnik, "datumFilterMin":datumMin, "datumFilterMax":datumMax, "brojFilterMin":brojPregleda[0], "brojFilterMax":brojPregleda[1], "sortBy":sortBy, "sortDirection":sortDirection}, function(data){
        if(data.status == 'success'){
            popuniRezultatePretrageVidea(data.videi);
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

function popuniRezultatePretrageVidea(videi){
    var main = $('.main');
    main.empty();
    for(var i in videi){
        main.append(videoPanel(videi[i]));
    }
}