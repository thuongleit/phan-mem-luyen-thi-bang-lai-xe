package com.uit.UI;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uit.R;
import com.uit.Functions.LuatListAdapter;
import com.uit.Providers.LuatDB;
import com.uit.objects.Luat;

/**
 * Demonstrates expandable lists using a custom {@link ExpandableListAdapter}
 * from {@link BaseExpandableListAdapter}.
 */
public class HocLuatActivity_Chuong extends Activity {

	public static final String ID_CHUONG = "chuong";
	TextView txtInfo;
	ListView list_noidung;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hocluat);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> contents = new ArrayList<String>();
		ArrayList<Luat> list_luat = new ArrayList<Luat>();
		list_luat = new LuatDB(this).getChuong();
		for (int i = 0; i < list_luat.size(); i++) {
			names.add("Chương " + list_luat.get(i).getChuong());
			contents.add(list_luat.get(i).getNoidung());
		}
		txtInfo = (TextView) findViewById(R.id.luat_txtInfo);
		list_noidung = (ListView) findViewById(R.id.luat_listview);
		LuatListAdapter adapter = new LuatListAdapter(this, names, contents);
		list_noidung.setAdapter(adapter);
		
		list_noidung.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(HocLuatActivity_Chuong.this, HocLuatActivity_Dieu.class);
				i.putExtra(ID_CHUONG, position + 1);
				startActivity(i);
			}
		});
	}
}
