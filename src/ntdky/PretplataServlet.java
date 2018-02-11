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
import ntdky.dao.VideoDAO;
import ntdky.model.Korisnik;
import ntdky.model.Video;


public class PretplataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));

		Map<String, Object> data = new HashMap<>();
		String status;

		if (ulogovaniKorisnik != null) {
			status = "success";

			boolean gotovo = false;

			// provera na stranici profila
			String korisnickoIme = request.getParameter("korisnik");
			if (korisnickoIme != null) {
				Korisnik korisnik = KorisnikDAO.get(korisnickoIme);
				if (korisnik != null) {
					if (!ulogovaniKorisnik.getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
						data.put("pretplacen", KorisnikDAO.checkPretplata(ulogovaniKorisnik, korisnik));
						data.put("broj", KorisnikDAO.getPretplaceneBroj(korisnik));
						gotovo = true;
					}
				} else {
					status = "error";
				}
			}

			if (gotovo == false) {
				// provera na stranici videa
				try {
					long videoId = Long.parseLong(request.getParameter("videoId"));

					Video video = VideoDAO.get(videoId, ulogovaniKorisnik);

					if (video != null) {
						Korisnik koga = KorisnikDAO.get(video.getVlasnik());
						if (koga != null) {
							if (!ulogovaniKorisnik.getKorisnickoIme().equals(koga.getKorisnickoIme())) {
								data.put("pretplacen", KorisnikDAO.checkPretplata(ulogovaniKorisnik, koga));
								data.put("broj", KorisnikDAO.getPretplaceneBroj(koga));
							} else {
								throw new Exception();
							}
						} else {
							throw new Exception();
						}
					} else {
						throw new Exception();
					}
				} catch (Exception e) {
					status = "error";
				}
			}
		} else {
			status = "unauthenticated";
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
		String status;

		if (ulogovaniKorisnik != null) {
			status = "success";

			boolean gotovo = false;

			// provera na stranici profila
			String korisnickoIme = request.getParameter("korisnik");
			if (korisnickoIme != null) {
				Korisnik korisnik = KorisnikDAO.get(korisnickoIme);
				if (korisnik != null) {
					if (!ulogovaniKorisnik.getKorisnickoIme().equals(korisnik.getKorisnickoIme())) {
						//KorisnikDAO.checkPretplata(ulogovaniKorisnik, korisnik);
						//KorisnikDAO.pretplati(ulogovaniKorisnik, korisnik);
						Korisnik.pretplata(ulogovaniKorisnik, korisnik);
						gotovo = true;
					}
				} else {
					status = "error";
				}
			}

			if (gotovo == false) {
				// provera na stranici videa
				try {
					long videoId = Long.parseLong(request.getParameter("videoId"));

					Video video = VideoDAO.get(videoId, ulogovaniKorisnik);

					if (video != null) {
						Korisnik koga = KorisnikDAO.get(video.getVlasnik());
						if (koga != null) {
							if (!ulogovaniKorisnik.getKorisnickoIme().equals(koga.getKorisnickoIme())) {
								//KorisnikDAO.pretplati(ulogovaniKorisnik, koga);
								Korisnik.pretplata(ulogovaniKorisnik, koga);
							} else {
								throw new Exception();
							}
						} else {
							throw new Exception();
						}
					} else {
						throw new Exception();
					}
				} catch (Exception e) {
					status = "error";
				}
			}
		} else {
			status = "unauthenticated";
		}
		data.put("status", status);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
