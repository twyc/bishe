package hunnuse.wyc.controller;

import hunnuse.wyc.Server.AttractionService;
import hunnuse.wyc.Server.RouteService;
import hunnuse.wyc.Server.UserService;
import hunnuse.wyc.dataobject.Travelogue;
import hunnuse.wyc.response.CommonReturnType;
import hunnuse.wyc.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.min;

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
    private RouteService routeService;
    @Autowired
    private AttractionService attractionService;
    @Autowired
    private UserService userService;

    @RequestMapping("/insertRoute2DB")
    public String insertRoute2DB() throws IOException {
        routeService.insert2DB(attractionService.listAttractions());
        return "插入成功";
    }


    /**
     * 根据request里面的参数 返回路线和游记url
     * @return 这一篇游记的实例 解析放到前端完成
     */
    @RequestMapping(value = "/getRoute",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getRoute(@RequestParam(name = "attsWant")String attsWant){
        List<Travelogue> yjList=routeService.listTravelogue();
        System.out.println("游记的数量"+yjList.size());
        List<Travelogue> list = routeService.getRouteByAtt(attsWant,yjList);
        System.out.println("符合att要求的路线数量"+list.size());
        if(list.size()>5){
            list = list.subList(0,5);
        }
        return CommonReturnType.create(list);
    }

    @RequestMapping(value = "/getRouteByChart",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getRouteByChart(@RequestParam(name = "chart")String chart){
        StringUtil util = new StringUtil();
        List<Travelogue> yjList=routeService.listTravelogue();
        List<Travelogue> list = routeService.getRouteByChart(chart,yjList);
        System.out.println("符合chart要求的路线数量"+list.size());
        if(list.size()>10){
            list = list.subList(0,10);
        }
        int index=0;
        while (list.size()<3&&index<yjList.size()){
            if (util.like(yjList.get(index).getKeywords(),chart)){
                list.add(yjList.get(index));
            }
            index++;
        }
        while (list.size()<5){
            Random random = new Random();
            list.add(yjList.get(random.nextInt(yjList.size())));
        }
        list=list.stream().distinct().collect(Collectors.toList());
        if (list.size()>3){
            list=list.subList(0,3);
        }
        return CommonReturnType.create(list);
    }

    @RequestMapping(value = "/listRoute",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listRoute(){
        List<Travelogue> yjList=routeService.listTravelogue();
        return CommonReturnType.create(yjList);
    }

}
