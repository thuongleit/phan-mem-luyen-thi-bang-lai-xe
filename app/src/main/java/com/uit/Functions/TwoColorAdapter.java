package com.uit.Functions;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uit.R;

/**
 * Class nham muc dich tao adapter cho listview Listview setAdapter nay, item se
 * chia thanh 2 mau chan le khac nhau
 * 
 * @author Thuong
 * 
 */
public class TwoColorAdapter extends ArrayAdapter<String> {
	protected final Activity context;
	protected final String[] names;

	public TwoColorAdapter(Activity context, int layoutId, String[] names) {
		super(context, layoutId, names);
		this.context = context;
		this.names = names;
	}

	// class tinh
	// khoi tao 2 TextView dung de luu tru thong tin
	protected static class ViewHolder {
		protected TextView txtName;
		protected TextView txtContent;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout

		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.activity_thongke_content, null,
					true);
			holder = new ViewHolder();
			holder.txtName = (TextView) rowView.findViewById(R.id.t_txtThongKe);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.txtName.setText(names[position]);
		//set mau cua item theo thu tu chan, le
		if (position % 2 == 0) {
			holder.txtName.setTextColor(Color.parseColor("#000033"));
		} else {
			holder.txtName.setTextColor(Color.parseColor("#CC0000"));
		}
		return rowView;
	}
}
