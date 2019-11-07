package com.appcloud.vm.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CompareDate {
	public static void main(String args[]) {
		Date date = new Date();
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		System.out.println(d.format(date));
		//System.out.println(date.getTime());
		Date nowdate = new Date();
		int i= compare_date(nowdate, "2016-08-12T16:00Z");
		return;
	}
	
	/**
	 * 比较是否即将过期
	 * @param DATE1   现在时间
	 * @param DATE2   过期时间
	 * @return 1即将过期的    0不是即将过期的 
	 */
	public static int compare_date(Date date1, String DATE2) {
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    	df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
        	Date dt2;
        	if(DATE2.contains("Z")){
        		dt2 = df2.parse(DATE2);
        	}else{
        		dt2 = df.parse(DATE2);
        	}
            if (date1.getTime() > (dt2.getTime()-7 * 24 * 60 * 60 * 1000)) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
