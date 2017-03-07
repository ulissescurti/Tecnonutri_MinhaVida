package br.com.soulskyye.tecnonutri.util;

/**
 * Created by ulissescurti on 3/7/17.
 */

public class DateUtils {

    public static String getFormattedDate(String date){// yyyy-MM-dd
        String[] splittedDate = date.split("-");
        return splittedDate[2]+"/"+splittedDate[1]+"/"+splittedDate[0];
    }
}
