package com.cics.rmis.controller;

import com.cics.rmis.bean.RespBean;
import com.cics.rmis.model.TUser;
import com.cics.rmis.repository.TUserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/UserController")
@RestController
public class UserController {
    // 创建线程安全的Map
    static Map<Integer, TUser> users = Collections.synchronizedMap(new HashMap<Integer, TUser>());

    @ApiOperation(value = "获取用户列表")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<TUser> getUserList(){
        List<TUser> userList = new ArrayList<TUser>(users.values());
        return userList;
    }

    @ApiOperation(value = "创建用户", notes = "根据用户对象创建")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "TUser")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute TUser user){
        users.put(user.getId(),user);
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TUser getUser(@PathVariable Integer id){
        TUser user = users.get(id);
        return user;
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "TUser")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Integer id, @ModelAttribute TUser user){
        TUser userObj = users.get(id);
        userObj.setName(user.getName());
        userObj.setAge(user.getAge());
        users.put(id, userObj);
        return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/{id}")
    public String deleteUser(@PathVariable Integer id){
        users.remove(id);
        return "success";
    }

    @Autowired
    private TUserRepository userRepository;

    @RequestMapping("/getAllUsers")
    public Page getUsersList() {
        int page=0,size=3;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<TUser> pageData = userRepository.findAll(pageable);
        return pageData;
    }

    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(name = "user", value = "用户详细信息实体")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Transactional
    public RespBean register(@ModelAttribute TUser user) {
        // 对用户密码加密
        if(user.getUsername()!=null){
            TUser existUser = this.userRepository.findByUsername(user.getUsername());
            if(existUser != null){
                return RespBean.error("用户已存在！");
            }
        }else{
            return RespBean.error("注册失败");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        this.userRepository.save(user);
        return RespBean.ok("注册成功");
    }
}
