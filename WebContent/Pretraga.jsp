<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Pretraga</title>
	
	<!-- Bootstrap and jQuery -->
  <link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
  <script src="lib/jquery/jquery-3.2.1.js"></script>
  <script src="lib/jquery/jquery-dateFormat.min.js"></script>
  <script src="lib/bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="lib/nouislider/nouislider.min.css">
	<script src="lib/nouislider/nouislider.min.js"></script>
	<link rel="stylesheet" type="text/css" href="lib/date-picker/css/bootstrap-datepicker.min.css">
	<script src="lib/date-picker/js/bootstrap-datepicker.min.js"></script>

  <!-- Custom files -->
  <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,600,700&amp;subset=latin-ext" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <script src="js/misc.js"></script>
  <script src="js/header.js"></script>
	<script src="js/pretraga.js"></script>
</head>
<body>
	<div class="container-fluid osnova">

    <!-- NAVIGACIJA GORE -->
    <%@include file="include/navigacija_gore.html"%>

    <div class="row page">

      <!-- PRETRAGA LEVO -->
      <div id="pretraga-levo" class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
				<p>Napredni filter</p>	
    		<div id="naziv-filter">
					<p>Naziv:</p>
					<input type="text" class="form-control" id="naziv-filter-polje" placeholder="Naziv"/>
				</div>
				<div id="vlasnik-filter">
					<p>Vlasnik:</p>
					<input type="text" class="form-control" id="vlasnik-filter-polje" placeholder="Vlasnik"/>
				</div>
				
				<div id="komentar-filter">
					<p>Sadrzaj komentara:</p>
					<input type="text" class="form-control" id="komentar-filter-polje" placeholder="Sadrzaj komentara"/>
				</div>

				<div id="broj-filter">
					<p>Broj pregleda:</p> 
					<div id="broj-slider"></div>
				</div>
				
				<div id="datum-filter" class="input-group input-daterange">
					<div id="datum-filter-div">
						<p>Datum:</p>
						<button type="button" class="btn btn-default" id="datum-filter-clear-btn">Clear</button>
    				</div>
					<input type="text" class="form-control" name="start" id="datum-filter1"/>
    				<span class="input-group-addon">to</span>
    				<input type="text" class="form-control" name="end" id="datum-filter2"/>
				</div>

				<div id="sort-wrapper">
					<div id="sort-filter">
						<p>Sortiraj po:</p>
						<select class="form-control" id="sort-filter-select">
							<option value="datum">Datumu</option>
  							<option value="naziv">Nazivu</option>
  							<option value="vlasnik">Vlasniku</option>
  							<option value="brojPregleda"> Broju pregleda</option>
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
				</div>

				<div id="pretraga-btn-wrapper">
					<button type="button" class="btn btn-default" id="pretraga-btn">Pretrazi</button>
				</div>
  		</div>

      <!-- MAIN CONTENT -->
      <div class="col-xs-12 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 col-lg-10 col-lg-offset-2 main">
  	  </div>
    </div>

    <!-- FOOTER -->
    <%@include file="include/navigacija_dole.html"%>
  </div>
</body>
</html>