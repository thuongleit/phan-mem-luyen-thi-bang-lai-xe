package com.uit.objects;

public class CauHoi {
	int id;
	String noidung;	
	String luachon1;
	String luachon2;
	String luachon3;
	String luachon4;
	String dapan;
	String hinhanh;
	int theloai;
	int solantraloisai;
	public CauHoi(int id, String noidung, String luachon1, String luachon2,
			String luachon3, String luachon4, String dapan, String hinhanh,
			int theloai, int solantraloisai) {
		super();
		this.id = id;
		this.noidung = noidung;
		this.luachon1 = luachon1;
		this.luachon2 = luachon2;
		this.luachon3 = luachon3;
		this.luachon4 = luachon4;
		this.dapan = dapan;
		this.hinhanh = hinhanh;
		this.theloai = theloai;
		this.solantraloisai = solantraloisai;
	}
	public CauHoi() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNoidung() {
		return noidung;
	}
	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}
	public String getLuachon1() {
		return luachon1;
	}
	public void setLuachon1(String luachon1) {
		this.luachon1 = luachon1;
	}
	public String getLuachon2() {
		return luachon2;
	}
	public void setLuachon2(String luachon2) {
		this.luachon2 = luachon2;
	}
	public String getLuachon3() {
		return luachon3;
	}
	public void setLuachon3(String luachon3) {
		this.luachon3 = luachon3;
	}
	public String getLuachon4() {
		return luachon4;
	}
	public void setLuachon4(String luachon4) {
		this.luachon4 = luachon4;
	}
	public String getDapan() {
		return dapan;
	}
	public void setDapan(String dapan) {
		this.dapan = dapan;
	}
	public String getHinhanh() {
		return hinhanh;
	}
	public void setHinhanh(String hinhanh) {
		this.hinhanh = hinhanh;
	}
	public int getTheloai() {
		return theloai;
	}
	public void setTheloai(int theloai) {
		this.theloai = theloai;
	}
	public int getSolantraloisai() {
		return solantraloisai;
	}
	public void setSolantraloisai(int solantraloisai) {
		this.solantraloisai = solantraloisai;
	}
}
