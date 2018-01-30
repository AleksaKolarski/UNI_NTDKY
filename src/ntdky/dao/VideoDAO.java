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
import ntdky.model.Video.VidljivostVidea;

public class VideoDAO {
	public static Video get(long id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan FROM Video WHERE id=?;";

			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, id);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
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
	
	public static List<Video> getAll() {
		List<Video> videi = new ArrayList<Video>();

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id, naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan FROM Video;";

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
}
