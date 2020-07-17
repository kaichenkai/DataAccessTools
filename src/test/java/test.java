import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: kaichenkai
 * @create: 7/8/2020 12:53
 */
public class test {
    public static void main(String[] args) throws ParseException {
//        String a = String.valueOf(new Date().getTime());
//        System.out.println();
//
//        int b = 999999999;
//        System.out.println(b);
//
//        String accessProgress = String.format("%.2f%%", (float)98 / (float)106 * 100);
//        System.out.println(accessProgress);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");//毫秒
        Date dateTime = simpleDateFormat.parse("20200427110321158");
//        Date dateTime = simpleDateFormat.parse("20200427110321");
    }
}
