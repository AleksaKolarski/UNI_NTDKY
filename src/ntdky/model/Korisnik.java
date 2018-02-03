package ntdky.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ntdky.dao.KorisnikDAO;

public class Korisnik {
	public enum TipKorisnika{
		USER,
		ADMIN
	}
	
	private String korisnickoIme; // jedinstveno
	private String lozinka;
	private String ime;
	private String prezime;
	private String email;
	private String opis;
	private Date datum;
	private TipKorisnika tipKorisnika;
	private boolean blokiran;
	private boolean obrisan;
	// pratioci (get za listu korisnika)
	// lajkovi/dislajkovi videa (get za listu lajkova/dislajkova)
	// lajkovi/dislajkovi komentara (get za listu lajkova/dislajkova)
	
	public Korisnik() {
		
	}
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String email, String opis, Date datum, TipKorisnika tipKorisnika, boolean blokiran, boolean obrisan) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.opis = opis;
		this.datum = datum; 
		this.tipKorisnika = tipKorisnika;
		this.blokiran = blokiran;
		this.obrisan = obrisan;
	}
	
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public Date getDatum() {
		return datum;
	}
	
	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}
	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}
	
	public boolean getBlokiran() {
		return blokiran;
	}
	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}
	
	public boolean getObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	
	public List<String> getPratioci(){
		// generisati listu pratioca
		return new ArrayList<String>();
	}
	
	/*
	public int getLajkVidea(){
		// generisati broj lajkova svih videa
		return 0;
	}
	public int getDislajkVidea(){
		// generisati broj dislajkova svih videa
		return 0;
	}
	
	public int getLajkKomentara(){
		// generisati broj lajkova svih videa
		return 0;
	}
	*/
	
	public static Korisnik getByKorisnickoIme(String korisnickoIme) {
		// prodji kroz sve korisnike i vrati korisnika
		return new Korisnik();
	}
	
	public static void pretplata(Korisnik ko, Korisnik koga) {
		if(KorisnikDAO.checkPretplata(ko, koga)) {
			KorisnikDAO.ponistiPretplatu(ko, koga);
		}
		else {
			KorisnikDAO.pretplati(ko, koga);
		}
	}
	
	@Override
	public String toString() {
		return "'" + korisnickoIme + "' '" + lozinka + "' '" + ime + "' '" + prezime + "' '" + 
				email + "' '" + opis + "' '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datum) + "' '" + 
				tipKorisnika.toString() + "' '" + blokiran + "' '" + obrisan + "'";
	}
}
