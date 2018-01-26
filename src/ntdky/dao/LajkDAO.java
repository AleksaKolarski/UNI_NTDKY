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

import ntdky.model.Lajk;
import ntdky.model.Lajk.Tip;

public class LajkDAO {
	public static Lajk get(long id) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT vlasnik, pozitivan, datum, tip, video, komentar, obrisan FROM Lajk WHERE id=?;";

			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, id);
			System.out.println(pstmt);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String vlasnik = rset.getString(index++);
				boolean pozitivan = rset.getBoolean(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				Tip tip = Tip.valueOf(rset.getString(index++));
				long video = 0;
				long komentar = 0;
				switch(tip) {
				case VIDEO:
					video = rset.getLong(index++);
					index++;
					break;
				case KOMENTAR:
					index++;
					komentar = rset.getLong(index++);
					break;
				}
				boolean obrisan = rset.getBoolean(index++);

				return new Lajk(id, vlasnik, pozitivan, datum, tip, (tip == Tip.VIDEO)? video:komentar, obrisan);
			} else {
				System.out.println("Greska pri pronalazenju lajka sa id '" + id + "' u bazi.");
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
	
	public static List<Lajk> getAll() {
		List<Lajk> lajkovi = new ArrayList<Lajk>();

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id, vlasnik, pozitivan, datum, tip, video, komentar, obrisan FROM Lajk;";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				long id = rset.getLong(index++);
				String vlasnik = rset.getString(index++);
				boolean pozitivan = rset.getBoolean(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				Tip tip = Tip.valueOf(rset.getString(index++));
				long video = 0;
				long komentar = 0;
				switch(tip) {
				case VIDEO:
					video = rset.getLong(index++);
					index++;
					break;
				case KOMENTAR:
					index++;
					komentar = rset.getLong(index++);
					break;
				}
				boolean obrisan = rset.getBoolean(index++);

				lajkovi.add(new Lajk(id, vlasnik, pozitivan, datum, tip, (tip == Tip.VIDEO)? video:komentar, obrisan));
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return lajkovi;
	}
	
	public static boolean update(Lajk lajk) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Lajk SET vlasnik=?, pozitivan=?, datum=?, tip=?, "+ ((lajk.getTip() == Tip.VIDEO)?"video=?":"komentar=?") + ", obrisan=? WHERE id=?;";
			pstmt = conn.prepareStatement(query);

			int index = 1;
			pstmt.setString(index++, lajk.getVlasnik());
			pstmt.setBoolean(index++, lajk.getPozitivan());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lajk.getDatum()));
			pstmt.setString(index++, lajk.getTip().toString());
			pstmt.setLong(index++, lajk.getMeta());
			pstmt.setBoolean(index++, lajk.getObrisan());
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
	
	public static boolean add(Lajk lajk) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, " + ((lajk.getTip() == Tip.VIDEO)?"video":"komentar") + ", obrisan) VALUES (?, ?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, lajk.getVlasnik());
			pstmt.setBoolean(index++, lajk.getPozitivan());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lajk.getDatum()));
			pstmt.setString(index++, lajk.getTip().toString());
			pstmt.setLong(index++, lajk.getMeta());
			pstmt.setBoolean(index++, lajk.getObrisan());
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
	
	public static boolean delete(Lajk lajk) {
		System.out.println("Brisanje lajka '" + lajk.getId() + "'");
		if(lajk.getObrisan() == false) {
			lajk.setObrisan(true);
			return update(lajk);
		}
		System.out.println("Lajk je vec obrisan.");
		return false;
	}
}
