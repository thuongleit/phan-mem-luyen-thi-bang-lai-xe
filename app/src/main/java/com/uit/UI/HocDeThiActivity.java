package com.uit.UI;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.uit.R;


public class HocDeThiActivity extends ActivityGroup {
		public static final String LOAI_CAUHOI = "loai";
		public static final String CAUHOI_LUAT = "luat";
		public static final String CAUHOI_BIENBAO = "bienbao";
		public static final String CAUHOI_TINHHUONG = "tinhhuong";
		public static final String CAUHOI_ALL = "all";
		private TabHost mTabHost;
		

		private void setupTabHost() {
			mTabHost = (TabHost) findViewById(android.R.id.tabhost);
			mTabHost.setup(this.getLocalActivityManager());
		}

		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_thongke);

			setupTabHost();
			//mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);

			Intent intent;
			
			intent = new Intent(this, CauHoiActivity.class);
			intent.putExtra(LOAI_CAUHOI, CAUHOI_LUAT);
			setupTab(intent, new TextView(this), "Câu hỏi luật", R.drawable.cauhoi_icon_luat);

			intent = new Intent().setClass(this, CauHoiActivity.class);
			intent.putExtra(LOAI_CAUHOI, CAUHOI_BIENBAO);
			setupTab(intent, new TextView(this), "Biển báo", R.drawable.cauhoi_icon_bienbao);
			
			intent = new Intent().setClass(this, CauHoiActivity.class);
			intent.putExtra(LOAI_CAUHOI, CAUHOI_TINHHUONG);
			setupTab(intent, new TextView(this), "Tình huống", R.drawable.cauhoi_icon_tinhhuong);
			
		}
		private void setupTab(Intent intent, final View view, final String tag, final int icon) {
			View tabview = createTabView(mTabHost.getContext(), tag, icon);

			TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);		
			mTabHost.addTab(setContent);

		}

		private static View createTabView(final Context context, final String text, final int icon) {
			View view = LayoutInflater.from(context).inflate(R.layout.activity_thongke_tab, null);
			TextView tv = (TextView) view.findViewById(R.id.tabsText);
			ImageView img = (ImageView) view.findViewById(R.id.tabsIcon);
			tv.setText(text);
			img.setImageResource(icon);
			return view;
		}
	}
