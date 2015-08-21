package com.uit.objects;

public class Luat {

	private int id;
	private int chuong;
	private int capdo;

	public Luat() {
	}

	public Luat(int id, int chuong, int capdo, String noidung) {
		super();
		this.id = id;
		this.chuong = chuong;
		this.capdo = capdo;
		this.noidung = noidung;
	}

	public int getId() {
		return id;
	}

	public int getChuong() {
		return chuong;
	}

	public int getCapdo() {
		return capdo;
	}

	public String getNoidung() {
		return noidung;
	}

	private String noidung = "";

}
