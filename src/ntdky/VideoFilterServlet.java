package ntdky;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import ntdky.dao.VideoDAO;
import ntdky.model.Korisnik;
import ntdky.model.Video;


public class VideoFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		
		Map<String, Object> data = new HashMap<>();
		
		String status = "success";
		
		
		// naziv
		// vlasnik
		// broj pregleda
		// datum
		// sortBy
		// sortDirection
		
		String nazivFilter = request.getParameter("nazivFilter");
		if(nazivFilter == null) {
			nazivFilter = "";
		}
		System.out.println("naziv: '" + nazivFilter + "'");
		
		String vlasnikFilter = request.getParameter("vlasnikFilter");
		if(vlasnikFilter == null) {
			vlasnikFilter = "";
		}
		System.out.println("vlasnik: '" + vlasnikFilter + "'");
		
		Date datumFilterMin;
		Date datumFilterMax;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy."); // format koji nam salje klijent
		try {
			datumFilterMin = formatter.parse(request.getParameter("datumFilterMin")); // format u bazi je yyyy-MM-dd
			datumFilterMax = formatter.parse(request.getParameter("datumFilterMax"));
		}
		catch(Exception e) {
			datumFilterMin = new Date(1970-1900, 1, 1); // 1.1.1970.
			datumFilterMax = new Date(9999-1900, 1, 1); // nesto daleko
		}
		
		System.out.println("datumMin: '" + datumFilterMin + "'");
		System.out.println("datumMax: '" + datumFilterMax + "'");
		
		long brojFilterMin;
		long brojFilterMax;
		try {
			brojFilterMin = Long.parseLong(request.getParameter("brojFilterMin"));
			brojFilterMax = Long.parseLong(request.getParameter("brojFilterMax"));
		}
		catch(Exception e) {
			brojFilterMin = 0;
			brojFilterMax = Long.MAX_VALUE;
		}
		if(brojFilterMin < 0) {
			brojFilterMin = 0;
		}
		if(brojFilterMax < 0) {
			brojFilterMax = 0;
		}
		
		System.out.println("brojMin: '" + brojFilterMin + "'");
		System.out.println("brojMax: '" + brojFilterMax + "'");
		
		String sortBy = request.getParameter("sortBy");
		if(sortBy != null) {
			if(!(Arrays.asList("naziv", "brojPregleda", "datum", "vlasnik")).contains(sortBy)) {
				sortBy = "datum";
			}
		}
		else {
			sortBy = "datum";
		}
		System.out.println("sortBy: '" + sortBy + "'");
		
		String sortDirection = request.getParameter("sortDirection");
		if(sortDirection != null) {
			if(!(Arrays.asList("ASC", "DESC")).contains(sortDirection)) {
				sortDirection = "DESC";
			}
		}
		else {
			sortDirection = "DESC";
		}
		System.out.println("sortDirection: '" + sortDirection + "'");
		
		List<Video> videi = VideoDAO.getFilter(nazivFilter, vlasnikFilter, datumFilterMin, datumFilterMax, brojFilterMin, brojFilterMax, sortBy, sortDirection);
		if(videi != null) {
			data.put("videi", videi);
		}
		else {
			status = "no-results";
		}
		
		data.put("preglediMinMax", VideoDAO.getPregledMinMax());
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
