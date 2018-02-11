package ntdky.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ntdky.model.Video;
import ntdky.model.Korisnik;
import ntdky.model.Korisnik.TipKorisnika;
import ntdky.model.Lajk.Tip;
import ntdky.model.Video.VidljivostVidea;

public class VideoDAO {
	public static Video get(long id, Korisnik ulogovaniKorisnik) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = null;
			
			if(ulogovaniKorisnik == null) {
				query = "SELECT naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan FROM Video " + 
						"WHERE vidljivostVidea!='PRIVATE' AND id=? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0;";
			}
			else {
				if(ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.USER) {
					query = "SELECT naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan FROM Video " + 
							"WHERE (vidljivostVidea!='PRIVATE' OR vlasnik=?) AND id=? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0;";
				}
				else {
					query = "SELECT naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan FROM Video " + 
							"WHERE id=? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0;";
				}
			}
			//String query = "SELECT naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan FROM Video WHERE id=? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0;";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			if(ulogovaniKorisnik != null && ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.USER) {
				pstmt.setString(index++, ulogovaniKorisnik.getKorisnickoIme());
			}
			pstmt.setLong(index++, id);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				index = 1;
				String naziv = rset.getString(index++);
				String putanjaVidea = rset.getString(index++);
				String putanjaSlike = rset.getString(index++);
				String opis = rset.getString(index++);
				VidljivostVidea vidljivostVidea = VidljivostVidea.valueOf(rset.getString(index++));
				boolean vidljivostKomentari = rset.getBoolean(index++);
				boolean vidljivostRejting = rset.getBoolean(index++);
				boolean blokiran = rset.getBoolean(index++);
				long brojPregleda = rset.getLong(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				String vlasnik = rset.getString(index++);
				boolean obrisan = rset.getBoolean(index++);
				
				blokiran = (blokiran || KorisnikDAO.get(vlasnik).getBlokiran());

				return new Video(id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan);
			} else {
				System.out.println("Greska pri pronalazenju videa sa id '" + id + "' u bazi.");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static List<Video> getFilter(String nazivFilter, String vlasnikFilter, Date datumFilterMin, Date datumFilterMax, long brojFilterMin, long brojFilterMax, String sortBy, String sortDirection, Korisnik ulogovaniKorisnik) {
		ArrayList<Video> videi = new ArrayList<Video>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = null;
			
			if(ulogovaniKorisnik == null) {
				query = "SELECT id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan " + 
						"FROM Video " + 
						"WHERE blokiran=0 AND vidljivostVidea='PUBLIC' AND naziv LIKE ? AND vlasnik LIKE ? AND datum BETWEEN CAST(? AS DATE) AND DATE_ADD(CAST(? AS DATE), INTERVAL 1 DAY) AND brojPregleda BETWEEN ? AND ? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0 AND (SELECT blokiran FROM Korisnik WHERE korisnickoIme=vlasnik)=0 ORDER BY " + sortBy + " " + sortDirection + ";";
			}
			else {
				if(ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.USER) {
					query = "SELECT id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan " + 
							"FROM Video " + 
							"WHERE ((SELECT blokiran FROM Korisnik WHERE korisnickoIme=vlasnik)=0 OR vlasnik=?) AND (blokiran=0 OR vlasnik=?) AND (vidljivostVidea='PUBLIC' OR vlasnik=?) AND naziv LIKE ? AND vlasnik LIKE ? AND datum BETWEEN CAST(? AS DATE) AND DATE_ADD(CAST(? AS DATE), INTERVAL 1 DAY) AND brojPregleda BETWEEN ? AND ? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0 ORDER BY " + sortBy + " " + sortDirection + ";";
				}
				else {
					query = "SELECT id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan " + 
							"FROM Video " + 
							"WHERE naziv LIKE ? AND vlasnik LIKE ? AND datum BETWEEN CAST(? AS DATE) AND DATE_ADD(CAST(? AS DATE), INTERVAL 1 DAY) AND brojPregleda BETWEEN ? AND ? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0 ORDER BY " + sortBy + " " + sortDirection + ";";
				}
			}
			
			/*
			query = "SELECT id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan " + 
							"FROM Video " + 
							"WHERE naziv LIKE ? AND vlasnik LIKE ? AND datum BETWEEN CAST(? AS DATE) AND DATE_ADD(CAST(? AS DATE), INTERVAL 1 DAY) AND brojPregleda BETWEEN ? AND ? AND obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0 ORDER BY " + sortBy + " " + sortDirection + ";";
			*/
			pstmt = conn.prepareStatement(query);
			int index = 1;
			if(ulogovaniKorisnik != null && ulogovaniKorisnik.getTipKorisnika() == TipKorisnika.USER) {
				pstmt.setString(index++, ulogovaniKorisnik.getKorisnickoIme());
				pstmt.setString(index++, ulogovaniKorisnik.getKorisnickoIme());
				pstmt.setString(index++, ulogovaniKorisnik.getKorisnickoIme());
			}
			
			pstmt.setString(index++, "%" + nazivFilter + "%");
			pstmt.setString(index++, "%" + vlasnikFilter + "%");
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			pstmt.setString(index++, formatter.format(datumFilterMin));
			pstmt.setString(index++, formatter.format(datumFilterMax));
			
			pstmt.setLong(index++, brojFilterMin);
			pstmt.setLong(index++, brojFilterMax);
						
			rset = pstmt.executeQuery();

			while (rset.next()) {
				index = 1;
				long id = rset.getLong(index++);
				String naziv = rset.getString(index++);
				String putanjaVidea = rset.getString(index++);
				String putanjaSlike = rset.getString(index++);
				String opis = rset.getString(index++);
				VidljivostVidea vidljivostVidea = VidljivostVidea.valueOf(rset.getString(index++));
				boolean vidljivostKomentari = rset.getBoolean(index++);
				boolean vidljivostRejting = rset.getBoolean(index++);
				boolean blokiran = rset.getBoolean(index++);
				long brojPregleda = rset.getLong(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				String vlasnik = rset.getString(index++);
				boolean obrisan = rset.getBoolean(index++);
				
				blokiran = (blokiran || KorisnikDAO.get(vlasnik).getBlokiran());

				videi.add(new Video(id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan));
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return videi;
	}
	
	public static List<Video> getAll() {
		List<Video> videi = new ArrayList<Video>();

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan FROM Video WHERE obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0;";

			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				long id = rset.getLong(index++);
				String naziv = rset.getString(index++);
				String putanjaVidea = rset.getString(index++);
				String putanjaSlike = rset.getString(index++);
				String opis = rset.getString(index++);
				VidljivostVidea vidljivostVidea = VidljivostVidea.valueOf(rset.getString(index++));
				boolean vidljivostKomentari = rset.getBoolean(index++);
				boolean vidljivostRejting = rset.getBoolean(index++);
				boolean blokiran = rset.getBoolean(index++);
				long brojPregleda = rset.getLong(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				String vlasnik = rset.getString(index++);
				boolean obrisan = rset.getBoolean(index++);

				videi.add(new Video(id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan));
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return videi;
	}
	
	public static boolean update(Video video) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Video SET naziv=?, putanjaVidea=?, putanjaSlike=?, opis=?, vidljivostVidea=?, vidljivostKomentari=?, vidljivostRejting=?, blokiran=?, brojPregleda=?, datum=?, vlasnik=?, obrisan=? WHERE id=?;";
			pstmt = conn.prepareStatement(query);

			int index = 1;
			pstmt.setString(index++, video.getNaziv());
			pstmt.setString(index++, video.getPutanjaVidea());
			pstmt.setString(index++, video.getPutanjaSlike());
			pstmt.setString(index++, video.getOpis());
			pstmt.setString(index++, video.getVidljivostVidea().toString());
			pstmt.setBoolean(index++, video.getVidljivostKomentari());
			pstmt.setBoolean(index++, video.getVidljivostRejting());
			pstmt.setBoolean(index++, video.getBlokiran());
			pstmt.setLong(index++, video.getBrojPregleda());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(video.getDatum()));
			pstmt.setString(index++, video.getVlasnik());
			pstmt.setBoolean(index++, video.getObrisan());
			pstmt.setLong(index++, video.getId());

			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean increment(Video video) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Video SET brojPregleda=? WHERE id=?;";
			pstmt = conn.prepareStatement(query);

			int index = 1;
			pstmt.setLong(index++, video.getBrojPregleda());
			pstmt.setLong(index++, video.getId());

			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean add(Video video) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, video.getNaziv());
			pstmt.setString(index++, video.getPutanjaVidea());
			pstmt.setString(index++, video.getPutanjaSlike());
			pstmt.setString(index++, video.getOpis());
			pstmt.setString(index++, video.getVidljivostVidea().toString());
			pstmt.setBoolean(index++, video.getVidljivostKomentari());
			pstmt.setBoolean(index++, video.getVidljivostRejting());
			pstmt.setBoolean(index++, video.getBlokiran());
			pstmt.setLong(index++, video.getBrojPregleda());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(video.getDatum()));
			pstmt.setString(index++, video.getVlasnik());
			pstmt.setBoolean(index++, video.getObrisan());
			
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean delete(Video video) {
		System.out.println("Brisanje videa '" + video.getId() + "'");
		if(video.getObrisan() == false) {
			video.setObrisan(true);
			return update(video);
		}
		System.out.println("Video je vec obrisan.");
		return false;
	}

	public static List<Long> getPregledMinMax() {
		List<Long> lista = null;
		
		long min = 0;
		long max = 0;

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MIN(brojPregleda), MAX(brojPregleda) FROM Video WHERE obrisan=0 AND (SELECT obrisan FROM Korisnik WHERE korisnickoIme=vlasnik)=0;";

			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			lista = new ArrayList<Long>();
			while (rset.next()) {
				min = rset.getLong(1);
				max = rset.getLong(2);
			}
			
			lista.add(0, min);
			lista.add(1, max);
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return lista;
	}
}
