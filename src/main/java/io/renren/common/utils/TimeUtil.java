package io.renren.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private Long[] getTimeDifference(String strTime1,String strTime2) {
        //格式日期格式，在此我用的是"2018-01-24 19:49:50"这种格式
        //可以更改为自己使用的格式，例如：yyyy/MM/dd HH:mm:ss 。。。
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            Date now = df.parse(strTime1);
            Date date=df.parse(strTime2);
            Long l=now.getTime()-date.getTime();
            Long day=l/(24*60*60*1000);
            Long hour=(l/(60*60*1000)-day*24);
            Long min=((l/(60*1000))-day*24*60-hour*60);
            Long s=(l/1000-day*24*60*60-hour*60*60-min*60);
            Long[] shuzu={day,hour,min,s};
           return shuzu;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }
    public static Timestamp getTimestamp(long time){
        return new Timestamp(time);
    }

    public static Long stampToTime(Timestamp timestamp) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(timestamp+"");
        long ts = date.getTime();
        return ts;
    }



}
