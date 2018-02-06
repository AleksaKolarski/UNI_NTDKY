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

public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");

		String profileUsername = request.getParameter("user");
		Korisnik profileUser;

		Map<String, Object> data = new HashMap<>();
		String status = "error";
		
		if(profileUsername != null) {
			if((profileUser = KorisnikDAO.get(profileUsername)) != null) {
				profileUser.setLozinka("");
				request.setAttribute("profil", profileUser);
				status = "success";
				String edit = request.getParameter("edit");
				if(edit != null) {
					if(edit.equals("true")) {
						request.setAttribute("edit", true);
					}
					else {
						request.setAttribute("edit", false);
					}
				}
				request.getRequestDispatcher("Profil.jsp").forward(request, response);
			}
		}
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");

		if (ulogovaniKorisnik != null) {
			String ime = request.getParameter("ime");
			String prezime = request.getParameter("prezime");
			String opis = request.getParameter("opis");
			String lozinka = request.getParameter("lozinka");

			if (ime == null) {
				ime = "";
			}
			if (prezime == null) {
				prezime = "";
			}
			if (opis == null) {
				opis = "";
			}

			Map<String, Object> data = new HashMap<>();
			String status = "success";

			if (ime.length() > 16 || prezime.length() > 16 || opis.length() > 1024) {
				status = "error";
				System.out.println("predugacko ime, prezime ili opis");
			}

			if (lozinka != null) {
				if (!lozinka.equals("")) {
					if (lozinka.length() < 4 || lozinka.length() > 16) {
						status = "error";
						System.out.println("pogresna duzina lozinke");
					}
				}
			}

			if (status.equals("success")) {
				ulogovaniKorisnik.setIme(ime);
				ulogovaniKorisnik.setPrezime(prezime);
				ulogovaniKorisnik.setOpis(opis);
				if (lozinka != null) {
					ulogovaniKorisnik.setLozinka(lozinka);
				}
				KorisnikDAO.update(ulogovaniKorisnik);
			}

			data.put("status", status);

			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}

	}

}
