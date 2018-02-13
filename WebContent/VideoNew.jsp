<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	  <title>Video</title>
	
	<!-- Bootstrap and jQuery -->
  	<link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
  	<script src="lib/jquery/jquery-3.2.1.js"></script>
    <script src="lib/jquery/jquery-dateFormat.min.js"></script>
    <script src"lib/jquery-ui-1.12.1/jquery-ui.min.js"></script>
  	<script src="lib/bootstrap/js/bootstrap.js"></script>

  	<!-- Custom files -->
  	<link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,600,700&amp;subset=latin-ext" rel="stylesheet">
  	<link rel="stylesheet" type="text/css" href="css/style.css">
  	<script src="js/misc.js"></script>
  	<script src="js/header.js"></script>
  	<script src="js/sidebar.js"></script>
    <script src="js/videoNew.js"></script>
</head>
<body>
  <div class="container-fluid osnova">

	<div class="modal fade" id="modal-obrisi" tabindex="-1">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span >&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">Brisanje videa</h4>
        </div>
        <div class="modal-body">
          Da li ste sigurni da zelite da obrisete ovaj video?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Odustani</button>
          <button type="button" id="btn-video-delete-modal" class="btn btn-primary" data-dismiss="modal">Potvrdi</button>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="modal-sacuvaj" tabindex="-1">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span >&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">Izmena videa</h4>
        </div>
        <div class="modal-body">
          Da li ste sigurni da zelite da sacuvate izmene videa?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Odustani</button>
          <button type="button" id="btn-video-save-modal" class="btn btn-primary" data-dismiss="modal">Potvrdi</button>
        </div>
      </div>
    </div>
  </div>

    <!-- NAVIGACIJA GORE -->
    <%@include file="include/navigacija_gore.html"%>

    <div class="row page">

      <!-- NAVIGACIJA LEVO -->
      <%@include file="include/navigacija_levo.html"%>

      <!-- MAIN CONTENT -->
      <div class="col-xs-12 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 col-lg-10 col-lg-offset-2 main">
				<div id="video-new" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

					<%-- SLIKA --%>
          <c:if test="${requestScope.edit == true}">
        	<div id="video-new-image-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<c:choose>
              <c:when test="${not empty requestScope.video.putanjaSlike}">
                <img src="img/video/icon/${requestScope.video.putanjaSlike}" id="video-new-image" alt="video image"  onerror="nemaSlike(event)">
              </c:when>
              <c:otherwise>
                <img src="img/video/icon/_.png" id="video-new-image" alt="video image">
              </c:otherwise>
            </c:choose>

            <input type="file" id="video-new-image-chooser" accept="image/*" name="${requestScope.video.id}" onchange="promenjenaSlika(event)">
					</div>
          </c:if>

					<div id="video-new-naziv-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<p class="profile-p profile-p-title">Naziv: </p>
            <input type="text" id="video-new-naziv" class="form-control" value="${requestScope.video.naziv}">
					</div>

					<div id="video-new-putanja-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<p class="profile-p profile-p-title">Putanja ka videu: </p>
            <input type="text" id="video-new-putanja" class="form-control" value="${requestScope.video.putanjaVidea}">
					</div>

					<div id="video-new-opis-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<p class="profile-p profile-p-title">Opis: </p>
            <input type="text" id="video-new-opis" class="form-control" value="${requestScope.video.opis}">
					</div>

					<c:if test="${requestScope.edit == true && requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'}">
						<div id="video-new-blokiran-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
							<p class="profile-p profile-p-title">Blokiran: </p>
							<input type="checkbox" id="video-new-blokiran" ${requestScope.video.blokiran == true?'checked':''}>
						</div>
					</c:if>

					<div id="video-new-vidljivost-videa-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<p class="profile-p profile-p-title">Vidljivost videa: </p>
            <select class="form-control" id="video-new-vidljivost-videa">
							<option value="PUBLIC" label="Javan" ${requestScope.video.vidljivostVidea == 'PUBLIC'?'selected':''}>Javan</option> <%-- Mozilla Firefox ima bug zbog kog ne prikazuje labele --%>
  						<option value="UNLISTED" label="Neizlistan" ${requestScope.video.vidljivostVidea == 'UNLISTED'?'selected':''}>Neizlistan</option>
							<option value="PRIVATE" label="Privatan" ${requestScope.video.vidljivostVidea == 'PRIVATE'?'selected':''}>Privatan</option>
						</select>
					</div>

					<div id="video-new-vidljivost-komentara-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<p class="profile-p profile-p-title">Vidljivost komentara: </p>
						<input type="checkbox" id="video-new-vidljivost-komentara" ${requestScope.video.vidljivostKomentari == true?'checked':''}>
					</div>

					<div id="video-new-vidljivost-rejtinga-wrap" class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<p class="profile-p profile-p-title">Vidljivost rejtinga: </p>
						<input type="checkbox" id="video-new-vidljivost-rejtinga" ${requestScope.video.vidljivostRejting == true?'checked':''}>
					</div>

					<c:if test="${requestScope.edit == true}">
						<button type="button" class="btn btn-default" id="video-new-btn-delete" data-toggle="modal" data-target="#modal-obrisi">Obrisi video</button>
					</c:if>

					<button type="button" class="btn btn-default" id="video-new-btn-save" data-toggle="modal" data-target="#modal-sacuvaj">Save</button>

					<p id="video-new-log"></p>
				</div>
      </div>
    </div>

    <!-- FOOTER -->
    <%@include file="include/navigacija_dole.html"%>
  </div>
</body>
</html>