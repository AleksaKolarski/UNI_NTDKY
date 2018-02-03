package ntdky.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ntdky.dao.KomentarDAO;
import ntdky.dao.LajkDAO;
import ntdky.dao.VideoDAO;
import ntdky.model.Lajk.Tip;

public class Video {
	public enum VidljivostVidea{
		PUBLIC,
		UNLISTED,
		PRIVATE
	}
	
	private long id; // jedinstven
	private String naziv;
	private String putanjaVidea;
	private String putanjaSlike;
	private String opis;
	private VidljivostVidea vidljivostVidea;
	private boolean vidljivostKomentari;
	private boolean vidljivostRejting;
	private boolean blokiran;
	private long brojPregleda;
	private Date datum;
	private String vlasnik;
	private boolean obrisan;
	
	public Video() {
		
	}
	
	// bez id
	public Video(String naziv, String putanjaVidea, String putanjaSlike, String opis, VidljivostVidea vidljivostVidea, boolean vidljivostKomentari, boolean vidljivostRejting, boolean blokiran, long brojPregleda, Date datum, String vlasnik, boolean obrisan) {
		this.naziv = naziv;
		this.putanjaVidea = putanjaVidea;
		this.putanjaSlike = putanjaSlike;
		this.opis = opis;
		this.vidljivostVidea = vidljivostVidea;
		this.vidljivostKomentari = vidljivostKomentari;
		this.vidljivostRejting = vidljivostRejting;
		this.blokiran = blokiran;
		this.brojPregleda = brojPregleda;
		this.datum = datum;
		this.vlasnik = vlasnik;
		this.obrisan = obrisan;
	}
	
	// sa id
	public Video(long id, String naziv, String putanjaVidea, String putanjaSlike, String opis, VidljivostVidea vidljivostVidea, boolean vidljivostKomentari, boolean vidljivostRejting, boolean blokiran, long brojPregleda, Date datum, String vlasnik, boolean obrisan) {
		this.id = id;
		this.naziv = naziv;
		this.putanjaVidea = putanjaVidea;
		this.putanjaSlike = putanjaSlike;
		this.opis = opis;
		this.vidljivostVidea = vidljivostVidea;
		this.vidljivostKomentari = vidljivostKomentari;
		this.vidljivostRejting = vidljivostRejting;
		this.blokiran = blokiran;
		this.brojPregleda = brojPregleda;
		this.datum = datum;
		this.vlasnik = vlasnik;
		this.obrisan = obrisan;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public String getPutanjaVidea() {
		return putanjaVidea;
	}
	public void setPutanjaVidea(String putanjaVidea) {
		this.putanjaVidea = putanjaVidea;
	}
	
	public String getPutanjaSlike() {
		return putanjaSlike;
	}
	public void setPutanjaSlike(String putanjaSlike) {
		this.putanjaSlike = putanjaSlike;
	}
	
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public VidljivostVidea getVidljivostVidea() {
		return vidljivostVidea;
	}
	public void setVidljivostVidea(VidljivostVidea vidljivostVidea) {
		this.vidljivostVidea = vidljivostVidea;
	}
	
	public boolean getVidljivostKomentari() {
		return vidljivostKomentari;
	}
	public void setVidljivostKomentari(boolean vidljivostKomentari) {
		this.vidljivostKomentari = vidljivostKomentari;
	}
	
	public boolean getVidljivostRejting() {
		return vidljivostRejting;
	}
	public void setVidljivostRejting(boolean vidljivostRejting) {
		this.vidljivostRejting = vidljivostRejting;
	}
	
	public boolean getBlokiran() {
		return blokiran;
	}
	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}
	
	public long getBrojPregleda() {
		return brojPregleda;
	}
	public void incrementBrojPregleda() {
		brojPregleda++;
		VideoDAO.update(this);
	}

	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public String getVlasnik() {
		return vlasnik;
	}
	public void setVlasnik(Korisnik vlasnik) {
		this.vlasnik = vlasnik.getKorisnickoIme();
	}
	public void setVlasnik(String vlasnik) {
		this.vlasnik = vlasnik;
	}
	
	public boolean getObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	
	public static Video getById(long id) {
		// nadji video i vrati ga
		return new Video();
	}
	
	// get komentari
	public List<Komentar> getKomentari(){
		return KomentarDAO.getAllVideo(this.getId());
	}
	
	// get broj lajkova
	public List<Long> getBrojLajkova() {
		return LajkDAO.getLajkove(Tip.VIDEO, this.id);
	}
	
	public void lajkuj(Korisnik korisnik) {
		int provera = LajkDAO.checkLike(korisnik, Tip.VIDEO, this);
		if(provera == -1) {
			// napravi novi lajk;
			LajkDAO.add(new Lajk(korisnik.getKorisnickoIme(), true, new Date(), Tip.VIDEO, this.getId(), false));
		}
		else if(provera == 1) {
			Lajk lajk = LajkDAO.get(korisnik, Tip.VIDEO, this);
			lajk.setObrisan(true);
			LajkDAO.update(lajk);
		}
		else {
			// ozivi lajk
			Lajk lajk = LajkDAO.get(korisnik, Tip.VIDEO, this);
			lajk.setObrisan(false);
			lajk.setPozitivan(true);
			LajkDAO.update(lajk);
		}
	}
	
	public void dislajkuj(Korisnik korisnik) {
		int provera = LajkDAO.checkLike(korisnik, Tip.VIDEO, this);
		if(provera == -1) {
			// napravi novi dislajk
			LajkDAO.add(new Lajk(korisnik.getKorisnickoIme(), false, new Date(), Tip.VIDEO, this.getId(), false));
		}
		else if(provera == 2) {
			Lajk lajk = LajkDAO.get(korisnik, Tip.VIDEO, this);
			lajk.setObrisan(true);
			LajkDAO.update(lajk);
		}
		else{
			Lajk lajk = LajkDAO.get(korisnik, Tip.VIDEO, this);
			lajk.setObrisan(false);
			lajk.setPozitivan(false);
			LajkDAO.update(lajk);
		}
	}
	
	@Override
	public String toString() {
		return "'" + id + "' '" + naziv + "' '" + putanjaVidea + "' '" + putanjaSlike + "' '" + 
				opis + "' '" + vidljivostVidea + "' '" + vidljivostKomentari + "' '" + 
				vidljivostRejting +"' '" + blokiran + "' '" + brojPregleda + "' '" + 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datum) + "' '" + 
				vlasnik + "' '" + obrisan + "'";
	}
}
