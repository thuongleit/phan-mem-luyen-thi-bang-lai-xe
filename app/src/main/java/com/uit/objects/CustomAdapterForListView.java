package com.uit.objects;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterForListView extends ArrayAdapter<String>{
	private int posChecked = -1;//bị trí đáp án đa trả lời
	private int posRight = -1;//bị trí đáp án dung trong de thi
	private final Activity context;
	private final String[] names;
	
	public CustomAdapterForListView(Activity context, int lv, String[] names, int posChecked ) {
		super(context, lv, names);
		this.context = context;
		this.names = names;
		this.posChecked = posChecked;
	}

	public CustomAdapterForListView(Activity context, int lv, String[] names, int posChecked, int posRight) {
		super(context, lv, names);
		this.context = context;
		this.names = names;
		this.posChecked = posChecked;
		this.posRight = posRight;
	}
	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the assess to the individual fields of the row

		
		ViewHolder holder;
		
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(com.uit.R.layout.list_dap_an_cau_hoi, null, true);
			holder = new ViewHolder();
			
			holder.textView = (TextView) rowView.findViewById(com.uit.R.id.txtlistDapAn);
			holder.imageView = (ImageView)rowView.findViewById(com.uit.R.id.icon_dapan);
			if(position == 0){
				holder.imageView.setBackgroundResource(com.uit.R.drawable.a_icon);
			}
			else if(position == 1){
				holder.imageView.setBackgroundResource(com.uit.R.drawable.b_icon);
			}
			else if(position == 2){
				holder.imageView.setBackgroundResource(com.uit.R.drawable.c_icon);
			}
			else if(position == 3){
				holder.imageView.setBackgroundResource(com.uit.R.drawable.d_icon);
			}

			//thiet lap icon checked cho dap an tai vi tri posChecked neu da chon truoc do
			if(posChecked != -1 && (position == posChecked)){
				holder.imageView.setBackgroundResource(com.uit.R.drawable.thi_dachon);			
			}
			
			//thiet lap mau chu cho cau tra loi dung
			if(posRight != -1 && position == posRight){
				int color = Color.GREEN;
				holder.textView.setTextColor(color);
				holder.textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
			}
			
			
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.textView.setText(names[position]);
		
		return rowView;
	}
	
	
}
