<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Video: ${requestScope.naziv}</title>
	
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
    <script src="js/video.js"></script>
</head>
<body>
<div class="container-fluid osnova">

    <!-- NAVIGACIJA GORE -->
    <div class="row">
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
	  	  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-10 col-lg-offset-1 video">
		      <h3>${requestScope.video.naziv}</h3>
		      <div class="embed-responsive embed-responsive-16by9">
		  	    <iframe src="https://www.youtube.com/embed/${requestScope.video.putanjaVidea}?rel=0&amp;showinfo=0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
		      </div>
		      <!--
		      <c:choose>
			    <c:when test="${requestScope.ulogovani == requestScope.profileUsername}">
				    <button type="button" class="btn btn-default btn-edit-profile">Edit profile</button>
			    </c:when>
			    <c:otherwise>
				    <button type="button" class="btn btn-default btn-subscribe-profile">Subscribe</button>
			    </c:otherwise>
		      </c:choose>
		      -->
		  
		      <div class="panel panel-default video-details">
            <div class="panel-heading" role="tab" id="video-details-heading">
              <table id="video-details-table">
                <tr id="video-details-row0">
                  <td id="video-details-r0-c0">
                    ${requestScope.video.vlasnik}
                  </td>
                  <td id="video-details-r0-c1">
                    
                  </td>
                  <td id="video-details-r0-c2">
                    ${requestScope.video.brojPregleda} pregleda
                  </td>
                </tr>
                <tr id="video-details-row1">
                  <td id="video-details-r1-c0">
                    <button type="button" class="btn btn-default btn-subscribe">Subscribe</button>
                  </td>
                  <td id="video-details-r1-c1">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" id="video-details-toggle">
                      
                      <span class="glyphicon glyphicon-menu-down"></span>
                    </a>
                  </td>
                  <td id="video-details-r1-c2">
                    ${requestScope.video.brojLajkova} lajkova
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
		  				    <dd>${requestScope.video.datum}</dd>
		  			    </dl>
      	  	  </div>
    	  	  </div>
          </div>

          <!-- Komentari -->
          

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