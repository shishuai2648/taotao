/**
 * Created by lenovo on 2018/4/3.
 */

import com.taotao.rest.compoent.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lenovo
 * @create 2018-04-03 13:49
 **/
public class JedisTest {

    @Test
    public void testJedisClientSpring(){
        // 创建一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        // 从容器中获得jedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("client2","1000");
        String result = jedisClient.get("client2");
        System.out.println(result);
    }
}
