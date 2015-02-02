package in.workarounds.instimap.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {
    public static String getAmPmString(Date date){
        String format = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
