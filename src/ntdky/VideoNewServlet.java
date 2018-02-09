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

import ntdky.dao.KorisnikDAO;
import ntdky.dao.VideoDAO;
import ntdky.model.Korisnik;
import ntdky.model.Video;
import ntdky.model.Video.VidljivostVidea;
import ntdky.model.Korisnik.TipKorisnika;


public class VideoNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));

		Map<String, Object> data = new HashMap<>();
		String status = "success";
		
		try {
			if (ulogovaniKorisnik != null) {
				String edit = request.getParameter("edit");

				if (edit != null && edit.equals("true")) {
					// edit
					long videoId = Long.parseLong(request.getParameter("videoId"));
					Video video = VideoDAO.get(videoId);
					
					if(video == null) {
						throw new Exception();
					}
					
					if(video.getVlasnik().equals(ulogovaniKorisnik.getKorisnickoIme()) || ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
						request.setAttribute("video", video);
						request.setAttribute("edit", true);
					}
					else {
						throw new Exception();
					}
				}
				else {
					// novi
					request.setAttribute("edit", false);
				}
				request.setAttribute("ulogovaniKorisnik", ulogovaniKorisnik);
				request.getRequestDispatcher("VideoNew.jsp").forward(request, response);
			}
			else {
				throw new Exception();
			}
		}
		catch(Exception e) {
			status = "error";
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
		
		boolean edit = false;
		if(ulogovaniKorisnik != null) {
			Video video = null;
			try {
				long videoId = Long.parseLong(request.getParameter("videoId"));
				video = VideoDAO.get(videoId);
				if(video != null) {
					if(video.getVlasnik().equals(ulogovaniKorisnik.getKorisnickoIme()) || ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
						edit = true;
					}
					else {
						status = "error";
					}
				}
			}
			catch(Exception e) {
				System.out.println("parametar video nije naveden");
			}
			
			String obrisan = request.getParameter("obrisan");
			if(obrisan != null && obrisan.equals("true")) {
				VideoDAO.delete(video);
				data.put("status", "success");

				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);

				response.setContentType("application/json");
				response.getWriter().write(jsonData);
				
				return;
			}
			
			String  naziv = request.getParameter("naziv");
			String putanja = request.getParameter("putanja");
			String opis = request.getParameter("opis");
			String blokiran = request.getParameter("blokiran");
			boolean blokiranBool = false;
			String vidljivostVidea = request.getParameter("vidljivostVidea");
			VidljivostVidea vidljivostVideaTip = VidljivostVidea.PUBLIC;
			String vidljivostKomentara = request.getParameter("vidljivostKomentara");
			boolean vidljivostKomentaraBool = true;
			String vidljivostRejtinga = request.getParameter("vidljivostRejtinga");
			boolean vidljivostRejtingaBool = true;
			
			if(naziv == null) {
				status = "error";
			}
			else if(naziv.length() < 4 || naziv.length() > 80) {
				status = "error";
			}
			
			if(putanja == null) {
				status = "error";
			}
			else if(putanja.length() != 11) {
				status = "error";
			}
			
			if(opis == null) {
				opis = "";
			}
			if(opis.length() > 1024) {
				status = "error";
			}
			
			if(ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
				if(blokiran == null) {
					status = "error";
				}
				else if(!(blokiran.equals("true") || blokiran.equals("false"))) {
					status = "error";
				}
				else {
					if(blokiran.equals("true")) {
						blokiranBool = true;
					}
					else {
						blokiranBool = false;
					}
				}
			}
			
			if(vidljivostVidea == null) {
				status = "error";
			}
			else if(!(vidljivostVidea.equals("PUBLIC") || vidljivostVidea.equals("UNLISTED") || vidljivostVidea.equals("PRIVATE"))) {
				status = "error";
			}
			else {
				if(vidljivostVidea.equals("PUBLIC")) {
					vidljivostVideaTip = VidljivostVidea.PUBLIC;
				}
				else if(vidljivostVidea.equals("UNLISTED")) {
					vidljivostVideaTip = VidljivostVidea.UNLISTED;
				}
				else {
					vidljivostVideaTip = VidljivostVidea.PRIVATE;
				}
			}
			
			if(vidljivostKomentara == null) {
				status = "error";
			}
			else if(!(vidljivostKomentara.equals("true") || vidljivostKomentara.equals("false"))) {
				status = "error";
			}
			else {
				if(vidljivostKomentara.equals("true")) {
					vidljivostKomentaraBool = true;
				}
				else {
					vidljivostKomentaraBool = false;
				}
			}
			
			if(vidljivostRejtinga == null) {
				status = "error";
			}
			else if(!(vidljivostRejtinga.equals("true") || vidljivostRejtinga.equals("false"))) {
				status = "error";
			}
			else {
				if(vidljivostRejtinga.equals("true")) {
					vidljivostRejtingaBool = true;
				}
				else {
					vidljivostRejtingaBool = false;
				}
			}
			
			
			
			if(status.equals("success")) {
				if(edit == false) {
					// napravi novi
					VideoDAO.add(new Video(naziv, putanja, "", opis, vidljivostVideaTip, vidljivostKomentaraBool, vidljivostRejtingaBool, false, 0, new Date(), ulogovaniKorisnik.getKorisnickoIme(), false));
				}
				else {
					// izmeni postojeci
					video.setNaziv(naziv);
					video.setPutanjaVidea(putanja);
					video.setOpis(opis);
					if(ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
						video.setBlokiran(blokiranBool);
					}
					video.setVidljivostVidea(vidljivostVideaTip);
					video.setVidljivostKomentari(vidljivostKomentaraBool);
					video.setVidljivostRejting(vidljivostRejtingaBool);
					VideoDAO.update(video);
				}
			}
		}else {
			System.out.println("korisnik nije ulogovan");
		}

		data.put("status", status);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
