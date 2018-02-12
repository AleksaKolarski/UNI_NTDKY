/* Fajl za video.
** -
**
** Autor: Aleksa Kolarski (SF 27/2016)
** 2018
*/

$(document).ready(function (e) {
	var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];

	// inicijalizacija padajuceg menija za detalje videa
	var linkZaPadajuci = $('#video-details-toggle');
	var padajuci = $('#collapseOne');
	padajuci.on('shown.bs.collapse', function () {
		linkZaPadajuci.empty();
		linkZaPadajuci.append('<span class="glyphicon glyphicon-menu-up"></span>');
	});
	padajuci.on('hidden.bs.collapse', function () {
		linkZaPadajuci.empty();
		linkZaPadajuci.append('<span class="glyphicon glyphicon-menu-down"></span>');
	});

	refreshsubscription(videoId);

	$('#btn-subscribe').on('click', null, function (event) {
		event.preventDefault();
		$.post('PretplataServlet?videoId=' + videoId, function (data) {
			if (data.status == 'error') {
				console.log('greska pri subskrajbovanju');
			}
			else {
				refreshsubscription(videoId);
				refreshSidebar();
			}
		});
	});

	// preuzimanje lajkova
	refreshVideoLikes(videoId);

	$('#video-dugme-like-link').on('click', null, function (event) {
		event.preventDefault();
		$.post('LajkServlet?target=VIDEO&id=' + videoId + '&tip=like', function (data) {
			if (data.status == 'error') {
				console.log('greska pri lajkovanju');
			}
			else {
				refreshVideoLikes(videoId);
			}
		});
	});
	$('#video-dugme-dislike-link').on('click', null, function (event) {
		event.preventDefault();
		$.post('LajkServlet?target=VIDEO&id=' + videoId + '&tip=dislike', function (data) {
			if (data.status == 'error') {
				console.log('greska pri dislajkovanju');
			}
			else {
				refreshVideoLikes(videoId);
			}
		});
	});

	refreshComments(videoId, 0, 5);

	if ($('#komentar-novi-button').length > 0) {
		$('#komentar-novi-button').on('click', null, function () {
			var komentarInput = $('#komentar-novi-text');
			if (komentarInput.val().length > 0 && komentarInput.val().length <= 1024) {
				addComment(komentarInput.val(), videoId);
			}
		});
	}

	validacija_input($('#komentar-novi-text'), 1, 1024);
});


function addComment(sadrzaj, videoId) {
	$.post('KomentarServlet', { 'sadrzaj': sadrzaj, 'videoId': videoId }, function (data) {
		console.log(data);
		if (data.status == 'success') {
			refreshComments(videoId, 0, 5);
			$('#komentar-novi-text').val('');
		}
		else {
			console.log('greska pri kreiranju komentara');
		}
	});
}


function refreshsubscription(videoId) {
	$.get('PretplataServlet?videoId=' + videoId, function (data) {
		var dugme = $("#btn-subscribe");
		if (data.status == "success") {
			if (data.pretplacen == true) {
				dugme.css("background-color", "#DDD");
				dugme.html('Unsubscribe <span class="badge">' + data.broj + '</span>');
				dugme.css("visibility", "visible");
			}
			else if (data.pretplacen == false) {
				// dugme.css();
				dugme.css("background-color", "#FFF");
				dugme.html('Subscribe <span class="badge">' + data.broj + '</span>');
				dugme.css("visibility", "visible");
			}
		}
	});
}


function refreshVideoLikes(videoId) {
	var linijaLike = $('#video-details-like-line');
	var linijaDislike = $('#video-details-dislike-line');
	var videoPoljeBrojLajkova = $('#video-details-broj-lajkova');
	var videoPoljeBrojDislajkova = $('#video-details-broj-dislajkova');
	$.get('LajkServlet?target=VIDEO&id=' + videoId, function (data) {
		if (data.status == 'success') {
			var lajkovi = data.lajkovi[0];
			var dislajkovi = data.lajkovi[1];
			var zbir = lajkovi + dislajkovi;
			if (zbir == 0) {
				linijaLike.css('width', '100%');
				linijaLike.css('background-color', 'gray');
				linijaDislike.css('width', '0%');
			}
			else{
				linijaLike.css('background-color', '#0CB71B');
			}
			linijaLike.css('width', ((1 / zbir) * lajkovi * 100) + '%');
			linijaDislike.css('width', ((1 / zbir) * dislajkovi * 100) + '%');

			videoPoljeBrojLajkova.empty();
			videoPoljeBrojDislajkova.empty();
			videoPoljeBrojLajkova.append(lajkovi);
			videoPoljeBrojDislajkova.append(dislajkovi);

			if (data.loginStatus != 'unauthenticated') {
				// 0 nije
				if (data.lajkovan == 1) { // lajkovan
					$('#video-dugme-like').css('color', '#0CB71B');
					$('#video-dugme-dislike').css('color', '#555');
				}
				else if (data.lajkovan == 2) { // dislajkovan
					$('#video-dugme-dislike').css('color', 'red');
					$('#video-dugme-like').css('color', '#555');
				}
				else {
					$('#video-dugme-like').css('color', '#555');
					$('#video-dugme-dislike').css('color', '#555');
				}
			}
		}
		else {
			console.log('Greska pri ucitavanju lajkova sa servera.');
		}
	});
}


function refreshCommentsBezPaginacije(videoId) {
	$.get('KomentarServlet?videoId=' + videoId, function (data) {
		var ulogovani = data.ulogovani;
		if (data.status == 'success') {
			var komentari = data.komentari;
			var poljeKomentara = $('#komentari-lista');
			poljeKomentara.empty();
			for (var i in komentari) {
				ispisiKomentar(komentari[i], poljeKomentara);
				if (i != komentari.length - 1) {
					poljeKomentara.append('<hr>');
				}
			}
		}
	});
}

function refreshComments(videoId, page, count, sortBy, sortDirection) {
	$.get('KomentarServlet?videoId=' + videoId + '&sortBy=' + sortBy + '&sortDirection=' + sortDirection, function (data) {
		var ulogovani = data.ulogovani;
		if (data.status == 'success') {
			var komentari = data.komentari;
			if (komentari.length == 0) {
				$('#komentari-lista').remove();
				$('#paginacija-nav').remove();
				$('#komentari-nema-komentara').remove();
				$('#komentari-container').append('<p id="komentari-nema-komentara">Nema komentara.</p>');
			}
			else {
				$('#komentari-lista').remove();
				$('#paginacija-nav').remove();
				$('#komentari-nema-komentara').remove();
				$('#komentari-container').append('<div id="komentari-lista"></div><nav id="paginacija-nav"><ul class="pagination" id="paginacija"></ul></nav>');
				var poljeKomentara = $('#komentari-lista');

				$('#sortiraj-btn').on('click', null, function(){
					refreshComments(videoId, page, count, $('#sort-filter-select').val(), $('#sort-by-filter input:radio:checked').val());
				});
				
				for (var i in komentari) {
					if (i >= page * count && i < (page + 1) * count) {
						ispisiKomentar(komentari[i], poljeKomentara, data.korisnik, data.korisnikTip);
						if (i != komentari.length - 1 && i != (page + 1) * count - 1) {
							poljeKomentara.append('<hr>');
						}
					}
				}
				$('#paginacija').empty();
				if (komentari.length > count) {
					$('#paginacija-nav').css('height', 'auto');
					for (var i = 0; i < komentari.length / count; i++) {
						dodajStranicuPaginacije(videoId, i, count, page);
					}
				}
				else {
					$('#paginacija-nav').css('height', '0px');
				}
			}
		}
	});
}

function dodajStranicuPaginacije(videoId, page, count, currentPage) {
	$('#paginacija').append('<li><a href="#" id="paginacija-komentar-' + page + '">' + (page + 1) + '</a></li>');
	$('#paginacija-komentar-' + page).on('click', function (event) {
		event.preventDefault();
		refreshComments(videoId, page, count, $('#sort-filter-select').val(), $('#sort-by-filter input:radio:checked').val());
	});
	if (page == currentPage) {
		$('#paginacija-komentar-' + page).css('background-color', '#337ab7');
		$('#paginacija-komentar-' + page).css('color', '#FFF');
	}
	else {
		$('#paginacija-komentar-' + page).css('background-color', '#FFF');
		$('#paginacija-komentar-' + page).css('color', '#337ab7');
	}
}

function ispisiKomentar(komentar, poljeKomentara, korisnik, korisnikTip, listaSlika) {
	console.log(komentar);

	poljeKomentara.append(
		'<div class="komentar" id="komentar-' + komentar["komentar"].id + '">' +
		'<img src="img/profile/' + ((komentar["slika"])?komentar["slika"]:'_.png') + '" alt="profile photo">' + 
		'<a href="ProfilServlet?user=' + komentar["komentar"].vlasnik + '">' +
		'<p class="komentar-ime">' + komentar["komentar"].vlasnik + '</p>' +
		'</a>' +
		'<p class="komentar-datum">' + $.format.date(komentar["komentar"].datum, "dd.MM.yyyy. HH:mm") + '</p>' +
		(
			(korisnik == komentar["komentar"].vlasnik || korisnikTip == 'ADMIN') ?
				'<a href="#" id="komentar-delete-' + komentar["komentar"].id + '" class="komentar-dugme-link komentar-edit-delete">' +
				'<span id="komentar-delete-dugme-' + komentar["komentar"].id + '" class="glyphicon glyphicon-remove"></span>' +
				'</a>' +
				'<a href="#" id="komentar-edit-' + komentar["komentar"].id + '" class="komentar-dugme-link komentar-edit-delete">' +
				'<span id="komentar-edit-dugme-' + komentar["komentar"].id + '" class="glyphicon glyphicon-pencil"></span>' +
				'</a>'
				:
				''
		) +
		'<div id="komentar-sadrzaj-wrap-' + komentar["komentar"].id + '" class="komentar-sadrzaj-wrap">' +
		'<p class="komentar-sadrzaj">' + komentar["komentar"].sadrzaj + '</p>' +
		'</div>' +
		'<a href="#" id="komentar-like-' + komentar["komentar"].id + '" class="komentar-dugme-link">' +
		'<span id="komentar-like-dugme-' + komentar["komentar"].id + '" class="glyphicon glyphicon-thumbs-up komentar-button-like"></span>' +
		'</a>' +
		'<p class="komentar-broj-like" id="komentar-broj-like-' + komentar["komentar"].id + '"></p>' +
		'<a href="#" id="komentar-dislike-' + komentar["komentar"].id + '" class="komentar-dugme-link">' +
		'<span id="komentar-dislike-dugme-' + komentar["komentar"].id + '" class="glyphicon glyphicon-thumbs-down komentar-button-dislike"></span>' +
		'</a>' +
		'<p class="komentar-broj-dislike" id="komentar-broj-dislike-' + komentar["komentar"].id + '"></p>' +
		'</div>');
	refreshCommentLikes(komentar["komentar"].id);

	$('#komentar-like-' + komentar["komentar"].id).on('click', null, function (event) {
		event.preventDefault();
		$.post('LajkServlet?target=KOMENTAR&id=' + komentar["komentar"].id + '&tip=like', function (data) {
			if (data.status == 'error') {
				console.log('greska pri lajkovanju');
			}
			else {
				refreshCommentLikes(komentar["komentar"].id);
			}
		});
	});
	$('#komentar-dislike-' + komentar["komentar"].id).on('click', null, function (event) {
		event.preventDefault();
		$.post('LajkServlet?target=KOMENTAR&id=' + komentar["komentar"].id + '&tip=dislike', function (data) {
			if (data.status == 'error') {
				console.log('greska pri lajkovanju');
			}
			else {
				refreshCommentLikes(komentar["komentar"].id);
			}
		});
	});
	$('#komentar-delete-' + komentar["komentar"].id).on('click', null, function (event) {
		event.preventDefault();
		$.post('KomentarServlet?komentarId=' + komentar["komentar"].id + '&obrisan=true', function (data) {
			if (data.status == 'success') {
				window.location.reload();
			}
		});
	});
	$('#komentar-edit-' + komentar["komentar"].id).on('click', null, function (event) {
		event.preventDefault();
		var komentarWrap = $('#komentar-sadrzaj-wrap-' + komentar["komentar"].id);
		komentarWrap.empty();
		komentarWrap.append('<textarea id="komentar-sadrzaj-edit-' + komentar["komentar"].id + '" class="form-control" rows="4">' + komentar["komentar"].sadrzaj + '</textarea>');
		validacija_input($('#komentar-sadrzaj-edit-' + komentar["komentar"].id), 1, 1024);
		komentarWrap.append('<button class="btn btn-default komentar-save-button" type="button" id="komentar-edit-save-' + komentar["komentar"].id + '">Save</button>');
		komentarWrap.css("margin-bottom", "20px");
		$('#komentar-edit-save-' + komentar["komentar"].id).on('click', null, function () {
			var sadrzaj = $('#komentar-sadrzaj-edit-' + komentar["komentar"].id).val();
			$.post('KomentarServlet', { 'komentarId': komentar["komentar"].id, 'sadrzaj': sadrzaj }, function (data) {
				if (data.status == 'success') {
					window.location.reload();
				}
			});
		});
	});
}


function refreshCommentLikes(komentarId) {
	$.get('LajkServlet?target=KOMENTAR&id=' + komentarId, function (data) {
		if (data.status == 'success') {
			if (data.loginStatus != 'unauthenticated') {
				// 0 nije
				if (data.lajkovan == 1) { // lajkovan
					$('#komentar-like-dugme-' + komentarId).css('color', '#0CB71B');
					$('#komentar-dislike-dugme-' + komentarId).css('color', '#555');
				}
				else if (data.lajkovan == 2) { // dislajkovan
					$('#komentar-dislike-dugme-' + komentarId).css('color', 'red');
					$('#komentar-like-dugme-' + komentarId).css('color', '#555');
				}
				else {
					$('#komentar-like-dugme-' + komentarId).css('color', '#555');
					$('#komentar-dislike-dugme-' + komentarId).css('color', '#555');
				}
			}
			$('#komentar-broj-like-' + komentarId).text(data.lajkovi[0]);
			$('#komentar-broj-dislike-' + komentarId).text(data.lajkovi[1]);
		}
	});
}