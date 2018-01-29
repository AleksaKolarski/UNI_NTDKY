<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Profil: ${requestScope.profileUsername}</title>
	<!-- Bootstrap and jQuery -->
  	<link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
  	<script src="lib/jquery/jquery-3.2.1.js"></script>
  	<script src="lib/bootstrap/js/bootstrap.js"></script>

  	<!-- Custom files -->
  	<link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,700&amp;subset=latin-ext" rel="stylesheet">
  	<link rel="stylesheet" type="text/css" href="css/style.css">
  	<script src="js/misc.js"></script>
  	<script src="js/header.js"></script>
  	<script src="js/sidebar.js"></script>
</head>
<body>
<div class="container-fluid osnova">

    <!-- NAVIGACIJA GORE -->
    <div class="row">
      <!-- <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2 navigacija-gore"> -->
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 navigacija-gore">
        <nav>
          <ul>
            <li class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
              <div class="navigacija-logo">
                <a href="#">NTDKY</a>
              </div>
            </li>
            <li class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
              <div class="navigacija-search input-group">
                <input type="text" class="form-control" placeholder="Pretraga...">
                <span class="input-group-btn">
                  <button class="btn btn-default" type="button">
                    <span class="glyphicon glyphicon-search"></span>
                  </button>
                </span>
              </div>
            </li>
            <li class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
              <div class="navigacija-user" id="navigacija-gore-polje">
              </div>
            </li>
          </ul>
        </nav>
      </div>
    </div>


    <div class="row page">

      <!-- NAVIGACIJA LEVO -->
      <div class="col-sm-3 col-md-2 col-lg-2 navigacija-levo">
        <ul>
          <!-- Menu items -->
          <a href="#">
            <li class="sidebar-menu-item">
              Pocetna
            </li>
          </a>
        </ul>
      </div>

      <!-- MAIN CONTENT -->
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 col-lg-10 col-lg-offset-2 main">
	  	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 profile-details">
		  <h4>Korisnicko ime: ${requestScope.profileUsername}</h4>
		  <h4>Ime: ${requestScope.profileName}</h4>
		  <h4>Prezime: ${requestScope.profileLastName}</h4>
		  <h4>Datum registracije: ${requestScope.profileDate}</h4>
		  <h4>Uloga: ${requestScope.profileUloga}</h4>
		  <h4>Broj pratilaca: ${requestScope.profileBrojPratilaca}</h4>
		  <c:choose>
			<c:when test="${requestScope.ulogovani == requestScope.profileUsername}">
				<button type="button" class="btn btn-default btn-edit-profile">Edit profile</button>
			</c:when>
			<c:otherwise>
				<button type="button" class="btn btn-default btn-subscribe-profile">Subscribe</button>
			</c:otherwise>
		  </c:choose>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-1.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-2.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-3.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-4.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-5.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-6.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-7.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-8.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-1.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-2.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-3.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-4.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3 pnl">
          <a href="#">
            <div class="panel panel-default">
              <div class="panel-body">
                <img src="img/video-thumbnail-5.jpg">
              </div>
              <div class="panel-footer">Panel footer</div>
            </div>
          </a>
        </div>
      </div>
    </div>

    <!-- FOOTER -->
    <div class="row">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 navigacija-dole">
        <footer>
          <ul>
            <li class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
              <div class="">
                <a href="#">NTDKY</a>
                <p>Ovo je neki tekst</p>
                <p>Ovo je neki tekst</p>
              </div>
            </li>
            <li class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
              <div class="">
                <a href="#">NTDKY</a>
                <p>Ovo je neki tekst</p>
                <p>Ovo je neki tekst</p>
              </div>
            </li>
            <li class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
              <div class="">
                <a href="#">NTDKY</a>
                <p>Ovo je neki tekst</p>
                <p>Ovo je neki tekst</p>
              </div>
            </li>
          </ul>
        </footer>
      </div>
    </div>
  </div>

</body>
</html>