package com.uit.Functions;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uit.R;
import com.uit.objects.BienBao;

public class BienBaoAdapter extends BaseAdapter {
	Context c;
	int start;
	int end;
	ImageView image;
	LayoutInflater li;
	ArrayList<BienBao> list_bienbao;
	HashMap<String, Integer> map = null;
	TextView ten_bienbao;

	/**
	 * Constructor of BienBaoAdapter
	 * 
	 * @param start
	 * @param end
	 * @param li
	 * @param list_bienbao
	 * @param map
	 */
	public BienBaoAdapter(int start, int end, LayoutInflater li,
			ArrayList<BienBao> list_bienbao, HashMap<String, Integer> map) {
		super();
		this.start = start;
		this.end = end;
		this.li = li;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		BienBao localItemBienbao = (BienBao) this.list_bienbao.get(paramInt
				+ this.start);
		View localView = this.li.inflate(R.layout.activity_bienbao_gridview,
				paramViewGroup, false);
		this.image = ((ImageView) localView
				.findViewById(R.id.grid_item_hinhanh));
		this.ten_bienbao = ((TextView) localView
				.findViewById(R.id.grid_item_tenBB));
		this.ten_bienbao.setText(localItemBienbao.getTenbb());
		this.image.setImageResource(((Integer)this.map.get(localItemBienbao.getLink_anh())).intValue());
		return localView;
	}
}