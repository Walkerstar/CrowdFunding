package com.mcw.scw.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 获取格式化好的当前时间
 */
public class AppDateUtils {

	public static String getFormatTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = format.format(new Date());
		return string;
	}

	public static String getFormatTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String string = format.format(new Date());
		return string;
	}

	public static String getFormatTime(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String string = format.format(date);
		return string;
	}

	//计算剩余天数
	public static Long getdays(String deploydate,Integer day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = format.parse(deploydate);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE,day);
        Date endTime = calendar.getTime();

        Date nowTime = new Date();
        Long diff=endTime.getTime()-nowTime.getTime();
//        System.out.println ("Days: " + diff / 1000 / 60 / 60 / 24);
        long convert = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        if(convert<0){
            convert=0;
        }
        return convert;
    }

}
