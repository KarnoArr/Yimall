import com.zackyj.Mmall.dao.UserMapper;
import com.zackyj.Mmall.model.pojo.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description
 * @Author zackyj
 * @Created IN PACKAGE_NAME ON 2021/7/26-周一.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DaoTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void Test1(){
        int count = userMapper.checkUsername("admin");
        System.out.println(count);
    }
}
