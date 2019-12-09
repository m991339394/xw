package io.renren.common.utils;

/**
 * @ClassName CornUtil
 * @Description TODO
 * @Author jgl
 * @Date 2019/9/19 10:44
 * @Version 1.0
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CronUtil {

    private static final String TRANS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String TRANS_CRON_FORMAT_ONCE = "ss mm HH dd MM ? yyyy";

    private static final String TRANS_CRON_FORMAT_DAY = "ss mm HH dd/ * ? *";

    private static final String TRANS_CRON_FORMAT_WEEK = "ss mm HH ? * -- *";

    private static final String TRANS_CRON_FORMAT_MONTH = "ss mm HH dd MM/1 ? *";
    
    private static final Long DELAY_SECONDS = 60*1000L;
    
    /**
     * 生成只执行一次的cron
     */
    public static String getCronByOnce() throws ParseException {
    	Long nowTime=System.currentTimeMillis();
    	Long executeTime=(nowTime+DELAY_SECONDS);
    	String dateStr= TimeUtil.getTimestamp(executeTime).toString();
        SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
        Date parse = format.parse(dateStr);
        return fmtDateToStr(parse, TRANS_CRON_FORMAT_ONCE);
    }
    
    /**
     * 生成只执行一次的cron
     */
    public static String getCronByOnce(Long seconds) throws ParseException {
    	String dateStr=TimeUtil.getTimestamp(seconds).toString();
    	SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
    	Date parse = format.parse(dateStr);
    	return fmtDateToStr(parse, TRANS_CRON_FORMAT_ONCE);
    }
    
    /**
     * 生成只执行一次的cron
     */
    public static String getCronByOnce(String dateStr) throws ParseException {
    	SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
    	Date parse = format.parse(dateStr);
    	return fmtDateToStr(parse, TRANS_CRON_FORMAT_ONCE);
    }

    /**
     * 生成每月或每周或每天执行的cron
     */
    public static String getCron(String period, String startDateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
        Date startDate = format.parse(startDateStr);
        StringBuffer cronStringBuffer = new StringBuffer();
        if ("month".equals(period)) {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_MONTH).trim();
            cronStringBuffer.append(startDateCron);
        } else if ("day".equals(period)) {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_DAY).trim();
            // StringBuffer stringBuffer = new StringBuffer(str);
            // stringBuffer.insert(stringBuffer.lastIndexOf("/") + 1, period);
            // cron = stringBuffer.toString().trim();
            // createPeriod(arrStart, arrEnd, 4);
            cronStringBuffer.append(startDateCron).insert(cronStringBuffer.lastIndexOf("/") + 1, "1");
        } else {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_WEEK).trim();
            cronStringBuffer.append(startDateCron.replaceAll("--", period));
        }
        return cronStringBuffer.toString();
    }


    public static String getCronToDate(String cron) {
        String format = null;
        StringBuffer stringBuffer = new StringBuffer(cron);
        int lastIndexOf = stringBuffer.lastIndexOf("/");
        stringBuffer.deleteCharAt(lastIndexOf);
        stringBuffer.deleteCharAt(lastIndexOf);
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date = sdf.parse(stringBuffer.toString());
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format = sdf.format(date);
        } catch (Exception e) {
            return null;
        }
        return format;
    }
    
    
    /***
    *
    * @param date 时间
    * @return  cron类型的日期
    */
   public static String getCron(final Date  date){
       SimpleDateFormat sdf = new SimpleDateFormat(TRANS_CRON_FORMAT_ONCE);
       String formatTimeStr = "";
       if (date != null) {
           formatTimeStr = sdf.format(date);
       }
       return formatTimeStr;
   }

    
    /***
    *
    * @param cron Quartz cron的类型的日期
    * @return  Date日期
    */

   public static Date getDate(final String cron) {


       if(cron == null) {
           return null;
       }

       SimpleDateFormat sdf = new SimpleDateFormat(TRANS_CRON_FORMAT_ONCE);
       Date date = null;
       try {
           date = sdf.parse(cron);
       } catch (ParseException e) {
           return null;// 此处缺少异常处理,自己根据需要添加
       }
       return date;
   }
    

    /**
     * 格式转换 日期-字符串
     */
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null){
            return "";
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
