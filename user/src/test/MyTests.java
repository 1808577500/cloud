import com.zcx.user.UserApplication;
import com.zcx.user.entity.SysUser;
import com.zcx.user.service.SysUserService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApplication.class})// 指定启动类
@Slf4j
public class MyTests {
    @Autowired
    SysUserService sysUserService;

    /**
     * 使用断言
     */
    @Test
    public void test2() {
        log.info("test hello 2");
        TestCase.assertEquals(1, 1);
    }


    /**
     * 测试注入
     */
    @Test
    public void test3() {
        ExecutorService pool = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 20; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setUsername("ZCX10");
            sysUser.setPassword("PWD");
            sysUser.setCreateTime(new Date());
            sysUser.setCreateUser(i);
            pool.execute(()-> {
                int insert = sysUserService.insertUpdate(sysUser);
                System.out.println(insert);
                log.info("insert:{}", insert);
            });
        }
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Before
    public void testBefore() {
        System.out.println("before");
    }

    @After
    public void testAfter() {
        System.out.println("after");
    }
}
