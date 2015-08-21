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
//Activity cua Giao dien Thong Ke
//gom 2 tab
public class ThongKeActivity extends ActivityGroup  {
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
		
		intent = new Intent(this, XepLoaiActivity.class);
		setupTab(intent, new TextView(this), "Xếp loại", R.drawable.t_icon_top);

		intent = new Intent().setClass(this, CauHoiHaySai.class);
		setupTab(intent, new TextView(this), "Câu hỏi hay sai", R.drawable.t_icon_error);
		
	}
	/**
	 * Khoi tao Tab content
	 * @param intent
	 * @param view
	 * @param tag tieu de hien thi
	 * @param icon icon hieu thi
	 */
	private void setupTab(Intent intent, final View view, final String tag, final int icon) {
		View tabview = createTabView(mTabHost.getContext(), tag, icon);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);		
		mTabHost.addTab(setContent);

	}
	/**
	 * Khoi tao tab view
	 * @param context 
	 * @param text tieu de se hien thi
	 * @param icon icon se hien thi
	 * @return view duoc khoi tao
	 */
	private static View createTabView(final Context context, final String text, final int icon) {
		View view = LayoutInflater.from(context).inflate(R.layout.activity_thongke_tab, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		ImageView img = (ImageView) view.findViewById(R.id.tabsIcon);
		tv.setText(text);
		img.setImageResource(icon);
		return view;
	}
}