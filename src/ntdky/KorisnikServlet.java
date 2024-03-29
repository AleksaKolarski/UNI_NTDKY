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
import ntdky.model.Korisnik.TipKorisnika;

public class KorisnikServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));
		
		Map<String, Object> data = new HashMap<>();
		String status;
		if (ulogovaniKorisnik != null) {
			// ulogovan je pa vracamo success i korisnicko ime
			status = "success";
			data.put("korisnik", ulogovaniKorisnik.getKorisnickoIme());
			
			if(ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
				data.put("tipKorisnika", "ADMIN");
			}
			data.put("blokiran", ulogovaniKorisnik.getBlokiran());
		}
		else {
			// nije ulogovan
			status = "unauthenticated";
		}
		data.put("status", status);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}
