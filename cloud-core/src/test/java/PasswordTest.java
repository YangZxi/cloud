import cn.hutool.core.util.RandomUtil;
import cn.xiaosm.cloud.Application;
import com.qcloud.cos.utils.Md5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(classes = Application.class)
public class PasswordTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void test() {
        String encode = bCryptPasswordEncoder.encode("123123");
        System.out.println(encode);
    }

    @Test
    public void test2() {
        String domain = "http://tencent.cdn.xiaosm.cn";
        String file = "/2023-07/7a5dcbbaaaf0e6c7e616ce01a4aa3efb.png";
        String timestamp = System.currentTimeMillis() / 1000 + "";
        String rand = RandomUtil.randomString(10);
        // 文件路径-timestamp-rand-uid-自定义密钥 的MD5值
        String md5Hex = Md5Utils.md5Hex(file + "-" + timestamp + "-" + rand + "-" + "0" + "-" + "自定义密钥");
        // http://DomainName/Filename?sign=timestamp-rand-uid-md5hash
        String url = domain + file + "?sign=" + timestamp + "-" + rand + "-" + "0" + "-" + md5Hex;
        System.out.println(url);


        String md5Hex1 = Md5Utils.md5Hex(file + "-1689163148-q0Lb4mKnomtbaEkN8Mb-0-" + "自定义密钥");
        // http://DomainName/Filename?sign=timestamp-rand-uid-md5hash
        System.out.println(md5Hex1);
    }
}
