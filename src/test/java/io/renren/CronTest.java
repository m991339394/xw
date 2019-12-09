package io.renren;

import io.renren.common.utils.CronUtil;

/**
 * @ClassName CronTest
 * @Description TODO
 * @Author jgl
 * @Date 2019/9/19 10:46
 * @Version 1.0
 */
public class CronTest {
    public static void main(String[] args) throws Exception {
//        String result = CronUtil.getCron("day", "2018-08-11 12:11:00");
//       String result = CronUtil.getCron("MON", "2018-08-11 12:11:00");
         String result = CronUtil.getCronByOnce("2017-05-29 12:10:02");
//       String result = CronUtil.getCron("month", "2019-01-01 12:00:00");
        // String result = CronUtil.getCronToDate("12 12 12 01 01/1 ? 2018");

        System.out.println(result);
    }
}
