package ntdky;

import java.io.Console;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");

		String profileUsername = request.getParameter("profil");
		Korisnik profileUser;

		Map<String, Object> data = new HashMap<>();
		String status_login = "unauthenticated";
		String status_profile = "not-found";

		if (profileUsername != null) {
			if ((profileUser = KorisnikDAO.get(profileUsername)) != null) {

				/*
				 * request.setAttribute("profileUsername", profileUsername);
				 * request.setAttribute("profileName", profileUser.getIme());
				 * request.setAttribute("profileLastName", profileUser.getPrezime());
				 * request.setAttribute("profileDate", profileUser.getDatum());
				 * request.setAttribute("profileUloga", profileUser.getTipKorisnika());
				 * request.setAttribute("profileBrojPratilaca",
				 * KorisnikDAO.getPretplateBroj(profileUser));
				 * 
				 * request.getRequestDispatcher("Profil.jsp").forward(request, response);
				 */

				if (ulogovaniKorisnik != null) {
					status_login = "success";
					data.put("ulogovani", ulogovaniKorisnik.getKorisnickoIme());

					data.put("pretplacen",
							(KorisnikDAO.checkPretplata(ulogovaniKorisnik, profileUser)) ? "true" : "false");
				}

				status_profile = "found";

				data.put("korisnickoIme", profileUser.getKorisnickoIme());
				data.put("ime", profileUser.getIme());
				data.put("prezime", profileUser.getPrezime());
				data.put("opis", profileUser.getOpis());
				data.put("datum", profileUser.getDatum().toString());
				data.put("uloga", profileUser.getTipKorisnika());
				data.put("brojPratilaca", KorisnikDAO.getPretplateBroj(profileUser));
			} else {
				// nema tog korisnika
				System.out.println("Nije pronadjen taj korisnik");
			}
		} else {
			// u linku nije specificiran korisnik
			System.out.println("U linku nije specificiran korisnik");
		}

		data.put("status_login", status_login);
		data.put("status_profile", status_profile);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}

	}

}
