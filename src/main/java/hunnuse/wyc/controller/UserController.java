package hunnuse.wyc.controller;

import hunnuse.wyc.Server.AttractionService;
import hunnuse.wyc.Server.UserService;
import hunnuse.wyc.dao.UserMapper;
import hunnuse.wyc.dataobject.User;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.springframework.util.DigestUtils.md5Digest;

/**
 * @ClassName: UserController
 * @Description: User的操作
 * @author: tycw
 * @date: 2020/4/21  17:28
 */
@Controller
@RequestMapping(value = "/user")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
@ResponseBody
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private AttractionService attractionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "userName")String userName,
                                     @RequestParam(name = "password")String password,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="education")Integer education,
                                     @RequestParam(name="hobby")String hobby) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (userMapper.selectByName(userName)!=null){
            return CommonReturnType.create(null,"failed");
        }
        User user = new User();
        user.setGender(new Byte(String.valueOf(gender.intValue())));
        user.setName(userName);
        user.setPassword(EncodeByMd5(password));
        user.setAge(age);
        user.setEducation(education);
        user.setHobby(hobby);
        userMapper.insertSelective(user);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "userName")String userName,
                                     @RequestParam(name = "password")String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (userMapper.selectByName(userName)==null){
            return CommonReturnType.create(null,"dont exist");
        }
        User user = userMapper.selectByName(userName);
        if (user.getPassword().equals(password)) {
            // TODO 改为将登录状态token保存在Redis中
            this.httpServletRequest.getSession().setMaxInactiveInterval(30 * 60);//s
            this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
            this.httpServletRequest.getSession().setAttribute("LOGIN_USER",user);
            return CommonReturnType.create(null);
        }
        return CommonReturnType.create(null,"failed");
    }

    @RequestMapping(value = "/adminLogin",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType adminLogin(@RequestParam(name = "userName")String userName,
                                  @RequestParam(name = "password")String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(userName.equals("admin")&&password.equals("admin")){
            httpServletRequest.getSession().setMaxInactiveInterval(30 * 60);//s
            httpServletRequest.getSession().setAttribute("adminLogin",true);
            return CommonReturnType.create(null);
        }
        return CommonReturnType.create(null,"failed");
    }
    @RequestMapping("/getAdmin")
    @ResponseBody
    public CommonReturnType getAdmin(){
        Boolean flag = (Boolean) this.httpServletRequest.getSession().getAttribute("adminLogin");
        if (flag==null||flag==false){
            return CommonReturnType.create(null,"false");
        }
        return CommonReturnType.create(null,"true");
    }
    @RequestMapping("/delUser")
    @ResponseBody
    public CommonReturnType delUser(@RequestParam(name = "id") Integer index){
        List<User> list = userService.listUser();
        return userService.delUser(list.get(index));
    }
    @RequestMapping("/listUser")
    @ResponseBody
    public CommonReturnType listUser(){
        Boolean flag=(Boolean) httpServletRequest.getSession().getAttribute("adminLogin");
        if(flag==null||!flag){
            return CommonReturnType.create("not login");
        }
        List<User> list = userService.listUser();
        return CommonReturnType.create(list.toArray());
    }

    /**
     * 进index.html的时候判断登录态 如果尚未登录 返回not login
     * 否则返回存在session里的user
     * @return
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public CommonReturnType getUser(){
        Boolean flag = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (flag==null||flag==false){
            return CommonReturnType.create(null,"not login");
        }
        User user = (User) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        return CommonReturnType.create(user);
    }

    /**
     * 根据第一次登陆用户选择的标签
     * 更新user的标签 修改其在数据库中的值
     * 返回推荐的内容
     * @return
     */
    @RequestMapping(value = "/updateUser",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType updateUser(@RequestParam(name = "chart") String chart){
        User user = (User) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if (user==null){
            return CommonReturnType.create(null,"session expired");
        }
        userMapper.deleteByPrimaryKey(user.getId());
        user.setPersonal(chart);
        userMapper.insert(user);
        httpServletRequest.getSession().setAttribute("LOGIN_USER",user);
        /**
         *  根据chart这三个字节返回完全匹配的景点 如果不够就random
         */
        List<WebGeoName>list = attractionService.getByChart(chart);
        return CommonReturnType.create(list.toArray());
    }

    /**
     * 根据用户在系统中的使用情况
     * 更新user的标签 修改其在数据库中的值
     * 返回推荐的内容
     * @return
     */
    @RequestMapping(value = "/updateChart",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType updateChart(@RequestParam(name = "chart") String chart){
        User user = (User) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if (user==null){
            return CommonReturnType.create(null,"session expired");
        }
        userService.updateChart(user,chart);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",user);
        return CommonReturnType.create("更新chart成功");
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/updateChartByAttsWant",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType updateChartByAttsWant(@RequestParam(name = "attsWant")String attsWant){
        User user = (User) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if (user==null){
            return CommonReturnType.create(null,"session expired");
        }
        String chart = userService.getChartFromAttsWant(attsWant);
        if (chart.equals("000")){
            return CommonReturnType.create("更新chart失败");
        }
        userService.updateChart(user,chart);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",user);
        return CommonReturnType.create("更新chart成功");
    }
    public String EncodeByMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64.Encoder base64Encoder = Base64.getEncoder();
        //加密字符串
        String newstr = base64Encoder.encodeToString(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
