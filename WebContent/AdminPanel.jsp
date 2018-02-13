<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Korisnici</title>
	
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
    <script src="js/adminPanel.js"></script>
</head>
<body>
<div class="container-fluid osnova">

    <!-- NAVIGACIJA GORE -->
    <%@include file="include/navigacija_gore.html"%>

    <div class="row page">

      <!-- PRETRAGA LEVO -->
      <div id="pretraga-korisnika-levo" class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
				<p>Napredni filter</p>	
				<form action="AdminPanelServlet" method="GET">
    		<div id="filter-korisnicko-ime">
					<p>Korisnicko ime:</p>
					<input type="text" name="korisnickoIme" class="form-control" id="naziv-filter-polje" placeholder="Korisnicko ime" value="${requestScope.filterKorisnickoIme}"/>
				</div>
				<div id="filter-ime">
					<p>Ime:</p>
					<input type="text" name="ime" class="form-control" id="vlasnik-filter-polje" placeholder="Ime" value="${requestScope.filterIme}"/>
				</div>
				<div id="filter-prezime">
					<p>Prezime:</p>
					<input type="text"  name="prezime" class="form-control" id="vlasnik-filter-polje" placeholder="Prezime" value="${requestScope.filterPrezime}"/>
				</div>
				<div id="filter-email">
					<p>Email:</p>
					<input type="text" name="email" class="form-control" id="vlasnik-filter-polje" placeholder="Email" value="${requestScope.filterEmail}"/>
				</div>
				<div id="filter-uloga">
					<p>Uloga:</p>
					<select name="tipKorisnika" class="form-control" id="sort-filter-select">
						<option value="ANY" ${requestScope.filterTipKorisnika == 'ANY'?'selected':''}>Bilo koja</option>
  					<option value="USER" ${requestScope.filterTipKorisnika == 'USER'?'selected':''}>Obican korisnik</option>
  					<option value="ADMIN" ${requestScope.filterTipKorisnika == 'ADMIN'?'selected':''}>Administrator</option>
					</select>
				</div>


				<div id="sort-wrapper">
					<div id="sort-filter">
						<p>Sortiraj po:</p>
						<select name="sortBy" class="form-control" id="sort-filter-select">
							<option value="korisnickoIme" ${requestScope.filterSortBy == 'korisnickoIme'?'selected':''}>Korisnickom imenu</option>
  						<option value="ime" ${requestScope.filterSortBy == 'ime'?'selected':''}>Imenu</option>
  						<option value="prezime" ${requestScope.filterSortBy == 'prezime'?'selected':''}>Prezimenu</option>
  						<option value="email" ${requestScope.filterSortBy == 'email'?'selected':''}>Emailu</option>
							<option value="tipKorisnika" ${requestScope.filterSortBy == 'tipKorisnika'?'selected':''}>Ulozi</option>
						</select>
					</div>

					<div id="sort-by-filter">
						<p>Redosled:</p>
						<div class="radio-inline">
  						<label>
    						<input type="radio" name="sortDirection" id="optionsRadios1" value="DESC" ${requestScope.filterSortDirection == 'DESC'?'checked':''}>
    						<span class="glyphicon glyphicon-sort-by-attributes-alt"></span>
  						</label>
						</div>
						<div class="radio-inline">
  						<label>
    						<input type="radio" name="sortDirection" id="optionsRadios2" value="ASC" ${requestScope.filterSortDirection == 'ASC'?'checked':''}>
    						<span class="glyphicon glyphicon-sort-by-attributes"></span>
 							</label>
						</div>
					</div>
				</div>

				<div id="pretraga-btn-wrapper">
					<button type="submit" class="btn btn-default" id="pretraga-btn">Pretrazi</button>
				</div>
				</form>
  		</div>

      <!-- MAIN CONTENT -->
      <div class="table-responsive col-xs-12 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 col-lg-10 col-lg-offset-2 main">
        <table class="table col-xs-12 col-sm-12 col-md-12 col-lg-12">
          <caption> Tabela </caption>
          <thead>
            <tr>
							<th>Korisnicko ime</th>
							<th>Ime</th>
							<th>Prezime</th>
							<th>Email</th>
							<th>Uloga</th>
            </tr>
          </thead>
					<tbody id="admin-panel-tbody">
						<c:forEach items="${requestScope.korisnici}" var="korisnik">
							<c:choose>
								<c:when test="${korisnik.blokiran == true}">
									<tr class="danger">
								</c:when>
								<c:otherwise>
									<tr>
								</c:otherwise>
							</c:choose>
								<th><a href="ProfilServlet?user=${korisnik.korisnickoIme}">${korisnik.korisnickoIme}</a></th>
								<th>${korisnik.ime}</th>
								<th>${korisnik.prezime}</th>
								<th>${korisnik.email}</th>
								<th>${korisnik.tipKorisnika}</th>
            	</tr>
						</c:forEach>
					</tbody>
        </table>
      </div>
    </div>

    <!-- FOOTER -->
    <%@include file="include/navigacija_dole.html"%>
  </div>
</body>
</html>