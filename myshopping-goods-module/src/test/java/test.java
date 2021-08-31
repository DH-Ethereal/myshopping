import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: myshopping-project
 * @description:
 * @author: hjc
 * @create: 2021-08-29 16:00
 **/
public class test {

    @Test
    public void getPwd(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));;
    }
}
