package com.cics.rmis;

import com.cics.rmis.controller.UserController;
import com.cics.rmis.model.TUser;
import com.cics.rmis.repository.TUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void testUserController() throws Exception {
        RequestBuilder request;
        //1、get查一下user列表，应该为空
        request = get("/User/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
        // 2、post提交一个user
        request = post("/User/")
                .param("id","1")
                .param("name","测试大师")
                .param("age", "20");

        mvc.perform(request)
                .andExpect((content().string(equalTo("success"))));

        // 3、get获取user列表，应该有刚才插入的数据
        request = get("/User/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));
        // 4、put修改id为1的user
        request = put("/User/1")
                .param("name","测试终极大师")
                .param("age", "30");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));
        // 5、get一个id为1的user
        request = get("/User/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));

        // 6、del删除id为1的user
        request = delete("/User/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));
    }

    @Autowired
    private TUserRepository userRepository;

    @Test
    public void testDBUserOpt() throws Exception {
        List list = userRepository.findAll();
        TUser userObj = new TUser();
        userObj.setName("王五");
        userObj.setAge(33);
        userRepository.save(userObj);
        Assert.assertEquals(3, userRepository.findAll().size());
    }
}
