package com.uit.UI;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.uit.R;
import com.uit.Functions.BienBaoAdapter;
import com.uit.Providers.BienBaoDB;
import com.uit.Providers.HashmapDB;
import com.uit.objects.BienBao;
import com.uit.objects.PopupMenuItem;

public class BienBaoGridview extends Activity {
	GridView gridview;
	ArrayList<BienBao> list_bienbao = new ArrayList<BienBao>();
	HashMap<String, Integer> map = null;
	Button btnMenu, btnChangeView;
	PopupMenuAction quickAction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bienbao_main);
		gridview = (GridView) findViewById(R.id.grid_bienbao);
		btnMenu = (Button) findViewById(R.id.grid_btnMenu);
		btnChangeView = (Button) findViewById(R.id.grid_btnChangeView);

		// get data from database
		//
		map = new HashmapDB().getMapBienBao();
		list_bienbao = new BienBaoDB(this).get_list_bienbao(BienBaoDB.ID_ALL);

		gridview.setAdapter(new BienBaoAdapter(0, list_bienbao.size() - 1,
				getLayoutInflater(), list_bienbao, map));

		quickAction = BienBaoSwitcher
				.createPopupMenuBB(getApplicationContext());
		setMenuAction();
		btnMenu.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				quickAction.show(v);
			}
		});
		btnChangeView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(BienBaoGridview.this,
						BienBaoSwitcher.class);
				startActivity(i);
				finish();
			}
		});
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView txtView = (TextView) view
						.findViewById(R.id.grid_item_tenBB);
				String tenbb = txtView.getText().toString();
				// Toast.makeText(getApplicationContext(), tenBB,
				// Toast.LENGTH_SHORT).show();
				BienBao bienbao = new BienBaoDB(getApplicationContext())
						.getBienBaoWithTen(tenbb);

				int actionId = position;
				String title = bienbao.getTenbb() + "\n\n"
						+ bienbao.getNoidung();
				// Drawable icon = getResources().getDrawable(
				// ((Integer) map.get(bienbao.getLink_anh())).intValue());
				PopupMenuItem itemShow = new PopupMenuItem(actionId, title);
				PopupMenuAction quickAction = new PopupMenuAction(
						getApplicationContext(), PopupMenuAction.VERTICAL);
				quickAction.addActionItem(itemShow);
				quickAction
						.setOnActionItemClickListener(new PopupMenuAction.OnActionItemClickListener() {

							public void onItemClick(PopupMenuAction source,
									int pos, int actionId) {

							}
						});
				quickAction.show(view);
			}
		});
	}

	private void setMenuAction() {
		// Set listener for action item clicked
		quickAction
				.setOnActionItemClickListener(new PopupMenuAction.OnActionItemClickListener() {
					public void onItemClick(PopupMenuAction source, int pos,
							int actionId) {
						// PopupMenuItem actionItem = quickAction
						// .getActionItem(pos);

						list_bienbao = new BienBaoDB(getApplicationContext())
								.get_list_bienbao(actionId);
						gridview.setAdapter(new BienBaoAdapter(0, list_bienbao
								.size() - 1, getLayoutInflater(), list_bienbao,
								map));
						// here we can filter which action item was clicked with
						// pos or actionId parameter
						String click = "";
						if (actionId == BienBaoDB.ID_ALL) {
							btnMenu.setText(BienBaoDB.STR_ALL);
							click = "Chọn xem tất cả";
						} else if (actionId == BienBaoDB.ID_CAM) {
							btnMenu.setText(BienBaoDB.STR_CAM);
							click = "Chọn xem biển báo cấm";
						} else if (actionId == BienBaoDB.ID_CHIDAN) {
							btnMenu.setText(BienBaoDB.STR_CHIDAN);
							click = "Chọn xem biển báo chỉ dẫn";
						} else if (actionId == BienBaoDB.ID_HIEULENH) {
							btnMenu.setText(BienBaoDB.STR_HIEULENH);
							click = "Chọn xem biển báo hiệu lệnh";
						} else if (actionId == BienBaoDB.ID_NGUYHIEM) {
							btnMenu.setText(BienBaoDB.STR_NGUYHIEM);
							click = "Chọn xem biển báo nguy hiểm";
						} else if (actionId == BienBaoDB.ID_PHU) {
							btnMenu.setText(BienBaoDB.STR_PHU);
							click = "Chọn xem biển báo phụ";
						} else {
							click = "Không chọn gì cả";
						}
						Toast.makeText(getApplicationContext(), click,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

}
