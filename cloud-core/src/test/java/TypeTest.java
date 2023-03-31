import cn.hutool.core.io.IoUtil;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Young
 * @create 2023/1/20
 * @since 1.0.0
 */
public class TypeTest {

    @Test
    public void test1() throws FileNotFoundException {
        String path = "C:\\APP\\workspace\\普中51-单核-A2开发板资料\\2--手把手开发讲解视频\\7. LED闪烁\\LED闪烁.avi";
        String s = IoUtil.readHex28Upper(new FileInputStream(path));
        System.out.println(s);
        String a = "test111";
        String b = "test111";
        System.out.println(a == b);
    }


    @Test
    public void test2() {
        Integer.valueOf("12").intValue();

        Integer.parseInt("12");
    }
}