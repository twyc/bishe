package hunnuse.wyc.controller;

import hunnuse.wyc.Server.Recommend;
import hunnuse.wyc.dao.TravelogueMapper;
import hunnuse.wyc.dao.WebGeoNameMapper;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.dataobject.WebGeoName;
import hunnuse.wyc.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RouteController
 * @Description: 景点Controller 实现route.html里面的推荐功能
 * @author: tycw
 * @date: 2020/4/17  13:31
 */
@Controller
@RequestMapping(value = "/route",method = {RequestMethod.GET})
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
@ResponseBody
public class RouteController {
    @Autowired
    private TravelogueMapper travelogueMapper;
    @Autowired
    private WebGeoNameMapper webGeoNameMapper;

    /**
     * 根据request里面的参数 返回路线和游记url
     * @return 这一篇游记的实例 解析放到前端完成
     */

    @RequestMapping(value = "/getRoute",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getRoute(@RequestParam(name = "attsWant")String attsWant){
        Recommend recommend = new Recommend();
        List<Travelogue> yjList=travelogueMapper.listTravelogue();
        System.out.println("游记的数量"+yjList.size());
        List<Travelogue> list = recommend.getRecommendRoute(attsWant,yjList);
        System.out.println("符合要求的路线数量"+list.size());
        if(list.size()>10){
            list = list.subList(0,10);
        }
        return CommonReturnType.create(list);
    }
}
