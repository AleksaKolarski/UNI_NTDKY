<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	  <title>Video: ${requestScope.naziv}</title>
	
	  <!-- Bootstrap and jQuery -->
  	<link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
  	<script src="lib/jquery/jquery-3.2.1.js"></script>
    <script src="lib/jquery/jquery-dateFormat.min.js"></script>
  	<script src="lib/bootstrap/js/bootstrap.js"></script>

  	<!-- Custom files -->
  	<link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,600,700&amp;subset=latin-ext" rel="stylesheet">
  	<link rel="stylesheet" type="text/css" href="css/style.css">
  	<script src="js/misc.js"></script>
  	<script src="js/header.js"></script>
  	<script src="js/sidebar.js"></script>
    <script src="js/video.js"></script>
</head>
<body>
<div class="container-fluid osnova">

    <!-- NAVIGACIJA GORE -->
    <%@include file="include/navigacija_gore.html"%>

    <div class="row page">

      <!-- NAVIGACIJA LEVO -->
      <%@include file="include/navigacija_levo.html"%>

      <!-- MAIN CONTENT -->
      <div class="col-xs-12 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 col-lg-10 col-lg-offset-2 main">
	  	  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-10 col-lg-offset-1 video">

          <c:choose>
          <c:when test="${requestScope.video.blokiran == true && requestScope.ulogovaniKorisnik.tipKorisnika != 'ADMIN' && requestScope.ulogovaniKorisnik.korisnickoIme != requestScope.video.vlasnik}">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="video-blokiran-obavestenje">
              <p class="text-center">Ovaj video ili njegov vlasnik je blokiran!</p>
            </div>
          </c:when>

          <c:otherwise>
          <c:if test="${requestScope.video.blokiran == true}">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="video-blokiran-obavestenje">
              <p class="text-center">Ovaj video ili njegov vlasnik je blokiran!</p>
            </div>
          </c:if>
		      <h3>${requestScope.video.naziv}</h3>
		      <div class="embed-responsive embed-responsive-16by9">
		  	    <iframe src="https://www.youtube.com/embed/${requestScope.video.putanjaVidea}?rel=0&amp;showinfo=0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
		      </div>
		  
		      <div class="panel panel-default video-details">
            <div class="panel-heading" id="video-details-heading">
              <table id="video-details-table">
                <tr id="video-details-row0">
                  <td id="video-details-r0-c0">
                    <c:choose>
                      <c:when test="${not empty requestScope.vlasnik.slika}">
                        <img src="img/profile/${requestScope.vlasnik.slika}" id="profile-image-video" alt="profile photo">
                      </c:when>
                      <c:otherwise>
                        <img src="img/profile/_.png" id="profile-image-video" alt="profile photo">
                      </c:otherwise>
                    </c:choose>
                    <a href="ProfilServlet?user=${requestScope.video.vlasnik}">${requestScope.video.vlasnik}</a>
                  </td>
                  <td id="video-details-r0-c1">
                    
                  </td>
                  <td id="video-details-r0-c2">
                    ${requestScope.video.brojPregleda} pregleda
                  </td>
                </tr>
                <tr id="video-details-row1">
                  <td rowspan="2" id="video-details-r1-c0">
                    <c:if test="${(requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.video.vlasnik && requestScope.video.blokiran == false) || (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN')}">
                      <button type="button" class="btn btn-default" id="btn-video-edit" onclick="window.location.href='/NTDKY/VideoNewServlet?videoId=${requestScope.video.id}&edit=true';">Edit video</button>
                    </c:if>
                    <c:if test="${requestScope.ulogovaniKorisnik.korisnickoIme != requestScope.video.vlasnik}">
                      <button type="button" class="btn btn-default" id="btn-subscribe"></button>
                    </c:if>
                  </td>
                  <td rowspan="2" id="video-details-r1-c1">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" id="video-details-toggle">
                      <span class="glyphicon glyphicon-menu-down"></span>
                    </a>
                  </td>
                  <td id="video-details-r1-c2">
                    <c:if test="${requestScope.video.vidljivostRejting == true || requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.video.vlasnik || requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'}">
                      <div id="video-details-line-container">
                        <hr id="video-details-like-line">
                        <hr id="video-details-dislike-line">
                      </div>
                    </c:if>
                  </td>
                </tr>
                
                <tr id="video-details-row2">
                  <td id="video-details-r2-c2">
                    <c:if test="${requestScope.video.vidljivostRejting == true || requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.video.vlasnik || requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'}">
                      <a href="#" id="video-dugme-like-link">
                        <span class="glyphicon glyphicon-thumbs-up" id="video-dugme-like"></span>
                      </a>
                      <p id="video-details-broj-lajkova"></p>
                      <a href="#" id="video-dugme-dislike-link">
                        <span class="glyphicon glyphicon-thumbs-down" id="video-dugme-dislike"></span>
                      </a>
                      <p id="video-details-broj-dislajkova"></p>
                    </c:if>
                  </td>
                </tr>
              </table>
            </div>

            <hr>
    	  	  <div id="collapseOne" class="panel-collapse collapse">
      	  	  <div class="panel-body">
        			  <dl class="dl-horizontal">
		  				    <dt>Opis:</dt>
		  				    <dd>${requestScope.video.opis}</dd>
                  <dt>Datum:</dt>
                  <dd><fmt:formatDate value="${requestScope.video.datum}" pattern="dd.MM.yyyy. HH:mm" /></dd>
		  			    </dl>
      	  	  </div>
    	  	  </div>
          </div>

          <!-- Komentari -->
          <c:if test="${requestScope.video.vidljivostKomentari == true || requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.video.vlasnik || requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'}">
          <div id="komentari-container" class="col-sm-12 col-md-9 col-lg-8">
            <%-- unos novog komentara --%>
            <c:if test="${not empty requestScope.ulogovaniKorisnik}">
            <c:if test="${(requestScope.ulogovaniKorisnik.blokiran == false && requestScope.video.blokiran == false) || requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'}">
              <p id="komentar-novi-ime">${requestScope.ulogovaniKorisnik.korisnickoIme}:</p>
              <div class="input-group" id="komentar-novi-container">
                <input type="text" class="form-control" placeholder="Komentar..." id="komentar-novi-text">
                <span class="input-group-btn">
                  <button class="btn btn-default" type="button" id="komentar-novi-button">
                    <span class="glyphicon glyphicon-send"></span>
                  </button>
                </span>
              </div>
              <hr>
            </c:if>
            </c:if>

            <div id="komentar-sort-wrap">
              <div id="sort-filter">
						    <p>Sortiraj po:</p>
						    <select class="form-control" id="sort-filter-select">
							    <option value="datum">Datumu</option>
  							  <option value="rejting">Rejtingu</option>
						    </select>
					    </div>
					    <div id="sort-by-filter">
						    <p>Redosled:</p>
						    <div class="radio-inline">
  						    <label>
    						    <input type="radio" name="optionsRadios" id="optionsRadios1" value="DESC" checked>
    						    <span class="glyphicon glyphicon-sort-by-attributes-alt"></span>
  						    </label>
						    </div>
						    <div class="radio-inline">
  						    <label>
    						    <input type="radio" name="optionsRadios" id="optionsRadios2" value="ASC">
    						    <span class="glyphicon glyphicon-sort-by-attributes"></span>
 							    </label>
						    </div>
					     </div>
              <button type="button" class="btn btn-default" id="sortiraj-btn">Sortiraj</button>
              <hr>
            </div>

            <%-- lista komentara --%>
            <div id="komentari-lista">

            </div>


            <nav id="paginacija-nav">
              <ul class="pagination" id="paginacija">
              </ul>
            </nav>
          </div>
          </c:if>
          </c:otherwise>
          </c:choose>
  		  </div>
  	  </div>
    </div>

    <!-- FOOTER -->
    <%@include file="include/navigacija_dole.html"%>
  </div>

</body>
</html>