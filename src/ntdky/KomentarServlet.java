package ntdky;

import java.io.IOException;
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
import ntdky.dao.VideoDAO;
import ntdky.model.Komentar;
import ntdky.model.Korisnik;
import ntdky.model.Video;


public class KomentarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		
		Map<String, Object> data = new HashMap<>();
		String status = "success";

		if(ulogovaniKorisnik == null) {
			data.put("loginStatus", "unauthenticated");
		}
		
		try {
			long videoId = Long.parseLong(request.getParameter("videoId"));
			Video video = VideoDAO.get(videoId);
			
			if(video == null) {
				throw new Exception();
			}
			
			data.put("komentari", video.getKomentari());
			
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
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		
		Map<String, Object> data = new HashMap<>();
		String status = "error";
		
		if(ulogovaniKorisnik != null) {
			String sadrzaj = request.getParameter("sadrzaj");
			if(sadrzaj != null) {
				if(sadrzaj.length() > 0) {
					try {
						long videoId = Long.parseLong(request.getParameter("videoId"));
						
						Video video = VideoDAO.get(videoId);
						if(video != null) {
							Komentar komentar = new Komentar(sadrzaj, new Date(), ulogovaniKorisnik.getKorisnickoIme(), video.getId(), false);
							if(KomentarDAO.add(komentar)) {
								status = "success";
							}
						}
					}
					catch(Exception e) {
					}
				}
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
