package ntdky;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import ntdky.dao.KorisnikDAO;
import ntdky.model.Korisnik;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String korisnickoIme = request.getParameter("korisnickoIme");
		String lozinka = request.getParameter("lozinka");
		
		String status = "error";
		
		Korisnik korisnik = KorisnikDAO.get(korisnickoIme);
		if(korisnik != null) {
			if(korisnik.getLozinka().equals(lozinka)) {
				// login pozitivan
				HttpSession session = request.getSession();
				session.setAttribute("ulogovaniKorisnik", korisnik);
				status = "success";
			}
		}
		
		// pravimo mapu podataka
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);

		// mapu konvertujemo u json
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		// json ispisujemo u response
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}
