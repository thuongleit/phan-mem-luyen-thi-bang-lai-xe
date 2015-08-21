package com.uit.objects;

public class BienBao {
	private int id;
	private String link_anh = "";
	private String tenbb = "";
	private String noidung = "";
	private String phanloai = "";

	public BienBao() {
	}

	public BienBao(int id, String link_anh, String tenbb, String noidung,
			String phanloai) {
		super();
		this.id = id;
		this.link_anh = link_anh;
		this.tenbb = tenbb;
		this.noidung = noidung;
		this.phanloai = phanloai;
	}

	public int getId() {
		return id;
	}

	public String getLink_anh() {
		return link_anh;
	}

	public String getTenbb() {
		return tenbb;
	}

	public String getNoidung() {
		return noidung;
	}

	public String getPhanloai() {
		return phanloai;
	}
}