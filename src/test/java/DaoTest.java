import com.zackyj.Mmall.common.CommonResponse;
import com.zackyj.Mmall.common.Constant;
import com.zackyj.Mmall.dao.CategoryMapper;
import com.zackyj.Mmall.dao.UserMapper;
import com.zackyj.Mmall.model.pojo.Category;
import com.zackyj.Mmall.model.pojo.User;

import com.zackyj.Mmall.service.IProductService;
import com.zackyj.Mmall.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @Description
 * @Author zackyj
 * @Created IN PACKAGE_NAME ON 2021/7/26-周一.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DaoTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    IProductService productService;
    @Autowired
    IUserService userService;

    @Test
    public void Test1() {
        int count = userMapper.checkUsername("admin");
        System.out.println(count);
    }

    @Test
    public void testCategory() {
        List<Category> categories = categoryMapper.selectCategoryChildrenByParentId(100001);
        System.out.println(categories);
    }

    @Test
    public void checkAnswer() {
        int n = userMapper.checkAnswer("zackyj", "是", "是");
        System.out.println(n);
    }

    @Test
    public void testProductList() {
        CommonResponse listForUser = productService.getListForUser(100002, "手机", "price", "desc", 1, 10);
        System.out.println(listForUser);
    }

    @Test
    public void testCheck() {
        userService.checkValid("zackyj", Constant.NO_USERNAME);
        //int n = userMapper.checkUsername("zackyj");
        //System.out.println(n);
    }
}
