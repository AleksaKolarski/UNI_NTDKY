package ntdky;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ntdky.dao.VideoDAO;
import ntdky.model.Korisnik;
import ntdky.model.Video;

/**
 * Servlet implementation class VideoServlet
 */
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		
		String status = "success";
		
		try {
			long videoId = Long.parseLong(request.getParameter("id"));
			Video video = VideoDAO.get(videoId);
			if(video == null) {
				throw new Exception();
			}
			
			request.setAttribute("video", video);
			
			request.getRequestDispatcher("Video.jsp").forward(request, response);
			
		}catch(Exception e) {
			System.out.println("Pogresan video id!");
			status = "not-found";
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
