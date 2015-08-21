package com.uit.objects;

public class HuongDan {
	int id;
	String tieude;
	String tieudecon;
	String noidung;

	public HuongDan(int id, String tieude, String tieudecon, String noidung) {
		super();
		this.id = id;
		this.tieude = tieude;
		this.tieudecon = tieudecon;
		this.noidung = noidung;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTieude() {
		return tieude;
	}

	public void setTieude(String tieude) {
		this.tieude = tieude;
	}

	public String getTieudecon() {
		return tieudecon;
	}

	public void setTieudecon(String tieudecon) {
		this.tieudecon = tieudecon;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

}
