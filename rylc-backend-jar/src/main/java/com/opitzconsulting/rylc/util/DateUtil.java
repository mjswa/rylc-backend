package com.opitzconsulting.rylc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_PATTERN);

    public static String getDateAsString(Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date toDate(String startDate)  {
        try{
            return simpleDateFormat.parse(startDate);
        }
        catch(ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date currentDateOnMidnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
