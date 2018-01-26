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

import ntdky.model.Korisnik;
import ntdky.model.Korisnik.TipKorisnika;

public class KorisnikDAO {

	public static Korisnik get(String korisnickoIme) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran, obrisan FROM Korisnik WHERE korisnickoIme=?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, korisnickoIme);
			System.out.println(pstmt);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String lozinka = rset.getString(index++);
				String ime = rset.getString(index++);
				String prezime = rset.getString(index++);
				String email = rset.getString(index++);
				String opis = rset.getString(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				TipKorisnika tip = TipKorisnika.valueOf(rset.getString(index++));
				boolean blokiran = rset.getBoolean(index++);
				boolean obrisan = rset.getBoolean(index++);

				return new Korisnik(korisnickoIme, lozinka, ime, prezime, email, opis, datum, tip, blokiran, obrisan);
			} else {
				System.out.println("Greska pri pronalazenju korisnika '" + korisnickoIme + "' u bazi.");
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

	public static List<Korisnik> getAll() {
		List<Korisnik> korisnici = new ArrayList<Korisnik>();

		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT korisnickoIme, lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran, obrisan FROM Korisnik;";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				String korisnickoIme = rset.getString(index++);
				String lozinka = rset.getString(index++);
				String ime = rset.getString(index++);
				String prezime = rset.getString(index++);
				String email = rset.getString(index++);
				String opis = rset.getString(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				TipKorisnika tip = TipKorisnika.valueOf(rset.getString(index++));
				boolean blokiran = rset.getBoolean(index++);
				boolean obrisan = rset.getBoolean(index++);

				korisnici.add(new Korisnik(korisnickoIme, lozinka, ime, prezime, email, opis, datum, tip, blokiran, obrisan));
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return korisnici;
	}

	public static boolean update(Korisnik korisnik) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Korisnik SET lozinka=?, ime=?, prezime=?, email=?, opis=?, datum=?, tipKorisnika=?, blokiran=?, obrisan=? WHERE korisnickoIme=?;";
			pstmt = conn.prepareStatement(query);

			int index = 1;
			pstmt.setString(index++, korisnik.getLozinka());
			pstmt.setString(index++, korisnik.getIme());
			pstmt.setString(index++, korisnik.getPrezime());
			pstmt.setString(index++, korisnik.getEmail());
			pstmt.setString(index++, korisnik.getOpis());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(korisnik.getDatum()));
			pstmt.setString(index++, korisnik.getTipKorisnika().toString());
			pstmt.setBoolean(index++, korisnik.getBlokiran());
			pstmt.setBoolean(index++, korisnik.getObrisan());
			pstmt.setString(index++, korisnik.getKorisnickoIme());
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
	
	public static boolean add(Korisnik korisnik) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran, obrisan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, korisnik.getKorisnickoIme());
			pstmt.setString(index++, korisnik.getLozinka());
			pstmt.setString(index++, korisnik.getIme());
			pstmt.setString(index++, korisnik.getPrezime());
			pstmt.setString(index++, korisnik.getEmail());
			pstmt.setString(index++, korisnik.getOpis());
			pstmt.setString(index++, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(korisnik.getDatum()));
			pstmt.setString(index++, korisnik.getTipKorisnika().toString());
			pstmt.setBoolean(index++, korisnik.getBlokiran());
			pstmt.setBoolean(index++, korisnik.getObrisan());
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
	
	public static boolean delete(Korisnik korisnik) {
		System.out.println("Brisanje korisnika '" + korisnik.getKorisnickoIme() + "'");
		if(korisnik.getObrisan() == false) {
			korisnik.setObrisan(true);
			return update(korisnik);
		}
		System.out.println("Korisnik je vec obrisan.");
		return false;
	}
	
	// PRATIOCI
	public static List<Korisnik> getPretplate(Korisnik korisnik){
		/* Vraca listu korisnika na koje je korisnik pretplacen */
		
		List<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT koga FROM Pretplata WHERE ko=?;";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, korisnik.getKorisnickoIme());
			rset = pstmt.executeQuery();
			System.out.println(pstmt);
			
			while (rset.next()) {
				String koga = rset.getString(1);

				korisnici.add(get(koga));
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return korisnici;
	}
	public static List<Korisnik> getPretplacene(Korisnik korisnik){
		/* Vraca listu korisnika koji su pretplaceni na korisnika */
		List<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT ko FROM Pretplata WHERE koga=?;";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, korisnik.getKorisnickoIme());
			rset = pstmt.executeQuery();
			System.out.println(pstmt);
			
			while (rset.next()) {
				String ko = rset.getString(1);

				korisnici.add(get(ko));
			}
		} catch (SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return korisnici;
	}
	
	public static List<Korisnik> getPopularne(int broj){
		/* Vraca sortiranu listu najpopularnijih kanala */
		List<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM Korisnik ORDER BY (SELECT count(*) FROM Pretplata WHERE koga = korisnickoIme) DESC LIMIT ?;";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, broj);
			rset = pstmt.executeQuery();
			System.out.println(pstmt);
			
			while (rset.next()) {
				int index = 1;
				String korisnickoIme = rset.getString(index++);
				String lozinka = rset.getString(index++);
				String ime = rset.getString(index++);
				String prezime = rset.getString(index++);
				String email = rset.getString(index++);
				String opis = rset.getString(index++);
				Date datum;
				try {
					datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rset.getString(index++));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				TipKorisnika tip = TipKorisnika.valueOf(rset.getString(index++));
				boolean blokiran = rset.getBoolean(index++);
				boolean obrisan = rset.getBoolean(index++);

				korisnici.add(new Korisnik(korisnickoIme, lozinka, ime, prezime, email, opis, datum, tip, blokiran, obrisan));
			}
			
		} catch(SQLException e) {
			System.out.println("Greska u SQL upitu: ");
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		
		return korisnici;
	}
}
