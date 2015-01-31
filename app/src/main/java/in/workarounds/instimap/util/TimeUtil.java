package in.workarounds.instimap.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by manidesto on 31/01/15.
 */
public class TimeUtil {
    public static String getAmPmString(Date date){
        String format = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
