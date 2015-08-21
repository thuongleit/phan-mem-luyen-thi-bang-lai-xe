package com.uit.UI;

import com.uit.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HuongDanLamBai extends Activity {

	TextView lblHuongDan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huongdanlambai);

		lblHuongDan = (TextView) findViewById(R.id.lblHuongDan);

		String huongdan = "Bài thi gồm 30 câu hỏi được random theo đúng cấu trúc khung của đề thi lý thuyết lái xe ô tô ngoài thực tế.  "
				+ "Gồm : \n"
				+ "-	 7 câu qui tắc giao thông.\n"
				+ "-	 1 câu về tốc độ và khoảng cách.\n"
				+ "-	 1 câu về nghiệp vụ vận tải.\n"
				+ "-	 1 câu về đạo đức người lái xe.\n"
				+ "-	 1 câu về kỹ thuật lái xe.\n"
				+ "-	 1 câu về cấu tạo sửa chữa ô tô.\n"
				+ "-	 10 câu về biển báo.\n"
				+ "-	 8 câu về sa hình.\n"
				+ "Thời gian thi là 20 phút, sẽ được đếm ngược trở về 0. "
				+ "Khi hết giờ chương trình tự động khóa và nộp bài thi. "
				+ "Thí sinh có thể nộp bài thi khi đã hoàn thành bằng cách click vào button finish (khi lá cờ đích xuất hiện),"
				+ "hoặc button “back” trên thiết bị. Hệ thống sẽ xác nhận lựa chọn của bạn và thực thi "
				+ "lưu bài thi. Sau khi thi sẽ cho phép thí sinh xem điểm thi và thời gian làm bài của mình.\n"
				+ "Chúc các bạn thi tốt!\n";
		lblHuongDan.setText(huongdan);
	}
}