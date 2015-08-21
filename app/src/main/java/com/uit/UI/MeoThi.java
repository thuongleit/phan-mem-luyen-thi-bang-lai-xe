

package com.uit.UI;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uit.R;
import com.uit.Functions.LuatListAdapter;
import com.uit.Providers.HuongDanDB;
import com.uit.objects.HuongDan;

public class MeoThi extends Activity {

	TextView txtInfo;
	ListView list_view;
	ArrayList<HuongDan> list_huongdan = new ArrayList<HuongDan>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hocluat);
		list_huongdan = new HuongDanDB(this).get_all_huongdan();

		ArrayList<String> list_tieude = new ArrayList<String>();
		ArrayList<String> list_tieudecon = new ArrayList<String>();

		for (int i = 0; i < list_huongdan.size(); i++) {
			list_tieude.add(list_huongdan.get(i).getTieude());
			list_tieudecon.add(list_huongdan.get(i).getTieudecon());
		}

		txtInfo = (TextView) findViewById(R.id.luat_txtInfo);
		txtInfo.setText("Phía trước tay lái là sự sống!!!");
		list_view = (ListView) findViewById(R.id.luat_listview);
		LuatListAdapter adapter = new LuatListAdapter(this, list_tieude,
				list_tieudecon);
		list_view.setAdapter(adapter);

		list_view.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				createDialogInfo(list_huongdan.get(position));
			}
		});

	}

	private void createDialogInfo(HuongDan item) {
		String[] noidung = { item.getNoidung() };
		AlertDialog.Builder builder;
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_huongdan,
				(ViewGroup) findViewById(R.id.dialog_root));
		TextView txtTieude = (TextView) layout.findViewById(R.id.dialog_tieude);
		txtTieude.setText(item.getTieude());
		// set listview hiển thị những đáp án của câu hỏi
		ListView list = (ListView) layout.findViewById(R.id.dialog_listview);
		list.setAdapter(new ArrayAdapter<String>(this,
				R.layout.dialog_meothi, noidung));
		list.setItemsCanFocus(false);
		list.setBackgroundColor(Color.TRANSPARENT);
		ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.Theme2);
		builder = new AlertDialog.Builder(ctw);
		builder.setView(layout);

		builder.setPositiveButton("OK", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		final AlertDialog dialog = builder.create();
		dialog.show();
	}
}