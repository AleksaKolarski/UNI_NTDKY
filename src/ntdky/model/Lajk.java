package ntdky.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lajk {
	public enum Tip{
		VIDEO,
		KOMENTAR
	}
	
	private long id;
	private String vlasnik;
	private boolean pozitivan;
	private Date datum;
	private Tip tip;
	private long meta; // id komentara/videa u zavisnosti od tipa
	private boolean obrisan;
	
	public Lajk() {
		
	}
	
	public Lajk(String vlasnik, boolean pozitivan, Date datum, Tip tip, long meta, boolean obrisan) {
		this.vlasnik = vlasnik;
		this.pozitivan = pozitivan;
		this.datum = datum;
		this.tip = tip;
		this.meta = meta;
		this.obrisan = obrisan;
	}
	
	public Lajk(long id, String vlasnik, boolean pozitivan, Date datum, Tip tip, long meta, boolean obrisan) {
		this.id = id;
		this.vlasnik = vlasnik;
		this.pozitivan = pozitivan;
		this.datum = datum;
		this.tip = tip;
		this.meta = meta;
		this.obrisan = obrisan;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean getPozitivan() {
		return pozitivan;
	}
	public void setPozitivan(boolean pozitivan) {
		this.pozitivan = pozitivan;
	}
	
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public Tip getTip() {
		return tip;
	}
	public void setTip(Tip tip) {
		this.tip = tip;
	}
	
	public long getMeta() {
		return meta;
	}
	public void setMeta(long meta) {
		this.meta = meta;
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
	
	@Override
	public String toString() {
		return "'" + id + "' '" + vlasnik + "' '" + pozitivan + "' '" + 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datum) + "' '" + 
				tip.toString() + "' '" + meta + "' '" + 
				obrisan + "'";
	}
}
