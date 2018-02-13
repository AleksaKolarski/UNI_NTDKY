package ntdky;

import ntdky.dao.ConnectionManager;


public class TestMain {

	public static void main(String[] args) {
		ConnectionManager.open();

//		Korisnik kor = new Korisnik("juzernejm", "loz123", "Imeme", "prezZme", "Ja@jabla.me", "Ovo sam ja", new Date(), TipKorisnika.USER, false, false);
//		KorisnikDAO.add(kor);
//		Korisnik korisnik = KorisnikDAO.get("juzernejm");
//		System.out.println(korisnik);
//		KorisnikDAO.delete(korisnik);
//		korisnik = KorisnikDAO.get("juzernejm");
//		System.out.println(korisnik);
//		KorisnikDAO.update(korisnik);
//		System.out.println(korisnik);
//		List<Korisnik> korisnici = KorisnikDAO.getAll();
//		for (Korisnik k : korisnici) {
//			System.out.println(k);
//		}

		
//		Video vidNew = new Video("NoviVideo", "W-YcpWYAido", "W-YcpWYAido.jpg", "", VidljivostVidea.PUBLIC, true, true, false, 15, new Date(), "user1", false);
//		VideoDAO.add(vidNew);
//		VideoDAO.delete(VideoDAO.get(5));
//		Video vid = VideoDAO.get(3);
//		System.out.println(vid);
//		vid.setOpis("Neki kreativni opis");
//		VideoDAO.update(vid);
//		System.out.println(vid);
//		List<Video> videi = VideoDAO.getAll();
//		for (Video v : videi) {
//			System.out.println(v);
//		}
		
		
		
//		Komentar kom = KomentarDAO.get(2);
//		kom.setSadrzaj("Prepravka");
//		KomentarDAO.update(kom);
//		List<Komentar> komentari = KomentarDAO.getAll();
//		for (Komentar k : komentari) {
//			System.out.println(k);
//		}
//		KomentarDAO.add(new Komentar("Novi komentar", new Date(), "user3", 3, false));
		
		
//		System.out.println(LajkDAO.get(1));
//		System.out.println(LajkDAO.get(2));
//		List<Lajk> lajkovi = LajkDAO.getAll();
//		for (Lajk l : lajkovi) {
//			System.out.println(l);
//		}
		
//		List<Korisnik> pretplate = KorisnikDAO.getPretplate(KorisnikDAO.get("user1"));
//		for (Korisnik k : pretplate) {
//			System.out.println(k);
//		}
		
//		List<Korisnik> pretplaceni = KorisnikDAO.getPretplacene(KorisnikDAO.get("user3"));
//		for (Korisnik k : pretplaceni) {
//			System.out.println(k);
//		}
		
		ConnectionManager.close();
	}
}
