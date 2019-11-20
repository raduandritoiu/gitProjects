package com.bigct.appint.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sdragon on 2/27/2016.
 */
public class Util {
    public static int getDaysBetween(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 1000;

        long t1 = date1.getTime();
        long t2 = date2.getTime();

        long t = (t1 - t2);
        long diffDays = t / (24 * 60 * 60 * 1000);
        return (int)diffDays;
    }
}
