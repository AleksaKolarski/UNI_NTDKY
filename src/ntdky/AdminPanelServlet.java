package ntdky;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import ntdky.dao.KorisnikDAO;
import ntdky.model.Korisnik;
import ntdky.model.Korisnik.TipKorisnika;

/**
 * Servlet implementation class AdminPanelServlet
 */
public class AdminPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));
		
		
		Map<String, Object> data = new HashMap<>();
		String status = "error";
		
		
		if(ulogovaniKorisnik != null && ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
			
			String korisnickoIme = request.getParameter("korisnickoIme");
			String ime = request.getParameter("ime");
			String prezime = request.getParameter("prezime");
			String email = request.getParameter("email");
			String uloga = request.getParameter("tipKorisnika");
			String sortBy = request.getParameter("sortBy");
			String sortDirection = request.getParameter("sortDirection");
			
			if(korisnickoIme == null) {
				korisnickoIme = "";
			}
			if(ime == null) {
				ime = "";
			}
			if(prezime == null) {
				prezime = "";
			}
			if(email == null) {
				email = "";
			}
			if(uloga == null) {
				uloga = "";
			}
			if(!(Arrays.asList("ANY", "USER", "ADMIN")).contains(uloga)) {
				uloga = "ANY";
			}
			
			if(sortBy == null) {
				sortBy = "";
			}
			if(!(Arrays.asList("korisnickoIme", "ime", "prezime", "email", "tipKorisnika")).contains(sortBy)) {
				sortBy = "korisnickoIme";
			}
			
			if(sortDirection == null) {
				sortDirection = "";
			}
			if(!(Arrays.asList("ASC", "DESC")).contains(sortDirection)) {
				sortDirection = "DESC";
			}
			
			
			List<Korisnik> korisnici = KorisnikDAO.getFilter(korisnickoIme, ime, prezime, email, uloga, sortBy, sortDirection);
			
			request.setAttribute("filterKorisnickoIme", korisnickoIme);
			request.setAttribute("filterIme", ime);
			request.setAttribute("filterPrezime", prezime);
			request.setAttribute("filterEmail", email);
			request.setAttribute("filterTipKorisnika", uloga);
			request.setAttribute("filterSortBy", sortBy);
			request.setAttribute("filterSortDirection", sortDirection);
			
			request.setAttribute("korisnici", korisnici);
			request.setAttribute("ulogovaniKorisnik", ulogovaniKorisnik);
			request.getRequestDispatcher("AdminPanel.jsp").forward(request, response);
			status = "success";
		}
		
		if(!status.equals("success")) {
			response.sendRedirect("error-404.jsp");
		}

		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
