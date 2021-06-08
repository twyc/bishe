package hunnuse.wyc.Server;

import hunnuse.wyc.controller.UserController;
import hunnuse.wyc.dao.UserMapper;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.User;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.request.UserLoginRequest;
import hunnuse.wyc.response.CommonReturnType;
import hunnuse.wyc.response.UserLoginResponse;
import hunnuse.wyc.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserService
 * @Description: 用户服务
 * @author: tycw
 * @date: 2020/4/27  12:52
 */
@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WebGeoNameMapper webGeoNameMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SnowFlake snowFlake;

    public void updateChart(User user,String chart){
        userMapper.deleteByPrimaryKey(user.getId());
        System.out.println("user之前的chart是"+user.getPersonal());
        user.setPersonal(chart);
        userMapper.insert(user);
        System.out.println("更新chart成功 更新之后的chart是"+chart);
    }
    public CommonReturnType login(UserLoginRequest userLoginRequest) {
        User user = userMapper.selectByName(userLoginRequest.getUserName());
        if (user == null){
            return CommonReturnType.create(null,"dont exist");
        }
        if (user.getPassword().equals(userLoginRequest.getPassword())) {
            Long token = snowFlake.nextId();
            LOG.info("生成单点登录token：{}，并放入redis中", token);
            redisTemplate.opsForValue().set(token.toString(), user.toString(), 3600 * 24, TimeUnit.SECONDS);
            UserLoginResponse response = new UserLoginResponse();
            response.setUserName(user.getName());//考虑登录用的用户名和返回用的昵称不一样
            response.setToken(token);
            response.setId(user.getId());
            return CommonReturnType.create(response);
        }
        return CommonReturnType.create(null,"failed");
    }

    public List listUser(){
        return userMapper.listUser();
    }
    public CommonReturnType delUser(User user){
        userMapper.deleteByPrimaryKey(user.getId());
        return CommonReturnType.create(null);
    }
    public String getChartFromAttsWant(String attsWant){
        String atts[] = attsWant.split("\\s+");
        boolean chart1,chart2,chart3;
        chart1=chart2=chart3=false;
        for (String att:atts) {
            List<WebGeoName>attractions = webGeoNameMapper.selectByName(att);
            if (attractions==null){
                System.out.println("like查询为空");
                continue;
            }
            WebGeoName attraction = attractions.get(0);
            String chart=attraction.getKeywords();
            chart1|=(chart.charAt(0)=='1');
            chart2|=(chart.charAt(1)=='1');
            chart3|=(chart.charAt(2)=='1');
        }
        String ret="";
        if (chart1){
            ret+="1";
        }else{
            ret+="0";
        }
        if (chart2){
            ret+="1";
        }else{
            ret+="0";
        }
        if (chart3){
            ret+="1";
        }else{
            ret+="0";
        }
        return ret;
    }
}
