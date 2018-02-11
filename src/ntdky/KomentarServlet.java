package ntdky;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import ntdky.dao.KomentarDAO;
import ntdky.dao.KorisnikDAO;
import ntdky.dao.VideoDAO;
import ntdky.model.Komentar;
import ntdky.model.Korisnik;
import ntdky.model.Video;


public class KomentarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));
		
		Map<String, Object> data = new HashMap<>();
		String status = "success";

		if(ulogovaniKorisnik == null) {
			data.put("loginStatus", "unauthenticated");
		}
		
		try {
			if(request.getParameter("videoId") == null) {
				throw new Exception();
			}
			
			long videoId = Long.parseLong(request.getParameter("videoId"));
			Video video = VideoDAO.get(videoId, ulogovaniKorisnik);
			
			if(video == null) {
				throw new Exception();
			}

			String sortBy = request.getParameter("sortBy");
			String sortDirection = request.getParameter("sortDirection");
			
			if(!(Arrays.asList("datum", "rejting")).contains(sortBy)) {
				sortBy = "datum";
			}
			if(!(Arrays.asList("ASC", "DESC")).contains(sortDirection)) {
				sortDirection = "DESC";
			}
			
			data.put("komentari", KomentarDAO.getAllVideoFilter(videoId, sortBy, sortDirection));
			if(ulogovaniKorisnik != null) {
				data.put("korisnik", ulogovaniKorisnik.getKorisnickoIme());
				data.put("korisnikTip", ulogovaniKorisnik.getTipKorisnika().toString());
			}
		}catch(Exception e) {
			System.out.println("Pogresan video id!");	
			status = "not-found";
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
		String status = "error";
		
		if(ulogovaniKorisnik != null) {
			String sadrzaj = request.getParameter("sadrzaj");
			if(sadrzaj != null) {
				if(sadrzaj.length() > 0) {
					boolean edit = true;
					try {
						long videoId = Long.parseLong(request.getParameter("videoId"));
						edit = false;
						
						Video video = VideoDAO.get(videoId, ulogovaniKorisnik);
						if(video != null) {
							Komentar komentar = new Komentar(sadrzaj, new Date(), ulogovaniKorisnik.getKorisnickoIme(), video.getId(), false);
							if(KomentarDAO.add(komentar)) {
								status = "success";
								System.out.println("dodat komentar");
							}
						}
					}
					catch(Exception e) {
					}
					if(edit == true) {
						try {
							long komentarId = Long.parseLong(request.getParameter("komentarId"));
							
							Komentar komentar = KomentarDAO.get(komentarId);
							if(komentar != null) {
								komentar.setSadrzaj(sadrzaj);
								if(KomentarDAO.update(komentar)) {
									status = "success";
								}
							}
						}
						catch(Exception e) {
							
						}
					}
				}
			}else {				
				try {
					long komentarId = Long.parseLong(request.getParameter("komentarId"));
					String obrisan = request.getParameter("obrisan");
					if(obrisan != null && obrisan.equals("true")) {
						Komentar komentar = KomentarDAO.get(komentarId);
						if(komentar != null) {
							KomentarDAO.delete(komentar);
							status = "success";
						}
					}
				}
				catch(Exception e) {}
			}
		}
		else {
			status = "unauthenticated";
		}
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}
