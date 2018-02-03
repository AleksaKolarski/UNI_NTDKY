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

import ntdky.dao.KomentarDAO;
import ntdky.dao.LajkDAO;
import ntdky.dao.VideoDAO;
import ntdky.model.Komentar;
import ntdky.model.Korisnik;
import ntdky.model.Video;
import ntdky.model.Lajk.Tip;


public class LajkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		
		String target = request.getParameter("target");
		
		Map<String, Object> data = new HashMap<>();
		
		String status = "success";
		
		
		if(target != null) {
			try {
				long id = Long.parseLong(request.getParameter("id"));
				
				if(target.equals("VIDEO")) {
					Video video = VideoDAO.get(id);
					if (video == null) {
						throw new Exception();
					}
					
					data.put("lajkovi", video.getBrojLajkova());
					if(ulogovaniKorisnik != null) {
						data.put("lajkovan", LajkDAO.checkLike(ulogovaniKorisnik, Tip.VIDEO, video));
					}
				}
				else if(target.equals("KOMENTAR")) {
					Komentar komentar = KomentarDAO.get(id);
					if(komentar == null) {
						throw new Exception();
					}
					
					data.put("lajkovi", komentar.getBrojLajkova());
					if(ulogovaniKorisnik != null) {
						data.put("lajkovan", LajkDAO.checkLike(ulogovaniKorisnik, Tip.KOMENTAR, komentar));
					}
				}
			}catch(Exception e) {
				System.out.println("Greska pri ucitavanu lajkova!");
				System.out.println(e);
				status = "error";
			}
		}
		else {
			status = "error";
		}
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		
		String target = request.getParameter("target");
		String tipLajka = request.getParameter("tip");
		
		Map<String, Object> data = new HashMap<>();
		
		String status = "success";

		if(ulogovaniKorisnik != null && target != null && tipLajka != null) {
			try {
				long id = Long.parseLong(request.getParameter("id"));
								
				if(target.equals("VIDEO")) {
					Video video = VideoDAO.get(id);
					if (video == null) {
						throw new Exception();
					}
					
					if(tipLajka.equals("like")) {
						video.lajkuj(ulogovaniKorisnik);
					}
					else if(tipLajka.equals("dislike")) {
						video.dislajkuj(ulogovaniKorisnik);
					}
					else {
						status = "error";
					}
				}
				else if(target.equals("KOMENTAR")) {
					Komentar komentar = KomentarDAO.get(id);
					if(komentar == null) {
						throw new Exception();
					}
					
					if(tipLajka.equals("like")) {
						komentar.lajkuj(ulogovaniKorisnik);
					}
					else if(tipLajka.equals("dislike")) {
						komentar.dislajkuj(ulogovaniKorisnik);
					}
					else {
						status = "error";
					}
				}
			}catch(Exception e) {
				System.out.println("Greska pri lajkovanju/dislajkovanju!");	
				status = "error";
			}
		}
		else {
			status = "error";
		}
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}
