import com.sun.jersey.core.impl.provider.entity.XMLRootObjectProvider;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        Object jedisPool = context.getBean("jedisPool");
        System.out.println(jedisPool);
    }
}
