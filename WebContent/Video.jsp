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
    <%@include file="include/navigacija_gore.html"%>

    <div class="row page">

      <!-- NAVIGACIJA LEVO -->
      <%@include file="include/navigacija_levo.html"%>

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
                  <td rowspan="2" id="video-details-r1-c0">
                    <button type="button" class="btn btn-default btn-subscribe">Subscribe</button>
                  </td>
                  <td rowspan="2" id="video-details-r1-c1">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" id="video-details-toggle">
                      <span class="glyphicon glyphicon-menu-down"></span>
                    </a>
                  </td>
                  <td id="video-details-r1-c2">
                    <div id="video-details-line-container">
                      <hr id="video-details-like-line">
                      <hr id="video-details-dislike-line">
                    </div>
                  </td>
                </tr>
                
                <tr id="video-details-row2">
                  <td id="video-details-r2-c2">
                    <a href="#" id="video-dugme-like-link">
                      <span class="glyphicon glyphicon-thumbs-up" id="video-dugme-like"></span>
                    </a>
                    <p id="video-details-broj-lajkova"></p>
                    <a href="#" id="video-dugme-dislike-link">
                      <span class="glyphicon glyphicon-thumbs-down" id="video-dugme-dislike"></span>
                    </a>
                    <p id="video-details-broj-dislajkova"></p>
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
    <%@include file="include/navigacija_dole.html"%>
  </div>

</body>
</html>