package com.uit.Functions;

import java.util.Date;

public class NgayThang {
	//change from long to Date 
	public Date FromLongToDate(long time)
	{
		Date result = new Date();
		result.setTime(time);
		return result;
	}
	
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public String[] FromLongToMinSec(int time){
		String[] result = new String[2];//array of min and sec
		int second = (time/1000) % 60;
		int min = (time/1000)/60;
		
		result[0] = ((Integer)min).toString();
		result[1] = ((Integer)second).toString();
		return result;
	}
}
