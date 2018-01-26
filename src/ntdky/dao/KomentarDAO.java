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

import ntdky.model.Komentar;

public class KomentarDAO {
	public static Komentar get(long id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT sadrzaj, datum, vlasnik, video, obrisan FROM Komentar WHERE id=?;";

			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, id);
			System.out.println(pstmt);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String sadrzaj = rset.getString(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				String vlasnik = rset.getString(index++);
				long video = rset.getLong(index++);
				boolean obrisan = rset.getBoolean(index++);
				
				return new Komentar(id, sadrzaj, datum, vlasnik, video, obrisan);
			} else {
				System.out.println("Greska pri pronalazenju komentara sa id '" + id + "' u bazi.");
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
	
	public static List<Komentar> getAll() {
		List<Komentar> komentari = new ArrayList<Komentar>();

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id, sadrzaj, datum, vlasnik, video, obrisan FROM Komentar;";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				long id = rset.getLong(index++);
				String sadrzaj = rset.getString(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				String vlasnik = rset.getString(index++);
				long video = rset.getLong(index++);
				boolean obrisan = rset.getBoolean(index++);

				komentari.add(new Komentar(id, sadrzaj, datum, vlasnik, video, obrisan));
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return komentari;
	}
	
	public static boolean update(Komentar komentar) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Komentar SET sadrzaj=?, datum=?, vlasnik=?, video=?, obrisan=?  WHERE id=?;";
			pstmt = conn.prepareStatement(query);

			int index = 1;
			pstmt.setString(index++, komentar.getSadrzaj());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(komentar.getDatum()));
			pstmt.setString(index++, komentar.getVlasnik());
			pstmt.setLong(index++, komentar.getVideo());
			pstmt.setBoolean(index++, komentar.getObrisan());
			pstmt.setLong(index++, komentar.getId());
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean add(Komentar komentar) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) VALUES (?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, komentar.getSadrzaj());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(komentar.getDatum()));
			pstmt.setString(index++, komentar.getVlasnik());
			pstmt.setLong(index++, komentar.getVideo());
			pstmt.setBoolean(index++, komentar.getObrisan());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public static boolean delete(Komentar komentar) {
		System.out.println("Brisanje komentara '" + komentar.getId() + "'");
		if(komentar.getObrisan() == false) {
			komentar.setObrisan(true);
			return update(komentar);
		}
		System.out.println("Kometntar je vec obrisan.");
		return false;
	}
}
