package com.burhan.arch.room.dbutils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Burhanuddin on 9/10/2017.
 */

public class Utils {
    public static String getDateOfBirth(Date userDob) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(userDob);
        return String.format("%d/%d/%d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
        );
    }
}
