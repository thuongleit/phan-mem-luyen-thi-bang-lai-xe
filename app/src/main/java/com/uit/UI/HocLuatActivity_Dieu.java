package com.uit.UI;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.uit.R;
import com.uit.Functions.LuatListAdapter;
import com.uit.Providers.LuatDB;
import com.uit.objects.Luat;

public class HocLuatActivity_Dieu extends Activity {

	TextView txtInfo;
	ListView list_noidung;
	ArrayList<Luat> list_luat = new ArrayList<Luat>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hocluat);
		
		//lay du lieu tu activity truoc do
		int chuong = getIntent().getExtras().getInt(
				HocLuatActivity_Chuong.ID_CHUONG);
		// Log.d("check", "" + chuong);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> contents = new ArrayList<String>();

		list_luat = new LuatDB(this).getDieu(chuong, LuatDB.ID_DIEU);

		for (int i = 0; i < list_luat.size(); i++) {
			names.add(list_luat.get(i).getNoidung());
		}

		//tạo dialog
		txtInfo = (TextView) findViewById(R.id.luat_txtInfo);
		txtInfo.setText("Chương " + chuong);
		list_noidung = (ListView) findViewById(R.id.luat_listview);
		LuatListAdapter adapter = new LuatListAdapter(this, names, contents);
		list_noidung.setAdapter(adapter);

		list_noidung.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Luat muc = new LuatDB(getApplicationContext())
						.getLuatwithId(list_luat.get(position).getId() + 1);
				
				//tạo dialog hiển thị
				createDialogInfo(list_luat.get(position).getNoidung(),
						muc.getNoidung());
			}
		});
	}

	private void createDialogInfo(String dieu, String _noidung) {
		String[] noidung = { _noidung };
		AlertDialog.Builder builder;
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_luat,
				(ViewGroup) findViewById(R.id.dialog_root));
		// TextView nội dung câu hỏi
		TextView txtTieude = (TextView) layout
				.findViewById(R.id.dialog_content);

		txtTieude.setText(dieu);

		// set listview hiển thị những đáp án của câu hỏi
		ListView list = (ListView) layout.findViewById(R.id.dialog_listview);
		list.setAdapter(new ArrayAdapter<String>(this,
				R.layout.dialog_listview, noidung));
		list.setItemsCanFocus(false);
		ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyTheme);
		builder = new AlertDialog.Builder(ctw);
		builder.setView(layout);

		final Button btnOK = (Button) layout.findViewById(R.id.dialog_btnOK);
		final AlertDialog dialog = builder.create();
		btnOK.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				dialog.dismiss();
			}
		});
		builder.setPositiveButton("OK", new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.getWindow().setLayout(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.show();
	}
}
