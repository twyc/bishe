package hunnuse.wyc.Server;

import hunnuse.wyc.dao.UserMapper;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.User;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserService
 * @Description: 用户服务
 * @author: tycw
 * @date: 2020/4/27  12:52
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WebGeoNameMapper webGeoNameMapper;
    public void updateChart(User user,String chart){
        userMapper.deleteByPrimaryKey(user.getId());
        System.out.println("user之前的chart是"+user.getPersonal());
        user.setPersonal(chart);
        userMapper.insert(user);
        System.out.println("更新chart成功 更新之后的chart是"+chart);
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
