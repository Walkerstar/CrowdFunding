import com.mcw.scw.util.AppDateUtils;

import java.text.ParseException;

/**
 * @author mcw 2019\12\28 0028-15:13
 */
public class getDaysTest {
    public static void main(String[] args) throws ParseException {
        Long getdays = AppDateUtils.getdays("2019-12-25", 10);
        System.out.println(getdays);
    }
}
