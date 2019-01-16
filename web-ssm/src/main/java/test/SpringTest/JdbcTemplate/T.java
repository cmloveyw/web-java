package test.SpringTest.JdbcTemplate;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @version 1.0
 * @类名: T.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-10
 */
public class T {
    public static void main(String[] args) {
        /*String str = "2019-01-11 15:42:59";
        //str = str + " 00:00:00";
        Timestamp timestamp = null;
        timestamp = Timestamp.valueOf(str);
        System.out.println(timestamp);*/

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
//要转换字符串 str_test
        String str_test = "2011-04-24";
        try {
            Timestamp ts = new Timestamp(format.parse(str_test).getTime());
            System.out.println(ts.toString());
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
