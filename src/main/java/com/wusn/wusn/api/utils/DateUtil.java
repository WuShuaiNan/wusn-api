package com.wusn.wusn.api.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public static List<String> getDatesBetween(Date startDate, Date endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate) || calendar.getTime().equals(endDate)) {
            Date currentDate = calendar.getTime();
            String currentDateStr = dateFormat.format(currentDate);
            dates.add(currentDateStr);
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }
}
