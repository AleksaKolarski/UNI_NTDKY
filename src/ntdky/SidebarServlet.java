package ntdky;

import java.io.IOException;
import java.util.ArrayList;
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


public class SidebarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));
		
		String status;
		Map<String, Object> data = new HashMap<>();
		
		List<Map<String, String>> kanali = new ArrayList<Map<String, String>>();
		if(ulogovaniKorisnik != null) {
			// ulogovan je pa vracamo listu pretplata
			status = "success";
			
			data.put("korisnik", ulogovaniKorisnik.getKorisnickoIme());
			
			List<Korisnik> pretplate = KorisnikDAO.getPretplate(ulogovaniKorisnik);
			for(Korisnik korisnik: pretplate) {
				Map<String, String> mapa = new HashMap<String, String>();
				mapa.put("profil", korisnik.getKorisnickoIme());
				mapa.put("slika", korisnik.getSlika());
				kanali.add(mapa);
			}
		}
		else {
			// nije ulogovan pa vracamo listu popularnih
			status = "unauthenticated";
			
			List<Korisnik> korisnici = KorisnikDAO.getPopularne(5);
			
			for(Korisnik korisnik: korisnici) {
				Map<String, String> mapa = new HashMap<String, String>();
				mapa.put("profil", korisnik.getKorisnickoIme());
				mapa.put("slika", korisnik.getSlika());
				kanali.add(mapa);
			}
		}
		
		data.put("kanali", kanali);
		
		data.put("status", status);

		// mapu konvertujemo u json
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		// json ispisujemo u response
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
