package ntdky;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import ntdky.dao.KorisnikDAO;
import ntdky.model.Korisnik;
import ntdky.model.Korisnik.TipKorisnika;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String email = request.getParameter("email");
		String korisnickoIme = request.getParameter("korisnickoIme");
		String lozinka = request.getParameter("lozinka");
		
		String status = "error";
		if(ime.length() <= 16) {
			if(prezime.length() <= 16) {
				if(email.matches(".+\\@.+\\..+") && email.length() <= 30) {
					if(korisnickoIme.length() >= 4 && korisnickoIme.length() <= 16) {
						if(KorisnikDAO.get(korisnickoIme) == null) {
							if(lozinka.length() >= 4 && lozinka.length() <= 16) {
								System.out.println("Registrovan novi korisnik '" + korisnickoIme + "'");
								if(KorisnikDAO.add(new Korisnik(korisnickoIme, lozinka, ime, prezime, email, "", null, new Date(), TipKorisnika.USER, false, false))) {
									status = "success";
								}
							}
						}
						else {
							status = "taken";
						}
					}
				}
			}
		}
		
		// pravimo mapu podataka
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);

		// mapu konvertujemo u json
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		// json ispisujemo u response
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
