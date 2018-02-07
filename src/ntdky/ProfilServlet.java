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

public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));

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
				request.setAttribute("ulogovaniKorisnik", ulogovaniKorisnik);
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
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));

		Map<String, Object> data = new HashMap<>();
		String status = "success";
		
		if (ulogovaniKorisnik != null) {
			String profil = request.getParameter("profil");
			if(profil != null && (ulogovaniKorisnik.getKorisnickoIme().equals(profil) || ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN)) {
				Korisnik korisnik = KorisnikDAO.get(profil);
				if(korisnik != null) {
					
					String obrisan = request.getParameter("obrisan");
					if(obrisan != null && obrisan.equals("true")) {
						KorisnikDAO.delete(korisnik);
						data.put("status", "success");

						ObjectMapper mapper = new ObjectMapper();
						String jsonData = mapper.writeValueAsString(data);

						response.setContentType("application/json");
						response.getWriter().write(jsonData);
						
						return;
					}
					
					
					
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
					
					if(ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
						String blokiran = request.getParameter("blokiran");
						String uloga = request.getParameter("uloga");
						
						if(blokiran != null && uloga != null) {
							if(blokiran.equals("TRUE")) {
								korisnik.setBlokiran(true);
							}
							else if(blokiran.equals("FALSE")) {
								korisnik.setBlokiran(false);
							}
							else {
								status = "error";
								System.out.println("pogresno blokiranje korisnika");
							}
							if(uloga.equals("USER")) {
								korisnik.setTipKorisnika(TipKorisnika.USER);
							}
							else if(uloga.equals("ADMIN")) {
								korisnik.setTipKorisnika(TipKorisnika.ADMIN);
							}
							else {
								status = "error";
								System.out.println("pogresna uloga korisnika");
							}
						}
						else {
							status = "error";
							System.out.println("ulogovani korisnik je admin ali nije navedeno blokiranje ili uloga");
						}
					}
					
					if (status.equals("success")) {
						korisnik.setIme(ime);
						korisnik.setPrezime(prezime);
						korisnik.setOpis(opis);
						if (lozinka != null) {
							korisnik.setLozinka(lozinka);
						}
						KorisnikDAO.update(korisnik);
						/*
						if(korisnik.getKorisnickoIme().equals(ulogovaniKorisnik.getKorisnickoIme())) {
							session.setAttribute("ulogovaniKorisnik", korisnik);
						}
						*/
					}
				}
				else {
					status = "error";
					System.out.println("korisnik ne postoji");
				}
			}
			else {
				status = "error";
				System.out.println("korisnik nije naveden ili ga menja neko ko nema prava na to");
			}
		}
		else {
			status = "error";
			System.out.println("nije ulogovan");
		}
		
		data.put("status", status);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
