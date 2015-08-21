package com.uit.objects;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.uit.Functions.ChonBoDe;
import com.uit.Providers.CauHoiDB;
import com.uit.Providers.DeThiRandom;

public class DeThi {

	/*
	 * Khai báo mảng chứa các câu hỏi theo phân loại câu hỏi 
	 */
	private int[] quitacgiaothong = new int[121]; //mảng chứa các câu hỏi theo thể loại qui tắc giao thông
	private int[] tocdovakhoangcach = new int[14]; //mảng chứa các câu hỏi theo thể loại tốc độ và khoảng cách
	private int[] nghiepvuvantai = new int[30]; //mảng chứa các câu hỏi theo thể loại nghiệp vụ vận tải
	private int[] daoducnguoilaixe = new int[10]; //mảng chứa các câu hỏi theo thể loại đạo đức người lái xe
	private int[] kythuatlaixe = new int[20]; //mảng chứa các câu hỏi theo thể loại kỹ thuật lái xe
	private int[] cautaosuachuaoto = new int[30]; //mảng chứa các câu hỏi theo thể loại cấu tạo sửa chữa ô tô
	private int[] bienbao = new int[100]; //mảng chứa các câu hỏi theo thể loại biển báo
	private int[] sahinh = new int[80]; //mảng chứa các câu hỏi theo thể loại sa hình
	
	/*
	 * Khai báo mảng số lần được chọn của các câu hỏi cho 1 user
	 */
	private int[] solanchoncauhoi = new int[405];	
	
	private int[] demquitacgiaothong = new int[121]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại qui tắc giao thông
	private int[] demtocdovakhoangcach = new int[14]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại tốc độ và khoảng cách
	private int[] demnghiepvuvantai = new int[30]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại nghiệp vụ vận tải
	private int[] demdaoducnguoilaixe = new int[10]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại đạo đức người lái xe
	private int[] demkythuatlaixe = new int[20]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại kỹ thuật lái xe
	private int[] demcautaosuachuaoto = new int[30]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại cấu tạo sửa chữa ô tô
	private int[] dembienbao = new int[100]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại biển báo
	private int[] demsahinh = new int[80]; //mảng chứa số lần xuất hiện của các câu hỏi theo thể loại sa hình
	
	
	public DeThi(){
		//Khởi tạo các mảng
		for(int i=0; i<405; i++){
			solanchoncauhoi[i] = 0;
			
			if(i<121)
			{
				quitacgiaothong[i] = i+1;
				demquitacgiaothong[i] = 0;
			}				
			else if(i >= 121 && i<135)
			{
				tocdovakhoangcach[i-121] = i+1;
				demtocdovakhoangcach[i-121] = 0;
			}
			else if(i >= 135 && i<165)
			{
				nghiepvuvantai[i-135] = i+1;
				demnghiepvuvantai[i-135] = 0;
			}
			else if(i >= 165 && i<175)
			{
				daoducnguoilaixe[i-165] = i+1;
				demdaoducnguoilaixe[i-165] = 0;
			}
			else if(i >= 175 && i<195)
			{
				kythuatlaixe[i-175] = i+1;
				demkythuatlaixe[i-175] = 0;
			}
			else if(i >= 195 && i<225)
			{
				cautaosuachuaoto[i-195] = i+1;
				demcautaosuachuaoto[i-195] = 0;
				
			}
			else if(i >= 225 && i<325)
			{
				bienbao[i-225] = i+1;
				dembienbao[i-225] = 0;
			}
			else if(i >= 325 && i<405)
			{
				sahinh[i-325] = i+1;
				demsahinh[i-325] = 0;
			}
		}		
		
		
	}
	
	//hàm select số lần được chọn của các câu hỏi từ bảng đề thi random, 
	//của user nào thì đếm câu hỏi của user đó.
	public void DemSoLanChonCauHoi(Context _context, int userId)
	{
		//select các câu hỏi đã xuất hiện ứng với user này trong bảng dethiramdon.
		DeThiRandom tableDethiRandom = new DeThiRandom(_context);
		tableDethiRandom.open();
		Cursor c= tableDethiRandom.getQuestionsByUserId(userId);
		if(c.moveToFirst()){
			do{
				//get idquestion
				int idQuestion = c.getInt(0);
				
				//gọi hàm kiểm tra id câu hỏi và tăng thêm 1 cho số lần chọn câu hỏi đó.
				solanchoncauhoi[idQuestion-1] += 1;
			}while(c.moveToNext());
		}
		
		
		tableDethiRandom.close();
	}
	
	//Hàm phân loại câu hỏi cùng với số lần xuất hiện của nó
	//input: dùng mảng chung solanchoncauhoi[]
	public void PhanLoai(Context _context, int userId) {
		//gọi hàm DemSoLanChonCauHoi để update mảng solanchoncauhoi[]
		DemSoLanChonCauHoi(_context, userId);
		
		//chia tách các câu hỏi theo từng loại và update vào các mảng phân loại o trên
		//từ câu 1 đến câu 121 là mảng quitacgiaothong, ....
		for(int i=0; i<405; i++){
			if(i<121){
				//cho vao mang qui tac giao thong
				demquitacgiaothong[i] = solanchoncauhoi[i];
			}
			else if(i >= 121 && i<135)
				demtocdovakhoangcach[i-121] = solanchoncauhoi[i];
			else if(i >= 135 && i<165)
				demnghiepvuvantai[i-135] = solanchoncauhoi[i];
			else if(i >= 165 && i<175)
				demdaoducnguoilaixe[i-165] = solanchoncauhoi[i];
			else if(i >= 175 && i<195)
				demkythuatlaixe[i-175] = solanchoncauhoi[i];
			else if(i >= 195 && i<225)
				demcautaosuachuaoto[i-195] = solanchoncauhoi[i];
			else if(i >= 225 && i<325)
				dembienbao[i-225] = solanchoncauhoi[i];
			else if(i >= 325 && i<405)
				demsahinh[i-325] = solanchoncauhoi[i];
		}
		
	}

	//các hàm trả về giá trị các mảng phân loại
	public int[] getDemQuiTacGiaoThong() {
		return demquitacgiaothong;
	}
	public int[] getDemTocDoKhoangCach() {
		return demtocdovakhoangcach;
	}
	public int[] getDemNghiepVuVanTai() {
		return demnghiepvuvantai;
	}
	public int[] getDemDaoDucNguoiLaiXe() {
		return demdaoducnguoilaixe;
	}
	public int[] getDemKyThuatLaiXe() {
		return demkythuatlaixe;
	}
	public int[] getDemCauTaoSuaChuaOTo() {
		return demcautaosuachuaoto;
	}
	public int[] getDemBienBao() {
		return dembienbao;
	}
	public int[] getDemSaHinh() {
		return demsahinh;
	}
	
	
	/**
	 * ham tra ve cac mang cau hoi phan loai
	 * @return
	 */
	public int[] getQuiTacGiaoThong() {
		return quitacgiaothong;
	}
	public int[] getTocDoKhoangCach() {
		return tocdovakhoangcach;
	}
	public int[] getNghiepVuVanTai() {
		return nghiepvuvantai;
	}
	public int[] getDaoDucNguoiLaiXe() {
		return daoducnguoilaixe;
	}
	public int[] getKyThuatLaiXe() {
		return kythuatlaixe;
	}
	public int[] getCauTaoSuaChuaOTo() {
		return cautaosuachuaoto;
	}
	public int[] getBienBao() {
		return bienbao;
	}
	public int[] getSaHinh() {
		return sahinh;
	}
	
	
	/**
	 * hàm xây dựng bảng dethirandom.
	 * @param _context
	 * @param userId
	 * @param bangcauhoi
	 * @throws SQLException
	 */
	public void insertQuestionsByUserId(Context _context, int userId, int[] bangcauhoi) throws SQLException{
		DeThiRandom dethi = new DeThiRandom(_context);
		dethi.open();
		
		//lấy về bộ đề mới nhất hiện có, để insert đề này là đề tiếp theo - chỉ áp dụng cho user này
		int idBodeHientai = dethi.getIdLastBodeByUserId(userId);
		
		
		for(int i=0; i<bangcauhoi.length; i++)
		{
			//lay dap an cua cau hoi tuong ung
			String[] dapanhinhanh = getDapanHinhAnhById(_context, bangcauhoi[i]);
			String dapan = dapanhinhanh[0];
			
			String hinhanh = null;
			if(dapanhinhanh[1] != null && !(dapanhinhanh[1]).equalsIgnoreCase("")){
				hinhanh = dapanhinhanh[1];
			}
			
			//insert tung cau co id vao bang dethirandom
			dethi.insertQuestionBelongUser(idBodeHientai + 1, userId, bangcauhoi[i], dapan, hinhanh);
		}
		dethi.close();
	}
	
	/**
	 * hàm trả về đáp án câu hỏi theo id câu hỏi
	 * @param _context
	 * @param idQuestion
	 * @return
	 */
	public String[] getDapanHinhAnhById(Context _context, int idQuestion){
		String result[] = new String[2];
		for(int i=0; i<2; i++){
			result[i] = null;
		}
		CauHoiDB ch = new CauHoiDB(_context);
		ch.open();
		Cursor dapan = ch.getAnswerAndImageById(idQuestion);
		Log.d("id cau hoi", ((Integer)idQuestion).toString());
		if(dapan.moveToFirst()){
			String strDA = dapan.getString(0);
			Log.d("dap an", strDA);
			result[0] = strDA;
			
			if( dapan.getString(1) != null && !(dapan.getString(1)).equalsIgnoreCase(""))
			{
					String strHA = dapan.getString(1);
					result[1] = strHA;							
			}
		}
		ch.close();		
		return result;
	}
	
	
	
	
	/**
	 * Hàm tạo đề thi và insert vào csdl với dữ kiện là các hàm đã xây dựng ở trên
	 * @param _context
	 * @param userId
	 */
	public void TaoBoDe(Context _context, int userId) {
		ChonBoDe bode = new ChonBoDe();
		
		//lay ve so lan da chon cua cac cau hoi
		this.PhanLoai(_context, userId);
		
		//random lay ra cac mang cau hoi da phan loai
		int[] chquitacgiaothong = new int[7];
		int[] chtocdokhoangcach = new int[1];
		int[] chnghiepvuvantai = new int[1];
		int[] chdaoducnguoilaixe = new int[1];
		int[] chkythuatlaixe = new int[1];
		int[] chcautaosuachuaoto = new int[1];
		int[] chbienbao = new int[10];
		int[] chsahinh = new int[8];
		
		chquitacgiaothong = bode.RandomBelongRate(quitacgiaothong, demquitacgiaothong, 7);
		chtocdokhoangcach = bode.RandomBelongRate(tocdovakhoangcach, demtocdovakhoangcach, 1);
		chnghiepvuvantai = bode.RandomBelongRate(nghiepvuvantai, demnghiepvuvantai, 1);
		chdaoducnguoilaixe = bode.RandomBelongRate(daoducnguoilaixe, demdaoducnguoilaixe, 1);
		chkythuatlaixe = bode.RandomBelongRate(kythuatlaixe, demkythuatlaixe, 1);
		chcautaosuachuaoto = bode.RandomBelongRate(cautaosuachuaoto, demcautaosuachuaoto, 1);
		chbienbao = bode.RandomBelongRate(bienbao, dembienbao, 10);
		chsahinh = bode.RandomBelongRate(sahinh, demsahinh, 8);
		
		//gop lai thanh mang cau hoi
		int[] finalQuestion = new int[30];
		for(int i=0; i<30; i++){
			if(i<7){
				finalQuestion[i] = chquitacgiaothong[i];
			}
			else if(i==7){
				finalQuestion[i] = chtocdokhoangcach[0];
			}
			else if(i == 8){
				finalQuestion[i] = chnghiepvuvantai[0];
			}
			else if(i==9){
				finalQuestion[i] = chdaoducnguoilaixe[0];
			}
			else if(i==10){
				finalQuestion[i] = chkythuatlaixe[0];
			}
			else if(i==11){
				finalQuestion[i] = chcautaosuachuaoto[0];
			}
			else if(i >=12 && i<22){
				finalQuestion[i] = chbienbao[i - 12];
			}
			else if(i >=22){
				finalQuestion[i] = chsahinh[i - 22];
			}
		}
		
		//chen vao csdl bang cau hoi vua tao
		this.insertQuestionsByUserId(_context, userId, finalQuestion);
		
		
		
	}
}
