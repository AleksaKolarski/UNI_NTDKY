package ntdky.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Komentar {
	
	private long id;
	private String sadrzaj;
	private Date datum;
	private String vlasnik;
	private long video;
	private boolean obrisan;
	
	public Komentar() {
		
	}
	
	public Komentar(String sadrzaj, Date datum, String vlasnik, long video, boolean obrisan) {
		this.sadrzaj = sadrzaj;
		this.datum = datum;
		this.vlasnik = vlasnik;
		this.video = video;
		this.obrisan = obrisan;
	}
	
	public Komentar(long id, String sadrzaj, Date datum, String vlasnik, long video, boolean obrisan) {
		this.id = id;
		this.sadrzaj = sadrzaj;
		this.datum = datum;
		this.vlasnik = vlasnik;
		this.video = video;
		this.obrisan = obrisan;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSadrzaj() {
		return sadrzaj;
	}
	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
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
	
	public long getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video.getId();
	}
	public void setVideo(long videoId) {
		this.video = videoId;
	}
	
	public boolean getObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	
	@Override
	public String toString() {
		return "'" + id + "' '" + sadrzaj + "' '" + 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datum) + 
				"' '" + vlasnik + "' '" + video + "' '" + obrisan + "'";
	}
}
