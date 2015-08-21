package com.uit.UI;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.uit.R;
import com.uit.Providers.BienBaoDB;
import com.uit.Providers.HashmapDB;
import com.uit.objects.BienBao;
import com.uit.objects.PopupMenuItem;

public class BienBaoSwitcher extends Activity implements
		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {
	PopupMenuAction quickAction;
	HashMap<String, Integer> map = null;
	ArrayList<BienBao> list_bienbao;
	TextView txtTenBB, txtNoidung;
	Button btnMenu, btnChangeView;
	Gallery g;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_bienbao_switcher);

		map = new HashmapDB().getMapBienBao();
		list_bienbao = new BienBaoDB(this).get_list_bienbao(BienBaoDB.ID_ALL);

		txtTenBB = (TextView) findViewById(R.id.switch_tenbb);
		txtNoidung = (TextView) findViewById(R.id.switch_noidung);
		btnMenu = (Button) findViewById(R.id.switch_btnMenu);
		btnChangeView = (Button) findViewById(R.id.switch_btnChangeView);
		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this, 0, list_bienbao.size() - 1,
				list_bienbao, map));
		g.setOnItemSelectedListener(this);

		quickAction = createPopupMenuBB(getApplicationContext());
		setMenuAction();
		btnMenu.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				quickAction.show(v);
				// quickAction.setAnimStyle(QuickAction.ANIM_REFLECT);
			}
		});

		btnChangeView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(BienBaoSwitcher.this,
						BienBaoGridview.class);
				startActivity(i);
				finish();
			}
		});
	}

	public void onItemSelected(AdapterView parent, View v, int position, long id) {
		BienBao localBienBao = (BienBao) this.list_bienbao.get(position);
		mSwitcher.setImageResource(((Integer) this.map
				.get(localBienBao.getLink_anh())).intValue());
		txtTenBB.setText(localBienBao.getTenbb());
		txtNoidung.setText(localBienBao.getNoidung());
	}

	public void onNothingSelected(AdapterView parent) {

	}

	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	private ImageSwitcher mSwitcher;

	public class ImageAdapter extends BaseAdapter {
		Context context;
		int start;
		int end;
		ArrayList<BienBao> list_bienbao;
		HashMap<String, Integer> map = null;

		public ImageAdapter(Context c, int start, int end,
				ArrayList<BienBao> list_bienbao, HashMap<String, Integer> map) {
			super();
			this.context = c;
			this.start = start;
			this.end = end;
			this.list_bienbao = list_bienbao;
			this.map = map;
		}

		public int getCount() {
			return 1 + (this.end - this.start);
		}

		public Object getItem(int paramInt) {
			return this.list_bienbao.get(paramInt + this.start);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView image = new ImageView(context);
			BienBao localBienBao = (BienBao) list_bienbao.get(position
					+ this.start);
			image.setImageResource(((Integer) map.get(localBienBao.getLink_anh()))
					.intValue());
			image.setAdjustViewBounds(true);
			image.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			// i.setBackgroundResource(R.drawable.picture_frame);
			return image;
		}
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
						g.setAdapter(new ImageAdapter(getApplicationContext(),
								0, list_bienbao.size() - 1, list_bienbao, map));
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
							click = "Chọn xembiển báo chỉ dẫn";
						} else if (actionId == BienBaoDB.ID_HIEULENH) {
							btnMenu.setText(BienBaoDB.STR_HIEULENH);
							click = "Chọn xem biển báo hiệu lệnh";
						} else if (actionId == BienBaoDB.ID_NGUYHIEM) {
							btnMenu.setText(BienBaoDB.STR_NGUYHIEM);
							click = "Chọn xem biển báo nguy hiểm";
						} 
						else if (actionId == BienBaoDB.ID_PHU) {
							btnMenu.setText(BienBaoDB.STR_PHU);
							click = "Chọn xem biển báo phụ";
						}else {
							click = "Không chọn gì cả";
						}
						Toast.makeText(getApplicationContext(), click,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	public static PopupMenuAction createPopupMenuBB(Context context) {
		PopupMenuAction quickAction;
		quickAction = new PopupMenuAction(context, PopupMenuAction.VERTICAL);
		PopupMenuItem bienAll = new PopupMenuItem(BienBaoDB.ID_ALL,
				BienBaoDB.STR_ALL, context.getResources().getDrawable(
						R.drawable.grid_menu_all));
		PopupMenuItem bienCam = new PopupMenuItem(BienBaoDB.ID_CAM,
				BienBaoDB.STR_CAM, context.getResources().getDrawable(
						R.drawable.grid_menu_cam));
		PopupMenuItem bienNguyHiem = new PopupMenuItem(BienBaoDB.ID_NGUYHIEM,
				BienBaoDB.STR_NGUYHIEM, context.getResources().getDrawable(
						R.drawable.grid_menu_nguyhiem));
		PopupMenuItem bienChiDan = new PopupMenuItem(BienBaoDB.ID_CHIDAN,
				BienBaoDB.STR_CHIDAN, context.getResources().getDrawable(
						R.drawable.grid_menu_chidan));
		PopupMenuItem bienHieuLenh = new PopupMenuItem(BienBaoDB.ID_HIEULENH,
				BienBaoDB.STR_HIEULENH, context.getResources().getDrawable(
						R.drawable.grid_menu_hieulenh));
		PopupMenuItem bienphu = new PopupMenuItem(BienBaoDB.ID_PHU,
				BienBaoDB.STR_PHU, context.getResources().getDrawable(
						R.drawable.grid_menu_phu));
		

		// // use setSticky(true) to disable QuickAction dialog being dismissed
		// // after an item is clicked
		// prevItem.setSticky(true);
		// nextItem.setSticky(true);

		// create QuickAction. Use QuickAction.VERTICAL or
		// QuickAction.HORIZONTAL param to define layout
		// orientation
		

		// add action items into QuickAction
		quickAction.addActionItem(bienAll);
		quickAction.addActionItem(bienCam);
		quickAction.addActionItem(bienNguyHiem);
		quickAction.addActionItem(bienChiDan);
		quickAction.addActionItem(bienHieuLenh);
		quickAction.addActionItem(bienphu);
		
		return quickAction;
	}
}
