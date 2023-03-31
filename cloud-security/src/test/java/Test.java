import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Young
 * @create 2022/11/13
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("toyong"));
    }

}