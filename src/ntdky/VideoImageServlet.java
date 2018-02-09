package ntdky;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ntdky.dao.KorisnikDAO;
import ntdky.dao.VideoDAO;
import ntdky.model.Korisnik;
import ntdky.model.Korisnik.TipKorisnika;
import ntdky.model.Video;

public class VideoImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovaniKorisnik = KorisnikDAO.get((String) session.getAttribute("ulogovaniKorisnik"));

		String status = "error";

		if (ulogovaniKorisnik != null) {

			if (!ServletFileUpload.isMultipartContent(request)) {
				throw new IllegalArgumentException(
						"Request is not multipart, please 'multipart/form-data' enctype for your form.");
			}

			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			try {
				List<FileItem> items = uploadHandler.parseRequest(request);

				for (FileItem item : items) {
					if (!item.isFormField()) {
						long videoId;
						try {
							videoId = Long.parseLong(item.getFieldName());

							Video video = VideoDAO.get(videoId);
							if (video != null) {
								if (ulogovaniKorisnik.getKorisnickoIme().equals(video.getVlasnik()) || ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.ADMIN) {
									File file = new File("WebContent/img/video/icon", item.getFieldName());
									item.write(file);

									video.setPutanjaSlike(item.getFieldName());
									VideoDAO.update(video);
									status = "success";
								}
							}
						} catch (Exception e) {}
					}
				}
			} catch (FileUploadException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
