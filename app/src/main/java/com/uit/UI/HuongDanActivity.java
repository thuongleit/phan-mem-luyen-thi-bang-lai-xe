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


public class HuongDanActivity extends ActivityGroup {
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
			
			intent = new Intent(this, HuongDanLamBai.class);
			setupTab(intent, new TextView(this), "Cách làm bài", R.drawable.icon_meothi);

			intent = new Intent().setClass(this, MeoThi.class);
			setupTab(intent, new TextView(this), "Lái xe an toàn", R.drawable.icon_cachlambai);
			
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
