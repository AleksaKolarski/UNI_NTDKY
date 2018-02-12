<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Profil: ${requestScope.profil.korisnickoIme}</title>
	
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
    <script src="js/profil.js"></script>
</head>
<body>

<div class="container-fluid osnova">
  <div class="modal fade" id="modal-obrisi" tabindex="-1">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span >&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">Brisanje korisnika</h4>
        </div>
        <div class="modal-body">
          Da li ste sigurni da zelite da obrisete ovog korisnika?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Odustani</button>
          <button type="button" id="btn-profile-delete-modal" class="btn btn-primary" data-dismiss="modal">Potvrdi</button>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="modal-sacuvaj" tabindex="-1">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span >&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">Izmena korisnika</h4>
        </div>
        <div class="modal-body">
          Da li ste sigurni da zelite da sacuvate izmene korisnika?
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Odustani</button>
          <button type="button" id="btn-profile-save-modal" class="btn btn-primary" data-dismiss="modal">Potvrdi</button>
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
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 col-lg-10 col-lg-offset-2 main">
        <c:if test="${not empty requestScope.profil}">
        <c:if test="${requestScope.profil.blokiran == true}">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="profil-blokiran-obavestenje">
            <p class="text-center">Ovaj korisnik je blokiran!</p>
          </div>
        </c:if>
        <c:if test="${requestScope.profil.blokiran == false || requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN' || requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.profil.korisnickoIme}">
  	      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 profile-details">
            <c:choose>
              <c:when test="${not empty requestScope.profil.slika}">
                <img src="img/profile/${requestScope.profil.slika}" id="profile-image" alt="profile photo">
              </c:when>
              <c:otherwise>
                <img src="img/profile/_.png" id="profile-image" alt="profile photo">
              </c:otherwise>
            </c:choose>

            <%-- <form method="POST" enctype="multipart/form-data" action="fup.cgi"> --%>
            <%-- <form action="ImageServlet" method="GET" id="profile-image-form" enctype="multipart/form-data" method="POST"> --%>
              <c:if test="${(requestScope.edit == true) && ((requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.profil.korisnickoIme && requestScope.profil.blokiran == false) || (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'))}">
                <input type="file" id="profile-image-chooser" accept="image/*" name="${requestScope.profil.korisnickoIme}" onchange="promenjenaSlika(event)">
              </c:if>
            <%-- </form> --%>

              <div class="profile-input-div">
                <p class="profile-p profile-p-title">Korisnicko ime: </p>
                <p class="profile-p">${requestScope.profil.korisnickoIme}</p>
              </div>

              <div class="profile-input-div">
                <p class="profile-p profile-p-title">Ime: </p>
                <c:choose>
                  <c:when test="${(requestScope.edit == true) && ((requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.profil.korisnickoIme && requestScope.profil.blokiran == false) || (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'))}">
                    <input type="text" id="profile-input-ime" class="form-control" value="${requestScope.profil.ime}">
                  </c:when>
                  <c:otherwise>
                    <p class="profile-p">${requestScope.profil.ime}</p>
                  </c:otherwise>
                </c:choose>
              </div>

              <div class="profile-input-div">
                <p class="profile-p profile-p-title">Prezime: </p>
                <c:choose>
                  <c:when test="${(requestScope.edit == true) && ((requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.profil.korisnickoIme && requestScope.profil.blokiran == false) || (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'))}">
                    <input type="text" id="profile-input-prezime" class="form-control" value="${requestScope.profil.prezime}">
                  </c:when>
                  <c:otherwise>
                    <p class="profile-p">${requestScope.profil.prezime}</p>
                  </c:otherwise>
                </c:choose>
              </div>

              <div class="profile-input-div">
                <p class="profile-p profile-p-title">Opis: </p>
                <c:choose>
                  <c:when test="${(requestScope.edit == true) && ((requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.profil.korisnickoIme && requestScope.profil.blokiran == false) || (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN'))}">
                    <textarea id="profile-input-opis" class="form-control" rows="4">${requestScope.profil.opis}</textarea>
                  </c:when>
                  <c:otherwise>
                    <p class="profile-p">${requestScope.profil.opis}</p>
                  </c:otherwise>
                </c:choose>
              </div>
            
              <div class="profile-input-div">
                <p class="profile-p profile-p-title">Uloga: </p>
                <c:choose>
                  <c:when test="${(requestScope.edit == true) && (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN')}">
                    <select class="form-control" id="profile-user-type">
							        <option value="USER" label="Obican korisnik" ${requestScope.profil.tipKorisnika == 'USER'?'selected':''}>Obican korisnik</option> <%-- Mozilla Firefox ima bug zbog kog ne prikazuje labele --%>
  						        <option value="ADMIN" label="Administrator" ${requestScope.profil.tipKorisnika == 'ADMIN'?'selected':''}>Administrator</option>
						        </select>
                  </c:when>
                  <c:otherwise>
                    <c:if test="${requestScope.profil.tipKorisnika == 'USER'}">
                      <p class="profile-p">Obican korisnik</p>
                    </c:if>
                    <c:if test="${requestScope.profil.tipKorisnika == 'ADMIN'}">
                      <p class="profile-p">Administrator</p>
                    </c:if>
                  </c:otherwise>
                </c:choose>
              </div>

              <div class="profile-input-div">
                <p class="profile-p profile-p-title">Datum registracije: </p>
                <p class="profile-p"><fmt:formatDate value="${requestScope.profil.datum}" pattern="dd.MM.yyyy. HH:mm" /></p>
              </div>

              <c:if test="${(requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.profil.korisnickoIme) || (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN')}">
                <div class="profile-input-div">
                  <p class="profile-p profile-p-title">Email: </p>
                  <p class="profile-p">${requestScope.profil.email}</p>
                </div>
              </c:if>

              <div class="profile-input-div">
                <p class="profile-p profile-p-title">Broj pratilaca: </p>
                <p class="profile-p">${requestScope.profil.brojPratioca}</p>
              </div>
          
              <c:if test="${not empty requestScope.ulogovaniKorisnik}">
                <c:if test="${(requestScope.ulogovaniKorisnik.korisnickoIme == requestScope.profil.korisnickoIme && requestScope.profil.blokiran == false) || (requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN')}">
                  <c:if test="${requestScope.edit != true}">
                    <button type="button" class="btn btn-default" id="btn-profile-edit">Edit profile</button>
                  </c:if>
                  <c:if test="${requestScope.edit == true}">
                    <div class="profile-input-div">
                      <p class="profile-p profile-p-title">Nova lozinka: </p>
                      <input type="password" class="form-control" id="profile-lozinka1"/>
                    </div>
                    <div class="profile-input-div">
                      <p class="profile-p profile-p-title">Ponovite lozinku: </p>
                      <input type="password" class="form-control" id="profile-lozinka2"/>
                    </div>
                  
                    <c:if test="${(requestScope.ulogovaniKorisnik.tipKorisnika == 'ADMIN')}">
                      <div class="profile-input-div">
                        <p class="profile-p profile-p-title">Blokiranje: </p>
                        <select class="form-control" id="profile-user-block">
							            <option value="FALSE" label="Nije blokiran" ${requestScope.profil.blokiran == false?'selected':''}>Nije blokiran</option>  <%-- Mozilla Firefox ima bug zbog kog ne prikazuje labele --%>
  						            <option value="TRUE" label="Blokiran" ${requestScope.profil.blokiran == true?'selected':''}>Blokiran</option>
						            </select>
                      </div>
                    </c:if>
                    <div id="button-div">
                    <button type="button" class="btn btn-default" id="btn-profile-save" data-toggle="modal" data-target="#modal-sacuvaj">Save</button>
                    <button type="button" class="btn btn-default" id="btn-profile-cancel">Cancel</button>
                    <p id="profile-log"></p>
                    </div>
                    <button type="button" class="btn btn-default" id="btn-profile-delete" data-toggle="modal" data-target="#modal-obrisi">Obrisi nalog</button>
                </c:if>
              </c:if>
              <c:if test="${requestScope.ulogovaniKorisnik.korisnickoIme != requestScope.profil.korisnickoIme && requestScope.ulogovaniKorisnik.blokiran == false}">
                <button type="button" class="btn btn-default" id="btn-subscribe"></button>
              </c:if>
            </c:if>
          </div>

          <%-- videi --%>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="profil-video-sort-wrapper">
					  <div id="sort-filter">
						  <p>Sortiraj po:</p>
						  <select class="form-control" id="sort-filter-select">
							  <option value="datum">Datumu</option>
  							<option value="brojPregleda">Broju pregleda</option>
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
				  </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="profile-videos">

          </div>
          
          <%-- pretplate --%>
          <c:if test="${not empty requestScope.pretplate}">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="profil-pretplate">
              <p>Pretplate:</p>
              <c:forEach items="${requestScope.pretplate}" var="pretplata">
                <div class="col-xs-6 col-sm-6 col-md-4 col-lg-2">
                  <div class="profil-pretplata">
                    <p><a href="ProfilServlet?user=${pretplata.key}">${pretplata.key}</a></p>
                    <span class="badge">${pretplata.value}</span>
                  </div>
                </div>
              </c:forEach>
            </div>
          </c:if>
        </c:if>
        </c:if>
      </div>
    </div>

    <!-- FOOTER -->
    <%@include file="include/navigacija_dole.html"%>
  </div>
</body>
</html>