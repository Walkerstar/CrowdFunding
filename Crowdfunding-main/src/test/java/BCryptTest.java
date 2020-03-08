import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author mcw 2019\12\9 0009-16:54
 */
public class BCryptTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

        String encode = pe.encode("123456");
        System.out.println(encode);
    }
}
